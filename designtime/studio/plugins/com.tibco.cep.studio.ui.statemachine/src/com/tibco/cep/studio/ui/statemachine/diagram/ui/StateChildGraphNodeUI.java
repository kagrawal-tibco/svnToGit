package com.tibco.cep.studio.ui.statemachine.diagram.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.image.ImageObserver;

import com.tibco.cep.diagramming.ui.ChildGraphNodeUI;
import com.tomsawyer.drawing.geometry.shared.TSConstRect;
import com.tomsawyer.drawing.geometry.shared.TSRect;
import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;

@SuppressWarnings("serial")
public class StateChildGraphNodeUI extends ChildGraphNodeUI  implements ImageObserver{

	protected TSEImage image = null;
	private static final int IMAGE_SIZE = 11;
	private static final int IMAGE_GAP = 2;	
	
    /* (non-Javadoc)
     * @see com.tibco.cep.diagramming.ui.ChildGraphNodeUI#draw(com.tomsawyer.graphicaldrawing.awt.TSEGraphics, boolean, boolean)
     */
    public void draw(TSEGraphics graphics, boolean respectSelection, boolean selectedOnly) {
       super.draw(graphics, respectSelection, selectedOnly);
       this.drawImage(graphics, new TSEColor(255, 110, 0),
	            TSEColor.white, getLeftTopImageChildGraphMarkBounds());
    }
	
    /**
	 * This method returns child graph mark bounds in the coordinate system of
	 * owner graph for left side image draw
	 */
	public TSRect getLeftTopImageChildGraphMarkBounds()
	{
		TSConstRect nodeBounds = this.getOwnerNode().getLocalBounds();
		TSRect childGraphMarkBounds = new TSRect();
		childGraphMarkBounds.setLeft(nodeBounds.getLeft() + IMAGE_GAP);
		childGraphMarkBounds.setTop(nodeBounds.getTop() - IMAGE_GAP);
		childGraphMarkBounds.setRight(childGraphMarkBounds.getLeft() + IMAGE_SIZE);
		childGraphMarkBounds.setBottom(childGraphMarkBounds.getTop() - IMAGE_SIZE);
		return childGraphMarkBounds;
	}	
	
	/**
	 * @param graphics
	 * @param backgroundColor
	 * @param crossColor
	 * @param localMarkBounds
	 */
	private void drawImage(TSEGraphics graphics,
							TSEColor backgroundColor,
							TSEColor crossColor,
							TSRect localMarkBounds) {
		Shape oldClip = graphics.getClip();
		graphics.clipRect(this.getOwnerNode().getLocalBounds());
		TSTransform transform = graphics.getTSTransform();

		int width = transform.widthToDevice(localMarkBounds.getWidth());
		int height = transform.heightToDevice(localMarkBounds.getHeight());

		int x = transform.xToDevice(localMarkBounds.getLeft());
		int y = transform.yToDevice(localMarkBounds.getTop());

		double worldWidth = image.getImage().getWidth(this);;
		double worldHeight = image.getImage().getHeight(this);;
		
		// get device equivalents
		int devLeft = transform.xToDevice(this.getOwnerNode().getLocalCenterX() - worldWidth / 2.0);
		int devTop = transform.yToDevice(this.getOwnerNode().getLocalCenterY() + (this.getTextHeight() + worldHeight) / 2.0);
		int devWidth = transform.widthToDevice(worldWidth);
		int devHeight = transform.widthToDevice(worldHeight);

		if ((devWidth < 3) && (devHeight < 3)) {
			graphics.setColor(TSEColor.black);
			graphics.drawRect(devLeft, devTop, devWidth, devHeight);
		}
		else {
			x = transform.xToDevice(localMarkBounds.getLeft() + 1);				
			graphics.drawImage(image.getImage(), x, y, width, height, this);
			//Setting the node text adjacent to image label 
			drawString(graphics);
		}
		graphics.setClip(oldClip);
	}
	
	
	/**
	 * @param graphics
	 */
	@SuppressWarnings("deprecation")
	public void drawString(TSEGraphics graphics) {
		TSTransform transform = graphics.getTSTransform();
		String nodeText = this.formattedText;
		if (nodeText != null) {
			TSConstRect ownerBounds = this.getOwner().getLocalBounds();
			Font scaledFont = this.getScaledFont(transform);
			graphics.setFont(scaledFont);
			FontMetrics fm = graphics.getFontMetrics();
			int ascent = fm.getAscent() - fm.getLeading() - fm.getDescent();
			int deviceTextHeight = ascent + fm.getHeight() * (this.getNumberOfLines() - 1);
			// get device equivalents
			double worldWidth = image.getImage().getWidth(this);;
			int devWidth = transform.widthToDevice(worldWidth);
            int top =  transform.yToDevice(ownerBounds.getTop()) + fm.getAscent() + 2;
            int left = transform.xToDevice(ownerBounds.getLeft())+ devWidth;
            graphics.setColor(this.getTextColor().getColor());
			// we also turn on anti-aliasing
			Object oldAntiAliasingHint = null;
			if (this.isTextAntiAliasingEnabled() &&
				deviceTextHeight <= this.getTextAntiAliasingThreshold()) {
				oldAntiAliasingHint = graphics.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			}
			int lineStart = 0;
			int textLength = nodeText.length();
			while (lineStart < textLength) {
				int lineEnd = nodeText.indexOf('\n', lineStart);
				if (lineEnd == -1) {
					lineEnd = textLength;
				}
				String lineString = nodeText.substring(lineStart, lineEnd);
                if (lineString.length() > 0) {
            		graphics.setColor(new TSEColor(Color.BLACK));
            		graphics.drawString(lineString, left, top);
				}
				lineStart = lineEnd + 1;
                top += fm.getHeight() + 2;
            }
            top =  transform.yToDevice(ownerBounds.getTop()) ;
            left = transform.xToDevice(ownerBounds.getRight()-20) ;
            if (this.isTextAntiAliasingEnabled() &&
            	deviceTextHeight <= this.getTextAntiAliasingThreshold()) {
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,oldAntiAliasingHint);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.ui.ChildGraphNodeUI#drawText(com.tomsawyer.graphicaldrawing.awt.TSEGraphics)
	 */
	public void drawText(TSEGraphics graphics) {
		//Not Required as we are setting the text in drawImage
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
