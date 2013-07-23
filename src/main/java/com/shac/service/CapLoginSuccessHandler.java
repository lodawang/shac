package com.shac.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.encoding.XMLType;

import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.tempuri.UserStateServiceLocator;
import org.tempuri.UserStateServiceSoapStub;

import com.shac.dao.hibernate.EmployeeNumberDaoHibernate;
import com.shac.dao.hibernate.UserDaoHibernate;
import com.shac.dao.hibernate.UserLogDaoHibernate;
import com.shac.model.EmployeeNumber;
import com.shac.model.User;
import com.shac.model.UserLog;
import com.shac.util.Constants;

public class CapLoginSuccessHandler implements AuthenticationSuccessHandler {
	
	private UserLogDaoHibernate userLogDao;
	private UserDaoHibernate userDao;
	private EmployeeNumberDaoHibernate employeeNumberDao;
	
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication auth) throws IOException,
			ServletException {
		
		boolean isAuthByEip = false;
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDao.findByLoginID(userDetails.getUsername());
						
		if(isAuthByEip){
			String OS = request.getParameter("os");			
			if(OS != null && OS.equalsIgnoreCase("1")){
				System.out.println("NEW");
				String passcode = request.getParameter("passcode");
				System.out.println("PASSCODE" + passcode);
				Service service = new Service();
				String url = "http://10.124.88.50/sso/SSO_Service.asmx";
				String namespace = "http://tempuri.org/";
				String actionUri = "GetUser";
				String op = "GetUser";
							
				try {
					Call call = (Call) service.createCall();
					call.setTargetEndpointAddress(new java.net.URL(url));
					call.setUseSOAPAction(true);
					call.setSOAPActionURI(namespace + actionUri);
					call.setOperationName(new QName(namespace, op));
					call.addParameter(new QName(namespace, "os"), XMLType.XSD_STRING,
							ParameterMode.IN);
					call.addParameter(new QName(namespace, "passcode"),
							XMLType.XSD_STRING, ParameterMode.IN);
					call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
					Object[] params = new Object[] {"1",passcode};
					String result = (String) call.invoke(params);
					System.out.println("ORC="+result);
					result = result.substring(1, result.length()-1);
					System.out.println("SUB="+result);
					JSONObject jsonObj = JSONObject.fromObject(result);
					String workno = jsonObj.getString("0");
					String reason = jsonObj.getString("1");
					System.out.println(workno + "=" + reason);
					if(workno!=null&&workno.length()>0){				
					Map filterMap = new HashMap();
					filterMap.put("empNumber", workno);
					List<EmployeeNumber> list = employeeNumberDao.findByAll(filterMap, null);
					if(list!=null && list.size()>0){
						EmployeeNumber employee = list.get(0);
						User bindUser = employee.getUser();
						if(!user.getLoginID().equals(bindUser.getLoginID())){
							//TODO清空认证信息
							response.sendRedirect("authFail.do");
							return;
						}
						HttpSession session = request.getSession();
						session.setAttribute("USER_JOBNO", workno);
					}else{
						//TODO清空认证信息
						response.sendRedirect("authFail.do");
						return;
					}
				}else {
					//TODO清空认证信息
					response.sendRedirect("authFail.do");
					return;
				}
								
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}						
			}else{
				System.out.println("OLD");
				String passcode = request.getParameter("passcode");
				UserStateServiceLocator client = new UserStateServiceLocator();
				UserStateServiceSoapStub binding;
				String ret = "";
				try {
					binding = (UserStateServiceSoapStub)client.getUserStateServiceSoap();
					ret = binding.getUserObjText(passcode);
					//System.out.println(ret);
				} catch (ServiceException e) {
					e.printStackTrace();
				}
				
				Document doc = null;
				try {
					doc = DocumentHelper.parseText(ret);
				} catch (DocumentException e) {
					e.printStackTrace();
				}
				Element root = doc.getRootElement();
				String code = root.elementText("code");
				if(code.equals("OK")){
					Node workNoNode = root.selectSingleNode("//description//SystemUser//WorkNo");
					String workNo = workNoNode.getText();
					Map filterMap = new HashMap();
					filterMap.put("empNumber", workNo);
					List<EmployeeNumber> list = employeeNumberDao.findByAll(filterMap, null);
					if(list!=null && list.size()>0){
						EmployeeNumber employee = list.get(0);
						User bindUser = employee.getUser();
						if(!user.getLoginID().equals(bindUser.getLoginID())){
							//TODO清空认证信息
							response.sendRedirect("authFail.do");
							return;
						}
						HttpSession session = request.getSession();
						session.setAttribute("USER_JOBNO", workNo);
					}else{
						//TODO清空认证信息
						response.sendRedirect("authFail.do");
						return;
					}
				}else if(code.equals("ERROR")){
					//TODO清空认证信息
					response.sendRedirect("authFail.do");
					return;
				}
			}	
		}
		
		
		
		UserLog log = new UserLog();
		log.setLogAction("i");
		log.setLoginIp(request.getRemoteAddr());
		log.setLoginTime(new Date());
		log.setUser(user);
		userLogDao.save(log);
		
		
		Collection<GrantedAuthority> auths = userDetails.getAuthorities();
		//
		//TODO  request.isUserInRole 替换现有用法
		//
		for(GrantedAuthority au:auths){
			if(au.getAuthority().equals(Constants.ROLE_ADMIN)){
				response.sendRedirect("admin/main.do");
			}
			if(au.getAuthority().equals(Constants.ROLE_RECV)){
				response.sendRedirect("admin/docInbox.do");
			}
			
		}
		
	}

	public UserLogDaoHibernate getUserLogDao() {
		return userLogDao;
	}
	
	@Autowired
    @Required
	public void setUserLogDao(UserLogDaoHibernate userLogDao) {
		this.userLogDao = userLogDao;
	}

	public UserDaoHibernate getUserDao() {
		return userDao;
	}
	
	@Autowired
    @Required
	public void setUserDao(UserDaoHibernate userDao) {
		this.userDao = userDao;
	}

	public EmployeeNumberDaoHibernate getEmployeeNumberDao() {
		return employeeNumberDao;
	}
	
	@Autowired
    @Required
	public void setEmployeeNumberDao(EmployeeNumberDaoHibernate employeeNumberDao) {
		this.employeeNumberDao = employeeNumberDao;
	}

}
