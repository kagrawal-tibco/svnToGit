package com.tibco.cep.query.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Nov 14, 2007
 * Time: 8:18:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseTreeNode implements ObjectTreeNode {
    protected List children;
    Object payload;
    private int type;
    private String text;

    public BaseTreeNode() {
    }

    public BaseTreeNode(Object payload) {
		this.payload = payload;
    }

    /** Override in a subclass to change the impl of children list */
    protected List createChildrenList() {
        return new ArrayList();
    }

    public void addChild(ObjectTreeNode t) {
        if ( t==null ) {
            return; // do nothing upon addChild(null)
        }
        ObjectTreeNode childTree = (ObjectTreeNode)t;
        if ( childTree.getChildCount() > 0  ) {
                if ( this.children != null ) { // must copy, this has children already
                    int n = childTree.getChildCount();
                    for (int i = 0; i < n; i++) {
                        this.children.add(childTree.getChild(i));
                    }
                }
                else {
                    // no children for this but t has children; just set pointer
                    this.children = childTree.getNodeChildren();
                }
        }
        else { // t is not empty and might have children
            if ( children==null ) {
                children = createChildrenList(); // create children list on demand
            }
            children.add(t);
        }
    }

    public ObjectTreeNode dupNode() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ObjectTreeNode dupTree() {
        ObjectTreeNode newTree = this.dupNode();
		for (int i = 0; children!=null && i < children.size(); i++) {
			ObjectTreeNode t = (ObjectTreeNode) children.get(i);
			ObjectTreeNode newSubTree = t.dupTree();
			newTree.addChild(newSubTree);
		}
		return newTree;
    }

    public ObjectTreeNode getChild(int i) {
        if ( children==null || i>=children.size() ) {
            return null;
        }
        return (BaseTreeNode)children.get(i);
    }

    public ObjectTreeNode getFirstChildWithType(int type) {
        for (int i = 0; children!=null && i < children.size(); i++) {
            BaseTreeNode t = (BaseTreeNode) children.get(i);
            if ( t.getType()==type ) {
                return t;
            }
        }
        return null;
    }

    public int getChildCount() {
        if ( children==null ) {
            return 0;
        }
        return children.size();
    }

    public List getNodeChildren() {
        return this.children;
    }

    public boolean isNil() {
        return payload == null;
    }

    public String getText() {
        return this.text;
    }

    public int getType() {
        return this.type;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String toStringTree() {
        if ( children==null || children.size()==0 ) {
			return this.toString();
		}
		StringBuffer buf = new StringBuffer();
		if ( !isNil() ) {
			buf.append("(");
			buf.append(this.toString());
			buf.append(' ');
		}
		for (int i = 0; children!=null && i < children.size(); i++) {
			ObjectTreeNode t =  (ObjectTreeNode) children.get(i);
			if ( i>0 ) {
				buf.append(' ');
			}
			buf.append(t.toStringTree());
		}
		if ( !isNil() ) {
			buf.append(")");
		}
		return buf.toString();
    }
}
