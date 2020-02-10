package com.tibco.cep.webstudio.server.ui.custom;

import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.ui.composite.TSCompositeNodeUI;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSBoundsUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSGroupUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSOvalUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSPolygonUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSRectangularUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSUIElementPoint;
import com.tomsawyer.graphicaldrawing.ui.composite.style.TSUIStyle;
import com.tomsawyer.graphicaldrawing.ui.composite.style.shared.TSUIStyleConstants;

/**
 * This class creates the Activity Diagram - Node UIs.
 */
public class BpmnMessageEndNodeUI extends TSCompositeNodeUI {
	public BpmnMessageEndNodeUI() {

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


		TSBoundsUIElement rectangle = new TSRectangularUIElement("big rect",
				new TSUIElementPoint(0, 5.5, 0, -3.5), new TSUIElementPoint(
						0, -5.5, 0, 3.5));

		((TSRectangularUIElement) rectangle)
				.setLineWidthTransformEnabled(true);

		TSBoundsUIElement innerTriangleEdge1 = new TSPolygonUIElement("triangle edge1");
		((TSPolygonUIElement) innerTriangleEdge1).getPoints().add(
				new TSUIElementPoint(0, -5.5, 0, 3.5));
		((TSPolygonUIElement) innerTriangleEdge1).getPoints().add(
				new TSUIElementPoint(0, 0, 0, 0));
		
		
		
		TSBoundsUIElement innerTriangleEdge2 = new TSPolygonUIElement("triangle edge2");
		((TSPolygonUIElement) innerTriangleEdge2).getPoints().add(
				new TSUIElementPoint(0, 0, 0, 0));
		((TSPolygonUIElement) innerTriangleEdge2).getPoints().add(
				new TSUIElementPoint(0.0, 5.5, 0, 3.5));
		
		TSOvalUIElement outerOvalElement = new TSOvalUIElement("shape1",
				new TSUIElementPoint(0, 10, 0, -10), new TSUIElementPoint(
						0, -10, 0, 10));

		style.setProperty(rectangle, TSUIStyleConstants.FILL_COLOR, "<"
				+ BpmnNodeConstants.BACKGROUND_COLOR + ">");
		style.setProperty(rectangle,
				TSUIStyleConstants.FILL_GRADIENT_ENABLED, "<"
						+ BpmnNodeConstants.GRADIENT_COLOR_ENABLED + ">");
		style.setProperty(rectangle, TSUIStyleConstants.GRADIENT_COLOR1, "<"
				+ BpmnNodeConstants.GRADIENT_START_COLOR + ">");
		style.setProperty(rectangle, TSUIStyleConstants.GRADIENT_COLOR2, "<"
				+ BpmnNodeConstants.GRADIENT_FINISH_COLOR + ">");
		style.setProperty(rectangle, TSUIStyleConstants.GRADIENT_DIRECTION,
				"<" + BpmnNodeConstants.GRADIENT_DIRECTION + ">");
		style.setProperty(rectangle, TSUIStyleConstants.LINE_COLOR, "<"
				+ BpmnNodeConstants.BORDER_COLOR + ">");
		style.setProperty(rectangle, TSUIStyleConstants.LINE_WIDTH, "<"
				+ BpmnNodeConstants.BORDER_WIDTH + ">");
		style.setProperty(rectangle, TSUIStyleConstants.LINE_STYLE, "<"
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
		style.setProperty(outerOvalElement, TSUIStyleConstants.LINE_COLOR,new TSEColor(255,153,0));
		style.setProperty(outerOvalElement, TSUIStyleConstants.LINE_WIDTH, 2);
		style.setProperty(outerOvalElement, TSUIStyleConstants.LINE_STYLE, "<"
				+ BpmnNodeConstants.BORDER_STYLE + ">");

		style.setProperty(innerTriangleEdge1, TSUIStyleConstants.FILL_COLOR, "<"
				+ BpmnNodeConstants.TRANSPARENT_COLOR + ">");
		style.setProperty(innerTriangleEdge1, TSUIStyleConstants.LINE_COLOR, "<"
				+ BpmnNodeConstants.BORDER_COLOR + ">");
		style.setProperty(innerTriangleEdge1, TSUIStyleConstants.LINE_WIDTH,0.5);
		style.setProperty(innerTriangleEdge1, TSUIStyleConstants.LINE_STYLE, "<"
				+ BpmnNodeConstants.BORDER_STYLE + ">");


		style.setProperty(innerTriangleEdge2, TSUIStyleConstants.FILL_COLOR, "<"
				+ BpmnNodeConstants.TRANSPARENT_COLOR + ">");
		style.setProperty(innerTriangleEdge2, TSUIStyleConstants.LINE_COLOR, "<"
				+ BpmnNodeConstants.BORDER_COLOR + ">");
		style.setProperty(innerTriangleEdge2, TSUIStyleConstants.LINE_WIDTH,0.5);
		style.setProperty(innerTriangleEdge2, TSUIStyleConstants.LINE_STYLE, "<"
				+ BpmnNodeConstants.BORDER_STYLE + ">");
		
		group.addElement(outerOvalElement);
		group.addElement(rectangle);
		group.addElement(innerTriangleEdge1);
		group.addElement(innerTriangleEdge2);

		return group;
	}

	/**
	 * Java Serialization ID.
	 */
	private static final long serialVersionUID = 1L;

}
