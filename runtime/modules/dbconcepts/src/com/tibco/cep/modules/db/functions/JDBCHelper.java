package com.tibco.cep.modules.db.functions;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.be.model.functions.Enabled;

import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.modules.db.functions.FunctionHelper.PreparedStmtDbg;
import com.tibco.cep.modules.db.functions.PreparedStatementParam.ParamDataType;
import com.tibco.cep.modules.db.model.runtime.AbstractDBConceptImpl;
import com.tibco.cep.modules.db.model.runtime.DBConcept;
import com.tibco.cep.modules.db.service.DBConnectionFactory;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArrayConcept;
import com.tibco.cep.runtime.model.element.PropertyAtomConcept;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.util.coll.ConcurrentHashMap;

import java.sql.*;
import java.util.*;
import java.util.Map.Entry;


@com.tibco.be.model.functions.BEPackage(
		catalog = "RDBMS",
        category = "Database",
        synopsis = "Database access functions")
public class JDBCHelper {

	static com.tibco.cep.kernel.service.logging.Logger logger;

	static {
		try {
			if (RuleServiceProviderManager.getInstance() != null &&
				RuleServiceProviderManager.getInstance().getDefaultProvider() != null	) {
				logger = RuleServiceProviderManager.getInstance().getDefaultProvider().getLogger(JDBCHelper.class);
			}
		} catch (Exception e) {
		}
	}

	protected static final ThreadLocal currentConnections = new ThreadLocal(){
    	protected synchronized Object initialValue(){
    		return new ConcurrentHashMap();
    	}
    };
    @com.tibco.be.model.functions.BEFunction(
        name = "assertDBInstance",
        synopsis = "Asserts a DB Concept instance into working memory. \nIf deep is set to true then asserts all contained/referenced DB concept instances",
        signature = "void assertDBInstance(Concept instance, boolean deep)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "The instance of the Concept to be asserted to working memory."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "deep", type = "boolean", desc = "Set to true if concept properties are to be asserted recursively.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Asserts a DB Concept instance into working memory. \nIf deep is set to true then asserts all contained/referenced DB concept instances",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void assertDBInstance(Concept instance, boolean deep) {
        if (instance == null) {
        	return;
        }

    	if (instance instanceof AbstractDBConceptImpl) {
			AbstractDBConceptImpl c = (AbstractDBConceptImpl) instance;
			RuleSession session = RuleSessionManager.getCurrentRuleSession();
			try {
				session.assertObject(c, true);
			} catch (DuplicateExtIdException e) {
				logger.log(Level.WARN, e, "Failed to assert DBConcept to WM: %s", c.getExtId());
			}
		}
    	if (!deep) {
    		return;
    	}

    	Property [] props = ((ConceptImpl)instance).getPropertiesNullOK();
    	for (int i = 0; i<props.length; i++) {
    		Property prop = props[i];
    		if (prop == null) {
    			continue;
    		}
    		if (prop instanceof PropertyAtomConcept) {
    			Concept c = ((PropertyAtomConcept)prop).getConcept();
    			if (c != null) {
    				assertDBInstance(c, deep);
    			}
    		} else if (prop instanceof PropertyArrayConcept) {
    			PropertyArrayConcept propArr = ((PropertyArrayConcept)prop);
    			for (int j=0; j<propArr.length(); j++) {
    				PropertyAtomConcept propAtomCept = (PropertyAtomConcept) propArr.get(j);
    				if (propAtomCept != null) {
    					Concept c = propAtomCept.getConcept();
    					if (c != null) {
    						assertDBInstance(c, deep);
    					}
    				}
    			}
    		}
    	}
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "setCurrentConnection",
        synopsis = "Sets the connection to use for database operations.",
        signature = "void setCurrentConnection(String jdbcResourceURI)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "jdbcResourceURI", type = "String", desc = "The JDBC resource URI which specifies the database connection to use.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Set the connection to use for the database operation.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void setCurrentConnection(String jdbcResourceURI) {
    	try {
            setCurrentConn(jdbcResourceURI);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "commit",
        synopsis = "Commits the current transaction.",
        signature = "void commit()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Commit the transaction that is currently in process. It uses the connection\nin the current thread's context.",
        cautions = "The setCurrentConnection must be invoked",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void commit() {
    	Map connections = (Map) currentConnections.get();
    	Iterator iter = connections.values().iterator();
		while (iter.hasNext()) {
			Connection conn = (Connection) iter.next();
			try {
                if (!conn.getAutoCommit()) {
                	conn.commit();
                	conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
		}
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "beginTransaction",
        synopsis = "Begins a transaction on the current connection.",
        signature = "void beginTransaction()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Begins a transaction on the current connection.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void beginTransaction() {
		Map connections = (Map) currentConnections.get();
		if (connections.size() <= 0) {
			throw new RuntimeException(
					"Connection not set to begin transaction");
		}
		Iterator iter = connections.values().iterator();
		while (iter.hasNext()) {
			Connection conn = (Connection) iter.next();
			try {
				conn.setAutoCommit(false);
			} catch (SQLException e) {
			}
		}
	}

    @com.tibco.be.model.functions.BEFunction(
        name = "rollback",
        synopsis = "Rolls back current transaction.",
        signature = "void rollback()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Rollback the transaction that is currently in process. \nItuses the connection in the current thread's context. \nIn case of failure, it will throw associated exception.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void rollback() {
		Map connections = (Map) currentConnections.get();
		Iterator iter = connections.values().iterator();
		while (iter.hasNext()) {
			Connection conn = (Connection) iter.next();
			try {
                if (!conn.getAutoCommit()) {
                	conn.rollback();
                	conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
		}
	}

    @com.tibco.be.model.functions.BEFunction(
        name = "unsetConnection",
        synopsis = "Releases the current connection back to the connection pool. \nTo be called each time after performing database operations.",
        signature = "void unsetConnection()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "unset the connection, in the current thread's context and release back to the pool.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void unsetConnection() {
    	Map connections = (Map) currentConnections.get();
    	Iterator iter = connections.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry e = (Entry) iter.next();
			Connection conn = (Connection) e.getValue();
			String resourceName = (String) e.getKey();
	    	releaseConnection(resourceName, conn);
			iter.remove();
		}
    }

    /*
	 * Releases the current connection back to the connection pool in the current thread's context.
	 * To be called each time after performing database operations.
	 * It takes the JDBC resource URI which specifies the database connection to release
	 * and Connection to release.
	 */
    static void unsetConnection(String resourceName, Connection conn) {
    	Map connections = (Map) currentConnections.get();
    	connections.remove(resourceName);
    	releaseConnection(resourceName, conn);
    }

    /*
     * Reset the auto-commit mode and release the connection back in pool
     *
     */
    private static void releaseConnection(String resourceName, Connection conn) {
		// if connection is not valid while setAutoCommit call, exception is throw
		// that results in a connection leak, thats why this call is put in try/catch
    	try {
			conn.setAutoCommit(true);
    	} catch (SQLException ex) {
    	} finally {
    		DBConnectionFactory.getInstance().getConnectionPoolManager().relaseConnection(resourceName, conn);
    	}
    }

	@com.tibco.be.model.functions.BEFunction(
        name = "insert",
        synopsis = "Inserts a record corresponding to the passed DB concept, into the database. If the passed instance contains concept references,\nthen all such instances are inserted recursively. Primary keys are either generated using sequences (based on sequences.xml in the\nproject) or have to be specified in the instance.",
        signature = "Concept insert(Concept instance)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "The instance of the DBConcept to insert.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = "The inserted concept instance"),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Inserts the instance of the Concept passed into the associated database table.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static Concept insert(Concept instance) {
        return InsertHelper.insert(instance);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "update",
        synopsis = "Updates the database using values in the passed concept",
        signature = "int update(Concept instance)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "The concept to update database with.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "Number of records updated"),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Updates the instance of the Concept passed into the associated database table.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static int update(Concept instance) {
        return UpdateHelper.update(instance);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "execUpdate",
        synopsis = "Updates the database using values in the passed instance concept",
        signature = "int execUpdate(DBConcept model, DBConcept instance)",
        enabled = @Enabled(property="TIBCO.CEP.modules.function.catalog.database.execupdate", value=false),
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "model", type = "DBConcept", desc = "The concept used to map to a database record."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "DBConcept", desc = "The concept used to update database with.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "Number of records updated"),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Updates the database table row associated to the DBCOncrpt model using the values from DBConcept instance.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static int execUpdate(Concept model, Concept instance) {
    	if (model == null
			|| instance == null
			|| !(model instanceof DBConcept)
			|| !(instance instanceof DBConcept)
			|| !(model.getExpandedName().getNamespaceURI().equals(instance.getExpandedName().getNamespaceURI()))){
    		throw new IllegalArgumentException("parameter must be not null, instance of one db concept type");
    	}
        return UpdateHelper.execUpdate((DBConcept)model, (DBConcept)instance);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "delete",
        synopsis = "Deletes rows from the database based on primary key values of the passed instance. If cascade is set to true,\ndeletes all rows corresponding to its contained concept and concept reference properties.  Further, for concept reference properties,\nupdates other tables by setting their foreign key references to null for those rows that match primary keys of the rows being deleted",
        signature = "int delete(Concept instance, boolean cascade)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "instance", type = "Concept", desc = "The concept to delete from the database."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cascade", type = "boolean", desc = "Set to true if delete should cascade to concept properties.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "Total number of records deleted and updated."),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Deletes the instance of the Concept passed from the associated database table.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static int delete(Concept instance, boolean cascade) {
        return DeleteHelper.delete(instance, cascade);
    }

	@com.tibco.be.model.functions.BEFunction(
        name = "executePreparedStmt",
        synopsis = "Executes a PreparedStatement. A prepared statement is a SQL statement where values are determined at runtime. The values to be used\nare passed as an array of objects.",
        signature = "int executePreparedStmt(String preparedStmt, Object[] args)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "preparedStmt", type = "String", desc = "The SQL prepared statement to execute"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "args", type = "Object[]", desc = "Positional values to be used for binding to the prepared statement")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "Total number of records affected by the prepared statement"),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Executes a prepared statement using the connection that is set",
        cautions = "Try to use executePreparedStmtByParamList().",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
	public static int executePreparedStmt(String preparedStmt, Object[] args) {
		PreparedStatement stmt = null;
		try {
			Connection conn = JDBCHelper.getCurrentConnection();

			try {
                stmt = conn.prepareStatement(preparedStmt);
                
                for (int i = 0; i < args.length; i++) {
                	if (args[i] instanceof Calendar) {
                		java.sql.Timestamp ts = new Timestamp(((Calendar) args[i])
                				.getTimeInMillis());
                		args[i] = ts;
                	}
                	stmt.setObject(i + 1, args[i]);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

			Object retVal = FunctionHelper.executeSQL(null, stmt, null);
			if (retVal instanceof Integer) {
				return ((Integer) retVal).intValue();
			}
			return 0;
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
				}
			}
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "executePreparedStmtByParamList",
		synopsis = "Executes a PreparedStatement. A prepared statement is a SQL statement where values are determined at runtime.<br/>The values to be used are passed as a List of parameters.", 
		signature = "int executePreparedStmtByParamList(String preparedStmt, List parameterList)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "preparedStmt", type = "String", desc = "The SQL prepared statement to execute"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "parameterList", type = "List", desc = "Positional values to be used for binding to the prepared statement. An ArrayList that has been populated using preparedstatement utility methods only (addIntPreparedStmtParam, addStringPreparedStmtParam etc).")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "Total number of records affected by the prepared statement"),
		version = "5.2",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Executes a PreparedStatement. A prepared statement is a SQL statement where values are determined at runtime.<br/>The values to be used are passed as a List of parameters.",
		cautions = "none",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static int executePreparedStmtByParamList(String preparedStmt, Object params) {
		PreparedStatement stmt = null;
		try {
			Connection conn = JDBCHelper.getCurrentConnection();
			stmt = conn.prepareStatement(preparedStmt);
			
			@SuppressWarnings("unchecked")
			List<PreparedStatementParam> paramList = (List<PreparedStatementParam>)params;
			for (int i = 0; i < paramList.size(); i++) {
				PreparedStatementParam param = paramList.get(i);
				if (param.getData() instanceof Calendar) {
					java.sql.Timestamp ts = new Timestamp(((Calendar) param.getData()).getTimeInMillis());
					param.setData(ts);
				}
				setPreparedStatementData(stmt, i + 1, param.getDataType(), param.getData());
			}
			Object retVal = FunctionHelper.executeSQL(null, stmt, null);
			if (retVal instanceof Integer) {
				return ((Integer) retVal).intValue();
			}
			return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
				}
			}
		}
	}
	
	/**
	 * Sets the specified <code>value</code> to the preparedStatement
	 * <code>stmt</code> at the specified <code>parameterIndex</code>
	 * 
	 * @param stmt
	 * @param parameterIndex
	 * @param dataType
	 * @param value
	 * @throws SQLException
	 */
	private static void setPreparedStatementData(PreparedStatement stmt, int parameterIndex, ParamDataType dataType, Object value) throws SQLException {
		switch (dataType) {
		case INT:
			if (value == null) {
				stmt.setNull(parameterIndex, java.sql.Types.INTEGER);
			}
			else {
				stmt.setInt(parameterIndex, (Integer)value);
			}
			break;
		case STRING:
			if (value == null) {
				stmt.setNull(parameterIndex, java.sql.Types.VARCHAR);
			}
			else {
				stmt.setString(parameterIndex, value.toString());
			}
			break;
		case DOUBLE:
			if (value == null) {
				stmt.setNull(parameterIndex, java.sql.Types.DOUBLE);
			}
			else {
				stmt.setDouble(parameterIndex, (Double)value);
			}
			break;
		case OBJECT:
			stmt.setObject(parameterIndex, value);
			break;
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "executeSQL",
        synopsis = "Executes an SQL statement. To be used for inserts/updates and deletes.",
        signature = "int executeSQL(String sql)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sql", type = "String", desc = "SQL statement to execute")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "Total number of records affected by the SQL statement."),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Executes an SQL statement using the connection that is set",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
	public static int executeSQL(String sql) {
		Statement stmt = null;
		try {
			Connection conn = JDBCHelper.getCurrentConnection();
			stmt = conn.createStatement();

			Object ret = FunctionHelper.executeSQL(null, stmt, sql);
			if (ret instanceof Integer) {
				return ((Integer)ret).intValue();
			} else {
				return 0;
			}
        } catch (SQLException e) {
            throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
				}
			}
		}
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "queryUsingPrimaryKeys",
        synopsis = "Queries the database using primary key values provided in an Event.",
        signature = "Concept[] queryUsingPrimaryKeys(String conceptURI, SimpleEvent pKeyEvent, boolean queryChildren)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "conceptURI", type = "String", desc = "The result concept type's URI"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pKeyEvent", type = "SimpleEvent", desc = "Event that contains primary key values to be used. Properties must match primary key properties in result concept."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "queryChildren", type = "boolean", desc = "If set to true, concept properties are recursively queried.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept[]", desc = "Array of result concepts"),
        version = "2.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Queries the database using primary key values provided in an Event.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
	public static Concept[] queryUsingPrimaryKeys(String conceptURI,
			SimpleEvent pKeyEvent, boolean queryChildren) {
		PreparedStmtDbg prepStmtDbg = null;
		try {
			Concept c = (Concept) RuleServiceProviderManager.getInstance().getDefaultProvider().getTypeManager().createEntity(conceptURI);
			if (! (c instanceof DBConcept)) {
				throw new Exception ("Specified concept is not a database concept: " + conceptURI);
			}
			DBConcept dbconcept = (DBConcept) c;
			prepStmtDbg = QueryHelper.getPrepStmt(dbconcept, pKeyEvent);
            logger.log(Level.DEBUG, "SQL: %s", prepStmtDbg.debugStr);
			List concepts = QueryHelper.executeQuery(dbconcept, prepStmtDbg.stmt, null);
			Concept[] cepts = QueryHelper.queryChildCepts(c, concepts, queryChildren);
			return cepts;
		} catch (Exception e) {
            throw new RuntimeException(e);
		} finally {
			if (prepStmtDbg != null && prepStmtDbg.stmt != null) {
				try {
					prepStmtDbg.stmt.close();
				} catch (Exception e) {
				}
			}
		}
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "queryUsingSQL",
        synopsis = "Queries underlying database using the supplied SQL.",
        signature = "Concept[] queryUsingSQL(String conceptURI, String sql, boolean queryChildren)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "conceptURI", type = "String", desc = "The result concept type's URI"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sql", type = "String", desc = "SQL statement to execute"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "queryChildren", type = "boolean", desc = "If set to true, concept properties are recursively queried.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept[]", desc = "Result array concepts"),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Queries the database and returns concepts",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
	public static Concept[] queryUsingSQL(String conceptURI, String sql,
			boolean queryChildren) {
		Statement stmt = null;
		try {
			Concept cept = FunctionHelper.createConcept(conceptURI);
			if (!(cept instanceof DBConcept)) {
				throw new Exception("Specified concept is not a database concept: "	+ conceptURI);
			}
			DBConcept dbCept = (DBConcept) cept;
			Connection conn = JDBCHelper.getCurrentConnection(((AbstractDBConceptImpl)dbCept).getJDBCResourceName());
			stmt = conn.createStatement();

			List concepts = QueryHelper.executeQuery(dbCept, stmt, sql);
			stmt.close();

			Concept[] cepts = QueryHelper.queryChildCepts(dbCept, concepts, queryChildren);
			return cepts;
		} catch (Exception e) {
		    throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
				}
			}
		}
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "queryUsingConceptProps",
        synopsis = "Queries database using the values in a concept instance.",
        signature = "Concept[] queryUsingConceptProps(Concept qConcept, boolean queryChildren)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "qConcept", type = "Concept", desc = "Database is queried for matching values from this concept."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "queryChildren", type = "boolean", desc = "If set to true, concept properties are recursively queried.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept[]", desc = ""),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Queries database using the values in a concept instance.",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
	public static Concept[] queryUsingConceptProps(Concept qConcept, boolean queryChildren) {
		if (! (qConcept instanceof DBConcept)) {
			throw new RuntimeException ("Specified concept is not a database concept: " +
					((ConceptImpl)qConcept).getType().substring(TypeManager.DEFAULT_BE_NAMESPACE_URI.length()));
		}
		DBConcept dbConcept = (DBConcept) qConcept;
		LinkedHashMap colSet = QueryHelper.getNotNullCols(dbConcept);
		try {
            List concepts = QueryHelper.executeInitialQuery(dbConcept, colSet);
            Concept[] cepts = QueryHelper.queryChildCepts(qConcept, concepts, queryChildren);
            return cepts;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "queryUsingPreparedStmt",
        synopsis = "Queries the database using the supplied prepared statement.",
        signature = "Concept[] queryUsingPreparedStmt(String conceptURI, String preparedStmt, Object[] args, boolean queryChildren)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "conceptURI", type = "String", desc = "The result concept type's URI"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "preparedStmt", type = "String", desc = "Prepared statement to execute"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "args", type = "Object[]", desc = "Positional values to be used for binding to the prepared statement."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "queryChildren", type = "boolean", desc = "If set to true, concept properties are recursively queried.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = ""),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Queries the database using the supplied prepared statement.",
        cautions = "Try to use queryUsingPreparedStmtByParamList().",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
	public static Concept[] queryUsingPreparedStmt(String conceptURI, String preparedStmt, Object[] args,
			boolean queryChildren) {
		PreparedStatement stmt = null;
		try {
			Concept cept = FunctionHelper.createConcept(conceptURI);
			if (!(cept instanceof DBConcept)) {
				throw new Exception("Specified concept is not a database concept: " + conceptURI);
			}
			DBConcept dbCept = (DBConcept) cept;
			Connection conn = JDBCHelper.getCurrentConnection(((AbstractDBConceptImpl)dbCept).getJDBCResourceName());

			stmt = conn.prepareStatement(preparedStmt);

			for (int i=0; i<args.length; i++) {
				if (args[i] instanceof Calendar) {
					java.sql.Timestamp ts = new Timestamp(((Calendar)args[i]).getTimeInMillis());
					args[i] = ts;
				}
				stmt.setObject(i+1, args[i]);
			}

			List concepts = QueryHelper.executeQuery(dbCept, stmt, null);
			Concept[] cepts = QueryHelper.queryChildCepts(dbCept, concepts, queryChildren);
			return cepts;
        } catch (Exception e) {
            throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
				}
			}
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "queryUsingPreparedStmtByParamList",
		synopsis = "Queries the database using the supplied prepared statement.",
		signature = "Concept[] queryUsingPreparedStmtByParamList(String conceptURI, String preparedStmt, List parameterList, boolean queryChildren)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "conceptURI", type = "String", desc = "The result concept type's URI"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "preparedStmt", type = "String", desc = "Prepared statement to execute"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "parameterList", type = "List", desc = "Positional values to be used for binding to the prepared statement. An ArrayList that has been populated using preparedstatement utility methods only (addIntPreparedStmtParam, addStringPreparedStmtParam etc)."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "queryChildren", type = "boolean", desc = "If set to true, concept properties are recursively queried.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Concept", desc = ""),
		version = "5.2",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Queries the database using the supplied prepared statement.",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static Concept[] queryUsingPreparedStmtByParamList(String conceptURI, String preparedStmt, Object params,
			boolean queryChildren) {
		PreparedStatement stmt = null;
		try {
			Concept cept = FunctionHelper.createConcept(conceptURI);
			if (!(cept instanceof DBConcept)) {
				throw new Exception("Specified concept is not a database concept: " + conceptURI);
			}
			DBConcept dbCept = (DBConcept) cept;
			Connection conn = JDBCHelper.getCurrentConnection(((AbstractDBConceptImpl)dbCept).getJDBCResourceName());
			stmt = conn.prepareStatement(preparedStmt);
			
			@SuppressWarnings("unchecked")
			List<PreparedStatementParam> paramList = (List<PreparedStatementParam>)params;
			for (int i=0; i<paramList.size(); i++) {
				PreparedStatementParam param = paramList.get(i);
				if (param.getData() instanceof Calendar) {
					java.sql.Timestamp ts = new Timestamp(((Calendar)param.getData()).getTimeInMillis());
					param.setData(ts);
				}
				setPreparedStatementData(stmt, i + 1, param.getDataType(), param.getData());
			}
			
			List concepts = QueryHelper.executeQuery(dbCept, stmt, null);
			Concept[] cepts = QueryHelper.queryChildCepts(dbCept, concepts, queryChildren);
			return cepts;
        } catch (Exception e) {
            throw new RuntimeException(e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception e) {
				}
			}
		}
	}

    public static Connection getCurrentConnection() {
    	int mapSize = ((Map) currentConnections.get()).size();
    	if (mapSize > 1) {
    		throw new RuntimeException("Multiple connection set. Use getCurrentConnection(String resourceName)");
    	} else if(mapSize <= 0) {
    		throw new RuntimeException("Connection is not available to execute this operation. Set connection using Database.setCurrentConnection(String).");
    	}
    	Connection conn = (Connection)((Map) currentConnections.get()).values().toArray()[0];
        return conn;
    }

	/**
	 * @.name getCurrentConnectionStatus
	 * @.synopsis Returns status of the connection pool for the given URI.
	 * @.signature int getCurrentConnectionStatus(String jdbcResourceURI)
	 * @param jdbcResourceURI String The jdbc URI for which status is required
	 * @return int 0 or 1, 0 means that the connection pool is disconnected, 1 means it is connected.
	 * @.version 3.0
	 * @.see
	 * @.mapper false
	 * @.description This method can be used to query the health of the underlying connection pool and action can be
	 * taken accordingly.
	 * @.cautions none
	 * @.domain action
	 * @.example
	 */
    /*
    synchronized public static int getCurrentConnectionStatus() {
    	int mapSize =  ((Map) currentConnections.get()).size();
    	if (mapSize > 1){
    		throw new RuntimeException("Multiple connection set. Use getConnectionStatus(String resourceName)");
    	}
    	String jdbcResourceURI = (String) ((Map)currentConnections).keySet().toArray()[0];
    	return DBConnectionFactory.getInstance().getConnectionPoolManager().getConnectionStatus(jdbcResourceURI);
    }
    */

	@com.tibco.be.model.functions.BEFunction(
        name = "getConnectionStatus",
        synopsis = "Returns the status of connections for the given JDBC URI\nIf underlying connections are good, returns 1, else returns 0",
        signature = "int getConnectionStatus(String jdbcResourceURI)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "jdbcResourceURI", type = "String", desc = "The JDBC URI for which connection status is required")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "0 or 1, 0 indicates that the connections are bad. 1 indicates good connections."),
        version = "3.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "This method can be used to query the health of the underlying connection pool and action can be\ntaken accordingly.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static int getConnectionStatus(String jdbcResourceURI) {
    	if (!jdbcResourceURI.endsWith(".sharedjdbc")) {
    		jdbcResourceURI += ".sharedjdbc";
    	}
     	return DBConnectionFactory.getInstance().getConnectionPoolManager().getConnectionStatus(jdbcResourceURI);
    }

	public static Connection getCurrentConnection(String jdbcResourceURI) {
    	if (!jdbcResourceURI.endsWith(".sharedjdbc")) {
    		jdbcResourceURI += ".sharedjdbc";
    	}
        Connection conn = (Connection)((Map) currentConnections.get()).get(jdbcResourceURI);
        if (conn == null) {
        	try {
                return setCurrentConn(jdbcResourceURI);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return conn;
    }

    private static Connection setCurrentConn(String resourceName) throws SQLException {
		Connection conn = null;
    	if (!resourceName.endsWith(".sharedjdbc")) {
    		resourceName += ".sharedjdbc";
    	}
        conn = DBConnectionFactory.getInstance().getConnectionPoolManager().getJDBCConnection(resourceName);
        if (conn == null) {
        	throw new RuntimeException(
					"Connections are unavailable, increase the max pool size or wait timeout "
							+ "Database may be disconnected. Resource Name - \""
							+ resourceName + "\"");
        }
        //currentConnections.set(conn);
        ((Map)currentConnections.get()).put(resourceName, conn);
        if (!conn.getAutoCommit()) {
        	conn.setAutoCommit(true);
        }
        return conn;
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "createQuery",
        synopsis = "Opens a database cursor for the given sql query. Once the cursor is open, a user can page on the resultset of the query",
        signature = "String createQuery(String jdbcURI, String cursorName, String resultConceptURI, String sql, int pageSize, Object requestObj)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "jdbcURI", type = "String", desc = "The JDBC URI of the resource to be used for getting connection."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cursorName", type = "String", desc = "The name of the cursor to be opened"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "resultTypeURI", type = "String", desc = "a user can retrieve the column values by specifying column names as key"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sql", type = "String", desc = "The sql query string, it can be a prepared statement query or a simple statement query."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pageSize", type = "int", desc = "The number of concepts/records to be fetched from the database for each page"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "requestObj", type = "Object", desc = "array of arguments")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The cursor name of the cursor opened"),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Opens a database cursor for the given sql query.",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
	public static String createQuery(String jdbcURI, String cursorName, String resultTypeURI, String sql, int pageSize, Object requestObj) {
	    try {
            return DatabaseCursorManager.openCursor(jdbcURI, cursorName, resultTypeURI, sql, pageSize, requestObj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "getNextPage",
        synopsis = "Gets the next page from the database cursor",
        signature = "Object[] getNextPage(String cursorName, int pageSize)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cursorName", type = "String", desc = "The name of the database cursor"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pageSize", type = "int", desc = "Default value is 500.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "The array of resultset data returned.\nThe array is of resultConceptType if resultConceptURI is specified else \nit is in the form of n-tuple object array where each tuple is an array of the \nvalues of resultset data returned."),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the next page from the database cursor",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
	public static Object[] getNextPage(String cursorName, int pageSize) {
		try {
			if (pageSize == -1)
				return DatabaseCursorManager.getNextRows(cursorName,true);
			else
				return DatabaseCursorManager.getNextRows(cursorName,true,pageSize);
		} catch (Exception e) {
			closeQuery(cursorName); // close the cursor if there is an error
            throw new RuntimeException(e);
		}
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "getPreviousPage",
        synopsis = "Gets the previous page from the database cursor. The fetch direction\nis always forward. So if the cursor is on 11th record calling this method will return\n1 to 10 records in th forward direction and not 10 to 1.",
        signature = "Object[] getPreviousPage(String cursorName, int pageSize)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cursorName", type = "String", desc = "The name of the database cursor"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pageSize", type = "int", desc = "a default value is 500.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "The array of resultset data returned.\nThe array is of resultConceptType if resultConceptURI is specified else \nit is in the form of n-tuple object array where each tuple is an array of the \nvalues of resultset data returned."),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the previous page from the database cursor",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
	public static Object[] getPreviousPage(String cursorName, int pageSize) {
		try {
			if (pageSize == -1)
				return DatabaseCursorManager.getNextRows(cursorName,false);
			else
				return DatabaseCursorManager.getNextRows(cursorName,false,pageSize);
		} catch (Exception e) {
			closeQuery(cursorName); // close the cursor if there is an error
            throw new RuntimeException(e);
		}
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "getNextPageFromOffset",
        synopsis = "Gets the next page from the database cursor, starting from the given offset",
        signature = "getNextPageFromOffset(String cursorName, int startOffset, int pageSize)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cursorName", type = "String", desc = "The name of the database cursor"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "startOffset", type = "the", desc = "start offset for the page"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pageSize", type = "int", desc = "a default value is 500.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "The array of resultset data returned.\nThe array is of resultConceptType if resultConceptURI is specified else \nit is in the form of n-tuple object array where each tuple is an array of the \nvalues of resultset data returned."),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the next page from the database cursor, starting from the given offset",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
	public static Object[] getNextPageFromOffset(String cursorName, int startOffset, int pageSize) {
		try {
			if (pageSize == -1)
				return DatabaseCursorManager.getNextRowsFromOffset(cursorName,true,startOffset);
			else
				return DatabaseCursorManager.getNextRowsFromOffset(cursorName,true,startOffset,pageSize);
		} catch (Exception e) {
			closeQuery(cursorName); // close the cursor if there is an error
            throw new RuntimeException(e);
		}
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "getPreviousPageFromOffset",
        synopsis = "Gets the previous page from the database cursor, starting from the given offset",
        signature = "getPreviousPageFromOffset(String cursorName, int startOffset, int pageSize)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cursorName", type = "String", desc = "The name of the database cursor"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "startOffset", type = "the", desc = "start offset for the page"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "pageSize", type = "int", desc = "a default value of 500 is used.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "The array of resultset data returned.\nThe array is of resultConceptType if resultConceptURI is specified else \nit is in the form of n-tuple object array where each tuple is an array of the \nvalues of resultset data returned."),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the previous page from the database cursor, starting from the given offset",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
	public static Object[] getPreviousPageFromOffset(String cursorName, int startOffset, int pageSize) {
		try {
			if (pageSize == -1)
				return DatabaseCursorManager.getNextRowsFromOffset(cursorName,false,startOffset);
			else
				return DatabaseCursorManager.getNextRowsFromOffset(cursorName,false,startOffset,pageSize);
		} catch (Exception e) {
			closeQuery(cursorName); // close the cursor if there is an error
            throw new RuntimeException(e);
		}
	}

	@com.tibco.be.model.functions.BEFunction(
        name = "closeQuery",
        synopsis = "Closes the cursor for the query",
        signature = "void closeQuery(String cursorName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cursorName", type = "String", desc = "The name of the database cursor to be closed")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Closes the cursor for the query",
        cautions = "none",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
	public static void closeQuery(String cursorName) {
	    try {
            DatabaseCursorManager.closeCursor(cursorName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
}
