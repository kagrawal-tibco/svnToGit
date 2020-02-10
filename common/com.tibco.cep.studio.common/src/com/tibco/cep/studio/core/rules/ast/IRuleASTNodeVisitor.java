/**
 * 
 */
package com.tibco.cep.studio.core.rules.ast;

/**
 * @author aathalye
 *
 */
public interface IRuleASTNodeVisitor<R extends RulesASTNode> extends IASTNodeVisitor<R> {
	
	boolean visitDeclaratorNode(R node);

	boolean visitTypeNode(R node);

	boolean visitScopeDeclaratorNode(R node);

	boolean visitBodyBlockNode(R node);

	boolean visitBlockNode(R node);

	boolean visitThenBlockNode(R node);

	boolean visitPredicatesNode(R node);

	boolean visitWhenBlockNode(R node);

	boolean visitScopeBlockNode(R node);
	
	boolean visitStringLiteralNode(R node);

	boolean visitRuleFunctionDeclarationNode(R node);

	boolean visitDeclareBlockNode(R node);
	
	boolean visitAnnotationBlockNode(R node);

	boolean visitAttributeBlockNode(R node);

	boolean visitRuleBlockNode(R node);

	boolean visitNameNode(R node);
	
	boolean visitMethodCallNode(R node);

	boolean visitEmptyStatement(R node);
	
	boolean visitSimpleNameNode(R node);
	
	boolean visitQualifiedNameNode(R node);

	boolean visitRuleDeclarationNode(R node);

	boolean visitRuleTemplateNode(R node);
	
	boolean visitRuleFunctionNode(R node);

	boolean visitRuleNode(R node);
	
	boolean visitReturnTypeNode(R node);
	
	boolean visitMappingBlockNode(R node);
	
	boolean visitValidityStatementNode(R node);
	
	boolean visitAliasStatementNode(R node);
	
	boolean visitPriorityStatementNode(R node);
	
	boolean visitForwardChainStatementNode(R node);
	
	boolean visitBackwardChainStatementNode(R node);
	
	boolean visitLocalVariableDeclNode(R node);
	
	boolean visitBinaryRelationNode(R node);
	
	boolean visitRelationExpressionNode(R node);
	
	boolean visitAdditiveExpressionNode(R node);
	
	boolean visitMultiplicativeExpressionNode(R node);
	
	boolean visitUnaryExpressionNode(R node);
	
	boolean visitDecimalLiteralNode(R node);

	boolean visitPrimaryAssignmentExpressionNode(R node);
	
	boolean visitPrimaryExpressionNode(R node);
	
	boolean visitVariableDeclaratorNode(R node);
	
	boolean visitReturnStatementNode(R node);
	
	boolean visitRankStatementNode(R node);
	
	boolean visitTryStatementNode(R node);
	
	boolean visitCatchRuleNode(R node);
	
	boolean visitFinallyRuleNode(R node);
	
	boolean visitTemplateDeclaratorNode(R node);
	
	boolean visitActionContextNode(R node);
	
	boolean visitActionContextStatementNode(R node);
	
	boolean visitViewsBlock(R node);
	
	boolean visitBindingViewStatement(R node);
	
	boolean visitBindingsBlock(R node);
	
	boolean visitBindingStatementNode(R node);
	/*
	boolean visitRangeConstraintNode(R node);
	
	boolean visitNullConstraintNode(R node);
	*/
	boolean visitDomainStatementNode(R node);
	
	boolean visitIfRuleBlock(R node);
	
	boolean visitForLoopBlock(R node);
	
	boolean visitDisplayBlock(R node);
	
	boolean visitDisplayProperty(R node);
	
}
