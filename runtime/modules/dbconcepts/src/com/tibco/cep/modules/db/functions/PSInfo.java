package com.tibco.cep.modules.db.functions;

import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * 
 * Class to hold PreparedStatement text and a map that contains 
 * An instance of this class is returned by SQLHelper. 
 * 
 * key ==> index to set in prepared statement
 * value ==> value to set at this index in the PreparedStatement
 * 
 */
public class PSInfo {
	
	// SELECT * FROM ORDER WHERE CUST_NAME = ?
	String psStmt; 
	
	// (key = 1, value = "JOE")
	Map indexAndValueMap;
	
	// SELECT * FROM ORDER WHERE CUST_NAME = JOE (for debug purposes only)
	String sqlWithVal;
	
	public PSInfo(){
		indexAndValueMap = new TreeMap();
	}
	
	public String getPsStmt() {
		return psStmt;
	}
	
	public void setPsStmt(String psStmt) {
		this.psStmt = psStmt;
	}
	
	public Map getPatternValues() {
		return indexAndValueMap;
	}
	
	public void setPatternValues(Map m){
		indexAndValueMap.putAll(m);
	}
	
	public String getSqlWithVal() {
		return sqlWithVal;
	}
	
	public void setSqlWithVal(String sqlWithVal){
		this.sqlWithVal =sqlWithVal;
	}
	
}
