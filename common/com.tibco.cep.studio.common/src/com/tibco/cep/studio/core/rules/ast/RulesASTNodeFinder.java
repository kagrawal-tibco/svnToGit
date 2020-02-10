package com.tibco.cep.studio.core.rules.ast;

import com.tibco.cep.studio.core.rules.grammar.RulesParser;

/**
 * AST visitor to find the node at a given offset.  Useful in an editor
 * to determine the current selection or the node under the cursor for
 * text hover, open declaration, etc.
 * 
 * @author rhollom
 *
 */
public class RulesASTNodeFinder extends DefaultRulesASTNodeVisitor {

    private int fOffset;
    protected RulesASTNode fFoundNode;

    public RulesASTNodeFinder(int offset) {
        this.fOffset = offset;
    }

    public RulesASTNodeFinder() {
        this.fOffset = -1;
    }
    
    @Override
    public boolean visit(RulesASTNode node) {
        if (node.getTokenStartIndex() == -1) {
            return true;
        }
        if (node.getType() == RulesParser.PLUS) {
        	return visitUnaryExpressionNode(node);
        }
        int offsetStart = node.getOffset();
        if (offsetStart > fOffset) {
        	return false;
        }
        int offsetEnd = offsetStart + node.getLength();
        if (offsetStart <= fOffset && offsetEnd >= fOffset) {
            fFoundNode = node;
            return true;
        }
        return false;
    }

    @Override
    public boolean visitUnaryExpressionNode(RulesASTNode node) {
    	if (node.getChildCount() == 2) {
    		RulesASTNode child1 = node.getChild(0);
    		RulesASTNode child2 = node.getChild(1);
    		if (visit(child2)) {
    			return true;
    		}
    		visit(child1);
    	}
    	
    	return true;
    }
    
    public RulesASTNode getFoundNode() {
        return fFoundNode;
    }

    public void setOffset(int offset) {
        fOffset = offset;
    }
    
}
