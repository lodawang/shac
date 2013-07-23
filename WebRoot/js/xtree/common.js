/*
	BeanSoft Common JavaScript lib v1.0
	(c) 2005 - 2006 Jacky Liu
	Dec 20, 2005
*/

// {{{
// Common utils methods

/**
 * Get the max page width of current browser.
 * @author beansoft
 * @version 1.0 2006-05-04
 * @return int - size in px
 */
function getMaxPageWidth() {
	return Math.max(document.documentElement.scrollWidth, 
		document.documentElement.clientWidth);
}

/**
 * Get the max page height of current browser.
 * @author beansoft
 * @version 1.0 2006-05-04
 * @return int - size in px
 */
function getMaxPageHeight() {
	return Math.max(document.documentElement.scrollHeight, 
		document.documentElement.clientHeight);
}


/*
 Make a given document object visible or not
 object - the object who's display to set
 visible - true or "true" make it visible, otherwise make it hidden
 */
function setDisplay(object, visible) {
  if(visible == true || visible == "true") {
      object.style.display = "";
  } else {
      object.style.display = "none";
  }
}

/*
 Based on fvlogger, for debug list all attributes of the object
 */
function dump(object) {
	if(!object) return;
    for( key in object) {
        debug(key + "=" + object[key]);
    }
}

/**
 Clone the two object, let them have the same attribute and values.
 2006-03-12
 @author BeanSoft
 */
function cloneObject(objectFrom, objectTo) {
	if(!objectFrom || !objectTo) {
		return;
	}

    for( key in objectFrom) {
        objectTo.key = objectFrom[key];
    }
}

/*
 Fetch a DOM object based on it's id
 objId - a object's id string
 */
function $(objId) {
    return document.getElementById(objId);
}

/*
 Init the image button to a hover style
 @param img the img object to init style
 @version 1.0 2006-02-03
 @author BeanSoft
 */
function initButtonStyle(img) {
	img.align = "absbottom";
	img.className = "mceButtonNormal";
	img.style.cursor = "pointer";

    img.onmouseover= function() {
		img.className = 'mceButtonOver';
	};

	img.onmouseout= function() {
			img.className = 'mceButtonNormal';
	};
}


/*
	AJAX download a url's document, and when ready, call the readyHandler.
	@version 2006-03-09 1.0
	@author BeanSoft
	url - the url string to read
	readyHandler - a function in format myFunction(xmlHttp)
 */
function ajaxDownloadLink(url, readyHandler) {
	if($('loading')) {
		// call in new thread to allow ui to update
	    window.setTimeout(function () {
			$('loading').style.display = "";
		}, 1);
	}

	var xmlHttp = XmlHttp.create();
	if(xmlHttp) {
		try {
		  xmlHttp.open("GET", url, true);	// async
		} catch (ex) {
		  error(ex);
		}

		xmlHttp.onreadystatechange = function () {
			if (xmlHttp.readyState == 4) {
				if (typeof readyHandler == "function") {
					if($('loading')) {
						// call in new thread to allow ui to update
					    window.setTimeout(function () {
							$('loading').style.display = "none";
						}, 1);
					}

					readyHandler(xmlHttp);
				}
			}
		};

		// call in new thread to allow ui to update
		window.setTimeout(function () {
			xmlHttp.send(null);
		}, 10);
	}
}

/**
 Init the sortable table header to a hover style.
 @see js/common.js
 @see css/sortabletable.css
 @param td the td object to init style
 @version 1.0 2006-03-06
 @author BeanSoft
 */
function initSortHeader(td) {
	td.className = 'sort-table_thead_td';

	td.onmouseover = function() {
		td.className = 'sort-table_thead_td_over';
	};

	td.onmouseout = function() {
		td.className = 'sort-table_thead_td';
	};
}

// Discover browser info {{{
var browserInfo = new Object();
var sAgent = navigator.userAgent.toLowerCase() ;

browserInfo.IsIE			= ( sAgent.indexOf("msie") != -1 ) ;
browserInfo.IsGecko		= !browserInfo.IsIE ;
browserInfo.IsSafari		= ( sAgent.indexOf("safari") != -1 ) ;
browserInfo.IsNetscape	= ( sAgent.indexOf("netscape") != -1 ) ;
// End of browser info }}}

/**
 * Check if the event is happent in the element.
 * @param event - event object
 * @param element - the element of the document
 *
 * @author beansoft
 * @version 1.0 2006-04-30
 */
function checkEventIn(event, element)
{
    if(!event) {
        event = window.event;
    }

    var e;

    if(browserInfo.IsGecko) {
       e = event.target;
    } else {
       e = event.srcElement;
    }



    while ( e )
	{
		if ( e == element ) return true;
		e = e.parentNode ;
	}

    return false;
}

/**
 * @constructor
 * This is a ajax form helper class.
 *
 * @param form - the document form
 * @param resultDivId - the result div id
 * @author beansoft
 * @version 2006-05-01
 */
function AjaxFormer (form, resultDivId) {
	/** form instance */
	this.form = form;
	/** div to display result in */
	this.resultDivId = resultDivId;


	/**
	 * This will call the ajax submit method to do the form submit.
	 * @author beansoft
	 * @version 1.0 2006-05-04
	 */
	this.ajaxSubmitForm = function() {
	  var elements = form.elements;// Enumeration the form elements
	  var element;
	  var i;

	  var postContent = "";// Form contents need to submit

	  for(i=0;i<elements.length;++i) {
	  	var element=elements[i];

	    if(element.type=="text" || element.type=="textarea" || element.type=="hidden") {
	      postContent += encodeURIComponent(element.name) + "=" + encodeURIComponent(element.value) + "&";
	    }
	    else if(element.type=="select-one"||element.type=="select-multiple") {
	      var options=element.options,j,item;
	      for(j=0;j<options.length;++j){
	        item=options[j];
	        if(item.selected) {
	        	postContent += encodeURIComponent(element.name) + "=" + encodeURIComponent(item.value) + "&";
			}
	      }
	    } else if(element.type=="checkbox"||element.type=="radio") {
	      if(element.checked) {
	        	postContent += encodeURIComponent(element.name) + "=" + encodeURIComponent(element.value) + "&";
			}
		} else if(element.type=="file") {
			if(element.value != "") {
				postContent += encodeURIComponent(element.name) + "=" + encodeURIComponent(element.value) + "&";
			}
		} else {
				postContent += encodeURIComponent(element.name) + "=" + encodeURIComponent(element.value) + "&";
		}
	  }

//	  var contentDiv = document.createElement("DIV");
//	  contentDiv.style.border="1px solid #008800";
//	  contentDiv.innerHTML = "Will post:<br/>" + postContent;
//	  document.body.appendChild(contentDiv);
	  this.ajaxSubmit(form.action, form.method, postContent);

	}

	// url - the url to do submit
	// method - "get" or "post"
	// postContent - the string with values to be submited
	// resultDivId - the division of which to display result text in, in null, then
	// create an element and add it to the end of the body
	this.ajaxSubmit = function(url, method, postContent, resultDivId)
	{
	    var loadingDiv = document.getElementById('loading');
	    if(!loadingDiv) {
	    	loadingDiv = document.createElement("div");
	    	loadingDiv.id = "loading";
	    	loadingDiv.style.border="1px solid #008800";
	    	document.body.appendChild(loadingDiv);
	    }
		// call in new thread to allow ui to update
		window.setTimeout(function () {
			loadingDiv.innerHTML = "<img src='/images/loading.gif'/>Loading....";
			loadingDiv.style.display = "";
		}, 1);

		// code for Mozilla, etc.
		if (window.XMLHttpRequest)
		{
			xmlhttp=new XMLHttpRequest();
		}
		// code for IE
		else if (window.ActiveXObject)
		{
			xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		}

		if(xmlhttp) {
			xmlhttp.onreadystatechange = function() {
				// debugger; // Moz debug keyword
				// if xmlhttp shows "loaded"
				if (xmlhttp.readyState==4)
				{
					if(resultDivId) {
						document.getElementByID(resultDivId).innerHTML = xmlhttp.responseText;
					} else {
	                    var result = document.createElement("DIV");
	                    result.style.border="1px solid #363636";
	                    result.innerHTML = xmlhttp.responseText;
	                    document.body.appendChild(result);
					}

				  	loadingDiv.innerHTML =
					  "<img src='/images/good.gif'/>Submit finnished!";
				}

			};

			if(method.toLowerCase() == "get") {
				xmlhttp.open("GET", url + "?" + postContent, true);
				xmlhttp.send(null);
			} else if(method.toLowerCase() == "post") {
				xmlhttp.open("POST", url, true);
				xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				xmlhttp.send(postContent);
			}
		} else {
	        loadingDiv.innerHTML =
			  "Can't create XMLHttpRequest object, please check your web browser.";
		}

	}
}
// }}} end of common utils methods