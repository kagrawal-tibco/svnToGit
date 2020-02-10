package com.tibco.cep.diagramming.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Stroke;

import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.drawing.geometry.shared.TSConstRect;
import com.tomsawyer.drawing.geometry.shared.TSPoint;
import com.tomsawyer.drawing.geometry.shared.TSRect;
import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEShapeNodeUI;

/**
 * 
 * @author ggrigore
 *
 */
public class GenericEventNodeUI extends TSEShapeNodeUI {

	private static final long serialVersionUID = 1L;
	protected int outerBorderMultiplier = 5;
	protected double INNER_SIZE = 0.75;
	public static int STATE_TYPE_START=0;
	public static int STATE_TYPE_END=1;
	protected static final Color START_COLOR = new Color(213, 0, 0);
	protected static final Color END_COLOR = new Color(247, 235, 235);
	public static Color GLOW_OUTER_HIGH_COLOR = new Color(255, 153, 0);
	
	private TSRect hour;
	
	public GenericEventNodeUI() {
		this.hour = new TSRect();
		this.hour.setWidth(5);
		this.hour.setHeight(5);		
	}
	
	public void setOuterBorderMultiplier(int i) {
		this.outerBorderMultiplier = i;
	}
	 
	/**
	 * This method draws the object represented by this UI.
	 */
	public void draw(TSEGraphics graphics) {
		TSENode owner = this.getOwnerNode();

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);

		if (!this.isTransparent()) {
			graphics.setColor(GLOW_OUTER_HIGH_COLOR);
			
			// make outer ring a bit thicker		
			BasicStroke oldStroke = (BasicStroke) graphics.getStroke();
			Stroke newStroke = new BasicStroke(
				oldStroke.getLineWidth() * this.outerBorderMultiplier,
				oldStroke.getEndCap(),
				oldStroke.getLineJoin(),
				oldStroke.getMiterLimit(),
				oldStroke.getDashArray(),
				oldStroke.getDashPhase());
			graphics.setStroke(newStroke);
			
			TSConstRect bounds = owner.getLocalBounds();		
			TSRect inner = new TSRect(bounds);
			double offset = oldStroke.getLineWidth() * this.outerBorderMultiplier;
			inner.setWidth(inner.getWidth() - offset);
			inner.setHeight(inner.getHeight() - offset);
			// graphics.drawOval(owner.getLocalBounds());
			graphics.drawOval(inner);
			
			graphics.setStroke(oldStroke);

			this.drawExtended(graphics);
		}

		// we do not draw the border at all
		// we do not draw the text associated with the node
		this.drawHighlight(graphics);
	}

	/**
	 * Add any extra drawing code in subclasses
	 */
	protected void drawExtended(TSEGraphics graphics) { }
	
	/**
	 * This method draws the outline of the object represented by this UI. It is
	 * faster than <code>draw</code> and can be used when drawing many objects
	 * during processor- intensive interactive operations such as dragging.
	 */
	public void drawOutline(TSEGraphics graphics) {
		graphics.drawOval(this.getOwnerNode().getLocalBounds());
	}
	
	public void drawMessageBadge(TSEGraphics graphics) {
		TSENode owner = this.getOwnerNode();
		TSConstRect bounds = owner.getLocalBounds();		
		TSRect inner = new TSRect(bounds);
		inner.setWidth(inner.getWidth() * 0.5);
		inner.setHeight(inner.getHeight() * 0.35);
		
		BasicStroke oldStroke = (BasicStroke) graphics.getStroke();
		Stroke newStroke = new BasicStroke(
			oldStroke.getLineWidth() * 3 /* (this.outerBorderMultiplier-1) */,
			oldStroke.getEndCap(),
			oldStroke.getLineJoin(),
			oldStroke.getMiterLimit(),
			oldStroke.getDashArray(),
			oldStroke.getDashPhase());
		graphics.setStroke(newStroke);
		graphics.setColor(GLOW_OUTER_HIGH_COLOR);
		
		boolean isCatch = this.isCatch();
		if (isCatch) {
			graphics.drawRect(inner);
		}
		else {
			graphics.fillRect(inner);
		}
		
		TSConstPoint start = new TSConstPoint(inner.getLeft()+1, inner.getTop()-1);
		TSConstPoint end = new TSConstPoint(inner.getLeft() + inner.getWidth()/2.0,
			inner.getTop()-inner.getHeight()/2.0);
		TSConstPoint endOther = new TSConstPoint(inner.getRight()-1, inner.getTop()-1);

		if (!isCatch) {
			graphics.setColor(Color.white);
		}
		
		graphics.drawLine(start, end);
		graphics.drawLine(end, endOther);
		
		graphics.setStroke(oldStroke);
	}

	public void drawTimerBadge(TSEGraphics graphics) {
		TSENode owner = this.getOwnerNode();
		TSConstRect bounds = owner.getLocalBounds();		
		TSRect inner = new TSRect(bounds);
		inner.setWidth(inner.getWidth() * 0.55);
		inner.setHeight(inner.getHeight() * 0.55);

		BasicStroke oldStroke = (BasicStroke) graphics.getStroke();
		Stroke newStroke = new BasicStroke(
			oldStroke.getLineWidth() * (this.outerBorderMultiplier-2),
			oldStroke.getEndCap(),
			oldStroke.getLineJoin(),
			oldStroke.getMiterLimit(),
			oldStroke.getDashArray(),
			oldStroke.getDashPhase());
		graphics.setStroke(newStroke);
		graphics.setColor(GLOW_OUTER_HIGH_COLOR);
		
		// draw thin circle (clock border)
		TSRect clock = new TSRect(bounds);
		clock.setWidth(clock.getWidth() * 0.65);
		clock.setHeight(clock.getHeight() * 0.65);
		graphics.drawOval(clock);
		
		double radius = inner.getHeight() / 2.0;
		double a = Math.sin(Math.toRadians(60.0)) * radius;
		double b = Math.cos(Math.toRadians(60.0)) * radius;
		double x = inner.getCenterX();
		double y = inner.getCenterY();
		
		// draw 12 o'clock, then 1, 2, ... 11
		this.drawHour(graphics, x, inner.getTop());
		this.drawHour(graphics, x+b, y+a);
		this.drawHour(graphics, x+a, y+b);
		this.drawHour(graphics, inner.getRight(), y);
		this.drawHour(graphics, x+a, y-b);
		this.drawHour(graphics, x+b, y-a);
		this.drawHour(graphics, x, inner.getBottom());
		this.drawHour(graphics, x-b, y-a);
		this.drawHour(graphics, x-a, y-b);
		this.drawHour(graphics, inner.getLeft(), y);
		this.drawHour(graphics, x-a, y+b);
		this.drawHour(graphics, x-b, y+a);

		// minute hand
		TSPoint lineEndPoint = new TSPoint(inner.getCenterX() + 4, inner.getTop());
		graphics.drawLine(inner.getCenter(), lineEndPoint);
		// hour hand
		lineEndPoint.setX(inner.getLeft() + inner.getWidth() * 0.8);
		lineEndPoint.setY(inner.getCenterY() - 3);
		graphics.drawLine(inner.getCenter(), lineEndPoint);

		graphics.setStroke(oldStroke);
	}
	
	private void drawHour(TSEGraphics graphics, double x, double y) {
		this.hour.setCenter(x, y);
		graphics.fillOval(hour);
	}

	public void drawErrorBadge(TSEGraphics graphics) {
		TSENode owner = this.getOwnerNode();
		TSConstRect bounds = owner.getLocalBounds();		
		TSTransform transform = graphics.getTSTransform();
		TSRect inner = new TSRect(bounds);
		inner.setWidth(inner.getWidth() * 0.5);
		inner.setHeight(inner.getHeight() * 0.5);

		BasicStroke oldStroke = (BasicStroke) graphics.getStroke();
		Stroke newStroke = new BasicStroke(
			oldStroke.getLineWidth() * (this.outerBorderMultiplier-1),
			oldStroke.getEndCap(),
			oldStroke.getLineJoin(),
			oldStroke.getMiterLimit(),
			oldStroke.getDashArray(),
			oldStroke.getDashPhase());
		graphics.setStroke(newStroke);
		graphics.setColor(GLOW_OUTER_HIGH_COLOR);
		
		int x,y;
		Polygon p = new Polygon();
		
		x = transform.xToDevice(inner.getLeft());
		y = transform.yToDevice(inner.getBottom());
		p.addPoint(x, y);
		
		x = transform.xToDevice(inner.getLeft() + inner.getWidth()/4.0);	// x2
		y = transform.yToDevice(inner.getBottom() + inner.getHeight() * 0.85); // --
		p.addPoint(x, y);

		x = transform.xToDevice(inner.getLeft() + inner.getWidth() * 0.75);	// x1
		y = transform.yToDevice(inner.getBottom() + inner.getHeight()/2.0); // y1
		p.addPoint(x, y);

		x = transform.xToDevice(inner.getRight());
		y = transform.yToDevice(inner.getTop());
		p.addPoint(x, y);

		x = transform.xToDevice(inner.getLeft() + inner.getWidth() * 0.75);	// x1
		y = transform.yToDevice(inner.getBottom() + inner.getHeight() * 0.15); // --
		p.addPoint(x, y);
		
		x = transform.xToDevice(inner.getLeft() + inner.getWidth() * 0.25);	// x2
		y = transform.yToDevice(inner.getBottom() + inner.getHeight()/2.0);	// y1
		p.addPoint(x, y);
		
		if (this.isCatch()) {
			graphics.drawPolygon(p);
		}
		else {
			graphics.fillPolygon(p);
		}
		graphics.setStroke(oldStroke);
	}
	
	private boolean isCatch() {
		TSENode owner = this.getOwnerNode();
		if (owner != null) {
//			EClass type = (EClass)owner.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
//			if (BpmnModelClass.CATCH_EVENT.isSuperTypeOf(type)) {
//				return true;
//			}
		}
		return false;
	}
	
}
