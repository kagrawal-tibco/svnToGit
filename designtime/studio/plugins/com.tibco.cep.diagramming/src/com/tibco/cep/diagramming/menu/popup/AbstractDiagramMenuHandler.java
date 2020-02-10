package com.tibco.cep.diagramming.menu.popup;

import java.awt.event.ActionListener;

import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEObject;
import com.tomsawyer.interactive.swing.TSSwingCanvas;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractDiagramMenuHandler implements ActionListener {

	protected DiagramManager manager;
	protected TSSwingCanvas canvas;
	protected com.tomsawyer.drawing.geometry.shared.TSConstPoint point;
	protected TSEGraph graphAtHitPoint;
	protected TSEObject hitObject;
	
	public abstract DiagramManager getManager();

	public abstract void setManager(DiagramManager manager);
	
	public abstract TSSwingCanvas getSwingCanvas();
	
	public abstract void setSwingCanvas(TSSwingCanvas canvas);
	
	public abstract void setHitPoint(com.tomsawyer.drawing.geometry.shared.TSConstPoint point);
	
	public abstract com.tomsawyer.drawing.geometry.shared.TSConstPoint getHitPoint();
	
	public abstract void setGraphAtHitPoint(TSEGraph graph);
	
	public abstract TSEGraph getGraphAtHitPoint();
	
	public abstract void setHitObject(TSEObject object);

}
