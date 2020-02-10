package com.tibco.cep.webstudio.server.ui.custom;

import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.ui.composite.TSCompositeNodeUI;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSBoundsUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSGroupUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSPolygonUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSUIElementPoint;
import com.tomsawyer.graphicaldrawing.ui.composite.style.TSUIStyle;
import com.tomsawyer.graphicaldrawing.ui.composite.style.shared.TSUIStyleConstants;

/**
 * @author dijadhav
 * 
 */
public class BpmnExclusiveGatewayNodeUI extends TSCompositeNodeUI {
	public BpmnExclusiveGatewayNodeUI() {

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

		TSBoundsUIElement shapeElement = new TSPolygonUIElement("diamond");

		// Represents left point of diamond
		((TSPolygonUIElement) shapeElement).getPoints().add(
				new TSUIElementPoint(0, -10, 0, 0));

		// Represents top point of diamond
		((TSPolygonUIElement) shapeElement).getPoints().add(
				new TSUIElementPoint(0.0, 0, 0, 10));

		// Represents right point of diamond
		((TSPolygonUIElement) shapeElement).getPoints().add(
				new TSUIElementPoint(0.0, 10, 0.0, 0));

		// Represents bottom point of diamond
		((TSPolygonUIElement) shapeElement).getPoints().add(
				new TSUIElementPoint(0.0, 0, 0.0, -10));

		((TSPolygonUIElement) shapeElement).setLineWidthTransformEnabled(true);

		TSBoundsUIElement cross1 = new TSPolygonUIElement("cross1");
		
		((TSPolygonUIElement) cross1).getPoints().add(
				new TSUIElementPoint(0.0, 5, 0, 4.5));		
		
		((TSPolygonUIElement) cross1).getPoints().add(
				new TSUIElementPoint(0.0, 3, 0, 5));
		
		((TSPolygonUIElement) cross1).getPoints().add(
				new TSUIElementPoint(0.0, -5, 0,-2.5));
		
		((TSPolygonUIElement) cross1).getPoints().add(
				new TSUIElementPoint(0.0, -5, 0,-4.5));
		
		((TSPolygonUIElement) cross1).getPoints().add(
				new TSUIElementPoint(0.0, -3.5, 0,-4.5));
		
		((TSPolygonUIElement) cross1).getPoints().add(
				new TSUIElementPoint(0.0, 5, 0,3));
		
		((TSPolygonUIElement) cross1).setLineWidthTransformEnabled(true);

		style.setProperty(shapeElement, TSUIStyleConstants.FILL_COLOR, "<"
				+ BpmnNodeConstants.BACKGROUND_COLOR + ">");
		style.setProperty(shapeElement,
				TSUIStyleConstants.FILL_GRADIENT_ENABLED, "<"
						+ BpmnNodeConstants.GRADIENT_COLOR_ENABLED + ">");
		style.setProperty(shapeElement, TSUIStyleConstants.GRADIENT_COLOR1, "<"
				+ BpmnNodeConstants.GRADIENT_START_COLOR + ">");
		style.setProperty(shapeElement, TSUIStyleConstants.GRADIENT_COLOR2, "<"
				+ BpmnNodeConstants.GRADIENT_FINISH_COLOR + ">");
		style.setProperty(shapeElement, TSUIStyleConstants.GRADIENT_DIRECTION,
				"<" + BpmnNodeConstants.GRADIENT_DIRECTION + ">");
		style.setProperty(shapeElement, TSUIStyleConstants.LINE_COLOR, new TSEColor(255,153,0));
		style.setProperty(shapeElement, TSUIStyleConstants.LINE_WIDTH, 0.5);
		style.setProperty(shapeElement, TSUIStyleConstants.LINE_STYLE, "<"
				+ BpmnNodeConstants.BORDER_STYLE + ">");
		
		TSBoundsUIElement cross2 = new TSPolygonUIElement("cross2");
		((TSPolygonUIElement) cross2).getPoints().add(
				new TSUIElementPoint(0.0, -5, 0, 4.5));		
		
		((TSPolygonUIElement) cross2).getPoints().add(
				new TSUIElementPoint(0.0, -5, 0, 3));
		
		((TSPolygonUIElement) cross2).getPoints().add(
				new TSUIElementPoint(0.0, 2.5, 0,-5));
		
		((TSPolygonUIElement) cross2).getPoints().add(
				new TSUIElementPoint(0.0, 5, 0,-4.5));
		
		((TSPolygonUIElement) cross2).getPoints().add(
				new TSUIElementPoint(0.0, 4.5, 0,-3));
		
		((TSPolygonUIElement) cross2).getPoints().add(
				new TSUIElementPoint(0.0, -3, 0,5));
		
		((TSPolygonUIElement) cross2).setLineWidthTransformEnabled(true);	
		
		style.setProperty(cross1, TSUIStyleConstants.FILL_COLOR, new TSEColor(0,0,0));
		style.setProperty(cross1, TSUIStyleConstants.LINE_WIDTH, 0);
		style.setProperty(cross2, TSUIStyleConstants.FILL_COLOR, new TSEColor(0,0,0));
		style.setProperty(cross2, TSUIStyleConstants.LINE_WIDTH, 0);
		
		group.addElement(shapeElement);
		group.addElement(cross1);
		group.addElement(cross2);
		return group;
	}

	/**
	 * Java Serialization ID.
	 */
	private static final long serialVersionUID = 1L;
}
