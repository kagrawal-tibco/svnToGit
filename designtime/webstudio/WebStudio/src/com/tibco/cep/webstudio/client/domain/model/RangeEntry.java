package com.tibco.cep.webstudio.client.domain.model;

/**
 * @author vdhumal
 *
 */
public class RangeEntry extends DomainEntry {

	private String lower;
	private String upper;
	private boolean lowerInclusive;
	private boolean upperInclusive;

	public RangeEntry() {
		lower = "Undefined";
		upper = "Undefined";
		lowerInclusive = false;
		upperInclusive = false;
	}
	
	public String getLower() {
		return lower;
	}
	public void setLower(String lower) {
		this.lower = lower;
	}
	public String getUpper() {
		return upper;
	}
	public void setUpper(String upper) {
		this.upper = upper;
	}
	public boolean isLowerInclusive() {
		return lowerInclusive;
	}
	public void setLowerInclusive(boolean lowerInclusive) {
		this.lowerInclusive = lowerInclusive;
	}
	public boolean isUpperInclusive() {
		return upperInclusive;
	}
	public void setUpperInclusive(boolean upperInclusive) {
		this.upperInclusive = upperInclusive;
	}
}
