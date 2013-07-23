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

var parentArray = new Array();
<s:iterator value="parentList" status="stat">
parentArray["<s:property value="id" escape="false"/>"] = new Array("<s:property value="dictValue" escape="false"/>","<s:property value="dictType" escape="false"/>");
</s:iterator>

$(function(){
	if($("#data_dictType").val() == "workshop"){
		$("#data_parent_id").children().each(
			function(){
				if($(this).val()!="" && parentArray[$(this).val()][1]!="dept"){
					$(this).remove();
				}
			}	
		);
	}
	else if($("#data_dictType").val() == "model"){
		$("#data_parent_id").children().each(
			function(){
				if($(this).val()!="" && parentArray[$(this).val()][1]!="client"){
					$(this).remove();
				}
			}	
		);
	}else{
		$("#data_parent_id").empty();
		$("<option value=''>无</option>").appendTo("#data_parent_id");
	}
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
      <li class="menu_2" ><a href="dictDataList.do" ><img src="../img/btn_jcsj.png"/></a></li>
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
      当前位置：首页 &gt; 基础数据
    &gt;数据编辑</div>
    <div class="btn_frame">
      <div style="float:left"><h1>数据编辑      </h1></div>
    </div>
    
    <form action="saveDictData.do" method="post">
    <s:hidden name="data.id"></s:hidden>
    <table width="99%" border="0" cellpadding="0" cellspacing="0" class="table_form">
      <tr>
        <th>数据类型：</th>
        <td><span class="main_container">
          <s:select cssClass="u38" cssStyle="width:152px" list="#{'dept':'厂部','workshop':'车间科室','client':'客户','model':'车型','techdoc':'技术文件类型','drawsize':'图幅','procdoc':'工艺文档分类','procmode':'工艺方式'}" name="data.dictType"  listKey="key" listValue="value" onchange="changeselect(this.value)" headerKey="" headerValue="请选择"></s:select>
        </span></td>
      
        <th>数据描述：</th>
        <td><span class="main_container">
          <s:textfield name="data.dictValue" cssClass="u27"/>
        </span></td>
        
        <th>上级：</th>
        <td><span class="main_container">
          <s:select cssStyle="width:120px" list="parentList" headerKey="" headerValue="无" listKey="id" listValue="dictValue" name="data.parent.id"></s:select>
        </span></td>
        
        <th>排序：</th>
        <td><span class="main_container">
          <s:textfield name="data.order" cssClass="u34"/>
        </span></td>                        
      </tr>
      <tr id="scop" style="display:none;">
      <th>发送范围：</th>
        <td><span class="main_container">
          <s:select cssClass="u38" cssStyle="width:152px" list="#{'0':'全部','1':'图纸','2':'工艺'}" name="data.scope"  listKey="key" listValue="value" headerKey="" headerValue="请选择"></s:select>
        </span></td>
          <th>厂号：</th>
        <td><span class="main_container">
         <s:textfield name="data.depno" cssClass="u27"/>          
        </span></td>
        <th>无下级部门</th>
        <td><span class="main_container">
         <s:checkbox theme="simple" name="data.alone" value="data.alone" fieldValue="true"></s:checkbox>
        </span></td>
        <th>&nbsp;</th>
        <td><span class="main_container">
           &nbsp;         
        </span></td>        
      </tr>
      <tr id="issue" style="display:none;">
       <th>发送范围：</th>
       <td>
            
            <s:iterator value="deptList" var="dd" status="stat">
	             <s:set name="ic" value="''" scope="session"/>
	             <s:iterator value="deptSelected" var="dl" status="instat">
	                	<s:if test="#dl == dictValue">
	                	    <s:set name="ic" value="'checked'" scope="session"/>	                	    
	                	</s:if>	            
	             </s:iterator>
                 <input type="checkbox" name="deptSelected" value="<s:property value="dictValue"/>" ${sessionScope.ic} />  <s:property value="dictValue"/> <br/>
            </s:iterator>
        </td>
          <th></th>
        <td><span class="main_container">          
        </span></td>
        <th></th>
        <td><span class="main_container">          
        </span></td>
        <th></th>
        <td><span class="main_container">          
        </span></td>  
      </tr>
      <tr>
        <th>&nbsp;</th>
        <td colspan="7"><input name="button" type="submit" class="btn_gray" id="button" value="保 存" />
          　
        <input name="button2" type="reset" class="btn_gray" id="button2" value="取 消" /></td>
      </tr>
    </table>
    </form>
  </div>
  <div class="space"></div>
</div>
<script type="text/javascript">
	function changeselect(value){
		if(value == "dept"){
			document.getElementById("scop").style.display='block';
		}else{
			document.getElementById("scop").style.display='none';
		}	
		
		if(value == "techdoc"){
			document.getElementById("issue").style.display='block';
		}else{
			document.getElementById("issue").style.display='none';
		}
		
		$("#data_parent_id").empty();
		$("<option value=''>无</option>").appendTo("#data_parent_id");
		if(value == "workshop"){
			for(prop in parentArray){
				if(parentArray[prop][1]=="dept"){
					$("<option value='"+prop+"'>"+parentArray[prop][0]+"</option>").appendTo("#data_parent_id");
				}
			}
		}
		if(value == "model"){
			for(prop in parentArray){
				if(parentArray[prop][1]=="client"){
					$("<option value='"+prop+"'>"+parentArray[prop][0]+"</option>").appendTo("#data_parent_id");
				}
			}
		}
	}	
	if(document.getElementById("data_dictType").value == "dept"){	
		document.getElementById("scop").style.display='block';
	}
	
	if(document.getElementById("data_dictType").value == "techdoc"){	
		document.getElementById("issue").style.display='block';
	}
</script>
</body>
</html>
