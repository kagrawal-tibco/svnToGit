package com.tibco.cep.dashboard.psvr.vizengine;

import com.tibco.cep.dashboard.psvr.util.PerfMeasurementTracker;

public class VizEngineMXBeanImpl implements VizEngineMXBean {
	
	private static final Object LOCK = new Object();

	private PerfMeasurementTracker modelRequestCountTracker;

	private PerfMeasurementTracker dataRequestCountTracker;

	private PerfMeasurementTracker totalRequestCountTracker;

	public VizEngineMXBeanImpl() {
		modelRequestCountTracker = new PerfMeasurementTracker(VisualizationEngine.getInstance().getModelRequestTime());
		dataRequestCountTracker = new PerfMeasurementTracker(VisualizationEngine.getInstance().getDataRequestTime());
		totalRequestCountTracker = new PerfMeasurementTracker(VisualizationEngine.getInstance().getRequestTime());
	}

	@Override
	public double getCumulativeAverageDataRequestTime() {
		return VisualizationEngine.getInstance().getDataRequestTime().getAverage();
	}

	@Override
	public double getCumulativeAverageModelRequestTime() {
		return VisualizationEngine.getInstance().getModelRequestTime().getAverage();
	}

	@Override
	public double getCumulativeAverageRequestTime() {
		return VisualizationEngine.getInstance().getRequestTime().getAverage();
	}

	@Override
	public long getCumulativeDataRequestCount() {
		return VisualizationEngine.getInstance().getDataRequestTime().getCount();
	}

	@Override
	public long getCumulativeModelRequestCount() {
		return VisualizationEngine.getInstance().getModelRequestTime().getCount();
	}

	@Override
	public long getCumulativeTotalRequestCount() {
		return VisualizationEngine.getInstance().getRequestTime().getCount();
	}

	@Override
	public ProcessingInfo getProcessingInfo() {
		synchronized (LOCK) {
			ProcessingInfo processingInfo = new ProcessingInfo(
				totalRequestCountTracker.getCount(), 
				modelRequestCountTracker.getCount(), 
				dataRequestCountTracker.getCount(), 
				totalRequestCountTracker.getAverage(),
				modelRequestCountTracker.getAverage(), 
				dataRequestCountTracker.getAverage()
			);
			totalRequestCountTracker.reset();
			modelRequestCountTracker.reset();
			dataRequestCountTracker.reset();
			return processingInfo;
		}
	}

	@Override
	public long getDataRequestCount() {
		synchronized (LOCK) {
			long count = dataRequestCountTracker.getCount();
			dataRequestCountTracker.reset();
			return count;
		}
	}

	@Override
	public long getModelRequestCount() {
		synchronized (LOCK) {
			long count = modelRequestCountTracker.getCount();
			modelRequestCountTracker.reset();
			return count;
		}		
	}

	@Override
	public long getTotalRequestCount() {
		synchronized (LOCK) {
			long count = totalRequestCountTracker.getCount();
			totalRequestCountTracker.reset();
			return count;
		}		
	}

	@Override
	public void resetStats() {
		VisualizationEngine.getInstance().getRequestTime().reset();
		VisualizationEngine.getInstance().getModelRequestTime().reset();
		VisualizationEngine.getInstance().getDataRequestTime().reset();
		synchronized (LOCK) {
			modelRequestCountTracker.reset();
			dataRequestCountTracker.reset();
			totalRequestCountTracker.reset();
		}
	}
	
}