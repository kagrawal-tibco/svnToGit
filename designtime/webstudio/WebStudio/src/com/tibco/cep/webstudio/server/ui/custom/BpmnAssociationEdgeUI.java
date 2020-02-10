package com.tibco.cep.webstudio.server.ui.custom;

import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSBendPointsUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSConditionalUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSCurvedPathUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSGroupUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSPathUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSStraightPathUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.style.TSUIStyle;
import com.tomsawyer.graphicaldrawing.ui.composite.style.shared.TSUIStyleConstants;
import com.tomsawyer.graphicaldrawing.ui.predefined.edge.TSMultiArrows;
import com.tomsawyer.graphicaldrawing.ui.predefined.edge.basic.TSBasicEdgeUI;

/**
 * This class creates the Activity Diagram - Node UIs.
 */
public class BpmnAssociationEdgeUI extends TSBasicEdgeUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3886509819355762229L;
	private boolean isCurved;

	public BpmnAssociationEdgeUI(boolean flag) {
		super(flag);
	}

	public BpmnAssociationEdgeUI(boolean flag, boolean flag1, boolean flag2) {
		super(flag, flag1, flag2);
		this.isCurved = flag;
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
		TSConditionalUIElement tsconditionaluielement = new TSConditionalUIElement(
				"bendsCondition",
				"$and(<Show_Bend_Points>, $not($isDrawingInWebCanvas()))");
		TSBendPointsUIElement tsbendpointsuielement = createBendPointsUIElement(tsuistyle);
		tsconditionaluielement.setIfElement(tsbendpointsuielement);
		Object obj;
		Object obj1;
		TSStraightPathUIElement tsstraightpathuielement;
		if (!isCurved) {
			obj = new TSStraightPathUIElement("path");
			obj1 = new TSStraightPathUIElement("selectedPath");
			tsstraightpathuielement = null;
		} else {
			obj = new TSCurvedPathUIElement("path");
			obj1 = new TSCurvedPathUIElement("selectedPath");
			tsstraightpathuielement = new TSStraightPathUIElement("bendsPath");
		}
		tsgroupuielement.addElement(((TSUIElement) (obj)));
		TSUIElement tsuielement = (new TSMultiArrows("path", hasSourceArrow,
				hasTargetArrow)).getElementTree(tsuistyle);
		tsgroupuielement.addElement(tsuielement);
		TSConditionalUIElement tsconditionaluielement3 = new TSConditionalUIElement(
				"ifSelected", "$drawSelected()");
		TSGroupUIElement tsgroupuielement1 = new TSGroupUIElement(
				"selectedGroup");
		tsconditionaluielement3.setIfElement(tsgroupuielement1);
		TSConditionalUIElement tsconditionaluielement4 = new TSConditionalUIElement(
				"ifSingleSelected", "$drawSingleSelected()");
		tsgroupuielement1.addElement(((TSUIElement) (obj1)));
		tsgroupuielement1.addElement(tsconditionaluielement4);
		if (tsstraightpathuielement != null) {
			TSGroupUIElement tsgroupuielement2 = new TSGroupUIElement(
					"singleSelectedGroup");
			tsconditionaluielement4.setIfElement(tsgroupuielement2);
			TSConditionalUIElement tsconditionaluielement5 = new TSConditionalUIElement(
					"backboneCondition",
					"$and(<Show_Backbone>, $not($isDrawingInWebCanvas()))");
			tsconditionaluielement5.setIfElement(tsstraightpathuielement);
			tsgroupuielement2.addElement(tsconditionaluielement5);
			tsgroupuielement2.addElement(tsconditionaluielement);
			tsconditionaluielement4.setIfElement(tsgroupuielement2);
		} else {
			tsconditionaluielement4.setIfElement(tsconditionaluielement);
		}
		tsgroupuielement.addElement(tsconditionaluielement3);
		tsuistyle.setProperty(((TSUIElement) (obj)),
				TSUIStyleConstants.LINE_COLOR, "<Color>");
		tsuistyle.setProperty(((TSUIElement) (obj)),
				TSUIStyleConstants.LINE_WIDTH, 1);
		tsuistyle.setProperty(((TSUIElement) (obj)),
				TSUIStyleConstants.LINE_STYLE, "<Edge_Style>");
		tsuistyle.setProperty(((TSUIElement) (obj)),
				"DASHED_LINE_ANIMATON_SPEED", "<Flow_Speed>");
		((TSPathUIElement) (obj))
				.setSourceDistance("$if($numberEquals(<Source_Head>, 0), 0, $if($and($numberLessThan($sourceEdgeLength(), $add(<Source_Arrow_Height>, <Thickness>)),$sourceBendSharp()),0, 1.5))");
		((TSPathUIElement) (obj))
				.setTargetDistance("$if($numberEquals(<Target_Head>, 0), 0, $if($and($numberLessThan($targetEdgeLength(), $add(<Target_Arrow_Height>, <Thickness>)), $targetBendSharp()),0, 1.5))");
		tsuistyle.setProperty(((TSUIElement) (obj1)),
				TSUIStyleConstants.LINE_COLOR,  new TSEColor(0,204,204));
		tsuistyle.setProperty(((TSUIElement) (obj1)),
				TSUIStyleConstants.LINE_WIDTH, 1);
		tsuistyle.setProperty(((TSUIElement) (obj1)),
				TSUIStyleConstants.LINE_STYLE, Integer.valueOf(2));
		if (isCurved) {
			tsuistyle.setProperty(((TSUIElement) (obj)),
					TSUIStyleConstants.CURVATURE, "<Curvature>");
			tsuistyle.setProperty(((TSUIElement) (obj1)),
					TSUIStyleConstants.CURVATURE, "<Curvature>");
		}
		if (tsstraightpathuielement != null) {
			tsuistyle.setProperty(tsstraightpathuielement,
					TSUIStyleConstants.LINE_COLOR, new TSEColor(0,204,204));
			tsuistyle.setProperty(tsstraightpathuielement,
					TSUIStyleConstants.LINE_WIDTH, Integer.valueOf(1));
			tsuistyle.setProperty(tsstraightpathuielement,
					TSUIStyleConstants.LINE_STYLE, Integer.valueOf(2));
			tsstraightpathuielement.setSourceDistance(((TSPathUIElement) (obj))
					.getSourceDistance());
			tsstraightpathuielement.setTargetDistance(((TSPathUIElement) (obj))
					.getTargetDistance());
		}
	}

}
