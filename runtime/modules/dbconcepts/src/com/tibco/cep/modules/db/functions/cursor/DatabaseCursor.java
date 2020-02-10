package com.tibco.cep.modules.db.functions.cursor;

/**
 * A database cursor interface on a sql query resultset
 * @author vjavere
 *
 */
public interface DatabaseCursor {
	
	public static final String DBCONCEPTS_CURSOR_CLOSE_QUERY_CLOSE_CONNECTION_PROPERTY = "be.dbconcepts.cursor.closeQuery.closeConnection";

	public Object [] getNextRows(boolean forward) throws Exception;
	public Object [] getNextRows(boolean forward, int pageSize) throws Exception;
	public Object [] getNextRowsFromOffset(boolean forward, int startOffset, int pageSize) throws Exception;
	public Object [] getNextRowsFromOffset(boolean forward, int startOffset) throws Exception;
	public void releaseAllResources() throws Exception;
}
