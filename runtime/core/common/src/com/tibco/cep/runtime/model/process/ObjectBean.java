package com.tibco.cep.runtime.model.process;


public interface ObjectBean  {

	public static enum BeanOp {

		BEAN_CREATED,

		BEAN_UPDATED,

		BEAN_DELETED;

	
	}

	String getKey();

	BeanOp getBeanOp();
	
	Class getType();

}
