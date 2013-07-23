package com.shac.webapp.action;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.tempuri.UserStateServiceLocator;
import org.tempuri.UserStateServiceSoapStub;

import com.opensymphony.xwork2.ActionSupport;
import com.shac.dao.hibernate.EmployeeNumberDaoHibernate;
import com.shac.dao.hibernate.UserDaoHibernate;
import com.shac.model.EmployeeNumber;
import com.shac.model.Role;
import com.shac.model.User;
import com.shac.util.Constants;

public class PassportAction extends ActionSupport{
	
	private String passcode;
	private EmployeeNumberDaoHibernate employeeNumberDao;
	private UserDaoHibernate userDao;
	
	
	
	@Action(value="passport",results={@Result(name="login",location="login.do?passcode=${passcode}",type="redirect"),@Result(name="fail",location="authFail.jsp"),@Result(name="admin",location="admin/issueTaskList.do",type="redirect"),@Result(name="recv",location="admin/docInbox.do",type="redirect")})
	public String passport() throws Exception{
		if(passcode==null || passcode.trim().equals("")){
			return "fail";
		}
		UserStateServiceLocator client = new UserStateServiceLocator();
		UserStateServiceSoapStub binding = (UserStateServiceSoapStub)client.getUserStateServiceSoap();
		String ret = binding.getUserObjText(passcode);
		//System.out.println(ret);
		//ret = "<?xml version=\"1.0\" encoding=\"utf-8\"?><replay><code>OK</code><description><SystemUser><WorkNo>00001</WorkNo><UserName>姓名</UserName><SystemName>系统名</SystemName><Email> E-Mail </Email></SystemUser></description></replay>";
		Document doc = DocumentHelper.parseText(ret);
		Element root = doc.getRootElement();
		String code = root.elementText("code");
		
		if(code.equals("OK")){
			Node workNoNode = root.selectSingleNode("//description//SystemUser//WorkNo");
			String workNo = workNoNode.getText();
			Map filterMap = new HashMap();
			filterMap.put("empNumber", workNo);
			List<EmployeeNumber> list = employeeNumberDao.findByAll(filterMap, null);
			if(list!=null && list.size()>0){
				//实现自动登录--start
				/**
				EmployeeNumber employee = list.get(0);
				User user = employee.getUser();
				
				List<Role> roles = userDao.findRolesByUid(user.getId());
				Set<GrantedAuthority> authorities = new LinkedHashSet<GrantedAuthority>();
				authorities.addAll(roles);
				user.setAuthorities(authorities);
				
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, user.getPassWord(), user.getAuthorities());
				auth.setDetails(user);
				SecurityContextHolder.getContext().setAuthentication(auth);
				
				for(GrantedAuthority au:authorities){
					if(au.getAuthority().equals(Constants.ROLE_ADMIN)){
						return "admin";
					}
					if(au.getAuthority().equals(Constants.ROLE_RECV)){
						return "recv";
					}
					
				}
				*/
				//实现自动登录--end
				return "login";
				
			}else{
				return "fail";
			}
		}else if(code.equals("ERROR")){
			return "fail";
		}
		return "fail";
	}

	public String getPasscode() {
		return passcode;
	}

	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}

	public EmployeeNumberDaoHibernate getEmployeeNumberDao() {
		return employeeNumberDao;
	}

	public void setEmployeeNumberDao(EmployeeNumberDaoHibernate employeeNumberDao) {
		this.employeeNumberDao = employeeNumberDao;
	}

	public UserDaoHibernate getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDaoHibernate userDao) {
		this.userDao = userDao;
	}
}
