<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
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
	$("#docutc_client").change(
		function(){
			$("#docutc_modelCode").empty();
			$("<option value=''>请选择</option>").appendTo("#docutc_modelCode");
			for(var i=0;i<modelArray.length;i++){
				if(modelArray[i][1]==$(this).val()){
					$("<option value='"+modelArray[i][0]+"'>"+modelArray[i][0]+"</option>").appendTo("#docutc_modelCode");
				}
			}
		}	
	);
	$("#docutc_client").change();
	$("#docutc_modelCode").val('<s:property value="docutc.modelCode"/>');
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
      <li class="menu_2" ><a href="listManaDocu.do"><img src="../img/btn_wdgl.png"/></a></li>
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
      当前位置：首页 &gt; TC文档管理
    &gt;文档编辑</div>
    <div class="btn_frame">
      <div style="float:left"><h1>文档编辑      </h1></div>
      <s:if test="docutc.id!=null">
      <div style="float:right">
        <input name="button3" type="submit" class="btn_ty" id="button3" value="发放范围"     onclick="window.location='viewTaskTC.do?id=<s:property value="docutc.id"/>'" />
      </div>
      </s:if>
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
    <form action="saveTechDocuTC.do" method="post" enctype="multipart/form-data" onsubmit="return confirm('本操作是修改临时库，要入正式库，请在发放范围内，发放文件!');">
    <s:hidden name="docutc.id"></s:hidden>
    <s:hidden name="docutc.docType" value="1"></s:hidden>
    <s:hidden name="docutc.status"></s:hidden>
    <s:hidden name="docutc.dealstatus"></s:hidden>
    <s:hidden name="docutc.createTime"></s:hidden>
    <s:hidden name="docutc.attachFile"></s:hidden>
    <s:hidden name="docutc.viewFile"></s:hidden>
    <s:hidden name="docutc.previousitem"></s:hidden>
    <s:hidden name="docutc.previousassem"></s:hidden>
    <s:hidden name="initclient"></s:hidden>
    <s:hidden name="initprocDocClass"></s:hidden>
    <s:hidden name="initmodelCode"></s:hidden>
    <s:hidden name="initprocessIn"></s:hidden>
    <s:hidden name="initcltPartNumb"></s:hidden>
    <s:hidden name="initassembTitle"></s:hidden>
    <s:hidden name="initassembly"></s:hidden>
    <s:hidden name="initdoctype" value="1" ></s:hidden>
    <table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_form">
      <tr>
        <th>文档类型：</th>
        <td>
技术资料</td>
        <th> 技术文档分类：</th>
        <td>
        <s:select cssClass="u36" cssStyle="width:152px" list="techDocClassList" name="docutc.techDocClass" listKey="dictValue" listValue="dictValue" headerKey="" headerValue="请选择"></s:select>
        </td>
      </tr>
      <tr>
        <th>文档ID：</th>
        <td><s:textfield name="docutc.partid" cssClass="u23"/></td>
        <th>版本号：</th>
        <td><span class="main_container">
          <s:textfield name="docutc.docVersion" cssClass="u32"/>
        </span></td>
      </tr>
      <tr >
        <th>客户：</th>
        <td><span class="main_container">
          <s:select cssClass="u36" cssStyle="width:152px" list="clientList" name="docutc.client" listKey="dictValue" listValue="dictValue" headerKey="" headerValue="请选择"></s:select>
        </span></td>
        <th>生产状态：</th>
        <td><span class="main_container">
          <s:select cssClass="u38" cssStyle="width:152px" list="#{'正式生产':'正式生产','试生产':'试生产','停产':'停产','取消':'取消'}" name="docutc.processIn"  listKey="key" listValue="value" headerKey="" headerValue="请选择"></s:select>
        </span></td>
      </tr>
      <tr >
        <th>车型：</th>
        <td><span class="main_container">
          <s:select cssClass="u29" cssStyle="width:152px" list="{}" name="docutc.modelCode" listKey="dictValue" listValue="dictValue" headerKey="" headerValue="请选择"></s:select>
        </span></td>
        <th>零件号：</th>
        <td><span class="main_container">
          <s:textfield name="docutc.cltPartNumb" cssClass="u27"/>
        </span></td>
      </tr>
      <tr>
        <th>总成号：</th>
        <td colspan="3"><span class="main_container">
          <s:textfield name="docutc.assembly" cssClass="u34"/>
        </span></td>
      </tr>
      <tr>
        <th>附件：</th>
        <td><s:file name="attach" />
        <s:if test="docutc.attachFile!=null">附件已上传
        <a href="<s:url value="%{docutc.attachFile}"/>" class="font_green">查看附件</a>
        </s:if>
        </td>
        <th>TC中版本号：</th>
        <td><span class="main_container">
          <s:textfield name="docutc.veroftc" cssClass="u34"/>
        </span></td>
      </tr>
      <tr>
        <th>名称：</th>
        <td><span class="main_container">
          <s:textfield name="docutc.assembTitle" cssClass="u34"/>
        </span></td>
        <td colspan="4"><input name="button" type="submit" class="btn_gray" id="button" value="保 存" />
          　
        </td>
      </tr>
    </table>
    </form>
     <s:if test="docutc.status==1">
    <div class="btn_frame">
      <div style="float:left">
        <h1>关联文档</h1>
      </div>
      <div style="float:right">
        
      </div>
    </div>
    
    <table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_list">
       <tr>          
          <th>文档ID</th>
          <th>总成号</th>
          <th>零件号</th>
          <th>版本</th>
          <th>文档类型</th>
          <th>生产状态</th>
          <th>创建日期</th>
          <th>库</th>
          <th>操作</th>
        </tr>
        <s:if test="updatedocutc!=null">
         <tr>
          <td> <s:property value="updatedocutc.partid"/> </td>
          <td> <s:property value="updatedocutc.assembly"/>&nbsp;</td>
          <td> <s:property value="updatedocutc.cltPartNumb"/>&nbsp;</td> 
          <td> <s:property value="updatedocutc.veroftc"/>(TC中的) </td>
          <td> 
          <s:if test="updatedocutc.docType==0">
          产品图纸
          </s:if>
          <s:if test="updatedocutc.docType==1">
         技术文件
          </s:if>
          </td>
          <td> <s:property value="updatedocutc.processIn"/> </td>
          <td><s:date name="updatedocutc.createTime" format="yyyy/MM/dd"/>&nbsp;</td>
          <td> 临时库请先发放这个版本  </td>
          <td align="center">
          	<a href="<s:url action="editDocuTC" namespace="/admin"><s:param name="id" value="updatedocutc.id"/></s:url>" class="font_green"><strong><u>操作</u></strong></a>
          	&nbsp;&nbsp;&nbsp;&nbsp;
          </td>
          </tr>
        </s:if> 
        
        <s:if test="updatedocu!=null">
         <tr>
          <td> <s:property value="updatedocu.partid"/> </td>
          <td> <s:property value="updatedocu.assembly"/>&nbsp;</td>
          <td> <s:property value="updatedocu.cltPartNumb"/>&nbsp;</td> 
          <td> <s:property value="updatedocu.docVersion"/> </td>
          <td> 
          <s:if test="updatedocu.docType==0">
          产品图纸
          </s:if>
          <s:if test="updatedocu.docType==1">
         技术文件
          </s:if>
          </td>
          <td> <s:property value="updatedocu.processIn"/> </td>
          <td><s:date name="updatedocu.createTime" format="yyyy/MM/dd"/>&nbsp;</td>
          <td> 正式库中  </td>
          <td align="center">
          	<a href="<s:url action="viewDocuMana" namespace="/admin"><s:param name="id" value="updatedocu.id"/></s:url>" class="font_green"><strong><u>查看</u></strong></a>
          	&nbsp;&nbsp;&nbsp;&nbsp;
          </td>
        </tr>
        </s:if>        
     </table>    
    </s:if> 
  </div>
  <div class="space"></div>
</div>
</body>
</html>
