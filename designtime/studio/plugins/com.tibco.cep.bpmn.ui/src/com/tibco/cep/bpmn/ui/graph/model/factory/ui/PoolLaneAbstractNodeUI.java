package com.tibco.cep.bpmn.ui.graph.model.factory.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import com.tibco.cep.diagramming.ui.ChildGraphNodeUI;
import com.tomsawyer.drawing.TSDGraph;
import com.tomsawyer.drawing.TSGraphTailor;
import com.tomsawyer.drawing.geometry.shared.TSConstRect;
import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.drawing.geometry.shared.TSDeviceRectangle;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;

/**
 * 
 * @author ggrigore
 *
 */
abstract public class PoolLaneAbstractNodeUI extends ChildGraphNodeUI {

    protected static int MARK_WIDTH = 9;
    protected static int MARK_OFFSET2 = 5;
	private static final long serialVersionUID = 999L;
	
	abstract public boolean isLane();
	
	protected void updateTextSize()	{
		super.updateTextSize();
		if ((this.getOwnerNode() != null) && (this.getOwnerNode().isExpanded())) {
			TSDGraph childGraph = (TSDGraph) this.getOwnerNode().getChildGraph();
			TSGraphTailor tailor = childGraph.getTailor();
			tailor.setTopNestedViewSpacing(5.0);
			tailor.setBottomNestedViewSpacing(5.0);
		}
	}   
    
	public void drawChildGraphMark(TSEGraphics graphics) {
		Color oldColor = graphics.getColor();		
		TSConstRect ownerBounds = this.getOwner().getLocalBounds();		
        graphics.setColor(ChildGraphNodeUI.MARK_COLOR);
        TSConstRect entireMarkBounds = new TSConstRect(
            	ownerBounds.getLeft() + MARK_OFFSET,
            	ownerBounds.getTop() - MARK_OFFSET,
            	ownerBounds.getLeft() + MARK_OFFSET + MARK_WIDTH,
            	ownerBounds.getTop() - MARK_OFFSET - MARK_WIDTH);
        graphics.drawRect(entireMarkBounds);

        TSConstRect bottomRectangleBounds = new TSConstRect(
        	ownerBounds.getLeft() + MARK_OFFSET,
        	ownerBounds.getTop() - MARK_OFFSET - MARK_WIDTH*2/3,
        	ownerBounds.getLeft() + MARK_OFFSET + MARK_WIDTH,
        	ownerBounds.getTop() - MARK_OFFSET - MARK_WIDTH);
        graphics.fillRect(bottomRectangleBounds);		
        graphics.setColor(oldColor);
	}    
    
	public double getWidth() {
		return (this.getRight() - this.getLeft()) - 15;
	}
	
	public void drawText(TSEGraphics graphics) {
		TSTransform transform = graphics.getTSTransform();

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
            int top =  transform.yToDevice(ownerBounds.getTop()) + fm.getAscent() + 2;
            int left = transform.xToDevice(ownerBounds.getLeft()) + 
            	ascent + fm.getHeight()/2;
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

			graphics.setColor(TSEColor.black);
       		
       		// Create a rotation transformation for the font.
       	    AffineTransform fontAT = new AffineTransform();
       	    // get the current font
       	    Font origFont = graphics.getFont();
       	    // derive a new font using a rotation transform
       	    fontAT.rotate(Math.PI * 1.5); // "/2" for top to bottom
       	    Font theDerivedFont = origFont.deriveFont(fontAT);
       	    // set the derived font in the Graphics2D context
       	    graphics.setFont(theDerivedFont);
       	    // Render a string using the derived font
       	    // double stringWidth = fm.getStringBounds(nodeText, graphics).getWidth();
       	    double parentNodeHeight = transform.heightToDevice(ownerBounds.getHeight());
       	    // TODO: cut off is length is too long!
       	    double deviceTextWidth = fm.getStringBounds(nodeText, graphics).getWidth();
       	    graphics.drawString(nodeText, (int)left, (int)(top+parentNodeHeight/2 + deviceTextWidth/2 - 5));
       	    // put the original font back
       	    graphics.setFont(origFont);
       		
       		// TODO:
       	    // above I'm only handling single line labels that are not too long
       	    // must be able to either cut off text or wrap around automatically...
       	    // if length if text > height of lane, cut it off or wrap it around, like
       	    // it is done below
            
			// Parse text to find newline characters and draw text respectively.
//			int lineStart = 0;
//			int textLength = nodeText.length();
//			
//			while (lineStart < textLength) {
//				int lineEnd = nodeText.indexOf('\n', lineStart);
//
//				if (lineEnd == -1) {
//					lineEnd = textLength;
//				}
//
//				String lineString = nodeText.substring(lineStart, lineEnd);
//                if (lineString.length() > 0) {
//            		graphics.setColor(new TSEColor(Color.BLACK));
//            		
//            		graphics.rotate( - Math.PI / 2 );  // <<<<<<<<<<<<<<<<<<<<<<<<<<< 
//            		// graphics.translate( - c.getHeight(), 0 );
//            		graphics.drawString(lineString, left, top);
//					// END CHANGE
//				}
//				lineStart = lineEnd + 1;
//                top += fm.getHeight() + 2;
//            }

            top =  transform.yToDevice(ownerBounds.getTop()) ;
            left = transform.xToDevice(ownerBounds.getRight()-20) ;

            if (this.isTextAntiAliasingEnabled() &&
            	deviceTextHeight <= this.getTextAntiAliasingThreshold()) {
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,oldAntiAliasingHint);
			}
            
			// now restore the old clipping
			graphics.setClip(oldClip);
		}
	}	
	
	
    protected void drawOtherBadges(TSEGraphics graphics) {
    	/*
    	 * commenting below, 1/07/2011, Manish
    	 * 
    	 * we are not going to support multiple lanes/pools in BPMN UI for now
    	 * so, '+' mark on pool is not required. Un comment following when we start supporting
    	 * multiple pool/lanes
    	 */
//		Color oldColor = graphics.getColor();		
//		TSConstRect ownerBounds = this.getOwner().getLocalBounds();		
//        graphics.setColor(ChildGraphNodeUI.MARK_COLOR);
//        TSConstRect entireBadgeBounds = new TSConstRect(
//            	ownerBounds.getLeft() + MARK_OFFSET,
//            	ownerBounds.getTop() - 3*MARK_OFFSET - MARK_WIDTH,
//            	ownerBounds.getLeft() + MARK_OFFSET + MARK_WIDTH,
//            	ownerBounds.getTop() - 3*MARK_OFFSET - 2*MARK_WIDTH);
//        super.badgeBounds = entireBadgeBounds;
//        
//        int lineOffset = 2;
//        
//		BasicStroke oldStroke = (BasicStroke) graphics.getStroke();
//		Stroke newStroke = new BasicStroke(
//			oldStroke.getLineWidth() * 1,
//			oldStroke.getEndCap(),
//			oldStroke.getLineJoin(),
//			oldStroke.getMiterLimit(),
//			oldStroke.getDashArray(),
//			oldStroke.getDashPhase());
//		graphics.setStroke(newStroke);
//		
//		// draw "+" sign if a pool so users can add lanes,
//		// draw a "X" to delete current lane if this is a lane
//        if (isLane()) {
//	        TSPoint start = new TSPoint(entireBadgeBounds.getLeft() + lineOffset, 
//	        		entireBadgeBounds.getTop() - lineOffset);
//	        TSPoint end = new TSPoint(entireBadgeBounds.getRight() - lineOffset,
//	        		entireBadgeBounds.getBottom() + lineOffset);
//	        graphics.drawLine(start, end);
//	        
//	        start.setX(entireBadgeBounds.getRight() - lineOffset);
//	        end.setX(entireBadgeBounds.getLeft() + lineOffset);
//	        graphics.drawLine(start, end);
//        }
//        else {
//	        TSPoint start = new TSPoint(entireBadgeBounds.getCenterX(), 
//	        		entireBadgeBounds.getTop() - lineOffset);
//	        TSPoint end = new TSPoint(entireBadgeBounds.getCenterX(),
//	        		entireBadgeBounds.getBottom() + lineOffset);
//	        graphics.drawLine(start, end);
//	        
//	        start.setX(entireBadgeBounds.getLeft() + lineOffset);
//	        start.setY(entireBadgeBounds.getCenterY());
//	        end.setX(entireBadgeBounds.getRight() - lineOffset);
//	        end.setY(start.getY());
//	        graphics.drawLine(start, end);        	
//        }
//        graphics.setStroke(oldStroke);
//        
//        graphics.drawRect(entireBadgeBounds);
//	
//        graphics.setColor(oldColor);
    }
}
