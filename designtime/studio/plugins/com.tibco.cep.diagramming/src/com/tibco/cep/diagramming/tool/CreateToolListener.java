package com.tibco.cep.diagramming.tool;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.drawing.DrawingCanvas;
import com.tomsawyer.interactive.swing.viewing.tool.TSESelectTool;
import com.tomsawyer.interactive.swing.viewing.tool.TSViewingToolHelper;
import com.tomsawyer.interactive.tool.TSToolManager;

public class CreateToolListener extends KeyAdapter {
	
	private DiagramManager manager;
	private ICreateTool tool;
	
	/**
	 * @param manager
	 * @param tool
	 */
	public CreateToolListener (DiagramManager manager, ICreateTool tool) {
		this.manager = manager;
		this.tool = tool;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.KeyAdapter#keyPressed(org.eclipse.swt.events.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.character == SWT.ESC) {
			DrawingCanvas drawingCanvas = manager.getDrawingCanvas();
			if(drawingCanvas != null) {
				TSToolManager toolManager = drawingCanvas.getToolManager();
				TSESelectTool selectTool = TSViewingToolHelper.getSelectTool(toolManager);
				if(toolManager.getActiveTool() != selectTool) {
					toolManager.setActiveTool(selectTool);
					tool.resetPaletteSelection();
				}
			}
		}
	}
}
