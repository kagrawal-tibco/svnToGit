package com.tibco.be.parser.tree;

import java.util.Iterator;
import java.util.LinkedList;

public abstract class BaseNode implements Node {
    protected LinkedList<Node> nodes = new LinkedList<Node>();
    protected NodeType type = null;
    protected int relation = Node.NODE_NULL_RELATION;
    protected int endJavaLine = -1;
    protected int beginJavaLine = -1;


    /* You can override these two methods in subclasses of ProductionNode to
    customize the way the node appears when the tree is dumped.  If
    your output uses more than one line you should override
    toString(String), otherwise overriding toString() is probably all
    you need to do. */

    public abstract String toString();
    public abstract String toString(String prefix);

    //Always inserts at the Zeroth location
    public void prependChild (Node n) {
        nodes.add(0, n);
    }
    public void appendChild (Node n) {
        nodes.add(n);
    }

    public Node removeFirstChild() {
        return (Node)nodes.removeFirst();
    }

    public Node getChild(int index) {
        if(nodes.size() <= index) {
            return null;
        }
        return (Node)nodes.get(index);
    }

    public Node getLastChild() {
        if(nodes.size() <= 0) return null;
        return (Node)nodes.getLast();
    }

    public Iterator<Node> getChildren() {
        return nodes.iterator();
    }

    public int getChildCount() {
        return (nodes == null) ? 0 : nodes.size();
    }
    
    public void clearChildren() {
        nodes.clear();
    }

    public abstract Object accept(NodeVisitor v);

    public void setRelation(int relation) {
        this.relation = relation;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public NodeType getType() {
        return type;
    }

    public int getRelationKind() {
        return relation;
    }

    public void setRelationKind(int arity) {
        relation = arity;
    }

	public int getBeginJavaLine() {
        return this.beginJavaLine;
    }

    public void setBeginJavaLine(int begin) {
        this.beginJavaLine = begin;
    }

    public int getEndJavaLine() {
        return this.endJavaLine;
    }

    public void setEndJavaLine(int end) {
        this.endJavaLine = end;
    }
}