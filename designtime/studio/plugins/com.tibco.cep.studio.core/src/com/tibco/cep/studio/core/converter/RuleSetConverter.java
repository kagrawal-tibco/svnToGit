package com.tibco.cep.studio.core.converter;

import java.util.List;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.RuleSet;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.StandaloneRule;

public class RuleSetConverter extends EntityConverter {

	@Override
	public Entity convertEntity(com.tibco.cep.designtime.model.Entity entity, String projectName) {
		RuleSet newEntity = RuleFactory.eINSTANCE.createRuleSet();
		populateEntity(newEntity, entity, projectName);
		return newEntity;
	}

	@Override
	public Class<? extends com.tibco.cep.designtime.model.Entity> getConverterClass() {
		return com.tibco.cep.designtime.model.rule.RuleSet.class;
	}

	@Override
	protected void populateEntity(Entity newEntity, com.tibco.cep.designtime.model.Entity entity, String projectName) {
			super.populateEntity(newEntity, entity, projectName);
			RuleSet newRuleSet = (RuleSet) newEntity;
			com.tibco.cep.designtime.model.rule.RuleSet ruleSet = (com.tibco.cep.designtime.model.rule.RuleSet) entity;
			List rules = ruleSet.getRules();
			if (rules != null && rules.size() > 0) {
				RuleConverter ruleConverter = new RuleConverter();
				for (Object object : rules) {
					Rule rule = (Rule) object;
					Entity newRule = ruleConverter.convertEntity(rule, projectName);
					if(entity instanceof StandaloneRule){
						newRule.setFolder(ruleSet.getFolderPath());
					}else{
						newRule.setFolder(ruleSet.getFolderPath()+ruleSet.getName()+"/");
					}
					newRuleSet.getRules().add((com.tibco.cep.designtime.core.model.rule.Rule) newRule);

					if (fConvertedEntities != null) {
						fConvertedEntities.add(newRule);
					}
				}
		}

	}
}
