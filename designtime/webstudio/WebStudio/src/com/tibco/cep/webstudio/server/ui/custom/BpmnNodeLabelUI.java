/**
 * 
 */
package com.tibco.cep.webstudio.server.ui.custom;

import com.tomsawyer.graphicaldrawing.ui.composite.TSCompositeLabelUI;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSGroupUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSTextUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSUIElement;
import com.tomsawyer.graphicaldrawing.ui.composite.element.shared.TSUIElementPoint;
import com.tomsawyer.graphicaldrawing.ui.composite.style.TSUIStyle;
import com.tomsawyer.graphicaldrawing.ui.composite.style.shared.TSUIStyleConstants;

/**
 * @author dijadhav
 * 
 */
public class BpmnNodeLabelUI extends TSCompositeLabelUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4033517190564468916L;

	public BpmnNodeLabelUI() {
		TSUIStyle style = new TSUIStyle();
		TSGroupUIElement root = new TSGroupUIElement("root");

		root.addElement(this.getCollapsedBodyElement(style));
		this.setRootElement(root);
		this.setStyle(style);
	}

	private TSUIElement getCollapsedBodyElement(TSUIStyle style) {
		TSGroupUIElement collapsedElement = new TSGroupUIElement(
				"collapsedBody");
		collapsedElement.addElement(this.createTextElement(style));

		return collapsedElement;
	}

	private TSUIElement createTextElement(TSUIStyle style) {

		double leftProportional = -0.5;
		double leftConstant = 0;
		double topProportional = 0.5;
		double topConstant = 0;
		double rightProportional = 0.5;
		double rightConstant = 0;
		double bottomProportional = -0.5;
		double bottomConstant = 0;
		TSTextUIElement textElement = new TSTextUIElement("Text", "$name()",
				new TSUIElementPoint(leftProportional, leftConstant,
						topProportional, topConstant), new TSUIElementPoint(
						rightProportional, rightConstant, bottomProportional,
						bottomConstant));
		style.setProperty(textElement, TSUIStyleConstants.TEXT_FONT, "<"
				+ "Text Font" + ">");
		style.setProperty(textElement, TSUIStyleConstants.TEXT_COLOR, "<"
				+ "Text Color" + ">");
		style.setProperty(textElement,
				TSUIStyleConstants.TEXT_TRUNCATION_ENABLED, Boolean.FALSE);
		return textElement;
	}
}
