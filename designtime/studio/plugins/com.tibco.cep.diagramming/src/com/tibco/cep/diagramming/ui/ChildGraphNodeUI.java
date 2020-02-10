package com.tibco.cep.diagramming.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.RoundRectangle2D;

import com.tomsawyer.drawing.TSDGraph;
import com.tomsawyer.drawing.TSGraphTailor;
import com.tomsawyer.drawing.geometry.shared.TSConstRect;
import com.tomsawyer.drawing.geometry.shared.TSRect;
import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.drawing.geometry.shared.TSDeviceRectangle;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEChildGraphUI;

/**
 * author: ggrigore
 * 
 * This class defines the node UI that is responsible for displaying child graphs.
 */
public class ChildGraphNodeUI extends TSEChildGraphUI  {

    protected static final long serialVersionUID = 1L;
    
    public static boolean displayCompleteName = false;
    protected boolean localDisplayCompleteName; 
    
    protected static final int ARC_SIZE = 8;
    protected static int MINIMIZE_MARK_WIDTH = 15;
    protected static Color FROM_COLOR = new Color(255,128,128);
    protected static Color TO_COLOR = new Color(196, 128, 128);
    protected static final Color GLOW_OUTER_HIGH_COLOR = new Color(255, 153, 0);
	protected static final Color GLOW_OUTER_LOW_COLOR = new Color(255, 204, 100);
	protected static final Color MARK_COLOR = new Color(170, 0, 55);	
	
    public static int MARK_WIDTH = 9;
    protected static int MARK_HEIGHT = 2;
    public static int MARK_OFFSET = 3;	
	
	private Color startColor;
	private Color endColor;
	private Color markColor;
	
    protected boolean isOuterRoundRect;
    protected boolean isInnerRoundRect;
    protected TSRect childMarkBounds;
    protected TSConstRect badgeBounds;
    
    // hack (for now till TS gives us something
    public boolean isFirst = true;
    protected boolean isLane = false;

    
    public ChildGraphNodeUI() {
         this.setDrawChildGraphMark(true);
         this.startColor = GLOW_OUTER_HIGH_COLOR;
         this.endColor = GLOW_OUTER_LOW_COLOR;
         this.markColor = MARK_COLOR;
         this.localDisplayCompleteName = displayCompleteName;
    }
    
    /**
     * This method resets the properties of this UI object to their default
     * values. Overriding classes must call the superclass' implementation of
     * this method. This is called by the <code>TSEObjectUI</code> constructor
     * and by <code>clone
     * </code>.
     */
    public void reset() {
        super.reset();
        this.setOuterRoundRect(true);
        this.setBorderDrawn(true);
        this.setFillColor(new TSEColor(191, 167, 232));
    }
    
    public boolean isShortenName() {
    	return this.localDisplayCompleteName;
    }
    
    public void setShortenName(boolean shorten) {
    	this.localDisplayCompleteName = shorten;
    }
    
	/**
	 * This method gets the formated text. If text formatting is set to true,
	 * the owner's text is returned with new lines inserted to wrap the text.
	 * This method returns the text without formatting if the owner's
	 * resizability is set to use <code>RESIZABILITY_TIGHT_WIDTH</code>
	 * or <code>RESIZABILITY_TIGHT_HEIGHT</code> and the owner is not
	 * an expanded node. If we don't want to display the entire text, then we 
	 * end it with "..."
	 */
	@SuppressWarnings("deprecation")
	protected String getFormattedText() {
		if (this.getOwner() == null) {
			return (this.getDefaultText());
		}

		if (this.getOwner().getText() == null) {
			if (this.getOwner() instanceof TSENode) {
				return null;
			}
			else {
				return (this.getDefaultText());
			}
		}

		if (!this.isFormattingEnabled()) {
			this.formattedText = this.getOwner().getText();
			return (this.formattedText);
		}

		int resizability = ((TSESolidObject) this.getOwner()).getResizability();

		if (((resizability & TSESolidObject.RESIZABILITY_TIGHT_WIDTH) != 0
			|| (resizability & TSESolidObject.RESIZABILITY_TIGHT_HEIGHT) != 0)
			&& !(this.getOwner() instanceof TSENode
				&& ((TSENode) this.getOwner()).isExpanded())) {
			this.formattedText = this.getOwner().getText();

			return (this.formattedText);
		}

		// we account for the collapse marker here.
		double width = this.getWidth() - 15;
		String text = this.getOwner().getText();

		FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(this.getFont().getFont());

		StringBuffer buffer = new StringBuffer();
		int start = 0;
		int wordBreak = -1;
		boolean foundEnd = false;
		
		for (int i = 1; i < text.length(); i++) {
			if (text.charAt(i) == '\n') {
				buffer.append(text.substring(start, i + 1));
				start = i + 1;
				wordBreak = -1;
			}
			else {
				if (Character.isWhitespace(text.charAt(i))) {
					wordBreak = i;
				}

				if (fm.stringWidth(text.substring(start, i + 1)) > width) {
					if (wordBreak != -1) {
						buffer.append(text.substring(start, wordBreak));
						start = wordBreak + 1;
						wordBreak = -1;
					}
					else {
						buffer.append(text.substring(start, i));
						start = i;
					}

					if (!this.localDisplayCompleteName) {
						buffer.append("...");
						foundEnd = true;
						break;
					}
					else {
						buffer.append('\n');
					}
				}
			}
		}

		if (text.substring(start).length() > 0 && !foundEnd) {
			buffer.append(text.substring(start));
		}

		this.formattedText = buffer.toString();

		return this.formattedText;
	}
    // ---------------------------------------------------------------------
    // Section: drawing
    // ---------------------------------------------------------------------

    /**
     * This method draws the outline of the object represented by this UI. It is
     * faster than <code>draw</code> and can be used when drawing many objects
     * during processor- intensive interactive operations such as dragging.
     *
     * @param graphics
     *            the <code>TSEGraphics</code> object onto which the UI is being
     *            drawn.
     */
    public void drawOutline(TSEGraphics graphics) {
        TSEGraph childGraph = (TSEGraph) this.getOwnerNode().getChildGraph();
        if (childGraph != null) {
            TSTransform transformFromChild = TSTransform.compose(
            	graphics.getTSTransform(), childGraph.getClonedTransform());
            TSEGraphics childGraphics = graphics.deriveGraphics(transformFromChild);
            ((ChildGraphNodeUI) childGraph.getUI()).drawOutline(childGraphics);

            // get frame bounding rectangle of child graph.
            TSRect frameBounds = (TSRect) childGraph.getLocalFrameBounds();
            TSDeviceRectangle frameDeviceBounds = transformFromChild.boundsToDevice(frameBounds);

            // draw rectangle around transformed frame bounding rectangle.
            graphics.drawRect((int)frameDeviceBounds.getX() - 1, (int)frameDeviceBounds.getY() - 1,
            		(int)frameDeviceBounds.getWidth() + 1, (int)frameDeviceBounds.getHeight() + 1);
        }
    }

    public void draw(TSEGraphics graphics, boolean respectSelection, boolean selectedOnly) {
        TSENode owner = this.getOwnerNode();
        if (owner == null)
        	return;

        TSEGraph childGraph = (TSEGraph) owner.getChildGraph();
        if (childGraph == null) {
        	return;
        }
        
        TSConstRect ownerBounds = owner.getLocalBounds();
        TSTransform transform = graphics.getTSTransform();
        double x =  transform.xToDevice(ownerBounds.getLeft());
        double y = transform.yToDevice(ownerBounds.getTop());
        double w = transform.widthToDevice(ownerBounds.getWidth());
        double h = transform.heightToDevice(ownerBounds.getHeight());
        
        if (isLane) {
        	if (isFirst) {
        		//isFirst = false;
        		//System.out.println("resetting...");
        	}
        	else {
        		TSEGraph poolGraph = (TSEGraph) owner.getOwnerGraph();
        		TSENode poolNode = (TSENode) poolGraph.getParent();
        		double width = transform.widthToDevice(poolNode.getLocalWidth());
        		graphics.setColor(GLOW_OUTER_HIGH_COLOR);
        		graphics.drawLine((int)x, (int)y, (int)(x+width), (int)y);
        	}
        }
        else {
            y = transform.yToDevice(ownerBounds.getTop());
            Shape roundRect = new RoundRectangle2D.Double(x,y,w,h,ARC_SIZE, ARC_SIZE);
            GradientPaint gradient = new GradientPaint(
					transform.xToDevice(this.getOwner().getLocalLeft()),
					transform.yToDevice(this.getOwner().getLocalTop()),
					this.startColor,
					transform.xToDevice(this.getOwner().getLocalRight()),
					transform.yToDevice(this.getOwner().getLocalBottom()),
					this.endColor);	
            graphics.setPaint(gradient);
	        graphics.fill(roundRect);		
            graphics.setColor(this.startColor);
            graphics.draw(roundRect);
            //graphics.drawRoundRect(ownerBounds, DEFAULT_ARC_TS_SIZE);
        }
        
        // draw the "minimize" mark:
        this.drawChildGraphMark(graphics);
        this.drawOtherBadges(graphics);
        this.drawHighlight(graphics);
        this.drawText(graphics);
    }
 
    public void draw(TSEGraphics graphics) {
        this.draw(graphics, true, false);
    }

    public void drawSelected(TSEGraphics graphics, boolean respectSelection, boolean selectedOnly) {
        this.draw(graphics, respectSelection, selectedOnly);
        graphics.setColor(this.getSelectedColor());
        this.drawGrapples(graphics);
    }
    
    // To be extended (if needed) by specialized subclasses
    protected void drawOtherBadges(TSEGraphics graphics) {    }
    
    public void setStartColor(Color startColor) {
    	this.startColor = startColor;
    }
    
    public Color getStartColor() {
    	return this.startColor;
    }
    
    public void setEndColor(Color endColor) {
    	this.endColor = endColor;
    }
    
    public Color getEndColor() {
    	return this.endColor;
    }
    
    public void setMarkColor(Color markColor) {
    	this.markColor = markColor;
    }
    
    public Color getMarkColor() {
    	return this.markColor;
    }

    // NEW:
    public boolean isOuterRoundRect() {
        return isOuterRoundRect;
    }

    public void setOuterRoundRect(boolean isOuterRoundRect) {
        this.isOuterRoundRect = isOuterRoundRect;
    }

    public boolean isInnerRoundRect() {
        return isInnerRoundRect;
    }

    public void setInnerRoundRect(boolean isInnerRoundRect) {
        this.isInnerRoundRect = isInnerRoundRect;
    }

	@SuppressWarnings("deprecation")
	public void drawText(TSEGraphics graphics) {
		TSTransform transform = graphics.getTSTransform();
		// TODO: hacking for now to handle null text strings
		if (this.formattedText.length() == 0) {
			this.getOwnerNode().setName(" ");
		}

		// Gabe's note: took out Suresh's hack and instead read from formatted text
		// String nodeText = ((StateVertex)this.getOwnerNode().getUserObject()).getName();
		String nodeText = this.formattedText;

		if (nodeText != null) {
			TSConstRect ownerBounds = this.getOwner().getLocalBounds();
			Font scaledFont = this.getScaledFont(transform);
			graphics.setFont(scaledFont);
			FontMetrics fm = graphics.getFontMetrics();

			// there's a bug in Swing where the ascent is reported too
			// high. More accurate ascent can be approximated (heh) by
			// subtracting the leading and descent from it.
			int ascent = fm.getAscent() - fm.getLeading() - fm.getDescent();
			int deviceTextHeight = ascent + fm.getHeight() * (this.getNumberOfLines() - 1);

			// when calculating the width of the scaled text we take a
			// slight correction to compensate for the fact that the
			// font metrics rounds the text width up.
            int top =  transform.yToDevice(ownerBounds.getTop()) + fm.getAscent() + 0;
            int left = transform.xToDevice(ownerBounds.getLeft())+ 6;
            graphics.setColor(this.getTextColor().getColor());

			// before we draw we need to set the clipping or otherwise
			// the text might stick out to the right due to integer step magnification
			Shape oldClip = graphics.getClip();
			TSDeviceRectangle newClip = transform.boundsToDevice(ownerBounds);
			
			graphics.clipRect((int)newClip.getX(), (int)newClip.getY(), (int)newClip.getWidth(), (int)newClip.getHeight());
			// we also turn on anti-aliasing
			Object oldAntiAliasingHint = null;

			if (this.isTextAntiAliasingEnabled() &&
				deviceTextHeight <= this.getTextAntiAliasingThreshold()) {
				oldAntiAliasingHint = graphics.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			}

			// Parse text to find newline characters and draw text respectively.
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
					// END CHANGE
				}
				lineStart = lineEnd + 1;
                top += fm.getHeight() + 2;
            }

            top =  transform.yToDevice(ownerBounds.getTop()) ;
            left = transform.xToDevice(ownerBounds.getRight()-20) ;

            // For debugging purposes only:
            /*
            Color oldC = graphics.getColor();
            graphics.setColor(new TSEColor(255,0,0));
            graphics.drawRect(this.getLocalChildGraphMarkBounds());
            graphics.setColor(oldC);
            */

            if (this.isTextAntiAliasingEnabled() &&
            	deviceTextHeight <= this.getTextAntiAliasingThreshold()) {
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,oldAntiAliasingHint);
			}

			// now restore the old clipping
			graphics.setClip(oldClip);
			
//			if (emptyTag) {
//				this.getOwnerNode().setName("");
//				emptyTag = false;
//			}
		}
	}
	
	public void drawChildGraphMark(TSEGraphics graphics) {
		Color oldColor = graphics.getColor();		
		TSConstRect ownerBounds = this.getOwner().getLocalBounds();		
        graphics.setColor(this.markColor);
        TSConstRect entireMarkBounds = new TSConstRect(
            	ownerBounds.getRight() - MARK_OFFSET - MARK_WIDTH,
            	ownerBounds.getTop() - MARK_OFFSET,
            	ownerBounds.getRight() - MARK_OFFSET,
            	ownerBounds.getTop() - MARK_OFFSET - MARK_WIDTH);
        graphics.drawRect(entireMarkBounds);

        TSConstRect bottomRectangleBounds = new TSConstRect(
        	ownerBounds.getRight() - MARK_OFFSET - MARK_WIDTH,
        	ownerBounds.getTop() - MARK_OFFSET - MARK_WIDTH*2/3,
        	ownerBounds.getRight() - MARK_OFFSET,
        	ownerBounds.getTop() - MARK_OFFSET - MARK_WIDTH);
        graphics.fillRect(bottomRectangleBounds);		
        graphics.setColor(oldColor);
	}

    public TSRect getLocalChildGraphMarkBounds() {
        // TODO: numbers should just come from the SIZE of the child graph mark & not be hard coded
        TSRect childMarkBounds = new TSRect();
        childMarkBounds.setLeft(this.getOwnerNode().getLocalBounds().getRight() - 20);
        childMarkBounds.setTop(this.getOwnerNode().getLocalBounds().getTop() - 0);
        childMarkBounds.setRight(childMarkBounds.getLeft() + 18);
        childMarkBounds.setBottom(childMarkBounds.getTop() - 15);
        return childMarkBounds;
    }

    public boolean hasChildGraphMark() {
        return true;
    }
    
    public TSConstRect getBadgeBounds() {
    	return this.badgeBounds;
    }
    
	// -------------------------------------------------------------------
	// Section: text offsets
	// -------------------------------------------------------------------

    // we subtract more to force the text wrapping method to wrap around sooner
    // to make room for the child graph mark which is on the right
	public double getWidth() {
		return (this.getRight() - this.getLeft()) - 15;
	}
	
	/**
	 * This method returns the vertical offset of the text relative to 
	 * the center point of this UI's owner.
	 */
	public double getTextOffsetY() {
		TSDGraph childGraph = (TSDGraph) this.getOwnerNode().getChildGraph();
		double topHeight = childGraph.getTailor().getTopNestedViewSpacing();
		return - (this.getOwnerNode().getLocalHeight() - topHeight) / 2;
	}


	/**
	 * This method returns the height of the bottom portion of the UI -- the area with the text.
	 */
	protected double getTopHeight() {
		TSDGraph childGraph = (TSDGraph) this.getOwnerNode().getChildGraph();
		TSGraphTailor tailor = childGraph.getTailor();
		return this.getTextHeight() + tailor.getTopNestedViewSpacing()-tailor.getTopNestedViewSpacing();
	}

	/**
	 * This method recalculates the text size by calling parent's updateTextSize
	 * and updates the owner's nested graph's TOP nested view spacing.
	 */
	// Hitesh - node jumping problem for nested nodes fixed here. 
	protected void updateTextSize()	{
		this.updateTextWidthAndHeight();
		if ((this.getOwnerNode() != null) && (this.getOwnerNode().isExpanded())) {
			TSDGraph childGraph = (TSDGraph) this.getOwnerNode().getChildGraph();
			TSGraphTailor tailor = childGraph.getTailor();
			tailor.setTopNestedViewSpacing(this.getTopHeight());
			tailor.setBottomNestedViewSpacing(2.0);
		}
	}    

}