package com.tibco.cep.dashboard.plugin.beviews.common.query;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.psvr.common.query.QueryExecutor;
import com.tibco.cep.dashboard.psvr.common.query.ResultSet;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public abstract class BaseViewsQueryExecutorImpl implements QueryExecutor {

	protected Logger logger;
	protected ExceptionHandler exceptionHandler;

	private Map<String, ResultSet> openResultSets;

	protected BaseViewsQueryExecutorImpl(Logger logger, ExceptionHandler exceptionHandler, Properties properties) {
		this.logger = logger;
		this.exceptionHandler = exceptionHandler;
		openResultSets = new HashMap<String, ResultSet>();
	}

	protected void registerResultSet(ResultSet resultSet) {
		openResultSets.put(resultSet.getId(), resultSet);
	}

	protected boolean unregisterResultSet(String id) {
		return openResultSets.remove(id) != null;
	}

	protected Collection<ResultSet> getOpenResultSets(){
		return openResultSets.values();
	}

	@Override
	public void close() {
		LinkedList<ResultSet> resultsets = new LinkedList<ResultSet>(getOpenResultSets());
		if (resultsets.isEmpty() == false) {
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Closing " + resultsets.size() + " resultset(s)...");
			}
			for (ResultSet resultSet : resultsets) {
				try {
					if (logger.isEnabledFor(Level.DEBUG) == true) {
						logger.log(Level.DEBUG, "Closing " + resultSet + "...");
					}
					resultSet.close();
				} catch (QueryException e) {
					exceptionHandler.handleException("could not close " + resultSet, e, Level.WARN);
				}
			}
			resultsets.clear();
		}
		resultsets = null;
	}

}
