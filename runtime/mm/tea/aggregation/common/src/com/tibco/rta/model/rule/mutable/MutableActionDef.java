package com.tibco.rta.model.rule.mutable;

import com.tibco.rta.model.rule.ActionDef;
import com.tibco.rta.model.rule.ActionFunctionDescriptor;
import com.tibco.rta.model.rule.InvokeConstraint;

public interface MutableActionDef extends ActionDef {
	
	void setConstraint(InvokeConstraint constraint);
	
	void setActionFunctionDescriptor (ActionFunctionDescriptor descriptor);
	
	void setName(String name);
	
	void setAlertLevel(String alertLevel);	

}
