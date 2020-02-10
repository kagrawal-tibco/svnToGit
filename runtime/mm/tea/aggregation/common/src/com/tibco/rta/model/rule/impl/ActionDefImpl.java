package com.tibco.rta.model.rule.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tibco.rta.model.rule.ActionFunctionDescriptor;
import com.tibco.rta.model.rule.InvokeConstraint;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.rule.mutable.MutableActionDef;
import com.tibco.rta.model.serialize.jaxb.adapter.ConstraintAdapter;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.*;

@XmlAccessorType(XmlAccessType.NONE)
@JsonIdentityInfo( generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class ActionDefImpl implements MutableActionDef {
	
	protected String name;
	
	@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@type")
	protected InvokeConstraint constraint;
	
	protected ActionFunctionDescriptor descriptor;
	@JsonIgnore
	protected RuleDef ruleDef;

	protected String alertLevel;
	
	public ActionDefImpl(ActionFunctionDescriptor descriptor, InvokeConstraint constraint) {
		this.descriptor = descriptor;
		this.constraint = constraint;
		this.name = descriptor.getName();		
	}
	
	protected ActionDefImpl() {

	}

	@XmlElement
	@XmlJavaTypeAdapter(ConstraintAdapter.class)
	@Override
	public InvokeConstraint getConstraint() {
		return constraint;
	}

	@XmlElement(type=ActionFunctionDescriptorImpl.class)
	@JsonDeserialize(as=ActionFunctionDescriptorImpl.class)
	@Override
	public ActionFunctionDescriptor getActionFunctionDescriptor() {
		return descriptor;
	}

	@Override
	public void setConstraint(InvokeConstraint constraint) {
		this.constraint = constraint;
		
	}

	@Override
	public void setActionFunctionDescriptor(ActionFunctionDescriptor descriptor) {
		this.descriptor = descriptor;
		
	}
		
	@XmlAttribute(name=ATTR_NAME_NAME)
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;		
	}
	
	@XmlElement(name=ATTR_ALERT_LEVEL)
	@Override
	public String getAlertLevel() {
		return alertLevel;
	}

	@Override
	public void setAlertLevel(String alertLevel) {
		this.alertLevel = alertLevel;
	}
	
	public void setRuleDef(RuleDef ruleDef) {
		this.ruleDef = ruleDef;
	}

//	@XmlElement(type=RuleDefImpl.class)
	@Override
	public RuleDef getRuleDef() {
		return ruleDef;
	}

}
