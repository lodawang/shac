/*
	js/js_tree_viewer.js
	JavaScript code for the js_tree_viewer.jsp v1.0
	(c) 2005 - 2006 Jacky Liu
	Mar 21, 2006
*/

// Custom alert function
//window.alert = function(msg) {
//	info("alert: " + msg);
//};

function toAddNodeTopic(){
	if (tree.getSelected()) {
	    var treeVO = tree.getSelected().treeNodeVO;
	    var parentID = "";
		if(treeVO) {
			parentID = treeVO.id;
		}
		window.open(webFXTreeConfig.createSubNodeUrl+parentID,"RightFrame");
	}
}

/**
 * Add a node to the tree, both add to the tree display and backgroud database.
 */
function openAddNodeDialog() {
	if (tree.getSelected()) {
//		var str = window.prompt("Please input the new node title:", "New");
		displayDivInLightBox($('createNodeDiv'));
		
		$('createNodeInput').focus();
	} else {
		alert("Please select one node on the tree first!");
	}
}

/**
 * Add a node to the tree, both add to the tree display and backgroud database.
 * 
 * @author beansoft
 * @version 1.0 2006-05-04
 */
function doAddNode() {
	if (tree.getSelected()) {
//		var str = window.prompt("Please input the new node title:", "New");		
		var str = $('createNodeInput').value;
		
		if(!str) {
			return;
		}

		var treeVO = tree.getSelected().treeNodeVO;
		if(treeVO) {
			treeVO = new TreeNodeVO("0", treeVO.id, false, str,
				treeVO.actionURI, treeVO.targetFrame);
		} else {
			treeVO = new TreeNodeVO("0", "0", false, str, "actionURI", "main");
		}

		var newNode = new WebFXTreeItem(str);
		newNode.treeNodeVO = treeVO;
		debug(newNode.treeNodeVO.toXML());

		tree.getSelected().createNode(newNode);	
	}
}

/**
 * Open the dialog to rename the node.
 */
function openRenameNodeDialog() {
	if (tree.getSelected()
	&& tree.getSelected().updateNode
	&& tree.getSelected().treeNodeVO) {
		$('renameNodeInput').value = tree.getSelected().text;
		displayDivInLightBox($('renameNodeDiv'));
		$('renameNodeInput').focus();
	} else {
		alert("Please select one node on the tree first!");
	}
}
/**
 * Rename the selected node.
 */
function doRenameSelectedNode() {
	if (tree.getSelected()
	&& tree.getSelected().updateNode
	&& tree.getSelected().treeNodeVO) {
//		var str = window.prompt("Please input the new node title:", tree.getSelected().text);
		var str = $('renameNodeInput').value;
		if(str && str != tree.getSelected().text) {
			//button.disabled = true;
		} else {
			return;
		}
		var selectedNode = tree.getSelected();
		var treeVO = selectedNode.treeNodeVO;
		if(treeVO) {
			treeVO = new TreeNodeVO(treeVO.id, treeVO.parentID, treeVO.hasChildren, str,
				treeVO.actionURI, treeVO.targetFrame);
		} else {
			treeVO = new TreeNodeVO("0", "0", false, str, "actionURI", "main");
		}

//		var newNode = new WebFXLoadTreeItem(str, selectedNode.src,
//			selectedNode.action);
//		newNode.treeNodeVO = treeVO;
//        newNode.target = selectedNode.target;
		selectedNode.treeNodeVO = treeVO;
		selectedNode.text = str;

		debug(selectedNode.treeNodeVO.toXML());

		selectedNode.updateNode();
	} else {
		alert("Please select one node (and not root node) on the tree first!");
	}
}

/**
 * Delete the selected node from both the tree and background database server.
 */
function delNode() {
	if (tree.getSelected() && tree.getSelected().removeNode) {
		tree.getSelected().removeNode();
	}
}

/**
 *  Refresh the selected node.
 */
function refreshNode() {
	if (tree.getSelected() && tree.getSelected().reload) {
	    tree.getSelected().reload();
	}
}

/*
 Start to move the node.
 2006-03-12
 */
function moveNodeStart() {
	if (tree.getSelected()
	&& tree.getSelected().updateNode
	&& tree.getSelected().treeNodeVO) {
		$('fromNode').treeNode = tree.getSelected();
		$('fromNode').innerHTML = tree.getSelected().text
			.fontcolor('green').bold();

		setDisplay($('moveNodeFieldSet'), true);
	}

}

// Display where the new node will be move to
document.onmouseup = function() {
	// If not display, then do nothing
	if($('moveNodeFieldSet').style.display == "none") {
		return;
	}

	if (tree.getSelected()) {
		$('toNode').treeNode = tree.getSelected();
		$('toNode').innerHTML = tree.getSelected().text
			.fontcolor('blue').bold();
	}
};

/**
	Finish to move the node.
	2006-03-12
 */
function moveNodeOk() {
	if($('fromNode').treeNode && $('toNode').treeNode) {
		// TODO Network update, if success then move it at front
		// Using cloned tree node, TODO judge XTreeItem and TreeItem,
		// update hasChildren property, update current node's parent
		// id(include top leve) and
		// action src for display new page

		var selectedNode = $('fromNode').treeNode;
		var toNode = $('toNode').treeNode;
		var treeVO = selectedNode.treeNodeVO;
		var toTreeVO = toNode.treeNodeVO;

		// TODO more code here

		if(treeVO) {
			treeVO = new TreeNodeVO(treeVO.id, treeVO.parentID, treeVO.hasChildren,
			treeVO.text,
				treeVO.actionURI, treeVO.targetFrame);
		} else {
			treeVO = new TreeNodeVO("0", "0", false, selectedNode.text, "actionURI", "main");
		}


		var newNode = new WebFXLoadTreeItem(selectedNode.text, selectedNode.src,
			selectedNode.action);
		newNode.treeNodeVO = treeVO;
        	newNode.target = selectedNode.target;

		$('fromNode').treeNode.remove();
		$('toNode').treeNode.add(newNode);
	}
	setDisplay($('moveNodeFieldSet'), false);
}

/**
 * Cancel move the node.
 */
function moveNodeCancel() {
	setDisplay($('moveNodeFieldSet'), false);
}

var oncontextmenu = function(e)
{
	// Make sure popup only displays on codeman_js_tree
	if(checkEventIn(e, $('js_tree')) == false) {
		return;
	}
	    
    if(!e) {
        e = window.event;
    }

    if(browserInfo.IsGecko) {
        try {
            e.preventDefault();
        } catch(ex) {
        }
    }

    $('popup').style.left = e.clientX + 'px';
    $('popup').style.top = e.clientY  + 'px';
    $('popup').style.display = "";
    fixPos($('popup'), e.clientX, e.clientY);

    return false;
};

OnDocumentClick = function( event )
{
	if(checkEventIn(event, $('popup')) == true) {
		return;
	}


    $('popup').style.display = "none";
}

function fixPos(menu, x, y)
{
	var docheight,docwidth,dh,dw;
	docheight = document.body.clientHeight;
	docwidth  = document.body.clientWidth;

	dh = y - docheight;
	dw = x - docwidth;
	debug( menu.style.width );

	if(dw>0)
	{
		menu.style.left = (x - dw - menu.style.width) + document.body.scrollLeft + "px";
	}
	else
	{
		menu.style.left = x + document.body.scrollLeft;
	}
	if(dh>0)
	{
		menu.style.top = (y - dh) + document.body.scrollTop + "px"
	}
	else
	{
		menu.style.top  = y + document.body.scrollTop;
	}
}

try {
   $('js_tree').oncontextmenu = oncontextmenu;
   document.onclick = OnDocumentClick;
}catch(ex){
}

if(browserInfo.IsGecko) {
    try {
       $('js_tree').addEventListener(
	   	'contextmenu', oncontextmenu, true);
    } catch(ex) {
    }
    document.addEventListener('click',OnDocumentClick,true);
}

function hidePopup() {
	$('popup').style.display = "none";
}