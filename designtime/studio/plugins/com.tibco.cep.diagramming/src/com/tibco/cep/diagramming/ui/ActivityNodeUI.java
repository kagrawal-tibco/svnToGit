package com.tibco.cep.diagramming.ui;

import java.awt.GradientPaint;
import java.awt.RenderingHints;

import com.tomsawyer.drawing.geometry.shared.TSSize;
import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEShapeNodeUI;

/**
 * 
 * @author ggrigore
 *
 */
public class ActivityNodeUI extends TSEShapeNodeUI {

	// TODO: gradient, shadow

	private static final long serialVersionUID = 1L;

	private TSEColor startColor;
	private TSEColor endColor;

//	public static TSEColor START_PRESENT_COLOR = new TSEColor(12, 141, 255);
//	public static TSEColor END_PRESENT_COLOR = new TSEColor(147, 255, 238);
	public static TSEColor START_PRESENT_COLOR = new TSEColor(255, 220, 81);
	public static TSEColor END_PRESENT_COLOR = new TSEColor(255, 255, 255);

	
	
	public ActivityNodeUI() {
		this.startColor = START_PRESENT_COLOR;
		this.endColor = END_PRESENT_COLOR;
//		this.setArcWidth(0.1);
//		this.setArcHeight(0.1);
		this.setBorderDrawn(true);
		
		/* FIXME 3.0.2 merge: get new method signature for TS 9.0
		this.setDrawGlow(false);
		this.setDrawGradient(true);
		this.setDrawShadow(false);
		*/
		
		// TBS-like color:
		this.setFillColor(new TSEColor(255,220,81));	
	}

	/**
	 * This method draws the object represented by this UI.
	 * 
	 * @param graphics
	 *            the <code>TSEGraphics</code> object onto which the UI is being
	 *            drawn.
	 */
	public void draw(TSEGraphics graphics) {
		if (this.getOwnerNode() == null) {
			return;
		}

		TSENode owner = this.getOwnerNode();

		TSSize arcSize = new TSSize(this.getWidth() * owner.getLocalWidth(),
				this.getHeight() * owner.getLocalHeight());

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		if (!this.isTransparent()) {
			this.drawGradient(graphics, arcSize);
		}

		if (this.isBorderDrawn()) {
			graphics.setColor(this.getBorderColor());
			graphics.drawRoundRect(owner.getLocalBounds(), arcSize);
		}

		this.drawText(graphics);
	}

	private void drawGradient(TSEGraphics graphics, TSSize arcSize) {
		TSESolidObject owner = this.getOwnerNode();
		TSTransform transform = graphics.getTSTransform();

		GradientPaint gradient = new GradientPaint(
			transform.xToDevice(owner.getLocalLeft()),
			transform.yToDevice(owner.getLocalTop()),
			this.startColor.getColor(),
			transform.xToDevice(owner.getLocalLeft()),
			transform.yToDevice(owner.getLocalBottom()),
			this.endColor.getColor());
		graphics.setPaint(gradient);

		arcSize = new TSSize(this.getWidth() * owner.getLocalWidth(),
				this.getHeight() * owner.getLocalHeight());

		graphics.fillRoundRect(transform.xToDevice(owner.getLocalLeft()),
				transform.yToDevice(owner.getLocalTop()),
				transform.widthToDevice(owner.getLocalWidth()),
				transform.heightToDevice(owner.getLocalHeight()),
				(int) arcSize.getWidth(),
				(int) arcSize.getHeight());
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
		TSESolidObject owner = this.getOwnerNode();
		TSSize arcSize = null;

		TSTransform transform = graphics.getTSTransform();

		arcSize = new TSSize(this.getWidth() * owner.getLocalWidth(),
				this.getHeight() * owner.getLocalHeight());

		graphics.fillRoundRect(transform.xToDevice(owner.getLocalLeft()),
				transform.yToDevice(owner.getLocalTop()),
				transform.widthToDevice(owner.getLocalWidth()),
				transform.heightToDevice(owner.getLocalHeight()),
				(int) arcSize.getWidth(),
				(int) arcSize.getHeight());
	}
}
