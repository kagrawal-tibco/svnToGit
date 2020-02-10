package com.tibco.be.parser.tree;

import com.tibco.be.parser.Token;

public class ProductionNode extends BaseNode {
    protected Token token;
    
    //this can be used to override the first and last token
    //index 0 is the first (may be null) and index 1 is the last(may be null);
    protected Token[] firstLastOverride = null;
    
    public ProductionNode(Token token, int reln) {
        this.token = token;
        relation = reln;
    }

    //public ProductionNode(RuleGrammar p, Token token, int relation) {
    //    this(token, relation);
    //    parser = p;
    //}

    public Token getToken() {
        return token;
    }

    /* You can override these two methods in subclasses of ProductionNode to
    customize the way the node appears when the tree is dumped.  If
    your output uses more than one line you should override
    toString(String), otherwise overriding toString() is probably all
    you need to do. */

    public String toString() { return "ProductionNode(" + relation + ") " + type + " " + token.image; }
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
    
    public void setToken(Token t) {
        token = t;
    }

    public static ProductionNode newNode(ParseSyntaxTree parseTree, Token t, int relation) {
        ProductionNode node = new ProductionNode(t, relation);
        if (parseTree != null)
            parseTree.pushNode(node);
        return node;
    }

    public Object accept(NodeVisitor v) {
        return v.visitProductionNode(this);   
    }

    public Token getFirstToken() {
        if(firstLastOverride != null && firstLastOverride[0] != null) {
            return firstLastOverride[0];
        } else {
            switch(getRelationKind()) {
                case Node.NODE_BINARY_RELATION:
                    //find the begin token of the LHS
                    Node firstChild = getChild(0);
                    if(firstChild != null) {
                        return firstChild.getFirstToken();
                    } else {
                        return getToken();
                    }
                case Node.NODE_NULL_RELATION:
                case Node.NODE_UNARY_RELATION:
                default:
                    return getToken();
            }
        }
    }

    public Token getLastToken() {
        if(firstLastOverride != null && firstLastOverride[1] != null) {
            return firstLastOverride[1];
        } else {
            if(getRelationKind() > 0 && nodes.size() > 0) {
                Node lastChild = getLastChild();
                if(lastChild != null) {
                    Token lastChildToken = lastChild.getLastToken();
                    if(lastChildToken.endLine > getToken().endLine || (lastChildToken.endLine == getToken().endLine && lastChildToken.endColumn > getToken().endColumn)) {
                        return lastChildToken;
                    }
                }
            }
            return getToken();
        }
    }
    
    public void overrideFirstToken(Token t) {
        if(firstLastOverride == null) firstLastOverride = new Token[]{null,null};
        firstLastOverride[0] = t;
    }

    public void overrideLastToken(Token t) {
        if(firstLastOverride == null) firstLastOverride = new Token[]{null,null};
        firstLastOverride[1] = t;
    }
}