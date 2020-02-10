package com.tibco.cep.bpmn.ui.graph.properties.filter;

import org.eclipse.jface.viewers.IFilter;

import com.tibco.cep.bpmn.ui.editor.IGraphSelection;

/**
 * 
 * @author ggrigore
 *
 */
public class SequenceConditionExpressionFilter implements IFilter{

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	@Override
	public boolean select(Object toTest) {
		if (toTest instanceof IGraphSelection) {
			IGraphSelection selection = (IGraphSelection) toTest;
			toTest = selection.getGraphObject();
		}
//		if (toTest instanceof TSEEdge) {
//			TSEEdge tsg = (TSEEdge) toTest;
//			if(tsg.getUserObject() != null && tsg.getUserObject() instanceof EObject) {
//				EObjectWrapper<EClass, EObject> userObjWrapper = 
//					EObjectWrapper.wrap((EObject)tsg.getUserObject());
//				if(userObjWrapper.isInstanceOf(BpmnModelClass.SEQUENCE_FLOW)) {
//					return true;
//				}
//			}
//		}
		return false;
	}

}