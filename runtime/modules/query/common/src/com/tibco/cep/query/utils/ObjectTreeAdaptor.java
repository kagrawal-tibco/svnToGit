package com.tibco.cep.query.utils;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Nov 14, 2007
 * Time: 7:02:08 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ObjectTreeAdaptor {
    // C o n s t r u c t i o n

	/**
     * Create a tree node
     */
	public Object create(Object payload);

	/** Duplicate tree recursively, using dupNode() for each node */
	public Object dupTree(ObjectTreeNode tree);

	/** Duplicate a single tree node */
	public Object dupNode(ObjectTreeNode treeNode);

	/** Add a child to the tree t.  If child is a flat tree (a list), make all
	 *  in list children of t.  Warning: if t has no children, but child does
	 *  and child isNil then you can decide it is ok to move children to t via
	 *  t.children = child.children; i.e., without copying the array.  Just
	 *  make sure that this is consistent with have the user will build
	 *  ASTs.  Do nothing if t or child is null.
	 */
	public void addChild(ObjectTreeNode t, ObjectTreeNode child);

    // C o n t e n t

	/** For tree parsing, I need to know the token type of a node */
	public int getType(ObjectTreeNode t);

	/** Node constructors can set the type of a node */
	public void setType(ObjectTreeNode t, int type);

    /** Return the text of the node */
	public String getText(ObjectTreeNode t);

	/** Node constructors can set the text of a node */
	public void setText(ObjectTreeNode t, String text);

    // N a v i g a t i o n

	/** Get a child 0..n-1 node */
	public ObjectTreeNode getChild(ObjectTreeNode t, int i);

	/** How many children?  If 0, then this is a leaf node */
	public int getChildCount(ObjectTreeNode t);

    /** Return a nil node (an empty but non-null node) that can hold
	 *  a list of element as the children.  If you want a flat tree (a list)
	 *  use "t=adaptor.nil(); t.addChild(x); t.addChild(y);"
	 */
	public ObjectTreeNode nil();

	/** Is tree considered a nil node used to make lists of child nodes? */
	public boolean isNil(ObjectTreeNode tree);
}
