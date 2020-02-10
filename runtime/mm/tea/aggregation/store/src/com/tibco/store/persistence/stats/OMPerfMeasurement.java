package com.tibco.store.persistence.stats;

import java.util.Observable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OMPerfMeasurement extends Observable {
	protected Lock lock;
	
	protected String name;
	
	protected boolean enabled;
	
	protected double sum;
	protected long count;
	
	protected OMPerfMeasurement(String name, boolean enabled) {
		this.name = name;
		this.enabled = enabled;
		lock = new ReentrantLock();
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return enabled;
	}
	
	public void add(double value) {
		if (enabled) {
			lock.lock();
			try {
				long time = System.currentTimeMillis();
				updateAvgParameters(time, value);
			}
			finally {
				lock.unlock();
				setChanged();
				notifyObservers();
			}
		}
	}
	
	protected void updateAvgParameters(long time, double value) {
		sum = sum + value;
		count++;
	}
	
	public long getCount() {
		return count;
	}
	
	public double getSum() {
		return sum;
	}
	
	
}
