package com.tibco.rta.model.mutable;

import com.tibco.rta.model.DataType;
import com.tibco.rta.model.DuplicateSchemaElementException;
import com.tibco.rta.model.FunctionDescriptor;
import com.tibco.rta.model.UndefinedSchemaElementException;

/**
 * Mutable function descriptor. Allows for modeling a function descriptor.
 * 
 *
 */
public interface MutableFunctionDescriptor extends FunctionDescriptor {
	/**
	 * Set the name of the function
	 * @param name
	 */
	void setName(String name);
	
	/**
	 * Set the category of the function.
	 * @param category
	 */
	void setCategory(String category);
	
	/**
	 * Set the description of the function.
	 * @param description
	 */
	void setDescription(String description);
	
	/**
	 * Set the implementation class of the function. Should extend SingleValueMetricFunction or a MultivalueMetricFunction.
	 * @param implClassName
	 */
	void setImplClass(String implClassName);

	/**
	 * Add a function parameter to the function descriptor.
	 * 
	 * @param functionParam The parameter to add.
	 * 
	 * @throws UndefinedSchemaElementException
	 * @throws DuplicateSchemaElementException
	 */
	void addFunctionParam (FunctionParam functionParam) throws UndefinedSchemaElementException, DuplicateSchemaElementException;

	/**
	 * Adds a function context.
	 * 
	 * @param functionParam function param to add
	 * @throws UndefinedSchemaElementException
	 * @throws DuplicateSchemaElementException
	 */
	void addFunctionContext (FunctionParam functionParam) throws UndefinedSchemaElementException, DuplicateSchemaElementException;

	/**
	 * 
	 * A mutable function parameter. Allows for modelling a function parameter. 
	 *
	 */
	public interface MutableFunctionParam extends FunctionParam {
		/**
		 * Set the name of the parameter.
		 * @param paramName the parameter name to set.
		 */
		void setName(String paramName);
		
		/**
		 * Set the cardinal index of the function parameter. 
		 * @param index the index to set.
		 */
		void setIndex(int index);
		
		/**
		 * Set the data type of the function parameter.
		 * @param dataType the datatype to set.
		 */
		void setDataType(DataType dataType);
		
		/**
		 * Set the description of the function parameter.
		 * @param paramDesc the description to set.
		 */
		void setDescription(String description);
	}
}