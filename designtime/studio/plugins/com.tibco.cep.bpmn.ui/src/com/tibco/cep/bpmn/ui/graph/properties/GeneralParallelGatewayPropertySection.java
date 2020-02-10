package com.tibco.cep.bpmn.ui.graph.properties;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;





/**
 * 
 * @author majha
 *
 */
public class GeneralParallelGatewayPropertySection extends GeneralGatewayPropertySection {


	public GeneralParallelGatewayPropertySection() {
		super();
	}
	
	@Override
	protected boolean isJoinRulefuntionApplicable(){

		return true;
	}
	
	@Override
	protected boolean isJoinRuleFunctionEnabled(){
		if (fTSENode != null) { 
//			EClass nodeType = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
//			EClass nodeExtType = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
			EObject userObject = (EObject) fTSENode.getUserObject();
			EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper.wrap(userObject);
			if(userObjWrapper.isInstanceOf(BpmnModelClass.FLOW_NODE)){
				EList<EObject> incoming = userObjWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_INCOMING);
				return incoming.size() > 1;
			}
			
		}
		
		return isJoinRulefuntionApplicable();
	}
	
	@Override
	protected boolean isMergeFuntionEnabled() {
		// TODO Auto-generated method stub
		return isJoinRuleFunctionEnabled();
	}
	
	@Override
	protected boolean isForkRulefuntionApplicable(){

		return true;
	}
	
	@Override
	protected boolean isForkRuleFuntionEnabled(){
		if (fTSENode != null) { 
//			EClass nodeType = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
//			EClass nodeExtType = (EClass) fTSENode.getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
			EObject userObject = (EObject) fTSENode.getUserObject();
			EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper.wrap(userObject);
			if(userObjWrapper.isInstanceOf(BpmnModelClass.FLOW_NODE)){
				EList<EObject> outgoing = userObjWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING);
				return outgoing.size() > 1;
			}
			
		}
		
		return isForkRulefuntionApplicable();
	}
	
	@Override
	protected boolean isMergeFuntionApplicable() {
		return true;
	}


	
}