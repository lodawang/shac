<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>上海汇众文档发布系统</title>
<link href="css/login.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div class="mian">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td><img src="img/login_1.jpg" /></td>
      <td><img src="img/login_2.jpg" /></td>
    </tr>
    <tr>
      <td><img src="img/login_3.jpg" /></td>
      <td align="left" valign="top"><img src="img/login_4.jpg" /></td>
    </tr>
    <tr>
      <td><img src="img/login_5.jpg" /></td>
      <td align="left" valign="top" class="copy_bg">
      <form name="loginForm" action="<s:url value="/j_spring_security_check"/>" method="post">
      <table border="0" cellspacing="5" cellpadding="5">
        
		<tr>
          <td align="center" nowrap="nowrap">
				<span style="text-align:left;color:red;">
						<h2>受限访问</h2>
				</span>
          </td>
        </tr>
        
      </table>
      </form>
      </td>
    </tr>
    <tr>
      <td><img src="img/login_7.jpg" /></td>
      <td align="left" valign="top" ><img src="img/login_8.jpg" /></td>
    </tr>
  </table>
</div>
</body>
</html>
