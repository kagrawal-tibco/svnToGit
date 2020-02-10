package com.tibco.cep.bpmn.model.designtime.ontology.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessIndex;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;

public class DefaultProcessIndex implements ProcessIndex {

	public EObjectWrapper<EClass, EObject> index;
	
	/**
	 * Constructor
	 * @param indexObject
	 */
	public DefaultProcessIndex(EObject indexObject) {
		this(EObjectWrapper.wrap(indexObject));
	}
	
	
	/**
	 * Constructor
	 * @param index
	 */
	public DefaultProcessIndex(EObjectWrapper<EClass, EObject> index) {
		if(index.isInstanceOf(BpmnModelClass.PROCESS) || index.isInstanceOf(BpmnModelClass.SUB_PROCESS) ||
			index.isInstanceOf(BpmnModelClass.DEFINITIONS)) {
			this.index = index;
		} else {
			throw new IllegalArgumentException("Not a Process"); //$NON-NLS-N$
		}
	}
	
	
	/**
	 * @return all contents
	 */
	protected Collection<EObject> getAllContents() {
		return getAllContents(index.getEInstance());		
	}
	
	/**
	 * @return all contents
	 */
	protected Collection<EObject> getAllContents(EObject contentRoot) {
		TreeIterator<EObject> contents = EcoreUtil.getAllContents(contentRoot, true);
		Collection<EObject> clist = new LinkedHashSet<EObject>();
		while(contents.hasNext()) {
			EObject next = contents.next();
			clist.add(next);
		}
		if(BpmnModelClass.DEFINITIONS.isSuperTypeOf(contentRoot.eClass())){
			EObjectWrapper<EClass, EObject> indexWrapper = EObjectWrapper.wrap(contentRoot);
			List<EObject> rootElements = BpmnCommonIndexUtils.getRootElements(indexWrapper);
			for(EObject rootElement:rootElements) {
				clist.add(rootElement);
				TreeIterator<EObject> rootContents = EcoreUtil.getAllContents(rootElement,true);
				while(rootContents.hasNext()) {
					clist.add(rootContents.next());
				}
			}
		}
		return clist;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.model.designtime.ontology.ProcessIndex#getAllElementsByType(org.eclipse.emf.ecore.EClass)
	 */
	public Collection<EObject> getAllElementsByType(EClass type) {
		return getElementsByType(index.getEInstance(), type);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.model.designtime.ontology.ProcessIndex#getAllEndEvents()
	 */
	@Override
	public Collection<EObject> getAllEndEvents() {
		return EcoreUtil.getObjectsByType(getAllContents(),BpmnModelClass.END_EVENT);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.common.ontology.BpmnOntology#getAllEvents()
	 */
	@Override
	public Collection<EObject> getAllEvents() {
		return EcoreUtil.getObjectsByType(getAllContents(),BpmnModelClass.EVENT);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.model.designtime.ontology.ProcessIndex#getAllFlowElements()
	 */
	@Override
	public Collection<EObject> getAllFlowElements() {
		return EcoreUtil.getObjectsByType(getAllContents(),BpmnModelClass.FLOW_ELEMENT);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.model.designtime.ontology.ProcessIndex#getAllFlowNodes()
	 */
	@Override
	public Collection<EObject> getAllFlowNodes() {
		return EcoreUtil.getObjectsByType(getAllContents(),BpmnModelClass.FLOW_NODE);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.common.ontology.BpmnOntology#getAllGateways()
	 */
	@Override
	public Collection<EObject> getAllGateways() {
		return EcoreUtil.getObjectsByType(getAllContents(),BpmnModelClass.GATEWAY);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.model.designtime.ontology.ProcessIndex#getAllIntermediateCatchEvents()
	 */
	@Override
	public Collection<EObject> getAllIntermediateCatchEvents() {
		return EcoreUtil.getObjectsByType(getAllContents(),BpmnModelClass.INTERMEDIATE_CATCH_EVENT);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.model.designtime.ontology.ProcessIndex#getAllIntermediateThrowEvents()
	 */
	@Override
	public Collection<EObject> getAllIntermediateThrowEvents() {
		return EcoreUtil.getObjectsByType(getAllContents(),BpmnModelClass.INTERMEDIATE_THROW_EVENT);
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.common.ontology.BpmnOntology#getRootElements()
	 */
	@Override
	public Collection<EObject> getAllRootElements() {
		return EcoreUtil.getObjectsByType(getAllContents(),BpmnModelClass.ROOT_ELEMENT);
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.common.ontology.BpmnOntology#getAllSequenceFlows()
	 */
	@Override
	public Collection<EObject> getAllSequenceFlows() {
		return EcoreUtil.getObjectsByType(getAllContents(),BpmnModelClass.SEQUENCE_FLOW);
	}
	
	
	@Override
	public Collection<EObject> getAllStartEvents() {
		return EcoreUtil.getObjectsByType(getAllContents(),BpmnModelClass.START_EVENT);
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.common.ontology.BpmnOntology#getAllSubProcesses()
	 */
	@Override
	public Collection<EObject> getAllSubProcesses() {
		return EcoreUtil.getObjectsByType(getAllContents(),BpmnModelClass.SUB_PROCESS);
	}


	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.common.ontology.BpmnOntology#getAllTasks()
	 */
	@Override
	public Collection<EObject> getAllTasks() {
		return EcoreUtil.getObjectsByType(getAllContents(),BpmnModelClass.TASK);
	}
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.common.ontology.BpmnOntology#getElementById(java.lang.String)
	 */
	@Override
	public EObject getElementById(String id) {
		Collection<EObject> elements = EcoreUtil.getObjectsByType(getAllContents(),BpmnModelClass.BASE_ELEMENT);
		for(EObject element:elements) {
			EObjectWrapper<EClass, EObject> elementWrapper = EObjectWrapper.wrap(element);
			final String eid = (String) elementWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			if(eid != null && eid.equals(id)) {
				return element;
			}
		}
		return null;
	}


	/**
	 * @param rootObject
	 * @param type
	 * @return
	 */
	public Collection<EObject> getElementsByType(EObject rootObject, EClass type) {
		 Collection<EObject> contents = getAllContents(rootObject);
		return EcoreUtil.getObjectsByType(contents,type);		
	}


	@Override
	public Collection<EObject> getFlowNodes(boolean hasIncoming,
			boolean hasOutgoing, EClass type) {
		return getFlowNodes(this.index.getEInstance(),hasIncoming, hasOutgoing, type);
	}
	
	/**
	 * @param pw
	 * @param hasIncoming
	 * @param hasOutgoing
	 * @return
	 */
	public Collection<EObject> getFlowNodes(EObject process, boolean hasIncoming, boolean hasOutgoing,
			EClass  type) {
				EObjectWrapper<EClass, EObject> pw = EObjectWrapper.wrap(process);
				Set<EObject> selectedflowElements = new HashSet<EObject>();
				EList<EObject> flowElements = pw.getListAttribute(BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENTS);
				Collection<EObject> flowNodes = EcoreUtil.getObjectsByType(flowElements, BpmnModelClass.FLOW_NODE);
				for (EObject element : flowNodes) {
					EObjectWrapper<EClass, EObject> eWrapper = EObjectWrapper.wrap(element);
					if(type != null) {
						if(!eWrapper.isInstanceOf(type)) {
							continue;
						}
					}
					boolean isIncoming = false;
					boolean isOutgoing = false;
					EList<EObject> incoming = eWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_INCOMING);
					EList<EObject> outgoing = eWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING);
					if(incoming != null && incoming.size() > 0 ) {
						isIncoming = true;
					}
					if(outgoing != null && outgoing.size() > 0) {
						isOutgoing = true;
					}
					if(hasIncoming && !hasOutgoing ) {
						if(isIncoming && !isOutgoing) {
							selectedflowElements.add(element);
						}
					}
					if(hasOutgoing && !hasIncoming) {
						if(isOutgoing && !isIncoming){
							selectedflowElements.add(element);
						}
					}
					if(hasOutgoing && hasIncoming) {
						if(isIncoming || isOutgoing) {
							selectedflowElements.add(element);
						}
					}
					if(!hasOutgoing && !hasIncoming) {
						if(!isIncoming && !isOutgoing) {
							selectedflowElements.add(element);
						}
					}
				}
				
				return selectedflowElements;
				
			}
	
	@Override
	public String getName() {
		return index.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.common.ontology.BpmnOntology#getProcess(org.eclipse.emf.ecore.EObject)
	 */
	public EObject getProcess(EObject element) {
		if(BpmnModelClass.PROCESS.isSuperTypeOf(element.eClass())) {
			return element;
		}
		EObject container = element.eContainer();
		while(container != null && !BpmnModelClass.PROCESS.isSuperTypeOf(container.eClass())) {
			container = container.eContainer();
		}
		return container;
	}


	/**
	 * @param element
	 * @param type
	 * @return
	 */
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.common.ontology.BpmnOntology#getProcessElementsByType(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EClass)
	 */
	public Collection<EObject> getProcessElementsByType(EObject element,EClass type) {
		EObject process = getProcess(element);
		if(process != null) {
			return getElementsByType(process, type);
		}
		return Collections.emptyList();
	}


	

}
