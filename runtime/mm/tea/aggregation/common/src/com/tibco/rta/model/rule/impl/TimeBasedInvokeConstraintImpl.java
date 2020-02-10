package com.tibco.rta.model.rule.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.tibco.rta.model.rule.InvokeConstraint;
import com.tibco.rta.model.rule.TimeBasedConstraint;
import com.tibco.rta.model.rule.mutable.MutableTimeBasedConstraint;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.*;

@XmlAccessorType(XmlAccessType.NONE)
public class TimeBasedInvokeConstraintImpl extends InvokeConstraintImpl implements MutableTimeBasedConstraint {

	protected long invocationFrequency;
	
	protected long maxInvocationCount = Long.MAX_VALUE;
	
	protected TimeBasedConstraint.Constraint timeConstraint;
	
	public TimeBasedInvokeConstraintImpl(TimeBasedConstraint.Constraint constraint, 
		long invocationFrequency) {
		this(constraint, invocationFrequency, Long.MAX_VALUE);
	}
	
	protected TimeBasedInvokeConstraintImpl() {
		
	}

	
	public TimeBasedInvokeConstraintImpl(TimeBasedConstraint.Constraint constraint, 
			long invocationFrequency, long maxInvocationCount) {
		super(InvokeConstraint.Constraint.TIMED);
		this.timeConstraint = constraint;
		this.invocationFrequency = invocationFrequency;
		this.maxInvocationCount = maxInvocationCount;
	}

	public TimeBasedInvokeConstraintImpl(InvokeConstraint.Constraint constraint) {
		super(constraint);
	}


	@XmlElement(name=ATTR_FREQUENCY_NAME)
	@Override
	public long getInvocationFrequency() {
		return invocationFrequency;
	}

	@XmlElement(name=ATTR_MAXCOUNT_NAME)
	@Override
	public long getMaxInvocationCount() {
		return maxInvocationCount;
	}

	@XmlElement(name=ATTR_TIME_CONSTRAINT)
	@Override
	public TimeBasedConstraint.Constraint getTimeConstraint() {
		return timeConstraint;
	}

	@Override
	public void setInvocationFrequency(long invocationFrequency) {
		this.invocationFrequency = invocationFrequency;
		
	}

	@Override
	public void setMaxInvocationCount(long maxInvocationCount) {
		this.maxInvocationCount = maxInvocationCount;
		
	}

	@Override
	public void setTimeConstraint(TimeBasedConstraint.Constraint timeConstraint) {
		this.timeConstraint = timeConstraint;
	}


	@Override
	public String toString() {
		return "TimeBasedInvokeConstraintImpl [invocationFrequency=" + invocationFrequency + ", maxInvocationCount="
				+ maxInvocationCount + ", timeConstraint=" + timeConstraint + "]";
	}

}
