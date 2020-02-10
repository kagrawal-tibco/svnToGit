package com.tibco.cep.dashboard.plugin.beviews.streaming;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.plugin.beviews.BEViewsProperties;
import com.tibco.cep.dashboard.plugin.beviews.data.QueryBasedDataSourceHandler;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.data.Updateable;
import com.tibco.cep.dashboard.psvr.mal.model.MALSourceElement;
import com.tibco.cep.dashboard.psvr.streaming.DataSourceUpdateHandler;
import com.tibco.cep.dashboard.psvr.streaming.DataUpdatesCommunicator;
import com.tibco.cep.dashboard.psvr.streaming.StreamingException;
import com.tibco.cep.dashboard.timer.ExceptionResistentTimerTask;
import com.tibco.cep.dashboard.timer.TimerProvider;
import com.tibco.cep.kernel.service.logging.Level;

public class QueryBasedDataSourceUpdateHandler extends DataSourceUpdateHandler {

	private boolean demoModeOn;

	private ExceptionResistentTimerTask demoUpdateTask;

	private long demoUpdateFrequency;

	private QueryBasedDataSourceHandler dataSourceHandler;

	@Override
	protected void init() {
		demoModeOn = (Boolean)BEViewsProperties.DEMO_MODE.getValue(properties);
		if (demoModeOn == true){
			demoUpdateFrequency = (Long)BEViewsProperties.DEMO_MODE_UPDATE_FREQUENCY.getValue(properties);
		}
		this.dataSourceHandler = (QueryBasedDataSourceHandler) super.dataSourceHandler;
	}

	@Override
	protected void handleSubscriptionRequest() throws StreamingException {
		String whereClause = this.dataSourceHandler.getCondition();
		MALSourceElement sourceElement = dataSourceHandler.getSourceElement();
		DataUpdatesCommunicator.getInstance().subscribe(dataSourceHandler.getUniqueName(), null, sourceElement, whereClause);
		if (demoModeOn == true){
			demoUpdateTask = new ExceptionResistentTimerTask(subscriptionName + " resetting demo task", logger, exceptionHandler, true) {

				@Override
				public void doRun() {
					//logger.log(Level.INFO, "Resetting "+subscriptionName+"...");
					try {
						resetData(false);
					} catch (DataException e) {
						exceptionHandler.handleException("could not update "+subscriptionName+" in demo mode", e, Level.WARN);
					}
				}

			};
			TimerProvider.getInstance().scheduleWithFixedDelay(demoUpdateTask, demoUpdateFrequency, demoUpdateFrequency, TimeUnit.MILLISECONDS);
		}
	}

	@Override
	protected void handleDataReset(boolean purge) throws DataException {
		dataSourceHandler.resetData(purge);
	}

	@Override
	protected void handleDataUpdate(Updateable.UpdateType updateType, List<Tuple> data) {
		this.dataSourceHandler.updateData(updateType, data);
	}


	@Override
	protected void doShutDown() {
		if (demoModeOn == true){
			demoUpdateTask.cancel();
		}
	}

}