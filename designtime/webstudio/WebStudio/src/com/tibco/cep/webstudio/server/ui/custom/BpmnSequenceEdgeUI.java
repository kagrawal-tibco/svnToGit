package com.tibco.cep.webstudio.server.ui.custom;

import java.awt.Color;

import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSConditionalUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSCurvedPathUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSGroupUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSStraightPathUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.renderer.graphics2d.TSUIHierarchyGraphics2DRenderer;
import com.tomsawyer.graphicaldrawing.ui.composite.renderer.shared.TSUIRenderer;
import com.tomsawyer.graphicaldrawing.ui.composite.style.TSUIStyle;
import com.tomsawyer.graphicaldrawing.ui.composite.style.shared.TSUIStyleConstants;
import com.tomsawyer.graphicaldrawing.ui.predefined.edge.basic.TSBasicEdgeUI;

/**
 * This class creates the Activity Diagram - Node UIs.
 */
public class BpmnSequenceEdgeUI extends TSBasicEdgeUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4225654590468540240L;

	public BpmnSequenceEdgeUI(boolean flag, boolean flag1, boolean flag2) {
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
		TSStraightPathUIElement path = new TSCurvedPathUIElement("path");
		TSStraightPathUIElement selectedPath = new TSCurvedPathUIElement(
				"selectedPath");

		tsgroupuielement.addElement(path);

		TSUIElement tsuielement = new BpmnTargetArrowUI("path", new TSEColor(0,
				0, 0)).getElementTree(tsuistyle);

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
		tsgroupuielement1.addElement(selectedPath);
		tsgroupuielement1.addElement(tsconditionaluielement4);
		tsgroupuielement.addElement(tsconditionaluielement3);

		tsuistyle.setProperty(path, TSUIStyleConstants.LINE_COLOR, "<Color>");
		tsuistyle.setProperty(path, TSUIStyleConstants.LINE_WIDTH, 0.5);
		tsuistyle.setProperty(path, TSUIStyleConstants.LINE_STYLE,
				"<Edge_Style>");
		tsuistyle.setProperty(path, "DASHED_LINE_ANIMATON_SPEED",
				"<Flow_Speed>");

		tsuistyle.setProperty(tsgroupuielement, "Thickness", 0.5);
		path.setSourceDistance("$if($numberEquals(<Source_Head>, 0), 0, $if($and($numberLessThan($sourceEdgeLength(), $add(<Source_Arrow_Height>, <Thickness>)),$sourceBendSharp()),0, 2))");
		path.setTargetDistance("$if($numberEquals(<Target_Head>, 0), 0, $if($and($numberLessThan($targetEdgeLength(), $add(<Target_Arrow_Height>, <Thickness>)), $targetBendSharp()),0, 0.5))");

		tsuistyle.setProperty(selectedPath, TSUIStyleConstants.LINE_COLOR,
				new TSEColor(0, 204, 204));
		tsuistyle.setProperty(selectedPath, TSUIStyleConstants.LINE_WIDTH, 0.5);
		tsuistyle.setProperty(selectedPath, TSUIStyleConstants.LINE_STYLE,
				Integer.valueOf(0));

		tsuistyle.setProperty(((TSUIElement) (selectArrow)), i, new TSEColor(0,
				204, 204));

		tsuistyle.setProperty(((TSUIElement) (selectArrow)), j, 3);

		tsuistyle.setProperty(((TSUIElement) (selectArrow)), k, 2);

		tsuistyle.setProperty(((TSUIElement) (tsuielement)), i, new TSEColor(0,
				204, 204));

		tsuistyle.setProperty(((TSUIElement) (tsuielement)), j, 3);

		tsuistyle.setProperty(((TSUIElement) (tsuielement)), k, 2);

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
		if (edge.isSelected()) {
			((TSUIHierarchyGraphics2DRenderer) tsuirenderer).getGraphics()
					.setColor(new Color(0, 204, 204));
		} else {
			((TSUIHierarchyGraphics2DRenderer) tsuirenderer).getGraphics()
					.setColor(new Color(0, 0, 0));
		}
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
