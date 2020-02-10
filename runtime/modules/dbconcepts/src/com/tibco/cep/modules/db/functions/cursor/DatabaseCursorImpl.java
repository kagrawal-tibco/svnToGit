package com.tibco.cep.modules.db.functions.cursor;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.modules.db.functions.FunctionHelper;
import com.tibco.cep.modules.db.functions.QueryHelper;
import com.tibco.cep.modules.db.model.runtime.DBConcept;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;

/**
 * Implementation for Database cursor class.
 * @author vjavere
 *
 */
public abstract class DatabaseCursorImpl implements DatabaseCursor {
	
	protected String sqlCommand;
	protected String cursorName;
	String jdbcResourceURI;
	protected int pageSize = 500; //default value for pageSize, if user does not specify a valid size
	protected String resultConceptURI;
	
	static com.tibco.cep.kernel.service.logging.Logger logger;
	static {
		try {
			logger = RuleServiceProviderManager.getInstance().getDefaultProvider().getLogger(DatabaseCursorImpl.class);
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	public DatabaseCursorImpl(String cursorName, String resultConceptURI, String jdbcResourceURI, String sqlCommand, int pageSize) {
		if(cursorName == null || "".equals(cursorName.trim()))
			this.cursorName = "DBCursor_" + hashCode(); // some unique name given to the cursor, if user has not specified any
		else
			this.cursorName = cursorName;
		this.jdbcResourceURI = jdbcResourceURI;
		this.sqlCommand = sqlCommand;
		if(pageSize != -1)
			this.pageSize = pageSize; 
		this.resultConceptURI = resultConceptURI;
		
	}
	
	/**
	 * Gets the pagesize number of next rows
	 */
	public Object [] getNextRows(boolean forward) throws Exception {
		return getNextRows(forward, pageSize);
	}
	
	/**
	 * Get <pagesize> number of rows from startOffset
	 */
	public Object [] getNextRowsFromOffset(boolean forward, int startOffset) throws Exception {
		return getNextRowsFromOffset(forward, startOffset, pageSize);
	}

    /**
     * Gets the query <pagesize> number of records from the resultset in the given direction 
     * @param rs
     * @param pageSize
     * @param forward
     * @return
     * @throws Exception
     */
	public Object [] getQueryData(ResultSet rs, int pageSize, boolean forward) throws Exception {
		List ceptsList = new ArrayList();
		List columnsList = new ArrayList();
		boolean returnConcepts = false,returnMap=false;
		
		int i = 1;
		boolean rowExists = false;
		if(forward)
			rowExists = rs.next();
		else
			rowExists = rs.previous();
		if(resultConceptURI != null && !"".equals(resultConceptURI)) 
			returnConcepts = true;
		while(i <= pageSize && rowExists) {
			if(returnConcepts) {
				if("Map".equalsIgnoreCase(resultConceptURI)) { // return the result as array of Maps
					 returnMap = true;
					 columnsList.add(populateResultsInMap(rs));	
				} else {
					Concept cept = FunctionHelper.createConcept(resultConceptURI);
					if(cept instanceof DBConcept) {
						DBConcept dbconcept = (DBConcept) cept;
						dbconcept.setProperties(rs);
				    	if(FunctionHelper.isSetExtId()){
				    		String extId = FunctionHelper.generateExtId(dbconcept);
				    		dbconcept.setExtId(extId);
				    	}
						ceptsList.add(dbconcept);
					} else {
						QueryHelper.setPropertiesToConcept(cept,rs);
						ceptsList.add(cept);
					}
				}
			} else {
				columnsList.add(populateResultsAsObjects(rs));
			}
			i++;
			if(i <= pageSize) {
				if(forward)
					rowExists = rs.next();
				else
					rowExists = rs.previous();
			}
		}
	
		if(!rowExists && "Map".equalsIgnoreCase(resultConceptURI)) { // fix for BE#12295
			 returnMap = true;
		}
		
		if(returnConcepts && !returnMap) {
			Concept retTypeCept = FunctionHelper.createConcept(resultConceptURI);
			Concept[] ceptsArray = (Concept[]) ceptsList.toArray((Concept[])Array.newInstance(retTypeCept.getClass(), ceptsList.size()));
			return ceptsArray;
		}
		else {
			return columnsList.toArray();
		}
	}
	
	/**
	 * Populate resultset rows in an object array
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private Object [] populateResultsAsObjects(ResultSet rs) throws Exception {
		ResultSetMetaData rsmd = rs.getMetaData();
		Object [] columnData = new Object[rsmd.getColumnCount()];
		for(int j=1; j<=rsmd.getColumnCount(); j++) {
			columnData[j-1] = QueryHelper.convertToDBPropertyType(rs,j);
		}
		return columnData;
	}
	
	/**
	 * Populate resultset rows in map
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private Map populateResultsInMap(ResultSet rs) throws Exception {
		ResultSetMetaData rsmd = rs.getMetaData();
		Map rsMap = new HashMap();
		for(int j=1; j<=rsmd.getColumnCount(); j++) {
			rsMap.put(rsmd.getColumnName(j), QueryHelper.convertToDBPropertyType(rs,j));
		}
		return rsMap;
	}
	
	/**
	 * Gets SQL Command
	 * @return
	 */
	public String getSqlCommand() {
		return sqlCommand;
	}

	/**
	 * Sets SQL Command
	 * @param sqlCommand
	 */
	public void setSqlCommand(String sqlCommand) {
		this.sqlCommand = sqlCommand;
	}


	public String getCursorName() {
		return cursorName;
	}


	public void setCursorName(String cursorName) {
		this.cursorName = cursorName;
	}


	public String getJdbcResourceURI() {
		return jdbcResourceURI;
	}


	public void setJdbcResourceURI(String jdbcResourceURI) {
		this.jdbcResourceURI = jdbcResourceURI;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
		
		

}
