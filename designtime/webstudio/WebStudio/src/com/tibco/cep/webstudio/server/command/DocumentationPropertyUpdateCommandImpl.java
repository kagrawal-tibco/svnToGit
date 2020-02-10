package com.tibco.cep.webstudio.server.command;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import com.tibco.cep.webstudio.client.process.ProcessConstants;
import com.tibco.cep.webstudio.server.model.ProcessElementTypes;
import com.tibco.cep.webstudio.server.model.process.AnnotationElement;
import com.tibco.cep.webstudio.server.model.process.AssociationElement;
import com.tibco.cep.webstudio.server.model.process.Documentation;
import com.tibco.cep.webstudio.server.model.process.FlowNodeElement;
import com.tibco.cep.webstudio.server.model.process.Process;
import com.tibco.cep.webstudio.server.model.process.SequenceElement;
import com.tibco.cep.webstudio.server.ui.ProcessWebDiagramDataRepository;
import com.tomsawyer.model.TSModelElement;
import com.tomsawyer.util.gwtclient.command.TSServiceCommand;
import com.tomsawyer.util.shared.TSAttributedObject;
import com.tomsawyer.util.shared.TSServiceException;
import com.tomsawyer.web.client.command.TSCustomCommand;
import com.tomsawyer.web.server.command.TSServiceCommandImpl;
import com.tomsawyer.web.server.drawing.TSWebDrawingViewServer;
import com.tomsawyer.web.server.service.TSPerspectivesViewService;

/**
 * This class is used to to update the documentation property of process,node
 * and edge.
 * 
 * @author dijadhav
 * 
 */
public class DocumentationPropertyUpdateCommandImpl implements
		TSServiceCommandImpl {

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
		TSCustomCommand customCommand = (TSCustomCommand) serviceCommand;
		List<String> args = customCommand.getCustomArgs();
		if (args == null || args.isEmpty()) {
			throw new TSServiceException("No arguments found for Process");
		}
		if (args.size() != 2) {
			throw new TSServiceException("No arguments found for Process");
		}
		String value = args.get(0);
		String type = args.get(1);
		/**
		 * Get the DrawingView
		 */
		TSWebDrawingViewServer drawingView = (TSWebDrawingViewServer) service
				.getView(((TSCustomCommand) customCommand).getProjectID(),
						((TSCustomCommand) customCommand).getViewID(), false);

		if (drawingView != null) {
			List<TSAttributedObject> selectedObjects = drawingView
					.getSelectedAttributedObjects();

			Iterator<TSAttributedObject> selectionIter = selectedObjects
					.iterator();
			Process process = ProcessWebDiagramDataRepository.getInstance()
					.getProcess(((TSCustomCommand) customCommand).getViewID());
			if (!selectionIter.hasNext() && "Process".equals(type)) {

				Documentation documentation = getDocumentation(value,
						process.getDocumentation());
				process.setDocumentation(documentation);

			} else {
				while (selectionIter.hasNext()) {
					TSModelElement element = (TSModelElement) selectionIter
							.next();
					String elementType = (String) element
							.getAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_TYPE);
					
					if (ProcessElementTypes.Association.getName()
							.equalsIgnoreCase(elementType)) {
						String elementId = (String) element
								.getAttribute(ProcessConstants.PROCESS_SEQUENCE_ATTRIBUTE_ID);
						setAssociationElementDocumentation(elementId, process
								.getAssociations().getAssociationElement(),
								value);
					} else if (ProcessElementTypes.SequenceFlow.getName()
							.equalsIgnoreCase(elementType)) {
						String elementId = (String) element
								.getAttribute(ProcessConstants.PROCESS_SEQUENCE_ATTRIBUTE_ID);
						setSequeceElementDocumentation(elementId, process
								.getSequenceFlows().getSequenceElement(), value);
					} else if (ProcessElementTypes.TextAnnotation.getName()
							.equalsIgnoreCase(elementType)) {
						String elementId = (String) element
								.getAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_ID);
						setAnnotationElementDocumentation(elementId, process
								.getAnnotations().getAnnotationElement(), value);
					} else {
						String elementId = (String) element
								.getAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_ID);
						setFlowNodeElementDocumentation(process, elementId,
								value);
					}
				}
			}
		}
		return Boolean.TRUE;
	}

	/**
	 * This method is sued to get the documentataion.
	 * 
	 * @param value
	 * @param process
	 * @return
	 */
	private Documentation getDocumentation(String value,
			Documentation documentation) {
		if (null == documentation) {
			documentation = new Documentation();
			documentation.setId(String.valueOf(System.currentTimeMillis()));
		}
		documentation.setText(value);
		return documentation;
	}

	private void setAssociationElementDocumentation(String elementId,
			List<AssociationElement> associationElements, String documentation) {

		if (null != associationElements && !associationElements.isEmpty()) {
			for (AssociationElement element : associationElements) {
				if (null != element
						&& element.getElementId().equalsIgnoreCase(elementId)) {
					element.setDocumentation(getDocumentation(documentation,
							element.getDocumentation()));
					break;
				}
			}
		}

	}

	private void setAnnotationElementDocumentation(String elementId,
			List<AnnotationElement> annotationElements, String documentation) {

		if (null != annotationElements && !annotationElements.isEmpty()) {
			for (AnnotationElement element : annotationElements) {
				if (null != element
						&& element.getElementId().equalsIgnoreCase(elementId)) {
					element.setDocumentation(getDocumentation(documentation,
							element.getDocumentation()));
					break;
				}
			}
		}

	}

	private void setSequeceElementDocumentation(String elementId,
			List<SequenceElement> sequenceElements, String documentation) {
		if (null != sequenceElements && !sequenceElements.isEmpty()) {
			for (SequenceElement element : sequenceElements) {
				if (null != element
						&& element.getElementId().equalsIgnoreCase(elementId)) {
					element.setDocumentation(getDocumentation(documentation,
							element.getDocumentation()));
					break;
				}
			}
		}

	}

	private void setFlowNodeElementDocumentation(Process process,
			String elementId, String documentation) {
		List<FlowNodeElement> flowNodeElements = process.getFlowNodes()
				.getFlowNodeElement();
		if (null != flowNodeElements && !flowNodeElements.isEmpty()) {
			for (FlowNodeElement element : flowNodeElements) {
				if (null != element
						&& element.getElementId().equalsIgnoreCase(elementId)) {
					element.setDocumentation(getDocumentation(documentation,
							element.getDocumentation()));
					break;
				}
			}
		}

	}
}
