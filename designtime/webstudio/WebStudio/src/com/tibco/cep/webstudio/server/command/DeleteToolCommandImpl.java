package com.tibco.cep.webstudio.server.command;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.webstudio.client.process.ProcessConstants;
import com.tibco.cep.webstudio.server.model.ProcessElementTypes;
import com.tibco.cep.webstudio.server.model.process.AnnotationElement;
import com.tibco.cep.webstudio.server.model.process.AssociationElement;
import com.tibco.cep.webstudio.server.model.process.FlowNodeElement;
import com.tibco.cep.webstudio.server.model.process.Process;
import com.tibco.cep.webstudio.server.model.process.SequenceElement;
import com.tibco.cep.webstudio.server.ui.ProcessWebDiagramDataRepository;
import com.tibco.cep.webstudio.server.ui.utils.ProcessDiagramUtils;
import com.tomsawyer.model.TSModel;
import com.tomsawyer.model.TSModelElement;
import com.tomsawyer.model.events.TSModelChangeEvent;
import com.tomsawyer.model.events.TSModelChangeEventData;
import com.tomsawyer.util.gwtclient.command.TSServiceCommand;
import com.tomsawyer.util.shared.TSAttributedObject;
import com.tomsawyer.util.shared.TSServiceException;
import com.tomsawyer.view.drawing.TSModelDrawingView;
import com.tomsawyer.web.client.command.TSCustomCommand;
import com.tomsawyer.web.server.command.TSServiceCommandImpl;
import com.tomsawyer.web.server.drawing.TSWebCanvas;
import com.tomsawyer.web.server.service.TSPerspectivesViewService;

/**
 * This class is used to execute to delete the selected element.
 * 
 * @author dijadhav
 * 
 */
public class DeleteToolCommandImpl implements TSServiceCommandImpl {
	private static final String COMMA = ",";

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
			TSServiceCommand serviceCommand) throws TSServiceException {

		String viewID = ((TSCustomCommand) serviceCommand).getViewID();
		TSModelDrawingView drawingView = (TSModelDrawingView) service.getView(
				((TSCustomCommand) serviceCommand).getProjectID(), viewID,
				false);
		if (drawingView != null) {
			Set<TSModelElement> modelElements = new LinkedHashSet<TSModelElement>();
			List<TSAttributedObject> selectedObjects = drawingView
					.getSelectedAttributedObjects();
			Process process = ProcessWebDiagramDataRepository.getInstance()
					.getProcess(viewID);

			Iterator<TSAttributedObject> selectionIter = selectedObjects
					.iterator();
			TSModel model = drawingView.getModel();
			
			Set<FlowNodeElement> flowNodeElements = new LinkedHashSet<FlowNodeElement>();
			Set<AnnotationElement> annotataionElements = new LinkedHashSet<AnnotationElement>();
			Set<AssociationElement> associationElements = new LinkedHashSet<AssociationElement>();
			Set<SequenceElement> sequenceElementlElements = new LinkedHashSet<SequenceElement>();
			Map<String,FlowNodeElement>incomingSquenceFlowNodeMap=new LinkedHashMap<String,FlowNodeElement>();
			Map<String,FlowNodeElement>outgoingSquenceFlowNodeMap=new LinkedHashMap<String,FlowNodeElement>();
			 
			 while (selectionIter.hasNext()) {
				TSModelElement element = (TSModelElement) selectionIter.next();

				String type = (String) element
						.getAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_TYPE);
				if(null==type || type.trim().isEmpty()){
					 type = (String) element
								.getAttribute(ProcessConstants.PROCESS_SEQUENCE_ATTRIBUTE_TYPE);
				}
				if (ProcessElementTypes.SequenceFlow.getName()
						.equalsIgnoreCase(type)) {
					updateOutgoing(model, element, process,outgoingSquenceFlowNodeMap);
					updateIncoming(model, element, process,incomingSquenceFlowNodeMap);
					SequenceElement sequenceFlowElement =ProcessDataUtils.getSequeceElement((String) element
							.getAttribute(ProcessConstants.PROCESS_SEQUENCE_ATTRIBUTE_ID), process.getSequenceFlows().getSequenceElement());
					sequenceElementlElements.add(sequenceFlowElement);

				} else if (!ProcessElementTypes.TextAnnotation.getName()
						.equalsIgnoreCase(type)
						&& !ProcessElementTypes.Association.getName()
								.equalsIgnoreCase(type)) {
					FlowNodeElement flowNodeElement = ProcessDataUtils
							.getFlowNodeElement(
									process,
									(String) element
											.getAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_ID));

					deleteOutGoingSequence(model, flowNodeElement,
							modelElements, process,sequenceElementlElements,incomingSquenceFlowNodeMap,outgoingSquenceFlowNodeMap);
					deleteIncomingSequence(model, flowNodeElement,
							modelElements, process,sequenceElementlElements,incomingSquenceFlowNodeMap,outgoingSquenceFlowNodeMap);
					flowNodeElements.add(flowNodeElement);		
				}
				if (ProcessElementTypes.TextAnnotation.getName()
						.equalsIgnoreCase(type)){
					AnnotationElement annotationElement =ProcessDataUtils.getAnnotationElement((String) element
							.getAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_ID), process.getAnnotations().getAnnotationElement());
					annotataionElements.add(annotationElement);
				}
				if (ProcessElementTypes.Association.getName()
						.equalsIgnoreCase(type)){
					AssociationElement associationElement =ProcessDataUtils.getAssociationElement((String) element
							.getAttribute(ProcessConstants.PROCESS_SEQUENCE_ATTRIBUTE_ID), process.getAssociations().getAssociationElement());
					associationElements.add(associationElement);
				}
				modelElements.add(element);
			}

			/*
			 * //Remove the elements from model for (TSModelElement modelElement
			 * : modelElements) { if (null != modelElement) {
			 * processModel.removeElement(modelElement); } }
			 * 
			 * TSEDeleteSelectedCommand removeModelElementsCommand = new
			 * TSEDeleteSelectedCommand( drawingView.getCanvas());
			 * removeModelElementsCommand.setAddToUndoHistory(true);
			 * drawingView.getModel().getCommandManager()
			 * .transmit(removeModelElementsCommand); drawingView.updateView();
			 */
			boolean wasFiringEvents = drawingView.getModel().getEventManager().isFiringEvents();
			drawingView.getModel().getEventManager().setFireEvents(false);
			try {

				ProcessElementDeleteCommand processElementDeleteCommand = new ProcessElementDeleteCommand();
				processElementDeleteCommand.setView(drawingView);
				processElementDeleteCommand.setModelElements(modelElements);
				processElementDeleteCommand.setAddToUndoHistory(true);
				processElementDeleteCommand.setOwnerModel(drawingView
						.getModel());
				processElementDeleteCommand.setFlowNodeElements(flowNodeElements);
				processElementDeleteCommand.setSequenceElementlElements(sequenceElementlElements);
				processElementDeleteCommand.setAnnotataionElements(annotataionElements);
				processElementDeleteCommand.setAssociationElements(associationElements);
				processElementDeleteCommand.setOutgoingSquenceFlowNodeMap(outgoingSquenceFlowNodeMap);
				processElementDeleteCommand.setIncomingSquenceFlowNodeMap(incomingSquenceFlowNodeMap);
				processElementDeleteCommand.setProcess(process);
				processElementDeleteCommand.setViewId(viewID);
				drawingView.getModel().getCommandManager()
						.transmit(processElementDeleteCommand);
			} finally {
				model.getEventManager().setFireEvents(wasFiringEvents);
			}
			((TSWebCanvas) drawingView.getCanvas()).fitInCanvas(true);
			model.getEventManager().fireEvent(
					new TSModelChangeEvent(TSModelChangeEvent.MODEL_ELEMENT_REMOVED,
							new TSModelChangeEventData(model, null, null)));
			ProcessDiagramUtils.globalLayoutUpdate(drawingView);
		}
		return Boolean.TRUE;
	}

	/**
	 * This method is used to delete the outgoing sequences from passed node.
	 * 
	 * @param model
	 * @param flowNodeElement
	 * @param modelElements
	 * @param process
	 * @param sequenceElementlElements 
	 * @param outgoingSquenceFlowNodeMap 
	 * @param incomingSquenceFlowNodeMap 
	 */
	private void deleteOutGoingSequence(TSModel model,
			FlowNodeElement flowNodeElement, Set<TSModelElement> modelElements,
			Process process, Set<SequenceElement> sequenceElementlElements, Map<String, FlowNodeElement> incomingSquenceFlowNodeMap, Map<String, FlowNodeElement> outgoingSquenceFlowNodeMap) {
		String outgoings = flowNodeElement.getOutgoing();
		if (null != outgoings && !outgoings.trim().isEmpty()) {
			if (outgoings.contains(COMMA)) {
				String[] outgoingArr = outgoings.split(COMMA);
				if (null != outgoingArr && outgoingArr.length > 0) {
					for (String outgoing : outgoingArr) {
						TSModelElement outgoingElement = ProcessDataUtils
								.getModelElementById(model, outgoing);
						if (null != outgoingElement) {
							updateOutgoing(model, outgoingElement, process, outgoingSquenceFlowNodeMap);
							updateIncoming(model, outgoingElement, process,incomingSquenceFlowNodeMap);
							modelElements.add(outgoingElement);
							SequenceElement sequenceFlowElement =ProcessDataUtils.getSequeceElement(outgoing, process.getSequenceFlows().getSequenceElement());
							sequenceElementlElements.add(sequenceFlowElement);
						}
					}
				}
			} else {
				TSModelElement outgoingElement = ProcessDataUtils
						.getModelElementById(model, outgoings);
				if (null != outgoingElement) {
					updateOutgoing(model, outgoingElement, process,outgoingSquenceFlowNodeMap);
					updateIncoming(model, outgoingElement, process,incomingSquenceFlowNodeMap);
					modelElements.add(outgoingElement);
					SequenceElement sequenceFlowElement =ProcessDataUtils.getSequeceElement(outgoings, process.getSequenceFlows().getSequenceElement());
					sequenceElementlElements.add(sequenceFlowElement);
				}
			}
		}
	}

	/**
	 * This method is used to delete the incoming sequences from passed node.
	 * 
	 * @param model
	 * @param flowNodeElement
	 * @param modelElements
	 * @param process
	 * @param sequenceElementlElements 
	 * @param outgoingSquenceFlowNodeMap 
	 * @param incomingSquenceFlowNodeMap 
	 */
	private void deleteIncomingSequence(TSModel model,
			FlowNodeElement flowNodeElement, Set<TSModelElement> modelElements,
			Process process, Set<SequenceElement> sequenceElementlElements, Map<String, FlowNodeElement> incomingSquenceFlowNodeMap, Map<String, FlowNodeElement> outgoingSquenceFlowNodeMap) {
		String incomings = flowNodeElement.getIncoming();
		if (null != incomings && !incomings.trim().isEmpty()) {
			if (incomings.contains(COMMA)) {
				String[] incomingArr = incomings.split(COMMA);
				if (null != incomingArr && incomingArr.length > 0) {
					for (String incoming : incomingArr) {
						TSModelElement incomingElement = ProcessDataUtils
								.getModelElementById(model, incoming);
						if (null != incomingElement) {
							updateOutgoing(model, incomingElement, process,outgoingSquenceFlowNodeMap);
							updateIncoming(model, incomingElement, process,incomingSquenceFlowNodeMap);
							modelElements.add(incomingElement);
							SequenceElement sequenceFlowElement =ProcessDataUtils.getSequeceElement(incoming, process.getSequenceFlows().getSequenceElement());
							sequenceElementlElements.add(sequenceFlowElement);
						}
					}
				}
			} else {
				TSModelElement incomingElement = ProcessDataUtils
						.getModelElementById(model, incomings);
				if (null != incomingElement) {
					updateOutgoing(model, incomingElement, process,outgoingSquenceFlowNodeMap);
					updateIncoming(model, incomingElement, process,incomingSquenceFlowNodeMap);
					modelElements.add(incomingElement);
					SequenceElement sequenceFlowElement =ProcessDataUtils.getSequeceElement(incomings, process.getSequenceFlows().getSequenceElement());
					sequenceElementlElements.add(sequenceFlowElement);
				}
			}

		}
	}

	/**
	 * This method is used to update the incoming of the nodes where the passed
	 * Sequence is refereed.
	 * 
	 * @param model
	 * @param sequenceElement
	 * @param incomingSquenceFlowNodeMap 
	 */
	private void updateIncoming(TSModel model, TSModelElement sequenceElement,
			Process process, Map<String, FlowNodeElement> incomingSquenceFlowNodeMap) {
		String targetReference = (String) sequenceElement
				.getAttribute(ProcessConstants.PROCESS_SEQUENCE_ATTRIBUTE_TO_FLOW);
		String sequenceId=(String) sequenceElement
				.getAttribute(ProcessConstants.PROCESS_SEQUENCE_ATTRIBUTE_ID);
		FlowNodeElement flowNodeElement = getModelElementById(process,
				targetReference);
		incomingSquenceFlowNodeMap.put(sequenceId, flowNodeElement);		
	}

	private FlowNodeElement getModelElementById(Process process,
			String targetReference) {
		FlowNodeElement flowNodeElement = null;
		List<FlowNodeElement> flowNodeElements = process.getFlowNodes()
				.getFlowNodeElement();
		if (null != flowNodeElements && !flowNodeElements.isEmpty()) {
			for (FlowNodeElement element : flowNodeElements) {
				if (null != element
						&& element.getElementId().equalsIgnoreCase(
								targetReference)) {
					flowNodeElement = element;
					break;
				}
			}
		}

		return flowNodeElement;
	}

	/**
	 * This method is used to update the outgoing of the nodes where the passed
	 * Sequence is refereed.
	 * 
	 * @param model
	 * @param sequenceElement
	 * @param outgoingSquenceFlowNodeMap 
	 */
	private void updateOutgoing(TSModel model, TSModelElement sequenceElement,
			Process process, Map<String, FlowNodeElement> outgoingSquenceFlowNodeMap) {
		String sourceReference = (String) sequenceElement
				.getAttribute(ProcessConstants.PROCESS_SEQUENCE_ATTRIBUTE_FROM_FLOW);
		String sequenceId=(String) sequenceElement
				.getAttribute(ProcessConstants.PROCESS_SEQUENCE_ATTRIBUTE_ID);
		FlowNodeElement flowNodeElement = getModelElementById(process,
				sourceReference);
		outgoingSquenceFlowNodeMap.put(sequenceId, flowNodeElement);
		String outgoings = flowNodeElement.getOutgoing();
		if (null != outgoings && !outgoings.trim().isEmpty()) {
			if (outgoings.contains(COMMA)) {
				String[] outgoingArr = outgoings.split(COMMA);
				if (null != outgoingArr && outgoingArr.length > 0) {
					int index = 0;
					for (String outgoing : outgoingArr) {
						if (null != outgoing && !outgoing.isEmpty()
								&& outgoing.equals(sourceReference)) {
							outgoingArr[index] = null;	
							
							break;
						}
						index++;
					}
				}
				StringBuilder builder = new StringBuilder();
				if (null != outgoingArr && outgoingArr.length > 0) {
					int index = 0;
					for (String outgoing : outgoingArr) {
						if (null != outgoing && !outgoing.isEmpty()) {
							builder.append(outgoing);
							if (index < outgoingArr.length - 1) {
								builder.append(COMMA);
							}
						}
						index++;
					}
				}
				flowNodeElement.setOutgoing(builder.toString());
			} else {
				outgoingSquenceFlowNodeMap.put(outgoings, flowNodeElement);
				flowNodeElement.setOutgoing("");
			}
		}
	}
	/**
	 * Update the last modification time.
	 * @param viewID 
	 * @param viewId 
	 */
	private void updateteLatModificanTime(String viewID) {
		Process process = ProcessWebDiagramDataRepository.getInstance()
				.getProcess(viewID);
		process.setLastModificationDate(new Date().toString());
	}
	
}
