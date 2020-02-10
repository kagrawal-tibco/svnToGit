package com.tibco.cep.dashboard.psvr.streaming;

import java.io.IOException;
import java.net.SocketException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.ogl.OGLException;
import com.tibco.cep.dashboard.psvr.ogl.model.DataColumn;
import com.tibco.cep.dashboard.psvr.ogl.model.DataRow;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationData;
import com.tibco.cep.dashboard.timer.ExceptionResistentTimerTask;
import com.tibco.cep.kernel.service.logging.Level;

public class PeriodicBurstStreamPublisher extends StreamPublisher {

	//the initial capacity of the queue
	private int streamingBufferSize;

	//the lock to synchronize access to the queue
	private ReentrantLock lock;

	// the set which holds a unique list of updated subscription names
	private Set<String> queue;

	// a list to drain all pending subscriptions from queue when shutting down
	protected LinkedList<String> pendingSubscriptions;

	protected long period;

	protected ExceptionResistentTimerTask periodicBurstTask;

	// a set for keeping tracking of paused pending subscriptions
	private Set<String> pausedPendingSubscriptions;

	public PeriodicBurstStreamPublisher() {
	}

	@Override
	protected void doInit(Properties properties) {
		period = (Long) StreamingProperties.STREAMING_PERIOD.getValue(properties);
		lock = new ReentrantLock();
		streamingBufferSize = (Integer)StreamingProperties.STREAMING_BUFFER_SIZE.getValue(properties);
		queue = new HashSet<String>(streamingBufferSize);
		pendingSubscriptions = new LinkedList<String>();
		pausedPendingSubscriptions = new HashSet<String>();
	}

	@Override
	protected void lineUpForProcessing(String subscriptionName) {
		//acquire a lock
		lock.lock();
		try {
			//add the subscription
			boolean added = queue.add(subscriptionName);
			//check if we added the subscriptionName and the current queue size is more then the stream buffer size
			if (added == true && queue.size() > streamingBufferSize) {
				//yes, it is show a warning
				logger.log(Level.INFO, messageGenerator.getMessage("stream.publisher.queue.size.update", getMessageArgs(null, subscriptionName, streamingBufferSize, queue.size())));
			}
		} finally {
			//release the lock
			lock.unlock();
		}
	}

	@Override
	protected void batchLineUpForProcessing(List<String> subscriptionNames) {
		queue.addAll(subscriptionNames);
	}

	@Override
	protected List<String> getPendingSubscriptionUpdates() {
		//acquire a lock
		lock.lock();
		try {
			return new LinkedList<String>(queue);
		} finally {
			//release the lock
			lock.unlock();
		}
	}

	@Override
	protected void doStart() {
		periodicBurstTask = new BurstStreamingTask("Periodic Streamer Task For "+token);
		StreamingTimerProvider.getInstance().scheduleWithFixedDelay(periodicBurstTask, 0, period, TimeUnit.MILLISECONDS);
	}

	@Override
	protected void doPause() {
		//acquire a lock
		lock.lock();
		try {
			pausedPendingSubscriptions.addAll(queue);
			queue.clear();
		} finally {
			//release the lock
			lock.unlock();
		}
	}

	@Override
	protected void doResume() {
		//acquire a lock
		lock.lock();
		try {
			queue.addAll(pausedPendingSubscriptions);
			pausedPendingSubscriptions.clear();
		} finally {
			//release the lock
			lock.unlock();
		}
	}

	@Override
	protected void doStop(String message) {
		//acquire a lock
		lock.lock();
		try {
			pendingSubscriptions.addAll(queue);
			queue.clear();
			periodicBurstTask.cancel();
			periodicBurstTask = null;
			if (logger.isEnabledFor(Level.DEBUG) == true){
				logger.log(Level.DEBUG,"Shutting down by sending "+message+" on "+streamer);
			}
			if (StringUtil.isEmptyOrBlank(message) == false) {
				try {
					streamer.stream(message);
				} catch (IOException e) {
					String msg = messageGenerator.getMessage("stream.publisher.io.failure",  getMessageArgs(e,message));
					exceptionHandler.handleException(msg, e, Level.WARN);
				}
			}
			streamer.close();
		} finally {
			//release the lock
			lock.unlock();
		}
	}

	protected void consolidateData(Map<String,VisualizationData> consolidatedData,List<VisualizationData> dataList) throws StreamingException{
		for (VisualizationData data : dataList) {
			VisualizationData existingVizData = consolidatedData.get(data.getComponentID());
			if (existingVizData == null){
				consolidatedData.put(data.getComponentID(),data);
			}
			else{
                String visDataAsStr = null;
                if (logger.isEnabledFor(Level.DEBUG) == true) {
                    visDataAsStr = "VisualizationData[id="+data.getComponentID()+",rowcnt="+data.getDataRowCount()+"]";
                    logger.log(Level.DEBUG, "Reusing UpdatePacket for "+visDataAsStr+" for "+token);
                }
                DataRow[] existingDataRows = existingVizData.getDataRow();
                DataRow[] newDataRows = data.getDataRow();
                for (int j = 0; j < newDataRows.length; j++) {
                    DataRow newDataRow = newDataRows[j];
                    int existingDataRowIdx = 0;
                    while( existingDataRowIdx < existingDataRows.length) {
                        DataRow existingDataRow = existingDataRows[existingDataRowIdx];
                        String existingDataRowAsStr = "datarow[id="+existingDataRow.getId()+",templateid="+existingDataRow.getTemplateID()+"]";
                        //chart accepts visualization data with data row template ids as the series id where as all the other components have the id as the unique differentiator
                        if (newDataRow.getTemplateID().equals(existingDataRow.getTemplateID()) == true && newDataRow.getId().equals(existingDataRow.getId()) == true){
                            DataColumn[] newDataCols = newDataRow.getDataColumn();
                            for (int l = 0; l < newDataCols.length; l++) {
                                DataColumn newDataCol = newDataCols[l];
                                DataColumn[] existingDataCols = existingDataRow.getDataColumn();
                                int existingDataColIdx = 0;
                                while (existingDataColIdx < existingDataCols.length) {
                                    if ( newDataCol.getId().equals(existingDataCols[existingDataColIdx].getId()) == true ){
                                        existingDataRow.setDataColumn(existingDataColIdx, newDataCol);
                                        if (logger.isEnabledFor(Level.DEBUG) == true) {
                                            String existingDataColAsStr = "datacol[id="+existingDataCols[existingDataColIdx].getId()+"]";
                                            String newDataColAsStr = "datacol[id="+newDataCol.getId()+"]";
                                            logger.log(Level.DEBUG, "Replacing "+existingDataColAsStr+" with "+newDataColAsStr+" in "+existingDataRowAsStr+" under VisualizationData[id="+existingVizData.getComponentID()+"] for "+token);
                                        }
                                        break;
                                    }
                                    existingDataColIdx++;
                                }
                                if (existingDataColIdx == existingDataCols.length){
                                    if (logger.isEnabledFor(Level.DEBUG) == true) {
                                        String newDataColAsStr = "datacol[id="+newDataCol.getId()+"]";
                                        logger.log(Level.DEBUG, "Adding "+newDataColAsStr+" in "+existingDataRowAsStr+" under VisualizationData[id="+existingVizData.getComponentID()+"] for "+token);
                                    }
                                    existingDataRow.addDataColumn(newDataCol);
                                }
                            }
                            break;
                        }
                        existingDataRowIdx++;
                    }
                    if (existingDataRowIdx == existingDataRows.length){
                        existingVizData.addDataRow(newDataRow);
                        if (logger.isEnabledFor(Level.DEBUG) == true) {
                            String currDataRowAsStr = "datarow[id="+newDataRow.getId()+",templateid="+newDataRow.getTemplateID()+"]";
                            logger.log(Level.DEBUG, "Adding "+currDataRowAsStr+" under "+visDataAsStr+" for "+token);
                        }
                    }
                }

			}
		}
	}


	protected class BurstStreamingTask extends ExceptionResistentTimerTask {

		protected LinkedList<String> subscriptionNames = new LinkedList<String>();
		protected Map<String,VisualizationData> consolidatedData = new HashMap<String, VisualizationData>();

		protected BurstStreamingTask(String name) {
			super(name, PeriodicBurstStreamPublisher.this.logger, PeriodicBurstStreamPublisher.this.exceptionHandler, true);
		}


		@Override
		public void doRun() {
			subscriptionNames.clear();
			consolidatedData.clear();
			if (isRunning() == false){
				return;
			}
			drainPendingSubscriptions(subscriptionNames);
			if (isRunning() == false){
				return;
			}
			if (subscriptionNames.isEmpty() == false) {
				processAndConsolidate(consolidatedData,subscriptionNames);
				if (isRunning() == false){
					return;
				}
				streamData(consolidatedData.values());
			}
		}

		protected void streamData(Collection<VisualizationData> dataset) {
			String xml = null;
			try {
				xml = marshall(dataset);
			} catch (OGLException e) {
				String msg = messageGenerator.getMessage("stream.publisher.marshalling.failure",  getMessageArgs(e));
				exceptionHandler.handleException(msg, e, Level.WARN);
			}
			if (isRunning() == false){
				return;
			}
			if (xml != null) {
				try {
					if (logger.isEnabledFor(Level.DEBUG) == true){
						logger.log(Level.DEBUG,"Streaming "+xml+" from "+dataset.size()+" upates on "+streamer);
					}
					streamer.stream(xml);
				} catch (IOException e) {
					if (e.getCause() instanceof SocketException){
						stop(null);
					}
					else {
						String msg = messageGenerator.getMessage("stream.publisher.io.failure", getMessageArgs(e, name, xml));
						exceptionHandler.handleException(msg, e, isRunning() == true ? Level.WARN : Level.DEBUG);
					}
				}
			}
		}

		protected void drainPendingSubscriptions(Collection<String> subscriptions) {
			//acquire lock
			lock.lock();
			try {
				if (isRunning() == false){
					return;
				}
				subscriptions.addAll(queue);
				queue.clear();
				if (logger.isEnabledFor(Level.DEBUG) == true){
					logger.log(Level.DEBUG,"Extracted "+subscriptions.size()+" update notifications for "+name);
				}
			} finally {
				//release the lock
				lock.unlock();
			}
		}

		protected void processAndConsolidate(Map<String,VisualizationData> consolidationMap, Collection<String> subscriptions) {
			for (String subscriptionName : subscriptions) {
				try {
					consolidateData(consolidationMap,process(subscriptionName));
				} catch (StreamingException e) {
					String msg = messageGenerator.getMessage("stream.publisher.streaming.failure",  getMessageArgs(e,subscriptionName));
					exceptionHandler.handleException(msg, e, Level.WARN);
				}
				if (isRunning() == false){
					return;
				}
			}
			if (logger.isEnabledFor(Level.DEBUG) == true){
				logger.log(Level.DEBUG,"Consolidated "+subscriptions.size()+" notifications into "+consolidationMap.size()+" updates for "+name);
			}
		}

	}
}
