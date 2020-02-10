package com.tibco.cep.studio.core.grammar.concept;

import org.antlr.runtime.Token;

import com.tibco.cep.studio.core.rules.ast.RulesTreeAdaptor;

public class ConceptTreeAdaptor extends RulesTreeAdaptor {

	@Override
	public Object create(Token payload) {
		ConceptASTNode node = new ConceptASTNode(payload);
		return node;
	}

}
