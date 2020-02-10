package com.tibco.cep.decision.tree.ui.nodes;

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
import com.tomsawyer.graphicaldrawing.ui.composite.TSCompositeNodeUI;

/*
@author ssailapp
@date Sep 19, 2011
 */

@SuppressWarnings("serial")
public abstract class TerminalNodeUI extends TSCompositeNodeUI {

	private int outerBorderMultiplier = 2;
	protected double INNER_SIZE = 0.65;

	public void setStateType(int stateType) {
	}

	public void setOuterBorderMultiplier(int i) {
		this.outerBorderMultiplier = i;
	}

	public void draw(TSEGraphics graphics) {
		TSENode owner = this.getOwnerNode();

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (!this.isTransparent()) {
			graphics.setColor(TSEColor.black);

			TSConstRect bounds = owner.getLocalBounds();
			graphics.setColor(getGlowColor());

			// make outer ring a bit thicker
			BasicStroke oldStroke = (BasicStroke) graphics.getStroke();
			Stroke newStroke = new BasicStroke(oldStroke.getLineWidth() * this.outerBorderMultiplier,
					oldStroke.getEndCap(), oldStroke.getLineJoin(), oldStroke.getMiterLimit(),
					oldStroke.getDashArray(), oldStroke.getDashPhase());
			graphics.setStroke(newStroke);
			graphics.drawOval(owner.getLocalBounds());
			graphics.setStroke(oldStroke);

			TSRect inner = new TSRect(bounds);
			inner.setWidth(inner.getWidth() * INNER_SIZE);
			inner.setHeight(inner.getHeight() * INNER_SIZE);

			// A non-cyclic gradient
			TSTransform transform = graphics.getTSTransform();
			GradientPaint gradient = new GradientPaint(transform.xToDevice(this.getOwner().getLocalLeft()),
					transform.yToDevice(this.getOwner().getLocalTop()), getStartColor(), transform.xToDevice(this
							.getOwner().getLocalRight()), transform.yToDevice(this.getOwner().getLocalBottom()), getEndColor());
			graphics.setPaint(gradient);

			graphics.fillOval(inner);
		}

		// we do not draw the border at all
		// we do not draw the text associated with the node
		//TODO - this.drawHighlight(graphics);
	}

	public void draw2(TSEGraphics graphics) {
		TSENode owner = this.getOwnerNode();

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (!this.isTransparent()) {
			graphics.setColor(TSEColor.black);

			TSConstRect bounds = owner.getLocalBounds();
			graphics.drawOval(owner.getLocalBounds());

			TSRect inner = new TSRect(bounds);
			inner.setWidth(inner.getWidth() * INNER_SIZE);
			inner.setHeight(inner.getHeight() * INNER_SIZE);

			graphics.fillOval(inner);
		}
	}

	private boolean isTransparent() {
		return false;
	}

	public void drawOutline(TSEGraphics graphics) {
		graphics.drawOval(this.getOwnerNode().getLocalBounds());
	}

	public abstract Color getStartColor();
	
	public abstract Color getEndColor();
	
	public abstract Color getGlowColor();
}
