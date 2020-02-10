package com.tibco.be.oracle.functions;

import static com.tibco.be.model.functions.FunctionDomain.*;

import com.tibco.be.model.functions.Enabled;
import com.tibco.be.oracle.impl.OracleConnectionCache;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.security.AXSecurityException;
import com.tibco.security.ObfuscationEngine;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

import oracle.jdbc.*;
import oracle.jdbc.pool.OracleDataSource;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Oracle",
        category = "Oracle",
        enabled = @Enabled(property="TIBCO.BE.function.catalog.Oracle",value=false),
        synopsis = "Functions to query oracle backing store")
public class OracleCustomFunctions {

    /**
     *
     * @param gv    GlobalVariables
     * @param value String
     * @return String
     */
    protected static String getSubstitutedStringValue(GlobalVariables gv, String value) {
        final CharSequence cs = gv.substituteVariables(value);
        if (null == cs) {
            return "";
        } else {
            return cs.toString();
        }
    }

    /**
     *
     * @param gv  GlobalVariables
     * @param pwd String
     * @return String
     */
    protected static String decryptPwd(GlobalVariables gv, String pwd) {
        try {
            char[] pass = ObfuscationEngine.decrypt(pwd);
            return new String(pass);
        }
        catch(Exception e) {
            try {
                return new String(ObfuscationEngine .decrypt( getSubstitutedStringValue(gv, pwd)));
            } catch (AXSecurityException e1) {
                return getSubstitutedStringValue(gv, pwd);

            }
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "registerConnection",
        synopsis = "Creates an Oracle connection pool and registers with the specified key",
        signature = "void registerConnection(String key, String uri, int poolSize)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "String", desc = "The identifier of the pool to registered with"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "The JDBC URI of the resource to be used for getting connections"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "poolSize", type = "int", desc = "The desired number of connections in the pool")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates an Oracle connection pool and registers with the specified key",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "<br/>registerConnection($1my_pool$1, $1/Connections/JDBC Connection.sharedjdbc$1, 5);<br/><br/>"
    )
    public final static void registerConnection(String key, String uri, int poolSize) {
        try {
            if (OracleConnectionCache.isRegistered(key)) {
                return;
            }
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            if (session != null) {
                XiNode dbNode= session.getRuleServiceProvider().getProject().getSharedArchiveResourceProvider().getResourceAsXiNode(uri);
                GlobalVariables gv = session.getRuleServiceProvider().getGlobalVariables();

                XiNode dbConfig= XiChild.getChild(dbNode, ExpandedName.makeName("config"));
                String oracleUserName=getSubstitutedStringValue(gv,XiChild.getString(dbConfig, ExpandedName.makeName("user")));
                String oraclePassword=decryptPwd(gv,XiChild.getString(dbConfig, ExpandedName.makeName("password")));
                String oracleURI=getSubstitutedStringValue(gv,XiChild.getString(dbConfig, ExpandedName.makeName("location")));

                DriverManager.registerDriver(new OracleDriver());
                OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
                ds.setURL(oracleURI);
                ds.setUser(oracleUserName);
                ds.setPassword(oraclePassword);
                OracleConnectionCache.registerConnection(key, ds, poolSize);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getConnection",
        synopsis = "Returns a connection from the pool of connections registered with the specified key",
        signature = "Object getConnection(String key)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "String", desc = "The identifier of the pool registered with registerConnection() call")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "1.0",
        see = "getConnectionWithTimeout(String key, long timeout)",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a connection from the pool of connections registered with the specified key (see registerConnection())",
        cautions = "This call blocks until a connection is available in the pool",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "<br/>Object conn = getConnection($1my_pool$1);<br/><br/>"
    )
    public final static Object getConnection(String key) {
        return getConnectionWithTimeout(key, -1);
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getConnectionWithTimeout",
        synopsis = "Returns a connection from the pool, if one becomes available before the timeout expires (returns null if time expires without getting a connection)",
        signature = "Object getConnectionWithTimeout(String key, long timeout)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "String", desc = "The identifier of the pool registered with registerConnection() call"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "timeout", type = "long", desc = "Wait for up to timeout milliseconds for a free connection. 0 doesn't wait, -1 waits indefinitely.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns a connection from the pool, if one becomes available before the timeout expires (returns null if time expires without getting a connection)",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "<br/>Object conn = getConnectionWithTimeout($1my_pool$1, 1000);<br/><br/>"
    )
    public final static Object getConnectionWithTimeout(String key, long timeout) {
        try {
            return OracleConnectionCache.getConnection(key, timeout);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException (ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "releaseConnection",
        synopsis = "Releases the connection back to the the pool",
        signature = "void releaseConnection(Object connection)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "connection", type = "Object", desc = "The connection previously acquired using one of the getConnection methods")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Releases the connection back to the the pool",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "<br/>releaseConnection(conn);<br/><br/>"
    )
    public final static void releaseConnection(Object connection) {
        ((OracleConnectionCache.PooledConnection)connection).releaseConnection();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "commit",
        synopsis = "Makes all changes made by this connection since the previous commit/rollback permanent and releases any database locks",
        signature = "void commit(Object connection)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "connection", type = "Object", desc = "The connection previously acquired using one of the getConnection methods")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Makes all changes made by this connection since the previous commit/rollback permanent and releases any database locks",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "<br/>commit(conn);<br/><br/>"
    )
    public final static void commit(Object connection) {
        try {
            ((OracleConnectionCache.PooledConnection)connection).getConnection().commit();
        } catch (Exception ex) {
            checkAndReleaseConnection((OracleConnectionCache.PooledConnection) connection);
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "rollback",
        synopsis = "Undoes all changes made in the current transaction and releases any database locks currently held by this connection",
        signature = "void rollback(Object connection)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "connection", type = "Object", desc = "The connection previously acquired using one of the getConnection methods")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Undoes all changes made in the current transaction and releases any database locks currently held by this connection",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "<br/>rollback(conn);<br/><br/>"
    )
    public final static void rollback(Object connection) {
        try {
            ((OracleConnectionCache.PooledConnection)connection).getConnection().rollback();
        } catch (Exception ex) {
            checkAndReleaseConnection((OracleConnectionCache.PooledConnection) connection);
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    private static void checkAndReleaseConnection(OracleConnectionCache.PooledConnection connection) {
        connection.isConnectionClosed();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "executeQuery",
        synopsis = "Executes the given SQL statement, which may return multiple results through the result-set",
        signature = "Object executeQuery(Object connection, String sql, Object[] args)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "connection", type = "Object", desc = "The database connection acquired by getConnection call"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sql", type = "String", desc = "The SQL statement to be executed"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "args", type = "Object[]", desc = "The arguments to execute with the SQL statement (bind variables)")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Executes the given SQL statement, which may return multiple results through the result-set",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "<br/>executeQuery(conn, $1insert into ...$1, new Object[$1John$1, 123]);<br/><br/>"
    )
    public final static Object executeQuery(Object connection, String sql, Object[] args) {
        OracleConnection sqlConnection = ((OracleConnectionCache.PooledConnection) connection).getConnection();
        try {
            OraclePreparedStatement statement= (OraclePreparedStatement) sqlConnection.prepareStatement(sql);
            for (int i=0; i < args.length; i++) {
                statement.setObject(i+1, args[i]);
            }
            return statement.executeQuery();
        } catch (SQLException e) {
            checkAndReleaseConnection((OracleConnectionCache.PooledConnection) connection);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "next",
        synopsis = "Moves the result-set's cursor down one row from its current position. Returns true if the new current row is valid",
        signature = "boolean next(Object resultSet)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "resultSet", type = "Object", desc = "The result-set (cursor pointing to current row)")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = ""),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Moves the result-set's cursor down one row from its current position. Returns true if the new current row is valid",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "<br/>next(resultSet);<br/><br/>"
    )
    public final static boolean next(Object resultSet) {
        try {
            if (resultSet == null) {
                return false;
            }
            OracleResultSet res= (OracleResultSet) resultSet;
            return res.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "closeResultSet",
        synopsis = "Closes the result-set of the query results, and releases it's database and JDBC resources",
        signature = "void closeResultSet(Object resultSet)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "resultSet", type = "Object", desc = "The result-set (cursor pointing to current row) to be closed")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Closes the result-set of the query results, and releases it's database and JDBC resources",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "<br/>closeResultSet(resultSet);<br/><br/>"
    )
    public final static void closeResultSet(Object resultSet) {
        try {
            OracleResultSet res= (OracleResultSet) resultSet;
            OracleStatement stmt = (OracleStatement) res.getStatement();
            res.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getColumnValueByIndex",
        synopsis = "Gets the value of the designated column in the current row of this result-set",
        signature = "Object getColumnValueByIndex(Object resultSet, int columnIndex)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "resultSet", type = "Object", desc = "The result-set (cursor pointing to current row)"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnIndex", type = "int", desc = "The index of requested column (the first column is 1, the second is 2 so on)")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the value of the designated column in the current row of this result-set",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "<br/>getColumnValueByIndex(resultSet, 1);<br/><br/>"
    )
    public final static Object getColumnValueByIndex(Object resultSet, int columnIndex) {
        try {
            OracleResultSet res= (OracleResultSet) resultSet;
            return res.getObject(columnIndex);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getColumnValueByName",
        synopsis = "Gets the value of the designated column in the current row of this result-set",
        signature = "Object getColumnValueByName(Object resultSet, String columnName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "resultSet", type = "Object", desc = "The result-set (cursor pointing to current row)"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "columnName", type = "String", desc = "The SQL name of requested column")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = ""),
        version = "3.0.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Gets the value of the designated column in the current row of this result-set",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = "<br/>getColumnValueByName(resultSet, $1CustomerName$1);<br/><br/>"
    )
    public final static Object getColumnValueByName(Object resultSet, String columnName) {
        try {
            OracleResultSet res= (OracleResultSet) resultSet;
            return res.getObject(columnName);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param connection  Object
     * @param resultSet   Object
     * @param columnIndex int
     * @return String
     */
    public final static String getString(Object connection, Object resultSet, int columnIndex) {
        try {
            OracleResultSet res= (OracleResultSet) resultSet;
            return res.getString(columnIndex);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param connection  Object
     * @param resultSet   Object
     * @param columnIndex int
     * @return long
     */
    public final static long getLong(Object connection, Object resultSet, int columnIndex) {
        try {
            OracleResultSet res= (OracleResultSet) resultSet;
            return res.getLong(columnIndex);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param connection  Object
     * @param resultSet   Object
     * @param columnIndex int
     * @return long
     */
    public final static long getInt(Object connection, Object resultSet, int columnIndex) {
        try {
            OracleResultSet res= (OracleResultSet) resultSet;
            return res.getInt(columnIndex);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param connection  Object
     * @param resultSet   Object
     * @param columnIndex int
     * @return double
     */
    public double getDouble(Object connection, Object resultSet, int columnIndex) {
        try {
            OracleResultSet res= (OracleResultSet) resultSet;
            return res.getDouble(columnIndex);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param connection  Object
     * @param resultSet   Object
     * @param columnIndex int
     * @return Date
     */
    public final static Date getDateTime(Object connection, Object resultSet, int columnIndex) {
        try {
            OracleResultSet res= (OracleResultSet) resultSet;
            return res.getDate(columnIndex);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
