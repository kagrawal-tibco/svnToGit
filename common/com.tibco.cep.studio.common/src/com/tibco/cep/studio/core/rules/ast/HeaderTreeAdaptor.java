package com.tibco.cep.studio.core.rules.ast;

import org.antlr.runtime.Token;

public class HeaderTreeAdaptor extends RulesTreeAdaptor {

	@Override
	public Object create(Token payload) {
		HeaderASTNode node = new HeaderASTNode(payload);
		return node;
	}

}
