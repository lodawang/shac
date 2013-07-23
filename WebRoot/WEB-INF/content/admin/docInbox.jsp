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

function AcceptIssue(id){
   if(confirm('请先查看文件内容，确认签收请单击“确定”，并通知相关工程师查看!')){
      var url = '/shacdp/admin/viewToRecive.do?id='+id;
      var urlnew = '/shacdp/admin/reciveDocu.do?id='+id;
      window.location.href=urlnew;
   }
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
      当前位置：首页 &gt; 文档接收
    </div>
    <div class="btn_frame">
      <h1 style="float:left">
        
      </h1>
      <span style="float:right">
       <s:if test='%{is_dr == "1"}'>
        <input name="buttonno" type="button" class="btn_ty" id="buttonno" value="导入工艺"  onclick="window.location='batchlist.do'"/>
       </s:if> 
        <s:if test='%{is_engineer == "1"}'>
         <input name="buttonno" type="button" class="btn_ty" id="buttonno" value="获得编号"  onclick="window.location='getDocNO.do'"/>
        </s:if>
        <input name="buttonno" type="button" class="btn_ty" id="buttonno" value="反馈意见"  onclick="window.location='adviceList.do'"/>
      </span>
    </div>
    <table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_list">
<tr>
          <th>序号</th>
          <th>文档ID</th>
          <th>版本</th>
          <th>文档类型</th>
          <th>生产状态</th>
          <th>发布日期</th>
          <th>操作</th>
        </tr>
        <s:iterator value="itemList" status="stat">
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
          <td> <s:property value="task.processIn"/> </td>
          <td> <s:date name="createTime" format="yyyy/MM/dd"/>  </td>
          <td>
            <a href="#" onClick = "AcceptIssue('<s:property value="id"/>')" class="font_green"><strong><u>签收</u></strong></a>
          	<a href="<s:url action="docPreviewPre" namespace="/admin"><s:param name="id" value="id"/></s:url>" class="font_green"><strong><u>查看</u></strong></a>
          	<a href="<s:url action="addlevelone" namespace="/admin"><s:param name="id" value="task.id"/></s:url>" class="font_green"><strong><u>反馈</u></strong></a>
          </td>
        </tr>
        </s:iterator>
      </table>
 
  </div>
  <div class="space"></div>
</div>

</body>
</html>
