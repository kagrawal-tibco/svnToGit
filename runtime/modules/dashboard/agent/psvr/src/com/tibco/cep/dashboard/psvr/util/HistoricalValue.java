package com.tibco.cep.dashboard.psvr.util;

import java.beans.ConstructorProperties;

public final class HistoricalValue {

	private long time;

	private double value;

	@ConstructorProperties({"time","value"})
	public HistoricalValue(long time, double value) {
		super();
		this.time = time;
		this.value = value;
	}

	public long getTime() {
		return time;
	}

	public double getValue() {
		return value;
	}

}