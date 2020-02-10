package com.tibco.cep.studio.ui.compare.model;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.element.Concept;

public class ConceptTreeNode extends AbstractTreeNode {

	public ConceptTreeNode(Object input, int featureId) {
		super(input, featureId);
	}

	@Override
	public boolean isEqualTo(Object obj) {
		if (input instanceof Concept && ((AbstractTreeNode)obj).getInput() instanceof Concept) {
			return true; 
		}
		return false;
	}

	@Override
	public void handleReplace(AbstractTreeNode dest,
			AbstractTreeNode src, EObject newObject) {
		super.handleReplace(dest, src, newObject);
	}
}