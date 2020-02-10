package com.tibco.rta.model.rule.impl;

import com.tibco.rta.RtaCommandListener;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.rule.mutable.MutableRuleDefEx;

public class RuleDefExImpl extends RuleDefImpl implements MutableRuleDefEx {

	private static final long serialVersionUID = 4530960492426796912L;
	private RtaCommandListener commandListener = null;
	
	public RuleDefExImpl() {
	}

	public RuleDefExImpl(String name) {
		super(name);
	}

	public RuleDefExImpl(String name, RtaSchema ownerSchema) {
		super(name, ownerSchema);
	}
	
	@Override
	public void setCommandListener(RtaCommandListener commandListener) {
		this.commandListener = commandListener;
	}

	@Override
	public RtaCommandListener getCommandListener() {
		return commandListener;
	}
}
