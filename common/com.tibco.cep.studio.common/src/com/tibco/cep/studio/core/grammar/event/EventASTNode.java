package com.tibco.cep.studio.core.grammar.event;

import org.antlr.runtime.Token;

import com.tibco.cep.studio.core.rules.ast.RulesASTNode;

public class EventASTNode extends RulesASTNode {

	public EventASTNode(Token payload) {
		super(payload);
	}

}
