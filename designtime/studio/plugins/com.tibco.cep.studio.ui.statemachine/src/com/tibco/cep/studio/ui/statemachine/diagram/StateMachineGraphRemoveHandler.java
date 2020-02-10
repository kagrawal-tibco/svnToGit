package com.tibco.cep.studio.ui.statemachine.diagram;

import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.UNDO_REDO;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.removeGUID;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.removeTransitions;

import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.statemachine.commands.EdgeRemoveCommand;
import com.tibco.cep.studio.ui.statemachine.commands.SimpleStateRemoveCommand;
import com.tibco.cep.studio.ui.statemachine.diagram.editors.StateMachineEditor;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * 
 * @author sasahoo
 *
 */
public class StateMachineGraphRemoveHandler {

    /**
     * This handles the node deletion operation
     * @param tsNode
     * @param manager
     * @param listener
     */
    public static void handleNodeDelete(TSENode tsNode, StateMachineDiagramManager manager, StateMachineDiagramChangeListener listener) {
    	if(tsNode == null) return;
		// first, check whether the editor can be modified before running chain of commands
    	if (!EditorUtils.checkPermissions((AbstractSaveableEntityEditorPart) manager.getEditor())) {
    		((AbstractSaveableEntityEditorPart) manager.getEditor()).doRevert();
    		return;
    	}

    	State state = (State)tsNode.getUserObject();
//    	listener.onNodeDeleted(tsNode); //TODO:replace with listener's super call

    	removeGUID(state, manager.getGUIDGraphMap());
    	
    	TSEGraph  graph= (TSEGraph)tsNode.getOwnerGraph();
		StateComposite composite = (StateComposite)graph.getUserObject();
    	EditingDomain editingDomain = ((StateMachineEditor)manager.getEditor()).getEditingDomain();
    	if (state instanceof StateComposite) {
    		StateComposite compositeState = (StateComposite)state;
    		
			removeTransitions(state, manager.getStateMachine());//removing all State Transitions for the deleted State Composite
    		if (compositeState.isRegion()) {
    			RemoveCommand removeCommand = new RemoveCommand(editingDomain,composite.getRegions(), compositeState);
    			EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) manager.getEditor(), removeCommand);
    		} else {
    			RemoveCommand removeCommand = new RemoveCommand(editingDomain,composite.getStateEntities(), state);
    			EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) manager.getEditor(), removeCommand);
    		}
    	} else {
    		AbstractSaveableEntityEditorPart editor= (StateMachineEditor)manager.getEditor();
    		if(UNDO_REDO){

				 SimpleStateRemoveCommand simpleStateRemoveCommand=new SimpleStateRemoveCommand(manager,
						 editor.getEditingDomain(),composite.getStateEntities(),state);
				 EditorUtils.executeCommand(editor, simpleStateRemoveCommand, true);
    		}else{
    		RemoveCommand removeCommand = new RemoveCommand(editingDomain,composite.getStateEntities(), state);
    		EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) manager.getEditor(), removeCommand);
    		}
    	}
		
	//	if (!manager.layoutDiagramOnChange()) {
	 		manager.getDrawingCanvas().drawGraph();
			manager.getDrawingCanvas().repaint();
	//	}
    }
    
    /**
     * This handles State Machine Edges deletions
     * @param tsEdge
     * @param manager
     * @param listener
     */
    public static void handleEdgeDelete(TSEEdge tsEdge, StateMachineDiagramManager manager, StateMachineDiagramChangeListener listener) {
    	if(tsEdge == null) return;
    	// first, check whether the editor can be modified before executing the chain of commands
    	if (!EditorUtils.checkPermissions((AbstractSaveableEntityEditorPart) manager.getEditor())) {
    		((AbstractSaveableEntityEditorPart) manager.getEditor()).doRevert();
    		return;
    	}
    	
    	
    	TSENode arg1 = (TSENode)tsEdge.getSourceNode();
    	TSENode arg2 =  (TSENode)tsEdge.getTargetNode();
    	StateTransition stateTransition =  (StateTransition)tsEdge.getUserObject();
    	TSEGraphManager arg0 = manager.getGraphManager();
    	//if(stateTransition!=null)
    	{
    		EditingDomain editingDomain = ((StateMachineEditor)manager.getEditor()).getEditingDomain();
    		StateMachine machine = (StateMachine)arg0.getAnchorGraph().getUserObject();
    	
//    		listener.onEdgeDeleted(tsEdge);//TODO:replace with listener's super call

    		removeGUID(stateTransition, manager.getGUIDGraphMap());

    		//RemoveCommand removeCommand = new RemoveCommand(editingDomain, machine.getStateTransitions(), stateTransition);
    		EdgeRemoveCommand removeCommand =new EdgeRemoveCommand(manager,editingDomain, machine.getStateTransitions(),stateTransition);
    		EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) manager.getEditor(), removeCommand);

    		EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) manager.getEditor(), 
    				(RemoveCommand)RemoveCommand.create
    				(editingDomain, (State)arg1.getUserObject(), StatesPackage.eINSTANCE.getStateVertex_OutgoingTransitions(),stateTransition));

    		EditorUtils.executeCommand((AbstractSaveableEntityEditorPart) manager.getEditor(), 
    				(RemoveCommand)RemoveCommand.create
    				(editingDomain, (State)arg2.getUserObject(), StatesPackage.eINSTANCE.getStateVertex_IncomingTransitions(),stateTransition));

    	//	if (!manager.layoutDiagramOnChange()) {
    			manager.getDrawingCanvas().drawGraph();
    			manager.getDrawingCanvas().repaint();
    	//	}
    	}
    }
	
}