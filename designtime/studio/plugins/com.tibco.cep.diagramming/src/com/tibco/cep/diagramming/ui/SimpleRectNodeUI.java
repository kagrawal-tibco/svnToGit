package com.tibco.cep.diagramming.ui;

import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;
import com.tomsawyer.graphicaldrawing.ui.simple.TSENodeUI;

/**
 * 
 * @author ggrigore
 *
 */
public class SimpleRectNodeUI extends TSENodeUI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8643330973817871386L;
	private static final TSEColor DEFAULT_FILL_COLOR = new TSEColor(255,255,153);
	
	public void draw(TSEGraphics graphics) {
		// draw yourself only if you have an owner
		if (this.getOwnerNode() != null) {
			TSESolidObject owner = this.getOwnerNode();

			// draw the background of the node if necessary
			if (!this.isTransparent()) {
				graphics.setColor(this.getFillColor());
				graphics.fillRect(owner.getLocalBounds());
			}

			// draw the border of the node if necessary
			if (this.isBorderDrawn()) {
				graphics.setColor(this.getBorderColor());
				graphics.drawRect(owner.getLocalBounds());
			}

			// draw the text of the node if necessary
			if (this.getOwner().getText() != null) {
				this.drawText(graphics);
			}
		}
	}

	public TSEColor getDefaultFillColor() {
		return DEFAULT_FILL_COLOR;
	}
}