package com.tibco.cep.dashboard.psvr.streaming;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.management.STATE;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.data.DataSourceHandler;
import com.tibco.cep.dashboard.psvr.data.Updateable;
import com.tibco.cep.dashboard.psvr.plugin.AbstractHandler;
import com.tibco.cep.dashboard.psvr.util.PerformanceMeasurement;
import com.tibco.cep.kernel.service.logging.Level;

public abstract class DataSourceUpdateHandler extends AbstractHandler {

	//the data-source handler which needs to be updated
	protected DataSourceHandler dataSourceHandler;
	//the unique name of the data-source
	protected String subscriptionName;

	//state variables
	protected boolean subscribed;
	protected STATE state;

	//updates specific variables
	protected Set<Channel> interestedChannels;

	//stats
	protected PerformanceMeasurement updateProcessingTime;

	protected PerformanceMeasurement resetProcessingTime;

	protected DataSourceUpdateHandler(){
		subscribed = false;
		state = STATE.STOPPED;
		interestedChannels = new HashSet<Channel>();
		updateProcessingTime = new PerformanceMeasurement("Update Processing Time");
		resetProcessingTime = new PerformanceMeasurement("Reset Processing Time");
	}

	protected void setDataSourceHandler(DataSourceHandler dataSourceHandler){
		this.dataSourceHandler = dataSourceHandler;
//		this.context = this.dataSourceHandler.getContext();
		this.subscriptionName = this.dataSourceHandler.getUniqueName();
	}

	public String getSubscriptionName(){
		return subscriptionName;
	}

	public void subscribe() throws StreamingException{
		if (subscribed == false){
			if (logger.isEnabledFor(Level.DEBUG) == true){
				logger.log(Level.DEBUG,"Attempting to subscribe %s", this.toString());
			}
			handleSubscriptionRequest();
			subscribed = true;
		}
	}

	protected abstract void handleSubscriptionRequest() throws StreamingException;

	public void unsubscribe() throws StreamingException{
		if (subscribed == true){
			if (logger.isEnabledFor(Level.DEBUG) == true){
				logger.log(Level.DEBUG,"Attempting to unsubscribe %s", this.toString());
			}
			DataUpdatesCommunicator.getInstance().unsubscribe(subscriptionName);
			subscribed = false;
		}
	}

	public void startNotifying(Channel channel){
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "%s is interested in notification from %s", channel.getName(), this.toString());
		}
		interestedChannels.add(channel);
	}

	public void stopNotifying(Channel channel){
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "%s is NOT interested in notification from %s", channel.getName(), this.toString());
		}
		interestedChannels.remove(channel);
	}

	public void start(){
		if (logger.isEnabledFor(Level.DEBUG) == true){
			logger.log(Level.DEBUG,"Starting %s", this.toString());
		}
		state = STATE.RUNNING;
	}

	public void stop(){
		if (logger.isEnabledFor(Level.DEBUG) == true){
			logger.log(Level.DEBUG,"Stopping %s", this.toString());
		}
		state = STATE.STOPPED;
	}

	@Override
	protected void shutdown(){
		if (logger.isEnabledFor(Level.DEBUG) == true){
			logger.log(Level.DEBUG,"Shutting down %s", this.toString());
		}
		stop();
		boolean removed = DataSourceUpdateHandlerFactory.getInstance().removeHandler(this);
		if (removed == true){
			doShutDown();
		}
	}

	protected abstract void doShutDown();

	public void updateData(Updateable.UpdateType updateType, List<Tuple> data){
		if (state == STATE.RUNNING){
			if (logger.isEnabledFor(Level.DEBUG) == true){
				logger.log(Level.DEBUG,"Updating %s", subscriptionName);
			}
			long stime = System.currentTimeMillis();
			handleDataUpdate(updateType, data);
			for (Channel channel : interestedChannels) {
				channel.lineUpForProcessing(subscriptionName);
			}
			long etime = System.currentTimeMillis();
			long timeTaken = etime - stime;
			updateProcessingTime.add(System.currentTimeMillis(), timeTaken);
		}
	}

	protected abstract void handleDataUpdate(Updateable.UpdateType updateType, List<Tuple> data);

	public void resetData(boolean purge) throws DataException {
		if (state == STATE.RUNNING){
			if (logger.isEnabledFor(Level.DEBUG) == true){
				logger.log(Level.DEBUG,"Resetting %s with purge %s", subscriptionName, purge == true ? "on" : "off");
			}
			long stime = System.currentTimeMillis();
			handleDataReset(purge);
			for (Channel channel : interestedChannels) {
				channel.lineUpForProcessing(subscriptionName);
			}
			long etime = System.currentTimeMillis();
			long timeTaken = etime - stime;
			resetProcessingTime.add(System.currentTimeMillis(), timeTaken);
		}
	}

	protected abstract void handleDataReset(boolean purge) throws DataException;
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
		sb.append("[subscriptionName=");
		sb.append(subscriptionName);
		sb.append(",query=");
		sb.append(this.dataSourceHandler.getQuery());
		sb.append(",queryParams=");
		sb.append(this.dataSourceHandler.getQueryParams());
		sb.append(",subscribed=");
		sb.append(subscribed);
		sb.append(",state=");
		sb.append(state);
		sb.append("]");
		return sb.toString();
		
	}

}