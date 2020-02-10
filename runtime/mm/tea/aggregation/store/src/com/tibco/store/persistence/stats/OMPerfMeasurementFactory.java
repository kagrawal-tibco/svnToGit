package com.tibco.store.persistence.stats;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class OMPerfMeasurementFactory {
	private static OMPerfMeasurementFactory instance;	
	ConcurrentHashMap<String, OMPerfMeasurement> measurementsMap;
	private boolean enableMeasurements;
	
	public static final synchronized OMPerfMeasurementFactory getInstance() {
		if (instance == null) {
			instance = new OMPerfMeasurementFactory();
		}
		return instance;
	}
	
	private OMPerfMeasurementFactory() {
		measurementsMap = new ConcurrentHashMap<String, OMPerfMeasurement>();
		enableMeasurements = true;
	}
	
	void setMeasurementsEnabled(boolean enabled) {
		enableMeasurements = enabled;
		for (OMPerfMeasurement measurement : measurementsMap.values()	) {
			measurement.setEnabled(enabled);
		}
	}

	boolean isMeasurementsEnabled(){
		return enableMeasurements;
	}
	
	public OMPerfMeasurement createPerformanceMeasurement(String name) {
		OMPerfMeasurement measurement = new OMPerfMeasurement(name, enableMeasurements);
		OMPerfMeasurement prevMeasurement = measurementsMap.putIfAbsent(name, measurement);
		if (prevMeasurement != null) {
			throw new IllegalArgumentException(String.format("%s is already used", name));
		}
		return measurement;		
	}
	
	Collection<OMPerfMeasurement> measurements() {
		return measurementsMap.values();
	}

}
