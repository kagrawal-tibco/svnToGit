package com.tibco.cep.studio.cluster.topology.handler;

import static com.tibco.cep.studio.cluster.topology.handler.ClusterTopologyDiagramChangeListener.UNDO_FlAG_ST;

import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.actions.ActionFactory;

import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.drawing.DrawingCanvas;
import com.tibco.cep.diagramming.drawing.LayoutManager;
import com.tibco.cep.studio.cluster.topology.editors.ClusterTopologyDiagramManager;
import com.tibco.cep.studio.cluster.topology.editors.ClusterTopologyEditor;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * 
 * @author sasahoo
 *
 */
public class ClusterTopologyEditHandler extends Action {

	private TSEGraphManager graphManager;
	private DrawingCanvas canvas;
	private LayoutManager layoutManager;
	private DiagramManager diagramManager;
    private ClusterTopologyEditor editor;
	
	/**
	 * @param id
	 * @param diagramManger
	 * @param graphManager
	 * @param canvas
	 * @param layoutManager
	 */
	public ClusterTopologyEditHandler(String id, 
			ClusterTopologyEditor editor) {
		super();
		setId(id);
		this.editor = editor;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {

		diagramManager = (ClusterTopologyDiagramManager) editor.getDiagramManager();
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
			List<?> nodesList = diagramManager.getGraphManager().getMainDisplayGraph().nodes();
			for (Object node : nodesList) {
				((TSENode)node).setSelected(true);
			}
			List<?> edgesList = diagramManager.getGraphManager().getMainDisplayGraph().edges();
			for (Object edge : edgesList) {
				((TSEEdge)edge).setSelected(true);
			}
			canvas.drawGraph();
			canvas.repaint();
		} else if (ActionFactory.FIND.getId().equals(getId())) {

			//TODO

		} else if (ActionFactory.CUT.getId().equals(getId())) {

			diagramManager.cutGraph();

		} else if (ActionFactory.DELETE.getId().equals(getId())) {
			diagramManager.delete();
		} else if (ActionFactory.UNDO.getId().equals(getId())) {
			diagramManager.undo();
			UNDO_FlAG_ST=true;
		} else if (ActionFactory.REDO.getId().equals(getId())) {
			diagramManager.redo();
			UNDO_FlAG_ST=true;
		}

	}
	
	@SuppressWarnings("unused")
	private void refresh() {
		layoutManager.callBatchGlobalLayout();
		canvas.drawGraph();
		canvas.repaint();
	}

	public TSEGraphManager getGraphManager() {
		return graphManager;
	}
}