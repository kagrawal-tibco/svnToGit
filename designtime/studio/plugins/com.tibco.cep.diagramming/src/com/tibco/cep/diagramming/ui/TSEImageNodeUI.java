package com.tibco.cep.diagramming.ui;

//
// Tom Sawyer Software
// Copyright 1992 - 2011
// All rights reserved.
//
// www.tomsawyer.com
//




import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.image.ImageObserver;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEFont;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;
import com.tomsawyer.graphicaldrawing.property.TSEFilenameInspectorProperty;
import com.tomsawyer.graphicaldrawing.property.TSEInspectable;
import com.tomsawyer.graphicaldrawing.property.TSEInspectorProperty;
import com.tomsawyer.graphicaldrawing.property.TSEInspectorPropertyID;
import com.tomsawyer.graphicaldrawing.ui.simple.TSENodeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;
import com.tomsawyer.graphicaldrawing.util.TSEResourceBundleWrapper;
import com.tomsawyer.util.shared.TSProperty;
import com.tomsawyer.util.TSSystem;


/**
 * This class defines a node UI that draws an image.
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class TSEImageNodeUI extends TSENodeUI
	implements ImageObserver
{

// ---------------------------------------------------------------------
// Section: Resetting
// ---------------------------------------------------------------------

	/**
	 * 
	 */
	private static final long serialVersionUID = 3391563140866496713L;


	/**
	 * This method resets the properties of this UI object to the
	 * default values. Overriding classes must call the parent's
	 * implementation of this method. This is called by the
	 * <code>TSEObjectUI</code> constructor and by <code>clone</code>.
	 */
	public void reset()
	{
		// first initialize parent's properties
		super.reset();

		// now initialize additional properties
		this.setImageScaled(this.isImageScaledByDefault());
		this.setFitToImage(this.isFitToImageByDefault());
		this.setFormattingEnabled(false);
		this.setImageInterpolationEnabled(
			this.getDefaultImageInterpolationEnabled());
	}


// ---------------------------------------------------------------------
// Section: Cloning and copying
// ---------------------------------------------------------------------

	/**
	 * This method copies all properties of the specified UI to this UI. The
	 * copying occurs only if both UIs are of the same type. If the UIs are of
	 * different types, a ClassCastException is thrown.
	 * This method does not clone the image object; instead, the image of the
	 * source UI is shared by this UI object.
	 * Overriding classes must call the parent's implementation
	 * of this method.
	 */
	public void copy(TSEObjectUI sourceUI)
	{
		// copy properties of the superclass

		super.copy(sourceUI);

		// still here? Must be the right type. To make things easier
		// convert a generic TSEObjectUI to our specific type

		TSEImageNodeUI sourceNodeUI = (TSEImageNodeUI) sourceUI;

		// copy the object properties
		this.image = sourceNodeUI.image;
		this.defaultText = sourceNodeUI.getDefaultText();

		// next copy simple properties
		this.imageWidth = sourceNodeUI.imageWidth;
		this.imageHeight = sourceNodeUI.imageHeight;
		this.scaledImageWidth = sourceNodeUI.scaledImageWidth;
		this.scaledImageHeight = sourceNodeUI.scaledImageHeight;

		this.setImageScaled(sourceNodeUI.isImageScaled());
		this.setFitToImage(sourceNodeUI.isFitToImage());

		this.setImageInterpolationEnabled(
			sourceNodeUI.isImageInterpolationEnabled());
	}


//----------------------------------------------------------------------
// Section: Drawing
//----------------------------------------------------------------------

	/**
	 * This method is used when the image data could not be loaded to draw a
	 * proxy representation of the image. Since the image area may be
	 * surrounded by a margin, this method must draw the proxy within the
	 * specified rectangle to avoid overdrawing it.
	 * @param graphics The instance of the <code>TSEGraphics</code> class onto
	 * which the proxy is being drawn.
	 * @param left The left coordinate of the proxy in device units.
	 * @param top The top coordinate of the proxy in device units.
	 * @param width The width of the proxy in device units.
	 * @param height The height of the proxy in device units.
	 */
	public void drawProxy(TSEGraphics graphics,
		int left,
		int top,
		int width,
		int height)
	{
		// Load and draw default.gif.

		TSEImage defaultImage = new TSEImage(TSEImageNodeUI.class,
			"images/default.gif");

		if (defaultImage.getImage() != null)
		{
			int defaultImageLeft = left;
			int defaultImageTop = top;
			int defaultImageWidth = defaultImage.getImage().getWidth(null);
			int defaultImageHeight = defaultImage.getImage().getHeight(null);

			// Scale the image.

			double magFactor = Math.min(
				(double) width / (double) defaultImageWidth,
				(double) height / (double) defaultImageHeight);

			defaultImageWidth *= magFactor;
			defaultImageHeight *= magFactor;

			if (defaultImageWidth != width)
			{
				defaultImageLeft += (width - defaultImageWidth) / 2;
			}
			else
			{
				defaultImageTop += (height - defaultImageHeight) / 2;
			}

			// draw a wee black rectangle if the image would be too small.

			if ((defaultImageWidth < 3) && (defaultImageHeight < 3))
			{
				graphics.setColor(Color.black);

				graphics.drawRect(
					defaultImageLeft,
					defaultImageTop,
					defaultImageWidth,
					defaultImageHeight);
			}
			else
			{
				Shape oldClip = graphics.getClip();
				graphics.setClip(defaultImageLeft,
					defaultImageTop,
					defaultImageWidth,
					defaultImageHeight);
				graphics.drawImage(defaultImage.getImage(),
					defaultImageLeft,
					defaultImageTop,
					defaultImageWidth,
					defaultImageHeight,
					null);
				graphics.setClip(oldClip);
			}
		}
	}


	/**
	 * This method draws the object represented by this UI.
	 * @param graphics The instance of the <code>TSEGraphics</code> class onto
	 * which the UI is being drawn.
	 */
	public void draw(TSEGraphics graphics)
	{
		TSTransform transform = graphics.getTSTransform();

		// fill the UI if it is not transparent

		if (!this.isTransparent())
		{
			graphics.setColor(this.getFillColor().getColor());
			graphics.fillRect(this.getOwnerNode().getLocalBounds());
		}

		// first calculate the amount of space available to draw the
		// image

		double maximumWidth = this.getOwnerNode().getLocalWidth() -
			2 * this.getMarginWidth();

		double maximumHeight = this.getOwnerNode().getLocalHeight() -
			2 * this.getMarginHeight() - this.getTextHeight();

		// check if we have any image to draw. If none, draw a proxy

		if ((this.image == null) || (this.image.getImage() == null))
		{
			int nodeDeviceLeft = transform.xToDevice(
				this.getOwnerNode().getLocalLeft());

			int nodeDeviceTop = transform.yToDevice(
				this.getOwnerNode().getLocalTop() - this.getTextHeight());

			this.drawProxy(
				graphics,
				nodeDeviceLeft,
				nodeDeviceTop,
				transform.widthToDevice(maximumWidth),
				transform.heightToDevice(maximumHeight));
		}
		else
		{
			// once we have the scaling factor we can compute the left
			// top corner and the width and height of the image as it is
			// going to appear in the device coordinate

			int devLeft = transform.xToDevice(
				this.getOwnerNode().getLocalCenterX() -
					this.scaledImageWidth / 2.0);
			int devTop = transform.yToDevice(
				this.getOwnerNode().getLocalCenterY() +
				(this.scaledImageHeight - this.getTextHeight()) / 2.0);
			int devWidth = transform.widthToDevice(this.scaledImageWidth);
			int devHeight = transform.heightToDevice(this.scaledImageHeight);

			// draw a small black rectangle if the image would be too small.

			if ((devWidth < 3) && (devHeight < 3))
			{
				graphics.setColor(Color.black);

				graphics.drawRect(
					devLeft,
					devTop,
					devWidth,
					devHeight);
			}
			else
			{
				Shape oldClip = graphics.getClip();

				if (oldClip != null)
				{
					if (oldClip instanceof Rectangle)
					{
						// if the old clip is a rectangle, then the
						// clip can be easily calculated

						Rectangle oldClipRect = (Rectangle) oldClip;

						int devBottom = devTop + devHeight;
						int devRight = devLeft + devWidth;

						int x1 =
							(int) StrictMath.max(
									oldClipRect.getMinX(),
									devLeft);
						int y1 =
							(int) StrictMath.max(
									oldClipRect.getMinY(),
									devTop);
						int x2 =
							(int) StrictMath.min(
									oldClipRect.getMaxX(),
									devRight);
						int y2 =
							(int) StrictMath.min(
									oldClipRect.getMaxY(),
									devBottom);

						graphics.setClip(x1, y1, x2 - x1, y2 - y1);
					}
					else
					{
						// if the old clip is a generic shape, then
						// we have to create some Area objects to
						// help us calculate.

						Rectangle imageBounds = this.getTempRectangle();
						imageBounds.setBounds(
							devLeft,
							devTop,
							devWidth,
							devHeight);

						Area oldClipArea = new Area(oldClip);
						Area newClipArea = new Area(imageBounds);

						newClipArea.intersect(oldClipArea);

						graphics.setClip(newClipArea);
					}
				}
				else
				{
					graphics.setClip(devLeft, devTop, devWidth, devHeight);
				}

				// If image interpolation is enabled, then set the
				// interpolation value to
				// RenderingHints.VALUE_INTERPOLATION_BICUBIC.

				Object oldImageInterpolationHint = graphics.getRenderingHint(
					RenderingHints.KEY_INTERPOLATION);

				if (this.isImageInterpolationEnabled())
				{
					graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
						RenderingHints.VALUE_INTERPOLATION_BICUBIC);
				}

				graphics.drawImage(this.image.getImage(),
					devLeft,
					devTop,
					devWidth,
					devHeight,
					this);

				// Restore the interpolation value.

				if (oldImageInterpolationHint != null)
				{
					graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
						oldImageInterpolationHint);
				}
				else
				{
					// Since the key cannot be set to null, set it to
					// RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR

					graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
						RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
				}

				graphics.setClip(oldClip);
			}
		}

		// draw the border if necessary

		if (this.isBorderDrawn())
		{
			this.drawBorder(graphics,
				this.getOwnerNode().getLocalBounds(),
				this.getBorderColor());
		}
		else if (Math.round(this.getOwnerNode().getLocalOriginalWidth()) !=
				Math.round(this.getOwnerNode().getLocalWidth()) ||
			Math.round(this.getOwnerNode().getLocalOriginalHeight()) !=
				Math.round(this.getOwnerNode().getLocalHeight()))
		{
			graphics.setColor(TSEColor.paleGray);
			graphics.drawRect(this.getOwnerNode().getLocalBounds());
		}

		// draw the text associated with the node
		this.drawText(graphics);

		this.drawHighlight(graphics);
	}


	/**
	 * This method returns the temporary rectangle for this UI object. If it is
	 * <code>null</code>, a new instance is allocated.
	 */
	private Rectangle getTempRectangle()
	{
		if (this.tempRectangle == null)
		{
			this.tempRectangle = new Rectangle();
		}

		return this.tempRectangle;
	}

	
//----------------------------------------------------------------------
// Section: Image scaling
//----------------------------------------------------------------------

	/**
	 * This method sets whether or not the image should be scaled up in size
	 * to fit the size of the owner node. Note that the image will always be
	 * scaled down in size if necessary, regardless of the value of this
	 * property.
	 */
	public void setImageScaled(boolean imageScaled)
	{
		Boolean wasScaled = TSSystem.valueOf(this.imageScaled);
		this.imageScaled = imageScaled;

		this.computeScaledImageSize();

		this.firePropertyChangedEvent(
			TSEImageNodeUI.SCALE,
			wasScaled,
			TSSystem.valueOf(imageScaled));
	}


	/**
	 * This method returns whether or not the image should be scaled up in
	 * size to fit the size of the owner node. Note that the image will always
	 * be scaled down in size if necessary, regardless of the value of this
	 * property.
	 */
	public boolean isImageScaled()
	{
		return (this.imageScaled);
	}


	/**
	 * This method sets whether or not the owner is resized to fit the image
	 * when a new image is set or this UI is attached to a node.
	 */
	public void setFitToImage(boolean fitToImage)
	{
		Boolean wasFitToImage = TSSystem.valueOf(this.fitToImage);
		this.fitToImage = fitToImage;

		this.resizeNodeToImage();

		this.firePropertyChangedEvent(
			TSEImageNodeUI.FIT_TO_IMAGE,
			wasFitToImage,
			TSSystem.valueOf(fitToImage));
	}


	/**
	 * This method returns whether or not the owner is resized
	 * to fit the image when a new image is set or this UI is attached to a
	 * node.
	 */
	public boolean isFitToImage()
	{
		return this.fitToImage;
	}


	/**
	 * This method returns whether or not image interpolation is enabled.
	 */
	public boolean isImageInterpolationEnabled()
	{
		return this.imageInterpolationEnabled;
	}

	/**
	 * This method sets whether or not image interpolation is enabled.
	 */
	public void setImageInterpolationEnabled(boolean imageInterpolationEnabled)
	{
		Boolean oldImageInterpolationEnabled =
			TSSystem.valueOf(this.imageInterpolationEnabled);
		this.imageInterpolationEnabled = imageInterpolationEnabled;
		this.firePropertyChangedEvent(
			TSEImageNodeUI.IMAGE_INTERPOLATION,
			oldImageInterpolationEnabled,
			TSSystem.valueOf(imageInterpolationEnabled));
	}

	
//----------------------------------------------------------------------
// Section: Accessing image
//----------------------------------------------------------------------

	/**
	 * This method sets the image displayed by this UI.
	 */
	public void setImage(TSEImage image)
	{
		TSEImage oldImage = this.image;

		if ((image != null) && (image.getImage() != null))
		{
			int width = image.getImage().getWidth(this);
			int height = image.getImage().getHeight(this);

			if (width != -1 && height != -1)
			{
				this.image = image;
			}
		}

		this.computeUnscaledImageSize();

		if (!this.isFitToImage())
		{
			this.computeScaledImageSize();
		}

		// the owner will need to resize if it's tight width or height.

		this.resizeNodeToImage();

		this.firePropertyChangedEvent(
			TSEImageNodeUI.IMAGE,
			oldImage,
			image);
	}


	/**
	 * This method resizes the node to fit the image.
	 */
	private void resizeNodeToImage()
	{
		TSENode ownerNode = this.getOwnerNode();

		if (ownerNode != null &&
			(this.isFitToImage() ||
			(ownerNode.getResizability() &
				TSESolidObject.RESIZABILITY_TIGHT_WIDTH) != 0 ||
			(ownerNode.getResizability() &
				TSESolidObject.RESIZABILITY_TIGHT_HEIGHT) != 0))
		{
			ownerNode.setLocalAdjustedSize(
				this.getTightWidth(),
				this.getTightHeight());

			ownerNode.setLocalAdjustedOriginalSize(
				ownerNode.getLocalWidth(),
				ownerNode.getLocalHeight());
		}
	}


	/**
	 * This method computes the size of the image in its original form.
	 * If the image is missing, it uses the size of the default image.
	 */
	private void computeUnscaledImageSize()
	{
		if ((this.image != null) && (this.image.getImage() != null))
		{
			int width = this.image.getImage().getWidth(this);
			int height = this.image.getImage().getHeight(this);

			if (width != -1 && height != -1)
			{
				this.imageWidth = width;
				this.imageHeight = height;
			}
		}
		else
		{
			TSEImage defaultImage = new TSEImage(TSEImageNodeUI.class,
				"images/default.gif");

			int width = defaultImage.getImage().getWidth(this);
			int height = defaultImage.getImage().getHeight(this);

			if (defaultImage.getImage() != null)
			{
				this.imageWidth = width;
				this.imageHeight = height;
			}
		}

		// Set default sizes for the scaled variables.
		this.scaledImageWidth = this.imageWidth;
		this.scaledImageHeight = this.imageHeight;
	}


	/**
	 * This method computes the size of the image as it is drawn.
	 * If the imageScaled property is true, then the image is scaled to
	 * the node bounds.
	 */
	private void computeScaledImageSize()
	{
		TSENode ownerNode = this.getOwnerNode();

		if (ownerNode != null)
		{
			// take the unscaled width and height of the image
			double worldWidth = this.imageWidth;
			double worldHeight = this.imageHeight;

			// determine the maximum size for the image

			double maximumWidth = ownerNode.getLocalWidth() -
				2 * this.getMarginWidth();

			double maximumHeight = ownerNode.getLocalHeight() - 2 *
				this.getMarginHeight() - this.getTextHeight();

			// if images are to be scaled, or the image is too large,
			// then update the scaling

			if (this.isImageScaled() ||
				maximumWidth < worldWidth ||
				maximumHeight < worldHeight)
			{
				double magFactor = Math.min(
					maximumWidth / this.imageWidth,
					maximumHeight / this.imageHeight);

				// update the the world image width and height

				worldWidth *= magFactor;
				worldHeight *= magFactor;
			}

			this.scaledImageWidth = worldWidth;
			this.scaledImageHeight = worldHeight;
		}
	}


	/**
	 * This method returns the image displayed by this UI.
	 */
	public TSEImage getImage()
	{
		return this.image;
	}


	/**
	 * This method updates marks when the owner is resized.
	 */
	public void onOwnerResized()
	{
		this.computeScaledImageSize();
	}
	

// ---------------------------------------------------------------------
// Section: Image observer
// ---------------------------------------------------------------------

	/**
	 * This method manages the redrawing of the component when the image has 
	 * changed. The <code>ImageObserver</code> method is called when image 
	 * information that has been requested by using an asynchronous routine, 
	 * such as the <code>drawImage</code> method of the <code>Graphics</code> 
	 * class, becomes available. This method should return <code>true</code>
	 * if further updates are needed or <code>false</code> if the required
	 * information has been acquired.
	 */
	public boolean imageUpdate(Image image,
		int flags,
		int x,
		int y,
		int width,
		int height)
	{
		return (((flags & ImageObserver.ALLBITS) == 0) ||
			((flags & ImageObserver.FRAMEBITS) == 0));
	}


// ---------------------------------------------------------------------
// Section: Ownership
// ---------------------------------------------------------------------

	/**
	 * This method sets the owner of this UI.
	 */
	public void setOwner(TSENode ownerNode)
	{
		super.setOwner(ownerNode);

		this.computeUnscaledImageSize();

		if (!this.isFitToImage())
		{
			this.computeScaledImageSize();
		}

		this.resizeNodeToImage();
	}


//----------------------------------------------------------------------
// Section: Resizability
//----------------------------------------------------------------------

	/**
	 * This method returns the tight width of this UI.
	 */
	public double getTightWidth()
	{
		this.updateTextWidthAndHeight(this.getOwner().getText());

		return Math.max(
			this.scaledImageWidth,
			this.getTextWidth()) + 2 * this.getMarginWidth();
	}


	/**
	 * This method returns the tight height of this UI.
	 */
	public double getTightHeight()
	{
		this.updateTextWidthAndHeight(this.getOwner().getText());

		return this.getTextHeight() + 2 * this.getMarginHeight() +
			this.scaledImageHeight;
	}


	/**
	 * This method returns the width of the margin around this UI.
	 * @return 0
	 */
	public double getMarginWidth()
	{
		return 0;
	}


	/**
	 * This method returns the height of the margin around this UI.
	 * @return 0
	 */
	public double getMarginHeight()
	{
		return 0;
	}


	/**
	 * This method sets the font of the text.
	 */
	public void setFont(TSEFont font)
	{
		super.setFont(font);

		if (this.getOwnerNode() != null)
		{
			this.onTextChanged(this.getOwnerNode().getText());
		}
	}


//----------------------------------------------------------------------
// Section: Text and font handling
//----------------------------------------------------------------------

	/**
	 * This method returns the horizontal offset of the text from the
	 * center of the owner node. For image nodes, the text is always
	 * centered.
	 */
	public double getTextOffsetX()
	{
		return 0;
	}


	/**
	 * This method returns the vertical offset of the text from the
	 * center of the owner node. For image nodes, the text is placed
	 * just a little lower than the top of the owner.
	 */
	public double getTextOffsetY()
	{
		return (this.getOwnerNode().getLocalHeight() / 2.0 -
			this.getTextHeight() / 2.0 - 1);
	}


//----------------------------------------------------------------------
// Section: Properties
//----------------------------------------------------------------------

	/**
	 * This method returns a list of all properties that are associated with
	 * this object. Overriding classes must call the parent's implementation 
	 * of this method.
	 */
	public List<TSProperty> getProperties()
	{
		List<TSProperty> list = super.getProperties();

		list.add(new TSProperty(
			TSEImageNodeUI.IMAGE,
			this.getImage()));
		list.add(new TSProperty(TSEImageNodeUI.SCALE,
			TSSystem.valueOf(this.isImageScaled())));
		list.add(new TSProperty(TSEImageNodeUI.FIT_TO_IMAGE,
			TSSystem.valueOf(this.isFitToImage())));
		list.add(new TSProperty(TSEImageNodeUI.IMAGE_INTERPOLATION,
			TSSystem.valueOf(this.isImageInterpolationEnabled())));

		return list;
	}


	/**
	 * This method returns a list of all properties that are not set to their
	 * default values. Overriding classes must call the parent's
	 * implementation of this method.
	 */
	public List<TSProperty> getChangedProperties()
	{
		List<TSProperty> list = super.getChangedProperties();

		if (this.image != null)
		{
			if (this.getImage().getResource() != null)
			{
				list.add(new TSProperty(
					TSEImageNodeUI.IMAGE,
					TSEImage.checkPath(
						this.getImage().getResource())));
			}
			else if (this.getImage().getURL() != null)
			{
				list.add(new TSProperty(
					TSEImageNodeUI.IMAGE_URL,
					this.getImage().getURL()));
			}
		}

		if (this.isImageScaled() != this.isImageScaledByDefault())
		{
			list.add(new TSProperty(TSEImageNodeUI.SCALE,
				TSSystem.valueOf(this.isImageScaled())));
		}

		if (this.isFitToImage() != this.isFitToImageByDefault())
		{
			list.add(new TSProperty(TSEImageNodeUI.FIT_TO_IMAGE,
				TSSystem.valueOf(this.isFitToImage())));
		}

		if (this.getDefaultImageInterpolationEnabled() !=
			this.isImageInterpolationEnabled())
		{
			list.add(new TSProperty(TSEImageNodeUI.IMAGE_INTERPOLATION,
					TSSystem.valueOf(this.isImageInterpolationEnabled())));
		}

		return list;
	}


	/**
	 * This method sets the specified property of this UI. Overriding classes
	 * that do not process the specified property must call the parent's 
	 * implementation of this method.
	 */
	public void setProperty(TSProperty property)
	{
		String attrString = null;

		if (property.getValue() != null)
		{
			attrString = property.getValue().toString();
		}

		if (TSEImageNodeUI.IMAGE.equals(property.getName()) ||
			"image".equals(property.getName()))
		{
			if (property.getValue() == null ||
				property.getValue() instanceof TSEImage)
			{
				this.setImage((TSEImage) property.getValue());
			}
			else
			{
				TSEImage image = TSEImage.getFromString(attrString);

				if (image != null)
				{
					this.setImage(image);
				}
				else
				{
					this.setImage(new TSEImage(attrString));
				}
			}
		}
		else if (TSEImageNodeUI.IMAGE_URL.equals(property.getName()))
		{
			try
			{
				this.setImage(new TSEImage(new URL(attrString)));
			}
			catch (MalformedURLException e)
			{
				TSSystem.println(this.getClass(),
					"Bad URL for object " + this.getOwnerNode(),
					TSSystem.DEBUG_WARNING);
				TSSystem.printException(this.getClass(), e, TSSystem.DEBUG_WARNING);
			}
		}
		else if (TSEImageNodeUI.TEXT.equals(property.getName()))
		{
			this.defaultText = attrString;
		}
		else if (TSEImageNodeUI.SCALE.equals(property.getName()))
		{
			if (property.getValue() instanceof Boolean)
			{
				this.setImageScaled(
					((Boolean) property.getValue()).booleanValue());
			}
			else
			{
				this.setImageScaled(
					Boolean.valueOf(attrString).booleanValue());
			}
		}
		else if (TSEImageNodeUI.FIT_TO_IMAGE.equals(property.getName()))
		{
			if (property.getValue() instanceof Boolean)
			{
				this.setFitToImage(
					((Boolean) property.getValue()).booleanValue());
			}
			else
			{
				this.setFitToImage(
					Boolean.valueOf(attrString).booleanValue());
			}
		}
		else if (TSEImageNodeUI.IMAGE_INTERPOLATION.equals(property.getName()))
		{
			if (property.getValue() instanceof Boolean)
			{
				this.setImageInterpolationEnabled(
					((Boolean) property.getValue()).booleanValue());
			}
			else
			{
				this.setImageInterpolationEnabled(
					Boolean.valueOf(attrString).booleanValue());
			}
		}
		else
		{
			super.setProperty(property);
		}
	}


//----------------------------------------------------------------------
// Section: Default properties
//----------------------------------------------------------------------

	/**
	 * This method returns the default value of the transparent property.
	 * @return true
	 */
	public boolean isTransparentByDefault()
	{
		return true;
	}


	/**
	 * This method returns the text of the UI to be displayed if the
	 * owner's text is <code>null</code>.
	 */
	public String getDefaultText()
	{
		return this.defaultText;
	}


	/**
	 * This method returns whether the image is scaled up in size
	 * to fit the size of the owner node by default.
	 * @return true
	 */
	public boolean isImageScaledByDefault()
	{
		return true;
	}


	/**
	 * This method returns whether the owner is resized to fit the image (when
	 * a new image is set or this UI is attached to a node) by default.
	 * @return true
	 */
	public boolean isFitToImageByDefault()
	{
		return true;
	}


	/**
	 * This method returns whether or not the border of the node should
	 * be drawn by default.
	 * @return false
	 */
	public boolean isBorderDrawnByDefault()
	{
		return false;
	}


	/**
	 * This method returns the default background color.
	 * @return <code>TSEColor.white</code>
	 */
	public TSEColor getDefaultFillColor()
	{
		return TSEColor.white;
	}


	/**
	 * This method returns whether or not image interpolation is enabled
	 * by default.
	 * @return <code>false</code>
	 */
	public boolean getDefaultImageInterpolationEnabled()
	{
		return false;
	}


// ---------------------------------------------------------------------
// Section: TSEInspectable implementation
// ---------------------------------------------------------------------

	/**
	 * This property makes the image name of the UI available to the
	 * inspector.
	 */
	public static TSEInspectorPropertyID PICTURE_ID =
		new TSEInspectorPropertyID(
			TSEResourceBundleWrapper.getSystemLabelBundle().
				getStringSafely("Picture"),
			String.class);


	/**
	 * This property makes the image interpolation property available to the
	 * inspector.
	 */
	public static TSEInspectorPropertyID IMAGE_INTERPOLATION_ID =
		new TSEInspectorPropertyID(
			TSEResourceBundleWrapper.getSystemLabelBundle().getStringSafely(
				"Image_Interpolation"),
			Boolean.class);


	/**
	 * This constant represents the key for the internationalized string that 
	 * will appear in the inspector window to represent the GIF file filter 
	 * string.
	 */
	public static final String GIF_FILTER_STRING =
		TSEResourceBundleWrapper.getSystemLabelBundle().
			getStringSafely("GIF_Image_(*.gif)");


	/**
	 * This constant represents the key for the internationalized string that 
	 * will appear in the inspector window to represent the JPEG file filter 
	 * string.
	 */
	public static final String JPEG_FILTER_STRING =
		TSEResourceBundleWrapper.getSystemLabelBundle().
			getStringSafely("JPEG_Image_(*.jpg)");


	/**
	 * This constant represents the key for the internationalized string that
	 * will appear in the inspector window to represent the PNG file filter
	 * string.
	 */
	public static final String PNG_FILTER_STRING =
		TSEResourceBundleWrapper.getSystemLabelBundle().
			getStringSafely("PNG_Image_(*.png)");


	/**
	 * This method adds inspector property IDs to the specified list.
	 * Overriding classes must call the parent's implementation of
	 * this method but they are free to remove IDs from the list as necessary.
	 * This adds PICTURE_ID, IMAGE_INTERPOLATION_ID and calls the parent's
	 * version of this method.
	 * @see com.tomsawyer.graphicaldrawing.property.TSEInspectable#getInspectorPropertyIDs
	 */
	public void getInspectorPropertyIDs(List idList)
	{
		super.getInspectorPropertyIDs(idList);
		idList.add(PICTURE_ID);
		idList.add(IMAGE_INTERPOLATION_ID);
	}


	/**
	 * This method returns an instance of the
	 * <code>TSEInspectorProperty</code> class with the specified ID.
	 * Overriding classes that do not process the specified ID must
	 * call the parent's implementation of this method.
	 * @see com.tomsawyer.graphicaldrawing.property.TSEInspectable#getInspectorProperty
	 */
	public TSEInspectorProperty getInspectorProperty(
		TSEInspectorPropertyID id)
	{
		if (id.equals(PICTURE_ID))
		{
			String imageName = "";

			if (this.image != null)
			{
				if (this.image.getResource() != null)
				{
					imageName = this.image.getResource();
				}
				else if (this.image.getURL() != null)
				{
					imageName = this.image.getURL().toString();
				}
			}

			TSEFilenameInspectorProperty property =
				new TSEFilenameInspectorProperty(imageName,
					!imageName.startsWith("http"));

			property.addFilter(
				new TSProperty("gif", GIF_FILTER_STRING));

			property.addFilter(
				new TSProperty("jpg", JPEG_FILTER_STRING));

			property.addFilter(
				new TSProperty("png", PNG_FILTER_STRING));

			return property;
		}
		else if (id.equals(IMAGE_INTERPOLATION_ID))
		{
			return new TSEInspectorProperty(
					TSSystem.valueOf(this.isImageInterpolationEnabled()));
		}
		else
		{
			return super.getInspectorProperty(id);
		}
	}


	/**
	 * This method is used by the inspector to change a property.
	 * Overriding classes that do not set the specified property should
	 * call the parent's implementation of this method.
	 * @see com.tomsawyer.graphicaldrawing.property.TSEInspectable#setInspectorProperty
	 */
	public int setInspectorProperty(
		TSEInspectorPropertyID id,
		TSEInspectorProperty property)
	{
		int rc;

		if (id.equals(PICTURE_ID))
		{
			if (property.getValue() instanceof TSEImage)
			{
				this.setImage((TSEImage) property.getValue());
			}
			else
			{
				this.setImage(new TSEImage(
					(String) property.getValue()));
			}

			rc = TSEInspectable.PROPERTY_SET;
		}
		else if (id.equals(IMAGE_INTERPOLATION_ID))
		{
			this.setImageInterpolationEnabled(
				((Boolean) property.getValue()).booleanValue());

			rc = TSEInspectable.PROPERTY_SET;
		}
		else
		{
			rc = super.setInspectorProperty(id, property);
		}

		return rc;
	}


//----------------------------------------------------------------------
// Section: Class variables
//----------------------------------------------------------------------

	/**
	 * This constant is the name for the image property that is managed by
	 * this class.
	 */
	public static final String IMAGE = "imageUsed";


	/**
	 * This constant is the name for the image URL property that is managed by
	 * this class.
	 */
	public static final String IMAGE_URL = "imageURL";


	/**
	 * This constant is the name for the text property that is managed by
	 * this class.
	 */
	public static final String TEXT = "text";


	/**
	 * This constant is the name for the scaling property that is managed by
	 * this class.
	 */
	public static final String SCALE = "scale";


	/**
	 * This constant is the name for the fit-to-image property that is managed 
	 * by this class.
	 */
	public static final String FIT_TO_IMAGE = "fit";


	/**
	 * This constant is the name for the image interpolation property
	 * managed by this class.
	 */
	public static final String IMAGE_INTERPOLATION = "imageInterpolation";


//----------------------------------------------------------------------
// Section: Instance variables
//----------------------------------------------------------------------

	/**
	 * This variable stores the image of this node UI.
	 */
	TSEImage image;

	/**
	 * This variable stores the width of the image in world coordinates.
	 */
	double imageWidth;

	/**
	 * This variable stores the height of the image in world coordinates.
	 */
	double imageHeight;

	/**
	 * This variable stores the default text for unnamed image nodes.
	 */
	String defaultText;

	/**
	 * This variable stores whether or not the images should be scaled up in
	 * size to fit the size of the owner node. Note that the image will always
	 * be scaled down in size if necessary, regardless of the value of this
	 * property.
	 */
	boolean imageScaled;

	/**
	 * This variable stores whether or not the owner should be resized to fit
	 * the image when a new image is set or this UI is attached to a node.
	 */
	boolean fitToImage;

	/**
	 * This variable stores the interpolation attribute of the image.
	 */
	boolean imageInterpolationEnabled;

	/**
	 * This variable stores the current width of the image, including the
	 * the width of the node.
	 */
	double scaledImageWidth;

	/**
	 * This variable stores the current height of the image, including
	 * the height of the node.
	 */
	double scaledImageHeight;

	/**
	 * This variable stores a rectangle that is used during some drawing
	 * calculations.
	 */
	transient Rectangle tempRectangle;
}
