package com.tibco.cep.bpmn.ui.graph.model.controller;

import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.BUSINESS_RULE_TASK;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.COMPLEX_GATEWAY;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.EVENT_BASED_GATEWAY;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.EXCLUSIVE_GATEWAY;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.INCLUSIVE_GATEWAY;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.INFERENCE_TASK;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.JAVA_TASK;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.MANUAL_TASK;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.PARALLEL_GATEWAY;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.RECEIVE_TASK;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.RULE_FUNCTION_TASK;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.SEND_TASK;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.SERVICE_TASK;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.core.utils.ECoreHelper;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EEnumWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.Identifier;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.editor.IBpmnDiagramManager;
import com.tibco.cep.bpmn.ui.graph.properties.MapperControl;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tomsawyer.graph.TSGraphMember;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graph.TSNode;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;


public class ModelController  {
	final public static Object UNSET = "unset";
	
	private EObjectWrapper<EClass,EObject> modelRoot;
	
	private AdapterFactory modelChangeAdapterFactory;

	private IBpmnDiagramManager diagramManager;
	
	public ModelController(EObjectWrapper<EClass,EObject> mRoot, final AdapterFactory bpmnModelChangeAdapterFactory, IBpmnDiagramManager manager) {
		this(bpmnModelChangeAdapterFactory);
		this.modelRoot = mRoot;
		this.diagramManager = manager;
	}
	
	
	
	public ModelController(final AdapterFactory bpmnModelChangeAdapterFactory) {
		this.modelChangeAdapterFactory = bpmnModelChangeAdapterFactory;
	}

	
	public AdapterFactory getModelChangeAdapterFactory() {
		return modelChangeAdapterFactory;
	}

	
	public IBpmnDiagramManager getDiagramManager() {
		return diagramManager;
	}
	/**
	 * @return the modelRoot
	 * @throws CoreException 
	 */
	public EObjectWrapper<EClass,EObject> getModelRoot() {
		return modelRoot;
	}
	
	
	public void setModelRoot(EObjectWrapper<EClass,EObject>  process) {
		modelRoot = process;
	}
	

	public EObjectWrapper<? extends EClass,? extends EObject> createNewDescription() {
		return EObjectWrapper.createInstance(BpmnModelClass.DOCUMENTATION);
	}

	

	

	EObjectWrapper<EClass, EObject> createSubProcess() {
		// TODO Auto-generated method stub
		return null;
	}

	public EObjectWrapper<EClass, EObject> createDocumentation(EObjectWrapper<EClass, EObject> owner,String text) {
		EObjectWrapper<EClass, EObject> docWrapper = EObjectWrapper.createInstance(BpmnMetaModel.DOCUMENTATION);
//		docWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, GUIDGenerator.getGUID());
		Identifier id = BpmnModelUtils.nextIdentifier(owner, docWrapper, null);
		docWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, id.getId());
		docWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_TEXT, text);
		owner.addToListAttribute(BpmnMetaModelConstants.E_ATTR_DOCUMENTATION, docWrapper);
		getModelChangeAdapterFactory().adapt(docWrapper, ModelChangeListener.class);
		return docWrapper;
	}

	/**
	 * @param processName2 
	 * @return
	 */
	public EObjectWrapper<EClass, EObject> createProcess(String processName, String projectName, String folder) {
		// create process
		boolean privateProcess = false;
		if(diagramManager != null){
			privateProcess = diagramManager.isPrivateProcess();
		}
		@SuppressWarnings("unused")
		String name =  processName;
		EObjectWrapper<EClass, EObject> process = EObjectWrapper.createInstance(BpmnModelClass.PROCESS);
		// process.setAttribute(BpmnMetaModelConstants.E_ATTR_LABEL,name);
		process.setAttribute(BpmnMetaModelConstants.E_ATTR_FOLDER, folder);
		process.setAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT, projectName);
		process.setAttribute(BpmnMetaModelConstants.E_ATTR_ID,processName);
//		Identifier id = BpmnModelUtils.nextIdentifier(process, process, processName);
		process.setAttribute(BpmnMetaModelConstants.E_ATTR_ID,processName);
		process.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME,processName);
		if(privateProcess)
			process.setAttribute(BpmnMetaModelConstants.E_ATTR_PROCESS_TYPE,BpmnModelClass.ENUM_PROCESS_TYPE_EXECUTABLE);
		else
			process.setAttribute(BpmnMetaModelConstants.E_ATTR_PROCESS_TYPE,BpmnModelClass.ENUM_PROCESS_TYPE_PUBLIC);
		Date time = Calendar.getInstance().getTime();
		process.setAttribute(BpmnMetaModelConstants.E_ATTR_CREATION_DATE,time);
		Map<String, Object> updateList = new HashMap<String, Object>();
		updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_VERSION, 1);
		updateEmfModel(process, updateList );
		ExtensionHelper.getAddDataExtensionValueWrapper(process);// add the extension attribute
		
		
		// create the process symbol
		//BpmnModelUtils.createSymbol(process.getEInstance(),"jobDataConcept", "JOB");
		//createProcessLaneSet(null,process);
		getModelChangeAdapterFactory().adapt(process, ModelChangeListener.class);
		
		
		modelRoot = process;
//		addDataObject(process);
		return process;
	}
	
//	public void addDataObject(EObjectWrapper<EClass, EObject> process){
//		String projectName = modelRoot.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT);
//		EObjectWrapper<EClass, EObject> bpmnIndex = EObjectWrapper.wrap(BpmnIndexUtils.getIndex(projectName));
//		ProcessAdapter createAdapter = new ProcessAdapter(
//				process.getEInstance(), new ProcessOntologyAdapter(bpmnIndex.getEInstance()));
//		EObjectWrapper<EClass, EObject> addProcessItemDefinition = ECoreHelper.addProcessItemDefinition(bpmnIndex, createAdapter);
//		EObjectWrapper<EClass, EObject> dataObj = EObjectWrapper.createInstance(BpmnModelClass.DATA_OBJECT);
//		Identifier id = BpmnModelUtils.nextIdentifier(process, dataObj, "dataObject");
//		dataObj.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, id.getId());
//		dataObj.setAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF,addProcessItemDefinition.getEInstance());
//
//		process.addToListAttribute(BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENTS, dataObj);
//	}
	
	
	public void addNodeToEmfModel(EObjectWrapper<EClass, EObject> model, EObjectWrapper<EClass, EObject> process, EObjectWrapper<EClass, EObject> lane){
		if(BpmnModelClass.FLOW_ELEMENT.isSuperTypeOf(model.getEClassType())){
			addFlowElement(model.getEInstance(), process, lane);
		}else if(BpmnModelClass.ARTIFACT.isSuperTypeOf(model.getEClassType())){
			addArifact(model.getEInstance(), process);
		}
	}
	

	public void addEdgeToEmfModel(EObjectWrapper<EClass, EObject> model,EObjectWrapper<EClass, EObject> start, EObjectWrapper<EClass, EObject> end, EObjectWrapper<EClass, EObject> process, EObjectWrapper<EClass, EObject> lane) {
		if(BpmnModelClass.SEQUENCE_FLOW.isSuperTypeOf(model.getEClassType())){
			addSequenceFlowToModel(model, start, end, process, lane);
		}else if(BpmnModelClass.ARTIFACT.isSuperTypeOf(model.getEClassType())){
			addAssociationToModel(model, start, end, process);
		}
	}

	/**
	 * @param laneSetLabel
	 * @param process
	 * @return
	 */
	public EObjectWrapper<EClass, EObject> createProcessLaneSet(String laneSetLabel,EObjectWrapper<EClass, EObject> process) {
		@SuppressWarnings("unchecked")
		EList<EObject>laneSets = (EList<EObject>) process.getAttribute(BpmnMetaModelConstants.E_ATTR_LANE_SETS);
		String label = laneSetLabel == null ? BpmnUIConstants.DEFAULT_LANESET_NAME : laneSetLabel ;
		EObjectWrapper<EClass, EObject> laneSet = createLaneSet(label,null);
		laneSets.add(laneSet.getEInstance());
		return laneSet;
	}
	
	/**
	 * @param name
	 * @param parentLane
	 * @return
	 */
	public EObjectWrapper<EClass, EObject> createLaneSet(String name,EObjectWrapper<EClass, EObject> parentLane) {
		EObjectWrapper<EClass, EObject> laneSet = EObjectWrapper.createInstance(BpmnModelClass.LANE_SET);
//		laneSet.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, GUIDGenerator.getGUID());
		Identifier id = BpmnModelUtils.nextIdentifier(getModelRoot(), laneSet, null);
		laneSet.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, id.getId());
		if(parentLane != null) {
			laneSet.setAttribute(BpmnMetaModelConstants.E_ATTR_PARENT_LANE,parentLane.getEInstance());
		}
		getModelChangeAdapterFactory().adapt(laneSet, ModelChangeListener.class);		
		return laneSet;
	}

	/**
	 * @param laneLabel
	 * @param parentLaneSet
	 * @return
	 */
	public EObjectWrapper<EClass, EObject> createLane(String laneLabel,EObjectWrapper<EClass, EObject> parentLaneSet) {
		EObjectWrapper<EClass, EObject> lane = EObjectWrapper.createInstance(BpmnModelClass.LANE);
		addLaneToModel(lane,laneLabel, parentLaneSet);
		ExtensionHelper.getAddDataExtensionValueWrapper(lane);// add the extension attribute 
		return lane;
	}
	
	public EObjectWrapper<EClass, EObject> addLaneToModel(EObjectWrapper<EClass, EObject> lane, String laneLabel, EObjectWrapper<EClass, EObject> parentLaneSet) {
		String label = laneLabel == null ? BpmnUIConstants.DEFAULT_LANE_NAME : laneLabel ;
//		lane.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, GUIDGenerator.getGUID());
		Identifier id = BpmnModelUtils.nextIdentifier(getModelRoot(), lane, null);
		lane.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, id.getId());
		lane.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, label);
		// GGG?
		// lane.setAttribute(BpmnMetaModelConstants.E_ATTR_LABEL, laneLabel);
		EObject childLaneSet = (EObject) lane.getAttribute(BpmnMetaModelConstants.E_ATTR_CHILD_LANE_SET);
	    if(childLaneSet == null) {
	    	EObjectWrapper<EClass, EObject> claneSet = createLaneSet(label,lane);
	    	lane.setAttribute(BpmnMetaModelConstants.E_ATTR_CHILD_LANE_SET, claneSet.getEInstance());
	    }
		
		@SuppressWarnings("unchecked")
		EList<EObject>lanes = (EList<EObject>) parentLaneSet.getAttribute(BpmnMetaModelConstants.E_ATTR_LANES);
		lanes.add(lane.getEInstance());
		getModelChangeAdapterFactory().adapt(lane, ModelChangeListener.class);
		return lane;
	}
	
	/**
	 * @param laneName
	 * @param parentLaneSet
	 * @return
	 */
	public void removeLane(EObjectWrapper<EClass, EObject> modelObject,
			EObjectWrapper<EClass, EObject> parentLaneSet) {
		EObjectWrapper<EClass, EObject> laneWrapper = modelObject;
		@SuppressWarnings("unchecked")
		EList<EObject>lanes = (EList<EObject>)parentLaneSet.getAttribute(BpmnMetaModelConstants.E_ATTR_LANES);
		lanes.remove(laneWrapper.getEInstance());
	}
	
	public  EObjectWrapper<EClass, EObject> createLane(TSGraphObject tsGraphObj) {
		@SuppressWarnings("unused")
		TSEGraph parentGraph = null;
		@SuppressWarnings("unused")
		final String nodeName = (String) tsGraphObj.getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
		@SuppressWarnings("unused")
		final EClass nodeType = (EClass) tsGraphObj.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		
		if(tsGraphObj instanceof TSEGraph) {
			parentGraph = (TSEGraph) tsGraphObj;
			
		} else if(tsGraphObj instanceof TSENode){
			parentGraph = (TSEGraph) ((TSENode)tsGraphObj).getChildGraph();
		}
		return null;
	}
	
	public EObjectWrapper<EClass, EObject> createEvent(String name,String toolId,
										EClass type,
										EClass eventDefinitionType, 
										EObjectWrapper<EClass, EObject> flowElementContainer, 
										EObjectWrapper<EClass, EObject> lane) {
		
		EObjectWrapper<EClass, EObject> eventWrapper = EObjectWrapper.createInstance(type);
//		eventWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, GUIDGenerator.getGUID());
		Identifier id = BpmnModelUtils.nextIdentifier(flowElementContainer, eventWrapper, name);
		BpmnModelUtils.addUniqueId(name,flowElementContainer,eventWrapper);
		
		eventWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, id.getId());
		eventWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, id.getName());
//		eventWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_LABEL, label);
		if(eventDefinitionType != null) {
			EObjectWrapper<EClass, EObject> eventDefinition = createEventDefinition(flowElementContainer,eventDefinitionType);
			eventWrapper.addToListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS,eventDefinition);
//			eventWrapper.addToListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITION_REFS,eventDefinition);
		}
		//subprocess does not have lanes
		if (!BpmnModelClass.SUB_PROCESS.isSuperTypeOf(flowElementContainer.getEClassType())) {
			List<EObject> lanepath = BpmnModelUtils.getLanePath(flowElementContainer, lane);
			for (EObject laneItem : lanepath) {
				eventWrapper.addToListAttribute(BpmnMetaModelConstants.E_ATTR_LANES, laneItem);
			}
		}
		flowElementContainer.addToListAttribute(BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENTS, eventWrapper);
		setToolIdAttribute(eventWrapper, toolId);
		ExtensionHelper.getAddDataExtensionValueWrapper(eventWrapper);// add the extension attribute 
		getModelChangeAdapterFactory().adapt(eventWrapper, ModelChangeListener.class);
		return eventWrapper;
	}
	
	private EObjectWrapper<EClass, EObject> createBoundaryEvent(EObjectWrapper<EClass, EObject>parentObject, EClass eventDefinitionType, String toolId) {

		EObjectWrapper<EClass, EObject> eventWrapper = EObjectWrapper
				.createInstance(BpmnModelClass.BOUNDARY_EVENT);
		Identifier id = BpmnModelUtils.nextIdentifier(parentObject, eventWrapper, null);
		
		BpmnModelUtils.addUniqueId(BpmnModelUtils.getProjName(parentObject.getEInstance()),parentObject,eventWrapper);
		
		eventWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, id.getId());
		eventWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, "Boundary Event");
		if (eventDefinitionType != null) {
			EObjectWrapper<EClass, EObject> eventDefinition = createEventDefinition(parentObject, eventDefinitionType);
			getModelChangeAdapterFactory().adapt(eventDefinition, ModelChangeListener.class);
			eventWrapper.addToListAttribute(
					BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS,
					eventDefinition);
//			eventWrapper.addToListAttribute(
//					BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITION_REFS,
//					eventDefinition);
		}

		getModelChangeAdapterFactory().adapt(eventWrapper,
				ModelChangeListener.class);
		return eventWrapper;
	}
	
	public EObjectWrapper<EClass, EObject> addBoundaryEvent(EObjectWrapper<EClass, EObject> parent, EClass eventDefinitionType, String toolId) {
		EObjectWrapper<EClass, EObject> boundaryEvent = createBoundaryEvent(parent, eventDefinitionType, toolId);
		EList<EObject> listAttribute = parent.getListAttribute(BpmnMetaModelConstants.E_ATTR_BOUNDARY_EVENT_REFS);
		if(listAttribute == null){
			List<EObject> events= new ArrayList<EObject>();
			events.add(boundaryEvent.getEInstance());
			parent.setAttribute(BpmnMetaModelConstants.E_ATTR_BOUNDARY_EVENT_REFS, boundaryEvent.getEInstance());
		}else
			parent.addToListAttribute(BpmnMetaModelConstants.E_ATTR_BOUNDARY_EVENT_REFS, boundaryEvent.getEInstance());
		boundaryEvent.setAttribute(BpmnMetaModelConstants.E_ATTR_ATTACHED_TO_REF, parent.getEInstance());
		
		setToolIdAttribute(boundaryEvent, toolId);
		ExtensionHelper.getAddDataExtensionValueWrapper(boundaryEvent);
		return boundaryEvent;
	}
	
	public void addBoundaryEvent(EObjectWrapper<EClass, EObject> parent, EObjectWrapper<EClass, EObject> boundaryEvent) {
		parent.addToListAttribute(BpmnMetaModelConstants.E_ATTR_BOUNDARY_EVENT_REFS, boundaryEvent.getEInstance());
		boundaryEvent.setAttribute(BpmnMetaModelConstants.E_ATTR_ATTACHED_TO_REF, parent.getEInstance());

	}
	
	public void removeBoundaryEvent(EObjectWrapper<EClass, EObject> parent, EObjectWrapper<EClass, EObject> boundaryEvent,
			List<EObjectWrapper<EClass, EObject>> incoming, 
			List<EObjectWrapper<EClass, EObject>> outgoing) {
		for(EObjectWrapper<EClass, EObject> inSeq:incoming) {
			if(inSeq.getAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF).equals( boundaryEvent.getEInstance()))
				inSeq.setAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF,null);
		}
		for(EObjectWrapper<EClass, EObject> outSeq:outgoing) {
			if(outSeq.getAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF).equals( boundaryEvent.getEInstance()))
				outSeq.setAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF,null);
		}
		parent.removeFromListAttribute(BpmnMetaModelConstants.E_ATTR_BOUNDARY_EVENT_REFS, boundaryEvent.getEInstance());

		boundaryEvent.setAttribute(BpmnMetaModelConstants.E_ATTR_ATTACHED_TO_REF, null);
	}
	
	public void addFlowElement(EObject model,
			EObjectWrapper<EClass, EObject> flowElementContainer, EObjectWrapper<EClass, EObject> lane) {
		EObjectWrapper<EClass, EObject> modelWrapper = EObjectWrapper.wrap(model);
		//subprocess does not have lanes
		if (!BpmnModelClass.SUB_PROCESS.isSuperTypeOf(flowElementContainer.getEClassType())) {
			List<EObject> lanepath = BpmnModelUtils.getLanePath(flowElementContainer, lane);
			for (EObject laneItem : lanepath) {
				modelWrapper.addToListAttribute(
						BpmnMetaModelConstants.E_ATTR_LANES, laneItem);
			}
			lane.addToListAttribute(
					BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENT_REFS,
					modelWrapper.getEInstance());
		}
		

		flowElementContainer.addToListAttribute(BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENTS,
				model);

		
		getModelChangeAdapterFactory().adapt(model, ModelChangeListener.class);
	}
	
	
	public EObjectWrapper<EClass, EObject> createFormalExpression( String body, String evaluationType, String lanuage){
		EObjectWrapper<EClass, EObject> expressionWrapper = EObjectWrapper.createInstance(BpmnModelClass.FORMAL_EXPRESSION);
		
			
		expressionWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_BODY, body);
		expressionWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_LANGUAGE, lanuage);
		
		EObjectWrapper<EClass, EObject> itemDefinitionWrapper = EObjectWrapper.createInstance(BpmnModelClass.ITEM_DEFINITION);
		getModelChangeAdapterFactory().adapt(itemDefinitionWrapper, ModelChangeListener.class);
		itemDefinitionWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_STRUCTURE, evaluationType);
		itemDefinitionWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_IS_COLLECTION, false);;
		expressionWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_EVALUATES_TO_TYPE_REF, itemDefinitionWrapper.getEInstance());
		
		getModelChangeAdapterFactory().adapt(expressionWrapper, ModelChangeListener.class);
		
		return expressionWrapper;
	}
	
	protected SmType getSimpleTypeForReturnType(String returnType) {
		SmType smType = null;
		if (returnType.equalsIgnoreCase(PROPERTY_TYPES.BOOLEAN.getName()))
			smType = XSDL.BOOLEAN;
		else if (returnType.equalsIgnoreCase(PROPERTY_TYPES.INTEGER
				.getName()))
			smType = XSDL.INTEGER;
		else if (returnType.equalsIgnoreCase(PROPERTY_TYPES.DOUBLE
				.getName()))
			smType = XSDL.DOUBLE;
		else if (returnType.equalsIgnoreCase(PROPERTY_TYPES.STRING
				.getName()))
			smType = XSDL.STRING;
		else if (returnType.equalsIgnoreCase(PROPERTY_TYPES.DATE_TIME
				.getName()))
			smType = XSDL.DATETIME;
		else if (returnType.equalsIgnoreCase(PROPERTY_TYPES.LONG
				.getName()))
			smType = XSDL.LONG;

		return smType;
	}
	
	public Map<String, Object> updateEmfModel(EObjectWrapper<EClass, EObject> modelWrapper,
			Map<String, Object> updateList) {
		getModelChangeAdapterFactory().adapt(modelWrapper,ModelChangeListener.class);
		Map<String, Object> oldValues = new HashMap<String, Object>();

		Set<String> keySet = updateList.keySet();
		for (Iterator<?> iterator = keySet.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			Object value = updateList.get(key);
			if(key.equals(BpmnMetaModelExtensionConstants.E_ATTR_INPUT_MAP_XSLT) ||
					key.equals(BpmnMetaModelExtensionConstants.E_ATTR_OUTPUT_MAP_XSLT))
				continue;
			if(modelWrapper.containsAttribute(key)) {
				Object attribute = modelWrapper.getAttribute(key);
				if(attribute != null && attribute instanceof Collection){
					List<Object> collec = new ArrayList<Object>((Collection<?>)attribute);
					oldValues.put(key, collec);
				}else{
					oldValues.put(key, attribute);
				}
				
				if (value == UNSET) {
					modelWrapper.unsetAttribute(key);
				}
				else {
					modelWrapper.setAttribute(key, value);
				}
			} else if(ExtensionHelper.isValidDataExtensionAttribute(modelWrapper,key)){
				EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(modelWrapper);
				if (valueWrapper != null) {
					getModelChangeAdapterFactory().adapt(valueWrapper, ModelChangeListener.class);
					if (modelWrapper.isInstanceOf(BpmnModelClass.INFERENCE_TASK)) {
						if(value instanceof Collection){
							Collection<String> rules =  valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_RULES);
							if(rules != null){
								List<String> collec = new ArrayList<String>(rules);
								oldValues.put(key, collec);
							}else{
								oldValues.put(key, new ArrayList<String>());
							}
							valueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_RULES, value);
						}else{
							Object attribute = valueWrapper.getAttribute(key);
							oldValues.put(key, attribute);
							if(value == UNSET)
								valueWrapper.unsetAttribute(key);
							else
								valueWrapper.setAttribute(key, value);
						}
					}else{
						Object attribute = valueWrapper.getAttribute(key);
						oldValues.put(key, attribute);
						if(value == UNSET)
							valueWrapper.unsetAttribute(key);
						else
							valueWrapper.setAttribute(key, value);
					}

				}
				//				ExtensionHelper.setDataExtensionValueAttribute(modelWrapper, key, value);
			}
		}
		
		return oldValues;
	}
	
	
	public EObjectWrapper<EClass, EObject> createPropertyDefinition(EObjectWrapper<EClass, EObject> process, String name, ExpandedName type, EEnumLiteral propType) {
		String projName= modelRoot.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT);
		EList<EObject> listAttribute = process.getListAttribute(BpmnMetaModelConstants.E_ATTR_PROPERTIES);
		EObjectWrapper<EClass, EObject> property = EObjectWrapper.createInstance(BpmnModelClass.PROPERTY);
		property.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, name);

		EObjectWrapper<EClass, EObject> eObjectWrapper = ECoreHelper.getItemDefinitionUsingNameSpace(projName, type.toString());
		property.setAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF, eObjectWrapper.getEInstance());
		listAttribute.add(property.getEInstance());
		
		EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(property);
		if(addDataExtensionValueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PROP_TYPE)){
			addDataExtensionValueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PROP_TYPE, propType);
		}
			
		
		getModelChangeAdapterFactory().adapt(eObjectWrapper, ModelChangeListener.class);
		getModelChangeAdapterFactory().adapt(property, ModelChangeListener.class);
		
		return property;
	}
	
	public boolean isArrayItemdef(EObjectWrapper<EClass, EObject> itemdef){
		boolean isCollection = itemdef.getAttribute(BpmnMetaModelConstants.E_ATTR_IS_COLLECTION);
		return isCollection;
	}
	
	public void removePropertyDefinition(EObjectWrapper<EClass, EObject> process,EObjectWrapper<EClass, EObject> propDef ) {
		process.removeFromListAttribute(BpmnMetaModelConstants.E_ATTR_PROPERTIES, propDef.getEInstance());
	}
	
	private void setToolIdAttribute(
			EObjectWrapper<EClass, EObject> modelWrapper, String value) {
		if (ExtensionHelper.isValidDataExtensionAttribute(modelWrapper,
				BpmnMetaModelExtensionConstants.E_ATTR_TOOL_ID)) {
			EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
					.getAddDataExtensionValueWrapper(modelWrapper);
			if (valueWrapper != null) {
				getModelChangeAdapterFactory().adapt(valueWrapper,
						ModelChangeListener.class);

				valueWrapper.setAttribute(
						BpmnMetaModelExtensionConstants.E_ATTR_TOOL_ID, value);
			}
		}
	}
	
	
	public void removeFlowElement(EObjectWrapper<EClass, EObject> modelObject,
			EObjectWrapper<EClass, EObject> flowElementContainer,
			EObjectWrapper<EClass, EObject> lane) {

		if (!BpmnModelClass.SUB_PROCESS.isSuperTypeOf(flowElementContainer
				.getEClassType()))
			lane.removeFromListAttribute(
					BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENT_REFS,
					modelObject.getEInstance());
		flowElementContainer.removeFromListAttribute(
				BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENTS, modelObject.getEInstance());
	}
	

	public void removeEvent(EObjectWrapper<EClass, EObject> modelObject,
			EObjectWrapper<EClass, EObject> flowElementContainer,
			EObjectWrapper<EClass, EObject> lane, 
			List<EObjectWrapper<EClass, EObject>> incoming, 
			List<EObjectWrapper<EClass, EObject>> outgoing) {
		EObjectWrapper<EClass, EObject> eventWrapper = modelObject;
		for(EObjectWrapper<EClass, EObject> inSeq:incoming) {
			if(inSeq.getAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF).equals( modelObject.getEInstance()))
				inSeq.setAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF,null);
		}
		for(EObjectWrapper<EClass, EObject> outSeq:outgoing) {
			if(outSeq.getAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF).equals( modelObject.getEInstance()))
				outSeq.setAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF,null);
		}
		
		Collection<EObject> associations = BpmnModelUtils.getAssociations(flowElementContainer);
		for (EObject object : associations) {
			EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper.wrap(object);
			if(wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF).equals(modelObject.getEInstance()))
				wrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF,null);
			else if(wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF).equals(modelObject.getEInstance()))
				wrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF, null);
			
		}
		
		if (!BpmnModelClass.SUB_PROCESS.isSuperTypeOf(flowElementContainer
				.getEClassType()))
			lane.removeFromListAttribute(
					BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENT_REFS,
					modelObject.getEInstance());
//		eventWrapper.clearListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
//		eventWrapper.clearListAttribute(BpmnMetaModelConstants.E_ATTR_LANES);
//		eventWrapper.clearListAttribute(BpmnMetaModelConstants.E_ATTR_INCOMING); // Flownode
//		eventWrapper.clearListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING); // Flownode

		flowElementContainer.removeFromListAttribute(BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENTS, eventWrapper);
	}

	private EObjectWrapper<EClass, EObject> createEventDefinition(
			EObjectWrapper<EClass, EObject> flowElementContainer, EClass eventDefinitionType) {
		EObjectWrapper<EClass, EObject> eventDefinition = EObjectWrapper.createInstance(eventDefinitionType);
//		eventDefinition.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, GUIDGenerator.getGUID());
		Identifier id = BpmnModelUtils.nextIdentifier(flowElementContainer, eventDefinition, null);
		
		BpmnModelUtils.addUniqueId(BpmnModelUtils.getProjName(flowElementContainer.getEInstance()),flowElementContainer,eventDefinition);
		
		eventDefinition.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, id.getId());
		getModelChangeAdapterFactory().adapt(eventDefinition, ModelChangeListener.class);
		return eventDefinition;
	}

	public EObjectWrapper<EClass, EObject> createTextAnnotation(String text,EClass type, EObjectWrapper<EClass, EObject> flowElementContainer, EObjectWrapper<EClass, EObject> lane) {
		final EObjectWrapper<EClass, EObject> annotationWrapper = EObjectWrapper.createInstance(type);
//		annotationWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, GUIDGenerator.getGUID());
		Identifier id = BpmnModelUtils.nextIdentifier(flowElementContainer, annotationWrapper, null);
		
		BpmnModelUtils.addUniqueId(BpmnModelUtils.getProjName(flowElementContainer.getEInstance()),flowElementContainer,annotationWrapper);
		
		annotationWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, id.getId());
		annotationWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_TEXT, text);
		
		flowElementContainer.addToListAttribute(BpmnMetaModelConstants.E_ATTR_ARTIFACTS, annotationWrapper);
		EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(annotationWrapper);// add the extension attribute
		addDataExtensionValueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LABEL, id.getName());
		getModelChangeAdapterFactory().adapt(annotationWrapper, ModelChangeListener.class);
		return annotationWrapper;
	}
	
	public void addArifact(EObject model,
			EObjectWrapper<EClass, EObject> flowElementContainer) {
		flowElementContainer.addToListAttribute(BpmnMetaModelConstants.E_ATTR_ARTIFACTS,
				model);

		getModelChangeAdapterFactory().adapt(model, ModelChangeListener.class);
	}
	
	public void removeTextAnnotation(EObjectWrapper<EClass, EObject> modelObject,
			EObjectWrapper<EClass, EObject> flowElementContainer,
			EObjectWrapper<EClass, EObject> lane, 
			List<EObjectWrapper<EClass, EObject>> incoming, 
			List<EObjectWrapper<EClass, EObject>> outgoing) {
		EObjectWrapper<EClass, EObject> textAnnotationWrapper = modelObject;
		for(EObjectWrapper<EClass, EObject> inassociation:incoming) {
			if(inassociation.getAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF).equals( modelObject.getEInstance())) {
				inassociation.setAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF,null);
				flowElementContainer.removeFromListAttribute(BpmnMetaModelConstants.E_ATTR_ARTIFACTS, inassociation);
			}
		}
		for(EObjectWrapper<EClass, EObject> outassociation:outgoing) {
			if(outassociation.getAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF).equals( modelObject.getEInstance())){
				outassociation.setAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF,null);
				flowElementContainer.removeFromListAttribute(BpmnMetaModelConstants.E_ATTR_ARTIFACTS, outassociation);
			}
		}
		flowElementContainer.removeFromListAttribute(BpmnMetaModelConstants.E_ATTR_ARTIFACTS, textAnnotationWrapper);
		
	}


	public EObjectWrapper<EClass, EObject> createGateway(String name,String toolId,
			EClass type, EEnumLiteral eventBasedGatewayType, EObjectWrapper<EClass, EObject> flowElementContainer, EObjectWrapper<EClass, EObject> lane) {
		final EObjectWrapper<EClass, EObject> gatewayWrapper = EObjectWrapper.createInstance(type);
//		gatewayWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, GUIDGenerator.getGUID());
		Identifier id = BpmnModelUtils.nextIdentifier(flowElementContainer, gatewayWrapper, name);
		
		BpmnModelUtils.addUniqueId(BpmnModelUtils.getProjName(flowElementContainer.getEInstance()),flowElementContainer,gatewayWrapper);
		
		gatewayWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, id.getId());
		gatewayWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, id.getName());
//		gatewayWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_LABEL, label);
		
		//subprocess does not have lanes
		if (!BpmnModelClass.SUB_PROCESS.isSuperTypeOf(flowElementContainer.getEClassType())) {
			List<EObject> lanepath = BpmnModelUtils.getLanePath(flowElementContainer, lane);
			for (EObject laneItem : lanepath) {
				gatewayWrapper.addToListAttribute(
						BpmnMetaModelConstants.E_ATTR_LANES, laneItem);
			}
		}
		//gatewayWrapper.addToListAttribute("lanes", lane);
		//gatewayWrapper.addToListAttribute("lanes", lane);
		// direction can be set when the sequenceflow converge or diverge w.r.t this gateway
		// not here since it is new gateway with no connections.
//		EEnumWrapper<EEnum, EEnumLiteral> gwDir = EEnumWrapper.createInstance(BpmnMetaModel.ENUM_GATEWAY_DIRECTION);
//		gatewayWrapper.setAttribute("gatewayDirection", gwDir.getEnumerator(BpmnMetaModel.ENUM_GATEWAY_DIRECTION_DIVERGING));
		flowElementContainer.addToListAttribute(BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENTS, gatewayWrapper);
		if(INCLUSIVE_GATEWAY.isSuperTypeOf(type)) {
		} else if(EXCLUSIVE_GATEWAY.isSuperTypeOf(type)) {
		} else if(COMPLEX_GATEWAY.isSuperTypeOf(type)) {
		} else if(PARALLEL_GATEWAY.isSuperTypeOf(type)) {
		} else if(EVENT_BASED_GATEWAY.isSuperTypeOf(type)) {
			gatewayWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_GATEWAY_TYPE, eventBasedGatewayType);
		} 
		
		EEnumWrapper<EEnum, EEnumLiteral> enWrapper = EEnumWrapper.createInstance(BpmnMetaModel.ENUM_GATEWAY_DIRECTION);
		EEnumLiteral unspecified = enWrapper.getEnumLiteral(BpmnMetaModel.ENUM_GATEWAY_DIRECTION_UNSPECIFIED);
		gatewayWrapper.setAttribute(
				BpmnMetaModelConstants.E_ATTR_GATEWAY_DIRECTION,unspecified);
		setToolIdAttribute(gatewayWrapper, toolId);
		ExtensionHelper.getAddDataExtensionValueWrapper(gatewayWrapper);// add the extension attribute
		getModelChangeAdapterFactory().adapt(gatewayWrapper, ModelChangeListener.class);
		return gatewayWrapper;
	}
	
	public void removeGateway(EObjectWrapper<EClass, EObject> modelObject,
			EObjectWrapper<EClass, EObject> flowElementContainer,
			EObjectWrapper<EClass, EObject> lane, 
			List<EObjectWrapper<EClass, EObject>> incoming, 
			List<EObjectWrapper<EClass, EObject>> outgoing) {
		EObjectWrapper<EClass, EObject> gatewayWrapper = modelObject;
		for(EObjectWrapper<EClass, EObject> inSeq:incoming) {
			if(inSeq.getAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF).equals( modelObject.getEInstance()))
				inSeq.setAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF,null);
		}
		for(EObjectWrapper<EClass, EObject> outSeq:outgoing) {
			if(outSeq.getAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF).equals( modelObject.getEInstance()))
				outSeq.setAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF,null);
		}
		
		Collection<EObject> associations = BpmnModelUtils.getAssociations(flowElementContainer);
		for (EObject object : associations) {
			EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper.wrap(object);
			if(wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF).equals(modelObject.getEInstance()))
				wrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF,null);
			else if(wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF).equals(modelObject.getEInstance()))
				wrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF, null);
			
		}
//		gatewayWrapper.clearListAttribute(BpmnMetaModelConstants.E_ATTR_LANES);
//		gatewayWrapper.clearListAttribute(BpmnMetaModelConstants.E_ATTR_INCOMING); // Flownode
//		gatewayWrapper.clearListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING); // Flownode

		if (!BpmnModelClass.SUB_PROCESS.isSuperTypeOf(flowElementContainer
				.getEClassType()))
			lane.removeFromListAttribute(
					BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENT_REFS,
					modelObject.getEInstance());
		
		flowElementContainer.removeFromListAttribute(BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENTS, gatewayWrapper);
		
	}
	
	public void removeTask(EObjectWrapper<EClass, EObject> modelObject,
			EObjectWrapper<EClass, EObject> flowElementContainer,
			EObjectWrapper<EClass, EObject> lane, 
			List<EObjectWrapper<EClass, EObject>> incoming, 
			List<EObjectWrapper<EClass, EObject>> outgoing) {
		EObjectWrapper<EClass, EObject> gatewayWrapper = modelObject;
		for(EObjectWrapper<EClass, EObject> inSeq:incoming) {
			if(inSeq.getAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF).equals( modelObject.getEInstance()))
				inSeq.setAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF,null);
		}
		for(EObjectWrapper<EClass, EObject> outSeq:outgoing) {
			if(outSeq.getAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF).equals( modelObject.getEInstance()))
				outSeq.setAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF,null);
		}
		
		Collection<EObject> associations = BpmnModelUtils.getAssociations(flowElementContainer);
		for (EObject object : associations) {
			EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper.wrap(object);
			if(wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF).equals(modelObject.getEInstance()))
				wrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF,null);
			else if(wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF).equals(modelObject.getEInstance()))
				wrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF, null);
			
		}
//		gatewayWrapper.clearListAttribute(BpmnMetaModelConstants.E_ATTR_LANES);
//		gatewayWrapper.clearListAttribute(BpmnMetaModelConstants.E_ATTR_INCOMING); // Flownode
//		gatewayWrapper.clearListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING); // Flownode

		if (!BpmnModelClass.SUB_PROCESS.isSuperTypeOf(flowElementContainer
				.getEClassType()))
			lane.removeFromListAttribute(
					BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENT_REFS,
					modelObject.getEInstance());
		flowElementContainer.removeFromListAttribute(BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENTS, gatewayWrapper);
		
	}
	
	public void removeActivity(EObjectWrapper<EClass, EObject> modelObject,
			EObjectWrapper<EClass, EObject> flowElementContainer,
			EObjectWrapper<EClass, EObject> lane, 
			List<EObjectWrapper<EClass, EObject>> incoming, 
			List<EObjectWrapper<EClass, EObject>> outgoing) {
		EObjectWrapper<EClass, EObject> gatewayWrapper = modelObject;
		for(EObjectWrapper<EClass, EObject> inSeq:incoming) {
			if(inSeq.getAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF).equals( modelObject.getEInstance()))
				inSeq.setAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF,null);
		}
		for(EObjectWrapper<EClass, EObject> outSeq:outgoing) {
			if(outSeq.getAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF).equals( modelObject.getEInstance()))
				outSeq.setAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF,null);
		}
		
		Collection<EObject> associations = BpmnModelUtils.getAssociations(flowElementContainer);
		for (EObject object : associations) {
			EObjectWrapper<EClass, EObject> wrapper = EObjectWrapper.wrap(object);
			if(wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF).equals(modelObject.getEInstance()))
				wrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF,null);
			else if(wrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF).equals(modelObject.getEInstance()))
				wrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF, null);
			
		}
		gatewayWrapper.clearListAttribute(BpmnMetaModelConstants.E_ATTR_LANES);
		gatewayWrapper.clearListAttribute(BpmnMetaModelConstants.E_ATTR_INCOMING); // Flownode
		gatewayWrapper.clearListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING); // Flownode

		if (!BpmnModelClass.SUB_PROCESS.isSuperTypeOf(flowElementContainer
				.getEClassType()))
			lane.removeFromListAttribute(
					BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENT_REFS,
					modelObject.getEInstance());
		
		flowElementContainer.removeFromListAttribute(BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENTS, gatewayWrapper);
		
	}

	

	public EObjectWrapper<EClass, EObject> createActivity(String nodeName, String toolId,
			EClass type, EObjectWrapper<EClass, EObject> flowElementContainer, EObjectWrapper<EClass, EObject> lane) {
		
		final EObjectWrapper<EClass, EObject> activityWrapper = EObjectWrapper.createInstance(type);
//		activityWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, GUIDGenerator.getGUID());
		Identifier id = BpmnModelUtils.nextIdentifier(flowElementContainer, activityWrapper, nodeName);
		
		BpmnModelUtils.addUniqueId(BpmnModelUtils.getProjName(flowElementContainer.getEInstance()), flowElementContainer, activityWrapper);
		
		activityWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, id.getId());
		activityWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, id.getName());
//		activityWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_LABEL, nodeLabel);
		activityWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_START_QUANTITY, 1);
		activityWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_COMPLETION_QUANTITY, 1);
		
		//subprocess does not have lanes
		if (!BpmnModelClass.SUB_PROCESS.isSuperTypeOf(flowElementContainer.getEClassType())) {
			List<EObject> lanepath = BpmnModelUtils.getLanePath(flowElementContainer, lane);
			for (EObject laneItem : lanepath) {
				activityWrapper.addToListAttribute(
						BpmnMetaModelConstants.E_ATTR_LANES, laneItem);
			}
		}
		flowElementContainer.addToListAttribute(BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENTS, activityWrapper);
		if(JAVA_TASK.isSuperTypeOf(type)){
		} else if(RULE_FUNCTION_TASK.isSuperTypeOf(type)){
		} else if(SEND_TASK.isSuperTypeOf(type)) {
		} else if(RECEIVE_TASK.isSuperTypeOf(type)) {
		} else if(MANUAL_TASK.isSuperTypeOf(type)) {
		} else if(INFERENCE_TASK.isSuperTypeOf(type)) {
		} else if(SERVICE_TASK.isSuperTypeOf(type)) {
		} else if(BUSINESS_RULE_TASK.isSuperTypeOf(type)) {
		}
		setToolIdAttribute(activityWrapper, toolId);
		ExtensionHelper.getAddDataExtensionValueWrapper(activityWrapper);// add the extension attribute
		getModelChangeAdapterFactory().adapt(activityWrapper, ModelChangeListener.class);
		return activityWrapper;
	}

	

	


	
	public EObjectWrapper<EClass, EObject> createAssociation(
			EObjectWrapper<EClass, EObject> start,
			EObjectWrapper<EClass, EObject> end,
			EObjectWrapper<EClass, EObject> flowElementContainer) {
		EObjectWrapper<EClass, EObject> associationWrapper = EObjectWrapper.createInstance(BpmnMetaModel.ASSOCIATION);
		return addAssociationToModel(associationWrapper, start, end, flowElementContainer);
	}
	
	public EObjectWrapper<EClass, EObject> addAssociationToModel(EObjectWrapper<EClass, EObject> associationWrapper, EObjectWrapper<EClass, EObject> start,
			EObjectWrapper<EClass, EObject> end,
			EObjectWrapper<EClass, EObject> flowElementContainer){
//		associationWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID,GUIDGenerator.getGUID());
		Identifier id = BpmnModelUtils.nextIdentifier(flowElementContainer, associationWrapper, null);
		
		BpmnModelUtils.addUniqueId(BpmnModelUtils.getProjName(flowElementContainer.getEInstance()), flowElementContainer, associationWrapper);
		
		associationWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, id.getId());
		associationWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF,start.getEInstance());
		associationWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF, end.getEInstance());
		flowElementContainer.addToListAttribute(BpmnMetaModelConstants.E_ATTR_ARTIFACTS,
				associationWrapper);

		EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(associationWrapper);// add the extension attribute 
		addDataExtensionValueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LABEL, id.getName());
		getModelChangeAdapterFactory().adapt(associationWrapper,
				ModelChangeListener.class);
		return associationWrapper;
	}
	
	
	public EObjectWrapper<EClass, EObject> createSequenceFlow(EObjectWrapper<EClass, EObject> start,
											EObjectWrapper<EClass, EObject> end,
											EObjectWrapper<EClass, EObject> flowElementContainer,
											EObjectWrapper<EClass, EObject> lane) {
		EObjectWrapper<EClass, EObject> sequenceFlow = EObjectWrapper.createInstance(BpmnMetaModel.SEQUENCE_FLOW);

		return addSequenceFlowToModel(sequenceFlow, start, end, flowElementContainer, lane);
	}
	
	public EObjectWrapper<EClass, EObject> addSequenceFlowToModel(
			EObjectWrapper<EClass, EObject> sequenceFlow,
			EObjectWrapper<EClass, EObject> start,
			EObjectWrapper<EClass, EObject> end,
			EObjectWrapper<EClass, EObject> flowElementContainer,
			EObjectWrapper<EClass, EObject> lane){
//		sequenceFlow.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, GUIDGenerator.getGUID());
		Identifier id = BpmnModelUtils.nextIdentifier(flowElementContainer, sequenceFlow, null);
		
		BpmnModelUtils.addUniqueId(BpmnModelUtils.getProjName(flowElementContainer.getEInstance()), flowElementContainer, sequenceFlow);
		
		sequenceFlow.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, id.getId());
//		sequenceFlow.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, start.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME)+"_"+end.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME));
		sequenceFlow.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME,  id.getName());
		sequenceFlow.setAttribute(BpmnMetaModelConstants.E_ATTR_IS_IMMEDIATE, true);
		sequenceFlow.setAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF, start.getEInstance());
		sequenceFlow.setAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF, end.getEInstance());
		start.addToListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING, sequenceFlow);
		end.addToListAttribute(BpmnMetaModelConstants.E_ATTR_INCOMING, sequenceFlow);
		//subprocess does not have lanes
		if (!BpmnModelClass.SUB_PROCESS.isSuperTypeOf(flowElementContainer.getEClassType())) {
			List<EObject> lanepath = BpmnModelUtils.getLanePath(flowElementContainer, lane);
			for (EObject laneItem : lanepath) {
				sequenceFlow.addToListAttribute(
						BpmnMetaModelConstants.E_ATTR_LANES, laneItem);
			}
			// sequenceFlow.addToListAttribute("lanes", lane);
			lane.addToListAttribute(
					BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENT_REFS,
					sequenceFlow);
		}
		
		
		flowElementContainer.addToListAttribute(BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENTS, sequenceFlow);
		ExtensionHelper.getAddDataExtensionValueWrapper(sequenceFlow);// add the extension attribute 
		updateGatewayDirection(start);
		updateGatewayDirection(end);
		
		updateGatewayDefaultPropForSequenceAdd(start, sequenceFlow);
		
		updateExclusiveGatewayMergeMapping(start, getProcessUri());
		updateExclusiveGatewayMergeMapping(end, getProcessUri());
		
    	getModelChangeAdapterFactory().adapt(sequenceFlow, ModelChangeListener.class);    	
		return sequenceFlow;
		
	}
	
	public void updateGatewayDirection(EObjectWrapper<EClass, EObject> node) {
		if (BpmnModelClass.GATEWAY.isSuperTypeOf(node.getEClassType())) {
			EList<EObject> outGoingEdges = node
					.getListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING);
			EList<EObject> incomingEdges = node
					.getListAttribute(BpmnMetaModelConstants.E_ATTR_INCOMING);
			EEnumWrapper<EEnum, EEnumLiteral> enWrapper = EEnumWrapper.createInstance(BpmnMetaModel.ENUM_GATEWAY_DIRECTION);
			if (outGoingEdges.size() <= 1 && incomingEdges.size() > 1) {
				EEnumLiteral converging = enWrapper.getEnumLiteral(BpmnMetaModel.ENUM_GATEWAY_DIRECTION_CONVERGING);
				node.setAttribute(
								BpmnMetaModelConstants.E_ATTR_GATEWAY_DIRECTION,converging);
			} else if (outGoingEdges.size() > 1 && incomingEdges.size() <= 1) {
				EEnumLiteral diverging = enWrapper.getEnumLiteral(BpmnMetaModel.ENUM_GATEWAY_DIRECTION_DIVERGING);
				node.setAttribute(
								BpmnMetaModelConstants.E_ATTR_GATEWAY_DIRECTION,diverging);
			} else if (outGoingEdges.size() > 1 && incomingEdges.size() > 1) {
				EEnumLiteral mixed = enWrapper.getEnumLiteral(BpmnMetaModel.ENUM_GATEWAY_DIRECTION_MIXED);
				node.setAttribute(
						BpmnMetaModelConstants.E_ATTR_GATEWAY_DIRECTION,mixed);
			} else {
				EEnumLiteral unspecified = enWrapper.getEnumLiteral(BpmnMetaModel.ENUM_GATEWAY_DIRECTION_UNSPECIFIED);
				node.setAttribute(
								BpmnMetaModelConstants.E_ATTR_GATEWAY_DIRECTION,unspecified);
			}
		}
	}
	
	private void updateGatewayDefaultPropForSequenceAdd(EObjectWrapper<EClass, EObject> wrap, EObjectWrapper<EClass, EObject> sequence) {
		if (BpmnModelClass.EXCLUSIVE_GATEWAY.isSuperTypeOf(wrap.getEClassType()) ||
				BpmnModelClass.INCLUSIVE_GATEWAY.isSuperTypeOf(wrap.getEClassType())||
				BpmnModelClass.COMPLEX_GATEWAY.isSuperTypeOf(wrap.getEClassType())) {
			EObject defaultSeq = (EObject)wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_DEFAULT);
			 EList<EObject> listAttribute = wrap.getListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING);
			if(defaultSeq == null && listAttribute.size() == 1){
				Map<String, Object> updateList = new HashMap<String, Object>();
				
				updateList.put(BpmnMetaModelConstants.E_ATTR_DEFAULT, sequence.getEInstance());
				updateEmfModel(wrap, updateList);
			}

		}
	}
	
	private void updateGatewayDefaultPropForSequenceDelete(
			EObjectWrapper<EClass, EObject> wrap,
			EObjectWrapper<EClass, EObject> sequence) {
		if (BpmnModelClass.EXCLUSIVE_GATEWAY
				.isSuperTypeOf(wrap.getEClassType())
				|| BpmnModelClass.INCLUSIVE_GATEWAY.isSuperTypeOf(wrap
						.getEClassType())
				|| BpmnModelClass.COMPLEX_GATEWAY.isSuperTypeOf(wrap
						.getEClassType())) {
			EObject defaultSeq = (EObject) wrap
					.getAttribute(BpmnMetaModelConstants.E_ATTR_DEFAULT);
			if (defaultSeq != null) {
				EObjectWrapper<EClass, EObject> defaultSeqWrap = EObjectWrapper
						.wrap(defaultSeq);
				String id = defaultSeqWrap
						.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				String seqId = sequence
						.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				if (id.equals(seqId)) {
					Map<String, Object> updateList = new HashMap<String, Object>();
					EObject newDefault = null;
					EList<EObject> listAttribute = wrap.getListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING);
					if(listAttribute.size() > 0)
						newDefault = listAttribute.get(0);
					updateList.put(BpmnMetaModelConstants.E_ATTR_DEFAULT, newDefault);
					updateEmfModel(wrap, updateList);
				}
			}
		}
	}
	
	private void updateExclusiveGatewayMergeMapping(EObjectWrapper<EClass, EObject> wrap, String processUri) {
		if (BpmnModelClass.EXCLUSIVE_GATEWAY.isSuperTypeOf(wrap.getEClassType())) {
			Map<String, Object> updateList = new HashMap<String, Object>();
			String createNewXSLT = createNewXSLT(MapperControl.getCopyXsltTemplate(), processUri);
			EList<EObject> listAttribute = wrap
					.getListAttribute(BpmnMetaModelConstants.E_ATTR_INCOMING);
			ArrayList<EObject> forkTransformations = new ArrayList<EObject>();
			for (EObject eObject : listAttribute) {
				EObjectWrapper<EClass, EObject> seqWrap = EObjectWrapper
						.wrap(eObject);
				String attribute = seqWrap
						.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				EObjectWrapper<EClass, EObject> createGatewayExpressionData = createGatewayForkJoinExpressionData(attribute, createNewXSLT);
				forkTransformations.add(createGatewayExpressionData.getEInstance());
			}
			
			updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_JOIN_EXPRESSIONS, forkTransformations);
			updateEmfModel(wrap, updateList);
		}
	}
	
	private String createNewXSLT(String xsltTemplate, String processUri) {
		ArrayList<String> params = new ArrayList<String>();
		params.add(processUri);
		String newXslt = XSTemplateSerializer.serialize(xsltTemplate, params,null);
		return newXslt;
	}
	
	public void removeArtifacts(
			EObjectWrapper<EClass, EObject> artifact,
			EObjectWrapper<EClass, EObject> flowElementContainer) {


		flowElementContainer.removeFromListAttribute(BpmnMetaModelConstants.E_ATTR_ARTIFACTS, artifact);
	}
	
	public void removeAssociation(
			EObjectWrapper<EClass, EObject> association,
			EObjectWrapper<EClass, EObject> start,
			EObjectWrapper<EClass, EObject> end,
			EObjectWrapper<EClass, EObject> flowElementContainer,
			EObjectWrapper<EClass, EObject> lane) {


		flowElementContainer.removeFromListAttribute(BpmnMetaModelConstants.E_ATTR_ARTIFACTS, association);
	}
	
	public void removeSequenceFlow(
			EObjectWrapper<EClass, EObject> sequenceFlow,
			EObjectWrapper<EClass, EObject> start,
			EObjectWrapper<EClass, EObject> end,
			EObjectWrapper<EClass, EObject> flowElementContainer,
			EObjectWrapper<EClass, EObject> lane) {

			start.removeFromListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING, sequenceFlow);
			end.removeFromListAttribute(BpmnMetaModelConstants.E_ATTR_INCOMING,
					sequenceFlow);
			
			updateGatewayDirection(start);
			updateGatewayDirection(end);
			updateGatewayDefaultPropForSequenceDelete(start, sequenceFlow);
			
			updateExclusiveGatewayMergeMapping(start, getProcessUri());
			updateExclusiveGatewayMergeMapping(end, getProcessUri());

		if(!BpmnModelClass.SUB_PROCESS.isSuperTypeOf(flowElementContainer.getEClassType()))
			lane.removeFromListAttribute(
					BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENT_REFS,
					sequenceFlow);
		flowElementContainer.removeFromListAttribute(BpmnMetaModelConstants.E_ATTR_FLOW_ELEMENTS, sequenceFlow);
	}
	
	private String getProcessUri() {
		EObjectWrapper<EClass, EObject> proc = getModelRoot();
		String fullPath = proc.getAttribute(BpmnMetaModelConstants.E_ATTR_FOLDER);
		String name = proc.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
		if(fullPath.equals("/"))
			fullPath = fullPath+ name;
		else
			fullPath= fullPath + "/"+name;
		return fullPath;
	}
	
	public EObjectWrapper<EClass, EObject> getDefaultLaneSet(
			EObjectWrapper<EClass, EObject> process) {
		EList<EObject> laneSets = process.getListAttribute(BpmnMetaModelConstants.E_ATTR_LANE_SETS);
		if(laneSets.size() > 0) {
			return EObjectWrapper.wrap(laneSets.get(0));
		} 
		return null;
	}

	public EObjectWrapper<EClass, EObject> getDefaultLane(
			EObjectWrapper<EClass, EObject> process) {
		EObjectWrapper<EClass, EObject> laneSet = getDefaultLaneSet(process);
		if(laneSet != null ){
			EList<EObject> lanes = laneSet.getListAttribute(BpmnMetaModelConstants.E_ATTR_LANES);
			if(lanes.size() > 0) {
				return EObjectWrapper.wrap(lanes.get(0));
			}
		}
		return null;
	}

	public void createItemDefinitionForType(String projectName,EClass type) {
		EObjectWrapper<EClass, EObject> itemDefWrapper = EObjectWrapper.createInstance(BpmnModelClass.ITEM_DEFINITION);
		itemDefWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_KIND, BpmnModelClass.ENUM_ITEM_KIND_INFORMATION);
		itemDefWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_STRUCTURE, type);
		// TODO : The idea is to create a XSD Schema structures for expressions,functions understood by BE in the BE Namespace
		// The problem is the BPMN model does not create a rulefunction to eval a expression until generation , therefore
		// the parameters,result of the expression needs to be crafted during modelling stage.
		// do not use this now
		
	}
	
	
	/**
	 * @param useInstance
	 * @return
	 */
	public Collection<EObjectWrapper<EClass, EObject>> getPropertyDefinitions(EObjectWrapper<EClass, EObject> useInstance) {
		
//		List<EObjectWrapper<EClass, EObject>> propDefs = new ArrayList<EObjectWrapper<EClass,EObject>>();
//		boolean validDataExtensionAttribute = ExtensionHelper.isValidDataExtensionAttribute(useInstance, BpmnMetaModelExtensionConstants.E_ATTR_PROPERTIES);
//		if (validDataExtensionAttribute) {
//			EObjectWrapper<EClass, EObject> valWrapper = ExtensionHelper
//					.getAddDataExtensionValueWrapper(useInstance);
//			List<EObject> listAttribute = valWrapper
//					.getListAttribute(BpmnMetaModelExtensionConstants.E_ATTR_PROPERTIES);
//			for (EObject eObject : listAttribute) {
//				propDefs.add(EObjectWrapper.wrap(eObject));
//			}
//		}
		List<EObjectWrapper<EClass, EObject>> propDefs = new ArrayList<EObjectWrapper<EClass,EObject>>();
		List<EObject> listAttribute = useInstance.getListAttribute(BpmnMetaModelConstants.E_ATTR_PROPERTIES);
		for (EObject eObject : listAttribute) {
			propDefs.add(EObjectWrapper.wrap(eObject));
		}
		
		return propDefs;
	}

	
	/**
	 * @param propDef
	 * @return
	 */
	public String getPropertyName(EObjectWrapper<EClass, EObject> property) {
		return property.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_NAME);
	}
	
	public void switchItemdefinitionType(String projName, EObjectWrapper<EClass, EObject> propDef, boolean isArray){
		EObjectWrapper<EClass, EObject> itemdef = EObjectWrapper.wrap((EObject)propDef.getAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF));
		if(!itemdef.getAttribute(BpmnMetaModelConstants.E_ATTR_IS_COLLECTION).equals(isArray)){
			String id = itemdef.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			if(isArray){
				id = id+ "[]";
			}else{
				id = id.replace("[]", "");
			}
			
			EObjectWrapper<EClass, EObject> eObjectWrapper = ECoreHelper.getItemDefinitionUsingNameSpace(projName, id);
			if(eObjectWrapper != null){
				propDef.setAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF, eObjectWrapper);
				EObject attribute = (EObject)itemdef.getAttribute(BpmnMetaModelConstants.E_ATTR_IMPORT);
				if(attribute != null){
					EObjectWrapper<EClass, EObject> importWrap = EObjectWrapper.wrap(attribute);
					String loc = importWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_LOCATION);
					EObjectWrapper<EClass, EObject> temp = EObjectWrapper.wrap((EObject)itemdef.getAttribute(BpmnMetaModelConstants.E_ATTR_IMPORT));
					temp.setAttribute(BpmnMetaModelConstants.E_ATTR_LOCATION, loc);
				}
				
			}
		}
	}
	

	/**
	 * @param propDef
	 * @return
	 */
	public EObjectWrapper<EClass, EObject> getItemDefinition(String projectName, ExpandedName type, boolean isArray) {
		String id = type.toString();
		if(isArray)
			id = type.toString()+"[]";
		
		EObjectWrapper<EClass, EObject> eObjectWrapper = ECoreHelper.getItemDefinitionUsingNameSpace(projectName, id);
		return eObjectWrapper;
	}
	
	
	public EObjectWrapper<EClass, EObject> getItemDefinitionForRuleElement(String location, boolean isReturn, boolean isArray) {
		String projectName = modelRoot.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT);
		List<EObjectWrapper<EClass, EObject>> itemDefinitionsList = ECoreHelper.getItemDefinitionUsingLocation(projectName, location);
		EObjectWrapper<EClass, EObject> result = null;
		for (EObjectWrapper<EClass, EObject> wrap : itemDefinitionsList) {
			String id = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			EObject imp = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_IMPORT);
			if(imp != null){
				EObjectWrapper<EClass, EObject> impWrapper = EObjectWrapper.wrap(imp);
				String loc = impWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_LOCATION);
				if(loc!= null && loc.equals(location)){
					if(isReturn){
						if(isArray && id.contains("return[]")){
							result = wrap;
							break;
						}else if(!isArray && id.contains("return")&& !id.contains("[]")){
							result = wrap;
							break;
						}
					}else{
						if(isArray && id.contains("arguments[]")){
							result = wrap;
							break;
						}else if(!isArray && id.contains("arguments")&& !id.contains("[]")){
							result = wrap;
							break;
						}
					}
						
				}
			}
		}
		return result;
	}
	
	public EObjectWrapper<EClass, EObject> getItemDefinitionUsingEntity(String entityPath, boolean isArray) {
		if(entityPath == null || entityPath.trim().isEmpty())
			return null;
		String projectName = modelRoot.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT);
		String name = entityPath.substring(entityPath.lastIndexOf('/') + 1);
		String nsURI = RDFTnsFlavor.BE_NAMESPACE + entityPath;
		
		ExpandedName type = ExpandedName.makeName(nsURI, name);
		String id = type.toString();
		if(isArray)
			id = type.toString()+"[]";
		EObjectWrapper<EClass, EObject> eObjectWrapper = ECoreHelper.getItemDefinitionUsingNameSpace(projectName, id);
		if(eObjectWrapper != null){
			EObject attribute = (EObject)eObjectWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_IMPORT);
			if(attribute != null){
				EObjectWrapper.wrap(attribute).setAttribute(BpmnMetaModelConstants.E_ATTR_LOCATION, entityPath);
			}
		}
		return eObjectWrapper;
	}
	
	

	/**
	 * @param graph
	 * @returno
	 */
	public TSEGraph getRootGraph(TSEGraph graph){
		EObject userObject = (EObject) graph.getUserObject();
		EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper.wrap(userObject);
		if(userObjWrapper.isInstanceOf(BpmnModelClass.PROCESS)) {
			return graph;
		} else	if(userObjWrapper.isInstanceOf(BpmnModelClass.LANE)) {
			TSGraphMember parent = graph.getParent();
			if(parent != null && parent instanceof TSNode) {
				TSGraphObject parentOwner = parent.getOwner();
				if(parentOwner != null && parentOwner instanceof TSEGraph) {
					TSEGraph parentGraph = (TSEGraph) parentOwner;
					if(parentGraph.getParent() == null) { // root graph for process
						// then this lane is a top level lane
						EObject pgUserObject = (EObject) parentGraph.getUserObject();
						EObjectWrapper<EClass, EObject> pgUserObjWrapper = EObjectWrapper.wrap(pgUserObject);
						if(pgUserObjWrapper.isInstanceOf(BpmnModelClass.PROCESS)){
							return parentGraph;
						}								
					}
				}
			}
		}
		return null;
	}
	
	public EObjectWrapper<EClass, EObject> createMessage(String name,
			EObjectWrapper<EClass, EObject> itemdef) {
		EObjectWrapper<EClass, EObject> message = EObjectWrapper
				.createInstance(BpmnModelClass.MESSAGE);
		message.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, name);
		message.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, name);
		message.setAttribute(BpmnMetaModelConstants.E_ATTR_STRUCTURE_REF,
				itemdef.getEInstance());

		return message;
	}
	
	public EObjectWrapper<EClass, EObject> createDataOutPut(String name,
			EObjectWrapper<EClass, EObject> itemdef) {

		EObjectWrapper<EClass, EObject> dataOutput = EObjectWrapper
				.createInstance(BpmnModelClass.DATA_OUTPUT);
		dataOutput.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, name);
		dataOutput.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, name);
		if(itemdef != null)
			dataOutput.setAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF,itemdef.getEInstance());

		return dataOutput;
	}
	
	public EObjectWrapper<EClass, EObject> createDataInput(String name,
			EObjectWrapper<EClass, EObject> itemdef) {

		EObjectWrapper<EClass, EObject> dataInput = EObjectWrapper
				.createInstance(BpmnModelClass.DATA_INPUT);
		dataInput.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, name);
		dataInput.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, name);
		if(itemdef != null)
			dataInput.setAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF,itemdef.getEInstance());

		return dataInput;
	}
	
	public EObjectWrapper<EClass, EObject> createIOSpecification() {

		EObjectWrapper<EClass, EObject> ioSpec = EObjectWrapper
				.createInstance(BpmnModelClass.INPUT_OUTPUT_SPECIFICATION);
//		parent.setAttribute(BpmnMetaModelConstants.E_ATTR_IO_SPECIFICATION, ioSpec.getEInstance());

		return ioSpec;
	}
	
	private EObjectWrapper<EClass, EObject> createDataAssociation(EObjectWrapper<EClass, EObject> sourceRef,EObjectWrapper<EClass, EObject> targetRef, String expression, EClass className ) {

		EObjectWrapper<EClass, EObject> ioSpec = EObjectWrapper
				.createInstance(className);
		EList<EObject> listAttribute = ioSpec.getListAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF);
		if(sourceRef != null){
			if(listAttribute == null || listAttribute.isEmpty()){
				ArrayList<EObject> list = new ArrayList<EObject>();
				list.add(sourceRef.getEInstance());
				ioSpec.setAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF,list );
			}else
				ioSpec.addToListAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF, sourceRef);
		}
		
		
		if(targetRef != null)
			ioSpec.setAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF, targetRef.getEInstance());
		
		EObjectWrapper<EClass, EObject> expressionWrapper = EObjectWrapper.createInstance(BpmnModelClass.FORMAL_EXPRESSION);
		getModelChangeAdapterFactory().adapt(expressionWrapper, ModelChangeListener.class);
		expressionWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_BODY, expression);
		
		ioSpec.setAttribute(BpmnMetaModelConstants.E_ATTR_TRANSFORMATION, expressionWrapper.getEInstance());

		
		return ioSpec;
	}
	
	public EObjectWrapper<EClass, EObject> createOutputDataAssociation(EObjectWrapper<EClass, EObject> sourceRef,EObjectWrapper<EClass, EObject> targetRef, String expression ) {
		return createDataAssociation(sourceRef, targetRef, expression, BpmnModelClass.DATA_OUTPUT_ASSOCIATION);
	}
	
	public EObjectWrapper<EClass, EObject> createInputDataAssociation(EObjectWrapper<EClass, EObject> sourceRef,EObjectWrapper<EClass, EObject> targetRef, String expression ) {
		return createDataAssociation(sourceRef, targetRef, expression, BpmnModelClass.DATA_INPUT_ASSOCIATION);
	}
	
	public EObjectWrapper<EClass, EObject> createVrfImplementation(String uri) {
		EObjectWrapper<EClass, EObject> impl = EObjectWrapper
				.createInstance(BpmnModelClass.EXTN_VRFIMPL_DATA);
		impl.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_URI, uri);
		impl.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EXPRESSION, "");
		impl.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_DEPLOYED, false);
		return impl;
	}
	
	public EObjectWrapper<EClass, EObject> createGatewayForkJoinExpressionData(String sequenceId, String transformation){
		EObjectWrapper<EClass, EObject> impl = EObjectWrapper
				.createInstance(BpmnModelClass.EXTN_GATEWAY_EXPRESSION_DATA);
		impl.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TRANSFORMATION, transformation);
		impl.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_SEQUENCE_FLOW_ID, sequenceId);
		return impl;
	}
	
	public EObjectWrapper<EClass, EObject> createParallelGatewayJoinExpressionData(String transformation){
		EObjectWrapper<EClass, EObject> impl = EObjectWrapper
				.createInstance(BpmnModelClass.EXTN_EXPRESSION_DATA);
		impl.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TRANSFORMATION, transformation);
		return impl;
	}
	
	public EObjectWrapper<EClass, EObject> createPoint(Double x, Double y){
		EObjectWrapper<EClass, EObject> impl = EObjectWrapper
				.createInstance(BpmnModelClass.EXTN_POINT);
		impl.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_X, x);
		impl.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_Y, y);
		return impl;
	}
	
}