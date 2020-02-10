package com.tibco.be.parser.tree;

import java.util.Iterator;

import com.tibco.be.parser.Token;
import com.tibco.be.parser.semantic.FunctionRec;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jun 8, 2004
 * Time: 1:57:58 PM
 * To change this template use Options | File Templates.
 */

public class FunctionNode extends BaseNode{

    protected NameNode mFnName;
    protected FunctionRec funcRec = null;
    
    public FunctionNode(NameNode name) {
        mFnName = name;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        
        buf.append("FunctionNode ");
        buf.append(mFnName.toString());
        buf.append(" returns ");
        buf.append(String.valueOf(type));
        buf.append(" (");
        if(nodes.size() > 0)  {
            buf.append(nodes.get(0).toString());
        }
        for (int i=1; i < nodes.size(); i++)
        {
            buf.append(",");
            buf.append(nodes.get(i).toString());
        }
        buf.append(")");

        return buf.toString();
    }
    
    public String toString(String prefix) { return prefix + toString(); }
    
    public void dump(String pfx) {
        System.out.println(toString(pfx));
    }
    
    public Object accept(NodeVisitor v) {
        return v.visitFunctionNode(this);   
    }

    public NameNode getName() {
        return mFnName;
    }
    
    public Iterator getArgs() {
        return getChildren();
    }
    
    public FunctionRec getFunctionRec() {
        return funcRec;
    }
    
    public void setFunctionRec(FunctionRec funcRec) {
        this.funcRec = funcRec;
    }
    
    public Token getFirstToken() {
        NameNode name = getName();
        if(name != null) {
            return name.getFirstToken();
        } else {
            return null;
        }
    }

    public Token getLastToken() {
        Node lastArg = getLastChild();
        if(lastArg != null) {
            return lastArg.getLastToken();
        } else {
            NameNode name = getName();
            if(name != null) {
                return name.getLastToken();
            } else {
                return null;
            }
        }
    }
}
