package com.tibco.cep.bpmn.ui.graph.model.controller;

import java.util.EventObject;

/**
 * @author pdhar
 *
 */
public class ModelChangedEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8194528016356035182L;

	/**
	 * @param source
	 */
	public ModelChangedEvent(Object source) {
		super(source);
	}

}
