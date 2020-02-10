package com.tibco.cep.bpmn.ui.xpdl.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.graph.BpmnGraphUtils;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelChangeAdapterFactory;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelChangeEvent;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelChangeListener;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tibco.cep.bpmn.ui.xpdl.XpdlNS.Attributes;
import com.tibco.cep.bpmn.ui.xpdl.XpdlNS.Elements;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * 
 * @author majha
 *
 */
public class XpdlImportHelper implements ModelChangeListener {
	
	private ModelChangeAdapterFactory modelChangeAdapterFactory;
	private ModelController modelController;
	private File xpdlFile;
	private IProject project;
	private IPath containerPath;
	
	//XPDL model
	private Map<String, Node> poolsMap;
	private Map<String, Map<String, Node>> lanesMap;
	private HashMap<String, EObjectWrapper<EClass, EObject>> tempProcess;
	private HashMap<String, EObjectWrapper<EClass, EObject>> tempSubProcess;
    private Map<String, EObjectWrapper<EClass, EObject>> createdLanesMap;
	private Map<EObjectWrapper<EClass, EObject>, Node> processMap;
    private Map<String, EObjectWrapper<EClass, EObject>> nodesMap;
	private Document doc;
	private Map<String, EObjectWrapper<EClass, EObject>> processes;
	private Map<String, EObjectWrapper<EClass, EObject>> subProcesses;
	private Map < String, Node> activitySetMap  = new HashMap<String,Node>();
	
	public XpdlImportHelper(File xpdlFile, IProject project,IPath containerPath) {
		this.modelChangeAdapterFactory = new ModelChangeAdapterFactory(this);
		this.modelController = new ModelController(modelChangeAdapterFactory);
		this.xpdlFile = xpdlFile;
		this.project = project;
		this.containerPath = containerPath;
	}

	public Map<String, EObjectWrapper<EClass, EObject>> importXpdl() throws Exception {
		processes = new HashMap<String, EObjectWrapper<EClass, EObject>>();
		doc = loadModel(xpdlFile);
		if (doc != null) {
			poolsMap = new HashMap<String, Node>();
			lanesMap = new HashMap<String, Map<String, Node>>();
			// processMap = new HashMap<String, Node>();
			// transitionMap = new HashMap<String, Map<String,Node>>();
			nodesMap = new HashMap<String, EObjectWrapper<EClass, EObject>>();
			subProcesses = new HashMap<String, EObjectWrapper<EClass, EObject>>();
			loadPools(doc);
		}

		return processes;
	}
	
	private Document loadModel(File xpdlFile) throws Exception {
		DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
		fact.setNamespaceAware(true);
		DocumentBuilder builder = fact.newDocumentBuilder();
		FileInputStream fis = new FileInputStream(xpdlFile); 
		Document doc = builder.parse(fis);
		
		return doc;
	}
	
	private NodeList getElementList(Element node, ExpandedName name){
		return node.getElementsByTagNameNS(name.namespaceURI, name.localName);
	}
	
//	private Map<String,EObjectWrapper<EClass, EObject>> loadProcesses(Document doc,
//			IProject project, IPath containerPath) {
//		Map<String,EObjectWrapper<EClass, EObject>> processes = new HashMap<String, EObjectWrapper<EClass,EObject>>();
//		Element root = doc.getDocumentElement();
//		NodeList elementsByTagNameNS = getElementList(root,
//				Elements.WORKFLOWPROCESSES);
//
//		if (elementsByTagNameNS.getLength() > 0) {
//			Node item = elementsByTagNameNS.item(0);
//			NodeList childNodes = getElementList((Element) item,
//					Elements.WORKFLOWPROCESS);
//			processMap = new HashMap<EObjectWrapper<EClass,EObject>, Node>();
//			int length = childNodes.getLength();
//			for (int i = 0; i < length; i++) {
//				Node processNode = childNodes.item(i);
//				String processName = getAttributevalue(processNode,
//						Attributes.NAME);
//				String processId = getAttributevalue(processNode,
//						Attributes.ID);
//				EObjectWrapper<EClass, EObject> process = modelController
//						.createProcess(processName, project.getName(), containerPath.makeAbsolute().toPortableString());
//				processMap.put(process, processNode);
//				processes.put( processName, process);
//				String poolIDForProcess = getPoolIDForProcess(processId);
//				Map<String, EObjectWrapper<EClass, EObject>> loadLanes = loadLanes(
//						root, lanesMap.get(poolIDForProcess), process);
//				if (loadLanes.size() > 0){
//					loadActivities(process, loadLanes, processNode);
//					loadTransitions(process, loadLanes, processNode);
//				}
//			}
//		}
//		return processes;
//	}
//	
	private String getPoolIDForProcess(String processId){
		Iterator<String> iterator = poolsMap.keySet().iterator();
		while (iterator.hasNext()) {
			String id = (String) iterator.next();
			Node node = poolsMap.get(id);
			String pId = getAttributevalue(node,Attributes.PROCESS);
			if(pId.equalsIgnoreCase(processId))
				return id;
		}
		
		return processId;
	}
	
	private void loadActivities(EObjectWrapper<EClass, EObject> process, Map<String, EObjectWrapper<EClass, EObject>> lanes, Node processNode){
		String activitySetItemId;
		Node activitySetItem = null; int counter = 0;
		
		NodeList activitySetsNodes = getElementList((Element)processNode, Elements.ACTIVITYSETS);
		if(activitySetsNodes.getLength() > 0){
			Node activitySetsItem = activitySetsNodes.item(0);
			NodeList activitySetNodes = getElementList((Element)activitySetsItem , Elements.ACTIVITYSET);
			if(activitySetNodes.getLength() > 0){
				for(counter =0; counter < activitySetNodes.getLength() ; counter++){
					activitySetItem = activitySetNodes.item(counter );
					activitySetItemId = getAttributevalue(activitySetItem, Attributes.ID);
					activitySetMap.put(activitySetItemId, activitySetItem);
				}
			}
		}
		NodeList activitiesNodes = getElementList((Element)processNode  , Elements.ACTIVITIES);
		if(activitiesNodes.getLength() > 0){
			for( int iterate = counter ;iterate < activitiesNodes.getLength(); iterate++){
			Node activitiesItem = activitiesNodes.item(iterate);
			NodeList activityNodes = getElementList((Element) activitiesItem, Elements.ACTIVITY);
			int length = activityNodes.getLength();

			for (int it = 0; it < length; it++) {
				Node activityItem = activityNodes.item(it);
				String laneId = fetchLaneId((Element)activityItem);
				EObjectWrapper<EClass, EObject> lane = getLane(lanes, laneId);
				NodeList eventList = getElementList((Element)activityItem, Elements.EVENT);
				NodeList gatewayList = getElementList((Element)activityItem, Elements.ROUTE);
				NodeList embeddedProcessList = getElementList((Element)activityItem,Elements.BLOCKACTIVITY);
				
				if(eventList.getLength() != 0){
					int eLen = eventList.getLength();
					for (int j = 0; j < eLen; j++) {
						eventList.item(j);
						loadEvents(process, lane, activityItem);	
					}
					
				}else if(gatewayList.getLength() != 0){
					loadGateways(process, lane, activityItem);
				}else if(embeddedProcessList.getLength() != 0){
					Node embeddedProcessItem = embeddedProcessList.item(0);
					loadEmbeddedProcess(process,lane,activityItem,embeddedProcessItem);
				}else{
					loadTask(process, lane, activityItem);
				}
			}
			}
		}
		  
		
		
	}
	

	private void loadActivitiesForSubprocess(EObjectWrapper<EClass, EObject> subProcess, Node processNode){
		NodeList childNodes = getElementList((Element) processNode, Elements.ACTIVITIES);
		if(childNodes.getLength() > 0){
			Node activitiesItem = childNodes.item(0);
			childNodes = getElementList((Element) activitiesItem, Elements.ACTIVITY);
			int length = childNodes.getLength();

			for (int i = 0; i < length; i++) {
				Node activityItem = childNodes.item(i);
				EObjectWrapper<EClass, EObject> lane = subProcess;
				NodeList eventList = getElementList((Element)activityItem, Elements.EVENT);
				NodeList gatewayList = getElementList((Element)activityItem, Elements.ROUTE);
				
				if(eventList.getLength() != 0){
					int eLen = eventList.getLength();
					for (int j = 0; j < eLen; j++) {
						eventList.item(j);
						loadEvents(subProcess, lane, activityItem); 
					}
					
				}else if(gatewayList.getLength() != 0){
					loadGateways(subProcess, lane, activityItem);
				}else{
					loadTask(subProcess, lane, activityItem);
				}
			}
		}
	}
	
	private void loadTransitions(EObjectWrapper<EClass, EObject> process, Map<String, EObjectWrapper<EClass, EObject>> lanes, Node processNode){
		EObjectWrapper<EClass, EObject> end,start=null;
		
		NodeList transitionsNodes = getElementList((Element) processNode, Elements.TRANSITIONS);
		if(transitionsNodes.getLength() > 0){
			
			for ( int iterate =0 ; iterate < transitionsNodes.getLength(); iterate++){
			Node transitionsItem = transitionsNodes.item(iterate);
			NodeList transitionNodes = getElementList((Element) transitionsItem, Elements.TRANSITION);
			int length = transitionNodes.getLength();

			for (int i = 0; i < length; i++) {
				Node transitionNode = transitionNodes.item(i);
				String laneId = "";
				EObjectWrapper<EClass, EObject> lane = getLane(lanes, laneId);
				String toId = getAttributevalue(transitionNode, Attributes.TO);
			//	 end =  checkForEndembeddedSubProcess(toId) ;
				/*  if (end == null)*/ end = nodesMap.get(toId);
				
				String fromId = getAttributevalue(transitionNode, Attributes.FROM);
			//	 start = checkForStartembeddedSubProcess(fromId); 
				/* if( start == null)*/ start = nodesMap.get(fromId);
				 
				if(start != null && end != null)
					modelController.createSequenceFlow(start, end, process, lane);
			}
		  }
		}
	}
	
	
	
	private void loadTransitionsForSubprocess(EObjectWrapper<EClass, EObject> process, Node processNode){
		NodeList childNodes = getElementList((Element) processNode, Elements.TRANSITIONS);
		if(childNodes.getLength() > 0){
			Node transitionsItem = childNodes.item(0);
			childNodes = getElementList((Element) transitionsItem, Elements.TRANSITION);
			int length = childNodes.getLength();

			for (int i = 0; i < length; i++) {
				Node transitionNode = childNodes.item(i);
				String toId = getAttributevalue(transitionNode, Attributes.TO);
				EObjectWrapper<EClass, EObject> end = nodesMap.get(toId);
				
				String fromId = getAttributevalue(transitionNode, Attributes.FROM);
				EObjectWrapper<EClass, EObject> start = nodesMap.get(fromId);
				if(start != null && end != null)
					modelController.createSequenceFlow(start, end, process, process);
			}
		}
	}


	
	private  EObjectWrapper<EClass, EObject> getLane(Map<String, EObjectWrapper<EClass, EObject>> lanes, String LaneId){
	 Iterator<EObjectWrapper<EClass, EObject>> iterator = lanes.values().iterator();
		return iterator.next();// only one lane supported at the momemnt
	}
	
	private String fetchLaneId(Element element){
		String id = "";
		NodeList elementList = getElementList(element, Elements.NODEGRAPHICSINFOS);
		if(elementList.getLength() > 0 ){
			NodeList elementList2 = getElementList((Element)elementList.item(0), Elements.NODEGRAPHICSINFO);
			if(elementList2.getLength() >0){
				id = getAttributevalue(elementList2.item(0), Attributes.LANEID);
			}
		}
		return id;
	}
	
	private void fetchCordinates(Element element, EObjectWrapper<EClass, EObject> wrap){
		NodeList elementList = getElementList(element, Elements.NODEGRAPHICSINFOS);
		if(elementList.getLength() > 0 ){
			NodeList elementList2 = getElementList((Element)elementList.item(0), Elements.NODEGRAPHICSINFO);
			if(elementList2.getLength() >0){
				NodeList cordList = getElementList((Element)elementList2.item(0), Elements.COORDINATES);
				if(cordList.getLength() > 0){
					Element xy = (Element)cordList.item(0);
					String x = getAttributevalue(xy, Attributes.XCOORDINATE);
					String y = getAttributevalue(xy, Attributes.YCOORDINATE);
					try {
						double doubX = Double.parseDouble(x);
						double doubY = Double.parseDouble(y);
						Map<String, Object> updateList = new HashMap<String, Object>();
						EObjectWrapper<EClass, EObject> createPoint = modelController.createPoint(doubX , doubY);
						updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_NODE_POINT, createPoint.getEInstance());
						modelController.updateEmfModel(wrap, updateList);
						
					} catch (Exception e) {
						// TODO: handle exception
					}
					
				}
			}
		}
	}
	
	private void loadEvents(EObjectWrapper<EClass, EObject> process,
			EObjectWrapper<EClass, EObject> lane, Node item) {
		String nodeId = getAttributevalue(item, Attributes.ID);
		String name = getAttributevalue(item, Attributes.NAME);

		NodeList eventList = getElementList((Element) item, Elements.EVENT);
		if (eventList.getLength() > 0) {
			EObjectWrapper<EClass, EObject> createEvent = null;
			Node eventNode = eventList.item(0);
			NodeList startEventList = getElementList((Element) eventNode,
					Elements.STARTEVENT);
			NodeList intermediateEventList = getElementList(
					(Element) eventNode, Elements.INTERMEDIATEEVENT);
			NodeList endEventList = getElementList((Element) eventNode,
					Elements.ENDEVENT);
			if (startEventList.getLength() > 0) {
				Node startEventNode = startEventList.item(0);

				EClass type = BpmnModelClass.START_EVENT;
				String attributevalue = getAttributevalue(startEventNode,
						Attributes.TRIGGER);
				EClass extendedType = null;
				if (attributevalue.equals("Message"))
					extendedType = BpmnModelClass.MESSAGE_EVENT_DEFINITION;
				else if (attributevalue.equals("Timer"))
					extendedType = BpmnModelClass.TIMER_EVENT_DEFINITION;
				else if (attributevalue.equals("Signal"))
					extendedType = BpmnModelClass.SIGNAL_EVENT_DEFINITION;
				else if (attributevalue.equals("Error"))
					extendedType = BpmnModelClass.ERROR;
				String nodeName = BpmnGraphUtils.getNodeName(type, extendedType);
				if(nodeName== null || nodeName.trim().isEmpty())
					nodeName = name;
				createEvent = modelController.createEvent(nodeName, "", type,
						extendedType, process, lane);
				createEvent.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, name);
			

			} else if (endEventList.getLength() > 0) {
				Node endEventNode = endEventList.item(0);
				EClass type = BpmnModelClass.END_EVENT;
				String attributevalue = getAttributevalue(endEventNode,
						Attributes.TRIGGER);
				EClass extendedType = null;
				if (attributevalue.equals("Message"))
					extendedType = BpmnModelClass.MESSAGE_EVENT_DEFINITION;
				else if (attributevalue.equals("Timer"))
					extendedType = BpmnModelClass.TIMER_EVENT_DEFINITION;
				else if (attributevalue.equals("Signal"))
					extendedType = BpmnModelClass.SIGNAL_EVENT_DEFINITION;
				else if (attributevalue.equals("Error"))
					extendedType = BpmnModelClass.ERROR;

				String nodeName = BpmnGraphUtils.getNodeName(type, extendedType);
				if(nodeName== null || nodeName.trim().isEmpty())
					nodeName = name;
				createEvent = modelController.createEvent(nodeName, "", type,
						extendedType, process, lane);
				createEvent.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, name);
	
				
			} else if (intermediateEventList.getLength() > 0) {
				Node intermediateEventNode = intermediateEventList.item(0);
				EClass type = BpmnModelClass.INTERMEDIATE_THROW_EVENT;
				String attributevalue = getAttributevalue(
						intermediateEventNode, Attributes.TRIGGER);
				EClass extendedType = null;
				if (attributevalue.equals("Message"))
					extendedType = BpmnModelClass.MESSAGE_EVENT_DEFINITION;
				else if (attributevalue.equals("Timer"))
					extendedType = BpmnModelClass.TIMER_EVENT_DEFINITION;
				else if (attributevalue.equals("Signal"))
					extendedType = BpmnModelClass.SIGNAL_EVENT_DEFINITION;
				else if (attributevalue.equals("Error"))
					extendedType = BpmnModelClass.ERROR;

				String nodeName = BpmnGraphUtils.getNodeName(type, extendedType);
				if(nodeName== null || nodeName.trim().isEmpty())
					nodeName = name;
				createEvent = modelController.createEvent(nodeName, "", type,
						extendedType, process, lane);
				createEvent.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, name);

			} else {

			}
			nodesMap.put(nodeId, createEvent);
			fetchCordinates((Element)item, createEvent);
		}
	}
	
	private void loadTask(EObjectWrapper<EClass, EObject> process,
			EObjectWrapper<EClass, EObject> lane, Node item) {
		String nodeId = getAttributevalue(item, Attributes.ID);
		String name = getAttributevalue(item, Attributes.NAME);
	
		NodeList eventList = getElementList((Element) item, Elements.EVENT);
		NodeList gatewayList = getElementList((Element) item, Elements.ROUTE);
		NodeList gateblockActivityList = getElementList((Element) item, Elements.BLOCKACTIVITY);
		NodeList implemetaList = getElementList((Element) item, Elements.IMPLEMENTATION);
		if(eventList.getLength() == 0 && gatewayList.getLength() == 0 && gateblockActivityList.getLength() == 0 ){
			EObjectWrapper<EClass, EObject> activity = null;
			EClass type = BpmnModelClass.RULE_FUNCTION_TASK;
			if(implemetaList.getLength() > 0){
				Node implementationNode = implemetaList.item(0);
				NodeList taskList = getElementList((Element) implementationNode,
						Elements.TASK);
				NodeList subprocessList = getElementList(
						(Element) implementationNode, Elements.SUBFLOW);
				if(taskList.getLength() > 0 ){
					Node taskNode = taskList.item(0);
					NodeList seviceTaskList = getElementList(
							(Element) taskNode, Elements.TASKSERVICE);
					NodeList scriptTaskList = getElementList(
							(Element) taskNode, Elements.TASKSCRIPT);
					NodeList sendTaskList = getElementList(
							(Element) taskNode, Elements.TASKSEND);
					NodeList receiveTaskList = getElementList(
							(Element) taskNode, Elements.TASKRECEIVE);
					NodeList mannualTaskList = getElementList(
							(Element) taskNode, Elements.TASKMANUAL);
					if(seviceTaskList.getLength() > 0 ){
						type = BpmnModelClass.SERVICE_TASK;
					}else if(sendTaskList.getLength() > 0 ){
						type = BpmnModelClass.SEND_TASK;
					}else if(receiveTaskList.getLength() > 0 ){
						type = BpmnModelClass.RECEIVE_TASK;
					}else if(scriptTaskList.getLength() > 0 ){
						type = BpmnModelClass.RULE_FUNCTION_TASK;
					}else if(mannualTaskList.getLength() > 0 ){
						type = BpmnModelClass.MANUAL_TASK;
					}
					
					String nodeName = BpmnGraphUtils.getNodeName(type, null);
					if(nodeName== null || nodeName.trim().isEmpty())
						nodeName = name;
					
					activity = modelController.createActivity(nodeName, "", type, process, lane);
					activity.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, name);
					nodesMap.put(nodeId, activity);
					fetchCordinates((Element)item, activity);
				}else if (subprocessList.getLength() > 0 ){
					type = BpmnModelClass.CALL_ACTIVITY;
					String nodeName = BpmnGraphUtils.getNodeName(type, null);
					if(nodeName== null || nodeName.trim().isEmpty())
						nodeName = name;
					
					activity = modelController.createActivity(nodeName, "", type, process, lane);
					activity.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, name);
					
					nodesMap.put(nodeId, activity);
					fetchCordinates((Element)item, activity);
					
					String procId = getAttributevalue((Element)subprocessList.item(0), Attributes.ID);
				//	loadSubProcesses(doc, procId, activity);
				}
			}else{
				String nodeName = BpmnGraphUtils.getNodeName(type, null);
				if(nodeName== null || nodeName.trim().isEmpty())
					nodeName = name;
				
				activity = modelController.createActivity(nodeName, "", type, process, lane);
				activity.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, name);
				
				nodesMap.put(nodeId, activity);
				fetchCordinates((Element)item, activity);
			}
		}
	}
	
	
	private void loadGateways(EObjectWrapper<EClass, EObject> process,
			EObjectWrapper<EClass, EObject> lane, Node item) {
		String nodeId = getAttributevalue(item, Attributes.ID);
		String name = getAttributevalue(item, Attributes.NAME);
		
		NodeList gatewayList = getElementList((Element) item, Elements.ROUTE);
		if (gatewayList.getLength() > 0) {
			EObjectWrapper<EClass, EObject> gateway = null;
			EClass type = BpmnModelClass.EXCLUSIVE_GATEWAY;
			
			Node gateWayNode = gatewayList.item(0);
			String gatewayType = getAttributevalue(gateWayNode, Attributes.GATEWAYTYPE);
			if(gatewayType.equalsIgnoreCase("parallel") || gatewayType.equalsIgnoreCase("and")){
				type = BpmnModelClass.PARALLEL_GATEWAY;
			}else if(gatewayType.equalsIgnoreCase("exclusive") || gatewayType.equalsIgnoreCase("xor")){
				type = BpmnModelClass.EXCLUSIVE_GATEWAY;
			}else if(gatewayType.equalsIgnoreCase("inclusive") || gatewayType.equalsIgnoreCase("or")){
				type = BpmnModelClass.INCLUSIVE_GATEWAY;
			}else if(gatewayType.equalsIgnoreCase("complex") ){
				type = BpmnModelClass.COMPLEX_GATEWAY;
			}

			String nodeName = BpmnGraphUtils.getNodeName(type, null);
			if (nodeName == null || nodeName.trim().isEmpty())
				nodeName = name;

			gateway = modelController.createGateway(nodeName, "", type, null, process, lane);
			gateway.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, name);
			nodesMap.put(nodeId, gateway);
			fetchCordinates((Element)item, gateway);
		}
	}
	
 	private void loadEmbeddedProcess(EObjectWrapper<EClass, EObject> process,
			EObjectWrapper<EClass, EObject> lane, Node item, Node embeddedProcessItem){
 		String nodeId = getAttributevalue(item , Attributes.ID);
 		String name = getAttributevalue(item, Attributes.NAME);
 		String activitySetId = getAttributevalue( embeddedProcessItem, Attributes.ACTIVITYSETID);
 		EObjectWrapper<EClass, EObject> blockActivity = null;
 		EClass type = BpmnModelClass.SUB_PROCESS;
 		
 		String nodeName = BpmnGraphUtils.getNodeName(type, null);
		if (nodeName == null || nodeName.trim().isEmpty())
			nodeName = name;
		
		blockActivity = modelController.createActivity(nodeName, "", type, process, lane);
		blockActivity.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, name);
		
		nodesMap.put(nodeId, blockActivity);
		fetchCordinates((Element)item,blockActivity);
		
		Set<String > activitySetKeys = activitySetMap.keySet();
		Node activitySetitem = activitySetMap.get(activitySetId);
		loadActivitiesForSubprocess(blockActivity, activitySetitem);
		activitySetKeys.remove(activitySetId);
		
 	}
	
	private void loadPools(Document doc ) {

		NodeList childNodes = null;
		Element root = doc.getDocumentElement();
		NodeList elementsByTagNameNS = root.getElementsByTagNameNS(
				Elements.POOLS.namespaceURI, Elements.POOLS.localName);
		if (elementsByTagNameNS.getLength() > 0) {
			Node item = elementsByTagNameNS.item(0);
			NodeList poolNodes = getElementList((Element) item,
					Elements.POOL);
		int poollength = poolNodes.getLength();
		for (int i = 0; i < poollength; i++) {
	
				Node poolItem = poolNodes.item(i);
				String id = getAttributevalue(poolItem, Attributes.ID);
				poolsMap.put(id, poolItem);
				Map<String, Node> lMap =new HashMap<String, Node>();
				lanesMap.put(id, lMap);
				childNodes = getElementList((Element) poolItem, Elements.LANES);
				if(childNodes.getLength() > 0){
					NodeList laneNodes = getElementList((Element) childNodes.item(0), Elements.LANE);
					int laneslength = laneNodes.getLength();
					for (int k = 0; k < laneslength; k++) {
						String laneId = getAttributevalue(laneNodes.item(k), Attributes.ID);
						lMap.put(laneId, laneNodes.item(k));
					}	
				}
				String pId = getAttributevalue(poolItem,Attributes.PROCESS);
				loadProcesses(doc, pId, project, containerPath);
		
				Collection<EObjectWrapper<EClass, EObject>> values = tempProcess.values();
				Iterator<EObjectWrapper<EClass, EObject>> iterator = values.iterator();
				while (iterator.hasNext()) {
					EObjectWrapper<EClass, EObject> process = (EObjectWrapper<EClass, EObject>) iterator.next();
					Node node = processMap.get(process);
					loadTransitions(process, createdLanesMap, node);
				}
				
				if(tempSubProcess != null){
					values = tempSubProcess.values();
					iterator = values.iterator();
					while (iterator.hasNext()) {
						EObjectWrapper<EClass, EObject> process = (EObjectWrapper<EClass, EObject>) iterator.next();
						Node node = processMap.get(process);
						loadTransitionsForSubprocess(process, node);
						iterator.remove();
					}
				}
	
		}
	}
	}
	
	private void loadProcesses(Document doc, String pId,
			IProject project, IPath containerPath) {
		
		processMap = new HashMap<EObjectWrapper<EClass,EObject>, Node>();
		tempProcess = new HashMap<String, EObjectWrapper<EClass, EObject>>();/*tempSubProcess = null;*/
		Element root = doc.getDocumentElement();
		NodeList elementsByTagNameNS = getElementList(root,
				Elements.WORKFLOWPROCESSES);

		if (elementsByTagNameNS.getLength() > 0) {
			Node item = elementsByTagNameNS.item(0);
			NodeList childNodes = getElementList((Element) item,
					Elements.WORKFLOWPROCESS);
			
			int length = childNodes.getLength();
			for (int i = 0; i < length; i++) {
				Node processNode = childNodes.item(i);
				String processName = getAttributevalue(processNode,
						Attributes.NAME);
				String processId = getAttributevalue(processNode,
						Attributes.ID);
				if(pId.equalsIgnoreCase(processId)){
					EObjectWrapper<EClass, EObject> process = modelController
							.createProcess(processName, project.getName(), containerPath.makeAbsolute().toPortableString());
					processMap.put(process, processNode);
					processes.put( processName, process);
					tempProcess.put(processName, process);
					updateProcessProperties(process);
					String poolIDForProcess = getPoolIDForProcess(processId);
					createdLanesMap= loadLanes(
							root, lanesMap.get(poolIDForProcess), process);
					if (createdLanesMap.size() > 0){
						loadActivities(process, createdLanesMap, processNode);
					}
					if(!activitySetMap.isEmpty()){
						Set<String> keySet = activitySetMap.keySet();
						for( Iterator it =  keySet.iterator() ; it.hasNext();){
							String key = (String) it.next();
							Node activitySetItem = activitySetMap.get(key);
							loadActivities(process,createdLanesMap,activitySetItem);
							it.remove();
						}
					}
				}
			}
		}
	}
	
	private void updateProcessProperties(EObjectWrapper<EClass, EObject> process){
		Element root = doc.getDocumentElement();
		NodeList elementsByTagNameNS = getElementList(root,
				Elements.REDEFINABLEHEADER);
		String author = "";
		if(elementsByTagNameNS.getLength() > 0){
			Element item = (Element)elementsByTagNameNS.item(0);
			NodeList elementList = getElementList(item, Elements.AUTHOR);
			if(elementList.getLength() > 0){
				Element item2 = (Element)elementList.item(0);
				Node firstChild = item2.getFirstChild();
				if(firstChild != null && firstChild instanceof Text){
					Text text = (Text)firstChild;
					author = text.getTextContent();
				}
			}
		}
		HashMap<String, Object> updateList = new HashMap<String, Object>();
		if (author != null && !author.trim().isEmpty()) {
			updateList.put(BpmnMetaModelConstants.E_ATTR_AUTHOR, author);
			modelController.updateEmfModel(process, updateList);
		}
	}

	private void loadSubProcesses(
			Document doc, String pId, EObjectWrapper<EClass, EObject> subProcess) {
		tempSubProcess = new HashMap<String, EObjectWrapper<EClass, EObject>>();
		Element root = doc.getDocumentElement();
		NodeList elementsByTagNameNS = getElementList(root,
				Elements.WORKFLOWPROCESSES);

		if (elementsByTagNameNS.getLength() > 0) {
			Node item = elementsByTagNameNS.item(0);
			NodeList childNodes = getElementList((Element) item,
					Elements.WORKFLOWPROCESS);
			int length = childNodes.getLength();
			for (int i = 0; i < length; i++) {
				Node processNode = childNodes.item(i);
				String processName = getAttributevalue(processNode,
						Attributes.NAME);
				String processId = getAttributevalue(processNode, Attributes.ID);
				if (pId.equalsIgnoreCase(processId)) {
					EObjectWrapper<EClass, EObject> process = subProcess;
					processMap.put(process, processNode);
					subProcesses.put(processName, process);
					tempSubProcess.put(processName,process);
					loadActivitiesForSubprocess(process, processNode);
					loadTransitionsForSubprocess(process, processNode);

					break;
				}
			}
		}
	}
	
	private Map<String, EObjectWrapper<EClass, EObject>>  loadLanes(Element root,Map<String, Node> laneNodeList,
			EObjectWrapper<EClass, EObject> emfProcessWrapper) {
		Map<String, EObjectWrapper<EClass, EObject>> laneMap = new HashMap<String, EObjectWrapper<EClass,EObject>>();
		@SuppressWarnings("unchecked")
		EList<EObject> laneSets = (EList<EObject>) emfProcessWrapper
				.getAttribute(BpmnMetaModelConstants.E_ATTR_LANE_SETS);
		EObjectWrapper<EClass, EObject> parentLaneSet = modelController
				.createLaneSet("", null);
		laneSets.add(parentLaneSet.getEInstance());

		if (laneNodeList.size() > 0) {//currently we support only one lane per process
			Node laneItem = laneNodeList.values().iterator().next();
			String nodeName = getAttributevalue(laneItem, Attributes.NAME);
			String id = getAttributevalue(laneItem, Attributes.ID);
			laneMap.put(id, modelController
					.createLane(nodeName, parentLaneSet));

		}
		return laneMap;
	}
	
	
	private String getAttributevalue(Node node , ExpandedName attrname){
		NamedNodeMap attributes = node.getAttributes();
		Node namedItem = attributes
				.getNamedItem(attrname.localName);
		String value = "";
		if(namedItem != null)
		 value = namedItem.getNodeValue();
		
		return value;
	}
	
	
	@Override
	public void modelChanged(ModelChangeEvent mce) {
		// TODO Auto-generated method stub
		
	}
}
