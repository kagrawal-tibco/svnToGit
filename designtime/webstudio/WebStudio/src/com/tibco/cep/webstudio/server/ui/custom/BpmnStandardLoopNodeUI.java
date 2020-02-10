package com.tibco.cep.webstudio.server.ui.custom;

import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.ui.composite.TSCompositeNodeUI;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSBoundsUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSGroupUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSOvalUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSPolygonUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSRectangularUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSRoundedRectangleUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSUIElementPoint;
import com.tomsawyer.graphicaldrawing.ui.composite.style.TSUIStyle;
import com.tomsawyer.graphicaldrawing.ui.composite.style.shared.TSUIStyleConstants;

/**
 * This class creates the Activity Diagram - Node UIs.
 */
public class BpmnStandardLoopNodeUI extends TSCompositeNodeUI {
	public BpmnStandardLoopNodeUI() {

		TSUIStyle style = new TSUIStyle();
		TSGroupUIElement root = new TSGroupUIElement("root");

		root.addElement(this.getCollapsedBodyElement(style));
		root.addElement(new BpmnSelectionRectangleTarget()
				.getElementTree(style));
		this.setRootElement(root);
		this.setStyle(style);
	}

	private TSUIElement getCollapsedBodyElement(TSUIStyle style) {
		TSGroupUIElement group = new TSGroupUIElement();
		group.setName("collapsedBody");

		TSBoundsUIElement shapeElement = new TSRoundedRectangleUIElement(
				"big rect", new TSUIElementPoint(0, 10, 0, 10),
				new TSUIElementPoint(0, -10, 0, -10));

		((TSRectangularUIElement) shapeElement)
				.setLineWidthTransformEnabled(true);

		TSOvalUIElement innerOval = new TSOvalUIElement("innerOval",
				new TSUIElementPoint(0, -1, 0, -7), new TSUIElementPoint(0, 2,
						0, -10));

		TSBoundsUIElement crossLine1 = new TSPolygonUIElement("crossLine1");

		((TSPolygonUIElement) crossLine1).getPoints().add(
				new TSUIElementPoint(0, 0, 0, -10));

		((TSPolygonUIElement) crossLine1).getPoints().add(
				new TSUIElementPoint(0.0, -2, 0, -8.5));

		((TSPolygonUIElement) crossLine1).setLineWidthTransformEnabled(true);
		TSBoundsUIElement crossLine2 = new TSPolygonUIElement("crossLine2");

		((TSPolygonUIElement) crossLine2).getPoints().add(
				new TSUIElementPoint(0, 0, 0, -10));

		((TSPolygonUIElement) crossLine2).getPoints().add(
				new TSUIElementPoint(0.0,.5, 0, -8));

		((TSPolygonUIElement) crossLine2).setLineWidthTransformEnabled(true);

		style.setProperty(shapeElement, TSUIStyleConstants.FILL_COLOR, new TSEColor(255, 255, 255));
		style.setProperty(shapeElement,
				TSUIStyleConstants.FILL_GRADIENT_ENABLED, false);
		style.setProperty(shapeElement, TSUIStyleConstants.GRADIENT_COLOR1, "<"
				+ BpmnNodeConstants.GRADIENT_START_COLOR + ">");
		style.setProperty(shapeElement, TSUIStyleConstants.GRADIENT_COLOR2, "<"
				+ BpmnNodeConstants.GRADIENT_FINISH_COLOR + ">");
		style.setProperty(shapeElement, TSUIStyleConstants.GRADIENT_DIRECTION,
				"<" + BpmnNodeConstants.GRADIENT_DIRECTION + ">");

		style.setProperty(shapeElement, TSUIStyleConstants.LINE_COLOR, new TSEColor(255, 153, 0));
		style.setProperty(shapeElement, TSUIStyleConstants.LINE_WIDTH, .5);
		style.setProperty(shapeElement, TSUIStyleConstants.LINE_STYLE, 0);

		style.setProperty(innerOval, TSUIStyleConstants.FILL_COLOR,
				new TSEColor(255, 255, 255));
		style.setProperty(innerOval, TSUIStyleConstants.LINE_COLOR,
				new TSEColor(0, 0, 0));
		style.setProperty(innerOval, TSUIStyleConstants.LINE_WIDTH, .1);

		style.setProperty(crossLine1, TSUIStyleConstants.FILL_COLOR,
				new TSEColor(255, 255, 255));
		style.setProperty(crossLine1, TSUIStyleConstants.LINE_COLOR,
				new TSEColor(0, 0, 0));
		style.setProperty(crossLine1, TSUIStyleConstants.LINE_WIDTH, .1);

		style.setProperty(crossLine2, TSUIStyleConstants.FILL_COLOR,
				new TSEColor(255, 255, 255));
		style.setProperty(crossLine2, TSUIStyleConstants.LINE_COLOR,
				new TSEColor(0, 0, 0));
		style.setProperty(crossLine2, TSUIStyleConstants.LINE_WIDTH, .1);

		group.addElement(shapeElement);
		group.addElement(innerOval);
		group.addElement(crossLine1);
		group.addElement(crossLine2);
		return group;
	}

	/**
	 * Java Serialization ID.
	 */
	private static final long serialVersionUID = 1L;

}
