package com.tibco.cep.diagramming.ui;

import java.awt.RenderingHints;

import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;

/**
 * 
 * @author ggrigore
 *
 */
public class ActionNodeUI extends BadgeRectNodeUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TSEColor actionColor = new TSEColor(255, 251, 221);
	private TSEColor startColor = new TSEColor(255, 207, 196);
	
	public ActionNodeUI() {
		
		this.setTextAntiAliasingEnabled(false);
		this.setDrawShadow(true);
		this.setShadowWidth(6);
		this.setDrawGradient(true);
		this.setGradient(this.startColor, this.actionColor);
		
		this.setDrawMinus(false);
	}

	public void draw(TSEGraphics graphics)
	    {
	        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON);
	        
	    	super.draw(graphics);
	    }
}
