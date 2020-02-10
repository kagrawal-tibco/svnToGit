package com.tibco.cep.diagramming.ui;

import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.util.Iterator;

import com.tomsawyer.drawing.TSDEdge;
import com.tomsawyer.drawing.TSGNode;
import com.tomsawyer.drawing.TSPEdge;
import com.tomsawyer.drawing.TSPNode;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;
import com.tomsawyer.graphicaldrawing.ui.simple.TSECurvedEdgeUI;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEEdgeUI;

/**
 * Author: ggrigore
 * Date: Nov 26, 2008
 *
 * This customized UI class extends the basic functionality of a curved
 * edge UI to draw a curved UML edge UI which has a (filled or hollow)
 * diamond at its source if it's a REFERENCE or CONTAINTMENT type of an edge.
 */
public class UMLCurvedEdgeUI extends TSECurvedEdgeUI {

	private static final long serialVersionUID = 1L;
	public final static int INHERITANCE = 0;
    public final static int REFERENCE = 1;
    public final static int CONTAINMENT = 2;

    private int type;
    
	private transient Shape curvedPath;
	private transient TSTransform curvedPathTransform =	new TSTransform(0, 0, 1, 1);


    public UMLCurvedEdgeUI()
    {
        this(UMLCurvedEdgeUI.INHERITANCE);
    }
    
    public UMLCurvedEdgeUI(int type)
    {
        if (type == UMLCurvedEdgeUI.REFERENCE || type == UMLCurvedEdgeUI.CONTAINMENT)
        {
            this.type = type;
            this.setArrowType(TSEEdgeUI.SOURCE_ARROW);
        }
        else
        {
            this.type = UMLCurvedEdgeUI.INHERITANCE;
        }
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
        this.drawLineWithArrow(graphics,
            fromX,
            fromY,
            toX,
            toY,
            drawSourceArrow,
            drawTargetArrow,
            fillArrows,
            drawArrowOutlines,
            true);
    }


    protected void drawLineWithArrow(TSEGraphics graphics,
                                     double fromX,
                                     double fromY,
                                     double toX,
                                     double toY,
                                     boolean drawSourceArrow,
                                     boolean drawTargetArrow,
                                     boolean fillArrows,
                                     boolean drawArrowOutlines,
                                     boolean drawArrowsOnly)
    {
        // UML:
        if (this.type == UMLCurvedEdgeUI.CONTAINMENT)
        {
            fillArrows = true;
        }
        else if (this.type == UMLCurvedEdgeUI.REFERENCE)
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
            (float) transform.widthToDevice(this.getArrowWidth()) / (float) 2;

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

        if (!drawArrowsOnly)
        {
            // draw the line of the edge
            graphics.drawLine(lineFromX, lineFromY, lineToX, lineToY);
        }
    }


    private float[] createControlPoints(TSTransform transform)
    {
        double[] controlPointsWorld = this.createControlPoints();

        float[] controlPoints = new float[controlPointsWorld.length];

        for (int i = 0; i < controlPoints.length; i += 2)
        {
            controlPoints[i] =
                    (float) transform.xToDevice(controlPointsWorld[i]);
            controlPoints[i + 1] =
                    (float) transform.yToDevice(controlPointsWorld[i + 1]);
        }

        if (this.type == UMLCurvedEdgeUI.REFERENCE ||
            this.type == UMLCurvedEdgeUI.CONTAINMENT)
        {
            this.resetUMLStartPoint(controlPoints, transform);
        }

        return controlPoints;
    }


    /**
     * The trick here is that the starting control point that defines this
     * curve needs to be modified so it starts from the end of the diamond
     * and not from the start of the other tip of the diamond.
     */
    private void resetUMLStartPoint(float[] controlPoints,
        TSTransform transform)
    {
        Iterator<?> pathIter = this.getOwnerEdge().pathIterator();
        TSPEdge path = (TSPEdge) pathIter.next();

        // TSPEdge path = this.getOwnerEdge().getFirstDrawablePEdge();

        TSConstPoint firstClipPoint =
                ((TSDEdge) path.getOwner()).
                        getLocalSourceClippingPoint();
        TSConstPoint targetPoint = path.getLocalTargetPoint();

        double fromX = firstClipPoint.getX();
        double fromY = firstClipPoint.getY();
        double toX = targetPoint.getX();
        double toY = targetPoint.getY();

        float edgeVectorX = (float) ((toX - fromX) * Math.abs(transform.getScaleX()));
        float edgeVectorY = (float) ((toY - fromY) * Math.abs(transform.getScaleY()));
        float edgeLength =
            (float) Math.sqrt((edgeVectorX * edgeVectorX) + (edgeVectorY * edgeVectorY));

        // avoid divide-by-zero.
        if (edgeLength == 0.0f)
        {
            return;
        }
        int lineFromX = transform.xToDevice(fromX);
        int lineFromY = transform.yToDevice(fromY);
        float arrowHeight = transform.heightToDevice(this.getArrowHeight());
        edgeVectorX /= edgeLength;
        edgeVectorY /= edgeLength;

        // calculate the vector from the center of the base to the arrow tip
        float arrowHeightX = arrowHeight * edgeVectorX;
        float arrowHeightY = arrowHeight * edgeVectorY;

        // set up start point for the edge
        lineFromX += (int) (2 * arrowHeightX);
        lineFromY -= (int) (2 * arrowHeightY);

        controlPoints[0] = lineFromX;
        controlPoints[1] = lineFromY;
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    // THE FOLLOWING METHODS ARE EXTENDED FROM TSECurvedEdgeUI ONLY BECAUSE
    // THEY WERE CALLING THE PRIVATE METHODS createControlPoints(*) WHICH
    // HAD TO BE OVERWRITTEN BUT WHICH WERE PRIVATE IN THE BASE CLASS
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    /**
	 * This method returns the control points used in drawing the curved
	 * edges in world coordinates.
	 */
	private double[] createControlPoints()
	{
		TSPEdge firstPath = this.getOwnerEdge().getFirstDrawablePEdge();
		TSPEdge lastPath = this.getOwnerEdge().getLastDrawablePEdge();

		int numberOfPoints = this.getOwnerEdge().numberOfPathEdges() + 1;

		double[] controlPoints = new double[2 * numberOfPoints];

		TSPEdge path;
		boolean startDraw = false;

		int i = 0;

		for (Iterator<?> pathIter = this.getOwnerEdge().pathIterator();
			pathIter.hasNext();)
		{
			path = (TSPEdge) pathIter.next();

			// check whether this is the first edge on the path

			boolean isFirst = (path == firstPath);
			boolean isLast = (path == lastPath);

			if (isFirst)
			{
				// start drawing from the first one
				startDraw = true;

				// we add the first X and Y coordinates to the control
				// point list

                TSConstPoint firstClipPoint =
					((TSDEdge) path.getOwner()).
						getLocalSourceClippingPoint();
				controlPoints[i] = firstClipPoint.getX();
				controlPoints[i + 1] = firstClipPoint.getY();

				i += 2;
			}

			// add the bend point of this path edge to our list of
			// control points

			if (startDraw)
			{
				if (isLast)
				{
					TSConstPoint lastClipPoint =
						((TSDEdge) path.getOwner()).
							getLocalTargetClippingPoint();
					controlPoints[i] = lastClipPoint.getX();
					controlPoints[i + 1] = lastClipPoint.getY();
				}
				else
				{
					TSConstPoint targetPoint =
						path.getLocalTargetPoint();
					controlPoints[i] = targetPoint.getX();
					controlPoints[i + 1] = targetPoint.getY();
				}

				i += 2;
			}

			if (isLast)
			{
				break;
			}
		}

		// because only now we know which "curved path edges"
		// are drawable (for performance reasons), we copy over to
		// a new array only the drawable points.

        int drawablePoints = i;
		double [] drawableControlPoints = new double[drawablePoints];

		for (int j = 0; j < drawablePoints; j++)
		{
			drawableControlPoints[j] = controlPoints[j];
		}

		return drawableControlPoints;
	}


    public void drawPath(TSEGraphics graphics,
        boolean fillArrows,
        boolean drawArrowOutlines,
        boolean arrowMovable)
    {
        // The following code illustrates how to traverse the edge
        // using a standard iterator. It is slightly inefficient since
        // it recomputes source and target device coordinates twice.
        // For connected paths, which are the only ones we are
        // interested in here, the end of one path segment must be
        // equal to the beginning of another. So one could save the
        // last set of target coordinates and assign them to the source
        // coodinates at the beginning of the loop.

        TSPEdge firstPath;
        TSPEdge lastPath;
        firstPath = this.getOwnerEdge().getFirstDrawablePEdge();
        lastPath = this.getOwnerEdge().getLastDrawablePEdge();

        TSPEdge path = null;

        Object oldHint = null;

        if (this.isAntiAliasingEnabled())
        {
            // set the anti-aliasing hint
            oldHint = graphics.getRenderingHint(
                RenderingHints.KEY_ANTIALIASING);

            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        }

        if (firstPath != null && lastPath != null)
        {
            TSTransform transform = graphics.getTSTransform();

            TSPEdge sourceArrowPath = firstPath;
            TSPEdge targetArrowPath = lastPath;

            // if we have bend points...

            if (!this.getOwnerEdge().isStraight())
            {
                if (arrowMovable)
                {
                    TSGNode endNode;

                    // if the first path edge is too short, move the arrow
                    // to the second path edge.

                    if (firstPath.length() <= this.getArrowHeight())
                    {
                        endNode = (TSGNode) firstPath.getTargetNode();

                        if (endNode.isPathNode())
                        {
                            sourceArrowPath = ((TSPNode) endNode).getOutEdge();
                        }
                    }

                    // if the last path edge is too short, move the arrow
                    // to the second path edge to the last.

                    if (lastPath.length() <= this.getArrowHeight())
                    {
                        endNode = (TSGNode) lastPath.getSourceNode();

                        if (endNode.isPathNode())
                        {
                            targetArrowPath = ((TSPNode) endNode).getInEdge();
                        }
                    }
                }

                float[] controlPoints =
                    this.createControlPoints(transform);

                // now that we have a list of all the points, draw the curve
                // with all of its options

                this.drawCurve(graphics,
                    controlPoints,
                    fillArrows,
                    drawArrowOutlines,
                    sourceArrowPath,
                    targetArrowPath);
            }

            // it means we have a straight edge (the very first path), so
            // we simply draw the path segment as a straight line.

            else
            {
                path = this.getOwnerEdge().getSourceEdge();

                // could have called super.drawPathEdge() but need to make
                // it protected or public but unexposed

                this.drawPathEdgeOrArrows(graphics,
                    fillArrows,
                    drawArrowOutlines,
                    path,
                    true,
                    true,
                    sourceArrowPath,
                    targetArrowPath,
                    false);
            }
        }

        // restore the anti-aliasing setting

        if (this.isAntiAliasingEnabled())
        {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                oldHint);
        }
    }

    /**
     * This method draws the Bezier curve for this edge.
     * @param graphics the <code>TSEGraphics</code> object onto which
     * the curve is being drawn.
     * @param controlPoints array of control point device coordinates.
     * @param fillArrows whether or not to fill the arrowheads.
     * @param drawArrowOutlines whether or not to draw arrow outlines.
     */
    private void drawCurve(TSEGraphics graphics,
        float[] controlPoints,
        boolean fillArrows,
        boolean drawArrowOutlines,
        TSPEdge firstDrawablePath,
        TSPEdge lastDrawablePath)
    {
        // generate the path
        this.curvedPath = this.createCurve(controlPoints);

        this.curvedPathTransform.copy(graphics.getTSTransform());

        // draw the curved path
        graphics.draw(this.curvedPath);

        // draw the arrows as well if there are any requested.

        if (firstDrawablePath != null &&
            firstDrawablePath == lastDrawablePath)
        {
                TSPEdge firstPath =
                    this.getOwnerEdge().getFirstDrawablePEdge();

                this.drawPathEdgeOrArrows(graphics,
                    fillArrows,
                    drawArrowOutlines,
                    firstPath,
                    true,
                    true,
                    firstDrawablePath,
                    lastDrawablePath,
                    true);
        }
        else
        {
            // this code may be called when we don't have an edge yet
            // (during build edge tool), so we can't assume there is an owner
            // edge, and thus the checks below.

            TSPEdge firstPath = null;
            TSPEdge lastPath = null;

            if (this.getOwnerEdge() != null)
            {
                firstPath = this.getOwnerEdge().getFirstDrawablePEdge();
                lastPath = this.getOwnerEdge().getLastDrawablePEdge();
            }

            if (firstPath != null)
            {
                this.drawPathEdgeOrArrows(graphics,
                    fillArrows,
                    drawArrowOutlines,
                    firstPath,
                    true,
                    false,
                    firstDrawablePath,
                    lastDrawablePath,
                    true);
            }

            if (lastPath != null)
            {
                this.drawPathEdgeOrArrows(graphics,
                    fillArrows,
                    drawArrowOutlines,
                    lastPath,
                    false,
                    true,
                    firstDrawablePath,
                    lastDrawablePath,
                    true);
            }
        }
    }


    private void drawPathEdgeOrArrows(TSEGraphics graphics,
        boolean fillArrows,
        boolean drawArrowOutlines,
        TSPEdge path,
        boolean isFirst,
        boolean isLast,
        TSPEdge sourceArrowPath,
        TSPEdge targetArrowPath,
        boolean drawArrowsOnly)
    {
        TSConstPoint sourcePoint;
        TSConstPoint targetPoint;

        // check whether it needs arrows

        boolean needsSourceArrow =
            (path == sourceArrowPath)
                && (this.getArrowType() & TSEEdgeUI.SOURCE_ARROW) != 0;
        boolean needsTargetArrow =
            (path == targetArrowPath)
                && (this.getArrowType() & TSEEdgeUI.TARGET_ARROW) != 0;

        if (isFirst)
        {
            // we need to clip for the first one
            sourcePoint = this.getOwnerEdge().getLocalSourceClippingPoint();
        }
        else
        {
            sourcePoint = path.getLocalSourcePoint();
        }

        if (isLast)
        {
            // we need to clip for the last one
            targetPoint = this.getOwnerEdge().getLocalTargetClippingPoint();
        }
        else
        {
            targetPoint = path.getLocalTargetPoint();
        }

        // if the first path edge is too short, move the arrow
        // to the second path edge.

        TSPEdge sArrowPath = null;
        TSPEdge tArrowPath = null;
        TSPEdge firstPath = this.getOwnerEdge().getFirstDrawablePEdge();
        TSPEdge lastPath = this.getOwnerEdge().getLastDrawablePEdge();

        if (needsSourceArrow &&
            firstPath.length() <= this.getArrowHeight())
        {
            TSGNode endNode = (TSGNode) firstPath.getTargetNode();

            if (endNode.isPathNode())
            {
                sArrowPath = ((TSPNode) endNode).getOutEdge();
            }
        }

        if (needsTargetArrow &&
            lastPath.length() <= this.getArrowHeight())
        {
            TSGNode endNode = (TSGNode) lastPath.getSourceNode();

            if (endNode.isPathNode())
            {
                tArrowPath = ((TSPNode) endNode).getInEdge();
            }
        }

        if (sArrowPath != null)
        {
            sourcePoint = sArrowPath.getLocalSourcePoint();

            // we need to reset the targetPoint as well to move it to the
            // same path edge

            targetPoint = sArrowPath.getLocalTargetPoint();
        }

        if (tArrowPath != null)
        {
            targetPoint = tArrowPath.getLocalTargetPoint();

            // we need to reset the sourcePoint as well to move it to
            // the previous path

            sourcePoint = tArrowPath.getLocalSourcePoint();
        }

        // start drawing from the first one

        // if the edge does not need any arrows, just draw a
        // simple line; otherwise draw the edge with an
        // arrow

        if (needsSourceArrow || needsTargetArrow)
        {
            this.drawLineWithArrow(graphics,
                sourcePoint.getX(),
                sourcePoint.getY(),
                targetPoint.getX(),
                targetPoint.getY(),
                needsSourceArrow,
                needsTargetArrow,
                fillArrows,
                drawArrowOutlines,
                drawArrowsOnly);
        }
        else if (!drawArrowsOnly)
        {
            graphics.drawLine(sourcePoint, targetPoint);
        }
    }

	public Shape getCurve(TSTransform transform)
	{
		if (!transform.equals(this.curvedPathTransform))
		{
			this.curvedPath =
				this.createCurve(
					this.createControlPoints(transform));

			this.curvedPathTransform.copy(transform);
		}

		return this.curvedPath;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
