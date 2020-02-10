package com.tibco.cep.diagramming.ui;

import java.awt.Polygon;

import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEPolylineEdgeUI;

/**
 * Author: ggrigore
 * Date: Nov 26, 2008
 */
public class PolylineEdgeUI extends TSEPolylineEdgeUI
{
  
	/**
	 * 
	 */
	private static final long serialVersionUID = -6287769167458074596L;

	private boolean drawHollowArrows;

    public PolylineEdgeUI()
    {
        this.drawHollowArrows = false;
    }

    public PolylineEdgeUI(boolean drawHollowArrows)
    {
        this.drawHollowArrows = drawHollowArrows;
    }

    public void drawLineWithArrow(TSEGraphics graphics,
                                  double fromX,
                                  double fromY,
                                  double toX,
                                  double toY,
                                  boolean drawSourceArrow,
                                  boolean drawTargetArrow,
                                  boolean fillArrows,
                                  boolean drawArrowOutlines)
    {
        if (drawHollowArrows)
        {
            super.drawLineWithArrow(graphics,fromX,fromY,toX,toY,
                drawSourceArrow,drawTargetArrow,false,drawArrowOutlines);
        }
        else
        {
            this.internalDrawLineWithArrow(graphics,fromX,fromY,toX,toY,
                drawSourceArrow,drawTargetArrow,false, false);
        }

    }

    private void internalDrawLineWithArrow(TSEGraphics graphics,
                                  double fromX,
                                  double fromY,
                                  double toX,
                                  double toY,
                                  boolean drawSourceArrow,
                                  boolean drawTargetArrow,
                                  boolean fillArrows,
                                  boolean drawArrowOutlines)
    {
        TSTransform transform = graphics.getTSTransform();

        float edgeVectorX = (float) ((toX - fromX) * Math.abs(transform.getScaleX()));
        float edgeVectorY = (float) ((toY - fromY) * Math.abs(transform.getScaleY()));
        float edgeLength =
            (float) Math.sqrt(
                (edgeVectorX * edgeVectorX) + (edgeVectorY * edgeVectorY));

        // avoid divide-by-zero.

        if (edgeLength == 0.0f)
        {
            return;
        }

        int lineFromX = transform.xToDevice(fromX);
        int lineFromY = transform.yToDevice(fromY);
        int lineToX = transform.xToDevice(toX);
        int lineToY = transform.yToDevice(toY);

        float halfArrowWidth =
            transform.widthToDevice(this.getArrowWidth()) / 2;

        if (halfArrowWidth < 0.5f)
        {
            graphics.drawLine(lineFromX, lineFromY, lineToX, lineToY);
            return;
        }

        float arrowHeight = transform.heightToDevice(this.getArrowHeight());

        // normalize the edge vector components.

        edgeVectorX /= edgeLength;
        edgeVectorY /= edgeLength;

        // calculate the vector from the center of the base to
        // the arrow tip

        float arrowHeightX = arrowHeight * edgeVectorX;
        float arrowHeightY = arrowHeight * edgeVectorY;

        // calculate the vector from the base center point to
        // the edge of the base

        float halfBaseVectorX = halfArrowWidth * edgeVectorY;
        float halfBaseVectorY = halfArrowWidth * edgeVectorX;

        Polygon sourceArrow = null;
        Polygon targetArrow = null;

        // draw source arrow

        if (drawSourceArrow)
        {
            sourceArrow = new Polygon();

            sourceArrow.addPoint(lineFromX, lineFromY);

            sourceArrow.addPoint(
                lineFromX + (int) (arrowHeightX - halfBaseVectorX),
                lineFromY - (int) (arrowHeightY + halfBaseVectorY));

            sourceArrow.addPoint(
                lineFromX + (int) (arrowHeightX + halfBaseVectorX),
                lineFromY - (int) (arrowHeightY - halfBaseVectorY));

            // set up start point for the edge

            lineFromX += (int) (arrowHeightX);
            lineFromY -= (int) (arrowHeightY);
        }

        // draw target arrow

        if (drawTargetArrow)
        {
            targetArrow = new Polygon();

            targetArrow.addPoint(lineToX, lineToY);

            targetArrow.addPoint(
                lineToX - (int) (arrowHeightX - halfBaseVectorX),
                lineToY + (int) (arrowHeightY + halfBaseVectorY));

            targetArrow.addPoint(
                lineToX - (int) (arrowHeightX + halfBaseVectorX),
                lineToY + (int) (arrowHeightY - halfBaseVectorY));

            // set up end point for the edge

            //GG lineToX -= (int) (arrowHeightX);
            //GG lineToY += (int) (arrowHeightY);
        }

        graphics.drawLine(lineToX, lineToY,
                lineToX - (int) (arrowHeightX - halfBaseVectorX),
                lineToY + (int) (arrowHeightY + halfBaseVectorY));

        graphics.drawLine(lineToX, lineToY,
                lineToX - (int) (arrowHeightX + halfBaseVectorX),
                lineToY + (int) (arrowHeightY - halfBaseVectorY));

        if (fillArrows && (halfArrowWidth > 1.5f))
        {
            if (sourceArrow != null)
            {
                graphics.fillPolygon(sourceArrow);
            }

            if (targetArrow != null)
            {
                graphics.fillPolygon(targetArrow);
            }
        }

        if (drawArrowOutlines)
        {
            if (sourceArrow != null)
            {
                graphics.drawPolygon(sourceArrow);
            }

            if (targetArrow != null)
            {
                graphics.drawPolygon(targetArrow);
            }
        }

        // draw the line of the edge
        graphics.drawLine(lineFromX, lineFromY, lineToX, lineToY);
    }
}
