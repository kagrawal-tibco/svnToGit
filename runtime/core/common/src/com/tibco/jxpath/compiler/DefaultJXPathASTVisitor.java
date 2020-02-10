package com.tibco.jxpath.compiler;


public class DefaultJXPathASTVisitor implements JXPathASTVisitor {

	@Override
	public boolean preVisit(JXPathASTNode node) {
        return true;
	}

	@Override
	public boolean postVisit(JXPathASTNode node) {
        return true;
	}

	@Override
	public boolean visitChildren(JXPathASTNode node) {
        for (int i = 0; i < node.getChildCount(); i++) {
            visit((JXPathASTNode) node.getChild(i));
        }
        return false;
    }

	protected boolean visitDefault(JXPathASTNode node) {
		return true;
	}

	@Override
	public boolean visit(JXPathASTNode node) {
		if (node == null) {
			return false;
		}
		if (node instanceof JXPathASTErrorNode) {
			System.err.println("Found error node: ");
			((JXPathASTErrorNode) node).getE().printStackTrace();
		}
		switch (node.getType()) {
		case JXPathParser.AND:
			return visitAndExpr(node);
			
		case JXPathParser.OR:
			return visitOrExpr(node);
			
		case JXPathParser.EQUAL:
		case JXPathParser.NOT_EQUAL:
			return visitEqualityExpr(node);
			
		case JXPathParser.LESS_THAN:
		case JXPathParser.LESS_THAN_EQUALS:
		case JXPathParser.GREATER_THAN:
		case JXPathParser.GREATER_THAN_EQUALS:
			return visitRelationalExpr(node);
			
		case JXPathParser.STAR:
		case JXPathParser.DIV:
		case JXPathParser.MOD:
			return visitMultiplicativeExpr(node);
			
		case JXPathParser.PLUS:
		case JXPathParser.MINUS:
			return visitAdditiveExpr(node);
			
		case JXPathParser.VAR_REF:
			return visitVarRef(node);
			
		case JXPathParser.FUNCTION_CALL:
			return visitFunctionCall(node);
			
		case JXPathParser.FILTER_PREDICATE_EXPR:
			return visitFilterExprWithPredicate(node);
			
		case JXPathParser.QUALIFIED_NAME:
			return visitQualifiedName(node);
			
		case JXPathParser.SIMPLE_AXIS_STEP:
			return visitSimpleAxisStep(node);
			
		case JXPathParser.ABBREVIATED_STEP:
			return visitAbbrStep(node);
			
		case JXPathParser.NAMED_AXIS_STEP:
			return visitNamedAxisStep(node);
			
		case JXPathParser.PATH_EXPR:
			return visitPathExpr(node);
			
		case JXPathParser.Number:
		case JXPathParser.Literal:
			return visitConstant(node);
			
		default:
			return visitDefault(node);
		}
	}

	@Override
	public boolean visitFunctionCall(JXPathASTNode node) {
		return true;
	}

	@Override
	public boolean visitFilterExprWithPredicate(JXPathASTNode node) {
		return true;
	}

	@Override
	public boolean visitQualifiedName(JXPathASTNode node) {
		return true;
	}

	@Override
	public boolean visitVarRef(JXPathASTNode node) {
		return true;
	}

	@Override
	public boolean visitSimpleAxisStep(JXPathASTNode node) {
		return true;
	}

	@Override
	public boolean visitAbbrStep(JXPathASTNode node) {
		return true;
	}

	@Override
	public boolean visitNamedAxisStep(JXPathASTNode node) {
		return true;
	}

	@Override
	public boolean visitPathExpr(JXPathASTNode node) {
		return true;
	}

	@Override
	public boolean visitOrExpr(JXPathASTNode node) {
		return true;
	}

	@Override
	public boolean visitAndExpr(JXPathASTNode node) {
		return true;
	}

	@Override
	public boolean visitEqualityExpr(JXPathASTNode node) {
		return true;
	}

	@Override
	public boolean visitRelationalExpr(JXPathASTNode node) {
		return true;
	}

	@Override
	public boolean visitMultiplicativeExpr(JXPathASTNode node) {
		return true;
	}

	@Override
	public boolean visitUnaryExpr(JXPathASTNode node) {
		return true;
	}

	@Override
	public boolean visitAdditiveExpr(JXPathASTNode node) {
		return true;
	}

	@Override
	public boolean visitConstant(JXPathASTNode node) {
		return true;
	}
	
}
