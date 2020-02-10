package com.tibco.cep.bpmn.ui.graph.model.factory.ui;

import java.util.List;

import com.tibco.cep.diagramming.ui.CurvedEdgeUI;
import com.tomsawyer.drawing.TSPNode;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.awt.TSEGraphics;

/**
 * 
 * @author ggrigore
 *
 */
public class BPMNCurvedEdgeUI extends CurvedEdgeUI {
	
	private static final long serialVersionUID = 3888857953210334171L;
	
	private int MARKER_INTERSECTION_OFFSET = 10;
	private int MARKER_LENGTH = 10;
	private int MARKER_ANGLE = 45;

	private boolean isDefault;
	
	
	public BPMNCurvedEdgeUI() {
		this.isDefault = false;
	}

	public BPMNCurvedEdgeUI(boolean drawHollowArrows) {
		super(drawHollowArrows);
		this.isDefault = false;
	}
	
	public void draw(TSEGraphics graphics) {
        super.draw(graphics);

        if (this.isDefault) {
        	this.drawDefaultMarker(graphics);
        }       
	}
	
	protected void drawDefaultMarker(TSEGraphics graphics) {
        TSEEdge edge = this.getOwnerEdge();
        
        if (edge == null) {
        	return;
        }
        
        TSTransform transform = graphics.getTSTransform();
        
        TSConstPoint start = edge.getLocalSourceClippingPoint();
        TSConstPoint end = edge.getLocalTargetClippingPoint();
        
        @SuppressWarnings("unchecked")
		List<TSPNode> pNodes = edge.pathNodes();
        if (pNodes != null && pNodes.size() != 0) {
        	end = ((TSPNode) pNodes.get(0)).getLocalCenter();
        }

        double xDiff = end.getX() - start.getX();
        double yDiff = end.getY() - start.getY();

        // handle if the first path edge is very small, don't draw default marker
        if (Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2)) < MARKER_INTERSECTION_OFFSET) {
        	// TODO: do something better later
        	return;
        }

        double edgeAngle = Math.atan(yDiff / xDiff);
        double markerAngle = edgeAngle + Math.toRadians(MARKER_ANGLE);
        
        double markerIntersectionX;
        double markerIntersectionY;
        
        if (xDiff < 0) {
        	markerIntersectionX = start.getX() - Math.cos(edgeAngle) * MARKER_INTERSECTION_OFFSET;
        	markerIntersectionY = start.getY() - Math.sin(edgeAngle) * MARKER_INTERSECTION_OFFSET;
        }
        else {
            markerIntersectionX = start.getX() + Math.cos(edgeAngle) * MARKER_INTERSECTION_OFFSET;
            markerIntersectionY = start.getY() + Math.sin(edgeAngle) * MARKER_INTERSECTION_OFFSET;        	
        }
        
        // for performance we store these in a temp variable
        double cosTemp = Math.cos(markerAngle) * MARKER_LENGTH / 2.0;
        double sinTemp = Math.sin(markerAngle) * MARKER_LENGTH / 2.0;
               
        graphics.drawLine(
        	transform.xToDevice(markerIntersectionX - cosTemp),  // markerStartX
        	transform.yToDevice(markerIntersectionY - sinTemp),  // markerStartY
        	transform.xToDevice(markerIntersectionX + cosTemp),  // markerEndX
        	transform.yToDevice(markerIntersectionY + sinTemp)); // markerEndY
	}
	
	public void setAsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	public boolean isDefault() {
		return this.isDefault;
	}

}
