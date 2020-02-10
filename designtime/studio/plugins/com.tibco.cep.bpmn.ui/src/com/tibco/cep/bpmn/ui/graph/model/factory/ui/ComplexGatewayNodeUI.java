package com.tibco.cep.bpmn.ui.graph.model.factory.ui;

import java.awt.BasicStroke;
import java.awt.Stroke;

import com.tomsawyer.drawing.geometry.shared.TSConstRect;
import com.tomsawyer.drawing.geometry.shared.TSPoint;
import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;

/**
 * 
 * @author ggrigore
 *
 */
public class ComplexGatewayNodeUI extends GatewayNodeUI {

	private static final long serialVersionUID = 1L;
	
	protected void drawExtended(TSEGraphics graphics) {
		TSConstRect bounds = this.getOwnerNode().getLocalBounds();		
		@SuppressWarnings("unused")
		TSTransform transform = graphics.getTSTransform();
		
		BasicStroke oldStroke = (BasicStroke) graphics.getStroke();
		Stroke newStroke = new BasicStroke(
			Double.valueOf(WIDTH).floatValue(),
			oldStroke.getEndCap(),
			oldStroke.getLineJoin(),
			oldStroke.getMiterLimit(),
			oldStroke.getDashArray(),
			oldStroke.getDashPhase());
		graphics.setStroke(newStroke);

		// draw |
		double HEIGHT = bounds.getHeight() * 0.5;
		double radius = HEIGHT / 2.0;
		double offset = (bounds.getHeight() - HEIGHT) / 2.0;
		TSPoint firstPoint = new TSPoint(bounds.getCenterX(), bounds.getTop() - offset);
		TSPoint secondPoint = new TSPoint(bounds.getCenterX(), bounds.getBottom() + offset);
		graphics.drawLine(firstPoint, secondPoint);

		// draw /
		double a = Math.cos(Math.toRadians(45.0)) * radius;
		firstPoint.setX(bounds.getCenterX() + a);
		firstPoint.setY(firstPoint.getY() - (radius-a));
		secondPoint.setX(bounds.getCenterX() - a);
		secondPoint.setY(secondPoint.getY() + (radius-a));
		graphics.drawLine(firstPoint, secondPoint);

		// draw -
		firstPoint.setX(bounds.getRight() - offset);
		firstPoint.setY(bounds.getCenterY());
		secondPoint.setX(bounds.getLeft() + offset);
		secondPoint.setY(firstPoint.getY());
		graphics.drawLine(firstPoint, secondPoint);		
				
		// draw \
		firstPoint.setX(bounds.getCenterX() - a);
		firstPoint.setY(bounds.getTop() - offset - (radius-a));
		secondPoint.setX(bounds.getCenterX() + a);
		secondPoint.setY(bounds.getBottom() + offset + (radius-a));
		graphics.drawLine(firstPoint, secondPoint);
		
		graphics.setStroke(oldStroke);
	}
}
