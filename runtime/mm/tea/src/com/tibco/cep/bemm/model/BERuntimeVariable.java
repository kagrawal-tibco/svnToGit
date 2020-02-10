package com.tibco.cep.bemm.model;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tibco.cep.bemm.model.impl.BERuntimeVariableImpl;

/**
 * This interface is used to get details of Global or System variable.
 * 
 * @author dijadhav
 *
 */
@JsonDeserialize(as = BERuntimeVariableImpl.class)
public interface BERuntimeVariable extends Serializable {
	String getName();

	void setName(String name);

	String getValue();

	void setValue(String value);

	String getDefaultValue();

	void setDefaultValue(String defaultValue);

}