package com.tibco.cep.rt;

import org.eclipse.core.runtime.jobs.ISchedulingRule;

public class LoadAllIndexSchedulingRule implements ISchedulingRule {
	
	public LoadAllIndexSchedulingRule() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean contains(ISchedulingRule rule) {
		return rule == this;
	}

	@Override
	public boolean isConflicting(ISchedulingRule rule) {
		return rule instanceof LoadAllIndexSchedulingRule;
	}

}
