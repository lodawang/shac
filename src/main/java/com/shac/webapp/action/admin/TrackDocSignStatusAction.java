package com.shac.webapp.action.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.web.util.WebUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.shac.dao.hibernate.DictDataDaoHibernate;
import com.shac.dao.hibernate.TaskDeptItemDaoHibernate;
import com.shac.model.DictData;
import com.shac.model.IssueTask;
import com.shac.util.Constants;
import com.shac.util.Page;

public class TrackDocSignStatusAction extends ActionSupport{
	
	private TaskDeptItemDaoHibernate taskDeptItemDao;
	//分页参数
	private Integer totalCount;
	private Page pager;
	private Integer page = new Integer(1);
	
	private IssueTask docu;
	private String status;
	
	private List docList;

	//联动厂部和车间
	private List<DictData> deptList;
	private List<DictData> workshopList;
	private DictDataDaoHibernate dictDataDao;
	private String querydep;
	private String querywork;
	private List deprelList;//这里存放的是厂部和部门的关系
	
	//时间判断
	private String begindate;
	private String enddate;
	
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
	
	public String getBegindate() {
		return begindate;
	}

	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	@Action(value="trackDocSignStatus",results={@Result(name="success",location="trackDocSignStatus.jsp")})
	public String list(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Map filterMap = WebUtils.getParametersStartingWith(request, "docu.");
		if(status!=null && !status.equals("")){
			filterMap.put("status", status);
		}
		if(querydep!=null && !querydep.equals("")){			
			if(querywork!=null && !querywork.equals("")){
				DictData work = new DictData();
				work.setId(querywork);
				filterMap.put("item_workshop", work);
			}else{
			    filterMap.put("dept", querydep);
			    filterMap.put("nullitem_workshop", "1");
			}
		}
		
		
		if(begindate!=null&&enddate!=null&&!begindate.equals("")&&!enddate.equals("")){
			filterMap.put("begindate", begindate);
			filterMap.put("enddate", enddate);
		}
		
		Map orderMap = new HashMap();
		orderMap.put("status", "asc");
		orderMap.put("createTime", "desc");
		docList = taskDeptItemDao.findBy(filterMap, orderMap, (page.intValue() - 1) * 500, 500);
		totalCount = taskDeptItemDao.getCount(filterMap).intValue();
		
		deptList = dictDataDao.findByDictType(Constants.DICTTYPE_REGION);
		workshopList = dictDataDao.findByDictType(Constants.DICTTYPE_WORKSHOP);
		
		return SUCCESS;
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

	public List getDocList() {
		return docList;
	}

	public void setDocList(List docList) {
		this.docList = docList;
	}

	public IssueTask getDocu() {
		return docu;
	}

	public void setDocu(IssueTask docu) {
		this.docu = docu;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
