package com.tibco.cep.dashboard.psvr.util;

import java.util.Observable;
import java.util.Observer;

public class PerfMeasurementTracker implements Observer {
	
	private PerformanceMeasurement measurement;
	
	private double sum;
	
	private long count;
	
	private double prevSum;
	
	private long prevCount;
	
	private boolean resetAllowed;
	
	public PerfMeasurementTracker(PerformanceMeasurement measurement){
		this.measurement = measurement;
		this.measurement.addObserver(this);
		resetAllowed = true;
	}
	
	public double getSum() {
		return sum;
	}

	public long getCount() {
		return count;
	}
	
	public double getAverage(){
		if (count == 0){
			return 0.0;
		}
		return sum/count;
	}
	
	public void reset(){
		if (resetAllowed == true) {
			prevSum = measurement.getSum();
			prevCount = measurement.getCount();
			sum = 0;
			count = 0;
			resetAllowed = false;
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		sum = measurement.getSum() - prevSum;
		count = measurement.getCount() - prevCount;
		resetAllowed = true;
	}

}
