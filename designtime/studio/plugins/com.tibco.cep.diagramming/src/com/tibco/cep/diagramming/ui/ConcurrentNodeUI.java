package com.tibco.cep.diagramming.ui;

import com.tomsawyer.graphicaldrawing.awt.TSEColor;

/**
 * 
 * @author ggrigore
 *
 */
public class ConcurrentNodeUI extends ChildGraphNodeUI {

	private static final long serialVersionUID = 1L;

	public void reset() {
		super.reset();
		this.setOuterRoundRect(true);
		this.setBorderDrawn(true);
		this.setFillColor(new TSEColor(218, 182, 135));
		this.setDrawChildGraphMark(false);
	}
}
