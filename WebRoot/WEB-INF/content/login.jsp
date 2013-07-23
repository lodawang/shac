<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>上海汇众文档发布系统</title>
<link href="css/login.css" rel="stylesheet" type="text/css" />
<script src="./js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
	$(function(){
	    $("#j_username").blur(function() {
			$(this).val($(this).val().toLowerCase());
		});
	});
</script>
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
      <s:hidden name="passcode" value="%{#parameters.passcode[0]}"/>
      <table border="0" cellspacing="5" cellpadding="5">
        <tr>
          <th width="20%" align="right" nowrap="nowrap">用户名：</th>
          <td><input name="j_username" type="text" class="user_bg" id="j_username" /></td>
        </tr>
        <tr>
          <th align="right" nowrap="nowrap">密码：</th>
          <td><input name="j_password" type="password" class="user_bg" id="j_password" /></td>
        </tr>
		<tr>
          <td align="center" nowrap="nowrap" colspan="2">
			<s:if test='%{#parameters.login_error[0] == "1"}'>
				<span style="text-align:left;color:red;">
						用户名或密码有误 
				</span>
			</s:if>
          </td>
        </tr>
        <tr>
          <th align="right">&nbsp;</th>
          <td><input type="image" src="img/btn_login.png" border="0" /></td>
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
