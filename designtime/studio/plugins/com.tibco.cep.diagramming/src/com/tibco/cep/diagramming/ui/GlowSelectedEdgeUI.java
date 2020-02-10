package com.tibco.cep.diagramming.ui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Stroke;

import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEPolylineEdgeUI;

public class GlowSelectedEdgeUI extends TSEPolylineEdgeUI {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Color GLOW_OUTER_HIGH_COLOR = new Color(255, 255, 255, 250);
    private static final Color GLOW_OUTER_LOW_COLOR = new Color(255, 249, 94);
    private static final Color GLOW_INNER_HIGH_COLOR = new Color(255, 255, 255, 50);
    private static final Color GLOW_INNER_LOW_COLOR = new Color(255, 219, 94);
    
    
	public void drawSelected(TSEGraphics graphics) {
		graphics.setColor(TSEColor.darkYellow);
		
		Stroke oldStroke = graphics.getStroke();
		BasicStroke stroke = new BasicStroke(2);
		graphics.setStroke(stroke);
		super.drawPath(graphics);
		graphics.setStroke(oldStroke);
		
		// we should use some fancier glow
		// this.drawLineGlow(graphics, 5);
	}
	
    protected void drawLineGlow(TSEGraphics g2, int glowWidth)
    {
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
            // g2.drawRect(this.getOwner().getLocalBounds());
            super.drawPath(g2);
        }
        
        g2.setStroke(oldStroke);
    }
    

    protected static Color getMixedColor(Color c1, float pct1, Color c2, float pct2)
    {
        float[] clr1 = c1.getComponents(null);
        float[] clr2 = c2.getComponents(null);

        for (int i = 0; i < clr1.length; i++)
        {
            clr1[i] = (clr1[i] * pct1) + (clr2[i] * pct2);
        }

        return new Color(clr1[0], clr1[1], clr1[2], clr1[3]);
    }
}
