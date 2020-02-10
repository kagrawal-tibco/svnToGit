package com.tibco.cep.webstudio.server.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.webstudio.client.process.ProcessConstants;
import com.tibco.cep.webstudio.client.process.validataion.Validation;
import com.tibco.cep.webstudio.client.process.validataion.ValidationErrorType;
import com.tibco.cep.webstudio.server.model.ProcessElementTypes;
import com.tibco.cep.webstudio.server.model.process.FlowNodeElement;
import com.tibco.cep.webstudio.server.model.process.FlowNodes;
import com.tibco.cep.webstudio.server.model.process.Process;
import com.tibco.cep.webstudio.server.ui.ProcessWebDiagramDataRepository;
import com.tomsawyer.util.gwtclient.command.TSServiceCommand;
import com.tomsawyer.util.shared.TSServiceException;
import com.tomsawyer.view.drawing.TSModelDrawingView;
import com.tomsawyer.web.client.command.TSCustomCommand;
import com.tomsawyer.web.client.view.data.TSWebViewClientCommandData;
import com.tomsawyer.web.server.command.TSServiceCommandImpl;
import com.tomsawyer.web.server.service.TSPerspectivesViewService;

/**
 * This class is used for process data validation.
 * 
 * @author dijadhav
 * 
 */
public class ProcessValidationCommandImpl implements TSServiceCommandImpl {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tomsawyer.web.server.command.TSServiceCommandImpl#doAction(com.tomsawyer
	 * .web.server.service.TSPerspectivesViewService,
	 * com.tomsawyer.util.gwtclient.command.TSServiceCommand)
	 */
	@Override
	public Serializable doAction(TSPerspectivesViewService service,
			TSServiceCommand customCommand) throws TSServiceException {

		TSWebViewClientCommandData result = new TSWebViewClientCommandData();
		List<Serializable> commandData = new ArrayList<Serializable>();
		TSModelDrawingView view = (TSModelDrawingView) service.getView(
				((TSCustomCommand) customCommand).getProjectID(),
				((TSCustomCommand) customCommand).getViewID(), false);
		if (null != view) {
			Process process = ProcessWebDiagramDataRepository.getInstance()
					.getProcess(((TSCustomCommand) customCommand).getViewID());
			FlowNodes flowNodes = process.getFlowNodes();
			if (null != flowNodes) {
				if (null != flowNodes) {
					List<FlowNodeElement> flowNodeElements = flowNodes
							.getFlowNodeElement();
					if (null != flowNodeElements && !flowNodeElements.isEmpty()) {
						boolean hasStartEvent = false;
						boolean hasEndEvent = false;

						for (FlowNodeElement flowNodeElement : flowNodeElements) {
							/*
							 * Check process have the start event of any type or
							 * not.
							 */
							if (!hasStartEvent) {
								if (ProcessConstants.EVENT_START_NONE_TOOLID
										.equals(toolId(flowNodeElement))
										|| ProcessConstants.EVENT_START_MESSAGE_TOOLID
												.equals(toolId(flowNodeElement))
										|| ProcessConstants.EVENT_START_SIGNAL
												.equals(toolId(flowNodeElement))
										|| ProcessConstants.EVENT_START_TIMER_TOOLID
												.equals(toolId(flowNodeElement))) {
									hasStartEvent = true;
								}
							}
							/*
							 * Check process have the end event of any type or
							 * not.
							 */
							if (!hasEndEvent) {
								if (ProcessConstants.EVENT_END_NONE_TOOLID
										.equals(toolId(flowNodeElement))
										|| ProcessConstants.EVENT_END_MESSAGE_TOOLID
												.equals(toolId(flowNodeElement))
										|| ProcessConstants.EVENT_END_SIGNAL_TOOLID
												.equals(toolId(flowNodeElement))
										|| ProcessConstants.EVENT_END_ERROR_TOOLID
												.equals(toolId(flowNodeElement))) {
									hasEndEvent = true;
								}
							}
							if (null == flowNodeElement.getResourcePath()
									|| flowNodeElement.getResourcePath().trim()
											.isEmpty()) {
								validateResource(commandData, flowNodeElement);
							}
							validateKeyExpression(commandData, flowNodeElement);
							validateMergeExpression(commandData,
									flowNodeElement);
							validateJoinRuleFunction(commandData,
									flowNodeElement);
							validateForkRuleFunction(commandData,
									flowNodeElement);
							validateEndPointURL(commandData, flowNodeElement);
							validateSoapAction(commandData, flowNodeElement);
						}
						if (!hasStartEvent) {
							Validation validation = new Validation();
							validation
									.setValidationErrorType(ValidationErrorType.NO_START_EVENT);
							validation.setLocation(process.getProcessId());
							commandData.add(validation);
						}
						if (!hasEndEvent) {
							Validation validation = new Validation();
							validation
									.setValidationErrorType(ValidationErrorType.NO_END_EVENT);
							validation.setLocation(process.getProcessId());
							commandData.add(validation);
						}
					}
				}
			} else {
				validateProcess(commandData, process);
			}
			result.setCommandData(commandData);

		}else {
			result = null;
		}
		return result;
	}

	/**
	 * This method is used to validate the process.
	 * 
	 * @param commandData
	 * @param process
	 */
	private void validateProcess(List<Serializable> commandData, Process process) {
		Validation processValidation = new Validation();
		processValidation
				.setValidationErrorType(ValidationErrorType.EMPTY_PROCESS);
		processValidation.setLocation(process.getProcessId());
		commandData.add(processValidation);
		Validation endEventValidation = new Validation();
		endEventValidation
				.setValidationErrorType(ValidationErrorType.NO_END_EVENT);
		endEventValidation.setLocation(process.getProcessId());
		commandData.add(endEventValidation);
		Validation startValidation = new Validation();
		startValidation
				.setValidationErrorType(ValidationErrorType.NO_START_EVENT);
		startValidation.setLocation(process.getProcessId());
		commandData.add(startValidation);
	}

	/**
	 * This method is used to validate the soap action
	 * 
	 * @param commandData
	 * @param flowNodeElement
	 */
	private void validateSoapAction(List<Serializable> commandData,
			FlowNodeElement flowNodeElement) {

		if (ProcessElementTypes.ServiceTask.getName().equals(
				flowNodeElement.getElementType())
				&& (null == flowNodeElement.getSoapAction() || flowNodeElement
						.getSoapAction().trim().isEmpty())) {
			Validation validation = new Validation();
			validation
					.setValidationErrorType(ValidationErrorType.NO_SOAP_ACTION);
			validation.setLocation(flowNodeElement.getElementId());
			commandData.add(validation);
		}
	}

	/**
	 * This method used to validate the end point url.
	 * 
	 * @param commandData
	 * @param flowNodeElement
	 */
	private void validateEndPointURL(List<Serializable> commandData,
			FlowNodeElement flowNodeElement) {

		if (ProcessElementTypes.ServiceTask.getName().equals(
				flowNodeElement.getElementType())
				&& (null == flowNodeElement.getTransport() || flowNodeElement
						.getTransport().trim().isEmpty())) {
			Validation validation = new Validation();
			validation.setValidationErrorType(ValidationErrorType.NO_END_POINT);
			validation.setLocation(flowNodeElement.getElementId());
			commandData.add(validation);
		}

	}

	/**
	 * This method is used to get the tool id
	 * 
	 * @param flowNodeElement
	 * @return
	 */
	private String toolId(FlowNodeElement flowNodeElement) {
		return flowNodeElement.getToolId();
	}

	/**
	 * This method is used to validate the key expression.
	 * 
	 * @param commandData
	 * @param flowNodeElement
	 */
	private void validateKeyExpression(List<Serializable> commandData,
			FlowNodeElement flowNodeElement) {
		if (ProcessElementTypes.ReceiveTask.getName().equals(
				flowNodeElement.getElementType())
				&& (null == flowNodeElement.getKeyExpression() || flowNodeElement
						.getKeyExpression().trim().isEmpty())) {
			Validation validation = new Validation();
			validation
					.setValidationErrorType(ValidationErrorType.NO_KEY_EXPRESSION);
			validation.setLocation(flowNodeElement.getElementId());
			commandData.add(validation);
		}
	}

	/**
	 * This method is used to validate Merge expression
	 * 
	 * @param commandData
	 * @param flowNodeElement
	 */
	private void validateMergeExpression(List<Serializable> commandData,
			FlowNodeElement flowNodeElement) {
		if (ProcessElementTypes.ParallelGateway.getName().equals(
				flowNodeElement.getElementType())) {
			String incoming = flowNodeElement.getIncoming();
			if (null != incoming && !incoming.trim().isEmpty()) {
				if (incoming.contains(",")) {
					String[] incomingParts = incoming.split(",");
					if (incomingParts.length > 1) {
						if (null == flowNodeElement.getMergeExpression()
								|| flowNodeElement.getMergeExpression().trim()
										.isEmpty()) {
							Validation validation = new Validation();
							validation
									.setValidationErrorType(ValidationErrorType.NO_MERGE_EXPRESSION);
							validation.setLocation(flowNodeElement
									.getElementId());
							commandData.add(validation);
						}
					}
				}

			}

		}
	}

	/**
	 * This method is used to validate join function.
	 * 
	 * @param commandData
	 * @param flowNodeElement
	 */
	private void validateJoinRuleFunction(List<Serializable> commandData,
			FlowNodeElement flowNodeElement) {
		if (ProcessElementTypes.ParallelGateway.getName().equals(
				flowNodeElement.getElementType())) {
			String incoming = flowNodeElement.getIncoming();
			if (null != incoming && !incoming.trim().isEmpty()) {
				if (incoming.contains(",")) {
					String[] incomingParts = incoming.split(",");
					if (incomingParts.length > 1) {
						if (null == flowNodeElement.getJoinRuleFunction()
								|| flowNodeElement.getJoinRuleFunction().trim()
										.isEmpty()) {
							Validation validation = new Validation();
							validation
									.setValidationErrorType(ValidationErrorType.NO_JOIN_RULE_FUNCTION);
							validation.setLocation(flowNodeElement
									.getElementId());
							commandData.add(validation);
						}
					}
				}

			}

		}
	}

	/**
	 * This method is used validate fork rule function.
	 * 
	 * @param commandData
	 * @param flowNodeElement
	 */
	private void validateForkRuleFunction(List<Serializable> commandData,
			FlowNodeElement flowNodeElement) {
		if (ProcessElementTypes.ParallelGateway.getName().equals(
				flowNodeElement.getElementType())) {
			String outgoing = flowNodeElement.getOutgoing();
			if (null != outgoing && !outgoing.trim().isEmpty()) {
				if (outgoing.contains(",")) {
					String[] outGoingParts = outgoing.split(",");
					if (outGoingParts.length > 1) {
						if (null == flowNodeElement.getForkRuleFunction()
								|| flowNodeElement.getForkRuleFunction().trim()
										.isEmpty()) {
							Validation validation = new Validation();
							validation
									.setValidationErrorType(ValidationErrorType.NO_FORK_RULE_FUNCTION);
							validation.setLocation(flowNodeElement
									.getElementId());
							commandData.add(validation);
						}
					}
				}

			}

		}
	}

	/**
	 * This method is used to validate the Resource.
	 * 
	 * @param commandData
	 * @param flowNodeElement
	 */
	private void validateResource(List<Serializable> commandData,
			FlowNodeElement flowNodeElement) {
		if (ProcessElementTypes.RuleFunctionTask.getName().equals(
				flowNodeElement.getElementType())
				|| ProcessElementTypes.BusinessRuleTask.getName().equals(
						flowNodeElement.getElementType())
				|| ProcessElementTypes.SendTask.getName().equals(
						flowNodeElement.getElementType())
				|| ProcessElementTypes.ReceiveTask.getName().equals(
						flowNodeElement.getElementType())
				|| ProcessElementTypes.InferenceTask.getName().equals(
						flowNodeElement.getElementType())
				|| ProcessElementTypes.ErrorEventDefinition.getName().equals(
						flowNodeElement.getElementType())
				|| ProcessElementTypes.ServiceTask.getName().equals(
						flowNodeElement.getElementType())
				|| ProcessConstants.EVENT_END_MESSAGE_TOOLID
						.equals(toolId(flowNodeElement))
				|| ProcessConstants.EVENT_END_SIGNAL_TOOLID
						.equals(toolId(flowNodeElement))
				|| ProcessConstants.EVENT_END_ERROR_TOOLID
						.equals(toolId(flowNodeElement))
				|| ProcessConstants.EVENT_START_MESSAGE_TOOLID
						.equals(toolId(flowNodeElement))
				|| ProcessConstants.EVENT_START_SIGNAL
						.equals(toolId(flowNodeElement))) {
			Validation validation = new Validation();
			validation.setValidationErrorType(ValidationErrorType.NO_RESOURCE);
			validation.setLocation(flowNodeElement.getElementId());
			commandData.add(validation);
		}
	}
}
