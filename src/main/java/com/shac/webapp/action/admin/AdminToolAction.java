package com.shac.webapp.action.admin;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import com.opensymphony.xwork2.ActionSupport;
import com.shac.dao.hibernate.TaskDeptItemDaoHibernate;
import com.shac.dao.hibernate.UserDaoHibernate;
import com.shac.model.TaskDeptItem;
import com.shac.model.User;
import com.shac.util.Constants;

public class AdminToolAction extends ActionSupport{
	
	private String uloginId;
	private TaskDeptItemDaoHibernate taskDeptItemDao;
	private UserDaoHibernate userDao;
	
	@Action(value="batchConfirmReceive",results={@Result(name="success",location="batchConfirmReceive.jsp")})
	public String batchConfirmReceive(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		if(request.getMethod().equals("GET")){
			return SUCCESS;
		}else if(request.getMethod().equals("POST")){
			User user = userDao.findByLoginID(uloginId);
			if(user!=null){
				String dept = user.getRegion().getId();
				Map filterMap = new HashMap();
				Map orderMap = new HashMap();
				filterMap.put("dept", dept);
				filterMap.put("status", "0");
				List<TaskDeptItem> itemList = taskDeptItemDao.findByAll(filterMap, orderMap);
				for(TaskDeptItem item:itemList){
					taskDeptItemDao.updateHistoryBySysUID(item.getTask(),dept,Constants.ROLE_RECVADMIN,null);
				}
				addActionMessage("批量接收完毕");
			}else{
				addActionMessage("没有此用户");
			}
			return SUCCESS;
		}
		return SUCCESS;
	}

	public String getUloginId() {
		return uloginId;
	}

	public void setUloginId(String uloginId) {
		this.uloginId = uloginId;
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
}
