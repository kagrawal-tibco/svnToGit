package com.tibco.cep.diagramming.ui;


public class ErrorNodeUI extends ActivityBadgeNodeUI {

	/**
	 * @author hitesh
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see com.tomsawyer.graphicaldrawing.ui.simple.TSEShapeNodeUI#reset()
	 */
	public void reset() {
		super.reset();
		this.setGradient(RoundRectNodeUI.ERROR_START_COLOR, RoundRectNodeUI.ERROR_END_COLOR);
	}
	
}
