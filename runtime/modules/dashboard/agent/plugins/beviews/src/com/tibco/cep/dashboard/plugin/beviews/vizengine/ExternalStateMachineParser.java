package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.plugin.beviews.vizengine.StateMachineTraverser.ActivityConfigCreator;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.model.MALStateMachineComponent;
import com.tibco.cep.dashboard.psvr.ogl.model.ActivityConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.CoordinatePoint;
import com.tibco.cep.dashboard.psvr.ogl.model.Path;
import com.tibco.cep.dashboard.psvr.ogl.model.ProcessConfig;
import com.tibco.cep.dashboard.psvr.ogl.model.ProcessModel;
import com.tibco.cep.dashboard.psvr.ogl.model.types.CoordinatePointIconType;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEnd;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateStart;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tomsawyer.drawing.TSDEdge;
import com.tomsawyer.drawing.TSDGraph;
import com.tomsawyer.drawing.TSDNode;
import com.tomsawyer.drawing.TSPNode;
import com.tomsawyer.drawing.complexity.TSNestingManager;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.interactive.service.TSEAllOptionsServiceInputData;
import com.tomsawyer.licensing.TSLicenseManager;
import com.tomsawyer.service.TSServiceInputData;
import com.tomsawyer.service.TSServiceOutputData;
import com.tomsawyer.service.layout.TSHierarchicalLayoutInputTailor;
import com.tomsawyer.service.layout.TSLayoutConstants;
import com.tomsawyer.service.layout.TSLayoutInputTailor;
import com.tomsawyer.service.layout.client.TSApplyLayoutResults;
import com.tomsawyer.service.layout.client.TSLayoutProxy;

/**
 * Uses an external process/mechanism to handle the processing of a state machine's layout. Currently Tom Sawyer is the only external
 * engine utilized by this class.
 *
 * @author mwiley
 *
 */
public class ExternalStateMachineParser implements StateMachineParser {

	static {
		initTSLicenseManager();
	}

	private static void initTSLicenseManager() {
		String username = System.getProperty("ts.dev.user.name");
		if (StringUtil.isEmptyOrBlank(username) == true) {
			username = System.getProperty("user.name");
		}
		TSLicenseManager.setUserName(username);
		TSLicenseManager.initTSSLicensing();
	}

	private Logger logger;
	private int stateVizWidth;
	private int stateVizHeight;
	private ActivityConfigCreator configCreator;
	private ProcessConfig rootProcessConfig;
	private Map<String, ActivityConfig> activities;

	private HashMap<StateEntity, TSDNode> stateToNodeMap;
	private LinkedHashMap<State, List<TSDEdge>> stateToEdgeMap;
	private TSEGraphManager graphManager;
	private TSEAllOptionsServiceInputData inputData;

	public ExternalStateMachineParser(Logger logger, MALStateMachineComponent stateMachineComponent, ActivityConfigCreator configCreator) {
		this.logger = logger;
		stateVizWidth = stateMachineComponent.getStateVisualizationWidth();
		stateVizHeight = stateMachineComponent.getStateVisualizationHeight();
		this.configCreator = configCreator;
		activities = new HashMap<String, ActivityConfig>();
		stateToNodeMap = new HashMap<StateEntity, TSDNode>();
		stateToEdgeMap = new LinkedHashMap<State, List<TSDEdge>>();
	}

	public ProcessConfig getRootProcessConfig() {
		return rootProcessConfig;
	}

	public void process(StateMachine stateMachine, PresentationContext ctx) throws VisualizationException, PluginException, IOException, MALException, ElementNotFoundException {
		if (logger.isEnabledFor(Level.INFO) == true) {
			logger.log(Level.INFO, "Externaly Processing StateMachine[name=" + stateMachine.getName() + "]...");
		}

		String command = ctx.getAttribute("command") == null ? "":((String[]) ctx.getAttribute("command"))[0];
		String nodeID = ctx.getAttribute("activityid") == null ? "":((String[]) ctx.getAttribute("activityid"))[0];

		if (logger.isEnabledFor(Level.INFO)){
			logger.log(Level.INFO, "ExternalStateMachineProcessor.process - Executing command: " + command);
			logger.log(Level.INFO, "ExternalStateMachineProcessor.process - Using Node ID: " + nodeID);
		}

		//build collection of currently expanded nodes
		HashMap<String, ActivityConfig> expandedNodes = null;
		if (command.equals("expandstatenode") || command.equals("collapsestatenode")) {
			logger.log(Level.DEBUG, "ExternalStateMachineProcessor.process - Building expanded node set from request.");
			expandedNodes = new HashMap<String, ActivityConfig>();
			if (ctx.getAttribute("processmodel") != null) {
				ProcessModel pModel = (ProcessModel) ctx.getAttribute("processmodel");
				ProcessConfig pConfig = pModel.getProcessConfig();
				addExpandedNodes(pConfig.getActivityConfig(), expandedNodes);
			}
			if (command.equals("expandstatenode")){
				expandedNodes.put(nodeID, null);
			}
			else {  //command == collapsestatenode
				expandedNodes.remove(nodeID);
			}
			logger.log(Level.DEBUG, "ExternalStateMachineProcessor.process - Built expanded node set of size: " + expandedNodes.size() + ".");
		}
		rootProcessConfig = new ProcessConfig();
		List<StateEntity> states = stateMachine.getStateEntities();
		initTomSawyer();
		TSDGraph graph = graphManager.getMainDisplayGraph();
		addGraphNodes(graph, states, expandedNodes);
		addGraphEdges(graph, stateMachine.getStateTransitions());
		configureGraphLayout(graph, inputData);
		layoutGraph(graphManager, inputData);
		addLayoutOfNestedStates(states, rootProcessConfig, ctx, graph.getBounds().getWidth()/2, graph.getBounds().getHeight()/2);
	}

	private void addExpandedNodes(ActivityConfig[] candidates, HashMap<String, ActivityConfig> expandedNodes){
		for(int i = 0; i < candidates.length; i++){
			if(candidates[i].getProcessConfig() != null && candidates[i].getProcessConfig().getActivityConfigCount() > 0){
				expandedNodes.put(candidates[i].getActivityID(), candidates[i]);
				addExpandedNodes(candidates[i].getProcessConfig().getActivityConfig(), expandedNodes);
			}
		}
	}

	private void initTomSawyer(){
		logger.log(Level.DEBUG, "ExternalStateMachineProcessor.initTomSawyer");
		graphManager = new TSEGraphManager();
		inputData = new TSEAllOptionsServiceInputData(graphManager);
	}

	private void addGraphNodes(TSDGraph graph, List<StateEntity> states, HashMap<String, ActivityConfig> expandedNodes){
		logger.log(Level.DEBUG, "ExternalStateMachineProcessor.addGraphNodes - Building Tom Sawyer model (nodes).");
		for (int i = 0; i < states.size(); i++) {
			State state = (State) states.get(i);
			if (state instanceof StateStart || state instanceof StateEnd) {
				continue;
			}
			TSDNode node = (TSDNode) graph.addNode();
			node.setWidth(stateVizWidth);
			node.setHeight(stateVizHeight);
			logger.log(Level.DEBUG, "ExternalStateMachineProcessor.addGraphNodes - Created node.");
			stateToNodeMap.put(state, node);
			if (expandedNodes != null && expandedNodes.containsKey(state.getGUID()) && state instanceof StateComposite) {
				logger.log(Level.DEBUG, "ExternalStateMachineProcessor.addGraphNodes - Building nested graph for composite state.");
				ActivityConfig config = expandedNodes.get(state.getGUID());
				if(config != null) {
					//honor previously set width and height
					node.setWidth(config.getWidth());
					node.setHeight(config.getHeight());
				}
				StateComposite stateComposite = (StateComposite) state;
				TSDGraph childGraph = (TSDGraph) graphManager.addGraph();
				configureGraphLayout(childGraph, inputData);
				node.setChildGraph(childGraph);
				addGraphNodes(childGraph, stateComposite.getStateEntities(), expandedNodes);
				TSNestingManager.expand(node);
				logger.log(Level.DEBUG, "ExternalStateMachineProcessor.addGraphNodes - Finished building nested graph for composite state.");
			}
		}
		logger.log(Level.DEBUG, "ExternalStateMachineProcessor.addGraphNodes - Finished building Tom Sawyer model (nodes).");
	}

//-- This routine has been replaced to work-around an issue in the statmachine model where state.getOutgoingTransitions doesn't return the correct transitions for all states (BE-14188)--//
//	private void addGraphEdges(TSDGraph graph){
//		logger.log(Level.DEBUG, "ExternalStateMachineProcessor.addGraphNodes - Building Tom Sawyer model (edges).");
//		for(Map.Entry<StateEntity, TSDNode> stateEntry : stateToNodeMap.entrySet()){
//			if (!(stateEntry.getKey() instanceof State)) {
//				continue;
//			}
//			State state = (State) stateEntry.getKey();
//			ArrayList<TSDEdge> edges = new ArrayList<TSDEdge>(state.getOutgoingTransitions().size());
//			for(StateTransition transition : state.getOutgoingTransitions()){
//				edges.add((TSDEdge) graphManager.addEdge(stateEntry.getValue(), stateToNodeMap.get(transition.getToState())));
//			}
//			stateToEdgeMap.put(state, edges);
//		}
//		graphManager.updateAllMetaEdges();
//		logger.log(Level.DEBUG, "ExternalStateMachineProcessor.addGraphNodes - Finished building Tom Sawyer model (edges).");
//	}

	private void addGraphEdges(TSDGraph graph, EList<StateTransition> allTransitions){
		logger.log(Level.DEBUG, "ExternalStateMachineProcessor.addGraphNodes - Building Tom Sawyer model (edges).");
		StateTransition transition;
		State fromState, toState;
		Iterator<StateTransition> transIter = allTransitions.iterator();
		while(transIter.hasNext()){
			transition = transIter.next();
			fromState = transition.getFromState();
			toState = transition.getToState();
			if(!(stateToNodeMap.containsKey(fromState) && stateToNodeMap.containsKey(toState))){
				//ignore all transitions to/from a state we don't care about
				continue;
			}
			List<TSDEdge> edges = stateToEdgeMap.get(fromState);
			if(edges == null){
				edges = new ArrayList<TSDEdge>();
				stateToEdgeMap.put(fromState, edges);
			}
			edges.add((TSDEdge) graphManager.addEdge(stateToNodeMap.get(fromState), stateToNodeMap.get(toState)));
		}
		graphManager.updateAllMetaEdges();
		logger.log(Level.DEBUG, "ExternalStateMachineProcessor.addGraphNodes - Finished building Tom Sawyer model (edges).");
	}

	private ActivityConfig getActivityConfig(ProcessConfig processConfig, State state, PresentationContext ctx) throws VisualizationException, PluginException, IOException, MALException, ElementNotFoundException {
		String stateId = state.getGUID();
		ActivityConfig activityConfig = activities.get(stateId);
		if (activityConfig == null) {
			if (configCreator != null) {
				activityConfig = configCreator.createActivityConfig(state, ctx);
			} else {
				activityConfig = new ActivityConfig();
				activityConfig.setActivityID(stateId);
				activityConfig.setTitle(state.getName());
			}
			processConfig.addActivityConfig(activityConfig);
			activities.put(state.getGUID(), activityConfig);
		}
		return activityConfig;
	}

	private void addLayoutOfNestedStates(List<StateEntity> states, ProcessConfig parentProcCfg, PresentationContext ctx, double wCorrection, double hCorrection) throws VisualizationException, PluginException, IOException, MALException, ElementNotFoundException {
		logger.log(Level.DEBUG, "ExternalStateMachineProcessor.addLayoutOfNestedStates - Building XML model.");
		for (int iState = 0; iState < states.size(); iState++) {
			State state = (State) states.get(iState);
			if (stateToNodeMap.get(state) == null) {
				continue;  //StateStart and StateEnd will have null map entries
			}
			//add self
			ActivityConfig activityCfg = getActivityConfig(parentProcCfg, state, ctx);
			activityCfg.setX( (int) (stateToNodeMap.get(state).getLeft()+wCorrection) );
			activityCfg.setY( (int) (stateToNodeMap.get(state).getBottom()+hCorrection) );
			activityCfg.setWidth( (int) stateToNodeMap.get(state).getWidth());
			activityCfg.setHeight( (int) stateToNodeMap.get(state).getHeight());
			logger.log(Level.DEBUG, "ExternalStateMachineProcessor.addLayoutOfNestedStates - Build XML for node (" + state.getGUID() + ").");
			if(stateToEdgeMap.containsKey(state)){
				for (TSDEdge edge : stateToEdgeMap.get(state)) {
					if (edge == null) {
						continue;
					}
					Path edgePath = new Path();
					CoordinatePoint pathPoint = new CoordinatePoint();
					pathPoint.setX( (int) (edge.getSourceEdge().getSourcePoint().getX()+wCorrection) );
					pathPoint.setY( (int) (edge.getSourceEdge().getSourcePoint().getY()+hCorrection) );
					edgePath.addPathnode(pathPoint);
					for(int iPathNode = 0; iPathNode < edge.pathNodes().size(); iPathNode++) {
						TSPNode pathNode = (TSPNode) edge.pathNodes().get(iPathNode);
						pathPoint = new CoordinatePoint();
						pathPoint.setX( (int) (pathNode.getCenterX()+wCorrection) );
						pathPoint.setY( (int) (pathNode.getCenterY()+hCorrection) );
						edgePath.addPathnode(pathPoint);
					}
					pathPoint = new CoordinatePoint();
					pathPoint.setX( (int) (edge.getTargetEdge().getTargetPoint().getX()+wCorrection) );
					pathPoint.setY( (int) (edge.getTargetEdge().getTargetPoint().getY()+hCorrection) );
					pathPoint.setIcon(CoordinatePointIconType.FILLED_ARROW);
					edgePath.addPathnode(pathPoint);
					activityCfg.addPath(edgePath);
					logger.log(Level.DEBUG, "ExternalStateMachineProcessor.addLayoutOfNestedStates - Build XML path for node edge. Node = " + state.getGUID() + "\t #Paths = " + activityCfg.getPathCount() + ".");
				}
			}
			//activity config is added to the process config by getActivityConfig
			if (state instanceof StateComposite) {
				StateComposite stateComposite = (StateComposite) state;
				if (!stateComposite.isConcurrentState()) {
					ProcessConfig procCfg = new ProcessConfig();
					activityCfg.setProcessConfig(procCfg);
					addLayoutOfNestedStates(stateComposite.getStateEntities(), procCfg, ctx, wCorrection, hCorrection);
				}
			}
		}
		logger.log(Level.DEBUG, "ExternalStateMachineProcessor.addLayoutOfNestedStates - Finished building XML model.");
	}

	private static void configureGraphLayout(TSDGraph graph, TSServiceInputData serviceInputData){
//		TSCircularLayoutInputTailor optionsTailor = new TSCircularLayoutInputTailor(serviceInputData);
		TSHierarchicalLayoutInputTailor optionsTailor = new TSHierarchicalLayoutInputTailor(serviceInputData);
//		TSOrthogonalLayoutInputTailor optionsTailor = new TSOrthogonalLayoutInputTailor(serviceInputData);
		optionsTailor.setGraph(graph);
		optionsTailor.setAsCurrentLayoutStyle();
		optionsTailor.setLevelDirection(TSLayoutConstants.DIRECTION_LEFT_TO_RIGHT);
		optionsTailor.setOrthogonalRouting(true);
		optionsTailor.setKeepNodeSizes(true);
	}

	private static void layoutGraph(TSEGraphManager graphManager, TSServiceInputData serviceInputData){
		TSLayoutInputTailor layoutTailor = new TSLayoutInputTailor(serviceInputData);
		layoutTailor.setGraphManager(graphManager);
		layoutTailor.setAsCurrentOperation();

		TSServiceOutputData outputData = new TSServiceOutputData();
		new TSLayoutProxy().run(serviceInputData, outputData);

		TSApplyLayoutResults applyResults = new TSApplyLayoutResults();
		applyResults.apply(serviceInputData, outputData);
	}

}
