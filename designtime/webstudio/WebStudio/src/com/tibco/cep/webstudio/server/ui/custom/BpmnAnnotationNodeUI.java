package com.tibco.cep.webstudio.server.ui.custom;

import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.ui.composite.TSCompositeNodeUI;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSBoundsUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSConditionalUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSGroupUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSPolygonUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSRoundedRectangleUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSTextUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSUIElementPoint;
import com.tomsawyer.graphicaldrawing.ui.composite.style.TSUIStyle;
import com.tomsawyer.graphicaldrawing.ui.composite.style.shared.TSUIStyleConstants;
import com.tomsawyer.graphicaldrawing.ui.predefined.TSSelectionRectangleTarget;

/**
 * This class creates the Activity Diagram - Node UIs.
 */
public class BpmnAnnotationNodeUI extends TSCompositeNodeUI {
	public BpmnAnnotationNodeUI() {

		TSUIStyle style = new TSUIStyle();
		TSGroupUIElement root = new TSGroupUIElement("root");

		root.addElement(this.getCollapsedBodyElement(style));
		root.addElement(new BpmnNoteSelectionRectangleTarget()
				.getElementTree(style));

		this.setRootElement(root);
		this.setStyle(style);
	}

	private TSUIElement getCollapsedBodyElement(TSUIStyle style) {
		TSGroupUIElement group = new TSGroupUIElement();
		group.setName("collapsedBody");

		TSBoundsUIElement note = new TSPolygonUIElement("note");
		
		((TSPolygonUIElement) note).getPoints().add(
				new TSUIElementPoint(0, -15, 0, 8));

		((TSPolygonUIElement) note).getPoints().add(
				new TSUIElementPoint(0, -15, 0, -8));
		
		((TSPolygonUIElement) note).getPoints().add(
				new TSUIElementPoint(0, 15, 0, -8));
		
		((TSPolygonUIElement) note).getPoints().add(
				new TSUIElementPoint(0, 15, 0, 3));
		
		((TSPolygonUIElement) note).getPoints().add(
				new TSUIElementPoint(0, 4, 0, 3));
		
		((TSPolygonUIElement) note).getPoints().add(
				new TSUIElementPoint(0, 4, 0, 8));
		
		TSBoundsUIElement vline = new TSPolygonUIElement("vline");


		((TSPolygonUIElement) vline).getPoints().add(
				new TSUIElementPoint(0, 4, 0, 8));

		((TSPolygonUIElement) vline).getPoints().add(
				new TSUIElementPoint(0, 4, 0, 3));
		
		((TSPolygonUIElement) vline).getPoints().add(
				new TSUIElementPoint(0, 15, 0, 3));
		
		
		style.setProperty(note, TSUIStyleConstants.FILL_COLOR, new TSEColor(
				255, 216, 0));
		style.setProperty(note, TSUIStyleConstants.FILL_GRADIENT_ENABLED, false);
		style.setProperty(note, TSUIStyleConstants.GRADIENT_COLOR1, "<"
				+ BpmnNodeConstants.GRADIENT_START_COLOR + ">");
		style.setProperty(note, TSUIStyleConstants.GRADIENT_COLOR2, "<"
				+ BpmnNodeConstants.GRADIENT_FINISH_COLOR + ">");
		style.setProperty(note, TSUIStyleConstants.GRADIENT_DIRECTION, "<"
				+ BpmnNodeConstants.GRADIENT_DIRECTION + ">");
		style.setProperty(note, TSUIStyleConstants.LINE_COLOR, new TSEColor(
				255, 226, 142));
		style.setProperty(note, TSUIStyleConstants.LINE_WIDTH, 0);
		style.setProperty(note, TSUIStyleConstants.LINE_STYLE, 0);
		
		style.setProperty(vline, TSUIStyleConstants.LINE_COLOR, new TSEColor(
				255, 226, 142));
		style.setProperty(vline, TSUIStyleConstants.LINE_WIDTH, 0.1);
		style.setProperty(vline, TSUIStyleConstants.LINE_STYLE, 0);
		style.setProperty(vline, TSUIStyleConstants.FILL_COLOR, new TSEColor(
				255, 216, 0));
		TSTextUIElement textUIElement = new TSTextUIElement("Text","$name()");
		style.setProperty(textUIElement, TSUIStyleConstants.TEXT_WRAP_ENABLED, true);
		style.setProperty(textUIElement, TSUIStyleConstants.TEXT_TRUNCATION_ENABLED, false);
		style.setProperty(textUIElement, TSUIStyleConstants.TEXT_FONT, "SansSerif-plain-3");
		style.setProperty(textUIElement, "Editable", "Yes");
		style.setProperty(textUIElement, TSUIStyleConstants.HORIZONTAL_JUSTIFICATION,"Left");
		group.addElement(note);
		group.addElement(vline);
		group.addElement(textUIElement);
		return group;
	}

	/**
	 * Java Serialization ID.
	 */
	private static final long serialVersionUID = 1L;
	
	public class BpmnNoteSelectionRectangleTarget extends TSSelectionRectangleTarget {

		/**
		 * 
		 */
		private static final long serialVersionUID = -1899868101022311035L;

		@Override
		public TSUIElement getElementTree(TSUIStyle tsuistyle) {
			TSGroupUIElement group = new TSGroupUIElement();

			TSRoundedRectangleUIElement tsroundedrectangleuielement1 = new TSRoundedRectangleUIElement(
					"selectionElement1", new TSUIElementPoint(0, -16, 0, 9),
					new TSUIElementPoint(0, -15, 0, 8), 0, false);
			
			tsuistyle.setProperty(tsroundedrectangleuielement1,
					TSUIStyleConstants.FILL_COLOR, TSEColor.blue);
			tsuistyle.setProperty(tsroundedrectangleuielement1,
					TSUIStyleConstants.LINE_WIDTH, 0.1);
			tsuistyle.setProperty(tsroundedrectangleuielement1,
					TSUIStyleConstants.LINE_COLOR, TSEColor.blue);
			group.addElement(tsroundedrectangleuielement1);

			TSRoundedRectangleUIElement tsroundedrectangleuielement2 = new TSRoundedRectangleUIElement(
					"selectionElement2", new TSUIElementPoint(0, -16, 0, 1),
					new TSUIElementPoint(0, -15, 0, 0), 0, false);
			tsuistyle.setProperty(tsroundedrectangleuielement2,
					TSUIStyleConstants.FILL_COLOR, TSEColor.blue);
			tsuistyle.setProperty(tsroundedrectangleuielement2,
					TSUIStyleConstants.LINE_WIDTH,  0.1);
			tsuistyle.setProperty(tsroundedrectangleuielement2,
					TSUIStyleConstants.LINE_COLOR, TSEColor.blue);
			group.addElement(tsroundedrectangleuielement2);

			TSRoundedRectangleUIElement tsroundedrectangleuielement3 = new TSRoundedRectangleUIElement(
					"selectionElement3", new TSUIElementPoint(0, -16, 0, -9),
					new TSUIElementPoint(0, -15, 0, -8), 0, false);
			tsuistyle.setProperty(tsroundedrectangleuielement3,
					TSUIStyleConstants.FILL_COLOR, TSEColor.blue);
			tsuistyle.setProperty(tsroundedrectangleuielement3,
					TSUIStyleConstants.LINE_WIDTH,  0.1);
			tsuistyle.setProperty(tsroundedrectangleuielement3,
					TSUIStyleConstants.LINE_COLOR, TSEColor.blue);
			group.addElement(tsroundedrectangleuielement3);

			TSRoundedRectangleUIElement tsroundedrectangleuielement4 = new TSRoundedRectangleUIElement(
					"selectionElement4", new TSUIElementPoint(0, 0, 0, -9),
					new TSUIElementPoint(0, 1, 0, -8), 0, false);
			tsuistyle.setProperty(tsroundedrectangleuielement4,
					TSUIStyleConstants.FILL_COLOR, TSEColor.blue);
			tsuistyle.setProperty(tsroundedrectangleuielement4,
					TSUIStyleConstants.LINE_WIDTH,  0.1);
			tsuistyle.setProperty(tsroundedrectangleuielement4,
					TSUIStyleConstants.LINE_COLOR, TSEColor.blue);
			group.addElement(tsroundedrectangleuielement4);

			TSRoundedRectangleUIElement tsroundedrectangleuielement5 = new TSRoundedRectangleUIElement(
					"selectionElement5", new TSUIElementPoint(0, 16, 0,-9),
					new TSUIElementPoint(0, 15, 0, -8), 0, false);
			tsuistyle.setProperty(tsroundedrectangleuielement5,
					TSUIStyleConstants.FILL_COLOR, TSEColor.blue);
			tsuistyle.setProperty(tsroundedrectangleuielement5,
					TSUIStyleConstants.LINE_WIDTH,  0.1);
			tsuistyle.setProperty(tsroundedrectangleuielement5,
					TSUIStyleConstants.LINE_COLOR, TSEColor.blue);
			group.addElement(tsroundedrectangleuielement5);

			TSRoundedRectangleUIElement tsroundedrectangleuielement6 = new TSRoundedRectangleUIElement(
					"selectionElement6", new TSUIElementPoint(0, 16, 0, 0),
					new TSUIElementPoint(0, 15, 0, 1), 0, false);
			tsuistyle.setProperty(tsroundedrectangleuielement6,
					TSUIStyleConstants.FILL_COLOR, TSEColor.blue);
			tsuistyle.setProperty(tsroundedrectangleuielement6,
					TSUIStyleConstants.LINE_WIDTH,  0.1);
			tsuistyle.setProperty(tsroundedrectangleuielement6,
					TSUIStyleConstants.LINE_COLOR, TSEColor.blue);
			group.addElement(tsroundedrectangleuielement6);

			TSRoundedRectangleUIElement tsroundedrectangleuielement7 = new TSRoundedRectangleUIElement(
					"selectionElement7", new TSUIElementPoint(0, 16, 0, 9),
					new TSUIElementPoint(0, 15, 0, 8), 0, false);
			tsuistyle.setProperty(tsroundedrectangleuielement7,
					TSUIStyleConstants.FILL_COLOR, TSEColor.blue);
			tsuistyle.setProperty(tsroundedrectangleuielement7,
					TSUIStyleConstants.LINE_WIDTH,  0.1);
			tsuistyle.setProperty(tsroundedrectangleuielement7,
					TSUIStyleConstants.LINE_COLOR, TSEColor.blue);
			group.addElement(tsroundedrectangleuielement7);

			TSRoundedRectangleUIElement tsroundedrectangleuielement8 = new TSRoundedRectangleUIElement(
					"selectionElement8", new TSUIElementPoint(0, 0, 0, 9),
					new TSUIElementPoint(0, 1, 0, 8), 0, false);
			tsuistyle.setProperty(tsroundedrectangleuielement8,
					TSUIStyleConstants.FILL_COLOR, TSEColor.blue);
			tsuistyle.setProperty(tsroundedrectangleuielement8,
					TSUIStyleConstants.LINE_WIDTH,  0.1);
			tsuistyle.setProperty(tsroundedrectangleuielement8,
					TSUIStyleConstants.LINE_COLOR, TSEColor.blue);				
			group.addElement(tsroundedrectangleuielement8);

			TSConditionalUIElement tsconditionaluielement = new TSConditionalUIElement(
					"ifSelected", "$and($drawSelected(),$not($isHovered()))");
			tsconditionaluielement.setIfElement(group);
			tsconditionaluielement.setSecondary(true);
			return tsconditionaluielement;

		}
	}

}
