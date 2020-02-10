package com.tibco.cep.studio.ui.navigator.providers;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.ResourceHelper;

public abstract class EntityContentProvider implements ITreeContentProvider {

	protected static final Object[] EMPTY_CHILDREN = new Object[0];

	protected abstract Object[] getEntityChildren(Entity entity, boolean isSharedElement);
	
	public Object[] getChildren(Object parentElement) {
		if (!(parentElement instanceof IFile)) {
			return EMPTY_CHILDREN;
		}
		IFile file = (IFile) parentElement;
		EObject eObject = IndexUtils.loadEObject(ResourceHelper.getLocationURI(file));
		if (!(eObject instanceof Entity)) {
			return getObjectChildren(eObject);
		}
		Entity entity = (Entity) eObject;
		
		return getEntityChildren(entity, false);
	}

	protected Object[] getObjectChildren(EObject object) {
		return EMPTY_CHILDREN;
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
