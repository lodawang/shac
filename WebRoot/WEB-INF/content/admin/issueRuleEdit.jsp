<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>上海汇众文档发布系统</title>
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="../css/zTreeStyle3.1/zTreeStyle.css" type="text/css" />
<script src="../js/jquery-1.5.1.min.js"></script>
<script src="../js/jquery.ztree.all-3.1.min.js"></script>
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
</script>
<script type="text/javascript">
		<!--
		var setting = {
			data: {
				simpleData: {
					enable: true
				}
			},
			check: {
				enable: true
			},
			view:{
				showLine:true
			}
		};
		
		$(document).ready(function(){
			$.getJSON("loadOrgsIssueRuleTree.do?ruleId=<s:property value="issueRule.id"/>&t="+new Date(),function(result){
				$.fn.zTree.init($("#treeConfig"), setting,result);
			});

		});
		
		function save(){
			var treeObj = $.fn.zTree.getZTreeObj("treeConfig");
			var treeNodes = treeObj.getCheckedNodes(true);
			//if(treeNodes.length == 0){
			//	alert("请选择接收方");
			//	return;
			//}
			
			if($("#issueRule_docType").val()=="" && $("#issueRule_processIn").val()==""
				&& $("#issueRule_procMode").val()=="" && $("#issueRule_procDocClass").val()==""){
				alert("请选择至少一种条件");
				return;
			}
			
			var orgidsValue = "";
			for (x in treeNodes) {
				orgidsValue+=treeNodes[x].id+",";
			}
			document.optForm.orgids.value = orgidsValue;
			document.optForm.submit();
		}
		//-->
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
      <li class="menu_1" ><a href="docReport.do" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('统计分析','','../img/btn_tjfx.png',1)"><img src="../img/btn_tjfx_g.png" alt="统计分析" name="统计分析" width="170" height="69" border="0" id="统计分析" /></a></li>
      <li class="menu_2" ><a href="issueRuleList.do"><img src="../img/btn_xtsz.png"/></a></li>
    </ul>
    <div><img name="" src="../img/left_line.jpg" width="180" height="20" alt="" /></div>
    <div class="font_gray">　软件版权信息：<br />
      　上海汇众汽车制造有限公司
    
    </div>
  </div>
  <div class="right">
    <div class="text_c">
      当前位置：首页 &gt; 系统设置
    &gt;发放规则</div>
    <div class="btn_frame">
      <div style="float:left"><h1>发放规则维护      </h1></div>
    </div>
    
    <form action="saveIssueRule.do" method="post" name="optForm">
    <s:hidden name="issueRule.id"></s:hidden>
    <s:hidden name="orgids"></s:hidden>
    <table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_form">
      <tr>
        <th>文档类型：</th>
        <td><span class="main_container">
          <s:select cssClass="u38" cssStyle="width:152px" list="#{'0':'图纸','1':'技术文件','2':'工艺文档'}" name="issueRule.docType"  listKey="key" listValue="value" headerKey="" headerValue="请选择"></s:select>
        </span></td>
        
        <th>生产状态：</th>
        <td><span class="main_container">
          <s:select cssClass="u38" cssStyle="width:152px" list="#{'正式生产':'正式生产','试生产':'试生产','停产':'停产','取消':'取消'}" name="issueRule.processIn"  listKey="key" listValue="value" headerKey="" headerValue="请选择"></s:select>
        </span></td>
      
        <th>工艺方式：</th>
        <td><span class="main_container">
          <s:select cssStyle="width:120px" list="procModeList" headerKey="" headerValue="无" listKey="dictValue" listValue="dictValue" name="issueRule.procMode"></s:select>
        </span></td>
        
        <th>工艺文档分类：</th>
        <td><span class="main_container">
          <s:select cssStyle="width:120px" list="procDocClassList" headerKey="" headerValue="无" listKey="dictValue" listValue="dictValue" name="issueRule.procDocClass"></s:select>
        </span></td>
        
      </tr>
      <tr>
      	<th>接收方：</th>
      	<td colspan="7">
      	   <ul id="treeConfig" class="ztree"></ul>
      	</td>
      </tr>
      <tr>
        <th>&nbsp;</th>
        <td colspan="7"><input name="button" type="button" class="btn_gray" id="button" onclick="return save();" value="保 存" />
          　
        <input name="button2" type="reset" class="btn_gray" id="button2" value="取 消" /></td>
      </tr>
    </table>
    </form>
  </div>
  <div class="space"></div>
</div>
</body>
</html>