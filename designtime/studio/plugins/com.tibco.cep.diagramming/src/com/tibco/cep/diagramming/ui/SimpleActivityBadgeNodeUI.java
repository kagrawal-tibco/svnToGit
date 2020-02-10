package com.tibco.cep.diagramming.ui;

import java.awt.Image;
import java.awt.Shape;
import java.awt.image.ImageObserver;

import com.tibco.cep.diagramming.DiagrammingPlugin;
import com.tomsawyer.drawing.geometry.shared.TSConstRect;
import com.tomsawyer.drawing.geometry.shared.TSRect;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;

/**
 * 
 * @author ggrigore
 *
 */
public class SimpleActivityBadgeNodeUI extends RoundRectNodeUI {
	
	private static final long serialVersionUID = 1L;
	private boolean drawChildGraphMark;
	private boolean drawMinus;
	
	public static int TYPE_NONE = -1;
	public static int TYPE_RF = 0;
	public static int TYPE_JAVA = 1;
	public static int TYPE_TABLE = 2;
	public static int TYPE_CQ = 3;
	public static int TYPE_SQ = 4;
	
	// This constant represents the size of the marks that are drawn
	private static final int MARK_SIZE = 50;
	private static final int TEXT_OFFSET = MARK_SIZE / 2;
	 // This constant represents the distance the marks are from the bounds of the node
	private static final int MARK_GAP = 2;	
	
	private TSEImage image;
	
	public SimpleActivityBadgeNodeUI() {
		this.drawChildGraphMark = true;
		this.drawMinus = true;
		
		// TBS-like shape:
		// FIXME 3.0.2 merge: look for new method signature
		//this.setArcHeight(0.35);
		//this.setArcWidth(0.15);
		
		// TODO: turned off until they work better with rounded rectangles
		this.setBorderDrawn(true);
		this.setDrawGlow(false);
		this.setDrawGradient(true);
		this.setDrawShadow(false);
		
		// TBS-like color:
		this.setFillColor(new TSEColor(255,220,81));
		
		DiagrammingPlugin.log("Done creating simple UI");
	}
	
	public SimpleActivityBadgeNodeUI(int type) {
		this();
		if (type == TYPE_NONE) {
			this.image = null;
		}
		else {
			DiagrammingPlugin.logErrorMessage("UNKNOWN activity type. Using wrong node UI.");
		}
	}
	
	public void setDrawMinus(boolean drawMinus) {
		this.drawMinus = drawMinus;
	}
	
	public void draw(TSEGraphics graphics) {
		super.draw(graphics);
		this.drawChildGraphMark(graphics);
	}
	
	public void drawChildGraphMark(TSEGraphics graphics)
	{
		if (this.drawChildGraphMark)
		{
		    if (this.drawMinus) {
		        this.drawMark(graphics,
			            new TSEColor(2, 27, 255),
			            TSEColor.white,
						this.getLocalChildGraphMarkBounds(),
						true);		    	
		    }
		    else {
		        this.drawMark(graphics,
			            new TSEColor(255, 110, 0),
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
		boolean isCollapseMarker)
	{
		Shape oldClip = graphics.getClip();

		graphics.clipRect(this.getOwnerNode().getLocalBounds());

	/*	TSTransform transform = graphics.getTSTransform();

		int width = transform.widthToDevice(localMarkBounds.getWidth());
		int height = transform.heightToDevice(localMarkBounds.getHeight());
		
		int x = transform.xToDevice(localMarkBounds.getLeft());
		int y = transform.yToDevice(localMarkBounds.getTop());

		int outerAndInnerX = width * 2 / 5;
		int outerAndInnerY = height * 2 / 5;*/
		// experimental //////////////////////////////////////////////

			TSEImage image; 
			if (this.image == null) {
				// PUT BACK: image = JAVA_IMAGE;
				image = null;
			}
			else {
				image = this.image;
			}
			
			// take the non-scaled width and height of the image
			
			if (image == null || image.getImage() == null) {
				// DecisionGraphPlugin.LOGGER.logDebug("NULL image!");
				graphics.setClip(oldClip);
				return;
			}
		graphics.setClip(oldClip);
	}
	
	
	/**
	 * This method returns child graph mark bounds in the coordinate system of
	 * owner graph.
	 */
	public TSRect getLocalChildGraphMarkBounds()
	{
		TSConstRect nodeBounds = this.getOwnerNode().getLocalBounds();
		TSRect childGraphMarkBounds = new TSRect();
		
		childGraphMarkBounds.setLeft(nodeBounds.getLeft() + MARK_GAP+15);
		childGraphMarkBounds.setTop(nodeBounds.getTop() - MARK_GAP);
		childGraphMarkBounds.setRight(childGraphMarkBounds.getLeft() + MARK_SIZE);
		childGraphMarkBounds.setBottom(childGraphMarkBounds.getTop() - MARK_SIZE);
		
		return childGraphMarkBounds;
	}	
	
	public boolean imageUpdate(Image image,	int flags, int x, int y, int width,	int height) {
		return (((flags & ImageObserver.ALLBITS) == 0) ||
			((flags & ImageObserver.FRAMEBITS) == 0));
	}
	
	public double getTextOffsetY() {
		return super.getTextOffsetY() - TEXT_OFFSET;
	}	
}
