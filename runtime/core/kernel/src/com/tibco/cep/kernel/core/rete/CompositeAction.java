package com.tibco.cep.kernel.core.rete;

import com.tibco.cep.kernel.model.rule.Action;

/* Used for startup functions, not for rules.  RuleFunctionsExecAction is in common, not kernel */
public interface CompositeAction extends Action
{
	int getComponentCount();
	void executeComponent(int index, Object[] objects);
}
