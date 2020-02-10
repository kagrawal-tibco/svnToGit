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
public class BpmnErrorEndEventNodeUI extends TSCompositeNodeUI {
	public BpmnErrorEndEventNodeUI() {

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

		TSOvalUIElement shapeElement = new TSOvalUIElement("shape",
				new TSUIElementPoint(0, 10, 0, -10), new TSUIElementPoint(0,
						-10, 0, 10));
		((TSOvalUIElement) shapeElement).setLineWidthTransformEnabled(true);

		style.setProperty(shapeElement, TSUIStyleConstants.FILL_COLOR, new TSEColor(255,255,255));
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
		style.setProperty(shapeElement, TSUIStyleConstants.LINE_WIDTH, 2);
		style.setProperty(shapeElement, TSUIStyleConstants.LINE_STYLE, 0);

		TSBoundsUIElement errorElement = new TSPolygonUIElement("errorElement");

		((TSPolygonUIElement) errorElement).getPoints().add(
				new TSUIElementPoint(0, -3, 0,5));
		
		((TSPolygonUIElement) errorElement).getPoints().add(
				new TSUIElementPoint(0, -3, 0,0));
		
		((TSPolygonUIElement) errorElement).getPoints().add(
				new TSUIElementPoint(0, 3, 0,-5));
		
		((TSPolygonUIElement) errorElement).getPoints().add(
				new TSUIElementPoint(0, 3, 0,0));
		
		
		TSBoundsUIElement errorElement1 = new TSPolygonUIElement("errorElement1");

		((TSPolygonUIElement) errorElement1).getPoints().add(
				new TSUIElementPoint(0, -3, 0,5));
		
		((TSPolygonUIElement) errorElement1).getPoints().add(
				new TSUIElementPoint(0, -3, 0,0));
		
		((TSPolygonUIElement) errorElement1).getPoints().add(
				new TSUIElementPoint(0, -4.5, 0,-3));
		
		TSBoundsUIElement errorElement2 = new TSPolygonUIElement("errorElement2");
		((TSPolygonUIElement) errorElement2).getPoints().add(
				new TSUIElementPoint(0, 3, 0,-5));
		
		((TSPolygonUIElement) errorElement2).getPoints().add(
				new TSUIElementPoint(0, 3, 0,0));	

		((TSPolygonUIElement) errorElement2).getPoints().add(
				new TSUIElementPoint(0, 4.5, 0,3));
		
		style.setProperty(errorElement, TSUIStyleConstants.LINE_COLOR,
				new TSEColor(255, 153, 0));
		style.setProperty(errorElement, TSUIStyleConstants.LINE_WIDTH,
				0.5);
		style.setProperty(errorElement, TSUIStyleConstants.LINE_STYLE, 0);
		style.setProperty(errorElement, TSUIStyleConstants.FILL_COLOR,
				new TSEColor(255, 153, 0));

		style.setProperty(errorElement1, TSUIStyleConstants.LINE_COLOR,
				new TSEColor(255, 153, 0));
		style.setProperty(errorElement1, TSUIStyleConstants.LINE_WIDTH,
				0.5);
		style.setProperty(errorElement1, TSUIStyleConstants.LINE_STYLE, 0);
		style.setProperty(errorElement1, TSUIStyleConstants.FILL_COLOR,
				new TSEColor(255, 153, 0));
		

		style.setProperty(errorElement2, TSUIStyleConstants.LINE_COLOR,
				new TSEColor(255, 153, 0));
		style.setProperty(errorElement2, TSUIStyleConstants.LINE_WIDTH,
				0.5);
		style.setProperty(errorElement2, TSUIStyleConstants.LINE_STYLE, 0);
		style.setProperty(errorElement2, TSUIStyleConstants.FILL_COLOR,
				new TSEColor(255, 153, 0));
		
		group.addElement(shapeElement);
		group.addElement(errorElement);
		group.addElement(errorElement1);
		group.addElement(errorElement2);
		return group;
	}

	/**
	 * Java Serialization ID.
	 */
	private static final long serialVersionUID = 1L;

}
