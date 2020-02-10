package com.tibco.cep.modules.db.model.runtime;

import java.io.Writer;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import oracle.sql.CLOB;

import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.modules.db.functions.FunctionHelper;
import com.tibco.cep.modules.db.functions.JDBCHelper;
import com.tibco.cep.modules.db.model.designtime.DBConstants;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.Property.PropertyConcept;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.impl.GeneratedConceptImpl;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;

abstract public class AbstractDBConceptImpl extends GeneratedConceptImpl implements DBConcept {

	abstract public String getJDBCResourceName();
	
	abstract public String[] getPropertyNames();
	
	protected String getInsertStatement() {
		return null;
	}

	protected String getUpdateStatement() {
		return null;
	}

	protected String getDeleteStatement() {
		return null;
	}
	
	//protected String getSelectStatement() {return null;}
	abstract public String[][] getPropertyToColMap();

	abstract protected int setStatementParameters(PreparedStatement stmt, int startCount, StringBuffer insertStmt) throws SQLException;
	abstract protected int setWhereClauseParameters(PreparedStatement stmt, int startCount) throws SQLException;
	
	static boolean schemaNameInExtId ;
	static String readInsertedRow;
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(AbstractDBConceptImpl.class); 

	static {
		try {
			if (RuleServiceProviderManager.getInstance().getDefaultProvider() != null) {
				schemaNameInExtId = RuleServiceProviderManager.getInstance().getDefaultProvider().getProperties().getProperty("be.dbconcepts.extid.include.schemaname", "false").trim().equals("true");
				readInsertedRow = RuleServiceProviderManager.getInstance().getDefaultProvider().getProperties().getProperty(
						"be.dbconcepts.read.autogenkeys", "false");
			}
		} catch (Exception e) {
			RuleServiceProviderManager.getInstance().getDefaultProvider().getLogger(AbstractDBConceptImpl.class)
                    .log(Level.WARN, " ## Error in reading TRA properties  !! %n%s", e.getMessage());
		}
	}

	public AbstractDBConceptImpl() {
		super();
	}

	public AbstractDBConceptImpl(long _id) {
		super(_id);
	}

	public AbstractDBConceptImpl(long _id, String extId) {
		super(_id, extId);
	}


	/**
	 * Inserts the concept to the database table.
	 */
//	public void insertOld() throws SQLException {

//	Connection conn = JDBCHelper
//	.getCurrentConnection(getJDBCResourceName());
//	String sql = getInsertStatement();
//	PreparedStatement stmt = conn.prepareStatement(sql);
//	StringBuffer buf = new StringBuffer().append(
//	sql.substring(0, sql.indexOf("VALUES"))).append(" (");

//	setStatementParameters(stmt, 1, buf);
//	buf.append(")");

//	Logger logger = RuleServiceProviderManager.getInstance().getDefaultProvider().getLogger();
//	if (logger != null && logger.isDebug()) {
//	logger.logDebug(conn + " " + buf.toString());
//	}
//	stmt.executeUpdate();
//	stmt.close();

//	}
	public void insert(String... primaryKeys) throws Exception {
		PreparedStatement stmt = null;
		boolean usingAutoGenKeys = false;
		Clob clob = null;
		try {
			Connection conn = JDBCHelper
			.getCurrentConnection(getJDBCResourceName());

			String[] props = getPropertyNames();
			Map propVals = new LinkedHashMap();
			for (int i = 0; i < props.length; i++) {
				String propName = props[i];
				Property p = getPropertyNullOK(propName);

				if (p != null) {
					if (p instanceof PropertyAtom
							&& !(p instanceof PropertyConcept)) {
						Object val = FunctionHelper
						.getPropertyValNullOK((PropertyAtom) p);
						String dataType = getDBColumnDataType(propName);
						if (dataType!= null && dataType.equalsIgnoreCase("xmltype")) {
							try {
								clob = getCLOB((String)val, conn);
							} catch (Exception e) {
								clob = null;
							}
							if (clob != null)
								propVals.put(propName, clob);
						} else
							propVals.put(propName, val);
					}
				}
			}
			// Obtain the table name using Function Helper

			// set the column names in sql string
			StringBuffer qStr = new StringBuffer(32);
			StringBuffer colBuf = new StringBuffer(64);
			for (Iterator i = propVals.entrySet().iterator(); i.hasNext();) {
				Map.Entry e = (Entry) i.next();
				String propName = (String) e.getKey();
				String colName = FunctionHelper.getColForProp(
						getPropertyToColMap(), propName);
				if(colName != null) {
					
					String dataType = getDBColumnDataType(propName);
					if (dataType!= null && dataType.equalsIgnoreCase("xmltype")) {
						colBuf.append(colName);
						qStr.append("(XMLType(?))");
					}
					else{
						colBuf.append(colName);
						qStr.append('?');
					}
					colBuf.append(',').append(' ');
					qStr.append(',').append(' ');
				}
			}
			if(colBuf.toString().endsWith(", ")) {
				colBuf = new StringBuffer(colBuf.substring(0,colBuf.lastIndexOf(",")));
				qStr = new StringBuffer(qStr.substring(0,qStr.lastIndexOf(",")));
			}
			StringBuffer sql = new StringBuffer(128).append("insert into ")
			.append(getDelimitedTableName()).append(' ');
			sql.append('(').append(colBuf).append(')');
			sql.append(" values ");
			sql.append('(').append(qStr).append(')');

			if("true".equalsIgnoreCase(readInsertedRow)) {
				try {
					String[] columnNames = primaryKeys;
					stmt = conn.prepareStatement(sql.toString(), columnNames);
//					stmt = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
					usingAutoGenKeys = true;
				} catch (Exception e) {
					// Above call will fail if underlying db driver does not support it.
					// In that case, do not read auto-generated keys
					stmt = conn.prepareStatement(sql.toString());
				}
			} else {
				stmt = conn.prepareStatement(sql.toString());
			}
			String prepStmtSql = sql.toString();
			sql.append(" (");
			int psIndex = 1;
			for (Iterator i = propVals.entrySet().iterator(); i.hasNext();) {
				Map.Entry e = (Entry) i.next();
				String propName = (String) e.getKey();
				String colName = FunctionHelper.getColForProp(
						getPropertyToColMap(), propName);
				if(colName != null) {
					Object val = e.getValue();
					if (val != null && val instanceof Calendar) {
						val = new java.sql.Timestamp(((Calendar) val)
								.getTimeInMillis());
					}
					
					stmt.setObject(psIndex, val);
					psIndex++;
					
					sql.append(val);
					sql.append(',').append(' ');
					
				}
			}
			if(sql.toString().endsWith(", ")) {
				sql = new StringBuffer(sql.substring(0,sql.lastIndexOf(",")));
			}
			sql.append(")\n");

			if (LOGGER != null) {
                LOGGER.log(Level.DEBUG, "%s %s", conn, sql);
            }

			try {
				
				stmt.executeUpdate();
				
			} catch (SQLException e) {				
				String ExceptionMsg = "ORA-00932: inconsistent datatypes: expected NUMBER got -";
				if((usingAutoGenKeys == true) && (e.getMessage().contains(ExceptionMsg))){
					stmt = constructPrepStmtUsingPK(conn, prepStmtSql, propVals);
					stmt.executeUpdate();
				} else {
					// If the exception is not caused due to Oracle IOT, then throw back the original exception
					throw e;
				}
			}

			if ("true".equalsIgnoreCase(readInsertedRow)) {
				readInsertedRow(conn, stmt);
			}

		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
				}
			}
		}

	}
	
    protected static Clob getCLOB(String value, Connection conn) throws SQLException {
    	if(value == null || value.trim().length() == 0)
    		return null;
    	CLOB tempClob = null;
    	try{
    		// If the temporary CLOB has not yet been created, create new
    		tempClob = CLOB.createTemporary(conn, true, CLOB.DURATION_SESSION);

    		// Open the temporary CLOB in readwrite mode to enable writing
    		tempClob.open(CLOB.MODE_READWRITE);
    		// Get the output stream to write
    		Writer tempClobWriter = tempClob.getCharacterOutputStream();
    		// Write the data into the temporary CLOB
    		tempClobWriter.write(value);

    		// Flush and close the stream
    		tempClobWriter.flush();  	      
    	} catch(SQLException sqlexp) {
    		if (tempClob != null) {
    			tempClob.freeTemporary();
    		}
    		if (LOGGER != null) {
                LOGGER.log(Level.ERROR, sqlexp,
                        "Error while writing XMLType to DB");
            }
    	} catch(Exception exp) {
    		if (tempClob != null) {
    			tempClob.freeTemporary();
    		}
    		if (LOGGER != null) {
                LOGGER.log(Level.ERROR, exp,
                        "Error while writing XMLType to DB");
            }
    	} finally {
    		if (tempClob != null) {
    			tempClob.close(); 
    		}
    	}
    	return tempClob;
  	} 

	/**
	 * inserts the Concepts and its dependencies in correct order
	 * 
	 * @throws SQLException
	 */
	public void insertGraph() throws SQLException {

	}

//	/**
//	* queries the individual concept
//	*/
//	public void query() throws SQLException {
//	String sql = getSelectStatement();
//	}

//	/**
//	*  queries the concept and its relationships
//	*/
//	public void queryGraph() throws SQLException {

//	}

	/**
	 * Updates the concept to the database table.
	 */
	public void update() throws SQLException {
		String sql = getUpdateStatement();
		Connection conn = JDBCHelper.getCurrentConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		StringBuffer buf = new StringBuffer();
		int lastCount = setStatementParameters(stmt, 1, buf);
		setWhereClauseParameters(stmt, lastCount);
		stmt.executeUpdate();
		stmt.close();

	}

	/**
	 * Deletes the concept from the database table.
	 */
	public void remove() throws SQLException{
		Connection conn = JDBCHelper.getCurrentConnection();
		String sql = getDeleteStatement();
		PreparedStatement stmt = conn.prepareStatement(sql);
		setWhereClauseParameters(stmt, 1);
		super.delete();
	}

	public Map getPrimaryKeyMap() {
		Map pkMap = new LinkedHashMap();

		String pkNames [] = getPrimaryKeyNames();

		for (int i=0; i<pkNames.length; i++) {
			pkMap.put(pkNames[i], getProperty(pkNames[i]));
		}
		return pkMap;
	}


	/**
	 * This implementation returns primary key properties, 
	 * extending class should return appropriate value 
	 * @return String[] used to form extId
	 * @see com.tibco.cep.modules.db.model.runtime.DBConstants#getExtIdPropertyNames()
	 */
	public String[] getExtIdPropertyNames(){
		return getPrimaryKeyNames();
	}

	/**
	 * This implementation is used if EXTID_PREFIX is NOT specified in the Concept extended props
	 * Depending on the TRA property -  be.dbconcepts.extid.include.schemaname , it includes the schema name or not
	 * @return String used as prefix
	 * @see com.tibco.cep.modules.db.model.runtime.DBConstants#getExtIdPrefix()
	 */
	public String getExtIdPrefix(){
		com.tibco.cep.designtime.model.element.Concept dtCept = FunctionHelper.getDTConceptFromRT(this);
		String extIdPrefix = FunctionHelper.getSimpleTableName(dtCept);
		if(schemaNameInExtId){
			extIdPrefix =  FunctionHelper.getDelimitedSchemaName(dtCept) + "." + extIdPrefix;
		}	
		return extIdPrefix;
	}

	public String getDelimitedTableName(){
		com.tibco.cep.designtime.model.element.Concept dtCept = FunctionHelper.getDTConceptFromRT(this);
		return FunctionHelper.getDelimitedFQDBObjectName(dtCept);
	}
	
	/**
     * Get property of dbconcept and associated column name whose extended
     * property has data type identity.
     * @param dtCept
     * @return 
     */
    private String getIdentityDataTypeColumnName(com.tibco.cep.designtime.model.element.Concept dtCept) {
        List<PropertyDefinition> allProperties = dtCept.getPropertyDefinitions(false);
        
        for (PropertyDefinition propertyDefinition : allProperties) {
            //Get extended properties
            Map<?, ?> extendedProperties = propertyDefinition.getExtendedProperties();
            //Check one with data_type
            String dataType = (String)extendedProperties.get(DBConstants.DATA_TYPE);
            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                LOGGER.log(Level.DEBUG, "Data Type %s", dataType);
            }
            //TODO Import needs to be fixed here
            if (dataType.toLowerCase().contains(DBConstants.SQL_SERVER_IDENTITY_COLUMN)) {
                return (String)extendedProperties.get(DBConstants.COLUMN_NAME);
            }
        }
        return null;
    }

	private void readInsertedRow(Connection conn, Statement stmt) {
		try {
			DatabaseMetaData dbMetaData = conn.getMetaData();
            String dbName = dbMetaData.getDatabaseProductName();
            LOGGER.log(Level.INFO, "Database product name %s", dbName);
			ResultSet rs = stmt.getGeneratedKeys();
			String columnName = null;
			Object val = null;
			boolean toExec = false;
			StringBuffer whereClause = new StringBuffer("");
			
			//Get designtime concept
            com.tibco.cep.designtime.model.element.Concept dtCept = FunctionHelper.getDTConceptFromRT(this);
            
			while (rs.next()) {
				int cc = rs.getMetaData().getColumnCount();
				for (int i = 0; i < cc; i++) {
					try {
						columnName = rs.getMetaData().getColumnName(i + 1);
						 /**
                         * This check is required for sqlserver since for autogenkeys
                         * it does not return the exact column(s) which contain(s) the key.
                         */
                        if (DBConstants.SQL_SERVER_DB_NAME.equalsIgnoreCase(dbName)) {
                            //Check if it is generated keys
                            if (DBConstants.SQL_SERVER_GENERATED_KEYS_COLUMN.equalsIgnoreCase(columnName)) {
                                //Expected to have only one identity column since
                                //only one is allowed in MS SQL
                                //Find property with metadata such that it has identity type
                                columnName = getIdentityDataTypeColumnName(dtCept);
                            }
                        } 
                        if (LOGGER.isEnabledFor(Level.DEBUG)) {
                            LOGGER.log(Level.DEBUG, "Retrieved column name %s", columnName);
                        }
						val = FunctionHelper.getValueAtIndex(i + 1, rs);

						if (val == null || columnName == null ){
							continue;
						}
						if (i >= 1) {
							whereClause.append(" and  ");
						}
						if (val instanceof String) {
							whereClause.append(columnName).append(" = ").append('\'').append(val).append('\'');
							toExec = true;
						} else if (val instanceof BigDecimal) {
							whereClause.append(columnName).append(" = ").append(((BigDecimal) val).doubleValue());
							toExec = true;
						} else {
							//else, try to get ROWID.stringValue() on this.... nasty ! (oracle thin driver)
							String rowid = (String)FunctionHelper.invokeMethod(val, "stringValue");
							if (rowid != null) {
								whereClause.append(columnName).append(" = ").append('\'').append(rowid).append('\'');
								toExec = true;
							}
						}
						if (LOGGER.isEnabledFor(Level.DEBUG)) {
                            LOGGER.log(Level.DEBUG, "Predicate clause for select query %s", whereClause);
                        }
					}catch (Exception e) {
						LOGGER.log(Level.WARN, e, "Cannot read auto-generated keys name/value **");
					}
				}
			}

			try {
				rs.close();
			} catch (Exception e) {

			}

			Statement stmtThisRec = null;
			try {
				// load row back from db
				stmtThisRec = conn.createStatement();
				if (toExec == true) {
					StringBuilder sqlThisRec = new StringBuilder("select * from ").append(getDelimitedTableName()).append(" where ");
					sqlThisRec.append(whereClause);
					String sqlString = sqlThisRec.toString();
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "Select query with auto gen key %s", sqlString);
                    }
					ResultSet s = stmtThisRec.executeQuery(sqlString);
					if (s != null) {
						while (s.next()) {
							setProperties(s);
						}
					}
					s.close();
				}
			} catch (Exception e) {
				LOGGER.log(Level.WARN, e, "Cannot read row based on auto-generated keys ** %s", columnName);
			} finally {
				if (stmtThisRec != null) {
					try {
						stmtThisRec.close();
					} catch (Exception e) {

					}
				}
			}
		} catch (Exception e) {
			LOGGER.log(Level.WARN, e, "Cannot read auto-generated keys **");
		}
	}

	private PreparedStatement constructPrepStmtUsingPK(Connection conn, String prepStmtSql, Map propVals) throws SQLException{
		if (LOGGER != null) {
            LOGGER.log(Level.DEBUG, "It appears that above exception has occurred because of retrieving "
                    + "ROWID from Oracle IOT. Trying insert again by using PK in PrepStmt.");
        }
		PreparedStatement stmt = conn.prepareStatement(prepStmtSql, getPrimaryKeyNames());
		int psIndex = 1;
		for (Iterator i = propVals.entrySet().iterator(); i.hasNext();) {
			Map.Entry e2 = (Entry) i.next();
			String propName = (String) e2.getKey();
			String colName = FunctionHelper.getColForProp(
					getPropertyToColMap(), propName);
			if(colName != null) {
				Object val = e2.getValue();
				if (val != null && val instanceof Calendar) {
					val = new java.sql.Timestamp(((Calendar) val)
							.getTimeInMillis());
				}
				stmt.setObject(psIndex, val);
				psIndex++;
			}
		}

		if (LOGGER != null) {
            LOGGER.log(Level.DEBUG, "%s %s", conn, prepStmtSql);
        }

		return stmt;
	}


}
