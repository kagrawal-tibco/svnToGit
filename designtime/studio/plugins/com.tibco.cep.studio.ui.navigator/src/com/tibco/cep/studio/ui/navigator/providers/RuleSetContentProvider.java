package com.tibco.cep.studio.ui.navigator.providers;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleSet;
import com.tibco.cep.studio.ui.StudioNavigatorNode;
import com.tibco.cep.studio.ui.navigator.model.RuleNode;

public class RuleSetContentProvider extends EntityContentProvider {

	public boolean hasChildren(Object element) {
		if (!(element instanceof IFile)) {
			return false;
		}
		return true;
	}

	@Override
	protected Object[] getEntityChildren(Entity entity, boolean isSharedElement) {
		if (!(entity instanceof RuleSet)) {
			return EMPTY_CHILDREN;
		}
		RuleSet ruleSet = (RuleSet) entity;
		EList<Rule> rules = ruleSet.getRules();
		StudioNavigatorNode[] attributes = new StudioNavigatorNode[rules
				.size()];
		ResourceSet set = new ResourceSetImpl();
		for (int i = 0; i < rules.size(); i++) {
			Rule rule = rules.get(i);
			if (rule.eIsProxy()) {
				rule = (Rule) EcoreUtil.resolve(rule, set);
			}
			attributes[i] = new RuleNode(rule, isSharedElement);
		}

		return attributes;
	}

}
