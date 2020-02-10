package com.tibco.cep.dashboard.plugin.beviews.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.plugin.beviews.common.query.ViewsQueryExecutorFactory;
import com.tibco.cep.dashboard.psvr.common.query.Query;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.psvr.common.query.QueryExecutor;
import com.tibco.cep.dashboard.psvr.common.query.ResultSet;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author apatil
 *
 */
class CountLimitingDataSet extends DataSet {

	protected int dataSetLimit;

	protected CountLimitingDataSet(Logger logger, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator) {
		super(logger, exceptionHandler, messageGenerator);
	}

	@Override
	protected void init(PresentationContext ctx) throws DataException, MALException, ElementNotFoundException {
		super.init(ctx);
		dataSetLimit = threshold.getThresholdValue();
		if (dataSetLimit >= Integer.MAX_VALUE) {
			logger.log(Level.ERROR, "The data set limit[" + dataSetLimit + "] in " + name + " is larger then " + Integer.MAX_VALUE + ", limiting it to " + maxFetchCount);
			dataSetLimit = maxFetchCount;
		}
	}

	@Override
	protected List<Tuple> readDataFromServer(Query query, PresentationContext ctx) throws DataException {
		boolean maxCntApplied = false;
		QueryExecutor queryExecutor = null;
		ResultSet result = null;
		try {
			//get query executor
			queryExecutor = ViewsQueryExecutorFactory.getInstance().createImplementation();
			//get count of row
			int count = queryExecutor.countQuery(query);
			//no data , return empty list
			if (count == 0) {
				return new ArrayList<Tuple>();
			}
			//adjust datasetlimit
			int currDataSetLimit = dataSetLimit;
			if (currDataSetLimit == 0 || Math.abs(currDataSetLimit) > maxFetchCount) {
				if (currDataSetLimit < 0) {
					currDataSetLimit = -maxFetchCount;
				} else {
					currDataSetLimit = maxFetchCount;
				}
				maxCntApplied = true;
			}

			int skippingCnt = 0;

			if (currDataSetLimit < 0){
				skippingCnt = count + currDataSetLimit;
				if (skippingCnt < 0) {
					skippingCnt = 0;
				}
				currDataSetLimit = Math.abs(currDataSetLimit);
			}

			result = queryExecutor.executeQuery(query);
			List<Tuple> tuples = new LinkedList<Tuple>();
			//skip n tuples
			for (int i = 0; i < skippingCnt; i++) {
				result.next();
			}
			while (result.next() == true && tuples.size() < currDataSetLimit) {
				tuples.add(result.getTuple());
			}
			boolean hasMoreTuples = result.next();
			if (hasMoreTuples == true && maxCntApplied == true) {
				logger.log(Level.WARN, "Max row count limit reached before reaching the " + dataSetLimit + " threshold, limiting the number of rows  to " + currDataSetLimit + " in " + name);
			} else if (hasMoreTuples == true) {
				logger.log(Level.DEBUG, "Fetched only " + currDataSetLimit + " tuples for " + name + " as per the set datasetlimit...");
			}
			return tuples;
		} catch (QueryException e) {
			throw new DataException(messageGenerator.getMessage("viewdatasrchandler.query.cursoriteration.failure", new MessageGeneratorArgs(e, name)), e);
		} finally {
			if (result != null){
				try {
					result.close();
				} catch (QueryException ignore) {
				}
			}
			if (queryExecutor != null){
				queryExecutor.close();
			}
		}
	}

	@Override
	protected <T> List<T> applyThreshold(List<T> tuples) {
		if (tuples == null || tuples.size() == 0) {
			return tuples;
		}
		List<T> thresholdAppliedList = tuples;
		boolean maxCntApplied = false;
		int size = tuples.size();
		long absDataSetLimit = Math.abs(dataSetLimit);
		if (absDataSetLimit > maxFetchCount || absDataSetLimit == 0) {
			absDataSetLimit = maxFetchCount;
			maxCntApplied = true;
		}
		if (size > absDataSetLimit) {
			// There are more elements present than the dataSetLimit
			if (dataSetLimit >= 0) {
				if (maxCntApplied == true) {
					logger.log(Level.WARN, "The number of rows[" + thresholdAppliedList.size() + "] is exceeding " + maxFetchCount + ", limiting to upper " + maxFetchCount + " in " + name);
				} else if (logger.isEnabledFor(Level.DEBUG) == true) {
					logger.log(Level.DEBUG, "Limiting " + tuples.size() + " tuple(s) to upper " + dataSetLimit + " tuple(s) in " + name);
				}
				thresholdAppliedList = new LinkedList<T>(tuples.subList(0, (int) absDataSetLimit));
			} else {
				if (maxCntApplied == true) {
					logger.log(Level.WARN, "The number of rows[" + thresholdAppliedList.size() + "] is exceeding " + maxFetchCount + ", limiting to lower " + maxFetchCount + " in " + name);
				} else if (logger.isEnabledFor(Level.DEBUG) == true) {
					logger.log(Level.DEBUG, "Limiting " + tuples.size() + " tuple(s) to lower " + absDataSetLimit + " tuple(s) in " + name);
				}
				thresholdAppliedList = new LinkedList<T>(tuples.subList(size - (int) absDataSetLimit, size));
			}
		}
		return thresholdAppliedList;
	}
}