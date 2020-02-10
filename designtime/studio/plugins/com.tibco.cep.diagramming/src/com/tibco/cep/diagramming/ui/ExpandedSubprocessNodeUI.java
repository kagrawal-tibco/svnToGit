package com.tibco.cep.diagramming.ui;


import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Stroke;

import com.tomsawyer.drawing.geometry.shared.TSConstRect;
import com.tomsawyer.drawing.geometry.shared.TSRect;
import com.tomsawyer.drawing.geometry.shared.TSSize;
import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.drawing.geometry.shared.TSDeviceRectangle;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEChildGraphUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEGraphUI;



/**
 * @author ggrigore
 * 
 * This class defines the node UI that is responsible for displaying child graphs.
 */
public class ExpandedSubprocessNodeUI extends TSEChildGraphUI {
	
	private static final long serialVersionUID = 1L;
	private static final int ARC_SIZE = 11;
	public static TSEColor SUB_PROCESS_FILL_COLOR = new TSEColor(255,220,81);
	private Stroke dashBorder = null;
	private boolean isDashBorder = false;
    private final TSSize arcSize = new TSSize(ARC_SIZE, ARC_SIZE);
    protected int borderWidth = 2;
    private boolean drawGlow = false;
	
    
	public void reset()	{
        super.reset();
        this.setBorderDrawn(true);
        this.setBorderColor(this.getFillColor());
        this.setTextColor(TSEColor.black);
        this.setDrawChildGraphMark(false);
    }


	// ---------------------------------------------------------------------
	// Section: drawing
	// ---------------------------------------------------------------------

	/**
	 * This method draws the outline of the object
	 * represented by this UI. It is faster than <code>draw</code>
	 * and can be used when drawing many objects during processor-
	 * intensive interactive operations such as dragging.
	 * @param graphics the <code>TSEGraphics</code> object onto which
	 * the UI is being drawn.
	 */
	@SuppressWarnings("unused")
	public void drawOutline(TSEGraphics graphics) {
		// super.drawOutline(graphics); //FIXME 3.0.2 merge: TS 9.0 integration
		// get the graph UI ...
		TSEGraph childGraph = (TSEGraph) this.getOwnerNode().getChildGraph();
		
		if (childGraph != null)	{
			TSTransform transformFromChild = TSTransform.compose(graphics.getTSTransform(),	childGraph.getClonedTransform());
			TSEGraphics childGraphics =	graphics.deriveGraphics(transformFromChild);
			//childGraph.getUI().drawOutline(childGraphics); //FIXME 3.0.2 merge: TS 9.0 integration
			// get frame bounding rectangle of child graph.
			TSRect frameBounds = (TSRect) childGraph.getLocalFrameBounds();
			TSDeviceRectangle frameDeviceBounds = transformFromChild.boundsToDevice(frameBounds);
			// draw rectangle around transformed frame bounding rectangle.
			graphics.drawRect(
				(int)frameDeviceBounds.getX() - 1,
				(int)frameDeviceBounds.getY() - 1,
				(int)frameDeviceBounds.getWidth() + 1,
				(int)frameDeviceBounds.getHeight() + 1);
		}
	}

	/**
	 * This method draws the object represented by this UI.
	 * @param graphics the <code>TSEGraphics</code> object onto which
	 * the UI is being drawn.
	 * @param respectSelection whether to draw selected graph
	 * objects in their selected state.
	 * @param selectedOnly a flag which indicates that only selected
	 * graph objects are to be drawn.
	 */
	/* (non-Javadoc)
	 * @see com.tomsawyer.graphicaldrawing.ui.simple.TSEChildGraphUI#draw(com.tomsawyer.graphicaldrawing.awt.TSEGraphics, boolean, boolean)
	 */
	public void draw(TSEGraphics graphics, boolean respectSelection, boolean selectedOnly) {
		if (this.getOwnerNode() != null) {
			TSENode owner = this.getOwnerNode();
			TSConstRect ownerBounds = owner.getLocalBounds();
			TSEGraph childGraph = (TSEGraph) owner.getChildGraph();
			
			if (this.drawGlow) {
				this.drawBorderGlow(graphics, 10);
			}
			
			super.draw(graphics, respectSelection, selectedOnly);			
			
			if (childGraph != null)	{
				// get frame bounding rectangle of child graph.
				TSConstRect frameBounds = childGraph.getLocalFrameBounds();
	
				TSTransform transformFromChild = TSTransform.compose(graphics.getTSTransform(), childGraph.getClonedTransform());
				TSEGraphics childGraphics =	graphics.deriveGraphics(transformFromChild);

				// draw the background of the node if necessary
                drawSubprocessChildBackground(graphics, owner, ownerBounds, childGraph, childGraphics, frameBounds, respectSelection, selectedOnly);
			}
		}		
	}
	
	private void drawSubprocessChildBackground(TSEGraphics graphics, 
			                                   TSENode owner, 
			                                   TSConstRect ownerBounds, 
			                                   TSEGraph childGraph,
			                                   TSEGraphics childGraphics,
			                                   TSConstRect frameBounds,
									           boolean respectSelection, 
									           boolean selectedOnly) {
		if ((!selectedOnly || owner.isSelected()) && !this.isTransparent()) {
			graphics.setColor(this.getFillColor());
            // TSSize DEFAULT_ARC_TS_SIZE = new TSSize(0.15 * owner.getLocalWidth(), 0.15 * owner.getLocalHeight());
            
            graphics.fillRoundRect(ownerBounds, this.arcSize);
            graphics.setColor(this.getBorderColor());

			((TSEGraphUI) childGraph.getUI()).drawBackground(
				childGraphics,
				frameBounds,
				true,
				true);
		}
        //draw background for child process graph
        this.drawSubprocessChildBorder(graphics, owner, ownerBounds, childGraphics,
        	frameBounds, respectSelection, selectedOnly);
	}
	
	/**
	 * @param graphics
	 * @param owner
	 * @param ownerBounds
	 * @param childGraphics
	 * @param frameBounds
	 * @param respectSelection
	 * @param selectedOnly
	 */
	private void drawSubprocessChildBorder(TSEGraphics graphics, 
									           TSENode owner, 
									           TSConstRect ownerBounds, 
									           TSEGraphics childGraphics,
									           TSConstRect frameBounds,
										       boolean respectSelection, 
										       boolean selectedOnly) {
		if (!selectedOnly || owner.isSelected()) {
			// draw the border of the node if necessary
			if (this.isBorderDrawn()) {
				// draw the outer border
				this.drawBorder(graphics, ownerBounds, this.getBorderColor());

//				TSTransform transform = graphics.getTSTransform();
//				int outerDevLeft = transform.xToDevice(ownerBounds.getLeft());
//				int outerDevTop = transform.yToDevice(ownerBounds.getTop());
//				int outerDevWidth = transform.widthToDevice(ownerBounds.getWidth()) - 1;
//				int outerDevHeight = transform.heightToDevice(ownerBounds.getHeight()) - 1;
//				graphics.drawRect(outerDevLeft,outerDevTop,outerDevWidth,outerDevHeight);
				
				// draw the inner border
				TSTransform childTransform = childGraphics.getTSTransform();
				int innerDevLeft =
					childTransform.xToDevice(frameBounds.getLeft()) - 1;
				int innerDevTop =
					childTransform.yToDevice(frameBounds.getTop()) - 1;
				int innerDevWidth =
					childTransform.widthToDevice(frameBounds.getWidth()) + 1;
				int innerDevHeight =
					childTransform.heightToDevice(frameBounds.getHeight()) + 1;
				
				childGraphics.drawRect(
					innerDevLeft,
					innerDevTop,
					innerDevWidth,
					innerDevHeight);
			}

			// draw the text of the node if necessary
			if (this.getOwner().getText() != null) {
				this.drawText(graphics);
			}
		}
	}

	/**
	 * This method draws the object represented by this UI.
	 * @param graphics the <code>TSEGraphics</code> object onto which
	 * the UI is being drawn.
	 */
	public void draw(TSEGraphics graphics) {
		this.draw(graphics, true, false);
	}

	/**
	 * This method draws the object represented by this UI in a
	 * selected state.
	 * @param graphics the <code>TSEGraphics</code> object onto which
	 * the UI is being drawn.
	 * @param respectSelection whether to draw selected graph
	 * objects in their selected state.
	 * @param selectedOnly a flag which indicates that only selected
	 * graph objects are to be drawn.
	 */
	/* (non-Javadoc)
	 * @see com.tomsawyer.graphicaldrawing.ui.simple.TSEChildGraphUI#drawSelected(com.tomsawyer.graphicaldrawing.awt.TSEGraphics, boolean, boolean)
	 */
	public void drawSelected(TSEGraphics graphics, boolean respectSelection, boolean selectedOnly) {
		this.draw(graphics, respectSelection, selectedOnly);
		graphics.setColor(this.getSelectedColor());
		this.drawGrapples(graphics);
	}
	
	/* (non-Javadoc)
	 * @see com.tomsawyer.graphicaldrawing.ui.simple.TSEChildGraphUI#drawBorder(com.tomsawyer.graphicaldrawing.awt.TSEGraphics, com.tomsawyer.drawing.geometry.shared.TSConstRect, com.tomsawyer.graphicaldrawing.awt.TSEColor)
	 */
	protected void drawBorder(TSEGraphics graphics, TSConstRect ownerBounds, TSEColor borderColor) {
		graphics.setColor(borderColor);
		if (isDashedBorder()) {
        	Stroke oldStroke = graphics.getStroke();
        	graphics.setStroke(this.getDashGraphicsStroke());
            graphics.drawRoundRect(ownerBounds, this.arcSize);
            graphics.setStroke(oldStroke);
		}
        else {
            graphics.drawRoundRect(ownerBounds, this.arcSize);
        }
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
			// g2.setColor(Color.WHITE);

			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,	pct));
			g2.setStroke(new BasicStroke(i));
			g2.drawRoundRect(this.getOwner().getLocalBounds(), this.arcSize);
		}

		g2.setStroke(oldStroke);
	}
	
	public void setDrawGlow(boolean glow) {
		this.drawGlow = glow;
	}

	public boolean isGlowDrawn() {
		return this.drawGlow;
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
}
