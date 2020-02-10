package com.tibco.cep.bpmn.ui.graph.properties.filter;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.editor.IGraphSelection;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * 
 * @author ggrigore
 *
 */
public class InputMapperPropertyFilter implements IFilter{

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	@Override
	public boolean select(Object toTest) {
		if (toTest instanceof IGraphSelection) {
			IGraphSelection selection = (IGraphSelection) toTest;
			toTest = selection.getGraphObject();
		}
		if (toTest instanceof TSGraph || toTest instanceof TSENode) {
			if(toTest instanceof TSGraph) {
				TSGraph tsg = (TSGraph) toTest;
				if(tsg.getUserObject() != null && tsg.getUserObject() instanceof EObject) {
					EObjectWrapper<EClass, EObject> userObjWrapper = 
						EObjectWrapper.wrap((EObject)tsg.getUserObject());
					if(userObjWrapper.isInstanceOf(BpmnModelClass.LANE) ||
							userObjWrapper.isInstanceOf(BpmnModelClass.PROCESS)) {
						return false;
					} else if(userObjWrapper.isInstanceOf(BpmnModelClass.SUB_PROCESS)){
						boolean  triggerByEvent = userObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TRIGGERED_BY_EVENT);
						return !triggerByEvent;
					}
				}
			}
			if(toTest instanceof TSENode) {
				TSENode node = (TSENode) toTest;
				if(node.getUserObject() != null && node.getUserObject() instanceof EObject) {
					EObjectWrapper<EClass, EObject> userObjWrapper = 
						EObjectWrapper.wrap((EObject)node.getUserObject());
					if (userObjWrapper.isInstanceOf(BpmnModelClass.LANE)
							|| userObjWrapper
									.isInstanceOf(BpmnModelClass.TEXT_ANNOTATION)
							|| userObjWrapper
									.isInstanceOf(BpmnModelClass.SERVICE_TASK)
							|| userObjWrapper
									.isInstanceOf(BpmnModelClass.START_EVENT)
							|| userObjWrapper
									.isInstanceOf(BpmnModelClass.RECEIVE_TASK)
//							||userObjWrapper
//									.isInstanceOf(BpmnModelClass.JAVA_TASK)
							|| BpmnModelClass.CATCH_EVENT
									.isSuperTypeOf(userObjWrapper
											.getEClassType())
							|| BpmnModelClass.GATEWAY
									.isSuperTypeOf(userObjWrapper
											.getEClassType())) {
						return false;
					} else if (userObjWrapper
							.isInstanceOf(BpmnModelClass.END_EVENT)) {
						EList<EObject> listAttribute = userObjWrapper
								.getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
						if (listAttribute.size() == 0)
							return false;
					} else if (userObjWrapper
							.isInstanceOf(BpmnModelClass.SUB_PROCESS)) {
						boolean  triggerByEvent = userObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TRIGGERED_BY_EVENT);
						return !triggerByEvent;
					}
				}
			}
			return true;
		}
		return false;
	}

}