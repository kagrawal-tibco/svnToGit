package com.tibco.be.parser.tree;

import java.util.Iterator;

import com.tibco.be.parser.Token;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Jan 18, 2005
 * Time: 5:15:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductionNodeListNode extends BaseNode{
    public static final ListType BLOCK_TYPE = ListType.BLOCK_TYPE;
    public static final ListType STATEMENT_EXPRESSION_LIST_TYPE = ListType.STATEMENT_EXPRESSION_LIST_TYPE;
    public static final ListType ARRAY_LITERAL_TYPE = ListType.ARRAY_LITERAL_TYPE;
    public static final ListType RANGE_TYPE = ListType.RANGE_TYPE;
    public static final ListType SET_MEMBERSHIP_TYPE = ListType.SET_MEMBERSHIP_TYPE;
    
    //if a block or array literal, these are start and end curly braces
    //for statement expression lists these are the first and last token of the list
    protected Token start, end;
    protected ListType listType;
    
    public ProductionNodeListNode(Token start, Token end, ListType type) {
        this.start = start;
        this.end = end;
        listType = type;
    }
    
    public String toString(String prefix) {
        StringBuffer buf = new StringBuffer();
        
        if(listType == BLOCK_TYPE) buf.append(prefix + "{\n");
        else if(listType == ARRAY_LITERAL_TYPE) buf.append(prefix + "{ ");
        for(Iterator it = getChildren(); it.hasNext();) {
            buf.append(prefix + ((Node)it.next()).toString());
            if(listType == BLOCK_TYPE) buf.append("\n");
            else if((listType == STATEMENT_EXPRESSION_LIST_TYPE || listType == ARRAY_LITERAL_TYPE)&& it.hasNext()) buf.append(", ");
        }
        if(listType == BLOCK_TYPE) buf.append(prefix + "}\n");
        else if(listType == ARRAY_LITERAL_TYPE) buf.append(" }");
        return buf.toString();
    }
    
    public String toString() {
        return toString("");
    }
    
    public void dump(String prefix) {
        System.out.println(toString(prefix));
    }
    
    public Object accept(NodeVisitor v) {
        return v.visitProductionNodeListNode(this);
    }

    public Token getFirstToken() { return start; }
    public Token getLastToken() { return end; }
    
    public ListType getListType() { return listType; };
    
    protected static enum ListType {
        BLOCK_TYPE("block"),
        STATEMENT_EXPRESSION_LIST_TYPE("statement expression list"),
        ARRAY_LITERAL_TYPE("array literal"),
        RANGE_TYPE("range"),
        SET_MEMBERSHIP_TYPE("set membership");
        
        final String name;
        private ListType(String name) {
            this.name = name;
        }
        
        public String toString() {
            return name;
        }
        
        public boolean isDomainSpec() {
            return this == RANGE_TYPE || this == SET_MEMBERSHIP_TYPE;
        }
    }
}
