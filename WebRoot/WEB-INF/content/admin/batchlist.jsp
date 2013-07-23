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

function schFormOnSubmit(){
		document.searchForm.page.value = 1;
}

$(function(){
	var modelArray = [];
	<s:iterator value="modelList" status="stat">
	modelArray[<s:property value="#stat.index"/>] = new Array("<s:property value="dictValue" escape="false"/>","<s:property value="parent.dictValue" escape="false"/>");
	</s:iterator>
	$("#initclient").change(
		function(){
			$("#initmodelCode").empty();
			$("<option value=''>请选择</option>").appendTo("#initmodelCode");
			for(var i=0;i<modelArray.length;i++){
				if(modelArray[i][1]==$(this).val()){
					$("<option value='"+modelArray[i][0]+"'>"+modelArray[i][0]+"</option>").appendTo("#initmodelCode");
				}
			}
		}	
	);
	$("#initclient").change();
	$("#initmodelCode").val('<s:property value="initmodelCode"/>');
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
      <li class="menu_2" ><a href="docInbox.do"><img src="../img/btn_wdjs.png" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('文档接收','','../img/btn_wdjs.png',1)"/></a></li>
      <li class="menu_1" ><a href="docLatest.do"><img src="../img/btn_zxwd_g.png" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('最新文档','','../img/btn_zxwd.png',1)" id="最新文档"/></a></li>
      <li class="menu_1" ><a href="docRecvQueryList.do" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('文档查询','','../img/btn_wdxc.png',1)"><img src="../img/btn_wdxc_g.png" alt="文档查询" name="文档查询" width="170" height="69" border="0" id="文档查询" /></a></li>
      <security:authorize ifAnyGranted="ROLE_RECVADMIN">
      <li class="menu_1" ><a href="factoryDocMngList.do" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('工艺管理','','../img/btn_gygl.png',1)"><img src="../img/btn_gygl_g.png" alt="工艺管理" name="工艺管理" width="170" height="69" border="0" id="工艺管理"/></a></li>
      </security:authorize>
      <li class="menu_1" ><a href="editProfile.do" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('系统设置','','../img/btn_xtsz.png',1)"><img src="../img/btn_xtsz_g.png" alt="系统设置" name="系统设置" width="170" height="69" border="0" id="系统设置" /></a></li>
    </ul>
    <div><img name="" src="../img/left_line.jpg" width="180" height="20" alt="" /></div>
    <div class="font_gray">　软件版权信息：<br />
      　上海汇众汽车制造有限公司
    
    </div>
  </div>
  <div class="right">
    <div class="text_c">
      当前位置：首页 &gt; 工艺文档导入
    </div>
    
    <div class="btn_frame">
        <h1 style="float:left">
                                       默认值
        </h1>
    </div>
    <form name="tobatchForm" action="tobatch.do" method="post">
    <table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_form">
      <tr>
        <td>客户：</td>
        <td>
         <s:select cssClass="u36" cssStyle="width:152px" list="clientList" id="initclient" name="initclient" listKey="dictValue" listValue="dictValue" headerKey="" headerValue="请选择"></s:select>
        </td>
        <td>分类：</td>
        <td>
         <s:select cssClass="u36" cssStyle="width:152px" list="procDocClassList" name="initprocDocClass" listKey="dictValue" listValue="dictValue" headerKey="" headerValue="请选择"></s:select>
        </td>
        <td>车型：</td>
        <td>
         <s:select cssClass="u29" cssStyle="width:152px" list="{}" id="initmodelCode" name="initmodelCode" listKey="dictValue" listValue="dictValue" headerKey="" headerValue="请选择"></s:select>
        </td>
        <td>生产状态：</td>
        <td>
         <s:select cssClass="u38" cssStyle="width:152px" list="#{'正式生产':'正式生产','试生产':'试生产','停产':'停产','取消':'取消'}" name="initprocessIn"  listKey="key" listValue="value" headerKey="" headerValue="请选择"></s:select>
        </td>
      </tr>
      
      <tr>
	       <td>工艺方式：</td>
	       <td>
	        <s:select cssClass="u36" cssStyle="width:152px" list="procModeList" name="initprocMode" listKey="dictValue" listValue="dictValue" headerKey="" headerValue="请选择"></s:select>
	       </td>
	       <td>零件号：</td>
	       <td>
	         <s:textfield name="initcltPartNumb" cssClass="u27"/>
	       </td>
	       <td>零件名称：</td>
	       <td>
	         <s:textfield name="initassembTitle" cssClass="u27"/>
	       </td>
	       <td>&nbsp;</td>
      
         <td><input name="button" type="submit" class="btn_gray" id="button" value="新增" /></td>
      </tr>
      <tr>
         <td>默认发放部门：</td>
         <td>
	         <s:iterator value="seleteddep" status="stat">
		        <s:if test='sent==null'>
		        <input type="checkbox" name="initseleteddep" value="<s:property value="dictValue"/>"/>
		        </s:if>
		        <s:if test='sent=="0"'>
		        <input type="checkbox" name="initseleteddep" checked="checked" value="<s:property value="dictValue"/>"/>
		        </s:if>
	             <s:property value="dictValue"/><br/>   
	         </s:iterator>
         </td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
         <td>&nbsp;</td>
      </tr>
    </table>
    </form>
    
    
    <form name="searchForm" action="batchlist.do" method="post" onsubmit="schFormOnSubmit();">
     <s:hidden name="page"></s:hidden>
     <table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_form">
      <tr>
        <th>文档ID：</th>
        <td>
        <s:textfield name="docu.partid" cssClass="u23"/>
        </td>
       <th>零件号：</th>
        <td>
        <s:textfield name="docu.cltPartNumb" cssClass="u23"/>
        </td>
        <th>零件名称：</th>
        <td>
        <s:textfield name="docu.assembTitle" cssClass="u23"/>
        </td>
        <th>附件：</th>
        <td><span class="main_container">
          <s:select cssClass="u38" cssStyle="width:152px" list="#{'1':'已上传','0':'未上传'}" name="upfile"  listKey="key" listValue="value" headerKey="" headerValue="请选择"></s:select>
        </span></td>
        <td><input name="button" type="submit" class="btn_gray" id="button" value="查询" /></td>
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
          <th>发布部门</th>
          <th>操作</th>
        </tr>
         <s:iterator value="docList" status="stat">
        <tr <s:if test="#stat.even==true">class="td_bg1"</s:if>>
          <td> <s:property value="#stat.count"/> </td>
          <td> <s:property value="partid"/> </td>
          <td> <s:property value="procDocClass"/>&nbsp;</td>
          <td> <s:property value="cltPartNumb"/>&nbsp;</td>
          <td> <s:property value="client"/> </td>
          <td> <s:property value="docVersion"/> </td>
          <td> <s:property value="procMode"/>&nbsp; </td>
          <td> <s:property value="processIn"/> </td>
          <td><s:date name="createTime" format="yyyy/MM/dd"/>&nbsp;</td>
          <td> <s:property value="taskdep" />  </td>
          <td align="center">
            <a href="<s:url action="tobatch" namespace="/admin"><s:param name="id" value="id"/></s:url>" class="font_green"><strong><u>属性修改</u></strong></a>
          	&nbsp;&nbsp;&nbsp;&nbsp;
          	<a onclick="return confirm('确认删除?')" href="<s:url action="removeBatchProcDoc" namespace="/admin"><s:param name="id" value="id"/></s:url>" class="font_green"><strong><u>删除</u></strong></a>
          </td>
        </tr>
        </s:iterator>
    </table>
      <p class="page-inner" style="text-align:right;width:99%">
		<pg:pager items="${totalCount}" url="${pageContext.request.contextPath}/admin/batchlist.do" index="center" maxPageItems="10" maxIndexPages="10" isOffset="<%=false%>" export="currentPageNumber=pageNumber" scope="request">
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
			转到  <select onchange="selectedPage=this.value;jumpPage('<s:url value="/admin/batchlist.do"/>',selectedPage)">
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
