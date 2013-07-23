package com.shac.webapp.action.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.doomdark.uuid.UUIDGenerator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.util.WebUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.shac.dao.hibernate.AdviceDaoHibernate;
import com.shac.dao.hibernate.IssueTaskDaoHibernate;
import com.shac.dao.hibernate.UserDaoHibernate;
import com.shac.model.Advice;
import com.shac.model.IssueTask;
import com.shac.model.User;
import com.shac.util.Page;

public class AdviceAction extends ActionSupport implements Preparable{
	public static final UUIDGenerator uuidmaker = UUIDGenerator.getInstance();

	// 分页参数
	private Integer totalCount;
	private Page pager;
	private Integer page = new Integer(1);
	
	private String id;
	
	private IssueTask docu;
	
	private Advice advice;
	
	private IssueTaskDaoHibernate issueTaskDao;
	
	private AdviceDaoHibernate adviceDao;
	
	private UserDaoHibernate userDao;
	
	private String superu;
	
	private List itemList;
	
	private String ifrwd;
	
	private User curuser;
			
	public User getCuruser() {
		return curuser;
	}
	public void setCuruser(User curuser) {
		this.curuser = curuser;
	}
	public String getIfrwd() {
		return ifrwd;
	}
	public void setIfrwd(String ifrwd) {
		this.ifrwd = ifrwd;
	}
	public String getSuperu() {
		return superu;
	}
	public void setSuperu(String superu) {
		this.superu = superu;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	
	public Advice getAdvice() {
		return advice;
	}
	public void setAdvice(Advice advice) {
		this.advice = advice;
	}
	
	public AdviceDaoHibernate getAdviceDao() {
		return adviceDao;
	}
	public void setAdviceDao(AdviceDaoHibernate adviceDao) {
		this.adviceDao = adviceDao;
	}
	
	public UserDaoHibernate getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDaoHibernate userDao) {
		this.userDao = userDao;
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
	public List getItemList() {
		return itemList;
	}
	public void setItemList(List itemList) {
		this.itemList = itemList;
	}
	/**
	 *  编辑一级反馈
	 */
	@Action(value = "addlevelone", results = { @Result(name = "success", location = "addlevelone.jsp") })
	public String addlevelone(){		
		docu = issueTaskDao.get(id);
		return SUCCESS;
	}
	
	
	/**
	 * 保存一级反馈意见
	 */
	@Action(value = "saveadviceleveone",results={@Result(name="success",location="adviceList.do",type="redirect")})	
	public String saveadviceleveone(){
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User currentUser = userDao.findByLoginID(userDetails.getUsername());
		Advice adviceone = new Advice();
		adviceone.setAdlevel("0");
		adviceone.setBakeone(advice.getBakeone());
		adviceone.setCreateTime(new Date());
		IssueTask temp = issueTaskDao.get(id);
		adviceone.setTask(temp);		
		adviceone.setStatus("0");
		adviceone.setSenderid(currentUser);
		//判断要提交的用户
		User receiverUser = getReceiver(currentUser);		
		adviceone.setReceiverid(receiverUser);
		adviceDao.save(adviceone);
		
		return SUCCESS;
	}
	
	
	
	/**
	 * 获得要提交的用户
	 */
	public User getReceiver(User cuser){
		User flag = null;
		//如果用户是厂部管理员就直接发给super，要不然发给厂部管理员
		if(cuser.getRwd()!=null&&cuser.getRwd().equalsIgnoreCase("1")){			
			flag = userDao.findByLoginID("super");
		}else{			
			flag = userDao.findWriteUserByDept(cuser.getRegion().getId());
		}		
		return flag;
	}
	
	
	/**
	 * 查看反馈意见
	 */
	@Action(value = "viewAdvice", results = { @Result(name = "success", location = "viewAdvice.jsp") })
	public String viewAdvice(){
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User currentUser = userDao.findByLoginID(userDetails.getUsername());
		if(currentUser.getLoginID().equalsIgnoreCase("super")){
			superu = "1";			
		}
		advice = adviceDao.get(id);
		return SUCCESS;
	}
	
	/**
	 * 反馈意见列表
	 */
	@Action(value = "adviceList", results = { @Result(name = "success", location = "adviceList.jsp") })
	public String adviceList(){
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User currentUser = userDao.findByLoginID(userDetails.getUsername());
		Map filterMap = new HashMap();
		Map orderMap = new HashMap();
        
		if(currentUser.getLoginID().equalsIgnoreCase("super")){
			User superuser =  userDao.findByLoginID("super");
			filterMap.put("receiveridsuper", superuser);
			superu = "1";
		}else if(currentUser.getRwd()!=null&&currentUser.getRwd().equalsIgnoreCase("1")){
			filterMap.put("rwd", currentUser);
			ifrwd = "1";
		}else{
			filterMap.put("senderid", currentUser);	
		}
		
		orderMap.put("createTime", "desc");
		itemList = adviceDao.findBy(filterMap, orderMap,(page.intValue() - 1) * 10, 10);
		totalCount = adviceDao.getCount(filterMap).intValue();
		curuser = currentUser;
		
		return SUCCESS;
	}
	
	/**
	 * 处理意见
	 */
	@Action(value = "dealAdvice",results = { @Result(name = "success", location = "dealAdvice.jsp") })
	public String dealAdvice(){
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User currentUser = userDao.findByLoginID(userDetails.getUsername());
		if(currentUser.getLoginID().equalsIgnoreCase("super")){
			superu = "1";			
		}
		advice = adviceDao.get(id);
		return SUCCESS;
	}
	
	/**
	 * 保存处理意见
	 */
	@Action(value = "saveadvicedeal",results = { @Result(name = "success", location="adviceList.do",type="redirect") })
	public String saveadvicedeal(){
		Advice resadvice = adviceDao.get(id);
		
		adviceDao.updateStatus(id,"2",advice.getResultbake());
		
		if(resadvice!=null&&resadvice.getFid()!=null){
			adviceDao.updateStatus(resadvice.getFid(),"2",advice.getResultbake());
			
		}
		
		return SUCCESS;
	}
	
	/**
	 * 厂部给管理员提交反馈意见
	 */
	@Action(value = "upAdvice",results = { @Result(name = "success", location = "upAdvice.jsp") })
	public String upAdvice(){
		advice = adviceDao.get(id);
		return SUCCESS;
	}
	
	/**
	 * 2次提交
	 */
	@Action(value = "saveadvicedealtwo",results = { @Result(name = "success", location="adviceList.do",type="redirect") })
	public String saveadvicedealtwo(){
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User currentUser = userDao.findByLoginID(userDetails.getUsername());
		//新的提交ID
		Advice advicetwo = new Advice();
		advicetwo.setAdlevel("1");
		advicetwo.setBakeone(advice.getBakeone());
		advicetwo.setBaketwo(advice.getBaketwo());
		advicetwo.setCreateTime(new Date());
		IssueTask temp = issueTaskDao.get(advice.getTask().getId());
		advicetwo.setTask(temp);		
		advicetwo.setStatus("0");
		advicetwo.setSenderid(currentUser);
		advicetwo.setFid(id);
		//判断要提交的用户
		User receiverUser = getReceiver(currentUser);		
		advicetwo.setReceiverid(receiverUser);
		adviceDao.save(advicetwo);	
		
		return SUCCESS;
	}
	
	
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
