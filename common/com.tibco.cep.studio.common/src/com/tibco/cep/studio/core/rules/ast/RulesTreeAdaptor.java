package com.tibco.cep.studio.core.rules.ast;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTreeAdaptor;

public class RulesTreeAdaptor extends CommonTreeAdaptor {

	@Override
	public Object create(Token payload) {
		RulesASTNode node = new RulesASTNode(payload);
		return node;
	}

	@Override
	public Object rulePostProcessing(Object root) {
		Object obj = super.rulePostProcessing(root);
		if (obj instanceof RulesASTNode && root instanceof RulesASTNode) {
			((RulesASTNode)obj).setBinding(((RulesASTNode) root).getBinding());
		}
		return obj;
	}

	@Override
	public void setTokenBoundaries(Object t, Token startToken, Token stopToken) {
		super.setTokenBoundaries(t, startToken, stopToken);
		if (t instanceof RulesASTNode && startToken instanceof CommonToken && stopToken instanceof CommonToken) {
			RulesASTNode node = (RulesASTNode) t;
			if (node.getLength() > 0) {
				// assume that the offset/length has already been set (?)
				return;
			}
			int startIndex = ((CommonToken)startToken).getStartIndex();
			node.setOffset(startIndex);
			int stopIndex = ((CommonToken)stopToken).getStopIndex() - node.getOffset() + 1;
			node.setLength(stopIndex);
		}
	}

}
