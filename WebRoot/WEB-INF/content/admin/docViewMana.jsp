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
      上海汇众汽车制造有限公司 </div>
  </div>
  <div class="right">
    <div class="text_c"> 当前位置：首页 &gt; 文档管理 
      &gt;文档信息</div>
    <div class="btn_frame">
    
      <h1>文档信息</h1>
  
    </div>
    <s:if test="docu.docType==0">
    <table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_form">
      <tr class="td_bg1">
        <th align="right">文档ID：</th>
        <td><s:property value="docu.partid"/> </td>
        <th>图号：</th>
        <td><s:property value="docu.drawingNumb"/></td>
        <th>图幅/页数：</th>
        <td><s:property value="docu.drawingSize"/> / <s:property value="docu.drawingPage"/>页</td>
        <th>版本号：</th>
        <td><s:property value="docu.docVersion"/>&nbsp;</td>
      </tr>
      <tr >
        <th align="right">客户：</th>
        <td><s:property value="docu.client"/></td>
        <th>车型：</th>
        <td><s:property value="docu.modelCode"/></td>
        <th>零件号：</th>
        <td><s:property value="docu.cltPartNumb"/>&nbsp;</td>
        <th>生产状态：</th>
        <td><s:property value="docu.processIn"/></td>
      </tr>
      <tr class="td_bg1">
        <th>总成：</th>
        <td><s:property value="docu.assembly"/>&nbsp;</td>
        <th>TC中版本号：</th>
        <td><s:property value="docu.veroftc"/>&nbsp;</td>
        <th align="right">物理文件：</th>
        <td><a href="<s:url value="%{docu.attachFile}"/>" class="font_green">附件</a> <a href="<s:url value="%{docu.attachFile.replaceAll('\\\.','-sct.')}"/>" class="font_green">水印PDF</a>
        <s:if test="docu.viewFile!=null">
        <a href="docProtectView.do?id=<s:property value="docu.id"/>" target="_blank" class="font_green">受保护文件</a>
        </s:if>
        <a href="viewAdminPrintFile.do?id=<s:property value="docu.id"/>" target="_blank" class="font_green">纸质提示打印版本</a>
        <a href="viewPrintFile.do?id=<s:property value="docu.id"/>" target="_blank" class="font_green">普通打印版本</a>
        </td>
        <th>名称：</th>
        <td><s:property value="docu.assembTitle"/></td>
      </tr>
    </table>
    </s:if>
     <s:if test="docu.docType==1">
    <table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_form">
      <tr class="td_bg1">
        <th align="right">文档ID：</th>
        <td><s:property value="docu.partid"/> </td>
        <th>技术文件类型：</th>
        <td><s:property value="docu.techDocClass"/></td>
        <th>生产状态：</th>
        <td><s:property value="docu.processIn"/></td>
        <th>版本号：</th>
        <td><s:property value="docu.docVersion"/>&nbsp;</td>
      </tr>
      <tr >
        <th align="right">客户：</th>
        <td><s:property value="docu.client"/></td>
        <th>车型：</th>
        <td><s:property value="docu.modelCode"/></td>
        <th>总成：</th>
        <td><s:property value="docu.assembly"/>&nbsp;</td>
        <th>零件号：</th>
        <td><s:property value="docu.cltPartNumb"/>&nbsp;</td>
      </tr>
      <tr class="td_bg1">
      	<th>名称：</th>
        <td><s:property value="docu.assembTitle"/>&nbsp;</td>
        <th>TC中版本号：</th>
        <td><s:property value="docu.veroftc"/>&nbsp;</td>
        <th align="right">物理文件：</th>
        <td colspan="3"><a href="<s:url value="%{docu.attachFile}"/>" class="font_green">附件</a></td>
      </tr>
    </table>
    </s:if>
    <div class="btn_frame">
      <div style="float:left">
        <h1>发放记录</h1>
      </div>
      <div style="float:right">
        
      </div>
    </div>
    <form action="<s:url value="/admin/issueDocu.do"/>" method="post">
    <s:hidden name="docu.id"></s:hidden>
    <table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_list">
      <tr>
        <th>厂部</th>
        <th>接收状态</th>
        <th>接收时间</th>
      </tr>
      <s:iterator value="deptList" status="stat" var="factory">
      <tr <s:if test="#stat.even==true">class="td_bg1"</s:if>>
        <td style="font-size: 15px;font-weight: bold;"><s:property value="dictValue"/> </td>
        <td>
        <s:if test='sent=="0"'>
        未签收
        </s:if>
        <s:if test='sent=="1"'>
        已签收
        </s:if>
        <s:if test='sent=="2"'>
        已标记
        </s:if>&nbsp;
        </td>
        <td>
        <s:date name="signTime" format="yyyy/MM/dd HH:mm"/>&nbsp;
        </td>
      </tr>
      <s:iterator value="workshopList">
      	<s:if test="parent.id == #factory.id">
      		<tr <s:if test="#stat.even==true">class="td_bg1"</s:if>>
      			<td style="padding-left: 30px"><s:property value="dictValue"/></td>
      			<td>
      			<s:if test='sent=="0"'>未签收</s:if>
      			<s:if test='sent=="1"'>已签收</s:if>&nbsp;
      			</td>
      			<td>
        			<s:date name="signTime" format="yyyy/MM/dd HH:mm"/>&nbsp;
        		</td>
      		</tr>
      	</s:if>
      </s:iterator>
      </s:iterator>
    </table>
    <div align="center">
    </div>
    </form>
  </div>
  <div class="space"></div>
</div>
</body>
</html>
