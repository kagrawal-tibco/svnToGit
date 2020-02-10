package com.tibco.cep.webstudio.server.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tibco.cep.webstudio.client.process.ProcessConstants;
import com.tibco.cep.webstudio.client.process.properties.DocumentationPropertyUtil;
import com.tibco.cep.webstudio.client.process.properties.InputMapProperty;
import com.tibco.cep.webstudio.client.process.properties.LoopCharateristicsProperty;
import com.tibco.cep.webstudio.client.process.properties.OutputMapProperty;
import com.tibco.cep.webstudio.client.process.properties.ReplyEvent;
import com.tibco.cep.webstudio.client.process.properties.ReplyEventProperty;
import com.tibco.cep.webstudio.client.process.properties.general.GeneralPropertyUtil;
import com.tibco.cep.webstudio.server.model.ProcessElementTypes;
import com.tibco.cep.webstudio.server.model.process.AnnotationElement;
import com.tibco.cep.webstudio.server.model.process.AssociationElement;
import com.tibco.cep.webstudio.server.model.process.FlowNodeElement;
import com.tibco.cep.webstudio.server.model.process.InputMap;
import com.tibco.cep.webstudio.server.model.process.LoopCharacteristics;
import com.tibco.cep.webstudio.server.model.process.MessageStarter;
import com.tibco.cep.webstudio.server.model.process.MessageStarterList;
import com.tibco.cep.webstudio.server.model.process.OutputMap;
import com.tibco.cep.webstudio.server.model.process.Process;
import com.tibco.cep.webstudio.server.model.process.SequenceElement;
import com.tibco.cep.webstudio.server.ui.ProcessWebDiagramDataRepository;
import com.tomsawyer.model.TSModelElement;
import com.tomsawyer.service.TSServiceException;
import com.tomsawyer.util.gwtclient.command.TSServiceCommand;
import com.tomsawyer.util.shared.TSAttributedObject;
import com.tomsawyer.web.client.command.TSCustomCommand;
import com.tomsawyer.web.client.view.data.TSWebViewClientCommandData;
import com.tomsawyer.web.server.command.TSServiceCommandImpl;
import com.tomsawyer.web.server.drawing.TSWebDrawingViewServer;
import com.tomsawyer.web.server.service.TSPerspectivesViewService;

/**
 * This class is used to fetch the properties of selected object from the
 * drawing view.
 * 
 * @author sasahoo,dijadhav
 * 
 */
public class ProcessFetchPropertiesCommandImpl implements TSServiceCommandImpl {

	/**
	 * Default Constructor
	 */
	public ProcessFetchPropertiesCommandImpl() {
	}

	/**
	 * This method implements the server side component of fetch properties.
	 * 
	 * @param service
	 *            the view service.
	 * @param command
	 *            the service command.
	 * @return A serializable result or null.
	 * @throws com.tomsawyer.util.shared.TSServiceException
	 *             The service exception if an error occurs.
	 */
	public Serializable doAction(TSPerspectivesViewService service,
			TSServiceCommand command) throws TSServiceException {

		TSCustomCommand customCommand = (TSCustomCommand) command;
		String _id = customCommand.getCustomArgs().get(0);
		String propertyType = customCommand.getCustomArgs().get(2);
		// create the web view client command data
		TSWebViewClientCommandData result = new TSWebViewClientCommandData();

		result.setCommand(ProcessConstants.NO_NATIVE_CALL);

		List<Serializable> commandData = new ArrayList<Serializable>();

		/**
		 * Get the DrawingView
		 */
		TSWebDrawingViewServer drawingView = null;
		try {
			drawingView = (TSWebDrawingViewServer) service.getView(
					((TSCustomCommand) customCommand).getProjectID(),
					((TSCustomCommand) customCommand).getViewID(), false);
		} catch (Exception exception) {
			// TODO: handle exception
		}

		if (drawingView != null) {
			Process process = ProcessWebDiagramDataRepository.getInstance()
					.getProcess(((TSCustomCommand) customCommand).getViewID());
			List<TSAttributedObject> selectedObjects = drawingView
					.getSelectedAttributedObjects();

			Iterator<TSAttributedObject> selectionIter = selectedObjects
					.iterator();

			if (!selectionIter.hasNext() && "Process".equals(_id)) {
				if (ProcessConstants.GENERAL_PROPERTY.equals(propertyType)) {
					GeneralPropertyUtil.populateProcessProperties(commandData,
							process);
				} else if (ProcessConstants.DOCUMENTATION_PROPERTY
						.equals(propertyType)) {
					DocumentationPropertyUtil.populateDocumetationPropetry(
							commandData, process.getDocumentation(),
							ProcessConstants.PROCESS_ID);
				} else {
					ProcessVariablePropertyUtil.populateProcessVariables(
							commandData, process);
				}
			} else if (selectionIter.hasNext()) {

				if (null != _id && _id.contains(ProcessConstants.SUBPROCESS)) {
					GeneralPropertyUtil.populateSubprocessGeneralProperty(
							commandData, drawingView,process);
				} else {
					while (selectionIter.hasNext()) {
						TSModelElement element = (TSModelElement) selectionIter
								.next();
						String type = (String) element
								.getAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_TYPE);

						if (ProcessElementTypes.TextAnnotation.getName()
								.equalsIgnoreCase(type)) {
							AnnotationElement flowNodeElement = getAnnotationElement(
									process.getAnnotations()
											.getAnnotationElement(),
									(String) element
											.getAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_ID));
							fetchProperty(propertyType, commandData,
									flowNodeElement);

						} else if (ProcessElementTypes.Association.getName()
								.equalsIgnoreCase(type)) {
							SequenceElement sequenceElement = getAssociationElement(
									process.getAssociations()
											.getAssociationElement(),
									(String) element
											.getAttribute(ProcessConstants.PROCESS_MODEL_ATTRIBUTE_ID));
							if (ProcessConstants.DOCUMENTATION_PROPERTY
									.equals(propertyType)) {
								DocumentationPropertyUtil
										.populateDocumetationPropetry(
												commandData, sequenceElement
														.getDocumentation(),
												"SequenceElement");

							} else {
								GeneralPropertyUtil
										.populateSequenceElementGeneralProperty(
												commandData, sequenceElement);
							}
						} else if (ProcessElementTypes.SequenceFlow.getName()
								.equalsIgnoreCase(type)) {
							SequenceElement sequenceElement = getSequenceElement(
									process.getSequenceFlows()
											.getSequenceElement(),
									(String) element
											.getAttribute(ProcessConstants.PROCESS_SEQUENCE_ATTRIBUTE_ID));
							if (ProcessConstants.DOCUMENTATION_PROPERTY
									.equals(propertyType)) {
								DocumentationPropertyUtil
										.populateDocumetationPropetry(
												commandData, sequenceElement
														.getDocumentation(),
												"SequenceElement");

							} else {
								GeneralPropertyUtil
										.populateSequenceElementGeneralProperty(
												commandData, sequenceElement);
							}

						} else {
							FlowNodeElement flowNodeElement = getFlowElement(
									process.getFlowNodes().getFlowNodeElement(),
									(String) element
											.getAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_ID));
							fetchProperty(propertyType, commandData,
									flowNodeElement);
						}
					}
				}

			}
			// store the argument strings as a list in the command data.
			result.setCommandData(commandData);
		} else {
			result = null;
		}
		return result;
	}

	/**
	 * This method is used to fetch different types of properties of
	 * task,gateway and event
	 * 
	 * @param propertyType
	 * @param commandData
	 * @param flowNodeElement
	 */
	private void fetchProperty(String propertyType,
			List<Serializable> commandData, FlowNodeElement flowNodeElement) {
		if (ProcessConstants.DOCUMENTATION_PROPERTY.equals(propertyType)) {
			DocumentationPropertyUtil.populateDocumetationPropetry(commandData,
					flowNodeElement.getDocumentation(), "FlowElement");

		} else if (ProcessConstants.REPLY_EVENT_PROPERTY.equals(propertyType)) {
			ReplyEventProperty replyEventProperty = new ReplyEventProperty();
			MessageStarterList messageStarterList = flowNodeElement
					.getMessageStarterList();
			if (null != messageStarterList) {
				List<MessageStarter> messageStarters = messageStarterList
						.getMessageStarter();
				if (null != messageStarters && !messageStarters.isEmpty()) {
					for (MessageStarter messageStarter : messageStarters) {
						if (null != messageStarter) {
							ReplyEvent replyEvent = new ReplyEvent();
							replyEvent.setMsgStarterId(messageStarter
									.getMsgStarterId());
							replyEvent.setReplyTo(messageStarter.isReplyTo());
							replyEvent.setConsumed(messageStarter.isConsume());
							replyEventProperty.add(replyEvent);
						}
					}
				}
			}
			commandData.add(replyEventProperty);
		} else if (ProcessConstants.LOOP_CHARATERISTICS_PROPERTY
				.equals(propertyType)) {
			LoopCharateristicsProperty loopCharateristicsProperty = new LoopCharateristicsProperty();
			LoopCharacteristics loopCharacteristics = flowNodeElement
					.getLoopCharacteristics();
			if (null != loopCharacteristics) {
				loopCharateristicsProperty.setType(loopCharacteristics.getType());
				loopCharateristicsProperty.setTestBefore(loopCharacteristics.isTestBefore());
				if(ProcessConstants.STANDARD.equals(loopCharacteristics.getType())){
					loopCharateristicsProperty.setLoopCondition(loopCharacteristics.getLoopCondition());
					loopCharateristicsProperty.setLoopCount(loopCharacteristics.getLoopCount());
				}else if(ProcessConstants.MULTI_INSTANCE.equals(loopCharacteristics.getType())){
					loopCharateristicsProperty.setBehaviour(loopCharacteristics.getBehavior());
					loopCharateristicsProperty.setConditionBody(loopCharacteristics.getCompletionCondition());	
					loopCharateristicsProperty.setSequential(loopCharacteristics.isIsSequential());	
					loopCharateristicsProperty.setCardinalityBody(loopCharateristicsProperty.getCardinalityBody());
				}
			}
			commandData.add(loopCharateristicsProperty);

		} else if (ProcessConstants.TIMEOUT_PROPERTY.equals(propertyType)) {

		} else if (ProcessConstants.OUTPUT_MAP_PROPERTY.equals(propertyType)) {
			OutputMap outputMap = flowNodeElement.getOutputMap();
			OutputMapProperty outputMapProperty = new OutputMapProperty();
			if (null != outputMap) {
				outputMapProperty.setMapping(outputMap.getMapping());
			}
			commandData.add(outputMapProperty);
		} else if (ProcessConstants.INPUT_MAP_PROPERTY.equals(propertyType)) {
			InputMap inputMap = flowNodeElement.getInputMap();
			InputMapProperty inputMapProperty = new InputMapProperty();
			if (null != inputMap) {
				inputMapProperty.setMapping(inputMap.getMapping());
			}
			commandData.add(inputMapProperty);
		} else {
			GeneralPropertyUtil.populateFlowElementGeneralProperty(commandData,
					flowNodeElement);
		}
	}

	/**
	 * This method is used to get the FlowNodeElement of given id from
	 * flowNodeElements.
	 * 
	 * @param flowNodeElements
	 *            - List of flow node elements.
	 * @param id
	 *            - Id for which we need to fetch the flow node element.
	 * @return FlowNodeElement for given id.
	 */
	private FlowNodeElement getFlowElement(
			List<FlowNodeElement> flowNodeElements, String id) {
		FlowNodeElement flowNodeElement = null;
		for (FlowNodeElement nodeElement : flowNodeElements) {
			if (id.equalsIgnoreCase(nodeElement.getElementId())) {
				flowNodeElement = nodeElement;
			}
		}
		return flowNodeElement;
	}

	/**
	 * This method is used to get the SequenceElement of given id from
	 * sequenceElements.
	 * 
	 * @param sequenceElements
	 *            - List of sequence elements.
	 * @param id
	 *            - Id for which we need to fetch the sequence element.
	 * @return SequenceElement for given id.
	 */
	private SequenceElement getSequenceElement(
			List<SequenceElement> sequenceElements, String id) {
		SequenceElement sequenceElement = null;
		for (SequenceElement element : sequenceElements) {
			if (id.equalsIgnoreCase(element.getElementId())) {
				sequenceElement = element;
			}
		}
		return sequenceElement;
	}

	/**
	 * This method is used to get the AnnotationElement of given id from
	 * annotationElements.
	 * 
	 * @param annotationElements
	 *            - List of annotation elements.
	 * @param id
	 *            - Id for which we need to fetch the annotation element.
	 * @return AnnotationElement for given id.
	 */
	private AnnotationElement getAnnotationElement(
			List<AnnotationElement> annotationElements, String id) {
		AnnotationElement flowNodeElement = null;
		for (AnnotationElement nodeElement : annotationElements) {
			if (id.equalsIgnoreCase(nodeElement.getElementId())) {
				flowNodeElement = nodeElement;
			}
		}
		return flowNodeElement;

	}

	/**
	 * This method is used to get the associationElement of given id from
	 * associationElements.
	 * 
	 * @param sequenceElements
	 *            - List of association elements.
	 * @param id
	 *            - Id for which we need to fetch the association element.
	 * @return AssociationElement for given id.
	 */
	private AssociationElement getAssociationElement(
			List<AssociationElement> associationElements, String id) {
		AssociationElement associationElement = null;
		for (AssociationElement element : associationElements) {
			if (id.equalsIgnoreCase(element.getElementId())) {
				associationElement = element;
			}
		}
		return associationElement;
	}

}
