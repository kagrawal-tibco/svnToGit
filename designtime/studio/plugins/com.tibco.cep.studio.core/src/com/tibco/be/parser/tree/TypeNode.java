package com.tibco.be.parser.tree;

import com.tibco.be.parser.Token;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Jul 12, 2005
 * Time: 5:12:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class TypeNode extends BaseNode {
    
    protected int arrayDim;
    protected Token lastBrackets = null; 
    
    public TypeNode(NameNode typeName) {
        this(typeName, 0);
    }
    public TypeNode(NameNode typeName, int arrayDimension) {
        this(typeName, arrayDimension, null);
    }
    
    //parser only uses this constructor, so must always follow conventions of the above constructors
    public TypeNode(NameNode typeName, int arrayDimension, Token lastBracketPair) {
        super.prependChild(typeName);
        arrayDim = arrayDimension;
        lastBrackets = lastBracketPair;
    }
    
    
    
    public int getArrayDimension() {
        return arrayDim;
    }
    
    public NameNode getTypeName() {
        return (NameNode) getChild(0);
    }
    
    public String toString() {
        StringBuffer str = new StringBuffer(getTypeName().toName());
        for(int ii = 0; ii < arrayDim; ii++) {
            str.append("[]");
        }
        return str.toString();
    }

    public String toString(String prefix) {
        return prefix + toString();
    }

    public void dump(String prefix) {
        System.out.println(toString(prefix));
    }

    public Object accept(NodeVisitor v) {
        return v.visitTypeNode(this);
    }

    public Token getFirstToken() {
        NameNode typeName = getTypeName();
        if(typeName != null) {
            return typeName.getFirstToken();
        } else {
            return null;
        }
    }

    public Token getLastToken() {
        NameNode typeName = getTypeName();
        if(arrayDim > 0 && lastBrackets != null) {
            return lastBrackets;
        } else if(typeName != null) {
            return typeName.getLastToken();
        } else {
            return null;
        }
    }
}
