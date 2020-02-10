package com.tibco.cep.studio.ui.statemachine.diagram.ui;

import com.tomsawyer.graphicaldrawing.awt.TSEImage;

public class RegionGraphNodeUI extends StateChildGraphNodeUI{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7840524233393443226L;

	/**
	 * @param node_type
	 */
	public RegionGraphNodeUI(){
		image =  new TSEImage(this.getClass(),"/icons/region.png");
	}
}
