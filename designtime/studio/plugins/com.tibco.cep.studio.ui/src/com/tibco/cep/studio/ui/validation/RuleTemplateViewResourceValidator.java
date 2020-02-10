/**
 * 
 */
package com.tibco.cep.studio.ui.validation;

import org.eclipse.core.resources.IResource;

import com.tibco.cep.designtime.core.model.rule.RuleTemplateView;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.validation.ValidationContext;
import com.tibco.cep.studio.ui.util.Messages;

/**
 */
public class RuleTemplateViewResourceValidator extends EntityResourceValidator {

	@Override
	public boolean canContinue() {
		return true;
	}

	@Override
	public boolean enablesFor(IResource resource) {
		return super.enablesFor(resource);
	}

	@Override
	public boolean validate(ValidationContext validationContext) {
		IResource resource = validationContext.getResource();
		if (resource == null) return true;
		// perform basic resource validation for folder etc
		super.validate(validationContext);
		
		// get Model Object
		DesignerElement modelObj = getModelObject(resource);
		if (modelObj instanceof EntityElement){
			RuleTemplateView entity = (RuleTemplateView) ((EntityElement)modelObj).getEntity();
			String ruleTemplatePath = entity.getRuleTemplatePath();
			RuleElement ruleElement = IndexUtils.getRuleElement(resource.getProject().getName(), ruleTemplatePath, ELEMENT_TYPES.RULE_TEMPLATE);
			if (ruleElement == null) {
				reportProblem(resource, Messages.getString("RuleTemplateView.validate.ruletemplate.notfound", ruleTemplatePath));
			}
			if (entity.getPresentationText() == null || entity.getPresentationText().trim().length() == 0) {
				reportProblem(resource, Messages.getString("RuleTemplateView.validate.presentation.empty", ruleTemplatePath));
			}
		}
		
		return true;
	}
	
}
