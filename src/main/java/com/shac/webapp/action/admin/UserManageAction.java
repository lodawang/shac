package com.shac.webapp.action.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.web.util.WebUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.shac.dao.hibernate.DictDataDaoHibernate;
import com.shac.dao.hibernate.EmployeeNumberDaoHibernate;
import com.shac.dao.hibernate.RoleDaoHibernate;
import com.shac.dao.hibernate.RoleUserDaoHibernate;
import com.shac.dao.hibernate.UserDaoHibernate;
import com.shac.model.EmployeeNumber;
import com.shac.model.Role;
import com.shac.model.RoleUser;
import com.shac.model.User;
import com.shac.util.Constants;
import com.shac.util.Page;

public class UserManageAction extends ActionSupport implements Preparable{
	//分页参数
	private Integer totalCount;
	private Page pager;
	private Integer page = new Integer(1);
	
	private UserDaoHibernate userDao;
	private DictDataDaoHibernate dictDataDao;
	private RoleDaoHibernate roleDao;
	private RoleUserDaoHibernate roleUserDao;
	
	
	private List userList;
	private User user;
	private List regionList;
	private List workshopList;
	
	private String roleName;
	
	private String id;
	private String qname;
	private String qstatus;
	private String qregionid;
	
	private EmployeeNumber employeeNumber;
	private List employeeList;
	private EmployeeNumberDaoHibernate employeeNumberDao;
	private String userid;
	
	
	
	@Action(value="userMngList",results={@Result(name="success",location="userMngList.jsp")})
	public String list(){
		regionList = dictDataDao.findByDictType(Constants.DICTTYPE_REGION);
		HttpServletRequest request = ServletActionContext.getRequest();
		Map filterMap = WebUtils.getParametersStartingWith(request, "");
		//filterMap.put("status", Constants.TRUE_STRING);
		Map orderMap = new HashMap();
		orderMap.put("createTime", "desc");
		userList = userDao.findBy(filterMap, orderMap, (page.intValue() - 1) * 10, 10);
		totalCount = userDao.getCount(filterMap).intValue();
		return SUCCESS;
	}
	
	@Action(value="userMngEdit",results={@Result(name="success",location="userMngEdit.jsp")})
	public String edit(){
		regionList = dictDataDao.findByDictType(Constants.DICTTYPE_REGION);
		workshopList = dictDataDao.findByDictType(Constants.DICTTYPE_WORKSHOP);
		if(id!=null){
			user = userDao.get(id);
			List<Role> roles = userDao.findRolesByUid(id);
			for(Role role:roles){
				if(role.getCode().equals(Constants.ROLE_ADMIN)){
					roleName = "techAdmin";//TODO 常量化
					break;
				}
				if(role.getCode().equals(Constants.ROLE_RECV)){
					roleName = "deptUser";//TODO 常量化
				}
				if(role.getCode().equals(Constants.ROLE_RECVADMIN)){
					roleName = "deptAdmin";//TODO 常量化
					break;
				}
				if(role.getCode().equals(Constants.ROLE_RECVADMIN_WORKSHOP)){
					roleName = "workshopAdmin";//TODO 常量化
					break;
				}
			}
			Map filterMap = new HashMap();
			Map orderMap = new HashMap();
			filterMap.put("userid", id);
			employeeList = employeeNumberDao.findByAll(filterMap, orderMap);
		}
		return SUCCESS;
	}
	
	@Action(value="userMngSave",results={@Result(name="success",location="userMngList.do",type="redirect"),@Result(name="input",location="userMngEdit.jsp")}
	,interceptorRefs={@InterceptorRef("params"),@InterceptorRef("defaultStack")}
	)
	public String save(){
		boolean isNew = user.getId()==null||user.getId().equals("");
		if(isNew){
			Map filterMap = new HashMap();
			filterMap.put("loginID", user.getLoginID());
			boolean userExist = userDao.getCount(filterMap).intValue()>0;
			if(userExist){
				addFieldError("user.loginID", "该登陆名已存在");
				regionList = dictDataDao.findByDictType(Constants.DICTTYPE_REGION);
				return INPUT;
			}
			
			//启用标记
			user.setStatus(Constants.TRUE_STRING);
			Md5PasswordEncoder md5 = new Md5PasswordEncoder(); 
			user.setPassWord(md5.encodePassword(user.getPassword(), null));			
			user.setCreateTime(new Date());
			String regionId = user.getRegion().getId();
			if(regionId==null || regionId.equals("")){
				user.setRegion(null);
			}
			if(roleName.equals("deptAdmin")){//TODO 常量化
				user.setRwd("1");//TODO 常量化
			}
			
			String workshopId = user.getWorkshop().getId();
			if(workshopId==null || workshopId.equals("")){
				user.setWorkshop(null);
			}
			
			String userId = userDao.saveUser(user);
			if(roleName.equals("techAdmin")){//TODO 常量化
				Role adminRole = roleDao.getRoleByCode(Constants.ROLE_ADMIN);
				RoleUser roleUser = new RoleUser();
				roleUser.setRoleId(adminRole.getId());
				roleUser.setUserId(userId);
				roleUserDao.save(roleUser);
			}
			if(roleName.equals("deptAdmin")){//TODO 常量化
				Role recvAdminRole = roleDao.getRoleByCode(Constants.ROLE_RECVADMIN);
				RoleUser roleUserRecvAdmin = new RoleUser();
				roleUserRecvAdmin.setRoleId(recvAdminRole.getId());
				roleUserRecvAdmin.setUserId(userId);
				roleUserDao.save(roleUserRecvAdmin);
				
				Role recvRole = roleDao.getRoleByCode(Constants.ROLE_RECV);
				RoleUser roleUser = new RoleUser();
				roleUser.setRoleId(recvRole.getId());
				roleUser.setUserId(userId);
				roleUserDao.save(roleUser);
			}
			if(roleName.equals("workshopAdmin")){//TODO 常量化
				Role recvAdminRole = roleDao.getRoleByCode(Constants.ROLE_RECVADMIN_WORKSHOP);
				RoleUser roleUserRecvAdmin = new RoleUser();
				roleUserRecvAdmin.setRoleId(recvAdminRole.getId());
				roleUserRecvAdmin.setUserId(userId);
				roleUserDao.save(roleUserRecvAdmin);
				
				Role recvRole = roleDao.getRoleByCode(Constants.ROLE_RECV);
				RoleUser roleUser = new RoleUser();
				roleUser.setRoleId(recvRole.getId());
				roleUser.setUserId(userId);
				roleUserDao.save(roleUser);
			}
			if(roleName.equals("deptUser")){//TODO 常量化
				Role recvRole = roleDao.getRoleByCode(Constants.ROLE_RECV);
				RoleUser roleUser = new RoleUser();
				roleUser.setRoleId(recvRole.getId());
				roleUser.setUserId(userId);
				roleUserDao.save(roleUser);
			}
			Role userRole = roleDao.getRoleByCode(Constants.ROLE_USER);
			RoleUser roleUser = new RoleUser();
			roleUser.setRoleId(userRole.getId());
			roleUser.setUserId(userId);
			roleUserDao.save(roleUser);
		}else{
			String userId = user.getId();
			roleUserDao.deleRoleByUser(userId);
									
			if(user.getPassWord()==null || user.getPassWord().equals("")){
				String passsword =  userDao.get(userId).getPassword();
				user.setPassWord(passsword);
			}else{
				Md5PasswordEncoder md5 = new Md5PasswordEncoder(); 
				user.setPassWord(md5.encodePassword(user.getPassword(), null));
			}
			
			String regionId = user.getRegion().getId();
			if(regionId==null || regionId.equals("")){
				user.setRegion(null);
			}
			if(roleName.equals("deptAdmin")){//TODO 常量化
				user.setRwd("1");//TODO 常量化
			}
			if(roleName.equals("deptUser")){//TODO 常量化
				user.setRwd("0");//TODO 常量化
			}
			
			String workshopId = user.getWorkshop().getId();
			if(workshopId==null || workshopId.equals("")){
				user.setWorkshop(null);
			}
			
			userDao.updateUser(user);
			
			 
			if(roleName.equals("techAdmin")){//TODO 常量化
				Role adminRole = roleDao.getRoleByCode(Constants.ROLE_ADMIN);
				RoleUser roleUser = new RoleUser();
				roleUser.setRoleId(adminRole.getId());
				roleUser.setUserId(userId);
				roleUserDao.save(roleUser);
			}
			if(roleName.equals("deptAdmin")){//TODO 常量化
				Role recvAdminRole = roleDao.getRoleByCode(Constants.ROLE_RECVADMIN);
				RoleUser roleUserRecvAdmin = new RoleUser();
				roleUserRecvAdmin.setRoleId(recvAdminRole.getId());
				roleUserRecvAdmin.setUserId(userId);
				roleUserDao.save(roleUserRecvAdmin);
				
				Role recvRole = roleDao.getRoleByCode(Constants.ROLE_RECV);
				RoleUser roleUser = new RoleUser();
				roleUser.setRoleId(recvRole.getId());
				roleUser.setUserId(userId);
				roleUserDao.save(roleUser);
			}
			if(roleName.equals("workshopAdmin")){//TODO 常量化
				Role recvAdminRole = roleDao.getRoleByCode(Constants.ROLE_RECVADMIN_WORKSHOP);
				RoleUser roleUserRecvAdmin = new RoleUser();
				roleUserRecvAdmin.setRoleId(recvAdminRole.getId());
				roleUserRecvAdmin.setUserId(userId);
				roleUserDao.save(roleUserRecvAdmin);
				
				Role recvRole = roleDao.getRoleByCode(Constants.ROLE_RECV);
				RoleUser roleUser = new RoleUser();
				roleUser.setRoleId(recvRole.getId());
				roleUser.setUserId(userId);
				roleUserDao.save(roleUser);
			}
			if(roleName.equals("deptUser")){//TODO 常量化
				Role recvRole = roleDao.getRoleByCode(Constants.ROLE_RECV);
				RoleUser roleUser = new RoleUser();
				roleUser.setRoleId(recvRole.getId());
				roleUser.setUserId(userId);
				roleUserDao.save(roleUser);
			}
			Role userRole = roleDao.getRoleByCode(Constants.ROLE_USER);
			RoleUser roleUser = new RoleUser();
			roleUser.setRoleId(userRole.getId());
			roleUser.setUserId(userId);
			roleUserDao.save(roleUser);
		}
		return SUCCESS;
	}
	
	@Action(value="delIUser",results={@Result(name="success",location="userMngList.do",type="redirect")})
	public String deleteUser(){
		if(id!=null){
			userDao.updateUserStatus(Constants.FALSE_STRING, id);
		}
		return SUCCESS;
	}
	
	@Action(value="enableIUser",results={@Result(name="success",location="userMngList.do",type="redirect")})
	public String enableUser(){
		if(id!=null){
			userDao.updateUserStatus(Constants.TRUE_STRING, id);
		}
		return SUCCESS;
	}
	
	@Action(value="bindEmpNumber",results={@Result(name="success",location="userMngEdit.do?id=${userid}",type="redirect"),@Result(name="input",location="userMngEdit.jsp")}
	,interceptorRefs={@InterceptorRef("params"),@InterceptorRef("defaultStack")}
	)
	public String bindEmpNumber(){
		Map filterMap = new HashMap();
		filterMap.put("empNumber", employeeNumber.getEmpNumber());
		int count = employeeNumberDao.getCount(filterMap).intValue();
		if(count>0){
			List<EmployeeNumber> empList = employeeNumberDao.findByAll(filterMap, null);
			EmployeeNumber empNumbObj =  empList.get(0);
			
			regionList = dictDataDao.findByDictType(Constants.DICTTYPE_REGION);
			user = userDao.get(employeeNumber.getUser().getId());
			List<Role> roles = userDao.findRolesByUid(employeeNumber.getUser().getId());
			for(Role role:roles){
				if(role.getCode().equals(Constants.ROLE_ADMIN)){
					roleName = "techAdmin";//TODO 常量化
					break;
				}
				if(role.getCode().equals(Constants.ROLE_RECV)){
					roleName = "deptUser";//TODO 常量化
				}
				if(role.getCode().equals(Constants.ROLE_RECVADMIN)){
					roleName = "deptAdmin";//TODO 常量化
					break;
				}
			}
			filterMap = new HashMap();
			Map orderMap = new HashMap();
			filterMap.put("userid", employeeNumber.getUser().getId());
			employeeList = employeeNumberDao.findByAll(filterMap, orderMap);
			
			addActionError("该工号已被系统用户[ "+empNumbObj.getUser().getLoginID()+" ]绑定");
			
			return INPUT;
		}
		employeeNumberDao.save(employeeNumber);
		userid = employeeNumber.getUser().getId();
		return SUCCESS;
	}
	
	@Action(value="unBindEmpNumber",results={@Result(name="success",location="userMngEdit.do?id=${userid}",type="redirect")})
	public String unBindEmpNumber(){
		EmployeeNumber empNumb = employeeNumberDao.get(id);
		employeeNumberDao.remove(id);
		userid = empNumb.getUser().getId();
		return SUCCESS;
	}
		


	public Integer getTotalCount() {
		return totalCount;
	}


	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}


	public Page getPager() {
		return pager;
	}


	public void setPager(Page pager) {
		this.pager = pager;
	}


	public Integer getPage() {
		return page;
	}


	public void setPage(Integer page) {
		this.page = page;
	}


	public UserDaoHibernate getUserDao() {
		return userDao;
	}


	public void setUserDao(UserDaoHibernate userDao) {
		this.userDao = userDao;
	}


	public DictDataDaoHibernate getDictDataDao() {
		return dictDataDao;
	}


	public void setDictDataDao(DictDataDaoHibernate dictDataDao) {
		this.dictDataDao = dictDataDao;
	}


	public List getUserList() {
		return userList;
	}


	public void setUserList(List userList) {
		this.userList = userList;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public List getRegionList() {
		return regionList;
	}


	public void setRegionList(List regionList) {
		this.regionList = regionList;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

	public void prepare() throws Exception {
	}
	
	public void prepareSave() throws Exception {
		if(!user.getId().equals("")){
			user = userDao.get(user.getId());
		}
	}
	
	public void prepareSaveCooper() throws Exception {
		if(!user.getId().equals("")){
			user = userDao.get(user.getId());
		}
	}
	
	public RoleDaoHibernate getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDaoHibernate roleDao) {
		this.roleDao = roleDao;
	}

	public RoleUserDaoHibernate getRoleUserDao() {
		return roleUserDao;
	}

	public void setRoleUserDao(RoleUserDaoHibernate roleUserDao) {
		this.roleUserDao = roleUserDao;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getQname() {
		return qname;
	}

	public void setQname(String qname) {
		this.qname = qname;
	}

	public EmployeeNumber getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(EmployeeNumber employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public EmployeeNumberDaoHibernate getEmployeeNumberDao() {
		return employeeNumberDao;
	}

	public void setEmployeeNumberDao(EmployeeNumberDaoHibernate employeeNumberDao) {
		this.employeeNumberDao = employeeNumberDao;
	}

	public List getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List employeeList) {
		this.employeeList = employeeList;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getQstatus() {
		return qstatus;
	}

	public void setQstatus(String qstatus) {
		this.qstatus = qstatus;
	}

	public List getWorkshopList() {
		return workshopList;
	}

	public void setWorkshopList(List workshopList) {
		this.workshopList = workshopList;
	}

	public String getQregionid() {
		return qregionid;
	}

	public void setQregionid(String qregionid) {
		this.qregionid = qregionid;
	}
	
}
