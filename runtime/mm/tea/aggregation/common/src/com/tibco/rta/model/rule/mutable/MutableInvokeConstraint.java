package com.tibco.rta.model.rule.mutable;

import com.tibco.rta.model.rule.InvokeConstraint;

public interface MutableInvokeConstraint extends InvokeConstraint {
	
	void setConstraint(InvokeConstraint.Constraint constraint);

}
