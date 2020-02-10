package com.tibco.cep.decision.tree.properties;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.tomsawyer.graph.TSEdge;
import com.tomsawyer.graphicaldrawing.TSENode;

/*
 @author ssailapp
 @date Sep 22, 2011
 */

public class GeneralPropertyFilter implements IFilter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	@SuppressWarnings("unused")
	@Override
	public boolean select(Object toTest) {
		if (toTest instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) toTest;
			toTest = selection.getFirstElement();
		}

		if (toTest instanceof TSENode) {
			TSENode tSENode = (TSENode) toTest;
			Object object = tSENode.getUserObject();
		}

		if (toTest instanceof TSEdge) {
			return false;
		}
		return true;
	}
}
