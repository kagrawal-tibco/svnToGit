/**
 * 
 */
package com.tibco.cep.studio.ui.editors.channels.contoller;

import java.util.List;

/**
 * @author aathalye
 *
 */
public class PropertyConfiguration {
	
	private String propertyName;
	
	private String propertyDisplayName;
	
	private String propertyConfigType;
	
	private List<String> values;
	
	private List<String> displayedValues;
	
	private String defaultValue;
	
	/**
	 * Whether the property should be mandatory
	 */
	private boolean mandatory;
	
	private boolean gvToggle;
	
	/**
	 * If mask property, then asterisk field
	 */
	private boolean mask;
	
	public PropertyConfiguration() {
		
	}

	/**
	 * @param defaultValue
	 * @param displayedValues
	 * @param propertyConfigType
	 * @param propertyName
	 * @param values
	 */
	public PropertyConfiguration(String defaultValue,
			List<String> displayedValues, String propertyConfigType,
			String propertyName, String displayName, List<String> values) {
		super();
		this.defaultValue = defaultValue;
		this.displayedValues = displayedValues;
		this.propertyConfigType = propertyConfigType;
		this.propertyName = propertyName;
		this.propertyDisplayName = displayName;
		this.values = values;
	}

	/**
	 * @return the propertyName
	 */
	public final String getPropertyName() {
		return propertyName;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public final void setDisplayName(String displayName) {
		this.propertyDisplayName = displayName;
	}

	/**
	 * @return the displayName
	 */
	public final String getDisplayName() {
		return this.propertyDisplayName;
	}

	/**
	 * @param propertyName the propertyName to set
	 */
	public final void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * @return the propertyConfigType
	 */
	public final String getPropertyConfigType() {
		return propertyConfigType;
	}

	/**
	 * @param propertyConfigType the propertyConfigType to set
	 */
	public final void setPropertyConfigType(String propertyConfigType) {
		this.propertyConfigType = propertyConfigType;
	}

	/**
	 * @return the values
	 */
	public final List<String> getValues() {
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public final void setValues(List<String> values) {
		this.values = values;
	}

	/**
	 * @return the displayedValues
	 */
	public final List<String> getDisplayedValues() {
		return displayedValues;
	}

	/**
	 * @param displayedValues the displayedValues to set
	 */
	public final void setDisplayedValues(List<String> displayedValues) {
		this.displayedValues = displayedValues;
	}

	/**
	 * @return the defaultValue
	 */
	public final String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue the defaultValue to set
	 */
	public final void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the mandatory
	 */
	public final boolean isMandatory() {
		return mandatory;
	}

	/**
	 * @param mandatory the mandatory to set
	 */
	public final void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
	
	public final boolean isGvToggle() {
		return gvToggle;
	}
	public final void setGvToggle(boolean gvToggle) {
		this.gvToggle= gvToggle;
	}

	public boolean isMask() {
		return mask;
	}

	public void setMask(boolean mask) {
		this.mask = mask;
	}
	
	
}
