package com.tibco.cep.studio.core.converter;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.rule.RuleFunction.Validity;

public class RuleFunctionConverter extends CompilableConverter {

	@Override
	public Entity convertEntity(com.tibco.cep.designtime.model.Entity entity, String projectName) {
		RuleFunction newEntity = RuleFactory.eINSTANCE.createRuleFunction();
		populateEntity(newEntity, entity, projectName);
		return newEntity;
	}

	@Override
	public Class<? extends com.tibco.cep.designtime.model.Entity> getConverterClass() {
		return com.tibco.cep.designtime.model.rule.RuleFunction.class;
	}

	@Override
	protected void populateEntity(Entity newEntity, com.tibco.cep.designtime.model.Entity entity, String projectName) {
		super.populateEntity(newEntity, entity, projectName);
		RuleFunction newRuleFn = (RuleFunction) newEntity;
		com.tibco.cep.designtime.model.rule.RuleFunction ruleFn = (com.tibco.cep.designtime.model.rule.RuleFunction) entity;
		newRuleFn.setVirtual(ruleFn.isVirtual());
		newRuleFn.setOwnerProjectName(projectName);
		newRuleFn.setReturnType(ruleFn.getReturnType());
		Validity validity = ruleFn.getValidity();
		switch (validity) {
		case ACTION:
			newRuleFn.setValidity(com.tibco.cep.designtime.core.model.rule.Validity.ACTION);
			break;

		case CONDITION:
			newRuleFn.setValidity(com.tibco.cep.designtime.core.model.rule.Validity.CONDITION);
			break;
			
		case QUERY:
			newRuleFn.setValidity(com.tibco.cep.designtime.core.model.rule.Validity.QUERY);
			break;
			
		default:
			break;
		}
		
		if (ruleFn.getAlias() != null) {
			newRuleFn.setAlias(ruleFn.getAlias());
		}

	}
}
