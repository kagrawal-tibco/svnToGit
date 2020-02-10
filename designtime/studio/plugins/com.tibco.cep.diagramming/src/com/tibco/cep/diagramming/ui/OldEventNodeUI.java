package com.tibco.cep.diagramming.ui;

import com.tomsawyer.graphicaldrawing.awt.TSEColor;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEAnnotatedUI;

/**
 * 
 * @author ggrigore
 *
 */
public class OldEventNodeUI extends RoundRectNodeUI {

	private static final long serialVersionUID = 1L;

	public OldEventNodeUI() {

//		this.setArcWidth(0.15);
//		this.setArcHeight(0.10);
		this.setGradient(new TSEColor(85, 141, 106), new TSEColor(255, 255, 255));
		this.setJustification(TSEAnnotatedUI.LEFT);
	}

}
