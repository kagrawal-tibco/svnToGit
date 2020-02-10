package com.tibco.cep.dashboard.psvr.data;

import com.tibco.cep.dashboard.psvr.mal.model.types.ThresholdUnitEnum;

public final class Threshold {
	
	public static final Threshold EMPTY_THRESHOLD = new Threshold(-1,ThresholdUnitEnum.COUNT);
	
	private int thresholdValue;
	
	private ThresholdUnitEnum thresholdUnit;
	
	private String stringRepresentation;
	
	private int hashCode;
	
	public Threshold(int thresholdValue, ThresholdUnitEnum thresholdUnit) {
		this.thresholdValue = thresholdValue;
		this.thresholdUnit = thresholdUnit;
		stringRepresentation = String.valueOf(this.thresholdValue);
		if (this.thresholdUnit.equals(ThresholdUnitEnum.COUNT) == false){
			stringRepresentation = stringRepresentation + " " + thresholdUnit.toString();
		}
		hashCode = stringRepresentation.hashCode();
	}

	public int getThresholdValue() {
		return thresholdValue;
	}

	public ThresholdUnitEnum getThresholdUnit() {
		return thresholdUnit;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this){
			return true;
		}
		if (obj instanceof Threshold){
			return ((Threshold)obj).stringRepresentation.equals(stringRepresentation);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	@Override
	public String toString() {
		return stringRepresentation;
	}
	
}