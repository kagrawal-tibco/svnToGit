package com.tibco.cep.dashboard.psvr.util;

import java.util.Observable;

public final class PerformanceMeasurement extends Observable {

	private String name;

	// last ten values with time
	private BoundedList<HistoricalValue> recordedStats;

	// last ten peak values with time
	private BoundedList<HistoricalValue> peakStats;

	// average time
	private double sum;
	private long count;

	public PerformanceMeasurement(String name) {
		this.name = name;
		recordedStats = new BoundedList<HistoricalValue>(10);
		peakStats = new BoundedList<HistoricalValue>(10);
	}

	public synchronized void add(long time, double processingTime) {
		// add to updateTimes
		recordedStats.add(new HistoricalValue(time, processingTime));
		// add to peak list if needed
		HistoricalValue lowestPeak = getLowestPeak();
		if (lowestPeak == null || processingTime > getLowestPeak().getValue()){
			peakStats.add(new HistoricalValue(time, processingTime));
		}
		sum = sum + processingTime;
		count++;
		setChanged();
		notifyObservers();
	}

	public String getName() {
		return name;
	}

	public HistoricalValue[] getSet() {
		return recordedStats.toArray(new HistoricalValue[recordedStats.size()]);
	}
	
	public double getLatestValue(){
		if (recordedStats.isEmpty() == true){
			return -1;
		}
		return recordedStats.get(recordedStats.size()-1).getValue();
	}

	public HistoricalValue[] getPeakSet() {
		return peakStats.toArray(new HistoricalValue[peakStats.size()]);
	}
	
	public HistoricalValue getLatestPeak(){
		if (peakStats.isEmpty() == true){
			return null;
		}
		return peakStats.get(peakStats.size());
	}	
	
	public HistoricalValue getHighestPeak(){
		HistoricalValue highest = null;
		for (HistoricalValue peakStat : peakStats) {
			if (highest == null){
				highest = peakStat;
			}
			else {
				if (peakStat.getValue() > highest.getValue()){
					highest = peakStat;
				}
			}
		}
		return highest;
	}
	
	public HistoricalValue getLowestPeak(){
		HistoricalValue lowest = getHighestPeak();
		for (HistoricalValue peakStat : peakStats) {
			if (lowest == null){
				lowest = peakStat;
			}
			else {
				if (peakStat.getValue() < lowest.getValue()){
					lowest = peakStat;
				}
			}			
		}
		return lowest;
	}	
	
	public double getAverage() {
		if (count == 0){
			return 0.0;
		}
		return sum / count;
	}
	
	public long getCount(){
		return count;
	}
	
	public double getSum(){
		return sum;
	}
	
	public synchronized void reset(){
		recordedStats.clear();
		peakStats.clear();
		sum = 0;
		count = 0;
		setChanged();
		notifyObservers();
	}

}
