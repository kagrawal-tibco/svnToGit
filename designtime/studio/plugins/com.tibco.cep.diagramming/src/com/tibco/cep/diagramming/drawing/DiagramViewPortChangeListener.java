package com.tibco.cep.diagramming.drawing;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.diagramming.DiagrammingPlugin;
import com.tomsawyer.graphicaldrawing.events.TSEViewportChangeEvent;
import com.tomsawyer.graphicaldrawing.events.TSEViewportChangeListener;

/**
 * 
 * @author sasahoo
 *
 */
public class DiagramViewPortChangeListener implements TSEViewportChangeListener,IZoomConstants{

    private Combo zoomCombo;
    private DrawingCanvas canvas;
	private DiagramManager diagramManager;
	/* (non-Javadoc)
	 * @see com.tomsawyer.graphicaldrawing.event.TSEViewportChangeListener#viewportChanged(com.tomsawyer.graphicaldrawing.event.TSEViewportChangeEvent)
	 */
   
	public DiagramViewPortChangeListener(DiagramManager manager) {
		super();
		this.diagramManager = manager ; 
	}

	public void viewportChanged(TSEViewportChangeEvent eventData) {
		try{
		if(eventData.getType()==  TSEViewportChangeEvent.ZOOM){
			canvas = (DrawingCanvas)eventData.getSource();
			zoomCombo = DiagrammingPlugin.getDefault().getMap().get("ZOOM");
			if(canvas != null && canvas.isDisplayable() && 
					zoomCombo!=null && !zoomCombo.isDisposed()){
				Display.getDefault().asyncExec(new Runnable(){
					/* (non-Javadoc)
					 * @see java.lang.Runnable#run()
					 */
					public void run() {
						String zoom = format.format(canvas.getZoomLevel() * multiplier);
						if(zoomCombo!=null && !zoomCombo.isDisposed()){
							zoomCombo.setText(zoom);
							diagramManager.onZoom();
						}
					}});
			}
		}
		if(eventData.getType()==  TSEViewportChangeEvent.PAN){
           //TODO
		}
		if(eventData.getType()==  TSEViewportChangeEvent.ANY_CHANGE){
			//TODO
		}
		}catch(Exception e){
		e.printStackTrace();
		}
	}

	
}
