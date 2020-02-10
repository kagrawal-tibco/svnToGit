package com.tibco.cep.diagramming.ui;

import java.awt.Polygon;

import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEEdgeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEPolylineEdgeUI;

/**
 * Author: ggrigore
 * Date: Nov 26, 2008
 *
 *
 * This customized UI class extends the basic functionality of a polyline
 * edge UI to draw a UML edge UI which has a (filled or hollow) diamond at
 * its source if it's a REFERENCE or CONTAINTMENT type of an edge.
 */
public class UMLEdgeUI extends TSEPolylineEdgeUI
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static int INHERITANCE = 0;
    public final static int REFERENCE = 1;
    public final static int CONTAINMENT = 2;

    private int type;

    public UMLEdgeUI()
    {
        this(UMLEdgeUI.INHERITANCE);
    }

    public UMLEdgeUI(int type)
    {
        if (type == UMLEdgeUI.REFERENCE || type == UMLEdgeUI.CONTAINMENT)
        {
            this.type = type;
            this.setArrowType(TSEEdgeUI.SOURCE_ARROW);
        }
        else
        {
            this.type = UMLEdgeUI.INHERITANCE;
        }
    }

	/**
	 * This method draws a line with an arrow on the end.
	 * @param graphics the <code>TSEGraphics</code> object onto which
	 * the edge is being drawn.
	 * @param fromX the source point X coordinate
	 * @param fromY the source point Y coordinate
	 * @param toX the target point X coordinate
	 * @param toY the target point Y coordinate
	 * @param drawSourceArrow whether or not to draw an arrowhead at the
	 *		source point
	 * @param drawTargetArrow whether or not to draw an arrowhead at the
	 *		target point
	 * @param fillArrows whether or not to fill the arrowheads
	 * @param drawArrowOutlines whether or not to draw an outline around
	 *		the arrowheads
	 */
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
        // UML:
        if (this.type == UMLEdgeUI.CONTAINMENT)
        {
            fillArrows = true;
        }
        else if (this.type == UMLEdgeUI.REFERENCE)
        {
            fillArrows = false;
        }

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

		// draw source arrow, but figure out the four points making up
        // the diamond.

        if (drawSourceArrow)
		{
			sourceArrow = new Polygon();

			// 1
            sourceArrow.addPoint(lineFromX, lineFromY);

            // 3
            sourceArrow.addPoint(
				lineFromX + (int) (arrowHeightX + halfBaseVectorX),
				lineFromY - (int) (arrowHeightY - halfBaseVectorY));


            // 4 UML
            sourceArrow.addPoint(
                lineFromX + (int) (2 * arrowHeightX),
                lineFromY - (int) (2 * arrowHeightY));

            // 2
            sourceArrow.addPoint(
                lineFromX + (int) (arrowHeightX - halfBaseVectorX),
                lineFromY - (int) (arrowHeightY + halfBaseVectorY));


            // set up start point for the edge
			lineFromX += (int) (2 * arrowHeightX);
			lineFromY -= (int) (2 * arrowHeightY);
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

			lineToX -= (int) (arrowHeightX);
			lineToY += (int) (arrowHeightY);
		}

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
