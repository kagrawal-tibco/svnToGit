package com.tibco.cep.bpmn.model.designtime.ontology;

import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * @author pdhar
 * Ontology for BPMN Elements to be used by codegen and runtime
 */
/**
 * @author pdhar
 *
 */
public interface BpmnIndex extends BaseIndex {

	
	/**
	 * @return collection of processes
	 */
	Collection<EObject> getAllProcesses();
	
	/**
	 * @return collection of extensions
	 */
	Collection<EObject> getAllExtensions();
	
	/**
	 * @param processName
	 * @param type
	 * @return
	 */
	public Collection<EObject> getProcessElementsByType(String processName, EClass type);
	
	public EObject getProcessByPath(String path);
	
	public EObject getProcessByPath(String folder, String name);

	/**
	 * @param processName
	 * @return
	 */
	public EObject getProcess(String processName);

	

	/**
	 * @param element
	 * @return
	 */
	public EObject getProcess(EObject element);
	
	/**
	 * @param process
	 * @return  collection of startevents
	 */
	Collection<EObject> getStartEvents(EObject process);
	
	
	/**
	 * @param process
	 * @return collections of end events
	 */
	Collection<EObject> getEndEvents(EObject process);
	
	/**
	 * @param process
	 * @return collections of intermediate events
	 */
	Collection<EObject> getIntermediateEvents(EObject process);
	
	/**
	 * @param process
	 * @return collections of gateways
	 */
	Collection<EObject> getGateways(EObject process);
	
	
	/**
	 * @param process
	 * @return  collection of flow nodes
	 */
	Collection<EObject> getFlowElements(EObject process);
	
	/**
	 * @param process
	 * @return  collection of flow nodes
	 */
	Collection<EObject> getFlowNodes(EObject process);
	
	
	/**
	 * @param process
	 * @return  collection of sequence flows
	 */
	Collection<EObject> getSequenceFlows(EObject process);

	/**
	 * @param pw
	 * @param hasIncoming
	 * @param hasOutgoing
	 * @return
	 */
	public Collection<EObject> getFlowNodes(EObject process, boolean hasIncoming, boolean hasOutgoing,
			EClass  type);
	
	

	

	
	

}
