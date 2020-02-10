package com.tibco.cep.diagramming.ui;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.RenderingHints;

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
public class InitialNodeUI extends TSEShapeNodeUI {

	private static final long serialVersionUID = 1L;
	protected double INNER_SIZE = 0.65;
	private static final Color START_COLOR = new Color(1, 159, 6);
	private static final Color END_COLOR = new Color(229, 247, 229);	
	// Orange: private static final Color BORDER_COLOR = new Color(2, 41, 0);	
	public static final TSEColor BORDER_COLOR = new TSEColor(START_COLOR);	

	/**
	 * This method draws the object represented by this UI.
	 * 
	 * @param graphics
	 *            the <code>TSEGraphics</code> object onto which the UI is being
	 *            drawn.
	 */
	
	public InitialNodeUI() {
		this.setBorderColor(BORDER_COLOR);
	}
	
	public void draw(TSEGraphics graphics) {
		TSENode owner = this.getOwnerNode();

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		// TODO:
		this.setFillColor(TSEColor.red);

		if (!this.isTransparent()) {
			graphics.setColor(this.getFillColor());

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
			graphics.fillOval(owner.getLocalBounds());

			graphics.setColor(this.getBorderColor());
			// graphics.setColor(FinalStateNodeUI.GLOW_OUTER_HIGH_COLOR);
			graphics.drawOval(owner.getLocalBounds());
		}

		// we do not draw the border at all
		// we do not draw the text associated with the node
		this.drawHighlight(graphics);
	}

	
	
	public void draw2(TSEGraphics graphics)
	{
		TSENode owner = this.getOwnerNode();

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		// TODO:
		this.setFillColor(TSEColor.black);
        
		if (!this.isTransparent())
		{
			graphics.setColor(this.getFillColor());
			graphics.fillOval(owner.getLocalBounds());
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
