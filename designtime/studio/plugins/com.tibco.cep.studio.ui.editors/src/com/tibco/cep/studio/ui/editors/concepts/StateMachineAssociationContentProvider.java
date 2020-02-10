package com.tibco.cep.studio.ui.editors.concepts;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.designtime.core.model.element.Concept;

/**
 * 
 * @author sasahoo
 *
 */
public class StateMachineAssociationContentProvider implements IStructuredContentProvider {
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object inputElement) {
		
		if(inputElement instanceof Concept){
			EList<String> stateMachineList= ((Concept)inputElement).getStateMachinePaths();
			return stateMachineList.toArray();
		}
		
		return new Object[0];
	}
	public void dispose() {
	}
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}
}