package com.tibco.cep.diagramming.drawing;

import com.tibco.cep.diagramming.tool.PALETTE;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.interactive.swing.TSSwingCanvas;
import com.tomsawyer.interactive.swing.overview.TSEOverviewComponent;

public interface IGraphDrawing {

	public IDiagramManager getDiagramManager();

	public TSSwingCanvas getDrawingCanvas();

	public TSEGraphManager getGraphManager();

	public LayoutManager getLayoutManager();

	public void setLayoutManager(LayoutManager mgr);

	public TSEOverviewComponent getOverviewComponent();
	
	public PALETTE getPalette();
	
	public IDiagramModelAdapter getDiagramModelAdapter();
	
	public boolean isDisplayFullName();
	
	public boolean isfillTaskIcons();
}
