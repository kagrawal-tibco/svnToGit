package com.tibco.cep.webstudio.client.decisiontable.model;

/**
 * 
 * @author sasahoo
 *
 */
public class Range extends DomainEntry {

	protected static final String LOWER_EDEFAULT = null;

	protected String lower = LOWER_EDEFAULT;

	protected static final String UPPER_EDEFAULT = null;
	protected String upper = UPPER_EDEFAULT;

	protected static final boolean LOWER_INCLUSIVE_EDEFAULT = false;
	protected boolean lowerInclusive = LOWER_INCLUSIVE_EDEFAULT;

	protected static final boolean UPPER_INCLUSIVE_EDEFAULT = false;
	protected boolean upperInclusive = UPPER_INCLUSIVE_EDEFAULT;
	
	public Range(String value, String desc) {
		super(value, desc);
	}


	public String getLower() {
		return lower;
	}

	public void setLower(String newLower) {
		lower = newLower;
	}

	public String getUpper() {
		return upper;
	}

	public void setUpper(String newUpper) {
		upper = newUpper;
	}

	public boolean isLowerInclusive() {
		return lowerInclusive;
	}

	public void setLowerInclusive(boolean newLowerInclusive) {
		lowerInclusive = newLowerInclusive;
	}

	public boolean isUpperInclusive() {
		return upperInclusive;
	}

	public void setUpperInclusive(boolean newUpperInclusive) {
		upperInclusive = newUpperInclusive;
	}
}