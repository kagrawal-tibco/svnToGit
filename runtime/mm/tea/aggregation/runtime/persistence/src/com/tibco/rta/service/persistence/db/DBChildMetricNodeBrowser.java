package com.tibco.rta.service.persistence.db;

import java.util.List;

import com.tibco.rta.MetricKey;
import com.tibco.rta.log.Level;
import com.tibco.rta.query.MetricFieldTuple;
import com.tibco.rta.query.QueryByKeyDef;

public class DBChildMetricNodeBrowser extends DBAbstractMetricNodeBrowser{

	QueryByKeyDef queryDef;
	MetricKey mKey;
	
	public DBChildMetricNodeBrowser(DatabasePersistenceService pServ, QueryByKeyDef queryDef) throws Exception {
		super(pServ, queryDef.getQueryKey().getSchemaName(), queryDef.getQueryKey().getCubeName(), queryDef
				.getQueryKey().getDimensionHierarchyName());
		this.queryDef = queryDef;
		this.mKey = queryDef.getQueryKey();

		String query = generateQueryFromKey(mKey);
		List<MetricFieldTuple> orderByTuples = queryDef.getOrderByTuples();
		if (orderByTuples != null && !orderByTuples.isEmpty()) {
			query = query + getOrderByQuerySubStr(orderByTuples);
		}
		if (LOGGER.isEnabledFor(Level.DEBUG)) {
			LOGGER.log(Level.DEBUG, "SQL query for DBChildMetricNodeBrowser = %s ", query);
		}
		executeQuery(pServ, query);
	}

	private String generateQueryFromKey(MetricKey key) {
		StringBuilder strBldr = new StringBuilder();
		strBldr.append("SELECT * FROM " + tableName + " WHERE ");
		String prefix = "";
		if (mKey.getDimensionLevelName() != null) {
			strBldr.append(DBConstant.DIMENSION_LEVEL_NAME_FIELD);
			strBldr.append(" = ");
			strBldr.append("'");
			strBldr.append(mKey.getDimensionLevelName());
			strBldr.append("'");
			prefix = " AND ";
		}
		for (String dimName : mKey.getDimensionNames()) {
			Object value = mKey.getDimensionValue(dimName);
			if (value instanceof String) {
				strBldr.append(prefix);
				strBldr.append(dimName);
				strBldr.append(" = '");
				strBldr.append(value);
				strBldr.append("'");
			} else if (value != null) {
				strBldr.append(prefix);
				strBldr.append(dimName);
				strBldr.append(" = ");
				strBldr.append(value);
			}
			prefix = " AND ";
		}
		return strBldr.toString();
	}
}
