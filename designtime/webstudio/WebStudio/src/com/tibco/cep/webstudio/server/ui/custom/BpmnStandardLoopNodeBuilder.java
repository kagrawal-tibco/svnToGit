package com.tibco.cep.webstudio.server.ui.custom;

import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.ui.TSNodeUI;
import com.tomsawyer.graphicaldrawing.ui.TSPNodeBuilder;
import com.tomsawyer.util.shared.TSAttributedObject;

public class BpmnStandardLoopNodeBuilder extends TSPNodeBuilder {
	/*
	 * @inheritDoc
	 */
	@Override
	protected void initDefaultAttributes(TSAttributedObject o) {

		this.setSize(20, 20);
		this.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
		this.setExpandedResizability(TSESolidObject.RESIZABILITY_LOCKED);
	}

	/*
	 * @inheritDoc
	 */
	@Override
	protected TSNodeUI createNodeUI() {
		return new BpmnStandardLoopNodeUI();
	}

	/**
	 * Java Serialization ID.
	 */
	private static final long serialVersionUID = 1L;
}
