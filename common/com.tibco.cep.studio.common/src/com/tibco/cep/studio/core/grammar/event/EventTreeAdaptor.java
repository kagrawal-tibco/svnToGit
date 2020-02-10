package com.tibco.cep.studio.core.grammar.event;

import org.antlr.runtime.Token;

import com.tibco.cep.studio.core.rules.ast.RulesTreeAdaptor;

public class EventTreeAdaptor extends RulesTreeAdaptor {

	@Override
	public Object create(Token payload) {
		EventASTNode node = new EventASTNode(payload);
		return node;
	}

}
