package com.tibco.cep.studio.ui.navigator.providers;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.ui.navigator.model.DomainInstanceNode;
import com.tibco.cep.studio.ui.navigator.model.EventPropertyNode;

/**
 * 
 * @author sasahoo
 *
 */
public class EventPropertiesContentProvider implements ITreeContentProvider {

	protected static final Object[] EMPTY_CHILDREN = new Object[0];

	
	public Object[] getChildren(Object parentElement) {
		if (!(parentElement instanceof EventPropertyNode)) {
			return EMPTY_CHILDREN;
		}
		PropertyDefinition propDef  = null;
		if(parentElement instanceof EventPropertyNode){
			EventPropertyNode propertyNode = (EventPropertyNode) parentElement;
			propDef = (PropertyDefinition)propertyNode.getUserProperty();
		}
		EList<DomainInstance> dmInstances = propDef.getDomainInstances();
		DomainInstanceNode[] nodes = new DomainInstanceNode[dmInstances.size()];
		for (int i = 0; i < dmInstances.size(); i++) {
			nodes[i] = new DomainInstanceNode(dmInstances.get(i));
		}
		return nodes;
	}

	protected Object[] getObjectChildren(EObject object) {
		return EMPTY_CHILDREN;
	}

	public Object getParent(Object element) {
		return null;
	}

	public boolean hasChildren(Object element) {
		if (!(element instanceof EventPropertyNode)) {
			return false;
		}
		return true;
	}

	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	public void dispose() {

	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

}