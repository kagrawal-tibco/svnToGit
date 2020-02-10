package com.tibco.cep.studio.ui.statemachine.diagram;

import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineConstants.STATE_TYPE;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.STATE_GRAPH_PATH;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.createTooltip;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.isRegionPresent;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.isStatePresent;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.overlapNodesFixed;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.updateStateGUID;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.generateUniqueName;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.getStateGraphPath;

import java.util.Map;

import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEnd;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateSimple;
import com.tibco.cep.designtime.core.model.states.StateSubmachine;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.statemachine.diagram.editors.StateMachineEditor;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.STATE;
import com.tibco.cep.studio.ui.util.StudioUIUtils;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEAnnotatedUI;

/**
 * 
 * @author sasahoo
 *
 */
public class StateMachineDiagramEditHandler {
	
	/**
	 * Handling clip board objects
	 * @param tsNode
	 * @param map
	 * @param isCopy
	 * @param manager
	 * @param listener
	 */
	@SuppressWarnings({ "unused"})
	public static void editClipBoardState(TSENode tsNode, 
			                       Map<String, Object> map, 
			                       boolean isCopy , 
			                       StateMachineDiagramManager manager, 
			                       StateMachineDiagramChangeListener listener) {
		/**
		 * Fix : After 9.20.0 integration, composite/concurrent/region node cut/copy/paste  
		 */
		if (tsNode != null && tsNode.getName() == null) {
			return;
		}
		if (!map.containsKey(tsNode.getName().toString())) {
			return;
		}
		State state  = (State)map.get(tsNode.getName().toString());
		if (state == null) {
			return;
		}
		Map<String, StateEntity> ss  = null;
		String tag = tsNode.getName().toString();
		if (overlapNodesFixed) {
			if (state instanceof StateComposite) {
				listener.getStateGraphPathmap().clear();
				tag = manager.getTagMap().get(tag);

				state = (State)EcoreUtil.copy(state);
				state.setName(tag);

				String smPath = manager.getStateMachine().getFullPath()+"."+IndexUtils.getFileExtension(manager.getStateMachine());
				
				trverseStatePathList(state, smPath, listener.getStateGraphPathmap());
				
				ss = listener.getStateGraphPathmap();
				
			}
		}
		tsNode.setUserObject(state);
		if (!overlapNodesFixed  || state instanceof StateSimple || state instanceof StateEnd) {
			tag = generateUniqueTag(tsNode, isCopy, tag);//add unique tag
			state.setName(tag);
		}
		arrangeNodeLabelOptions(state, tsNode, tag, manager);//set Node Label Options
		tsNode.setName(tag);
		tsNode.setTooltipText(createTooltip(state));
		updateGUID(tsNode, isCopy, manager);//update GUID
		if (tsNode.getChildGraph() != null) {
			tsNode.getChildGraph().setUserObject(state);
		}
		if (tsNode.getAttributeValue(STATE_TYPE).equals(STATE.COMPOSITE)
				|| tsNode.getAttributeValue(STATE_TYPE).equals(STATE.REGION)
				|| tsNode.getAttributeValue(STATE_TYPE).equals(STATE.CONCURRENT)) {
//			tsNode.getChildGraph().edges().clear();
			clearEdges(tsNode); //fixing clear edges from heirachical nodes 
			if (!overlapNodesFixed) {
				tsNode.getChildGraph().nodes().clear();
			}
		}
	}
	
    /**
     * This is for Composite State after Paste added
     * @param tsNode
     * @param manager
     * @param listener
     * @return
     */
    public static boolean afterEditNodeAdded(TSENode tsNode,  
    		                          final StateMachineDiagramManager manager, 
                                      StateMachineDiagramChangeListener listener) {
    	if (tsNode.getAttributeValue(STATE_TYPE)==null ) {
    		return false;
    	}
    	if ((tsNode.getAttributeValue(STATE_TYPE).equals(STATE.COMPOSITE)
    			|| tsNode.getAttributeValue(STATE_TYPE).equals(STATE.REGION)
    			|| tsNode.getAttributeValue(STATE_TYPE).equals(STATE.CONCURRENT))) {
    		TSEGraph  graph= (TSEGraph)tsNode.getOwnerGraph();
    		StateComposite stateComposite = (StateComposite)tsNode.getUserObject();

    		if (!overlapNodesFixed) {
    			stateComposite = (StateComposite)EcoreUtil.copy(stateComposite);
    			tsNode.setUserObject(stateComposite);
    		}

    		tsNode.setExpandedResizability(TSESolidObject.RESIZABILITY_NO_FIT);
    		tsNode.setTooltipText(createTooltip(stateComposite));

    		if (overlapNodesFixed) {
    			traverseNodes(tsNode, listener);
    		}

    		if (!overlapNodesFixed) {
    			manager.generateStates(tsNode.getChildGraph(), (StateComposite)tsNode.getUserObject());
    		}
    		StateComposite graphComposite = (StateComposite)graph.getUserObject();
    		if (graphComposite.isConcurrentState()) {
    			//adding Regions
    			graphComposite.getRegions().add((StateComposite)tsNode.getUserObject());
    		} else {
    			graphComposite.getStateEntities().add((StateEntity)tsNode.getUserObject());
    		}
    		StudioUIUtils.invokeOnDisplayThread(new Runnable() {
    			public void run() {
    				((StateMachineEditor) manager.getEditor()).modified();
    			}
    		}, false);
    		return true;
    	}

		return false;
	}
                                      
    
	
	/**
	 * @param tsNode
	 * @param isCopy
	 * @param tag
	 */
	public static String generateUniqueTag(TSENode tsNode, boolean isCopy, String tag) {
		if (isCopy) {
			tag = generateUniqueName(tag);
		} else {
			//First time cut/paste the same node addition
			//Then second time onwards unique tag generation
			StateComposite stateComposite =(StateComposite) tsNode.getOwnerGraph().getUserObject();
			if (stateComposite.isConcurrentState()) {
				if (isRegionPresent(stateComposite.getRegions(), tag)) {
					tag = generateUniqueName(tag);
				}
			} else {
				if (isStatePresent(stateComposite.getStateEntities(), tag)) {
					tag = generateUniqueName(tag);
				}
			}
		}
		return tag;
	}
	

	/**
	 * @param state
	 * @param tsNode
	 * @param tag
	 * @param manager
	 */
	private static void arrangeNodeLabelOptions(State state, TSENode tsNode, String tag, StateMachineDiagramManager manager) {
		if (state instanceof StateEnd
				|| state instanceof StateSimple
				|| state instanceof StateSubmachine) {
			tsNode.discardAllLabels();
			TSENodeLabel label = (TSENodeLabel) tsNode.addLabel();
			((TSEAnnotatedUI) label.getUI()).setTextAntiAliasingEnabled(true);
			label.setName(tag);
			label.setDefaultOffset();
			if ((StateMachineLayoutManager)(manager.getLayoutManager()) != null) {
				((StateMachineLayoutManager)(manager.getLayoutManager())).setNodeLabelOptions(label);
			}
		}
	}
	
	/**
	 * @param tsNode
	 * @param isCopy
	 * @param manager
	 */
	private static void updateGUID(TSENode tsNode, boolean isCopy, StateMachineDiagramManager manager){
		StateEntity stateEntity = (StateEntity)tsNode.getUserObject();
		if (isCopy) {
			updateStateGUID(stateEntity, manager.getGUIDGraphMap());
		} else {
			if (manager.getGUIDGraphMap().containsKey(stateEntity.getGUID())) {
				manager.getGUIDGraphMap().remove(stateEntity.getGUID());
			}
			updateStateGUID(stateEntity, manager.getGUIDGraphMap());
		}
	}

	
	/**
	 * Clear edges from child graphs
	 * @param node
	 */
	public static void clearEdges(TSENode node) {
		if (node.getChildGraph() != null) {
			node.getChildGraph().edges().clear();
			for (Object cnode : node.getChildGraph().nodes()) {
				clearEdges((TSENode)cnode);
			}
		}
	}
    
	/**
	 * Traverse Nodes to associate State Entity object
	 * @param node
	 * @param listener
	 */
	public static void traverseNodes(TSENode node, StateMachineDiagramChangeListener listener) {
		String path = node.getAttributeValue(STATE_GRAPH_PATH).toString();
		StateEntity stateEntity = listener.getStateGraphPathmap().get(path);
		if (node.getUserObject() == null) {
			node.setUserObject(stateEntity);
		}
		if (node.getChildGraph() != null) {
			if (node.getChildGraph().getUserObject() == null) {
				node.getChildGraph().setUserObject(stateEntity);
			}
			for (Object cnode : node.getChildGraph().nodes()) {
				traverseNodes((TSENode)cnode, listener);
			}
		}
	}

	
    /**
     * Traverse State Path 
     * @param stateEntity
     * @param stateGraphPathmap
     */
    public static void trverseStatePathList(StateEntity stateEntity, String smPath, Map<String, StateEntity> stateGraphPathmap) {
    	String path = getStateGraphPath(stateEntity);
    	if (!stateGraphPathmap.containsKey(path)) {
    		stateGraphPathmap.put(smPath + "/" + path, stateEntity);
    	}
    	if (stateEntity instanceof StateComposite) {
    		StateComposite stateComposite = (StateComposite)stateEntity;
    		for (StateEntity entity : stateComposite.isConcurrentState()? 
    				stateComposite.getRegions():stateComposite.getStateEntities() ) {
    			trverseStatePathList(entity, smPath, stateGraphPathmap);
    		}
    	}
    }
}
