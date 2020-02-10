package com.tibco.cep.studio.ui.statemachine.diagram.utils;

import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.getDefaultStateMachinePropertiesMap;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.addSymbol;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.updateSymbol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

import com.tibco.be.util.BidiMap;
import com.tibco.be.util.GUIDGenerator;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEnd;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateSimple;
import com.tibco.cep.designtime.core.model.states.StateStart;
import com.tibco.cep.designtime.core.model.states.StateSubmachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.studio.common.util.ModelNameUtil;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.statemachine.StateMachinePlugin;
import com.tomsawyer.graph.TSEdge;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSEObject;

/**
 * 
 * @author ggrigore
 *
 */
public class StateMachineUtils extends StateMachineConstants {

	public enum STATE {
		START, END, SIMPLE, COMPOSITE, CONCURRENT, SUBSTATEMACHINE, REGION, TRANSITION
	}

	public static String STATE_GRAPH_PATH= "StateGraphPath";
	public static boolean overlapNodesFixed = true;
	 public static boolean UNDO_REDO=false;
	 public static boolean is_UNDO=false;
	 public static boolean is_REDO=false;
	/**
	 * @param graph
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getNodeName(TSEGraph graph,String uniqueStateIdentifier) {
		List<TSENode> nodes = graph.nodes();
		List<Integer> noList = new ArrayList<Integer>(); 
		try{

			for(TSENode node:nodes) {
				if(node.getText()!=null) {
					if(node.getText().startsWith(uniqueStateIdentifier)) {
						String no = node.getText().substring(node.getText().indexOf("_")+1);
						noList.add(getValidNo(no));
					}
				}
			}
			if(noList.size()>0) {
				java.util.Arrays.sort(noList.toArray());
				
				int no = getLargest(noList);no++;
				return uniqueStateIdentifier+no;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return uniqueStateIdentifier+0;
	}
	
	private static int getLargest(List<Integer> noList) {
		int largest=-1;
		int i=0;
		while(i!=noList.size()){
			if(noList.get(i)>largest){
				largest=noList.get(i);
			}
			i++;
		}
		return largest;
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
				java.util.Arrays.sort(noList.toArray());
				int no = noList.get(noList.size()-1).intValue();no++;
				return tag+no;
			}
		}
	
		return tag+0;
	}
	
	/**
	 * @param statemachine
	 * @param uniqueStateIdentifier
	 * @return
	 */
	public static String getEdgeName(StateMachine statemachine,String uniqueStateIdentifier) {
		EList<StateTransition> transitions = statemachine.getStateTransitions();
		List<Integer> noList = new ArrayList<Integer>(); 
		try {
			for (StateTransition transition:transitions) {
				if (transition.getName()!=null) {
					if (transition.getName().startsWith(uniqueStateIdentifier)) {
						String no = transition.getName().substring(transition.getName().indexOf("_") + 1);
						noList.add(getValidNo(no));
					}
				}
			}
			if (noList.size() > 0) {
				int no = Collections.max(noList);
				no++;
				return uniqueStateIdentifier + no;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return uniqueStateIdentifier+0;
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
	public static boolean isStatePresent(EList<StateEntity> entities,String name) {
		for(StateEntity entity: entities) {
			if(entity.getName().equalsIgnoreCase(name)) {
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
	 * @param editingDomain
	 * @param graph
	 * @param state
	 * @param tag
	 * @param isRegion
	 */
	public static void execute(AbstractSaveableEntityEditorPart editor, 
			                   TSEGraph graph, 
			                   Object state, 
			                   String tag, boolean isRegion) {
		if(isRegion) {
			if(!isRegionPresent(((StateComposite)graph.getUserObject()).getRegions(), tag)) {
				AddCommand addCommand = new AddCommand(	editor.getEditingDomain(),((StateComposite)graph.getUserObject()).getRegions(),state);
				EditorUtils.executeCommand(editor, addCommand, true);
			}
		}else{
			if(!isStatePresent(((StateComposite)graph.getUserObject()).getStateEntities(), tag)) {
				AddCommand addCommand = new AddCommand(	editor.getEditingDomain(),((StateComposite)graph.getUserObject()).getStateEntities(),state);
				EditorUtils.executeCommand(editor, addCommand, true);
			}
		}
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
	if(entity!=null){
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
	}}
	
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
	 * @param stateEntity
	 */
	public static void removeTransitions(StateEntity stateEntity, StateMachine stateMachine) {
		if(stateEntity != null) {
			if (stateEntity instanceof StateComposite) {
				StateComposite sc = (StateComposite)stateEntity;
				for (StateEntity s : sc.getStateEntities()) {
					if(s instanceof State) {
						for(StateTransition transtion:((State)s).getIncomingTransitions()) {
							stateMachine.getStateTransitions().remove(transtion);
						}
						for(StateTransition transtion:((State)s).getOutgoingTransitions()) {
							stateMachine.getStateTransitions().remove(transtion);
						}
						((State)s).getIncomingTransitions().clear();
						((State)s).getOutgoingTransitions().clear();
					}
					
					if(s instanceof StateComposite) {
						removeTransitions(s, stateMachine);
					}
				}
				if (sc.isConcurrentState()) {
					EList<StateComposite> regions = sc.getRegions();
					for (StateComposite region : regions) {
						removeTransitions(region, stateMachine);
					}
				}
			} 
		}
	}
	
	/**
	 * @param stateEntity
	 * @param projectName
	 * @param ownerStateModels
	 */
	public static void getCallStateModelOwners(StateEntity stateEntity, String projectName, Set<String> ownerStateModels) {
		if(stateEntity != null) {
			if (stateEntity instanceof StateComposite) {
				StateComposite sc = (StateComposite)stateEntity;
				addOwnerStateModels(sc.isConcurrentState()? sc.getRegions() : sc.getStateEntities(), projectName, ownerStateModels);
			} 
		}
	}


	/**
	 * @param list
	 * @param projectName
	 * @param ownerStateModels
	 */
	public static void addOwnerStateModels(EList<? extends StateEntity> list , String projectName, Set<String> ownerStateModels) {
		for (StateEntity s : list) {
			if(s instanceof StateSubmachine) {
				StateSubmachine subStateMachine = (StateSubmachine)s;
				if(subStateMachine.getOwnerStateMachine()!= null) {
					ownerStateModels.add(subStateMachine.getOwnerStateMachine().getFullPath());
				}
			}
			if(s instanceof StateComposite) {
				getCallStateModelOwners(s, projectName, ownerStateModels);
			}
		}
	}
	
	/**
	 * @param map
	 * @param nodes
	 */
	public static void addToEditGraphMap(Map<String, Object> map, List<TSENode> nodes,  List<TSEEdge> selectedEdges,StateMachine stateMachine, boolean isCopy) {
		map.clear();
		for(TSENode node: nodes) {
			StateEntity entity = (StateEntity)node.getUserObject();
			try {
				StateEntity newEntity = (StateEntity)EcoreUtil.copy(entity);
				if(newEntity instanceof StateComposite) {
					removeTransitions(newEntity, stateMachine, isCopy);
				}
				map.put(entity.getName(), newEntity);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//For Selected Edges
		for(TSEEdge edge: selectedEdges) {
			StateTransition transition = (StateTransition)edge.getUserObject();
			try {
				StateTransition newTransition = (StateTransition)EcoreUtil.copy(transition);
				map.put(transition.getName(), newTransition);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param stateEntity
	 */
	public static void removeTransitions(StateEntity stateEntity, StateMachine stateMachine, boolean isCopy) {
		if(stateEntity != null) {
			if (stateEntity instanceof StateComposite) {
				StateComposite sc = (StateComposite)stateEntity;
				for (StateEntity s : sc.getStateEntities()) {
					if(s instanceof State) {
						if(!isCopy) {
							for(StateTransition transtion:((State)s).getIncomingTransitions()) {
								stateMachine.getStateTransitions().remove(transtion);
							}
							for(StateTransition transtion:((State)s).getOutgoingTransitions()) {
								stateMachine.getStateTransitions().remove(transtion);
							}
							((State)s).getIncomingTransitions().clear();
							((State)s).getOutgoingTransitions().clear();
						}
					}
					if(s instanceof StateComposite) {
						removeTransitions(s, stateMachine, isCopy);
					}
				}
				if (sc.isConcurrentState()) {
					EList<StateComposite> regions = sc.getRegions();
					for (StateComposite region : regions) {
						removeTransitions(region, stateMachine, isCopy);
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
	
	
	public static void setNodeTooltip(TSENode node, StateStart state) {
		StringBuffer tooltip = new StringBuffer();
		String codeString = null;
		tooltip.append(createTooltip(state));
		codeString = state.getExitAction().getActionText();
		if (codeString.length() > 0) {
			tooltip.append("<p><b>Exit Action:</b><p>" + codeString);
		}
		node.setTooltipText(tooltip.toString());
	}

	public static void setNodeTooltip(TSENode node, StateEnd state) {
		StringBuffer tooltip = new StringBuffer();
		String codeString = null;
		tooltip.append(createTooltip(state));
		codeString = state.getEntryAction().getActionText();
		if (codeString.length() > 0) {
			tooltip.append("<p><b>Entry Action:</b><p>" + codeString);
		}
		node.setTooltipText(tooltip.toString());
	}

	public static void setNodeTooltip(TSENode node, State state, boolean isSimple) {
		StringBuffer tooltip = new StringBuffer();
		String codeString = null;
		tooltip.append(createTooltip(state));

		codeString = state.getEntryAction().getActionText();
		if (codeString.length() > 0) {
			tooltip.append("<p><b>Entry Action:</b><p>" + codeString);
		}

		codeString = state.getExitAction().getActionText();
		if (codeString.length() > 0) {
			tooltip.append("<p><b>Exit Action:</b><p>" + codeString);
		}

		if (state.getTimeoutState() != null) {
			tooltip.append("<p><b>Timeout State: </b>" + state.getTimeoutState().getName());
		}
		
		codeString = state.getTimeoutExpression().getActionText();
		if (codeString.length() > 0) {		
			tooltip.append("<p><b>Timeout Expression:</b><p>" + codeString);
		}
		
		codeString = state.getTimeoutAction().getActionText();
		if (codeString.length() > 0) {		
			tooltip.append("<p><b>Timeout Action:</b><p>" + codeString);
		}
		node.setTooltipText(tooltip.toString());
	}

	public static void setNodeTooltip(TSENode node, StateComposite state) {
		
		if (state.isRegion()) {
			StringBuffer tooltip = new StringBuffer();
			String codeString = null;
			tooltip.append(createTooltip(state));

			if (state.getTimeoutState() != null) {
				tooltip.append("<p><b>Timeout State: </b>" + state.getTimeoutState().getName());
			}
			
			codeString = state.getTimeoutExpression().getActionText();
			if (codeString.length() > 0) {		
				tooltip.append("<p><b>Timeout Expression:</b><p>" + codeString);
			}
			
			codeString = state.getTimeoutAction().getActionText();
			if (codeString.length() > 0) {		
				tooltip.append("<p><b>Timeout Action:</b><p>" + codeString);
			}
			node.setTooltipText(tooltip.toString());
		}
		else {
			setNodeTooltip(node, (State)state, true);
		}
	}
	
	public static void setNodeTooltip(TSENode node, StateSubmachine state) {
		StringBuffer tooltip = new StringBuffer();
		String codeString = null;
		tooltip.append(createTooltip(state));
		codeString = state.getExitAction().getActionText();
		if (codeString.length() > 0) {
			tooltip.append("<p><b>Exit Action:</b><p>" + codeString);
		}
		codeString = state.getOwnerStateMachinePath();
		if(codeString!=null){
			if (codeString.length() > 0) {
				tooltip.append("<p><b>Owner StateMachine:</b><p>" + codeString);
			}
		}
		node.setTooltipText(tooltip.toString());
	}

	public static void setEdgeTooltip(TSEEdge edge, StateTransition transition) {
		StringBuffer tooltip = new StringBuffer();
		String codeString = null;
		
		codeString = transition.getGuardRule().getConditionText();
		if (codeString.length() > 0) {
			tooltip.append("<b>Conditions:</b><p>" + codeString);
		}
		
		codeString = transition.getGuardRule().getActionText();
		if (codeString.length() > 0) {
			tooltip.append("<p><b>Actions:</b><p>" + codeString);
		}
		edge.setTooltipText(tooltip.toString());
	}	
	
	
	/**
	 * @param tseNode
	 * @param nameText
	 */
	public static boolean doStateNameValidation(TSENode tseNode, Text nameText) {
		String name = nameText.getText().trim();
		nameText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
		nameText.setToolTipText("");
		//verifying valid BE identifiers
		if(!ModelNameUtil.isValidIdentifier(name)) {
			nameText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
			String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("BE_State_invalidname", name);
			nameText.setToolTipText(problemMessage);
			return false;
		}
		
		//verifying duplicate names
		if(tseNode !=null) {
			StateComposite stateComposite = (StateComposite)tseNode.getOwnerGraph().getUserObject();
			String oldName = ((State)tseNode.getUserObject()).getName();
			if(!name.trim().equalsIgnoreCase(oldName)) {
				if(stateComposite.isConcurrentState()) {
					if(isRegionPresent(stateComposite.getRegions(), name)) {
						nameText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
						String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("BE_State_duplicatename", name);
						nameText.setToolTipText(problemMessage);
						return false;
					}
				}
				if(isStatePresent(stateComposite.getStateEntities(), name)) {
					nameText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
					String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("BE_State_duplicatename", name);
					nameText.setToolTipText(problemMessage);
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * @param se
	 * @param conceptPath
	 * @param projectName
	 */
	public static void updateStateMachineRuleSymbols(StateEntity se, String conceptPath, String projectName) {
		if (se instanceof StateSubmachine) {
			StateSubmachine ssm = (StateSubmachine)se;
			updateStateRulesSymbols(ssm, conceptPath, projectName);
		} 
		else if (se instanceof StateComposite) {
			StateComposite sc = (StateComposite)se;
			updateStateRulesSymbols(sc, conceptPath, projectName);
			for (StateEntity s : sc.getStateEntities()) {
				updateStateMachineRuleSymbols(s, conceptPath, projectName);
			}
			if(((StateComposite) se).isConcurrentState()) {
				for (StateEntity s : sc.getRegions()) {
					updateStateMachineRuleSymbols(s, conceptPath, projectName);
				}
			}
		} else if (se instanceof StateStart) {
			StateStart start = (StateStart)se;
			if(start.getExitAction() != null) {
//				start.getExitAction().getSymbols().getSymbolMap().clear();
//				addSymbol(conceptPath, projectName, start.getExitAction().getSymbols().getSymbolMap());
				if(start.getExitAction().getSymbols().getSymbolMap().size()==0){
					addSymbol(conceptPath, projectName, start.getExitAction().getSymbols().getSymbolMap());
				}else if((start.getExitAction().getSymbols().getSymbolList()).size()>0)
				updateSymbol(conceptPath, projectName, start.getExitAction().getSymbols().getSymbolList().get(0));
			}
			
		} else if (se instanceof StateEnd) {
			StateEnd end = (StateEnd)se;
			if(end.getEntryAction()!= null) {
//				end.getEntryAction().getSymbols().getSymbolMap().clear();
//				addSymbol(conceptPath, projectName, end.getEntryAction().getSymbols().getSymbolMap());
				if(end.getEntryAction().getSymbols().getSymbolMap().size()==0){
					addSymbol(conceptPath, projectName, end.getEntryAction().getSymbols().getSymbolMap());
				}else if((end.getEntryAction().getSymbols().getSymbolList()).size()>0)
				updateSymbol(conceptPath, projectName, end.getEntryAction().getSymbols().getSymbolList().get(0));
			}
			
		} else if (se instanceof State) {			
			State s = (State)se;
			updateStateRulesSymbols(s, conceptPath, projectName);
		}
	}
	
	/**
	 * @param sm
	 * @param oldConceptPath
	 * @param newConceptPath
	 * @param projectName
	 */
	public static void updateStateTransitionRulesSymbols(StateMachine sm, String oldConceptPath, String newConceptPath, String projectName) {
		for (StateTransition stateTransition : sm.getStateTransitions()) {
			if(stateTransition.getGuardRule() != null ) {
				//removeTransitionRule(stateTransition, oldConceptPath);
				//addSymbol(newConceptPath, projectName, stateTransition.getGuardRule().getSymbols().getSymbolMap());
				for(Symbol symbol:stateTransition.getGuardRule().getSymbols().getSymbolList()) {
					if(symbol.getType().equals(oldConceptPath)) {
						Entity entity =IndexUtils.getEntity(projectName, newConceptPath);
						symbol.setType(newConceptPath);
						symbol.setIdName(entity.getName().toLowerCase());
					}
				}
			}
		}
	}
	
	/**
	 * @param s
	 * @param conceptPath
	 * @param projectName
	 */
	public static void updateStateRulesSymbols(State s, String conceptPath, String projectName) {
		if (s.isInternalTransitionEnabled()) {
			if(s.getInternalTransitionRule() !=null) {
//				s.getInternalTransitionRule().getSymbols().getSymbolMap().clear();
//				addSymbol(conceptPath, projectName, s.getInternalTransitionRule().getSymbols().getSymbolMap());
				//System.out.println(s.getInternalTransitionRule().getSymbols().getSymbolMap().size());
				if(s.getInternalTransitionRule().getSymbols().getSymbolMap().size()==0){
					addSymbol(conceptPath, projectName, s.getInternalTransitionRule().getSymbols().getSymbolMap());
				}else if((s.getInternalTransitionRule().getSymbols().getSymbolList()).size()>0)
				updateSymbol(conceptPath, projectName, s.getInternalTransitionRule().getSymbols().getSymbolList().get(0));
			}
		}
		if(s.getEntryAction()!=null) {
//			s.getEntryAction().getSymbols().getSymbolMap().clear();
//			addSymbol(conceptPath, projectName, s.getEntryAction().getSymbols().getSymbolMap());
			//System.out.println(s.getEntryAction().getSymbols().getSymbolMap().size());
			if(s.getEntryAction().getSymbols().getSymbolMap().size()==0){
				addSymbol(conceptPath, projectName, s.getEntryAction().getSymbols().getSymbolMap());
			}else if((s.getEntryAction().getSymbols().getSymbolList()).size()>0)
			updateSymbol(conceptPath, projectName, s.getEntryAction().getSymbols().getSymbolList().get(0));
		}
		if(s.getExitAction() != null) {
//			s.getExitAction().getSymbols().getSymbolMap().clear();
//			addSymbol(conceptPath, projectName, s.getExitAction().getSymbols().getSymbolMap());
			if(s.getExitAction().getSymbols().getSymbolMap().size()==0){
				addSymbol(conceptPath, projectName, s.getExitAction().getSymbols().getSymbolMap());
			}else if((s.getExitAction().getSymbols().getSymbolList()).size()>0)
			updateSymbol(conceptPath, projectName, s.getExitAction().getSymbols().getSymbolList().get(0));
		}
		if(s.getTimeoutAction() !=null) {
//			s.getTimeoutAction().getSymbols().getSymbolMap().clear();
//			addSymbol(conceptPath, projectName, s.getTimeoutAction().getSymbols().getSymbolMap());
			if(s.getTimeoutAction().getSymbols().getSymbolMap().size()==0){
				addSymbol(conceptPath, projectName, s.getTimeoutAction().getSymbols().getSymbolMap());
			}else if((s.getTimeoutAction().getSymbols().getSymbolList()).size()>0)
			updateSymbol(conceptPath, projectName, s.getTimeoutAction().getSymbols().getSymbolList().get(0));
		}
		if(s.getTimeoutExpression()!=null) {
//			s.getTimeoutExpression().getSymbols().getSymbolMap().clear();
//			addSymbol(conceptPath, projectName, s.getTimeoutExpression().getSymbols().getSymbolMap());
			if(s.getTimeoutExpression().getSymbols().getSymbolMap().size()==0){
				addSymbol(conceptPath, projectName, s.getTimeoutExpression().getSymbols().getSymbolMap());
			}else if((s.getTimeoutExpression().getSymbols().getSymbolList()).size()>0)
			updateSymbol(conceptPath, projectName, s.getTimeoutExpression().getSymbols().getSymbolList().get(0));
		}
	}
	
	/**
	 * @param selElement
	 * @return
	 */
	public static String getText(TSEObject selElement) {
		String text = null;
		StateEntity state = null;
		if (selElement instanceof TSEGraph) {
			TSEGraph  tSEGraph = (TSEGraph) selElement;
			Object object= tSEGraph.getUserObject();
			if(object instanceof StateMachine) {
				StateMachine stateMachine = (StateMachine)object;
				return getStateTitle(stateMachine, stateMachine.getName());
			}
			if (object instanceof StateComposite) {
				return getStateTitle((StateComposite)object, ((StateComposite)object).getName()) ;
			}
			return "Unknown State Machine";
		}
		if (selElement instanceof TSENode) {   
			TSENode  tSENode = (TSENode) selElement;
			if(tSENode.getUserObject() !=null) {
				text = ((State)tSENode.getUserObject()).getName();
				state = (State)tSENode.getUserObject();
			}
		}
		if (selElement instanceof TSEdge) {   
			TSEdge  tSEdge = (TSEdge) selElement;
			if(tSEdge.getUserObject() !=null) {
				text = ((StateTransition)tSEdge.getUserObject()).getLabel();
				state = (StateTransition)tSEdge.getUserObject();
			}
		}
		return text != null ? getStateTitle(state, text) : ""/*super.getText(element)*/;
	}

	/**
	 * @param state
	 * @param label
	 * @return
	 */
	private static String getStateTitle(StateEntity state, String label) {
		if(state instanceof StateMachine) {
			return "State Model: "+label;
		}
		if (state instanceof StateSimple) {
			return "Simple State: "+label;
		}
		if (state instanceof StateComposite) {
			StateComposite stateComposite = (StateComposite)state;
			if(stateComposite.isRegion()) {
				return "Region: "+label;
			}
			if(stateComposite.isConcurrentState()) {
				return "Concurrent State: "+label;
			}
			if(stateComposite instanceof StateSubmachine) {
				return "Call State Model: "+label;
			}
			return "Composite State: "+label;
		}
		if (state instanceof StateStart) {
			return "Start State: "+label;
		}
		if (state instanceof StateEnd) {
			return "End State: "+label;
		}
		if (state instanceof StateTransition) {
			return "Transition ("+ state.getName()+"): " +label;
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
			if(object instanceof StateMachine) {
				return StudioUIPlugin.getDefault().getImage("icons/state_machine.png");
			}
			if (object instanceof StateComposite) {
				if(((StateComposite)object).isRegion())
					return StateMachinePlugin.getDefault().getImage("icons/region.png");
				if(((StateComposite)object).isConcurrentState())
					return StudioUIPlugin.getDefault().getImage("icons/concurrent.png");
				return StudioUIPlugin.getDefault().getImage("icons/composite.png");
			}
		}
		if (element instanceof TSENode) {
			TSENode  tSENode = (TSENode) element;
			Object object= tSENode.getUserObject();
			if (object instanceof StateSimple) {
				return StudioUIPlugin.getDefault().getImage("icons/SimpleState.png");
			}
			if (object instanceof StateSubmachine) {
				return StudioUIPlugin.getDefault().getImage("icons/sub_machinestate.png");
			}
			if (object instanceof StateComposite) {
				if(((StateComposite)object).isRegion())
					return StateMachinePlugin.getDefault().getImage("icons/region.png");
				if(((StateComposite)object).isConcurrentState())
					return StudioUIPlugin.getDefault().getImage("icons/concurrent.png");

				return StudioUIPlugin.getDefault().getImage("icons/composite.png");
			}
			if (object instanceof StateStart) {
				return StudioUIPlugin.getDefault().getImage("icons/StartState.png");
			}
			if (object instanceof StateEnd) {
				return StudioUIPlugin.getDefault().getImage("icons/EndState.png");
			}
			if (object instanceof State) {
				return StudioUIPlugin.getDefault().getImage("icons/SimpleState.png");
			} 
		}
		if (element instanceof TSEdge) {   
			return StateMachineImages.getImage(StateMachineImages.SM_PALETTE_TRANSITION_STATE);
		}
		return img;
	}
	
	/**
	 * @param stateMachine
	 * @param state
	 * @param projectName
	 */
	public static void addStateRule(StateMachine stateMachine, 
							        State state,
							        String projectName) {
		if (state instanceof StateSubmachine) {
			return;
		}
		Rule rule = RuleFactory.eINSTANCE.createRule();
		
		RuleFunction timeoutExpression = null;
		
		if (state instanceof StateStart) {
			addCompilable(rule, stateMachine, state, projectName,  StatesPackage.eINSTANCE.getState_ExitAction().getName());
			state.setExitAction(rule);
			timeoutExpression = ModelUtils.generateDefaultTimeoutExpression(state);
			addCompilable(timeoutExpression, 
					      stateMachine, 
					      state, 
					      projectName,  
					      StatesPackage.eINSTANCE.getState_TimeoutExpression().getName());
			state.setTimeoutExpression(timeoutExpression);
			return;
		}
		if (state instanceof StateEnd) {
			rule = RuleFactory.eINSTANCE.createRule();
			addCompilable(rule, stateMachine, state, projectName,  StatesPackage.eINSTANCE.getState_EntryAction().getName());
			timeoutExpression = ModelUtils.generateDefaultTimeoutExpression(state);
			addCompilable(timeoutExpression, 
					      stateMachine, 
					      state, 
					      projectName,  
					      StatesPackage.eINSTANCE.getState_TimeoutExpression().getName());
			state.setTimeoutExpression(timeoutExpression);
			state.setEntryAction(rule);
			return;
		}
		boolean isRegion = false;
        if(state instanceof StateComposite) {
        	StateComposite stateComposite = (StateComposite)state;
        	if(stateComposite.isRegion()) {
        		isRegion = true;
        	}
        }
        if(!isRegion) {
        	rule = RuleFactory.eINSTANCE.createRule();
        	addCompilable(rule, stateMachine, state, projectName,  State_EntryAction_Name);
        	state.setEntryAction(rule);
        }
        if(!isRegion) {
        	rule = RuleFactory.eINSTANCE.createRule();
        	addCompilable(rule, stateMachine, state, projectName,  State_ExitAction_Name);
        	state.setExitAction(rule);
        }
		rule = RuleFactory.eINSTANCE.createRule();
		addCompilable(rule, stateMachine, state, projectName,  State_TimeoutAction_Name);
		state.setTimeoutAction(rule);
		RuleFunction ruleFunction = ModelUtils.generateDefaultTimeoutExpression(state);
//		RuleFunction ruleFunction = RuleFactory.eINSTANCE.createRuleFunction();
		addCompilable(ruleFunction, stateMachine, state, projectName,  State_TimeoutExpression_Name);
		state.setTimeoutExpression(ruleFunction);

	}
	
	/**
	 * @param stateMachine
	 * @param state
	 * @param projectName
	 */
	public static void addTransitionRule(StateMachine stateMachine, 
							        StateTransition state,
							        String projectName) {
		Rule rule = RuleFactory.eINSTANCE.createRule();
		rule.setOwnerProjectName(projectName);
		rule.setPriority(5);
		rule.setName(stateMachine.getName() +"_"+ state.getName()+ "_" + StateTransition_GuardRule_Name);
		addSymbol(stateMachine.getOwnerConceptPath(), stateMachine.getOwnerProjectName(), rule.getSymbols().getSymbolMap());
		state.setGuardRule((Rule)rule);
	}
	
	/**
	 * @param rule
	 * @param stateMachine
	 * @param state
	 * @param projectName
	 * @param feature
	 */
	public static void addCompilable(Compilable rule, 
			                   StateMachine stateMachine, 
			                   State state,
			                   String projectName, 
			                   String feature) {
		rule.setOwnerProjectName(projectName);
		if (state instanceof StateMachine) {
			rule.setName(stateMachine.getName() +"_"+ feature);
		} else {
			rule.setName(stateMachine.getName() + "_" + state.getName() + "_" + feature);
		}
		addSymbol(stateMachine.getOwnerConceptPath(), stateMachine.getOwnerProjectName(), rule.getSymbols().getSymbolMap());
	}
	
		
	/**
	 * Default extended Properties for State Entity Nodes
	 * @param entity
	 */
	public static void setDefaultExtendedProperties(Entity entity) {
		try {
			entity.setExtendedProperties(getDefaultStateMachinePropertiesMap());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}