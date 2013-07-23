package com.shac.webapp.action.admin;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.util.WebUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.shac.dao.hibernate.DictDataDaoHibernate;
import com.shac.dao.hibernate.IssueRuleDaoHibernate;
import com.shac.dao.hibernate.IssueTaskDaoHibernate;
import com.shac.dao.hibernate.MnoDaoHibernate;
import com.shac.dao.hibernate.SnoDaoHibernate;
import com.shac.dao.hibernate.TaskDeptItemDaoHibernate;
import com.shac.dao.hibernate.UserDaoHibernate;
import com.shac.dao.hibernate.ZnoDaoHibernate;
import com.shac.model.DictData;
import com.shac.model.IssueTask;
import com.shac.model.IssueTaskTC;
import com.shac.model.Role;
import com.shac.model.TaskDeptItem;
import com.shac.model.User;
import com.shac.service.MailEngine;
import com.shac.util.Constants;
import com.shac.util.Page;

public class ReceiveDocumentAction extends ActionSupport{
	//分页参数
	private Integer totalCount;
	private Page pager;
	private Integer page = new Integer(1);
	
	private String id;
	private TaskDeptItemDaoHibernate taskDeptItemDao;
	private UserDaoHibernate userDao;
	private List itemList;
	private TaskDeptItem deptItem;
	private MailEngine mailEngine;
	private String newVersionNotice;
	private List historyList;
	
	private IssueTask docu;
	private IssueTaskTC docutc;
	
	private IssueTaskDaoHibernate issueTaskDao;
	private IssueRuleDaoHibernate issueRuleDao;
	
	
	//是否工艺工程师
		private String is_engineer;
		
	//是否导入帐号
		private String is_dr;
		
    //是否可看发放范围
		private String is_issuesee;
		private List<DictData> deptList;
		private List<DictData> workshopList;
		private DictDataDaoHibernate dictDataDao;
		
		

		public List<DictData> getDeptList() {
			return deptList;
		}

		public void setDeptList(List<DictData> deptList) {
			this.deptList = deptList;
		}

		public List<DictData> getWorkshopList() {
			return workshopList;
		}

		public void setWorkshopList(List<DictData> workshopList) {
			this.workshopList = workshopList;
		}

		public DictDataDaoHibernate getDictDataDao() {
			return dictDataDao;
		}

		public void setDictDataDao(DictDataDaoHibernate dictDataDao) {
			this.dictDataDao = dictDataDao;
		}

		public String getIs_issuesee() {
			return is_issuesee;
		}

		public void setIs_issuesee(String is_issuesee) {
			this.is_issuesee = is_issuesee;
		}

		public IssueTaskTC getDocutc() {
			return docutc;
		}

		public void setDocutc(IssueTaskTC docutc) {
			this.docutc = docutc;
		}

		public String getIs_dr() {
			return is_dr;
		}

		public void setIs_dr(String is_dr) {
			this.is_dr = is_dr;
		}

		public String getIs_engineer() {
			return is_engineer;
		}

		public void setIs_engineer(String is_engineer) {
			this.is_engineer = is_engineer;
		}
	
	
	@Action(value="docInbox",results={@Result(name="success",location="docInbox.jsp")})
	public String list(){
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDao.findByLoginID(userDetails.getUsername());
		String dept = user.getRegion().getId();
		
		List<Role> roles = userDao.findRolesByUid(user.getId());
		boolean recvAdmin = false;
		boolean recvAdmin_ws = false;
		for(Role role:roles){
			if(role.getCode().equals(Constants.ROLE_RECVADMIN)){
				recvAdmin = true;
			}
			if(role.getCode().equals(Constants.ROLE_RECVADMIN_WORKSHOP)){
				recvAdmin_ws = true;
			}
		}
		//厂部管理员
		if(recvAdmin){
			Map filterMap = new HashMap();
			Map orderMap = new HashMap();
			filterMap.put("dept", dept);
			filterMap.put("status", "0");
			filterMap.put("role", Constants.ROLE_RECVADMIN);
			itemList = taskDeptItemDao.findByAll(filterMap, orderMap);
		}
		//车间管理员
		if(recvAdmin_ws){
			Map filterMap = new HashMap();
			Map orderMap = new HashMap();
			filterMap.put("dept", dept);
			filterMap.put("status", "0");
			//filterMap.put("role", Constants.ROLE_RECVADMIN_WORKSHOP);
			//filterMap.put("workshop", user.getWorkshop());
			if(user.getWorkshop()!=null){
				filterMap.put("item_workshop", user.getWorkshop());
				itemList = taskDeptItemDao.findByAll(filterMap, orderMap);
			}else{
				itemList = null;
			}
		}
		
		if(user.getIs_engineer()!=null&&user.getIs_engineer().equalsIgnoreCase("1")){ 
			is_engineer = user.getIs_engineer();			
		}else{
			is_engineer = "0";		
		}
		//只有DR开头的用户才能导入工艺
		if(user.getLoginID().toUpperCase().startsWith("DR")){
			is_dr = "1";
		}else{
			is_dr = "0";
		}
		
		return SUCCESS;
	}
	
	@Action(value="docProtectView",results={@Result(name="success",location="docProtectView.jsp")})
	public String protectView(){
		docu = issueTaskDao.get(id);
		return SUCCESS;
	}
	
	
	
	@Action(value="docLatest",results={@Result(name="success",location="docLatest.jsp")})
	public String latest(){
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDao.findByLoginID(userDetails.getUsername());
		String dept = user.getRegion().getId();
		
		HttpServletRequest request = ServletActionContext.getRequest();
		Map filterMap = WebUtils.getParametersStartingWith(request, "deptItem.");
		Map orderMap = new HashMap();
		if(filterMap.get("task.processIn")==null){
			filterMap.put("task.processIn", "正式生产");
		}
		filterMap.put("dept", dept);
		filterMap.put("status", "1");
		filterMap.put("history", "0");
		
		//兼顾全厂接收和部分车间接收的文档
		List<Role> roles = userDao.findRolesByUid(user.getId());
		boolean recvAdmin = false;
		for(Role role:roles){
			if(role.getCode().equals(Constants.ROLE_RECVADMIN)){
				recvAdmin = true;
			}
		}
		if(recvAdmin){
			filterMap.put("role", Constants.ROLE_RECVADMIN);
		}else{
			filterMap.put("role", Constants.ROLE_RECV);
			filterMap.put("workshop", user.getWorkshop());
		}
		
		orderMap.put("createTime", "desc");
		itemList = taskDeptItemDao.findBy(filterMap, orderMap,(page.intValue() - 1) * 10, 10);
		totalCount = taskDeptItemDao.getCount(filterMap).intValue();
		return SUCCESS;
	}
	
		
	@Actions({
		@Action(value="viewToRecive",results={@Result(name="success",location="viewToRecive.jsp")}),
		@Action(value="docPreview",results={@Result(name="success",location="docPreview.jsp")}),
		@Action(value="docPreviewPre",results={@Result(name="success",location="docPreviewPre.jsp")})})
	public String previewDoc(){
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User currentUser = userDao.findByLoginID(userDetails.getUsername());
		deptItem = taskDeptItemDao.get(id);
		
		//厂部管理员和厂部普通人员之间看到的某文档的历史文档信息不同
		List<Role> roles = userDao.findRolesByUid(currentUser.getId());
		String roleCode = "";
		DictData workshop = null;
		for(Role role:roles){
			if(role.getCode().equals(Constants.ROLE_RECVADMIN)){
				roleCode = Constants.ROLE_RECVADMIN;
			}else{
				roleCode = Constants.ROLE_RECV;
				workshop = currentUser.getWorkshop();
			}
		}
		
		List list = taskDeptItemDao.findMyLatestIssuesBySysUID(deptItem.getTask().getSysUID(),currentUser.getRegion().getId(),roleCode,workshop);
		if(list!=null && list.size()>0){
			TaskDeptItem item = (TaskDeptItem)list.get(0);
			newVersionNotice = item.getTask().getDocVersion();
		}
		
		//如果用户是可看发放范围的，可以看发放范围
		if (currentUser.getIssuesee()!=null&&currentUser.getIssuesee().equalsIgnoreCase("1")) {
			is_issuesee = "1";
			//初始化发放表
			docu = deptItem.getTask();
			deptList = dictDataDao.findByDictType(Constants.DICTTYPE_REGION);
			workshopList = dictDataDao.findByDictType(Constants.DICTTYPE_WORKSHOP);
			Map filterMap = new HashMap();
			filterMap.put("taskid", docu.getId());
			Map orderMap = new HashMap();
			List<TaskDeptItem> taskDepts = taskDeptItemDao.findByAll(filterMap, orderMap);
			for(DictData data:deptList){
				for(TaskDeptItem item:taskDepts){
					if(item.getWorkshop()==null && item.getDept().getId().equals(data.getId())){
						data.setSent(item.getStatus());
						data.setSignTime(item.getSignTime());
					}
				}
			}
			for(DictData dt:workshopList){
				for(TaskDeptItem item:taskDepts){
					if(item.getWorkshop()!=null && item.getWorkshop().getId().equals(dt.getId())){
						dt.setSent(item.getStatus());
						dt.setSignTime(item.getSignTime());
					}
				}
			}		
		} else {
			is_issuesee = "0";
		}
		
		
		return SUCCESS;
	}
	
	@Action(value="viewDocuMine",results={@Result(name="success",location="docuViewMine.jsp")})
	public String viewDoc(){
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User currentUser = userDao.findByLoginID(userDetails.getUsername());
		deptItem = taskDeptItemDao.get(id);
		if(deptItem.getHistory().equals("0")){
			//historyList = taskDeptItemDao.findMyHistoryIssues(deptItem.getTask().getPartid(),currentUser.getRegion().getId());
			
			//厂部管理员和厂部普通人员之间看到的某文档的历史文档信息不同
			List<Role> roles = userDao.findRolesByUid(currentUser.getId());
			String roleCode = "";
			DictData workshop = null;
			for(Role role:roles){
				if(role.getCode().equals(Constants.ROLE_RECVADMIN)){
					roleCode = Constants.ROLE_RECVADMIN;
				}else{
					roleCode = Constants.ROLE_RECV;
					workshop = currentUser.getWorkshop();
				}
			}
			
			historyList = taskDeptItemDao.findMyHistoryIssuesBySysUID(deptItem.getTask().getSysUID(),currentUser.getRegion().getId(),roleCode,workshop);
		}
		return SUCCESS;
	}
	
	/**
	 * 厂部管理员|车间管理员签收
	 * @return
	 */
	@Action(value="reciveDocu",results={@Result(name="success",location="docInbox.do",type="redirect"),@Result(name="forward",location="editDocuToForward.do?deptItemId=${id}",type="redirect")})
	public String reciveDocu(){
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User currentUser = userDao.findByLoginID(userDetails.getUsername());
		User releaseUser = userDao.findByLoginID(Constants.USER_RELEASEOP_LOGINID);
		
		TaskDeptItem item = taskDeptItemDao.get(id);
		docu = issueTaskDao.get(item.getTask().getId());
		
		//车间需签收的文档
		if(item.getWorkshop()!=null){
			//更新历史状态,发布状态
			taskDeptItemDao.updateHistoryBySysUID(item.getTask(), currentUser.getRegion().getId(),Constants.ROLE_RECVADMIN_WORKSHOP,currentUser.getWorkshop().getId());
			
			//发送邮件给厂部管理员及总部管理员
			if(Constants.SEND_RECEIVE_FEEDBACK){
				SimpleMailMessage feedbackMail = new SimpleMailMessage();
				feedbackMail.setFrom(currentUser.getEmail());
				feedbackMail.setCc(releaseUser.getEmail());
				User factoryAdminUser = userDao.findFactoryAdminUser(currentUser.getRegion().getId());
				if(factoryAdminUser!=null){
					feedbackMail.setTo(factoryAdminUser.getEmail());
				}else{
					feedbackMail.setTo(releaseUser.getEmail());
				}
				String subject = "文档发放签收回执[车间]--编号:"+docu.getPartid()+" 版本:"+docu.getDocVersion()+"["+currentUser.getRegion().getDictValue()+"]";
				feedbackMail.setSubject(subject);
		        String bodyText = currentUser.getRegion().getDictValue()+" "+ currentUser.getWorkshop().getDictValue() +"对编号:"+docu.getPartid()+" 版本:"+docu.getDocVersion()+"的文档已签收";
		        feedbackMail.setText(bodyText);
		        mailEngine.sendAsync(feedbackMail);
			}
	        return SUCCESS;
		}
		
		//判断是否需要二次发放
        List rules = issueRuleDao.findByDocument(docu);
        //本应有下级单位且文档符合二次发放规则
        if((currentUser.getRegion().getAlone()==null || !currentUser.getRegion().getAlone()) && rules!=null && rules.size()>0){
        	return "forward";
        }
		//更新历史状态,发布状态
		taskDeptItemDao.updateHistoryBySysUID(item.getTask(), currentUser.getRegion().getId(),Constants.ROLE_RECVADMIN,null);
		
		if(Constants.SEND_RECEIVE_FEEDBACK){
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom(currentUser.getEmail());
			mailMessage.setTo(releaseUser.getEmail());
			String subject = "文档发放签收回执--编号:"+docu.getPartid()+" 版本:"+docu.getDocVersion()+"["+currentUser.getRegion().getDictValue()+"]";
	        mailMessage.setSubject(subject);
	        String bodyText = currentUser.getRegion().getDictValue()+"对编号:"+docu.getPartid()+" 版本:"+docu.getDocVersion()+"的文档已签收";
	        mailMessage.setText(bodyText);
	        mailEngine.sendAsync(mailMessage);
		}
        return SUCCESS;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public TaskDeptItemDaoHibernate getTaskDeptItemDao() {
		return taskDeptItemDao;
	}


	public void setTaskDeptItemDao(TaskDeptItemDaoHibernate taskDeptItemDao) {
		this.taskDeptItemDao = taskDeptItemDao;
	}


	public UserDaoHibernate getUserDao() {
		return userDao;
	}


	public void setUserDao(UserDaoHibernate userDao) {
		this.userDao = userDao;
	}


	public List getItemList() {
		return itemList;
	}


	public void setItemList(List itemList) {
		this.itemList = itemList;
	}

	public TaskDeptItem getDeptItem() {
		return deptItem;
	}

	public void setDeptItem(TaskDeptItem deptItem) {
		this.deptItem = deptItem;
	}

	public MailEngine getMailEngine() {
		return mailEngine;
	}

	public void setMailEngine(MailEngine mailEngine) {
		this.mailEngine = mailEngine;
	}

	public String getNewVersionNotice() {
		return newVersionNotice;
	}

	public void setNewVersionNotice(String newVersionNotice) {
		this.newVersionNotice = newVersionNotice;
	}

	public List getHistoryList() {
		return historyList;
	}

	public void setHistoryList(List historyList) {
		this.historyList = historyList;
	}

	public IssueTask getDocu() {
		return docu;
	}

	public void setDocu(IssueTask docu) {
		this.docu = docu;
	}

	public IssueTaskDaoHibernate getIssueTaskDao() {
		return issueTaskDao;
	}

	public void setIssueTaskDao(IssueTaskDaoHibernate issueTaskDao) {
		this.issueTaskDao = issueTaskDao;
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

	public IssueRuleDaoHibernate getIssueRuleDao() {
		return issueRuleDao;
	}

	public void setIssueRuleDao(IssueRuleDaoHibernate issueRuleDao) {
		this.issueRuleDao = issueRuleDao;
	}
	
	
}
