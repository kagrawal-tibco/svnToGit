package com.tibco.be.model.functions.impl;

import com.tibco.be.model.functions.Predicate;

public interface ModelFunction extends Predicate {

	/**
	 *
	 * @return
	 */
	public /*Entity*/Object[] getEntityArguments();

	/**
	 *
	 * @return
	 */
	public /*Entity*/Object getEntityReturnType();

	public /*Entity*/Object getModel();
	
	public String getModelClass();

}