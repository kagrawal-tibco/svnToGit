package com.tibco.cep.studio.core.converter;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;

public class RuleConverter extends CompilableConverter {

	@Override
	public Entity convertEntity(com.tibco.cep.designtime.model.Entity entity, String projectName) {
		Rule newEntity = RuleFactory.eINSTANCE.createRule();
		populateEntity(newEntity, entity, projectName);
		return newEntity;
	}

	@Override
	public Class<? extends com.tibco.cep.designtime.model.Entity> getConverterClass() {
		return com.tibco.cep.designtime.model.rule.Rule.class;
	}

	@Override
	protected void populateEntity(Entity newEntity, com.tibco.cep.designtime.model.Entity entity, String projectName) {
		super.populateEntity(newEntity, entity, projectName);
		Rule newRule = (Rule) newEntity;
		com.tibco.cep.designtime.model.rule.Rule rule = (com.tibco.cep.designtime.model.rule.Rule) entity;
		
		newRule.setPriority(rule.getPriority());
		newRule.setMaxRules(rule.getRequeueCount());
		newRule.setTestInterval(rule.getTestInterval());
		newRule.setStartTime(rule.getStartTime());
		newRule.setRequeue(rule.doesRequeue());
		if (rule.getRequeueCount() > 0) {
			newRule.getRequeueVars().addAll(rule.getRequeueIdentifiers());
		}
		newRule.setBackwardChain(rule.usesBackwardChaining());
		newRule.setForwardChain(rule.usesForwardChaining());
		newRule.setFunction(rule.isFunction());
		newRule.setAuthor(rule.getAuthor());
	}
	
}
