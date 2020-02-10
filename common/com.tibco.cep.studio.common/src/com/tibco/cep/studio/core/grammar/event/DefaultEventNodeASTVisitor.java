package com.tibco.cep.studio.core.grammar.event;

import com.tibco.cep.studio.core.rules.ast.IASTNodeVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

public class DefaultEventNodeASTVisitor implements IASTNodeVisitor<RulesASTNode> {

	@Override
	public boolean preVisit(RulesASTNode node) {
		return true;
	}

	@Override
	public boolean postVisit(RulesASTNode node) {
		return true;
	}

	@Override
	public boolean visitChildren(RulesASTNode node) {
		return true;
	}

	@Override
	public boolean visit(RulesASTNode node) {
		if (node == null) {
			return false;
		}
		switch (node.getType()) {
		case EventParser.EVENT_DEFINITION:
			return visitEventDefinition(node);

		case EventParser.PROPERTY_DECLARATION:
			return visitPropertyDeclaration(node);
			
		case EventParser.DECLARATOR:
			return visitDeclaratorNode(node);
			
		case EventParser.THEN_BLOCK:
			return visitThenBlockNode(node);
			
		case EventParser.PROPERTIES_BLOCK:
			return visitPropertiesBlock(node);
			
		case EventParser.PROPERTY_BLOCK:
			return visitPropertyBlock(node);
			
		case EventParser.NAMESPACES_BLOCK:
			return visitNamespacesBlock(node);
			
		case EventParser.EXPIRY_ACTION_BLOCK:
			return visitExpiryActionBlock(node);
			
		case EventParser.PAYLOAD_BLOCK:
			return visitPayloadBlock(node);
			
		case EventParser.ATTRIBUTES_BLOCK:
			return visitAttributesBlock(node);
			
		case EventParser.DECLARE_BLOCK:
			return visitDeclareBlock(node);
			
		case EventParser.NAMESPACE_STATEMENT:
			return visitNamespaceStatement(node);
			
		case EventParser.RETRY_ON_EXCEPTION_STATEMENT:
			return visitRetryOnExceptionStatement(node);
			
		case EventParser.DEFAULT_DESTINATION_STATEMENT:
			return visitDefaultDestinationStatement(node);
			
		case EventParser.TTL_STATEMENT:
			return visitTTLStatement(node);
			
		case EventParser.PAYLOAD_STRING_STATEMENT:
			return visitPayloadStringStatement(node);
			
		case EventParser.DOMAIN_STATEMENT:
			return visitDomainStatement(node);
			
		default:
			return visitDefault(node);
		}
	}

	private boolean visitDefault(RulesASTNode node) {
		return true;
	}

	public boolean visitDomainStatement(RulesASTNode node) {
		return true;
	}

	public boolean visitPayloadStringStatement(RulesASTNode node) {
		return true;
	}

	public boolean visitTTLStatement(RulesASTNode node) {
		return true;
	}

	public boolean visitDefaultDestinationStatement(RulesASTNode node) {
		return true;
	}

	public boolean visitRetryOnExceptionStatement(RulesASTNode node) {
		return true;
	}

	public boolean visitNamespaceStatement(RulesASTNode node) {
		return true;
	}

	public boolean visitDeclareBlock(RulesASTNode node) {
		return true;
	}

	public boolean visitAttributesBlock(RulesASTNode node) {
		return true;
	}

	public boolean visitPropertyBlock(RulesASTNode node) {
		return true;
	}

	public boolean visitPayloadBlock(RulesASTNode node) {
		return true;
	}
	
	public boolean visitExpiryActionBlock(RulesASTNode node) {
		return true;
	}
	
	public boolean visitNamespacesBlock(RulesASTNode node) {
		return true;
	}
	
	public boolean visitPropertiesBlock(RulesASTNode node) {
		return true;
	}

	public boolean visitPropertyDeclaration(RulesASTNode node) {
		return true;
	}

	public boolean visitEventDefinition(RulesASTNode node) {
		return true;
	}

	public boolean visitDeclaratorNode(RulesASTNode node) {
		return true;
	}

	public boolean visitThenBlockNode(RulesASTNode node) {
		return true;
	}

}
