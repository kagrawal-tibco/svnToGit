package com.tibco.cep.bpmn.ui.graph.properties.filter;

import org.eclipse.jface.viewers.IFilter;

/**
 * 
 * @author ggrigore
 *
 */
public class ExtendedPropertyFilter implements IFilter{

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	@Override
	public boolean select(Object toTest) {
//		if (toTest instanceof IStructuredSelection) {
//			IStructuredSelection selection = (IStructuredSelection) toTest;
//			toTest = selection.getFirstElement();
//		}
//			if(toTest instanceof TSGraph) {
//				TSGraph tsg = (TSGraph) toTest;
//				if(tsg.getUserObject() != null && tsg.getUserObject() instanceof EObject) {
//					EObjectWrapper<EClass, EObject> userObjWrapper = 
//						EObjectWrapper.useInstance((EObject)tsg.getUserObject());
//					if(userObjWrapper.isInstanceOf(BpmnModelClass.PROCESS)) {
//						return true;
//					} 
//				}
//			}
//			if(toTest instanceof TSENode) {
//				TSENode node = (TSENode) toTest;
//				if(node.getUserObject() != null && node.getUserObject() instanceof EObject) {
//					EObjectWrapper<EClass, EObject> userObjWrapper = 
//						EObjectWrapper.useInstance((EObject)node.getUserObject());
//					if(userObjWrapper.isInstanceOf(BpmnModelClass.LANE)) {
//						return true;
//					}
//				}
//			}
//			
		return false;
	}

}