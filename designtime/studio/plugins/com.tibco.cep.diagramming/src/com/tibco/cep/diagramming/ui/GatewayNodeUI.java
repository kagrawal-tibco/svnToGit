package com.tibco.cep.diagramming.ui;

import java.awt.RenderingHints;

import com.tomsawyer.drawing.geometry.shared.TSPolygonShape;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEShapeNodeUI;

/**
 * 
 * @author ggrigore
 *
 */
public class GatewayNodeUI extends TSEShapeNodeUI {

	private static final long serialVersionUID = 1L;
	protected final int innerThickness = 3;
	protected double INNER_SIZE = 0.50;
	protected double WIDTH = 6;


	public GatewayNodeUI() {

		if (this.getOwnerNode() != null) {
			this.getOwnerNode().setShape(TSPolygonShape.fromString(
					"[ 4 (50, 0) (100, 50) (50, 100) (0, 50) ]"));
			// we don't want to paint any tags
			this.getOwnerNode().setName(null);
		}
		
		this.setBorderColor(TSEColor.black);
		this.setFillColor(new TSEColor(255, 232, 138));
		super.setTextAntiAliasingEnabled(true);
		super.setShapeAntiAliasingEnabled(true);
	}

	public void draw(TSEGraphics graphics) {
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);		
		super.draw(graphics);
		drawExtended(graphics);
	}
	
	protected void drawExtended(TSEGraphics graphics) { }
}
