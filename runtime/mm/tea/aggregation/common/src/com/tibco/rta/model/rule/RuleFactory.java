package com.tibco.rta.model.rule;

import com.tibco.rta.model.rule.InvokeConstraint.Constraint;
import com.tibco.rta.model.rule.impl.ActionDefImpl;
import com.tibco.rta.model.rule.impl.ActionFunctionDescriptorImpl;
import com.tibco.rta.model.rule.impl.InvokeConstraintImpl;
import com.tibco.rta.model.rule.impl.RuleDefImpl;
import com.tibco.rta.model.rule.impl.TimeBasedInvokeConstraintImpl;
import com.tibco.rta.model.rule.mutable.MutableActionDef;
import com.tibco.rta.model.rule.mutable.MutableInvokeConstraint;
import com.tibco.rta.model.rule.mutable.MutableRuleDef;
import com.tibco.rta.model.rule.mutable.MutableTimeBasedConstraint;

/**
 * A factory class to create rules and actions.
 * 
 *
 */
public class RuleFactory {
	
	public static RuleFactory INSTANCE = new RuleFactory();

	public MutableRuleDef newRuleDef(String name) {
		return new RuleDefImpl(name);
	}
	
	public MutableActionDef newSetActionDef(RuleDef ruleDef, ActionFunctionDescriptor descriptor, InvokeConstraint constraint) {
		ActionDefImpl actionDef = new ActionDefImpl(descriptor, constraint);
		actionDef.setRuleDef(ruleDef);		
		((RuleDefImpl)ruleDef).addSetActionDef(actionDef);
		return actionDef;
	}

	public MutableActionDef newClearActionDef(RuleDef ruleDef, ActionFunctionDescriptor descriptor, InvokeConstraint constraint) {
		ActionDefImpl actionDef = new ActionDefImpl(descriptor, constraint);
		actionDef.setRuleDef(ruleDef);		
		((RuleDefImpl)ruleDef).addClearActionDef(actionDef);
		return actionDef;
	}

	public MutableActionDef newSetActionDef(RuleDef ruleDef, ActionDef actionDef) {
		ActionDefImpl cloneActionDef = cloneActionDef(actionDef); 						
		cloneActionDef.setRuleDef(ruleDef);
		((RuleDefImpl)ruleDef).addSetActionDef(actionDef);
		return cloneActionDef;
	}


	public MutableActionDef newClearActionDef(RuleDef ruleDef, ActionDef actionDef) {
		ActionDefImpl cloneActionDef = cloneActionDef(actionDef); 						
		cloneActionDef.setRuleDef(ruleDef);
		((RuleDefImpl)ruleDef).addClearActionDef(actionDef);
		return cloneActionDef;
	}


	private InvokeConstraint cloneInvokeConstraint(InvokeConstraint invokeConstraint) {
		InvokeConstraint cloneConstraint = newInvokeConstraint(invokeConstraint.getConstraint());
		if (invokeConstraint instanceof TimeBasedConstraint) {
			TimeBasedConstraint tbc = (TimeBasedConstraint)invokeConstraint;
			((MutableTimeBasedConstraint)cloneConstraint).setTimeConstraint(tbc.getTimeConstraint());
			((MutableTimeBasedConstraint)cloneConstraint).setMaxInvocationCount(tbc.getMaxInvocationCount());
			((MutableTimeBasedConstraint)cloneConstraint).setInvocationFrequency(tbc.getInvocationFrequency());
			return cloneConstraint;
		} 
		return invokeConstraint;
	}
	
	private ActionFunctionDescriptor cloneDescriptor(ActionFunctionDescriptor desc) {
		return new ActionFunctionDescriptorImpl(desc.getName(), desc.getCategory(), desc.getImplClass(), null, desc.getDescription());
	}
	
	private ActionDefImpl cloneActionDef(ActionDef actionDef) {						
		return new ActionDefImpl(cloneDescriptor(actionDef.getActionFunctionDescriptor()), cloneInvokeConstraint(actionDef.getConstraint()));		
	}

	public MutableInvokeConstraint newInvokeConstraint(InvokeConstraint.Constraint constraint) {
		InvokeConstraintImpl invokeConstraint = null;
		if (constraint.equals(Constraint.ALWAYS)) {
			invokeConstraint = new InvokeConstraintImpl(constraint);
		} else if (constraint.equals(Constraint.ONCE_ONLY)) {
			invokeConstraint = new InvokeConstraintImpl(constraint);
		} else if (constraint.equals(Constraint.TIMED)) {
			invokeConstraint = new TimeBasedInvokeConstraintImpl(constraint);
		}
		return invokeConstraint;
	}
	
	public MutableTimeBasedConstraint newTimeBasedConstraint(TimeBasedConstraint.Constraint constraint, long invocationFrequency, long maxInvocationCount) {
		TimeBasedInvokeConstraintImpl timeBasedConstraint = 
				new TimeBasedInvokeConstraintImpl(constraint, invocationFrequency, maxInvocationCount);
		return timeBasedConstraint;
	}
	
	public MutableTimeBasedConstraint newTimeBasedConstraint(TimeBasedConstraint.Constraint constraint, long invocationFrequency) {
		TimeBasedInvokeConstraintImpl timeBasedConstraint = 
				new TimeBasedInvokeConstraintImpl(constraint, invocationFrequency);
		return timeBasedConstraint;
	}
	
}
