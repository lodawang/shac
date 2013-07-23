package com.shac.webapp.action.admin;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import com.opensymphony.xwork2.ActionSupport;
import com.shac.dao.hibernate.UserDaoHibernate;
import com.shac.model.User;

public class UserSelfConfigAction extends ActionSupport{
	
	private UserDaoHibernate userDao;
	private User user;
	
	
	@Action(value="editProfile",results={@Result(name="success",location="editProfile.jsp")})
	public String editProfile(){
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		user = userDao.findByLoginID(userDetails.getUsername());
		return SUCCESS;
	}
	
	@Action(value="saveProfile",results={
			@Result(name="success",location="editProfile.do",type="redirect"),
			@Result(name="input",location="editProfile.jsp")
	})
	public String save(){
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User u = userDao.findByLoginID(userDetails.getUsername());
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		userDao.updateUserPassword(md5.encodePassword(user.getPassWord(), null),u.getId());
		return SUCCESS;
	}


	public UserDaoHibernate getUserDao() {
		return userDao;
	}


	public void setUserDao(UserDaoHibernate userDao) {
		this.userDao = userDao;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
