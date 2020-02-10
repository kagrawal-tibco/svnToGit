package com.tibco.cep.dashboard.psvr.streaming;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.management.STATE;
import com.tibco.cep.dashboard.psvr.ogl.OGLException;
import com.tibco.cep.dashboard.psvr.ogl.OGLMarshaller;
import com.tibco.cep.dashboard.psvr.ogl.model.UpdatePacket;
import com.tibco.cep.dashboard.psvr.ogl.model.UpdatePackets;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationData;
import com.tibco.cep.dashboard.psvr.util.PerformanceMeasurement;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Logger;

//PORT should we add a distinct destroy ?
public abstract class StreamPublisher {

	protected Logger logger;

	protected ExceptionHandler exceptionHandler;

	protected MessageGenerator messageGenerator;

	protected String name;

	protected SecurityToken token;

	// protected Principal preferredPrincipal;

	protected Streamer streamer;

	protected SubscriptionHandlerIndex subscriptionHandlerIndex;

	// indicates publisher state
	protected STATE state;
	
	// protected String sessionName;

	// stats
	protected PerformanceMeasurement updateProcessingTime;

	protected PerformanceMeasurement marshallingTime;

	private String type;

	protected StreamPublisher() {
		state = STATE.UNINITIALIZED;
		updateProcessingTime = new PerformanceMeasurement("Update Processing Time");
		marshallingTime = new PerformanceMeasurement("Marshalling Time");
	}
	
	protected final void init(Properties properties) {
		if (state.compareTo(STATE.UNINITIALIZED) == 0) {
			doInit(properties);
			state = STATE.INITIALIZED;
		}
	}

	protected abstract void doInit(Properties properties);

	protected final void start() {
		if (state.compareTo(STATE.INITIALIZED) == 0 || state.compareTo(STATE.STOPPED) == 0) {
			doStart();
			state = STATE.RUNNING;
		}
	}

	protected abstract void doStart();

	protected abstract void lineUpForProcessing(String subscriptionName);
	
	protected final void pause(String message){
		if (state.compareTo(STATE.RUNNING) == 0) {
			doPause();
			state = STATE.PAUSED;
//			try {
//				streamer.stream(message);
//			} catch (IOException e) {
//				String msg = messageGenerator.getMessage("stream.publisher.io.failure", getMessageArgs(e, name, message));
//				exceptionHandler.handleException(msg, e, Level.WARN);
//			}
		}		
	}
	
	protected abstract void doPause();
	
	protected final void resume(String message){
		if (state.compareTo(STATE.PAUSED) == 0) {
//			try {
//				streamer.stream(message);
//			} catch (IOException e) {
//				String msg = messageGenerator.getMessage("stream.publisher.io.failure", getMessageArgs(e, name, message));
//				exceptionHandler.handleException(msg, e, Level.WARN);
//			}
			state = STATE.RUNNING;
			doResume();
		}		
	}
	
	protected abstract void doResume();	
	
	protected final void stop(String message) {
		if (state.compareTo(STATE.RUNNING) == 0) {
			state = STATE.STOPPED;
			doStop(message);
		}
	}

	protected abstract void doStop(String message);
	
	protected abstract List<String> getPendingSubscriptionUpdates();

	protected abstract void batchLineUpForProcessing(List<String> subscriptionNames);

	protected List<VisualizationData> process(String subscriptionName) throws StreamingException {
		long stime = System.currentTimeMillis();
		try {
			List<VisualizationData> data = new LinkedList<VisualizationData>();
			Collection<SubscriptionHandler> handlers = subscriptionHandlerIndex.getHandlers(subscriptionName);
			for (SubscriptionHandler subscriptionHandler : handlers) {
				if (isRunning() == false) {
					return Collections.emptyList();
				}
				VisualizationData[] processedData = subscriptionHandler.processUpdate(subscriptionName);
				data.addAll(Arrays.asList(processedData));
			}
			return data;
		} finally {
			long etime = System.currentTimeMillis();
			long timeTaken = etime - stime;
			updateProcessingTime.add(System.currentTimeMillis(), timeTaken);
		}
	}

	protected String marshall(Collection<VisualizationData> visualizationDataSet) throws OGLException {
		long stime = System.currentTimeMillis();
		try {
			UpdatePackets packetSet = new UpdatePackets();
			for (VisualizationData data : visualizationDataSet) {
				UpdatePacket packet = new UpdatePacket();
				packet.setVisualizationData(data);
				packetSet.addUpdatePacket(packet);
			}
			return OGLMarshaller.getInstance().marshall(token, packetSet);
		} finally {
			long etime = System.currentTimeMillis();
			long timeTaken = etime - stime;
			marshallingTime.add(System.currentTimeMillis(), timeTaken);
		}
	}

	protected String marshall(VisualizationData data) throws OGLException {
		long stime = System.currentTimeMillis();
		try {		
			UpdatePackets packetSet = new UpdatePackets();
			UpdatePacket packet = new UpdatePacket();
			packet.setVisualizationData(data);
			packetSet.addUpdatePacket(packet);
			return OGLMarshaller.getInstance().marshall(token, packetSet);
		} finally {
			long etime = System.currentTimeMillis();
			long timeTaken = etime - stime;
			marshallingTime.add(System.currentTimeMillis(), timeTaken);
		}
	}

	protected MessageGeneratorArgs getMessageArgs(Throwable t, Object... args) {
		return new MessageGeneratorArgs(token.toString(), token.getUserID(), token.getPreferredPrincipal(), token.getPrincipals(), t, args);
	}

	boolean isRunning() {
		return state.equals(STATE.RUNNING) || state.equals(STATE.PAUSED); 
	}
	
	void setType(String type){
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	PerformanceMeasurement getUpdateProcessingTime(){
		return updateProcessingTime;
	}
	
	PerformanceMeasurement getMarshallingTime(){
		return marshallingTime;
	}
	
	PerformanceMeasurement getStreamingTime(){
		return streamer.streamingTime;
	}
	
	

}