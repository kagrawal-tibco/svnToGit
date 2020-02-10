package com.tibco.rta.service.persistence.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.common.service.ModelChangeListener;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.MetadataElement;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.service.persistence.tools.JdbcDeployment;
import com.tibco.rta.service.persistence.tools.SqlLineBrowser;

public class DatabaseSchemaManager implements ModelChangeListener {

	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(
			LoggerCategory.RTA_SERVICES_PERSISTENCE.getCategory());
	private DatabaseConnectionPool connectionPool;
	private JdbcDeployment jdbcDeployment;
	private boolean isGenerateSchema = false;
	private boolean isRuleTableCreated = false;

	public DatabaseSchemaManager(DatabasePersistenceService pService, boolean usePK, boolean isGenerateSchema, boolean isToUseBlob) {
		this.connectionPool = pService.getConnectionpool();
		this.isGenerateSchema = isGenerateSchema;
		if (isGenerateSchema && LOGGER.isEnabledFor(Level.DEBUG)) {
			LOGGER.log(Level.DEBUG, "Generating Sqls For Database Type : %s", connectionPool.getDatabaseType());
		}
		this.jdbcDeployment = new JdbcDeployment(usePK, connectionPool.getDatabaseType(), isToUseBlob);
	}

	synchronized void createSchema(RtaSchema schema) throws Exception {
		SqlLineBrowser browser = jdbcDeployment.getCreateSqlsForRtaSchema(schema);
		executeSql(browser);
		
		//Create Rule table
		if (!isRuleTableCreated) {
			browser = jdbcDeployment.getCreateSqlsForRule();
			executeSql(browser);
			isRuleTableCreated = true;
		}
	}

	private void executeSql(SqlLineBrowser browser) throws Exception {
		if (!isGenerateSchema) {
			// Production mode, Nothing to generate.
			return;
		}
		// Dev mode only.		
		if (LOGGER.isEnabledFor(Level.INFO)) {
			LOGGER.log(Level.INFO, "<<<DEV MODE>>> Executing CREATE SQLs...");
		}
		try {
			Connection con = connectionPool.getSqlConnection();
			boolean isTableCreated = false;
			String sql = null;
			while (browser.hasNext()) {
				sql = browser.next();
				if(sql == null || sql.trim().isEmpty()){
					continue;
				}
				sql = sql.trim();
				if( !isTableCreated && (sql.startsWith("ALTER TABLE") || sql.startsWith("alter table"))){
					continue;
				}
				if (LOGGER.isEnabledFor(Level.INFO)) {
					LOGGER.log(Level.INFO, "Executing SQL:[%s]", sql);
				}
				Statement stmt = null;
				try {

					stmt = con.createStatement();
					stmt.execute(sql);
					isTableCreated = true;
				} catch (SQLException ex) {
					isTableCreated = false;
					connectionPool.getSqlConnection().rollback();
					if (ex.getMessage() != null
							&& (ex.getMessage().contains("already exists") || ex.getMessage().contains("already used") || ex.getMessage().contains("Duplicate key") || ex.getMessage().contains("already an object"))) {
						if (LOGGER.isEnabledFor(Level.INFO)) {
							LOGGER.log(Level.INFO, "Already exists!");
						}
					} else if(((SQLException) ex).getSQLState() != null  && ((SQLException) ex).getSQLState().startsWith("42")){
						if (LOGGER.isEnabledFor(Level.INFO)) {
							LOGGER.log(Level.INFO, "Already exists!");
						}
					} else {
						LOGGER.log(Level.ERROR,"An error executing SQL.", ex);
						throw ex;
					}
					if (LOGGER.isEnabledFor(Level.DEBUG)) {
						LOGGER.log(Level.ERROR, "An exception in Executing SQL=[%s]", ex, sql);
					}
				} finally {
					if (stmt != null) {
						stmt.close();
					}
				}
			}
			connectionPool.getSqlConnection().commit();
		} catch (Exception e) {
			connectionPool.getSqlConnection().rollback();
			throw e;
		} finally {
			connectionPool.releaseCurrentConnection();
		}
	}

	String makeFactTableName(String schemaName) {
		return jdbcDeployment.makeFactTableName(schemaName);
	}

	String makeProcessedFactTableName(String schemaName) {
		return jdbcDeployment.makeProcessedFactTableName(schemaName);
	}

	String makeProcessedFactTableName(String schemaName, String cubeName, String dhName) {
		RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName);
		Cube cube = schema.getCube(cubeName);
		DimensionHierarchy dh = cube.getDimensionHierarchy(dhName);
		return jdbcDeployment.makeProcessedFactTableName(schemaName, cubeName, dh);
	}

	String makeMetricFactTableName(RtaSchema schema, Cube cube, DimensionHierarchy dh) {
		return jdbcDeployment.makeMetricFactTableName(schema.getName(), cube.getName(), dh);
	}

	String makeMetricFactTableName(String schemaName, String cubeName, DimensionHierarchy dh) {
		return jdbcDeployment.makeMetricFactTableName(schemaName, cubeName, dh);
	}

	String makeMetricFactTableName(String schemaName, String cubeName, String dhName) {
		RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName);
		Cube cube = schema.getCube(cubeName);
		DimensionHierarchy dh = cube.getDimensionHierarchy(dhName);
		return jdbcDeployment.makeMetricFactTableName(schemaName, cubeName, dh);
	}

	String makeMetricSchemaTableName(RtaSchema schema, Cube cube, DimensionHierarchy dh) {
		return jdbcDeployment.makeMetricTableName(schema.getName(), cube.getName(), dh);
	}

	String makeRuleMetricTableName(String schemaName, String cubeName, DimensionHierarchy dh) {
		return jdbcDeployment.makeRuleMetricTableName(schemaName, cubeName, dh);
	}

	String makeRuleMetricTableName(String schemaName, String cubeName, String dhName) {
		RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName);
		Cube cube = schema.getCube(cubeName);
		DimensionHierarchy dh = cube.getDimensionHierarchy(dhName);
		return jdbcDeployment.makeRuleMetricTableName(schemaName, cubeName, dh);
	}

	String makeRuleMetricTableName(RtaSchema schema, Cube cube, DimensionHierarchy dh) {
		return makeRuleMetricTableName(schema.getName(), cube.getName(), dh);
	}

	String makeAlertTableName(String schemaName, String cubeName, DimensionHierarchy dh) {
		return jdbcDeployment.makeAlertTableName(schemaName, cubeName, dh);
	}

	String makeAlertTableName(String schemaName, String cubeName, String dhName) {
		RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName);
		Cube cube = schema.getCube(cubeName);
		DimensionHierarchy dh = cube.getDimensionHierarchy(dhName);
		return jdbcDeployment.makeAlertTableName(schemaName, cubeName, dh);
	}

	String makeAlertTableName(RtaSchema schema, Cube cube, DimensionHierarchy dh) {
		return makeAlertTableName(schema.getName(), cube.getName(), dh);
	}

	String makeMetricSchemaTableName(String schemaName, String cubeName, String dhName) {
		RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName);
		Cube cube = schema.getCube(cubeName);
		DimensionHierarchy dh = cube.getDimensionHierarchy(dhName);
		return jdbcDeployment.makeMetricTableName(schemaName, cubeName, dh);
	}

	String makeMetricMultiValuedTableName(DimensionHierarchy dh, String measurementName) {
		return jdbcDeployment.makeMetricMultiValuedTableName(dh, measurementName);
	}

	String getStorageSchema(DimensionHierarchy dh) {
		return jdbcDeployment.getStorageSchema(dh);
	}

	String getStorageSchema(String schemaName, String cubeName, String hierarhyName) {
		RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName);
		Cube cube = schema.getCube(cubeName);
		DimensionHierarchy dh = cube.getDimensionHierarchy(hierarhyName);
		return jdbcDeployment.getStorageSchema(dh);

	}

	public void createSessionInfoTable() throws Exception {
		SqlLineBrowser browser = jdbcDeployment.getCreateSqlsForSession();
		executeSql(browser);
	}

	boolean isTableExists(String tableName) throws SQLException {
		boolean isExists = false;
		try {
			Connection con = connectionPool.getSqlConnection();
			Statement stmt = con.createStatement();
			stmt.execute("SELECT 1 FROM " + tableName);
			isExists = true;
		} catch (SQLException e) {
			LOGGER.log(Level.ERROR, "Database Table [%s] does not exist", tableName);
			throw e;
		} finally {
			connectionPool.releaseCurrentConnection();
		}
		return isExists;
	}

	@Override
	public void onCreate(MetadataElement metadataElement) {
		try {
			RtaSchema schema = (RtaSchema) metadataElement;
			if (isGenerateSchema) {
				if (LOGGER.isEnabledFor(Level.DEBUG)) {
					LOGGER.log(Level.DEBUG, "OnCreate(), creating Tables for SchemaName: %s", schema.getName());
				}
				createSchema(schema);
				if (LOGGER.isEnabledFor(Level.DEBUG)) {
					LOGGER.log(Level.DEBUG, schema.getName()
							+ " Database schema created successfully for SchemaName : %s", schema.getName());
				}
			}
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "An error occurred, while creating schema.", e);
		}
	}

	@Override
	public void onUpdate(MetadataElement metadataElement) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDelete(MetadataElement metadataElement) {
		// TODO Auto-generated method stub

	}
}
