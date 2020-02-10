package com.tibco.cep.webstudio.server.command;

import java.util.List;

import com.tibco.cep.webstudio.client.process.ProcessConstants;
import com.tibco.cep.webstudio.server.model.process.AnnotationElement;
import com.tibco.cep.webstudio.server.model.process.AssociationElement;
import com.tibco.cep.webstudio.server.model.process.FlowNodeElement;
import com.tibco.cep.webstudio.server.model.process.Process;
import com.tibco.cep.webstudio.server.model.process.SequenceElement;
import com.tomsawyer.model.TSModel;
import com.tomsawyer.model.TSModelElement;

/**
 * This class has some utility methods to process, its components related data.
 * 
 * @author dijadhav
 *
 */
public class ProcessDataUtils {
	public static AssociationElement getAssociationElement(String elementId,
			List<AssociationElement> associationElements) {
		AssociationElement associationElement = null;
		if (null != associationElements && !associationElements.isEmpty()) {
			for (AssociationElement element : associationElements) {
				if (null != element
						&& element.getElementId().equalsIgnoreCase(elementId)) {
					associationElement = element;
					break;
				}
			}
		}
		return associationElement;

	}

	public static AnnotationElement getAnnotationElement(String elementId,
			List<AnnotationElement> annotationElements) {
		AnnotationElement annotationElement = null;
		if (null != annotationElements && !annotationElements.isEmpty()) {
			for (AnnotationElement element : annotationElements) {
				if (null != element
						&& element.getElementId().equalsIgnoreCase(elementId)) {
					annotationElement = element;
					break;
				}
			}
		}
		return annotationElement;

	}

	public static SequenceElement getSequeceElement(String elementId,
			List<SequenceElement> sequenceElements) {
		SequenceElement sequenceElement = null;
		if (null != sequenceElements && !sequenceElements.isEmpty()) {
			for (SequenceElement element : sequenceElements) {
				if (null != element
						&& element.getElementId().equalsIgnoreCase(elementId)) {
					sequenceElement = element;
					break;
				}
			}
		}
		return sequenceElement;

	}

	public static FlowNodeElement getFlowNodeElement(Process process, String elementId) {
		List<FlowNodeElement> flowNodeElements = process.getFlowNodes()
				.getFlowNodeElement();
		FlowNodeElement flowNodeElement = null;
		if (null != flowNodeElements && !flowNodeElements.isEmpty()) {
			for (FlowNodeElement element : flowNodeElements) {
				if (null != element
						&& element.getElementId().equalsIgnoreCase(elementId)) {
					flowNodeElement = element;
					break;
				}
			}
		}
		return flowNodeElement;
	}
	public static TSModelElement getModelElementById(TSModel model, String elementId) {

		TSModelElement element = null;
		List<TSModelElement> modelElements = model.getModelElements();
		if (null != modelElements && !modelElements.isEmpty()) {

			for (TSModelElement modelElement : modelElements) {
				if (null != modelElement) {
					
					String id = (String) modelElement
							.getAttribute(ProcessConstants.PROCESS_FLOW_ATTRIBUTE_ID);

					if(null==id||id.trim().isEmpty()){
						id=(String) modelElement
								.getAttribute(ProcessConstants.PROCESS_SEQUENCE_ATTRIBUTE_ID);
					}
						if (id.equalsIgnoreCase(elementId)) {
						element =  modelElement;
						break;
					}
				}
			}
		}
		return element;
	}
	public static void deleteFlowNodeElement(Process process, FlowNodeElement flowNodeElement) {
		List<FlowNodeElement> flowNodeElements = process.getFlowNodes()
				.getFlowNodeElement();
		if (null != flowNodeElements && !flowNodeElements.isEmpty()) {
			flowNodeElements.remove(flowNodeElement);
		}
	}
	public static void deleteSequenceElement(Process process, SequenceElement sequenceElement) {
		List<SequenceElement> sequenceElements = process.getSequenceFlows().getSequenceElement();
		if (null != sequenceElements && !sequenceElements.isEmpty()) {
			sequenceElements.remove(sequenceElement);
		}
	}
	public static void deleteAssociationElement(Process process, AssociationElement associationElement) {
		List<AssociationElement> associationElements = process.getAssociations()
				.getAssociationElement();
		if (null != associationElements && !associationElements.isEmpty()) {
			associationElements.remove(associationElement);
		}
	}
	public static void deleteAnnotationElement(Process process, AnnotationElement annotationElement) {
		List<AnnotationElement> annotationElements = process.getAnnotations().getAnnotationElement();
		if (null != annotationElements && !annotationElements.isEmpty()) {
			annotationElements.remove(annotationElement);
		}
	}
}
