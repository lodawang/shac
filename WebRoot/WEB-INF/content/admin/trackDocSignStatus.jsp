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
<script src="../js/My97DatePicker/WdatePicker.js"></script>
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
	gotoPage(url+"?pager.offset="+(toPage-1)*500,toPage);
}
function schFormOnSubmit(){
		document.searchForm.page.value = 1;
}
$(function(){
	var modelArray = [];
	<s:iterator value="workshopList" status="stat">
	modelArray[<s:property value="#stat.index"/>] = new Array("<s:property value="dictValue" escape="false"/>","<s:property value="parent.dictValue" escape="false"/>","<s:property value="id" escape="false"/>");
	</s:iterator>
	$("#querydep").change(
		function(){
			$("#querywork").empty();
			$("<option value=''>请选择</option>").appendTo("#querywork");
			for(var i=0;i<modelArray.length;i++){			    			   
				if(modelArray[i][1]==$('#querydep option:selected').text()){
					$("<option value='"+modelArray[i][2]+"'>"+modelArray[i][0]+"</option>").appendTo("#querywork");
				}
			}
		}	
	);
	$("#querydep").change();
	$("#querywork").val('<s:property value="querywork"/>');
});
</script>
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
      <li class="menu_1" ><a href="userMngList.do" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('用户管理','','../img/btn_yhgl.png',1)"><img src="../img/btn_yhgl_g.png" alt="用户管理" name="用户管理" width="170" height="69" border="0" id="用户管理" /></a></li>
      <li class="menu_1" ><a href="docReport.do"><img src="../img/btn_tjfx.png"/></a></li>
      <li class="menu_1" ><a href="issueRuleList.do" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('系统设置','','../img/btn_xtsz.png',1)"><img src="../img/btn_xtsz_g.png" alt="系统设置" name="系统设置" width="170" height="69" border="0" id="系统设置" /></a></li>
    </ul>
    <div><img name="" src="../img/left_line.jpg" width="180" height="20" alt="" /></div>
    <div class="font_gray">　软件版权信息：<br />
      　上海汇众汽车制造有限公司
    
    </div>
  </div>
  <div class="right">
    <div class="text_c">
      <div>当前位置：首页 &gt; 统计分析&gt; 文档签收跟踪表</div>
    </div>
    <div class="btn_frame">
      <h1 style="float:left">
        
      </h1>
      <span style="float:right">
      <input name="button" type="button" class="btn_ty" id="button" value="在产一览表"  onclick="window.location='docReport.do'"/>
      <input name="button" type="button" class="btn_ty" id="button" value="打印记录"  onclick="window.location='logPrintList.do'"/>
      <input name="button" type="button" class="btn_ty" id="button" value="签收跟踪"  onclick="window.location='trackDocSignStatus.do'"/>
      </span>
    </div>
    <form name="searchForm" action="trackDocSignStatus.do" method="post" onsubmit="schFormOnSubmit();">
    <s:hidden name="page"></s:hidden>
    <table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_form">
      <tr>
        <th>文档ID：</th>
        <td>
        <s:textfield name="docu.partid" cssClass="u23" theme="simple"/>
        <s:select theme="simple" cssClass="u38" cssStyle="width:152px" list="#{'':'全部','0':'未签收','1':'已签收','3':'已回收','2':'已标记'}" name="status"  listKey="key" listValue="value" ></s:select>
        </td>
        <th>&nbsp;</th>
        <td><input name="button" type="submit" class="btn_gray" id="button" value="查询" /></td>                        
      </tr>
      <tr>
        <th>厂部：</th>
        <td>
          <s:select cssClass="u36" cssStyle="width:152px" list="deptList" name="querydep" listKey="id" listValue="dictValue" headerKey="" headerValue="请选择"></s:select>                                          
        </td>
          <th>车间：</th>
        <td><s:select cssClass="u29" cssStyle="width:152px" list="{}"  name="querywork" listKey="id" listValue="dictValue" headerKey="" headerValue="请选择"></s:select></td>  
       </tr> 
       <tr>
        <th>开始时间：</th>
        <td>
          <s:textfield name="begindate" cssClass="u23" id="begindate" onClick="WdatePicker()"/>                                          
        </td>
          <th>结束时间：</th>
        <td>
          <s:textfield name="enddate" cssClass="u23" id="enddate" onClick="WdatePicker()"/>
        </td>  
       </tr>         
    </table>
    </form>
    <table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_list">
<tr>
          <th>序号</th>
          <th>文档ID</th>
          <th>版本</th>
          <th>文档类型</th>
          <th>发布时间</th>
          <th>发放范围</th>
          <th>签收状态</th>
          <th>签收时间</th>
        </tr>
        <s:iterator value="docList" status="stat">
        <tr <s:if test="#stat.even==true">class="td_bg1"</s:if>>
          <td> <s:property value="#stat.count"/> </td>
          <td> <s:property value="task.partid"/> </td>
          <td> <s:property value="task.docVersion"/> </td>
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
          <td><s:date name="createTime" format="yyyy/MM/dd"/>&nbsp;</td>
          <td><s:property value="dept.dictValue"/>   <s:property value="workshop.dictValue"/></td>
          <td>
          <s:if test="status==0">
          未签收
          </s:if>
          <s:if test="status==1">
          已签收
          </s:if>
          <s:if test="status==3">
          已销毁回收
          </s:if>
          <s:if test="status==2">
          已标记
          </s:if>
          </td>
          <td><s:date name="signTime" format="yyyy/MM/dd"/>&nbsp;</td>
        </tr>
        </s:iterator>
      </table>
 	 <p class="page-inner" style="text-align:right;width:99%">
		<pg:pager items="${totalCount}" url="${pageContext.request.contextPath}/admin/trackDocSignStatus.do" index="center" maxPageItems="500" maxIndexPages="500" isOffset="<%=false%>" export="currentPageNumber=pageNumber" scope="request">
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
			
			共<%=pageCount%>页  ${totalCount}条
		</pg:index>
		</pg:pager>
		</p>
  </div>
  <div class="space"></div>
</div>
</body>
</html>
