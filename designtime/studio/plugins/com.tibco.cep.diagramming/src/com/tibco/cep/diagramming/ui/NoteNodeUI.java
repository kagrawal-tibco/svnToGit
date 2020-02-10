package com.tibco.cep.diagramming.ui;

import java.util.Iterator;

import com.tomsawyer.drawing.geometry.shared.TSPolygonShape;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.drawing.geometry.shared.TSShape;
import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.drawing.geometry.shared.TSDeviceRectangle;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEShapeNodeUI;

/**
 * 
 * @author ggrigore
 *
 */
public class NoteNodeUI extends TSEShapeNodeUI {

	private static final long serialVersionUID = 1L;
	int[] xCoords;
	int[] yCoords;
	int[] xCoordsCorner;
	int[] yCoordsCorner;
	double scaleX = Double.NaN;
	double scaleY = Double.NaN;
	double shiftX = Double.NaN;
	double shiftY = Double.NaN;
	private static TSShape dummyShape = new TSPolygonShape();	

	private static TSEColor FILL_COLOR = new TSEColor(255,216,0);
	private static TSEColor DRAW_BORDER_COLOR = new TSEColor(255, 226, 142);
	
	public NoteNodeUI() {

		if (this.getOwnerNode() != null) {
			this.getOwnerNode().setShape(TSPolygonShape.fromString(
			"[ 6 (0, 0) (100, 0) (100, 50) (75, 50) (75, 75) (0, 75) ]"));
		}
		this.setBorderColor(DRAW_BORDER_COLOR);
		this.setFillColor(FILL_COLOR);
	}

	public void draw(TSEGraphics graphics) {	
		// recompute the polygon if necessary
		this.validateShape(this.getOwnerNode().getShape(), graphics.getTSTransform());

		this.xCoordsCorner = new int[3];
		xCoordsCorner[0] = this.xCoords[2];
		xCoordsCorner[1] = this.xCoords[3];
		xCoordsCorner[2] = this.xCoords[4];
		
		this.yCoordsCorner = new int[3];
		yCoordsCorner[0] = this.yCoords[2];
		yCoordsCorner[1] = this.yCoords[3];
		yCoordsCorner[2] = this.yCoords[4];

		graphics.setColor(this.getFillColor());
		graphics.fillPolygon(this.xCoordsCorner,
				this.yCoordsCorner,
				this.xCoordsCorner.length);
		
		graphics.setColor(this.getBorderColor());
		graphics.drawPolygon(this.xCoordsCorner,
				this.yCoordsCorner,
				this.xCoordsCorner.length);
		
		super.draw(graphics);
	}

	private void validateShape(TSShape abstractShape, TSTransform transform) {
		if (abstractShape instanceof TSPolygonShape) {
			TSPolygonShape shape = (TSPolygonShape) abstractShape;

			// make sure that x and y coordinate arrays are pre-allocated
			// and have the right size
			if (this.xCoords == null ||	this.xCoords.length != shape.points().size()) {
				this.xCoords = new int[shape.points().size()];
			}

			if (this.yCoords == null ||	this.yCoords.length != shape.points().size()) {
				this.yCoords = new int[shape.points().size()];
			}

			TSDeviceRectangle transformedRect = transform.boundsToDevice(
					this.getOwnerNode().getLocalBounds());

			// check to see if recomputing the points is necessary

			int width = (int) transformedRect.getWidth();
			int height = (int) (transformedRect.getHeight());

			int offsetX = (int) transformedRect.getX();
			int offsetY = (int) transformedRect.getY() + height;

			double hrzScale = width / 100.0;
			double vrtScale = height / 100.0;

			if (hrzScale != this.scaleX ||
					vrtScale != this.scaleY ||
					offsetX != this.shiftX ||
					offsetY != this.shiftY) {
				// recompute the points, use index to store which point we're computing

				int index = 0;

				for (Iterator<?> pointIter = shape.points().iterator();
					pointIter.hasNext();) {
					TSConstPoint point = (TSConstPoint) pointIter.next();
					this.xCoords[index] =
						(int) Math.round(hrzScale * point.getX() + offsetX);
					this.yCoords[index] =
						(int) Math.round(offsetY - vrtScale * point.getY());
					++index;
				}

				// store the new scaling factors for future use
				this.scaleX = hrzScale;
				this.scaleY = vrtScale;
				this.shiftX = offsetX;
				this.shiftY = offsetY;
			}
		}
		else {
			this.validateShape(NoteNodeUI.dummyShape, transform);
		}
	}

}
