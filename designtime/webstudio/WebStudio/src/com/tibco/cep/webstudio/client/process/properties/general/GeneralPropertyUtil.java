package com.tibco.cep.webstudio.client.process.properties.general;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.webstudio.client.process.ProcessConstants;
import com.tibco.cep.webstudio.server.command.ProcessDataUtils;
import com.tibco.cep.webstudio.server.model.ProcessElementTypes;
import com.tibco.cep.webstudio.server.model.process.AnnotationElement;
import com.tibco.cep.webstudio.server.model.process.FlowNodeElement;
import com.tibco.cep.webstudio.server.model.process.ImplementationURI;
import com.tibco.cep.webstudio.server.model.process.Process;
import com.tibco.cep.webstudio.server.model.process.SequenceElement;
import com.tibco.cep.webstudio.server.model.process.UriList;
import com.tibco.xml.datamodel.XiNode;
import com.tomsawyer.model.TSModelElement;
import com.tomsawyer.util.shared.TSAttributedObject;
import com.tomsawyer.view.drawing.TSModelDrawingView;

/**
 * This is utility class for general properties of process, flow and sequence
 * element.
 * 
 * @author dijadhav
 * 
 */
public class GeneralPropertyUtil {

	/**
	 * This method is used to populate general properties flow element
	 * 
	 * @param commandData
	 * @param flowNodeElement
	 */
	public static void populateFlowElementGeneralProperty(
			List<Serializable> commandData, FlowNodeElement flowNodeElement) {
		if (ProcessElementTypes.RuleFunctionTask.getName().equals(
				flowNodeElement.getElementType())) {
			populateRuleFunctionTaskGeneralProperty(commandData,
					flowNodeElement);
		} else if (ProcessElementTypes.JavaTask.getName().equals(
				flowNodeElement.getElementType())) {
			populateJavaTaskGeneralProperty(commandData, flowNodeElement);
		} else if (ProcessElementTypes.BusinessRuleTask.getName().equals(
				flowNodeElement.getElementType())) {
			populateBusinessTaskGeneralProperty(commandData, flowNodeElement);
		} else if (ProcessElementTypes.SendTask.getName().equals(
				flowNodeElement.getElementType())) {
			populateSendTaskGeneralProperty(commandData, flowNodeElement);
		} else if (ProcessElementTypes.ReceiveTask.getName().equals(
				flowNodeElement.getElementType())) {
			populateReceiveTaskGeneralProperty(commandData, flowNodeElement);
		} else if (ProcessElementTypes.ManualTask.getName().equals(
				flowNodeElement.getElementType())) {
			populateManualTaskGeneralProperty(commandData, flowNodeElement);
		} else if (ProcessElementTypes.ServiceTask.getName().equals(
				flowNodeElement.getElementType())) {
			populateWebServiceTaskGeneralProperty(commandData, flowNodeElement);
		} else if (ProcessElementTypes.InferenceTask.getName().equals(
				flowNodeElement.getElementType())) {
			populateInferenceTaskGeneralProperty(commandData, flowNodeElement);
		} else if (ProcessElementTypes.CallActivity.getName().equals(
				flowNodeElement.getElementType())) {
			populatCallActivityGeneralProperty(commandData, flowNodeElement);
		} else if (ProcessElementTypes.ParallelGateway.getName().equals(
				flowNodeElement.getElementType())) {
			populateParallelGatewayGeneralProperty(commandData, flowNodeElement);
		} else if (ProcessElementTypes.ExclusiveGateway.getName().equals(
				flowNodeElement.getElementType())) {
			populateExclusiveGatewayGeneralProperty(commandData,
					flowNodeElement);
		} else if (ProcessConstants.EVENT_START_NONE_TOOLID
				.equals(flowNodeElement.getToolId())) {
			populateStartEventGeneralProperty(commandData, flowNodeElement);
		} else if (flowNodeElement.getElementId().contains(
				ProcessConstants.EXTENDED_NAME_MESSAGE_START)
				|| ProcessConstants.EVENT_START_MESSAGE_TOOLID
						.equals(flowNodeElement.getToolId())) {
			populateMessageStartEventGeneralProperty(commandData,
					flowNodeElement);
		} else if (flowNodeElement.getElementId().contains(
				ProcessConstants.EXTENDED_NAME_SIGNAL_START)
				|| ProcessConstants.EVENT_START_SIGNAL.equals(flowNodeElement
						.getToolId())) {
			populateSignalStartEventGeneralProperty(commandData,
					flowNodeElement);
		} else if (flowNodeElement.getElementId().contains(
				ProcessConstants.EXTENDED_NAME_TIMER_START)
				|| ProcessConstants.EVENT_START_TIMER_TOOLID
						.equals(flowNodeElement.getToolId())) {
			populateTimerEventGeneralProperty(commandData, flowNodeElement);
		} else if (ProcessConstants.EVENT_END_NONE_TOOLID
				.equals(flowNodeElement.getToolId())) {
			populateEndEventGeneralProperty(commandData, flowNodeElement);
		} else if (flowNodeElement.getElementId().contains(
				ProcessConstants.EXTENDED_NAME_MESSAGE_END)
				|| ProcessConstants.EVENT_END_MESSAGE_TOOLID
						.equals(flowNodeElement.getToolId())) {
			populateMessageEndEventGeneralProperty(commandData, flowNodeElement);
		} else if (flowNodeElement.getElementId().contains(
				ProcessConstants.EXTENDED_NAME_SIGNAL_END)
				|| ProcessConstants.EVENT_END_SIGNAL_TOOLID
						.equals(flowNodeElement.getToolId())) {
			populateSignalEndEventGeneralProperty(commandData, flowNodeElement);
		} else if (flowNodeElement.getElementId().contains(
				ProcessConstants.EXTENDED_NAME_ERROR_END)
				|| ProcessConstants.EVENT_END_ERROR_TOOLID
						.equals(flowNodeElement.getToolId())) {
			populateErrorEndEventGeneralProperty(commandData, flowNodeElement);
		} else if (ProcessElementTypes.TextAnnotation.getName().equals(
				flowNodeElement.getElementType())) {
			populateTextAnnotationGeneralProperty(commandData, flowNodeElement);
		}
	}

	/**
	 * This method is used to populate the general properties of text
	 * annotation.
	 */
	private static void populateTextAnnotationGeneralProperty(
			List<Serializable> commandData, FlowNodeElement flowNodeElement) {
		TextAnnotationGeneralProperty textAnnotationGeneralProperty = new TextAnnotationGeneralProperty();
		textAnnotationGeneralProperty.setName(flowNodeElement.getElementId());
		textAnnotationGeneralProperty
				.setText(((AnnotationElement) flowNodeElement).getTextValue());
		textAnnotationGeneralProperty
				.setLabel(flowNodeElement.getElementName());
		textAnnotationGeneralProperty.setType(flowNodeElement.getElementType());
		commandData.add(textAnnotationGeneralProperty);
	}

	/**
	 * This method is used to populate the general properties of rule function
	 * task.
	 */
	private static void populateRuleFunctionTaskGeneralProperty(
			List<Serializable> commandData, FlowNodeElement flowNodeElement) {
		RuleFunctionTaskGeneralProperty ruleFunctionTaskGeneralProperty = new RuleFunctionTaskGeneralProperty();
		ruleFunctionTaskGeneralProperty.setName(flowNodeElement.getElementId());
		ruleFunctionTaskGeneralProperty.setLabel(flowNodeElement
				.getElementName());
		ruleFunctionTaskGeneralProperty.setType(flowNodeElement
				.getElementType());
		ruleFunctionTaskGeneralProperty.setResource(flowNodeElement
				.getResourcePath());
		ruleFunctionTaskGeneralProperty.setCheckPoint(flowNodeElement
				.isCheckpoint());
		commandData.add(ruleFunctionTaskGeneralProperty);
	}

	/**
	 * This method is used to populate the general properties of java task.
	 */
	private static void populateJavaTaskGeneralProperty(
			List<Serializable> commandData, FlowNodeElement flowNodeElement) {
		JavaTaskGeneralProperty javaTaskGeneralProperty = new JavaTaskGeneralProperty();
		javaTaskGeneralProperty.setName(flowNodeElement.getElementId());
		javaTaskGeneralProperty.setLabel(flowNodeElement.getElementName());
		javaTaskGeneralProperty.setResource(flowNodeElement.getResourcePath());
		javaTaskGeneralProperty.setType(flowNodeElement.getElementType());
		commandData.add(javaTaskGeneralProperty);
	}

	/**
	 * This method is used to populate the properties of business task.
	 * 
	 * @param commandData
	 * @param flowNodeElement
	 */
	private static void populateBusinessTaskGeneralProperty(
			List<Serializable> commandData, FlowNodeElement flowNodeElement) {
		BusinessTaskGeneralProperty businessTaskGeneralProperty = new BusinessTaskGeneralProperty();
		businessTaskGeneralProperty.setName(flowNodeElement.getElementId());
		businessTaskGeneralProperty.setType(flowNodeElement.getElementType());
		businessTaskGeneralProperty.setLabel(flowNodeElement.getElementName());
		businessTaskGeneralProperty.setResource(flowNodeElement
				.getResourcePath());
		businessTaskGeneralProperty.setCheckPoint(flowNodeElement
				.isCheckpoint());

		UriList uriList = flowNodeElement.getUriList();
		if (null != uriList) {
			List<VRFImplementationURI> vrfImplementationURIs = new LinkedList<VRFImplementationURI>();
			List<ImplementationURI> implementationURIs = uriList
					.getImplementationURI();
			if (null != implementationURIs && !implementationURIs.isEmpty()) {
				for (ImplementationURI implementationURI : implementationURIs) {
					if (null != implementationURI) {
						VRFImplementationURI vrfImplementationURI = new VRFImplementationURI();
						vrfImplementationURI.setUri(implementationURI.getUri());
						vrfImplementationURI.setDeployed(implementationURI
								.isIsDeployed());
						vrfImplementationURIs.add(vrfImplementationURI);
					}
				}
			}
			businessTaskGeneralProperty
					.setImpelmentationURIList(vrfImplementationURIs);
		}
		commandData.add(businessTaskGeneralProperty);
	}

	/**
	 * This method is sued to populate general properties of send message task.
	 * 
	 * @param commandData
	 * @param flowNodeElement
	 */
	private static void populateSendTaskGeneralProperty(
			List<Serializable> commandData, FlowNodeElement flowNodeElement) {
		SendTaskGeneralProperty sendTaskGeneralProperty = new SendTaskGeneralProperty();
		sendTaskGeneralProperty.setName(flowNodeElement.getElementId());
		sendTaskGeneralProperty.setLabel(flowNodeElement.getElementName());
		sendTaskGeneralProperty.setType(flowNodeElement.getElementType());
		sendTaskGeneralProperty.setResource(flowNodeElement.getResourcePath());
		sendTaskGeneralProperty.setCheckPoint(flowNodeElement.isCheckpoint());
		commandData.add(sendTaskGeneralProperty);
	}

	/**
	 * This method is used to populate general properties of received task.
	 * 
	 * @param commandData
	 * @param flowNodeElement
	 */
	private static void populateReceiveTaskGeneralProperty(
			List<Serializable> commandData, FlowNodeElement flowNodeElement) {
		ReceiveTaskGeneralProperty receiveTaskGeneralProperty = new ReceiveTaskGeneralProperty();
		receiveTaskGeneralProperty.setName(flowNodeElement.getElementId());
		receiveTaskGeneralProperty.setLabel(flowNodeElement.getElementName());
		receiveTaskGeneralProperty.setType(flowNodeElement.getElementType());
		receiveTaskGeneralProperty.setResource(flowNodeElement
				.getResourcePath());
		receiveTaskGeneralProperty
				.setCheckPoint(flowNodeElement.isCheckpoint());
		receiveTaskGeneralProperty
				.setKeyExpression(evaluateExpression(flowNodeElement
						.getKeyExpression()));
		commandData.add(receiveTaskGeneralProperty);
	}

	/**
	 * This method is sued to populate the general properties of manual task.
	 * 
	 * @param commandData
	 * @param flowNodeElement
	 */
	private static void populateManualTaskGeneralProperty(
			List<Serializable> commandData, FlowNodeElement flowNodeElement) {
		ManualTaskGeneralProperty manualTaskGeneralProperty = new ManualTaskGeneralProperty();
		manualTaskGeneralProperty.setLabel(flowNodeElement.getElementName());
		manualTaskGeneralProperty.setName(flowNodeElement.getElementId());
		manualTaskGeneralProperty.setType(flowNodeElement.getElementType());

	}

	/**
	 * This method is used to populate the properties of web service
	 * 
	 * @param commandData
	 * @param flowNodeElement
	 */
	private static void populateWebServiceTaskGeneralProperty(
			List<Serializable> commandData, FlowNodeElement flowNodeElement) {
		WebserviceTaskGeneralProperty webserviceTaskGeneralProperty = new WebserviceTaskGeneralProperty();
		webserviceTaskGeneralProperty
				.setLabel(flowNodeElement.getElementName());
		webserviceTaskGeneralProperty.setName(flowNodeElement.getElementId());
		webserviceTaskGeneralProperty.setType(flowNodeElement.getElementType());
		webserviceTaskGeneralProperty.setCheckPoint(flowNodeElement
				.isCheckpoint());
		webserviceTaskGeneralProperty.setResource(flowNodeElement
				.getResourcePath());
		webserviceTaskGeneralProperty.setSoapAction(flowNodeElement
				.getSoapAction());
		Integer opTimeout = flowNodeElement.getOpTimeout();
		if (null != opTimeout) {
			webserviceTaskGeneralProperty.setTimeout(opTimeout.toString());
		}
		webserviceTaskGeneralProperty.setService(flowNodeElement.getService());
		webserviceTaskGeneralProperty.setPort(flowNodeElement.getPort());
		webserviceTaskGeneralProperty.setOperation(flowNodeElement
				.getOperation());
		commandData.add(webserviceTaskGeneralProperty);
	}

	/**
	 * This method is used to populate the properties of inference task.
	 * 
	 * @param commandData
	 * @param flowNodeElement
	 */
	private static void populateInferenceTaskGeneralProperty(
			List<Serializable> commandData, FlowNodeElement flowNodeElement) {
		InferenceTaskGeneralProperty inferenceTaskGeneralProperty = new InferenceTaskGeneralProperty();
		inferenceTaskGeneralProperty.setName(flowNodeElement.getElementId());
		inferenceTaskGeneralProperty.setLabel(flowNodeElement.getElementName());
		inferenceTaskGeneralProperty.setType(flowNodeElement.getElementType());
		inferenceTaskGeneralProperty.setCheckPoint(flowNodeElement
				.isCheckpoint());
		Set<String> resources = new LinkedHashSet<String>();

		String resourcePath = flowNodeElement.getResourcePath();
		if (null != resourcePath && !resourcePath.trim().isEmpty()) {
			String[] rules = resourcePath.split(",");
			if (null != rules && rules.length > 0) {
				for (String rule : rules) {
					resources.add(rule);
				}
			}
		}
		inferenceTaskGeneralProperty.setResources(resources);
		commandData.add(inferenceTaskGeneralProperty);
	}

	/**
	 * This method is used to populate the general properties of subprocess.
	 * 
	 * @param commandData
	 * @param drawingView
	 */
	public static void populateSubprocessGeneralProperty(
			List<Serializable> commandData, TSModelDrawingView drawingView,
			Process process) {

		List<TSAttributedObject> selectedObjects = drawingView
				.getSelectedAttributedObjects();

		Iterator<TSAttributedObject> selectionIter = selectedObjects.iterator();

		while (selectionIter.hasNext()) {
			SubprocessGeneralProperty generalProperty = new SubprocessGeneralProperty();
			TSModelElement element = (TSModelElement) selectionIter.next();

			String type = (String) element
					.getAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_TYPE);
			String elementId = (String) element
					.getAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_ID);

			if (!(ProcessElementTypes.TextAnnotation.getName()
					.equalsIgnoreCase(type)
					|| ProcessElementTypes.Association.getName()
							.equalsIgnoreCase(type) || ProcessElementTypes.SequenceFlow
					.getName().equalsIgnoreCase(type))) {
				String label = (String) element
						.getAttributeValue(ProcessConstants.ATTRIBUTE_LABEL);
				FlowNodeElement flowNodeElement = ProcessDataUtils
						.getFlowNodeElement(process, elementId);
				generalProperty.setCheckPoint(flowNodeElement.isCheckpoint());
				generalProperty.setLabel(label);
				generalProperty.setType(type);
				generalProperty.setName(elementId);
				commandData.add(generalProperty);
			}
		}
	}

	/**
	 * This method is used to populate the general properties of call activity
	 * 
	 * @param commandData
	 * @param flowNodeElement
	 */
	private static void populatCallActivityGeneralProperty(
			List<Serializable> commandData, FlowNodeElement flowNodeElement) {
		CallActivityGeneralProperty callActivityGeneralProperty = new CallActivityGeneralProperty();
		callActivityGeneralProperty.setName(flowNodeElement.getElementId());
		callActivityGeneralProperty.setLabel(flowNodeElement.getElementName());
		callActivityGeneralProperty.setType(flowNodeElement.getElementType());
		callActivityGeneralProperty.setResource(flowNodeElement
				.getResourcePath());
		callActivityGeneralProperty.setCheckPoint(flowNodeElement
				.isCheckpoint());
		commandData.add(callActivityGeneralProperty);
	}

	/**
	 * This method is used to populate the general properties of exclusive
	 * gateway.
	 * 
	 * @param commandData
	 * @param flowNodeElement
	 */
	private static void populateExclusiveGatewayGeneralProperty(
			List<Serializable> commandData, FlowNodeElement flowNodeElement) {
		ExclusiveGatewayGeneralProperty gatewayGeneralProperty = new ExclusiveGatewayGeneralProperty();
		gatewayGeneralProperty.setName(flowNodeElement.getElementId());
		gatewayGeneralProperty.setLabel(flowNodeElement.getElementName());
		gatewayGeneralProperty.setType(flowNodeElement.getElementType());
		gatewayGeneralProperty.setOutgoing(flowNodeElement.getOutgoing());
		gatewayGeneralProperty.setDefaultSequenceId(flowNodeElement
				.getDefaultSequenceId());
		commandData.add(gatewayGeneralProperty);
	}

	/**
	 * This method is used to populate general properties of parallel gateway.
	 * 
	 * @param commandData
	 * @param flowNodeElement
	 */
	private static void populateParallelGatewayGeneralProperty(
			List<Serializable> commandData, FlowNodeElement flowNodeElement) {
		ParallelGatewayGeneralProperty parallelGatewayGeneralProperty = new ParallelGatewayGeneralProperty();
		parallelGatewayGeneralProperty.setName(flowNodeElement.getElementId());
		parallelGatewayGeneralProperty.setLabel(flowNodeElement
				.getElementName());
		parallelGatewayGeneralProperty
				.setType(flowNodeElement.getElementType());
		parallelGatewayGeneralProperty
				.setMergeExpression(evaluateExpression(flowNodeElement
						.getMergeExpression()));
		parallelGatewayGeneralProperty.setForkFunction(flowNodeElement
				.getForkRuleFunction());
		parallelGatewayGeneralProperty.setJoinfunction(flowNodeElement
				.getJoinRuleFunction());
		parallelGatewayGeneralProperty.setOutgoing(flowNodeElement
				.getOutgoing());
		parallelGatewayGeneralProperty.setIncomming(flowNodeElement
				.getIncoming());
		parallelGatewayGeneralProperty.setDefaultSequenceId(flowNodeElement
				.getDefaultSequenceId());
		commandData.add(parallelGatewayGeneralProperty);
	}

	/**
	 * This method is used to populate the general properties of start event.
	 * 
	 * @param commandData
	 * @param flowNodeElement
	 */
	private static void populateStartEventGeneralProperty(
			List<Serializable> commandData, FlowNodeElement flowNodeElement) {
		StartEventGeneralProperty startEventGeneralProperty = new StartEventGeneralProperty();
		startEventGeneralProperty.setName(flowNodeElement.getElementId());
		startEventGeneralProperty.setLabel(flowNodeElement.getElementName());
		startEventGeneralProperty.setType(flowNodeElement.getElementType());
		commandData.add(startEventGeneralProperty);

	}

	/**
	 * This method is used to populate the general properties of message start
	 * event.
	 * 
	 * @param commandData
	 * @param flowNodeElement
	 */
	private static void populateMessageStartEventGeneralProperty(
			List<Serializable> commandData, FlowNodeElement flowNodeElement) {
		MessageStartEventGeneralProperty messageStartEventGeneralProperty = new MessageStartEventGeneralProperty();
		messageStartEventGeneralProperty
				.setName(flowNodeElement.getElementId());
		messageStartEventGeneralProperty.setLabel(flowNodeElement
				.getElementName());
		messageStartEventGeneralProperty.setType(flowNodeElement
				.getElementType());
		messageStartEventGeneralProperty.setResource(flowNodeElement
				.getResourcePath());
		commandData.add(messageStartEventGeneralProperty);

	}

	/**
	 * This method is used to populate the general properties of signal start
	 * event.
	 * 
	 * @param commandData
	 * @param flowNodeElement
	 */
	private static void populateSignalStartEventGeneralProperty(
			List<Serializable> commandData, FlowNodeElement flowNodeElement) {
		SignalStartEventGeneralProperty signalStartEventGeneralProperty = new SignalStartEventGeneralProperty();
		signalStartEventGeneralProperty.setName(flowNodeElement.getElementId());
		signalStartEventGeneralProperty.setLabel(flowNodeElement
				.getElementName());
		signalStartEventGeneralProperty.setType(flowNodeElement
				.getElementType());
		signalStartEventGeneralProperty.setResource(flowNodeElement
				.getResourcePath());
		commandData.add(signalStartEventGeneralProperty);
	}

	/**
	 * This method is used to populate the general properties of timer start
	 * event.
	 * 
	 * @param commandData
	 * @param flowNodeElement
	 */
	private static void populateTimerEventGeneralProperty(
			List<Serializable> commandData, FlowNodeElement flowNodeElement) {
		TimerStartEventGeneralProperty startEventGeneralProperty = new TimerStartEventGeneralProperty();
		startEventGeneralProperty.setName(flowNodeElement.getElementId());
		startEventGeneralProperty.setLabel(flowNodeElement.getElementName());
		startEventGeneralProperty.setType(flowNodeElement.getElementType());
		startEventGeneralProperty
				.setResource(flowNodeElement.getResourcePath());
		commandData.add(startEventGeneralProperty);
	}

	/**
	 * This method is used to populate the general properties of end event.
	 * 
	 * @param commandData
	 * @param flowNodeElement
	 */
	private static void populateEndEventGeneralProperty(
			List<Serializable> commandData, FlowNodeElement flowNodeElement) {
		EndEventGeneralProperty endEventGeneralProperty = new EndEventGeneralProperty();
		endEventGeneralProperty.setName(flowNodeElement.getElementId());
		endEventGeneralProperty.setLabel(flowNodeElement.getElementName());
		endEventGeneralProperty.setType(flowNodeElement.getElementType());
		commandData.add(endEventGeneralProperty);
	}

	/**
	 * This method is used to populate the general properties of message end
	 * event.
	 * 
	 * @param commandData
	 * @param flowNodeElement
	 */
	private static void populateMessageEndEventGeneralProperty(
			List<Serializable> commandData, FlowNodeElement flowNodeElement) {
		MessageEndEventGeneralProperty messageEndEventGeneralProperty = new MessageEndEventGeneralProperty();
		messageEndEventGeneralProperty.setName(flowNodeElement.getElementId());
		messageEndEventGeneralProperty.setLabel(flowNodeElement
				.getElementName());
		messageEndEventGeneralProperty
				.setType(flowNodeElement.getElementType());
		messageEndEventGeneralProperty.setResource(flowNodeElement
				.getResourcePath());
		commandData.add(messageEndEventGeneralProperty);
	}

	/**
	 * This method is used to populate the general properties of signal end
	 * event.
	 * 
	 * @param commandData
	 * @param flowNodeElement
	 */
	private static void populateSignalEndEventGeneralProperty(
			List<Serializable> commandData, FlowNodeElement flowNodeElement) {
		SignalEndEventGeneralProperty errorEndEventGeneralProperty = new SignalEndEventGeneralProperty();
		errorEndEventGeneralProperty.setName(flowNodeElement.getElementId());
		errorEndEventGeneralProperty.setLabel(flowNodeElement.getElementName());
		errorEndEventGeneralProperty.setType(flowNodeElement.getElementType());
		errorEndEventGeneralProperty.setResource(flowNodeElement
				.getResourcePath());
		commandData.add(errorEndEventGeneralProperty);
	}

	/**
	 * This method is used to populate the general properties of error end
	 * event.
	 * 
	 * @param commandData
	 * @param flowNodeElement
	 */
	private static void populateErrorEndEventGeneralProperty(
			List<Serializable> commandData, FlowNodeElement flowNodeElement) {
		ErrorEndEventGeneralProperty errorEndEventGeneralProperty = new ErrorEndEventGeneralProperty();
		errorEndEventGeneralProperty.setName(flowNodeElement.getElementId());
		errorEndEventGeneralProperty.setLabel(flowNodeElement.getElementName());
		errorEndEventGeneralProperty.setType(flowNodeElement.getElementType());
		errorEndEventGeneralProperty.setResource(flowNodeElement
				.getResourcePath());
		commandData.add(errorEndEventGeneralProperty);
	}

	/**
	 * This method is used to populate the general properties of process.
	 * 
	 * @param commandData
	 * @param drawingView
	 */
	public static void populateProcessProperties(
			List<Serializable> commandData, Process process) {
		ProcessGeneralProperty property = new ProcessGeneralProperty();
		property.setType("Process");
		String processType = process.getProcessType();
		int revision = process.getVersion().intValue();
		String label = process.getLabel();
		String processName = process.getProcessId();
		String author = process.getAuthor();
		property.setName(processName);
		property.setLabel(label);
		property.setRevision(revision);
		property.setProcessType(processType);
		property.setAuthor(author);
		commandData.add(property);
	}

	/**
	 * This method is used to get the merged expression value.
	 * 
	 * @param xpathValue
	 *            - String representation of xpath.
	 * @return Value of the merged expression.
	 */
	private static String evaluateExpression(String xpathValue) {
		String keyFilter = ProcessConstants.BLANK;
		if (xpathValue != null && !xpathValue.trim().isEmpty()) {
			XiNode xpathNode = null;
			try {
				xpathNode = XSTemplateSerializer
						.deSerializeXPathString(xpathValue);
				keyFilter = XSTemplateSerializer
						.getXPathExpressionAsStringValue(xpathNode);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		return keyFilter;
	}

	/**
	 * This method is used to populate general properties of sequence element.
	 * 
	 * @param commandData
	 * @param sequenceElement
	 */
	public static void populateSequenceElementGeneralProperty(
			List<Serializable> commandData, SequenceElement sequenceElement) {
		if (ProcessElementTypes.SequenceFlow.getName().equals(
				sequenceElement.getElementType())) {
			populateSequenceFlowGeneralProperty(commandData, sequenceElement);
		} else if (ProcessElementTypes.Association.getName().equals(
				sequenceElement.getElementType())) {
			populateAssociationkGeneralProperty(commandData, sequenceElement);
		}
	}

	/**
	 * This method is used to populate the properties of association.
	 * 
	 * @param commandData
	 * @param sequenceElement
	 */
	private static void populateAssociationkGeneralProperty(
			List<Serializable> commandData, SequenceElement sequenceElement) {
		AssociationGeneralProperty associationGeneralProperty = new AssociationGeneralProperty();
		associationGeneralProperty.setName(sequenceElement.getElementId());
		associationGeneralProperty.setLabel(sequenceElement.getElementName());
		associationGeneralProperty.setType(sequenceElement.getElementType());
		commandData.add(associationGeneralProperty);
	}

	/**
	 * This method is used to populate the properties of sequence flow.
	 * 
	 * @param commandData
	 * @param sequenceElement
	 */
	private static void populateSequenceFlowGeneralProperty(
			List<Serializable> commandData, SequenceElement sequenceElement) {
		SequenceGeneralProperty sequenceGeneralProperty = new SequenceGeneralProperty();
		sequenceGeneralProperty.setName(sequenceElement.getElementId());
		sequenceGeneralProperty.setLabel(sequenceElement.getElementName());
		sequenceGeneralProperty.setType(sequenceElement.getElementType());
		commandData.add(sequenceGeneralProperty);
	}
}
