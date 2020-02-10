package com.tibco.cep.studio.ui.statemachine.tabbed.properties;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.tomsawyer.graph.TSEdge;

/**
 * 
 * @author sasahoo
 *
 */
public class TransitionGeneralPropertySectionFilter implements IFilter{

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	@Override
	public boolean select(Object toTest) {
		if (toTest instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) toTest;
			toTest = selection.getFirstElement();
		}
		
		if (toTest instanceof TSEdge) {
			return true;
		}
		return false;
	}
}
