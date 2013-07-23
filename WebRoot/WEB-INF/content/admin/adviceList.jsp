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
</script>
</head>


 <s:if test="superu == null">
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
      当前位置：首页 &gt; 意见反馈列表
    </div>
    <div class="btn_frame">
      <h1 style="float:left">
        
      </h1>
    
    </div>
     <form action="queryadvice.do" method="post" enctype="multipart/form-data">
        <table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_list">
<tr>
          <th>序号</th>
          <th>文档ID</th>
          <th>类型</th>
          <th>名称</th>
          <th>总成</th>
          <th>零件号</th>
          <th>厂部</th>
          <th>车间</th>          
          <th>提交人</th>
          <th>提交日期</th>
          <th>处理状态</th>
          <th>操作</th>
        </tr>
        <s:iterator value="itemList" status="stat">
        <tr <s:if test="#stat.even==true">class="td_bg1"</s:if>>
          <td> <s:property value="#stat.count"/> </td>
          <td> <s:property value="task.partid"/> </td>
          <td> 
           <s:if test="task.docType==0">
          产品图纸
          </s:if>
          <s:if test="task.docType==1">
         技术文件
          </s:if>
          <s:if test="task.docType==2">
         工艺文档
          </s:if>
           </td>
          <td> <s:property value="task.assembTitle"/> </td>
          <td> <s:property value="task.assembly"/> </td>
          <td> <s:property value="task.cltPartNumb"/> </td>
          <td> <s:property value="senderid.region.dictValue"/>  </td>
          <td> <s:property value="senderid.workshop.dictValue"/>  &nbsp;</td>
          <td> <s:property value="senderid.loginID"/> </td>
          <td> <s:date name="createTime" format="yyyy/MM/dd"/>  </td>
          <td> 
          <s:if test="status==0">
                                               未处理
          </s:if>
          <s:if test="status==2">
                                               处理 
          </s:if>
          </td>
          <td>
           <s:if test="status!=2">
              <s:if test="ifrwd !=null">
               <s:if test="senderid.loginID != curuser.loginID">
               <a href="<s:url action="dealAdvice.do" namespace="/admin"><s:param name="id" value="id"/></s:url>" class="font_green"><strong><u>处理</u></strong></a>
               <a href="<s:url action="upAdvice.do" namespace="/admin"><s:param name="id" value="id"/></s:url>" class="font_green"><strong><u>反馈至总部</u></strong></a>
               </s:if>
              </s:if>
           </s:if>
          	<a href="<s:url action="viewAdvice.do" namespace="/admin"><s:param name="id" value="id"/></s:url>" class="font_green"><strong><u>查看</u></strong></a>
          </td>
        </tr>
        </s:iterator>
      </table>
 	 <p class="page-inner" style="text-align:right;width:99%">
		<pg:pager items="${totalCount}" url="${pageContext.request.contextPath}/admin/adviceList.do" index="center" maxPageItems="10" maxIndexPages="10" isOffset="<%=false%>" export="currentPageNumber=pageNumber" scope="request">
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
			转到  <select onchange="selectedPage=this.value;jumpPage('<s:url value="/admin/adviceList.do"/>',selectedPage)">
				<s:iterator begin="1" end="#attr.pageCount" status="stat">
				<option value="<s:property value="#stat.count"/>"  <s:if test="%{#stat.count==page}">selected</s:if>><s:property value="#stat.count"/></option>
				</s:iterator>
			</select>  页
			</pg:page>
			共<%=pageCount%>页  ${totalCount}条
		</pg:index>
		</pg:pager>
		</p>     
     </form>
  </div>
  <div class="space"></div>
</div>
</body>
</s:if>
<s:if test="superu != null">
<body onload="MM_preloadImages('../img/btn_wdxc.png','../img/btn_jcsj.png','../img/btn_yhgl.png','../img/btn_tjfx.png','../img/btn_xtsz.png')">
<jsp:include page="inc_header.jsp"></jsp:include>
<div class="main">
    <div class="left">
    <ul class="menu">
      <li class="menu_2" ><a href="issueTaskList.do"><img src="../img/btn_wdfb.png" /></a></li>
      <li class="menu_1" ><a href="listManaDocu.do" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('文档管理','','../img/btn_wdgl.png',1)"><img src="../img/btn_wdgl_g.png" alt="文档管理" name="文档管理" width="170" height="69" border="0" id="文档管理" /></a></li>
      <li class="menu_1" ><a href="docQueryList.do" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('文档查询','','../img/btn_wdxc.png',1)"><img src="../img/btn_wdxc_g.png" alt="文档查询" name="文档查询" width="170" height="69" border="0" id="文档查询" /></a></li>
      <li class="menu_1" ><a href="dictDataList.do" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('基础数据','','../img/btn_jcsj.png',1)"><img src="../img/btn_jcsj_g.png" alt="基础数据" name="基础数据" width="170" height="69" border="0" id="基础数据" /></a></li>
      <li class="menu_1" ><a href="userMngList.do" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('用户管理','','../img/btn_yhgl.png',1)"><img src="../img/btn_yhgl_g.png" alt="用户管理" name="用户管理" width="170" height="69" border="0" id="用户管理" /></a></li>
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
      当前位置：首页 &gt; 意见反馈列表
    </div>
    <div class="btn_frame">
      <h1 style="float:left">
        
      </h1>
    
    </div>
     <form action="queryadvice.do" method="post" enctype="multipart/form-data">
        <table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_list">
<tr>
          <th>序号</th>
          <th>文档ID</th>
          <th>类型</th>
          <th>名称</th>
          <th>总成</th>
          <th>零件号</th>
          <th>厂部</th>
          <th>车间</th> 
          <th>提交人</th>
          <th>提交日期</th>
          <th>处理状态</th>
          <th>操作</th>
        </tr>
        <s:iterator value="itemList" status="stat">
        <tr <s:if test="#stat.even==true">class="td_bg1"</s:if>>
          <td> <s:property value="#stat.count"/> </td>
          <td> <s:property value="task.partid"/> </td>
          <td> 
           <s:if test="task.docType==0">
          产品图纸
          </s:if>
          <s:if test="task.docType==1">
         技术文件
          </s:if>
          <s:if test="task.docType==2">
         工艺文档
          </s:if>
           </td>
          <td> <s:property value="task.assembTitle"/> </td>
          <td> <s:property value="task.assembly"/> </td>
          <td> <s:property value="task.cltPartNumb"/> </td>
          <td> <s:property value="senderid.region.dictValue"/>  </td>
          <td> <s:property value="senderid.workshop.dictValue"/>  &nbsp;</td>       
          <td> <s:property value="senderid.loginID"/> </td>
          <td> <s:date name="createTime" format="yyyy/MM/dd"/>  </td>
          <td> 
          <s:if test="status==0">
                                               未处理
          </s:if>
          <s:if test="status==2">
                                               处理 
          </s:if>
          </td>
          <td>
           <s:if test="status!=2">
            <a href="<s:url action="dealAdvice.do" namespace="/admin"><s:param name="id" value="id"/></s:url>" class="font_green"><strong><u>处理</u></strong></a>
           </s:if> 
          	<a href="<s:url action="viewAdvice.do" namespace="/admin"><s:param name="id" value="id"/></s:url>" class="font_green"><strong><u>查看</u></strong></a>
          </td>
        </tr>
        </s:iterator>
      </table>
 	 <p class="page-inner" style="text-align:right;width:99%">
		<pg:pager items="${totalCount}" url="${pageContext.request.contextPath}/admin/adviceList.do" index="center" maxPageItems="10" maxIndexPages="10" isOffset="<%=false%>" export="currentPageNumber=pageNumber" scope="request">
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
			转到  <select onchange="selectedPage=this.value;jumpPage('<s:url value="/admin/adviceList.do"/>',selectedPage)">
				<s:iterator begin="1" end="#attr.pageCount" status="stat">
				<option value="<s:property value="#stat.count"/>"  <s:if test="%{#stat.count==page}">selected</s:if>><s:property value="#stat.count"/></option>
				</s:iterator>
			</select>  页
			</pg:page>
			共<%=pageCount%>页  ${totalCount}条
		</pg:index>
		</pg:pager>
		</p>     
     </form>
  </div>
  <div class="space"></div>
</div>
</body>
</s:if>
</html>
