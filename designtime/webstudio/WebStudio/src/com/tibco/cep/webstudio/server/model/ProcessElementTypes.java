package com.tibco.cep.webstudio.server.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author sasahoo
 *
 */
public enum ProcessElementTypes {
	
	StartEvent(0, "StartEvent", "StartEvent"),

	EndEvent(1, "EndEvent", "EndEvent"),
	
	RuleFunctionTask(2, "RuleFunctionTask", "RuleFunctionTask"),

	BusinessRuleTask(3, "BusinessRuleTask", "BusinessRuleTask"),
	
	ServiceTask(4, "ServiceTask", "ServiceTask"),

	ReceiveTask(5, "ReceiveTask", "ReceiveTask"),
	
	SendTask(6, "SendTask", "SendTask"),

	SequenceFlow(7, "SequenceFlow", "SequenceFlow"),
	
	Association(8, "Association", "Association"),
	
	SubProcess(9, "SubProcess", "SubProcess"),
	
	TextAnnotation(10, "TextAnnotation", "TextAnnotation"),
	
	JavaTask(11, "JavaTask", "JavaTask"),
	
	ManualTask(12, "ManualTask", "ManualTask"),
	
	InferenceTask(13, "InferenceTask", "InferenceTask"),
	
	CallActivity(14, "CallActivity", "CallActivity"),
	
	ParallelGateway(15, "ParallelGateway", "ParallelGateway"),
	
	ExclusiveGateway(16, "ExclusiveGateway", "ExclusiveGateway"),
	
	MessageEventDefinition(17, "MessageEventDefinition", "MessageEventDefinition"),
	
	TimerEventDefinition(18, "TimerEventDefinition", "TimerEventDefinition"),
	
	SignalEventDefinition(19, "SignalEventDefinition", "SignalEventDefinition"),
	
	ErrorEventDefinition(20, "ErrorEventDefinition", "ErrorEventDefinition");
	
	public static final int StartEvent_value = 0;

	public static final int EndEvent_value = 1;
	
	public static final int RuleFunctionTask_value = 2;
	
	public static final int BusinessRuleTask_value = 3;
	
	public static final int ServiceTask_value = 4;
	
	public static final int ReceiveTask_value = 5;
	
	public static final int SendTask_value = 6;
	
	public static final int SequenceFlow_value = 7;
	
	public static final int Association_value = 8;
	
	public static final int SubProcess_value = 9;
	
	public static final int TextAnnotation_value = 10;
	
	public static final int JavaTask_value = 11;
	
	public static final int ManualTask_value = 12;
	
	public static final int InferenceTask_value = 13;
	
	public static final int CallActivity_value = 14;
	
	public static final int ParallelGateway_value = 15;
	
	public static final int ExclusiveGateway_value = 16;
	
	public static final int MessageEventDefinition_value = 17;
	
	public static final int TimerEventDefinition_value = 18;
	
	public static final int SignalEventDefinition_value = 19;
	
	public static final int ErrorEventDefinition_value = 20;
	
	private static final ProcessElementTypes[] VALUES_ARRAY =
 new ProcessElementTypes[] {
			StartEvent, EndEvent, RuleFunctionTask, BusinessRuleTask,
			ServiceTask, ReceiveTask, SendTask, SequenceFlow, Association,
			SubProcess, TextAnnotation, JavaTask, ManualTask, InferenceTask,
			CallActivity, ParallelGateway, ExclusiveGateway,
			MessageEventDefinition, TimerEventDefinition,
			SignalEventDefinition, ErrorEventDefinition };

	public static final List<ProcessElementTypes> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	public static ProcessElementTypes get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ProcessElementTypes result = VALUES_ARRAY[i];
			if (result.toString().equalsIgnoreCase(literal)) {
				return result;
			}
		}
		return null;
	}

	public static ProcessElementTypes getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ProcessElementTypes result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	public static ProcessElementTypes get(int value) {
		switch (value) {
			case StartEvent_value: return StartEvent;
			case EndEvent_value: return EndEvent;
			case RuleFunctionTask_value: return RuleFunctionTask;
			case BusinessRuleTask_value: return BusinessRuleTask;
			case ServiceTask_value: return ServiceTask;
			case ReceiveTask_value: return ReceiveTask;
			case SendTask_value: return SendTask;
			case SequenceFlow_value: return SequenceFlow;
			case Association_value: return Association;
			case SubProcess_value: return SubProcess;
			case TextAnnotation_value: return TextAnnotation;
			case JavaTask_value: return JavaTask;
			case ManualTask_value: return ManualTask;
			case InferenceTask_value: return InferenceTask;
			case CallActivity_value: return CallActivity;
			case ParallelGateway_value: return ParallelGateway;
			case ExclusiveGateway_value: return ExclusiveGateway;
			case MessageEventDefinition_value: return MessageEventDefinition;
			case TimerEventDefinition_value: return TimerEventDefinition;
			case SignalEventDefinition_value: return SignalEventDefinition;
			case ErrorEventDefinition_value: return ErrorEventDefinition;
		}
		return null;
	}

	private final int value;

	private final String name;

	private final String literal;

	private ProcessElementTypes(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	public int getValue() {
	  return value;
	}

	public String getName() {
	  return name;
	}

	public String getLiteral() {
	  return literal;
	}

		@Override
	public String toString() {
		return literal;
	}
}