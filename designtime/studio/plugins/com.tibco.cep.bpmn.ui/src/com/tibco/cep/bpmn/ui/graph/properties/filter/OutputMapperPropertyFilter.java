package com.tibco.cep.bpmn.ui.graph.properties.filter;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.editor.IGraphSelection;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * 
 * @author ggrigore
 *
 */
public class OutputMapperPropertyFilter implements IFilter{

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 */
	@Override
	public boolean select(Object toTest) {
		if (toTest instanceof IGraphSelection) {
			IGraphSelection selection = (IGraphSelection) toTest;
			toTest = selection.getGraphObject();
		}
		if (toTest instanceof TSGraph || toTest instanceof TSENode || toTest instanceof TSEConnector) {
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
					if( 
							userObjWrapper.isInstanceOf(BpmnModelClass.LANE) ||
							userObjWrapper.isInstanceOf(BpmnModelClass.TEXT_ANNOTATION) ||
							userObjWrapper.isInstanceOf(BpmnModelClass.END_EVENT)||
							userObjWrapper.isInstanceOf(BpmnModelClass.BUSINESS_RULE_TASK)||
							userObjWrapper.isInstanceOf(BpmnModelClass.SEND_TASK)|| 
							userObjWrapper.isInstanceOf(BpmnModelClass.INFERENCE_TASK)||
							userObjWrapper.isInstanceOf(BpmnModelClass.SERVICE_TASK)||
//							userObjWrapper.isInstanceOf(BpmnModelClass.JAVA_TASK)||
							BpmnModelClass.THROW_EVENT.isSuperTypeOf(userObjWrapper.getEClassType())||
							BpmnModelClass.GATEWAY.isSuperTypeOf(userObjWrapper.getEClassType())
					   ) {
						return false;
					} else if (userObjWrapper
							.isInstanceOf(BpmnModelClass.START_EVENT)) {
						
						EList<EObject> listAttribute = null ;
						if (  userObjWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS ) )
						 listAttribute = userObjWrapper
								.getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
						//Subprocess Start Event should not have mapper
						EObject eobjP = BpmnModelUtils.getFlowElementContainer( ( EObject ) node.getUserObject( ) );
						if ( eobjP != null && BpmnModelClass.SUB_PROCESS.isSuperTypeOf( eobjP.eClass() ) ) {
							if ( listAttribute != null && listAttribute.size() == 0 )
								return false;
						} 
						
						if (listAttribute.size() == 0)
							return true;
					} else if (userObjWrapper
							.isInstanceOf(BpmnModelClass.SUB_PROCESS)) {
						boolean  triggerByEvent = userObjWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TRIGGERED_BY_EVENT);
						return !triggerByEvent;
					}
				}
			}if (toTest instanceof TSEConnector) {
				TSEConnector tsg = (TSEConnector) toTest;
				if(tsg.getUserObject() != null && tsg.getUserObject() instanceof EObject) {
					EObjectWrapper<EClass, EObject> userObjWrapper = 
						EObjectWrapper.wrap((EObject)tsg.getUserObject());
					if(BpmnModelClass.BOUNDARY_EVENT.isSuperTypeOf(userObjWrapper.getEClassType())) {
						EList<EObject> listAttribute = userObjWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
						if(listAttribute.size() >0){
							EObject eObject = listAttribute.get(0);
							EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(eObject);
							if(BpmnModelClass.TIMER_EVENT_DEFINITION.isSuperTypeOf(wrap.getEClassType())){
								return false;
							}
								
						}else{
							return false;
						}
						return true;
					}
				}
			}
			return true;
		}
		return false;
	}

}