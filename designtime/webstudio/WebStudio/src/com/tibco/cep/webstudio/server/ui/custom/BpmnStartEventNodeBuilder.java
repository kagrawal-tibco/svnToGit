package com.tibco.cep.webstudio.server.ui.custom;

import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.ui.TSNodeUI;
import com.tomsawyer.util.shared.TSAttributedObject;


public class BpmnStartEventNodeBuilder extends BpmnBaseStartEventNodeBuilder
{
	/* 
	 * @inheritDoc
	 */
	@Override
	protected void initDefaultAttributes(TSAttributedObject o)
	{

		this.setSize(20, 20);
		this.setResizability(TSESolidObject.RESIZABILITY_LOCKED);

		super.initDefaultAttributes(o);
	}


	/* 
	 * @inheritDoc
	 */
	@Override
	protected TSNodeUI createNodeUI()
	{
		return new BpmnStartEventNodeUI();
	}
	
	/**
	 * Java Serialization ID.
	 */
	private static final long serialVersionUID = 1L;
}
