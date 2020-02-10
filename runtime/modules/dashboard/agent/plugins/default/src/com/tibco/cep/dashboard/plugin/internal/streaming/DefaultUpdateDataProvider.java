package com.tibco.cep.dashboard.plugin.internal.streaming;

import java.util.Collections;
import java.util.List;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.psvr.data.Updateable;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.streaming.DataSourceUpdateHandler;
import com.tibco.cep.dashboard.psvr.streaming.StreamingException;
import com.tibco.cep.dashboard.psvr.streaming.UpdateDataProvider;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;

public class DefaultUpdateDataProvider extends DataSourceUpdateHandler implements UpdateDataProvider {

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

	@Override
	public List<Tuple> getData(PresentationContext ctx) throws VisualizationException {
		return Collections.emptyList();
	}

}