package com.shac.webapp.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.web.util.WebUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.shac.dao.hibernate.DictDataDaoHibernate;
import com.shac.model.DictData;
import com.shac.util.Constants;

public class DictDataAction extends ActionSupport implements Preparable{
	private DictDataDaoHibernate dictDataDao;
	private List dataList;
	private DictData data;
	private String id;
	private List<?> parentList;
	private String dataType;
	private List<DictData> deptList;
	private List<String>  deptSelected;


	@Action(value="dictDataList",results={@Result(name="success",location="dictDataList.jsp")})
	public String list(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Map filterMap = WebUtils.getParametersStartingWith(request, "data.");
		Map orderMap = new HashMap();
		orderMap.put("order", "desc");
		dataList = dictDataDao.findByAll(filterMap, orderMap);
		return SUCCESS;
	}
	
	@Action(value="dictDataEdit",results={@Result(name="success",location="dictDataEdit.jsp")})
	public String edit(){
		parentList = dictDataDao.findByAll(new HashMap(), new HashMap());
		deptSelected = new ArrayList();
		if(id!=null){
			data = dictDataDao.get(id);
			if(data.getIssue()!=null){
				String [] selecteddep = data.getIssue().split(",");
				for(int i=0 ;i <selecteddep.length; i++){
						deptSelected.add(selecteddep[i]);
				}		
			}
		}
		
		deptList = dictDataDao.findByDictType(Constants.DICTTYPE_REGION);
		return SUCCESS;
	}
	
	@Action(value="dictDataUp",results={@Result(name="success",location="dictDataList.jsp")})
	public String dictDataUp(){
		if(id!=null){
			data = dictDataDao.get(id);
			dictDataDao.updateOrder(id, "up");
			Map filterMap = new HashMap();
			filterMap.put("dictType", data.getDictType());
			Map orderMap = new HashMap();
			orderMap.put("order", "desc");
			dataList = dictDataDao.findByAll(filterMap, orderMap);
		}
		
		return SUCCESS;
	}
	
	@Action(value="dictDataDown",results={@Result(name="success",location="dictDataList.jsp")})
	public String dictDataDown(){
		if(id!=null){
			data = dictDataDao.get(id);
			dictDataDao.updateOrder(id, "down");
			Map filterMap = new HashMap();
			filterMap.put("dictType", data.getDictType());
			Map orderMap = new HashMap();
			orderMap.put("order", "desc");
			dataList = dictDataDao.findByAll(filterMap, orderMap);
		}
		return SUCCESS;
	}
	
	@Action(value="saveDictData",results={@Result(name="success",location="dictDataList.do?data.dictType=${dataType}",type="redirect")},interceptorRefs={@InterceptorRef(value="params",params={"excludeParams", "dojo\\..*,^struts\\..*,^__checkbox_.*"}),@InterceptorRef("defaultStack")})
	public String save(){
		boolean isNew = data.getId()==null||data.getId().equals("");
		String parentId = data.getParent().getId();
		if(parentId==null || parentId.equals("")){
			data.setParent(null);
		}
		//增加发放范围
		if(deptSelected!=null){
			String temp_dept = "";
			for(String dept:deptSelected){
				temp_dept = temp_dept + dept + ",";
			}
			data.setIssue(temp_dept);
		}else{
			data.setIssue("");
		}
		
		if(isNew){
			data.setStatus(Constants.TRUE_STRING);
			dictDataDao.saveDictData(data);
		}else{
			if(data.getParent()!=null && data.getId().equals(data.getParent().getId())){
				addActionError("上级设置有误，不能设置为自身");
				return INPUT;
			}
			dictDataDao.updateDictData(data);
		}
		dataType = data.getDictType();
		return SUCCESS;
	}
	
	public void prepareSave() throws Exception {
		parentList = dictDataDao.findByDictType(Constants.DICTTYPE_CLIENT);
		if(!data.getId().equals("")){
			data = dictDataDao.get(data.getId());
		}
	}
	
	

	public DictDataDaoHibernate getDictDataDao() {
		return dictDataDao;
	}

	public void setDictDataDao(DictDataDaoHibernate dictDataDao) {
		this.dictDataDao = dictDataDao;
	}

	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

	public DictData getData() {
		return data;
	}

	public void setData(DictData data) {
		this.data = data;
	}

	public void prepare() throws Exception {
	
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<?> getParentList() {
		return parentList;
	}

	public void setParentList(List<?> parentList) {
		this.parentList = parentList;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	public List<DictData> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<DictData> deptList) {
		this.deptList = deptList;
	}
    
	public List<String> getDeptSelected() {
		return deptSelected;
	}

	public void setDeptSelected(List<String> deptSelected) {
		this.deptSelected = deptSelected;
	}
}
