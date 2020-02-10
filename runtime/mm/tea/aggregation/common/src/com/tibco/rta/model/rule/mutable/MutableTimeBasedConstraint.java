package com.tibco.rta.model.rule.mutable;

import com.tibco.rta.model.rule.TimeBasedConstraint;

public interface MutableTimeBasedConstraint extends TimeBasedConstraint {
	
	void setInvocationFrequency(long frequency);
	
	void setMaxInvocationCount(long count);
	
	void setTimeConstraint(TimeBasedConstraint.Constraint constraint);
	
}
