package com.tibco.cep.studio.ui.statemachine.diagram;

import static com.tibco.cep.diagramming.utils.DiagramUtils.refreshDiagram;
import static com.tibco.cep.studio.ui.overview.OverviewUtils.refreshOverview;
import static com.tibco.cep.studio.ui.statemachine.diagram.StateMachineDiagramEditHandler.afterEditNodeAdded;
import static com.tibco.cep.studio.ui.statemachine.diagram.StateMachineDiagramEditHandler.editClipBoardState;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineConstants.STATE_TYPE;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineConstants.UNIQUE_TRANSITION;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.UNDO_REDO;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.addStateRule;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.addTransitionRule;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.createTooltip;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.execute;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.getEdgeName;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.getGUID;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.isTransitionPresent;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.setDefaultExtendedProperties;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.setWorkbenchSelection;

import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.be.util.GUIDGenerator;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEnd;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateSimple;
import com.tibco.cep.designtime.core.model.states.StateStart;
import com.tibco.cep.designtime.core.model.states.StateSubmachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.designtime.core.model.states.StatesFactory;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.statemachine.commands.EdgeAddCommand;
import com.tibco.cep.studio.ui.statemachine.commands.SimpleStateAddCommand;
import com.tibco.cep.studio.ui.statemachine.diagram.editors.StateMachineEditor;
import com.tibco.cep.studio.ui.statemachine.diagram.ui.ConcurrentStateNodeUI;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.STATE;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEEdgeLabel;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEAnnotatedUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;



/**
 * 
 * @author sasahoo
 *
 */
public class StateMachineGraphAddHandler {

	/**
	 * @param tsNode
	 * @param manager
	 * @param listener
	 */
	@SuppressWarnings("unchecked")
	public static void handleNodeAdd(TSENode tsNode, StateMachineDiagramManager manager, StateMachineDiagramChangeListener listener) {
		if (!EditorUtils.checkPermissions((AbstractSaveableEntityEditorPart) manager.getEditor())) {
			return;
		}
		TSEGraph  graph= (TSEGraph)tsNode.getOwnerGraph();
	//	EditingDomain editingDomain = ((StateMachineEditor)manager.getEditor()).getEditingDomain();
		if (manager.isCutGraph() && manager.isPasteGraph()) {
			editClipBoardState(tsNode, manager.getCutMap(), false, manager, listener);
			if (afterEditNodeAdded(tsNode, manager, listener)) {
				return;//For Composite/Concurrent Node cut/paste
			}
		}
		if (manager.isCopyGraph() && manager.isPasteGraph()) {
			editClipBoardState(tsNode, manager.getCopyMap(), true, manager, listener);
			if (afterEditNodeAdded(tsNode, manager, listener)) { 
				return;//For Composite/Concurrent Node copy/paste
			}
		}

		StateMachine stateMachine = manager.getStateMachine();
		String projectName = stateMachine.getOwnerProjectName();

		if (tsNode.getAttributeValue(STATE_TYPE)==null) {
			return;
		}

		//Start State
		else if (tsNode.getAttributeValue(STATE_TYPE).equals(STATE.START)) {
			// System.out.println("Start State added: ");
			StateStart startstate = StatesFactory.eINSTANCE.createStateStart();
			startstate.setName("Start");
			startstate.setGUID(getGUID(GUIDGenerator.getGUID(), manager.getGUIDGraphMap(), tsNode));//setting generated GUID
			setDefaultExtendedProperties(startstate);
			addStateRule(stateMachine, startstate, projectName);
			tsNode.setUserObject(startstate);
			tsNode.setTooltipText(createTooltip(startstate));
			execute((StateMachineEditor)manager.getEditor(), startstate, StatesPackage.eINSTANCE.getStateEntity_OwnerStateMachine(), stateMachine.getFullPath());
			execute((StateMachineEditor)manager.getEditor(), graph, startstate, "Start", false);
		}
		//End State
		else if (tsNode.getAttributeValue(STATE_TYPE).equals(STATE.END)) {
			// System.out.println("End State added: ");
			StateEnd endstate = null;
			if (tsNode.getUserObject() == null) {
				endstate = StatesFactory.eINSTANCE.createStateEnd();
				endstate.setName(tsNode.getText());
				endstate.setGUID(getGUID(GUIDGenerator.getGUID(), manager.getGUIDGraphMap(), tsNode));//setting generated GUID
				addStateRule(stateMachine, endstate, projectName);
				tsNode.setUserObject(endstate);
				tsNode.setTooltipText(createTooltip(endstate));
			} else {
				endstate = (StateEnd)tsNode.getUserObject();
				endstate = (StateEnd)EcoreUtil.copy(endstate);
				tsNode.setUserObject(endstate);
				tsNode.setTooltipText(createTooltip(endstate));
			}
			setDefaultExtendedProperties(endstate);
			execute((StateMachineEditor)manager.getEditor(), endstate, StatesPackage.eINSTANCE.getStateEntity_OwnerStateMachine(), stateMachine.getFullPath());
			execute((StateMachineEditor)manager.getEditor(), graph, endstate, tsNode.getText(),false);
		}
		//Simple State
		else if(tsNode.getAttributeValue(STATE_TYPE).equals(STATE.SIMPLE)) {
		
			StateSimple state = null;
			if(tsNode.getUserObject() == null){
				state = StatesFactory.eINSTANCE.createStateSimple();
				state.setName(tsNode.getText());
				state.setGUID(getGUID(GUIDGenerator.getGUID(), manager.getGUIDGraphMap(), tsNode));//setting generated GUID
				addStateRule(stateMachine, state, projectName);
				tsNode.setUserObject(state);
				tsNode.setTooltipText(createTooltip(state));
				setDefaultExtendedProperties(state);
			} else {
				state = (StateSimple)tsNode.getUserObject();
				state = (StateSimple)EcoreUtil.copy(state);
				tsNode.setUserObject(state);
				tsNode.setTooltipText(createTooltip(state));
			}
			
			AbstractSaveableEntityEditorPart editor= (StateMachineEditor)manager.getEditor();
			if(UNDO_REDO){
				
				 SimpleStateAddCommand simpleStateAddCommand=new SimpleStateAddCommand(manager,
						 editor.getEditingDomain(),((StateComposite)graph.getUserObject()).getStateEntities(),state);
				 EditorUtils.executeCommand(editor, simpleStateAddCommand, true);
			}else{
			execute((StateMachineEditor)manager.getEditor(), state, StatesPackage.eINSTANCE.getStateEntity_OwnerStateMachine(), stateMachine.getFullPath());
			//Adding new model element as User Object  
			execute((StateMachineEditor)manager.getEditor(), graph, state, tsNode.getText(),false);//in memory persistence to editing domain
			//createSimpleState(tsNode,manager,stateMachine,projectName,graph);
			}
		}
		//Composite State
		else if(tsNode.getAttributeValue(STATE_TYPE).equals(STATE.COMPOSITE)) {
			StateComposite compositeState = null;
			TSEGraph compositeGraph = (TSEGraph)tsNode.getChildGraph();
			if(tsNode.getUserObject()==null){
			compositeState = StatesFactory.eINSTANCE.createStateComposite();
			compositeState.setName(tsNode.getText());
			compositeState.setGUID(getGUID(GUIDGenerator.getGUID(), manager.getGUIDGraphMap(), tsNode));//setting generated GUID
			addStateRule(stateMachine, compositeState, projectName);
			setDefaultExtendedProperties(compositeState);
			}else{
				compositeState=(StateComposite) tsNode.getUserObject();
				compositeState=(StateComposite)EcoreUtil.copy(compositeState);
			}
			
			execute((StateMachineEditor)manager.getEditor(), compositeState, StatesPackage.eINSTANCE.getStateEntity_OwnerStateMachine(), stateMachine.getFullPath());

			compositeGraph.setUserObject(compositeState);
			tsNode.setUserObject(compositeState);

			List<TSENode> nodes = compositeGraph.nodes();
			StateStart startstate = null;
			StateEnd endstate = null;

			for (TSENode node : nodes) {
				if (node.getAttributeValue(STATE_TYPE).equals(STATE.START)) {
					startstate = StatesFactory.eINSTANCE.createStateStart();
					startstate.setName("Start");
					startstate.setGUID(getGUID(GUIDGenerator.getGUID(), manager.getGUIDGraphMap(), node));//setting generated GUID
					setDefaultExtendedProperties(startstate);
					execute((StateMachineEditor)manager.getEditor(), startstate, StatesPackage.eINSTANCE.getStateEntity_OwnerStateMachine(), stateMachine.getFullPath());
					addStateRule(stateMachine, startstate, projectName);
					node.setUserObject(startstate);
					node.setTooltipText(createTooltip(startstate));
				}
				if (node.getAttributeValue(STATE_TYPE).equals(STATE.END)) {
					endstate = StatesFactory.eINSTANCE.createStateEnd();
					endstate.setName("End");
					endstate.setGUID(getGUID(GUIDGenerator.getGUID(), manager.getGUIDGraphMap(), node));//setting generated GUID
					setDefaultExtendedProperties(endstate);
					execute((StateMachineEditor)manager.getEditor(), endstate, StatesPackage.eINSTANCE.getStateEntity_OwnerStateMachine(), stateMachine.getFullPath());
					addStateRule(stateMachine, endstate, projectName);
					node.setUserObject(endstate);
					node.setTooltipText(createTooltip(endstate));
				}
			}
			tsNode.setTooltipText(createTooltip(compositeState));

			execute((StateMachineEditor)manager.getEditor(), graph, compositeState, tsNode.getText(),false);
			execute((StateMachineEditor)manager.getEditor(), compositeGraph, startstate, "Start",false);
			execute((StateMachineEditor)manager.getEditor(), compositeGraph, endstate, "End", false);
		}

		//Region
		else if (tsNode.getAttributeValue(STATE_TYPE).equals(STATE.REGION)) {
			TSEGraph regionGraph = (TSEGraph)tsNode.getChildGraph();
			StateComposite region=null;
			if(tsNode.getUserObject() == null){
			region = StatesFactory.eINSTANCE.createStateComposite();
			region.setName(tsNode.getText());
			region.setGUID(getGUID(GUIDGenerator.getGUID(), manager.getGUIDGraphMap(), tsNode));//setting generated GUID

			addStateRule(stateMachine, region, projectName);
			setDefaultExtendedProperties(region);
			}else{
				
				region=(StateComposite)tsNode.getUserObject();
				region=(StateComposite)EcoreUtil.copy(region);
				
			}
			
			
			execute((StateMachineEditor)manager.getEditor(), region, StatesPackage.eINSTANCE.getStateEntity_OwnerStateMachine(), stateMachine.getFullPath());

			tsNode.setUserObject(region);
			regionGraph.setUserObject(region);

			List<TSENode> nodes = regionGraph.nodes();
			StateStart startstate = null;
			StateEnd endstate = null;

			for (TSENode node : nodes) {
				if (node.getAttributeValue(STATE_TYPE).equals(STATE.START)) {
					startstate = StatesFactory.eINSTANCE.createStateStart();
					startstate.setName("Start");
					startstate.setGUID(getGUID(GUIDGenerator.getGUID(), manager.getGUIDGraphMap(), node));//setting generated GUID
					addStateRule(stateMachine, startstate, projectName);
					setDefaultExtendedProperties(startstate);
					execute((StateMachineEditor)manager.getEditor(), startstate, StatesPackage.eINSTANCE.getStateEntity_OwnerStateMachine(), stateMachine.getFullPath());
					node.setUserObject(startstate);
					node.setTooltipText(createTooltip(startstate));
				}
				if (node.getAttributeValue(STATE_TYPE).equals(STATE.END)) {
					endstate = StatesFactory.eINSTANCE.createStateEnd();
					endstate.setName("End");
					endstate.setGUID(getGUID(GUIDGenerator.getGUID(), manager.getGUIDGraphMap(), node));//setting generated GUID
					addStateRule(stateMachine, endstate, projectName);
					setDefaultExtendedProperties(endstate);
					execute((StateMachineEditor)manager.getEditor(), endstate, StatesPackage.eINSTANCE.getStateEntity_OwnerStateMachine(), stateMachine.getFullPath());
					node.setUserObject(endstate);
					node.setTooltipText(createTooltip(endstate));
				}
			}

			tsNode.setTooltipText(createTooltip(region));

			execute((StateMachineEditor)manager.getEditor(), graph, region, tsNode.getText(), true);
			execute((StateMachineEditor)manager.getEditor(), regionGraph, startstate, "Start", false);
			execute((StateMachineEditor)manager.getEditor(), regionGraph, endstate, "End", false);
		}

		//Concurrent State
		else if (tsNode.getAttributeValue(STATE_TYPE).equals(STATE.CONCURRENT)) {
			TSEGraph concurrentGraph = (TSEGraph)tsNode.getChildGraph();
			StateComposite concurrentState=null;
			if( tsNode.getUserObject() == null){
			concurrentState = StatesFactory.eINSTANCE.createStateComposite();
			concurrentState.setName(tsNode.getText());
			concurrentState.setGUID(getGUID(GUIDGenerator.getGUID(), manager.getGUIDGraphMap(), tsNode));//setting generated GUID
			addStateRule(stateMachine, concurrentState, projectName);
			concurrentState.setConcurrentState(true);
			setDefaultExtendedProperties(concurrentState);
			}else{
				concurrentState=(StateComposite)tsNode.getUserObject();
				concurrentState=(StateComposite)EcoreUtil.copy(concurrentState);
			}

			execute((StateMachineEditor)manager.getEditor(), concurrentState, StatesPackage.eINSTANCE.getStateEntity_OwnerStateMachine(), stateMachine.getFullPath());
			

			concurrentGraph.setUserObject(concurrentState);
			tsNode.setUserObject(concurrentState);

			tsNode.setTooltipText(createTooltip(concurrentState));

			List<TSENode> nodes = concurrentGraph.nodes();
			StateComposite region = null;
			String tag_region = null;
			execute((StateMachineEditor)manager.getEditor(), graph, concurrentState, tsNode.getText(), false);
			for (TSENode node : nodes) {
				if(node.getUserObject()==null){
				region = StatesFactory.eINSTANCE.createStateComposite();
				region.setName(node.getText());
				region.setGUID(getGUID(GUIDGenerator.getGUID(), manager.getGUIDGraphMap(), node));//setting generated GUID
				addStateRule(stateMachine, region, projectName);
				setDefaultExtendedProperties(region);
				}else{
					
					region=(StateComposite)tsNode.getUserObject();
					region=(StateComposite)EcoreUtil.copy(region);
					
				}
				node.setUserObject(region);
				node.getChildGraph().setUserObject(region);
				tag_region = node.getText();
				node.setTooltipText(createTooltip(region));
				execute((StateMachineEditor)manager.getEditor(), region, StatesPackage.eINSTANCE.getStateEntity_OwnerStateMachine(), stateMachine.getFullPath());
				execute((StateMachineEditor)manager.getEditor(), concurrentGraph, region, tag_region, true);
			}
			for (TSENode node : nodes) {
				createCompositeStateNodesForConcurrentStateNode(node, manager);
				
			}
			//manager.getLayoutManager().callInteractiveIncrementalLayout();
			ConcurrentStateNodeUI concurrentStateNodeUI = new ConcurrentStateNodeUI();
			tsNode.setUI((TSEObjectUI)concurrentStateNodeUI);
			refreshDiagram(manager);
			refreshOverview(manager.getEditor().getEditorSite(), true, true);
			
		}
		//Call State Machine
		else if (tsNode.getAttributeValue(STATE_TYPE).equals(STATE.SUBSTATEMACHINE)) {
			StateSubmachine stateSubmachine = null;
			if (tsNode.getUserObject() == null) {
				stateSubmachine = StatesFactory.eINSTANCE.createStateSubmachine();
				stateSubmachine.setName(tsNode.getText());
				stateSubmachine.setOwnerProjectName(projectName);
				if (tsNode.getOwnerGraph().getUserObject() != null) {
					stateSubmachine.setParent((StateEntity)tsNode.getOwnerGraph().getUserObject());
				}
				stateSubmachine.setGUID(getGUID(GUIDGenerator.getGUID(), manager.getGUIDGraphMap(), tsNode));//setting generated GUID
				tsNode.setUserObject(stateSubmachine);
				tsNode.setTooltipText(createTooltip(stateSubmachine)); 
				setDefaultExtendedProperties(stateSubmachine);
			} else {
				stateSubmachine =(StateSubmachine)tsNode.getUserObject();
				stateSubmachine = (StateSubmachine)EcoreUtil.copy(stateSubmachine);
				if (tsNode.getOwnerGraph().getUserObject() != null) {
					stateSubmachine.setParent((StateEntity)tsNode.getOwnerGraph().getUserObject());
				}
				tsNode.setUserObject(stateSubmachine);
				tsNode.setTooltipText(createTooltip(stateSubmachine)); 
			}
			
			execute((StateMachineEditor)manager.getEditor(), stateSubmachine, StatesPackage.eINSTANCE.getStateEntity_OwnerStateMachine(), stateMachine.getFullPath());
			execute((StateMachineEditor)manager.getEditor(), graph, stateSubmachine, tsNode.getText() , false);//in memory persistence to editing domain
		}
	/*	CommandStack commandStack= editingDomain.getCommandStack();
	org.eclipse.emf.common.command.Command undoCommand=commandStack.getUndoCommand();
	boolean result=commandStack.canUndo();
	*/
		setWorkbenchSelection(tsNode, manager.getEditor());	
	 
		manager.layoutDiagramOnChange();

		// reset to Select tool after adding a node if such a preference is set
		//listener.resetToSelectToolAfterChange();

	}

	/**
	 * @param tsNode
	 * @param editingDomain
	 */
	@SuppressWarnings("unchecked")
	public static void createCompositeStateNodesForConcurrentStateNode(TSENode tsNode, StateMachineDiagramManager manager) {
		TSEGraph compositeGraph = (TSEGraph)tsNode.getChildGraph();

		List<TSENode> nodes = compositeGraph.nodes();
		StateStart startstate = null;
		StateEnd endstate = null;

		StateMachine stateMachine = manager.getStateMachine();
		String projectName = stateMachine.getOwnerProjectName();

		for (TSENode node : nodes) {
			if (node.getAttributeValue(STATE_TYPE).equals(STATE.START)) {
				startstate = StatesFactory.eINSTANCE.createStateStart();
				startstate.setName("Start");
				startstate.setGUID(getGUID(GUIDGenerator.getGUID(), manager.getGUIDGraphMap(), node));//setting generated GUID
				addStateRule(stateMachine, startstate, projectName);
				setDefaultExtendedProperties(startstate);
				node.setUserObject(startstate);
				node.setTooltipText(createTooltip(startstate));
			}
			if (node.getAttributeValue(STATE_TYPE).equals(STATE.END)) {
				endstate = StatesFactory.eINSTANCE.createStateEnd();
				endstate.setName("End");
				endstate.setGUID(getGUID(GUIDGenerator.getGUID(), manager.getGUIDGraphMap(), node));//setting generated GUID
				addStateRule(stateMachine, endstate, projectName);
				setDefaultExtendedProperties(endstate);
				node.setUserObject(endstate);
				node.setTooltipText(createTooltip(endstate));
			}
			//node.setSize(38,38);
		}
		execute((StateMachineEditor)manager.getEditor(), compositeGraph, startstate, "Start", false);
		execute((StateMachineEditor)manager.getEditor(), compositeGraph, endstate, "End", false);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.drawing.DiagramChangeListener#onEdgeAdded(com.tomsawyer.graphicaldrawing.TSEEdge)
	 */
	public static void handleEdgeAdd(TSEEdge tsEdge, StateMachineDiagramManager manager, StateMachineDiagramChangeListener listener) {
		// first, check whether the editor can be modified before running chain of commands
		if (!EditorUtils.checkPermissions((AbstractSaveableEntityEditorPart) manager.getEditor())) {
			((AbstractSaveableEntityEditorPart) manager.getEditor()).doRevert();
			return;
		}
		if (manager.isCutGraph() && manager.isPasteGraph()) {
			return;//For Transition
		}
		if (manager.isCopyGraph() && manager.isPasteGraph()) {
			return;//For Transition
		}

		if (tsEdge == null) {
			return;
		}

		if (tsEdge.getTargetNode()==null) {
			return;
		}

		TSEGraphManager arg0 = manager.getGraphManager();
		TSENode arg1 = (TSENode)tsEdge.getSourceNode();
		TSENode arg2 =  (TSENode)tsEdge.getTargetNode();

		if(arg2.getUserObject()==null)return;

		StateMachine stateMachine = manager.getStateMachine();
		String projectName = stateMachine.getOwnerProjectName();

		//////// Place label properly after we create a state transition
		StateMachine machine = (StateMachine)arg0.getAnchorGraph().getUserObject();
		String tag  = getEdgeName(machine, UNIQUE_TRANSITION);
		tsEdge.setName(tag);

		addEdgeLabel(tsEdge, "", manager);
		//////////// 

		EditingDomain editingDomain = ((StateMachineEditor)manager.getEditor()).getEditingDomain();
		StateTransition stateTransition;
		if(tsEdge.getUserObject()==null){
		stateTransition = StatesFactory.eINSTANCE.createStateTransition();
		stateTransition.setName(tsEdge.getText());
		stateTransition.setLabel("");
		stateTransition.setGUID(getGUID(GUIDGenerator.getGUID(), manager.getGUIDGraphMap(), tsEdge));//setting generated GUID
		stateTransition.setOwnerProjectName(projectName);
		stateTransition.setOwnerStateMachine(stateMachine);
		addTransitionRule(stateMachine, stateTransition, projectName);
		stateTransition.setFromState((State)arg1.getUserObject());
		((State)arg1.getUserObject()).getOutgoingTransitions().add(stateTransition);
		stateTransition.setToState((State)arg2.getUserObject());
		((State)arg2.getUserObject()).getIncomingTransitions().add(stateTransition);

		tsEdge.setUserObject(stateTransition);

		}else{
			stateTransition=(StateTransition)tsEdge.getUserObject();
			stateTransition= (StateTransition)EcoreUtil.copy(stateTransition);
			stateTransition.setFromState((State)arg1.getUserObject());
			((State)arg1.getUserObject()).getOutgoingTransitions().add(stateTransition);
			stateTransition.setToState((State)arg2.getUserObject());
			((State)arg2.getUserObject()).getIncomingTransitions().add(stateTransition);

			tsEdge.setUserObject(stateTransition);

		}
		
		

		
		// StateMachine machine = (StateMachine)arg0.getAnchorGraph().getUserObject();
		try {
			if (!isTransitionPresent(machine.getStateTransitions(), tsEdge.getText())) {
				//AddCommand addCommand = new AddCommand(	editingDomain, machine.getStateTransitions(), stateTransition);
				EdgeAddCommand addCommand =new EdgeAddCommand(manager,editingDomain, machine.getStateTransitions(),stateTransition);
				EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) manager.getEditor(), addCommand);

				EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) manager.getEditor(),
						SetCommand.create(editingDomain, 
								stateTransition, StatesPackage.eINSTANCE.getStateTransition_FromState(),(State)arg1.getUserObject()));

				EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) manager.getEditor(),
						SetCommand.create
						(editingDomain, stateTransition, StatesPackage.eINSTANCE.getStateTransition_ToState(),(State)arg2.getUserObject()));

				EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) manager.getEditor(),
						(AddCommand)AddCommand.create
						(editingDomain, (State)arg1.getUserObject(), StatesPackage.eINSTANCE.getStateVertex_OutgoingTransitions(),stateTransition));

				EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) manager.getEditor(),
						(AddCommand)AddCommand.create
						(editingDomain, (State)arg2.getUserObject(), StatesPackage.eINSTANCE.getStateVertex_IncomingTransitions(),stateTransition));

				setWorkbenchSelection(tsEdge, manager.getEditor());	
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		//manager.layoutDiagramOnChange();
		// reset to Select tool if such a preference is set
		//listener.resetToSelectToolAfterChange();
	}
	
	/**
	 * @param tsEdge
	 * @param tag
	 */
	public static void addEdgeLabel(TSEEdge tsEdge, String tag, StateMachineDiagramManager manager) {
		TSEEdgeLabel label = (TSEEdgeLabel) tsEdge.addLabel();
		label.setName(tag);
		((TSEAnnotatedUI) label.getUI()).setTextAntiAliasingEnabled(true);
		//((StateMachineLayoutManager) manager.getLayoutManager()).callBatchLabeling();
	}

}
