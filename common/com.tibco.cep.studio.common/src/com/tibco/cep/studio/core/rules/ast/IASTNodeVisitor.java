package com.tibco.cep.studio.core.rules.ast;

public interface IASTNodeVisitor<R extends RulesASTNode> {

	boolean preVisit(R node);
	
	boolean postVisit(R node);
	
	boolean visitChildren(R node);
	
	boolean visit(R node);
	
}
