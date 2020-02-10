package com.tibco.cep.dashboard.psvr.streaming;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.tibco.cep.dashboard.common.utils.DateTimeUtils;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationData;
import com.tibco.cep.dashboard.timer.ExceptionResistentTimerTask;
import com.tibco.cep.kernel.service.logging.Level;

public class SteadyBurstStreamPublisher extends PeriodicBurstStreamPublisher {

	private boolean isOn;

	private long batchingPeriod;

	private int batchingThreshold;

	private int batchCount;

	private ExceptionResistentTimerTask batchStreamingTask;

	private LinkedBlockingQueue<List<VisualizationData>> batchQueue;

	@Override
	protected void doInit(Properties properties) {
		super.doInit(properties);
		isOn = true;
		batchingPeriod = (Long) StreamingProperties.BATCHING_PERIOD.getValue(properties);
		batchingThreshold = (Integer) StreamingProperties.BATCHING_THRESHOLD.getValue(properties);
		if (period <= DateTimeUtils.SECOND) {
			logger.log(Level.INFO, messageGenerator.getMessage("stream.steadypublisher.toolow.burstperiod", getMessageArgs(null, name, StreamingProperties.STREAMING_PERIOD.getName(), period, DateTimeUtils.SECOND, name)));
			isOn = false;
		}
		if (isOn == true && period <= batchingPeriod) {
			logger.log(Level.INFO, messageGenerator.getMessage("stream.steadypublisher.lesser.burstperiod", getMessageArgs(null, name, StreamingProperties.STREAMING_PERIOD.getName(), period, StreamingProperties.BATCHING_PERIOD.getName(), batchingPeriod)));
			isOn = false;
		}
		batchCount = (int) (period / batchingPeriod);
		if (period % batchingPeriod == 0) {
			// Reduce by one so that always we get one free timer task run [for overlap?]
			batchCount--;
		}
		if (isOn == true) {
			batchQueue = new LinkedBlockingQueue<List<VisualizationData>>();
		}
	}

	@Override
	protected void doStart() {
		if (isOn == false) {
			super.doStart();
			return;
		}
		periodicBurstTask = new BatchComputerTask("Batch Computer Task For " + token);
		StreamingTimerProvider.getInstance().scheduleWithFixedDelay(periodicBurstTask, 0, period, TimeUnit.MILLISECONDS);
		batchStreamingTask = new BatchStreamingTask("Batch Streamer Task For " + token);
		StreamingTimerProvider.getInstance().scheduleWithFixedDelay(batchStreamingTask, 0, batchingPeriod, TimeUnit.MILLISECONDS);
	}

	@Override
	protected void doStop(String message) {
		if (isOn == true) {
			batchStreamingTask.cancel();
			batchStreamingTask = null;
		}
		super.doStop(message);
		return;
	}

	private class BatchComputerTask extends BurstStreamingTask {

		protected BatchComputerTask(String name) {
			super(name);
		}

		@Override
		public void doRun() {
			subscriptionNames.clear();
			consolidatedData.clear();
			if (isRunning() == false) {
				return;
			}
			drainPendingSubscriptions(subscriptionNames);
			if (isRunning() == false) {
				return;
			}
			processAndConsolidate(consolidatedData, subscriptionNames);
			if (isRunning() == false) {
				return;
			}
			ArrayList<VisualizationData> dataset = new ArrayList<VisualizationData>(consolidatedData.values());
			int size = dataset.size();
			if (size != 0) {
				if (size > batchingThreshold) {
					// compute batch size
					int batchQuantity = size / batchingThreshold;
					// adjust the batch quantity to handle fractional values
					if (size % batchingThreshold > 0) {
						batchQuantity++;
					}
					if (logger.isEnabledFor(Level.DEBUG) == true) {
						logger.log(Level.DEBUG, "Creating " + batchQuantity + " batches from " + size + " updates");
					}
					int fromIndex = 0;
					for (int i = 0; i < batchingThreshold; i++) {
						int toIndex = fromIndex + batchQuantity;
						if (toIndex > size) {
							toIndex = size;
						}
						List<VisualizationData> subList = new ArrayList<VisualizationData>(dataset.subList(fromIndex, toIndex));
						if (subList.isEmpty() == true) {
							break;
						}
						if (isRunning() == false) {
							batchQueue.clear();
							return;
						}
						boolean offerSucceeded = batchQueue.offer(subList);
						if (offerSucceeded == false) {
							logger.log(Level.WARN, messageGenerator.getMessage("stream.steadypublisher.batchqueuing.failure", getMessageArgs(null)));
						}
						fromIndex = toIndex;
					}
				} else {
					if (logger.isEnabledFor(Level.DEBUG) == true) {
						logger.log(Level.DEBUG, "Number of updates[" + size + " is less then the batching threshold[" + batchingThreshold + "]");
					}
					streamData(dataset);
				}
			}
		}

	}

	private class BatchStreamingTask extends BurstStreamingTask {

		protected BatchStreamingTask(String name) {
			super(name);
		}

		@Override
		public void doRun() {
			if (isRunning() == false) {
				return;
			}
			List<VisualizationData> data = batchQueue.poll();
			if (data == null || isRunning() == false) {
				return;
			}
			streamData(data);
		}

	}
}
