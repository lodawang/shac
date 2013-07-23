<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>打印版本处理中</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<%
		String tp = request.getParameter("tp");
	    if(tp.equals("ad")){
	%>
	<meta http-equiv="refresh" content="15;url=viewAdminPrintFile.do?id=<%=request.getParameter("id")%>"  />
	<%}else{%>
	<meta http-equiv="refresh" content="15;url=viewPrintFile.do?id=<%=request.getParameter("id")%>"  />	
	<%}%>
	
	<script language="javascript" type="text/javascript"> 
		var time =15;
		function countDown(){
			for(var i=time;i>=0;i--){ 
				window.setTimeout('updateTime(' + i + ')', (time-i) * 1000); 
			} 
		} 
		function updateTime(num){ 
			document.getElementById('timeDiv').innerHTML = num;
		}
		countDown();
	</script> 
  </head>
  <body>
    打印版本处理中，页面<span id="timeDiv"></span>秒后会自动跳转
  </body>
</html>
