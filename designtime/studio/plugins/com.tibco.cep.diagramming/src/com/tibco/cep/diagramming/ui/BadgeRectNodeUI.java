package com.tibco.cep.diagramming.ui;

import java.awt.Image;
import java.awt.Shape;
import java.awt.image.ImageObserver;

import com.tomsawyer.drawing.geometry.shared.TSConstRect;
import com.tomsawyer.drawing.geometry.shared.TSRect;
import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;

/**
 * 
 * @author ggrigore
 *
 */
public class BadgeRectNodeUI extends RectNodeUI implements ImageObserver {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//	private static final long serialVersionUID = 1L;
	private boolean drawChildGraphMark;
	private boolean drawMinus;
	
	private static TSEImage COLLAPSE_IMAGE = new TSEImage("/icons/collapseall.gif");
	private static TSEImage EXPAND_IMAGE = new TSEImage("/icons/expandall.gif");
	private static TSEImage TABLE_IMAGE = new TSEImage("/icons/table.gif");
	private static TSEImage OPENCOND_IMAGE = new TSEImage("/icons/openCond.gif");
	private static TSEImage GENREPORT_IMAGE = new TSEImage("/icons/genrepor.gif");
	
	private static TSEColor EXPANDED_COLOR = new TSEColor(2, 27, 255);
	private static TSEColor COLLAPSED_COLOR = new TSEColor(255, 110, 0);
	
	// This constant represents the size of the marks that are drawn
	private static final int MARK_SIZE = 11;
	 // This constant represents the distance the marks are from the bounds of the node
	private static final int MARK_GAP = 2;	
	
	public BadgeRectNodeUI() {
		this.drawChildGraphMark = true;
		this.drawMinus = true;
		// TSEImage.setLoaderClass(this.getClass());		
	}
	
	public void setDrawMinus(boolean drawMinus) {
		this.drawMinus = drawMinus;
	}
	
	public void draw(TSEGraphics graphics) {
		super.draw(graphics);
		this.drawChildGraphMark(graphics);
	}
	
	public void drawChildGraphMark(TSEGraphics graphics) {
		if (this.drawChildGraphMark) {
		    if (this.drawMinus) {
		        this.drawMark(graphics,
			            EXPANDED_COLOR,
			            TSEColor.white,
						this.getLocalChildGraphMarkBounds(),
						true);		    	
		    }
		    else  {
		        this.drawMark(graphics,
			            COLLAPSED_COLOR,
			            TSEColor.white,
						this.getLocalChildGraphMarkBounds(),
						false);
		    }
		}
	}
	
	/**
	 * This method is used to draw a box with a plug sign in the center
	 * with the given bounds and with the given colors
	 */
	private void drawMark(TSEGraphics graphics,
		TSEColor backgroundColor,
		TSEColor crossColor,
		TSRect localMarkBounds,
		boolean isCollapseMarker) {
		
		Shape oldClip = graphics.getClip();
		graphics.clipRect(this.getOwnerNode().getLocalBounds());
		TSTransform transform = graphics.getTSTransform();
		int width = transform.widthToDevice(localMarkBounds.getWidth());
		int height = transform.heightToDevice(localMarkBounds.getHeight());
		int x = transform.xToDevice(localMarkBounds.getLeft());
		int y = transform.yToDevice(localMarkBounds.getTop());

		// only draw something black if images are too small...
//		if ((width < 5) && (height < 5))
//		{
//			graphics.setColor(TSEColor.black);
//			graphics.drawRect(x, y,	width, height);
//			return;
//		}		
		int outerX = width / 5;
		int outerY = height / 5;
		int outerAndInnerX = width * 2 / 5;
		int outerAndInnerY = height * 2 / 5;
		int centerX = width - outerAndInnerX * 2;
		int centerY = height - outerAndInnerY * 2;

		boolean useImage = true;
		
		if (!useImage) {
			// draw the border
			graphics.setColor(TSEColor.black);
			graphics.drawRect(x, y, width, height);
	
			graphics.setColor(backgroundColor);
			graphics.fillRect(x, y, width, height);
	
			graphics.setColor(crossColor);
			graphics.fillRect(
				x + outerX,
				y + outerAndInnerY,
				width - 2 * outerX,
				centerY);
	
			if (!isCollapseMarker) {
				graphics.fillRect(
					x + outerAndInnerX,
					y + outerY,
					centerX,
					height - 2 * outerY);
			}
		}
		
		// experimental //////////////////////////////////////////////

		if (useImage) {
			TSEImage image;
			if (isCollapseMarker) {
				image = COLLAPSE_IMAGE;
			}
			else {
				image = EXPAND_IMAGE;
			}
			
			// take the unscaled width and height of the image
			if (image == null || image.getImage() == null) {
				// DiagrammingPlugin.debug("BadgeRectNodeUI", "NULL image");
				graphics.setClip(oldClip);
				return;
			}
			
			double worldWidth = image.getImage().getWidth(this);;
			double worldHeight = image.getImage().getHeight(this);;
			// get device equivalents
			int devLeft = transform.xToDevice(
					this.getOwnerNode().getLocalCenterX() - worldWidth / 2.0);
			int devTop = transform.yToDevice(
				this.getOwnerNode().getLocalCenterY() +
				(this.getTextHeight() + worldHeight) / 2.0);
			int devWidth = transform.widthToDevice(worldWidth);
			int devHeight = transform.widthToDevice(worldHeight);
				
			if ((devWidth < 3) && (devHeight < 3)) {
				graphics.setColor(TSEColor.black);
				graphics.drawRect(devLeft, devTop, devWidth, devHeight);
			}
			else {
				// graphics.drawImage(image.getImage(), devLeft, devTop, devWidth, devHeight, this);
				graphics.drawImage(image.getImage(), x, y, width, height, this);
			}
			
			x = transform.xToDevice(localMarkBounds.getRight() + 1.0);
			graphics.drawImage(TABLE_IMAGE.getImage(), x, y, width, height, this);
			x = transform.xToDevice(localMarkBounds.getRight() + localMarkBounds.getWidth() + 1.0);
			graphics.drawImage(OPENCOND_IMAGE.getImage(), x, y, width, height, this);
			
			if (!isCollapseMarker) {
				x = transform.xToDevice(this.getOwnerNode().getLocalRight() - localMarkBounds.getWidth() - 1.0);
				graphics.drawImage(GENREPORT_IMAGE.getImage(), x, y, width, height, this);
			}
		}
		
		graphics.setClip(oldClip);
	}
	
	
	/**
	 * This method returns child graph mark bounds in the coordinate system of owner graph.
	 */
	public TSRect getLocalChildGraphMarkBounds() {
		TSConstRect nodeBounds = this.getOwnerNode().getLocalBounds();
		TSRect childGraphMarkBounds = new TSRect();
		
		childGraphMarkBounds.setLeft(nodeBounds.getLeft() + MARK_GAP);
		childGraphMarkBounds.setTop(nodeBounds.getTop() - MARK_GAP);
		childGraphMarkBounds.
			setRight(childGraphMarkBounds.getLeft() + MARK_SIZE);
		childGraphMarkBounds.
			setBottom(childGraphMarkBounds.getTop() - MARK_SIZE);
		
		return childGraphMarkBounds;
	}	
	
	public boolean imageUpdate(Image image, int flags, int x, int y, int width, int height)	{
			return (((flags & ImageObserver.ALLBITS) == 0) ||
				((flags & ImageObserver.FRAMEBITS) == 0));
	}	
}
