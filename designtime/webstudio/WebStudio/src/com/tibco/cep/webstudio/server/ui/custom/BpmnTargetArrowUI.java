package com.tibco.cep.webstudio.server.ui.custom;

import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSArrowUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSConditionalUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSGroupUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSOvalUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSPolygonUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSSwitchUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSTargetArrowUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSUIElementPoint;
import com.tomsawyer.graphicaldrawing.ui.composite.style.TSUIStyle;
import com.tomsawyer.graphicaldrawing.ui.composite.style.shared.TSUIStyleConstants;
import com.tomsawyer.graphicaldrawing.ui.predefined.edge.TSMultiArrows;

/**
 * This class is used to create the target arrow
 * 
 * @author dijadhav
 *
 */
public class BpmnTargetArrowUI extends TSMultiArrows {
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -7331599648290564101L;

	private TSEColor arrowColor;

	/**
	 * @param arrowColor
	 */
	public BpmnTargetArrowUI(String name, TSEColor arrowColor) {
		super(name, false, true);
		this.arrowColor = arrowColor;
	}

	@Override
	public TSUIElement getElementTree(TSUIStyle tsuiStyle) {
		TSConditionalUIElement tsconditionaluielement = new TSConditionalUIElement(
				(new StringBuilder()).append(name).append("isArrowVisible")
						.toString(), "$zoomLevelGreaterThan(0.25)");
		tsconditionaluielement
				.setIfElement(getTargetArrowSwitchGroup(tsuiStyle));
		tsuiStyle.setProperty(tsconditionaluielement,
				TSUIStyleConstants.LINE_COLOR, arrowColor);
		return tsconditionaluielement;
	}

	private TSSwitchUIElement getTargetArrowSwitchGroup(TSUIStyle tsuistyle) {
		TSSwitchUIElement tsswitchuielement = getSwitchGroup(tsuistyle,
				"<Target_Head>",
				(new StringBuilder()).append(name).append("target").toString(),
				false);
		tsuistyle.setProperty(tsswitchuielement, TSUIStyleConstants.FILL_COLOR,
				arrowColor);
		return tsswitchuielement;
	}

	private TSSwitchUIElement getSwitchGroup(TSUIStyle tsuistyle, String s,
			String s1, boolean flag) {
		TSPolygonUIElement tspolygonuielement = new TSPolygonUIElement(
				(new StringBuilder()).append(s1).append("triangle1").toString());
		tspolygonuielement.getPoints().add(
				new TSUIElementPoint(-0.5D, 0.0D, 0.5D, 0.0D));
		tspolygonuielement.getPoints().add(
				new TSUIElementPoint(0.5D, 0.0D, 0.0D, 0.0D));
		tspolygonuielement.getPoints().add(
				new TSUIElementPoint(-0.5D, 0.0D, -0.5D, 0.0D));
		tspolygonuielement.setLineWidthTransformEnabled(true);
		TSPolygonUIElement tspolygonuielement1 = new TSPolygonUIElement(
				(new StringBuilder()).append(s1).append("triangle2").toString());
		tspolygonuielement1.getPoints().add(
				new TSUIElementPoint(-0.5D, 0.0D, 0.5D, 0.0D));
		tspolygonuielement1.getPoints().add(
				new TSUIElementPoint(0.5D, 0.0D, 0.0D, 0.0D));
		tspolygonuielement1.getPoints().add(
				new TSUIElementPoint(-0.5D, 0.0D, -0.5D, 0.0D));
		double d = 0.10000000000000001D;
		tspolygonuielement1.getPoints().add(
				new TSUIElementPoint(-0.5D, 0.0D, -0.5D + d, 0.0D));
		tspolygonuielement1.getPoints().add(
				new TSUIElementPoint(0.5D - d, 0.0D, 0.0D, 0.0D));
		tspolygonuielement1.getPoints().add(
				new TSUIElementPoint(-0.5D, 0.0D, 0.5D - d, 0.0D));
		tsuistyle.setProperty(tspolygonuielement1,
				TSUIStyleConstants.LINE_WIDTH, "<Thickness>");
		tspolygonuielement1.setLineWidthTransformEnabled(true);
		TSPolygonUIElement tspolygonuielement2 = new TSPolygonUIElement(
				(new StringBuilder()).append(s1).append("triangle3").toString());
		tspolygonuielement2.getPoints().add(
				new TSUIElementPoint(-0.5D, 0.0D, 0.0D, 0.0D));
		tspolygonuielement2.getPoints().add(
				new TSUIElementPoint(0.0D, 0.0D, 0.5D, 0.0D));
		tspolygonuielement2.getPoints().add(
				new TSUIElementPoint(0.5D, 0.0D, 0.0D, 0.0D));
		tspolygonuielement2.getPoints().add(
				new TSUIElementPoint(0.0D, 0.0D, -0.5D, 0.0D));
		tspolygonuielement2.setLineWidthTransformEnabled(true);
		TSOvalUIElement tsovaluielement = new TSOvalUIElement("triangle4",
				new TSUIElementPoint(-0.5D, 0.0D, 0.5D, 0.0D),
				new TSUIElementPoint(0.5D, 0.0D, -0.5D, 0.0D));
		tsovaluielement.setLineWidthTransformEnabled(true);
		TSPolygonUIElement tspolygonuielement3 = new TSPolygonUIElement(
				(new StringBuilder()).append(s1).append("filledTriangle5_1")
						.toString());
		tspolygonuielement3.getPoints().add(
				new TSUIElementPoint(-0.5D, 0.0D, 0.5D, 0.0D));
		tspolygonuielement3.getPoints().add(
				new TSUIElementPoint(0.20000000000000001D, 0.0D, 0.0D, 0.0D));
		tspolygonuielement3.getPoints().add(
				new TSUIElementPoint(-0.5D, 0.0D, -0.5D, 0.0D));
		tspolygonuielement3.setLineWidthTransformEnabled(true);
		TSPolygonUIElement tspolygonuielement4 = new TSPolygonUIElement(
				(new StringBuilder()).append(s1).append("filledTriangle5_2")
						.toString());
		tspolygonuielement4.getPoints().add(
				new TSUIElementPoint(-0.20000000000000001D, 0.0D, 0.5D, 0.0D));
		tspolygonuielement4.getPoints().add(
				new TSUIElementPoint(0.5D, 0.0D, 0.0D, 0.0D));
		tspolygonuielement4.getPoints().add(
				new TSUIElementPoint(-0.20000000000000001D, 0.0D, -0.5D, 0.0D));
		tspolygonuielement4.setLineWidthTransformEnabled(true);
		TSGroupUIElement tsgroupuielement = new TSGroupUIElement(
				(new StringBuilder()).append(s1).append("triangle5").toString());
		tsgroupuielement.addElement(tspolygonuielement3);
		tsgroupuielement.addElement(tspolygonuielement4);
		TSPolygonUIElement tspolygonuielement5 = new TSPolygonUIElement(
				(new StringBuilder()).append(s1).append("triangle6").toString());
		tspolygonuielement5.getPoints().add(
				new TSUIElementPoint(-0.5D, 0.0D, 0.5D, 0.0D));
		tspolygonuielement5.getPoints().add(
				new TSUIElementPoint(0.5D, 0.0D, 0.0D, 0.0D));
		tspolygonuielement5.getPoints().add(
				new TSUIElementPoint(-0.5D, 0.0D, -0.5D, 0.0D));
		tspolygonuielement5.getPoints().add(
				new TSUIElementPoint(-0.20000000000000001D, 0.0D, 0.0D, 0.0D));
		tspolygonuielement5.setLineWidthTransformEnabled(true);
		TSArrowUIElement tsarrowuielement;
		TSArrowUIElement tsarrowuielement1;
		TSArrowUIElement tsarrowuielement2;
		TSArrowUIElement tsarrowuielement3;
		TSArrowUIElement tsarrowuielement4;
		TSArrowUIElement tsarrowuielement5;

		tsarrowuielement = newTargetArrowElement(tsuistyle);
		tsarrowuielement1 = newTargetArrowElement(tsuistyle);
		tsarrowuielement2 = newTargetArrowElement(tsuistyle);
		tsarrowuielement3 = newTargetArrowElement(tsuistyle);
		tsarrowuielement4 = newTargetArrowElement(tsuistyle);
		tsarrowuielement5 = newTargetArrowElement(tsuistyle);
		tsarrowuielement.setChild(tspolygonuielement);
		tsarrowuielement1.setChild(tspolygonuielement1);
		tsarrowuielement2.setChild(tspolygonuielement2);
		tsarrowuielement3.setChild(tsovaluielement);
		tsarrowuielement4.setChild(tsgroupuielement);
		tsarrowuielement5.setChild(tspolygonuielement5);
		TSSwitchUIElement tsswitchuielement = new TSSwitchUIElement(
				(new StringBuilder()).append(s1).append("arrows").toString(), s);
		tsswitchuielement.addChild("1", tsarrowuielement);
		tsswitchuielement.addChild("2", tsarrowuielement1);
		tsswitchuielement.addChild("3", tsarrowuielement2);
		tsswitchuielement.addChild("4", tsarrowuielement3);
		tsswitchuielement.addChild("5", tsarrowuielement4);
		tsswitchuielement.addChild("6", tsarrowuielement5);
		return tsswitchuielement;
	}

	private TSArrowUIElement newTargetArrowElement(TSUIStyle tsuistyle) {
		TSTargetArrowUIElement tstargetarrowuielement = new TSTargetArrowUIElement(
				(new StringBuilder()).append(name).append("targetArrow")
						.toString());
		tsuistyle.setProperty(tstargetarrowuielement, TSUIStyleConstants.WIDTH,
				"$add(<Target_Arrow_Width>,<Thickness>)");
		tsuistyle.setProperty(tstargetarrowuielement,
				TSUIStyleConstants.HEIGHT,
				"$add(<Target_Arrow_Height>,<Thickness>)");
		tsuistyle.setProperty(tstargetarrowuielement,
				TSUIStyleConstants.LINE_WIDTH, Integer.valueOf(1));
		return tstargetarrowuielement;
	}
}
