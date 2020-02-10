package com.tibco.cep.bemm.model;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tibco.cep.bemm.model.impl.SummaryImpl;

/**
 * Summary of up/down instances
 * 
 * @author dijadhav
 *
 */
@JsonDeserialize(as = SummaryImpl.class)
public interface Summary extends Serializable {
	/**
	 * Get the count of up instances.
	 * 
	 * @return
	 */
	int getUpInstances();

	/**
	 * Set the count of up instances.
	 * 
	 * @return
	 */
	void setUpInstances(int upInstances);

	/**
	 * Get the count of down instances.
	 * 
	 * @return
	 */
	int getDownInstances();

	/**
	 * Set the count of down instances.
	 * 
	 * @return
	 */
	void setDownInstances(int downInstances);
	
	/**
	 * Get the count of intermediate instances.
	 * 
	 * @return
	 */
	int getIntermediateStateInstances();

	/**
	 * Set the count of intermediate state instances.
	 * 
	 * @return
	 */
	void setIntermediateStateInstances(int intermediateStateInstances);


}
