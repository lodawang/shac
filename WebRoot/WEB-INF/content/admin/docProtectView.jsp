<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">	
    <head>
        <title></title>         
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="Cache-Control" content="no-store"/>
		<meta http-equiv="Pragma" content="no-cache"/>
		<meta http-equiv="Expires" content="0"/>
        <style type="text/css" media="screen"> 
			html, body	{ height:100%; }
			body { margin:0; padding:0; overflow:auto; text-align:center; 
			       background-color: #ffffff; }   
			object:focus { outline:none; }
			#flashContent { display:none; }
        </style>
		<!-- Enable Browser History by replacing useBrowserHistory tokens with two hyphens -->
        <!-- BEGIN Browser History required section -->
        <link rel="stylesheet" type="text/css" href="../flexpage/history/history.css" />
        <script type="text/javascript" src="../flexpage/history/history.js"></script>
        <!-- END Browser History required section -->
        <script type="text/javascript" src="../flexpage/swfobject.js"></script>
        <script type="text/javascript" src="../flexpage/js/jquery.js"></script>
        <script>
        function onDocumentPrinted(){
        	var formData = "docuId=<s:property value="docu.id"/>";
	    	$.ajax({type:'POST',url:"<s:url value="/admin/logPrint.do"/>",data:formData,cache: false, success: function(data){
	        	   //var dataObj=eval("("+data+")");
	        	   //alert(dataObj.msg);
	        	}
	        });
        }
        </script>
        <script type="text/javascript">
            <!-- For version detection, set to min. required Flash Player version, or 0 (or 0.0.0), for no version detection. --> 
            var swfVersionStr = "10.0.0";
            <!-- To use express install, set to playerProductInstall.swf, otherwise the empty string. -->
            var xiSwfUrlStr = "playerProductInstall.swf";
            var flashvars = { 
                  SwfFile : escape('<s:url action="fileRead" namespace="/admin"><s:param name="fid" value="%{docu.id}"/></s:url>'),//
				  Scale : 0.3, //
				  ZoomTransition : "easeOut",//
				  ZoomTime : 0.5,//
  				  ZoomInterval : 0.1,//
  				  FitPageOnLoad : false,//
  				  FitWidthOnLoad : false,//
  				  <security:authorize ifNotGranted="ROLE_ADMIN,ROLE_RECVADMIN">
  				  PrintEnabled : false,//
  				  PrintToolsVisible : false,//
  				  </security:authorize>
            	  <security:authorize ifAnyGranted="ROLE_ADMIN,ROLE_RECVADMIN">
            	  PrintEnabled : true,//
  				  PrintToolsVisible : true,//
            	  </security:authorize>
  				  MinZoomSize : 0.3,
				  MaxZoomSize : 2,
  				  FullScreenAsMaxWindow : false,//
				  ProgressiveLoading : true,//
				  
  				  ViewModeToolsVisible : true,//
  				  ZoomToolsVisible : true,//
  				  FullScreenVisible : true,
  				  NavToolsVisible : true,//
  				  CursorToolsVisible : true,//
  				  SearchToolsVisible : true,//
  				  ShowLogoVisible : false,
  				  
  				  localeChain: "zh_CN"
				  };
            var params = {};
            params.quality = "high";
            params.bgcolor = "#ffffff";
            params.allowscriptaccess = "sameDomain";
            params.allowfullscreen = "true";
            var attributes = {};
            attributes.id = "FlexPaperViewer";
            attributes.name = "FlexPaperViewer";
            attributes.align = "middle";
            swfobject.embedSWF(
                "../flexpage/FlexPaperViewer.swf", "flashContent", 
                "100%", "100%", 
                swfVersionStr, xiSwfUrlStr, 
                flashvars, params, attributes);
			<!-- JavaScript enabled so display the flashContent div in case it is not replaced with a swf object. -->
			swfobject.createCSS("#flashContent", "display:block;text-align:left;");
        </script>
    </head>
    <body>
        <!-- SWFObject's dynamic embed method replaces this alternative HTML content with Flash content when enough 
			 JavaScript and Flash plug-in support is available. The div is initially hidden so that it doesn't show
			 when JavaScript is disabled.
		-->
		<input name="docuId" id="docuId" type="hidden" value="<s:property value="docu.id"/>"/>
        <div id="flashContent">
        	<p>
	        	To view this page ensure that Adobe Flash Player version 
				10.0.0 or greater is installed. 
			</p>
			<script type="text/javascript"> 
				var pageHost = ((document.location.protocol == "https:") ? "https://" :	"http://"); 
				document.write("<a href='http://www.adobe.com/go/getflashplayer'><img src='" 
								+ pageHost + "www.adobe.com/images/shared/download_buttons/get_flash_player.gif' alt='Get Adobe Flash player' /></a>" ); 
			</script> 
        </div>
	   	
       	<noscript>
            <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="100%" id="FlexPaperViewer">
                <param name="movie" value="FlexPaperViewer.swf" />
                <param name="quality" value="high" />
                <param name="bgcolor" value="#ffffff" />
                <param name="allowScriptAccess" value="sameDomain" />
                <param name="allowFullScreen" value="true" />
                <!--[if !IE]>-->
                <object type="application/x-shockwave-flash" data="FlexPaperViewer.swf" width="100%" height="100%">
                    <param name="quality" value="high" />
                    <param name="bgcolor" value="#ffffff" />
                    <param name="allowScriptAccess" value="sameDomain" />
                    <param name="allowFullScreen" value="true" />
                <!--<![endif]-->
                <!--[if gte IE 6]>-->
                	<p> 
                		Either scripts and active content are not permitted to run or Adobe Flash Player version
                		10.0.0 or greater is not installed.
                	</p>
                <!--<![endif]-->
                    <a href="http://www.adobe.com/go/getflashplayer">
                        <img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash Player" />
                    </a>
                <!--[if !IE]>-->
                </object>
                <!--<![endif]-->
            </object>
	    </noscript>		
   </body>
</html>