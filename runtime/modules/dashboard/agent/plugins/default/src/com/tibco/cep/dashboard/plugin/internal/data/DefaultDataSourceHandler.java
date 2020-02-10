package com.tibco.cep.dashboard.plugin.internal.data;

import java.util.Collections;
import java.util.List;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.data.DataSourceHandler;
import com.tibco.cep.dashboard.psvr.data.Updateable;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;

public class DefaultDataSourceHandler extends DataSourceHandler implements Updateable {

	@Override
	protected void configure(MALSeriesConfig seriesConfig, PresentationContext ctx) throws DataException {
	}

	@Override
	public List<Tuple> getData(PresentationContext ctx) throws DataException {
		return Collections.emptyList();
	}

	@Override
	protected void shutdown() {

	}

	@Override
	public void resetData(boolean purge) throws DataException {
		//do nothing
	}

	@Override
	public void updateData(Updateable.UpdateType updateType, List<Tuple> data) {
		//do nothing
	}

}