

package com.tibco.be.parser.tree;


import java.util.Iterator;

import com.tibco.be.parser.Token;

/* All AST nodes must implement this interface.  It provides basic
machinery for constructing the parent and child relationships
between nodes. */

public interface Node {
    int NODE_NULL_RELATION=0; //works with none, tokens and episolons(e)
    int NODE_UNARY_RELATION=1; //works with one node
    int NODE_BINARY_RELATION = 2;  //Always has two nodes to work with
    int NODE_VARIABLE_RELATION = Integer.MAX_VALUE;

    public int getRelationKind();

    public void setRelationKind(int arity);
    
    /**
     * add child to beginning of list of children
     * @param child
     */ 
    public void prependChild(Node child);
    public void appendChild(Node child);
    /**
     * get child at index in list of children
     * @param index
     * @return
     */ 
    public Node getChild(int index);
    /**
     * iterator over list of children for this node only(not recursive)
     * @return
     */ 
    public Iterator<Node> getChildren();

    public void dump(String prefix);
    
    public Object accept(NodeVisitor v);
    public void setType(NodeType type);
    public NodeType getType();
    
    public Token getFirstToken();
    public Token getLastToken();

    public int getBeginJavaLine();

    public void setBeginJavaLine(int begin);

    public int getEndJavaLine();

    public void setEndJavaLine(int end);

}
