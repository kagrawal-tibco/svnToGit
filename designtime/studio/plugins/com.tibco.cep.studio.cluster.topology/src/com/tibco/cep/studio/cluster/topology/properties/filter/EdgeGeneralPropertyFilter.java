package com.tibco.cep.studio.cluster.topology.properties.filter;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.tomsawyer.graphicaldrawing.TSEEdge;

public class EdgeGeneralPropertyFilter implements IFilter{

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	@Override
	public boolean select(Object toTest) {
		if (toTest instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) toTest;
			toTest = selection.getFirstElement();
		}
		if (toTest instanceof TSEEdge) {
			return true;
		}
		return false;
	}

}