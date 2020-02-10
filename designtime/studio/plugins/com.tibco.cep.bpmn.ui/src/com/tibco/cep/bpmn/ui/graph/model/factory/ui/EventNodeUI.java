package com.tibco.cep.bpmn.ui.graph.model.factory.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Stroke;

import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.diagramming.ui.ShapeNodeUI;
import com.tomsawyer.drawing.geometry.shared.TSConstRect;
import com.tomsawyer.drawing.geometry.shared.TSPoint;
import com.tomsawyer.drawing.geometry.shared.TSRect;
import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;

/**
 * 
 * @author ggrigore
 *
 */
public class EventNodeUI extends ShapeNodeUI {

	private static final long serialVersionUID = 1L;
	protected int outerBorderMultiplier = 1;
	protected double INNER_SIZE = 0.75;
	public static int STATE_TYPE_START=0;
	public static int STATE_TYPE_END=1;
	/*protected static final Color START_COLOR = new Color(213, 0, 0);
	protected static final Color END_COLOR = new Color(247, 235, 235);*/
	public static Color GLOW_OUTER_HIGH_COLOR = new Color(255, 153, 0);
	public static Color GLOW_OUTER_LOW_COLOR = new Color(255, 251, 221);
	
	private EClass eventDefinitionType;
	private TSRect hour;
	
	public EventNodeUI(EClass eventDefType) {
		this.eventDefinitionType = eventDefType;
		this.hour = new TSRect();
		this.hour.setWidth(5);
		this.hour.setHeight(5);		
	}
	
	public EClass getEventDefinitionType() {
		if(this.eventDefinitionType == null) {
			this.eventDefinitionType = (EClass) this.getOwnerNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
		}
		return eventDefinitionType;
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
		
		if (isDrawGlow()) {
			this.drawBorderGlow(graphics,8);
		}
		
		if (!this.isTransparent()) {
			graphics.setColor(getColor());
			
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
		
		if (isShowBreakPoints()) {
			if (isInputBreakpointHit()) {
				drawBreakpointHit(graphics, true, Color.RED);
			}  else {
				this.drawInputBreakpoints(graphics, isInputBreakpoint(), isInputBreakPointToggle(), GLOW_OUTER_HIGH_COLOR);
			}
		}
		
		if(isDrawError()) {
			drawError(graphics,Color.RED, 5);
		}
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
	protected void drawBorderGlowShape(TSEGraphics graphics) {
         TSRect localBounds = (TSRect) this.getOwner().getLocalBounds();
         graphics.drawOval(localBounds);
        
   }     

	public void drawOutline(TSEGraphics graphics) {
		graphics.drawOval(this.getOwnerNode().getLocalBounds());
	}
	
	
	public void drawMessageBadge(TSEGraphics graphics) {
		TSENode owner = this.getOwnerNode();
		TSConstRect bounds = owner.getLocalBounds();		
		TSRect badgeBounds = new TSRect(bounds);
		badgeBounds.setWidth(badgeBounds.getWidth() * 0.5);
		badgeBounds.setHeight(badgeBounds.getHeight() * 0.35);	
		
		
		BasicStroke oldStroke = (BasicStroke) graphics.getStroke();
		Stroke newStroke = new BasicStroke(
			oldStroke.getLineWidth() * 3 /* (this.outerBorderMultiplier-1) */,
			oldStroke.getEndCap(),
			oldStroke.getLineJoin(),
			oldStroke.getMiterLimit(),
			oldStroke.getDashArray(),
			oldStroke.getDashPhase());
		graphics.setStroke(newStroke);
		graphics.setColor(getColor());
		
		this.drawMessageBackground(graphics, badgeBounds);		
		
		
		
		super.drawMessageBadge(graphics, badgeBounds);
 		graphics.setStroke(oldStroke);		
	}
	
	protected void drawMessageBackground(TSEGraphics graphics, TSRect badgeBounds) {
		boolean isCatch = this.isCatch();

		if (isCatch) {
			graphics.drawRect(badgeBounds);
		}
		else {
			graphics.fillRect(badgeBounds);
		}

		if (!isCatch) {
			graphics.setColor(Color.white);
		}
	}

	public void drawTimerBadge(TSEGraphics graphics) {
		TSENode owner = this.getOwnerNode();
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
		graphics.setColor(getColor());
		
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
		graphics.setColor(getColor());
		
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
	
	public void drawSignalBadge(TSEGraphics graphics) {
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
		graphics.setColor(getColor());
		
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
			EClass type = (EClass)owner.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
			if ( type != null && BpmnModelClass.CATCH_EVENT.isSuperTypeOf(type)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param graphics
	 * @param inputBreakpointHit
	 */
	public void drawBreakpointHit(TSEGraphics graphics, boolean inputBreakpointHit , 
            Color color) {
		Color oldColor = graphics.getColor();
		graphics.setColor(oldColor);
		TSConstRect ownerBounds = this.getOwner().getLocalBounds();
		TSTransform transform = graphics.getTSTransform();	
		if (inputBreakpointHit) {

//			drawImage(graphics, RADIO_BRK_HIT_IMAGE, new TSEColor(255, 110, 0), TSEColor.white, inputBreakPointBounds);
//			drawOutputBreakpoints(graphics,hasOutBreakpoint, outBrkPoinToggle);
			
			drawInputBreakpoints(graphics, true, false, color);
			drawBreakPointLeft(graphics, ownerBounds, transform, color);
		} 
	}
	
	private void drawBreakPointLeft(TSEGraphics graphics, TSConstRect ownerBounds, TSTransform transform, Color color) {
		Color oldColor = graphics.getColor();
		int x = transform.xToDevice(ownerBounds.getLeft() + INNER_BREAKPOINT_SIZE );
		int y = transform.yToDevice(ownerBounds.getCenterY() + INNER_BREAKPOINT_SIZE - 3);
		graphics.fillOval(x - 2 + 6, y - DEFAULT_BREAKPOINT_OFFSET, transform.widthToDevice(INNER_BREAKPOINT_SIZE), transform.heightToDevice(INNER_BREAKPOINT_SIZE));
		graphics.setColor(oldColor);
		oldColor = graphics.getColor();
		graphics.setColor(color);
	}
	
	/**
	 * @param graphics
	 * @param inputBrkPnt
	 * @param inBrkPnToggle
	 * @param outputBrkPnt
	 * @param outBrkPnToggle
	 */
	public void drawInputBreakpoints(TSEGraphics graphics, 
				                    boolean inputBrkPnt, 
				                    boolean inBrkPnToggle, 
				                    Color color) {
		Color oldColor = graphics.getColor();
		graphics.setColor(oldColor);
		TSConstRect ownerBounds = this.getOwner().getLocalBounds();
		TSTransform transform = graphics.getTSTransform();	
//		Color color = this.breakpointColor;
		graphics.setColor(color);
		setInputBreakPointBounds(new TSConstRect(
	            	ownerBounds.getLeft() + 1 + 2,
	            	ownerBounds.getCenterY() + DEFAULT_BREAKPOINT_SIZE/2,
	            	ownerBounds.getLeft() + 1 + DEFAULT_BREAKPOINT_SIZE + 2,
	            	ownerBounds.getCenterY() - DEFAULT_BREAKPOINT_SIZE/2));
		graphics.drawOval(getInputBreakPointBounds());
		//Draw Border Shadow for the outer one
		drawBorderShadow(graphics, 2, getInputBreakPointBounds(), false);

	//Draw Image
//		if (inputBrkPnt && inBrkPnToggle) {
//			drawImage(graphics, RADIO_ON_IMAGE, new TSEColor(255, 110, 0), TSEColor.white, inputBreakPointBounds);
//		} else {
//			drawImage(graphics, RADIO_OFF_IMAGE, new TSEColor(255, 110, 0), TSEColor.white, inputBreakPointBounds);
//		}
		graphics.setColor(oldColor);
		oldColor = graphics.getColor();
		graphics.setColor(color);

		//Draw Border Shadow for the outer one
//		drawBorderShadow(graphics, 4, inputBreakPointBounds, true);
		
		graphics.setColor(oldColor);
		oldColor = graphics.getColor();
		graphics.setColor(color);
		
		if (inputBrkPnt && inBrkPnToggle) {
			int x = transform.xToDevice(ownerBounds.getLeft() + INNER_BREAKPOINT_SIZE );
			int y = transform.yToDevice(ownerBounds.getCenterY() + INNER_BREAKPOINT_SIZE - 3);
			graphics.fillOval(x - 2 + 6, y - DEFAULT_BREAKPOINT_OFFSET, transform.widthToDevice(INNER_BREAKPOINT_SIZE), transform.heightToDevice(INNER_BREAKPOINT_SIZE));
			graphics.setColor(oldColor);
			oldColor = graphics.getColor();
			graphics.setColor(color);
		}
	}
	
	protected void drawBorderShadow(TSEGraphics graphics, int shadowWidth, TSConstRect rect, boolean isArc) {
		Stroke oldStroke = graphics.getStroke();
		int sw = shadowWidth;
		if (!isArc) {
			sw = shadowWidth * 2;
		} 
		for (int i = sw; i >= 2; i -= 2) {
			float pct = (float) (sw - i) / (sw - 1);
			graphics.setStroke(new BasicStroke(i));
			if (isArc) {
				graphics.setColor(getMixedColor(GLOW_OUTER_HIGH_COLOR, pct, GLOW_OUTER_LOW_COLOR, 1.0f - pct));
				graphics.drawArc(rect, 400, 200);
			} else {
				graphics.drawOval(rect);
			}
		}
		graphics.setStroke(oldStroke);
	}
	public void setNodeColor(){
		
		//this.setBorderDrawn(true);
		//this.setBorderColor(getColor());
		GLOW_OUTER_HIGH_COLOR = (getColor()).getColor();
		GLOW_OUTER_LOW_COLOR =  (getColor()).getColor();
	}
	
}
