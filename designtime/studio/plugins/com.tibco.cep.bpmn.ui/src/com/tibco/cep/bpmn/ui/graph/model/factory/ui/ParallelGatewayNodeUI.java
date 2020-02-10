package com.tibco.cep.bpmn.ui.graph.model.factory.ui;

import com.tomsawyer.drawing.geometry.shared.TSConstRect;
import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;

/**
 * 
 * @author ggrigore
 *
 */
public class ParallelGatewayNodeUI extends GatewayNodeUI {

	private static final long serialVersionUID = 1L;
	
	protected void drawExtended(TSEGraphics graphics) {
		TSConstRect bounds = this.getOwnerNode().getLocalBounds();		
		TSTransform transform = graphics.getTSTransform();
		
		// draw vertical line
		double x = bounds.getLeft() + bounds.getWidth()/2.0 - WIDTH/2;
		double y = bounds.getTop() - bounds.getHeight()/6.0;
		double HEIGHT = bounds.getHeight() * 0.667;
		graphics.fillRect(transform.xToDevice(x),
				transform.yToDevice(y),
				transform.widthToDevice(WIDTH),
				transform.heightToDevice(HEIGHT));
		
		// draw horizontal line
		x = bounds.getLeft() + bounds.getWidth()/6.0;
		y = bounds.getBottom() + bounds.getHeight()/2.0 + WIDTH/2;
		graphics.fillRect(transform.xToDevice(x),
				transform.yToDevice(y),
				transform.widthToDevice(HEIGHT),
				transform.heightToDevice(WIDTH));
	}
}
