package com.tibco.cep.decision.tree.ui.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.swt.graphics.Image;

import com.tibco.be.util.BidiMap;
import com.tibco.be.util.GUIDGenerator;
import com.tibco.cep.decision.tree.common.model.DecisionTree;
import com.tibco.cep.decision.tree.common.model.FlowElement;
import com.tibco.cep.decision.tree.common.model.edge.Edge;
import com.tibco.cep.decision.tree.common.model.node.NodeElement;
import com.tibco.cep.decision.tree.common.model.node.action.Action;
import com.tibco.cep.decision.tree.common.model.node.condition.BoolCond;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEnd;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateStart;
import com.tibco.cep.designtime.core.model.states.StateSubmachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tomsawyer.graph.TSEdge;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;

/*
@author ssailapp
@date Sep 14, 2011
 */

public class DecisionTreeUiUtil extends DecisionTreeConstants {

	public static boolean overlapNodesFixed = true;
	public static boolean UNDO_REDO=false;
	public static boolean is_UNDO=false;
	public static boolean is_REDO=false;
	
	/**
	 * @param graph
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getNodeName(TSEGraph graph, String uniqueNodeIdentifier) {
		List<TSENode> nodes = graph.nodes();
		List<Integer> noList = new ArrayList<Integer>(); 
		try{

			for(TSENode node:nodes) {
				if(node.getText()!=null) {
					if(node.getText().startsWith(uniqueNodeIdentifier)) {
						String no = node.getText().substring(node.getText().indexOf("_")+1);
						noList.add(getValidNo(no));
					}
				}
			}
			if (noList.size()>0) {
				int no = Collections.max(noList);
				no++;
				return uniqueNodeIdentifier+no;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return uniqueNodeIdentifier+0;
	}
	
	/**
	 * @param tag
	 * @return
	 */
	public static String getUniqueTag(String tag) {
		List<Integer> noList = new ArrayList<Integer>(); 
		if(tag.endsWith("_") && tag.lastIndexOf("_") != -1) {
			String sno = tag.substring(tag.lastIndexOf("_")+1);
			noList.add(getValidNo(sno));
			if(noList.size()>0) {
				int no = noList.get(noList.size()-1).intValue();no++;
				return tag+no;
			}
		}
	
		return tag+0;
	}
	
	@SuppressWarnings("unchecked")
	public static String getEdgeName(TSEGraph graph, String uniqueEdgeIdentifier) {
		List<TSEEdge> edges = graph.edges();
		List<Integer> noList = new ArrayList<Integer>(); 
		try {
			for(TSEEdge node:edges) {
				if(node.getText()!=null) {
					if(node.getText().startsWith(uniqueEdgeIdentifier)) {
						String no = node.getText().substring(node.getText().indexOf("_")+1);
						noList.add(getValidNo(no));
					}
				}
			}
			if(noList.size()>0) {
				int no = Collections.max(noList);
				no++;
				return uniqueEdgeIdentifier+no;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return uniqueEdgeIdentifier+0;
	}
	
	
	/**
	 * @param no
	 * @return
	 */
	public static int getValidNo(String no) {
		int n;
		String no1;
		try{
			int i=0;
			
			while((no.length()>i)&& no.charAt(i)!= '_' )
			{
				i++;
			}
			
			no1=no.substring(0,i);
			
			n = Integer.parseInt(no1); 
			
		}catch(Exception e) {
			return 0;
		}
		return n;
	}
	
	/**
	 * @param entities
	 * @param name
	 * @return
	 */
	public static boolean isStateStartPresent(EList<StateEntity> entities,String name) {
		for(StateEntity entity: entities) {
			if(entity instanceof StateStart) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param entities
	 * @param name
	 * @return
	 */
	public static boolean isStateEndPresent(EList<StateEntity> entities,String name) {
		for(StateEntity entity: entities) {
			if(entity instanceof StateEnd) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param entities
	 * @param name
	 * @return
	 */
	public static int getStateEndSize(EList<StateEntity> entities) {
		int i = 0;
		for(StateEntity entity: entities) {
			if(entity instanceof StateEnd) {
				i++;
			}
		}
		return i;
	}
	
	/**
	 * @param entities
	 * @param name
	 * @return
	 */
	public static int getRegionSize(EList<StateEntity> entities) {
		int i = 0;
		for(StateEntity entity: entities) {
			if(entity instanceof StateComposite) {
				i++;
			}
		}
		return i;
	}
	
	
	/**
	 * @param entities
	 * @param name
	 * @return
	 */
	public static boolean isTransitionPresent(EList<StateTransition> entities,String name) {
		for(StateTransition entity: entities) {
			if(entity.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param entities
	 * @param label
	 * @return
	 */
	public static boolean isTransitionLabelPresent(EList<StateTransition> entities,String label) {
		for(StateTransition entity: entities) {
			if(entity.getLabel().equalsIgnoreCase(label)) {
				return true;
			}
		}
		return false;
	}

	
	/**
	 * @param editor
	 * @param owner
	 * @param feature
	 * @param value
	 */
	public static void execute(AbstractSaveableEntityEditorPart editor, 
			                   EObject owner, 
			                   EStructuralFeature feature, 
			                   Object value) {
    	Command command = new SetCommand( editor.getEditingDomain(), owner, feature,value) ;			
    	EditorUtils.executeCommand(editor, command);
	}
	
	
	/**
	 * @param entities
	 * @param name
	 * @return
	 */
	public static boolean isRegionPresent(EList<StateComposite> entities,String name) {
		for(StateComposite entity: entities) {
			if(entity.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method checks for duplicate GUID
	 * @param GUID
	 * @param map
	 * @param object
	 * @return
	 */
	public static String getGUID(String GUID, 
			                     BidiMap<String, TSEObject> map, 
			                     TSEObject object) {
		synchronized (map) {
			while (map.keySet().contains(GUID)) {
				GUID = GUIDGenerator.getGUID();
			} 
			map.put(GUID, object);
			return GUID;
		}
	}
	
	/**
	 * @param newGUID
	 * @param oldGUID
	 * @param map
	 * @return
	 */
	public static String regenerateGUID(String newGUID, 
			 							String oldGUID,
			                            BidiMap<String, TSEObject> map) {
		synchronized (map) {
			if (map.containsKey(oldGUID)) {
				map.remove(oldGUID);
			}
			while (map.keySet().contains(newGUID)) {
				newGUID = GUIDGenerator.getGUID();
			} 
			return newGUID;
		}
	}
	
	/**
	 * @param GUID
	 * @param map
	 */
	public static void removeGUID(String GUID, BidiMap<String, TSEObject> map) {
		synchronized (map) {
			if (map.keySet().contains(GUID)) {
				map.remove(GUID);
			} 
		}
	}

	/**
	 * @param entity
	 * @param map
	 */
	public static void removeGUID(StateEntity entity, BidiMap<String, TSEObject> map) {
		removeGUID(entity.getGUID(), map);
		if (entity instanceof StateComposite) {
			StateComposite stateComposite = (StateComposite)entity;
			if (stateComposite.isConcurrentState()) {
				for (StateEntity region:stateComposite.getRegions()) {
					removeGUID(region, map);
				}
			} else {
				for (StateEntity ent:stateComposite.getStateEntities()) {
					removeGUID(ent, map);
				}
			}
		}
	}
	
	/**
	 * @param stateEntity
	 * @param map
	 */
	public static void updateStateGUID(StateEntity stateEntity, BidiMap<String, TSEObject> map) {
		if(stateEntity != null) {
			stateEntity.setGUID(regenerateGUID(GUIDGenerator.getGUID(), stateEntity.getGUID(), map));
			if (stateEntity instanceof StateComposite) {
				StateComposite sc = (StateComposite)stateEntity;
				if (sc.isConcurrentState()) {
					for (StateEntity s : sc.getRegions()) {
						updateStateGUID(s, map);
					}
				} else {
					for (StateEntity s : sc.getStateEntities()) {
						updateStateGUID(s, map);
					}
				}
			} 
		}
	}
	
	/**
	 * @param node
	 * @return
	 */
	public static String createTooltip(State state) {
		if(state == null) return "";
		StringBuffer tooltip = new StringBuffer();
		if(state.getName() !=null) {
			tooltip.append("<p><b>Name: </b>");
			tooltip.append(state.getName());
			tooltip.append("</p>");
		}
		if(state.getDescription()!=null && !state.getDescription().trim().equalsIgnoreCase("")) {
			tooltip.append("<p><b>Description: </b>");
			tooltip.append(state.getDescription());
			tooltip.append("</p>");
		}
		if(!(state instanceof StateStart) && !(state instanceof StateEnd) && !(state instanceof StateSubmachine)) {
			if(state.getTimeout()!= -1) {
				tooltip.append("<p><b>Timeout Units: </b>");
				tooltip.append(state.getTimeoutUnits().getName());
//				tooltip.append(state.getTimeout()+ " " + state.getTimeoutUnits().getName());
				tooltip.append("</p>");
			}
		}
		return tooltip.toString();
	}
	
		
	/**
	 * @param selElement
	 * @return
	 */
	public static String getText(TSEObject selElement) {
		String text = null;
		if (selElement instanceof TSEGraph) {
			TSEGraph  tSEGraph = (TSEGraph) selElement;
			Object object = tSEGraph.getUserObject();
			if (object instanceof DecisionTree) {
				DecisionTree decisionTree = (DecisionTree)object;
				return getDecisionTreeTitle(decisionTree, "");
			}
			return "Unknown Decision Tree";
		}
		Object element = null;
		if (selElement instanceof TSENode) {   
			TSENode  tSENode = (TSENode) selElement;
			if(tSENode.getUserObject() !=null) {
				text = ((FlowElement)tSENode.getUserObject()).getName();
				element = (FlowElement)tSENode.getUserObject();
			}
		}
		if (selElement instanceof TSEdge) {   
			TSEdge  tSEdge = (TSEdge) selElement;
			if(tSEdge.getUserObject() !=null) {
				text = ((StateTransition)tSEdge.getUserObject()).getLabel();
				element = (StateTransition)tSEdge.getUserObject();
			}
		}
		return text != null ? getDecisionTreeTitle(element, text): "";
	}

	/**
	 * @param state
	 * @param label
	 * @return
	 */
	private static String getDecisionTreeTitle(Object element, String label) {
		if (element instanceof DecisionTree) {
			return "Decision Tree: "+label;
		} else if (element instanceof BoolCond) {
			return "Boolean Condition: "+label;
		} else if (element instanceof Action) {
			return "Action: "+label;
		}
		return label;
	}
	
	/**
	 * @param element
	 * @return
	 */
	public static Image getImage(Object element) {
		Image img = null;
		if (element instanceof TSEGraph) {
			TSEGraph  tSEGraph = (TSEGraph) element;
			Object object= tSEGraph.getUserObject();
			if (object instanceof DecisionTree) {
				return DecisionTreeImages.getImage(DecisionTreeImages.TREE);
			}
		}
		if (element instanceof TSENode) {
			TSENode  tSENode = (TSENode) element;
			Object object= tSENode.getUserObject();
			if (object instanceof BoolCond) {
				return DecisionTreeImages.getImage(DecisionTreeImages.BOOLEAN_CONDITION);
			}
			if (object instanceof Action) {
				return DecisionTreeImages.getImage(DecisionTreeImages.SIMPLE_NODE);
			}
		}
		if (element instanceof TSEdge) {   
			return DecisionTreeImages.getImage(DecisionTreeImages.TRANSITION);
		}
		return img;
	}
	
		
	/**
	 * Default extended Properties for State Entity Nodes
	 * @param entity
	 */
	public static void setDefaultExtendedProperties(Entity entity) {
		try {
			//entity.setExtendedProperties(getDefaultDecisionTreePropertiesMap());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getTrimmedText(String text) {
		if (text.length() > 20) {
			String newText = text.substring(0, 17) + "..."; // + text.substring(text.length()-3);
			return newText;
		}
		return text;
	}
	
	public static long getUniqueId(DecisionTree tree) {
		ArrayList<Long> ids = new ArrayList<Long>();
		for (NodeElement nodeElement: tree.getNodes()) {
			ids.add(nodeElement.getId());
		}
		for (Edge Edge: tree.getEdges()) {
			ids.add(Edge.getId());
		}
		long maxId = Collections.max(ids);
		return maxId+1;
	}
	
	public static void connectNodes(NodeElement srcElement, NodeElement tgtElement, Edge edge) {
		srcElement.setOutEdge(edge);
		tgtElement.getInEdges().add(edge);
	}
	
	public static void connectNodesFalseEdge(NodeElement srcElement, NodeElement tgtElement, Edge edge) {
		if (srcElement instanceof BoolCond) {
			((BoolCond)srcElement).setFalseEdge(edge);
			tgtElement.getInEdges().add(edge);
		}
	}
	
	public static void removeEdge(DecisionTree decisionTree, NodeElement srcElement, NodeElement tgtElement) {
		for (Iterator<Edge> itr = decisionTree.getEdges().iterator(); itr.hasNext();) {
			Edge curEdge = (Edge) itr.next();
			if (curEdge.getSrc().equals(srcElement) && curEdge.getTgt().equals(tgtElement)) {
				itr.remove();
			}
		}
	}
	
	public static int getOutDegree(NodeElement element) {
		int degree = 0;
		if (element.getOutEdge() != null) {
			degree++;
		}
		if (element instanceof BoolCond) {
			if (((BoolCond)element).getFalseEdge() != null)
				degree++;
		}
		return degree;	
	}
	
	public static int getInDegree(NodeElement element) {
		return element.getInEdges().size();
	}
}