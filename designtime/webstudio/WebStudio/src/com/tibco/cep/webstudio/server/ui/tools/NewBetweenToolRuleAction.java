package com.tibco.cep.webstudio.server.ui.tools;

import java.util.List;

import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.webstudio.client.process.ProcessConstants;
import com.tibco.cep.webstudio.server.command.ProcessDataUtils;
import com.tibco.cep.webstudio.server.model.ProcessElementTypes;
import com.tibco.cep.webstudio.server.model.process.FlowNodeElement;
import com.tibco.cep.webstudio.server.model.process.Process;
import com.tibco.cep.webstudio.server.ui.ProcessActiveToolRepository;
import com.tibco.cep.webstudio.server.ui.ProcessWebDiagramDataRepository;
import com.tibco.cep.webstudio.server.ui.utils.ProcessDiagramUtils;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.TSCommand;
import com.tomsawyer.model.TSModelElement;
import com.tomsawyer.util.shared.TSAttributedObject;
import com.tomsawyer.view.behavior.toolcustomization.rule.action.TSWebToolRuleAction;
import com.tomsawyer.view.drawing.TSModelDrawingView;
import com.tomsawyer.view.drawing.command.TSInsertModelElementBetweenCommand;
import com.tomsawyer.view.mapping.TSMapper;
import com.tomsawyer.web.server.drawing.TSWebCanvas;

/**
 * @author dijadhav
 * 
 */
public class NewBetweenToolRuleAction implements TSWebToolRuleAction {
	ProcessActiveToolRepository activeToolManager = ProcessActiveToolRepository
			.getInstance();

	@Override
	public TSCommand onAction(Object object, TSModelDrawingView view,
			TSConstPoint mouseLocation) {
		String type = getType();
		if (isSatisfied(object, view)) {
			List objectList = (List) object;

			Object sourceObject = objectList.get(0);
			Object targetObject = objectList.get(1);

			TSMapper mapper = view.getMapper();

			TSModelElement sourceElement = (TSModelElement) mapper
					.getMappedObjects(sourceObject).iterator().next();

			TSModelElement targetElement = (TSModelElement) mapper
					.getMappedObjects(targetObject).iterator().next();

			if (sourceElement != null && targetElement != null) {
				String sourceID = (String) sourceElement
						.getAttributeValue(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_ID);

				String targetID = (String) targetElement
						.getAttributeValue(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_ID);

				ProcessActiveToolRepository activeToolManager = ProcessActiveToolRepository
						.getInstance();

				String elementType = activeToolManager.getActiveToolType();

				String typename = elementType.trim().replaceAll(" ", "_");

				// Set the Current tool name to Select Tool
				((TSWebCanvas) view.getCanvas())
						.setCurrentToolName("Select Tool");
				BpmnModelElement bpmnModelElement = new BpmnModelElement();
				bpmnModelElement.setElementId(type);
				bpmnModelElement.setElementName(typename);
				bpmnModelElement.setElementType(type);
				bpmnModelElement.setSourceRefernceId(sourceID);
				bpmnModelElement.setTargetReferenceId(targetID);
				bpmnModelElement.setNewElement(true);

				TSModelElement sequenceElement = ProcessDiagramUtils
						.addSequenceElement(view, activeToolManager,
								bpmnModelElement);
				view.selectViewObject(sequenceElement);
				((TSWebCanvas) view.getCanvas()).fitInCanvas(true);
				((TSWebCanvas) view.getCanvas()).setSyncViewportInClient(true);
				return new TSInsertModelElementBetweenCommand(view,
						sequenceElement);
			}

		}
		return null;

	}

	private String getType() {
		String emfTypeAttribute = activeToolManager.getActiveToolEmfType();
		if (emfTypeAttribute != null) {
			String emfType = emfTypeAttribute.toString();
			EClass emfTypeClass = BpmnMetaModel.getInstance().getEClass(
					ExpandedName.parse(emfType));
			if (null != emfTypeClass) {
				return emfTypeClass.getName();
			}
		}
		return null;

	}

	private boolean isSatisfied(Object object, TSModelDrawingView view) {
		boolean isSatisfied = false;

		if (object instanceof List && ((List) object).size() >= 2) {
			String type = getType();
			List objectList = (List) object;

			TSGraphObject sourceObject = (TSGraphObject) objectList.get(0);
			TSGraphObject targetObject = (TSGraphObject) objectList.get(1);
			Process process = ProcessWebDiagramDataRepository.getInstance()
					.getProcess(
							ProcessActiveToolRepository.getInstance()
									.getViewId());
			if (isValidSourceAndDestination(sourceObject, targetObject)) {
				TSModelElement sourceElement = (TSModelElement) getAttributedObject(
						view, sourceObject);

				TSModelElement targetElement = (TSModelElement) getAttributedObject(
						view, targetObject);

				String sourceId = (String) sourceElement
						.getAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_ID);

				String targetId = (String) targetElement
						.getAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_ID);

				if(sourceId.equalsIgnoreCase(targetId)){
					isSatisfied = false;
				}else
					// Add the association if source or destination is annotation.
				if (type.equals(ProcessElementTypes.Association.getName())) {

					if (sourceId.contains(ProcessElementTypes.TextAnnotation
							.getName())
							|| targetId
									.contains(ProcessElementTypes.TextAnnotation
											.getName())) {
						isSatisfied = true;
					} 
					
					if (sourceId
							.contains(ProcessElementTypes.TextAnnotation
									.getName())
							& targetId
									.contains(ProcessElementTypes.TextAnnotation
											.getName())) {
						isSatisfied = false;
					}
				} else if (type.equals(ProcessElementTypes.SequenceFlow
						.getName())) {
					if (sourceId.contains(ProcessElementTypes.TextAnnotation
							.getName())
							|| targetId
									.contains(ProcessElementTypes.TextAnnotation
											.getName())) {
						isSatisfied = false;
					} else {
						FlowNodeElement sourceFlowNodeElement = ProcessDataUtils
								.getFlowNodeElement(process, sourceId);
						FlowNodeElement targetFlowNodeElement = ProcessDataUtils
								.getFlowNodeElement(process, targetId);
						isSatisfied = isSequenceAllowed(sourceFlowNodeElement,
								targetFlowNodeElement);
					}
				}
			}

		}

		return isSatisfied;
	}

	/**
	 * This method is used to check whether sequence drawing allowed or not.
	 * 
	 * @param sourceFlowNodeElement
	 * @param targetFlowNodeElement
	 * @return
	 */
	private boolean isSequenceAllowed(FlowNodeElement sourceFlowNodeElement,
			FlowNodeElement targetFlowNodeElement) {
		boolean isSourceValid = validateSource(sourceFlowNodeElement);
		boolean isTargetValid = validateTarget(targetFlowNodeElement);
		return isSourceValid && isTargetValid;
	}

	/**
	 * This method is used to check whether the passed target is valid or not
	 * 
	 * @param targetFlowNodeElement
	 * @return
	 */
	private boolean validateTarget(FlowNodeElement targetFlowNodeElement) {
		boolean result = false;
		int inDegree = getIndegree(targetFlowNodeElement.getIncoming());
		if (ProcessConstants.EVENT_START_NONE_TOOLID
				.equals(toolId(targetFlowNodeElement))
				|| ProcessConstants.EVENT_START_MESSAGE_TOOLID
						.equals(toolId(targetFlowNodeElement))
				|| ProcessConstants.EVENT_START_SIGNAL
						.equals(toolId(targetFlowNodeElement))
				|| ProcessConstants.EVENT_START_TIMER_TOOLID
						.equals(toolId(targetFlowNodeElement))) {

			result = false;
		}
		if (0 == inDegree
				&& ProcessElementTypes.ExclusiveGateway.getName().equals(
						targetFlowNodeElement.getElementType())) {
			result = true;
		}
		if (ProcessElementTypes.ParallelGateway.getName().equals(
				targetFlowNodeElement.getElementType())
				|| ProcessElementTypes.RuleFunctionTask.getName().equals(
						targetFlowNodeElement.getElementType())
				|| ProcessElementTypes.BusinessRuleTask.getName().equals(
						targetFlowNodeElement.getElementType())
				|| ProcessElementTypes.SendTask.getName().equals(
						targetFlowNodeElement.getElementType())
				|| ProcessElementTypes.ReceiveTask.getName().equals(
						targetFlowNodeElement.getElementType())
				|| ProcessElementTypes.ServiceTask.getName().equals(
						targetFlowNodeElement.getElementType())
				|| ProcessElementTypes.InferenceTask.getName().equals(
						targetFlowNodeElement.getElementType())
				|| ProcessElementTypes.CallActivity.getName().equals(
						targetFlowNodeElement.getElementType())) {
			result = true;
		}
		if (ProcessConstants.EVENT_END_NONE_TOOLID
				.equals(toolId(targetFlowNodeElement))
				|| ProcessConstants.EVENT_END_MESSAGE_TOOLID
						.equals(toolId(targetFlowNodeElement))
				|| ProcessConstants.EVENT_END_SIGNAL_TOOLID
						.equals(toolId(targetFlowNodeElement))
				|| ProcessConstants.EVENT_END_ERROR_TOOLID
						.equals(toolId(targetFlowNodeElement))) {
			result = true;
		}

		return result;
	}

	/**
	 * This method is used to check whether the passed source is valid or not.
	 * 
	 * @param sourceFlowNodeElement
	 * @return
	 */
	private boolean validateSource(FlowNodeElement sourceFlowNodeElement) {
		boolean result = false;
		int outDegree = getOutdegree(sourceFlowNodeElement.getOutgoing());
		if (0 == outDegree) {
			if (ProcessConstants.EVENT_START_NONE_TOOLID
					.equals(toolId(sourceFlowNodeElement))
					|| ProcessConstants.EVENT_START_MESSAGE_TOOLID
							.equals(toolId(sourceFlowNodeElement))
					|| ProcessConstants.EVENT_START_SIGNAL
							.equals(toolId(sourceFlowNodeElement))
					|| ProcessConstants.EVENT_START_TIMER_TOOLID
							.equals(toolId(sourceFlowNodeElement))) {

				result = true;
			} else if (ProcessElementTypes.RuleFunctionTask.getName().equals(
					sourceFlowNodeElement.getElementType())
					|| ProcessElementTypes.BusinessRuleTask.getName().equals(
							sourceFlowNodeElement.getElementType())
					|| ProcessElementTypes.SendTask.getName().equals(
							sourceFlowNodeElement.getElementType())
					|| ProcessElementTypes.ReceiveTask.getName().equals(
							sourceFlowNodeElement.getElementType())
					|| ProcessElementTypes.ServiceTask.getName().equals(
							sourceFlowNodeElement.getElementType())
					|| ProcessElementTypes.InferenceTask.getName().equals(
							sourceFlowNodeElement.getElementType())
					|| ProcessElementTypes.CallActivity.getName().equals(
							sourceFlowNodeElement.getElementType())) {
				result = true;
			}
		}
		if (ProcessConstants.EVENT_END_NONE_TOOLID
				.equals(toolId(sourceFlowNodeElement))
				|| ProcessConstants.EVENT_END_MESSAGE_TOOLID
						.equals(toolId(sourceFlowNodeElement))
				|| ProcessConstants.EVENT_END_SIGNAL_TOOLID
						.equals(toolId(sourceFlowNodeElement))
				|| ProcessConstants.EVENT_END_ERROR_TOOLID
						.equals(toolId(sourceFlowNodeElement))) {
			result = false;
		}
		if (ProcessElementTypes.ParallelGateway.getName().equals(
				sourceFlowNodeElement.getElementType())
				|| ProcessElementTypes.ExclusiveGateway.getName().equals(
						sourceFlowNodeElement.getElementType())) {
			result = true;
		}

		return result;
	}

	/**
	 * This method is used to find the in degree.
	 * 
	 * @param incoming
	 * @return
	 */
	private int getIndegree(String incoming) {
		int inDegree = 0;
		if (null != incoming && !incoming.trim().isEmpty()) {
			if (incoming.contains(ProcessConstants.COMMA)) {
				String[] incomingParts = incoming.split(ProcessConstants.COMMA);
				inDegree = incomingParts.length;
			} else {
				inDegree = 1;
			}
		}
		return inDegree;
	}

	/**
	 * This method is used to find the out degree.
	 * 
	 * @param outgoing
	 * @return
	 */
	private int getOutdegree(String outgoing) {
		int outDegree = 0;
		if (null != outgoing && !outgoing.trim().isEmpty()) {
			if (outgoing.contains(ProcessConstants.COMMA)) {
				String[] outGoingParts = outgoing.split(ProcessConstants.COMMA);
				outDegree = outGoingParts.length;
			} else {
				outDegree = 1;
			}
		}
		return outDegree;
	}

	/**
	 * This method is used to check the source and target object are valid or
	 * not.
	 * 
	 * @param sourceObject
	 * @param targetObject
	 * @return
	 */
	private boolean isValidSourceAndDestination(TSGraphObject sourceObject,
			TSGraphObject targetObject) {
		return (null != sourceObject && sourceObject instanceof TSENode)
				&& (null != sourceObject && targetObject instanceof TSENode) ? true
				: false;
	}

	/**
	 * This method is used to get attributed object from given graph object.
	 * 
	 * @param view
	 * @param graphObject
	 * @return
	 */
	private TSAttributedObject getAttributedObject(TSModelDrawingView view,
			TSGraphObject graphObject) {
		return view.getMapper().getAttributedObject(graphObject);
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

}
