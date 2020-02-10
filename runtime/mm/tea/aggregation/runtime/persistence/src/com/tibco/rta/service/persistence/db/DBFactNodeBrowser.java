package com.tibco.rta.service.persistence.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.tibco.rta.Fact;
import com.tibco.rta.MetricKey;
import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.Attribute;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.TimeDimension;
import com.tibco.rta.model.TimeUnits;
import com.tibco.rta.query.Browser;
import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.MetricFieldTuple;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.runtime.model.MetricNode;

public class DBFactNodeBrowser implements Browser<Fact> {
	private static final Logger LOGGER = LogManagerFactory.getLogManager()
			.getLogger(LoggerCategory.RTA_SERVICES_PERSISTENCE.getCategory());
	DatabasePersistenceService pService;
	MetricNode metricNode;
	String metricFactTableName;
	String factTableName;
	ResultSet rs;
	Statement st;
	RtaSchema schema;
	Connection connection;
	private Fact fact;
	private List<MetricFieldTuple> orderByList;
	
	
	public static long SECONDS_IN_MILLIS = 1000;
	public static long MINUTES_IN_MILLIS = SECONDS_IN_MILLIS * 60;
	public static long HOURS_IN_MILLIS = MINUTES_IN_MILLIS * 60;
	public static long DAYS_IN_MILLIS = HOURS_IN_MILLIS * 24;
	public static long WEEK_IN_MILLIS = DAYS_IN_MILLIS * 7;

	DBFactNodeBrowser(DatabasePersistenceService pService, MetricNode metricNode, List<MetricFieldTuple> orderByList) throws Exception {
		this.pService = pService;
		this.metricNode = metricNode;
		this.orderByList = orderByList;
		// metricFactTableName =
		// pService.getSchemaManager().getOrCreateMetricFactSchema(metricNode);
		MetricKey mKey = (MetricKey) metricNode.getKey();
		schema = ModelRegistry.INSTANCE.getRegistryEntry(mKey.getSchemaName());
		Cube cube = schema.getCube(mKey.getCubeName());
		DimensionHierarchy dh = cube.getDimensionHierarchy(mKey.getDimensionHierarchyName());

		factTableName = pService.getSchemaManager().makeFactTableName(schema.getName());

		String query = null;

		StringBuilder strBldr = new StringBuilder("SELECT * FROM " + factTableName + " WHERE ");
		// if (usePK) {
		// strBldr.append(" WHERE " +
		// DatabasePersistenceService.METRIC_TABLE_PREFIX
		// + DatabasePersistenceService.KEY_FIELD);
		// strBldr.append("='" + mKey.toString() + "'");
		// } else {
		// strBldr.append(" WHERE " +
		// DatabasePersistenceService.DIMENSION_LEVEL_NAME_FIELD + " = '"
		// + mKey.getDimensionLevelName() + "'");

		long smallestTimeDimensionGranule = 0;

		TimeUnits tm = null;
		Dimension timeDimension = null;
		String prefix = "";

		for (int i = 0; i < mKey.getDimensionNames().size(); i++) {
			String dimName = mKey.getDimensionNames().get(i);
			Object value = mKey.getDimensionValue(dimName);
			Dimension dim = schema.getDimension(dimName);
			Attribute attr = dim.getAssociatedAttribute();

			if (value != null) {
				if (dim instanceof TimeDimension) {
					long timeValue = (Long) value;
					if (smallestTimeDimensionGranule < timeValue) {
						timeDimension = dim;
						TimeDimension td = (TimeDimension) dim;
						smallestTimeDimensionGranule = timeValue;
						tm = td.getTimeUnit();
					}
				} else {
					if (value instanceof String) {
						strBldr.append(prefix + attr.getName() + " = '" + value + "'");
					} else {
						strBldr.append(prefix + attr.getName() + " = " + value);
					}
					prefix = " AND ";
				}
			}
		}
		if (tm != null) {
			long timeInterval = 0;
			switch (tm.getTimeUnit()) {
			case MILLISECOND: {
				timeInterval = 1;
			}
			case SECOND: {
				timeInterval = SECONDS_IN_MILLIS;
				break;
			}
			case MINUTE: {
				timeInterval = MINUTES_IN_MILLIS;
				break;
			}
			case HOUR: {
				timeInterval = HOURS_IN_MILLIS;
				break;
			}
			case DAY: {
				timeInterval = DAYS_IN_MILLIS;
				break;
			}
			case WEEK: {
				timeInterval = WEEK_IN_MILLIS;
				break;
			}
			default: {
				timeInterval = 1;
			}

			}

			timeInterval = timeInterval * tm.getMultiplier();

			strBldr.append(prefix + timeDimension.getAssociatedAttribute().getName() + " BETWEEN "
					+ smallestTimeDimensionGranule + " AND " + (smallestTimeDimensionGranule + timeInterval - 1));
		}

		strBldr.append(getOrderBySubStr());
		
		query = strBldr.toString();
		
		if (LOGGER.isEnabledFor(Level.DEBUG)) {
			LOGGER.log(Level.DEBUG, "SQL query for DBFactNodeBrowser = %s ", query);
		}
		connection = pService.getConnectionpool().getSqlConnection();
		
		try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
		} finally {
			pService.getConnectionpool().removeCurrentConnectionFromThreadLocal();
		}

	}

	@Override
	public boolean hasNext() {
		if (rs == null) {
            return false;
        }
        if (fact == null) {
			fact = pService.fetchFactFromRS(rs, schema);
            if (fact == null) {
            	// close result set and statement.
				pService.releaseResources(rs, st);
				rs = null;
				pService.getConnectionpool().releaseConnection(connection);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
	}



	@Override
	public Fact next() {
		Fact next = fact;
		fact = null;
		return next;
	}

	@Override
	public void remove() {
		// nothing to do.
	}

	@Override
	public void stop() {
		pService.releaseResources(rs, st);
		rs = null;
		pService.getConnectionpool().releaseConnection(connection);
	}

	protected String getOrderBySubStr() {
		// if no ordering is provided by user sort it based on the created time stamp
		
		StringBuilder query = new StringBuilder();
		query.append(" ORDER BY ");
		
		if (orderByList == null || orderByList.isEmpty()) {
			return query.append(DBConstant.CREATED_DATE_TIME_FIELD).toString();
		}
		boolean isFirstTuple = true;
		for (MetricFieldTuple tuple : orderByList) {
			if (!isFirstTuple) {
				query.append(", ");
			}
			isFirstTuple = false;
			if (tuple.getMetricQualifier() != null
					&& tuple.getMetricQualifier().equals(MetricQualifier.DIMENSION_LEVEL)) {
				query.append(DBConstant.DIMENSION_LEVEL_FIELD);
			} else if (tuple.getKeyQualifier() != null
					&& tuple.getKeyQualifier().equals(FilterKeyQualifier.DIMENSION_NAME)) {
				query.append(tuple.getKey());
			} else if (tuple.getKeyQualifier() != null
					&& tuple.getKeyQualifier().equals(FilterKeyQualifier.MEASUREMENT_NAME)) {
				query.append(tuple.getKey());
			}
		}
		return query.toString();
	}
}
