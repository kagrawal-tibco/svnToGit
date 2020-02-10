package com.tibco.cep.studio.ui.navigator.providers;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.studio.ui.navigator.model.StateEntityNode;

public class StateCompositeContentProvider implements ITreeContentProvider {
	protected static final Object[] EMPTY_CHILDREN = new Object[0];

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	public Object[] getChildren(Object parentElement) {
		if (!(parentElement instanceof StateEntityNode)) {
			return EMPTY_CHILDREN;
		}
		if(parentElement instanceof StateEntityNode){
			StateEntityNode stateEntityNode = (StateEntityNode) parentElement;
			if(stateEntityNode.getEntity() instanceof StateComposite){
				StateComposite composite = (StateComposite)stateEntityNode.getEntity();
				if(composite.isConcurrentState()){
					EList<StateComposite> entities = composite.getRegions();
					StateEntityNode[] attributes = new StateEntityNode[composite.getRegions().size()];
					for (int i = 0; i < composite.getRegions().size(); i++) {
						attributes[i] = new StateEntityNode(entities.get(i), false);
					}
					return attributes;
				}else{
					EList<StateEntity> entities = composite.getStateEntities();
					StateEntityNode[] attributes = new StateEntityNode[composite.getStateEntities().size()];
					for (int i = 0; i < composite.getStateEntities().size(); i++) {
						attributes[i] = new StateEntityNode(entities.get(i), false);
					}
					return attributes;
				}
				
			}else{
				return EMPTY_CHILDREN;
			}
		}
		return EMPTY_CHILDREN;
	}

	protected Object[] getObjectChildren(EObject object) {
		return EMPTY_CHILDREN;
	}

	public Object getParent(Object element) {
		return null;
	}

	public boolean hasChildren(Object element) {
		if (!(element instanceof StateEntityNode)) {
			return false;
		}
		if(((StateEntityNode)element).getEntity() instanceof StateComposite){
			return true;
		}
		return false;
	}

	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	public void dispose() {

	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

}