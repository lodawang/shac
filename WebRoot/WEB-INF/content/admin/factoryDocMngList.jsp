<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
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
function gotoPage(url,toPage){
	document.searchForm.page.value = toPage;
	document.searchForm.action = url;
	document.searchForm.submit();
}
function jumpPage(url,toPage){
	gotoPage(url+"?pager.offset="+(toPage-1)*10,toPage);
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
	$("#docu_modelCode").val('<s:property value="docu.modelCode" escape="false"/>');
});
function schFormOnSubmit(){
		document.searchForm.page.value = 1;
}
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
    </div>
    <div class="btn_frame">
      <div style="float:left">
      <font color="red">说明：本页只列出你有权修改的工艺文档</font>
      </div>
      <div style="float:right">
      <input name="button" type="button" class="btn_ty" id="button" value="图纸管理"  onclick="window.location='factoryDocMngShow.do?docType=0'"/>
      <input name="button" type="button" class="btn_ty" id="button" value="技术文档"  onclick="window.location='factoryDocMngTechShow.do?docType=1'"/>
      <input name="button" type="button" class="btn_ty" id="button" value="新工艺文档"  onclick="window.location='factoryDocMngEditForSave.do?docType=2'"/>
      </div>
    </div>
    <form name="searchForm" action="factoryDocMngList.do" method="post" onsubmit="schFormOnSubmit();">
    <s:hidden name="page"></s:hidden>
    <table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_form">
      <tr>
        <th>文档类型：</th>
        <td>工艺文档</td>
        <td>工艺文档分类</td>
        <td> <s:select cssClass="u36" cssStyle="width:152px" list="procDocClassList" name="docu.procDocClass" listKey="dictValue" listValue="dictValue" headerKey="" headerValue="请选择"></s:select> </td>
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
          <s:select cssClass="u38" cssStyle="width:152px" list="#{'正式生产':'正式生产','试生产':'试生产','停产':'停产','取消':'取消'}" name="docu.processIn"  listKey="key" listValue="value" headerKey="" headerValue="请选择"></s:select>
        </span></td>
        <th>版本号：</th>
        <td><span class="main_container">
          <s:textfield name="docu.docVersion" cssClass="u34"/>
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
      	<td>&nbsp;</td>
        <td colspan="5"><input name="button" type="submit" class="btn_gray" id="button" value="查询"/></td>
      </tr>
    </table>
    </form>
    <table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_list">
<tr>
         <th>序号</th>
          <th>文档ID</th>
          <th>工艺文档分类</th>
          <th>零件号</th>
          <th>客户</th>
          <th>版本</th>
          <th>工艺方式</th>
          <th>生产状态</th>
          <th>创建日期</th>
          <th>发布状态</th>
          <th>操作</th>
        </tr>
        <s:iterator value="docList" status="stat">
        <tr <s:if test="#stat.even==true">class="td_bg1"</s:if>>
          <td> <s:property value="#stat.count"/> </td>
          <td> <s:property value="task.partid"/> </td>
          <td> <s:property value="task.procDocClass"/>&nbsp;</td>
          <td> <s:property value="task.cltPartNumb"/>&nbsp;</td>
          <td> <s:property value="task.client"/> </td>
          <td> <s:property value="task.docVersion"/> </td>
          <td> <s:property value="task.procMode"/>&nbsp; </td>
          <td> 
             <s:if test="task.processIn=='停产'">
             <font color="red"><s:property value="task.processIn"/></font>
          </s:if>
          <s:elseif test="task.processIn=='取消'"><font color="red"><s:property value="task.processIn"/></font></s:elseif>
          <s:else><s:property value="task.processIn"/></s:else> 
          </td>
          <td><s:date name="createTime" format="yyyy/MM/dd"/>&nbsp;</td>
          <td>
             <s:if test="issueType==null">
			          未发放
			</s:if>
			<s:if test="issueType==1">
			         已发放
			</s:if>       
          </td>
          <td align="center">
            <a href="<s:url action="factoryDocMngEditForSave" namespace="/admin"><s:param name="id" value="task.id"/></s:url>" class="font_green"><strong><u>属性修改</u></strong></a>
          </td>
        </tr>
        </s:iterator>
      </table>
      <p class="page-inner" style="text-align:right;width:99%">
		<pg:pager items="${totalCount}" url="${pageContext.request.contextPath}/admin/factoryDocMngList.do" index="center" maxPageItems="10" maxIndexPages="10" isOffset="<%=false%>" export="currentPageNumber=pageNumber" scope="request">
		<pg:index export="pageCount">
			<pg:first>
			<a href="javascript:gotoPage('<%= pageUrl %>','<%= pageNumber %>')">首页</a>
			</pg:first>
			<pg:prev export="pageUrl,pageNumber" ifnull="<%= true %>">
			<% if (pageUrl != null) { %>
			<a href="javascript:gotoPage('<%= pageUrl %>','<%= pageNumber %>')">&lt;&lt;上一页</a>
			<%}else{%>
			<span>&lt;&lt;上一页</span>
			<%} %>
			</pg:prev>
			
			<pg:next export="pageUrl,pageNumber" ifnull="<%= true %>">
			<% if (pageUrl != null) { %>
			<a href="javascript:gotoPage('<%= pageUrl %>','<%= pageNumber %>')">下一页&gt;&gt;</a>
			<%}else{%>
			<span>下一页&gt;&gt;</span>
			<%} %>
			</pg:next>
			<pg:last>
			<a href="javascript:gotoPage('<%= pageUrl %>','<%= pageNumber %>')">末页</a>
			</pg:last>
			<pg:page export="pageUrl,pageNumber">
			转到  <select onchange="selectedPage=this.value;jumpPage('<s:url value="/admin/factoryDocMngList.do"/>',selectedPage)">
				<s:iterator begin="1" end="#attr.pageCount" status="stat">
				<option value="<s:property value="#stat.count"/>"  <s:if test="%{#stat.count==page}">selected</s:if>><s:property value="#stat.count"/></option>
				</s:iterator>
			</select>  页
			</pg:page>
			共<%=pageCount%>页  ${totalCount}条
		</pg:index>
		</pg:pager>
		</p>
  </div>
  <div class="space"></div>
</div>
</body>
</html>
