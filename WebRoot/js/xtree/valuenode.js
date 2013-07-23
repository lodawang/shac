/**
 * @author BeanSoft
 * @version 1.0
    Create a TreeNodeVO js object corresponding to the backgroud Java object.
*/
function TreeNodeVO(id, parentID, hasChildren, text, actionURI, targetFrame) {
    this.id = id;
    this.parentID = parentID;
    this.hasChildren = hasChildren;
    this.text = text;
    this.actionURI = actionURI;
    this.targetFrame = targetFrame;

    // Encode current node to xml, dependency: xmlextras.js
    this.toXML = function() {
		var doc = XmlDocument.create();
		doc.appendChild( doc.createProcessingInstruction("xml",
			"version=\"1.0\" encoding=\"utf-8\"") );
		var root = doc.createElement("nodes");

		var node = doc.createElement("node");

		node.setAttribute("id", this.id);
		node.setAttribute("parentID", this.parentID);
		node.setAttribute("hasChildren", this.hasChildren);

		if(this.actionURI) {
			node.setAttribute("actionURI", this.actionURI);
		}

		if(this.targetFrame) {
			node.setAttribute("targetFrame", this.targetFrame);
		}

		if(this.text) {
			node.setAttribute("text", this.text);
		}

		root.appendChild(node);
		doc.appendChild(root);

		return doc.xml;
    };

    this.toString = function() {
    	return "TreeNodeVO:id = " + this.id + ", parentID = " + this.parentID
    		+ ", hasChildren = " + this.hasChildren + ", text = " + this.text;
    };
}