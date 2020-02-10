package com.tibco.cep.studio.core.grammar.concept;

import com.tibco.cep.studio.core.grammar.EntityCreatorASTVisitor;
import com.tibco.cep.studio.core.rules.ast.IASTNodeVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

public class DefaultConceptNodeASTVisitor implements IASTNodeVisitor<RulesASTNode> {

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
		case ConceptParser.CONCEPT_DEFINITION:
			return visitConceptDefinition(node);

		case ConceptParser.PROPERTY_DECLARATION:
			return visitPropertyDeclaration(node);
			
		case ConceptParser.PROPERTIES_BLOCK:
			return visitPropertiesBlock(node);
			
		case ConceptParser.PROPERTY_BLOCK:
			return visitPropertyBlock(node);
			
		case ConceptParser.ATTRIBUTES_BLOCK:
			return visitAttributesBlock(node);
			
		case ConceptParser.STATE_MACHINES_BLOCK:
			return visitStateMachinesBlock(node);
			
		case ConceptParser.METADATA_BLOCK:
			return visitMetadataBlock(node);
			
		case ConceptParser.AUTO_START_STATEMENT:
			return visitAutoStartStatement(node);
			
		case ConceptParser.CONTAINED_STATEMENT:
			return visitContainedStatement(node);
			
		case ConceptParser.POLICY_STATEMENT:
			return visitPolicyStatement(node);
			
		case ConceptParser.HISTORY_STATEMENT:
			return visitHistoryStatement(node);
			
		case ConceptParser.DOMAIN_STATEMENT:
			return visitDomainStatement(node);
			
		case ConceptParser.STATE_MACHINE_STATEMENT:
			return visitStateMachineStatement(node);
			
		case ConceptParser.META_DATA_PROPERTY_GROUP:
			return visitMetadataPropertyGroup(node);
			
		case ConceptParser.META_DATA_PROPERTY:
			return visitMetadataProperty(node);
			
		default:
			return visitDefault(node);
		}
	}

	private boolean visitDefault(RulesASTNode node) {
		return true;
	}

	public boolean visitMetadataProperty(RulesASTNode node) {
		return true;
	}

	public boolean visitMetadataPropertyGroup(RulesASTNode node) {
		return true;
	}

	public boolean visitStateMachineStatement(RulesASTNode node) {
		return true;
	}

	public boolean visitDomainStatement(RulesASTNode node) {
		return true;
	}

	public boolean visitHistoryStatement(RulesASTNode node) {
		return true;
	}

	public boolean visitPolicyStatement(RulesASTNode node) {
		return true;
	}

	public boolean visitContainedStatement(RulesASTNode node) {
		return true;
	}

	public boolean visitAutoStartStatement(RulesASTNode node) {
		return true;
	}

	public boolean visitMetadataBlock(RulesASTNode node) {
		return true;
	}

	public boolean visitStateMachinesBlock(RulesASTNode node) {
		return true;
	}

	public boolean visitAttributesBlock(RulesASTNode node) {
		return true;
	}

	public boolean visitPropertyBlock(RulesASTNode node) {
		return true;
	}

	public boolean visitPropertiesBlock(RulesASTNode node) {
		return true;
	}

	public boolean visitPropertyDeclaration(RulesASTNode node) {
		return true;
	}

	public boolean visitConceptDefinition(RulesASTNode node) {
		return true;
	}

}
