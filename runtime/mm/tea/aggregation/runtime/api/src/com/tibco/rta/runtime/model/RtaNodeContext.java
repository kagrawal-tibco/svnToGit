package com.tibco.rta.runtime.model;

import java.io.Serializable;


/**
 * Each metric node may need to store some extra contextual data associated with the
 * computation of its value. This data is stored as tuples in the {@code RtaContextNode} in a generic manner
 * 
 * This class is a container of these data tuples.
 * 
 */

public interface RtaNodeContext extends Serializable {
	
	/**
	 * 
	 * @return The keys associated with discreet contextual data using which this data will be accessed
	 */
	String[] getTupleNames();
	
	/**
	 * 
	 * @param key The key to use to query the context data
	 * @return The context data associated with this key.
	 */
	Object getTupleValue(String key);
	
	/**
	 * Used to store a key/value as context data
	 * @param name Key to use to store the context data
	 * @param value The data to use to store with this key
	 */
	void setTuple(String name, Object value);
	
	
	RtaNodeContext deepCopy();

}
