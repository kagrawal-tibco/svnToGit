package com.tibco.cep.dashboard.plugin.beviews.data;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.plugin.beviews.common.query.ViewsQueryExecutorFactory;
import com.tibco.cep.dashboard.plugin.beviews.common.query.SeriesDataGenerationConfiguration;
import com.tibco.cep.dashboard.psvr.common.query.Query;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.psvr.common.query.QueryExecutor;
import com.tibco.cep.dashboard.psvr.common.query.ResultSet;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALTwoDimSeriesConfig;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.kernel.service.logging.Logger;

class DesignDataBasedDataSet extends CountLimitingDataSet {

	private MALTwoDimSeriesConfig seriesConfig;

	private MALFieldMetaInfo categoryField;

	protected DesignDataBasedDataSet(MALSeriesConfig seriesConfig, Logger logger, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator) {
		super(logger, exceptionHandler, messageGenerator);
		if ((seriesConfig instanceof MALTwoDimSeriesConfig) == false){
			throw new IllegalArgumentException(URIHelper.getURI(seriesConfig)+" should be an instance of "+MALTwoDimSeriesConfig.class.getName());
		}
		this.seriesConfig = (MALTwoDimSeriesConfig) seriesConfig;
	}

	@Override
	protected void init(PresentationContext ctx) throws DataException, MALException, ElementNotFoundException {
		super.init(ctx);
		dataSetLimit = 0;
		categoryField = ctx.resolveFieldRef(seriesConfig.getCategoryDataConfig().getExtractor().getSourceField());
	}

	@Override
	protected List<Tuple> readDataFromServer(Query query, PresentationContext ctx) throws DataException {
		QueryExecutor queryExecutor = null;
		SeriesDataGenerationConfiguration configuration = null;
		ResultSet resultSet = null;
		try {
			queryExecutor = ViewsQueryExecutorFactory.getInstance().createImplementation();
			configuration = new SeriesDataGenerationConfiguration(seriesConfig,sourceElement,categoryField);
			configuration.attachToCurrentThread();
			resultSet = queryExecutor.executeQuery(query);
			List<Tuple> tuples = new ArrayList<Tuple>();
			while (resultSet.next()) {
				tuples.add(resultSet.getTuple());
			}
			return tuples;
		} catch (QueryException e) {
			throw new DataException(e);
		} catch (MALException e) {
			throw new DataException(e);
		} catch (ElementNotFoundException e) {
			throw new DataException(e);
		} finally {
			if (resultSet != null){
				try {
					resultSet.close();
				} catch (QueryException ignore) {
				}
			}
			if (configuration != null){
				configuration.deattachFromCurrentThread();
			}
			if (queryExecutor != null) {
				queryExecutor.close();
			}
		}
	}
}
