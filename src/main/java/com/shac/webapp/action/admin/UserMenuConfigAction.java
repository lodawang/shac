package com.shac.webapp.action.admin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.opensymphony.xwork2.ActionSupport;
import com.shac.dao.hibernate.UserMenuDaoHibernate;
import com.shac.model.Menu;
import com.shac.model.User;
import com.shac.model.UserMenu;
import com.shac.util.Constants;

public class UserMenuConfigAction extends ActionSupport{
	
	private UserMenuDaoHibernate userMenuDao;
	private List<Menu> myMenus;
	private List<Menu> roleMenu;
	
	
	private List allMenus;
	private List<String> selectedMenus;
	
	@Action(value="userMenuList",results={@Result(name="success",location="userMenuList.jsp")})
	public String list(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String userId = (String)request.getSession().getAttribute(Constants.SESSION_USER_ID);
		
		String roleCode = Constants.ROLE_USER;
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<GrantedAuthority> auths = userDetails.getAuthorities();

		for(GrantedAuthority auth:auths){
			if(auth.getAuthority().equals(Constants.ROLE_ADMIN)){
				roleCode = Constants.ROLE_ADMIN;
			}
		}
		
		
		List<Menu> temp = userMenuDao.listRoleMenu(roleCode);
		myMenus = userMenuDao.listMyMenu(userId);
		roleMenu = new ArrayList();
		for(Menu menu : temp){
			boolean added = false;
			for(Menu my : myMenus){
				if(menu.getId().equals(my.getId())){
					added = true;
				}
			}
			if(!added){
				roleMenu.add(menu);
			}
		}
		
		return SUCCESS;
	}
	
	@Action(value="saveMyMenu",results={@Result(name="success",location="userMenuList.do",type="redirect")})
	public String saveMyMenu(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String userId = (String)request.getSession().getAttribute(Constants.SESSION_USER_ID);
		User user = new User();
		user.setId(userId);
		//if(selectedMenus!=null && selectedMenus.size()>0){
			userMenuDao.removeUserMenu(userId);
			for(String s: selectedMenus){
				UserMenu um = new UserMenu();
				um.setUser(user);
				Menu menu = new Menu();
				menu.setId(s);
				um.setMenu(menu);
				userMenuDao.save(um);
			}
		//}
		HttpSession session = ServletActionContext.getRequest().getSession();
		List my = userMenuDao.listMyMenu(userId);
		session.setAttribute(Constants.SESSION_USER_MENU, my);
		return SUCCESS;
	}
	
	public UserMenuDaoHibernate getUserMenuDao() {
		return userMenuDao;
	}

	public void setUserMenuDao(UserMenuDaoHibernate userMenuDao) {
		this.userMenuDao = userMenuDao;
	}

	public List getAllMenus() {
		return allMenus;
	}

	public void setAllMenus(List allMenus) {
		this.allMenus = allMenus;
	}

	public List<String> getSelectedMenus() {
		return selectedMenus;
	}

	public void setSelectedMenus(List<String> selectedMenus) {
		this.selectedMenus = selectedMenus;
	}

	public List<Menu> getMyMenus() {
		return myMenus;
	}

	public void setMyMenus(List<Menu> myMenus) {
		this.myMenus = myMenus;
	}

	public List<Menu> getRoleMenu() {
		return roleMenu;
	}

	public void setRoleMenu(List<Menu> roleMenu) {
		this.roleMenu = roleMenu;
	}
	
	
}
