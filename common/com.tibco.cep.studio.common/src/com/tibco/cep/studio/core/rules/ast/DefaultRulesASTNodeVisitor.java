package com.tibco.cep.studio.core.rules.ast;

import com.tibco.cep.studio.core.rules.grammar.RulesParser;

/**
 * @author aathalye
 *
 */
public abstract class DefaultRulesASTNodeVisitor implements IRuleASTNodeVisitor<RulesASTNode> {

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
		case RulesParser.RULE:
			return visitRuleNode(node);
		case RulesParser.RULE_TEMPLATE_DECL:
			return visitRuleTemplateDeclarationNode(node);
		case RulesParser.RULE_TEMPLATE:
			return visitRuleTemplateNode(node);
		case RulesParser.RULE_FUNCTION:
			return visitRuleFunctionNode(node);
		case RulesParser.RULE_DECL:
			return visitRuleDeclarationNode(node);
		case RulesParser.NAME:
			return visitNameNode(node);
		case RulesParser.SIMPLE_NAME:
			return visitSimpleNameNode(node);
		case RulesParser.METHOD_CALL:
			return visitMethodCallNode(node);
		case RulesParser.SEMICOLON:
			return visitEmptyStatement(node);
		case RulesParser.QUALIFIED_NAME:
			return visitQualifiedNameNode(node);
		case RulesParser.RULE_BLOCK:
			return visitRuleBlockNode(node);
		case RulesParser.ATTRIBUTE_BLOCK:
			return visitAttributeBlockNode(node);
		case RulesParser.DECLARE_BLOCK:
			return visitDeclareBlockNode(node);
		case RulesParser.RULE_FUNC_DECL:
			return visitRuleFunctionDeclarationNode(node);
		case RulesParser.ANNOTATION:
			return visitAnnotationBlockNode(node);
		case RulesParser.SCOPE_BLOCK:
			return visitScopeBlockNode(node);
		case RulesParser.StringLiteral:
			return visitStringLiteralNode(node);
		case RulesParser.WHEN_BLOCK:
			return visitWhenBlockNode(node);
		case RulesParser.PREDICATES:
			return visitPredicatesNode(node);
		case RulesParser.THEN_BLOCK:
			return visitThenBlockNode(node);
		case RulesParser.BODY_BLOCK:
			return visitBodyBlockNode(node);
		case RulesParser.SCOPE_DECLARATOR:
			return visitScopeDeclaratorNode(node);
		case RulesParser.TYPE:
			return visitTypeNode(node);
		case RulesParser.RETURN_TYPE:
			return visitReturnTypeNode(node);
		case RulesParser.RETURN_STATEMENT:
			return visitReturnStatementNode(node);
		case RulesParser.MAPPING_BLOCK:
			return visitMappingBlockNode(node);
		case RulesParser.PRIORITY_STATEMENT:
			return visitPriorityStatementNode(node);
		case RulesParser.VALIDITY_STATEMENT:
			return visitValidityStatementNode(node);
		case RulesParser.ALIAS_STATEMENT:
			return visitAliasStatementNode(node);
		case RulesParser.FORWARD_CHAIN_STATEMENT:
			return visitForwardChainStatementNode(node);
		case RulesParser.BACKWARD_CHAIN_STATEMENT:
			return visitBackwardChainStatementNode(node);
		case RulesParser.LOCAL_VARIABLE_DECL:
			return visitLocalVariableDeclNode(node);
		case RulesParser.PRIMARY_ASSIGNMENT_EXPRESSION:
			return visitPrimaryAssignmentExpressionNode(node);
		case RulesParser.PRIMARY_EXPRESSION:
			return visitPrimaryExpressionNode(node);
		case RulesParser.DecimalLiteral:
		case RulesParser.HexLiteral:
			return visitDecimalLiteralNode(node);
		case RulesParser.VARIABLE_DECLARATOR:
			return visitVariableDeclaratorNode(node);
		case RulesParser.DECLARATOR:
			return visitDeclaratorNode(node);
		case RulesParser.TEMPLATE_DECLARATOR:
			return visitTemplateDeclaratorNode(node);
		case RulesParser.PLUS:
		case RulesParser.MINUS:
		case RulesParser.MULT:
		case RulesParser.DIVIDE:
		case RulesParser.MOD:
		case RulesParser.LT:
		case RulesParser.GT:
		case RulesParser.LE:
		case RulesParser.GE:
		case RulesParser.NE:
		case RulesParser.EQ:
		case RulesParser.AND:
		case RulesParser.OR:
		case RulesParser.INSTANCE_OF:
		case RulesParser.POUND:
		case RulesParser.BINARY_RELATION_NODE:
			return visitBinaryRelationNode(node);
		case RulesParser.UNARY_EXPRESSION_NODE:
			return visitUnaryExpressionNode(node);
		case RulesParser.BLOCK:
			return visitBlockNode(node);
		case RulesParser.RANK_STATEMENT:
			return visitRankStatementNode(node);
		case RulesParser.TRY_STATEMENT:
			return visitTryStatementNode(node);
		case RulesParser.CATCH_RULE:
			return visitCatchRuleNode(node);
		case RulesParser.FINALLY_RULE:
			return visitFinallyRuleNode(node);
		case RulesParser.ACTION_CONTEXT_BLOCK:
			return visitActionContextNode(node);
		case RulesParser.ACTION_CONTEXT_STATEMENT:
			return visitActionContextStatementNode(node);
		case RulesParser.BINDINGS_BLOCK:
			return visitBindingsBlock(node);
		case RulesParser.BINDING_STATEMENT:
			return visitBindingStatementNode(node);
		case RulesParser.BINDING_VIEW_STATEMENT:
			return visitBindingViewStatement(node);
		case RulesParser.DOMAIN_MODEL_STATEMENT:
			return visitDomainStatementNode(node);
		case RulesParser.BINDINGS_VIEWS_BLOCK:
			return visitViewsBlock(node);
		case RulesParser.IF_RULE:
			return visitIfRuleBlock(node);
		case RulesParser.FOR_LOOP:
			return visitForLoopBlock(node);
		case RulesParser.DISPLAY_BLOCK:
			return visitDisplayBlock(node);
		case RulesParser.DISPLAY_PROPERTY:
			return visitDisplayProperty(node);
		default:
			return visitDefault(node);
		}
	}

	public boolean visitForLoopBlock(RulesASTNode node) {
		return true;
	}

	public boolean visitIfRuleBlock(RulesASTNode node) {
		return true;
	}

	public boolean visitDeclaratorNode(RulesASTNode node) {
		return true;
	}

	public boolean visitTemplateDeclaratorNode(RulesASTNode node) {
		return true;
	}
	
	public boolean visitPrimaryAssignmentExpressionNode(RulesASTNode node) {
		return true;
	}
	
	public boolean visitPrimaryExpressionNode(RulesASTNode node) {
		return true;
	}
	
	public boolean visitDecimalLiteralNode(RulesASTNode node) {
		return true;
	}
	
	public boolean visitVariableDeclaratorNode(RulesASTNode node) {
		return true;
	}
	
	public boolean visitTypeNode(RulesASTNode node) {
		return true;
	}

	public boolean visitMappingBlockNode(RulesASTNode node) {
		return true;
	}
	
	public boolean visitReturnTypeNode(RulesASTNode node) {
		return true;
	}
	
	public boolean visitReturnStatementNode(RulesASTNode node) {
		return true;
	}
	
	public boolean visitRankStatementNode(RulesASTNode node) {
		return true;
	}
	
	public boolean visitTryStatementNode(RulesASTNode node) {
		return true;
	}
	
	public boolean visitCatchRuleNode(RulesASTNode node) {
		return true;
	}
	
	public boolean visitFinallyRuleNode(RulesASTNode node) {
		return true;
	}
	
	public boolean visitScopeDeclaratorNode(RulesASTNode node) {
		return true;
	}

	public boolean visitBodyBlockNode(RulesASTNode node) {
		return true;
	}

	public boolean visitBlockNode(RulesASTNode node) {
		return true;
	}
	
	public boolean visitThenBlockNode(RulesASTNode node) {
		return true;
	}

	public boolean visitPredicatesNode(RulesASTNode node) {
		return true;
	}

	public boolean visitWhenBlockNode(RulesASTNode node) {
		return true;
	}

	public boolean visitScopeBlockNode(RulesASTNode node) {
		return true;
	}

	public boolean visitStringLiteralNode(RulesASTNode node) {
		return true;
	}
	
	public boolean visitRuleFunctionDeclarationNode(RulesASTNode node) {
		return true;
	}

	public boolean visitDeclareBlockNode(RulesASTNode node) {
		return true;
	}

	public boolean visitAttributeBlockNode(RulesASTNode node) {
		return true;
	}

	public boolean visitRuleBlockNode(RulesASTNode node) {
		return true;
	}

	public boolean visitNameNode(RulesASTNode node) {
		return true;
	}

	public boolean visitSimpleNameNode(RulesASTNode node) {
		return true;
	}
	
	public boolean visitMethodCallNode(RulesASTNode node) {
		return true;
	}
	
	public boolean visitEmptyStatement(RulesASTNode node) {
		return true;
	}
	
	public boolean visitQualifiedNameNode(RulesASTNode node) {
		return true;
	}
	
	public boolean visitRuleDeclarationNode(RulesASTNode node) {
		return true;
	}

	public boolean visitRuleTemplateDeclarationNode(RulesASTNode node) {
		return true;
	}
	
	public boolean visitRuleFunctionNode(RulesASTNode node) {
		return true;
	}

	public boolean visitRuleTemplateNode(RulesASTNode node) {
		return true;
	}
	
	public boolean visitRuleNode(RulesASTNode node) {
		return true;
	}
	
	public boolean visitAnnotationBlockNode(RulesASTNode node) {
		return true;
	}

    @Override
	public boolean visitPriorityStatementNode(RulesASTNode node) {
		return true;
	}

	@Override
	public boolean visitValidityStatementNode(RulesASTNode node) {
		return true;
	}

	@Override
	public boolean visitAliasStatementNode(RulesASTNode node) {
		return true;
	}
	
	@Override
	public boolean visitForwardChainStatementNode(RulesASTNode node) {
		return true;
	}
	
	@Override
	public boolean visitBackwardChainStatementNode(RulesASTNode node) {
		return true;
	}
	
	@Override
	public boolean visitLocalVariableDeclNode(RulesASTNode node) {
		return true;
	}

	@Override
	public boolean visitBinaryRelationNode(RulesASTNode node) {
		return true;
	}
	
	@Override
	public boolean visitRelationExpressionNode(RulesASTNode node) {
		return true;
	}
	
	@Override
	public boolean visitAdditiveExpressionNode(RulesASTNode node) {
		return true;
	}
	
	@Override
	public boolean visitMultiplicativeExpressionNode(RulesASTNode node) {
		return true;
	}
	
	@Override
	public boolean visitUnaryExpressionNode(RulesASTNode node) {
		return true;
	}
	
	@Override
	public boolean visitActionContextNode(RulesASTNode node) {
		return true;
	}

	@Override
	public boolean visitActionContextStatementNode(RulesASTNode node) {
		return true;
	}

	@Override
	public boolean visitBindingsBlock(RulesASTNode node) {
		return true;
	}

	@Override
	public boolean visitBindingStatementNode(RulesASTNode node) {
		return true;
	}

	@Override
	public boolean visitBindingViewStatement(RulesASTNode node) {
		return true;
	}
/*
	@Override
	public boolean visitNullConstraintNode(RulesASTNode node) {
		return true;
	}

	@Override
	public boolean visitRangeConstraintNode(RulesASTNode node) {
		return true;
	}
*/
	
	@Override
	public boolean visitViewsBlock(RulesASTNode node) {
		return true;
	}

	@Override
	public boolean visitDomainStatementNode(RulesASTNode node) {
		return true;
	}

	protected boolean visitDefault(RulesASTNode node) {
//		for (int i = 0; i < node.getChildCount(); i++) {
//			visit((RulesASTNode)node.getChild(i));
//		}
		return true;
	}

	@Override
	public boolean visitDisplayBlock(RulesASTNode node) {
		return true;
	}

	@Override
	public boolean visitDisplayProperty(RulesASTNode node) {
		return true;
	}
	
	
}
