package com.tibco.cep.studio.ui.statemachine.handler;

import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.actions.ActionFactory;

import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.drawing.DrawingCanvas;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tibco.cep.studio.ui.statemachine.diagram.StateMachineDiagramManager;
import com.tibco.cep.studio.ui.statemachine.diagram.editors.StateMachineEditor;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * 
 * @author sasahoo
 *
 */
@SuppressWarnings("unused")
public class StateMachineDiagramEditorHandler extends Action {


	private TSEGraphManager graphManager;
	private DrawingCanvas canvas;
	private LayoutManager layoutManager;
	private DiagramManager diagramManager;
    private StateMachineEditor editor;
    private EditingDomain editingDomain;
	
	/**
	 * @param id
	 * @param diagramManger
	 * @param graphManager
	 * @param canvas
	 * @param layoutManager
	 */
	public StateMachineDiagramEditorHandler(String id, 
											 StateMachineEditor editor) {
		super();
		setId(id);
		this.editor = editor;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		diagramManager = (StateMachineDiagramManager) editor.getStateMachineDiagramManager();
		graphManager =diagramManager.getGraphManager();
		canvas = diagramManager.getDrawingCanvas();
		layoutManager = diagramManager.getLayoutManager();
		editingDomain = editor.getEditingDomain();

		if (ActionFactory.SAVE.getId().equals(getId())) {
			editor.doSave(new NullProgressMonitor());
		}else if (ActionFactory.SELECT_ALL.getId().equals(getId())) {
			List<Object> nodesList = diagramManager.getGraphManager().getMainDisplayGraph().nodes();
			for (Object node : nodesList) {
				((TSENode)node).setSelected(true);
			}
			List<Object> edgesList = diagramManager.getGraphManager().getMainDisplayGraph().edges();
			for (Object edge : edgesList) {
				((TSEEdge)edge).setSelected(true);
			}
			canvas.drawGraph();
			canvas.repaint();
		}else if (ActionFactory.COPY.getId().equals(getId())) {
			diagramManager.copyGraph();
		} else if (ActionFactory.PASTE.getId().equals(getId())) {
			diagramManager.pasteGraph();
		} else if (ActionFactory.CUT.getId().equals(getId())) {
			diagramManager.cutGraph();
		} else if (ActionFactory.DELETE.getId().equals(getId())) {
			diagramManager.delete();
		} else if (ActionFactory.SELECT_ALL.getId().equals(getId())) {
			//TODO
		} else if (ActionFactory.FIND.getId().equals(getId())) {
			//TODO
		} else if (ActionFactory.UNDO.getId().equals(getId())) {
			diagramManager.undo();
			editor.enableUndoRedoContext();
		} else if (ActionFactory.REDO.getId().equals(getId())) {
			diagramManager.redo();
			editor.enableUndoRedoContext();
		}
	}
	
	private void undo() {
		
		//diagramManager.undo();
		CommandStack commandStack = editingDomain.getCommandStack();
		if (commandStack.canUndo()) {
			Command mostRecentCommand = commandStack.getMostRecentCommand();
			if (mostRecentCommand != null) {
				//TODO: Check for type of commands  (ADD/REMOVE/SET Command)
			}
 			commandStack.undo();
 			
 			//is_UNDO=true;
 		/*	if(mostRecentCommand instanceof AbstractNodeAddCommand){
 				if(mostRecentCommand instanceof SimpleStateAddCommand){
 				 ((SimpleStateAddCommand) mostRecentCommand).runForChange((StateMachineDiagramManager)diagramManager);
 				}
 			}else if(mostRecentCommand instanceof AbstractNodeRemoveCommand){
 				if(mostRecentCommand instanceof SimpleStateRemoveCommand){
 	 				 ((SimpleStateRemoveCommand) mostRecentCommand).runForChange((StateMachineDiagramManager)diagramManager);
 	 				}
 	 			}
 			*/
		}
	}
	
	private void redo() {
	
		CommandStack commandStack = editingDomain.getCommandStack();
		if (commandStack.canRedo()) {
			Command mostRecentCommand = commandStack.getMostRecentCommand();
			if (mostRecentCommand != null) {
				//TODO: Check for type of commands(ADD/REMOVE/SET Command)
			}
 			commandStack.redo();
 		/*	is_UNDO=true;
 			if(mostRecentCommand instanceof AbstractNodeRemoveCommand){
 				if(mostRecentCommand instanceof SimpleStateRemoveCommand){
 				 ((SimpleStateRemoveCommand) mostRecentCommand).runForChange((StateMachineDiagramManager)diagramManager);
 				}
 			}*/
 			
		}
	}
	
	private void refresh() {
		layoutManager.callBatchGlobalLayout();
		canvas.drawGraph();
		canvas.repaint();
	}
}