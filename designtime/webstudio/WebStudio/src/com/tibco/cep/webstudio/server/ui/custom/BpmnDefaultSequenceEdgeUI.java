package com.tibco.cep.webstudio.server.ui.custom;

import java.awt.Color;
import java.util.List;

import com.tomsawyer.drawing.TSPNode;
import com.tomsawyer.drawing.geometry.shared.TSConstPoint;
import com.tomsawyer.drawing.geometry.shared.TSTransform;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSConditionalUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSCurvedPathUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSGroupUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSPathUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSStraightPathUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.renderer.graphics2d.TSUIHierarchyGraphics2DRenderer;
import com.tomsawyer.graphicaldrawing.ui.composite.renderer.shared.TSUIRenderer;
import com.tomsawyer.graphicaldrawing.ui.composite.style.TSUIStyle;
import com.tomsawyer.graphicaldrawing.ui.composite.style.shared.TSUIStyleConstants;
import com.tomsawyer.graphicaldrawing.ui.predefined.edge.TSMultiArrows;
import com.tomsawyer.graphicaldrawing.ui.predefined.edge.basic.TSBasicEdgeUI;

/**
 * This class creates the Activity Diagram - Node UIs.
 */
public class BpmnDefaultSequenceEdgeUI extends TSBasicEdgeUI {
	private int MARKER_INTERSECTION_OFFSET = 5;
	private int MARKER_LENGTH = 5;
	private int MARKER_ANGLE = 45;

	/**
	 * 
	 */
	private static final long serialVersionUID = 4225654590468540240L;

	public BpmnDefaultSequenceEdgeUI(boolean flag, boolean flag1, boolean flag2) {
		super(flag, flag1, flag2);
		this.isCurved = flag;
		this.hasSourceArrow = flag1;
		this.hasTargetArrow = flag2;
		init();
	}

	protected void init() {
		TSUIStyle tsuistyle = new TSUIStyle();
		TSGroupUIElement tsgroupuielement = new TSGroupUIElement("root");
		addPathElements(tsgroupuielement, tsuistyle);
		setRootElement(tsgroupuielement);
		setStyle(tsuistyle);
	}

	private void addPathElements(TSGroupUIElement tsgroupuielement,
			TSUIStyle tsuistyle) {

		TSStraightPathUIElement path=new TSCurvedPathUIElement("path");
		TSStraightPathUIElement selectedPath =new TSCurvedPathUIElement("selectedPath");
		
		tsgroupuielement.addElement(((TSUIElement) (path)));
		TSUIElement tsuielement = new BpmnTargetArrowUI("path",
				new TSEColor(0, 0, 0)).getElementTree(tsuistyle);

		tsgroupuielement.addElement(tsuielement);

		TSConditionalUIElement tsconditionaluielement3 = new TSConditionalUIElement(
				"ifSelected", "$drawSelected()");

		TSGroupUIElement tsgroupuielement1 = new TSGroupUIElement(
				"selectedGroup");

		tsconditionaluielement3.setIfElement(tsgroupuielement1);

		TSConditionalUIElement tsconditionaluielement4 = new TSConditionalUIElement(
				"ifSingleSelected", "$drawSingleSelected()");

		TSUIElement selectArrow = new BpmnTargetArrowUI("selectedPath",
				new TSEColor(0, 204, 204)).getElementTree(tsuistyle);
		
		tsgroupuielement1.addElement(((TSUIElement) (selectArrow)));
		tsgroupuielement1.addElement(((TSUIElement) (selectedPath)));
		tsgroupuielement1.addElement(tsconditionaluielement4);

		tsgroupuielement.addElement(tsconditionaluielement3);

		tsuistyle.setProperty(((TSUIElement) (path)),
				TSUIStyleConstants.LINE_COLOR, "<Color>");
		tsuistyle.setProperty(((TSUIElement) (path)),
				TSUIStyleConstants.LINE_WIDTH, 0.5);
		tsuistyle.setProperty(((TSUIElement) (path)),
				TSUIStyleConstants.LINE_STYLE, "<Edge_Style>");
		tsuistyle.setProperty(((TSUIElement) (path)),
				"DASHED_LINE_ANIMATON_SPEED", "<Flow_Speed>");

		((TSPathUIElement) (path))
				.setSourceDistance("$if($numberEquals(<Source_Head>, 0), 0, $if($and($numberLessThan($sourceEdgeLength(), $add(<Source_Arrow_Height>, <Thickness>)),$sourceBendSharp()),0, 2))");
		((TSPathUIElement) (path))
				.setTargetDistance("$if($numberEquals(<Target_Head>, 0), 0, $if($and($numberLessThan($targetEdgeLength(), $add(<Target_Arrow_Height>, <Thickness>)), $targetBendSharp()),0, 1))");

		tsuistyle.setProperty(tsgroupuielement, TSUIStyleConstants.LINE_COLOR,
				"<Color>");
		tsuistyle.setProperty(tsgroupuielement, TSUIStyleConstants.LINE_WIDTH,
				0.5);
		tsuistyle.setProperty(tsgroupuielement, TSUIStyleConstants.LINE_STYLE,
				"<Edge_Style>");
		tsuistyle.setProperty(tsgroupuielement, "DASHED_LINE_ANIMATON_SPEED",
				"<Flow_Speed>");

		tsuistyle.setProperty(tsgroupuielement, "Thickness", 0.5);

		tsuistyle.setProperty(((TSUIElement) (selectedPath)),
				TSUIStyleConstants.LINE_COLOR, new TSEColor(0, 204, 204));
		tsuistyle.setProperty(((TSUIElement) (selectedPath)),
				TSUIStyleConstants.LINE_WIDTH, 0.5);
		tsuistyle.setProperty(((TSUIElement) (selectedPath)),
				TSUIStyleConstants.LINE_STYLE, Integer.valueOf(0));

		tsuistyle.setProperty(((TSUIElement) (selectArrow)),
				i, new TSEColor(0, 204, 204));
		tsuistyle.setProperty(tsgroupuielement1, TSUIStyleConstants.LINE_COLOR,
				new TSEColor(0, 204, 204));
		tsuistyle.setProperty(tsgroupuielement1, TSUIStyleConstants.LINE_WIDTH,
				0.5);
		tsuistyle.setProperty(tsgroupuielement1, TSUIStyleConstants.LINE_STYLE,
				"<Edge_Style>");
		tsuistyle.setProperty(tsgroupuielement1, "DASHED_LINE_ANIMATON_SPEED",
				"<Flow_Speed>");
	}

	@Override
	protected void drawFully(TSUIRenderer tsuirenderer) {
		// TODO Auto-generated method stub
		super.drawFully(tsuirenderer);

		TSEEdge edge = this.getOwnerEdge();

		if (edge == null) {
			return;
		}

		TSTransform transform = ((TSUIHierarchyGraphics2DRenderer) tsuirenderer)
				.getTSTransform();

		TSConstPoint start = edge.getLocalSourceClippingPoint();
		TSConstPoint end = edge.getLocalTargetClippingPoint();

		@SuppressWarnings("unchecked")
		List<TSPNode> pNodes = edge.pathNodes();
		if (pNodes != null && pNodes.size() != 0) {
			end = ((TSPNode) pNodes.get(0)).getLocalCenter();
		}

		double xDiff = end.getX() - start.getX();
		double yDiff = end.getY() - start.getY();

		// handle if the first path edge is very small, don't draw default
		// marker
		if (Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2)) < MARKER_INTERSECTION_OFFSET) {
			// TODO: do something better later
			return;
		}

		double edgeAngle = Math.atan(yDiff / xDiff);
		double markerAngle = edgeAngle + Math.toRadians(MARKER_ANGLE);

		double markerIntersectionX;
		double markerIntersectionY;

		if (xDiff < 0) {
			markerIntersectionX = start.getX() - Math.cos(edgeAngle)
					* MARKER_INTERSECTION_OFFSET;
			markerIntersectionY = start.getY() - Math.sin(edgeAngle)
					* MARKER_INTERSECTION_OFFSET;
		} else {
			markerIntersectionX = start.getX() + Math.cos(edgeAngle)
					* MARKER_INTERSECTION_OFFSET;
			markerIntersectionY = start.getY() + Math.sin(edgeAngle)
					* MARKER_INTERSECTION_OFFSET;
		}

		// for performance we store these in a temp variable
		double cosTemp = Math.cos(markerAngle) * MARKER_LENGTH / 2.0;
		double sinTemp = Math.sin(markerAngle) * MARKER_LENGTH / 2.0;
		if (edge.isSelected()) {
			((TSUIHierarchyGraphics2DRenderer) tsuirenderer).getGraphics()
					.setColor(new Color(0, 204, 204));
		} else {
			((TSUIHierarchyGraphics2DRenderer) tsuirenderer).getGraphics()
					.setColor(new Color(0, 0, 0));
		}

		((TSUIHierarchyGraphics2DRenderer) tsuirenderer).getGraphics()
				.drawLine(transform.xToDevice(markerIntersectionX - cosTemp), // markerStartX
						transform.yToDevice(markerIntersectionY - sinTemp), // markerStartY
						transform.xToDevice(markerIntersectionX + cosTemp), // markerEndX
						transform.yToDevice(markerIntersectionY + sinTemp)); // markerEndY

	}

	private boolean isCurved;
	protected boolean hasSourceArrow;
	protected boolean hasTargetArrow;
	public static final String a = "Color";
	public static final String b = "Edge_Style";
	public static final String c = "Thickness";
	public static final String d = "Source_Head";
	public static final String e = "Source_Arrow_Color";
	public static final String f = "Source_Arrow_Width";
	public static final String g = "Source_Arrow_Height";
	public static final String h = "Target_Head";
	public static final String i = "Target_Arrow_Color";
	public static final String j = "Target_Arrow_Width";
	public static final String k = "Target_Arrow_Height";
	public static final String l = "Curvature";
	public static final String m = "Show_Backbone";
	public static final String n = "Show_Bend_Points";
	public static final String o = "Flow_Speed";

}
