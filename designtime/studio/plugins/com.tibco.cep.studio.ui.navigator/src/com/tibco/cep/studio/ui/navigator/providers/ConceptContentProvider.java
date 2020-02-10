package com.tibco.cep.studio.ui.navigator.providers;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.ui.StudioNavigatorNode;
import com.tibco.cep.studio.ui.navigator.model.PropertyNode;
import com.tibco.cep.studio.ui.navigator.model.StateMachineAssociationNode;

public class ConceptContentProvider extends EntityContentProvider {

	public boolean hasChildren(Object element) {
		if (!(element instanceof IFile)) {
			return false;
		}
		return true;
	}

	@Override
	protected Object[] getEntityChildren(Entity entity, boolean isSharedElement) {
		if (!(entity instanceof Concept)) {
			return EMPTY_CHILDREN;
		}
		Concept concept = (Concept) entity;
		EList<PropertyDefinition> properties = concept.getProperties();
		StudioNavigatorNode[] attributes = new StudioNavigatorNode[properties.size()];
		for (int i = 0; i < properties.size(); i++) {
			attributes[i] = new PropertyNode(properties.get(i), isSharedElement);
		}
		if (StudioCorePlugin.getDefault().isStateMachineBundleInstalled()) {
			StateMachineAssociationNode[] smAsscNodes = new StateMachineAssociationNode[concept.getStateMachinePaths().size()];
			for (int i = 0; i < concept.getStateMachinePaths().size(); i++) {
				smAsscNodes[i] = new StateMachineAssociationNode(concept.getStateMachinePaths().get(i),entity.getOwnerProjectName());
			}
			if (smAsscNodes.length > 0) {
				Object[] result = new Object[attributes.length + smAsscNodes.length];
				System.arraycopy(attributes, 0, result, 0, attributes.length);
				System.arraycopy(smAsscNodes, 0, result, attributes.length, smAsscNodes.length);
				return result;
			}
		}
		return attributes;
	}

}
