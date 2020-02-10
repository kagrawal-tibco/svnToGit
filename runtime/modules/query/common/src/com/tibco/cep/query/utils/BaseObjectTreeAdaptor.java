package com.tibco.cep.query.utils;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Nov 14, 2007
 * Time: 7:13:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseObjectTreeAdaptor implements ObjectTreeAdaptor{

    public BaseObjectTreeAdaptor() {
    }

    /**
     * Add a child to the tree t.  If child is a flat tree (a list), make all
     * in list children of t.  Warning: if t has no children, but child does
     * and child isNil then you can decide it is ok to move children to t via
     * t.children = child.children; i.e., without copying the array.  Just
     * make sure that this is consistent with have the user will build
     * ASTs.  Do nothing if t or child is null.
     */
    public void addChild(ObjectTreeNode t, ObjectTreeNode child) {
        if ( t!=null && child!=null ) {
			t.addChild(child);
		}
    }

    /**
     * Create a tree node
     */
    public ObjectTreeNode create(Object payload) {
        return new BaseTreeNode(payload);
    }

    /**
     * Duplicate a single tree node
     */
    public ObjectTreeNode dupNode(ObjectTreeNode treeNode) {
        if ( treeNode==null ) {
			return null;
		}
		return treeNode.dupNode();
    }

    /**
     * Duplicate tree recursively, using dupNode() for each node
     */
    public ObjectTreeNode dupTree(ObjectTreeNode tree) {
        return tree.dupTree();
    }

    /**
     * Get a child 0..n-1 node
     */
    public ObjectTreeNode getChild(ObjectTreeNode t, int i) {
        return t.getChild(i);
    }

    /**
     * How many children?  If 0, then this is a leaf node
     */
    public int getChildCount(ObjectTreeNode t) {
        return t.getChildCount();
    }

    /**
     * Return the text of the node
     */
    public String getText(ObjectTreeNode t) {
        return t.getText();
    }

    /**
     * For tree parsing, I need to know the token type of a node
     */
    public int getType(ObjectTreeNode t) {
        return t.getType();
    }

    /**
     * Node constructors can set the text of a node
     */
    public void setText(ObjectTreeNode t, String text) {
        t.setText(text);
    }

    /**
     * Node constructors can set the type of a node
     */
    public void setType(ObjectTreeNode t, int type) {
        t.setType(type);
    }

    /**
     * Is tree considered a nil node used to make lists of child nodes?
     */
    public boolean isNil(ObjectTreeNode tree) {
        return tree.isNil();
    }

    /**
     * Return a nil node (an empty but non-null node) that can hold
     * a list of element as the children.  If you want a flat tree (a list)
     * use "t=adaptor.nil(); t.addChild(x); t.addChild(y);"
     */
    public ObjectTreeNode nil() {
        return create(null);
    }


}
