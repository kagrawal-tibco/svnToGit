package com.tibco.cep.bpmn.model.designtime.extension;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.CommonECoreHelper;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.xml.data.primitive.ExpandedName;

public class ExtensionDefintionFactory {
	
	
	
	static Map<ExpandedName,ExpandedName> attrDefinitionMap = new HashMap<ExpandedName,ExpandedName>();
	
	static { 
		/**
		 *  every model class has one extension attribute <link>BpmnModelExtensionConstants.EXTENSION_ATTRIBUTE_NAME</link>
		 *  whose type maps to a Bpmn EMF Model extension class for respective model classes. By having a EMF model 
		 *  structure it is easy to de/serialize the data to xmi.
		 */
		// Process
		attrDefinitionMap.put(BpmnMetaModelConstants.PROCESS,BpmnMetaModelExtensionConstants.EXTN_PROCESS_DATA);
		// Tasks
		attrDefinitionMap.put(BpmnMetaModelConstants.RULE_FUNCTION_TASK,BpmnMetaModelExtensionConstants.EXTN_RULE_FUNCTION_TASK_DATA);
		attrDefinitionMap.put(BpmnMetaModelConstants.SERVICE_TASK,BpmnMetaModelExtensionConstants.EXTN_SERVICE_TASK_DATA);
		attrDefinitionMap.put(BpmnMetaModelConstants.BUSINESS_RULE_TASK,BpmnMetaModelExtensionConstants.EXTN_BUSINESS_RULE_TASK_DATA);
		attrDefinitionMap.put(BpmnMetaModelConstants.SUB_PROCESS,BpmnMetaModelExtensionConstants.EXTN_SUB_PROCESS_DATA);
		attrDefinitionMap.put(BpmnMetaModelConstants.INFERENCE_TASK,BpmnMetaModelExtensionConstants.EXTN_INFERENCE_TASK_DATA);
		attrDefinitionMap.put(BpmnMetaModelConstants.CALL_ACTIVITY,BpmnMetaModelExtensionConstants.EXTN_CALL_ACTIVITY_DATA);
		attrDefinitionMap.put(BpmnMetaModelConstants.SEND_TASK,BpmnMetaModelExtensionConstants.EXTN_SEND_TASK_DATA);
		attrDefinitionMap.put(BpmnMetaModelConstants.RECEIVE_TASK,BpmnMetaModelExtensionConstants.EXTN_RECEIVE_TASK_DATA);
		attrDefinitionMap.put(BpmnMetaModelConstants.MANUAL_TASK,BpmnMetaModelExtensionConstants.EXTN_MANUAL_TASK_DATA);
		attrDefinitionMap.put(BpmnMetaModelConstants.JAVA_TASK,BpmnMetaModelExtensionConstants.EXTN_JAVA_TASK_DATA);
		// Events
		attrDefinitionMap.put(BpmnMetaModelConstants.START_EVENT,BpmnMetaModelExtensionConstants.EXTN_START_EVENT_DATA);
		attrDefinitionMap.put(BpmnMetaModelConstants.END_EVENT,BpmnMetaModelExtensionConstants.EXTN_END_EVENT_DATA);
		attrDefinitionMap.put(BpmnMetaModelConstants.INTERMEDIATE_THROW_EVENT,BpmnMetaModelExtensionConstants.EXTN_THROW_EVENT_DATA);
		attrDefinitionMap.put(BpmnMetaModelConstants.INTERMEDIATE_CATCH_EVENT,BpmnMetaModelExtensionConstants.EXTN_CATCH_EVENT_DATA);
		attrDefinitionMap.put(BpmnMetaModelConstants.BOUNDARY_EVENT,BpmnMetaModelExtensionConstants.EXTN_BOUNDARY_EVENT_DATA);
		// Gateways
		attrDefinitionMap.put(BpmnMetaModelConstants.EXCLUSIVE_GATEWAY,BpmnMetaModelExtensionConstants.EXTN_EXCLUSIVE_GATEWAY_DATA);
		attrDefinitionMap.put(BpmnMetaModelConstants.INCLUSIVE_GATEWAY,BpmnMetaModelExtensionConstants.EXTN_INCLUSIVE_GATEWAY_DATA);
		attrDefinitionMap.put(BpmnMetaModelConstants.PARALLEL_GATEWAY,BpmnMetaModelExtensionConstants.EXTN_PARALLEL_GATEWAY_DATA);
		attrDefinitionMap.put(BpmnMetaModelConstants.COMPLEX_GATEWAY,BpmnMetaModelExtensionConstants.EXTN_COMPLEX_GATEWAY_DATA);
		attrDefinitionMap.put(BpmnMetaModelConstants.EVENT_BASED_GATEWAY,BpmnMetaModelExtensionConstants.EXTN_EVENT_BASED_GATEWAY_DATA);
		// SequenceFlows
		attrDefinitionMap.put(BpmnMetaModelConstants.SEQUENCE_FLOW,BpmnMetaModelExtensionConstants.EXTN_SEQUENCE_FLOW_DATA);
		// Associations
		attrDefinitionMap.put(BpmnMetaModelConstants.ASSOCIATION,BpmnMetaModelExtensionConstants.EXTN_ASSOCIATION_DATA);
		// Associations
		attrDefinitionMap.put(BpmnMetaModelConstants.TEXT_ANNOTATION,BpmnMetaModelExtensionConstants.EXTN_TEXT_ANNOTATION_DATA);
		// lane
		attrDefinitionMap.put(BpmnMetaModelConstants.LANE,BpmnMetaModelExtensionConstants.EXTN_LANE_DATA);
		
		// property
		attrDefinitionMap.put(BpmnMetaModelConstants.PROPERTY,BpmnMetaModelExtensionConstants.EXTN_PROPERTY_DATA);
	}
	
	
	
	/**
	 * @param eClass
	 * @return
	 */
	public static boolean hasDataExtensionDefintion(EClass eClass) {
		return attrDefinitionMap.containsKey(CommonECoreHelper.getExpandedName(eClass));
	}
	
	/**
	 * @param eClass
	 * @return
	 */
	public static EObject getDataExtensionDefintion(EClass eClass) {
		return getExtensionDefintion(eClass,BpmnMetaModelExtensionConstants.EXTENSION_ATTRIBUTE_NAME);
	}
	
	public static EObject getExtensionDefintion(EClass eClass,String attributeName) {
		EObject extnDefinition = null; 
		if(attrDefinitionMap.containsKey(CommonECoreHelper.getExpandedName(eClass))) {
			EObjectWrapper<EClass, EObject> extnDefinitionWrapper = EObjectWrapper.createInstance(BpmnModelClass.EXTENSION_DEFINITION);
			extnDefinitionWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, CommonECoreHelper.getExpandedName(eClass).getExpandedForm());
			createExtensionAttributeDefinition(
					extnDefinitionWrapper,
					attributeName,
					attrDefinitionMap.get(CommonECoreHelper.getExpandedName(eClass)),
					false);
			extnDefinition = extnDefinitionWrapper.getEInstance();
		}
		return extnDefinition;
	}
	
	
	/**
	 * create extension attribute definition
	 * @param extensionDef
	 * @param attrName
	 * @param attrType
	 * @param isReference
	 */
	private static void createExtensionAttributeDefinition(
			EObjectWrapper<EClass, EObject> extensionDef,
			String attrName,
			ExpandedName attrType,
			boolean isReference) {
		
		EObjectWrapper<EClass, EObject> instance 
			= EObjectWrapper.createInstance(BpmnModelClass.EXTENSION_ATTRIBUTE_DEFINITION);
		instance.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, attrName);
		// instance.setAttribute(BpmnMetaModelConstants.E_ATTR_LABEL, attrName);
		instance.setAttribute(BpmnMetaModelConstants.E_ATTR_TYPE, attrType.getExpandedForm());
		instance.setAttribute(BpmnMetaModelConstants.E_ATTR_IS_REFERENCE, isReference);
		instance.setAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSION_DEFINITION, extensionDef.getEInstance());
		extensionDef.addToListAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSION_ATTRIBUTE_DEFINITIONS, instance.getEInstance());
	}

}
