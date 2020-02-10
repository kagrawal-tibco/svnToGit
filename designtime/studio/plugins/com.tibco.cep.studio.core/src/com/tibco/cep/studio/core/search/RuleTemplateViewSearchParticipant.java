package com.tibco.cep.studio.core.search;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.rule.RulePackage;
import com.tibco.cep.designtime.core.model.rule.RuleTemplateView;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class RuleTemplateViewSearchParticipant extends EntitySearchParticipant {

	@Override
	protected void searchEntity(EObject resolvedElement, DesignerProject index,
			String nameToFind, IProgressMonitor monitor, SearchResult result) {
		List<Entity> allEntities = IndexUtils.getAllEntities(index.getName(), new ELEMENT_TYPES[] { ELEMENT_TYPES.RULE_TEMPLATE_VIEW });
		for (Entity entity : allEntities) {
			RuleTemplateView view = (RuleTemplateView) entity;
			if (isEqual(view, resolvedElement)) {
				result.addExactMatch(createElementMatch(DEFINITION_FEATURE, view.eClass(), resolvedElement));
			}
			String ruleTemplatePath = view.getRuleTemplatePath();
			RuleElement rule = IndexUtils.getRuleElement(index.getName(), ruleTemplatePath, ELEMENT_TYPES.RULE_TEMPLATE);
			if (isEqual(rule, resolvedElement)) {
				result.addExactMatch(createElementMatch(RulePackage.RULE_TEMPLATE_VIEW__RULE_TEMPLATE_PATH, view.eClass(), view));
			}
		}
	}

	@Override
	protected boolean isEqual(Object element, Object resolvedElement) {
//		if (element instanceof RuleTemplateView
//				&& resolvedElement instanceof RuleTemplateView) {
//			RuleTemplateView rtv1 = (RuleTemplateView) element;
//			RuleTemplateView rtv2 = (RuleTemplateView) resolvedElement;
//			if (rtv1.getName().equals(rtv2.getName())
//					&& rtv1.getEventURI() != null
//					&& rtv2.getEventURI() != null
//					&& rtv1.getEventURI().equals(rtv2.getEventURI())
//					&& rtv1.getFolder() != null 
//					&& rtv2.getFolder()!= null 
//					&& rtv1.getFolder().equals(rtv2.getFolder())) {
//				return true;
//			}
//		}
		return super.isEqual(element, resolvedElement);
	}

}
