package com.tibco.cep.decision.tree.properties;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.decision.tree.ui.util.DecisionTreeUiUtil;
import com.tomsawyer.graphicaldrawing.TSEObject;

/*
@author ssailapp
@date Sep 22, 2011 
 */

public class DecisionTreePropertiesLabelProvider extends LabelProvider {

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
			text = DecisionTreeUiUtil.getText((TSEObject)selElement);
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
		if (element instanceof TSEObject) {
			img = DecisionTreeUiUtil.getImage((TSEObject)element);
		}
		return img != null ? img : super.getImage(element);
	}
}
