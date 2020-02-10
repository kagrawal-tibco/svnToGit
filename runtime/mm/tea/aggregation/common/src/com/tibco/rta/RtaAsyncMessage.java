package com.tibco.rta;

import java.io.Serializable;
import java.util.List;

import com.tibco.rta.model.DataType;

/**
 * 
 * An asynchronous notification object that sends server notifications like exceptions and certain life cycle events
 *
 */
public interface RtaAsyncMessage extends Serializable {
	
	/**
	 * Gets a list of property names in this message.
	 * @return
	 */
    List<String> getProperties();

    /**
     * Gets associated value
     * @param property property name for which value is desired.
     * @return the associated value.
     */
    Object getValue(String property);
    
    /**
     * Gets the data type of the given property.
     * @param propertyName property name whose values data type is desired.
     * @return the property values data type.
     */
    DataType getDataType(String propertyName);

}