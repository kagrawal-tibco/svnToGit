package com.tibco.cep.bpmn.ui.graph.properties.filter;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.cep.bpmn.ui.editor.IGraphSelection;
import com.tomsawyer.graph.TSGraphObject;

/**
 * 
 * @author majha
 *
 */
public class DescriptionPropertyFilter implements IFilter{
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	@Override
	public boolean select(Object toTest) {
		if (toTest instanceof IGraphSelection) {
			IGraphSelection selection = (IGraphSelection) toTest;
			toTest = selection.getGraphObject();
		}
		if (toTest instanceof TSGraphObject) {
			TSGraphObject tsg = (TSGraphObject) toTest;
			if(tsg.getUserObject() != null && tsg.getUserObject() instanceof EObject) {
				return true;
			}
		}
		return false;
	}
}