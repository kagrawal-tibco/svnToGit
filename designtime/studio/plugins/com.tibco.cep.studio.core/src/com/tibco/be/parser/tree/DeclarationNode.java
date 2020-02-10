package com.tibco.be.parser.tree;

import java.util.Iterator;

import com.tibco.be.parser.Token;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Mar 2, 2005
 * Time: 4:47:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class DeclarationNode extends BaseNode {

    public String toString() {
        return null;
    }

    public String toString(String prefix) {
        return prefix + toString();
    }

    public void dump(String prefix) {
        System.out.println(toString(prefix));
    }

    public Object accept(NodeVisitor v) {
        v.visitDeclarationNode(this);
        return null;
    }

    public TypeNode getDeclarationType() {
        if(nodes.size() > 0) {
            return (TypeNode)getChild(0);
        } else {
            return null;
        }
    }
    
    //iterator of Nodes.  Either returns a single NamedNode or else
    //a ProductionNode with the form NamedNode = ProductionNode
    public Iterator getDeclarations() {
        if(nodes.size() > 1) {
            return nodes.subList(1, nodes.size()).iterator();
        } else {
            return null;
        }
    }
    
    public Token getFirstToken() {
        TypeNode declType = getDeclarationType();
        if(declType != null) {
            return getDeclarationType().getFirstToken();
        } else {
            return null;
        }
    }

    public Token getLastToken() {
        Node lastNode = getLastChild();
        if(lastNode != null) {
            return lastNode.getLastToken();
        } else {
            return null;
        }
    }
}
