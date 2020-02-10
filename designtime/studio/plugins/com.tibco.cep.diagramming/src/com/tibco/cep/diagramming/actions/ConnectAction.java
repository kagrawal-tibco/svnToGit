package com.tibco.cep.diagramming.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEPolylineEdgeUI;
import com.tomsawyer.interactive.swing.TSSwingCanvas;
import com.tomsawyer.interactive.swing.editing.tool.TSECreateEdgeTool;
import com.tomsawyer.interactive.swing.editing.tool.TSEditingToolHelper;
import com.tomsawyer.interactive.tool.TSToolManager;

public class ConnectAction implements IWorkbenchWindowActionDelegate {

	private IWorkbenchPage page;
	private IGraphDrawing editor;
	private Object diagramManager;

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void init(IWorkbenchWindow window) {
		page = window.getActivePage();

	}

	public void run(IAction action) {
		IEditorPart activeEditorPart = page.getActiveEditor();
		if (activeEditorPart instanceof IGraphDrawing) {
			editor = (IGraphDrawing) activeEditorPart;
		}
		diagramManager = editor.getDiagramManager();

		TSSwingCanvas swingCanvas = ((IGraphDrawing) diagramManager).getDrawingCanvas();

		TSToolManager toolManager = swingCanvas.getToolManager();
		TSEPolylineEdgeUI edgeUI = new TSEPolylineEdgeUI();
		edgeUI.setAntiAliasingEnabled(true);
		TSECreateEdgeTool createEdgeTool = TSEditingToolHelper.getCreateEdgeTool(toolManager);
		TSEGraphManager graphManager = ((IGraphDrawing) diagramManager).getGraphManager();
		graphManager.getEdgeBuilder().setEdgeUI(edgeUI);
//		createEdgeTool.setEdgeUI(edgeUI);

		toolManager.setActiveTool(createEdgeTool);

	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
