package com.tibco.cep.studio.ui.compare.model;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.element.PropertyDefinition;


public class PropertyDefinitionTreeNode extends AbstractTreeNode {

	public PropertyDefinitionTreeNode(EObject input, int featureId) {
		super(input, featureId);
	}

	@Override
	public boolean isEqualTo(Object obj) {
		if (input instanceof PropertyDefinition && ((AbstractTreeNode)obj).getInput() instanceof PropertyDefinition) {
			PropertyDefinition prop1 = (PropertyDefinition) input;
			PropertyDefinition prop2 = (PropertyDefinition) ((AbstractTreeNode)obj).getInput();
			return prop1.getName().equals(prop2.getName());
		}
		return false;
	}

	@Override
	public void handleReplace(AbstractTreeNode dest, AbstractTreeNode src, EObject newObject) {
		super.handleReplace(dest, src, newObject);
	}

	@Override
	public boolean isUnsettable() {
		return true;
	}
}
