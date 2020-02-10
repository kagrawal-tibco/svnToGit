package com.tibco.cep.studio.ui.editors.concepts;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.actions.ActionFactory;

import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.drawing.DrawingCanvas;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tibco.cep.studio.ui.diagrams.ConceptDiagramManager;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;

/**
 * 
 * @author smarathe
 *
 */
public class ConceptDiagramEditHandler extends Action {

	private TSEGraphManager graphManager;
	private DrawingCanvas canvas;
	private LayoutManager layoutManager;
	private DiagramManager diagramManager;
    private ConceptDiagramEditor editor;
    public static boolean is_UNDO=false;
	
	/**
	 * @param id
	 * @param diagramManger
	 * @param graphManager
	 * @param canvas
	 * @param layoutManager
	 */
	public ConceptDiagramEditHandler(String id, 
			ConceptDiagramEditor editor) {
		super();
		setId(id);
		this.editor = editor;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {

		diagramManager = (ConceptDiagramManager) editor.getDiagramManager();
		graphManager =diagramManager.getGraphManager();
		canvas = diagramManager.getDrawingCanvas();
		layoutManager = diagramManager.getLayoutManager();

//		KeyboardFocusManager keyboardFocusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
//		Component component = keyboardFocusManager.getPermanentFocusOwner();

		if (ActionFactory.SAVE.getId().equals(getId())) {
			
			editor.doSave(new NullProgressMonitor());
			
		}else if (ActionFactory.COPY.getId().equals(getId())) {

		diagramManager.copyGraph();

		} else if (ActionFactory.PASTE.getId().equals(getId())) {

		diagramManager.pasteGraph();

		} else if (ActionFactory.SELECT_ALL.getId().equals(getId())) {

			//TODO

		} else if (ActionFactory.FIND.getId().equals(getId())) {

			//TODO

		} else if (ActionFactory.CUT.getId().equals(getId())) {

			diagramManager.cutGraph();

		} else if (ActionFactory.DELETE.getId().equals(getId())) {
			diagramManager.delete();
		}else if (ActionFactory.UNDO.getId().equals(getId())) {
			is_UNDO =true;
			diagramManager.undo();
			
			
		}else if (ActionFactory.REDO.getId().equals(getId())) {
			diagramManager.redo();
		}

	}
	
	@SuppressWarnings("unused")
	private void refresh() {
		layoutManager.callBatchGlobalLayout();
		canvas.drawGraph();
		canvas.repaint();
	}
}
