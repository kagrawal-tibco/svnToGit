package com.tibco.cep.bpmn.ui.graph.model.factory.ui;

import java.awt.BasicStroke;
import java.awt.Stroke;

import com.tomsawyer.drawing.geometry.shared.TSConstRect;
import com.tomsawyer.drawing.geometry.shared.TSRect;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;

/**
 * 
 * @author ggrigore
 *
 */
public class EventBasedGatewayNodeUI extends GatewayNodeUI {

	private static final long serialVersionUID = 1L;
	
	protected void drawExtended(TSEGraphics graphics) {
		
		TSENode owner = this.getOwnerNode();
		TSConstRect bounds = owner.getLocalBounds();		
		TSRect inner = new TSRect(bounds);
		inner.setWidth(inner.getWidth() * INNER_SIZE);
		inner.setHeight(inner.getHeight() * INNER_SIZE);
		// draw inner circle
		BasicStroke oldStroke = (BasicStroke) graphics.getStroke();
		Stroke newStroke = new BasicStroke(
			oldStroke.getLineWidth() * this.innerThickness,
			oldStroke.getEndCap(),
			oldStroke.getLineJoin(),
			oldStroke.getMiterLimit(),
			oldStroke.getDashArray(),
			oldStroke.getDashPhase());
		graphics.setStroke(newStroke);
		// graphics.setColor(GLOW_OUTER_HIGH_COLOR);
		graphics.drawOval(inner);
		graphics.setStroke(oldStroke);		
	}
}
