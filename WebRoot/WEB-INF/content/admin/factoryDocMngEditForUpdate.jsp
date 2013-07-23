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
	var modelArray = [];
	<s:iterator value="modelList" status="stat">
	modelArray[<s:property value="#stat.index"/>] = new Array("<s:property value="dictValue" escape="false"/>","<s:property value="parent.dictValue" escape="false"/>");
	</s:iterator>
	$("#docu_client").change(
		function(){
			$("#docu_modelCode").empty();
			$("<option value=''>请选择</option>").appendTo("#docu_modelCode");
			for(var i=0;i<modelArray.length;i++){
				if(modelArray[i][1]==$(this).val()){
					$("<option value='"+modelArray[i][0]+"'>"+modelArray[i][0]+"</option>").appendTo("#docu_modelCode");
				}
			}
		}	
	);
	$("#docu_client").change();
	$("#docu_modelCode").val('<s:property value="docu.modelCode"/>');
});
</script>
</head>

<body onload="MM_preloadImages('../img/btn_wdxc.png','../img/btn_jcsj.png','../img/btn_yhgl.png','../img/btn_tjfx.png','../img/btn_xtsz.png')">
<div class="head">
  <div class="logo"><img src="../img/logo.jpg" /></div>
  <div class="nav_btn"><a href="#"><img src="../img/btn_home.png" alt="首页" border="0" /></a><a href="<s:url value="/j_spring_security_logout"/>"><img src="../img/btn_exit.png" alt="退出" border="0" /></a></div>
  <div class="nav_xx">您好 <security:authentication property="name"/>,欢迎使用技术文档发布系统!</div>

</div>
<div class="main">
    <div class="left">
    <ul class="menu">
      <li class="menu_1" ><a href="docInbox.do" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('文档接收','','../img/btn_wdjs.png',1)"><img src="../img/btn_wdjs_g.png" name="文档接收" width="170" height="69" border="0" id="文档接收"/></a></li>
      <li class="menu_1" ><a href="docLatest.do"><img src="../img/btn_zxwd_g.png" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('最新文档','','../img/btn_zxwd.png',1)" id="最新文档"/></a></li>
      <li class="menu_1" ><a href="docRecvQueryList.do" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('文档查询','','../img/btn_wdxc.png',1)"><img src="../img/btn_wdxc_g.png" alt="文档查询" name="文档查询" width="170" height="69" border="0" id="文档查询" /></a></li>
      <li class="menu_1" ><a href="factoryDocMngList.do"><img src="../img/btn_gygl.png"/></a></li>
      <li class="menu_1" ><a href="editProfile.do" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('系统设置','','../img/btn_xtsz.png',1)"><img src="../img/btn_xtsz_g.png" alt="系统设置" name="系统设置" width="170" height="69" border="0" id="系统设置" /></a></li>
    </ul>
    <div><img name="" src="../img/left_line.jpg" width="180" height="20" alt="" /></div>
    <div class="font_gray">　软件版权信息：<br />
      　上海汇众汽车制造有限公司
    
    </div>
  </div>
  <div class="right">
    <div class="text_c">
      当前位置：首页 &gt; 工艺管理
    &gt;升级</div>
    <div class="btn_frame">
      <div style="float:left"><h1>文档升级      </h1></div>
      <s:if test="docu.id!=null">
      <div style="float:right">
        
      </div>
      </s:if>
    </div>
    
    <form action="factoryDocMngUpdate.do" method="post" enctype="multipart/form-data">
    <s:hidden name="docu.id"></s:hidden>
    <s:hidden name="docu.docType" value="2"></s:hidden>
    
    <table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_form">
      <tr>
        <th>文档类型：</th>
        <td>工艺文档</td>
        <th>工艺文档分类</th>
        <td><s:select cssClass="u36" cssStyle="width:152px" list="procDocClassList" name="docu.procDocClass" listKey="dictValue" listValue="dictValue" headerKey="" headerValue="请选择"></s:select> </td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <th>文档ID：</th>
        <td>
        <s:textfield name="docu.partid" cssClass="u23"/>
        </td>
        <th>客户：</th>
        <td><span class="main_container">
          <s:select cssClass="u36" cssStyle="width:152px" list="clientList" name="docu.client" listKey="dictValue" listValue="dictValue" headerKey="" headerValue="请选择"></s:select>
        </span></td>
        <th>车型：</th>
        <td><span class="main_container">
          <s:select cssClass="u29" cssStyle="width:152px" list="{}" name="docu.modelCode" listKey="dictValue" listValue="dictValue" headerKey="" headerValue="请选择"></s:select>
        </span></td>
      </tr>
      <tr>
        <th>零件号：</th>
        <td><span class="main_container">
          <s:textfield name="docu.cltPartNumb" cssClass="u27"/>
        </span></td>
        <th>生产状态：</th>
        <td><span class="main_container">
          <s:select cssClass="u38" cssStyle="width:152px" list="#{'正式生产':'正式生产'}" name="docu.processIn"  listKey="key" listValue="value" headerKey="" headerValue="请选择"></s:select>
        </span></td>
        <th>版本号：</th>
        <td><span class="main_container">
          <s:property value="docu.docVersion"/>
        </span></td>
      </tr>
      <tr>
        <th>工艺方式：</th>
        <td><span class="main_container">
          <s:select cssClass="u36" cssStyle="width:152px" list="procModeList" name="docu.procMode" listKey="dictValue" listValue="dictValue" headerKey="" headerValue="请选择"></s:select>
        </span></td>
        <th>零件名称：</th>
        <td><span class="main_container">
          <s:textfield name="docu.assembTitle" cssClass="u27"/>
        </span></td>
        <td colspan="2">&nbsp;</td>
      </tr>
      <tr>
        <th>附件：</th>
        <td colspan="5"><s:file name="attach" />
        <s:if test="docu.attachFile!=null">附件已上传
        <a href="<s:url value="%{docu.attachFile}"/>" class="font_green">查看附件</a>
        </s:if>
        <s:if test="docu.viewFile!=null">
        <a href="docProtectView.do?id=<s:property value="docu.id"/>" target="_blank" class="font_green">受保护文件</a>
        </s:if>
        <a href="viewAdminPrintFile.do?id=<s:property value="docu.id"/>" target="_blank" class="font_green">纸质提示打印版本</a>
        <a href="viewPrintFile.do?id=<s:property value="docu.id"/>" target="_blank" class="font_green">普通打印版本</a>
        </td>
      </tr>
      <tr>
      	<td>&nbsp;</td>
        <td colspan="5"><input name="button" type="submit" class="btn_gray" id="button" value="升级" />
          　
        <input name="button2" type="reset" class="btn_gray" id="button2" value="取 消" /></td>
      </tr>
    </table>
    </form>
  </div>
  <div class="space"></div>
</div>
</body>
</html>
