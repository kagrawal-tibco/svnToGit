package com.tibco.cep.modules.db.functions;

import java.sql.Connection;
import java.util.HashMap;

import com.tibco.cep.modules.db.functions.cursor.DatabaseCursor;
import com.tibco.cep.modules.db.functions.cursor.DatabaseCursorImpl;
import com.tibco.cep.modules.db.functions.cursor.ResultSetDatabaseCursor;

public class DatabaseCursorManager {
	
	private static HashMap s_cursorMap = new HashMap();
	
	/**
	 * Opens a cursor for the <sql> query with the given name.
	 * The  cursorname is unique for each cursor. A user cannot work on the same cursor
	 * concurrently.
	 * @param jdbcResourceURI
	 * @param cursorName
	 * @param resultConceptURI
	 * @param sql
	 * @param pageSize
	 * @param argsObj
	 * @return
	 * @throws Exception
	 */
	public static String openCursor(String jdbcResourceURI, String cursorName, String resultConceptURI, String sql, int pageSize, Object argsObj) throws Exception {
		if (s_cursorMap.get(cursorName) != null) {
			throw new Exception("The cursor with the given name is already open.");
		}
		DatabaseCursorImpl dc = new ResultSetDatabaseCursor(cursorName,resultConceptURI,jdbcResourceURI,sql,pageSize,argsObj);
		s_cursorMap.put(dc.getCursorName(), dc);
		return dc.getCursorName();
	}

    /**
     * Gets the next <pagesize> number of rows from the resultset in the given direction
     */ 
	public static Object [] getNextRows(String cursorName, boolean forward) throws Exception {
		DatabaseCursor dc = (DatabaseCursor)s_cursorMap.get(cursorName);
		if (dc == null) {
			throw new Exception("The cursor with the given name is closed, re-open the cursor " +
					"and retry the operation.");
		}
		return dc.getNextRows(forward);
	}
	
    /**
     * Gets the next <pagesize> number of rows from the resultset in the given direction
     */ 
	public static Object [] getNextRows(String cursorName, boolean forward,int pageSize) throws Exception {
		DatabaseCursor dc = (DatabaseCursor)s_cursorMap.get(cursorName);
		if (dc == null) {
			throw new Exception("The cursor with the given name is closed, re-open the cursor " +
					"and retry the operation.");
		}
		return dc.getNextRows(forward,pageSize);
	}	
	
	/**
     * Gets the next <pagesize> number of rows from the resultset in the given direction,
     * starting from <startOffset>
	 */
	public static Object [] getNextRowsFromOffset(String cursorName, boolean forward,int startOffset, int pageSize) throws Exception {
		DatabaseCursor dc = (DatabaseCursor)s_cursorMap.get(cursorName);
		if (dc == null) {
			throw new Exception("The cursor with the given name is closed, re-open the cursor " +
					"and retry the operation.");
		}
		return dc.getNextRowsFromOffset(forward,startOffset,pageSize);
	}
	
	/**
     * Gets the next <pagesize> number of rows from the resultset in the given direction,
     * starting from <startOffset>
	 */
	public static Object [] getNextRowsFromOffset(String cursorName, boolean forward,int startOffset) throws Exception {
		DatabaseCursor dc = (DatabaseCursor)s_cursorMap.get(cursorName);
		if (dc == null) {
			throw new Exception("The cursor with the given name is closed, re-open the cursor " +
					"and retry the operation.");
		}
		return dc.getNextRowsFromOffset(forward,startOffset);
	}
	
	/**
	 * closes the cursor with the given name
	 * @param cursorName
	 * @throws Exception
	 */
	public static void closeCursor(String cursorName) throws Exception {
		DatabaseCursor dc = (DatabaseCursor)s_cursorMap.remove(cursorName);
		if (dc != null) {
			dc.releaseAllResources();
		}
	}
	
    /**
     * Unsets the connection
     * @param resourceName
     * @param conn
     */
	public static void unsetConnectionForQuery(String resourceName, Connection conn){
    	JDBCHelper.unsetConnection(resourceName, conn);
    }
	
	/**
	 * Gets current connection
	 * @param jdbcResourceURI
	 * @return
	 */
    public static Connection getCurrentConnection(String jdbcResourceURI) throws Exception {
    	return JDBCHelper.getCurrentConnection(jdbcResourceURI);
    }
}
