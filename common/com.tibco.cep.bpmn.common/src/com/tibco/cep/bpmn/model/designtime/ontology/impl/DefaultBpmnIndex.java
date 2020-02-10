package com.tibco.cep.bpmn.model.designtime.ontology.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.BpmnIndex;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.ROEObjectWrapper;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/**
 * @author pdhar
 *
 */
public class DefaultBpmnIndex extends DefaultProcessIndex implements BpmnIndex {
	/**
	 * Constructor
	 * @param index
	 */
	public DefaultBpmnIndex(EObjectWrapper<EClass, EObject> index) {
		super(index);
	}
	
	/**
	 * Constructor
	 * @param indexObject
	 */
	public DefaultBpmnIndex(EObject indexObject) {
		this(EObjectWrapper.wrap(indexObject));
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.common.ontology.BpmnOntology#getProcesses()
	 */
	@Override
	public Collection<EObject> getAllProcesses() {
		return EcoreUtil.getObjectsByType(getAllRootElements(), BpmnModelClass.PROCESS);
	}
	
	
	public EObject getProcessByPath(String path) {
		if(path.endsWith(CommonIndexUtils.PROCESS_EXTENSION)) {
			path = path.substring(0, path.length() - CommonIndexUtils.PROCESS_EXTENSION.length()-1);
		}
		for(EObject p: getAllProcesses()) {
			ROEObjectWrapper<EClass, EObject> pw = EObjectWrapper.wrap(p);
			String name = pw.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			String folder = pw.getAttribute(BpmnMetaModelConstants.E_ATTR_FOLDER);
			EObjectWrapper<EClass, EObject> vw = ExtensionHelper.getAddDataExtensionValueWrapper(p);
			int revision = (Integer)vw.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_VERSION);
			String filePath = "";
			if(folder.equals("/"))
				filePath = "/"+name;
			else
				filePath = folder + "/"+name;
			String versionFilePath = filePath+"_"+revision;
			if(path.equals(filePath) || versionFilePath.equals(path)) {
				return p;
			}
		}
		return null;
	}
	
	public EObject getProcessByPath(String folder, String name) {
		for(EObject p: getAllProcesses()) {
			ROEObjectWrapper<EClass, EObject> pw = EObjectWrapper.wrap(p);
			String n = pw.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			String f = pw.getAttribute(BpmnMetaModelConstants.E_ATTR_FOLDER);
			if(folder.equals(f) && n.equals(name)) {
				return p;
			}
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.common.ontology.BpmnOntology#getRootElements()
	 */
	@Override
	public Collection<EObject> getAllRootElements() {
		return BpmnCommonIndexUtils.getRootElements(index);
	}	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.common.ontology.BpmnOntology#getExtensions()
	 */
	@Override
	public Collection<EObject> getAllExtensions() {
		return index.getListAttribute(BpmnMetaModelConstants.E_ATTR_EXTENSIONS);
	}	
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.common.ontology.BpmnOntology#getProcessElementsByType(java.lang.String, org.eclipse.emf.ecore.EClass)
	 */
	public Collection<EObject> getProcessElementsByType(String processName,EClass type) {
		EObject process = getProcess(processName);
		if(process != null) {
			return getElementsByType(process, type);
		}
		return Collections.emptyList();
	}
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.common.ontology.BpmnOntology#getProcess(java.lang.String)
	 */
	public EObject getProcess(String processName) {
		for(EObject process:getAllProcesses()) {
			EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper.wrap(process);
			String pName = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			if(pName.equals(processName)){
				return process;
			}
		}
		return null;
		
	}
	
	public Collection<EObject> getStartEvents(EObject process) {
		Collection<EObject> sevts = Collections.emptyList();
		EObjectWrapper<EClass, EObject> pw = EObjectWrapper.wrap(process);
		if(pw.isInstanceOf(BpmnModelClass.PROCESS)) {
			sevts = EcoreUtil.getObjectsByType(getFlowNodes(process), BpmnModelClass.START_EVENT);
		}
		return sevts;
	}
	
	public Collection<EObject> getEndEvents(EObject process) {
		Collection<EObject> sevts = Collections.emptyList();
		EObjectWrapper<EClass, EObject> pw = EObjectWrapper.wrap(process);
		if(pw.isInstanceOf(BpmnModelClass.PROCESS)) {
			sevts = EcoreUtil.getObjectsByType(getFlowNodes(process), BpmnModelClass.START_EVENT);
		}
		return sevts;
	}
	
	public Collection<EObject> getFlowElements(EObject process) {
		EObjectWrapper<EClass, EObject> pw = EObjectWrapper.wrap(process);
		return pw.getListAttribute(BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENTS);		
	}
	
	public Collection<EObject> getFlowNodes(EObject process) {
		return getFlowNodes(process, true, true, null);
	}
	
	@Override
	public Collection<EObject> getSequenceFlows(EObject process) {
		Collection<EObject> fnodes = Collections.emptyList();
		EObjectWrapper<EClass, EObject> pw = EObjectWrapper.wrap(process);
		if(pw.isInstanceOf(BpmnModelClass.PROCESS)) {
			fnodes = EcoreUtil.getObjectsByType(
					pw.getListAttribute(BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENTS),
					BpmnModelClass.SEQUENCE_FLOW);
		}
		return fnodes;
	}


	@Override
	public Collection<EObject> getGateways(EObject process) {
		Collection<EObject> sevts = Collections.emptyList();
		EObjectWrapper<EClass, EObject> pw = EObjectWrapper.wrap(process);
		if(pw.isInstanceOf(BpmnModelClass.PROCESS)) {
			sevts = EcoreUtil.getObjectsByType(getFlowNodes(process), BpmnModelClass.GATEWAY);
		}
		return sevts;
	}


	@Override
	public Collection<EObject> getIntermediateEvents(EObject process) {
		Collection<EObject> sevts = Collections.emptyList();
		EObjectWrapper<EClass, EObject> pw = EObjectWrapper.wrap(process);
		if(pw.isInstanceOf(BpmnModelClass.PROCESS)) {
			sevts = EcoreUtil.getObjectsByType(getFlowNodes(process), BpmnModelClass.INTERMEDIATE_CATCH_EVENT);
	        final Collection<? extends EObject> throwEvents =
	                EcoreUtil.getObjectsByType(getFlowNodes(process), BpmnModelClass.INTERMEDIATE_THROW_EVENT);
			sevts.addAll(throwEvents); // Do not use the value of throwEvents directly in addAll (compilation issue).
		}
		return sevts;
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

	
	


	

}
