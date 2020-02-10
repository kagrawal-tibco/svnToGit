package com.tibco.cep.dashboard.plugin.internal.streaming;

import java.util.List;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.psvr.data.Updateable;
import com.tibco.cep.dashboard.psvr.streaming.DataSourceUpdateHandler;
import com.tibco.cep.dashboard.psvr.streaming.StreamingException;

public class DefaultDataSourceUpdateHandler extends DataSourceUpdateHandler {

	@Override
	protected void init() {
		// do nothing
	}

	@Override
	protected void handleSubscriptionRequest() throws StreamingException {
		// do nothing
	}

	@Override
	protected void handleDataReset(boolean purge) {
		// do nothing
	}

	@Override
	protected void handleDataUpdate(Updateable.UpdateType updateType, List<Tuple> data) {
		// do nothing
	}

	@Override
	protected void doShutDown() {
		// do nothing
	}

}