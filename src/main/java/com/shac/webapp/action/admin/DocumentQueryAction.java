package com.shac.webapp.action.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.util.WebUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.shac.dao.hibernate.DictDataDaoHibernate;
import com.shac.dao.hibernate.IssueTaskDaoHibernate;
import com.shac.dao.hibernate.TaskDeptItemDaoHibernate;
import com.shac.dao.hibernate.UserDaoHibernate;
import com.shac.model.DictData;
import com.shac.model.IssueTask;
import com.shac.model.Role;
import com.shac.model.TaskDeptItem;
import com.shac.model.User;
import com.shac.util.Constants;
import com.shac.util.Page;

public class DocumentQueryAction extends ActionSupport{
	//分页参数
	private Integer totalCount;
	private Page pager;
	private Integer page = new Integer(1);
	
	private String id;
	private IssueTask docu;
	private IssueTaskDaoHibernate issueTaskDao;
	private DictDataDaoHibernate dictDataDao;
	private UserDaoHibernate userDao;
	private TaskDeptItemDaoHibernate taskDeptItemDao;
	
	private List clientList;
	private List modelList;
	private List drawingSizeList;//图幅
	private List techDocClassList;//技术文档类型
	private List docList;
	private String docType;
	
	//联动厂部和车间
		private List<DictData> deptList;
		private List<DictData> workshopList;
		private String querydep;
		private String querywork;
		private List deprelList;//这里存放的是厂部和部门的关系
		
   		
		
	
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


		public String getQuerydep() {
			return querydep;
		}


		public void setQuerydep(String querydep) {
			this.querydep = querydep;
		}


		public String getQuerywork() {
			return querywork;
		}


		public void setQuerywork(String querywork) {
			this.querywork = querywork;
		}


		public List getDeprelList() {
			return deprelList;
		}


		public void setDeprelList(List deprelList) {
			this.deprelList = deprelList;
		}

    
    //是否11年导入
	private String oldimport;
		
	public String getOldimport() {
		return oldimport;
	}


	public void setOldimport(String oldimport) {
		this.oldimport = oldimport;
	}


	@Actions({
		@Action(value="docQueryList",params={"docType","0"},results={@Result(name="success",location="docQueryList.jsp")}),
		@Action(value="docTechQueryList",params={"docType","1"},results={@Result(name="success",location="docTechQueryList.jsp")})
	})
	
	public String list(){
		clientList = dictDataDao.findByDictType(Constants.DICTTYPE_CLIENT);
		modelList = dictDataDao.findByDictType(Constants.DICTTYPE_MODEL);
		drawingSizeList = dictDataDao.findByDictType(Constants.DICTTYPE_DRAWINGSIZE);
		techDocClassList = dictDataDao.findByDictType(Constants.DICTTYPE_TECHDOCCLASS);
		deptList = dictDataDao.findByDictType(Constants.DICTTYPE_REGION);
		workshopList = dictDataDao.findByDictType(Constants.DICTTYPE_WORKSHOP);
		
		HttpServletRequest request = ServletActionContext.getRequest();
		Map filterMap = WebUtils.getParametersStartingWith(request, "docu.");
		Map orderMap = new HashMap();
		filterMap.put("history", "0");
		filterMap.put("docType", docType);
		if(oldimport!=null&&oldimport.equalsIgnoreCase("1")){
			filterMap.put("oldimport", oldimport);
		}
		
		//有厂部必定有发放
		if(querydep!=null && !querydep.equals("")){	
			filterMap.put("taskStatus", "1");
			//这里先查出所有符合的文档id
			filterMap.put("dept", querydep);
			if(querywork!=null && !querywork.equals("")){
				filterMap.put("querywork", querywork);
			}
		}
		orderMap.put("createTime", "desc");
		docList = issueTaskDao.findBy(filterMap, orderMap, (page.intValue() - 1) * 500, 500);
		totalCount = issueTaskDao.getCount(filterMap).intValue();
		return SUCCESS;
	}
	
	
	@Actions({
		@Action(value="docRecvQueryList",params={"docType","0"},results={@Result(name="success",location="docRecvQueryList.jsp")}),
		@Action(value="docRecvTechQueryList",params={"docType","1"},results={@Result(name="success",location="docRecvTechQueryList.jsp")})
	})
	
	public String recvList(){
		clientList = dictDataDao.findByDictType(Constants.DICTTYPE_CLIENT);
		modelList = dictDataDao.findByDictType(Constants.DICTTYPE_MODEL);
		drawingSizeList = dictDataDao.findByDictType(Constants.DICTTYPE_DRAWINGSIZE);
		techDocClassList = dictDataDao.findByDictType(Constants.DICTTYPE_TECHDOCCLASS);
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDao.findByLoginID(userDetails.getUsername());
		String dept = user.getRegion().getId();
		
		
		HttpServletRequest request = ServletActionContext.getRequest();
		Map filterMap = WebUtils.getParametersStartingWith(request, "docu.");
		Map orderMap = new HashMap();
		
		filterMap.put("dept", dept);
		filterMap.put("status", "1");
		filterMap.put("docType", docType);
		filterMap.put("history", "0");
		
		if(oldimport!=null&&oldimport.equalsIgnoreCase("1")){
			filterMap.put("oldimport", oldimport);
		}
		
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
		docList = taskDeptItemDao.findBy(filterMap, orderMap,(page.intValue() - 1) * 500, 500);
		totalCount = taskDeptItemDao.getCount(filterMap).intValue();
		return SUCCESS;
	}
	
	@Action(value="docReport",params={"docType","0"},results={@Result(name="success",location="docReport.jsp")})
	public String report(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Map filterMap = WebUtils.getParametersStartingWith(request, "docu.");
		Map orderMap = new HashMap();
		filterMap.put("history", "0");
		filterMap.put("docType", docType);
		filterMap.put("processIn","正式生产");
		orderMap.put("createTime", "desc");
		docList = issueTaskDao.findBy(filterMap, orderMap,(page.intValue() - 1) * 15, 15);
		totalCount = issueTaskDao.getCount(filterMap).intValue();
		return SUCCESS;
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

	public DictDataDaoHibernate getDictDataDao() {
		return dictDataDao;
	}

	public void setDictDataDao(DictDataDaoHibernate dictDataDao) {
		this.dictDataDao = dictDataDao;
	}

	public List getClientList() {
		return clientList;
	}

	public void setClientList(List clientList) {
		this.clientList = clientList;
	}

	public List getModelList() {
		return modelList;
	}

	public void setModelList(List modelList) {
		this.modelList = modelList;
	}

	public List getDrawingSizeList() {
		return drawingSizeList;
	}

	public void setDrawingSizeList(List drawingSizeList) {
		this.drawingSizeList = drawingSizeList;
	}

	public List getTechDocClassList() {
		return techDocClassList;
	}

	public void setTechDocClassList(List techDocClassList) {
		this.techDocClassList = techDocClassList;
	}

	public List getDocList() {
		return docList;
	}

	public void setDocList(List docList) {
		this.docList = docList;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}


	public UserDaoHibernate getUserDao() {
		return userDao;
	}


	public void setUserDao(UserDaoHibernate userDao) {
		this.userDao = userDao;
	}


	public TaskDeptItemDaoHibernate getTaskDeptItemDao() {
		return taskDeptItemDao;
	}


	public void setTaskDeptItemDao(TaskDeptItemDaoHibernate taskDeptItemDao) {
		this.taskDeptItemDao = taskDeptItemDao;
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
	
	
}
