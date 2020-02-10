package com.tibco.rta.service.persistence.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.tibco.rta.Fact;
import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.impl.FactImpl;
import com.tibco.rta.impl.FactKeyImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.TimeDimension;
import com.tibco.rta.query.Browser;

public class DBAssetFactNodeBrowser implements Browser<Fact> {
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(
			LoggerCategory.RTA_SERVICES_PERSISTENCE.getCategory());
	private RtaSchema schema;
	private ResultSet rs;
	private String factTableName;
	private Connection connection;
	DatabasePersistenceService pService;
	private Fact fact;
	private Statement st;

	DBAssetFactNodeBrowser(DatabasePersistenceService pService, String schemaName, String cubeName,
			String hierarchyName, String dimensionName, Object dimensionValue) throws Exception {
		this.pService = pService;
		schema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName);
		Cube cube = schema.getCube(cubeName);
		DimensionHierarchy dh = cube.getDimensionHierarchy(hierarchyName);
		factTableName = pService.getSchemaManager().makeFactTableName(schemaName);

		StringBuilder queryBldr = new StringBuilder();
		queryBldr.append("SELECT DISTINCT ");
		String prefix = "";
		for (Dimension dm : dh.getDimensions()) {
			if (!(dm instanceof TimeDimension)) {
				queryBldr.append(prefix + dm.getName());
				prefix = ",";
			}
		}

		queryBldr.append(" FROM " + factTableName);
		queryBldr.append(" WHERE ");
		prefix = "";
		for (Dimension dm : dh.getDimensions()) {
			if (dm instanceof TimeDimension) {
				continue;
			}
			if (dm.getName().equals(dimensionName) && dimensionValue != null) {
				continue;
			}
			queryBldr.append(prefix + dm.getName() + " IS NOT NULL");
			prefix = " AND ";
		}
		if (dimensionName != null && dimensionValue != null) {
			if (dimensionValue instanceof String) {
				queryBldr.append(" AND " + dimensionName + " = '" + dimensionValue + "'");
			} else {
				queryBldr.append(" AND " + dimensionName + " = " + dimensionValue);
			}
		}

		executeQuery(queryBldr.toString());
	}

	private void executeQuery(String query) throws Exception {
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
			fact = fetchFactFromRS();
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

	private Fact fetchFactFromRS() {
		try {
			if (rs != null) {
				FactImpl fact = null;
				fact = new FactImpl();
				fact.setOwnerSchema(schema);
				// Set new Key
				fact.setKey(new FactKeyImpl(schema.getName()));
				pService.setFactAttributes(schema, fact, rs);
				return fact;
			}
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "An error occurred, while fetching Distinct Fact from Database.", e);
		}
		return null;
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
}
