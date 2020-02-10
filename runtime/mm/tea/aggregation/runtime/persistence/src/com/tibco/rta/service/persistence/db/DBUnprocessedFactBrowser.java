package com.tibco.rta.service.persistence.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import com.tibco.rta.Fact;
import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.impl.RecoveredFactImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.query.Browser;

public class DBUnprocessedFactBrowser implements Browser<Fact> {
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(
			LoggerCategory.RTA_SERVICES_PERSISTENCE.getCategory());
	private DatabasePersistenceService pService;
	private Fact fact;
	private RtaSchema schema;
	private DimensionHierarchy dh;
	private Connection connection;
	private boolean insertProcessed;
	private boolean isInsertProcessedMultiTable;
	private int index = 0;
	private int numberOfResultSets = 0;
	private HashMap<Integer, String> indexToCube = new HashMap<Integer, String>();
	private HashMap<Integer, String> indexToDimHr = new HashMap<Integer, String>();
	private HashMap<Integer, ResultSet> indexToResultSet = new HashMap<Integer, ResultSet>();
	private HashMap<Integer, Statement> indexToStatement = new HashMap<Integer, Statement>();
	
	DBUnprocessedFactBrowser(DatabasePersistenceService pService, String schemaName, boolean insertProcessed)
			throws Exception {
		this.pService = pService;
		this.insertProcessed = insertProcessed;
		this.index=0;
		this.numberOfResultSets=0;
		
		String factTableName = pService.getSchemaManager().makeFactTableName(schemaName);
		StringBuilder querybldr;
		schema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName);

		if (this.insertProcessed) {
			String processedFactTableName = pService.getSchemaManager().makeProcessedFactTableName(schemaName);
			for (Cube cube : schema.getCubes()) {
				for (DimensionHierarchy dh : cube.getDimensionHierarchies()) {
					if (!dh.isEnabled()) {
						continue;
					}
					StringBuilder subQuery = new StringBuilder("SELECT " + DBConstant.FACT_KEY_FIELD + " FROM "
							+ processedFactTableName + " WHERE " + DBConstant.CUBE_FIELD + " = '" + cube.getName()
							+ "' AND " + DBConstant.DIMHR_FIELD + " ='" + dh.getName() + "'");
					querybldr = new StringBuilder("SELECT * FROM " + factTableName + " WHERE " + DBConstant.FACT_KEY_FIELD
							+ " NOT IN (" + subQuery.toString()+")");

					ResultSet rs = executeQuery(querybldr.toString());
					indexToCube.put(numberOfResultSets, cube.getName());
					indexToDimHr.put(numberOfResultSets, dh.getName());
					indexToResultSet.put(numberOfResultSets, rs);
					numberOfResultSets++;
				}
			}
		} else {
			querybldr = new StringBuilder("SELECT * FROM " + factTableName + " WHERE ");
			String prefix = "";
			for (Cube cube : schema.getCubes()) {
				for (DimensionHierarchy dh : cube.getDimensionHierarchies()) {
					querybldr.append(prefix + cube.getName() + DBConstant.SEP
							+ pService.getSchemaManager().getStorageSchema(dh) + " IS NULL ");
					prefix = " OR ";
				}
			}
			ResultSet rs = executeQuery(querybldr.toString());
			indexToResultSet.put(numberOfResultSets, rs);
			numberOfResultSets++;
		}

	}
	
	DBUnprocessedFactBrowser(DatabasePersistenceService pService, String schemaName, String cubeName, String dhName)
			throws Exception {
		this.pService = pService;
		this.insertProcessed = true;
		this.isInsertProcessedMultiTable = true;
		this.insertProcessed = true;
		schema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName);
		dh = schema.getCube(cubeName).getDimensionHierarchy(dhName);
		String factTableName = pService.getSchemaManager().makeFactTableName(schemaName);
		String proFactTableName = pService.getSchemaManager().makeProcessedFactTableName(schemaName, cubeName, dhName);
		StringBuilder subQuery = new StringBuilder("SELECT " + DBConstant.FACT_KEY_FIELD + " FROM " + proFactTableName);
		StringBuilder querybldr = new StringBuilder("SELECT * FROM " + factTableName + " WHERE " + DBConstant.FACT_KEY_FIELD
				+ " NOT IN (" + subQuery.toString() + ")");
		ResultSet rs = executeQuery(querybldr.toString());
		indexToResultSet.put(numberOfResultSets, rs);
		numberOfResultSets++;

	}

	private ResultSet executeQuery(String query) throws Exception {
		ResultSet rs;
		Statement st;
		if (LOGGER.isEnabledFor(Level.DEBUG)) {
			LOGGER.log(Level.DEBUG, "SQL query for UnprocessedFactBrowser = %s ", query);
		}
		connection = pService.getConnectionpool().getSqlConnection();
		try {
			st = connection.createStatement();
			indexToStatement.put(numberOfResultSets, st);
			rs = st.executeQuery(query);
		} finally {
			 pService.getConnectionpool().removeCurrentConnectionFromThreadLocal();
		}
		return rs;
	}

	@Override
	public boolean hasNext() {

		while (index < numberOfResultSets) {
			if (indexToResultSet.get(index) == null) {
				index++;
				continue;
			}
			if (fact == null) {
				fact = fetchFactFromRS(index);
				if (fact == null) {
					// close result set and statement for particular index.
					pService.releaseResources(indexToResultSet.get(index), indexToStatement.get(index));
					indexToResultSet.put(index, null);
					index++;
					continue;
				} else {
					return true;
				}
			} else {
				return true;
			}
		}
		pService.getConnectionpool().releaseConnection(connection);
		return false;
	}

	private Fact fetchFactFromRS(int currResultSetNumber) {
		fact = pService.fetchFactFromRS(indexToResultSet.get(currResultSetNumber), schema, true);
		if (fact != null) {
			setRecoveredFactAttribute(schema, (RecoveredFactImpl) fact, currResultSetNumber);
		}
		return fact;
	}

	@Override
	public Fact next() {
		Fact next = fact;
		fact = null;
		return next;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		for (int i = 0; i < numberOfResultSets; i++) {
			pService.releaseResources(indexToResultSet.get(i), indexToStatement.get(i));
			indexToResultSet.put(i, null);
		}	
		pService.getConnectionpool().releaseConnection(connection);
	}

	private void setRecoveredFactAttribute(RtaSchema schema, RecoveredFactImpl fact, int currResultSetNumber) {
		try {

			if (insertProcessed) {
				if (isInsertProcessedMultiTable) {
					fact.addToUnprocessedList(dh);
				} else {
					Cube cube = schema.getCube(indexToCube.get(currResultSetNumber));
					DimensionHierarchy dh = cube.getDimensionHierarchy(indexToDimHr.get(currResultSetNumber));
					fact.addToUnprocessedList(dh);
				}
			} else {
				ResultSet rs = indexToResultSet.get(currResultSetNumber);
				for (Cube cube : schema.getCubes()) {
					for (DimensionHierarchy dh : cube.getDimensionHierarchies()) {
						if (!dh.isEnabled()) {
							continue;
						}
						String columnLabel = cube.getName() + DBConstant.SEP
								+ pService.getSchemaManager().getStorageSchema(dh);
						// Get unprocessed dimension hierarchies, i.e. whose
						// status
						// is null.
						if (rs.getString(columnLabel) == null) {
							fact.addToUnprocessedList(dh);
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "An error occurred, while setting Recovery Fact Attribute.", e);
		}
	}

}
