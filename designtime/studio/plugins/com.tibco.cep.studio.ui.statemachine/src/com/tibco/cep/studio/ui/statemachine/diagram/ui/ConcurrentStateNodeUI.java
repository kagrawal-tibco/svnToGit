package com.tibco.cep.studio.ui.statemachine.diagram.ui;

import com.tomsawyer.graphicaldrawing.awt.TSEImage;

@SuppressWarnings("serial")
public class ConcurrentStateNodeUI extends StateChildGraphNodeUI{

	/**
	 * @param node_type
	 */
	public ConcurrentStateNodeUI(/*String node_type*/){
		image =  new TSEImage(this.getClass(),"/icons/concurrent.png");
	}
	
}
