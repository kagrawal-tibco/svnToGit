package com.tibco.cep.bpmn.ui.graph.properties.filter;

import org.eclipse.jface.viewers.IFilter;

import com.tibco.cep.bpmn.ui.editor.IGraphSelection;

public class ForkPropertyFilter implements IFilter {

	@Override
	public boolean select(Object toTest) {
		if (toTest instanceof IGraphSelection) {
			IGraphSelection selection = (IGraphSelection) toTest;
			toTest = selection.getGraphObject();
		}
//		if (toTest instanceof TSENode) {
//			TSENode tsg = (TSENode) toTest;
//			if(tsg.getUserObject() != null && tsg.getUserObject() instanceof EObject) {
//				EObjectWrapper<EClass, EObject> userObjWrapper = 
//					EObjectWrapper.wrap((EObject)tsg.getUserObject());
//				if(BpmnModelClass.INCLUSIVE_GATEWAY.isSuperTypeOf(userObjWrapper.getEClassType()) ||
//						BpmnModelClass.PARALLEL_GATEWAY.isSuperTypeOf(userObjWrapper.getEClassType())) {
//					EEnumLiteral gwDir = userObjWrapper.getEnumAttribute(BpmnMetaModelConstants.E_ATTR_GATEWAY_DIRECTION);
//					if(gwDir.equals(BpmnModelClass.ENUM_GATEWAY_DIRECTION_DIVERGING) ||
//							gwDir.equals(BpmnModelClass.ENUM_GATEWAY_DIRECTION_MIXED)) {
//						return true;
//					}
//				}
//			}
//		}
		return false;
	}

}
