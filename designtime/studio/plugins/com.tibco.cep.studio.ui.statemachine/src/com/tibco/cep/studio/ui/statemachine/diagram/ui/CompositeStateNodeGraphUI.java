package com.tibco.cep.studio.ui.statemachine.diagram.ui;

import com.tomsawyer.graphicaldrawing.awt.TSEImage;

@SuppressWarnings("serial")
public class CompositeStateNodeGraphUI extends StateChildGraphNodeUI{

	/**
	 * @param node_type
	 */
	public CompositeStateNodeGraphUI(){
		image = new TSEImage(this.getClass(),"/icons/composite.png");
	}
}
