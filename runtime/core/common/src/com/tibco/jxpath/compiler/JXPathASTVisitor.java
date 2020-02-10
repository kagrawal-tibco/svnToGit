package com.tibco.jxpath.compiler;


public interface JXPathASTVisitor {

	boolean preVisit(JXPathASTNode node);
	
	boolean postVisit(JXPathASTNode node);
	
	boolean visitChildren(JXPathASTNode node);
	
	boolean visit(JXPathASTNode node);

	boolean visitFunctionCall(JXPathASTNode node);
	
	boolean visitFilterExprWithPredicate(JXPathASTNode node);
	
	boolean visitQualifiedName(JXPathASTNode node);
	
	boolean visitVarRef(JXPathASTNode node);
	
	boolean visitSimpleAxisStep(JXPathASTNode node);
	
	boolean visitAbbrStep(JXPathASTNode node);
	
	boolean visitNamedAxisStep(JXPathASTNode node);
	
	boolean visitPathExpr(JXPathASTNode node);
	
	boolean visitOrExpr(JXPathASTNode node);
	
	boolean visitAndExpr(JXPathASTNode node);
	
	boolean visitEqualityExpr(JXPathASTNode node);
	
	boolean visitAdditiveExpr(JXPathASTNode node);

	boolean visitRelationalExpr(JXPathASTNode node);
	
	boolean visitMultiplicativeExpr(JXPathASTNode node);
	
	boolean visitUnaryExpr(JXPathASTNode node);
	
	boolean visitConstant(JXPathASTNode node);
	
}
