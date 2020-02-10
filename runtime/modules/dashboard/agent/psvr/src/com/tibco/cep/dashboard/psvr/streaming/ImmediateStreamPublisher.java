package com.tibco.cep.dashboard.psvr.streaming;

import java.io.IOException;
import java.net.SocketException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.ogl.OGLException;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationData;
import com.tibco.cep.kernel.service.logging.Level;

public class ImmediateStreamPublisher extends StreamPublisher implements Runnable {

	private static ThreadGroup THREAD_GROUP = new ThreadGroup("ImmediateStreamPublishers");

	// INFO we may want to provide configuration variables eventually (hint using license to limit concurrent users)
	private static final ExecutorService PUBSLISHER_THREAD_POOL = Executors.newCachedThreadPool(new ThreadFactory() {

		@Override
		public Thread newThread(Runnable r) {
			THREAD_GROUP.activeCount();
			return new Thread(THREAD_GROUP, r, "Publisher-" + (THREAD_GROUP.activeCount() + 1));

		}

	});

	// provides means to indicator end of queue/processing
	private static final String EOQ = "EOQ";

	// the unbounded queue which holds updated subscription names
	private ArrayBlockingQueue<String> queue;

	// indicates the shutdown message to be sent as the final message
	private String shutdownMessage;

	// a list to drain all pending subscriptions from queue when shutting down
	private LinkedList<String> pendingSubscriptions;
	
	// a set for keeping tracking of paused pending subscriptions
	private Set<String> pausedPendingSubscriptions;

	ImmediateStreamPublisher() {
	}

	@Override
	protected void doInit(Properties properties) {
		queue = new ArrayBlockingQueue<String>((Integer)StreamingProperties.STREAMING_BUFFER_SIZE.getValue(properties));
		pendingSubscriptions = new LinkedList<String>();
		pausedPendingSubscriptions = new HashSet<String>();
	}

	@Override
	protected void lineUpForProcessing(String subscriptionName) {
		// we use offer to avoid waiting for the queue to have space
		boolean offerSucceeded = queue.offer(subscriptionName);
		if (offerSucceeded == false) {
			logger.log(Level.WARN, messageGenerator.getMessage("stream.publisher.queuing.failure", getMessageArgs(null, name, subscriptionName)));
		}
	}

	@Override
	protected void doStart() {
		PUBSLISHER_THREAD_POOL.execute(this);
	}
	
	@Override
	protected void doPause() {
		synchronized (queue) {
			queue.drainTo(pausedPendingSubscriptions);
		}
	}
	
	@Override
	protected void doResume() {
		synchronized (queue) {
			queue.addAll(pausedPendingSubscriptions);
			pausedPendingSubscriptions.clear();
		}
	}

	@Override
	protected void doStop(String message) {
		if (StringUtil.isEmptyOrBlank(message) == false) {
			shutdownMessage = message;
		}
		synchronized (queue) {
			queue.drainTo(pendingSubscriptions);
			queue.add(EOQ);
		}
	}

	@Override
	public void run() {
		try {
			while (isRunning() == true) {
				String str = null;
				try {
					str = queue.take();
					if (str != EOQ) {
						List<VisualizationData> dataToBePublished = process(str);
						if (isRunning() == false){
							return;
						}						
						String xml = marshall(dataToBePublished);
						if (isRunning() == false){
							return;
						}						
						if (logger.isEnabledFor(Level.DEBUG) == true) {
							logger.log(Level.DEBUG, "Streaming " + xml + " on " + streamer);
						}
						streamer.stream(xml);
					}
				} catch (InterruptedException e) {
					String msg = messageGenerator.getMessage("stream.immediatepublisher.dequeuing.failure", getMessageArgs(null, name));
					exceptionHandler.handleException(msg, e, isRunning() == true ? Level.WARN : Level.DEBUG);
				} catch (IOException e) {
					if (e.getCause() instanceof SocketException){
						stop(shutdownMessage);
					}
					else {
						String msg = messageGenerator.getMessage("stream.publisher.io.failure", getMessageArgs(e, name, str));
						exceptionHandler.handleException(msg, e, isRunning() == true ? Level.WARN : Level.DEBUG);
					}
				} catch (StreamingException e) {
					String msg = messageGenerator.getMessage("stream.publisher.streaming.failure", getMessageArgs(e, name, str));
					exceptionHandler.handleException(msg, e, isRunning() == true ? Level.WARN : Level.DEBUG);
				} catch (OGLException e) {
					String msg = messageGenerator.getMessage("stream.publisher.marshalling.failure", getMessageArgs(e, name, str));
					exceptionHandler.handleException(msg, e, isRunning() == true ? Level.WARN : Level.DEBUG);
				}
			}
		} finally {
			if (shutdownMessage != null) {
				if (logger.isEnabledFor(Level.DEBUG) == true) {
					logger.log(Level.DEBUG, "Attempting to send " + shutdownMessage + " on " + streamer);
				}
				try {
					streamer.stream(shutdownMessage);
				} catch (IOException ignoreex) {
				}
			}			
			streamer.close();
		}
	}

	@Override
	protected List<String> getPendingSubscriptionUpdates() {
		synchronized (queue) {
			List<String> pendingUpdates = new LinkedList<String>();
			queue.drainTo(pendingUpdates);
			return pendingUpdates;
		}
	}

	@Override
	protected void batchLineUpForProcessing(List<String> subscriptionNames) {
		queue.addAll(subscriptionNames);
	}

}