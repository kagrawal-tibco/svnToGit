package com.tibco.cep.webstudio.server.ui.custom;

import com.tomsawyer.graphicaldrawing.ui.TSEdgeUI;
import com.tomsawyer.graphicaldrawing.ui.predefined.edge.activity.TSNoteAssociationEdgeBuilder;
import com.tomsawyer.util.shared.TSAttributedObject;

public class BpmnAssociationEdgeBuilder extends TSNoteAssociationEdgeBuilder {

	@Override
	protected void initDefaultAttributes(TSAttributedObject tsattributedobject) {
		// TODO Auto-generated method stub
		super.initDefaultAttributes(tsattributedobject);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3206897549553484640L;

	@Override
	protected TSEdgeUI createEdgeUI() {
		
		return new BpmnAssociationEdgeUI(false, false, false);
	}
	
}
