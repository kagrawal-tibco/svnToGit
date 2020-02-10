package com.tibco.be.parser.tree;

import com.tibco.be.parser.Token;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Mar 11, 2005
 * Time: 4:32:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class RootNode extends BaseNode {
    protected SourceType sourceType;
    protected Token start, end;
    protected String userContext;
    
    
    public RootNode(SourceType sourceType, Token start, Token end) {
        this.sourceType = sourceType;
        this.relation = Node.NODE_UNARY_RELATION;
        this.start = start;
        this.end = end;
    }
    
    public SourceType getSourceType() {
        return sourceType;
    }
    public void setSourceType(SourceType srcType) {
        sourceType = srcType;
    }
    
    public String toString() { return "RootNode " + sourceType.name(); }
    public String toString(String prefix) { return prefix + toString(); }

    /* Override this method if you want to customize how the node dumps
    out its children. */

    public void dump(String prefix) {
        System.out.println(toString(prefix));
        if (nodes != null) {
            for (int i = 0; i < nodes.size(); ++i) {
                Node n = (Node)nodes.get(i);
                if (n != null) {
                    n.dump(prefix + " ");
                }
            }
        }
    }

    public Object accept(NodeVisitor v) {
        return v.visitRootNode(this);
    }

    public String getSourceText() {
        if(start == null || end == null) return "";
        StringBuffer text = new StringBuffer();
        //testing for end.next is safe because if
        //end is the final token, then end.next will be
        //null and the loop will still end normally
        for(Token t = start; t != end.next; t = t.next) {
            //don't append whitespace or comments before the start of the root node.
            if(t != start) {
				int offset = text.length();
                for(Token s = t.specialToken; s != null; s = s.specialToken) {
                    text.insert(offset, s.image);
                }
            }
            text.append(t.image);
        }
        return text.toString();
    }

    public Token getFirstToken() {
        return start;
    }
    public Token getLastToken() {
        return end;
    }
    public void setLastToken(Token end) {
        this.end = end;
    }

    public String getUserContext() {
        return userContext;
    }

    public void setUserContext(String context) {
        this.userContext = context;
    }
}
