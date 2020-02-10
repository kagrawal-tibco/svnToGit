package com.tibco.cep.dashboard.plugin.beviews.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.data.TupleComparator;
import com.tibco.cep.dashboard.plugin.beviews.common.query.ViewsQuery;
import com.tibco.cep.dashboard.plugin.beviews.data.DataSet.DATA_STATES;
import com.tibco.cep.dashboard.plugin.beviews.mal.MALSourceElementCache;
import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.data.DataSourceHandler;
import com.tibco.cep.dashboard.psvr.data.Threshold;
import com.tibco.cep.dashboard.psvr.data.Updateable;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.studio.dashboard.core.query.BEViewsQueryInterpreter;

public class QueryBasedDataSourceHandler extends DataSourceHandler implements Observer, Updateable {

	private ViewsQuery queryObj;

	private DataSet dataSet;

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
			// compile query
			Metric metric = (Metric) sourceElement.getSource();
			BEViewsQueryInterpreter interpreter = new BEViewsQueryInterpreter(Arrays.asList(metric));
			interpreter.setLogger(logger);
			interpreter.interpret(query);
			if (interpreter.hasError() == true) {
				throw new DataException(messageGenerator.getMessage("data.query.interpretation.failure", pCtx.getMessageGeneratorArgs(null, URIHelper.getURI(seriesConfig), interpreter.getErrorMessage())));
			}
			String condition = interpreter.getCondition();
			queryObj = new ViewsQuery(query, condition, queryParams, metric.getGUID());
			TupleComparator comparator = new TupleComparator();
			Collection<String> sortByFieldNames = interpreter.getSortByFieldNames();
			for (String fieldName : sortByFieldNames) {
				comparator.addSortSpec(fieldName, interpreter.isSortAscending(fieldName));
			}
			// configure and create the data set
			DataSetConfigurator dataSetConfigurator = new DataSetConfigurator();
			dataSetConfigurator.setName(name);
			dataSetConfigurator.setSourceElement(sourceElement);
			dataSetConfigurator.setSeriesConfig(seriesConfig);
			dataSetConfigurator.setThreshold(threshold);
			dataSetConfigurator.setTupleComparator(comparator);
			dataSetConfigurator.setObserver(this);
			dataSet = dataSetConfigurator.getDataSet(pCtx);
		} catch (MALException e) {
			throw new DataException(messageGenerator.getMessage("data.query.fieldresolution.failure", pCtx.getMessageGeneratorArgs(e, URIHelper.getURI(seriesConfig))));
		} catch (ElementNotFoundException e) {
			throw new DataException(messageGenerator.getMessage("data.query.fieldresolution.notfound", pCtx.getMessageGeneratorArgs(e, URIHelper.getURI(seriesConfig))));
		}
	}

	public final String getCondition() {
		return queryObj.bindCondition();
	}

	@Override
	public List<Tuple> getData(PresentationContext ctx) throws DataException {
		synchronized (this) {
			if (dataSet.getData() == null) {
				List<Tuple> data = dataSet.readDataFromServer(queryObj, ctx);
				dataSet.setData(data);
			}
			// we return a new instance as a way to provide snapshot
			return new LinkedList<Tuple>(dataSet.getData());
		}
	}

	@Override
	protected void shutdown() throws NonFatalException {
		if (dataSet != null) {
			dataSet.close();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o == dataSet) {
			DataSet.DATA_STATES state = (DATA_STATES) arg;
			switch (state) {
				case datarefreshed:
					fireRefreshed();
					break;
				case datatruncated:
					fireThresholdApplied();
					break;
			}
		}
	}

	@Override
	public void resetData(boolean purge) throws DataException {
		synchronized (this) {
			//PATCH not setting presentation context in dataSet.readDataFromServer(bindedQuery, null)
			List<Tuple> data = dataSet.readDataFromServer(queryObj, null);
			if (purge == true) {
				// force the dataset to clean
				dataSet.setData(new ArrayList<Tuple>());
				// set the data (optimized call compared to reset)
				dataSet.setData(data);
			} else {
				// reset the data using the newly read data as reference
				dataSet.resetData(data);
			}
		}
	}

	@Override
	public void updateData(UpdateType updateType, List<Tuple> data) {
		synchronized (this) {
			dataSet.updateData(updateType, data);
		}
	}

	@Override
	protected String getBindedQuery() {
		return queryObj.bindQuery();
	}

}