package com.tibco.rta.service.persistence.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.xml.sax.InputSource;

import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.Attribute;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.DataType;
import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.FunctionDescriptor.FunctionParam;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.model.MetadataElement;
import com.tibco.rta.model.MetricFunctionDescriptor;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.RtaSchemaModelFactory;
import com.tibco.rta.model.serialize.impl.ModelSerializationConstants;
import com.tibco.rta.service.persistence.db.DBConstant;

public class JdbcDeployment {
	
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_PERSISTENCE_TOOLS.getCategory());
	private String databaseType;
	private boolean showEnv = false;
	private boolean useAnsi = false;
	static final String BRK = System.getProperty("line.separator", "\n");
	private Properties env = new Properties();
	private String outputFolderPath;
	private String schemaOwner;
	private boolean isUsePk;
//	private boolean insertProcessed;
//	private boolean isInsertProcessedWithMultipleTable;
	private boolean isMigration;
	/**
	 * Whether to use Blob for metric multivalued function or not.
	 */
	private boolean isToUseBlob;
	private boolean isMSSqlServer;

	public JdbcDeployment(boolean isUsePk, String dbType, boolean isToUseBlob) {
		this.isUsePk = isUsePk;
//		this.insertProcessed = System.getProperty("insert_processed", "true").equals("true");
//		this.isInsertProcessedWithMultipleTable = System.getProperty("insert_processed_multiple", "true").equals("true");
		this.isToUseBlob = isToUseBlob;
		this.databaseType = dbType;
		RDBMSType.setDefaultSqlType(databaseType, useAnsi);
		if (databaseType != null && RDBMSType.RDBMS_TYPE_NAME_SQLSERVER.equalsIgnoreCase(databaseType.trim())) {
			isMSSqlServer = true;
		}
//		this.sqlType = RDBMSType.sSqlType;
	}

	public JdbcDeployment(String[] args) {
		// parse command line arguments
		Properties cmdLineArgs = parseArguments(args);
//		LOGGER.log(Level.INFO, BRK + PersistenceComponentVersion.printVersionBanner());

		String propsFile = cmdLineArgs.getProperty("spm.bootstrap.property.file");
		boolean isPropFile = false;
		if (propsFile == null) {
			// if no properties file is defined, then use TRA file as default properties file
			propsFile = System.getProperty("wrapper.tra.file", "tibspmddlgenerator.tra");
			LOGGER.log(Level.INFO, "Using (default) property file: " + propsFile);
		} else {
			LOGGER.log(Level.INFO, "Using property file: " + propsFile);
			isPropFile = true;
		}
		env.setProperty("spm.bootstrap.property.file", propsFile);
		// load properties file.
		try {
			loadProperties(propsFile);
		} catch (FileNotFoundException e) {
			if (isPropFile) {
				LOGGER.log(Level.WARN, "Property file not found [%s], Exiting", propsFile);
				System.exit(1);
			} else {
				LOGGER.log(Level.WARN, "(Default) property file not found [%s], will continue", propsFile);
			}
		} catch (IOException e) {
			LOGGER.log(Level.WARN, "Property file not found [%s]", propsFile);
		}
		// overwrite properties with command line arguments (precedence to command line arguments).
		env.putAll(cmdLineArgs);
		
		if (env.getProperty("jdbcdeploy.spm.config.path") == null) {
			env.setProperty("jdbcdeploy.spm.config.path", ".");
			LOGGER.log(Level.INFO, "Using (default) path for SPM schema files: [%s]", ".");
		} else {
			LOGGER.log(Level.INFO, "Using path for SPM schema files: [%s]", env.getProperty("jdbcdeploy.spm.config.path"));
		}
		if (env.getProperty("jdbcdeploy.schema.output.path") == null) {
			env.setProperty("jdbcdeploy.schema.output.path", ".");
			LOGGER.log(Level.INFO, "Using (default) path for SPM database schema files: [%s]", ".");
		} else {
			LOGGER.log(Level.INFO, "Using path for SPM database schema files: [%s]", env.getProperty("jdbcdeploy.schema.output.path"));
		}
		if (env.getProperty("jdbcdeploy.database.type") == null) {
			env.setProperty("jdbcdeploy.database.type",  "postgresql");
			LOGGER.log(Level.INFO, "Using (default) database for SPM database schema files: [%s]", "postgresql");
		} else {
			LOGGER.log(Level.INFO, "Using database for SPM database schema files: [%s]", env.getProperty("jdbcdeploy.database.type"));
		}
		
		
		String usePKStr = System.getProperty("use_pk");
		if (usePKStr == null) {
			// No System variable configured.
			String dbtype = env.getProperty("jdbcdeploy.database.type");
			if (dbtype != null && RDBMSType.RDBMS_TYPE_NAME_SQLSERVER.equalsIgnoreCase(dbtype.trim())) {
				isMSSqlServer = true;
				if (LOGGER.isEnabledFor(Level.DEBUG)) {
					LOGGER.log(Level.DEBUG, "MS Sql Server, Default No Primary Key mode.");
				}
				this.isUsePk = false;
			} else {
				// Default use Primary key.
				this.isUsePk = true;
			}
		} else {
			// System variable configured, go as configured.
			this.isUsePk = "true".equalsIgnoreCase(usePKStr);
		}

		if (showEnv) {
			showProperties();
		}
	}

	/**
	 * Generate SQL for given database.
	 * 
	 * @throws Exception
	 */
	protected void deploySql() throws Exception {
		if (LOGGER.isEnabledFor(Level.DEBUG)) {
			LOGGER.log(Level.DEBUG, String.format("Generating scripts with Primary Key as : %s", isUsePk ? "ENABLED" : "DISABLED"));
		}
		this.isToUseBlob = "false".equalsIgnoreCase(env.getProperty("tea.agent.metrics.multivalues.explicit", "false"));
		LOGGER.log(Level.DEBUG, "Use BLOB for MultiValued Metric : [%s]", isToUseBlob);
		
		this.databaseType = env.getProperty("jdbcdeploy.database.type");
//		if (this.databaseType != null) {
//			this.databaseType = this.databaseType.trim();
//		} else {
//			LOGGER.log(Level.INFO, "Not database type defined, defaulting to Oracle.");
//			databaseType = "Oracle";
//		}
		this.databaseType = this.databaseType.toLowerCase();

		// String traFile = env.getProperty("spm.bootstrap.property.file");

		this.outputFolderPath = env.getProperty("jdbcdeploy.schema.output.path");
//		if (this.outputFolderPath == null || outputFolderPath.isEmpty()) {
//			LOGGER.log(Level.INFO, "No output directory defined, generating scripts in current directory.");
//			outputFolderPath = null;
//		} else {
//			LOGGER.log(Level.INFO, "Configured Output directory : " + outputFolderPath);
		File outPathFile = new File(outputFolderPath);
		if (!outPathFile.exists()) {
			outPathFile.mkdir();
		}
//
//		}

		if (useAnsi) {
			LOGGER.log(Level.INFO, "Generating database schema Sqls using Ansi Type");
		}
		try {
			RDBMSType.setDefaultSqlType(databaseType, useAnsi);
		} catch (RuntimeException e) {
			LOGGER.log(Level.ERROR,"An error occurred. " + e.getMessage());
			LOGGER.log(Level.INFO, "Usage:");
			printUsage();
			System.exit(1);
		}
//		this.sqlType = RDBMSType.sSqlType;
//		LOGGER.log(Level.INFO, "Configured Database Type : " + RDBMSType.sRuntimeTypeName);
		generateScripts();
	}

	void alterSchemaForMigration(Connection con, SqlSchemaFile schemaFile) {
		LOGGER.log(Level.INFO, "Generating Migration SQL, SchemaName: " + schemaFile.getName());

		Iterator<JdbcTable> iter = schemaFile.getTables();
		while (iter.hasNext()) {
			JdbcTable table = iter.next();
			// check if table already exists in database or not
			if (isTableExists(con, table.getName())) {
				// LOGGER.log(Level.INFO, table.getName() + " table already exits in database, comparing table columns..");				
				// get table info from database.
				Map<String, AttrColumnInfo> dbTableInfo = getTableInfoFromDB(con, table.getName());
				if ((dbTableInfo != null) && !dbTableInfo.isEmpty()) {
					compare(dbTableInfo, table);
				}
			} else {
				LOGGER.log(Level.INFO, table.getName() + " table doesn't exits in database, to be created.");
			}
		}
	}

	private boolean isTableExists(Connection con, String tableName) {
		boolean isExists = false;
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			stmt.execute("SELECT 1 FROM " + tableName);
			isExists = true;
		} catch (SQLException e) {
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return isExists;
	}

	private Map<String, AttrColumnInfo> getTableInfoFromDB(Connection conn, String tableName) {
		Map<String, AttrColumnInfo> typeMap = null;
		ResultSet rs = null;
		// Get the table descriptor
		try {
			DatabaseMetaData meta = conn.getMetaData();
			// errStream.println("TableInfoFromDB:" + table_name.toUpperCase());

			if (RDBMSType.sRuntimeType == RDBMSType.ORACLE) {
				rs = meta.getColumns(null, schemaOwner.toUpperCase(), tableName.toUpperCase(), null); // ORACLE
			} else {
				rs = meta.getColumns(null, schemaOwner, tableName.toUpperCase(), null); // MSSQL
																						// and
																						// others???
			}
			String attrColumnName, attrTypeName;
			typeMap = new HashMap<String, AttrColumnInfo>();
			while (rs.next()) {
				attrColumnName = rs.getString("COLUMN_NAME").toUpperCase();
				attrTypeName = rs.getString("TYPE_NAME");
				/*
				 * LOGGER.log(Level.INFO,  "  CAT:"+rs.getString("TABLE_CAT") +
				 * "  SCH:"+rs.getString("TABLE_SCHEM") +
				 * "  TBL:"+rs.getString("TABLE_NAME") +
				 * "  COL:"+rs.getString("COLUMN_NAME") +
				 * "  TYP:"+rs.getString("TYPE_NAME") +
				 * "  DTP:"+rs.getInt("DATA_TYPE") +
				 * "  SIZ:"+rs.getInt("COLUMN_SIZE") +
				 * "  POS:"+rs.getInt("ORDINAL_POSITION") +
				 * "  NUL:"+rs.getString("IS_NULLABLE") +
				 * "  DIG:"+rs.getInt("DECIMAL_DIGITS"));
				 */
				AttrColumnInfo tInfo = new AttrColumnInfo(attrColumnName, attrTypeName);
				tInfo.length = rs.getInt("COLUMN_SIZE");

				typeMap.put(attrColumnName, tInfo);
			}
		} catch (SQLException sqe) {
			LOGGER.log(Level.INFO, "An error occurred, while fetching table info form database. Table Name: " + tableName);
			sqe.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
		}
		return typeMap;
	}

	/**
	 * Used to compare primary table schemas
	 */
	private boolean compare(Map<String, AttrColumnInfo> dbTableInfo, JdbcTable entityTable) {
		boolean ret = true;
		for (String memberName : dbTableInfo.keySet()) {
			AttrColumnInfo dbInfo = dbTableInfo.get(memberName);
			// errStream.println("COMPARE:" + entityTable.getName() + ":=" +
			// entityTable.hashCode() + " - " + dbInfo.attrName);
			JdbcTable.ColumnDef info = entityTable.findMember(memberName.toUpperCase());
			if (info == null) {
				// Field is deleted - Represent as a DELETED member
				info = new JdbcTable.ColumnDef(dbInfo.attrName, dbInfo.attrType, true);
				// Mark Column to be deleted
				info.columnState = JdbcTable.ColumnDef.ColumnState.DELETED;
				entityTable.addMember(memberName, info);
				entityTable.state = JdbcTable.State.MODIFIED;
			} else {
				String updatedType = info.memberType.toUpperCase();
//				String updatedType = RDBMSType.sSqlType.getColumnTypeForPrimitiveType(info.memberType, info.size);
//				updatedType = updatedType.toUpperCase();
				if (info.columnState == JdbcTable.ColumnDef.ColumnState.DELETED) {
					// This field is marked as deleted so do not process any further.
					continue;
				}
				if (RDBMSType.sSqlType.columnTypeMatches(info.memberType, info.size, dbInfo.attrType, dbInfo.length)) {
					// Mark Column as unchanged.
					info.columnState = JdbcTable.ColumnDef.ColumnState.UNCHANGED;
				} else if (RDBMSType.sSqlType.columnTypeConvertable(info.memberType, info.size, dbInfo.attrType,
						dbInfo.length)) {
					// Mark as changed
					info.columnState = JdbcTable.ColumnDef.ColumnState.MODIFIED;
					entityTable.state = JdbcTable.State.MODIFIED;
				} else {
					// Mark as incompatible
					String errorMessage = "--* For Table " + entityTable.getName() + ", Field " + memberName
							+ " database data type " + dbInfo.attrType + "  changed to INCOMPATIBLE data type " + updatedType;
					info.columnState = JdbcTable.ColumnDef.ColumnState.INCOMPATIBLE;
					entityTable.state = JdbcTable.State.MODIFIED;
					// migrationErrors.append(be_jdbcstoreVersion.line_separator
					// + error);
					LOGGER.log(Level.ERROR, errorMessage);
					ret = false;
				}
			}
		}
		
		// Explicitly set table state as modified, As has no harm.
		entityTable.state = JdbcTable.State.MODIFIED;
		
		return ret;
	}

	boolean loadRtaSchema() throws Exception {
		String configDir = env.getProperty("jdbcdeploy.spm.config.path");		
		
		if (configDir == null || configDir.trim().isEmpty()) {
			throw new Exception("No SPM Schema Config directory defined.");
		}
		File configDirectory = new File(configDir);
		if (!configDirectory.exists()) {
			LOGGER.log(Level.ERROR, "Config directory [%s] does not exist", configDir);
			return false;
		}
		
		if (configDirectory.isDirectory()) {

			File config = new File(configDir);
			File[] fileset = config.listFiles();
			boolean isSchemaPresent = false;
			for (File schemaFile : fileset) {
				if (schemaFile.isDirectory()) {
					continue;
				} else if (!(schemaFile.getAbsolutePath().endsWith(".xml"))) {
					continue;
				}
				isSchemaPresent = true;
				LOGGER.log(Level.INFO, "Loading schema file : " + schemaFile);
				ModelRegistry modelRegistry = ModelRegistry.INSTANCE;

				InputSource schemaFileStream = new InputSource(
						new FileInputStream(schemaFile));
				RtaSchema schema = RtaSchemaModelFactory.getInstance()
						.createSchema(schemaFileStream);
				modelRegistry.put(schema);
			}
			if (!isSchemaPresent) {
				LOGGER.log(
						Level.WARN,
						"No SPM Schema XML file found in configured directory [%s]",
						configDir);
				return false;
			}
		} else {
			if (!(configDirectory.getAbsolutePath().endsWith(".xml"))) {
				LOGGER.log(Level.ERROR,	"Exiting, [%s] is not an XML file",	configDir);
				return false;
			} else {
				LOGGER.log(Level.INFO, "Loading schema file : " + configDir);
				InputSource schemaFileStream = new InputSource(
						new FileInputStream(configDir));
				ModelRegistry modelRegistry = ModelRegistry.INSTANCE;

				RtaSchema schema = RtaSchemaModelFactory.getInstance()
						.createSchema(schemaFileStream);
				modelRegistry.put(schema);
			}
		}
		loadAllSystemSchemas();
		return true;
	}
	
    private void loadAllSystemSchemas() throws Exception {
    	if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Loading system schemas..");
        }
    	ModelRegistry modelRegistry = ModelRegistry.INSTANCE;
    	InputStream is = getClass().getClassLoader().getResourceAsStream("BETEA_System_Schema.xml");
    	
    	RtaSchema systemSchema = RtaSchemaModelFactory.getInstance().createSchema(new InputSource(is));
    	modelRegistry.put(systemSchema);
    	if (LOGGER.isEnabledFor(Level.INFO)) {
            LOGGER.log(Level.INFO, "Loading system schemas [%s] done.", systemSchema.getName());
        }
    }	


	private void generateScripts() throws Exception {
		Connection con = null;
		if (isMigration) {
			LOGGER.log(Level.INFO, "Migration is ENABLED!");
			// Get Database connection if migration.
			con = getConn();
			if (con == null) {
				LOGGER.log(Level.ERROR, "An error occurred while connecting to database.");
				System.exit(1);
			}
		}
		// load alias from database if defined.
		// Properties aliases = null;
		// Connection conn = getConn();
		// if (conn != null) {
		// aliases = loadAliasesFromDB(conn);
		// } else {
		// aliases = new Properties();
		// }
		if (outputFolderPath != null) {
			if (!outputFolderPath.endsWith("/") || !outputFolderPath.endsWith("\\")) {
				outputFolderPath = outputFolderPath + System.getProperty("file.separator", "/");
			}
		} else {
			outputFolderPath = "";
		}
		
		String fName = (isMigration) ? "migrateddl.sql" : "createddl.sql";
		final File schFile = new File(outputFolderPath + DBConstant.SPM_PRODUCT_NAME + DBConstant.SEP + databaseType
				+ DBConstant.SEP + fName);
		PrintWriter pwCreate = new PrintWriter(new FileOutputStream(schFile));

		final File cleanupFile = new File(outputFolderPath + DBConstant.SPM_PRODUCT_NAME + DBConstant.SEP
				+ databaseType + DBConstant.SEP + "cleanupdml.sql");
		PrintWriter pwCleanup = new PrintWriter(new FileOutputStream(cleanupFile));

		final File removeFile = new File(outputFolderPath + DBConstant.SPM_PRODUCT_NAME + DBConstant.SEP + databaseType
				+ DBConstant.SEP + "dropddl.sql");
		PrintWriter pwRemove = new PrintWriter(new FileOutputStream(removeFile));
		
		pwCleanup.print(RDBMSType.sSqlType.getBeginTxnCommand());
		pwCleanup.flush();
		
		List<RtaSchema> schemas = ModelRegistry.INSTANCE.getAllRegistryEntries();
		for (RtaSchema schema : schemas) {
			String schemaName = schema.getName();
			if (!isMigration) {
				LOGGER.log(Level.INFO, "Creating SQL schema for Schema Name = " + schemaName);
			}
			SqlSchemaFile schemaFile = generateSqlForRtaSchema(schema, false);

			if (isMigration) {
				alterSchemaForMigration(con, schemaFile);
				LOGGER.log(Level.INFO, "Writing migration script for :" + schemaName);
			}else {
				LOGGER.log(Level.INFO, "Writing generate script for :" + schemaName);
			}
			pwCreate.print(schemaFile.toStringBuffer().toString());
			pwCreate.flush();

			LOGGER.log(Level.INFO, "Writing cleanup script for :" + schemaName);			
			pwCleanup.print(schemaFile.deleteQuery());
			pwCleanup.flush();

			LOGGER.log(Level.INFO, "Writing remove script for :" + schemaName);
			pwRemove.print(schemaFile.removeQuery());
			pwRemove.flush();
		}

		// Generate Sql for Other Tables like session persistence Table, which
		// are not under any specific RTA Schema.
		SqlSchemaFile otherSqlSchemaFile = new SqlSchemaFile("Other");
		otherSqlSchemaFile.addTable(createSessionTable(false));
		otherSqlSchemaFile.addTable(createRuleTable(false));
		if (isMigration) {
			alterSchemaForMigration(con, otherSqlSchemaFile);
		}
		LOGGER.log(Level.INFO, "Writing other sqls.");
		pwCreate.print(otherSqlSchemaFile.toStringBuffer());
		pwCreate.flush();
		pwCreate.close();
		
		pwCleanup.print(otherSqlSchemaFile.deleteQuery());
		pwCleanup.flush();
		pwCleanup.print(RDBMSType.sSqlType.getCommitCommand() + RDBMSType.sSqlType.getNewline());
		pwCleanup.flush();
		pwCleanup.close();
		pwRemove.print(otherSqlSchemaFile.removeQuery());
		pwRemove.flush();
		pwRemove.close();

		LOGGER.log(Level.INFO, "SPM database scripts are generated in [%s]", outputFolderPath);
	}

	public JdbcTable createRuleTable(boolean isDrop) {
		JdbcTable ruleTable = new JdbcTable(DBConstant.RULE_TABLE_NAME, isDrop);
		ruleTable.addMember(DBConstant.RULE_NAME_FIELD, DataType.STRING, false);
		ruleTable.addMember(DBConstant.RULE_CONTENT_FIELD, "CLOB", true);
		ruleTable.addPrimaryKeys(ruleTable.getName() + "_PK", DBConstant.RULE_NAME_FIELD);
		return ruleTable;
	}

	private SqlSchemaFile generateSqlForRule() {
		SqlSchemaFile otherSqlSchemaFile = new SqlSchemaFile("Other");
		otherSqlSchemaFile.addTable(createRuleTable(false));
		return otherSqlSchemaFile;
	}

//	private SqlSchemaFile createProcessedFactsTables(RtaSchema schema, boolean isDropSql, SqlSchemaFile schemaFile) {
//		String schemaName = schema.getName();
//
//		if (insertProcessed) {
//			if (isInsertProcessedWithMultipleTable) {
//				for (Cube cube : schema.getCubes()) {
//					for (DimensionHierarchy dh : cube.getDimensionHierarchies()) {
//						// Create multiple processed fact tables per dimension
//						// hierarchy
//						JdbcTable processedFactTable = new JdbcTable(makeProcessedFactTableName(schemaName,
//								cube.getName(), dh), isDropSql);
//						processedFactTable.addMember(DBConstant.FACT_KEY_FIELD, DataType.STRING, false);
//						processedFactTable.addMember(DBConstant.CREATED_DATE_TIME_FIELD, "timestamp", false);
//						// Add Primary Key.
//						processedFactTable.addPrimaryKeys(processedFactTable.getName() + "_PK", DBConstant.FACT_KEY_FIELD);
//						// Index created time.
//						processedFactTable.addIndex(DBConstant.CREATED_DATE_TIME_FIELD, false);
//						schemaFile.addTable(processedFactTable);
//					}
//				}
//
//			} else {
//				JdbcTable processedFactTable = new JdbcTable(makeProcessedFactTableName(schemaName), isDropSql);
//
//				processedFactTable.addMember(DBConstant.FACT_KEY_FIELD, DataType.STRING);
//				processedFactTable.addMember(DBConstant.CUBE_FIELD, DataType.STRING);
//				processedFactTable.addMember(DBConstant.DIMHR_FIELD, DataType.STRING);
//				processedFactTable.addMember(DBConstant.CREATED_DATE_TIME_FIELD, "timestamp", false);
//				schemaFile.addTable(processedFactTable);
//			}
//
//		}
//		return schemaFile;
//	}
	
	private void createMetricTables(RtaSchema schema, boolean isDropSql, SqlSchemaFile schemaFile) {
		String schemaName = schema.getName();
		for (Cube cube : schema.getCubes()) {
			for (DimensionHierarchy dh : cube.getDimensionHierarchies()) {
				// Create Metric table.
				JdbcTable metricTable = new JdbcTable(makeMetricTableName(schemaName, cube.getName(), dh), isDropSql);
				if (!isUsePk && isMSSqlServer) {
					// SQL server only
					// If no primary key then and auto increment column as primary key.
					metricTable.addMember(DBConstant.METRIC_ID_FIELD, DataType.LONG, false);
					metricTable.addMember(DBConstant.METRIC_KEY_HASH_FIELD, DataType.STRING, false);
				}				
				metricTable.addMember(DBConstant.METRIC_KEY_FIELD, DataType.STRING, false, DBConstant.METRIC_KEY_MAX_SIZE);
				metricTable.addMember(DBConstant.DIMENSION_LEVEL_FIELD, DataType.INTEGER);
				metricTable.addMember(DBConstant.DIMENSION_LEVEL_NAME_FIELD, DataType.STRING);
				// index dimension level name
				metricTable.addIndex(DBConstant.DIMENSION_LEVEL_NAME_FIELD, false);
				metricTable.addMember(DBConstant.IS_PROCESSED, DataType.BOOLEAN);
				metricTable.addIndex(DBConstant.IS_PROCESSED, false);

				// Create Metric fact table.
				JdbcTable metricFactTable = new JdbcTable(makeMetricFactTableName(schemaName, cube.getName(), dh), isDropSql);
				metricFactTable.addMember(DBConstant.FACT_TABLE_PREFIX + DBConstant.FACT_KEY_FIELD, DataType.STRING, false);
				if (isUsePk) {
					metricFactTable.addMember(DBConstant.METRIC_TABLE_PREFIX + DBConstant.METRIC_KEY_FIELD, DataType.STRING, false, DBConstant.METRIC_KEY_MAX_SIZE);
				} else {
					metricFactTable.addMember(DBConstant.DIMENSION_LEVEL_NAME_FIELD, DataType.STRING);
				}
				StringBuilder compositeIndexFields = new StringBuilder(DBConstant.DIMENSION_LEVEL_NAME_FIELD);

				for (Dimension dim : dh.getDimensions()) {
					int size = getStorageSize(dim.getAssociatedAttribute());
					metricTable.addMember(dim.getName(), getStorageDataType(dim.getAssociatedAttribute()), true, size);
					// index all dimensions
					metricTable.addIndex(dim.getName(), false);
					if (!isUsePk) {
						metricFactTable.addMember(dim.getName(), getStorageDataType(dim.getAssociatedAttribute()), true, size);
					}
					compositeIndexFields.append("," + dim.getName());
				}
				
				ArrayList<JdbcTable> metricMultiValuedTables = new ArrayList<JdbcTable>();
				for (Measurement measurement : dh.getMeasurements()) {
					DataType dataType = getStorageDataType(measurement);
					int size = getStorageSize(measurement);
					
					MetricFunctionDescriptor md = measurement.getMetricFunctionDescriptor();
					if (md.isMultiValued()) {
						if (isToUseBlob) {
							// create blob column for multivalued function.
							metricTable.addMember(measurement.getName(), "BLOB", true);
						} else {
							// create a separate table for multivalued functions.
							String mvTableName = makeMetricMultiValuedTableName(dh, measurement.getName());
							LOGGER.log(Level.INFO, "Adding, MultiValued Table : " + mvTableName);
							JdbcTable metricMultiValuedTable = new JdbcTable(mvTableName, isDropSql);
							metricMultiValuedTable.addMember(DBConstant.METRIC_KEY_FIELD, DataType.STRING, false, DBConstant.METRIC_KEY_MAX_SIZE);
							if (dataType != null) {
								metricMultiValuedTable.addMember(DBConstant.MULTIVALUED_METRIC_TABLE_VALUE_FIELD, dataType, true, size);
							} else {
								metricMultiValuedTable.addMember(DBConstant.MULTIVALUED_METRIC_TABLE_VALUE_FIELD, md.getMetricDataType(), true);
							}
							
							// Index column to maintain insertion order.
							metricMultiValuedTable.addMember(DBConstant.MULTIVALUED_METRIC_TABLE_INDEX_FIELD, DataType.INTEGER, false);
							
							// Add created time column.
							metricMultiValuedTable.addMember(DBConstant.CREATED_DATE_TIME_FIELD, "timestamp", false);
							metricMultiValuedTables.add(metricMultiValuedTable);
						}
					} else {
						if (dataType != null) {
							metricTable.addMember(measurement.getName(), dataType, true, size);
						} else {
							metricTable.addMember(measurement.getName(), md.getMetricDataType());
						}
					}
					for (FunctionParam param : md.getFunctionContexts()) {
						metricTable.addMember(measurement.getName() + DBConstant.SEP + param.getName(),
								param.getDataType());
					}
				}
				addUpdatedCreatedTimeColumn(metricTable);
				// Index updated time for metric table
				metricTable.addIndex(DBConstant.UPDATED_DATE_TIME_FIELD, false);

				addUpdatedCreatedTimeColumn(metricFactTable);

				if (isUsePk) {
					metricTable.addPrimaryKeys(metricTable.getName() + "_PK", DBConstant.METRIC_KEY_FIELD);
					metricFactTable.addPrimaryKeys(metricFactTable.getName() + "_PK", DBConstant.FACT_TABLE_PREFIX
							+ DBConstant.FACT_KEY_FIELD + "," + DBConstant.METRIC_TABLE_PREFIX + DBConstant.METRIC_KEY_FIELD);
				} else {
					if (!isMSSqlServer) {
						// If not MS SQL server, do composite indexing.
						metricTable.addIndex(compositeIndexFields.toString(), false);
						metricFactTable.addIndex(compositeIndexFields.toString(), false);
					} else {
						metricTable.addIndex(DBConstant.METRIC_KEY_HASH_FIELD, false);
					}
				}
				schemaFile.addTable(metricTable);
				// schemaFile.addTable(metricFactTable);
				
				// Add MultiValued Metric tables if any.
				for (JdbcTable table : metricMultiValuedTables) {
					schemaFile.addTable(table);
				}
			}
		}
	}
	
	private void createRuleMetricTables(RtaSchema schema, boolean isDropSql, SqlSchemaFile schemaFile) {
		String schemaName = schema.getName();
		for (Cube cube : schema.getCubes()) {
			for (DimensionHierarchy dh : cube.getDimensionHierarchies()) {

				// Create Metric Rule table.
				JdbcTable ruleMetricTable = new JdbcTable(makeRuleMetricTableName(schemaName, cube.getName(), dh), isDropSql);
				if (!isUsePk && isMSSqlServer) {
					// SQL server only
					// If no primary key then and auto increment column as primary key.
					ruleMetricTable.addMember(DBConstant.RULE_METRIC_ID_FIELD, DataType.LONG, false);
					ruleMetricTable.addMember(DBConstant.RULE_METRIC_KEY_HASH_FIELD, DataType.STRING, false);
				}	
				ruleMetricTable.addMember(DBConstant.METRIC_KEY_FIELD, DataType.STRING, false, DBConstant.METRIC_KEY_MAX_SIZE);
				ruleMetricTable.addMember(DBConstant.RULE_NAME_FIELD, DataType.STRING, false);
				ruleMetricTable.addMember(DBConstant.RULE_ACTION_NAME_FIELD, DataType.STRING, false);
				ruleMetricTable.addMember(DBConstant.RULE_SET_COUNT_FIELD, DataType.INTEGER);
				ruleMetricTable.addMember(DBConstant.RULE_SCHEDULED_TIME_FIELD, DataType.LONG);
				ruleMetricTable.addMember(DBConstant.RULE_LAST_FIRED_TIME_FIELD, DataType.LONG);
				ruleMetricTable.addMember(DBConstant.DIMENSION_LEVEL_NAME_FIELD, DataType.STRING);
				ruleMetricTable.addMember(DBConstant.RULE_SET_CONDITION_KEY_FILED, DataType.STRING);
				ruleMetricTable.addMember(DBConstant.RULE_CLEAR_CONDITION_KEY_FILED, DataType.STRING);

				for (Dimension dim : dh.getDimensions()) {
					int size = getStorageSize(dim.getAssociatedAttribute());
					ruleMetricTable.addMember(dim.getName(), getStorageDataType(dim.getAssociatedAttribute()), true, size);
				}
				
				for (Measurement measurement : dh.getMeasurements()) {
					DataType dataType = getStorageDataType(measurement);
					int size = getStorageSize(measurement);
					
					MetricFunctionDescriptor md = measurement.getMetricFunctionDescriptor();
					if (!md.isMultiValued()) {
						if (dataType != null) {
							ruleMetricTable.addMember(measurement.getName(), dataType, true, size);
						} else {
							ruleMetricTable.addMember(measurement.getName(), md.getMetricDataType());							
						}
					}
					for (FunctionParam param : md.getFunctionContexts()) {
						ruleMetricTable.addMember(measurement.getName() + DBConstant.SEP + param.getName(),
								param.getDataType());
					}
				}

				// RuleMetric table
				addUpdatedCreatedTimeColumn(ruleMetricTable);
				ruleMetricTable.addMember(DBConstant.IS_PROCESSED, DataType.BOOLEAN);
				
				if (isUsePk) {
					// Composite Key rule metric table.
					ruleMetricTable.addPrimaryKeys(ruleMetricTable.getName() + "_PK", DBConstant.METRIC_KEY_FIELD + ","
							+ DBConstant.RULE_NAME_FIELD + "," + DBConstant.RULE_ACTION_NAME_FIELD);
				} else {
					// individually index each column with disabled uniqueness
					if (!isMSSqlServer) {
						ruleMetricTable.addIndex(DBConstant.METRIC_KEY_FIELD, false);
					} else {
						ruleMetricTable.addIndex(DBConstant.RULE_METRIC_KEY_HASH_FIELD, false);
					}
					ruleMetricTable.addIndex(DBConstant.RULE_NAME_FIELD, false);
					ruleMetricTable.addIndex(DBConstant.RULE_ACTION_NAME_FIELD, false);
				}
				// Index scheduled time.
				ruleMetricTable.addIndex(DBConstant.RULE_SCHEDULED_TIME_FIELD, false);
				schemaFile.addTable(ruleMetricTable);
			}
		}
	}

	private void createAssetTables(RtaSchema schema, boolean isDropSql,
			SqlSchemaFile schemaFile) {
		// Create Asset tables.
//		for (Asset asset : schema.getAssets()) {
//			JdbcTable assetTable = new JdbcTable(makeAssetTableName(asset), isDropSql);
//			assetTable.addMember(DBConstant.ASSET_UID_FIELD, DataType.STRING, false);
//			assetTable.addMember(DBConstant.ASSET_NAME_FIELD, DataType.STRING, false);
//			// Composite index on mandatory fields.
//			StringBuilder indexColumns = new StringBuilder();
//			String separator = "";
//			for(AssetAttribute attribute : asset.getAttributes()){
//				int size = getStorageSize(attribute);
//				assetTable.addMember(attribute.getName(), getStorageDataType(attribute), true, size);			
//				if (attribute.isMandatory()) {
//					indexColumns.append(separator + attribute.getName());
//					separator = ",";
//				}				
//			}
//			if(indexColumns.length() > 0){
//				assetTable.addIndex(indexColumns.toString(), false);
//			}
//			assetTable.addMember(DBConstant.ASSET_STATUS_FIELD, DataType.INTEGER, false);
//			assetTable.addMember(DBConstant.ASSET_IS_DELETED_FIELD, DataType.BOOLEAN);
//			assetTable.addMember(DBConstant.ASSET_TIMESTAMP_FIELD, "timestamp", false);
//			addUpdatedCreatedTimeColumn(assetTable);
//			// make UID field as primary key.
//			assetTable.addPrimaryKeys(assetTable.getName() + "_PK", DBConstant.ASSET_UID_FIELD);
//			schemaFile.addTable(assetTable);
//		}
	}

	private void createFactTables(RtaSchema schema, boolean isDropSql, SqlSchemaFile schemaFile) {
		String schemaName = schema.getName();

		// Add Fact Table.
		JdbcTable factTable = new JdbcTable(makeFactTableName(schemaName), isDropSql);
		factTable.addMember(DBConstant.FACT_KEY_FIELD, DataType.STRING, false);
		factTable.addMember(DBConstant.OWNER_SCHEMA_FIELD, DataType.STRING);
		Iterator<Attribute> i = schema.getAttributes().iterator();
		while (i.hasNext()) {
			Attribute attribute = i.next();
			int size = getStorageSize(attribute);
			factTable.addMember(attribute.getName(),
					getStorageDataType(attribute), true, size);
		}

//		if (!insertProcessed) {
//			for (Cube cube : schema.getCubes()) {
//				for (DimensionHierarchy dh : cube.getDimensionHierarchies()) {
//					factTable.addMember(cube.getName() + DBConstant.SEP
//							+ getStorageSchema(dh), DataType.BOOLEAN);
//				}
//			}
//		}

		addUpdatedCreatedTimeColumn(factTable);
		factTable.addPrimaryKeys(factTable.getName() + "_PK", DBConstant.FACT_KEY_FIELD);
		// Index created time in Fact table.
		factTable.addIndex(DBConstant.CREATED_DATE_TIME_FIELD, false);

		schemaFile.addTable(factTable);

	}

	private SqlSchemaFile generateSqlForRtaSchema(RtaSchema schema, boolean isDropSql) {
		String schemaName = schema.getName();

		SqlSchemaFile schemaFile = new SqlSchemaFile(schemaName);
		createFactTables(schema, isDropSql, schemaFile);
		
		// Not to create processed fact table.
		// createProcessedFactsTables(schema, isDropSql, schemaFile);
		
		createMetricTables(schema, isDropSql, schemaFile);
		createRuleMetricTables(schema, isDropSql, schemaFile);
		createAssetTables(schema, isDropSql, schemaFile);
//		createAlertTableSchema(schema, isDropSql, schemaFile);
		createSessionTable(isDropSql);
		return schemaFile;
	}
	

	private void createAlertTableSchema(RtaSchema schema, boolean isDropSql, SqlSchemaFile schemaFile) {
		String schemaName = schema.getName();
		
		for (Cube cube : schema.getCubes()) {
			for (DimensionHierarchy dh : cube.getDimensionHierarchies()) {

				// Create Metric Rule table.
				JdbcTable alertTable = new JdbcTable(makeAlertTableName(schemaName, cube.getName(), dh), isDropSql);
				
				alertTable.addMember(DBConstant.ALERT_ID, DataType.STRING, false, DBConstant.METRIC_KEY_MAX_SIZE);
				alertTable.addMember(DBConstant.METRIC_KEY_FIELD, DataType.STRING, false, DBConstant.METRIC_KEY_MAX_SIZE);
				alertTable.addMember(DBConstant.RULE_NAME_FIELD, DataType.STRING, false);
				alertTable.addMember(DBConstant.RULE_ACTION_NAME_FIELD, DataType.STRING, false);
				alertTable.addMember(DBConstant.RULE_SET_COUNT_FIELD, DataType.INTEGER);
				alertTable.addMember(DBConstant.RULE_SCHEDULED_TIME_FIELD, DataType.LONG);
				alertTable.addMember(DBConstant.RULE_LAST_FIRED_TIME_FIELD, DataType.LONG);
				alertTable.addMember(DBConstant.DIMENSION_LEVEL_NAME_FIELD, DataType.STRING);
				alertTable.addMember(DBConstant.RULE_SET_CONDITION_KEY_FILED, DataType.STRING);
				alertTable.addMember(DBConstant.RULE_CLEAR_CONDITION_KEY_FILED, DataType.STRING);


				
				ArrayList<JdbcTable> metricMultiValuedTables = new ArrayList<JdbcTable>();
				for (Measurement measurement : dh.getMeasurements()) {
					MetricFunctionDescriptor md = measurement.getMetricFunctionDescriptor();
					DataType dataType = getStorageDataType(measurement);
					int size = getStorageSize(measurement);
					
					if (md.isMultiValued()) {
						if (isToUseBlob) {
							// create blob column for multi-valued function.
							alertTable.addMember(measurement.getName(), "BLOB", true);
						} else {
							//TODO for multi-valued RuleMetricTable?
						}
					} else {	
						if (dataType!= null) {
							alertTable.addMember(measurement.getName(), dataType, true, size);
						} else {
							alertTable.addMember(measurement.getName(), md.getMetricDataType());
						}
					}
//Don't want contexts					
//					for (FunctionParam param : md.getFunctionContexts()) {
//						alertTable.addMember(measurement.getName() + DBConstant.SEP + param.getName(),
//								param.getDataType());
//					}
				}
				
				// Add MultiValued Metric tables if any.
				for (JdbcTable table : metricMultiValuedTables) {
					schemaFile.addTable(table);
				}

				// Alert table
				addUpdatedCreatedTimeColumn(alertTable);
				// Composite Key rule metric table.
				alertTable.addPrimaryKeys(alertTable.getName() + "_PK", DBConstant.ALERT_ID);
				
				// Index scheduled time.
				alertTable.addIndex(DBConstant.RULE_SCHEDULED_TIME_FIELD, false);
				alertTable.addIndex(DBConstant.CREATED_DATE_TIME_FIELD, false);
				alertTable.addIndex(DBConstant.UPDATED_DATE_TIME_FIELD, false);
				schemaFile.addTable(alertTable);
			}
		}
		
	}

	public SqlLineBrowser getCreateSqlsForRule() {
		SqlSchemaFile schemaFile = generateSqlForRule();
		return new SqlLineBrowser(schemaFile.toStringBuffer());
	}

	public SqlLineBrowser getCreateSqlsForRtaSchema(RtaSchema schema) {
		SqlSchemaFile schemaFile = generateSqlForRtaSchema(schema, false);
		return new SqlLineBrowser(schemaFile.toStringBuffer());
	}

	public JdbcTable createSessionTable(boolean isDrop) {
		JdbcTable sessionTable = new JdbcTable(DBConstant.SESSION_TABLE_NAME, isDrop);
		sessionTable.addMember(DBConstant.SESSION_UID, DataType.STRING, false);
		sessionTable.addMember(DBConstant.SESSION_ID, DataType.STRING);
		sessionTable.addMember(DBConstant.SESSION_NAME, DataType.STRING);
		sessionTable.addMember(DBConstant.SESSION_QUERY_NAME, DataType.STRING);
		sessionTable.addMember(DBConstant.SESSION_QUERY_DETAIL, "CLOB", true);
		sessionTable.addMember(DBConstant.SESSION_RULE_NAME, DataType.STRING);
		sessionTable.addMember(DBConstant.SESSION_RULE_DETAIL, "CLOB", true);
		sessionTable.addPrimaryKeys(sessionTable.getName() + "_PK", DBConstant.SESSION_UID);
		return sessionTable;
	}

	public SqlLineBrowser getCreateSqlsForSession() {
		return new SqlLineBrowser(createSessionTable(false).toStringBuffer());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			JdbcDeployment dbDeployment = new JdbcDeployment(args);
			if (dbDeployment.loadRtaSchema()) {
				dbDeployment.deploySql();
				LOGGER.log(Level.INFO, "Done");
			} else {
				LOGGER.log(Level.ERROR, "Exiting.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Properties parseArguments(String argv[]) {

//		if (argv.length < 2 || (argv.length % 2 != 0)) {
//			printUsage();
//			System.exit(0);
//		}
		Properties props = new Properties();
		for (int i = 0; i < argv.length; i++) {
			String key = argv[i];
			if (key.matches("-h|/h|-help|/help|--help|/--help")) {
				printUsage();
				System.exit(0);
			} else if (key.matches("-p|/p|-property|/property")) {
				++i;
				// put properties file if provided
				props.put("spm.bootstrap.property.file", argv[i]);
			} else if (key.matches("-c|/c|-config|/config")) {
				++i;
				props.put("jdbcdeploy.spm.config.path", argv[i]);
			} else if (key.matches("-o|/o|-out|/out")) {
				++i;
				props.put("jdbcdeploy.schema.output.path", argv[i]);
			} else if (key.matches("-s|/s|-schema|/schema")) {
				++i;
				props.put("jdbcdeploy.bootstrap.basetype.file", argv[i]);
			} else if (key.matches("-d|/d|-database|/database")) {
				++i;
				props.put("jdbcdeploy.database.type", argv[i]);
			} else if (key.matches("-m|/m|-migrate|/migrate")) {
				++i;
				if ("true".equalsIgnoreCase(argv[i])) {
					isMigration = true;
				} else {
					isMigration = false;
				}
			} else if (key.matches("-ansi|/ansi")) {
				++i;
				if (argv[i].equalsIgnoreCase("false")) {
					useAnsi = false;
				} else {
					useAnsi = true;
				}
			} else if (key.matches("-showenv|/showenv")) {
				showEnv = true;
			} else if (key.matches("-optimize|/optimize")) {
//				optimize = true;
			} else {
				props.put("jdbcdeploy.repourl", argv[i]);
			}
		}

		return props;
	}

	private void printUsage() {
		StringBuilder bldr = new StringBuilder();
		bldr.append(BRK + "tibspmddlgenerator [-c <path to SPM schema files or a XML schema file>] [-o < database DDL output folder>] [-d <database>]");
		bldr.append(BRK + "\t <database> is one of oracle, postgresql, db2, sqlserver");
		LOGGER.log(Level.INFO, bldr.toString());
	}

	private void loadProperties(String fileName) throws FileNotFoundException, IOException {
			env.load(new FileInputStream(fileName));
//		} catch (FileNotFoundException e1) {
//			e1.printStackTrace();
//			System.exit(1);
//		} catch (IOException e1) {
//			e1.printStackTrace();
//			System.exit(1);
//		}
	}

	private void showProperties() {
		Iterator<Entry<Object, Object>> propEntrySetIterator = env.entrySet().iterator();
		LOGGER.log(Level.INFO, "-----------Configured Properties-----------" + BRK);
		StringBuilder bldr = new StringBuilder();
		while (propEntrySetIterator.hasNext()) {
			Entry<Object, Object> e = propEntrySetIterator.next();
			bldr.append(e.getKey() + "=" + e.getValue() + BRK);
		}
		LOGGER.log(Level.INFO, bldr.toString() + BRK + "-----------End Configured Properties-----------");
	}

	protected Connection getConn() {
		String dbUrl = null;
		String dbDrv = null;
		Connection conn = null;
		try {
			dbUrl = (String) ConfigProperty.RTA_JDBC_URL.getValue(env);
			LOGGER.log(Level.INFO, "Connecting to Database URL :" + dbUrl);
			dbDrv = (String) ConfigProperty.RTA_JDBC_DRIVER.getValue(env);
			if (dbUrl != null && !dbUrl.trim().isEmpty() && dbDrv != null && !dbDrv.trim().isEmpty()) {
				// schemaOwner = env.getProperty("spm.jdbc.schema.owner");
				// if (schemaOwner != null) {
				// schemaOwner = schemaOwner.trim();
				// }
				Class.forName(dbDrv);
				String user = (String) ConfigProperty.RTA_JDBC_USER.getValue(env);
				// Assuming schema owner is the current database user.
				schemaOwner = user;
				conn = DriverManager.getConnection(dbUrl, user, (String) ConfigProperty.RTA_JDBC_PASSWORD.getValue(env));
				if (conn != null) {
					LOGGER.log(Level.INFO, "Successfully connected to database: " + dbUrl);
				}
			} else {
				LOGGER.log(Level.ERROR, "Not a valid database JDBC URL/Driver.");
			}
			return conn;
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "Failed connecting to database: " + dbUrl);
			e.printStackTrace();
		}
		return null;
	}

	// private Properties loadAliasesFromDB(Connection conn) {
	// Properties alz = new Properties();
	// try {
	// Statement stmt = conn.createStatement();
	// String query = (schemaOwner == null) ?
	// "Select SPM, ALIAS from SPM_ALIASES"
	// : "Select SPM_NAME, ALIAS from " + schemaOwner + ".SPM_ALIASES";
	// ResultSet rs = stmt.executeQuery(query);
	// while (rs.next()) {
	// String name = rs.getString(1);
	// String als = rs.getString(2);
	// alz.put(name, als);
	// }
	// } catch (Exception e) {
	// errStream.println("Failed to load SPM ALIASES: " + e.getMessage());
	// }
	// return alz;
	// }

	private void addUpdatedCreatedTimeColumn(JdbcTable table) {
		table.addMember(DBConstant.CREATED_DATE_TIME_FIELD, "timestamp", false);
		table.addMember(DBConstant.UPDATED_DATE_TIME_FIELD, "timestamp", false);
	}

	private DataType getStorageDataType(MetadataElement metaElem) {
		String dataType = metaElem.getProperty(ModelSerializationConstants.ATTR_STORAGE_DATATYPE_NAME);
		if (dataType!= null && dataType.length() != 0) {
			return DataType.valueOf(dataType);
		} else if (metaElem instanceof Attribute) {
			return ((Attribute)metaElem).getDataType();
		}
		return null;
	}
	
	private int getStorageSize(MetadataElement metaElem) {
		String storageSize = metaElem.getProperty(DBConstant.ATTR_STORAGE_SIZE);
		if (storageSize != null && !storageSize.isEmpty()) {
			return Integer.parseInt(storageSize);
		}
		return 0;
	}
	
	public String getStorageSchema(DimensionHierarchy dh) {
		String storageSchema = dh.getProperty(DBConstant.ATTR_STORAGE_SCHEMA);
		if (storageSchema == null || storageSchema.isEmpty()) {
//			LOGGER.log(Level.INFO, String.format("No StorageSchema attribute defined for Dimension HierarchyName: %s", dh.getName()));
			// No Storage schema defined, use hierarchy name as storage schema
			storageSchema = dh.getName();
		}
		return storageSchema;
	}
	
	public String makeFactTableName(String schemaName) {
		return removeSplChars(DBConstant.FACT_TABLE_PREFIX + schemaName);
	}

	public String makeMetricTableName(String schemaName, String cubeName, DimensionHierarchy dh) {
		return removeSplChars(DBConstant.METRIC_TABLE_PREFIX + getStorageSchema(dh));
	}
	
	public String makeMetricMultiValuedTableName(DimensionHierarchy dh, String measurementName) {
		String tableName = "mv" + DBConstant.SEP  + getStorageSchema(dh) + DBConstant.SEP + measurementName;
		if (tableName.length() > 28) {
			tableName = tableName.substring(0, 28);
		}
		return removeSplChars(tableName);
	}

	public String makeMetricFactTableName(String schemaName, String cubeName, DimensionHierarchy dh) {
		return removeSplChars(DBConstant.METRICS_FACT_TABLE_PREFIX + getStorageSchema(dh));
	}

	public String makeRuleMetricTableName(String schemaName, String cubeName, DimensionHierarchy dh) {
		return removeSplChars(DBConstant.RULE_METRIC_TABLE_PREFIX + getStorageSchema(dh));
	}
	
	public String makeAlertTableName(String schemaName, String cubeName, DimensionHierarchy dh) {
		return removeSplChars(DBConstant.ALERT_METRIC_TABLE_PREFIX + getStorageSchema(dh));
	}

	public String makeProcessedFactTableName(String schemaName) {
		return removeSplChars(DBConstant.PROCESSED_FACT_TABLE_PREFIX + schemaName);
	}
	
	public String makeProcessedFactTableName(String schemaName, String cubeName, DimensionHierarchy dh) {
		return removeSplChars(DBConstant.PROCESSED_FACT_TABLE_PREFIX + getStorageSchema(dh));
	}

	private String removeSplChars(String str) {
		return str.replaceAll("-", DBConstant.SEP);
	}

	private static class AttrColumnInfo {
		String attrName;
		String attrType;
		public int length = -1;

		public AttrColumnInfo(String columnName, String columnType) {
			// Get rid of any schema name prefix
			int dotIndex = columnName.indexOf('.');
			if (dotIndex < 0) {
				attrName = columnName;
			} else {
				attrName = columnName.substring(dotIndex + 1);
			}
			attrType = columnType;
		}

		@Override
		public String toString() {
			return "AttrColumnInfo [attrName=" + attrName + ", attrType=" + attrType + ", length=" + length + "]";
		}
	}

}
