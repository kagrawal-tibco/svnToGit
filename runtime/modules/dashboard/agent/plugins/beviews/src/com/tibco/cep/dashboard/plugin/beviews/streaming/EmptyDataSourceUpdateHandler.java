package com.tibco.cep.dashboard.plugin.beviews.streaming;

import java.util.Collections;
import java.util.List;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.data.Updateable.UpdateType;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.streaming.DataSourceUpdateHandler;
import com.tibco.cep.dashboard.psvr.streaming.StreamingException;
import com.tibco.cep.dashboard.psvr.streaming.UpdateDataProvider;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;

public class EmptyDataSourceUpdateHandler extends DataSourceUpdateHandler implements UpdateDataProvider {

	@Override
	public List<Tuple> getData(PresentationContext ctx) throws VisualizationException {
		return Collections.emptyList();
	}

	@Override
	protected void handleSubscriptionRequest() throws StreamingException {
		//do nothing
	}

	@Override
	protected void doShutDown() {
		//do nothing
	}

	@Override
	protected void handleDataUpdate(UpdateType updateType, List<Tuple> data) {
		//do nothing
	}

	@Override
	protected void handleDataReset(boolean purge) throws DataException {
		//do nothing
	}

	@Override
	protected void init() {
		//do nothing
	}

}
