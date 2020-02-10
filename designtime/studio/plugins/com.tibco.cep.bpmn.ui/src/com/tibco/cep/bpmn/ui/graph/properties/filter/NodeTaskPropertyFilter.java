package com.tibco.cep.bpmn.ui.graph.properties.filter;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.editor.IGraphSelection;
import com.tibco.cep.bpmn.ui.graph.model.BpmnSupportedEmfType;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * 
 * @author ggrigore
 * 
 */
public class NodeTaskPropertyFilter implements IFilter {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	@Override
	public boolean select(Object toTest) {
		if (toTest instanceof IGraphSelection) {
			IGraphSelection selection = (IGraphSelection) toTest;
			toTest = selection.getGraphObject();
		}
		if (toTest instanceof TSENode) {
			TSENode tsg = (TSENode) toTest;
			if (tsg.getUserObject() != null
					&& tsg.getUserObject() instanceof EObject) {
				EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper
						.wrap((EObject) tsg.getUserObject());
				if (BpmnModelClass.TASK.isSuperTypeOf(userObjWrapper
						.getEClassType())
						&& !BpmnModelClass.INFERENCE_TASK
								.isSuperTypeOf(userObjWrapper.getEClassType())
						&& !BpmnModelClass.SERVICE_TASK
								.isSuperTypeOf(userObjWrapper.getEClassType())
						&& !BpmnModelClass.RULE_FUNCTION_TASK
								.isSuperTypeOf(userObjWrapper.getEClassType())
						&& !BpmnModelClass.SCRIPT_TASK
								.isSuperTypeOf(userObjWrapper.getEClassType())
						&& !BpmnModelClass.SEND_TASK
								.isSuperTypeOf(userObjWrapper.getEClassType())
						&& !BpmnModelClass.RECEIVE_TASK
								.isSuperTypeOf(userObjWrapper.getEClassType())
						&& !BpmnModelClass.MANUAL_TASK
								.isSuperTypeOf(userObjWrapper.getEClassType())
						&& !BpmnModelClass.BUSINESS_RULE_TASK
								.isSuperTypeOf(userObjWrapper.getEClassType())) {
					BpmnSupportedEmfType bpmnSupportedEmfType = BpmnSupportedEmfType
							.getSupportedTypeMap().get(
									userObjWrapper.getEClassType().getName());
					return bpmnSupportedEmfType.canAttachBEResource();
				}
			}
		} 
//		else if (toTest instanceof TSEGraph) {
//			TSEGraph tsg = (TSEGraph) toTest;
//			if (tsg.getUserObject() != null
//					&& tsg.getUserObject() instanceof EObject) {
//				EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper
//						.wrap((EObject) tsg.getUserObject());
//				if (BpmnModelClass.SUB_PROCESS.isSuperTypeOf(userObjWrapper
//						.getEClassType())) {
//					return true;
//				}
//			}
//		}
		return false;
	}
}