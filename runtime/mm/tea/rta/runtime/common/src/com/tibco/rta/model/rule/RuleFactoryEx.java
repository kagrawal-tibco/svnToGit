package com.tibco.rta.model.rule;

import com.tibco.rta.model.rule.impl.RuleDefExImpl;
import com.tibco.rta.model.rule.mutable.MutableRuleDef;

public class RuleFactoryEx extends RuleFactory {

	public static RuleFactory INSTANCE = new RuleFactoryEx();
	
	@Override
	public MutableRuleDef newRuleDef(String name) {
		return new RuleDefExImpl(name);
	}

}
