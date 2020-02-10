/**
 * 
 */
package com.tibco.cep.webstudio.server.command;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.tibco.cep.webstudio.server.model.process.AnnotationElement;
import com.tibco.cep.webstudio.server.model.process.AssociationElement;
import com.tibco.cep.webstudio.server.model.process.FlowNodeElement;
import com.tibco.cep.webstudio.server.model.process.Process;
import com.tibco.cep.webstudio.server.model.process.SequenceElement;
import com.tibco.cep.webstudio.server.ui.ProcessWebDiagramDataRepository;
import com.tomsawyer.interactive.command.TSCommand;
import com.tomsawyer.model.TSModel;
import com.tomsawyer.model.TSModelElement;
import com.tomsawyer.model.TSModelNotification;
import com.tomsawyer.view.drawing.TSModelDrawingView;

/**
 * This is the custom command used to
 * 
 * @author dijadhav
 * 
 */
public class ProcessElementDeleteCommand extends TSCommand {
	private static final String COMMA = ",";
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 7849434814515824807L;

	private Set<TSModelElement> modelElements = new LinkedHashSet<TSModelElement>();
	private Set<FlowNodeElement> flowNodeElements = new LinkedHashSet<FlowNodeElement>();
	private Set<AnnotationElement> annotataionElements = new LinkedHashSet<AnnotationElement>();
	private Set<AssociationElement> associationElements = new LinkedHashSet<AssociationElement>();
	private Set<SequenceElement> sequenceElementlElements = new LinkedHashSet<SequenceElement>();
	private TSModel ownerModel;
	private TSModelDrawingView view;
	private String viewId;
	private Process process;
	private Map<String, FlowNodeElement> incomingSquenceFlowNodeMap = new LinkedHashMap<String, FlowNodeElement>();
	private Map<String, FlowNodeElement> outgoingSquenceFlowNodeMap = new LinkedHashMap<String, FlowNodeElement>();

	public ProcessElementDeleteCommand() {
	}

	@Override
	protected void doAction() throws Throwable {

		if (null != modelElements && !modelElements.isEmpty()) {

			for (TSModelElement tsmodelelement : modelElements) {
				if (view.getModel().getModelIndex().contains(tsmodelelement)) {
					setAddToUndoHistory(true);
					TSModelNotification.removeElementFromModel(tsmodelelement,
							view.getModel());
					tsmodelelement.setOwnerModel(null);
				}
			}
			for (FlowNodeElement flowNodeElement : flowNodeElements) {
				ProcessDataUtils
						.deleteFlowNodeElement(process, flowNodeElement);
			}
			for (AnnotationElement annotationElement : annotataionElements) {
				ProcessDataUtils.deleteAnnotationElement(process,
						annotationElement);
			}
			for (AssociationElement associationElement : associationElements) {
				ProcessDataUtils.deleteAssociationElement(process,
						associationElement);
			}
			for (SequenceElement sequenceFlowElement : sequenceElementlElements) {
				ProcessDataUtils.deleteSequenceElement(process,
						sequenceFlowElement);
			}
			updateIncoming();
			updateOutGoing();
			view.updateView();
			updateteLatModificanTime();
		}
	}

	@Override
	protected void redoAction() throws Throwable {
		if (null != modelElements && !modelElements.isEmpty()) {
			for (TSModelElement tsmodelelement : modelElements) {

				ownerModel.removeElement(tsmodelelement);
			}
			for (FlowNodeElement flowNodeElement : flowNodeElements) {
				ProcessDataUtils
						.deleteFlowNodeElement(process, flowNodeElement);
			}
			for (AnnotationElement annotationElement : annotataionElements) {
				ProcessDataUtils.deleteAnnotationElement(process,
						annotationElement);
			}
			for (AssociationElement associationElement : associationElements) {
				ProcessDataUtils.deleteAssociationElement(process,
						associationElement);
			}
			for (SequenceElement sequenceFlowElement : sequenceElementlElements) {
				ProcessDataUtils.deleteSequenceElement(process,
						sequenceFlowElement);
			}
			for (Entry<String, FlowNodeElement> flowNodeElementEntry : outgoingSquenceFlowNodeMap
					.entrySet()) {
				FlowNodeElement flowNodeElement = flowNodeElementEntry.getValue();
				
				String outgoings = flowNodeElement.getOutgoing();
				if (null != outgoings && !outgoings.trim().isEmpty()) {
					outgoings=outgoings.trim();
					if(outgoings.endsWith(COMMA)){
						outgoings=outgoings+flowNodeElementEntry.getKey();
					}else{
						outgoings=outgoings+COMMA+flowNodeElementEntry.getKey();
					}
				}else{
					outgoings=flowNodeElementEntry.getKey();
				}
				flowNodeElement.setOutgoing(outgoings);
			}
			
			for (Entry<String, FlowNodeElement> flowNodeElementEntry : incomingSquenceFlowNodeMap
					.entrySet()) {
				FlowNodeElement flowNodeElement = flowNodeElementEntry.getValue();
				
				String incomings = flowNodeElement.getIncoming();
				if (null != incomings && !incomings.trim().isEmpty()) {
					incomings=incomings.trim();
					if(incomings.endsWith(COMMA)){
						incomings=incomings+flowNodeElementEntry.getKey();
					}else{
						incomings=incomings+COMMA+flowNodeElementEntry.getKey();
					}
				}else{
					incomings=flowNodeElementEntry.getKey();
				}
				flowNodeElement.setOutgoing(incomings);
			}
			
			view.updateView();
			updateteLatModificanTime();
		}
	}

	@Override
	protected void undoAction() throws Throwable {

		if (null != modelElements && !modelElements.isEmpty()) {
			for (TSModelElement tsmodelelement : modelElements) {
				ownerModel.addElement(tsmodelelement);
				view.selectViewObject(tsmodelelement);
			}

			process.getFlowNodes().getFlowNodeElement()
					.addAll(annotataionElements);
			process.getAssociations().getAssociationElement()
					.addAll(associationElements);
			process.getAnnotations().getAnnotationElement()
					.addAll(annotataionElements);
			process.getSequenceFlows().getSequenceElement()
					.addAll(sequenceElementlElements);
			updateIncoming();
			updateOutGoing();
			view.updateView();
			updateteLatModificanTime();
		}
	}

	public Set<TSModelElement> getModelElements() {
		return modelElements;
	}

	public void setModelElements(Set<TSModelElement> modelElements) {
		this.modelElements = modelElements;
	}

	public TSModel getOwnerModel() {
		return ownerModel;
	}

	public void setOwnerModel(TSModel ownerModel) {
		this.ownerModel = ownerModel;
	}

	public TSModelDrawingView getView() {
		return view;
	}

	public void setView(TSModelDrawingView view) {
		this.view = view;
	}

	public String getViewId() {
		return viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}

	/**
	 * Update the last modification time.
	 * 
	 * @param viewId
	 */
	private void updateteLatModificanTime() {
		Process process = ProcessWebDiagramDataRepository.getInstance()
				.getProcess(viewId);
		process.setLastModificationDate(new Date().toString());
	}

	/**
	 * @return the flowNodeElements
	 */
	public Set<FlowNodeElement> getFlowNodeElements() {
		return flowNodeElements;
	}

	/**
	 * @param flowNodeElements
	 *            the flowNodeElements to set
	 */
	public void setFlowNodeElements(Set<FlowNodeElement> flowNodeElements) {
		this.flowNodeElements = flowNodeElements;
	}

	/**
	 * @return the annotataionElements
	 */
	public Set<AnnotationElement> getAnnotataionElements() {
		return annotataionElements;
	}

	/**
	 * @param annotataionElements
	 *            the annotataionElements to set
	 */
	public void setAnnotataionElements(
			Set<AnnotationElement> annotataionElements) {
		this.annotataionElements = annotataionElements;
	}

	/**
	 * @return the associationElements
	 */
	public Set<AssociationElement> getAssociationElements() {
		return associationElements;
	}

	/**
	 * @param associationElements
	 *            the associationElements to set
	 */
	public void setAssociationElements(
			Set<AssociationElement> associationElements) {
		this.associationElements = associationElements;
	}

	/**
	 * @return the sequenceElementlElements
	 */
	public Set<SequenceElement> getSequenceElementlElements() {
		return sequenceElementlElements;
	}

	/**
	 * @param sequenceElementlElements
	 *            the sequenceElementlElements to set
	 */
	public void setSequenceElementlElements(
			Set<SequenceElement> sequenceElementlElements) {
		this.sequenceElementlElements = sequenceElementlElements;
	}

	/**
	 * @return the process
	 */
	public Process getProcess() {
		return process;
	}

	/**
	 * @param process
	 *            the process to set
	 */
	public void setProcess(Process process) {
		this.process = process;
	}

	/**
	 * @return the incomingSquenceFlowNodeMap
	 */
	public Map<String, FlowNodeElement> getIncomingSquenceFlowNodeMap() {
		return incomingSquenceFlowNodeMap;
	}

	/**
	 * @param incomingSquenceFlowNodeMap
	 *            the incomingSquenceFlowNodeMap to set
	 */
	public void setIncomingSquenceFlowNodeMap(
			Map<String, FlowNodeElement> incomingSquenceFlowNodeMap) {
		this.incomingSquenceFlowNodeMap = incomingSquenceFlowNodeMap;
	}

	/**
	 * @return the outgoingSquenceFlowNodeMap
	 */
	public Map<String, FlowNodeElement> getOutgoingSquenceFlowNodeMap() {
		return outgoingSquenceFlowNodeMap;
	}

	/**
	 * @param outgoingSquenceFlowNodeMap
	 *            the outgoingSquenceFlowNodeMap to set
	 */
	public void setOutgoingSquenceFlowNodeMap(
			Map<String, FlowNodeElement> outgoingSquenceFlowNodeMap) {
		this.outgoingSquenceFlowNodeMap = outgoingSquenceFlowNodeMap;
	}

	private void updateOutGoing() {
		for (Entry<String, FlowNodeElement> flowNodeElementEntry : outgoingSquenceFlowNodeMap
				.entrySet()) {
			FlowNodeElement flowNodeElement = flowNodeElementEntry.getValue();
			
			String outgoings = flowNodeElement.getOutgoing();
			if (null != outgoings && !outgoings.trim().isEmpty()) {
				if (outgoings.contains(COMMA)) {
					String[] outgoingArr = outgoings.split(COMMA);
					if (null != outgoingArr && outgoingArr.length > 0) {
						int index = 0;
						for (String outgoing : outgoingArr) {
							if (null != outgoing && !outgoing.isEmpty()
									&& outgoing.equals(flowNodeElementEntry.getKey())) {
								outgoingArr[index] = null;	
								outgoingSquenceFlowNodeMap.put(outgoing, flowNodeElement);
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
	}

	private void updateIncoming() {
		for (Entry<String, FlowNodeElement> flowNodeElementEntry : incomingSquenceFlowNodeMap
				.entrySet()) {
			FlowNodeElement flowNodeElement = flowNodeElementEntry.getValue();

			String incomings = flowNodeElement.getIncoming();
			if (null != incomings && !incomings.trim().isEmpty()) {
				if (incomings.contains(COMMA)) {
					String[] incomingArr = incomings.split(COMMA);
					if (null != incomingArr && incomingArr.length > 0) {
						int index = 0;
						for (String incoing : incomingArr) {
							if (null != incoing
									&& !incoing.isEmpty()
									&& incoing.equals(flowNodeElementEntry
											.getKey())) {
								incomingArr[index] = null;
								incomingSquenceFlowNodeMap.put(incoing, flowNodeElement);
								break;
							}
							index++;
						}
					}
					StringBuilder builder = new StringBuilder();
					if (null != incomingArr && incomingArr.length > 0) {
						int index = 0;
						for (String incoing : incomingArr) {
							if (null != incoing && !incoing.isEmpty()) {
								builder.append(incoing);
								if (index < incomingArr.length - 1) {
									builder.append(COMMA);
								}
							}
							index++;
						}
					}
					flowNodeElement.setIncoming(builder.toString());
				} else {
					incomingSquenceFlowNodeMap.put(incomings, flowNodeElement);
					flowNodeElement.setIncoming("");

				}
			}
		}

	}
}
