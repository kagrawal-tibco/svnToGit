package com.tibco.jxpath.compiler;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;

public class JXPathASTNode extends CommonTree {

    public JXPathASTNode(Token payload) {
    	super(payload);
    }

	public void accept(JXPathASTVisitor visitor) {
        visitor.preVisit(this);
        if (visitor.visit(this)) {
            doVisitChildren(visitor);
        }
        visitor.postVisit(this);
    }

    private void doVisitChildren(JXPathASTVisitor visitor) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            JXPathASTNode child = (JXPathASTNode) getChild(i);
            child.accept(visitor);
        }
    }

}
