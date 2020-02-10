package com.tibco.cep.bpmn.model.designtime.ontology;

import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

public interface BaseIndex {
	
	/**
	 * @return
	 */
	public String getName();
	
	/**
	 * @return collection of RootElements
	 */
	Collection<EObject> getAllRootElements();
	
	/**
	 * @param id
	 * @return
	 */
	EObject getElementById(String id);
	
	/**
	 * @return Collection of Tasks
	 */
	Collection<EObject> getAllTasks();
	
	
	/**
	 * @return Collection of sequence flows
	 */
	Collection<EObject> getAllSequenceFlows();
	
	
	
	/**
	 * @return Collection of events
	 */
	Collection<EObject> getAllEvents();
	
	
	/**
	 * @return Collection of gateways
	 */
	Collection<EObject> getAllGateways();
	
	
	/**
	 * @return Collection of sub processes
	 */
	Collection<EObject> getAllSubProcesses();
	
	
	
	
	
	/**
	 * @return  collection of startevents
	 */
	Collection<EObject> getAllStartEvents();
	
	
	/**
	 * @return collections of end events
	 */
	Collection<EObject> getAllEndEvents();
	
	/**
	 * @return collections of intermediate events
	 */
	Collection<EObject> getAllIntermediateCatchEvents();
	
	
	Collection<EObject> getAllIntermediateThrowEvents();
	
	
	/**
	 * @return  collection of flow nodes
	 */
	Collection<EObject> getAllFlowElements();
	
	/**
	 * @return  collection of flow nodes
	 */
	Collection<EObject> getAllFlowNodes();
	
	
	/**
	 * @param type
	 * @return
	 */
	public Collection<EObject> getAllElementsByType(EClass type);
	
	
	/**
	 * @param element
	 * @param type
	 * @return
	 */
	public Collection<EObject> getProcessElementsByType(EObject element, EClass type);

}
