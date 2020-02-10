package com.tibco.cep.diagramming.ui;

import java.awt.Color;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.ImageObserver;

import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEShapeNodeUI;

/**
 * 
 * @author ggrigore
 *
 */
@SuppressWarnings("serial")
public class SubStateNodeUI extends TSEShapeNodeUI implements ImageObserver{
	private static final Color BORDER_COLOR = new Color(2, 41, 0);	

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
			graphics.setColor(this.getFillColor());
			// A non-cyclic gradient
			TSTransform transform = graphics.getTSTransform();
			graphics.setColor(BORDER_COLOR);
			graphics.setColor(FinalStateNodeUI.GLOW_OUTER_HIGH_COLOR);
			TSEImage image = new TSEImage(this.getClass(),"/icons/call_statemachine.png");
			int x = transform.xToDevice(this.getOwner().getLocalLeft());
			int y = transform.yToDevice(this.getOwner().getLocalTop());
			int width = transform.widthToDevice(owner.getLocalBounds().getWidth());
			int height = transform.heightToDevice(owner.getLocalBounds().getHeight());
			graphics.drawImage(image.getImage(), x, y, width, height, this);
		}
		this.drawHighlight(graphics);
	}

	/* (non-Javadoc)
	 * @see java.awt.image.ImageObserver#imageUpdate(java.awt.Image, int, int, int, int, int)
	 */
	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y,
			int width, int height) {
		return (((infoflags & ImageObserver.ALLBITS) == 0) ||
				((infoflags & ImageObserver.FRAMEBITS) == 0));
	}

}
