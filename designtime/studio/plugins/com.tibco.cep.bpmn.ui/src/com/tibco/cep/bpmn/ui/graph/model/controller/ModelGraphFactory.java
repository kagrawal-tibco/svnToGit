package com.tibco.cep.bpmn.ui.graph.model.controller;



import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramManager;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.model.BpmnGraphUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.AbstractEdgeCommandFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.AssociationCommandFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.BoundaryEventCommandFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.CallActivityCommandFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.EventCommandFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.GatewayCommandFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.ICommandFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.LaneCommandFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.PoolCommandFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.ProcessCommandFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.SequenceFlowCommandFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.SubProcessCommandFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.TaskCommandFactory;
import com.tibco.cep.bpmn.ui.graph.model.command.TextAnnotationCommandFactory;
import com.tibco.cep.bpmn.ui.graph.model.factory.process.PoolLaneNodeUIFactory;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;
import com.tomsawyer.graphicaldrawing.complexity.TSENestingManager;

public class ModelGraphFactory {

	private List<ICommandFactory> commandFactoryList = new ArrayList<ICommandFactory>();

	private BpmnProcessLoader processLoader;

	private ModelController modelcontroller;

	private BpmnDiagramManager diagramManager;

	/**
	 * @param controller
	 * @param graphManager
	 * @param layoutManager
	 * @param drawingCanvas
	 */
	public ModelGraphFactory(final BpmnDiagramManager dManager,final ModelController controller) {
		this.processLoader = new BpmnProcessLoader(
				dManager.getGraphManager(),
				(BpmnLayoutManager)dManager.getLayoutManager(),
				controller.getModelChangeAdapterFactory());
		this.modelcontroller = controller;
		this.diagramManager = dManager;
		if (!dManager.isWeb()) {
			IProject proj = dManager.getProject();
			registerCommandFactory(new EventCommandFactory(proj, controller,BpmnModelClass.START_EVENT,null));
			registerCommandFactory(new EventCommandFactory(proj,controller,BpmnModelClass.START_EVENT,BpmnModelClass.MESSAGE_EVENT_DEFINITION));
			registerCommandFactory(new EventCommandFactory(proj,controller,BpmnModelClass.START_EVENT,BpmnModelClass.TIMER_EVENT_DEFINITION));
			registerCommandFactory(new EventCommandFactory(proj,controller,BpmnModelClass.START_EVENT,BpmnModelClass.SIGNAL_EVENT_DEFINITION));
			registerCommandFactory(new EventCommandFactory(proj,controller,BpmnModelClass.INTERMEDIATE_CATCH_EVENT,BpmnModelClass.MESSAGE_EVENT_DEFINITION));
			registerCommandFactory(new EventCommandFactory(proj,controller,BpmnModelClass.INTERMEDIATE_CATCH_EVENT,BpmnModelClass.TIMER_EVENT_DEFINITION));
			registerCommandFactory(new EventCommandFactory(proj,controller,BpmnModelClass.INTERMEDIATE_CATCH_EVENT,BpmnModelClass.ERROR_EVENT_DEFINITION));
			registerCommandFactory(new EventCommandFactory(proj,controller,BpmnModelClass.INTERMEDIATE_CATCH_EVENT,BpmnModelClass.SIGNAL_EVENT_DEFINITION));
			registerCommandFactory(new EventCommandFactory(proj,controller,BpmnModelClass.END_EVENT,null));
			registerCommandFactory(new EventCommandFactory(proj,controller,BpmnModelClass.END_EVENT,BpmnModelClass.MESSAGE_EVENT_DEFINITION));
			registerCommandFactory(new EventCommandFactory(proj,controller,BpmnModelClass.END_EVENT,BpmnModelClass.ERROR_EVENT_DEFINITION));
			registerCommandFactory(new EventCommandFactory(proj,controller,BpmnModelClass.END_EVENT,BpmnModelClass.SIGNAL_EVENT_DEFINITION));
			registerCommandFactory(new EventCommandFactory(proj,controller,BpmnModelClass.INTERMEDIATE_THROW_EVENT,null));
			registerCommandFactory(new EventCommandFactory(proj,controller,BpmnModelClass.INTERMEDIATE_THROW_EVENT,BpmnModelClass.MESSAGE_EVENT_DEFINITION));
			registerCommandFactory(new EventCommandFactory(proj,controller,BpmnModelClass.INTERMEDIATE_THROW_EVENT,BpmnModelClass.ERROR_EVENT_DEFINITION));
			registerCommandFactory(new EventCommandFactory(proj,controller,BpmnModelClass.INTERMEDIATE_THROW_EVENT,BpmnModelClass.SIGNAL_EVENT_DEFINITION));

			//gateway
			registerCommandFactory(new GatewayCommandFactory(proj,controller,BpmnModelClass.INCLUSIVE_GATEWAY,null));
			registerCommandFactory(new GatewayCommandFactory(proj,controller,BpmnModelClass.EXCLUSIVE_GATEWAY,null));
			registerCommandFactory(new GatewayCommandFactory(proj,controller,BpmnModelClass.COMPLEX_GATEWAY,null));
			registerCommandFactory(new GatewayCommandFactory(proj,controller,BpmnModelClass.PARALLEL_GATEWAY,null));
			registerCommandFactory(new GatewayCommandFactory(proj,controller,BpmnModelClass.EVENT_BASED_GATEWAY,BpmnModelClass.ENUM_EVENT_BASED_GATEWAY_TYPE_PARALLEL));
			registerCommandFactory(new GatewayCommandFactory(proj,controller,BpmnModelClass.EVENT_BASED_GATEWAY,BpmnModelClass.ENUM_EVENT_BASED_GATEWAY_TYPE_EXCLUSIVE));

			//task
			registerCommandFactory(new TaskCommandFactory(proj,controller,BpmnModelClass.JAVA_TASK,null));
			registerCommandFactory(new TaskCommandFactory(proj,controller,BpmnModelClass.RULE_FUNCTION_TASK,null));
			registerCommandFactory(new TaskCommandFactory(proj,controller,BpmnModelClass.SCRIPT_TASK,null));
			registerCommandFactory(new TaskCommandFactory(proj,controller,BpmnModelClass.SEND_TASK,null));
			registerCommandFactory(new TaskCommandFactory(proj,controller,BpmnModelClass.RECEIVE_TASK,null));
			registerCommandFactory(new TaskCommandFactory(proj,controller,BpmnModelClass.MANUAL_TASK,null));
			registerCommandFactory(new TaskCommandFactory(proj,controller,BpmnModelClass.INFERENCE_TASK,null));
			registerCommandFactory(new TaskCommandFactory(proj,controller,BpmnModelClass.SERVICE_TASK,null));
			registerCommandFactory(new TaskCommandFactory(proj,controller,BpmnModelClass.BUSINESS_RULE_TASK,null));

			//sub-process
			registerCommandFactory(new SubProcessCommandFactory(proj,controller,BpmnModelClass.SUB_PROCESS, null, diagramManager));
			//call-activity
			registerCommandFactory(new CallActivityCommandFactory(proj,controller,BpmnModelClass.CALL_ACTIVITY, null));
			// sequence flow
			registerCommandFactory(new SequenceFlowCommandFactory(controller,BpmnModelClass.SEQUENCE_FLOW,BpmnModelClass.SEQUENCE_FLOW));

			// lane
			registerCommandFactory(new LaneCommandFactory(proj,controller,BpmnModelClass.LANE,null));

			// pool
			registerCommandFactory(new PoolCommandFactory(proj,controller,BpmnModelClass.LANE,BpmnModelClass.PROCESS));

			// process
			registerCommandFactory(new ProcessCommandFactory(controller,BpmnModelClass.PROCESS,BpmnModelClass.PROCESS));
			// text annotation
			registerCommandFactory(new TextAnnotationCommandFactory(proj,controller,BpmnModelClass.TEXT_ANNOTATION,null,diagramManager));
			registerCommandFactory(new AssociationCommandFactory(controller,BpmnModelClass.ASSOCIATION,null));
			//boundaryEvent
			registerCommandFactory(new BoundaryEventCommandFactory(proj,controller,BpmnModelClass.MESSAGE_EVENT_DEFINITION));
			registerCommandFactory(new BoundaryEventCommandFactory(proj, controller,BpmnModelClass.ERROR_EVENT_DEFINITION));
			registerCommandFactory(new BoundaryEventCommandFactory(proj, controller,BpmnModelClass.TIMER_EVENT_DEFINITION));
			registerCommandFactory(new BoundaryEventCommandFactory(proj, controller,BpmnModelClass.SIGNAL_EVENT_DEFINITION));
		}
	}
	
	/**
	 * @return
	 */
	public ModelController getModelController() {
		return modelcontroller;
	}
	/**
	 * @return
	 */
//	public BpmnDiagramManager getBpmnGraphDiagramManager1() {
//		return diagramManager;
//	}
	
//	/**
//	 * @return
//	 */
//	public TSEGraphManager getGraphManager() {
//		return getBpmnGraphDiagramManager().getGraphManager();
//	}
//	
//	/**
//	 * @return
//	 */
//	public LayoutManager getLayoutManager() {
//		return getBpmnGraphDiagramManager().getLayoutManager();
//	}
//	
//	/**
//	 * @return
//	 */
//	public DrawingCanvas getDrawingCanvas() {
//		return getBpmnGraphDiagramManager().getDrawingCanvas();
//	}
//	
	/**
	 * @param <T>
	 * @param guid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends TSEObject> T getCachedNode(String guid) {
		return (T)processLoader.getNodeRegistry().getNode(guid);
	}
	
	
	
	/**
	 * @return
	 */
	public ModelGraphNodeRegistry<TSEObject> getNodeRegistry() {
		return processLoader.getNodeRegistry();
	}
	
	/**
	 * @return
	 */
	public void buildNodeRegistry() {
		processLoader.buildNodeRegistry();
	}

	/**
	 * @param <T>
	 * @param guid
	 * @param object
	 * @return
	 */
	public <T extends TSEObject> boolean addNodeToCache(String guid, T object) {
		return processLoader.addNodeToCache(guid, object);
	}
	
	
	/**
	 * @param graph
	 * @return
	 */
	public TSENode getStartNode(TSEGraph graph) {
		return processLoader.getStartNode(graph);
	}
	
	/**
	 * 
	 */
	public void clearCache() {
		processLoader.clearCache();
	}
	
	/**
	 * create the process
	 * @param subProgressMonitor 
	 * @return 
	 * @return
	 */
	public  TSEGraph createProcess(IFile file, String processName, String desc, IProgressMonitor pm) {
		TSEGraph graph = null;
		try {
			pm.beginTask(Messages.getString(BpmnUIConstants.BPMN_MODEL_LOAD_PROCESS), 100);
			pm.subTask(Messages.getString(BpmnUIConstants.BPMN_MODEL_CREATE_PROCESS));
			// make sure that index is created before we create the new process, else extension definition in process would be missing
			EObject index = BpmnCorePlugin.getDefault().getBpmnModelManager().getBpmnIndex(file.getProject());
			EObjectWrapper<EClass, EObject> indexWrapper = EObjectWrapper.wrap(index);
			EObjectWrapper<EClass, EObject> process = getModelController().createProcess(processName, file.getProject().getName(), file.getProjectRelativePath().removeLastSegments(1).makeAbsolute().toPortableString());	
			// add the newly created process to the index
			BpmnCommonIndexUtils.addRootElement(indexWrapper, process.getEInstance());
			
			getModelController().createDocumentation(process, desc);
			// graph manager is associated with process
			diagramManager.getGraphManager().setUserObject(process.getEInstance());
			
			// main display graph is associated with process
			graph = (TSEGraph) diagramManager.getGraphManager().getMainDisplayGraph();
			graph.setUserObject(process.getEInstance());
			graph.setAttribute(BpmnUIConstants.NODE_ATTR_NAME, process.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME));
			
			// System.out.println(process.getAttribute(BpmnMetaModelConstants.E_ATTR_LABEL));
			
			// GGG? label still needed for this graph?
//			if (process.getAttribute(BpmnMetaModelConstants.E_ATTR_LABEL) != null) {
//				graph.setAttribute(BpmnUIConstants.NODE_ATTR_LABEL, process.getAttribute(BpmnMetaModelConstants.E_ATTR_LABEL));
//			}
			
			graph.setAttribute(BpmnUIConstants.NODE_ATTR_TYPE, process.getEClassType());
			graph.setAttribute(BpmnUIConstants.NODE_ATTR_TASK_DESCRIPTION, desc);
			pm.worked(10);
			//resetDirection(graph);
	
			return graph;
		}
		catch (Exception e) {
			BpmnUIPlugin.log(e);
			return graph;
		}
		finally {
			pm.done();
		}
	}
	
	/**
	 * @param tsGraphObject
	 * @param name
	 * @return
	 */
	public TSENode createPoolLaneNode(TSGraphObject tsGraphObject,String name, String toolId) {
		if(tsGraphObject instanceof TSGraph) { // process
			return createPoolLaneNode((TSGraph) tsGraphObject, name, toolId);
		} else if (tsGraphObject instanceof TSENode) {
			return createPoolLaneNode(((TSENode)tsGraphObject).getChildGraph(),name, toolId);
		}
		return null;
	}
	
	/**
	 * @param parentGraph
	 * @param name
	 * @param isLane
	 * @return
	 */
	public TSENode createPoolLaneNode(TSGraph parentGraph, String name, String toolId) {
//		boolean isPool = false;
		EObjectWrapper<EClass, EObject> parentLaneSet = null;
		final EObject pObj = (EObject) parentGraph.getUserObject();
		final EClass pObjClass = pObj.eClass();
		if(EObjectWrapper.wrap(pObj).isInstanceOf(BpmnModelClass.PROCESS)) {
//			isPool = true;
			EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper.wrap((EObject)parentGraph.getUserObject());
			@SuppressWarnings("unchecked")
			EList<EObject>laneSets = (EList<EObject>) processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_LANE_SETS);
			parentLaneSet = getModelController().createLaneSet(name,null);
			laneSets.add(parentLaneSet.getEInstance());

		} else if(EObjectWrapper.wrap(pObj).isInstanceOf(BpmnModelClass.LANE)) {
			    EObjectWrapper<EClass, EObject> lane = EObjectWrapper.wrap(pObj);
			    EObject childLaneSet = (EObject) lane.getAttribute(BpmnMetaModelConstants.E_ATTR_CHILD_LANE_SET);
		    	parentLaneSet = EObjectWrapper.wrap(childLaneSet);
		}
		
		PoolLaneNodeUIFactory uiFactory = (PoolLaneNodeUIFactory) BpmnGraphUIFactory
			.getInstance((BpmnLayoutManager) diagramManager.getLayoutManager())
			.getNodeUIFactory(name,"",toolId,
					BpmnModelClass.LANE, 
					BpmnMetaModel.INSTANCE.getExpandedName(pObjClass));
		TSENode laneNode = uiFactory.addNode((TSEGraph) parentGraph);
		if (name == null) {
			laneNode.setName("");
		}
		else {
			laneNode.setName(name);
		}
		EObjectWrapper<EClass, EObject> laneWrapper = 
				getModelController().createLane(name, parentLaneSet);
		laneNode.setUserObject(laneWrapper.getEInstance());
		
		TSEGraph childGraph = (TSEGraph) laneNode.getChildGraph();
		if(childGraph == null) {
			childGraph = (TSEGraph) diagramManager.getGraphManager().addGraph();
			laneNode.setChildGraph(childGraph);
			childGraph.setUserObject(laneWrapper.getEInstance());
		} else {
			childGraph.setUserObject(laneWrapper.getEInstance());
		}
		((BpmnLayoutManager) diagramManager.getLayoutManager()).setLayoutOptionsForPool(laneNode);
		TSENestingManager.expand(laneNode);
		
		
//		LaneChildGraphNodeUI ui = new LaneChildGraphNodeUI();
//		ui.setOuterRoundRect(true);
//		ui.setFillColor(BpmnGraphConstants.LANE_FILL_COLOR);
//		ui.setBorderDrawn(true);
//		ui.setDrawChildGraphMark(false);
//		ui.setIsLane(isLane);
//		ui.isFirst = isFirst;
//		laneNode.setUI(ui);
		
//		((BpmnLayoutManager) this.getLayoutManager()).setLayoutOptionsForLane(laneNode);
		
//		if (isLane) {
//			isPool = false;
//		}
		
		return laneNode;
	}

	

	/**
	 * create the process
	 * @param subProgressMonitor 
	 * @return 
	 * @return
	 */
	public  TSEGraph loadProcess(EObjectWrapper<EClass, EObject> process,TSGraph tsGraph, IProgressMonitor pm) {
		return processLoader.loadProcess(process, tsGraph, pm);
	}
	
	public  void reLoadProcess(EObjectWrapper<EClass, EObject> process) {
		processLoader.reLoadProcess(process);
	}
	
	
	public void registerCommandFactory(ICommandFactory factory) {
		this.commandFactoryList.add(factory);
	}
	
	public ICommandFactory getCommandFactory(EClass type,ENamedElement extType) {
		for(ICommandFactory f : this.commandFactoryList) {
			if(f.handlesModelType(type, extType)){
				return f;
			}
		}
		
		return null;
	}

		
	public AbstractEdgeCommandFactory getEdgeCommandFactory(EClass edgeType) {
		for(ICommandFactory f : this.commandFactoryList) {
			if(f.handlesModelType(edgeType, null)){
				ICommandFactory cf =  f;
				return (AbstractEdgeCommandFactory) cf;
			}
		}		
		return null;
	}	
	
	public BpmnProcessLoader getProcessLoader() {
		return processLoader;
	}

}
