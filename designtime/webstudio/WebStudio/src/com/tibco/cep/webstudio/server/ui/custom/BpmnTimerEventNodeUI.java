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
public class BpmnTimerEventNodeUI extends TSCompositeNodeUI {
	public BpmnTimerEventNodeUI() {

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

		TSOvalUIElement shapeElement = new TSOvalUIElement("shape",
				new TSUIElementPoint(0, 10, 0, 10), new TSUIElementPoint(0,
						-10, 0, -10));
		((TSOvalUIElement) shapeElement).setLineWidthTransformEnabled(true);

		TSOvalUIElement shapeElement2 = new TSOvalUIElement("inner shape",
				new TSUIElementPoint(0, 7, 0, 7), new TSUIElementPoint(0, -7,
						0, -7));
		((TSOvalUIElement) shapeElement2).setLineWidthTransformEnabled(true);

		TSOvalUIElement timerOval12 = new TSOvalUIElement("oval12",
				new TSUIElementPoint(0, 0.5, 0, 6), new TSUIElementPoint(0,
						-.5, 0, 5));

		TSOvalUIElement timerOval3 = new TSOvalUIElement("oval3",
				new TSUIElementPoint(0, 6, 0, 0.5), new TSUIElementPoint(0, 5,
						0, -0.5));

		TSOvalUIElement timerOval9 = new TSOvalUIElement("oval9",
				new TSUIElementPoint(0, -6, 0, 0.5), new TSUIElementPoint(0,
						-5, 0, -0.5));

		TSOvalUIElement timerOval6 = new TSOvalUIElement("oval6",
				new TSUIElementPoint(0, 0.5, 0, -6), new TSUIElementPoint(0,
						-0.5, 0, -5));

		TSOvalUIElement timerOval1 = new TSOvalUIElement("oval1",
				new TSUIElementPoint(0, 3.7, 0, 5.1), new TSUIElementPoint(0,
						2.7, 0, 4.1));

		TSOvalUIElement timerOval2 = new TSOvalUIElement("oval2",
				new TSUIElementPoint(0, 5.4, 0, 3.0), new TSUIElementPoint(0,
						4.4, 0, 2));

		TSOvalUIElement timerOval4 = new TSOvalUIElement("oval4",
				new TSUIElementPoint(0, 4.4, 0, -2.2), new TSUIElementPoint(0,
						5.4, 0, -3.2));

		TSOvalUIElement timerOval5 = new TSOvalUIElement("oval5",
				new TSUIElementPoint(0, 2.3, 0, -5.2), new TSUIElementPoint(0, 3.3,
						0, -4.2));

		TSOvalUIElement timerOval11 = new TSOvalUIElement("oval11",
				new TSUIElementPoint(0, -3.7, 0, 5.1), new TSUIElementPoint(0,
						-2.7, 0, 4.1));

		TSOvalUIElement timerOval10 = new TSOvalUIElement("oval10",
				new TSUIElementPoint(0, -5.5, 0, 3.0), new TSUIElementPoint(0,
						-4.5, 0, 2.0));

		TSOvalUIElement timerOval7 = new TSOvalUIElement("oval7",
				new TSUIElementPoint(0, -2.3, 0, -5.2), new TSUIElementPoint(0, -3.3,
				0, -4.2));

		TSOvalUIElement timerOval8 = new TSOvalUIElement("oval8",
				new TSUIElementPoint(0, -4.4, 0, -2.2), new TSUIElementPoint(0,
						-5.4, 0, -3.2));

		TSBoundsUIElement minuteHand = new TSPolygonUIElement("minuteHand");

		// Represents left point of triangle
		((TSPolygonUIElement) minuteHand).getPoints().add(
				new TSUIElementPoint(0, 0, 0, 0));

		// Represents left point of triangle
		((TSPolygonUIElement) minuteHand).getPoints().add(
				new TSUIElementPoint(0, 1.7, 0, 6));

		style.setProperty(minuteHand, TSUIStyleConstants.FILL_COLOR,
				new TSEColor(255, 255, 255));
		style.setProperty(minuteHand, TSUIStyleConstants.LINE_COLOR,
				new TSEColor(255, 153, 0));
		style.setProperty(minuteHand, TSUIStyleConstants.LINE_WIDTH, 0.5);
		style.setProperty(minuteHand, TSUIStyleConstants.LINE_STYLE, 0);

		TSBoundsUIElement secondHand = new TSPolygonUIElement("secondHand");

		// Represents left point of triangle
		((TSPolygonUIElement) secondHand).getPoints().add(
				new TSUIElementPoint(0, 0, 0, 0));

		// Represents left point of triangle
		((TSPolygonUIElement) secondHand).getPoints().add(
				new TSUIElementPoint(0, 4.5, 0, -1.2));

		style.setProperty(secondHand, TSUIStyleConstants.FILL_COLOR,
				new TSEColor(255, 255, 255));
		style.setProperty(secondHand, TSUIStyleConstants.LINE_COLOR,
				new TSEColor(255, 153, 0));
		style.setProperty(secondHand, TSUIStyleConstants.LINE_WIDTH, 0.5);
		style.setProperty(secondHand, TSUIStyleConstants.LINE_STYLE, 0);

		style.setProperty(shapeElement, TSUIStyleConstants.FILL_COLOR,
				new TSEColor(255, 255, 255));
		style.setProperty(shapeElement,
				TSUIStyleConstants.FILL_GRADIENT_ENABLED, false);
		style.setProperty(shapeElement,
				TSUIStyleConstants.TOOL_TIP, "Label:$name()");
		style.setProperty(shapeElement, TSUIStyleConstants.GRADIENT_COLOR1, "<"
				+ BpmnNodeConstants.GRADIENT_START_COLOR + ">");
		style.setProperty(shapeElement, TSUIStyleConstants.GRADIENT_COLOR2, "<"
				+ BpmnNodeConstants.GRADIENT_FINISH_COLOR + ">");
		style.setProperty(shapeElement, TSUIStyleConstants.GRADIENT_DIRECTION,
				"<" + BpmnNodeConstants.GRADIENT_DIRECTION + ">");
		style.setProperty(shapeElement, TSUIStyleConstants.LINE_COLOR,
				new TSEColor(255, 153, 0));
		style.setProperty(shapeElement, TSUIStyleConstants.LINE_WIDTH, 1);
		style.setProperty(shapeElement, TSUIStyleConstants.LINE_STYLE, 0);

		style.setProperty(shapeElement2, TSUIStyleConstants.FILL_COLOR,
				new TSEColor(255, 255, 255));
		style.setProperty(shapeElement2, TSUIStyleConstants.LINE_COLOR,
				new TSEColor(255, 153, 0));
		style.setProperty(shapeElement2, TSUIStyleConstants.LINE_WIDTH, 1);
		style.setProperty(shapeElement2, TSUIStyleConstants.LINE_STYLE, 0);

		setStyle(style, timerOval12);

		setStyle(style, timerOval1);

		setStyle(style, timerOval2);

		setStyle(style, timerOval3);

		setStyle(style, timerOval6);

		setStyle(style, timerOval9);

		setStyle(style, timerOval4);

		setStyle(style, timerOval5);

		setStyle(style, timerOval11);

		setStyle(style, timerOval10);

		setStyle(style, timerOval7);
		setStyle(style, timerOval8);

		group.addElement(shapeElement);
		group.addElement(shapeElement2);
		group.addElement(timerOval12);
		group.addElement(timerOval1);
		group.addElement(timerOval2);
		group.addElement(timerOval3);
		group.addElement(timerOval6);
		group.addElement(timerOval9);
		group.addElement(timerOval4);
		group.addElement(timerOval5);
		group.addElement(timerOval11);
		group.addElement(timerOval10);
		group.addElement(timerOval7);
		group.addElement(timerOval8);
		group.addElement(minuteHand);
		group.addElement(secondHand);
		return group;
	}

	private void setStyle(TSUIStyle style, TSOvalUIElement timerOval10) {
		style.setProperty(timerOval10, TSUIStyleConstants.FILL_COLOR,
				new TSEColor(255, 153, 0));
		style.setProperty(timerOval10, TSUIStyleConstants.LINE_COLOR,
				new TSEColor(255, 153, 0));
		style.setProperty(timerOval10, TSUIStyleConstants.LINE_WIDTH, 0.1);
		style.setProperty(timerOval10, TSUIStyleConstants.LINE_STYLE, 0);
		style.setProperty(timerOval10,
				TSUIStyleConstants.FILL_GRADIENT_ENABLED, false);
		style.setProperty(timerOval10, TSUIStyleConstants.GRADIENT_COLOR1, "<"
				+ BpmnNodeConstants.GRADIENT_START_COLOR + ">");
		style.setProperty(timerOval10, TSUIStyleConstants.GRADIENT_COLOR2, "<"
				+ BpmnNodeConstants.GRADIENT_FINISH_COLOR + ">");
		style.setProperty(timerOval10, TSUIStyleConstants.GRADIENT_DIRECTION,
				"<" + BpmnNodeConstants.GRADIENT_DIRECTION + ">");
	}

	/**
	 * Java Serialization ID.
	 */
	private static final long serialVersionUID = 1L;

}
