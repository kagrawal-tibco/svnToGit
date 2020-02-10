package com.tibco.cep.diagramming.ui;

import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEImageNodeUI;

/**
 * 
 * @author ggrigore
 *
 */
public class NoTagImageNodeUI extends TSEImageNodeUI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2775774089633495728L;
	
	private boolean drawTag = false;
	TSEImage image ;
	
	public NoTagImageNodeUI(TSEImage image) {
		this.image = image;
		this.setImage(image);
	}

	public void drawText(TSEGraphics graphics) {
		if (this.drawTag) {
			super.drawText(graphics);
		}
	}
	
	public boolean isDrawTag() {
		return drawTag;
	}

	public void setDrawTag(boolean drawTag) {
		this.drawTag = drawTag;
	}
}