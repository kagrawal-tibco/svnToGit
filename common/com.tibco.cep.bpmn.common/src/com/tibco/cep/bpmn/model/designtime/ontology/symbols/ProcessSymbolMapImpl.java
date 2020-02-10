package com.tibco.cep.bpmn.model.designtime.ontology.symbols;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.ProcessIndex;
import com.tibco.cep.bpmn.model.designtime.ontology.impl.DefaultProcessIndex;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;


/**
 * @author pdhar
 * 
 * This class 
 *
 */
public class ProcessSymbolMapImpl extends FlowElementSymbolMap implements ProcessSymbolMap {
	Map<EObject,FlowElementSymbolMap> symbolMapRegistry = new HashMap<EObject,FlowElementSymbolMap>();
	
	public ProcessSymbolMapImpl(EObject obj) {
		this(obj,null);
	}
	
	public ProcessSymbolMapImpl(EObject obj,SymbolMap parent) {
		super(obj,parent);
		init();
	}
	
	private void addFlowElementSymbolMap(EObject flowElement) {
		if(!symbolMapRegistry.containsKey(flowElement)){
//			EObjectWrapper<EClass, EObject> flowElementWrapper = EObjectWrapper.wrap(flowElement);
			FlowElementSymbolMap flowElementSymbolMap = new FlowElementSymbolMap(flowElement,this);
			symbolMapRegistry.put(flowElement, flowElementSymbolMap);		
		}
	}
	
	
	

	public boolean containsElementSymbolMap(EObject flowElement) {
		return symbolMapRegistry.containsKey(flowElement);
	}
	
	public SymbolMap getElementSymbolMap(EObject flowElement) {
		if(!containsElementSymbolMap(flowElement)) {
			addFlowElementSymbolMap(flowElement);
		}
		return symbolMapRegistry.get(flowElement);
	}
	
	@Override
	public Collection<SymbolMap> getEndSymbolMaps() {
		return incomingMaps;
	}
	
	@Override
	public SymbolMap getRootSymbolMap() {
		return getParentSymbolMap();
	}
	
	@Override
	public Collection<SymbolMap> getStartSymbolMaps() {
		return outgoingMaps;
	}
	
	@Override
	public Map<EObject, FlowElementSymbolMap> getSymbolMapRegistry() {
		return symbolMapRegistry;
	}
	
	@Override
	public boolean hasRootSymbolMap() {
		return getParentSymbolMap() != null && getParentSymbolMap()  instanceof RootSymbolMap;
	}
	
	/**
	 * Builds the scoped symbol hierarchy tree
	 * @param projectName
	 * @return
	 */
	public void init() {
		ProcessIndex pi = new DefaultProcessIndex(getFlowElement());
		Collection<EObject> flowNodes = pi.getAllFlowNodes();
		for(EObject node:flowNodes) {
			initElementSymbolMap(node);
		}
		Collection<EObject> seqFlows = pi.getAllSequenceFlows();
		for(EObject seqFlow:seqFlows) {
			SymbolMap seqSymbolMap = initElementSymbolMap(seqFlow);
			EObjectWrapper<EClass, EObject> seqFlowWrapper = EObjectWrapper.wrap(seqFlow);
			EObject source = seqFlowWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF);
			EObject target = seqFlowWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF);
			FlowElementSymbolMap sourceSymbolMap = getSymbolMapRegistry().get(source);
			FlowElementSymbolMap targetSymbolMap = getSymbolMapRegistry().get(target);
			sourceSymbolMap.getOutgoingMaps().add(seqSymbolMap);
			targetSymbolMap.getIncomingMaps().add(seqSymbolMap);
			seqSymbolMap.getIncomingMaps().add(sourceSymbolMap);
			seqSymbolMap.getOutgoingMaps().add(targetSymbolMap);
		}
		Collection<EObject> startNodes = pi.getFlowNodes(false, true, BpmnModelClass.FLOW_NODE);
		for(EObject startNode: startNodes) {
			FlowElementSymbolMap nodeSymbolMap = getSymbolMapRegistry().get(startNode);
			getOutgoingMaps().add(nodeSymbolMap);
		}
		
		Collection<EObject> endNodes = pi.getFlowNodes(true, false, BpmnModelClass.FLOW_NODE);
		for(EObject endNode: endNodes) {
			FlowElementSymbolMap nodeSymbolMap = getSymbolMapRegistry().get(endNode);
			getIncomingMaps().add(nodeSymbolMap);
		}	

	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.bpmn.model.runtime.symbols.ProcessSymbolMap#initElementSymbolMap(org.eclipse.emf.ecore.EObject)
	 */
	public SymbolMap initElementSymbolMap(EObject flowElement) {
		EObjectWrapper<EClass, EObject> flowElementWrapper = EObjectWrapper.wrap(flowElement);
		SymbolMap flowElementSymbolMap = getElementSymbolMap(flowElement);
		EList<EObject> symbolList = ExtensionHelper.getExtensionAttributeValue(
				flowElementWrapper, 
				BpmnMetaModelExtensionConstants.E_ATTR_VARIABLES);
		if(symbolList != null) {
			for(EObject symbol:symbolList) {
				EObjectWrapper<EClass, EObject> symbolWrapper = EObjectWrapper.wrap(symbol);
				flowElementSymbolMap.addSymbol(symbolWrapper.getEInstance());
			}
		}
		return flowElementSymbolMap;
	}

}
