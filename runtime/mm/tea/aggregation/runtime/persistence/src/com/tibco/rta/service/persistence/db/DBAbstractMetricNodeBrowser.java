package com.tibco.rta.service.persistence.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.query.Browser;
import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.MetricFieldTuple;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.impl.MetricNodeImpl;

public abstract class DBAbstractMetricNodeBrowser implements Browser<MetricNode> {
	protected static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_PERSISTENCE.getCategory());
	protected static final Logger LOGGER_DTLS = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_PERSISTENCE_DETAILS.getCategory());

	protected String schemaName;
	protected String cubeName;
	protected String hierarchyName;
	// protected String measurementName;
	protected DatabasePersistenceService pService;
	protected String tableName;
	protected ResultSet rs;
	private Statement st;
	// private int count = 0;
	protected MetricNodeImpl metricNode;

	// the db connection associated with this resultset/query
	protected Connection connection;
	private String query;

	DBAbstractMetricNodeBrowser(DatabasePersistenceService pServ, String schemaName, String cubeName,
			String hierarchyName) throws Exception {
		this.pService = pServ;
		this.schemaName = schemaName;
		this.cubeName = cubeName;
		this.hierarchyName = hierarchyName;
		// this.measurementName = measurementName;
		tableName = pService.getSchemaManager().makeMetricSchemaTableName(schemaName, cubeName, hierarchyName);
	}

	@Override
	public boolean hasNext() {
		try {
			if (rs == null) {
				return false;
			}
			if (metricNode == null) {
				metricNode = fetchMetricNodeFromRS();
				if (metricNode == null) {
					// close result set and statement.
					pService.releaseResources(rs, st);
					rs = null;
					pService.getConnectionpool().releaseConnection(connection);
					
					if (LOGGER.isEnabledFor(Level.DEBUG)) {
						LOGGER.log(Level.DEBUG, "STOPPED hasNext() !!!! MetricNodeBrowser = %s ", query);
					}
					
					return false;
				} else {
					return true;
				}
			} else {
				return true;
			}
		} catch (Exception e) {
			try {
				pService.releaseResources(rs, st);
			} catch (Exception e1) {
				
			}
			try {
				pService.getConnectionpool().releaseConnection(connection);
			} catch (Exception e1) {
				
			}
			
			throw new RuntimeException(e);
		}
	}

	MetricNodeImpl fetchMetricNodeFromRS() throws Exception {
		return pService.fetchMetricNodeFromRS(rs, schemaName, cubeName, hierarchyName, query);
	}

	@Override
	public MetricNodeImpl next() {
		MetricNodeImpl next = metricNode;
		metricNode = null;
		return next;
	}

	@Override
	public void remove() {

	}

	@Override
	public void stop() {
		pService.releaseResources(rs, st);
		rs = null;
		pService.getConnectionpool().releaseConnection(connection);
		if (LOGGER.isEnabledFor(Level.DEBUG)) {
			LOGGER.log(Level.DEBUG, "Stopping query: [%s]", query);
		}
	}

	protected void executeQuery(DatabasePersistenceService pService, String query) throws Exception {
		this.query = query;
		if (LOGGER.isEnabledFor(Level.DEBUG)) {
			LOGGER.log(Level.DEBUG, "SQL query for MetricNodeBrowser = [%s] ", query);
		}
		try {
			connection = pService.getConnectionpool().getSqlConnection();
			st = connection.createStatement();
			if (pService.getConnectionpool().getDatabaseType().equals("postgreSql")) {
				st.setFetchSize(Integer.parseInt(ConfigProperty.RTA_POSTGRE_QUERY_FETCH_SIZE.getDefaultValue()));
			} else {
				st.setFetchSize(pService.getQueryFetchSize());
			}
			
			long ts = System.currentTimeMillis();
			rs = st.executeQuery(query);
			long ts2 = System.currentTimeMillis();
			if (LOGGER_DTLS.isEnabledFor(Level.TRACE)) {
				LOGGER_DTLS.log(Level.TRACE, "SQL query for MetricNodeBrowser = [%s] Time taken to execute: [%d] ms", query, (ts2-ts));
			}
		} finally {
			pService.getConnectionpool().removeCurrentConnectionFromThreadLocal();
		}
	}

	protected String getOrderByQuerySubStr(List<MetricFieldTuple> metricTuples) {
		if (metricTuples == null || metricTuples.isEmpty()) {
			return "";
		}

		StringBuilder query = new StringBuilder();
		query.append(" ORDER BY ");
		boolean isFirstTuple = true;
		for (MetricFieldTuple tuple : metricTuples) {
			if (!isFirstTuple) {
				query.append(", ");
			}
			isFirstTuple = false;
			if (tuple.getMetricQualifier() != null ) {
				if (tuple.getMetricQualifier().equals(MetricQualifier.DIMENSION_LEVEL)) {
					query.append(DBConstant.DIMENSION_LEVEL_NAME_FIELD);
				} else if (tuple.getMetricQualifier().equals(MetricQualifier.CREATED_TIME)) {
					query.append(DBConstant.CREATED_DATE_TIME_FIELD);
				} else if (tuple.getMetricQualifier().equals(MetricQualifier.UPDATED_TIME)) {
					query.append(DBConstant.UPDATED_DATE_TIME_FIELD);
				} else if (tuple.getMetricQualifier().equals(MetricQualifier.DIMENSION_LEVEL_NO)) {
					query.append(DBConstant.DIMENSION_LEVEL_FIELD);
				} else if (tuple.getMetricQualifier().equals(MetricQualifier.IS_PROCESSED)) {
					query.append(DBConstant.IS_PROCESSED);
				}
			} else if (tuple.getKeyQualifier() != null
					/*&& tuple.getKeyQualifier().equals(FilterKeyQualifier.DIMENSION_NAME)*/) {
				query.append(tuple.getKey());
			}
		}
		return query.toString();
	}
}
