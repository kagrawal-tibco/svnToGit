package com.tibco.cep.webstudio.server.command;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.tibco.cep.webstudio.server.model.ProcessElementTypes;
import com.tibco.cep.webstudio.server.model.process.AnnotationElement;
import com.tibco.cep.webstudio.server.model.process.Annotations;
import com.tibco.cep.webstudio.server.model.process.AssociationElement;
import com.tibco.cep.webstudio.server.model.process.Associations;
import com.tibco.cep.webstudio.server.model.process.FlowNodeElement;
import com.tibco.cep.webstudio.server.model.process.FlowNodes;
import com.tibco.cep.webstudio.server.model.process.LoopCharacteristics;
import com.tibco.cep.webstudio.server.model.process.Process;
import com.tibco.cep.webstudio.server.model.process.SequenceElement;
import com.tibco.cep.webstudio.server.model.process.SequenceFlows;
import com.tibco.cep.webstudio.server.ui.ProcessActiveToolRepository;
import com.tibco.cep.webstudio.server.ui.ProcessWebDiagramDataRepository;
import com.tibco.cep.webstudio.server.ui.tools.BpmnModelElement;
import com.tibco.cep.webstudio.server.ui.utils.ProcessDataSerializer;
import com.tibco.cep.webstudio.server.ui.utils.ProcessDiagramUtils;
import com.tomsawyer.graphicaldrawing.util.TSELocalization;
import com.tomsawyer.model.TSModel;
import com.tomsawyer.model.TSModelElement;
import com.tomsawyer.model.events.TSModelChangeEvent;
import com.tomsawyer.model.events.TSModelChangeEventData;
import com.tomsawyer.model.schema.TSSchema;
import com.tomsawyer.util.gwtclient.command.TSServiceCommand;
import com.tomsawyer.util.shared.TSServiceException;
import com.tomsawyer.view.drawing.TSModelDrawingView;
import com.tomsawyer.web.client.command.TSCustomCommand;
import com.tomsawyer.web.server.TSProjectSessionInfo;
import com.tomsawyer.web.server.command.TSServiceCommandImpl;
import com.tomsawyer.web.server.drawing.TSWebCanvas;
import com.tomsawyer.web.server.service.TSPerspectivesViewService;

/**
 * This class is used to load the process data.
 * 
 * @author sasahoo
 * 
 */
public class ProcessLoadCommandImpl implements TSServiceCommandImpl {
	private TSModelDrawingView view;
	private TSModel model;
	private TSSchema schema;

	protected String projectName;
	protected String processArtifactPath;
	protected String artifactExtention;
	private ProcessActiveToolRepository activeToolManager = ProcessActiveToolRepository
			.getInstance();

	@Override
	public Serializable doAction(TSPerspectivesViewService service,
			TSServiceCommand command) throws TSServiceException {
		TSCustomCommand customCommand = (TSCustomCommand) command;

		// Check the arguments
		List<String> args = customCommand.getCustomArgs();
		if (args == null || args.isEmpty()) {
			throw new TSServiceException("No arguments found for Process");
		} else if (args.size() != 7) {
			throw new TSServiceException(
					"Wrong number of arguments found for Process");
		}

		projectName = args.get(0);
		processArtifactPath = args.get(1);
		artifactExtention = args.get(2);

		String processarg = args.get(3);

		if (Boolean.parseBoolean(args.get(5))) {
			processarg = buildNewProcess(args.get(6));
		}
		init(service, customCommand);

		boolean wasFiringEvents = model.getEventManager().isFiringEvents();
		model.getEventManager().setFireEvents(false);

		try {
			populateData(((TSCustomCommand) customCommand).getViewID(),
					processarg);

			view.clearSelection();
			// view.updateView();
			// globalLayoutUpdate(view);
			//String localeInfo = args.get(4);
			//String language=localeInfo.split("_")[0];
			//String country=localeInfo.split("_")[1];
			//Locale locale = new Locale(language,country);
			//TSELocalization.setLocale(locale);
			((TSWebCanvas) view.getCanvas()).fitInCanvas(true);
			view.getCanvas().setZoomLevel(1.0, true);
			model.getCommandManager().setUndoLimit(10);
		} finally {
			model.getEventManager().setFireEvents(wasFiringEvents);
		}
		model.getEventManager().fireEvent(
				new TSModelChangeEvent(TSModelChangeEvent.GLOBAL_CHANGE,
						new TSModelChangeEventData(model, null, null)));
		ProcessDiagramUtils.globalLayoutUpdate(view);
		return Boolean.TRUE;
	}

	/**
	 * This method is used to initialize the different components required for
	 * TomsWayer.
	 * 
	 * @param service
	 * @param customCommand
	 */
	private void init(TSPerspectivesViewService service,
			TSCustomCommand customCommand) {
		TSProjectSessionInfo projectInfo = service
				.getProjectSessionInfo(customCommand.getProjectID());
		view = (TSModelDrawingView) service.getView(
				((TSCustomCommand) customCommand).getProjectID(),
				((TSCustomCommand) customCommand).getViewID(), false);
		model = view.getModel();
		model.clear();
		schema = projectInfo.getProject().getSchema("Process");

		schema.initModel(model);
		view.getMapper().setModel(model);

	}

	private void populateData(String viewId, String processarg) {
		try {

			Process process = ProcessDataSerializer.deserialize(processarg);
			ProcessWebDiagramDataRepository.getInstance().addProcess(viewId,
					process);

			Set<String> defaultSeqSet = new LinkedHashSet<String>();

			// Draw All FlowElements
			drawFlowElements(process, defaultSeqSet);

			// Draw all Annotations
			drawAnnotations(process);

			// Draw all Association
			drawAssociations(process);

			// Draw All SequenceElements
			drawSequences(process, defaultSeqSet);

			// groupCommand.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to draw the flow elements from the process.
	 * 
	 * @param process
	 *            -Instance of Process.
	 * @param defaultSeqSet
	 */
	private void drawSequences(Process process, Set<String> defaultSeqSet) {
		SequenceFlows sequenceFlows = process.getSequenceFlows();
		if (null != sequenceFlows) {
			List<SequenceElement> sequenceElement = sequenceFlows
					.getSequenceElement();
			if (null != sequenceElement && !sequenceElement.isEmpty()) {
				for (SequenceElement seqElement : sequenceElement) {
					boolean isDefaultSeq = false;
					if (defaultSeqSet.contains(seqElement.getElementId()
							.toString())) {
						isDefaultSeq = true;
					}

					BpmnModelElement bpmnModelElement = new BpmnModelElement();
					bpmnModelElement.setElementId(seqElement.getElementId());
					bpmnModelElement
							.setElementName(seqElement.getElementType());
					bpmnModelElement
							.setElementType(seqElement.getElementType());
					bpmnModelElement.setSourceRefernceId(seqElement
							.getSourceReference());
					bpmnModelElement.setTargetReferenceId(seqElement
							.getTargetReference());
					bpmnModelElement.setNewElement(false);

					TSModelElement modelElement = ProcessDiagramUtils
							.addSequenceElement(view, activeToolManager,
									bpmnModelElement);
					modelElement.setAttribute("IsDefault", isDefaultSeq);
					view.getModel().addElement(modelElement);

				}
			}
		}
	}

	/**
	 * This method is used to draw the flow elements from the process.
	 * 
	 * @param process
	 *            -Instance of Process.
	 */
	private void drawAssociations(Process process) {
		Associations associations = process.getAssociations();
		if (null != associations) {
			List<AssociationElement> associationElements = associations
					.getAssociationElement();
			if (null != associationElements && !associationElements.isEmpty()) {
				for (AssociationElement associationElement : associationElements) {
					BpmnModelElement bpmnModelElement = new BpmnModelElement();
					bpmnModelElement.setElementId(associationElement
							.getElementId());
					bpmnModelElement.setElementName(associationElement
							.getElementLabel());
					bpmnModelElement.setElementType(associationElement
							.getElementType());
					bpmnModelElement.setSourceRefernceId(associationElement
							.getSourceReference());
					bpmnModelElement.setTargetReferenceId(associationElement
							.getTargetReference());
					bpmnModelElement.setNewElement(false);

					TSModelElement modelElement = ProcessDiagramUtils
							.addSequenceElement(view, activeToolManager,
									bpmnModelElement);
					view.getModel().addElement(modelElement);

				}
			}
		}
	}

	/**
	 * This method is used to draw the flow elements from the process.
	 * 
	 * @param process
	 *            -Instance of Process.
	 */
	private void drawAnnotations(Process process) {
		Annotations annotations = process.getAnnotations();
		if (null != annotations) {
			List<AnnotationElement> annotationElement = annotations
					.getAnnotationElement();
			if (null != annotationElement && !annotationElement.isEmpty()) {
				for (AnnotationElement flowNode : annotationElement) {
					BpmnModelElement bpmnModelElement = new BpmnModelElement();
					bpmnModelElement.setElementId(flowNode.getElementId());
					bpmnModelElement.setElementName(flowNode.getElementName());
					bpmnModelElement.setToolId(flowNode.getToolId());
					bpmnModelElement.setElementType(flowNode.getElementType());
					bpmnModelElement.setTextValue(flowNode.getTextValue());
					bpmnModelElement.setNewElement(false);

					TSModelElement modelElement = ProcessDiagramUtils
							.addAnnotationElement(view, activeToolManager,
									bpmnModelElement);
					view.getModel().addElement(modelElement);
				}
			}
		}
	}

	/**
	 * This method is used to draw the flow elements from the process.
	 * 
	 * @param process
	 *            -Instance of Process.
	 * @param defaultSeqSet
	 * @return The value of default sequence in exclusive gateway
	 */
	private String drawFlowElements(Process process, Set<String> defaultSeqSet) {
		String defaultSeq = "";
		FlowNodes flowNodes = process.getFlowNodes();
		if (null != flowNodes) {
			List<FlowNodeElement> flowNodeElement = flowNodes
					.getFlowNodeElement();
			if (null != flowNodeElement && !flowNodeElement.isEmpty()) {
				for (FlowNodeElement flowNode : flowNodeElement) {
					BpmnModelElement bpmnModelElement = new BpmnModelElement();
					bpmnModelElement.setElementId(flowNode.getElementId());
					bpmnModelElement.setElementName(flowNode.getElementName());
					bpmnModelElement.setToolId(flowNode.getToolId());
					bpmnModelElement.setElementType(flowNode.getElementType());
					bpmnModelElement.setNewElement(false);
					LoopCharacteristics loopCharacteristics = flowNode.getLoopCharacteristics();
					if (null != loopCharacteristics) {
						bpmnModelElement.setLoopType(loopCharacteristics.getType());
					}
					TSModelElement modelElement = ProcessDiagramUtils
							.addFlowElement(view, activeToolManager,
									bpmnModelElement);
					view.getModel().addElement(modelElement);
					if (flowNode.getElementType().equals(
							ProcessElementTypes.ExclusiveGateway.getName())) {
						defaultSeqSet.add(flowNode.getDefaultSequenceId());
					}
				}
			}
		}
		return defaultSeq;
	}

	/**
	 * Build new process data.
	 * 
	 * @param processName
	 * @return
	 */
	private String buildNewProcess(String processName) {
		processName = processName.replace("." + artifactExtention, "");
		StringBuilder builder = new StringBuilder("<process Id=\"1\">");
		builder.append("<version>1</version>");
		builder.append("<processType>Public</processType>");
		builder.append("<zoomLevel>0.0E0</zoomLevel>");
		builder.append("<properties Id=\"2\"/>");
		builder.append("<processId>");
		builder.append(processName);
		builder.append("</processId>");
		builder.append("<creationDate>");
		builder.append(new Date().toString());
		builder.append("</creationDate><lastModificationDate>");
		builder.append(new Date().toString());
		builder.append("</lastModificationDate><laneset Id=\"3\"><lane Id=\"4\"><laneId>");
		builder.append(processName);
		builder.append(".Lane_0</laneId><name>Default Lane</name></lane></laneset>");
		builder.append(getFlowNodes(processName));
		builder.append(getSequenceFlows(processName));
		builder.append("<annotations Id=\"37\"/>");
		builder.append("<associations Id=\"38\"/>");
		builder.append(getDocumentation(processName));
		builder.append("</process>");
		return builder.toString();
	}

	/**
	 * Get Sequence flows
	 * 
	 * @param processName
	 * @return
	 */
	private String getSequenceFlows(String processName) {
		StringBuilder builder = new StringBuilder("<sequenceFlows Id=\"26\">");
		builder.append(getSequenceFlow0(processName));
		builder.append(getSequenceFlow1(processName));
		builder.append("</sequenceFlows>");
		return builder.toString();
	}

	/**
	 * Get Sequence flow from start event to script task.
	 * 
	 * @param processName
	 * @return
	 */
	private String getSequenceFlow0(String processName) {
		StringBuilder builder = new StringBuilder("<sequenceElement Id=\"27\">");
		builder.append("<elementId>");
		builder.append(processName);
		builder.append(".SequenceFlow_0</elementId>");
		builder.append("<elementType>SequenceFlow</elementType>");
		builder.append("<elementName>SequenceFlow_0</elementName>");
		builder.append("<lanes Id=\"28\"><lane Id=\"29\"><laneId>");
		builder.append(processName);
		builder.append(".Lane_0</laneId><name>Default Lane</name></lane></lanes>");
		builder.append("<elementVersion>0</elementVersion>");
		builder.append("<uniqueId>4</uniqueId>");
		builder.append("<targetReference>");
		builder.append(processName);
		builder.append(".Script_0</targetReference>");
		builder.append("<sourceReference>");
		builder.append(processName);
		builder.append(".Message_Start_0</sourceReference>");
		builder.append("<startPoint Id=\"30\"><x>-47.62421987899779</x><y>7.883191480429552</y></startPoint>");
		builder.append("<endPoint Id=\"31\"><x>-19.624219878997792</x><y>7.883191480429552</y></endPoint>");
		builder.append("</sequenceElement>");
		return builder.toString();
	}

	/**
	 * Get Sequence script task to end event
	 * 
	 * @param processName
	 * @return
	 */
	private String getSequenceFlow1(String processName) {
		StringBuilder builder = new StringBuilder("<sequenceElement Id=\"32\">");
		builder.append("<elementId>");
		builder.append(processName);
		builder.append(".SequenceFlow_1</elementId>");
		builder.append("<elementType>SequenceFlow</elementType>");
		builder.append("<elementName>SequenceFlow_1</elementName>");
		builder.append("<lanes Id=\"33\"><lane Id=\"34\"><laneId>");
		builder.append(processName);
		builder.append(".Lane_0</laneId><name>Default Lane</name></lane></lanes>");
		builder.append("<elementVersion>0</elementVersion>");
		builder.append("<uniqueId>4</uniqueId>");
		builder.append("<targetReference>");
		builder.append(processName);
		builder.append(".Message_End_0</targetReference>");
		builder.append("<sourceReference>");
		builder.append(processName);
		builder.append(".Script_0</sourceReference>");
		builder.append("<startPoint Id=\"35\"><x>50.37578012100221</x><y>7.883191480429552</y></startPoint>");
		builder.append("<endPoint Id=\"36\"><x>78.37578012100221</x><y>7.883191480429552</y></endPoint>");
		builder.append("</sequenceElement>");
		return builder.toString();
	}

	/**
	 * Get Flow Nodes
	 * 
	 * @param processName
	 * @return
	 */
	private String getFlowNodes(String processName) {
		StringBuilder builder = new StringBuilder("<flowNodes Id=\"5\">");
		builder.append(getStartEvent(processName));
		builder.append(getEndEvent(processName));
		builder.append(getScriptTask(processName));
		builder.append("</flowNodes>");
		return builder.toString();

	}

	/**
	 * New Start Event
	 * 
	 * @param processName
	 * @return
	 */
	private String getStartEvent(String processName) {
		StringBuilder builder = new StringBuilder("<flowNodeElement Id=\"6\">");
		builder.append("<elementId>");
		builder.append(processName);
		builder.append(".Message_Start_0</elementId>");
		builder.append("<elementType>StartEvent</elementType>");
		builder.append("<elementName>Message_Start_0</elementName>");
		builder.append("<labelPoint Id=\"7\"/>");
		builder.append("<nodePoint Id=\"8\"/>");
		builder.append("<resourcePath/>");
		builder.append("<outgoing>");
		builder.append(processName);
		builder.append(".SequenceFlow_0</outgoing>");
		builder.append("<elementVersion>0</elementVersion>");
		builder.append("<uniqueId>1</uniqueId>");
		builder.append("<elementLabel/>");
		builder.append("<lanes Id=\"9\"><lane Id=\"10\"><laneId>");
		builder.append(processName);
		builder.append(".Lane_0</laneId><name>Default Lane</name></lane></lanes>");
		/* builder.append("<outputMap Id=\"11\"/>"); */
		builder.append("<toolId>event.start.message</toolId>");
		builder.append("<checkpoint>false</checkpoint>");
		builder.append("<opTimeout>0</opTimeout>");
		builder.append("<nodePoint Id=\"11\"><x>-77.62421987899779</x><y>7.883191480429531</y></nodePoint>");
		builder.append("<labelPoint Id=\"45\"><x>-77.12421987899776</x><y>-28.08347518623715</y></labelPoint>");
		builder.append("<eventDefinition Id=\"545\"><definitionType>MessageEventDefinition</definitionType>");
		builder.append("<definitionId>");
		builder.append(processName);
		builder.append(".MessageEventDefinition_0</definitionId></eventDefinition>");
		builder.append("</flowNodeElement>");
		return builder.toString();
	}

	/**
	 * New End Event
	 * 
	 * @param processName
	 * @return
	 */
	private String getEndEvent(String processName) {
		StringBuilder builder = new StringBuilder("<flowNodeElement Id=\"12\">");
		builder.append("<elementId>");
		builder.append(processName);
		builder.append(".Message_End_0</elementId>");
		builder.append("<elementType>EndEvent</elementType>");
		builder.append("<elementName>Message_End_0</elementName>");
		builder.append("<labelPoint Id=\"13\"/>");
		builder.append("<nodePoint Id=\"14\"/>");
		builder.append("<resourcePath/>");
		builder.append("<incoming>");
		builder.append(processName);
		builder.append(".SequenceFlow_1</incoming>");
		builder.append("<elementVersion>0</elementVersion>");
		builder.append("<uniqueId>2</uniqueId>");
		builder.append("<elementLabel/>");
		builder.append("<lanes Id=\"15\"><lane Id=\"16\"><laneId>");
		builder.append(processName);
		builder.append(".Lane_0</laneId><name>Default Lane</name></lane></lanes>");
		builder.append("<toolId>event.end.message</toolId>");
		builder.append("<checkpoint>false</checkpoint>");
		builder.append("<opTimeout>0</opTimeout>");
		builder.append("<messageStarterList Id=\"17\"><messageStarter Id=\"18\">");
		builder.append("<msgStarterId>");
		builder.append(processName);
		builder.append(".Message_Start_0</msgStarterId>");
		builder.append("<replyTo>true</replyTo><consume>false</consume>");
		builder.append("</messageStarter></messageStarterList>");
		builder.append("<nodePoint Id=\"755\"><x>108.37578012100221</x><y>7.883191480429531</y></nodePoint>");
		builder.append("<labelPoint Id=\"565\"><x>108.37578012100221</x><y>-28.083475186237152</y></labelPoint>");
		builder.append("<eventDefinition Id=\"765\"><definitionType>MessageEventDefinition</definitionType>");
		builder.append("<definitionId>");
		builder.append(processName);
		builder.append(".MessageEventDefinition_1</definitionId></eventDefinition>");
		builder.append("</flowNodeElement>");
		return builder.toString();
	}

	/**
	 * New Script Node
	 * 
	 * @param processName
	 * @return
	 */
	private Object getScriptTask(String processName) {
		StringBuilder builder = new StringBuilder("<flowNodeElement Id=\"19\">");
		builder.append("<elementId>");
		builder.append(processName);
		builder.append(".Script_0</elementId>");
		builder.append("<elementType>RuleFunctionTask</elementType>");
		builder.append("<elementName>Script_0</elementName>");
		builder.append("<labelPoint Id=\"20\"/>");
		builder.append("<nodePoint Id=\"21\"/>");
		builder.append("<resourcePath/>");
		builder.append("<incoming>");
		builder.append(processName);
		builder.append(".SequenceFlow_0</incoming>");
		builder.append("<outgoing>");
		builder.append(processName);
		builder.append(".SequenceFlow_1</outgoing>");
		builder.append("<elementVersion>0</elementVersion>");
		builder.append("<uniqueId>3</uniqueId>");
		builder.append("<elementLabel/>");
		builder.append("<lanes Id=\"22\"><lane Id=\"23\"><laneId>");
		builder.append(processName);
		builder.append(".Lane_0</laneId><name>Default Lane</name></lane></lanes>");
		builder.append("<toolId>activity.ruleFunction</toolId>");
		builder.append("<checkpoint>false</checkpoint>");
		builder.append("<opTimeout>0</opTimeout>");
		/*
		 * builder.append("inputMap Id=\"24\"/>");
		 * builder.append("<outputMap Id=\"25\"/>");
		 */
		builder.append("<nodePoint Id=\"345\"><x>15.375780121002208</x><y>7.878191480429578</y></nodePoint>");
		builder.append("<labelPoint Id=\"456\"><x>15.375780121002208</x><y>-26.121808519570422</y></labelPoint>");
		builder.append("</flowNodeElement>");
		return builder.toString();
	}
	
	private String getDocumentation(String processName) {
		StringBuilder builder = new StringBuilder("<documentation Id=\"39\">");
		builder.append("<docId>");
		builder.append(processName + ".Documentation_0");
		builder.append("</docId>");
		builder.append("<text>");
		builder.append("Add documentation for new process.");
		builder.append("</text>");
		builder.append("</documentation>");
		return builder.toString();
	}
}
