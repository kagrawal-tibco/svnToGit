package com.tibco.cep.webstudio.server.ui.custom;

import com.tomsawyer.graphicaldrawing.ui.TSEdgeUI;
import com.tomsawyer.graphicaldrawing.ui.predefined.edge.basic.TSStraightEdgeBuilder;

public class BpmnSequenceEdgeBuilder extends TSStraightEdgeBuilder {
	
	/*
	 * @inheritDoc
	 */
	@Override
	protected TSEdgeUI createEdgeUI() {
		return new BpmnSequenceEdgeUI(false, false, true);
	}

	/**
	 * Java Serialization ID.
	 */
	private static final long serialVersionUID = 1L;
}
