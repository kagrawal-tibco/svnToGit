package com.tibco.cep.dashboard.plugin.beviews.data;

import java.util.Collections;
import java.util.List;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.plugin.beviews.mal.MALSourceElementCache;
import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.data.DataSourceHandler;
import com.tibco.cep.dashboard.psvr.data.Threshold;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.designtime.core.model.Entity;

public class EmptyDataSourceHandler extends DataSourceHandler {

	@Override
	protected void configure(MALSeriesConfig seriesConfig, PresentationContext pCtx) throws DataException {
		try {
			// get the element being queried
			Object resolvedReference = pCtx.resolveRef(seriesConfig.getActionRule().getDataSource().getSrcElement());
			sourceElement = MALSourceElementCache.getInstance().getMALSourceElement((Entity)resolvedReference);
			// owner
			owner = seriesConfig.getActionRule().getOwner().getId();
			// threshold
			threshold = new Threshold(seriesConfig.getActionRule().getThreshold(), seriesConfig.getActionRule().getThresholdUnit());
		} catch (MALException e) {
			throw new DataException(messageGenerator.getMessage("data.query.fieldresolution.failure", pCtx.getMessageGeneratorArgs(e, URIHelper.getURI(seriesConfig))));
		} catch (ElementNotFoundException e) {
			throw new DataException(messageGenerator.getMessage("data.query.fieldresolution.notfound", pCtx.getMessageGeneratorArgs(e, URIHelper.getURI(seriesConfig))));
		}
	}

	@Override
	public List<Tuple> getData(PresentationContext ctx) throws DataException {
		return Collections.emptyList();
	}

	@Override
	protected void shutdown() throws NonFatalException {
		//do nothing
	}

}
