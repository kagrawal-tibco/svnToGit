package com.tibco.cep.bpmn.model.designtime.ontology.symbols;

import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.BpmnIndex;
import com.tibco.cep.bpmn.model.designtime.ontology.impl.DefaultBpmnIndex;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;

public class ProjectSymbolMap extends FlowElementSymbolMap implements RootSymbolMap {
	
	
	public ProjectSymbolMap(EObject obj) {
		super(obj,null);
	}
	
	@Override
	public Collection<SymbolEntry> getGlobalSymbolEntries() {
		return symbolMap;
	}
	
	/**
	 * @param processName
	 * @return
	 */
	public ProcessSymbolMap getProcessSymbolMap(EObject process) {
		ProcessSymbolMap processSymbolMap = null;
		for(SymbolMap outgoingMap:getOutgoingMaps()) {
			if(outgoingMap.getFlowElement().equals(process)) {
				processSymbolMap = (ProcessSymbolMap) outgoingMap;
				break;
			}
		}
		if(processSymbolMap == null) {
			processSymbolMap = new ProcessSymbolMapImpl(process,this);
			getOutgoingMaps().add(processSymbolMap);
			processSymbolMap.getIncomingMaps().add(this);
		}
		return processSymbolMap;
	}
	
	
	/**
	 * Builds the scoped symbol hierarchy tree
	 * @param projectName
	 * @return
	 */
	public void refresh() {
		BpmnIndex ontology =  new DefaultBpmnIndex(getFlowElement());
		Collection<EObject> processes = ontology.getAllProcesses();
		for(EObject proc: processes) {
			ProcessSymbolMap processSymbolMap = getProcessSymbolMap(proc);
			Collection<EObject> flowNodes = ontology.getFlowNodes(proc);
			for(EObject node:flowNodes) {
				processSymbolMap.initElementSymbolMap(node);
			}
			Collection<EObject> seqFlows = ontology.getSequenceFlows(proc);
			for(EObject seqFlow:seqFlows) {
				SymbolMap seqSymbolMap = processSymbolMap.initElementSymbolMap(seqFlow);
				EObjectWrapper<EClass, EObject> seqFlowWrapper = EObjectWrapper.wrap(seqFlow);
				EObject source = seqFlowWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF);
				EObject target = seqFlowWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF);
				FlowElementSymbolMap sourceSymbolMap = processSymbolMap.getSymbolMapRegistry().get(source);
				FlowElementSymbolMap targetSymbolMap = processSymbolMap.getSymbolMapRegistry().get(target);
				sourceSymbolMap.getOutgoingMaps().add(seqSymbolMap);
				targetSymbolMap.getIncomingMaps().add(seqSymbolMap);
				seqSymbolMap.getIncomingMaps().add(sourceSymbolMap);
				seqSymbolMap.getOutgoingMaps().add(targetSymbolMap);
			}
			Collection<EObject> startNodes = ontology.getFlowNodes(proc, false, true, BpmnModelClass.FLOW_NODE);
			for(EObject startNode: startNodes) {
				FlowElementSymbolMap nodeSymbolMap = processSymbolMap.getSymbolMapRegistry().get(startNode);
				processSymbolMap.getOutgoingMaps().add(nodeSymbolMap);
			}
			
			Collection<EObject> endNodes = ontology.getFlowNodes(proc, true, false, BpmnModelClass.FLOW_NODE);
			for(EObject endNode: endNodes) {
				FlowElementSymbolMap nodeSymbolMap = processSymbolMap.getSymbolMapRegistry().get(endNode);
				processSymbolMap.getIncomingMaps().add(nodeSymbolMap);
			}			
			
		}
	}
	
	

}
