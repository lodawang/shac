<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
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
</script>
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
      当前位置：首页 &gt; 文档管理
    &gt;文档升级</div>
    <div class="btn_frame">
      <div style="float:left"><h1>文档升级      </h1></div>
      <s:if test="docu.id!=null">
      <div style="float:right">
        
      </div>
      </s:if>
    </div>
    
    <form action="updateDocuVer.do" method="post" enctype="multipart/form-data">
    <s:hidden name="docu.id"></s:hidden>
    <s:hidden name="docu.docType" value="0"></s:hidden>
    <table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_form">
      <tr>
        <th>文档类型：</th>
        <td>产品图纸</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <th>文档ID：</th>
        <td>
        <s:textfield name="docu.partid" cssClass="u23"/>
        </td>
        <th>图纸号：</th>
        <td><span class="main_container">
          <s:textfield name="docu.drawingNumb" cssClass="u32"/>
        </span></td>
        <th>图纸张数：</th>
        <td><span class="main_container">
          <s:textfield name="docu.drawingPage" cssClass="u34"/>
        </span></td>
      </tr>
      <tr >
        <th>图幅：</th>
        <td><span class="main_container">
          <s:select cssClass="u36" cssStyle="width:152px" list="drawingSizeList" name="docu.drawingSize" listKey="dictValue" listValue="dictValue" headerKey="" headerValue="请选择"></s:select>
        </span></td>
        <th>客户：</th>
        <td><span class="main_container">
          <s:select cssClass="u36" cssStyle="width:152px" list="clientList" name="docu.client" listKey="dictValue" listValue="dictValue" headerKey="" headerValue="请选择"></s:select>
        </span></td>
        <th>车型：</th>
        <td><span class="main_container">
          <s:select cssClass="u29" cssStyle="width:152px" list="modelList" name="docu.modelCode" listKey="dictValue" listValue="dictValue" headerKey="" headerValue="请选择"></s:select>
        </span></td>
      </tr>
      <tr >
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
      	<th>是否总成：</th>
        <td><span class="main_container">
          <s:select cssClass="u38" cssStyle="width:152px" list="#{false:'否',true:'是'}" name="docu.assembOrnot"  listKey="key" listValue="value" headerKey="" headerValue="请选择"></s:select>
        </span></td>
        <th>总成号：</th>
        <td><span class="main_container">
          <s:textfield name="docu.assembly" cssClass="u34"/>
        </span></td>
        <th>附件：</th>
        <td><input type="file" name="attach" id="attach" />
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
        <th>TC中版本号：</th>
        <td><span class="main_container">
          <s:textfield name="docu.veroftc" cssClass="u34"/>
        </span></td>
        <th>名称：</th>
        <td><span class="main_container">
          <s:textfield name="docu.assembTitle" cssClass="u34"/>
        </span></td>
        <td colspan="2"><input name="button" type="submit" class="btn_gray" id="button" value="升 级" />
          　
        <input name="button2" type="reset" class="btn_gray" id="button2" value="取 消" /></td>
      </tr>
    </table>
    </form>
  </div>
  <div class="space"></div>
</div>
</body>
</html>
