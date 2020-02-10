package com.tibco.cep.diagramming.ui;

import java.awt.RenderingHints;

import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;

/**
 * 
 * @author ggrigore
 *
 */
public class ConditionNodeUI extends BadgeRectNodeUI {

	private static final long serialVersionUID = 1L;

	public ConditionNodeUI() {

		this.setTextAntiAliasingEnabled(false);
		this.setBorderDrawn(true);
		this.setDrawShadow(true);
		this.setShadowWidth(6);
		this.setDrawGradient(true);
		this.setGradient(new TSEColor(122, 217, 255), new TSEColor(216, 255, 231));
	}

	public void draw(TSEGraphics graphics) {
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		super.draw(graphics);
	}
}
