package com.tibco.cep.diagramming.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.RenderingHints;
import java.awt.Stroke;

import com.tomsawyer.drawing.geometry.shared.TSConstRect;
import com.tomsawyer.drawing.geometry.shared.TSRect;
import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEShapeNodeUI;

/**
 * 
 * @author ggrigore
 *
 */
public class FinalStateNodeUI extends TSEShapeNodeUI {

	private static final long serialVersionUID = 1L;
	private int outerBorderMultiplier = 2;
	protected double INNER_SIZE = 0.65;
	public static int STATE_TYPE_START=0;
    public static int STATE_TYPE_END=1;
    @SuppressWarnings("unused")
	private int stateType = STATE_TYPE_START;
	private static final Color START_COLOR = new Color(213, 0, 0);
	private static final Color END_COLOR = new Color(247, 235, 235);
	
	 /*private static Color STARTSTATE_FROM_COLOR = new Color(1, 159, 6);
	 private static Color STARTSTATE_TO_COLOR = new Color(229, 247, 229);
	 private static Color ENDSTATE_FROM_COLOR = new Color(213, 0, 0);
	 private static Color ENDSTATE_TO_COLOR = new Color(247, 235, 235);*/
	 public static Color GLOW_OUTER_HIGH_COLOR = new Color(255, 153, 0);

	 public void setStateType(int stateType) {
	        this.stateType = stateType;
	    }
	 
	 public void setOuterBorderMultiplier(int i) {
		 this.outerBorderMultiplier = i;
	 }
	 
	/**
	 * This method draws the object represented by this UI.
	 * 
	 * @param graphics
	 *            the <code>TSEGraphics</code> object onto which the UI is being
	 *            drawn.
	 */
	public void draw(TSEGraphics graphics) {
		TSENode owner = this.getOwnerNode();

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);

		if (!this.isTransparent()) {
			graphics.setColor(TSEColor.black);

			TSConstRect bounds = owner.getLocalBounds();
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
			graphics.drawOval(owner.getLocalBounds());
			graphics.setStroke(oldStroke);


			TSRect inner = new TSRect(bounds);
			inner.setWidth(inner.getWidth() * INNER_SIZE);
			inner.setHeight(inner.getHeight() * INNER_SIZE);

//			Color startColor = stateType == STATE_TYPE_START ? STARTSTATE_FROM_COLOR : ENDSTATE_FROM_COLOR;
//	        Color toColor = stateType == STATE_TYPE_START ? STARTSTATE_TO_COLOR : ENDSTATE_TO_COLOR;
	            
			// A non-cyclic gradient
			TSTransform transform = graphics.getTSTransform();
			GradientPaint gradient = new GradientPaint(
				transform.xToDevice(this.getOwner().getLocalLeft()),
				transform.yToDevice(this.getOwner().getLocalTop()),
				START_COLOR,
				transform.xToDevice(this.getOwner().getLocalRight()),
				transform.yToDevice(this.getOwner().getLocalBottom()),
				END_COLOR);
			graphics.setPaint(gradient);

			graphics.fillOval(inner);
		}

		// we do not draw the border at all
		// we do not draw the text associated with the node
		this.drawHighlight(graphics);
	}
	
	public void draw2(TSEGraphics graphics) {
		TSENode owner = this.getOwnerNode();

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
        
		if (!this.isTransparent())
		{
			// graphics.setColor(this.getFillColor());
			graphics.setColor(TSEColor.black);
			
			TSConstRect bounds = owner.getLocalBounds();
			graphics.drawOval(owner.getLocalBounds());
			
			TSRect inner = new TSRect(bounds);
			inner.setWidth(inner.getWidth() * INNER_SIZE);
			inner.setHeight(inner.getHeight() * INNER_SIZE);
			
			graphics.fillOval(inner);
		}

		// we do not draw the border at all
		// we do not draw the text associated with the node
	}

	/**
	 * This method draws the outline of the object represented by this UI. It is
	 * faster than <code>draw</code> and can be used when drawing many objects
	 * during processor- intensive interactive operations such as dragging.
	 * 
	 * @param graphics
	 *            the <code>TSEGraphics</code> object onto which the UI is being
	 *            drawn.
	 */
	public void drawOutline(TSEGraphics graphics) {
		graphics.drawOval(this.getOwnerNode().getLocalBounds());
	}

}
