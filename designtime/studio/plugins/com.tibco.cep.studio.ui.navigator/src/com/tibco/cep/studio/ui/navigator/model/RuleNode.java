package com.tibco.cep.studio.ui.navigator.model;

import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.studio.ui.StudioNavigatorNode;

public class RuleNode extends StudioNavigatorNode {

	public RuleNode(Rule rule, boolean isSharedElement) {
		super(rule, isSharedElement);
	}
}