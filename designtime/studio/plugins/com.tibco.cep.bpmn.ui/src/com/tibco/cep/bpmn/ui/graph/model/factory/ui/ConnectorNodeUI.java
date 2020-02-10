package com.tibco.cep.bpmn.ui.graph.model.factory.ui;

import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.ERROR_EVENT_DEFINITION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.MESSAGE_EVENT_DEFINITION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.SIGNAL_EVENT_DEFINITION;
import static com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass.TIMER_EVENT_DEFINITION;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Stroke;

import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.drawing.geometry.shared.TSConstRect;
import com.tomsawyer.drawing.geometry.shared.TSPoint;
import com.tomsawyer.drawing.geometry.shared.TSRect;
import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEConnectorUI;

/**
 * 
 * @author ggrigore
 *
 */
public class ConnectorNodeUI extends TSEConnectorUI {

	private static final long serialVersionUID = 4304750154682951085L;
	protected int outerBorderMultiplier = 1;
	protected double INNER_SIZE = 0.75;
	public static int STATE_TYPE_START=0;
	public static int STATE_TYPE_END=1;
//	protected static final Color START_COLOR = new Color(64, 0, 64);
//	protected static final Color END_COLOR = new Color(64, 0, 64);
	public static Color GLOW_OUTER_HIGH_COLOR = new Color(255, 153, 0);
	
	private EClass eventDefinitionType;
	private TSRect hour;
	
	public ConnectorNodeUI(EClass eventDefType) {
		this.eventDefinitionType = eventDefType;
		this.outerBorderMultiplier = 4;
		this.hour = new TSRect();
		this.hour.setWidth(5);
		this.hour.setHeight(5);		
	}

	
	public EClass getEventDefinitionType() {
		if(this.eventDefinitionType == null) {
			this.eventDefinitionType = (EClass) this.getOwnerConnector().getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
		}
		return eventDefinitionType;
	}

	
	/**
	 * This method draws the object represented by this UI.
	 */
	public void draw(TSEGraphics graphics) {
		 TSEConnector owner = this.getOwnerConnector();

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
			}
			this.drawExtended(graphics);
		
		// we do not draw the border at all
		// we do not draw the text associated with the node
		this.drawHighlight(graphics);
	}

	/**
	 * This method draws the outline of the object represented by this UI. It is
	 * faster than <code>draw</code> and can be used when drawing many objects
	 * during processor- intensive interactive operations such as dragging.
	 */
	protected void drawBorderGlowShape(TSEGraphics graphics) {
         TSRect localBounds = (TSRect) this.getOwner().getLocalBounds();
         graphics.drawOval(localBounds);
        
   }     

	public void drawOutline(TSEGraphics graphics) {
		graphics.drawOval(this.getOwnerConnector().getLocalBounds());
	}
	
	public void drawMessageBadge(TSEGraphics graphics) {
		TSEConnector owner = this.getOwnerConnector();
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
		

		graphics.drawRect(inner);

		TSConstPoint start = new TSConstPoint(inner.getLeft()+1, inner.getTop()-1);
		TSConstPoint end = new TSConstPoint(inner.getLeft() + inner.getWidth()/2.0,
			inner.getTop()-inner.getHeight()/2.0);
		TSConstPoint endOther = new TSConstPoint(inner.getRight()-1, inner.getTop()-1);

		
		graphics.setColor(Color.white);

		
		graphics.drawLine(start, end);
		graphics.drawLine(end, endOther);
		
		graphics.setStroke(oldStroke);
	}

	public void drawTimerBadge(TSEGraphics graphics) {
		TSEConnector owner = this.getOwnerConnector();
		TSConstRect bounds = owner.getLocalBounds();		
		@SuppressWarnings("unused")
		TSTransform transform = graphics.getTSTransform();
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
		TSEConnector owner = this.getOwnerConnector();
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
		
		graphics.drawPolygon(p);

		graphics.setStroke(oldStroke);
	}
	
	public void drawSignalBadge(TSEGraphics graphics) {
		TSEConnector owner = this.getOwnerConnector();
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
		
		x = transform.xToDevice(inner.getRight());	// x2
		y = transform.yToDevice(inner.getBottom()); // --
		p.addPoint(x, y);

		x = transform.xToDevice((inner.getLeft() + inner.getRight()) * 0.5);	// x1
		y = transform.yToDevice(inner.getTop()); // y1
		p.addPoint(x, y);

		

		graphics.drawPolygon(p);

		graphics.setStroke(oldStroke);
	}
	
	public void drawExtended(TSEGraphics graphics) {
		this.drawIntermediate(graphics);
		
		if (MESSAGE_EVENT_DEFINITION.isSuperTypeOf(getEventDefinitionType())) {		
			this.drawMessageBadge(graphics);
		}
		else if (TIMER_EVENT_DEFINITION.isSuperTypeOf(getEventDefinitionType())) {
			this.drawTimerBadge(graphics);
		}
		else if (ERROR_EVENT_DEFINITION.isSuperTypeOf(getEventDefinitionType())) {
			this.drawErrorBadge(graphics);
		}else if (SIGNAL_EVENT_DEFINITION.isSuperTypeOf(getEventDefinitionType())) {
			this.drawSignalBadge(graphics);
		}
	}
	
	private void drawIntermediate(TSEGraphics graphics) {
		TSEConnector owner = this.getOwnerConnector();
		TSConstRect bounds = owner.getLocalBounds();		
		TSRect inner = new TSRect(bounds);
		inner.setWidth(inner.getWidth() * INNER_SIZE);
		inner.setHeight(inner.getHeight() * INNER_SIZE);

		// fill in inner circle
		TSTransform transform = graphics.getTSTransform();
		GradientPaint gradient = new GradientPaint(
			transform.xToDevice(this.getOwner().getLocalLeft()),
			transform.yToDevice(this.getOwner().getLocalTop()),
			new Color(255,203,17), // RoundRectNodeUI.START_COLOR.getColor(), //START_COLOR,
			transform.xToDevice(this.getOwner().getLocalRight()),
			transform.yToDevice(this.getOwner().getLocalBottom()),
			new Color(255,255,255)); // RoundRectNodeUI.END_COLOR.getColor()); // END_COLOR);
		graphics.setPaint(gradient);
		graphics.fillOval(inner);
		
		// draw inner circle
		BasicStroke oldStroke = (BasicStroke) graphics.getStroke();
		Stroke newStroke = new BasicStroke(
			oldStroke.getLineWidth() * this.outerBorderMultiplier,
			oldStroke.getEndCap(),
			oldStroke.getLineJoin(),
			oldStroke.getMiterLimit(),
			oldStroke.getDashArray(),
			oldStroke.getDashPhase());
		graphics.setStroke(newStroke);
		graphics.setColor(GLOW_OUTER_HIGH_COLOR);
		graphics.drawOval(inner);
		graphics.setStroke(oldStroke);
	}

}
