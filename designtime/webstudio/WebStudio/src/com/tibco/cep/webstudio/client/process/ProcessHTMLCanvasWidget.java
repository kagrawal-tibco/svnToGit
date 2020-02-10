package com.tibco.cep.webstudio.client.process;

import com.tomsawyer.visualization.gwt.client.widget.canvas.tool.TSSelectMouseTool;
import com.tomsawyer.web.client.view.drawing.TSDrawingViewWidget;
import com.tomsawyer.web.client.view.drawing.TSHTMLCanvasWidget;
import com.tomsawyer.web.client.view.drawing.tool.predefinedtool.htmlcanvas.TSHTMLCanvasSelectTool;
import com.google.gwt.user.client.Event;
import com.tomsawyer.visualization.gwt.client.drawing.TSDrawingElement;

/**
 * 
 * @author sasahoo
 *
 */
public class ProcessHTMLCanvasWidget  extends TSHTMLCanvasWidget {
    public ProcessHTMLCanvasWidget(TSDrawingViewWidget arg0) {
		super(arg0);
	}

	protected TSSelectMouseTool newSelectTool(String toolID) {
        return new TSHTMLCanvasSelectTool(this, toolID) {
                protected boolean onMouseClickOnElement(Event event, 
                		TSDrawingElement hitElement) {
                    boolean rc = super.onMouseClickOnElement(event, hitElement);

                    System.out.println("xsxsxsx-->>"+ hitElement);
                    // Do something here. Custom operations etc.

                    return rc;
                }
            };
    }
}
