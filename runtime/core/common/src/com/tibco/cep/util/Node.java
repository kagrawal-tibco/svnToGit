package com.tibco.cep.util;


import java.util.List;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;


/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Apr 10, 2006
 * Time: 12:53:27 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Node {


    /**
     * @return String which specifies the full path of node. Each element in the path is the node id
     *         Example /foo/bar where / is the root (nameless), foo is a child of the root, and bar is a child of foo
     */
    String getFullPath();


    /**
     * @return String value of the node id
     */
    String getID();


    /**
     * Check if it has any children
     *
     * @return
     */
    boolean hasChildren();


    /**
     * Add Node to the children list
     *
     * @param n
     */
    void addNode(Node n);


    /**
     * Return the parent node of this node.
     *
     * @return
     */
    Node getParent();


    /**
     * Returns the children of this nodes. Can be an empty, but non null list
     *
     * @return
     */
    List getNodes();


    /**
     * Get the child node at index i
     *
     * @param index
     * @return
     */
    Node getNode(int index);


    /**
     * Get a Node w.r.t this as specified by the path.
     *
     * @param path
     * @return
     */
    Node getNode(String path);


    /**
     * Search for a node by this idref in the children, and return
     *
     * @param idRef
     * @return
     */
    Node getNodeByIDRef(String idRef);


    /**
     * Search for a node given the search filter, It does pre-order tree-traversal, and visit each node
     *
     * @param f a Searchfilter.
     * @return a
     * @see SearchFilter
     */
    Node searchNode(SearchFilter f);


    /**
     * Return a list of nodes that match the search filter.
     *
     * @param f
     * @return
     */
    List searchNodes(SearchFilter f);


    /**
     * Remove a Node from the graph, and indicates its success as a boolean value
     *
     * @param n
     * @return
     */
    boolean removeNode(Node n);


    /**
     * Check if the node is a leaf node, or not.
     *
     * @return
     */
    boolean isLeaf();


    /**
     * Return an XML DOM representation of this Graph
     *
     * @return
     */
    XiNode toXiNode();


    /**
     * Return the Value associated to the Node
     *
     * @return
     */
    Object getValue();


    /**
     * Return a Fully Qualified Name - Only useful when you are manipulating with XML stuff.
     * TODO  - can be refactored to someother interface
     *
     * @return
     */
    ExpandedName getNodeType();


    /**
     * Return the firstNode in the children list
     *
     * @return
     */
    Node firstNode();


    /**
     * Return the last node in the list
     *
     * @return
     */
    Node lastNode();


    /**
     * Copy the node. This is a deep copy contract
     *
     * @return
     */
    Node copy();


    /**
     * Set the parent of the Node
     *
     * @param parent
     */
    void setParent(Node parent);


    /**
     * Deletes all the children
     */
    void removeAllChildren();


    /**
     * Copies children from the other node.
     *
     * @param otherNode
     */
    void addAllChildren(Node otherNode);


    boolean isVisitable();


    void accept(NodeVisitor nv);


    public interface NodeVisitor {


        void visited(Node n);
    }

}
