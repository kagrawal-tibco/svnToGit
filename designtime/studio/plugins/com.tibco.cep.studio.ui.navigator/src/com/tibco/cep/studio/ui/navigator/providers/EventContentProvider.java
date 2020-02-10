package com.tibco.cep.studio.ui.navigator.providers;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.ResourceHelper;
import com.tibco.cep.studio.ui.navigator.model.EventNavigatorNode;
import com.tibco.cep.studio.ui.navigator.model.EventPropertyNode;

public class EventContentProvider implements ITreeContentProvider {

	protected static final Object[] EMPTY_CHILDREN = new Object[0];
	public Object[] getChildren(Object parentElement) {
		if (!(parentElement instanceof IFile)) {
			return EMPTY_CHILDREN;
		}
		IFile file = (IFile) parentElement;
		EObject eObject = IndexUtils.loadEObject(ResourceHelper.getLocationURI(file));
		if (!(eObject instanceof Entity)) {
			return EMPTY_CHILDREN;
		}
		Entity entity = (Entity) eObject;
		
		return getEntityChildren(entity, false);
	}

	public Object[] getEntityChildren(Entity entity, boolean isSharedElement) {
		Event event = (Event) entity;
		EList<PropertyDefinition> properties = event.getProperties();
		EventNavigatorNode[] attributes = new EventNavigatorNode[properties.size()];
		for (int i = 0; i < properties.size(); i++) {
			attributes[i] = new EventPropertyNode(properties.get(i), isSharedElement);
		}
		
		return attributes;
	}

	public Object getParent(Object element) {
		return null;
	}

	public boolean hasChildren(Object element) {
		if (!(element instanceof IFile)) {
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
