package com.shac.webapp.action.admin;

import java.util.Collection;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import com.opensymphony.xwork2.ActionSupport;
import com.shac.util.Constants;

public class MenuAction extends ActionSupport{

	@Action(results={@Result(name="successAdmin",location="../inc_adminMenu.jsp"),
			@Result(name="successUser",location="../inc_userMenu.jsp")}
			)
	public String userMenu(){
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<GrantedAuthority> auths = userDetails.getAuthorities();
		//TODO 读取角色菜单表生成菜单
		String result = "successUser";
		for(GrantedAuthority auth:auths){
			if(auth.getAuthority().equals(Constants.ROLE_ADMIN)){
				result = "successAdmin";
			}
			
		}
		return result;
	}
	
	/**
	 * 该方法用来解决父页面验证出错返回input时，该action也返回input的问题
	 */
	public void validateUserMenu() {
		this.clearErrorsAndMessages();
	}

}
