package com.tibco.cep.webstudio.client.process.validataion;

import java.io.Serializable;

/**
 * This class is used to hold process validation record.
 * 
 * @author dijadhav
 * 
 */
public class Validation implements Serializable {
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -2332334399241744269L;
	private ValidationErrorType validationErrorType;
	private String location;

	/**
	 * @return the validationErrorType
	 */
	public ValidationErrorType getValidationErrorType() {
		return validationErrorType;
	}

	/**
	 * @param validationErrorType
	 *            the validationErrorType to set
	 */
	public void setValidationErrorType(ValidationErrorType validationErrorType) {
		this.validationErrorType = validationErrorType;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

}
