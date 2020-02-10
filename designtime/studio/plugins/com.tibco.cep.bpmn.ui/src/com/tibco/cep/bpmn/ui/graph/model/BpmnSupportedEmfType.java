package com.tibco.cep.bpmn.ui.graph.model;


import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * 
 * @author majha
 *
 */
public enum BpmnSupportedEmfType {
	Sequence(false, BpmnMetaModelConstants.SEQUENCE_FLOW, new ExpandedName[0]),
	Note(false, BpmnMetaModelConstants.TEXT_ANNOTATION, new ExpandedName[0]),
	Association(false, BpmnMetaModelConstants.ASSOCIATION, new ExpandedName[0]),
	CallAcitvity(BpmnMetaModelConstants.CALL_ACTIVITY, new ExpandedName[0]),
	JavaTask(BpmnMetaModelConstants.JAVA_TASK, new ExpandedName[0]),
//	Mannual(false, BpmnMetaModelConstants.MANUAL_TASK, new ExpandedName[0]),
	WebService(true, BpmnMetaModelConstants.SERVICE_TASK, new ExpandedName[0]),
	RuleFunction(BpmnMetaModelConstants.RULE_FUNCTION_TASK, new ExpandedName[0]),
	Inference( BpmnMetaModelConstants.INFERENCE_TASK, new ExpandedName[0]),
	SubProcess(false, BpmnMetaModelConstants.SUB_PROCESS, new ExpandedName[0]),
	BusinessRule(BpmnMetaModelConstants.BUSINESS_RULE_TASK, new ExpandedName[0]),
	ReceiveEvent(BpmnMetaModelConstants.RECEIVE_TASK, new ExpandedName[0]),
	SendEvent(BpmnMetaModelConstants.SEND_TASK, new ExpandedName[0]),
	EndEvent(
			BpmnMetaModelConstants.END_EVENT, 
			ExpandedName.makeName("None","None"), 
			BpmnMetaModelConstants.MESSAGE_EVENT_DEFINITION,
			BpmnMetaModelConstants.ERROR_EVENT_DEFINITION,
			BpmnMetaModelConstants.SIGNAL_EVENT_DEFINITION), 
	StartEvent(
			BpmnMetaModelConstants.START_EVENT, 
			ExpandedName.makeName("None","None"), 
			BpmnMetaModelConstants.MESSAGE_EVENT_DEFINITION,
			BpmnMetaModelConstants.SIGNAL_EVENT_DEFINITION,
			BpmnMetaModelConstants.TIMER_EVENT_DEFINITION), 
//	ThrowEvent(
//			BpmnMetaModelConstants.INTERMEDIATE_THROW_EVENT, ExpandedName.makeName("None","None"), 
//			 BpmnMetaModelConstants.MESSAGE_EVENT_DEFINITION),
//	CatchEvent(
//			BpmnMetaModelConstants.INTERMEDIATE_CATCH_EVENT,
//			BpmnMetaModelConstants.MESSAGE_EVENT_DEFINITION,
//			BpmnMetaModelConstants.ERROR_EVENT_DEFINITION,
//			BpmnMetaModelConstants.TIMER_EVENT_DEFINITION),
//	InclusiveGateway(false, BpmnMetaModelConstants.INCLUSIVE_GATEWAY, new ExpandedName[0]),
	ExclusiveGateway(false, BpmnMetaModelConstants.EXCLUSIVE_GATEWAY, new ExpandedName[0]),
	ParallelGateWay(false, BpmnMetaModelConstants.PARALLEL_GATEWAY, new ExpandedName[0]);
//	ComplexGateway(false, BpmnMetaModelConstants.COMPLEX_GATEWAY, new ExpandedName[0]);
	

	public static final ExpandedName None_Extended_Type =ExpandedName.makeName("None","None");
	private static Map<String, BpmnSupportedEmfType> supportedTypeMap;
	private boolean canAttachBEResource;
	private ExpandedName type;
	private ExpandedName[] extendedTypes;
	
	private BpmnSupportedEmfType(Boolean canAttachBEResource, ExpandedName type, ExpandedName... extendedTypes){
		this.canAttachBEResource = canAttachBEResource;
		this.type = type;
		this.extendedTypes = extendedTypes;
	}
	
	private BpmnSupportedEmfType(ExpandedName type, ExpandedName... extendedTypes){
		this(true, type, extendedTypes);
	}
	
	public ExpandedName getType(){
		return type;
	}
	
	public ExpandedName[] getExtendedTypes(){
		return extendedTypes;
	}
	
	public boolean canAttachBEResource() {
		return canAttachBEResource;
	}

	public static Map<String, BpmnSupportedEmfType> getSupportedTypeMap(){
		if(supportedTypeMap == null){
			supportedTypeMap = new HashMap<String, BpmnSupportedEmfType>();
			BpmnSupportedEmfType[] values = BpmnSupportedEmfType.values();
			for (BpmnSupportedEmfType bpmnSupportedEmfType : values) {
				supportedTypeMap.put(bpmnSupportedEmfType.getType().localName, bpmnSupportedEmfType);
			}
		}
		return supportedTypeMap;
	}
}
