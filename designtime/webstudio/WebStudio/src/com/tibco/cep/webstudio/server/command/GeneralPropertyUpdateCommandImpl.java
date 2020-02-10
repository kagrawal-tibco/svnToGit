package com.tibco.cep.webstudio.server.command;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import com.tibco.cep.webstudio.client.process.ProcessConstants;
import com.tibco.cep.webstudio.server.model.ProcessElementTypes;
import com.tibco.cep.webstudio.server.model.process.AnnotationElement;
import com.tibco.cep.webstudio.server.model.process.AssociationElement;
import com.tibco.cep.webstudio.server.model.process.FlowNodeElement;
import com.tibco.cep.webstudio.server.model.process.ImplementationURI;
import com.tibco.cep.webstudio.server.model.process.Process;
import com.tibco.cep.webstudio.server.model.process.SequenceElement;
import com.tibco.cep.webstudio.server.model.process.UriList;
import com.tibco.cep.webstudio.server.ui.ProcessWebDiagramDataRepository;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;
import com.tomsawyer.model.TSModelElement;
import com.tomsawyer.util.gwtclient.command.TSServiceCommand;
import com.tomsawyer.util.shared.TSAttributedObject;
import com.tomsawyer.util.shared.TSServiceException;
import com.tomsawyer.web.client.command.TSCustomCommand;
import com.tomsawyer.web.server.command.TSServiceCommandImpl;
import com.tomsawyer.web.server.drawing.TSWebDrawingViewServer;
import com.tomsawyer.web.server.service.TSPerspectivesViewService;

/**
 * This class is used to update the general properties of node or edge.
 * 
 * @author dijadhav
 * 
 */
public class GeneralPropertyUpdateCommandImpl implements TSServiceCommandImpl {

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
		String name = args.get(1);
		if (!ProcessElementTypes.ExclusiveGateway.getName().equals(name)) {
			if (args.size() != 2) {
				throw new TSServiceException("Invalid number of arguments.");
			}
		} else {
			if (args.size() != 3) {
				throw new TSServiceException("Invalid number of arguments.");
			}
		}

		String value = args.get(0);

		/**
		 * Get the DrawingView
		 */
		TSWebDrawingViewServer drawingView = (TSWebDrawingViewServer) service
				.getView(((TSCustomCommand) customCommand).getProjectID(),
						((TSCustomCommand) customCommand).getViewID(), false);

		if (drawingView != null) {
			Process process = ProcessWebDiagramDataRepository.getInstance()
					.getProcess(((TSCustomCommand) customCommand).getViewID());

			List<TSAttributedObject> selectedObjects = drawingView
					.getSelectedAttributedObjects();
			if (null != selectedObjects && !selectedObjects.isEmpty()) {
				Iterator<TSAttributedObject> selectionIter = selectedObjects
						.iterator();
				while (selectionIter.hasNext()) {
					TSAttributedObject attributedObject = selectionIter.next();
					TSModelElement element = (TSModelElement) attributedObject;
					if (null != element) {
						String type = (String) element
								.getAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_TYPE);
						
						if(null==type || type.trim().isEmpty()){
							type= (String) element
									.getAttribute(ProcessConstants.PROCESS_SEQUENCE_ATTRIBUTE_TYPE);
						}
						if (ProcessElementTypes.Association.getName()
								.equalsIgnoreCase(type)) {
							String elementId = (String) element
									.getAttribute(ProcessConstants.PROCESS_SEQUENCE_ATTRIBUTE_ID);
							AssociationElement associationElement = ProcessDataUtils
									.getAssociationElement(elementId, process
											.getAssociations()
											.getAssociationElement());
							associationElement.setElementName(value);
						} else if (ProcessElementTypes.SequenceFlow.getName()
								.equalsIgnoreCase(type)) {
							String elementId = (String) element
									.getAttribute(ProcessConstants.PROCESS_SEQUENCE_ATTRIBUTE_ID);
							if (ProcessConstants.LABEL.equals(name)) {
								element.setAttribute(
										ProcessConstants.PROCESS_SEQUENCE_ATTRIBUTE_LABEL, value);
								SequenceElement sequenceElement = ProcessDataUtils
										.getSequeceElement(elementId, process
												.getSequenceFlows()
												.getSequenceElement());
								sequenceElement.setElementName(value);
							}
						} else {
							String elementId = (String) element
									.getAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_ID);
							if (ProcessConstants.LABEL.equals(name)) {
								updateFlowElementLabel(value, drawingView,
										element,
										ProcessDataUtils.getFlowNodeElement(
												process, elementId));
							} else if (ProcessConstants.CHECKPOINT.equals(name)) {
								FlowNodeElement flowNodeElement = ProcessDataUtils
										.getFlowNodeElement(process, elementId);
								if (null != flowNodeElement) {
									flowNodeElement.setCheckpoint(Boolean
											.parseBoolean(value));
								}
							} else if (ProcessConstants.TEXT.equals(name)) {
								AnnotationElement flowNodeElement = ProcessDataUtils
										.getAnnotationElement(elementId,
												process.getAnnotations()
														.getAnnotationElement());
								if (null != flowNodeElement) {
									flowNodeElement.setTextValue(value);
								}
								List<TSGraphObject> graphObjects = drawingView
										.getMapper().getGraphObjects(element);
								if (null != graphObjects
										&& !graphObjects.isEmpty()) {
									TSEObject object = (TSEObject) graphObjects
											.get(0);
									if (null != object
											&& object instanceof TSENode) {
										TSENode node = (TSENode) object;
										node.setName(value);
									}
								}
							} else if (ProcessConstants.TRIGGER_BY_EVENT_
									.equals(name)) {
								FlowNodeElement flowNodeElement = ProcessDataUtils
										.getFlowNodeElement(process, elementId);
								if (null != flowNodeElement) {
									// TODO Add TriggerbyEvent
									/*
									 * flowNodeElement.setTriggerByEvent(Boolean
									 * .parseBoolean(value));
									 */
								}
							} else if (ProcessConstants.RESOURCE.equals(name)) {
								FlowNodeElement flowNodeElement = ProcessDataUtils
										.getFlowNodeElement(process, elementId);
								if (null != flowNodeElement) {
									flowNodeElement.setResourcePath(value);
								}

							} else if (ProcessConstants.FORK_FUNCTION
									.equals(name)) {
								FlowNodeElement flowNodeElement = ProcessDataUtils
										.getFlowNodeElement(process, elementId);
								if (null != flowNodeElement) {
									flowNodeElement.setForkRuleFunction(value);
								}
							} else if (ProcessConstants.JOIN_FUNCTION
									.equals(name)) {
								FlowNodeElement flowNodeElement = ProcessDataUtils
										.getFlowNodeElement(process, elementId);
								if (null != flowNodeElement) {
									flowNodeElement.setJoinRuleFunction(value);
								}
							} else if (ProcessConstants.SOAPACTION
									.equals(name)) {
								FlowNodeElement flowNodeElement = ProcessDataUtils
										.getFlowNodeElement(process, elementId);
								if (null != flowNodeElement) {
									flowNodeElement.setSoapAction(value);
								}
							} else if (ProcessConstants.SERVICE
									.equals(name)) {
								FlowNodeElement flowNodeElement = ProcessDataUtils
										.getFlowNodeElement(process, elementId);
								if (null != flowNodeElement) {
									flowNodeElement.setService(value);
								}
							} else if (ProcessConstants.PORT
									.equals(name)) {
								FlowNodeElement flowNodeElement = ProcessDataUtils
										.getFlowNodeElement(process, elementId);
								if (null != flowNodeElement) {
									flowNodeElement.setPort(value);
								}
							} else if (ProcessConstants.OPERATION
									.equals(name)) {
								FlowNodeElement flowNodeElement = ProcessDataUtils
										.getFlowNodeElement(process, elementId);
								if (null != flowNodeElement) {
									flowNodeElement.setOperation(value);
								}
							} else if (ProcessConstants.TIMEOUT
									.equals(name)) {
								FlowNodeElement flowNodeElement = ProcessDataUtils
										.getFlowNodeElement(process, elementId);
								if (null != flowNodeElement) {
									int opTimeout = 0;
									try {
										opTimeout = Integer.parseInt(value);
									} catch(NumberFormatException nfe) {
										opTimeout = 0;
									}
									flowNodeElement.setOpTimeout(opTimeout);
								}
							} else if (ProcessConstants.VRFIMPLURI.equals(name)) {

								updateImplementationURI(customCommand,
										ProcessDataUtils.getFlowNodeElement(
												process, elementId));
							} else if (ProcessElementTypes.ExclusiveGateway
									.getName().equals(name)) {
								FlowNodeElement flowNodeElement = ProcessDataUtils
										.getFlowNodeElement(process, elementId);
								if (null != flowNodeElement) {
									String existingDefaultSequence = flowNodeElement
											.getDefaultSequenceId();
									flowNodeElement.setDefaultSequenceId(value);
									if ((null != existingDefaultSequence && !existingDefaultSequence
											.trim().isEmpty())
											&& (null != value && !value.trim()
													.isEmpty())
											&& !value
													.equals(existingDefaultSequence)) {
										flowNodeElement
												.setOutgoing(args.get(2));

										TSModelElement newDefaultSeqElement = ProcessDataUtils
												.getModelElementById(
														drawingView.getModel(),
														value);
										newDefaultSeqElement.setAttribute(
												"IsDefault", true);

										TSModelElement oldDefaultSeqElement = ProcessDataUtils
												.getModelElementById(
														drawingView.getModel(),
														existingDefaultSequence);
										oldDefaultSeqElement.setAttribute(
												"IsDefault", false);

									}
								}
							}

						}
					}
				}
			} else {
				updateProcessProperties(value, name,
						((TSCustomCommand) customCommand).getViewID());
			}
		}
		return Boolean.TRUE;
	}

	private void updateImplementationURI(TSCustomCommand customCommand,
			FlowNodeElement flowNodeElement) {
		String listGridRecords = customCommand.getCustomArgs().get(0);
		if (null != flowNodeElement) {
			UriList uriList = flowNodeElement.getUriList();
			if (null != uriList) {
				List<ImplementationURI> implementationURIs = uriList
						.getImplementationURI();
				String[] vrfimplementationURIs = listGridRecords.split(",");
				for (ImplementationURI implementationURI : implementationURIs) {
					if (null != vrfimplementationURIs
							&& vrfimplementationURIs.length > 0) {
						for (String vrfimplementationURI : vrfimplementationURIs) {
							String[] parts = vrfimplementationURI.split("#");

							if (null != implementationURI
									&& parts[1].equals(implementationURI
											.getUri())) {
								implementationURI.setIsDeployed(Boolean
										.parseBoolean(parts[0]));
							}
						}
					} else {
						uriList.getImplementationURI().removeAll(
								implementationURIs);
					}
				}
			}else{
				uriList = new UriList();
				String[] vrfimplementationURIs = listGridRecords.split(",");
				if (null != vrfimplementationURIs
						&& vrfimplementationURIs.length > 0) {
					for (String vrfimplementationURI : vrfimplementationURIs) {
						String[] parts = vrfimplementationURI.split("#");
						ImplementationURI implementationURI = new ImplementationURI();
						implementationURI.setIsDeployed(Boolean
									.parseBoolean(parts[0]));
						implementationURI.setUri(parts[1]);
						uriList.getImplementationURI().add(implementationURI);
						
					}
				}
			}
			flowNodeElement.setUriList(uriList);
		}
	}

	/**
	 * This method is used to update the properties of process.
	 * 
	 * @param value
	 *            - The updated value of the property.
	 * @param name
	 *            - Name of the property which needs to update.
	 * @param viewId
	 */
	private void updateProcessProperties(String value, String name,
			String viewId) {
		Process process = ProcessWebDiagramDataRepository.getInstance()
				.getProcess(viewId);
		if (ProcessConstants.LABEL.equals(name)) {
			process.setLabel(value);
		}
		if (ProcessConstants.AUTHOR.equals(name)) {
			process.setAuthor(value);
		}
		if (ProcessConstants.REVISION.equals(name)) {
			process.setVersion(new BigInteger(value));
		}
		if (ProcessConstants.PROCESS_TYPE.equals(name)) {
			process.setProcessType(value);
		}
	}

	/**
	 * This method is used to update the attribute.
	 * 
	 * @param label
	 * @param drawingView
	 * @param element
	 * @param flowNodeElement2
	 */
	private void updateFlowElementLabel(String label,
			TSWebDrawingViewServer drawingView, TSModelElement element,
			FlowNodeElement flowNodeElement) {
		element.setAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_LABEL, label);
		element.setAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_NAME, label);
		if (null != flowNodeElement) {
			flowNodeElement.setElementName(label);
			flowNodeElement.setElementLabel(label);
		}
		/*
		 * if (null != graphObjects && !graphObjects.isEmpty()) { TSEObject
		 * object = (TSEObject) graphObjects.get(0); if (null != object &&
		 * object instanceof TSENode) { TSENode node = (TSENode) object; if
		 * (!ProcessElementTypes
		 * .TextAnnotation.getName().equals(element.getAttribute
		 * (ProcessConstants.PROCESS_FLOW_ATTRIBUTE_TYPE))) { TSENodeLabel
		 * nodeLabel = (TSENodeLabel) node.getLabels() .get(0);
		 * nodeLabel.setName(label); } } }
		 */
	}

}
