package com.tibco.cep.studio.core.rules.ast;

import com.tibco.cep.studio.core.rules.grammar.HeaderParser;

/**
 * @author rhollom
 *
 */
public abstract class DefaultHeaderASTNodeVisitor implements IHeaderASTNodeVisitor<RulesASTNode> {

	public boolean preVisit(RulesASTNode o){
        return true;
    }
    
    public boolean postVisit(RulesASTNode o){
        return true;
    }

    public boolean visitChildren(RulesASTNode o) {
        for (int i = 0; i < o.getChildCount(); i++) {
            visit((RulesASTNode) o.getChild(i));
        }
        return false;
    }

	public boolean visit(RulesASTNode node) {
		if (node == null) {
			return false;
		}
		switch (node.getType()) {
		case HeaderParser.HEADER_BLOCK:
			return visitHeaderBlockNode(node);
		case HeaderParser.AUTHOR_STATEMENT:
			return visitAuthorStatementNode(node);
		case HeaderParser.DESCRIPTION_STATEMENT:
			return visitDescriptionStatementNode(node);
		default:
			return visitDefault(node);
		}
	}

    @Override
	public boolean visitDescriptionStatementNode(RulesASTNode node) {
		return true;
	}

    @Override
	public boolean visitAuthorStatementNode(RulesASTNode node) {
		return true;
	}

    @Override
	public boolean visitHeaderBlockNode(RulesASTNode node) {
		return true;
	}
	
	protected boolean visitDefault(RulesASTNode node) {
		for (int i = 0; i < node.getChildCount(); i++) {
			visit((RulesASTNode)node.getChild(i));
		}
		return false;
	}
}
