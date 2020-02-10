package com.tibco.cep.webstudio.server.ui.utils;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.webstudio.client.process.ProcessConstants;
import com.tibco.cep.webstudio.server.command.ProcessDataUtils;
import com.tibco.cep.webstudio.server.model.ProcessElementTypes;
import com.tibco.cep.webstudio.server.model.process.AnnotationElement;
import com.tibco.cep.webstudio.server.model.process.AssociationElement;
import com.tibco.cep.webstudio.server.model.process.BendPointList;
import com.tibco.cep.webstudio.server.model.process.BendPoints;
import com.tibco.cep.webstudio.server.model.process.EndPoint;
import com.tibco.cep.webstudio.server.model.process.EventDefinition;
import com.tibco.cep.webstudio.server.model.process.FlowNodeElement;
import com.tibco.cep.webstudio.server.model.process.LabelPoint;
import com.tibco.cep.webstudio.server.model.process.Lane;
import com.tibco.cep.webstudio.server.model.process.Lanes;
import com.tibco.cep.webstudio.server.model.process.MessageStarter;
import com.tibco.cep.webstudio.server.model.process.MessageStarterList;
import com.tibco.cep.webstudio.server.model.process.NodePoint;
import com.tibco.cep.webstudio.server.model.process.Process;
import com.tibco.cep.webstudio.server.model.process.SequenceElement;
import com.tibco.cep.webstudio.server.model.process.StartPoint;
import com.tibco.cep.webstudio.server.ui.ProcessActiveToolRepository;
import com.tibco.cep.webstudio.server.ui.ProcessWebDiagramDataRepository;
import com.tibco.cep.webstudio.server.ui.tools.BpmnModelElement;
import com.tomsawyer.drawing.TSGraphTailor;
import com.tomsawyer.drawing.TSPEdge;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.editing.TSEGlobalLayoutCommand;
import com.tomsawyer.interactive.command.editing.TSEIncrementalLayoutCommand;
import com.tomsawyer.model.TSModelElement;
import com.tomsawyer.model.schema.TSSchema;
import com.tomsawyer.service.TSServiceException;
import com.tomsawyer.service.TSServiceOutputData;
import com.tomsawyer.service.layout.TSGeneralLayoutInputTailor;
import com.tomsawyer.service.layout.TSHierarchicalLayoutInputTailor;
import com.tomsawyer.service.layout.TSLayoutConstants;
import com.tomsawyer.service.layout.client.TSApplyLayoutResults;
import com.tomsawyer.service.layout.client.TSLayoutProxy;
import com.tomsawyer.view.TSAbstractModelView;
import com.tomsawyer.view.drawing.TSModelDrawingView;

/**
 * 
 * @author sasahoo
 * 
 */
public class ProcessDiagramUtils {

	/**
	 * For Incremental Layout
	 * 
	 * @param view
	 */
	public static void incrementalLayoutUpdate(TSModelDrawingView view) {
		final TSEIncrementalLayoutCommand c = new TSEIncrementalLayoutCommand(
				view.getCanvas(), view.getServiceInputData());
		c.setFitInCanvasAfterOperation(true);
		c.setThreaded(false);
		view.getCanvas().getCommandManager().transmit(c);
	}

	/**
	 * For all layout except Incremental layout
	 * 
	 * @param view
	 */
	public static void globalLayoutUpdate(TSModelDrawingView view) {
		// TSLayoutConstants.LAYOUT_STYLE_NO_STYLE
		// TSLayoutConstants.LAYOUT_STYLE_CIRCULAR
		// TSLayoutConstants.LAYOUT_STYLE_HIERARCHICAL
		// TSLayoutConstants.LAYOUT_STYLE_ORTHOGONAL
		// TSLayoutConstants.LAYOUT_STYLE_SYMMETRIC
		TSEGlobalLayoutCommand c = new TSEGlobalLayoutCommand(view.getCanvas(),
				view.getServiceInputData(),
				TSLayoutConstants.LAYOUT_STYLE_HIERARCHICAL);
		c.setFitInCanvasAfterOperation(true);
		c.setThreaded(false);
		view.getCanvas().getCommandManager().transmit(c);
	}

	public static void heirachicalLayoutUpdate(TSModelDrawingView view) {
		TSEGlobalLayoutCommand c = new TSEGlobalLayoutCommand(view.getCanvas(),
				view.getServiceInputData(),
				TSLayoutConstants.LAYOUT_STYLE_HIERARCHICAL);
		c.setFitInCanvasAfterOperation(true);
		c.setThreaded(false);
		view.getCanvas().getCommandManager().transmit(c);
	}

	public static void setLayoutOptionsForSubProcess(TSEGraph graph,
			TSModelDrawingView view) {
		TSHierarchicalLayoutInputTailor hierarchicalLayoutInputTailor = new TSHierarchicalLayoutInputTailor(
				view.getServiceInputData());
		hierarchicalLayoutInputTailor.setGraph(graph);
		hierarchicalLayoutInputTailor.setAsCurrentLayoutStyle();
		hierarchicalLayoutInputTailor.setOrthogonalRouting(true);
		hierarchicalLayoutInputTailor.setKeepNodeSizes(true);
		hierarchicalLayoutInputTailor
				.setLevelDirection(TSLayoutConstants.DIRECTION_LEFT_TO_RIGHT);
		TSGraphTailor graphTailor = graph.getTailor();
		// graphTailor.setMargin(5.0);
		graphTailor.setNestedViewSpacing(5.0);
		// graphTailor.setTopNestedViewSpacing(MARGIN);
		// graphTailor.setLeftNestedViewSpacing(20.0);
		// graphTailor.setRightNestedViewSpacing(MARGIN);
		graphTailor.setBottomNestedViewSpacing(10);
	}

	public static void callLayoutService(TSModelDrawingView view)
			throws TSServiceException {
		TSLayoutProxy layoutProxy = new TSLayoutProxy();
		TSServiceOutputData outputData = new TSServiceOutputData();
		layoutProxy.run(view.getServiceInputData(), outputData);

		TSApplyLayoutResults result = new TSApplyLayoutResults();
		result.apply(view.getServiceInputData(), outputData);
	}

	public static void setFitNestedGraph(TSModelDrawingView view,
			TSENode expandedNode, boolean fitNestedGraph) {
		TSGeneralLayoutInputTailor genInputTailor = new TSGeneralLayoutInputTailor(
				view.getServiceInputData());
		genInputTailor.setFitNestedGraph(expandedNode, fitNestedGraph);
	}

	/**
	 * Add Flow Element
	 * 
	 * @param view
	 * @param activeToolManager
	 * @param isNew
	 * @param activeToolManager
	 * @param typename
	 * @param type
	 * @param extendedType
	 * @param elementId
	 * @param label
	 * @return
	 */
	public static TSModelElement addFlowElement(TSAbstractModelView view,
			ProcessActiveToolRepository activeToolManager,
			BpmnModelElement modelElement) {
		Process process = ProcessWebDiagramDataRepository.getInstance()
				.getProcess(activeToolManager.getViewId());
		TSModelElement flowElement = view.getModel().newModelElement("Flow");

		TSSchema schema = view.getViewDefinition().getSchema();

		schema.initModelElement(flowElement);
		if (modelElement.isNewElement()) {

			int i = 0;

			flowElement.setAttribute(
					ProcessConstants.PROCESS_FLOW_ATTRIBUTE_ID,
					activeToolManager.getArtifactName().replace(".beprocess",
							"")
							+ "." + modelElement.getElementName() + "_" + i);
			while (!schema.isUniqueIdentifiers(flowElement, view.getModel())) {
				++i;
				flowElement
						.setAttribute(
								ProcessConstants.PROCESS_FLOW_ATTRIBUTE_ID,
								activeToolManager.getArtifactName().replace(
										".beprocess", "")
										+ "."
										+ modelElement.getElementName()
										+ "_" + i);
			}
			modelElement.setElementId((String) flowElement
					.getAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_ID));

			modelElement.setElementLabel(modelElement.getElementName() + "_"
					+ i);
			modelElement
					.setElementName(modelElement.getElementName() + "_" + i);

			if (null != modelElement.getExtendedType())
				modelElement.setDefinationId(process.getProcessId() + "."
						+ modelElement.getExtendedType() + "_" + i);
			setModelElemnetProperties(modelElement);
			addFlowNodeElement(modelElement, process.getFlowNodes()
					.getFlowNodeElement(), process.getLaneset().getLane()
					.getLaneId());
		} else {
			flowElement.setAttribute(
					ProcessConstants.PROCESS_FLOW_ATTRIBUTE_ID,
					modelElement.getElementId());
			String loopType = modelElement.getLoopType();
			if (null != loopType && !loopType.trim().isEmpty()) {
				flowElement.setAttribute(
						ProcessConstants.PROCESS_FLOW_ATTRIBUTE_LOOP, loopType);
			}
			setModelElemnetProperties(modelElement);
		}

		flowElement.setAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_LABEL,
				modelElement.getElementName());
		/*
		 * flowElement.setAttribute(ProcessConstants.NAME,
		 * modelElement.getElementName());
		 */
		flowElement.setAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_TYPE,
				modelElement.getElementType());
		flowElement.setAttribute(
				ProcessConstants.PROCESS_FLOW_ATTRIBUTE_EXTENDEDTYPE,
				modelElement.getExtendedType());
		flowElement.setAttribute(
				ProcessConstants.PROCESS_FLOW_ATTRIBUTE_PARENT,
				"Main_Display_Graph");

		return flowElement;
	}

	/**
	 * Add Annotation Element
	 * 
	 * @param view
	 * @param isNew
	 * @param activeToolManager
	 * @param typename
	 * @param type
	 * @param extendedType
	 * @param elementId
	 * @param textValue
	 * @return
	 */

	public static TSModelElement addAnnotationElement(TSAbstractModelView view,
			ProcessActiveToolRepository activeToolManager,
			BpmnModelElement modelElement) {
		Process process = ProcessWebDiagramDataRepository.getInstance()
				.getProcess(activeToolManager.getViewId());
		TSModelElement flowElement = view.getModel().newModelElement("Flow");

		TSSchema schema = view.getViewDefinition().getSchema();
		String name = "";
		schema.initModelElement(flowElement);

		if (modelElement.isNewElement()) {
			int i = 0;
			name = modelElement.getElementType() + "_" + i;
			flowElement.setAttribute(
					ProcessConstants.PROCESS_FLOW_ATTRIBUTE_ID,
					activeToolManager.getArtifactName().replace(".beprocess",
							"")
							+ "." + name);
			while (!schema.isUniqueIdentifiers(flowElement, view.getModel())) {
				++i;
				name = modelElement.getElementType() + "_" + i;
				flowElement.setAttribute(
						ProcessConstants.PROCESS_FLOW_ATTRIBUTE_ID,
						activeToolManager.getArtifactName() + "." + name);
			}
			modelElement.setElementId((String) flowElement
					.getAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_ID));
			modelElement.setElementName(name);
			addAnnotationElement(modelElement, process.getAnnotations()
					.getAnnotationElement(), process.getLaneset().getLane()
					.getLaneId());
		} else {
			flowElement.setAttribute(
					ProcessConstants.PROCESS_FLOW_ATTRIBUTE_ID,
					modelElement.getElementId());
		}
		flowElement.setAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_TYPE,
				modelElement.getElementType());

		flowElement.setAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_NAME,
				modelElement.getElementName());
		flowElement.setAttribute(
				ProcessConstants.PROCESS_FLOW_ATTRIBUTE_TEXTVALUE,
				modelElement.getTextValue());
		flowElement.setAttribute(
				ProcessConstants.PROCESS_FLOW_ATTRIBUTE_PARENT,
				"Main_Display_Graph");

		flowElement.setAttribute(ProcessConstants.ATTRIBUTE_EXTENDEDTYPE,
				modelElement.getElementType());
		return flowElement;
	}

	/**
	 * Add Sequence Element
	 * 
	 * @param view
	 * @param isNew
	 * @param activeToolManager
	 * @param typename
	 * @param type
	 * @param elementId
	 * @param sourceID
	 * @param targetID
	 * @return
	 */
	public static TSModelElement addSequenceElement(TSAbstractModelView view,
			ProcessActiveToolRepository activeToolManager,
			BpmnModelElement modelElement) {
		Process process = ProcessWebDiagramDataRepository.getInstance()
				.getProcess(activeToolManager.getViewId());
		TSModelElement sequenceElement = view.getModel().newModelElement(
				"Sequence");

		TSSchema schema = view.getViewDefinition().getSchema();

		schema.initModelElement(sequenceElement);
		if (modelElement.isNewElement()) {
			int i = 0;
			String name = modelElement.getElementId() + "_" + i;
			sequenceElement.setAttribute(
					ProcessConstants.PROCESS_SEQUENCE_ATTRIBUTE_ID,
					activeToolManager.getArtifactName().replace(".beprocess",
							"")
							+ "." + name);
			while (!schema
					.isUniqueIdentifiers(sequenceElement, view.getModel())) {
				++i;
				name = modelElement.getElementId() + "_" + i;
				sequenceElement.setAttribute(
						ProcessConstants.PROCESS_SEQUENCE_ATTRIBUTE_ID,
						activeToolManager.getArtifactName().replace(
								".beprocess", "")
								+ "." + name);
			}
			modelElement.setElementId((String) sequenceElement
					.getAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_ID));
			modelElement.setElementName(name);
			modelElement.setElementLabel(name);
			if (ProcessElementTypes.SequenceFlow.getName().equals(
					modelElement.getElementType())) {
				addSequenceElement(modelElement, process.getSequenceFlows()
						.getSequenceElement(), process.getLaneset().getLane()
						.getLaneId(), process, view);
			} else {
				addAssoiciationElement(modelElement, process.getAssociations()
						.getAssociationElement(), process.getLaneset()
						.getLane().getLaneId());
			}
		} else {

			sequenceElement.setAttribute(
					ProcessConstants.PROCESS_SEQUENCE_ATTRIBUTE_ID,
					modelElement.getElementId());
		}
		sequenceElement.setAttribute(
				ProcessConstants.PROCESS_SEQUENCE_ATTRIBUTE_LABEL,
				modelElement.getElementName());
		sequenceElement.setAttribute(
				ProcessConstants.PROCESS_SEQUENCE_ATTRIBUTE_TYPE,
				modelElement.getElementType());
		sequenceElement.setAttribute(
				ProcessConstants.PROCESS_SEQUENCE_ATTRIBUTE_FROM_FLOW,
				modelElement.getSourceRefernceId());
		sequenceElement.setAttribute(
				ProcessConstants.PROCESS_SEQUENCE_ATTRIBUTE_TO_FLOW,
				modelElement.getTargetReferenceId());

		return sequenceElement;
	}

	private static void setModelElemnetProperties(BpmnModelElement modelElement) {
		if (modelElement.isNewElement()) {
			// Set tool id for element of type start event.
			if (ProcessElementTypes.StartEvent.getName().equals(
					modelElement.getElementType())) {
				if (ProcessElementTypes.MessageEventDefinition.getName()
						.equalsIgnoreCase(modelElement.getExtendedType())) {
					modelElement
							.setToolId(ProcessConstants.EVENT_START_MESSAGE_TOOLID);
				} else if (ProcessElementTypes.SignalEventDefinition.getName()
						.equalsIgnoreCase(modelElement.getExtendedType())) {
					modelElement.setToolId(ProcessConstants.EVENT_START_SIGNAL);
				} else if (ProcessElementTypes.TimerEventDefinition.getName()
						.equalsIgnoreCase(modelElement.getExtendedType())) {
					modelElement
							.setToolId(ProcessConstants.EVENT_START_TIMER_TOOLID);
				} else {
					modelElement
							.setToolId(ProcessConstants.EVENT_START_NONE_TOOLID);
				}
			} else if (ProcessElementTypes.EndEvent.getName().equals(
					modelElement.getElementType())) {
				if (ProcessElementTypes.MessageEventDefinition.getName()
						.equalsIgnoreCase(modelElement.getExtendedType())) {
					modelElement
							.setToolId(ProcessConstants.EVENT_END_MESSAGE_TOOLID);
				} else if (ProcessElementTypes.SignalEventDefinition.getName()
						.equalsIgnoreCase(modelElement.getExtendedType())) {
					modelElement
							.setToolId(ProcessConstants.EVENT_END_SIGNAL_TOOLID);
				} else if (ProcessElementTypes.ErrorEventDefinition.getName()
						.equalsIgnoreCase(modelElement.getExtendedType())) {
					modelElement
							.setToolId(ProcessConstants.EVENT_END_ERROR_TOOLID);
				} else {
					modelElement
							.setToolId(ProcessConstants.EVENT_START_NONE_TOOLID);
				}
			} else if (ProcessElementTypes.ParallelGateway.getName().equals(
					modelElement.getElementType())) {

			} else if (ProcessElementTypes.ParallelGateway.getName().equals(
					modelElement.getElementType())) {

			} else if (ProcessElementTypes.ParallelGateway.getName().equals(
					modelElement.getElementType())) {

			} else if (ProcessElementTypes.ParallelGateway.getName().equals(
					modelElement.getElementType())) {

			} else if (ProcessElementTypes.ParallelGateway.getName().equals(
					modelElement.getElementType())) {

			} else if (ProcessElementTypes.ParallelGateway.getName().equals(
					modelElement.getElementType())) {

			} else if (ProcessElementTypes.ParallelGateway.getName().equals(
					modelElement.getElementType())) {

			} else if (ProcessElementTypes.ParallelGateway.getName().equals(
					modelElement.getElementType())) {

			} else if (ProcessElementTypes.ParallelGateway.getName().equals(
					modelElement.getElementType())) {

			} else if (ProcessElementTypes.ParallelGateway.getName().equals(
					modelElement.getElementType())) {

			}

		} else {
			/*
			 * This method is used set the extended type of the start event
			 * element
			 */
			if (modelElement.getElementType().equals(
					ProcessElementTypes.StartEvent.getName())) {
				if (ProcessConstants.EVENT_START_MESSAGE_TOOLID
						.equals(modelElement.getToolId())) {
					modelElement
							.setExtendedType(ProcessElementTypes.MessageEventDefinition
									.getName());
				} else if (ProcessConstants.EVENT_START_SIGNAL
						.equals(modelElement.getToolId())) {
					modelElement
							.setExtendedType(ProcessElementTypes.SignalEventDefinition
									.getName());
				} else if (ProcessConstants.EVENT_START_TIMER_TOOLID
						.equals(modelElement.getToolId())) {
					modelElement
							.setExtendedType(ProcessElementTypes.TimerEventDefinition
									.getName());
				}
			} else if (modelElement.getElementType().equals(
					ProcessElementTypes.EndEvent.getName())) {
				if (ProcessConstants.EVENT_END_MESSAGE_TOOLID
						.equals(modelElement.getToolId())) {
					modelElement
							.setExtendedType(ProcessElementTypes.MessageEventDefinition
									.getName());
				} else if (ProcessConstants.EVENT_END_SIGNAL_TOOLID
						.equals(modelElement.getToolId())) {
					modelElement
							.setExtendedType(ProcessElementTypes.SignalEventDefinition
									.getName());
				} else if (ProcessConstants.EVENT_END_ERROR_TOOLID
						.equals(modelElement.getToolId())) {
					modelElement
							.setExtendedType(ProcessElementTypes.ErrorEventDefinition
									.getName());
				}
			}

		}

	}

	private static void addFlowNodeElement(BpmnModelElement modelElement,
			List<FlowNodeElement> flowNodeElements, String laneId) {
		FlowNodeElement nodeElement = new FlowNodeElement();
		Lanes lanes = getLanes(laneId);
		nodeElement.setLanes(lanes);
		nodeElement.setElementType(modelElement.getElementType());
		nodeElement.setElementId(modelElement.getElementId());
		nodeElement.setElementName(modelElement.getElementName());
		nodeElement.setToolId(modelElement.getToolId());
		NodePoint nodePoint = new NodePoint();
		nodePoint.setX(modelElement.getMouseLocation().getX());
		nodePoint.setY(modelElement.getMouseLocation().getY());
		nodeElement.setNodePoint(nodePoint);

		LabelPoint labelPoint = new LabelPoint();
		labelPoint.setX(modelElement.getMouseLocation().getX());
		labelPoint.setY(modelElement.getMouseLocation().getX() - 12);
		nodeElement.setLabelPoint(labelPoint);
		if (null != modelElement.getExtendedType()) {
			EventDefinition eventDefinition = new EventDefinition();
			eventDefinition.setDefinitionType(modelElement.getExtendedType());
			eventDefinition.setDefinitionId(modelElement.getDefinationId());
			nodeElement.setEventDefinition(eventDefinition);
		}

		if (ProcessElementTypes.ReceiveTask.getName().equals(
				modelElement.getElementType())) {
			updateReplyEvent(nodeElement, flowNodeElements);
		}
		if (ProcessElementTypes.StartEvent.getName().equals(
				modelElement.getElementType())) {
			if (null != modelElement.getExtendedType()
					&& (ProcessElementTypes.MessageEventDefinition.getName()
							.equals(modelElement.getExtendedType())
							|| ProcessElementTypes.TimerEventDefinition
									.getName().equals(
											modelElement.getExtendedType()) || ProcessElementTypes.SignalEventDefinition
							.getName().equals(modelElement.getExtendedType()))) {
				updateReplyEvent(nodeElement, flowNodeElements);
			}
		}

		if (ProcessElementTypes.SendTask.getName().equals(
				modelElement.getElementType())) {
			addReplyEvent(nodeElement, flowNodeElements);
		}

		if (ProcessElementTypes.EndEvent.getName().equals(
				modelElement.getElementType())) {
			if (null != modelElement.getExtendedType()
					&& (ProcessElementTypes.MessageEventDefinition.getName()
							.equals(modelElement.getExtendedType()) || ProcessElementTypes.SignalEventDefinition
							.getName().equals(modelElement.getExtendedType()))) {
				addReplyEvent(nodeElement, flowNodeElements);
			}
		}
		flowNodeElements.add(nodeElement);
	}

	/**
	 * Add Annotation Element
	 * 
	 * @param modelElement
	 * @param annotationElements
	 * @param laneId
	 */
	private static void addAnnotationElement(BpmnModelElement modelElement,
			List<AnnotationElement> annotationElements, String laneId) {
		AnnotationElement nodeElement = new AnnotationElement();
		Lanes lanes = getLanes(laneId);
		nodeElement.setLanes(lanes);
		nodeElement.setElementType(modelElement.getElementType());
		nodeElement.setElementId(modelElement.getElementId());
		nodeElement.setElementName(modelElement.getElementName());
		nodeElement.setElementLabel(modelElement.getElementLabel());
		nodeElement.setTextValue(modelElement.getTextValue());
		NodePoint nodePoint = new NodePoint();
		nodePoint.setX(modelElement.getMouseLocation().getX());
		nodePoint.setY(modelElement.getMouseLocation().getY());
		nodeElement.setNodePoint(nodePoint);

		annotationElements.add(nodeElement);
	}

	/**
	 * Add Sequence Element
	 * 
	 * @param modelElement
	 * @param sequenceElements
	 * @param laneId
	 * @param process
	 * @param view
	 */
	private static void addSequenceElement(BpmnModelElement modelElement,
			List<SequenceElement> sequenceElements, String laneId,
			Process process, TSAbstractModelView view) {
		SequenceElement sequenceElement = new SequenceElement();
		Lanes lanes = getLanes(laneId);
		sequenceElement.setLanes(lanes);
		sequenceElement.setSourceReference(modelElement.getSourceRefernceId());
		sequenceElement.setTargetReference(modelElement.getTargetReferenceId());
		sequenceElement.setElementName(modelElement.getElementName());
		sequenceElement.setElementId(modelElement.getElementId());
		sequenceElement.setElementType(modelElement.getElementType());
		sequenceElements.add(sequenceElement);

		updateOutGoingSequence(modelElement.getSourceRefernceId(), process,
				modelElement.getElementId(), view, modelElement);
		updateIncomingSequence(modelElement.getTargetReferenceId(), process,
				modelElement.getElementId());

	}

	/**
	 * Add Association Element
	 * 
	 * @param modelElement
	 * @param associationElements
	 * @param laneId
	 */

	private static void addAssoiciationElement(BpmnModelElement modelElement,
			List<AssociationElement> associationElements, String laneId) {
		AssociationElement associationElement = new AssociationElement();
		Lanes lanes = getLanes(laneId);
		associationElement.setLanes(lanes);
		associationElement.setSourceReference(modelElement
				.getSourceRefernceId());
		associationElement.setTargetReference(modelElement
				.getTargetReferenceId());
		associationElement.setElementName(modelElement.getElementName());
		associationElement.setElementId(modelElement.getElementId());
		associationElement.setElementType(modelElement.getElementType());
		associationElements.add(associationElement);
	}

	/**
	 * This method is used to add the bendPoints to the given edge.
	 * 
	 * @param seqElement
	 *            - SequenceElement Instance
	 * @param edge
	 *            - Edge to which wee need to ad bend points.
	 */
	private static void addBendPoints(SequenceElement seqElement, TSEEdge edge) {
		if (null != seqElement) {
			BendPointList pointsList = seqElement.getBendPointList();
			if (null != pointsList) {
				List<BendPoints> bendPoints = pointsList.getBendPoints();
				if (null != bendPoints && !bendPoints.isEmpty()) {
					StartPoint startPoint = seqElement.getStartPoint();
					edge.setSourceClipping(new TSConstPoint(startPoint.getX(),
							startPoint.getY()), true);
					List list = edge.pathEdges();
					for (BendPoints bendPoint : bendPoints) {
						if (null != bendPoint) {
							TSConstPoint tsconstpoint = new TSConstPoint(
									bendPoint.getX(), bendPoint.getY());
							edge.addPathNode((TSPEdge) list.get(0),
									tsconstpoint);
						}
					}
					EndPoint endPoint = seqElement.getEndPoint();
					edge.setTargetClipping(new TSConstPoint(endPoint.getX(),
							endPoint.getY()), true);
				}
			}

		}
	}

	/**
	 * This method is used to update the incoming sequence of the flow node
	 * element.
	 * 
	 */
	private static void updateIncomingSequence(String targetReference,
			Process process, String id) {
		FlowNodeElement flowNodeElement = ProcessDataUtils.getFlowNodeElement(
				process, targetReference);
		if (null != flowNodeElement) {
			String incoming = flowNodeElement.getIncoming();
			if (null == incoming || incoming.trim().isEmpty()) {
				flowNodeElement.setIncoming(id);
			} else {
				if (incoming.contains(",")) {
					String[] incomings = incoming.split(",");

					if (null != incomings && incomings.length > 0) {
						int i = 0;
						String[] temp = new String[(incomings.length + 1)];
						for (; i < incomings.length; i++) {
							temp[i] = incomings[i];
						}
						temp[i] = id;
						StringBuilder incomingBuilder = new StringBuilder();
						int index = 0;
						for (String incomingSeq : temp) {
							if (null != incomingSeq
									&& !incomingSeq.trim().isEmpty()) {
								incomingBuilder.append(incomingSeq);
								if (index < (temp.length - 1)) {
									incomingBuilder.append(",");
								}
							}
							index++;
						}
						flowNodeElement.setIncoming(incomingBuilder.toString());

					}
				} else {
					StringBuilder incomingBuilder = new StringBuilder();
					incomingBuilder.append(incoming);
					incomingBuilder.append(",");
					incomingBuilder.append(id);
					flowNodeElement.setIncoming(incomingBuilder.toString());
				}
			}
		}
	}

	/**
	 * This method is used to update the outgoing sequence of the flow node
	 * element.
	 * 
	 * @param elementData
	 * 
	 */
	private static void updateOutGoingSequence(String sourceReferenceId,
			Process process, String id, TSAbstractModelView view,
			BpmnModelElement modelElement) {
		FlowNodeElement flowNodeElement = ProcessDataUtils.getFlowNodeElement(
				process, sourceReferenceId);
		if (null != flowNodeElement) {
			String outGoing = flowNodeElement.getOutgoing();
			if (null == outGoing || outGoing.trim().isEmpty()) {
				flowNodeElement.setOutgoing(id);

			} else {
				if (outGoing.contains(",")) {
					String[] outGoings = outGoing.split(",");

					if (null != outGoings && outGoings.length > 0) {
						int i = 0;
						String[] temp = new String[(outGoings.length + 1)];
						for (; i < outGoings.length; i++) {
							temp[i] = outGoings[i];
						}
						temp[i] = id;
						StringBuilder outGoingBuilder = new StringBuilder();
						int index = 0;
						for (String outgoing : temp) {
							if (null != outgoing && !outgoing.trim().isEmpty()) {
								outGoingBuilder.append(outgoing);
								if (index < (temp.length - 1)) {
									outGoingBuilder.append(",");
								}
							}
							index++;
						}
						flowNodeElement.setOutgoing(outGoingBuilder.toString());
					}
				} else {
					if (modelElement.isNewElement()) {
						if (ProcessElementTypes.ExclusiveGateway.getName()
								.equals(flowNodeElement.getElementType())
								&& (null == flowNodeElement
										.getDefaultSequenceId() || flowNodeElement
										.getDefaultSequenceId().trim()
										.isEmpty())) {
							flowNodeElement.setDefaultSequenceId(outGoing);
							TSModelElement defaultSequenceElement = ProcessDataUtils
									.getModelElementById(view.getModel(),
											outGoing);

							if (null != defaultSequenceElement) {
								defaultSequenceElement
										.setAttribute(
												ProcessConstants.PROCESS_SEQUENCE_ATTRIBUTE_ISDEFAULT,
												true);
							}
						}
					}
					StringBuilder outGoingBuilder = new StringBuilder();
					outGoingBuilder.append(outGoing);
					outGoingBuilder.append(",");
					outGoingBuilder.append(id);
					flowNodeElement.setOutgoing(outGoingBuilder.toString());
				}
			}
		}
	}

	private static Lanes getLanes(String laneId) {
		Lane lane = new Lane();
		lane.setLaneId(laneId);
		Lanes lanes = new Lanes();
		lanes.setLane(lane);
		return lanes;
	}

	/**
	 * This method is used to add new reply event.
	 * 
	 * @param nodeElement
	 * @param flowNodeElements
	 */
	private static void addReplyEvent(FlowNodeElement nodeElement,
			List<FlowNodeElement> flowNodeElements) {

		if (null != flowNodeElements && !flowNodeElements.isEmpty()) {
			for (FlowNodeElement flowNodeElement : flowNodeElements) {
				if (null != flowNodeElement) {
					if (null != flowNodeElement.getEventDefinition()) {
						String definitionType = flowNodeElement
								.getEventDefinition().getDefinitionType();
						if (ProcessElementTypes.StartEvent.getName().equals(
								flowNodeElement.getElementType())
								&& (ProcessElementTypes.MessageEventDefinition
										.getName().equals(definitionType)
										|| ProcessElementTypes.SignalEventDefinition
												.getName().equals(
														definitionType) || ProcessElementTypes.TimerEventDefinition
										.getName().equals(definitionType))) {
							addMessageStarterList(flowNodeElement, nodeElement);
						}
					}
					if (ProcessElementTypes.ReceiveTask.getName().equals(
							flowNodeElement.getElementType())) {
						addMessageStarterList(flowNodeElement, nodeElement);
					}
				}
			}
		}
	}

	/**
	 * Add new Message Starter List.
	 * 
	 * @param flowNodeElement
	 * @param nodeElement
	 */
	private static void addMessageStarterList(FlowNodeElement flowNodeElement,
			FlowNodeElement nodeElement) {
		addMessageStarter(flowNodeElement, nodeElement);

	}

	/**
	 * Add new Message Starter in Message Starter List.
	 * 
	 * @param flowNodeElement
	 * @param nodeElement
	 */
	private static void addMessageStarter(FlowNodeElement flowNodeElement,
			FlowNodeElement nodeElement) {
		MessageStarterList messageStarterList = nodeElement
				.getMessageStarterList();
		if (null == messageStarterList) {
			messageStarterList = new MessageStarterList();
		}
		MessageStarter messageStarter = new MessageStarter();
		messageStarter.setConsume(false);
		messageStarter.setReplyTo(false);
		messageStarter.setMsgStarterId(flowNodeElement.getElementId());
		List<MessageStarter> messageStarters = messageStarterList
				.getMessageStarter();
		if (null == messageStarters) {
			messageStarters = new ArrayList<MessageStarter>();
		}
		messageStarters.add(messageStarter);
		nodeElement.setMessageStarterList(messageStarterList);
	}

	/**
	 * Update Reply Event
	 * 
	 * @param nodeElement
	 * @param flowNodeElements
	 */
	private static void updateReplyEvent(FlowNodeElement nodeElement,
			List<FlowNodeElement> flowNodeElements) {
		if (null != flowNodeElements && !flowNodeElements.isEmpty()) {
			for (FlowNodeElement flowNodeElement : flowNodeElements) {
				if (null != flowNodeElement) {
					if (null != flowNodeElement.getEventDefinition()) {
						String definitionType = flowNodeElement
								.getEventDefinition().getDefinitionType();
						if (ProcessElementTypes.EndEvent.getName().equals(
								flowNodeElement.getElementType())
								&& (ProcessElementTypes.MessageEventDefinition
										.getName().equals(definitionType) || ProcessElementTypes.SignalEventDefinition
										.getName().equals(definitionType))) {
							updateMessageStarterList(flowNodeElement,
									nodeElement);
						}
					}
					if (ProcessElementTypes.SendTask.getName().equals(
							flowNodeElement.getElementType())) {
						updateMessageStarterList(flowNodeElement, nodeElement);
					}
				}
			}
		}
	}

	/**
	 * Update Message Starter List.
	 * 
	 * @param flowNodeElement
	 * @param nodeElement
	 */
	private static void updateMessageStarterList(
			FlowNodeElement flowNodeElement, FlowNodeElement nodeElement) {
		addMessageStarter(nodeElement, flowNodeElement);
	}
}
