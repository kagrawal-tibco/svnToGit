package com.tibco.cep.diagramming.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedString;

import javax.swing.Timer;

import com.tibco.cep.diagramming.drawing.DrawingCanvas;
import com.tomsawyer.canvas.swing.TSBaseSwingCanvas;
import com.tomsawyer.drawing.geometry.shared.TSConstRect;
import com.tomsawyer.drawing.geometry.shared.TSRect;
import com.tomsawyer.drawing.geometry.shared.TSSize;
import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.drawing.geometry.shared.TSDeviceRectangle;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEAnnotatedUI;

/**
 * @author ggrigore
 * 
 * Created by IntelliJ IDEA. User: tsawyer Date: Oct 26, 2006 Time: 1:47:48 PM
 * To change this template use File | Settings | File Templates.
 * 
 * 
 * This customized UI extends the basic functionality of a rounded rect node UI
 * (which could be potentially just a rectangle as well) to draw a customizable
 * shadow, a glow (if the owner node is selected), and also make its text look
 * like a hyperlink (underline the text) if instructed to do so externally. For
 * example, when the mouse is over the owner node, one could set the
 * hyperlinking mode on and turn it back off when the mouse is no longer over
 * the owner node. This UI can also draw its background using a gradient.
 */
public class RoundRectNodeUI extends ShapeNodeUI implements ImageObserver, ActionListener {

	private static final long serialVersionUID = 1L;

	//public static final int DEFAULT_SHADOW_WIDTH = 5;
	public static final TSEColor BORDER_COLOR = new TSEColor(GLOW_OUTER_HIGH_COLOR);

	public static final TSEColor ERROR_START_COLOR = new TSEColor(255, 134, 35);
	public static final TSEColor ERROR_END_COLOR = new TSEColor(255, 254, 247);
	
	private static final Color MARK_COLOR = new Color(170, 0, 55);

	private static int MARK_WIDTH = 6;
	private static int MARK_OFFSET = 2;	
	
			

	// public static final TSEColor START_COLOR = new TSEColor(2,212,255);
	// public static final TSEColor END_COLOR = new TSEColor(163,255,193);

	// TBS-like colors:
	/*public static final TSEColor START_COLOR = new TSEColor(255,255,255);
	public static final TSEColor END_COLOR = new TSEColor(255,255,255);*/

    public static final TSEColor START_COLOR = new TSEColor(255, 153, 0);
    public static final TSEColor END_COLOR = new TSEColor(255, 204, 100);
    
	private Stroke dashBorder = null;
	private Stroke normalBorder = null;
	private boolean isDashBorder = false;
    protected int borderWidth = 2;    

    private boolean drawTag;
    private boolean isRoundRect;
	private boolean isHyperLink;
	private boolean drawGradient;
	private boolean useSafeGradient;
	private TSEColor startColor;
	private TSEColor endColor;
	private TSEColor borderColor;
	private double oldWidth;
	private double oldHeight;
	protected boolean localDisplayCompleteName;
	protected static int MAX_TEXT_LINES = 2;
	private boolean isAnimated = false;
	DrawingCanvas parentCanvas;
    Timer timer;
	

	public RoundRectNodeUI() {
		super.setShapeAntiAliasingEnabled(true);
		setDrawShadow(true);
		//this.shadowWidth = DEFAULT_SHADOW_WIDTH;
		
		this.isRoundRect = true;
		this.setWidth(0.15);
//		this.setHeight(0.10);

		this.drawGradient = true;
		this.setGradient(RoundRectNodeUI.START_COLOR,
			RoundRectNodeUI.END_COLOR);
		this.borderColor = new TSEColor(GLOW_OUTER_HIGH_COLOR);
		this.isHyperLink = false;
		this.setJustification(TSEAnnotatedUI.LEFT);
		this.drawTag = false;
		this.localDisplayCompleteName = false;
		
		// TODO: expose animation delay
		this.timer = new Timer(300, this);
		this.timer.setInitialDelay(1000);
		this.timer.setActionCommand("ANIMATE");
	}
	
	private void setWidth(double d) {
		// TODO Auto-generated method stub	
	}
	
	public void setDrawTag(boolean drawTag) {
		this.drawTag = drawTag;
	}


	public TSRect getLocalChildGraphMarkBoundsOFF() {
		TSConstRect nodeBounds = this.getOwnerNode().getLocalBounds();
		TSRect childGraphMarkBounds = new TSRect();
		childGraphMarkBounds.setLeft(nodeBounds.getRight()-13);
		childGraphMarkBounds.setTop(nodeBounds.getTop() - 2);
		childGraphMarkBounds.setRight(childGraphMarkBounds.getLeft() +10);
		childGraphMarkBounds.setBottom(childGraphMarkBounds.getTop() - 10);
		
		// working from CollapsedSubprocessNodeUI
//		TSConstRect nodeBounds = this.getOwnerNode().getLocalBounds();
//		TSRect childGraphMarkBounds = new TSRect();
//		childGraphMarkBounds.setLeft(nodeBounds.getLeft() + MARK_GAP);
//		childGraphMarkBounds.setTop(nodeBounds.getTop() - MARK_GAP);
//		childGraphMarkBounds.setRight(childGraphMarkBounds.getLeft() + MARK_SIZE);
//		childGraphMarkBounds.setBottom(childGraphMarkBounds.getTop() - MARK_SIZE);
		
		return childGraphMarkBounds;
	}	
	
	public TSConstRect getLocalBounds() {
		return this.getOwnerNode().getLocalBounds();
	}

	

	/**
	 * This is the method that does the rendering and that we had to override.
	 */
	@SuppressWarnings("static-access")
	public void draw(TSEGraphics graphics) {
		if (this.getOwnerNode() == null) {
			return;
		}
		
		if (this.isAnimated)
			// System.out.println("was asked to draw: " + this.breakpointColor);
		
		if (graphics.getCanvas() instanceof TSBaseSwingCanvas) {
			TSBaseSwingCanvas canvas = (TSBaseSwingCanvas) graphics.getCanvas();
		
			// if we haven't initialized parentCanvas yet, do it now if possible.
			if ((this.parentCanvas == null) &&
				(!canvas.isOverview()) &&
				(canvas instanceof DrawingCanvas)) {
				this.parentCanvas = (DrawingCanvas) canvas;
			}
			
			
//		    if (this.isAnimated && !hasTimerSet) {
//		    	this.parentCanvas.addTimerListener(this);
//		    	this.parentCanvas.setTimerDelay(200);
//		    	hasTimerSet = true;
//		    }
//		    else {
//		    	this.parentCanvas.removeTimerListener(this);
//		    	hasTimerSet = false;
//		    }

			// if we're being drawn, we should always be listening for
			// events. This will set us up to start paying attention to the
			// mouse and to graph change events.
			// this.setListening(true);
		}
		
		// turn anti-aliasing on, but remember the old value so we can restore it.
		Object oldValue = graphics.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		TSESolidObject owner = this.getOwnerNode();
		@SuppressWarnings("unused")
		TSSize arcSize = null;

		if (isDrawShadow()) {
			this.drawBorderShadow(graphics, getShadowWidth());
		}
		
		if (isDrawGlow()) {
			this.drawBorderGlow(graphics, 10);
		}

		TSTransform transform = graphics.getTSTransform();

		if (!this.isTransparent()) {
			if (this.drawGradient) {
//				GradientPaint gradient = new GradientPaint(
//					transform.xToDevice(owner.getLocalLeft()),
//					transform.yToDevice(owner.getLocalTop()),
//					this.startColor.getColor(),
//					transform.xToDevice(owner.getLocalRight()),
//					transform.yToDevice(owner.getLocalBottom()),
//					this.endColor.getColor());
//				graphics.setPaint(gradient);

				if (this.isRoundRect) {
//					DEFAULT_ARC_TS_SIZE = new TSSize(this.getArcWidth()
//							* owner.getLocalWidth(), this.getArcHeight()
//							* owner.getLocalHeight());
//
//					graphics.fillRoundRect(transform.xToDevice(owner
//							.getLocalLeft()), transform.yToDevice(owner
//							.getLocalTop()), transform.widthToDevice(owner
//							.getLocalWidth()), transform.heightToDevice(owner
//							.getLocalHeight()), (int) DEFAULT_ARC_TS_SIZE.getWidth(),
//							(int) DEFAULT_ARC_TS_SIZE.getHeight());

			        TSConstRect ownerBounds = owner.getLocalBounds();			
			        double x =  transform.xToDevice(ownerBounds.getLeft());
			        double y = transform.yToDevice(ownerBounds.getTop());
			        double w = transform.widthToDevice(ownerBounds.getWidth());
			        double h = transform.heightToDevice(ownerBounds.getHeight());			
			        Shape roundRect = new RoundRectangle2D.Double(x,y,w,h,DEFAULT_ARC_SIZE, DEFAULT_ARC_SIZE);
					//if (this.useSafeGradient) {
//				        GradientPaint gradient = new GradientPaint(50.0f, 50.0f, this.startColor.getColor(),  
//		                   50.0f, 250.0f, this.endColor.getColor()); 
						GradientPaint gradient = new GradientPaint(
							transform.xToDevice(this.getOwner().getLocalLeft()),
							transform.yToDevice(this.getOwner().getLocalTop()),
							this.startColor.getColor(),
							transform.xToDevice(this.getOwner().getLocalRight()),
							transform.yToDevice(this.getOwner().getLocalBottom()),
							this.endColor.getColor());	
				        graphics.setPaint(gradient);
				        graphics.fill(roundRect);						
			        graphics.setColor(this.borderColor);
			       // graphics.draw(roundRect);
				} else {
					// This uses Swing gradient:
					graphics.fillRect(transform.xToDevice(owner.getLocalLeft()),
							transform.yToDevice(owner.getLocalTop()),
							transform.widthToDevice(owner.getLocalWidth()),
							transform.heightToDevice(owner.getLocalHeight()));
				}
			}
			// we were not asked to draw a gradient, so we just draw the colors normally.
			// this never happens for us because we don't use this mode
			else {
				graphics.setColor(this.getFillColor());
				if (this.isRoundRect) {
					arcSize = new TSSize(this.getWidth()
							* owner.getLocalWidth(), this.getHeight()
							* owner.getLocalHeight());
					// graphics.fillRoundRect(owner.getLocalBounds(), DEFAULT_ARC_TS_SIZE);
					
			        TSConstRect ownerBounds = owner.getLocalBounds();			
			        double x =  transform.xToDevice(ownerBounds.getLeft());
			        double y = transform.yToDevice(ownerBounds.getTop());
			        double w = transform.widthToDevice(ownerBounds.getWidth());
			        double h = transform.heightToDevice(ownerBounds.getHeight());			
			        Shape roundRect = new RoundRectangle2D.Double(x,y,w,h,DEFAULT_ARC_SIZE, DEFAULT_ARC_SIZE);
			        graphics.setColor(this.startColor);
			      //  graphics.draw(roundRect);
					
					
				} else {
					graphics.fillRect(owner.getLocalBounds());
				}
			}
		}

		// we never draw the border so it doesn't interfere with shadow
		if (this.isBorderDrawn() /* && !this.drawShadow */) {
			// graphics.setColor(this.getBorderColor());
			graphics.setColor(this.borderColor);

			if (this.isRoundRect) {
				// graphics.drawRoundRect(owner.getLocalBounds(), DEFAULT_ARC_TS_SIZE);
				// moved it out of here above... not cleanest thing (but OK for now)
				
				// Added back due to BPMN needs:
				if (isDashedBorder()) {
		        	Stroke oldStroke = graphics.getStroke();
		        	graphics.setStroke(this.getDashGraphicsStroke());
		            graphics.drawRoundRect(owner.getLocalBounds(), this.DEFAULT_ARC_TS_SIZE);
		            graphics.setStroke(oldStroke);
				}
				// for the case when we don't fill in entire task node UIs
				else if (!this.drawGradient) {
		        	Stroke oldStroke = graphics.getStroke();
		        	graphics.setStroke(this.getNormalGraphicsStroke());
		            graphics.drawRoundRect(owner.getLocalBounds(), this.DEFAULT_ARC_TS_SIZE);
		            graphics.setStroke(oldStroke);
				}
			} else {
				graphics.drawRect(owner.getLocalBounds());
			}
		}
		
		// draw the PLUS sign
		if (this.getOwnerNode().getChildGraph() != null) {
			this.drawChildGraphMark(graphics);
		}
		
		if (owner.getText() != null && this.drawTag) {
			// System.out.println("drawing text: " + owner.getText());
			this.drawText(graphics);
		}
		else {
			// System.out.println("NOT drawing text: " + owner.getText());
		}
		
		if (this.getOwnerNode() != null) {
			//this.drawImageBadge(graphics);
			this.drawBottommBadges(graphics);
		}
		
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldValue);
		this.drawHighlight(graphics);
	}
	
	// To be extended by subclasses if desired
	protected void drawImageBadge(TSEGraphics graphics) {
	}
	
	// To be extended by subclasses if desired
	protected void drawBottommBadges(TSEGraphics graphics) {
	}	
	
	
	protected Stroke getDashGraphicsStroke() {
		if (this.dashBorder == null) {
			this.dashBorder = new BasicStroke(this.getBorderWidth(),
					BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_BEVEL,
					0,
					new float[] { 3f, 3f },
					0);
		}
		
		return this.dashBorder;
	}
	
	
	protected Stroke getNormalGraphicsStroke() {
		if (this.normalBorder == null) {
			this.normalBorder = new BasicStroke(this.getBorderWidth());
		}
		
		return this.normalBorder;
	}
	
	protected void drawBorderGlowShape(TSEGraphics graphics) {
		graphics.drawRoundRect(this.getOwner().getLocalBounds(), ShapeNodeUI.DEFAULT_ARC_TS_SIZE);
	}	
	
	public boolean isDashedBorder() {
		return this.isDashBorder;
	}
	
	public void setDashedBorder(boolean dashed) {
		this.isDashBorder = dashed;
	}
	
	public int getBorderWidth() {
		return this.borderWidth;
	}
	
	public void setBorderWidth(int width) {
		this.borderWidth = width;
	}
	
	public void drawSelected(TSEGraphics graphics) {
		// TURNED OFF BECAUSE IT FLICKERS
		// this.drawBorderGlow(graphics, DEFAULT_SHADOW_WIDTH + 2);
		// doing it here doesn't show the grapples: this.drawGrapples(graphics);

		// we don't want to draw grapples, so either comment this out or
		// override drawGrapples(TSEGraphics) to do nothing:
		// super.drawSelected(graphics);
		
		if (graphics.getCanvas() instanceof TSBaseSwingCanvas)	{
			TSBaseSwingCanvas canvas = (TSBaseSwingCanvas) graphics.getCanvas();
	
			// if we haven't initialized parentCanvas yet, do it now if possible.
			if ((this.parentCanvas == null) &&
				(!canvas.isOverview()) &&
				(canvas instanceof DrawingCanvas)) {
				this.parentCanvas = (DrawingCanvas) canvas;
			}

			// if we're being drawn, we should always be listening for
			// events. This will set us up to start paying attention to the
			// mouse and to graph change events.
			// this.setListening(true);
		}		
		
		
		boolean oldShadow = isDrawShadow();
		boolean oldBorder = isBorderDrawn();

		setDrawShadow(false);
		// this.setBorderDrawn(false);

		// now do the normal rendering
		this.draw(graphics);
		Color oldColor = graphics.getColor();
		graphics.setColor(Color.blue);
		this.drawGrapples(graphics);
		graphics.setColor(oldColor);
		
		// restore old settings
		setDrawShadow(oldShadow);
		this.setBorderDrawn(oldBorder);

		// if we wanted the glow to be painted on top of the node (because
		// it is a bit wider), then we could draw the glow here, after
		// the normal rendering instead of before:
		// this.drawBorderGlow(graphics, DEFAULT_SHADOW_WIDTH + 2);
	}

	/*
	 * Draw the PLUS sign.
	 */
	public void drawChildGraphMark(TSEGraphics graphics) {
		Color oldColor = graphics.getColor();
		graphics.setColor(MARK_COLOR);
		TSConstRect ownerBounds = this.getOwner().getLocalBounds();		
		// TODO: for performance reasons we should pre-calculate these
		// math operations!
        TSConstRect horizontalRectangle = new TSConstRect(
            	ownerBounds.getRight() - MARK_WIDTH - MARK_OFFSET,
            	ownerBounds.getTop() - MARK_OFFSET - MARK_WIDTH/3,
            	ownerBounds.getRight() - MARK_OFFSET,
            	ownerBounds.getTop() - MARK_OFFSET - MARK_WIDTH*2/3);
        graphics.fillRect(horizontalRectangle);
            
        TSConstRect verticalRectangle = new TSConstRect(
               	ownerBounds.getRight() - MARK_OFFSET - MARK_WIDTH*2/3,
              	ownerBounds.getTop() - MARK_OFFSET,
               	ownerBounds.getRight() - MARK_OFFSET - MARK_WIDTH/3,
               	ownerBounds.getTop() - MARK_OFFSET - MARK_WIDTH);
        graphics.fillRect(verticalRectangle);
        graphics.setColor(oldColor);
	}
	
	protected void drawBorderShadow(TSEGraphics graphics, int shadowWidth) {
		TSTransform transform = graphics.getTSTransform();
		TSDeviceRectangle clip = transform.boundsToDevice(this.getOwner().getLocalBounds());
		Stroke oldStroke = graphics.getStroke();
		/*
		clip.setSize((int) clip.getWidth() - shadowWidth,
				(int) clip.getHeight() - shadowWidth);
		clip.setLocation((int) (clip.getLocation().getX() + shadowWidth),
				(int) (clip.getLocation().getY() + shadowWidth));
		*/
		clip.setBounds(clip.getX() + shadowWidth,
			clip.getY() + shadowWidth,
			clip.getWidth() - shadowWidth,
			clip.getHeight() - shadowWidth);
		
		int sw = shadowWidth * 2;
		for (int i = sw; i >= 2; i -= 2) {
			float pct = (float) (sw - i) / (sw - 1);
			graphics.setColor(getMixedColor(/* Color.LIGHT_GRAY */Color.GRAY,
					pct, Color.WHITE, 1.0f - pct));
			graphics.setStroke(new BasicStroke(i));
			graphics.drawRect(clip);
		}

		graphics.setStroke(oldStroke);
	}


	/**
	 * This method draws the text (the owner node's tag) inside the node.
	 */
	@SuppressWarnings("deprecation")
	public void drawText(TSEGraphics graphics) {
		TSTransform transform = graphics.getTSTransform();

		if (this.getOwner().getWidth() != this.oldWidth
				|| this.getOwner().getHeight() != this.oldHeight) {
			this.oldWidth = this.getOwner().getWidth();
			this.oldHeight = this.getOwner().getWidth();
			this.updateTextWidthAndHeight();
		}

		// text to be displayed
		String nodeText = this.formattedText;
		
		if (nodeText == null) {
			nodeText = ((Font)this.getOwnerNode().getUserObject()).getName();
		}
		
		if (nodeText != null) {
			TSConstRect ownerBounds = this.getOwner().getLocalBounds();

			double worldCenterX = this.getTextOffsetX() + ownerBounds.getCenterX();
			// double worldCenterY = this.getTextOffsetY() + ownerBounds.getCenterY();
			double worldCenterY = ownerBounds.getCenterY();

			Font scaledFont = this.getScaledFont(transform);
			graphics.setFont(scaledFont);
			FontMetrics fm = graphics.getFontMetrics();
			int deviceTextWidth = fm.stringWidth(this.getLongestLine());

			// there's a bug in Swing where the ascent is reported too
			// high. More accurate ascent can be approximated (heh) by
			// subtracting the leading and descent from it.
			int ascent = fm.getAscent() - fm.getLeading() - fm.getDescent();
			int deviceTextHeight = ascent + fm.getHeight()
					* (this.getNumberOfLines() - 1);

			// when calculating the width of the scaled text we take a
			// slight correction to compensate for the fact that the
			// font metrics rounds the text width up.
			int deviceTextLeft = transform.xToDevice(worldCenterX) - deviceTextWidth / 2;
			int deviceTextTop = transform.yToDevice(worldCenterY) - deviceTextHeight / 2;
			graphics.setColor(this.getTextColor().getColor());

			// before we draw we need to set the clipping or otherwise
			// the text might stick out to the right due to interger
			// step magnification
			Shape oldClip = graphics.getClip();
			TSDeviceRectangle newClip = transform.boundsToDevice(ownerBounds);
			graphics.clipRect((int)newClip.getX(), (int)newClip.getY(), (int)newClip.getWidth(), (int)newClip.getHeight());

			// we also turn on anti-aliasing
			Object oldAntiAliasingHint = null;

			if (this.isTextAntiAliasingEnabled()
					&& deviceTextHeight <= this.getTextAntiAliasingThreshold()) {
				oldAntiAliasingHint = 
					graphics.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			}

			int justification = this.getJustification();

			// Parse text to find newline characters and draw text respectively.
			int lineStart = 0;
			int lineBottom = deviceTextTop + ascent;
			int textLength = nodeText.length();

			while (lineStart < textLength) {
				int lineEnd = nodeText.indexOf('\n', lineStart);

				if (lineEnd == -1) {
					lineEnd = textLength;
				}

				String lineString = nodeText.substring(lineStart, lineEnd);
				if (justification == TSEAnnotatedUI.RIGHT) {
					deviceTextLeft = transform.xToDevice(worldCenterX)
							+ deviceTextWidth / 2 - fm.stringWidth(lineString);
				} else if (justification == TSEAnnotatedUI.CENTER) {
					deviceTextLeft = transform.xToDevice(worldCenterX)
							- fm.stringWidth(lineString) / 2;
				}

				if (lineString.length() > 0) {
					// CHANGE:
					// if (this.getOwner().isSelected())
					if (this.isHyperLink) {
						Font plainFont = this.getFont().getFont();
						AttributedString as = new AttributedString(lineString);
						as.addAttribute(TextAttribute.FONT, plainFont);
						as.addAttribute(TextAttribute.UNDERLINE,
							TextAttribute.UNDERLINE_ON, 0, lineString.length());
						graphics.drawString(as.getIterator(), deviceTextLeft, lineBottom);
					} else {
						graphics.drawString(lineString, deviceTextLeft, lineBottom);
					}
					// END CHANGE
				}

				lineBottom += fm.getHeight();
				lineStart = lineEnd + 1;
			}

			if (this.isTextAntiAliasingEnabled()
					&& deviceTextHeight <= this.getTextAntiAliasingThreshold()) {
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					oldAntiAliasingHint);
			}

			// now restore the old clipping
			graphics.setClip(oldClip);
		}
	}
	
	// ANIMATION RELATED
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	// TODO: this work is not finished (Gabe). POC for now.
	
    int animationDuration = 5000;   // each animation will take 5 seconds
    long animStartTime;     // start time for each animation
    
	public void setAnimated(boolean animate) {
		this.isAnimated = animate;
	    if (animate) {
	    	timer.start();
	    }
	    else {
	    	timer.stop();
	    }
	}
	
	public boolean isAnimated() {
		return this.isAnimated;
	}

    /**
     * Callback from the Swing Timer. Calculate the fraction elapsed of
     * our desired animation duration and interpolate between our start and
     * end colors accordingly.
     */
    Color startColor2 = Color.WHITE;  // where we start
    Color endColor2 = Color.BLACK;         // where we end
    Color currentColor = startColor2;
    		
    public void actionPerformed(ActionEvent ae) {
        // calculate elapsed fraction of animation
        long currentTime = System.nanoTime() / 1000000;
        long totalTime = currentTime - animStartTime;
        if (totalTime > animationDuration) {
            animStartTime = currentTime;
        }
        float fraction = (float)totalTime / animationDuration;
        fraction = Math.min(1.0f, fraction);
        // interpolate between start and end colors with current fraction
        int red = (int)(fraction * endColor2.getRed() + (1 - fraction) * startColor2.getRed());
        int green = (int)(fraction * endColor2.getGreen() + (1 - fraction) * startColor2.getGreen());
        int blue = (int)(fraction * endColor2.getBlue() + (1 - fraction) * startColor2.getBlue());
        // set our new color appropriately
        currentColor = new Color(red, green, blue);
        // force a repaint to display our oval with its new color
        
        if (this.parentCanvas != null) {
        	if (this.parentCanvas.isTimerEvent(ae)) {
        		// make sure the nodeUI is redrawn on the next timer tick since it's changed.
        		TSEGraph ownerGraph = (TSEGraph) this.getOwnerNode().getOwnerGraph();

        		if (ownerGraph.getOwnerGraphManager() == this.parentCanvas.getGraphManager()) {
        			if (getBreakpointColor().equals(Color.BLUE)) {
        				setBreakpointColor(Color.YELLOW);
        			}
        			else if (getBreakpointColor().equals(Color.YELLOW)) {
        				setBreakpointColor(Color.RED);
        			}
        			else if (getBreakpointColor().equals(Color.RED)) {
        				setBreakpointColor(Color.BLUE);
        			}

        			this.invalidateWorldBounds();
        		}
        	}
        	else if (ae.getActionCommand().equalsIgnoreCase("ANIMATE")) {
        		TSEGraph ownerGraph = (TSEGraph) this.getOwnerNode().getOwnerGraph();
        		if (ownerGraph.getOwnerGraphManager() == this.parentCanvas.getGraphManager()) {

        			boolean useDiscreteColors = true;

        			if (useDiscreteColors) {
        				if (getBreakpointColor().equals(Color.BLUE)) {
        					setBreakpointColor(Color.YELLOW);
        				}
        				else if (getBreakpointColor().equals(Color.YELLOW)) {
        					setBreakpointColor(Color.RED);
        				}
        				else if (getBreakpointColor().equals(Color.RED)) {
        					setBreakpointColor(Color.GREEN);
        				}
        				else if (getBreakpointColor().equals(Color.GREEN)) {
        					setBreakpointColor(Color.BLUE);
        				}
        			}
        			else {
        				setBreakpointColor(currentColor);
        			}

        			this.invalidateWorldBounds();
        		}
        	}
        }
    }

    
	/**
	 * This method invalidates the area that this UI occupies in the swing canvas.
	 */
	public void invalidateWorldBounds()
	{
		// figure out the pixel coordinates of the invalid rectangle
		TSENode ownerNode = this.getOwnerNode();
		
		if (ownerNode.isViewable()) {
			this.parentCanvas.addInvalidRegion(ownerNode);
			// tell the parent canvas it should do an update next tick.
			this.parentCanvas.requestUpdateOnTimer();
		}
	}
	
	//////////////////////////////////////////////////////////////////////

	public void setDrawGradient(boolean gradient) {
		this.drawGradient = gradient;
	}

	public boolean isGradientDrawn() {
		return this.drawGradient;
	}


	public void setGradient(TSEColor startColor, TSEColor endColor) {
		this.startColor = startColor;
		this.endColor = endColor;
		this.drawGradient = true;
	}

	public void setHyperLink(boolean hyperlink) {
		this.isHyperLink = hyperlink;
	}

	public boolean isHyperLink() {
		return this.isHyperLink;
	}

	public boolean isRoundRect() {
		return isRoundRect;
	}

	public void setRoundRect(boolean isRoundRect) {
		this.isRoundRect = isRoundRect;
	}
	
	public void setBorderColor(TSEColor color) {
		this.borderColor = color;
	}
	
	public TSEColor getBorderColor() {
		return this.borderColor;
	}
	
	public void setUseSafeGradient(boolean use) {
		this.useSafeGradient = use;
	}
	
	public boolean isUseSafeGradient() {
		return this.useSafeGradient;
	}
	
	public boolean imageUpdate(Image image,
			int flags,
			int x,
			int y,
			int width,
			int height) {
			return (((flags & ImageObserver.ALLBITS) == 0) ||
				((flags & ImageObserver.FRAMEBITS) == 0));
	}	
	
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

		@SuppressWarnings("deprecation")
		FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(this.getFont().getFont());

		StringBuffer buffer = new StringBuffer();
		int start = 0;
		int wordBreak = -1;
		boolean foundEnd = false;
		int numberOfLines = 1;
		
		for (int i = 1; i < text.length(); i++) {
			if (text.charAt(i) == '\n') {
				buffer.append(text.substring(start, i + 1));
				start = i + 1;
				wordBreak = -1;
				numberOfLines++;
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

					if (!this.localDisplayCompleteName && numberOfLines == MAX_TEXT_LINES) {
						buffer.append("...");
						foundEnd = true;
						break;
					}
					else {
						buffer.append('\n');
						numberOfLines++;
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
	
	public void drawBreakpointHit(TSEGraphics graphics, boolean inputBreakpointHit , 
            Color color) {
		Color oldColor = graphics.getColor();
		graphics.setColor(oldColor);
		TSConstRect ownerBounds = this.getOwner().getLocalBounds();
		TSTransform transform = graphics.getTSTransform();	
		if (inputBreakpointHit) {

//			drawImage(graphics, RADIO_BRK_HIT_IMAGE, new TSEColor(255, 110, 0), TSEColor.white, inputBreakPointBounds);
//			drawOutputBreakpoints(graphics,hasOutBreakpoint, outBrkPoinToggle);
			
			drawInputBreakpoints(graphics, true, false, color);
			drawBreakPointLeft(graphics, ownerBounds, transform, color);
			drawOutputBreakpoints(graphics, true, isOutputBreakPointToggle(), getBreakpointColor());
			if (outputBreakpointHit) {
				drawBreakpointHit(graphics, false, color);
			}
		} else {
			
//			drawImage(graphics, RADIO_BRK_HIT_IMAGE, new TSEColor(255, 110, 0), TSEColor.white, outputBreakPointBounds);
//			drawInputBreakpoints(graphics, hasInBreakpoint, inBrkPoinToggle);
			
			drawOutputBreakpoints(graphics, true, false, color);
			drawBreakPointRight(graphics, ownerBounds, transform, color);
			drawInputBreakpoints(graphics, true, isOutputBreakPointToggle(), getBreakpointColor());
			if (inputBreakpointHit) {
				drawBreakpointHit(graphics, true, color);
			}
		}
	}
	
	private void drawBreakPointLeft(TSEGraphics graphics, TSConstRect ownerBounds, TSTransform transform, Color color) {
		Color oldColor = graphics.getColor();
		int x = transform.xToDevice(ownerBounds.getLeft() + INNER_BREAKPOINT_SIZE );
		int y = transform.yToDevice(ownerBounds.getCenterY() + INNER_BREAKPOINT_SIZE - 3);
		graphics.fillOval(x - 2, y - DEFAULT_BREAKPOINT_OFFSET, transform.widthToDevice(INNER_BREAKPOINT_SIZE), transform.heightToDevice(INNER_BREAKPOINT_SIZE));
		graphics.setColor(oldColor);
		oldColor = graphics.getColor();
		graphics.setColor(color);
	}
	
	private void drawBreakPointRight(TSEGraphics graphics, TSConstRect ownerBounds, TSTransform transform, Color color) {
		Color oldColor = graphics.getColor();
		int x = transform.xToDevice(ownerBounds.getRight() - INNER_BREAKPOINT_SIZE - 3 );
		int y = transform.yToDevice(ownerBounds.getCenterY() + INNER_BREAKPOINT_SIZE - 3);
		graphics.fillOval(x -2, y - DEFAULT_BREAKPOINT_OFFSET, transform.widthToDevice(INNER_BREAKPOINT_SIZE), transform.heightToDevice(INNER_BREAKPOINT_SIZE));
		graphics.setColor(oldColor);
		oldColor = graphics.getColor();
		graphics.setColor(color);
	}
	
	
	/**
	 * @param graphics
	 * @param inputBrkPnt
	 * @param inBrkPnToggle
	 * @param outputBrkPnt
	 * @param outBrkPnToggle
	 */
	public void drawInputBreakpoints(TSEGraphics graphics, 
				                    boolean inputBrkPnt, 
				                    boolean inBrkPnToggle, 
				                    Color color) {
		Color oldColor = graphics.getColor();
		graphics.setColor(oldColor);
		TSConstRect ownerBounds = this.getOwner().getLocalBounds();
		TSTransform transform = graphics.getTSTransform();	
//		Color color = this.breakpointColor;
		graphics.setColor(color);
		setInputBreakPointBounds(new TSConstRect(
	            	ownerBounds.getLeft() + 1,
	            	ownerBounds.getCenterY() + DEFAULT_BREAKPOINT_SIZE/2,
	            	ownerBounds.getLeft() + 1 + DEFAULT_BREAKPOINT_SIZE,
	            	ownerBounds.getCenterY() - DEFAULT_BREAKPOINT_SIZE/2));
		graphics.drawOval(getInputBreakPointBounds());
		//Draw Border Shadow for the outer one
		drawBorderShadow(graphics, 2, getInputBreakPointBounds(), false);

	//Draw Image
//		if (inputBrkPnt && inBrkPnToggle) {
//			drawImage(graphics, RADIO_ON_IMAGE, new TSEColor(255, 110, 0), TSEColor.white, inputBreakPointBounds);
//		} else {
//			drawImage(graphics, RADIO_OFF_IMAGE, new TSEColor(255, 110, 0), TSEColor.white, inputBreakPointBounds);
//		}
		graphics.setColor(oldColor);
		oldColor = graphics.getColor();
		graphics.setColor(color);

		//Draw Border Shadow for the outer one
//		drawBorderShadow(graphics, 4, inputBreakPointBounds, true);
		
		graphics.setColor(oldColor);
		oldColor = graphics.getColor();
		graphics.setColor(color);
		
		if (inputBrkPnt && inBrkPnToggle) {
			int x = transform.xToDevice(ownerBounds.getLeft() + INNER_BREAKPOINT_SIZE );
			int y = transform.yToDevice(ownerBounds.getCenterY() + INNER_BREAKPOINT_SIZE - 3);
			graphics.fillOval(x - 2, y - DEFAULT_BREAKPOINT_OFFSET, transform.widthToDevice(INNER_BREAKPOINT_SIZE), transform.heightToDevice(INNER_BREAKPOINT_SIZE));
			graphics.setColor(oldColor);
			oldColor = graphics.getColor();
			graphics.setColor(color);
		}
	}
	
	protected void drawBorderShadow(TSEGraphics graphics, int shadowWidth, TSConstRect rect, boolean isArc) {
		Stroke oldStroke = graphics.getStroke();
		int sw = shadowWidth;
		if (!isArc) {
			sw = shadowWidth * 2;
		} 
		for (int i = sw; i >= 2; i -= 2) {
			float pct = (float) (sw - i) / (sw - 1);
			graphics.setStroke(new BasicStroke(i));
			if (isArc) {
				graphics.setColor(getMixedColor(GLOW_OUTER_HIGH_COLOR, pct, GLOW_OUTER_LOW_COLOR, 1.0f - pct));
				graphics.drawArc(rect, 400, 200);
			} else {
				graphics.drawOval(rect);
			}
		}
		graphics.setStroke(oldStroke);
	}
	
	public void drawOutputBreakpoints(TSEGraphics graphics, 
										boolean outputBrkPnt, 
										boolean outBrkPnToggle, 
					                    Color color) {

		Color oldColor = graphics.getColor();
		graphics.setColor(oldColor);
		TSConstRect ownerBounds = this.getOwner().getLocalBounds();
		TSTransform transform = graphics.getTSTransform();	
//		Color color = this.breakpointColor;
		graphics.setColor(color);
		setOutputBreakPointBounds(new TSConstRect(
				ownerBounds.getRight() - 1 - DEFAULT_BREAKPOINT_SIZE -1,
				ownerBounds.getCenterY() + DEFAULT_BREAKPOINT_SIZE/2,
				ownerBounds.getRight() - 1 - 1,
				ownerBounds.getCenterY() - DEFAULT_BREAKPOINT_SIZE/2));
		graphics.drawOval(getOutputBreakPointBounds());
		
		//Draw Border Shadow for the outer one 
		drawBorderShadow(graphics, 2, getOutputBreakPointBounds(), false);
		
		//Draw Image
//		if (outputBrkPnt && outBrkPnToggle) {
//			drawImage(graphics, RADIO_ON_IMAGE, new TSEColor(255, 110, 0), TSEColor.white, outputBreakPointBounds);
//		} else {
//			drawImage(graphics, RADIO_OFF_IMAGE, new TSEColor(255, 110, 0), TSEColor.white, outputBreakPointBounds);
//		}
		graphics.setColor(oldColor);
		oldColor = graphics.getColor();
		graphics.setColor(color);
		
		//Draw Border Shadow for the outer one
//		drawBorderShadow(graphics, 4, outputBreakPointBounds, true);
		
		graphics.setColor(oldColor);
		oldColor = graphics.getColor();
		graphics.setColor(color);

		if (outputBrkPnt && outBrkPnToggle) {
			int x = transform.xToDevice(ownerBounds.getRight() - INNER_BREAKPOINT_SIZE - 3 );
			int y = transform.yToDevice(ownerBounds.getCenterY() + INNER_BREAKPOINT_SIZE - 3);
			graphics.fillOval(x - 2, y - DEFAULT_BREAKPOINT_OFFSET, transform.widthToDevice(INNER_BREAKPOINT_SIZE), transform.heightToDevice(INNER_BREAKPOINT_SIZE));
			graphics.setColor(oldColor);
			oldColor = graphics.getColor();
			graphics.setColor(color);
		}
	}

}
