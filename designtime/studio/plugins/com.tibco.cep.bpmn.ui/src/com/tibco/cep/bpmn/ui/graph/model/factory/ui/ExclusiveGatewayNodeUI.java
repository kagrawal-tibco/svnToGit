package com.tibco.cep.bpmn.ui.graph.model.factory.ui;

import com.tomsawyer.drawing.geometry.shared.TSConstRect;
import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;

/**
 * 
 * @author ggrigore
 *
 */
public class ExclusiveGatewayNodeUI extends GatewayNodeUI {

	private static final long serialVersionUID = 1L;

	protected void drawExtended(TSEGraphics graphics) {
		// TODO: draw an "X"
		
		TSConstRect bounds = this.getOwnerNode().getLocalBounds();		
		TSTransform transform = graphics.getTSTransform();
		final double SCALING_FACTOR=0.03125;
		final int WIDTH=4,LIMIT=604;
		double x = bounds.getLeft()+14;
		double y = bounds.getTop()-33;
		for(int i=0;i<LIMIT;i++){
				
		graphics.fillRect(transform.xToDevice(x),
				transform.yToDevice(y),
				transform.widthToDevice(WIDTH),
				transform.heightToDevice(WIDTH));
		
		x = x+SCALING_FACTOR;
		y = y+SCALING_FACTOR;
		
		}
		x = bounds.getLeft()+14;
		y = bounds.getBottom()+35;
		
		for(int i=0;i<LIMIT;i++){
				
			graphics.fillRect(transform.xToDevice(x),
					transform.yToDevice(y),
					transform.widthToDevice(WIDTH),
					transform.heightToDevice(WIDTH));
		
		x = x+SCALING_FACTOR;
		y = y-SCALING_FACTOR;
		
		}
		
	}
}
