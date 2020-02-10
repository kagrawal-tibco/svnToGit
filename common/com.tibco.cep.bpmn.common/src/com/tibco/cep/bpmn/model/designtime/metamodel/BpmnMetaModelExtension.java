package com.tibco.cep.bpmn.model.designtime.metamodel;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import com.tibco.cep.bpmn.model.designtime.utils.EEnumWrapper;

/**
**	Generated on 2014.09.09 AD at 02:48:50 IST
**	by BpmnMetaModelInterfaceGenerator
**
**/

public interface BpmnMetaModelExtension { 

	public static EClass EXTN_PROCESS_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_PROCESS_DATA);
	public static EClass EXTN_LANE_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_LANE_DATA);
	public static EClass EXTN_ACTIVITY_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_ACTIVITY_DATA);
	public static EClass EXTN_TASK_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_TASK_DATA);
	public static EClass EXTN_SERVICE_TASK_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_SERVICE_TASK_DATA);
	public static EClass EXTN_RULE_FUNCTION_TASK_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_RULE_FUNCTION_TASK_DATA);
	public static EClass EXTN_BUSINESS_RULE_TASK_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_BUSINESS_RULE_TASK_DATA);
	public static EClass EXTN_INFERENCE_TASK_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_INFERENCE_TASK_DATA);
	public static EClass EXTN_CALL_ACTIVITY_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_CALL_ACTIVITY_DATA);
	public static EClass EXTN_SUB_PROCESS_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_SUB_PROCESS_DATA);
	public static EClass EXTN_SEND_TASK_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_SEND_TASK_DATA);
	public static EClass EXTN_RECEIVE_TASK_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_RECEIVE_TASK_DATA);
	public static EClass EXTN_VRFIMPL_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_VRFIMPL_DATA);
	public static EClass EXTN_MANUAL_TASK_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_MANUAL_TASK_DATA);
	public static EEnum ENUM_WS_BINDING = BpmnMetaModel.INSTANCE.getEnum(BpmnMetaModel.ENUM_WS_BINDING);
	public static EEnumLiteral ENUM_WS_BINDING_HTTP = EEnumWrapper.useInstance(ENUM_WS_BINDING).getEnumLiteral(BpmnMetaModel.ENUM_WS_BINDING_HTTP);
	public static EEnumLiteral ENUM_WS_BINDING_JMS = EEnumWrapper.useInstance(ENUM_WS_BINDING).getEnumLiteral(BpmnMetaModel.ENUM_WS_BINDING_JMS);
	public static EClass EXTN_JMS_CONFIG_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_JMS_CONFIG_DATA);
	public static EEnum ENUM_MESSAGE_FORMAT = BpmnMetaModel.INSTANCE.getEnum(BpmnMetaModel.ENUM_MESSAGE_FORMAT);
	public static EEnumLiteral ENUM_MESSAGE_FORMAT_BYTES = EEnumWrapper.useInstance(ENUM_MESSAGE_FORMAT).getEnumLiteral(BpmnMetaModel.ENUM_MESSAGE_FORMAT_BYTES);
	public static EEnumLiteral ENUM_MESSAGE_FORMAT_TEXT = EEnumWrapper.useInstance(ENUM_MESSAGE_FORMAT).getEnumLiteral(BpmnMetaModel.ENUM_MESSAGE_FORMAT_TEXT);
	public static EClass EXTN_JMS_CONTEXT_PARAMETER = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_JMS_CONTEXT_PARAMETER);
	public static EClass EXTN_TIMER_TASK_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_TIMER_TASK_DATA);
	public static EClass EXTN_HTTP_SSL_CONFIG = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_HTTP_SSL_CONFIG);
	public static EClass EXTN_JMS_SSL_CONFIG = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_JMS_SSL_CONFIG);
	public static EClass EXTN_JAVA_TASK_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_JAVA_TASK_DATA);
	public static EClass EXTN_PROPERTY_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_PROPERTY_DATA);
	public static EClass EXTN_END_POINT_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_END_POINT_DATA);
	public static EClass EXTN_OPERATION_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_OPERATION_DATA);
	public static EClass EXTN_INTERFACE_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_INTERFACE_DATA);
	public static EClass EXTN_FLOW_ELEMENT_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_FLOW_ELEMENT_DATA);
	public static EClass EXTN_FLOW_NODE_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_FLOW_NODE_DATA);
	public static EClass EXTN_FLOW_ELEMENTS_CONTAINER_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_FLOW_ELEMENTS_CONTAINER_DATA);
	public static EClass EXTN_SEQUENCE_FLOW_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_SEQUENCE_FLOW_DATA);
	public static EClass EXTN_CALLABLE_ELEMENT_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_CALLABLE_ELEMENT_DATA);
	public static EClass EXTN_SCOPE_CONTAINER_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_SCOPE_CONTAINER_DATA);
	public static EClass EXTN_SYMBOL = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_SYMBOL);
	public static EClass EXTN_PROPERTY_DEFINITION_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_PROPERTY_DEFINITION_DATA);
	public static EEnum ENUM_PROPERTY_TYPES = BpmnMetaModel.INSTANCE.getEnum(BpmnMetaModel.ENUM_PROPERTY_TYPES);
	public static EEnumLiteral ENUM_PROPERTY_TYPES_String = EEnumWrapper.useInstance(ENUM_PROPERTY_TYPES).getEnumLiteral(BpmnMetaModel.ENUM_PROPERTY_TYPES_String);
	public static EEnumLiteral ENUM_PROPERTY_TYPES_Integer = EEnumWrapper.useInstance(ENUM_PROPERTY_TYPES).getEnumLiteral(BpmnMetaModel.ENUM_PROPERTY_TYPES_Integer);
	public static EEnumLiteral ENUM_PROPERTY_TYPES_Long = EEnumWrapper.useInstance(ENUM_PROPERTY_TYPES).getEnumLiteral(BpmnMetaModel.ENUM_PROPERTY_TYPES_Long);
	public static EEnumLiteral ENUM_PROPERTY_TYPES_Double = EEnumWrapper.useInstance(ENUM_PROPERTY_TYPES).getEnumLiteral(BpmnMetaModel.ENUM_PROPERTY_TYPES_Double);
	public static EEnumLiteral ENUM_PROPERTY_TYPES_Boolean = EEnumWrapper.useInstance(ENUM_PROPERTY_TYPES).getEnumLiteral(BpmnMetaModel.ENUM_PROPERTY_TYPES_Boolean);
	public static EEnumLiteral ENUM_PROPERTY_TYPES_DateTime = EEnumWrapper.useInstance(ENUM_PROPERTY_TYPES).getEnumLiteral(BpmnMetaModel.ENUM_PROPERTY_TYPES_DateTime);
	public static EEnumLiteral ENUM_PROPERTY_TYPES_Concept = EEnumWrapper.useInstance(ENUM_PROPERTY_TYPES).getEnumLiteral(BpmnMetaModel.ENUM_PROPERTY_TYPES_Concept);
	public static EEnumLiteral ENUM_PROPERTY_TYPES_ConceptReference = EEnumWrapper.useInstance(ENUM_PROPERTY_TYPES).getEnumLiteral(BpmnMetaModel.ENUM_PROPERTY_TYPES_ConceptReference);
	public static EEnumLiteral ENUM_PROPERTY_TYPES_Event = EEnumWrapper.useInstance(ENUM_PROPERTY_TYPES).getEnumLiteral(BpmnMetaModel.ENUM_PROPERTY_TYPES_Event);
	public static EEnumLiteral ENUM_PROPERTY_TYPES_Process = EEnumWrapper.useInstance(ENUM_PROPERTY_TYPES).getEnumLiteral(BpmnMetaModel.ENUM_PROPERTY_TYPES_Process);
	public static EEnumLiteral ENUM_PROPERTY_TYPES_Void = EEnumWrapper.useInstance(ENUM_PROPERTY_TYPES).getEnumLiteral(BpmnMetaModel.ENUM_PROPERTY_TYPES_Void);
	public static EEnum ENUM_TIME_UNITS = BpmnMetaModel.INSTANCE.getEnum(BpmnMetaModel.ENUM_TIME_UNITS);
	public static EEnumLiteral ENUM_TIME_UNITS_Milliseconds = EEnumWrapper.useInstance(ENUM_TIME_UNITS).getEnumLiteral(BpmnMetaModel.ENUM_TIME_UNITS_Milliseconds);
	public static EEnumLiteral ENUM_TIME_UNITS_Seconds = EEnumWrapper.useInstance(ENUM_TIME_UNITS).getEnumLiteral(BpmnMetaModel.ENUM_TIME_UNITS_Seconds);
	public static EEnumLiteral ENUM_TIME_UNITS_Minutes = EEnumWrapper.useInstance(ENUM_TIME_UNITS).getEnumLiteral(BpmnMetaModel.ENUM_TIME_UNITS_Minutes);
	public static EEnumLiteral ENUM_TIME_UNITS_Hours = EEnumWrapper.useInstance(ENUM_TIME_UNITS).getEnumLiteral(BpmnMetaModel.ENUM_TIME_UNITS_Hours);
	public static EEnumLiteral ENUM_TIME_UNITS_Days = EEnumWrapper.useInstance(ENUM_TIME_UNITS).getEnumLiteral(BpmnMetaModel.ENUM_TIME_UNITS_Days);
	public static EEnumLiteral ENUM_TIME_UNITS_WeekDays = EEnumWrapper.useInstance(ENUM_TIME_UNITS).getEnumLiteral(BpmnMetaModel.ENUM_TIME_UNITS_WeekDays);
	public static EEnumLiteral ENUM_TIME_UNITS_Weeks = EEnumWrapper.useInstance(ENUM_TIME_UNITS).getEnumLiteral(BpmnMetaModel.ENUM_TIME_UNITS_Weeks);
	public static EEnumLiteral ENUM_TIME_UNITS_Months = EEnumWrapper.useInstance(ENUM_TIME_UNITS).getEnumLiteral(BpmnMetaModel.ENUM_TIME_UNITS_Months);
	public static EEnumLiteral ENUM_TIME_UNITS_Years = EEnumWrapper.useInstance(ENUM_TIME_UNITS).getEnumLiteral(BpmnMetaModel.ENUM_TIME_UNITS_Years);
	public static EEnum ENUM_VARIABLE_SCOPE = BpmnMetaModel.INSTANCE.getEnum(BpmnMetaModel.ENUM_VARIABLE_SCOPE);
	public static EEnumLiteral ENUM_VARIABLE_SCOPE_NODE = EEnumWrapper.useInstance(ENUM_VARIABLE_SCOPE).getEnumLiteral(BpmnMetaModel.ENUM_VARIABLE_SCOPE_NODE);
	public static EEnumLiteral ENUM_VARIABLE_SCOPE_CONTAINER = EEnumWrapper.useInstance(ENUM_VARIABLE_SCOPE).getEnumLiteral(BpmnMetaModel.ENUM_VARIABLE_SCOPE_CONTAINER);
	public static EClass EXTN_VARIABLE = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_VARIABLE);
	public static EClass EXTN_POINT = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_POINT);
	public static EClass EXTN_EVENT_REPLY_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_EVENT_REPLY_DATA);
	public static EClass EXTN_DESTINATION_CONFIG_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_DESTINATION_CONFIG_DATA);
	public static EClass EXTN_MESSAGE_STARTER_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_MESSAGE_STARTER_DATA);
	public static EClass EXTN_EVENT_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_EVENT_DATA);
	public static EClass EXTN_CATCH_EVENT_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_CATCH_EVENT_DATA);
	public static EClass EXTN_THROW_EVENT_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_THROW_EVENT_DATA);
	public static EClass EXTN_START_EVENT_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_START_EVENT_DATA);
	public static EClass EXTN_END_EVENT_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_END_EVENT_DATA);
	public static EClass EXTN_BOUNDARY_EVENT_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_BOUNDARY_EVENT_DATA);
	public static EClass EXTN_GATEWAY_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_GATEWAY_DATA);
	public static EClass EXTN_INCLUSIVE_GATEWAY_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_INCLUSIVE_GATEWAY_DATA);
	public static EClass EXTN_EXCLUSIVE_GATEWAY_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_EXCLUSIVE_GATEWAY_DATA);
	public static EClass EXTN_PARALLEL_GATEWAY_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_PARALLEL_GATEWAY_DATA);
	public static EClass EXTN_COMPLEX_GATEWAY_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_COMPLEX_GATEWAY_DATA);
	public static EClass EXTN_EVENT_BASED_GATEWAY_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_EVENT_BASED_GATEWAY_DATA);
	public static EClass EXTN_EXPRESSION_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_EXPRESSION_DATA);
	public static EClass EXTN_GATEWAY_EXPRESSION_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_GATEWAY_EXPRESSION_DATA);
	public static EEnum ENUM_MAPPING_OPTION = BpmnMetaModel.INSTANCE.getEnum(BpmnMetaModel.ENUM_MAPPING_OPTION);
	public static EEnumLiteral ENUM_MAPPING_OPTION_MAPPER = EEnumWrapper.useInstance(ENUM_MAPPING_OPTION).getEnumLiteral(BpmnMetaModel.ENUM_MAPPING_OPTION_MAPPER);
	public static EEnumLiteral ENUM_MAPPING_OPTION_COPY = EEnumWrapper.useInstance(ENUM_MAPPING_OPTION).getEnumLiteral(BpmnMetaModel.ENUM_MAPPING_OPTION_COPY);
	public static EClass EXTN_ARTIFACT_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_ARTIFACT_DATA);
	public static EClass EXTN_ASSOCIATION_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_ASSOCIATION_DATA);
	public static EClass EXTN_TEXT_ANNOTATION_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_TEXT_ANNOTATION_DATA);
	public static EClass EXTN_BASE_ELEMENT_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_BASE_ELEMENT_DATA);
	public static EClass EXTN_ROOT_ELEMENT_DATA = BpmnMetaModel.INSTANCE.getEClass(BpmnMetaModel.EXTN_ROOT_ELEMENT_DATA);

}
