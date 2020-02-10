package com.tibco.cep.bpmn.model.designtime.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.basic.BasicBorders.SplitPaneBorder;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtension;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.symbols.ProcessSymbolMap;
import com.tibco.cep.bpmn.model.designtime.ontology.symbols.ProcessSymbolMapImpl;
import com.tibco.cep.bpmn.model.designtime.ontology.symbols.SymbolMap;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmComponent;
import com.tibco.xml.tns.TnsComponent;
import com.tibco.xml.tns.impl.TargetNamespaceCache;
import com.tibco.xml.tns.parse.TnsDocument;
import com.tibco.xml.tns.parse.TnsFragment;

public class BpmnCommonModelUtils {

	public static final String BPMN_DOT_SEPARATOR = ".";
	public static final String BPMN_PREFIX_SEPARATOR = "_";
	public static final String BPMN_PROCESS_PREFIX = "pcs";
	public static final String BPMN_RULE_PREFIX = "rul";
	public static final String BPMN_RULE_FUNCTION_PREFIX = "rfn";
	public static final String BPMN_CONCEPT_PREFIX = "cpt";
	public static final String BPMN_EVENT_PREFIX = "evt";
	public static final String BPMN_TIME_EVENT_PREFIX = "tev";
	public static final String BPMN_SCORECARD_PREFIX = "scd";
	public static final String BPMN_VARIABLE_PREFIX = "v";
	public static final String CODE_GEN_FOLDER_PATH = "/codegen";
	public static final String FLOW_ELEMENT_HANDLER_PREFIX = "handle";
	public static final String FLOW_ELEMENT_EVAL_PREFIX = "eval";

	/**
	 * @param startElement
	 * @return
	 */
	public static List<EObject> getNextFlowElements(EObject startElement) {
		EObjectWrapper<EClass, EObject> startElementWrapper = EObjectWrapper.wrap(startElement);
		if(!startElementWrapper.isInstanceOf(BpmnModelClass.FLOW_ELEMENT)){
			return Collections.emptyList();
		}
		List<EObject> flowElements = new LinkedList<EObject>();
		EObject element = startElement;
		EObjectWrapper<EClass, EObject> elementWrapper = EObjectWrapper.wrap(element);
		
		if(elementWrapper.isInstanceOf(BpmnModelClass.FLOW_NODE)) {
			EList<EObject> outgoing = elementWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING);
			for(EObject oe: outgoing) {
//				EObjectWrapper<EClass, EObject> oeWrapper = EObjectWrapper.wrap(oe);
				flowElements.add(oe);
			}
			
		} else if(elementWrapper.isInstanceOf(BpmnModelClass.SEQUENCE_FLOW)) {
			EObject targetRef = elementWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF);
			EObjectWrapper<EClass, EObject> targetWrapper = EObjectWrapper.wrap(targetRef);
			flowElements.add(targetWrapper.getEInstance());			
		}
		return flowElements;		
	}

	/**
	 * @param startElement
	 * @return
	 */
	public static List<EObject> getNextFlowNodes(EObject startElement) {
		EObjectWrapper<EClass, EObject> startElementWrapper = EObjectWrapper.wrap(startElement);
		if(!startElementWrapper.isInstanceOf(BpmnModelClass.FLOW_ELEMENT)){
			return Collections.emptyList();
		}
		List<EObject> flowNodes = new LinkedList<EObject>();
		EObject element = startElement;
		EObjectWrapper<EClass, EObject> elementWrapper = EObjectWrapper.wrap(element);
		
		if(elementWrapper.isInstanceOf(BpmnModelClass.FLOW_NODE)) {
			EList<EObject> outgoing = elementWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING);
			for(EObject oe: outgoing) {
//				EObjectWrapper<EClass, EObject> oeWrapper = EObjectWrapper.wrap(oe);
				flowNodes.addAll(getNextFlowNodes(oe));
			}
			
		} else if(elementWrapper.isInstanceOf(BpmnModelClass.SEQUENCE_FLOW)) {
			EObject targetRef = elementWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF);
			EObjectWrapper<EClass, EObject> targetWrapper = EObjectWrapper.wrap(targetRef);
			flowNodes.add(targetWrapper.getEInstance());			
		}
		return flowNodes;		
	}

	/**
	 * @param startElement
	 * @return
	 */
	public static List<EObject> getNextInlineFlowNode(EObject startElement) {
		EObjectWrapper<EClass, EObject> startElementWrapper = EObjectWrapper.wrap(startElement);
		if(!startElementWrapper.isInstanceOf(BpmnModelClass.FLOW_ELEMENT)){
			return Collections.emptyList();
		}
		List<EObject> flowElements = new LinkedList<EObject>();
		EObject element = startElement;
		EObjectWrapper<EClass, EObject> elementWrapper = EObjectWrapper.wrap(element);
		
		if(elementWrapper.isInstanceOf(BpmnModelClass.FLOW_NODE)) {
			EList<EObject> outgoing = elementWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING);
			for(EObject oe: outgoing) {
//				EObjectWrapper<EClass, EObject> oeWrapper = EObjectWrapper.wrap(oe);
				flowElements.addAll(getNextInlineFlowNode(oe));
			}
			
		} else if(elementWrapper.isInstanceOf(BpmnModelClass.SEQUENCE_FLOW)) {
			EObject targetRef = elementWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF);
			EObjectWrapper<EClass, EObject> targetWrapper = EObjectWrapper.wrap(targetRef);
			if(targetWrapper.isInstanceOf(BpmnModelClass.ACTIVITY)) {
				final Boolean inline = ExtensionHelper.getExtensionAttributeValue(targetWrapper, BpmnMetaModelExtensionConstants.E_ATTR_INLINE);
				if(inline) {
					flowElements.add(targetWrapper.getEInstance());
				}
			}
		}
		return flowElements;		
	}

	public static Map<ExpandedName,TnsComponent> getCachedElements(TargetNamespaceCache tnsCache) {
		HashMap<ExpandedName,TnsComponent> cachedElements = new HashMap<ExpandedName,TnsComponent>();
		Iterator<?> ir = tnsCache.getLocations();
		while (ir.hasNext()) {
	        String loc = (String)ir.next();
	        TnsDocument doc = tnsCache.getDocument(loc);
	        if(doc == null)
	        	continue;
	        Iterator<?> frags = doc.getFragments();
	
	        while (frags.hasNext()) {
	            TnsFragment frag = (TnsFragment) frags.next();
	            Iterator<?> r = frag.getComponents(SmComponent.ELEMENT_TYPE);
	            while (r.hasNext()) {
	                TnsComponent comp = (TnsComponent)r.next();
	                cachedElements.put(comp.getExpandedName(), comp);
	            }
	        }
	    }
		return cachedElements;
	}
	
	public static Map<ExpandedName, TnsComponent> getCachedElementsForLocation(
			TargetNamespaceCache tnsCache, String loc) {
		HashMap<ExpandedName, TnsComponent> cachedElements = new HashMap<ExpandedName, TnsComponent>();
		try {
			TnsDocument doc = tnsCache.getDocument(loc);
			if (doc != null) {
				Iterator<?> frags = doc.getFragments();

				while (frags.hasNext()) {
					TnsFragment frag = (TnsFragment) frags.next();
					Iterator<?> r = frag
							.getComponents(SmComponent.ELEMENT_TYPE);
					while (r.hasNext()) {
						TnsComponent comp = (TnsComponent) r.next();
						cachedElements.put(comp.getExpandedName(), comp);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cachedElements;
	}
	

	/**
	 * @param process
	 * @param parentLane
	 * @return
	 */
	public static Collection<EObject> getFlowNodes(EObjectWrapper<EClass, EObject> process, EObjectWrapper<EClass, EObject> parentLane) {
	
		List<EObject> flowElementsInLane = new ArrayList<EObject>();
	
		EList<EObject> flowElements = process.getListAttribute(BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENTS);
		Collection<EObject> flowNodes = EcoreUtil.getObjectsByType(
				flowElements, BpmnModelClass.FLOW_NODE);
		for (EObject element : flowNodes) {
	
			EObjectWrapper<EClass, EObject> eWrapper = EObjectWrapper.wrap(element);
			EList<EObject> lanes = eWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_LANES);
	
			List<EObject> lanepath = getLanePath(process,
					parentLane);
	
			for (EObject lane : lanes) {
				EObjectWrapper<EClass, EObject> lWrapper = EObjectWrapper
						.wrap(lane);
				if (parentLane.equals(lWrapper)
						&& lanepath.contains(lWrapper.getEInstance())) {
					flowElementsInLane.add(element);
				}
			}
		}
		return flowElementsInLane;
	}

	/**
	 * @param process
	 * @param parentLane
	 * @return
	 */
	public static Collection<EObject> getFlowNodes(EObjectWrapper<EClass, EObject> wrapper) {
	
		EList<EObject> flowElements = wrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENTS);
		Collection<EObject> flowNodes = EcoreUtil.getObjectsByType(
				flowElements, BpmnModelClass.FLOW_NODE);
		return flowNodes;
	}

	public static List<EObject> getAllFlowNodes(EObject model) {
		TreeIterator<Object> allContents = EcoreUtil.getAllContents(model, false);
		List<EObject> flowNodes = new ArrayList<EObject>();
		while (allContents.hasNext()) {
			Object object = (Object) allContents.next();
			if (BpmnModelClass.FLOW_NODE.isInstance(object))
		      {
		    	  flowNodes.add((EObject)object);
		      }
		}
	
		return flowNodes;
	}
	
	public static List<EObject> getAllSequenceFlows(EObject model) {
		TreeIterator<Object> allContents = EcoreUtil.getAllContents(model, false);
		List<EObject> sequenceFLows = new ArrayList<EObject>();
		while (allContents.hasNext()) {
			Object object = (Object) allContents.next();
			if (BpmnModelClass.SEQUENCE_FLOW.isInstance(object))
		      {
				sequenceFLows.add((EObject)object);
		      }
		}
	
		return sequenceFLows;
	}

	public static List<EObject> getAllBaseElements(EObject model) {
		TreeIterator<Object> allContents = EcoreUtil.getAllContents(model, false);
		List<EObject> baseElements = new ArrayList<EObject>();
		while (allContents.hasNext()) {
			Object object = (Object) allContents.next();
			if (BpmnModelClass.BASE_ELEMENT.isInstance(object))
		      {
				baseElements.add((EObject)object);
		      }
		}
	
		return baseElements;
	}

	public static Collection<EObject> getFlowNodes(EObjectWrapper<EClass, EObject> wrapper, EClass type) {
	
		EList<EObject> listAttribute = wrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENTS);
		List<EObject> flowElements = new ArrayList<EObject>(listAttribute.size()); 
		flowElements.addAll(listAttribute);
		Collection<EObject> flowNodes = EcoreUtil.getObjectsByType(
				flowElements, type);
		
	
		return flowNodes;
	}

	public static Collection<EObject> getArtifactNodes(EObjectWrapper<EClass, EObject> process) {
	
		EList<EObject> artifacts = process.getListAttribute(BpmnMetaModelConstants.E_ATTR_ARTIFACTS);
		Collection<EObject> textAnnotations = EcoreUtil.getObjectsByType(
				artifacts, BpmnModelClass.TEXT_ANNOTATION);
		
		return textAnnotations;
	}

	/**
	 * @param process
	 * @param parentLane
	 * @return
	 */
	public static Collection<EObject> getSequenceFlows(EObjectWrapper<EClass, EObject> process, EObjectWrapper<EClass, EObject> parentLane) {
		List<EObject> seqFlowsInLane = new ArrayList<EObject>();
	
		EList<EObject> flowElements = process.getListAttribute(BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENTS);
		Collection<EObject> sequenceFlows = EcoreUtil.getObjectsByType(
				flowElements, BpmnModelClass.SEQUENCE_FLOW);
		for (EObject element : sequenceFlows) {
//			final String parentLaneId = (String) parentLane.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			final EObjectWrapper<EClass, EObject> eWrapper = EObjectWrapper
					.wrap(element);
			final EList<EObject> lanes = eWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_LANES);
	
			List<EObject> lanepath = getLanePath(process,
					parentLane);
	
			for (EObject lane : lanes) {
				EObjectWrapper<EClass, EObject> lWrapper = EObjectWrapper
						.wrap(lane);
				if (lWrapper.equals(parentLane)
						&& lanepath.contains(lWrapper.getEInstance())) {
					// if(parentLaneId.equals(lWrapper.getAttribute("id"))){
					seqFlowsInLane.add(element);
				}
			}
		}
		return seqFlowsInLane;
	}

	/**
	 * @param process
	 * @param parentLane
	 * @return
	 */
	public static Collection<EObject> getSequenceFlowsInSubprocess(EObjectWrapper<EClass, EObject> subProcess) {
//		List<EObject> seqFlowsInLane = new ArrayList<EObject>();
	
		EList<EObject> flowElements = subProcess.getListAttribute(BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENTS);
		Collection<EObject> sequenceFlows = EcoreUtil.getObjectsByType(
				flowElements, BpmnModelClass.SEQUENCE_FLOW);
	
		return sequenceFlows;
	}
	
	
	public static Collection<EObject> getSubprocess(ROEObjectWrapper<EClass, EObject> flowelementContainer) {
	
		EList<EObject> flowElements = flowelementContainer.getListAttribute(BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENTS);
		Collection<EObject> subProcesses = EcoreUtil.getObjectsByType(
				flowElements, BpmnModelClass.SUB_PROCESS);
	
		return subProcesses;
	}

	/**
	 * @param process
	 * @param parentLane
	 * @return
	 */
	public static Collection<EObject> getAssociations(EObjectWrapper<EClass, EObject> process) {
		
		EList<EObject> artifacts = process.getListAttribute(BpmnMetaModelConstants.E_ATTR_ARTIFACTS);
		Collection<EObject> associations = EcoreUtil.getObjectsByType(
				artifacts, BpmnModelClass.ASSOCIATION);
		
		return associations;
	}

	public static List<EObject> getLanePath(EObjectWrapper<EClass, EObject> process, EObjectWrapper<EClass, EObject> endLane) {
		List<EObjectWrapper<EClass, EObject>> laneSets = process
				.getWrappedEObjectList(BpmnMetaModelConstants.E_ATTR_LANE_SETS);
		for (EObjectWrapper<EClass, EObject> laneSet : laneSets) {
			List<EObjectWrapper<EClass, EObject>> lanes = laneSet
					.getWrappedEObjectList(BpmnMetaModelConstants.E_ATTR_LANES);
			for (EObjectWrapper<EClass, EObject> lane : lanes) {
				if (lane.equals(endLane)) {
					List<EObject> chain = new ArrayList<EObject>();
					chain.add(lane.getEInstance());
					return chain;
				} else {
					List<Pair<EObject>> pairs = new ArrayList<Pair<EObject>>();
					getLanePairs(pairs, lane.getEInstance());
					List<EObject> chain = QuickFind.quickFind(pairs, lane
							.getEInstance(), endLane.getEInstance());
					if (chain.contains(lane.getEInstance())
							&& chain.contains(endLane.getEInstance())) {
						return chain;
					}
				}
			}
		}
	
		return null;
	}

	public static void getLanePairs(List<Pair<EObject>> pairs, final EObject parentLane) {
		// TODO Auto-generated method stub
		EObject childLaneSet = EObjectWrapper.wrap(parentLane).getAttribute(BpmnMetaModelConstants.E_ATTR_CHILD_LANE_SET);
		if(childLaneSet != null) {
			List<EObject> lanes = EObjectWrapper.wrap(childLaneSet).getListAttribute(BpmnMetaModelConstants.E_ATTR_LANES);
			for(EObject lane:lanes) {
				pairs.add(new Pair<EObject>(parentLane,lane));
				getLanePairs(pairs,lane);
				
			}
		}
		
	}

	/**
	 * @param elementWrapper
	 * @return
	 */
	public static EObjectWrapper<EClass, EObject> getProcess(EObjectWrapper<EClass, EObject> elementWrapper) {
		return getProcess(elementWrapper.getEInstance());
	}

	public static EObjectWrapper<EClass, EObject> getProcess(EObject element) {
		if(BpmnModelClass.PROCESS.isSuperTypeOf(element.eClass())) {
			return EObjectWrapper.wrap(element);
		}
		EObject container = element.eContainer();
		while(container != null && !BpmnModelClass.PROCESS.isSuperTypeOf(container.eClass())) {
			container = container.eContainer();
		}
		if(container != null)
			return EObjectWrapper.wrap(container);
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	public static Object getAttachedResource(EObject flowNode) {
		Object resource =null;
		EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(flowNode);
		EObjectWrapper<EClass, EObject> valueWrapper =
			ExtensionHelper.getAddDataExtensionValueWrapper(flowNodeWrapper);
		// "rulefunction" "decisiontable" "rule" "service"
		if (valueWrapper != null) {
			if (flowNodeWrapper.isInstanceOf(BpmnModelClass.RULE_FUNCTION_TASK)) {
				resource = (String) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_RULEFUNCTION);
			}
			if (flowNodeWrapper.isInstanceOf(BpmnModelClass.JAVA_TASK)) {
				resource = (String) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JAVA_FILE_PATH);
			}
			else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.INFERENCE_TASK)) {
				resource = (Collection<String>) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_RULES);
			}
			else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.START_EVENT) ||
					flowNodeWrapper.isInstanceOf(BpmnModelClass.END_EVENT) ||
					flowNodeWrapper.isInstanceOf(BpmnModelClass.SEND_TASK) ||
					flowNodeWrapper.isInstanceOf(BpmnModelClass.RECEIVE_TASK) ||
					flowNodeWrapper.isInstanceOf(BpmnModelClass.CATCH_EVENT) ||
					flowNodeWrapper.isInstanceOf(BpmnModelClass.BOUNDARY_EVENT)||
					flowNodeWrapper.isInstanceOf(BpmnModelClass.THROW_EVENT)) {
	
				if (valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EVENT) != null) {
					resource= (String) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EVENT);
				}
			}else if (
					flowNodeWrapper.isInstanceOf(BpmnModelClass.SERVICE_TASK)) {
	
				if (valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_WSDL) != null) {
					resource= (String) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_WSDL);
				}
			}
			else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.BUSINESS_RULE_TASK)) {
				resource = (String) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_VIRTUALRULEFUNCTION);
			}
			else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.CALL_ACTIVITY)) {
				resource = (EObject) flowNodeWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_CALLED_ELEMENT);
	
			}else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.PROCESS)) {
				resource = (String) valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JOB_DATA_CONCEPT);
			
			}
			else if (flowNodeWrapper.isInstanceOf( BpmnModelClass.JAVA_TASK )) {
				resource = (String) flowNodeWrapper.getAttribute( BpmnMetaModelConstants.E_ATTR_JAVA_FILE_PATH );
			}
		}
		return resource;
	}

	@SuppressWarnings("unchecked")
	public static void setResourceAttr(EObject flowNode, Object val) {
		EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(flowNode);
		EObjectWrapper<EClass, EObject> valueWrapper =
			ExtensionHelper.getAddDataExtensionValueWrapper(flowNodeWrapper);
		// "rulefunction" "decisiontable" "rule" "service"
		if (valueWrapper != null) {
			if (flowNodeWrapper.isInstanceOf(BpmnModelClass.RULE_FUNCTION_TASK)) {
				ExtensionHelper.setExtensionAttributeValue(flowNode, BpmnMetaModelExtensionConstants.E_ATTR_RULEFUNCTION, val);
			}
			else if(flowNodeWrapper.isInstanceOf(BpmnModelClass.JAVA_TASK)){
			ExtensionHelper.setExtensionAttributeValue(flowNode, BpmnMetaModelExtensionConstants.E_ATTR_JAVA_FILE_PATH, val);
			String path = "";
			String [] parts = ((String)val).split("/");
			if (parts[0].equals("")){
				for(int i = 1;i<parts.length-1;i++){
				path += "/" + parts[i];
				}
			}else{
				for(int i = 0;i<parts.length-1;i++){
					path += "/" + parts[i];
				}
			}
			
			ExtensionHelper.setExtensionAttributeValue(flowNode, BpmnMetaModelExtensionConstants.E_ATTR_JAVA_PACKAGE, path);
			}
			else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.INFERENCE_TASK)) {
				if(val instanceof Collection){
					Collection<String> rules = (Collection<String>)val;
					if(rules.isEmpty()){
						EList<EObject> attributes = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_RULES);
						attributes.clear();
					}else
						valueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_RULES, val);
				}
	
			}
			else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.START_EVENT) ||
					flowNodeWrapper.isInstanceOf(BpmnModelClass.END_EVENT) ||
					flowNodeWrapper.isInstanceOf(BpmnModelClass.SEND_TASK) ||
					flowNodeWrapper.isInstanceOf(BpmnModelClass.RECEIVE_TASK) ||
					flowNodeWrapper.isInstanceOf(BpmnModelClass.CATCH_EVENT) ||
					flowNodeWrapper.isInstanceOf(BpmnModelClass.THROW_EVENT)) {
	
				if (valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EVENT) != null) {
					ExtensionHelper.setExtensionAttributeValue(flowNode, BpmnMetaModelExtensionConstants.E_ATTR_EVENT, val);
				}
			}else if (
					flowNodeWrapper.isInstanceOf(BpmnModelClass.SERVICE_TASK)) {
				ExtensionHelper.setExtensionAttributeValue(flowNode, BpmnMetaModelExtensionConstants.E_ATTR_WSDL, val);
			}
			else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.BUSINESS_RULE_TASK)) {
				ExtensionHelper.setExtensionAttributeValue(flowNode, BpmnMetaModelExtensionConstants.E_ATTR_DECISIONTABLE, val);
				ExtensionHelper.setExtensionAttributeValue(flowNode, BpmnMetaModelExtensionConstants.E_ATTR_VIRTUALRULEFUNCTION, val);
			}
			else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.CALL_ACTIVITY)) {
				flowNodeWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_CALLED_ELEMENT, val);
	
			}else if (flowNodeWrapper.isInstanceOf(BpmnModelClass.PROCESS)) {
				ExtensionHelper.setExtensionAttributeValue(flowNode, BpmnMetaModelConstants.E_ATTR_JOB_DATA_CONCEPT, val);
			}
		}
	}

	/**
	 * @param outgoingNodes
	 * @param type
	 * @return
	 */
	public static boolean containsFlowNodeType(Collection<EObject> outgoingNodes, EClass type) {
		for(EObject ogn: outgoingNodes) {
			EObjectWrapper<EClass, EObject>ognWrapper = EObjectWrapper.wrap(ogn);
			if(ognWrapper.isInstanceOf(type)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param element
	 * @return
	 */
	public static EObject getFlowElementContainer(EObject element) {
		EObject container = element.eContainer();
		while(container != null && !BpmnModelClass.FLOW_ELEMENTS_CONTAINER.isSuperTypeOf(container.eClass())) {
			container = container.eContainer();
		}
		return container;
	}

	public static EObject createSymbol(EObject flowElement, String idName, String type) {
		EObject flowElementContainer = null;
		if(BpmnModelClass.FLOW_ELEMENTS_CONTAINER.isSuperTypeOf(flowElement.eClass())){
			flowElementContainer = flowElement;
		} else {
			flowElementContainer = flowElement.eContainer();
		}
		if(flowElementContainer != null &&
				BpmnModelClass.PROCESS.isSuperTypeOf(flowElementContainer.eClass())) {
			EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper.wrap(flowElementContainer);
//			String projectName = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT);
//			String processName = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
	
//			RootSymbolMap rootMap = BpmnCorePlugin.getDefault().getBpmnModelManager().getRootSymbolMap(projectName);
//			ProcessSymbolMap processMap = rootMap.getProcessSymbolMap(flowElementContainer);
			ProcessSymbolMap processMap = new ProcessSymbolMapImpl(processWrapper.getEInstance(),null);
			SymbolMap flowElementMap = processMap.findSymbolMap(flowElement);
			EObject symbol = flowElementMap.generateSymbol(idName, type);
			ExtensionHelper.setExtensionListAttributeValue(
					flowElement, 
					BpmnMetaModelExtensionConstants.E_ATTR_VARIABLES, 
					symbol);
			return symbol;
		}
		return null;
	}

	public static EObject createBpmnSymbol(String idName, String type) {
		EObjectWrapper<EClass, EObject> bpmnSymbol = EObjectWrapper.createInstance(BpmnMetaModelExtension.EXTN_SYMBOL);
		bpmnSymbol.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_ID_NAME, idName);
		bpmnSymbol.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TYPE, type);
		return bpmnSymbol.getEInstance();
	}


	/**
	 * convert to BE symbol
	 * @param bpmnSymbol
	 * @return BE symbol
	 */
	public static Symbol convertBpmnSymbol(EObject bpmnSymbol) {
		EObjectWrapper<EClass, EObject> symbolWrapper = EObjectWrapper.wrap(bpmnSymbol);
		Symbol s = CommonIndexUtils.createSymbol(
				(String)symbolWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_ID_NAME), 
				(String)symbolWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TYPE));
		return s;
	}

}
