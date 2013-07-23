package com.shac.webapp.action.admin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import com.opensymphony.xwork2.ActionSupport;
import com.shac.dao.hibernate.IssueTaskDaoHibernate;
import com.shac.model.IssueTask;

public class DocumentFileReaderAction extends ActionSupport{
	
	private String fid;
	private IssueTaskDaoHibernate issueTaskDao;
	
	@Action(value="fileRead",results={@Result(name="success",location="/")})
	public String fileRead(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String referer = request.getHeader("Referer");
		if(referer==null || !referer.endsWith("FlexPaperViewer.swf")){
			try {
				response.sendError(404);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		IssueTask task = issueTaskDao.get(fid);
		String filePath = task.getViewFile();
		String realPath = ServletActionContext.getServletContext().getRealPath(filePath);
		File swfFile = new File(realPath);
		FileInputStream fis = null;
		BufferedOutputStream fos = null;
		try {
			response.setHeader("Pragma","No-cache");
			response.setHeader("Cache-Control","no-cache");
			response.setDateHeader("Expires",0);
			OutputStream output = response.getOutputStream();
			fos = new BufferedOutputStream(output);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			fis = new FileInputStream(swfFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		int bytesRead = 0;
		byte[] buffer = new byte[1024];
		try {
			while ((bytesRead = fis.read(buffer, 0, 1024)) != -1) {
				fos.write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			fis.close();
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public IssueTaskDaoHibernate getIssueTaskDao() {
		return issueTaskDao;
	}

	public void setIssueTaskDao(IssueTaskDaoHibernate issueTaskDao) {
		this.issueTaskDao = issueTaskDao;
	}
}
