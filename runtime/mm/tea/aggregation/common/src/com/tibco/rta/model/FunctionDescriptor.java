package com.tibco.rta.model;

import java.io.Serializable;
import java.util.Collection;

/**
 * 
 * Describes a function used for computation
 *
 */
public interface FunctionDescriptor extends Serializable {

	/**
	 * 
	 * @return Function name.
	 */
	String getName();
	
	/**
	 * Returns the description
	 * @return
	 */
	String getDescription();
	
	/**
	 * Returns the category
	 * @return
	 */
	String getCategory();

	/**
	 * Returns the implementation class. Must extend a SingleValueMetricFunction or a MultiValueMetricFunction
	 * @return
	 */
	String getImplClass();
	
	FunctionParam getFunctionParam(String paramName);
	
	FunctionParam getFunctionParam(int index);

	/**
	 * List of function parameters in the order in which they appear in the "compute" method if the implementation class
	 * @return
	 */
	Collection<FunctionParam> getFunctionParams();
	
	FunctionParam getFunctionContext(String contextName);
	
	FunctionParam getFunctionContext(int index);
	
	Collection<FunctionParam> getFunctionContexts();
	
	/**
	 * 
	 * Describes a funciton parameter
	 *
	 */
	public interface FunctionParam extends Serializable {
		/**
		 * Get the parameter name
		 * @return
		 */
		String getName();
		
		/**
		 * Get its datatype
		 * @return
		 */
		
		DataType getDataType();
		
		/**
		 * Get its positional index as defined in the "compute" method of the implementation class
		 * @return
		 */
		int getIndex();
		
		/**
		 * Get the parameter description
		 * @return
		 */
		String getDescription();
	}
	
	/**
	 * 
	 * Represents a function parameter with a corresponding value.
	 *
	 */
	public interface FunctionParamValue extends FunctionParam {
		
		/**
		 * Gets the value associated with a parameter.
		 * @return the parameter value.
		 */
		public Object getValue();
		
		/**
		 * Sets a value to a function parameter.
		 * @param value
		 * @throws DataTypeMismatchException
		 */
		public void setValue(Object value) throws DataTypeMismatchException;
		
	}

}