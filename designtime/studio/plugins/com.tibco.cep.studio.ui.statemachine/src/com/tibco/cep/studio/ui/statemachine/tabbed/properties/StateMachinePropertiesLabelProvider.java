package com.tibco.cep.studio.ui.statemachine.tabbed.properties;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils;
import com.tomsawyer.graphicaldrawing.TSEObject;

/**
 * 
 * @author sasahoo
 *
 */
public class StateMachinePropertiesLabelProvider extends LabelProvider {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		String text = null;
		Object selElement = null;
		if (element instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) element;
			selElement = selection.getFirstElement();
		}
		if(selElement instanceof TSEObject){
			text = StateMachineUtils.getText((TSEObject)selElement);
		}
		return text != null ? text : ""/*super.getText(element)*/;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		Image img = null;
		if (element instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) element;
			element = selection.getFirstElement();
		}
		if(element instanceof TSEObject){
			img = StateMachineUtils.getImage((TSEObject)element);
		}
		return img != null ? img : super.getImage(element);
	}
}