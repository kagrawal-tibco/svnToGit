package com.tibco.cep.modules.db.functions.cursor;

import java.sql.ResultSet;
import java.sql.Statement;

public class ResultSetResources {
	private Statement stmtHandle;
	private ResultSet rsethandle;
	
	public ResultSetResources(Statement stmt, ResultSet rset) {
		this.stmtHandle = stmt;
		this.rsethandle = rset;
	}
	
	Statement getStatement() {
		return this.stmtHandle;
	}
	
	ResultSet getResultSet() {
		return this.rsethandle;
	}
}
