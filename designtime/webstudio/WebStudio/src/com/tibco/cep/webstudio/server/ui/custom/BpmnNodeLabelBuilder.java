/**
 * 
 */
package com.tibco.cep.webstudio.server.ui.custom;

import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.awt.TSEFont;
import com.tomsawyer.graphicaldrawing.ui.TSLabelUI;
import com.tomsawyer.graphicaldrawing.ui.TSPLabelBuilder;
import com.tomsawyer.util.shared.TSAttributedObject;

/**
 * @author dijadhav
 * 
 */
public class BpmnNodeLabelBuilder extends TSPLabelBuilder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -861337256730317957L;

	@Override
	protected TSLabelUI createLabelUI() {

		return new BpmnNodeLabelUI();
	}

	@Override
	protected void initDefaultAttributes(TSAttributedObject attributedObject) {
		super.initDefaultProperties();
		attributedObject.setAttribute("Text Font", new TSEFont(
				"SansSerif-plain-4"));
		this.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
	}

	@Override
	public TSENodeLabel addNodeLabel(TSENode tsenode) {
		// TODO Auto-generated method stub
		return super.addNodeLabel(tsenode);
	}

}
