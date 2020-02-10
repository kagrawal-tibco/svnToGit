package com.tibco.cep.diagramming.ui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Stroke;

import org.eclipse.swt.graphics.RGB;

import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.drawing.geometry.shared.TSConstRect;
import com.tomsawyer.drawing.geometry.shared.TSSize;
import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEShapeNodeUI;

/**
 * 
 * @author ggrigore
 *
 */
public class ShapeNodeUI extends TSEShapeNodeUI {
	
	protected static final int DEFAULT_ARC_SIZE = 11;
	protected static final TSSize DEFAULT_ARC_TS_SIZE = new TSSize(DEFAULT_ARC_SIZE, DEFAULT_ARC_SIZE);	
    public static final int DEFAULT_BREAKPOINT_OFFSET = 2;
    public static final int DEFAULT_BREAKPOINT_SIZE = 8;
    public static final int DEFAULT_SHADOW_WIDTH = 5;	
	

    public static final Color GLOW_INNER_HIGH_COLOR = new Color(255, 10, 22);
	
    public static final Color GLOW_INNER_LOW_COLOR = new Color(255, 10, 22);
    
    /**
    private static final Color GLOW_OUTER_HIGH_COLOR = new Color(255, 153, 0);	
	private static final Color GLOW_OUTER_LOW_COLOR = new Color(255, 204, 100);
	private static final Color GLOW_OUTER_LOW_COLOR = new Color(255, 251, 221);
	private static final Color GLOW_INNER_HIGH_COLOR = new Color(255, 255, 255,50);
	private static final Color GLOW_INNER_LOW_COLOR = new Color(255, 219, 94);
    */
	public static final Color GLOW_OUTER_HIGH_COLOR = new Color(255, 153, 0);
    
   // public static final Color GLOW_OUTER_HIGH_COLOR = new Color(0,0, 0);
    
	public static final Color GLOW_OUTER_LOW_COLOR = new Color(255, 251, 221);
	
	public static final int INNER_BREAKPOINT_SIZE = 4;
	
	private static final long serialVersionUID = 1L;
	public RGB color= new RGB(255, 153, 0);
	
	/**
	 * @param c1
	 * @param pct1
	 * @param c2
	 * @param pct2
	 * @return
	 */
	public static Color getMixedColor(Color c1, float pct1, Color c2, float pct2) {
		float[] clr1 = c1.getComponents(null);
		float[] clr2 = c2.getComponents(null);
		for (int i = 0; i < clr1.length; i++) {
			clr1[i] = (clr1[i] * pct1) + (clr2[i] * pct2);
		}
		return new Color(clr1[0], clr1[1], clr1[2], clr1[3]);
	}
	
	
	private Color breakpointColor = GLOW_OUTER_HIGH_COLOR;
	
	private boolean drawError = false;
	
	private boolean drawGlow = false;
	
	private boolean drawShadow=true;
	
	private boolean inputBreakpoint = false;	
	
	private boolean inputBreakpointHit = false;
	
	private boolean inputBreakPointToggle = false;
	
	private boolean outputBreakpoint = false;
	
	private TSConstRect outputBreakPointBounds;
	
	protected boolean outputBreakpointHit = false;
	
	private boolean outputBreakPointToggle = false;
	
	private TSConstRect inputBreakPointBounds;
	
	private TSConstRect errorBounds;
	
	private int shadowWidth=DEFAULT_SHADOW_WIDTH;	
	
	protected boolean showBreakPoints = false;
	
	/**
	 * @param graphics
	 * @param isEnabled
	 * @param inBrkPnToggle
	 * @param outputBrkPnt
	 * @param outBrkPnToggle
	 * @deprecated Use {@link #drawError(TSEGraphics,Color,int)} instead
	 */
	public void drawError(TSEGraphics graphics,Color color) {
		drawError(graphics, color, 0);
	}

	/**
	 * @param graphics
	 * @param yOffset TODO
	 * @param isEnabled
	 * @param inBrkPnToggle
	 * @param outputBrkPnt
	 * @param outBrkPnToggle
	 */
	public void drawError(TSEGraphics graphics,Color color, int yOffset) {
		double offset = 10+yOffset;
		double crosswidth = 3;
		Color oldColor = graphics.getColor();
		graphics.setColor(oldColor);
		TSConstRect ownerBounds = this.getOwner().getLocalBounds();
		TSTransform transform = graphics.getTSTransform();	
//		Color color = this.breakpointColor;
		graphics.setColor(color);
		setErrorBounds(new TSConstRect(
					ownerBounds.getCenterX() + DEFAULT_BREAKPOINT_SIZE/2,
	            	ownerBounds.getTop() - offset,
	            	ownerBounds.getCenterX() - DEFAULT_BREAKPOINT_SIZE/2,
	            	ownerBounds.getTop() - offset + DEFAULT_BREAKPOINT_SIZE
	            	));
		graphics.drawOval(getErrorBounds());
		//Draw Border Shadow for the outer one
//		drawBorderShadow(graphics, 1, getErrorBounds(), false);

		graphics.setColor(oldColor);
		oldColor = graphics.getColor();
		graphics.setColor(color);
		
		graphics.setColor(oldColor);
		oldColor = graphics.getColor();
		graphics.setColor(color);
		int x1 = transform.xToDevice(ownerBounds.getCenterX() - crosswidth/2 );
		int y1 = transform.yToDevice(ownerBounds.getTop() - (offset+yOffset+2)/2 - crosswidth/2 );
		int x2 = transform.xToDevice(ownerBounds.getCenterX() + crosswidth/2 );
		int y2 = transform.yToDevice(ownerBounds.getTop() - (offset+yOffset+2)/2 + crosswidth/2 );
		TSConstRect bounds = new TSConstRect(x1, y1, x2, y2);
		Stroke stroke = graphics.getStroke();
		try {
			Stroke newStroke = new BasicStroke(2);
			graphics.setStroke(newStroke);
			graphics.drawLine(x1, y1, x2, y2);
			graphics.drawLine(x1, y2, x2, y1);
		} finally {
			graphics.setStroke(stroke);
			graphics.setColor(oldColor);
			oldColor = graphics.getColor();
			graphics.setColor(color);
		}
	}
	
	/**
	 * @return the errorBounds
	 */
	public TSConstRect getErrorBounds() {
		return errorBounds;
	}

	/**
	 * @param errorBounds the errorBounds to set
	 */
	public void setErrorBounds(TSConstRect errorBounds) {
		this.errorBounds = errorBounds;
	}

	public TSEColor getColor(){
		return new TSEColor(color.red,color.green,color.blue);
		
	}
	public void setNodeColor(){
		this.setBorderColor(getColor());
		this.setBreakpointColor(new Color(color.red,color.green,color.blue));
	}
	public void draw(TSEGraphics graphics) {
		//		if (this.drawShadow) {
		//			this.drawBorderShadow(graphics, this.shadowWidth);
		//		}
		if (this.drawGlow) {
			this.drawBorderGlow(graphics, 10);
		}
		
		
		
		super.draw(graphics);
		
//		this.drawBreakpoints(graphics);
	}

	/**
	 * Here's the trick... To render the glow, we start with a thick pen of the
	 * "inner" color and stroke the desired shape. Then we repeat with
	 * increasingly thinner pens, moving closer to the "outer" color and
	 * increasing the opacity of the color so that it appears to fade towards
	 * the interior of the shape.
	 */
	protected void drawBorderGlow(TSEGraphics g2, int glowWidth) {
		float height = (float) this.getHeight();
		int gw = glowWidth * 2;

		Stroke oldStroke = g2.getStroke();

		for (int i = gw; i >= 2; i -= 2) {
			float pct = (float) (gw - i) / (gw - 1);

			Color mixHi = RoundRectNodeUI.getMixedColor(
				RoundRectNodeUI.GLOW_INNER_HIGH_COLOR,
				pct,
				RoundRectNodeUI.GLOW_OUTER_HIGH_COLOR,
				1.0f - pct);
			Color mixLo = RoundRectNodeUI.getMixedColor(
				RoundRectNodeUI.GLOW_INNER_LOW_COLOR,
				pct,
				RoundRectNodeUI.GLOW_OUTER_LOW_COLOR,
				1.0f - pct);

			g2.setPaint(new GradientPaint(0.0f, height * 0.25f, mixHi, 0.0f, height, mixLo));

			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,	pct));
			g2.setStroke(new BasicStroke(i));
			this.drawBorderGlowShape(g2);
		}

		g2.setStroke(oldStroke);
	}

	protected void drawBorderGlowShape(TSEGraphics graphics) {
			
	}

	protected void drawMessageBackground(TSEGraphics graphics, TSConstRect badgeBounds) {
		System.err.println("Parent message node background should never be called.");
	}

	protected void drawMessageBadge(TSEGraphics graphics, TSConstRect badgeBounds) {
		
//		BasicStroke oldStroke = (BasicStroke) graphics.getStroke();
//		Stroke newStroke = new BasicStroke(
//			oldStroke.getLineWidth() * 3 /* (this.outerBorderMultiplier-1) */,
//			oldStroke.getEndCap(),
//			oldStroke.getLineJoin(),
//			oldStroke.getMiterLimit(),
//			oldStroke.getDashArray(),
//			oldStroke.getDashPhase());
//		graphics.setStroke(newStroke);
//		graphics.setColor(GLOW_OUTER_HIGH_COLOR);
//		
//		drawMessageBackground(graphics, badgeBounds);
		
		TSConstPoint start = new TSConstPoint(badgeBounds.getLeft()+1, badgeBounds.getTop()-1);
		TSConstPoint end = new TSConstPoint(badgeBounds.getLeft() + badgeBounds.getWidth()/2.0,
			badgeBounds.getTop()-badgeBounds.getHeight()/2.0);
		TSConstPoint endOther = new TSConstPoint(badgeBounds.getRight()-1, badgeBounds.getTop()-1);

		graphics.drawLine(start, end);
		graphics.drawLine(end, endOther);
		
// 		graphics.setStroke(oldStroke);		
	}

	public Color getBreakpointColor() {
		return breakpointColor;
	}
	
	public TSConstRect getInputBreakPointBounds() {
		return inputBreakPointBounds;
	}
	
	
	public TSConstRect getOutputBreakPointBounds() {
		return outputBreakPointBounds;
	}
	
	public int getShadowWidth() {
		return shadowWidth;
	}
	
	public boolean hasInputBreakpoint() {
		return this.inputBreakpoint;
	}
	
	

	public boolean hasOutputBreakpoint() {
		return this.outputBreakpoint;
	}

	/**
	 * @return the drawError
	 */
	public boolean isDrawError() {
		return drawError;
	}

	public boolean isDrawGlow() {
		return drawGlow;
	}

	public boolean isDrawShadow() {
		return drawShadow;
	}

	public boolean isGlowDrawn() {
		return this.drawGlow;
	}


	public boolean isInputBreakpoint() {
		return inputBreakpoint;
	}

	public boolean isInputBreakpointHit() {
		return inputBreakpointHit;
	}
	public boolean isInputBreakPointToggle() {
		return inputBreakPointToggle;
	}

	public boolean isOutBreakpoint() {
		return outputBreakpoint;
	}
	/**
	 * @return the outputBreakpoint
	 */
	public boolean isOutputBreakpoint() {
		return outputBreakpoint;
	}
	
	public boolean isOutputBreakpointHit() {
		return outputBreakpointHit;
	}


	public boolean isOutputBreakPointToggle() {
		return outputBreakPointToggle;
	}


	public boolean isShowBreakPoints() {
		return showBreakPoints;
	}


	public void setBreakpointColor(Color breakpointColor) {
		this.breakpointColor = breakpointColor;
	}
	
	/**
	 * @param drawError the drawError to set
	 */
	public void setDrawError(boolean drawError) {
		this.drawError = drawError;
	}
	public void setDrawGlow(boolean glow) {
		this.drawGlow = glow;
	}
	public void setDrawShadow(boolean drawShadow) {
		this.drawShadow = drawShadow;
	}
	
	public void setInBreakpoint(boolean hasInBreakpoint) {
		this.inputBreakpoint = hasInBreakpoint;
	}
	
	public void setInputBreakpoint(boolean in) {
		this.inputBreakpoint = in;
	}	
	
	/**
	 * @param inputBreakPointBounds the inputBreakPointBounds to set
	 */
	public void setInputBreakPointBounds(TSConstRect inputBreakPointBounds) {
		this.inputBreakPointBounds = inputBreakPointBounds;
	}
	
	
	public void setInputBreakpointHit(boolean inputBreakpointHit) {
		this.inputBreakpointHit = inputBreakpointHit;
	}

	
	public void setInputBreakPointToggle(boolean inBrkPoinToggle) {
		this.inputBreakPointToggle = inBrkPoinToggle;
	}
	
	public void setOutBreakpoint(boolean hasOutBreakpoint) {
		this.outputBreakpoint = hasOutBreakpoint;
	}
	
	
//	public void drawBreakpoints(TSEGraphics graphics) {
//		if (!this.hasInBreakpoint && !this.hasOutBreakpoint) {
//			return;
//		}
//		else {
//			if (this.hasInBreakpoint) {
//				TSEConnector inConnector = (TSEConnector) this.getOwnerNode().addConnector();
//				inConnector.setSize(7.0, 7.0);
//				inConnector.setProportionalXOffset(-0.45);
//				inConnector.setProportionalYOffset(0);
//			}
//			if (this.hasOutBreakpoint) {
//				TSEConnector inConnector = (TSEConnector) this.getOwnerNode().addConnector();
//				inConnector.setSize(7.0, 7.0);
//				inConnector.setProportionalXOffset(0.45);
//				inConnector.setProportionalYOffset(0);
//			}
//		}
//	}
	
	public void setOutputBreakpoint(boolean out) {
		this.outputBreakpoint = out;
	}

	/**
	 * @param outputBreakPointBounds the outputBreakPointBounds to set
	 */
	public void setOutputBreakPointBounds(TSConstRect outputBreakPointBounds) {
		this.outputBreakPointBounds = outputBreakPointBounds;
	}
	
	public void setOutputBreakpointHit(boolean outputBreakpointHit) {
		this.outputBreakpointHit = outputBreakpointHit;
	}
	
	public void setOutputBreakPointToggle(boolean outBrkPoinToggle) {
		this.outputBreakPointToggle = outBrkPoinToggle;
	}
	

	public void setShadowWidth(int shadowWidth) {
		this.shadowWidth = shadowWidth;
	}

	public void setShowBreakPoints(boolean showBreakPoints) {
		this.showBreakPoints = showBreakPoints;
	}
}
