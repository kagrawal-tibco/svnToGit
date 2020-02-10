package com.tibco.cep.webstudio.client.process.properties;

/**
 * This class is sued to store time out property
 * 
 * @author dijadhav
 * 
 */
public class TimoutProperty extends Property {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 6132310735479611280L;
	private boolean isEnabled;
	private String expression;
	private String resource;
	private String unit;
	/**
	 * @return the isEnabled
	 */
	public boolean isEnabled() {
		return isEnabled;
	}
	/**
	 * @param isEnabled the isEnabled to set
	 */
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	/**
	 * @return the expression
	 */
	public String getExpression() {
		return expression;
	}
	/**
	 * @param expression the expression to set
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}
	/**
	 * @return the resource
	 */
	public String getResource() {
		return resource;
	}
	/**
	 * @param resource the resource to set
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}
	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}	
}
