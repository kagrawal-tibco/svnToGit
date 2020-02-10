package com.tibco.cep.webstudio.server.ui.custom;

import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.ui.composite.TSCompositeNodeUI;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSBoundsUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSGroupUIElement;
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
public class BpmnCallActivityNodeUI extends TSCompositeNodeUI {
	public BpmnCallActivityNodeUI() {
		TSUIStyle style = new TSUIStyle();
		TSGroupUIElement root = new TSGroupUIElement("root");

		root.addElement(this.getCollapsedBodyElement(style));
		root.addElement(new BpmnSelectionRectangleTarget().getElementTree(style));
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

		TSBoundsUIElement innerRect = new TSPolygonUIElement("innerRect");

		((TSPolygonUIElement) innerRect).getPoints().add(
				new TSUIElementPoint(0.0, 2, 0, -8));

		((TSPolygonUIElement) innerRect).getPoints().add(
				new TSUIElementPoint(0.0, -2, 0, -8));

		((TSPolygonUIElement) innerRect).getPoints().add(
				new TSUIElementPoint(0.0, -2, 0, -4));

		((TSPolygonUIElement) innerRect).getPoints().add(
				new TSUIElementPoint(0.0, 2, 0, -4));

		((TSPolygonUIElement) innerRect).setLineWidthTransformEnabled(true);

		TSBoundsUIElement verticleLine = new TSPolygonUIElement("verticleLine");

		((TSPolygonUIElement) verticleLine).getPoints().add(
				new TSUIElementPoint(0.0, 0, 0, -5));

		((TSPolygonUIElement) verticleLine).getPoints().add(
				new TSUIElementPoint(0.0, 0, 0, -7));

		((TSPolygonUIElement) verticleLine).setLineWidthTransformEnabled(true);

		TSBoundsUIElement horizontalLine = new TSPolygonUIElement(
				"horizontalLine");
		((TSPolygonUIElement) horizontalLine).getPoints().add(
				new TSUIElementPoint(0.0, -1.5, 0, -6));

		((TSPolygonUIElement) horizontalLine).getPoints().add(
				new TSUIElementPoint(0.0, 1.5, 0, -6));

		((TSPolygonUIElement) horizontalLine)
				.setLineWidthTransformEnabled(true);

		shapeElement.setVisual(true);
		style.setProperty(shapeElement, TSUIStyleConstants.FILL_COLOR,
				new TSEColor(255, 255, 255));
		style.setProperty(shapeElement,
				TSUIStyleConstants.FILL_GRADIENT_ENABLED, false);
		style.setProperty(shapeElement, TSUIStyleConstants.GRADIENT_COLOR1, "<"
				+ BpmnNodeConstants.GRADIENT_START_COLOR + ">");
		style.setProperty(shapeElement, TSUIStyleConstants.GRADIENT_COLOR2, "<"
				+ BpmnNodeConstants.GRADIENT_FINISH_COLOR + ">");
		style.setProperty(shapeElement, TSUIStyleConstants.GRADIENT_DIRECTION,
				"<" + BpmnNodeConstants.GRADIENT_DIRECTION + ">");

		style.setProperty(shapeElement, TSUIStyleConstants.LINE_COLOR,
				new TSEColor(255, 153, 0));
		style.setProperty(shapeElement, TSUIStyleConstants.LINE_WIDTH, 1);
		style.setProperty(shapeElement, TSUIStyleConstants.LINE_STYLE, "<"
				+ BpmnNodeConstants.BORDER_STYLE + ">");

		style.setProperty(innerRect, TSUIStyleConstants.FILL_COLOR,
				new TSEColor(255, 255, 255));
		style.setProperty(innerRect, TSUIStyleConstants.LINE_COLOR,
				new TSEColor(0, 0, 0));
		style.setProperty(innerRect, TSUIStyleConstants.LINE_WIDTH, .1);

		style.setProperty(verticleLine, TSUIStyleConstants.FILL_COLOR,
				new TSEColor(255, 255, 255));
		style.setProperty(verticleLine, TSUIStyleConstants.LINE_COLOR,
				new TSEColor(0, 0, 0));
		style.setProperty(verticleLine, TSUIStyleConstants.LINE_WIDTH, .1);

		style.setProperty(horizontalLine, TSUIStyleConstants.FILL_COLOR,
				new TSEColor(255, 255, 255));
		style.setProperty(horizontalLine, TSUIStyleConstants.LINE_COLOR,
				new TSEColor(0, 0, 0));
		style.setProperty(horizontalLine, TSUIStyleConstants.LINE_WIDTH, .1);

		group.addElement(shapeElement);
		group.addElement(innerRect);
		group.addElement(verticleLine);
		group.addElement(horizontalLine);

		return group;
	}

	/**
	 * Java Serialization ID.
	 */
	private static final long serialVersionUID = 1L;

}
