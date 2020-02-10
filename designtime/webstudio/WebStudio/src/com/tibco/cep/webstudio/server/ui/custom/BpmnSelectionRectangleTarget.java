/**
 * 
 */
package com.tibco.cep.webstudio.server.ui.custom;

import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSConditionalUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSGroupUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSRoundedRectangleUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSUIElementPoint;
import com.tomsawyer.graphicaldrawing.ui.composite.style.TSUIStyle;
import com.tomsawyer.graphicaldrawing.ui.composite.style.shared.TSUIStyleConstants;
import com.tomsawyer.graphicaldrawing.ui.predefined.TSSelectionRectangleTarget;

/**
 * @author dijadhav
 * 
 */
public class BpmnSelectionRectangleTarget extends TSSelectionRectangleTarget {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1899868101022311035L;

	@Override
	public TSUIElement getElementTree(TSUIStyle tsuistyle) {
		TSGroupUIElement group = new TSGroupUIElement();
		tsuistyle.setProperty(group, TSUIStyleConstants.WIDTH, 10);
		tsuistyle.setProperty(group, TSUIStyleConstants.HEIGHT, 10);

		TSRoundedRectangleUIElement tsroundedrectangleuielement1 = new TSRoundedRectangleUIElement(
				"selectionElement1", new TSUIElementPoint(0, -11, 0, 10),
				new TSUIElementPoint(0, -10, 0, 9), 0, false);
		tsuistyle.setProperty(tsroundedrectangleuielement1,
				TSUIStyleConstants.FILL_COLOR, TSEColor.blue);
		tsuistyle.setProperty(tsroundedrectangleuielement1,
				TSUIStyleConstants.LINE_WIDTH, 0.1);
		tsuistyle.setProperty(tsroundedrectangleuielement1,
				TSUIStyleConstants.LINE_COLOR, TSEColor.blue);
		group.addElement(tsroundedrectangleuielement1);

		TSRoundedRectangleUIElement tsroundedrectangleuielement2 = new TSRoundedRectangleUIElement(
				"selectionElement2", new TSUIElementPoint(0, -11, 0, 1),
				new TSUIElementPoint(0, -10, 0, 0), 0, false);
		tsuistyle.setProperty(tsroundedrectangleuielement2,
				TSUIStyleConstants.FILL_COLOR, TSEColor.blue);
		tsuistyle.setProperty(tsroundedrectangleuielement2,
				TSUIStyleConstants.LINE_WIDTH,  0.1);
		tsuistyle.setProperty(tsroundedrectangleuielement2,
				TSUIStyleConstants.LINE_COLOR, TSEColor.blue);
		group.addElement(tsroundedrectangleuielement2);

		TSRoundedRectangleUIElement tsroundedrectangleuielement3 = new TSRoundedRectangleUIElement(
				"selectionElement3", new TSUIElementPoint(0, -11, 0, -10),
				new TSUIElementPoint(0, -10, 0, -9), 0, false);
		tsuistyle.setProperty(tsroundedrectangleuielement3,
				TSUIStyleConstants.FILL_COLOR, TSEColor.blue);
		tsuistyle.setProperty(tsroundedrectangleuielement3,
				TSUIStyleConstants.LINE_WIDTH,  0.1);
		tsuistyle.setProperty(tsroundedrectangleuielement3,
				TSUIStyleConstants.LINE_COLOR, TSEColor.blue);
		group.addElement(tsroundedrectangleuielement3);

		TSRoundedRectangleUIElement tsroundedrectangleuielement4 = new TSRoundedRectangleUIElement(
				"selectionElement4", new TSUIElementPoint(0, 11, 0, -10),
				new TSUIElementPoint(0, 10, 0, -9), 0, false);
		tsuistyle.setProperty(tsroundedrectangleuielement4,
				TSUIStyleConstants.FILL_COLOR, TSEColor.blue);
		tsuistyle.setProperty(tsroundedrectangleuielement4,
				TSUIStyleConstants.LINE_WIDTH,  0.1);
		tsuistyle.setProperty(tsroundedrectangleuielement4,
				TSUIStyleConstants.LINE_COLOR, TSEColor.blue);
		group.addElement(tsroundedrectangleuielement4);

		TSRoundedRectangleUIElement tsroundedrectangleuielement5 = new TSRoundedRectangleUIElement(
				"selectionElement5", new TSUIElementPoint(0, 11, 0,1),
				new TSUIElementPoint(0, 10, 0, 0), 0, false);
		tsuistyle.setProperty(tsroundedrectangleuielement5,
				TSUIStyleConstants.FILL_COLOR, TSEColor.blue);
		tsuistyle.setProperty(tsroundedrectangleuielement5,
				TSUIStyleConstants.LINE_WIDTH,  0.1);
		tsuistyle.setProperty(tsroundedrectangleuielement5,
				TSUIStyleConstants.LINE_COLOR, TSEColor.blue);
		group.addElement(tsroundedrectangleuielement5);

		TSRoundedRectangleUIElement tsroundedrectangleuielement6 = new TSRoundedRectangleUIElement(
				"selectionElement6", new TSUIElementPoint(0, 11, 0, 10),
				new TSUIElementPoint(0, 10, 0, 9), 0, false);
		tsuistyle.setProperty(tsroundedrectangleuielement6,
				TSUIStyleConstants.FILL_COLOR, TSEColor.blue);
		tsuistyle.setProperty(tsroundedrectangleuielement6,
				TSUIStyleConstants.LINE_WIDTH,  0.1);
		tsuistyle.setProperty(tsroundedrectangleuielement6,
				TSUIStyleConstants.LINE_COLOR, TSEColor.blue);
		group.addElement(tsroundedrectangleuielement6);

		TSRoundedRectangleUIElement tsroundedrectangleuielement7 = new TSRoundedRectangleUIElement(
				"selectionElement7", new TSUIElementPoint(0, 1, 0, 11),
				new TSUIElementPoint(0, 0, 0, 10), 0, false);
		tsuistyle.setProperty(tsroundedrectangleuielement7,
				TSUIStyleConstants.FILL_COLOR, TSEColor.blue);
		tsuistyle.setProperty(tsroundedrectangleuielement7,
				TSUIStyleConstants.LINE_WIDTH,  0.1);
		tsuistyle.setProperty(tsroundedrectangleuielement7,
				TSUIStyleConstants.LINE_COLOR, TSEColor.blue);
		group.addElement(tsroundedrectangleuielement7);

		TSRoundedRectangleUIElement tsroundedrectangleuielement8 = new TSRoundedRectangleUIElement(
				"selectionElement8", new TSUIElementPoint(0, 1, 0, -11),
				new TSUIElementPoint(0, 0, 0, -10), 0, false);
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
