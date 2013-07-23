/*----------------------------------------------------------------------------\
|                    XLoadTree for CodeManager 1.00                           |
|-----------------------------------------------------------------------------|
|           Modified for Code Manager with background node operation.         |
|                 Coypright (c) 2006 BeanSoft Studio                          |
|                          2006-03-12                                         |
|-----------------------------------------------------------------------------|


/*----------------------------------------------------------------------------\
|                               XLoadTree 1.11                                |
|-----------------------------------------------------------------------------|
|                         Created by Erik Arvidsson                           |
|                  (http://webfx.eae.net/contact.html#erik)                   |
|                      For WebFX (http://webfx.eae.net/)                      |
|-----------------------------------------------------------------------------|
| An extension to xTree that allows sub trees to be loaded at runtime by      |
| reading XML files from the server. Works with IE5+ and Mozilla 1.0+         |
|-----------------------------------------------------------------------------|
|                   Copyright (c) 1999 - 2002 Erik Arvidsson                  |
|-----------------------------------------------------------------------------|
| This software is provided "as is", without warranty of any kind, express or |
| implied, including  but not limited  to the warranties of  merchantability, |
| fitness for a particular purpose and noninfringement. In no event shall the |
| authors or  copyright  holders be  liable for any claim,  damages or  other |
| liability, whether  in an  action of  contract, tort  or otherwise, arising |
| from,  out of  or in  connection with  the software or  the  use  or  other |
| dealings in the software.                                                   |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| This  software is  available under the  three different licenses  mentioned |
| below.  To use this software you must chose, and qualify, for one of those. |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| The WebFX Non-Commercial License          http://webfx.eae.net/license.html |
| Permits  anyone the right to use the  software in a  non-commercial context |
| free of charge.                                                             |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| The WebFX Commercial license           http://webfx.eae.net/commercial.html |
| Permits the  license holder the right to use  the software in a  commercial |
| context. Such license must be specifically obtained, however it's valid for |
| any number of  implementations of the licensed software.                    |
| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |
| GPL - The GNU General Public License    http://www.gnu.org/licenses/gpl.txt |
| Permits anyone the right to use and modify the software without limitations |
| as long as proper  credits are given  and the original  and modified source |
| code are included. Requires  that the final product, software derivate from |
| the original  source or any  software  utilizing a GPL  component, such  as |
| this, is also licensed under the GPL license.                               |
|-----------------------------------------------------------------------------|
| 2001-09-27 | Original Version Posted.                                       |
| 2002-01-19 | Added some simple error handling and string templates for      |
|            | reporting the errors.                                          |
| 2002-01-28 | Fixed loading issues in IE50 and IE55 that made the tree load  |
|            | twice.                                                         |
| 2002-10-10 | (1.1) Added reload method that reloads the XML file from the   |
|            | server.                                                        |
/ 2003-05-06 | Added support for target attribute                             |
|-----------------------------------------------------------------------------|
| Dependencies: xtree.js - original xtree library                             |
|               xtree.css - simple css styling of xtree                       |
|               xmlextras.js - provides xml http objects and xml document     |
|                              objects                                        |
|-----------------------------------------------------------------------------|
| Created 2001-09-27 | All changes are in the log above. | Updated 2003-05-06 |
\----------------------------------------------------------------------------*/

webFXTreeConfig.loadingText = "Loading...";
webFXTreeConfig.loadErrorTextTemplate = "Error loading \"%1%\"";
webFXTreeConfig.emptyErrorTextTemplate = "Error \"%1%\" does not contain any tree items";
// Taken from beta2
webFXTreeConfig.loadingIcon = "images/loading.gif";

/*
 * WebFXLoadTree class
 */
function WebFXLoadTree(sText, sXmlSrc, sAction, sBehavior, sIcon, sOpenIcon) {
	// call super
	this.WebFXTree = WebFXTree;
	this.WebFXTree(sText, sAction, sBehavior, sIcon, sOpenIcon);

	// setup default property values
	this.src = sXmlSrc;
	this.loading = false;
	this.loaded = false;
	this.errorText = "";

	// check start state and load if open
	if (this.open)
		_startLoadXmlTree(this.src, this);
	else {
		// and create loading item if not
		this._loadingItem = WebFXLoadTree.createLoadingItem();
		this.add(this._loadingItem);
	}
}

// New added function, supports div on click
webFXTreeHandler.onclick = function (oItem) {
	if (typeof oItem._onclick == "function") {
		oItem._onclick();
	}
};

// Taken from xloadtree2.js http://webfx.eae.net/dhtml/xtree2b/
WebFXLoadTree.createLoadingItem = function (sText, sIcon) {
	if(!sText) {
		sText = webFXTreeConfig.loadingText;
	}

	if(!sIcon) {
		sIcon = webFXTreeConfig.loadingIcon;
	}
	return new WebFXTreeItem(sText, null, null,
							 sIcon);
};

WebFXLoadTree.prototype = new WebFXTree;

// override the expand method to load the xml file
WebFXLoadTree.prototype._webfxtree_expand = WebFXTree.prototype.expand;
WebFXLoadTree.prototype.expand = function() {
	if (!this.loaded && !this.loading) {
		// load
		_startLoadXmlTree(this.src, this);
	}
	this._webfxtree_expand();
};



/**
 * WebFXLoadTreeItem class.

 * Now supports TreeNodeVO
 */

function WebFXLoadTreeItem(sText, sXmlSrc, sAction, eParent, sIcon, sOpenIcon) {
	// call super
	this.WebFXTreeItem = WebFXTreeItem;
	this.WebFXTreeItem(sText, sAction, eParent, sIcon, sOpenIcon);

	// setup default property values
	this.src = sXmlSrc;
	this.loading = false;
	this.loaded = false;
	this.errorText = "";

	// check start state and load if open
	if (this.open)
		_startLoadXmlTree(this.src, this);
	else {
		// and create loading item if not
		this._loadingItem = WebFXLoadTree.createLoadingItem();
		this.add(this._loadingItem);
	}
}

WebFXLoadTreeItem.prototype = new WebFXTreeItem;

/* Update the displayed label text after the node has been displayed.
   @param text - the new text to display
   @author BeanSoft
   @version 1.0 2006-01-04
   */
WebFXTreeItem.prototype.setText = function(text) {
   if(text && document.getElementById(this.id + '-anchor')) {
   		// Note: FireFox doesn't support the innerText attribute
		document.getElementById(this.id + '-anchor').innerHTML = text;
   }
};

// Create a new node and call server side to create it
// node: a WebFXLoadTreeItem with treeNodeVO property
WebFXTreeItem.prototype.createNode = function(node, bNoIdent) {
	
	var currentNode = this;

	var xmlhttp = XmlHttp.create();
	method = "post";
	// TODO create a config object
	url = "applet_new_node.jsp";

	if(xmlhttp && node.treeNodeVO) {
		// and create loading item if not
		// call in new thread to allow ui to update
		window.setTimeout(function () {
			currentNode._loadingItem = WebFXLoadTree.createLoadingItem("Creating node...");
			currentNode.add(currentNode._loadingItem);
			currentNode.expand();
		}, 10);

		xmlhttp.onreadystatechange = function() {
			// if xmlhttp shows "loaded"
			if (xmlhttp.readyState==4)
			{
				// remove dummy
				if (currentNode._loadingItem != null) {
					currentNode._loadingItem.remove();
				}

				try {
					var newId = parseInt(xmlhttp.responseText);
					if(isNaN(newId)) {
						alert("Unable to create new node.");
					} else {
						// TODO create a config object
						node.treeNodeVO.id = newId;
						node.action = "node_info.jsp?id=" + newId;
						node.target = "main";
//						// TODO WebFXLoadTreeItem
//						var newNode = new WebFXLoadTreeItem(node.text,
//						"treenode.jsp?parentID=" + newId);
//
//						newNode.action = node.action;
//						newNode.target = node.target;
//
						currentNode.add(node, bNoIdent);
//
//						currentNode.reload();
//						currentNode.expand();

//						node.select();
						currentNode.blur();
						if(currentNode.getLast()) {
							currentNode.getLast().select();

							var previousSibling = currentNode.getLast().getPreviousSibling();
							if(previousSibling) {
								previousSibling.blur();
							}
						}

						// Browse the current node info page
						window.open(node.action, node.target);
					}
				} catch(e) {
					alert("Unable to create new node.");
				}

			}
		};

		var postContent = encodeURIComponent("nodexml") + "=" +
			encodeURIComponent(node.treeNodeVO.toXML());

		// call in new thread to allow ui to update
		window.setTimeout(function () {
			if(method.toLowerCase() == "get") {
				xmlhttp.open("GET", url + "?" + postContent, true);
				xmlhttp.send(null);
			} else if(method.toLowerCase() == "post") {
				xmlhttp.open("POST", url, true);
				xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				xmlhttp.send(postContent);
			}
		}, 10);

	} else {
        alert(
		  "Can't create XMLHttpRequest object, please check your web browser.");
	}

};
WebFXTree.prototype.createNode = WebFXLoadTreeItem.prototype.createNode;

// Update a new node and call server side to update it
WebFXTreeItem.prototype.updateNode = function() {
	var currentNode = this;

	var xmlhttp = XmlHttp.create();
	method = "post";
	// TODO create a config object
	url = "applet_update_node.jsp";

	if(xmlhttp && currentNode.treeNodeVO) {
		// and create loading item if not
		// call in new thread to allow ui to update
		window.setTimeout(function () {
			currentNode._loadingItem = WebFXLoadTree.createLoadingItem("Updating node...");
			currentNode.add(currentNode._loadingItem);
			currentNode.expand();
		}, 10);
		xmlhttp.onreadystatechange = function() {
			// if xmlhttp shows "loaded"
			if (xmlhttp.readyState==4)
			{
				// remove dummy
				if (currentNode._loadingItem != null) {
					currentNode._loadingItem.remove();
				}

				var res = xmlhttp.responseText;
 				if(res == "true") {
					if(currentNode.setText) {
						currentNode.setText(currentNode.text);
					}
                    currentNode.select();

					// Browse the current new node info page
					window.open(currentNode.action, currentNode.target);
				} else {
					alert("Unable to update node.");
				}

			}
		};

		var postContent = encodeURIComponent("nodexml") + "=" +
			encodeURIComponent(currentNode.treeNodeVO.toXML());

		// call in new thread to allow ui to update
		window.setTimeout(function () {
			if(method.toLowerCase() == "get") {
				xmlhttp.open("GET", url + "?" + postContent, true);
				xmlhttp.send(null);
			} else if(method.toLowerCase() == "post") {
				xmlhttp.open("POST", url, true);
				xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				xmlhttp.send(postContent);
			}
		}, 10);

	} else {
        alert(
		  "Can't create XMLHttpRequest object, please check your web browser.");
	}

};

// Remove a node from the tree, call server function
WebFXTreeItem.prototype.removeNode = function() {
	var currentNode = this;

	var xmlhttp = XmlHttp.create();
	method = "post";
	// TODO create a config object
	url = webFXTreeConfig.removeTreeNodeUrl;
	if(xmlhttp && currentNode.treeNodeVO) {
		// and create loading item if not
		// call in new thread to allow ui to update
		window.setTimeout(function () {
			currentNode._loadingItem = WebFXLoadTree.createLoadingItem("Deleting node...");
			currentNode.add(currentNode._loadingItem);
			currentNode.expand();
		}, 10);
		xmlhttp.onreadystatechange = function() {
			// if xmlhttp shows "loaded"
			if (xmlhttp.readyState==4)
			{
				// remove dummy
				if (currentNode._loadingItem != null) {
					currentNode._loadingItem.remove();
				}

				var res = xmlhttp.responseText;

				if(res == "true") {
					var previousSibling = currentNode.getPreviousSibling();

					currentNode.remove();
					// Browse the previous sibling
					if(previousSibling && previousSibling.action) {
						previousSibling.select();
						// Browse the current node info page
						//window.open(previousSibling.action, previousSibling.target);
					}
				} else {
					alert("无法删除该节点，请检查该目录下是否有子目录以及未删除的文档！");
				}
			}
		};

		var postContent = encodeURIComponent("id") + "="
			+ encodeURIComponent(currentNode.treeNodeVO.id);

		// call in new thread to allow ui to update
		window.setTimeout(function () {
			if(method.toLowerCase() == "get") {
				alert(url + "?" + postContent);
				xmlhttp.open("GET", url + "?" + postContent, true);
				xmlhttp.send(null);
			} else if(method.toLowerCase() == "post") {
				xmlhttp.open("POST", url, true);
				xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				xmlhttp.send(postContent);
			}
		}, 10);

	} else {
        alert(
		  "Can't create XMLHttpRequest object, please check your web browser.");
	}

};

// override the expand method to load the xml file
WebFXLoadTreeItem.prototype._webfxtreeitem_expand = WebFXTreeItem.prototype.expand;
WebFXLoadTreeItem.prototype.expand = function() {
	if (!this.loaded && !this.loading) {
		// load
		_startLoadXmlTree(this.src, this);
	}
	this._webfxtreeitem_expand();
};

// reloads the src file if already loaded
//WebFXLoadTree.prototype.reload =
WebFXLoadTreeItem.prototype.reload = function () {
	// if loading do nothing
	if (this.loaded) {
		var open = this.open;
		// remove
		while (this.childNodes.length > 0) {
			// Bug fix Feb 3 2006 by BeanSoft, fix a bug that this element
			// has no properties while reload
			if(this.childNodes[this.childNodes.length - 1]) {
				this.childNodes[this.childNodes.length - 1].remove();
			}
		}

		this.loaded = false;

		this._loadingItem = WebFXLoadTree.createLoadingItem();
		this.add(this._loadingItem);

		if (open)
			this.expand();
	}
	else if (this.open && !this.loading)
		_startLoadXmlTree(this.src, this);
};

/*
 * Helper functions
 */

// creates the xmlhttp object and starts the load of the xml document
function _startLoadXmlTree(sSrc, jsNode) {
	if (jsNode.loading || jsNode.loaded)
		return;
	jsNode.loading = true;
	var xmlHttp = XmlHttp.create();
	xmlHttp.open("GET", sSrc, true);	// async
	xmlHttp.onreadystatechange = function () {
		if (xmlHttp.readyState == 4) {
			jsNode._xmlHttp = xmlHttp;
			// Modified
			_xmlFileLoaded(jsNode);
		}
	};
	// call in new thread to allow ui to update
	window.setTimeout(function () {
		xmlHttp.send(null);
	}, 10);
}


// Converts an xml tree to a js tree. See article about xml tree format
function _xmlTreeToJsTree(oNode) {
	// retreive attributes
	var text = oNode.getAttribute("text");
	var action = oNode.getAttribute("action");
	var parent = null;
	var icon = oNode.getAttribute("icon");
	var openIcon = oNode.getAttribute("openIcon");
	var src = oNode.getAttribute("src");
	var target = oNode.getAttribute("target");

	// For TreeNodeVO, @author BeanSoft 2006-01-29
	var id = oNode.getAttribute("id");
	var parentID = oNode.getAttribute("parentID");
	var hasChildren = oNode.getAttribute("hasChildren");
	var treeNodeVO = new TreeNodeVO(id, parentID, hasChildren, text);
//	listAttributes(treeNodeVO);

	// create jsNode
	var jsNode;
	if (src != null && src != "")
		jsNode = new WebFXLoadTreeItem(text, src, action, parent, icon, openIcon);
	else
		jsNode = new WebFXTreeItem(text, action, parent, icon, openIcon);

	// Save the TreeNodeVO
	jsNode.treeNodeVO = treeNodeVO;

	if (target != "")
		jsNode.target = target;

	// go through childNOdes
	var cs = oNode.childNodes;
	var l = cs.length;
	for (var i = 0; i < l; i++) {
		if (cs[i].tagName == "tree")
			jsNode.add( _xmlTreeToJsTree(cs[i]), true );
	}

	return jsNode;
}

// Inserts an xml document as a subtree to the provided node
function _xmlFileLoaded(jsParentNode) {
	if (jsParentNode.loaded)
		return;

    oXmlDoc = jsParentNode._xmlHttp.responseXML;

	var bIndent = false;
	var bAnyChildren = false;
	jsParentNode.loaded = true;
	jsParentNode.loading = false;

	// check that the load of the xml file went well
//	if( oXmlDoc == null || oXmlDoc.documentElement == null) {
//		jsParentNode.errorText = parseTemplateString(webFXTreeConfig.loadErrorTextTemplate,
//							jsParentNode.src);
//	}

	// check that the load of the xml file went well, added from xtree2b 2006-03-19
	if(!oXmlDoc || oXmlDoc.parserError && oXmlDoc.parseError.errorCode != 0 || !oXmlDoc.documentElement) {
		if (!oXmlDoc || oXmlDoc.parseError.errorCode == 0) {
			jsParentNode.errorText = "Error loading " + jsParentNode.src + " (" + jsParentNode._xmlHttp.status + ": " + jsParentNode._xmlHttp.statusText + ")";
		} else {
			jsParentNode.errorText = "Error loading " + jsParentNode.src + " (" + oXmlDoc.parseError.reason + ")";
		}
	}
	else {
		// there is one extra level of tree elements
		var root = oXmlDoc.documentElement;

		// loop through all tree children
		var cs = root.childNodes;
		var l = cs.length;
		for (var i = 0; i < l; i++) {
			if (cs[i].tagName == "tree") {
				bAnyChildren = true;
				bIndent = true;
				jsParentNode.add( _xmlTreeToJsTree(cs[i]), true);
			}
		}

		// if no children we got an error
		if (!bAnyChildren)
			jsParentNode.errorText = parseTemplateString(webFXTreeConfig.emptyErrorTextTemplate,
										jsParentNode.src);
	}

	// remove dummy
	if (jsParentNode._loadingItem != null) {
		jsParentNode._loadingItem.remove();
		bIndent = true;
	}

	// show error in status bar
	if (jsParentNode.errorText != "")
		window.status = jsParentNode.errorText;

	var jsNode = jsParentNode;
	if (jsNode.errorText != "") {
		jsNode._loadingItem = WebFXLoadTree.createLoadingItem(jsNode.errorText, "images/exclamation.16.gif");
        jsNode._loadingItem.toolTip = "Click to reload";

		jsNode.add(jsNode._loadingItem);

		// Fetch by id
		$(jsNode._loadingItem.id)._onclick = function () {
			jsNode.reload();
		};
	}

	if (bIndent) {
		// indent now that all items are added
		jsParentNode.indent();
		jsParentNode.expand();
	}

}

// parses a string and replaces %n% with argument nr n
function parseTemplateString(sTemplate) {
	var args = arguments;
	var s = sTemplate;

	s = s.replace(/\%\%/g, "%");

	for (var i = 1; i < args.length; i++)
		s = s.replace( new RegExp("\%" + i + "\%", "g"), args[i] )

	return s;
}