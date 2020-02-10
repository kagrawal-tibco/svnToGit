package com.tibco.rta;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import com.tibco.rta.model.DataType;
import com.tibco.rta.model.DataTypeMismatchException;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.UndefinedSchemaElementException;


/**
 * 
 * A {@code Fact} is an instance of the schema attributes. A fact could trigger one or more metric computations
 * 
 * Facts will be submitted by the client API to the metric server.
 * The server will perform metric calculation on the facts, as defined in the model.
 * 
 * The dimensions will be aggregated as per the dimension hierarchy defined in the model
 * 
 */
public interface Fact extends Serializable {
	
	/**
	 * For an attribute by this name in the fact, its value represents the name of the asset. 
	 */
	public static final String ASSET_NAME = "asset_name";
	
	/**
	 * For an attribute by this name, in the fact, its value represents the status/state of the asset.
	 */
	public static final String ASSET_STATUS = "asset_status";

	/**
	 * Get its key
	 * @return
	 * @see Key
	 */
	Key getKey();

    /**
     * Sets the attribute value. The name of attribute should be valid, as defined in the schema.
     * @param attrName the attribute name
     * @param value the value depending on the attribute's allowable {@link DataType}
     * @throws UndefinedSchemaElementException
     * @throws DataTypeMismatchException 
     */
	void setAttribute (String attrName, Object value) throws UndefinedSchemaElementException, DataTypeMismatchException;

    /**
     * Sets attributes in bulk. The name of attribute should be valid, as defined in the schema.
     *
     * @throws UndefinedSchemaElementException
     * @throws DataTypeMismatchException
     */
    void setAttributes (Map<String, Object>  attributes) throws UndefinedSchemaElementException, DataTypeMismatchException;
	
	/**
	 * Gets the attribute value.
	 *
	 * @param attrName the name to be used as key to get its value
	 * @return the dimension value
	 */
	Object getAttribute(String attrName);
	
	/**
	 * Get a list of attribute names defined for this fact.
	 * @return a set
	 */
	Collection<String> getAttributeNames();

    /**
     * Clear attributes of this fact.
     */
    void clear();

    /**
     * Return the owner {@link RtaSchema} associated with this fact.
     * @return schema
     */
	RtaSchema getOwnerSchema();

//Moved it to FactImpl. Might consider a MutableFact... not for now.
//    /**
//     *
//     * @return
//     */
//	boolean isNew();
//
//    /**
//     *
//     * @param isNew
//     */
//	void setNew(boolean isNew);
	
}