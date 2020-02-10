package com.tibco.cep.bpmn.ui.graph.model.factory.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.Stroke;

import com.tibco.cep.diagramming.ui.ShapeNodeUI;
import com.tomsawyer.drawing.geometry.shared.TSPolygonShape;
import com.tomsawyer.drawing.geometry.shared.TSConstRect;
import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;

/**
 * 
 * @author ggrigore
 *
 */
public class GatewayNodeUI extends ShapeNodeUI {

	private static final long serialVersionUID = 1L;
	protected final int innerThickness = 3;
	protected double INNER_SIZE = 0.50;
	protected double WIDTH = 6;
	

	public GatewayNodeUI() {

		if (this.getOwnerNode() != null) {
			this.getOwnerNode().setShape(TSPolygonShape.fromString(
			"[ 4 (50, 0) (100, 50) (50, 100) (0, 50) ]"));
			// we don't want to paint any tags
			this.getOwnerNode().setName(TSPolygonShape.DIAMOND);
		}
		
		this.setBorderColor(TSEColor.black);
		this.setFillColor(new TSEColor(255, 232, 138));
		setTextAntiAliasingEnabled(true);
		setShapeAntiAliasingEnabled(true);
		//setDrawGlow(false);
	}
	
	public void draw(TSEGraphics graphics) {
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);		
		if (isDrawGlow()) {
			this.drawBorderGlow(graphics,6);
		}
		
		super.draw(graphics);
		drawExtended(graphics);
		if (isShowBreakPoints()) {
			if (isInputBreakpointHit()) {
				drawBreakpointHit(graphics, true, Color.RED);
			} else if (isOutputBreakpointHit()) {
				drawBreakpointHit(graphics, false, Color.RED);
			} else {
				this.drawInputBreakpoints(graphics, isInputBreakpoint(), isInputBreakPointToggle(), getBreakpointColor());
				this.drawOutputBreakpoints(graphics,isOutBreakpoint(), isOutputBreakPointToggle(), getBreakpointColor());
			}
		}
		if(isDrawError()) {
			drawError(graphics,Color.RED, 2);
		}
	}
    protected void drawBorderGlowShape(TSEGraphics graphics) {

          TSTransform transform = graphics.getTSTransform();          
          TSConstRect localBounds = this.getOwner().getLocalBounds();
          int width = transform.widthToDevice(localBounds.getWidth());
          int height = transform.heightToDevice(localBounds.getHeight());
          int x = transform.xToDevice(localBounds.getLeft());
          int y = transform.yToDevice(localBounds.getTop());
          graphics.drawPolygon(
        		  new int[]{x,x+(width/2),width+x,x+(width/2)},
        		  new int[]{y+(height/2),y,y+(height/2),y+height},4);

    } 
    
    protected void drawExtended(TSEGraphics graphics) { }
    
	/**
	 * @param graphics
	 * @param inputBreakpointHit
	 */
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
			if (isOutputBreakpointHit()) {
				drawBreakpointHit(graphics, false, color);
			}
		} else {
			
//			drawImage(graphics, RADIO_BRK_HIT_IMAGE, new TSEColor(255, 110, 0), TSEColor.white, outputBreakPointBounds);
//			drawInputBreakpoints(graphics, hasInBreakpoint, inBrkPoinToggle);
			
			drawOutputBreakpoints(graphics, true, false, color);
			drawBreakPointRight(graphics, ownerBounds, transform, color);
			drawInputBreakpoints(graphics, true, isInputBreakPointToggle(), GLOW_OUTER_HIGH_COLOR);
			if (inputBreakpointHit) {
				drawBreakpointHit(graphics, true, color);
			}
		}
	}
	
	private void drawBreakPointLeft(TSEGraphics graphics, TSConstRect ownerBounds, TSTransform transform, Color color) {
		Color oldColor = graphics.getColor();
		int x = transform.xToDevice(ownerBounds.getLeft() + INNER_BREAKPOINT_SIZE );
		int y = transform.yToDevice(ownerBounds.getCenterY() + INNER_BREAKPOINT_SIZE - 3);
		graphics.fillOval(x - 2 + 6, y - DEFAULT_BREAKPOINT_OFFSET, transform.widthToDevice(INNER_BREAKPOINT_SIZE), transform.heightToDevice(INNER_BREAKPOINT_SIZE));
		graphics.setColor(oldColor);
		oldColor = graphics.getColor();
		graphics.setColor(color);
	}
	
	private void drawBreakPointRight(TSEGraphics graphics, TSConstRect ownerBounds, TSTransform transform, Color color) {
		Color oldColor = graphics.getColor();
		int x = transform.xToDevice(ownerBounds.getRight() - INNER_BREAKPOINT_SIZE - 3 );
		int y = transform.yToDevice(ownerBounds.getCenterY() + INNER_BREAKPOINT_SIZE - 3);
		graphics.fillOval(x - 2 - 2, y - DEFAULT_BREAKPOINT_OFFSET, transform.widthToDevice(INNER_BREAKPOINT_SIZE), transform.heightToDevice(INNER_BREAKPOINT_SIZE));
		graphics.setColor(oldColor);
		oldColor = graphics.getColor();
		graphics.setColor(color);
	}

    public void drawInputBreakpoints(TSEGraphics graphics,
				                    boolean inputBrkPnt, 
				                    boolean inBrkPnToggle, 
				                    Color color) {
		Color oldColor = graphics.getColor();
		graphics.setColor(oldColor);
		TSConstRect ownerBounds = this.getOwner().getLocalBounds();
		TSTransform transform = graphics.getTSTransform();	
		graphics.setColor(color);
		setInputBreakPointBounds(new TSConstRect(
	            	ownerBounds.getLeft() + 1 + 2,
	            	ownerBounds.getCenterY() + DEFAULT_BREAKPOINT_SIZE/2,
	            	ownerBounds.getLeft() + 1 + DEFAULT_BREAKPOINT_SIZE + 2,
	            	ownerBounds.getCenterY() - DEFAULT_BREAKPOINT_SIZE/2));
		graphics.drawOval(getInputBreakPointBounds());
		//Draw Border Shadow for the outer one
		drawBorderShadow(graphics, 2, getInputBreakPointBounds(), false);

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
			graphics.fillOval(x - 2 + 6, y - DEFAULT_BREAKPOINT_OFFSET, transform.widthToDevice(INNER_BREAKPOINT_SIZE), transform.heightToDevice(INNER_BREAKPOINT_SIZE));
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
				ownerBounds.getRight() - 1 - DEFAULT_BREAKPOINT_SIZE -1 -1,
				ownerBounds.getCenterY() + DEFAULT_BREAKPOINT_SIZE/2,
				ownerBounds.getRight() - 1 - 1 - 1,
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
			graphics.fillOval(x - 2 - 2, y - DEFAULT_BREAKPOINT_OFFSET, transform.widthToDevice(INNER_BREAKPOINT_SIZE), transform.heightToDevice(INNER_BREAKPOINT_SIZE));
			graphics.setColor(oldColor);
			oldColor = graphics.getColor();
			graphics.setColor(color);
		}
	}
}
