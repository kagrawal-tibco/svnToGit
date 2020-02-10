package com.tibco.cep.studio.ui.diagrams;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Iterator;
import java.util.List;

import com.tibco.cep.diagramming.ui.RoundRectNodeUI;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.util.ImageIconsFactory;
import com.tomsawyer.drawing.geometry.shared.TSConstRect;
import com.tomsawyer.drawing.geometry.shared.TSRect;
import com.tomsawyer.drawing.geometry.shared.TSSize;
import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.drawing.geometry.shared.TSDeviceRectangle;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;
import com.tomsawyer.interactive.swing.TSSwingCanvas;

/**
 * author: ggrigore
 * 
 * This is started off from the RuleNodeUI class I wrote a long time ago for RuleAnalyzer.
 * I changed it so it doesn't extend TSENodeUI but rather RoundRectNodeUI
 */
public class EntityNodeUI extends RoundRectNodeUI implements ImageObserver {

	private static final long serialVersionUID = 1L;
	public static final TSEImage CONCEPT_IMAGE = new TSEImage("/icons/concept.png");	
	public static final TSEImage SIMPLE_EVENT_IMAGE = new TSEImage("/icons/event.png");
	public static final TSEImage TIME_EVENT_IMAGE = new TSEImage("/icons/time-event.gif");
	private static final TSEImage BOOLEAN_IMAGE = new TSEImage("/icons/iconBoolean16.gif");
	private static final TSEImage CONCEPTREF_IMAGE = new TSEImage("/icons/PropertyConceptReference.png");
	private static final TSEImage CONCEPTCON_IMAGE = new TSEImage("/icons/PropertyContainedConcept.png");
	private static final TSEImage DATETIME_IMAGE = new TSEImage("/icons/iconDate16.gif");
	private static final TSEImage DOUBLE_IMAGE = new TSEImage("/icons/iconReal16.gif");
	private static final TSEImage INTEGER_IMAGE = new TSEImage("/icons/iconInteger16.gif");
	private static final TSEImage LONG_IMAGE = new TSEImage("/icons/iconLong16.gif");
	private static final TSEImage STRING_IMAGE = new TSEImage("/icons/iconString16.gif");
	private static final TSEImage PROPERTY_IMAGE = new TSEImage("/icons/Property.png");
	public static final TSEImage METRIC_IMAGE = new TSEImage("/icons/metric_16x16.gif");
	public static final TSEColor START_COLOR = new TSEColor(255, 220, 81);
	public static final TSEColor END_COLOR = new TSEColor(255, 255, 255);
	public static final TSEColor METRIC_START_COLOR = new TSEColor(255,	127,36);
	public static final TSEColor METRIC_END_COLOR = new TSEColor(255, 255, 255);
	
	// This constant represents the size of the marks that are drawn
	private static final int MARK_SIZE = 11;
	// This constant represents the distance the marks are from the bounds of the node
	private static final int MARK_GAP = 2;
	private static final int PROPERTY_IMAGE_SIZE = 9;
	private static final int ARC_SIZE = 9;
	private static final TSSize ARC_SIZE_RECT = new TSSize(ARC_SIZE, ARC_SIZE);
	
	private TSSwingCanvas canvas = null;
	private static Image image;
	private double barHeight;
	private int padding;
	private TSEColor startColor = null;
	private TSEColor endColor = null;
	
	private int minAttributeSize = 3;
	// TODO: expose in preference
	private int showNumberOfAttributes = 4;
	private TSEImage iconimage;

	public EntityNodeUI() {
		super();
		this.setImage(CONCEPT_IMAGE);
		this.setDrawHideMark(false);
		// NEW:
		this.setDrawShadow(true);
//		this.setArcHeight(0.15);
//		this.setArcWidth(0.10);
		this.setRoundRect(true);
		this.setGradient(START_COLOR, END_COLOR);
	}
	
	public void setImage(TSEImage image) {
		this.iconimage = image;
	}
	
	public TSEImage getImage() {
		return this.iconimage;
	}

	// ---------------------------------------------------------------------
	// Section: Drawing
	// ---------------------------------------------------------------------

	/**
	 * This method draws the node represented by this UI.
	 * 
	 * @param graphics
	 *            the <code>TSEGraphics</code> object onto which the UI is being
	 *            drawn.
	 */
	public void draw(TSEGraphics graphics) {
		// draw yourself only if you have an owner

		if (this.getOwnerNode() != null) {
			Object oldValue = graphics.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			TSESolidObject owner = this.getOwnerNode();

			// draw the background of the node if necessary
			if (!this.isTransparent()) {
				graphics.setColor(this.getFillColor());
				graphics.fillRect(owner.getLocalBounds());
			}

			if (this.canvas != null) // this means we have set size already
			{
				// NEW:
				if (isDrawShadow()) {
					// this.drawBorderGlow(graphics, 5);
					this.drawBorderShadow(graphics, this.getShadowWidth());
				}

				this.drawGUI(graphics);
			}

			// draw the border of the node if necessary
			if (this.isBorderDrawn()) {
				graphics.setColor(this.getBorderColor());

				if (this.isRoundRect()) {
					// TSSize DEFAULT_ARC_TS_SIZE = new TSSize(this.getWidth() * owner.getLocalWidth(), this.getHeight() * owner.getLocalHeight());
					// DEFAULT_ARC_TS_SIZE = new TSSize(DEFAULT_ARC_SIZE, DEFAULT_ARC_SIZE);
					// TSSize DEFAULT_ARC_TS_SIZE = ARC_SIZE_RECT;

					TSTransform transform = graphics.getTSTransform();

					graphics.drawRoundRect(
						transform.xToDevice(owner.getLocalLeft()),
						transform.yToDevice(owner.getLocalTop()),
						transform.widthToDevice(owner.getLocalWidth()),
						transform.heightToDevice(owner.getLocalHeight()),
						(int) ARC_SIZE_RECT.getWidth(),
						(int) ARC_SIZE_RECT.getHeight());
				} else {
					graphics.drawRect(owner.getLocalBounds());
				}
			}

			// we don't draw the hide mark
//			if (!this.isExpanded()) {
				/*
				 * this.drawHideMark(graphics,
				 * TSENodeUI.DEFAULT_HIDE_MARK_COLOR, TSEColor.white,
				 * this.getLocalHideMarkBounds(), false);
				 */
//			}

			//graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldValue);
		}
	}

	/**
	 * @param canvas
	 */
	public void resize(TSSwingCanvas canvas) {
		if (EntityNodeUI.image == null) {
			EntityNodeUI.image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
		}
		
		this.canvas = canvas;

		TSEGraphics graphics = canvas.newGraphics(EntityNodeUI.image.getGraphics());

		// see how much space is necessary for a line of text in this font
		graphics.setFont(this.getFont());
		FontMetrics fm = graphics.getFontMetrics();
		int fontHeight = fm.getAscent() - fm.getLeading() - fm.getDescent();

		this.padding = fontHeight / 3;
		this.barHeight = fontHeight * 1.66;

//		if (this.isExpanded()) {
//			this.getOwnerNode().setHeight(
//				(this.getAttributes().size() + 1) * (fontHeight*1.47));
//		} else {
//			this.getOwnerNode().setHeight(fontHeight*1.47);
//		}

		
		int nrAttrs;
		
		nrAttrs = this.getAttributes().size();
		if (this.isCollapsed()) {
			nrAttrs = Math.min(this.showNumberOfAttributes, nrAttrs);
		}
		
		this.getOwnerNode().setHeight(
			((nrAttrs + 1) * fontHeight) + 8 + nrAttrs * 3);

		double tightWidth = 0;
		String stringLabel = null;
		int stringWidth = 0;

		Iterator iter = this.getAttributes().iterator();
		if (this.isExpanded()) {
			while (iter.hasNext()) {
				stringLabel = (((EntityNodeItem) iter.next()).getLabel());
				if(stringLabel != null){
					stringWidth = graphics.getFontMetrics().stringWidth(stringLabel);
					tightWidth = Math.max(tightWidth, stringWidth);
				}
			}
		}
		else {
			// TODO: still iterate but only over first this.showNumberOfAttributes
			for (int i = 0; iter.hasNext(); i++) {
				if (i >= this.showNumberOfAttributes) {
					break;
				}
				stringLabel = (((EntityNodeItem) iter.next()).getLabel());
				if(stringLabel != null){
					stringWidth = graphics.getFontMetrics().stringWidth(stringLabel);
					tightWidth = Math.max(tightWidth, stringWidth);
				}
			}
		}
		
		//Done twice, so commented once
//		if(this.getLabel() != null ){
//			tightWidth = Math.max(tightWidth, graphics.getFontMetrics().stringWidth(this.getLabel()));	
//		}
		

		if (this.getAction().getLabel() != null) {
			tightWidth = Math.max(tightWidth, graphics.getFontMetrics().stringWidth(this.getAction().getLabel()));
		}

		// we add a little bit to the width because sometimes the text almost gets cut off.
		this.getOwnerNode().setWidth(tightWidth + MARK_SIZE + MARK_GAP * 5);
		this.getOwnerNode().setResizabilityNoResize(TSESolidObject.RESIZABILITY_LOCKED);
	}

	/**
	 * This is an internal method that does the actual rendering of the UI once
	 * the sizes and other objects (such as connectors) are appropriately
	 * created.
	 * @param graphics
	 */
	private void drawGUI(TSEGraphics graphics) {
		TSTransform transform = graphics.getTSTransform();

		if (this.startColor != null && this.endColor != null) {
			// A non-cyclic gradient
			GradientPaint gradient = new GradientPaint(transform.xToDevice(this
					.getOwner().getLocalLeft()), transform.yToDevice(this
					.getOwner().getLocalTop()), this.startColor.getColor(),
					transform.xToDevice(this.getOwner().getLocalRight()),
					transform.yToDevice(this.getOwner().getLocalBottom()),
					this.endColor.getColor());
			graphics.setPaint(gradient);

			// NEW:
			if (this.isRoundRect()) {
				TSESolidObject owner = this.getOwnerNode();
//				TSSize DEFAULT_ARC_TS_SIZE = new TSSize(this.getArcWidth()
//						* owner.getLocalWidth(), this.getArcHeight()
//						* owner.getLocalHeight());
				// TSSize DEFAULT_ARC_TS_SIZE = new TSSize(DEFAULT_ARC_SIZE, DEFAULT_ARC_SIZE);

				graphics.fillRoundRect(
					transform.xToDevice(owner.getLocalLeft()),
					transform.yToDevice(owner.getLocalTop()),
					transform.widthToDevice(owner.getLocalWidth()),
					transform.heightToDevice(owner.getLocalHeight()),
					(int) ARC_SIZE_RECT.getWidth(),
					(int) ARC_SIZE_RECT.getHeight());
			} else {
				graphics.fillRect(transform.xToDevice(this.getOwner()
						.getLocalLeft()), transform.yToDevice(this.getOwner()
						.getLocalTop()), transform.widthToDevice(this
						.getOwner().getLocalWidth()), transform
						.heightToDevice(this.getOwner().getLocalHeight()));
			}
		}

		// set the color of the pen to the text color
		graphics.setColor(this.getTextColor());

		// find the left point of the node
		int left = transform.xToDevice(this.getTextOffsetX() + this.getOwner().getLocalLeft());

		Font scaledFont;

		if (transform.getScaleX() != 1.0) {
			scaledFont = this.getScaledFont(transform);
		} else {
			scaledFont = this.getFont().getFont();
		}

		graphics.setFont(scaledFont);

		// see how much space is necessary for a line of text in this
		// font

		// FontMetrics fm = graphics.getFontMetrics();
		// int fontHeight = fm.getAscent() - fm.getLeading() - fm.getDescent();

		// find the top point from which the text is drawn
		int deviceBarHeight = transform.heightToDevice(this.barHeight);
		int devicePadding = transform.heightToDevice(this.padding);
		int fontHeight = deviceBarHeight - 2 * devicePadding;
		int textY = transform.yToDevice(this.getOwner().getLocalTop()) + devicePadding + fontHeight;

		// before we draw we need to set the clipping or otherwise the
		// text might stick out to the right due to integer step
		// magnification

		Shape oldClip = graphics.getClip();
		TSDeviceRectangle newClip = transform.boundsToDevice(this.getOwner().getLocalBounds());
		graphics.clipRect((int)newClip.getX(), (int)newClip.getY(), (int)newClip.getWidth(), (int)newClip.getHeight());

		// moved to here so we print label on top, not after attributes
//		if(((EntityNodeData) this.getOwnerNode().getUserObject()).getEntity().getLabel()!=null){
		if (((EntityNodeData) this.getOwnerNode().getUserObject()).getEntity().getLabel().length() > 0) {
			int oldLeft = left;
			left += this.drawConceptImage(graphics) + MARK_GAP * 2;
			// left += transform.xToDevice(MARK_SIZE);

			Font font = new Font(scaledFont.getFontName(), Font.BOLD, scaledFont.getSize());
			Font oldFont = graphics.getFont();
			graphics.setFont(font);
			graphics.drawString(((EntityNodeData) this.getOwnerNode().getUserObject()).getEntity().getLabel(), left, textY);
			graphics.setFont(oldFont);
			left = oldLeft;
	//	}
		}
		// OLD: now we draw the attributes
		int devWidth = transform.widthToDevice(
				this.getOwnerNode().getWidth());

		if (fontHeight > this.minAttributeSize) {

			// added here because now we print label on top
			textY += devicePadding;

			Iterator attrIter = ((EntityNodeData) this.getOwnerNode().getUserObject()).getAttributes().iterator();

			EntityNodeItem item;

			left += graphics.getTSTransform().widthToDevice(PROPERTY_IMAGE_SIZE);

			if (this.isExpanded()) {
				while (attrIter.hasNext()) {
					graphics.drawLine(transform.xToDevice(this.getOwner().getLocalLeft()), textY, transform.xToDevice(this.getOwner().getLocalRight()), textY);
					item = (EntityNodeItem) attrIter.next();
					this.drawPropertyImage(graphics, item.getAttributeType(), textY + transform.heightToDevice(MARK_GAP));
					textY += fontHeight;
					graphics.drawString(item.getLabel(), left, textY);
					textY += devicePadding;
				}
			}
			else {
				// draw first this.showMaxNumberOfAttributes
				// NOTE: this code is very similar to the one above, but duplicated
				// here for performance reasons as this code is called many times
				int i = 0;
				while (attrIter.hasNext()) {
					if (i++ >= this.showNumberOfAttributes) {
						break;
					}					
					graphics.drawLine(transform.xToDevice(this.getOwner()
							.getLocalLeft()), textY, transform.xToDevice(this.getOwner().getLocalRight()), textY);
					item = (EntityNodeItem) attrIter.next();
					this.drawPropertyImage(graphics, item.getAttributeType(), textY
							+ transform.heightToDevice(MARK_GAP));
					textY += fontHeight;
					// This means it's the last one we're showing!!!
					if (i == this.showNumberOfAttributes &&
							attrIter.hasNext()) {
						graphics.drawString(item.getLabel() + "...", left, textY);
					}
					else {
						graphics.drawString(item.getLabel(), left, textY);
					}
					textY += devicePadding;
				}
			}
		}


		if (this.startColor == null || this.endColor == null) {
			// we set some default color if none were given to us,
			// although this should not happen
			graphics.setColor(this.getDefaultFillColor());

			int topOfLastBar = textY - devicePadding - fontHeight + 1;

			graphics.fillRect(
				transform.xToDevice(this.getOwner().getLocalLeft()),
				topOfLastBar,
				transform.widthToDevice(this.getOwnerNode().getWidth()),
				transform.yToDevice(this.getOwnerNode().getBottom()) - topOfLastBar);
		}

		graphics.setColor(TSEColor.black);

		// now restore the old clipping
		graphics.setClip(oldClip);
	}

	/**
	 * @param graphics
	 * @param attrType
	 * @param y
	 * @return
	 */
	private int drawPropertyImage(TSEGraphics graphics, int attrType, int y) {
		Image image;
		switch (attrType) {
		case EntityNodeItem.BOOLEAN_ATTRIBUTE:
			if(BOOLEAN_IMAGE.getImage() == null){setTSImage(BOOLEAN_IMAGE);}
			image = BOOLEAN_IMAGE.getImage();
			break;
		case EntityNodeItem.CONCEPTCON_ATTRIBUTE:
			if(CONCEPTCON_IMAGE.getImage() == null){setTSImage(CONCEPTCON_IMAGE);}
			image = CONCEPTCON_IMAGE.getImage();
			break;
		case EntityNodeItem.CONCEPTREF_ATTRIBUTE:
			if(CONCEPTREF_IMAGE.getImage() == null){setTSImage(CONCEPTREF_IMAGE);}
			image = CONCEPTREF_IMAGE.getImage();
			break;
		case EntityNodeItem.DATETIME_ATTRIBUTE:
			if(DATETIME_IMAGE.getImage() == null){setTSImage(DATETIME_IMAGE);}
			image = DATETIME_IMAGE.getImage();
			break;
		case EntityNodeItem.DOUBLE_ATTRIBUTE:
			if(DOUBLE_IMAGE.getImage() == null){setTSImage(DOUBLE_IMAGE);}
			image = DOUBLE_IMAGE.getImage();
			break;
		case EntityNodeItem.INTEGER_ATTRIBUTE:
			if(INTEGER_IMAGE.getImage() == null){setTSImage(INTEGER_IMAGE);}
			image = INTEGER_IMAGE.getImage();
			break;
		case EntityNodeItem.LONG_ATTRIBUTE:
			if(LONG_IMAGE.getImage() == null){setTSImage(LONG_IMAGE);}
			image = LONG_IMAGE.getImage();
			break;
		case EntityNodeItem.STRING_ATTRIBUTE:
			if(STRING_IMAGE.getImage() == null){setTSImage(STRING_IMAGE);}
			image = STRING_IMAGE.getImage();
			break;
		default:
			if(PROPERTY_IMAGE.getImage() == null){setTSImage(PROPERTY_IMAGE);}
			image = PROPERTY_IMAGE.getImage();
			System.out.println("Unknown Property Type");
		}

		int x = graphics.getTSTransform().xToDevice(this.getOwnerNode().getLocalBounds().getLeft() + 1);
		int width = graphics.getTSTransform().widthToDevice(PROPERTY_IMAGE_SIZE);
		int height = graphics.getTSTransform().heightToDevice(PROPERTY_IMAGE_SIZE);
		graphics.drawImage(image, x, y, width, height, this);

		return width;
	}

	/**
	 * @param graphics
	 * @return
	 */
	private int drawConceptImage(TSEGraphics graphics) {
		TSConstRect nodeBounds = this.getOwnerNode().getLocalBounds();
		TSRect localMarkBounds = new TSRect();
		localMarkBounds.setLeft(nodeBounds.getLeft() + MARK_GAP);
		localMarkBounds.setTop(nodeBounds.getTop() - MARK_GAP);
		localMarkBounds.setRight(localMarkBounds.getLeft() + MARK_SIZE);
		localMarkBounds.setBottom(localMarkBounds.getTop() - MARK_SIZE);

		int x = graphics.getTSTransform().xToDevice(localMarkBounds.getLeft() + 1);
		int y = graphics.getTSTransform().yToDevice(localMarkBounds.getTop());
		int width = graphics.getTSTransform().widthToDevice(localMarkBounds.getWidth());
		int height = graphics.getTSTransform().heightToDevice(localMarkBounds.getHeight());
		
		try{
			if(this.iconimage.getImage() == null){setTSImage(this.iconimage);}
			graphics.drawImage(this.iconimage.getImage(), x, y, width, height, this);
		}catch(Exception e){
			e.printStackTrace();
		}
		return width;
	}

	/**
	 * @param tsimage
	 */
	private void setTSImage(TSEImage tsimage){
		Image image = ImageIconsFactory.createIcon(tsimage.toString(), EditorsUIPlugin.class.getClassLoader()).getImage();
		tsimage.setImage(image);
	}
	
	// ---------------------------------------------------------------------
	// Section: Accessors
	// ---------------------------------------------------------------------
	/**
	 * This method returns the tight width of this UI.
	 */
	/*
	 * public double getTightWidth() { return this.width; }
	 */

	/**
	 * This method returns the tight height of this UI.
	 */
	/*
	 * public double getTightHeight() { return this.height; }
	 */

	// ---------------------------------------------------------------------
	// Section: Ownership
	// ---------------------------------------------------------------------
	/**
	 * This method sets the owner of this UI.
	 */
	public void setOwner(TSENode ownerNode) {
		super.setOwner(ownerNode);

		if (ownerNode != null) {
			ownerNode.setLocalSize(this.getTightWidth(), this.getTightHeight());
			ownerNode.setLocalOriginalSize(this.getTightWidth(), this
					.getTightHeight());
		}
	}

	// ---------------------------------------------------------------------
	// Section: Default properties
	// ---------------------------------------------------------------------

	/**
	 * This method returns the default fill color of the UI.
	 */
	public TSEColor getDefaultFillColor() {
		return (new TSEColor(248, 176, 182));

	}

	/**
	 * This method returns the amount by which the text displayed in this node
	 * is horizontally shifted relative to the center of the owner node.
	 */
	public double getTextOffsetX() {
		return (3);
	}

	public void setGradient(TSEColor startColor, TSEColor endColor) {
		this.startColor = startColor;
		this.endColor = endColor;
	}

	public void removeGradient() {
		this.startColor = null;
		this.endColor = null;
	}

	// ---------------------------------------------------------------------
	// Section: TSEInspectable implementation
	// ---------------------------------------------------------------------

	public String getLabel() {
		return getAction().getLabel();
	}

	public List getAttributes() {
		return ((EntityNodeData) this.getOwnerNode().getUserObject())
				.getAttributes();
	}

	public EntityNodeItem getAction() {
		return ((EntityNodeData) this.getOwnerNode().getUserObject()).getEntity();
	}

	public boolean isCollapsed() {
		return ((EntityNodeData) this.getOwnerNode().getUserObject())
				.isCollapsed();
	}

	public boolean isExpanded() {

		return !isCollapsed();
	}

	/**
	 * This method draws the hide mark on this node if there are hidden objects
	 * connected to the owner node UI. However, this method is not called right
	 * now because we do not want to show these marks when we have hidden
	 * objects as we do not want the users to see if there are hidden objects
	 * next to the owner node having this UI.
	 */
	private void drawHideMark(TSEGraphics graphics, TSEColor backgroundColor,
			TSEColor crossColor, TSRect localMarkBounds,
			boolean isCollapseMarker) {
		Shape oldClip = graphics.getClip();

		graphics.clipRect(this.getOwnerNode().getLocalBounds());

		TSTransform transform = graphics.getTSTransform();

		int width = transform.widthToDevice(localMarkBounds.getWidth());
		int height = transform.heightToDevice(localMarkBounds.getHeight());

		int outerX = width / 5;
		int outerY = height / 5;

		int outerAndInnerX = width * 2 / 5;
		int outerAndInnerY = height * 2 / 5;

		int centerX = width - outerAndInnerX * 2;
		int centerY = height - outerAndInnerY * 2;

		int x = transform.xToDevice(localMarkBounds.getLeft());
		int y = transform.yToDevice(localMarkBounds.getTop());

		graphics.setColor(backgroundColor);
		graphics.fillRect(x, y, width, height);

		graphics.setColor(crossColor);
		graphics.fillRect(x + outerX, y + outerAndInnerY, width - 2 * outerX, centerY);

		if (!isCollapseMarker) {
			graphics.fillRect(x + outerAndInnerX, y + outerY, centerX, height - 2 * outerY);
		}
		graphics.setClip(oldClip);
	}

	public boolean imageUpdate(Image image, int flags, int x, int y, int width, int height) {
		return (((flags & ImageObserver.ALLBITS) == 0) || ((flags & ImageObserver.FRAMEBITS) == 0));
	}
}