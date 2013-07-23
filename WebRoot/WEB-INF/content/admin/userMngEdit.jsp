<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>上海汇众文档发布系统</title>
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<script src="../js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

$(function(){
	var workshops = [];
	<s:iterator value="workshopList" status="stat">
	workshops["<s:property value="id" escape="false"/>"] = new Array("<s:property value="dictValue" escape="false"/>","<s:property value="parent.id" escape="false"/>");
	</s:iterator>
	
	$("#user_region_id").change(
		function(){
			$("#user_workshop_id").empty();
			$("<option value=''>请选择</option>").appendTo("#user_workshop_id");
			for(prop in workshops){
				if(workshops[prop][1]==$(this).val()){
					$("<option value='"+prop+"'>"+workshops[prop][0]+"</option>").appendTo("#user_workshop_id");
				}
			}
		}	
	);
	
	$("#user_region_id").change();
	$("#user_workshop_id").val('<s:property value="user.workshop.id"/>');
});
  $(function(){
	    $("#user_loginID").blur(function() {
			$(this).val($(this).val().toLowerCase());
		});
	});
</script>
<style>
	.errorMessage{
		color:red;
	}
</style>
</head>

<body onload="MM_preloadImages('../img/btn_wdxc.png','../img/btn_jcsj.png','../img/btn_yhgl.png','../img/btn_tjfx.png','../img/btn_xtsz.png')">
<jsp:include page="inc_header.jsp"></jsp:include>
<div class="main">
    <div class="left">
    <ul class="menu">
      <li class="menu_1" ><a href="issueTaskList.do" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('文档发布','','../img/btn_wdfb.png',1)"><img src="../img/btn_wdfb_g.png" alt="文档发布" name="文档发布" width="170" height="69" border="0" id="文档发布"/></a></li>
      <li class="menu_1" ><a href="listManaDocu.do" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('文档管理','','../img/btn_wdgl.png',1)"><img src="../img/btn_wdgl_g.png" alt="文档管理" name="文档管理" width="170" height="69" border="0" id="文档管理" /></a></li>
      <li class="menu_1" ><a href="docQueryList.do" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('文档查询','','../img/btn_wdxc.png',1)"><img src="../img/btn_wdxc_g.png" alt="文档查询" name="文档查询" width="170" height="69" border="0" id="文档查询" /></a></li>
      <li class="menu_1" ><a href="dictDataList.do" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('基础数据','','../img/btn_jcsj.png',1)"><img src="../img/btn_jcsj_g.png" alt="基础数据" name="基础数据" width="170" height="69" border="0" id="基础数据" /></a></li>
      <li class="menu_2" ><a href="userMngList.do"><img src="../img/btn_yhgl.png"/></a></li>
      <li class="menu_1" ><a href="docReport.do" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('统计分析','','../img/btn_tjfx.png',1)"><img src="../img/btn_tjfx_g.png" alt="统计分析" name="统计分析" width="170" height="69" border="0" id="统计分析" /></a></li>
      <li class="menu_1" ><a href="issueRuleList.do" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('系统设置','','../img/btn_xtsz.png',1)"><img src="../img/btn_xtsz_g.png" alt="系统设置" name="系统设置" width="170" height="69" border="0" id="系统设置" /></a></li>
    </ul>
    <div><img name="" src="../img/left_line.jpg" width="180" height="20" alt="" /></div>
    <div class="font_gray">　软件版权信息：<br />
      　上海汇众汽车制造有限公司
    
    </div>
  </div>
  <div class="right">
    <div class="text_c">
      当前位置：首页 &gt; 用户管理
    &gt;用户编辑</div>
    <div class="btn_frame">
      <div style="float:left"><h1>用户编辑      </h1></div>
    </div>
    
    <form action="userMngSave.do" method="post">
    <s:hidden name="user.id"></s:hidden>
    <table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_form">
      <tr>
        <th>登陆名：</th>
        <td><span class="main_container">
          <s:textfield name="user.loginID" cssClass="u27"/>
        </span></td>
        <th>密码：</th>
        <td><span class="main_container">
          <s:password name="user.passWord" cssClass="u27"/>
        </span></td>
        <th>姓名：</th>
        <td><span class="main_container">
          <s:textfield name="user.name" cssClass="u34"/>
        </span></td>
        <th>电子邮件</th>
        <td><s:textfield name="user.email" cssClass="u34"/></td>
      </tr>
      <tr>
        <th>厂部：</th>
        <td><span class="main_container">
          <s:select cssClass="u38" cssStyle="width:152px" list="regionList" name="user.region.id"  listKey="id" listValue="dictValue"></s:select>
        </span></td>
         <th>车间科室：</th>
        <td><span class="main_container">
          <s:select cssClass="u38" cssStyle="width:152px" list="workshopList" name="user.workshop.id"  listKey="id" listValue="dictValue"></s:select>
        </span></td>
        <th>角色：</th>
        <td><span class="main_container">
          <s:select cssClass="u38" cssStyle="width:152px" list="#{'techAdmin':'技术中心管理员','deptAdmin':'厂部管理员','workshopAdmin':'车间管理员','deptUser':'厂部普通用户'}" name="roleName"  listKey="key" listValue="value"></s:select>
        </span></td>
        <th>工艺工程师：</th>
        <td><span class="main_container"><s:select cssClass="u38" cssStyle="width:152px" list="#{'0':'否','1':'是'}" name="user.is_engineer"  listKey="key" listValue="value"></s:select></span></td>
      </tr>
      <tr>
        <th>发放可见：</th>
        <td><span class="main_container">
          <s:select cssClass="u38" cssStyle="width:152px" list="#{'0':'否','1':'是'}" name="user.issuesee"  listKey="key" listValue="value"></s:select>
        </span></td>
        <th>&nbsp;</th>
        <td>&nbsp;</td>
        <th>&nbsp;</th>
        <td>&nbsp;</td>
        <th>&nbsp;</th>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <th>&nbsp;</th>
        <td colspan="7"><input name="button" type="submit" class="btn_gray" id="button" value="保 存" />
          　
        <input name="button2" type="reset" class="btn_gray" id="button2" value="取 消" /></td>
      </tr>
    </table>
    </form>
    
    <div class="btn_frame">
      <div style="float:left"><h1>绑定工号      </h1></div>
    </div>
    
    <s:if test="hasActionErrors()">
  	<table align="center" width="99%" border="0" cellpadding="0" cellspacing="0" style="margin-bottom:5px;">
    	<tr>
    		<td align="center" class="font" style="color:red">
    		<s:iterator value="actionErrors">
        		<s:property/><br />
      		</s:iterator>
    		</td>
    	</tr>
    </table>
    </s:if>
    
    <form action="bindEmpNumber.do" method="post">
    <s:hidden name="employeeNumber.user.id" value="%{user.id}"></s:hidden>
    <table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_form">
      <tr>
        <th>工号：</th>
        <td><span class="main_container">
          <s:textfield name="employeeNumber.empNumber" cssClass="u27"/>
        </span></td>
        <th>姓名：</th>
        <td><span class="main_container">
          <s:textfield name="employeeNumber.empName" cssClass="u34"/>
        </span></td>
      </tr>
      <tr>
        <th>&nbsp;</th>
        <td colspan="5"><input name="button" type="submit" class="btn_gray" id="button" value="保 存" />
          　
        <input name="button2" type="reset" class="btn_gray" id="button2" value="取 消" /></td>
      </tr>
    </table>
    </form>
    
    <table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_list">
		<tr>
          <th>序号</th>
          <th>工号</th>
          <th>姓名</th>
          <th>操作</th>
        </tr>
        <s:iterator value="employeeList" status="stat">
        <tr <s:if test="#stat.even==true">class="td_bg1"</s:if>>
          <td> <s:property value="#stat.count"/> </td>
          <td> <s:property value="empNumber"/></td>
          <td> <s:property value="empName"/> &nbsp; </td>
          <td>
          <a onclick="return confirm('确认删除该记录?');" href="<s:url action="unBindEmpNumber" namespace="/admin"><s:param name="id" value="id"/></s:url>" class="font_green"><strong><u>删除</u></strong></a>
          </td>
        </tr>
        </s:iterator>
     </table>
  </div>
  <div class="space"></div>
</div>
</body>
</html>
