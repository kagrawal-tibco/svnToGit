package com.tibco.cep.modules.db.functions.cursor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.modules.db.functions.DatabaseCursorManager;
import com.tibco.cep.modules.db.functions.QueryHelper;
import com.tibco.cep.modules.db.service.DBConnectionFactory;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Database Cursor implementation using scrollable and concurrent_read_only JDBC ResultSet 
 * The cursor holds on the db connection until the cursor is open.
 * The connection is released only when the cursor is closed. 
 * @author vjavere
 *
 */
public class ResultSetDatabaseCursor extends DatabaseCursorImpl {
	
	private int startOffset,endOffset,totalRows =-1;
	private Connection conn;
	private Statement stmtHandle;
	private ResultSet rsHandle;
	
	public ResultSetDatabaseCursor(String cursorName,String resultConceptURI, String jdbcResourceURI, String sqlCommand, int pageSize, Object argsObj) throws Exception {
		super(cursorName,resultConceptURI,jdbcResourceURI,sqlCommand,pageSize);
		this.conn = DatabaseCursorManager.getCurrentConnection(jdbcResourceURI);
		ResultSetResources resources = fetchResultSet(this.conn, sqlCommand, argsObj);
		this.stmtHandle = resources.getStatement();
		this.rsHandle = resources.getResultSet();
		this.startOffset = 0;
		this.endOffset = 0;
	}
	
	/**
	 * Fetches the resultset for the sql query
	 * @param conn
	 * @param sql
	 * @param argsObj
	 * @return
	 * @throws Exception
	 */
	public static ResultSetResources fetchResultSet(Connection conn,String sql,Object argsObj) throws Exception {
		ResultSet rs = null;
		Statement stmt = null;		
		if (argsObj == null) {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
		            ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
		} else {
			stmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,
		            ResultSet.CONCUR_READ_ONLY);
			
			Object [] args = null;
			if (argsObj instanceof Concept) {
				args = QueryHelper.getNotNullParameterValues((Concept)argsObj);
			} else if (argsObj instanceof Object []) {
				args = (Object [])argsObj;
			} else {
				throw new Exception("Unknown Argument Type");
			}
			for (int i=0; i<args.length; i++) {
				if (args[i] instanceof Calendar) {
					java.sql.Timestamp ts = new Timestamp(((Calendar)args[i]).getTimeInMillis());
					args[i] = ts;
				}
				((PreparedStatement)stmt).setObject(i+1, args[i]);
			}
			rs = ((PreparedStatement)stmt).executeQuery();
		}
		return new ResultSetResources(stmt, rs);
	}

	/**
	 * Releases the resultset handle
	 */
	public void releaseAllResources() throws Exception {
		if (rsHandle != null) {
			rsHandle.close();
		}
		if (stmtHandle != null) {
			stmtHandle.close();
		}
		if (conn != null && canUnsetConnection()) {
			DatabaseCursorManager.unsetConnectionForQuery(jdbcResourceURI, conn);
			conn = null;
		}
	}
	
	private boolean canUnsetConnection() {
		RuleServiceProvider RSP = DBConnectionFactory.getInstance().getModuleManager().getRuleServiceProvider();
		return Boolean.valueOf(RSP.getProperties().getProperty(DBCONCEPTS_CURSOR_CLOSE_QUERY_CLOSE_CONNECTION_PROPERTY, "true"));
	}
	
    /**
     * Gets the next <pagesize> number of rows from the resultset in the given direction
     */ 
	public Object [] getNextRows(boolean forward, int pageSize) throws Exception {
		Object [] resultcepts = null;
		if (!forward) {
			if (this.startOffset == 0 ) {
				logger.log(Level.DEBUG, "Returning with empty list");
				return new Concept []{};
			} else {
				int relPos = -1;
				if (rsHandle.isAfterLast()) {
					rsHandle.last();
					relPos = pageSize;
				} else {
					if (startOffset-pageSize <= 0) {
						pageSize = startOffset-1;
					}
					relPos = (endOffset-startOffset+pageSize) + 1;
				}
				
				rsHandle.relative(-relPos);
				logger.log(Level.DEBUG, "Relative Position: %s", relPos);
			}
		}
		logger.log(Level.DEBUG, "Start offset=%s, end offset=%s", this.startOffset, this.endOffset);
		resultcepts = getQueryData(rsHandle, pageSize,true);
		if (forward) {
			if (this.startOffset == 0 ) {
				if (resultcepts.length > 0) {
					this.startOffset = 1;
					this.endOffset = resultcepts.length;
				}
			} else {
				if (resultcepts.length > 0) {
					this.startOffset = endOffset + 1;
					this.endOffset += resultcepts.length;
					if (this.totalRows < (this.startOffset + resultcepts.length)) {
						this.totalRows = (this.startOffset + resultcepts.length) - 1;
					}
				} else {
					if (this.totalRows == -1) {
						this.totalRows = this.endOffset;
					}
					this.endOffset = totalRows+1;
					this.startOffset = this.endOffset;
					logger.log(Level.DEBUG, "Reached end position with total rows=%d", this.totalRows);
				}
			}
		} else {
			if (resultcepts.length > 0) {
				if (this.endOffset > this.totalRows) {
					this.endOffset = this.totalRows;
					this.startOffset = this.totalRows - resultcepts.length + 1; 
				} else {
					this.endOffset = startOffset - 1 ;
					this.startOffset -= resultcepts.length;
				}
			} else {
				logger.log(Level.DEBUG, "Reached starting position!");
				rsHandle.beforeFirst();
				this.startOffset = 0;
				this.endOffset = 0;
			}
		}
		logger.log(Level.DEBUG, "After processing: start=%s end=%s", this.startOffset, this.endOffset);
		return resultcepts;
	}
	
	/**
     * Gets the next <pagesize> number of rows from the resultset in the given direction,
     * starting from <startOffset>
	 */
	public Object [] getNextRowsFromOffset(boolean forward, int startOffset, int pageSize) throws Exception {
		Object [] resultcepts = null;
		boolean abs = false;
		if (forward)
			abs = rsHandle.absolute(startOffset-1);
		else
			abs = rsHandle.absolute(startOffset+1);
		logger.log(Level.DEBUG, "Absolute position: %s", abs);
		if (!abs) {
			return new Concept[]{};
		}
		else {
			resultcepts = getQueryData(rsHandle, pageSize,forward);
			this.startOffset = 0;
			this.endOffset = 0;
			rsHandle.beforeFirst();
		}
		logger.log(Level.DEBUG, "After processing: start=%s end=%s", this.startOffset, this.endOffset);
		return resultcepts;
	}
	
	/*
	public Concept [] getNextRows(boolean forward, int pageSize) throws Exception {
		Concept [] resultcepts = null;
		if (!forward) {
			if (this.startOffset == 0 || this.startOffset == 1) {
				JDBCHelper.logger.logDebug("Returning with empty list");
				return new Concept[]{};
			} else {
				int relPos = -1;
				if (startOffset-pageSize <= 0)
					pageSize = startOffset-1;
				if (rsHandle.isAfterLast()) {
					rsHandle.last();
					relPos = (endOffset-startOffset+pageSize) +1;
				} else {
					relPos = (endOffset-startOffset+pageSize) +1;
				}
				
				rsHandle.relative(-relPos);
				JDBCHelper.logger.logDebug("Relative Position:"+relPos);
			}
		}
		JDBCHelper.logger.logDebug("Start Offset:"+this.startOffset+" End Offset:"+this.endOffset);
		resultcepts = getConcepts(rsHandle, pageSize,true);
		if (forward) {
			if (this.startOffset == 0 ) {
				if (resultcepts.length > 0) {
					this.startOffset = 1;
					this.endOffset = resultcepts.length;
				}
			} else {
				if (resultcepts.length > 0) {
					this.startOffset = endOffset + 1;
					this.endOffset += resultcepts.length;
				}
			}
		} else {
			if (resultcepts.length > 0) {
				this.endOffset = startOffset - 1 ;
				this.startOffset -= resultcepts.length;
			}
		}
		JDBCHelper.logger.logDebug("After processing: start:"+this.startOffset+ " End:"+this.endOffset);
		return resultcepts;
	}*/
}
