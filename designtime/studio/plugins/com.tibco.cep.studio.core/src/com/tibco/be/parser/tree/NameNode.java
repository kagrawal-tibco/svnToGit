package com.tibco.be.parser.tree;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.parser.Token;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jun 3, 2004
 * Time: 9:42:19 AM
 * To change this template use Options | File Templates.
 */
public class NameNode extends BaseNode {
    /*
    protected static class NameType { private NameType(){} };
    public static final NameType TYPE_NAME = new NameType();
    public static final NameType EXPRESSION_NAME = new NameType();
    public static final NameType METHOD_NAME = new NameType();
    public static final NameType AMBIGUOUS_NAME = new NameType();
    public static final NameType PACKAGE_NAME = new NameType();
    public static final NameType PACKAGE_OR_TYPE_NAME = new NameType();
    //these will have only one token and no prefix
    public static final NameType FIELD_NAME = new NameType();
    
    protected NameType nameType;
    */
    
    protected Token[] ids;
    protected Token[] dots;
    
    /*
    protected NameNode(NameType type, Token[] identifiers) {
        nameType = type;
        ids = identifiers;
    }
    */
    
    public NameNode(Token[] identifiers, Token[] dots) {
        ids = identifiers;
        this.dots = dots;
    }
    
    public NameNode(Token identifier) {
        ids = new Token[] {identifier};
        dots = new Token[0];
    }
    
    public Token[] getIds() {
        return ids;
    }
    public Token[] getDots() {
        return dots;
    }
    
    /*
    public void setSuffix(NameNode suffix) {
        nodes.addLast(suffix);
    }
    
    public NameNode getSuffix() {
        return (NameNode)getChild(0);
    }
    
    public boolean hasSuffix() {
        return(getSuffix() != null);
    }
    
    public boolean hasNameType(NameType nameType) {
        return this.nameType == nameType;
    }
    */
    
    public Object accept(NodeVisitor v) {
        return v.visitNameNode(this);   
    }

    /**
     * Concatenates the segements of the named node with separator in between each segment.
     * If a NamedNode has children "java", "lang", and "String",
     * join(".") would return java.lang.String 
     * @param separator
     * @return
     */ 
    public String join(String separator) {
        StringBuffer buf = new StringBuffer();
        boolean first = true;
        
        //there should only be one prefix node
        //Iterator it = getChildren();
        //if(it.hasNext()) {
        //    buf.append(((NameNode)it.next()).join(separator));
        //    first = false;
        //}
        
        for(int ii = 0; ii < ids.length; ii++) {
            if(!first || (first = false)) buf.append(separator);
            buf.append(ids[ii].image);
        }
        return buf.toString();
    }
    
    public String join(char separator) {
        return join(String.valueOf(separator));
    }
    
    public String toName() {
        return join(ModelNameUtil.RULE_SEPARATOR_CHAR);
    }
    
    public String toString() {
        return "NamedNode " + join(".");
    }

    public String toString(String prefix) { 
        return prefix + toString(); 
    }
    
    public void dump(String pfx) {
        System.out.println(pfx + toString());
    }
    
    public Token getFirstToken() {
        if(ids.length > 0 ) {
            return ids[0];
        } else {
            return null;
        }
    }

    public Token getLastToken() {
        if(ids.length > 0) {
            return ids[ids.length - 1];
        } else {
            return null;
        }
    }
}
