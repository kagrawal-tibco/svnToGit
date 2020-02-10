package com.tibco.cep.webstudio.server.ui.custom;

import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.ui.composite.TSCompositeNodeUI;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSBoundsUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSGroupUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSOvalUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSPolygonUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSUIElementPoint;
import com.tomsawyer.graphicaldrawing.ui.composite.style.TSUIStyle;
import com.tomsawyer.graphicaldrawing.ui.composite.style.shared.TSUIStyleConstants;

/**
 * This class creates the Activity Diagram - Node UIs.
 */
public class BpmnSignalEndNodeUI extends TSCompositeNodeUI {
	public BpmnSignalEndNodeUI() {

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

		TSBoundsUIElement innerTriangle = new TSPolygonUIElement(
				"signalendtriangle");

		// Represents left point of triangle
		((TSPolygonUIElement) innerTriangle).getPoints().add(
				new TSUIElementPoint(0, 0, 0, 6));

		// Represents right point of triangle
		((TSPolygonUIElement) innerTriangle).getPoints().add(
				new TSUIElementPoint(0, -7.1, 0, -4));

		// Represents top point of triangle
		((TSPolygonUIElement) innerTriangle).getPoints().add(
				new TSUIElementPoint(0, 7.1, 0, -4));

		TSOvalUIElement outerOvalElement = new TSOvalUIElement("signalendoval",
				new TSUIElementPoint(0, 10, 0, -10), new TSUIElementPoint(0,
						-10, 0, 10));

		style.setProperty(innerTriangle, TSUIStyleConstants.FILL_COLOR, "<"
				+ BpmnNodeConstants.BACKGROUND_COLOR + ">");
		style.setProperty(innerTriangle,
				TSUIStyleConstants.FILL_GRADIENT_ENABLED, "<"
						+ BpmnNodeConstants.GRADIENT_COLOR_ENABLED + ">");
		style.setProperty(innerTriangle, TSUIStyleConstants.GRADIENT_COLOR1,
				"<" + BpmnNodeConstants.GRADIENT_START_COLOR + ">");
		style.setProperty(innerTriangle, TSUIStyleConstants.GRADIENT_COLOR2,
				"<" + BpmnNodeConstants.GRADIENT_FINISH_COLOR + ">");
		style.setProperty(innerTriangle, TSUIStyleConstants.GRADIENT_DIRECTION,
				"<" + BpmnNodeConstants.GRADIENT_DIRECTION + ">");
		style.setProperty(innerTriangle, TSUIStyleConstants.LINE_COLOR, "<"
				+ BpmnNodeConstants.BORDER_COLOR + ">");
		style.setProperty(innerTriangle, TSUIStyleConstants.LINE_WIDTH, "<"
				+ BpmnNodeConstants.BORDER_WIDTH + ">");
		style.setProperty(innerTriangle, TSUIStyleConstants.LINE_STYLE, "<"
				+ BpmnNodeConstants.BORDER_STYLE + ">");

		style.setProperty(outerOvalElement, TSUIStyleConstants.FILL_COLOR, "<"
				+ BpmnNodeConstants.BACKGROUND_COLOR + ">");
		style.setProperty(outerOvalElement,
				TSUIStyleConstants.FILL_GRADIENT_ENABLED, false);
		style.setProperty(outerOvalElement, TSUIStyleConstants.GRADIENT_COLOR1,
				"<" + BpmnNodeConstants.GRADIENT_START_COLOR + ">");
		style.setProperty(outerOvalElement, TSUIStyleConstants.GRADIENT_COLOR2,
				"<" + BpmnNodeConstants.GRADIENT_FINISH_COLOR + ">");
		style.setProperty(outerOvalElement,
				TSUIStyleConstants.GRADIENT_DIRECTION, "<"
						+ BpmnNodeConstants.GRADIENT_DIRECTION + ">");
		style.setProperty(outerOvalElement, TSUIStyleConstants.LINE_COLOR,
				new TSEColor(255, 153, 0));
		style.setProperty(outerOvalElement, TSUIStyleConstants.LINE_WIDTH, 2);
		style.setProperty(outerOvalElement, TSUIStyleConstants.LINE_STYLE, "<"
				+ BpmnNodeConstants.BORDER_STYLE + ">");

		group.addElement(outerOvalElement);

		group.addElement(innerTriangle);

		return group;
	}

	/**
	 * Java Serialization ID.
	 */
	private static final long serialVersionUID = 1L;

}
