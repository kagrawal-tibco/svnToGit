package com.tibco.cep.diagramming.ui;

import java.awt.Image;
import java.awt.Shape;
import java.awt.image.ImageObserver;

import com.tibco.cep.diagramming.DiagrammingPlugin;
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
public class ActivityBadgeNodeUI extends RoundRectNodeUI implements ImageObserver {
	
	private static final long serialVersionUID = 1L;
	private boolean drawChildGraphMark;
	private boolean drawMinus;
	
	private static TSEImage JAVA_IMAGE = new TSEImage("/icons/javadev_obj.gif");
	private static TSEImage RF_IMAGE = new TSEImage("/icons/rule-function_48x48.png");
	private static TSEImage TABLE_IMAGE = new TSEImage("/icons/table_48x48.png");
	private static TSEImage CQ_IMAGE = new TSEImage("/icons/continuousQuery49x35.png");
	private static TSEImage SQ_IMAGE = new TSEImage("/icons/snapshotQuery46x50.png");
	
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
	
	public ActivityBadgeNodeUI() {
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
	}

	public ActivityBadgeNodeUI(TSEImage image) {
		this();
		this.image = image;
	}	
	
	public ActivityBadgeNodeUI(int type) {
		this();
		if (type == TYPE_RF) {
			this.image = RF_IMAGE;
		}
		else if (type == TYPE_JAVA) {
			this.image = JAVA_IMAGE;
		}
		else if (type == TYPE_TABLE) {
			this.image = TABLE_IMAGE;
		}
		else if (type == TYPE_CQ) {
			this.image = CQ_IMAGE;
		}
		else if (type == TYPE_SQ) {
			this.image = SQ_IMAGE;
		}
		else if (type == TYPE_NONE) {
			this.image = null;
		}
		else {
			DiagrammingPlugin.logErrorMessage("UNKNOWN activity type.");
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
		    if (this.drawMinus)
		    {
//		        this.drawMark(graphics,
//			            TSENodeUI.DEFAULT_CHILD_GRAPH_COLLAPSE_MARK_COLOR,
//			            TSEColor.white,
//						this.getLocalChildGraphMarkBounds(),
//						true);
		        this.drawMark(graphics,
			            new TSEColor(2, 27, 255),
			            TSEColor.white,
						this.getLocalChildGraphMarkBounds(),
						true);		    	
		    }
		    else
		    {
//		        this.drawMark(graphics,
//		            TSENodeUI.DEFAULT_CHILD_GRAPH_EXPAND_MARK_COLOR,
//		            TSEColor.white,
//					this.getLocalChildGraphMarkBounds(),
//					false);
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

		TSEImage image = null; 
		if (this.image != null) {
			image = this.image;
		}
		// take the non-scaled width and height of the image
		if (image == null || image.getImage() == null) {
			graphics.setClip(oldClip);
			return;
		}

		double worldWidth = image.getImage().getWidth(this) + 4;
		double worldHeight = image.getImage().getHeight(this) + 4;
		// get device equivalents
		int devLeft = transform.xToDevice(
				this.getOwnerNode().getLocalCenterX());
		int devTop = transform.yToDevice(
				this.getOwnerNode().getLocalCenterY());
		int devWidth = transform.widthToDevice(worldWidth);
		int devHeight = transform.widthToDevice(worldHeight);

		if ((devWidth < 3) && (devHeight < 3)) {
			graphics.setColor(TSEColor.black);
			graphics.drawRect(devLeft, devTop, devWidth, devHeight);
		}
		else {
			worldWidth = image.getImage().getWidth(this);
			graphics.drawImage(image.getImage(), x, y, width, height, this);
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
