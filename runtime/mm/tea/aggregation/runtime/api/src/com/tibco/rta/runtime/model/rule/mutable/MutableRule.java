package com.tibco.rta.runtime.model.rule.mutable;

import com.tibco.rta.runtime.model.MetricNodeEvent;
import com.tibco.rta.runtime.model.rule.Action;
import com.tibco.rta.runtime.model.rule.Rule;

public interface MutableRule extends Rule {
    /**
     * Add a set action.
     * @param action the set action to add.
     */
	void addSetAction(Action action);

	/**
	 * Add a clear action.
	 * @param action the clear action to add.
	 */
	void addClearAction(Action action);
	
	/**
	 * Evaluate the rule.
	 * 
	 * @param nodeEvent the node event to evaluate.
	 * @throws Exception
	 */
	void eval(MetricNodeEvent nodeEvent) throws Exception;
	
}
