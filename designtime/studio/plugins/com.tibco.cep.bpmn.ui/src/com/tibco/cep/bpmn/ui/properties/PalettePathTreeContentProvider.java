package com.tibco.cep.bpmn.ui.properties;


import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.studio.common.configuration.BpmnProcessSettings;

/**
 * Content Provider for custom function jars which are then on the build path
 * @author pdhar
 *
 */
public class PalettePathTreeContentProvider implements ITreeContentProvider {

	public PalettePathTreeContentProvider(){
	}

	@Override
	public Object[] getElements(Object inputElement) {			
		return getChildren(inputElement);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof BpmnProcessSettings) {
			BpmnProcessSettings config = (BpmnProcessSettings) parentElement;
			return config.getPalettePathEntries().toArray();			
		}

		return new Object[0];

	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if(element instanceof BpmnProcessSettings) {
			return true;
		}
		return false;
	}

}
