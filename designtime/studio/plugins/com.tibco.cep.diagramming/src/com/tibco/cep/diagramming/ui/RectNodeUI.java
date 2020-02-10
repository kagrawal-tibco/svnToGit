package com.tibco.cep.diagramming.ui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.RenderingHints;
import java.awt.Stroke;

import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.drawing.geometry.shared.TSDeviceRectangle;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEAnnotatedUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEShapeNodeUI;

/**
 * created by ggrigore
 *
 */
public class RectNodeUI extends TSEShapeNodeUI {

/**
	 * 
	 */
	private static final long serialVersionUID = 2732651856550043362L;



	//	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_SHADOW_WIDTH = 5;

//    private static final Color GLOW_INNER_HIGH_COLOR = new Color(253, 239, 175, 148);
//    private static final Color GLOW_INNER_LOW_COLOR = new Color(255, 209, 0);
//    private static final Color GLOW_OUTER_HIGH_COLOR = new Color(253, 239, 175, 124);
//    private static final Color GLOW_OUTER_LOW_COLOR = new Color(255, 179, 0);

    private static final Color GLOW_OUTER_HIGH_COLOR = new Color(255, 255, 255, 250);
    private static final Color GLOW_OUTER_LOW_COLOR = new Color(255, 249, 94);
    private static final Color GLOW_INNER_HIGH_COLOR = new Color(255, 255, 255, 50);
    private static final Color GLOW_INNER_LOW_COLOR = new Color(255, 219, 94);
    
    public static final TSEColor START_COLOR = new TSEColor(2,212,255);
    public static final TSEColor END_COLOR = new TSEColor(163,255,193);

  
    private boolean drawShadow;
    private boolean drawGradient;
    private boolean drawGlow;
    private int shadowWidth;
    public TSEColor startColor;
    public TSEColor endColor;
   


    public RectNodeUI() {
        this.drawShadow = true;
        this.shadowWidth = DEFAULT_SHADOW_WIDTH;
        this.drawGradient = true;
        this.setGradient(RectNodeUI.START_COLOR, RectNodeUI.END_COLOR);
        this.setJustification(TSEAnnotatedUI.LEFT);
    }


    /**
     * This is the method that does the rendering and that we had to override.
     */
    public void draw(TSEGraphics graphics) {
        if (this.getOwnerNode() == null) {
            return;
        }

        // turn anti-aliasing on, but remember the old value so we can restore it.
        Object oldValue = graphics.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        
//        super.draw(graphics);
        TSESolidObject owner = this.getOwnerNode();

        if (this.drawShadow) {
            this.drawBorderShadow(graphics, this.shadowWidth);
        }

        if (!this.isTransparent()) {
            if (this.drawGradient) {
                TSTransform transform = graphics.getTSTransform();

                GradientPaint gradient = new GradientPaint(
                    transform.xToDevice(owner.getLocalLeft()),
                    transform.yToDevice(owner.getLocalTop()),
                    this.startColor.getColor(),
                    transform.xToDevice(owner.getLocalLeft()),
                    transform.yToDevice(owner.getLocalBottom()),
                    this.endColor.getColor());
                graphics.setPaint(gradient);

                    // This uses Swing gradient:
                    graphics.fillRect(
                        transform.xToDevice(owner.getLocalLeft()),
                        transform.yToDevice(owner.getLocalTop()),
                        transform.widthToDevice(owner.getLocalWidth()),
                        transform.heightToDevice(owner.getLocalHeight()));
            }
            // we were not asked to draw a gradient, so we just draw the colors normally.
            else {
                graphics.setColor(this.getFillColor());
                graphics.fillRect(owner.getLocalBounds());
            }
        }

        if (this.isBorderDrawn()) {
            graphics.setColor(this.getBorderColor());
            graphics.drawRect(owner.getLocalBounds());
        }

        // draw the text
        if (owner.getText() != null) {
            if (owner.isSelected()) {
                // attempts to draw the text underlined here failed...
//                this.drawText(graphics);
//
//                TSTransform transform = graphics.getTSTransform();
//
//                Font f = this.getFont().getFont();
//                FontMetrics fm = graphics.getFontMetrics(f);
//                int x1 = 0;
//                int y1 = fm.getHeight();
//                int x2 = fm.stringWidth(this.getOwner().getText());
//
//                if (this.getOwner().getText().length() > 0)
//                {
//                    Point start = transform.pointToDevice(x1, y1);
//                    Point end = transform.pointToDevice(x2, y1);
//                    graphics.drawLine(
//                            (int)start.getX(),
//                            (int)start.getY(),
//                            (int)end.getX(),
//                            (int)end.getY());
//                }

                  this.drawText(graphics);

//                Font plainFont = new Font("Times New Roman", Font.PLAIN, 24);
//
//                AttributedString as = new AttributedString(this.getOwner().getText());
//                as.addAttribute(TextAttribute.FONT, plainFont);
//                as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, 1, 15);
//                graphics.drawString(as.getIterator(), 24, 70);
            }
            else {
                this.drawText(graphics);
            }
        }

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldValue);
    }


    /**
     * This is the method that does the rendering if the owner node is selected.
     */
    public void drawSelected(TSEGraphics graphics) {
   	
        this.drawBorderGlow(graphics, DEFAULT_SHADOW_WIDTH + 2);

        // we don't want to draw grapples, so either comment this out or
        // override drawGrapples(TSEGraphics) to do nothing:
        // super.drawSelected(graphics);
        boolean oldShadow = this.drawShadow;
        boolean oldBorder = this.isBorderDrawn();

        this.drawShadow = false;
        this.setBorderDrawn(false);

        // now do the normal rendering
        this.draw(graphics);

        // restore old settings
        this.drawShadow = oldShadow;
        this.setBorderDrawn(oldBorder);

        // if we wanted the glow to be painted on top of the node (because
        // it is a bit wider), then we could draw the glow here, after
        // the normal rendering instead of before:
        // this.drawBorderGlow(graphics, DEFAULT_SHADOW_WIDTH + 2);
    }

    protected void drawBorderShadow(TSEGraphics graphics, int shadowWidth) {
        TSTransform transform = graphics.getTSTransform();
        TSDeviceRectangle clip = transform.boundsToDevice(this.getOwner().getLocalBounds());
        
        Stroke oldStroke = graphics.getStroke();

        /*
        clip.setSize(
            (int) clip.getWidth() - shadowWidth,
            (int) clip.getHeight() - shadowWidth);

        clip.setLocation(
            (int) (clip.getLocation().getX() + shadowWidth),
            (int) (clip.getLocation().getY() + shadowWidth));
        */
        clip.setWidth(clip.getWidth() - shadowWidth);
        clip.setHeight(clip.getHeight() - shadowWidth);
        clip.setX(clip.getX() + shadowWidth);
        clip.setY(clip.getY() + shadowWidth);

        int sw = shadowWidth * 2;
        for (int i = sw; i >= 2; i -= 2) {
            float pct = (float)(sw - i) / (sw - 1);
            graphics.setColor(
            	// Changed from Color.LIGHT_GRAY to Color.GRAY !!! 
                getMixedColor(Color.GRAY,
                    pct,
                    Color.WHITE,
                    1.0f - pct));
            graphics.setStroke(new BasicStroke(i));
            // graphics.draw(clip);
            graphics.drawRect(clip);
        }
        
        graphics.setStroke(oldStroke);
    }


    /**
     * Here's the trick... To render the glow, we start with a thick pen
     * of the "inner" color and stroke the desired shape.  Then we repeat
     * with increasingly thinner pens, moving closer to the "outer" color
     * and increasing the opacity of the color so that it appears to
     * fade towards the interior of the shape.
     */
    protected void drawBorderGlow(TSEGraphics g2, int glowWidth) {
        float height = (float) this.getHeight();
        int gw = glowWidth*2;
        
        Stroke oldStroke = g2.getStroke();        

        for (int i=gw; i >= 2; i-=2)
        {
            float pct = (float)(gw - i) / (gw - 1);

            Color mixHi = getMixedColor(
                GLOW_INNER_HIGH_COLOR,
                pct,
                GLOW_OUTER_HIGH_COLOR,
                1.0f - pct);
            Color mixLo = getMixedColor(
                GLOW_INNER_LOW_COLOR,
                pct,
                GLOW_OUTER_LOW_COLOR,
                1.0f - pct);

            g2.setPaint(
                new GradientPaint(0.0f, height*0.25f,  mixHi, 0.0f, height, mixLo));
            //g2.setColor(Color.WHITE);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, pct));
            g2.setStroke(new BasicStroke(i));
            g2.drawRect(this.getOwner().getLocalBounds());
        }
        
        g2.setStroke(oldStroke);
    }


    protected static Color getMixedColor(Color c1, float pct1, Color c2, float pct2) {
        float[] clr1 = c1.getComponents(null);
        float[] clr2 = c2.getComponents(null);

        for (int i = 0; i < clr1.length; i++) {
            clr1[i] = (clr1[i] * pct1) + (clr2[i] * pct2);
        }

        return new Color(clr1[0], clr1[1], clr1[2], clr1[3]);
    }

    public void setDrawGradient(boolean gradient) {
        this.drawGradient = gradient;
    }

    public void setDrawShadow(boolean shadow) {
        this.drawShadow = shadow;
    }
    
    public void setDrawGlow(boolean glow) {
    	this.drawGlow = glow;
    }

    public boolean isShadowDrawn() {
        return this.drawShadow;
    }

    public boolean isGradientDrawn() {
        return this.drawGradient;
    }

    public boolean isGlowDrawn() {
        return this.drawGlow;
    }

    public void setShadowWidth(int width) {
        this.shadowWidth = width;
    }

    public int getShadowWidth() {
        return this.shadowWidth;
    }

    public void setGradient(TSEColor startColor, TSEColor endColor) {
        this.startColor = startColor;
        this.endColor = endColor;
        this.drawGradient = true;
    }
}
