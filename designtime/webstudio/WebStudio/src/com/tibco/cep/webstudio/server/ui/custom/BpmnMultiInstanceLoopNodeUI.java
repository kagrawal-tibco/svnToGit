package com.tibco.cep.webstudio.server.ui.custom;

import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.ui.composite.TSCompositeNodeUI;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSBoundsUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSGroupUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSRectangularUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSRoundedRectangleUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSUIElementPoint;
import com.tomsawyer.graphicaldrawing.ui.composite.style.TSUIStyle;
import com.tomsawyer.graphicaldrawing.ui.composite.style.shared.TSUIStyleConstants;

/**
 * This class creates the Activity Diagram - Node UIs.
 */
public class BpmnMultiInstanceLoopNodeUI extends TSCompositeNodeUI {
	public BpmnMultiInstanceLoopNodeUI() {

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

		TSBoundsUIElement rectNo1 = new TSRoundedRectangleUIElement("rectNo1",
				new TSUIElementPoint(0, -3, 0, -3.5), new TSUIElementPoint(0, 3,
						0, -5));

		TSBoundsUIElement rectNo2 = new TSRoundedRectangleUIElement("rectNo2",
				new TSUIElementPoint(0, -3, 0, -5.5), new TSUIElementPoint(0, 3,
						0, -7));

		TSBoundsUIElement rectNo3 = new TSRoundedRectangleUIElement("rectNo3",
				new TSUIElementPoint(0, -3, 0, -7.5), new TSUIElementPoint(0, 3,
						0, -9));

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
		style.setProperty(shapeElement, TSUIStyleConstants.LINE_WIDTH, 0.5);
		style.setProperty(shapeElement, TSUIStyleConstants.LINE_STYLE, 0);

		style.setProperty(rectNo3, TSUIStyleConstants.FILL_COLOR, new TSEColor(
				0, 0, 0));
		style.setProperty(rectNo3, TSUIStyleConstants.LINE_COLOR, new TSEColor(
				0, 0, 0));
		style.setProperty(rectNo3, TSUIStyleConstants.LINE_WIDTH, .1);
		style.setProperty(rectNo3, TSUIStyleConstants.LINE_STYLE, "<"
				+ BpmnNodeConstants.BORDER_STYLE + ">");
		style.setProperty(rectNo3, "Show Border", false);

		style.setProperty(rectNo2, TSUIStyleConstants.FILL_COLOR, new TSEColor(
				0, 0, 0));
		style.setProperty(rectNo2, TSUIStyleConstants.LINE_COLOR, new TSEColor(
				0, 0, 0));
		style.setProperty(rectNo2, TSUIStyleConstants.LINE_WIDTH, .1);
		style.setProperty(rectNo2, TSUIStyleConstants.LINE_STYLE, "<"
				+ BpmnNodeConstants.BORDER_STYLE + ">");
		style.setProperty(rectNo2, "Show Border", false);

		style.setProperty(rectNo1, TSUIStyleConstants.FILL_COLOR, new TSEColor(
				0, 0, 0));
		style.setProperty(rectNo1, TSUIStyleConstants.LINE_COLOR, new TSEColor(
				0, 0, 0));
		style.setProperty(rectNo1, TSUIStyleConstants.LINE_WIDTH, .1);
		style.setProperty(rectNo1, TSUIStyleConstants.LINE_STYLE, "<"
				+ BpmnNodeConstants.BORDER_STYLE + ">");
		style.setProperty(rectNo1, "Show Border", false);

		group.addElement(shapeElement);
		group.addElement(rectNo1);
		group.addElement(rectNo2);
		group.addElement(rectNo3);
		return group;
	}

	/**
	 * Java Serialization ID.
	 */
	private static final long serialVersionUID = 1L;

}
