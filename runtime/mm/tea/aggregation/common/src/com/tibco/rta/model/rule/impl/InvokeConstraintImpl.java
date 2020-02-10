package com.tibco.rta.model.rule.impl;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_TYPE_NAME;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.tibco.rta.model.rule.mutable.MutableInvokeConstraint;

@XmlAccessorType(XmlAccessType.NONE)
public class InvokeConstraintImpl implements MutableInvokeConstraint {

	protected Constraint constraint;
	
	public InvokeConstraintImpl(Constraint constraint) {
		this.constraint = constraint;
	}
	
	protected InvokeConstraintImpl() {
		
	}
	
	@XmlElement(name=ATTR_TYPE_NAME)
	@Override
	public Constraint getConstraint() {
		return constraint;
	}

	@Override
	public void setConstraint(Constraint constraint) {
		this.constraint = constraint;
	}
	
	@Override
	public String toString() {
		return "InvokeConstraintImpl [constraint=" + constraint + "]";
	}

}
