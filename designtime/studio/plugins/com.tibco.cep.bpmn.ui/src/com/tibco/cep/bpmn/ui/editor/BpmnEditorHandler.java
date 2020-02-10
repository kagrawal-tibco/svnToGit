package com.tibco.cep.bpmn.ui.editor;

import static com.tibco.cep.bpmn.ui.graph.BpmnGraphUtils.editProcessObjects;
import static com.tibco.cep.bpmn.ui.graph.BpmnGraphUtils.pasteClipBoardProcessObjects;

import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.ActionFactory;

import com.tibco.cep.diagramming.drawing.DrawingCanvas;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;

public class BpmnEditorHandler extends Action {

	private TSEGraphManager graphManager;
	private DrawingCanvas canvas;
	@SuppressWarnings("unused")
	private BpmnLayoutManager layoutManager;
	private BpmnDiagramManager diagramManager;
	private IEditorPart editor;

	/**
	 * @param editor
	 * @param id
	 * @param diagramManger
	 * @param graphManager
	 * @param canvas
	 * @param layoutManager
	 */
	public BpmnEditorHandler(IEditorPart editor, 
							 String id, 
			                 BpmnDiagramManager diagramManger, 
			                 TSEGraphManager graphManager,
			                 DrawingCanvas canvas, 
			                 BpmnLayoutManager layoutManager) {
		super();
		setId(id);
		this.editor = editor;
		this.graphManager = graphManager;
		this.canvas = canvas;
		this.layoutManager = layoutManager;
		this.diagramManager = diagramManger;
		setEnabled(isEnabled());//setting the initial enabled status of handler
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		// KeyboardFocusManager keyboardFocusManager =
		// KeyboardFocusManager.getCurrentKeyboardFocusManager();
		if (graphManager == null || canvas == null || diagramManager == null)
			return;
		if (ActionFactory.COPY.getId().equals(getId())) {
			
			diagramManager.copyGraph();
			
			editProcessObjects(editor.getSite().getShell(), diagramManager);
		} else if (ActionFactory.PASTE.getId().equals(getId())) {
			//TODO:remove paste and put at appropriate place
			pasteClipBoardProcessObjects(diagramManager.getEditor());
			
			diagramManager.pasteGraph();

		} else if (ActionFactory.SELECT_ALL.getId().equals(getId())) {
			selectAllNodeRecursively(diagramManager.getGraphManager().getMainDisplayGraph());
			
			canvas.drawGraph();
			canvas.repaint();

		} else if (ActionFactory.FIND.getId().equals(getId())) {

			// diagramManager.find();

		} else if (ActionFactory.CUT.getId().equals(getId())) {
			
			diagramManager.cutGraph();
			
			editProcessObjects(editor.getSite().getShell(), diagramManager);

		} else if (ActionFactory.DELETE.getId().equals(getId())) {
			diagramManager.delete();

		} else if (ActionFactory.UNDO.getId().equals(getId())) {
			setEnabled(true);//make sure handler undo is enabled as this action code executing
			diagramManager.undo();
		} else if (ActionFactory.REDO.getId().equals(getId())) {
			setEnabled(true);//make sure handler redo is enabled as this action code executing
			diagramManager.redo();
		} else if(ActionFactory.SAVE.getId().equals(getId())) {
			editor.doSave(new NullProgressMonitor());
		} else if(ActionFactory.SAVE_AS.getId().equals(getId())) {
			
		} else if(ActionFactory.SAVE_ALL.getId().equals(getId())) {
			
		}
		
		refresh();
	}
	
	@SuppressWarnings("rawtypes")
	private void selectAllNodeRecursively(TSGraph graph){
		List nodesList = graph.nodes();
		for (Object obj : nodesList) {
			TSENode node = (TSENode)obj;
			node.setSelected(true);
			TSGraph childGraph = node.getChildGraph();
			if(childGraph != null)
				selectAllNodeRecursively(childGraph);
		}
		
		List edgesList = graph.edges();
		for (Object edge : edgesList) {
			((TSEEdge)edge).setSelected(true);
		}
	}

	@Override
	public void runWithEvent(Event event) {
		// TODO Auto-generated method stub
		super.runWithEvent(event);
	}

	private void refresh() {
		// this should not happen, unless the preference of "Run Layout On Changes" is selected to something other than None..
		// diagramManager.layoutDiagramOnChange();
		
		canvas.drawGraph();
		canvas.repaint();

		IAction undoHandler = editor.getEditorSite().getActionBars()
				.getGlobalActionHandler(ActionFactory.UNDO.getId());
		undoHandler.setEnabled(undoHandler.isEnabled());

		IAction redoHandler = editor.getEditorSite().getActionBars()
				.getGlobalActionHandler(ActionFactory.REDO.getId());
		redoHandler.setEnabled(redoHandler.isEnabled());
		
		IAction cutHandler = editor.getEditorSite().getActionBars()
		.getGlobalActionHandler(ActionFactory.CUT.getId());
		cutHandler.setEnabled(cutHandler.isEnabled());
		
		IAction copyHandler = editor.getEditorSite().getActionBars()
		.getGlobalActionHandler(ActionFactory.COPY.getId());
		copyHandler.setEnabled(copyHandler.isEnabled());
		
		IAction pasteHandler = editor.getEditorSite().getActionBars()
		.getGlobalActionHandler(ActionFactory.PASTE.getId());
		pasteHandler.setEnabled(pasteHandler.isEnabled());
		
		IAction deleteHandler = editor.getEditorSite().getActionBars()
		.getGlobalActionHandler(ActionFactory.DELETE.getId());
		deleteHandler.setEnabled(deleteHandler.isEnabled());
		
	}

	@Override
	public boolean isEnabled() {
		if (ActionFactory.UNDO.getId().equals(getId())) { 
			return diagramManager.canUndo();
		} else if (ActionFactory.REDO.getId().equals(getId())) {
			return diagramManager.canRedo();
		} else if (ActionFactory.CUT.getId().equals(getId())) {
			return diagramManager.canCut();
		} else if (ActionFactory.COPY.getId().equals(getId())) {
			return diagramManager.canCopy();
		} else if (ActionFactory.PASTE.getId().equals(getId())) {
			return diagramManager.canPaste();
		}else if (ActionFactory.DELETE.getId().equals(getId())) {
			return diagramManager.canDelete();
		}else if( ActionFactory.SAVE.getId().equals(getId())){
			return editor.isDirty();
		}
		return true;
	}
	
}