package com.tibco.cep.studio.core.dependency;

import java.io.File;
import java.util.List;

import com.tibco.cep.designtime.core.model.rule.RuleTemplateView;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class RuleTemplateViewDependencyCalculator extends EntityDependencyCalculator {

	@Override
	protected void processEntityElement(File projectDir, String projectName, EntityElement element,
			List<Object> dependencies) {
		if (!(element.getEntity() instanceof RuleTemplateView)) {
			return;
		}
		RuleTemplateView rtView = (RuleTemplateView) element.getEntity();
		String ruleTemplatePath = rtView.getRuleTemplatePath();
		RuleElement rule = IndexUtils.getRuleElement(projectName, ruleTemplatePath, ELEMENT_TYPES.RULE_TEMPLATE);
		if (rule != null) {
			processAndAddDependency(projectDir, dependencies, projectName, rule);
		}
	}

}
