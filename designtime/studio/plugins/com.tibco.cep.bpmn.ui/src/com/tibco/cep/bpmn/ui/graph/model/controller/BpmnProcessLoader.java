package com.tibco.cep.bpmn.ui.graph.model.controller;

import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.ACTIVITY;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.AD_HOC_SUB_PROCESS;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.BOUNDARY_EVENT;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.BUSINESS_RULE_TASK;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.CATCH_EVENT;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.COMPLEX_GATEWAY;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.INFERENCE_TASK;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.END_EVENT;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.ERROR_EVENT_DEFINITION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.EVENT;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.EVENT_BASED_GATEWAY;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.EXCLUSIVE_GATEWAY;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.GATEWAY;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.INCLUSIVE_GATEWAY;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.INTERMEDIATE_CATCH_EVENT;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.INTERMEDIATE_THROW_EVENT;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.MANUAL_TASK;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.MESSAGE_EVENT_DEFINITION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.PARALLEL_GATEWAY;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.RECEIVE_TASK;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.JAVA_TASK;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.RULE_FUNCTION_TASK;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.SEND_TASK;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.SEQUENCE_FLOW;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.SERVICE_TASK;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.SIGNAL_EVENT_DEFINITION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.START_EVENT;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.SUB_PROCESS;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.TEXT_ANNOTATION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.THROW_EVENT;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.TIMER_EVENT_DEFINITION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.TRANSACTION;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.core.utils.ECoreHelper;
import com.tibco.cep.bpmn.core.web.index.WebBpmnModelManager;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EEnumWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.model.AbstractEdgeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.AbstractNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.BpmnGraphUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.events.AbstractConnectorUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.process.PoolLaneNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.process.activity.SubProcessNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.sequence.SequenceFlowEdgeFactory;
import com.tibco.cep.diagramming.drawing.IDiagramModelAdapter;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.complexity.TSENestingManager;
import com.tomsawyer.service.layout.TSLayoutConstants;

/**
 * 
 * This is helper class, helps to load process to graph from emf model
 * 
 * @author majha
 *
 */
public class BpmnProcessLoader {
	
	private ModelGraphNodeRegistry<TSEObject> nodeRegistry ;
	private Map<TSEGraph,TSENode> startNodes ;
	private Map<TSEGraph,List<TSENode>> endNodeLists;
	private BpmnLayoutManager layoutManager;
	private TSEGraphManager graphManager;
	private boolean changeMainDisplayGraph;
	private boolean showPoolLaneNode;
	private TSEGraph rootGraph;
	private IDiagramModelAdapter modelAdapter;

	private AdapterFactory modelChangeAdapterFactory;

	public BpmnProcessLoader(TSEGraphManager gManager, BpmnLayoutManager lManager){
		this(gManager, lManager, null);
	}

	public BpmnProcessLoader(TSEGraphManager gManager, BpmnLayoutManager lManager, AdapterFactory mAdaptorfactory) {
		this(gManager, lManager, mAdaptorfactory, null);
	}

	public BpmnProcessLoader(TSEGraphManager gManager, BpmnLayoutManager lManager, AdapterFactory mAdaptorfactory, IDiagramModelAdapter adapter){
		modelChangeAdapterFactory = mAdaptorfactory;
		nodeRegistry = new ModelGraphNodeRegistry<TSEObject>();
		startNodes = new HashMap<TSEGraph,TSENode>();
		endNodeLists = new HashMap<TSEGraph,List<TSENode>>();
		layoutManager = lManager;
		graphManager = gManager;
		changeMainDisplayGraph = true;
		showPoolLaneNode = true;
		// this.modelAdapter = adapter;
		this.modelAdapter = layoutManager.getDiagramManager().getDiagramModelAdapter();
	}
	

	/**
	 * @return
	 */
	protected TSEGraphManager getGraphManager() {
		return graphManager;
	}
	
	/**
	 * @return
	 */
	protected BpmnLayoutManager getLayoutManager() {
		return layoutManager;
	}
	

	
	/**
	 * @return
	 */
	public ModelGraphNodeRegistry<TSEObject> getNodeRegistry() {
		return nodeRegistry;
	}
	/**
	 * @param <T>
	 * @param guid
	 * @param object
	 * @return
	 */
	protected <T extends TSEObject> boolean addNodeToCache(String guid, T object) {
		return nodeRegistry.addTSObject(guid, object);
	}
	
	/**
	 * 
	 */
	public void clearCache() {
		nodeRegistry.clearCache();
	}
	
	/**
	 * @param graph
	 * @return
	 */
	public TSENode getStartNode(TSEGraph graph) {
		return (TSENode) this.startNodes.get(graph);
	}
	
	protected void registerForModelchange(Notifier model){
		if (modelChangeAdapterFactory != null)
			modelChangeAdapterFactory.adapt(model,
					ModelChangeListener.class);
	}
	
	protected TSEGraph resetDirection(TSEGraph graph) {
		Iterator<TSEGraph> iter = this.startNodes.keySet().iterator();
		TSENode startNode;
		List<TSENode> endNodes;
		List<TSENode> tempList;
		while (iter.hasNext()) {
			graph = (TSEGraph) iter.next();
			startNode = this.startNodes.get(graph);
			endNodes =  this.endNodeLists.get(graph);
			if (startNode != null && endNodes != null && endNodes.size() > 0) {
				tempList = new LinkedList<TSENode>();
				tempList.add(startNode);
				tempList.addAll(endNodes);
				(this.getLayoutManager()).addSequenceConstraint(
						tempList, TSLayoutConstants.DIRECTION_LEFT_TO_RIGHT);
			}
		}
		return graph;
	} 
	
	
	/**
	 * this is specifically written for process loading through webstudio
	 * later on some abstraction to be provided
	 * @param projDir
	 * @param projectName
	 * @param projRelpath
	 * @return
	 * @throws Exception
	 */
	public  TSEGraph loadProcess(String projDir, String projectName, String projRelpath) throws Exception {
		WebBpmnModelManager webBpmnModelManager = new WebBpmnModelManager();
		EObject loadEObject = webBpmnModelManager.loadProcess(projDir, projectName, projRelpath);
		EObjectWrapper<EClass, EObject> process = EObjectWrapper.wrap(loadEObject);
		TSEGraph processGraph = loadProcess(process, null, new NullProgressMonitor());
		return processGraph;
	}
	
	
	public  TSEGraph loadProcess(IResource file) throws Exception {
		EList<EObject> resources = ECoreHelper.deserializeModelXMI(file, true);
		EObjectWrapper<EClass, EObject> process = EObjectWrapper.wrap(resources.get(0));
		TSEGraph processGraph = loadProcess(process, null, new NullProgressMonitor());
		return processGraph;
	}
	
	/**
	 * create the process
	 * @param subProgressMonitor 
	 * @return 
	 * @return
	 */
	public  TSEGraph loadProcess(EObjectWrapper<EClass, EObject> process,TSGraph tsGraph,IProgressMonitor pm) {		
		try {
			pm.beginTask(Messages.getString(BpmnUIConstants.BPMN_MODEL_LOAD_PROCESS), 100);
			registerForModelchange(process);
			rootGraph = (TSEGraph) tsGraph;
			if(tsGraph == null) {
				rootGraph = (TSEGraph) getGraphManager().getMainDisplayGraph();
			} 
			rootGraph.setUserObject(process.getEInstance());
			rootGraph.setAttribute(BpmnUIConstants.NODE_ATTR_NAME, process.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME));
			// GGG?
			// graph.setAttribute(BpmnUIConstants.NODE_ATTR_LABEL, process.getAttribute(BpmnMetaModelConstants.E_ATTR_LABEL));
			rootGraph.setAttribute(BpmnUIConstants.NODE_ATTR_TYPE, process.getEClassType());
			rootGraph.setAttribute(BpmnUIConstants.NODE_ATTR_TASK_DESCRIPTION, getDescription(process.getEInstance()));
			String id = process.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			addNodeToCache(id, rootGraph);
			EList<EObject>laneSets = process.getListAttribute(BpmnMetaModelConstants.E_ATTR_LANE_SETS);
			pm.subTask(Messages.getString(BpmnUIConstants.BPMN_MODEL_LOAD_POOL_LANES));
			
			for(EObject laneSetObj:laneSets) {
				EObjectWrapper<EClass, EObject> laneSet = EObjectWrapper.wrap(laneSetObj);
				registerForModelchange(laneSet);
				EList<EObject> lanes = laneSet.getListAttribute(BpmnMetaModelConstants.E_ATTR_LANES);
				for(EObject lane :lanes) {
					loadPoolLane(rootGraph,rootGraph,
									EObjectWrapper.wrap(lane),
									true,showPoolLaneNode,
									new SubProgressMonitor(pm,90));
				}
			}
			pm.worked(90);
			
			//resetDirection(graph);
			
			return rootGraph;
		} finally {
			pm.done();
		}
	}
	
	/**
	 * this for process which is not loaded though this class(e.g new process)
	 */
	public void buildNodeRegistry(){
		rootGraph = (TSEGraph)((TSEGraph) getGraphManager().getMainDisplayGraph()).getGreatestAncestor();
		EObject userObject = (EObject)rootGraph.getUserObject();
		if(userObject != null){
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap((EObject)userObject);
			String id = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			addNodeToCache(id, rootGraph);	
		}
		buildNodeRegistry(rootGraph);
	}

	@SuppressWarnings("rawtypes")
	private void buildNodeRegistry(TSGraph graph){
		List edgeSet = graph.edgeSet;
		for (Object obj : edgeSet) {
			TSEEdge edge = (TSEEdge) obj;
			Object userObject = edge.getUserObject();
			if(userObject != null){
				EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap((EObject)userObject);
				String id = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				addNodeToCache(id, edge);
			}
		}
		List nodeSet = graph.nodeSet;
		for (Object obj : nodeSet) {
			TSENode node = (TSENode) obj;
			Object userObject = node.getUserObject();
			if(userObject != null){
				EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap((EObject)userObject);
				String id = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				addNodeToCache(id, node);
			}
			List connectors = node.connectors();
			for (Object object : connectors) {
				if (object instanceof TSEConnector) {
					TSEConnector connec = (TSEConnector) object;
					userObject = connec.getUserObject();
					if (userObject != null && (userObject instanceof EObject)) {
						EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
								.wrap((EObject) userObject);
						String id = wrap
								.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
						addNodeToCache(id, node);
					}
				}
			}
			TSGraph childGraph = ((TSENode)node).getChildGraph();
			if(childGraph != null)
				buildNodeRegistry(childGraph);
		}
	}
	
	
	public void reLoadProcess(EObjectWrapper<EClass, EObject> process) {
		if (process == null)
			return;
		if(rootGraph != null) {
			rootGraph.setUserObject(process.getEInstance());
		}else{
			rootGraph = (TSEGraph)((TSEGraph) getGraphManager().getMainDisplayGraph()).getGreatestAncestor();
			rootGraph.setUserObject(process.getEInstance());
		}

		String idProc = process.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
		TSEObject prcessGraph = getNodeRegistry().getNode(idProc);
		if(prcessGraph!= null)
			prcessGraph.setUserObject(process.getEInstance());
		
		registerForModelchange(process);
		
		List<EObject> allBaseElements = BpmnModelUtils
				.getAllBaseElements(process.getEInstance());
		for (EObject object : allBaseElements) {
			EObjectWrapper<EClass, EObject> objWrapper = EObjectWrapper
					.wrap(object);
			registerForModelchange(objWrapper);
			if (objWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_ID)) {
				String id = objWrapper
						.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				if(id != null && id.equals(idProc))
					continue;
				TSEObject graphObj = getNodeRegistry().getNode(id);
				if (graphObj != null) {
					graphObj.setUserObject(object);
					if (graphObj instanceof TSENode) {
						TSGraph childGraph = ((TSENode) graphObj).getChildGraph();
						if (childGraph != null)
							childGraph.setUserObject(object);
					}
					addNodeToCache(id, graphObj);
				}
			}
		}
	}
	
	private void loadPoolLane(TSEGraph processGraph, TSEGraph parentGraph,
			EObjectWrapper<EClass, EObject> parentLane, boolean isPool,boolean showPool,
			IProgressMonitor pm) {
		try {
			pm.beginTask(Messages
					.getString(BpmnUIConstants.BPMN_MODEL_LOAD_POOL), 40);
			pm.subTask(Messages.format(
					BpmnUIConstants.BPMN_MODEL_LOAD_POOL_NAME, (String) parentLane.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME)));
			final EObjectWrapper<EClass, EObject> childLaneSet = parentLane
					.getWrappedEObjectAttribute(BpmnMetaModelConstants.E_ATTR_CHILD_LANE_SET);
			registerForModelchange(childLaneSet);
			final EList<EObject> childLanes = childLaneSet.getListAttribute(BpmnMetaModelConstants.E_ATTR_LANES);
			TSEGraph childLaneGraph = parentGraph;
			if (showPool) {
				String id = parentLane.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				String name = parentLane.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
				String description = getDescription(parentLane.getEInstance());
				
				String toolId = (String) ExtensionHelper.getExtensionAttributeValue(parentLane,BpmnMetaModelExtensionConstants.E_ATTR_TOOL_ID);
				String resUrl = getAttachedResource(parentLane);
				
				// XYZ
				// TSENode parentLaneNode = (TSENode) parentGraph.addNode();
				TSENode parentLaneNode = this.modelAdapter.addNode(parentGraph);
					
				addNodeToCache(id,parentLaneNode);

				registerForModelchange(parentLane);
				parentLaneNode.setName(name);
				parentLaneNode.setUserObject(parentLane.getEInstance());

				// Lane/Pool node should contain the following the information
				
				parentLaneNode.setAttribute(BpmnUIConstants.NODE_ATTR_NAME,name);

				parentLaneNode.setAttribute(BpmnUIConstants.NODE_ATTR_TYPE,
						parentLane.getEClassType());
				parentLaneNode.setAttribute(BpmnUIConstants.NODE_ATTR_TOOL_ID,
						toolId);
				parentLaneNode.setAttribute(BpmnUIConstants.NODE_ATTR_TASK_DESCRIPTION,description );
				parentLaneNode.setAttribute(BpmnUIConstants.NODE_ATTR_BE_RESOURCE_URL,resUrl );

				// get lane children

				// XYZ
				// childLaneGraph = (TSEGraph) getGraphManager().addGraph(); // graph
				childLaneGraph = this.modelAdapter.addGraph();

				if (isPool && changeMainDisplayGraph) { // if it is the first
														// process laneset graph
														// then
					getGraphManager().setMainDisplayGraph(childLaneGraph);
				}
				childLaneGraph.setUserObject(parentLane.getEInstance());

				getLayoutManager().setLayoutOptionsForSubProcess(childLaneGraph);

				parentLaneNode.setChildGraph(childLaneGraph);
				getLayoutManager().setLayoutOptionsForPool(parentLaneNode);
				
				if (childLaneGraph.nodeSet.isEmpty())
					extendGraph(parentLaneNode, childLaneGraph);

				// set ui at the end
				PoolLaneNodeUIFactory uiFactory = (PoolLaneNodeUIFactory) BpmnGraphUIFactory
						.getInstance((BpmnLayoutManager) getLayoutManager())
						.getNodeUIFactory(
								(String) parentLane
										.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME),resUrl,
								toolId, BpmnMetaModel.LANE,
								isPool ? BpmnMetaModel.PROCESS: null );
				uiFactory.decorateNode(parentLaneNode);
				uiFactory.layoutNode(parentLaneNode);
				if (ExtensionHelper.isValidDataExtensionAttribute(
						parentLane,
						BpmnMetaModelExtensionConstants.E_ATTR_LABEL)){
					EObjectWrapper<EClass, EObject> valWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(parentLane);
					if (valWrapper != null) {
						String label = valWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LABEL);
						uiFactory.addNodeLabel(parentLaneNode, label);
					}
				}
				
				TSENestingManager.expand(parentLaneNode);
			}

			pm.worked(10);


			// lane can have childlanes as well as artifact
			for (EObject childLane : childLanes) {
				loadPoolLane(processGraph, childLaneGraph, EObjectWrapper
						.wrap(childLane), false, true,
						new SubProgressMonitor(pm, 10));
			}

			EObjectWrapper<EClass, EObject> process = EObjectWrapper
					.wrap((EObject) processGraph.getUserObject());

			Collection<EObject> flowNodes = BpmnModelUtils.getFlowNodes(
					process, parentLane);
			loadFlowNodes(processGraph, (TSEGraph) childLaneGraph, flowNodes,
					new SubProgressMonitor(pm, 10));

			Collection<EObject> sequenceFlows = BpmnModelUtils
					.getSequenceFlows(process, parentLane);
			loadSequenceFlows(processGraph, (TSEGraph) childLaneGraph,
					sequenceFlows, new SubProgressMonitor(pm, 10));

			Collection<EObject> artifactNodes = BpmnModelUtils
					.getArtifactNodes(process);
			loadArtifactNodes(processGraph, (TSEGraph) childLaneGraph,
					artifactNodes, new SubProgressMonitor(pm, 10));

			Collection<EObject> associations = BpmnModelUtils
					.getAssociations(process);
			loadAssociations(processGraph, (TSEGraph) childLaneGraph,
					associations, new SubProgressMonitor(pm, 10));
			
			for (EObject eObject : flowNodes) {
				if(eObject.eClass().equals(BpmnModelClass.EXCLUSIVE_GATEWAY)|| eObject.eClass().equals(BpmnModelClass.INCLUSIVE_GATEWAY)){
					String guid = (String) EObjectWrapper.wrap(eObject).getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
					TSEObject node = nodeRegistry.getNode(guid);
					if(node != null && node instanceof TSENode){
						SequenceFlowEdgeFactory.refreshEdgeUiAttachededToGateway((TSENode)node, null);
					}
				}
			}


		} finally {
			pm.done();
		}

	}
	
	private void extendGraph(TSENode laneNode, TSEGraph laneGraph) {
		//TODO , following code seems  having bug or not giving the desire result
//		TSEGraph childGraph = laneGraph;
//		double childLeft = laneNode.getLeft();
//		double childTop = laneNode.getTop();
//		double DEFAULT_BREAKPOINT_OFFSET = 1000;
//		// NW node
//		addDummyNode(childGraph, childLeft - DEFAULT_BREAKPOINT_OFFSET, childTop + DEFAULT_BREAKPOINT_OFFSET);
//		// NE node
//		addDummyNode(childGraph, childLeft + DEFAULT_BREAKPOINT_OFFSET, childTop + DEFAULT_BREAKPOINT_OFFSET);
//		// SW node
//		addDummyNode(childGraph, childLeft - DEFAULT_BREAKPOINT_OFFSET, childTop - DEFAULT_BREAKPOINT_OFFSET);
//		// SE node
//		addDummyNode(childGraph, childLeft + DEFAULT_BREAKPOINT_OFFSET, childTop - DEFAULT_BREAKPOINT_OFFSET);

//		getGraphDiagramManager().refreshNode(laneNode);
	}
	
	@SuppressWarnings("unused")
	private void addDummyNode(TSEGraph childGraph, double x, double y) {
		// XYZ
		// TSENode node = (TSENode) childGraph.addNode();
		TSENode node = this.modelAdapter.addNode(childGraph);
		
		node.setVisible(false);
		node.setSize(1.0, 1.0);
		node.setCenterX(x);
		node.setCenterY(y);
	}


	/**
	 * @param childGraph
	 * @param flowNodes
	 * @param pm 
	 */
	private void loadFlowNodes(TSEGraph processGraph, TSEGraph parentGraph,
			Collection<EObject> flowNodes, IProgressMonitor pm) {
		try {
			pm.beginTask(BpmnUIConstants.BPMN_MODEL_LOAD_FLOW_NODES, flowNodes.size());
			for(EObject flowNode: flowNodes) {
				pm.subTask(
						MessageFormat.format(
								BpmnUIConstants.BPMN_MODEL_LOAD_FLOW_NODE,
								(String) EObjectWrapper.wrap(flowNode).getAttribute(BpmnMetaModelConstants.E_ATTR_NAME)));
				final EObjectWrapper<EClass, EObject> eFlowNode = EObjectWrapper.wrap(flowNode);
				registerForModelchange(eFlowNode);
				final TSENode tsNode = loadFlowNode(processGraph,parentGraph,flowNode, pm);
				if(tsNode != null)
					tsNode.setAttribute(BpmnUIConstants.NODE_ATTR_TASK_DESCRIPTION, getDescription(flowNode));
				final String guid = (String) eFlowNode.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				if (getNodeRegistry().containsKey(guid)) {
					BpmnUIPlugin.logErrorMessage(
							MessageFormat.format("[{0}] Duplicate GUID in BPMN diagram {1}",
									BpmnMetaModel.INSTANCE.getExpandedName(flowNode.eClass()),guid));
				} else {
					if (tsNode != null)
						addNodeToCache(guid, tsNode);
				}
				pm.worked(1);
			}
		} finally {
			pm.done();
		}
		
	}
	
	
	/**
	 * @param childGraph
	 * @param flowNodes
	 * @param pm 
	 */
	private void loadArtifactNodes(TSEGraph processGraph, TSEGraph parentGraph,
			Collection<EObject> artifactNodes, IProgressMonitor pm) {
		try {
			pm.beginTask(BpmnUIConstants.BPMN_MODEL_LOAD_ARTIFACT_NODES, artifactNodes.size());
			for(EObject atrifact: artifactNodes) {
				pm.subTask(
						MessageFormat.format(
								BpmnUIConstants.BPMN_MODEL_LOAD_ARTIFACT_NODE,Messages.getString("title.textannotation")));
				final EObjectWrapper<EClass, EObject> eFlowNode = EObjectWrapper.wrap(atrifact);
				registerForModelchange(eFlowNode);
				final TSENode tsNode = loadArtifactNode(processGraph, parentGraph,atrifact);
				if(tsNode != null)
					tsNode.setAttribute(BpmnUIConstants.NODE_ATTR_TASK_DESCRIPTION, getDescription(atrifact));
				final String guid = (String) eFlowNode.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				if (getNodeRegistry().containsKey(guid)) {
					BpmnUIPlugin.logErrorMessage(
							MessageFormat.format("[{0}] Duplicate GUID in BPMN diagram {1}",
									BpmnMetaModel.INSTANCE.getExpandedName(atrifact.eClass()),guid));
				} else {
					addNodeToCache(guid, tsNode);
				}
				pm.worked(1);
			}
		} catch (Exception e){
		   BpmnUIPlugin.log(e);
		} finally {
			pm.done();
		}
		
	}
	

	/**
	 * @param parentGraph
	 * @param flowNode
	 */
	private TSENode loadFlowNode(TSEGraph processGraph, TSEGraph parentGraph, EObject flowNode, IProgressMonitor pm) {
		EClass type = flowNode.eClass();
		if(ACTIVITY.isSuperTypeOf(type)) {
			if(SUB_PROCESS.isSuperTypeOf(type)){
				if(TRANSACTION.isSuperTypeOf(type)) {
					//createTransaction(parentGraph,flowNode);
				} else if(AD_HOC_SUB_PROCESS.isSuperTypeOf(type)) {
					//createAdHocSubProcess(parentGraph,flowNode);
				} else {
					return loadSubProcess(processGraph, parentGraph,flowNode, pm);
				}
			} else if(ACTIVITY.isSuperTypeOf(type)) {
				return loadTask(processGraph, parentGraph,flowNode);
			}
		} else if(EVENT.isSuperTypeOf(type)) {
			return loadEvent(processGraph,parentGraph,flowNode);
		} else if(GATEWAY.isSuperTypeOf(type)) {
			return loadGateway(processGraph,parentGraph,flowNode);
		} 
		return null;
	}
	
	private TSENode loadArtifactNode(TSEGraph processGraph, TSEGraph parentGraph, EObject artifactNode) {
		EClass type = artifactNode.eClass();
		if(TEXT_ANNOTATION.equals(type)) {
			return loadTextAnnotation(processGraph, parentGraph,artifactNode);
		}
		return null;
	}
	

	/**
	 * @param childGraph
	 * @param seqFlows
	 * @param pm 
	 */
	private void loadSequenceFlows(TSEGraph processGraph, TSEGraph parentGraph,
			Collection<EObject> seqFlows, IProgressMonitor pm) {
		try {
			pm.beginTask(BpmnUIConstants.BPMN_MODEL_LOAD_SEQUENCE_FLOWS, seqFlows.size());
			for(EObject seqFlow: seqFlows) {
				pm.subTask(
						MessageFormat.format(
								BpmnUIConstants.BPMN_MODEL_LOAD_SEQUENCE_FLOW,
								(String) EObjectWrapper.wrap(seqFlow).getAttribute(BpmnMetaModelConstants.E_ATTR_NAME)));
				createSequenceFlow(processGraph,parentGraph,seqFlow);
				pm.worked(1);
			}
		} finally {
			pm.done();
		}
		
	}
	
	private void loadAssociations(TSEGraph processGraph, TSEGraph parentGraph,
			Collection<EObject> seqFlows, IProgressMonitor pm) {
		try {
			pm.beginTask(BpmnUIConstants.BPMN_MODEL_LOAD_ASSOCIATIONS, seqFlows.size());
			for(EObject seqFlow: seqFlows) {
				pm.subTask(
						MessageFormat.format(
								BpmnUIConstants.BPMN_MODEL_LOAD_ASSOCIATION,Messages.getString("title.association")));
				createAssociation(processGraph,parentGraph,seqFlow);
				pm.worked(1);
			}
		} finally {
			pm.done();
		}
		
	}

	

	private TSENode loadSubProcess(TSEGraph processGraph, TSEGraph parentGraph, EObject flowNode, IProgressMonitor pm) {
		TSENode subProcess = loadTask(processGraph,parentGraph, flowNode);
		EObjectWrapper<EClass, EObject> subProcessWrap 
			= EObjectWrapper.wrap((EObject)subProcess.getUserObject());
		
		// XYZ
		// TSEGraph childGraph = (TSEGraph) getGraphManager().addGraph(); // graph to add child lanes
		TSEGraph childGraph = this.modelAdapter.addGraph();
		

		childGraph.setUserObject(subProcessWrap.getEInstance());
		
		getLayoutManager().setLayoutOptionsForSubProcess(childGraph);
		
		subProcess.setChildGraph(childGraph);
		
		expandSubprocess(subProcessWrap, subProcess);
		
		Collection<EObject> flowNodes = BpmnModelUtils.getFlowNodes(subProcessWrap);
		loadFlowNodes(processGraph,childGraph,flowNodes,new SubProgressMonitor(pm,10));
		
		Collection<EObject> sequenceFlows = BpmnModelUtils.getSequenceFlowsInSubprocess(subProcessWrap);
		loadSequenceFlows(processGraph,childGraph,sequenceFlows,new SubProgressMonitor(pm,10));
		
		Collection<EObject> artifactNodes = BpmnModelUtils.getArtifactNodes(subProcessWrap);
		loadArtifactNodes(processGraph,childGraph,artifactNodes,new SubProgressMonitor(pm,10));
		
		Collection<EObject> associations = BpmnModelUtils.getAssociations(subProcessWrap);
		loadAssociations(processGraph,childGraph,associations,new SubProgressMonitor(pm,10));
		
		for (EObject eObject : flowNodes) {
			if(eObject.eClass().equals(BpmnModelClass.EXCLUSIVE_GATEWAY)|| eObject.eClass().equals(BpmnModelClass.INCLUSIVE_GATEWAY)){
				String guid = (String) EObjectWrapper.wrap(eObject).getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				TSEObject node = nodeRegistry.getNode(guid);
				if(node != null && node instanceof TSENode){
					SequenceFlowEdgeFactory.refreshEdgeUiAttachededToGateway((TSENode)node, null);
				}
			}
		}
		
		return subProcess;
	}
	
	private void expandSubprocess(
			EObjectWrapper<EClass, EObject> subProcessWrap, TSENode subProcess) {
		boolean set = false;
		EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper
				.getAddDataExtensionValueWrapper(subProcessWrap);
		if (addDataExtensionValueWrapper != null
				&& addDataExtensionValueWrapper
						.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EXPANDED)) {
			set = true;
			Boolean expanded = addDataExtensionValueWrapper
					.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_EXPANDED);
			String nodeName = (String) subProcess
					.getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
			String toolId = (String) subProcess
					.getAttributeValue(BpmnUIConstants.NODE_ATTR_TOOL_ID);
			String resUrl = (String) subProcess
					.getAttributeValue(BpmnUIConstants.NODE_ATTR_BE_RESOURCE_URL);
			SubProcessNodeUIFactory uiFactory = (SubProcessNodeUIFactory) BpmnGraphUIFactory
					.getInstance(getLayoutManager()).getNodeUIFactory(nodeName,
							resUrl, toolId, BpmnMetaModel.SUB_PROCESS);
			if (expanded) {
				uiFactory.setExpanded(true, subProcess);
			} else {
				TSENestingManager.collapse(subProcess);
				uiFactory.setExpanded(false, subProcess);
				subProcess.setUI(uiFactory.getNodeUI(false));
				subProcess.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
				subProcess.setSize(70, 50);
			}

			if (addDataExtensionValueWrapper
					.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LENGTH)
					&& addDataExtensionValueWrapper
							.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_BREADTH)) {
				Double length = addDataExtensionValueWrapper
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LENGTH);
				Double breadth = addDataExtensionValueWrapper
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_BREADTH);
				if (length != null && breadth != null) {
					subProcess.setSize(length, breadth);
					set = true;
				}
			}
		}

		if (!set)
			TSENestingManager.expand(subProcess);
	}

	/**
	 * @param graph
	 * @param flowNode
	 * @return
	 */
	private TSENode loadTask(TSEGraph processGraph, TSEGraph parentGraph, EObject flowNode) {
		EClass type = flowNode.eClass();
		EObject laneObj = (EObject) parentGraph.getUserObject();
		EObjectWrapper<EClass, EObject> lane = EObjectWrapper.wrap(laneObj);
		@SuppressWarnings("unused")
		EObjectWrapper<EClass, EObject> process  = null;
		
		if(BpmnModelClass.SUB_PROCESS.isSuperTypeOf(lane.getEClassType())){
			process = lane;
		}
		else{
			EObject processObj = (EObject) processGraph.getUserObject();
			process = EObjectWrapper.wrap(processObj);
		}
		
		final EObjectWrapper<EClass, EObject> taskWrapper = EObjectWrapper.wrap(flowNode);
		String toolId = (String)ExtensionHelper.getExtensionAttributeValue(taskWrapper, BpmnMetaModelExtensionConstants.E_ATTR_TOOL_ID);
		String attachedResource = getAttachedResource(taskWrapper);
		AbstractNodeUIFactory uiFactory = null;
		if(taskWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_TRIGGERED_BY_EVENT)){
			boolean trigByevent = (Boolean)taskWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TRIGGERED_BY_EVENT);
			uiFactory = BpmnGraphUIFactory.getInstance(getLayoutManager())
				.getNodeUIFactory((String)taskWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME),attachedResource,
						toolId,
						type, trigByevent);	
		}else{
			uiFactory = BpmnGraphUIFactory.getInstance(getLayoutManager())
			.getNodeUIFactory((String)taskWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME),attachedResource,
					toolId,
					type);
		}

		// XYZ
		TSENode tsNode = uiFactory.addNode(parentGraph);
		if(taskWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_NAME)){
			String label = taskWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			if (label != null && !label.trim().isEmpty())
				uiFactory.addNodeLabel(tsNode, label);
		}
		EObject loopCharacteristics = (EObject)taskWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_LOOP_CHARACTERISTICS);
		if(loopCharacteristics != null){
			EObjectWrapper<EClass, EObject> loopWrapper = EObjectWrapper.wrap(loopCharacteristics);
			if(loopWrapper.getEClassType().equals(BpmnModelClass.MULTI_INSTANCE_LOOP_CHARACTERISTICS))
				tsNode.setAttribute(BpmnUIConstants.NODE_ATTR_TASK_MODE, BpmnUIConstants.NODE_ATTR_TASK_MODE_MULTIPLE);	
			else if(loopWrapper.getEClassType().equals(BpmnModelClass.STANDARD_LOOP_CHARACTERISTICS))
				tsNode.setAttribute(BpmnUIConstants.NODE_ATTR_TASK_MODE, BpmnUIConstants.NODE_ATTR_TASK_MODE_LOOP);	
		}
		
		if (taskWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_IS_FOR_COMPENSATION)) {
			boolean compensation = (Boolean)taskWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_IS_FOR_COMPENSATION);
			 tsNode.setAttribute(BpmnUIConstants.NODE_ATTR_TASK_MODE_COMPENSATION, compensation);
		}		
		
		
		tsNode.setUserObject(taskWrapper.getEInstance());
		uiFactory.updateNodeToolTip(tsNode);
		// System.out.println("while loading, set task user object to: " + taskWrapper.getEInstance());
		if(JAVA_TASK.isSuperTypeOf(type)){
		} else if(RULE_FUNCTION_TASK.isSuperTypeOf(type)){
		} else if(SEND_TASK.isSuperTypeOf(type)) {
		} else if(RECEIVE_TASK.isSuperTypeOf(type)) {
		} else if(MANUAL_TASK.isSuperTypeOf(type)) {
		} else if(INFERENCE_TASK.isSuperTypeOf(type)) {
		} else if(SERVICE_TASK.isSuperTypeOf(type)) {
		} else if(BUSINESS_RULE_TASK.isSuperTypeOf(type)) {
		}
		
		EList<EObject> listAttribute = taskWrapper.getListAttribute((BpmnMetaModelConstants.E_ATTR_BOUNDARY_EVENT_REFS));
		for (EObject object : listAttribute) {
			loadBoundaryEvent(tsNode, object);
		}
		return tsNode;
	}

	/**
	 * @param graph
	 * @param flowNode
	 * @return
	 */
	private TSENode loadEvent(TSEGraph processGraph, TSEGraph parentGraph, EObject flowNode) {
		TSENode tsNode = null;
		EClass extType = null;
		EClass type = flowNode.eClass();
		EList<EObject> eventDefinitions = EObjectWrapper.wrap(flowNode).getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
		if(eventDefinitions.size() == 0 )
			eventDefinitions = EObjectWrapper.wrap(flowNode).getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITION_REFS);
		EObject eDef = eventDefinitions.size()>  0 ? eventDefinitions.get(0): null;
		if(eDef != null ){
			registerForModelchange(eDef);
			extType = eDef.eClass();
		}
		if(BOUNDARY_EVENT.isSuperTypeOf(type))
			return tsNode;
		
		if(CATCH_EVENT.isSuperTypeOf(type)) {
			if(START_EVENT.isSuperTypeOf(type)) {
				if(extType != null) {
					if(MESSAGE_EVENT_DEFINITION.isSuperTypeOf(extType)||
							TIMER_EVENT_DEFINITION.isSuperTypeOf(extType)||
							SIGNAL_EVENT_DEFINITION.isSuperTypeOf(extType)){
						tsNode = loadEvent(processGraph,parentGraph, flowNode, type, extType);
					} 
				} else { // default
					tsNode = loadEvent(processGraph,parentGraph, flowNode, type, null);
				}
				this.startNodes.put(parentGraph, tsNode);
				
			} else if(INTERMEDIATE_CATCH_EVENT.isSuperTypeOf(type)) {
				if(extType != null) {
					if(MESSAGE_EVENT_DEFINITION.isSuperTypeOf(extType)||
							ERROR_EVENT_DEFINITION.isSuperTypeOf(extType)||
							TIMER_EVENT_DEFINITION.isSuperTypeOf(extType)||
							SIGNAL_EVENT_DEFINITION.isSuperTypeOf(extType)){
						tsNode = loadEvent(processGraph,parentGraph, flowNode, type, extType);
					}						
				} else { // default
					tsNode = loadEvent(processGraph,parentGraph, flowNode, type, MESSAGE_EVENT_DEFINITION);
				}
			}
			
		} else if( THROW_EVENT.isSuperTypeOf(type)) {
			if(END_EVENT.isSuperTypeOf(type)) {
				if(extType != null) {
					if(MESSAGE_EVENT_DEFINITION.isSuperTypeOf(extType)||
							ERROR_EVENT_DEFINITION.isSuperTypeOf(extType)||
							SIGNAL_EVENT_DEFINITION.isSuperTypeOf(extType)){
						tsNode = loadEvent(processGraph,parentGraph, flowNode, type, extType);
					}					
				} else {//default
					tsNode = loadEvent(processGraph,parentGraph, flowNode, type, null);
				}
				
				List<TSENode> endList = (List<TSENode>) this.endNodeLists.get(parentGraph);
				if (endList == null) {
					endList = new LinkedList<TSENode>();
				}
				endList.add(tsNode);
				this.endNodeLists.put(parentGraph, endList);
				
			} else if(INTERMEDIATE_THROW_EVENT.isSuperTypeOf(type)){
				if(extType != null) {
					if(MESSAGE_EVENT_DEFINITION.isSuperTypeOf(extType)||
							SIGNAL_EVENT_DEFINITION.isSuperTypeOf(extType)){
						tsNode = loadEvent(processGraph,parentGraph, flowNode, type, extType);
					} 						
				} else { // default
					tsNode = loadEvent(processGraph,parentGraph, flowNode, type, MESSAGE_EVENT_DEFINITION);
				}
			}
		}
		
		return tsNode;
	}

	/**
	 * @param graph
	 * @param flowNode
	 * @param type
	 * @param extType
	 * @return
	 */
	private TSENode loadEvent(TSEGraph processGraph, TSEGraph parentGraph, EObject flowNode, EClass type,
			EClass extType) {
		EObject laneObj = (EObject) parentGraph.getUserObject();
		EObjectWrapper<EClass, EObject> lane = EObjectWrapper.wrap(laneObj);
		@SuppressWarnings("unused")
		EObjectWrapper<EClass, EObject> process  = null;
		
		if(BpmnModelClass.SUB_PROCESS.isSuperTypeOf(lane.getEClassType())){
			process = lane;
		}
		else{
			EObject processObj = (EObject) processGraph.getUserObject();
			process = EObjectWrapper.wrap(processObj);
		}
		
		EObjectWrapper<EClass, EObject> eventWrapper = EObjectWrapper.wrap(flowNode);
		String toolId = (String)ExtensionHelper.getExtensionAttributeValue(eventWrapper, BpmnMetaModelExtensionConstants.E_ATTR_TOOL_ID);
		String attachedResource = getAttachedResource(eventWrapper);
		AbstractNodeUIFactory uiFactory = BpmnGraphUIFactory
		.getInstance( getLayoutManager())
		.getNodeUIFactory((String)eventWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME),attachedResource,
				toolId,
				type, 
				BpmnMetaModel.INSTANCE.getExpandedName(extType));
		// XYZ
		TSENode tsNode = uiFactory.addNode(parentGraph);
		if(eventWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_NAME)){
			String label = eventWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			if (label != null && !label.trim().isEmpty())
				uiFactory.addNodeLabel(tsNode, label);
		}
			
		tsNode.setUserObject(eventWrapper.getEInstance());
		uiFactory.updateNodeToolTip(tsNode);
		return tsNode;
	}
	
	
	private void loadBoundaryEvent(TSENode parentNode, EObject boundaryEvent){
		EClass extType = null;
		EClass type = boundaryEvent.eClass();
		EObjectWrapper<EClass, EObject> boundaryEventWrapper = EObjectWrapper.wrap(boundaryEvent);
		EList<EObject> eventDefinitions = EObjectWrapper.wrap(boundaryEvent).getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
		if(eventDefinitions.size() == 0 )
			eventDefinitions = EObjectWrapper.wrap(boundaryEvent).getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITION_REFS);
		EObject eDef = eventDefinitions.size()>  0 ? eventDefinitions.get(0): null;
		if(eDef != null ){
			registerForModelchange(eDef);
			extType = eDef.eClass();
			ExpandedName classSpec =  BpmnMetaModel.INSTANCE.getExpandedName(extType);
			String attachedResource = getAttachedResource(boundaryEventWrapper);
			AbstractConnectorUIFactory nodeUIFactory = BpmnGraphUIFactory.getInstance(null).getConnectorUIFactory(
				parentNode, (String)boundaryEventWrapper.getAttribute(
					BpmnMetaModelConstants.E_ATTR_NAME),attachedResource,"", type, classSpec);
			TSEConnector addConnector = nodeUIFactory.addConnector(parentNode);
			addConnector.setUserObject(boundaryEvent);
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(boundaryEvent);
			String id = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			addNodeToCache(id, addConnector);
		}
		
	}
	
	/**
	 * @param graph
	 * @param flowNode
	 * @param type
	 * @param extType
	 * @return
	 */
	private TSENode loadTextAnnotation(TSEGraph processGraph, TSEGraph parentGraph, EObject artifactNode) {
		EClass type = artifactNode.eClass();
		final EObject laneObj = (EObject) parentGraph.getUserObject();
		@SuppressWarnings("unused")
		final EObjectWrapper<EClass, EObject> lane = EObjectWrapper.wrap(laneObj);
		final EObject processObj = (EObject) processGraph.getUserObject();
		@SuppressWarnings("unused")
		final EObjectWrapper<EClass, EObject> process = EObjectWrapper.wrap(processObj);
		
		final EObjectWrapper<EClass, EObject> artifactWrapper = EObjectWrapper.wrap(artifactNode);
		String toolId = (String)ExtensionHelper.getExtensionAttributeValue(artifactWrapper, BpmnMetaModelExtensionConstants.E_ATTR_TOOL_ID);
		String attachedResource = getAttachedResource(artifactWrapper);
		AbstractNodeUIFactory uiFactory = BpmnGraphUIFactory
			.getInstance(getLayoutManager())
			.getNodeUIFactory(Messages.getString("title.textannotation"),attachedResource,toolId,type);
		// XYZ
		TSENode tsNode = uiFactory.addNode(parentGraph);
		
		if (type.equals(BpmnModelClass.TEXT_ANNOTATION)) {
			Object attribute = artifactWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_TEXT);
			String text = attribute.toString();
			tsNode.setAttribute(BpmnUIConstants.ATTR_ANOOTATION_TEXT, text);
			tsNode.setName(text);
		}
		tsNode.setUserObject(artifactWrapper.getEInstance());
		tsNode.discardAllLabels();
		uiFactory.updateNodeToolTip(tsNode);
		
		return tsNode;
	}

	/**
	 * @param graph
	 * @param flowNode
	 * @return
	 */
	private TSENode loadGateway(TSEGraph processGraph, TSEGraph parentGraph, EObject flowNode) {
		EClass type = flowNode.eClass();
		
		EObject laneObj = (EObject) parentGraph.getUserObject();
		EObjectWrapper<EClass, EObject> lane = EObjectWrapper.wrap(laneObj);
		@SuppressWarnings("unused")
		EObjectWrapper<EClass, EObject> process = null;
		
		if(BpmnModelClass.SUB_PROCESS.isSuperTypeOf(lane.getEClassType())){
			process = lane;
		}
		else{
			EObject processObj = (EObject)processGraph.getUserObject();
			process = EObjectWrapper.wrap(processObj);
		}
		
		final EObjectWrapper<EClass, EObject> gatewayWrapper = EObjectWrapper.wrap(flowNode);
		String toolId = (String)ExtensionHelper.getExtensionAttributeValue(gatewayWrapper, BpmnMetaModelExtensionConstants.E_ATTR_TOOL_ID);
		String attachedResource = getAttachedResource(gatewayWrapper);
		AbstractNodeUIFactory uiFactory = BpmnGraphUIFactory
			.getInstance(getLayoutManager())
			.getNodeUIFactory((String)gatewayWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME),attachedResource,
					toolId,
					type);
		TSENode tsNode = uiFactory.addNode(parentGraph);
		if(gatewayWrapper.containsAttribute(BpmnMetaModelConstants.E_ATTR_NAME)){
			String label = gatewayWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			if (label != null && !label.trim().isEmpty())
				uiFactory.addNodeLabel(tsNode, label);
		}
		tsNode.setUserObject(gatewayWrapper.getEInstance());
		uiFactory.updateNodeToolTip(tsNode);
		if(INCLUSIVE_GATEWAY.isSuperTypeOf(type)) {
		} else if(EXCLUSIVE_GATEWAY.isSuperTypeOf(type)) {
		} else if(COMPLEX_GATEWAY.isSuperTypeOf(type)) {
		} else if(PARALLEL_GATEWAY.isSuperTypeOf(type)) {
		} else if(EVENT_BASED_GATEWAY.isSuperTypeOf(type)) {
			EEnumLiteral gatewayType = (EEnumLiteral) EObjectWrapper.wrap(flowNode).getAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_GATEWAY_TYPE);
			@SuppressWarnings("unused")
			EEnumWrapper<EEnum, EEnumLiteral> enWrapper = EEnumWrapper.createInstance(BpmnMetaModel.ENUM_EVENT_BASED_GATEWAY_TYPE);
			if(gatewayType != null) {
			} else {
			}
		} 
		return tsNode;
		
	}
	
	private String getDescription(EObject obj){
		String description = null;
		EObjectWrapper<EClass, EObject> elementWrapper = EObjectWrapper.wrap(obj);
		EList<EObject> listAttribute = elementWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_DOCUMENTATION);
		if(listAttribute.size() > 0 ){
			EObjectWrapper<EClass, EObject> doc = EObjectWrapper.wrap(listAttribute.get(0));
			description = (String)doc.getAttribute(BpmnMetaModelConstants.E_ATTR_TEXT);
			registerForModelchange(doc);
		}
		if(description == null)
			description = "";
		
		return description;
	}
	
	/**
	 * @param parentGraph
	 * @param flowNode
	 * @return 
	 */
	private TSEEdge createSequenceFlow(TSEGraph processGraph, TSEGraph parentGraph, EObject flowNode) {
		final EObject laneObj = (EObject) parentGraph.getUserObject();
		@SuppressWarnings("unused")
		final EObjectWrapper<EClass, EObject> lane = EObjectWrapper.wrap(laneObj);
		final EObject processObj = (EObject)processGraph.getUserObject();
		@SuppressWarnings("unused")
		final EObjectWrapper<EClass, EObject> process = EObjectWrapper.wrap(processObj);
		
		
		EObjectWrapper<EClass, EObject> sequenceFlow = EObjectWrapper.wrap(flowNode);
		registerForModelchange(sequenceFlow);
		final EObjectWrapper<EClass, EObject> start = sequenceFlow.getWrappedEObjectAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF);
		final EObjectWrapper<EClass, EObject> end = sequenceFlow.getWrappedEObjectAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF);
		AbstractEdgeUIFactory uiFactory = BpmnGraphUIFactory.getInstance( getLayoutManager())
			.getEdgeUIFactory(
					(String)sequenceFlow.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME),
					SEQUENCE_FLOW);
		TSEObject node = getNodeRegistry().getNode((String) start.getAttribute(BpmnMetaModelConstants.E_ATTR_ID));
		TSENode startNode = null;
		TSEConnector connector = null;
		if(node instanceof TSENode)
			startNode = (TSENode)node;
		else if(node instanceof TSEConnector){
			connector = (TSEConnector)node;
			startNode = (TSENode)connector.getOwner();
		}
		TSENode endNode = (TSENode) getNodeRegistry().getNode((String) end.getAttribute(BpmnMetaModelConstants.E_ATTR_ID));
		TSEEdge edge  = null;
		if (startNode != null && endNode != null) {
			if(connector != null)	
				edge = uiFactory.addEdge(parentGraph, startNode, endNode, connector);
			else
				edge = uiFactory.addEdge(parentGraph, startNode, endNode);
			String lbl = sequenceFlow.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			if(lbl != null && !lbl.trim().isEmpty()){
				uiFactory.addEdgeLabel(edge, lbl);
			}
			edge.setUserObject(sequenceFlow.getEInstance());
			
			final String guid = (String) sequenceFlow.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			if (getNodeRegistry().containsKey(guid)) {
				BpmnUIPlugin.logErrorMessage(
						MessageFormat.format("[{0}] Duplicate GUID in BPMN diagram {1}",
								BpmnMetaModel.INSTANCE.getExpandedName(sequenceFlow.getEClassType()),guid));
			} else {
				addNodeToCache(guid, edge);
			}
		}
		return edge;
	}
	
	/**
	 * @param parentGraph
	 * @param association
	 * @return 
	 */
	private TSEEdge createAssociation(TSEGraph processGraph, TSEGraph parentGraph, EObject association) {
		final EObject laneObj = (EObject) parentGraph.getUserObject();
		@SuppressWarnings("unused")
		final EObjectWrapper<EClass, EObject> lane = EObjectWrapper.wrap(laneObj);
		final EObject processObj = (EObject) processGraph.getUserObject();
		@SuppressWarnings("unused")
		final EObjectWrapper<EClass, EObject> process = EObjectWrapper.wrap(processObj);
		
		
		EObjectWrapper<EClass, EObject> associationWrapper = EObjectWrapper.wrap(association);
		registerForModelchange(associationWrapper);
		final EObjectWrapper<EClass, EObject> start = associationWrapper.getWrappedEObjectAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF);
		final EObjectWrapper<EClass, EObject> end = associationWrapper.getWrappedEObjectAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF);
		AbstractEdgeUIFactory uiFactory = BpmnGraphUIFactory.getInstance( getLayoutManager())
		.getEdgeUIFactory(Messages.getString("title.association"), BpmnModelClass.ASSOCIATION);
		TSENode startNode = (TSENode) getNodeRegistry().getNode((String) start.getAttribute(BpmnMetaModelConstants.E_ATTR_ID));
		TSENode endNode = (TSENode) getNodeRegistry().getNode((String) end.getAttribute(BpmnMetaModelConstants.E_ATTR_ID));
		TSEEdge edge  = uiFactory.addEdge(parentGraph, startNode, endNode);
		if (ExtensionHelper.isValidDataExtensionAttribute(
				associationWrapper,
				BpmnMetaModelExtensionConstants.E_ATTR_LABEL)){
			EObjectWrapper<EClass, EObject> valWrapper = ExtensionHelper
			.getAddDataExtensionValueWrapper(associationWrapper);
			if (valWrapper != null) {
				String label = valWrapper
						.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LABEL);
				uiFactory.addEdgeLabel(edge, label);
			}
		}
		
		String guid = (String) associationWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
		if (getNodeRegistry().containsKey(guid)) {
			BpmnUIPlugin.logErrorMessage(
					MessageFormat.format("[{0}] Duplicate GUID in BPMN diagram {1}",
							BpmnMetaModel.INSTANCE.getExpandedName(associationWrapper.getEClassType()),guid));
		} else {
			addNodeToCache(guid, edge);
		}
		edge.setUserObject(associationWrapper.getEInstance());
		return edge;
	}

	public void setChangeMainDisplayGraph(boolean changeMainDisplayGraph) {
		this.changeMainDisplayGraph = changeMainDisplayGraph;
	}


	public void setShowPoolLaneNode(boolean showPoolLaneNode) {
		this.showPoolLaneNode = showPoolLaneNode;
	}
	
	protected String getAttachedResource(EObjectWrapper<EClass, EObject> userObjWrapper){
		String taskName = "";
		String attrName = getAttrNameForTaskSelection(userObjWrapper.getEClassType());
		if (userObjWrapper != null && attrName != null) {
			if (ExtensionHelper.isValidDataExtensionAttribute(userObjWrapper, attrName)) {
				EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
						.getAddDataExtensionValueWrapper(userObjWrapper);
				if (valueWrapper != null) {
					Object attribute = valueWrapper.getAttribute(attrName);
					if (attribute instanceof String){
						taskName = (String) attribute;
					}
				}
			}else if(userObjWrapper.containsAttribute(attrName)) {
				taskName = getAttributeValue(userObjWrapper,  attrName);
			}
		}
		
		return taskName;
	}
	
	protected String getAttrNameForTaskSelection(EClass type) {
		String name = null;
		if (type.equals(BpmnModelClass.JAVA_TASK))
			name=BpmnMetaModelExtensionConstants.E_ATTR_JAVA_FILE_PATH;
		else if (type.equals(BpmnModelClass.MANUAL_TASK));
		//	name= BpmnMetaModelExtensionConstants.E_ATTR_; // to write here
		else if (type.equals(BpmnModelClass.RULE_FUNCTION_TASK))
			name= BpmnMetaModelExtensionConstants.E_ATTR_RULEFUNCTION;
		else if (type.equals(BpmnModelClass.BUSINESS_RULE_TASK))
			name= BpmnMetaModelExtensionConstants.E_ATTR_VIRTUALRULEFUNCTION;
		else if (type.equals(BpmnModelClass.INFERENCE_TASK))
			name= BpmnMetaModelExtensionConstants.E_ATTR_RULES;
		else if (type.equals(BpmnModelClass.SERVICE_TASK))
			name= BpmnMetaModelExtensionConstants.E_ATTR_WSDL;
		else if (type.equals(BpmnModelClass.RECEIVE_TASK))
			name= BpmnMetaModelExtensionConstants.E_ATTR_EVENT;
		else if (type.equals(BpmnModelClass.SEND_TASK))
			name= BpmnMetaModelExtensionConstants.E_ATTR_EVENT;
		else if (BpmnModelClass.EVENT.isSuperTypeOf(type))
			name= BpmnMetaModelExtensionConstants.E_ATTR_EVENT;
		

		return name;
	}
	
	protected String getAttributeValue(EObjectWrapper<EClass, EObject> userObjWrapper, String attrName){
		if(userObjWrapper.containsAttribute(attrName)){
			Object attr = userObjWrapper.getAttribute(attrName);
			if (attr != null)
				return attr.toString();
		}
		
		return "";
	}
	
	
}
