package com.shac.webapp.action.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.util.WebUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.shac.dao.hibernate.PrintLogDaoHibernate;
import com.shac.dao.hibernate.UserDaoHibernate;
import com.shac.model.IssueTask;
import com.shac.model.PrintLog;
import com.shac.model.User;

@ParentPackage("json-default")
public class PrintLogAction extends ActionSupport{
	
	private PrintLogDaoHibernate printLogDao;
	private UserDaoHibernate userDao;
	private String docuId;
	private Map root;
	private List logList;
	
	@Action(value="logPrint",results={@Result(type="json",params={"root","root","contentType","text/html"})})
	public String logPrint(){
		HttpServletRequest request = ServletActionContext.getRequest();
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User currentUser = userDao.findByLoginID(userDetails.getUsername());
		
		IssueTask task = new IssueTask();
		task.setId(docuId);
		root = new HashMap();
		PrintLog log = new PrintLog();
		log.setPrintTime(new Date());
		log.setPrintIp(request.getRemoteAddr());
		log.setTask(task);
		log.setUser(currentUser);
		printLogDao.save(log);
		root.put("msg", "ok");
		return SUCCESS;
	}
	
	
	@Action(value="logPrintList",results={@Result(name="success",location="logPrintList.jsp")})
	public String logPrintList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Map filterMap = WebUtils.getParametersStartingWith(request, "docu.");
		Map orderMap = new HashMap();
		orderMap.put("printTime", "desc");
		logList = printLogDao.findByAll(filterMap, orderMap);
		return SUCCESS;
	}

	public PrintLogDaoHibernate getPrintLogDao() {
		return printLogDao;
	}

	public void setPrintLogDao(PrintLogDaoHibernate printLogDao) {
		this.printLogDao = printLogDao;
	}

	public UserDaoHibernate getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDaoHibernate userDao) {
		this.userDao = userDao;
	}

	public String getDocuId() {
		return docuId;
	}

	public void setDocuId(String docuId) {
		this.docuId = docuId;
	}

	public Map getRoot() {
		return root;
	}

	public void setRoot(Map root) {
		this.root = root;
	}


	public List getLogList() {
		return logList;
	}


	public void setLogList(List logList) {
		this.logList = logList;
	}
	
}
