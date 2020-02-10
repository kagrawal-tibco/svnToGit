package com.tibco.cep.modules.db.functions;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.tibco.be.functions.object.ObjectHelper;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.modules.db.model.runtime.DBConcept;
import com.tibco.cep.modules.db.service.ConstraintRegistry;
import com.tibco.cep.modules.db.service.ConstraintRegistry.Relation;
import com.tibco.cep.modules.db.service.TemplateEntry;
import com.tibco.cep.runtime.model.PropertyNullValues;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.Property.PropertyConcept;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomBoolean;
import com.tibco.cep.runtime.model.element.PropertyAtomDateTime;
import com.tibco.cep.runtime.model.element.PropertyAtomDouble;
import com.tibco.cep.runtime.model.element.PropertyAtomInt;
import com.tibco.cep.runtime.model.element.PropertyAtomLong;
import com.tibco.cep.runtime.model.element.PropertyAtomString;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

public class FunctionHelper {

	static Logger logger;
	
	//fetchsize for resultsets
	static int fetchSize = 0;
	static String delimitersForQuery;
	
	static boolean setExtId; 
	static boolean reuseRefs;
	// schema name will be include in SQL statements on demand by user
	static boolean schemaNameInSqlStatement;
	
	//Is added to have the quotes around table names and its columns
	//was required by db2 installed on z/os 
	public static boolean quoteColumns = false;
	
	//set user defined sql timeout in seconds on statement
	//zero means unlimited
	static int sqltimeout = 0;
	
	static {
		try {
			logger = RuleServiceProviderManager.getInstance().getDefaultProvider().getLogger(FunctionHelper.class);
			String fetchSizeStr = RuleServiceProviderManager.getInstance().getDefaultProvider().getProperties().
				getProperty("be.dbconcepts.resultset.fetchsize", "0");
			fetchSize = Integer.parseInt(fetchSizeStr);
			delimitersForQuery = RuleServiceProviderManager.getInstance().getDefaultProvider().getProperties().getProperty("be.dbconcepts.sqlquery.delimiters", "");
			
			String strSetExtId = RuleServiceProviderManager.getInstance().getDefaultProvider().getProperties().
					getProperty("be.dbconcepts.setextid", "true");
			setExtId = "true".equalsIgnoreCase(strSetExtId);
			reuseRefs = "true".equalsIgnoreCase(RuleServiceProviderManager.getInstance().getDefaultProvider().getProperties().
							getProperty("be.dbconcepts.query.reuseRefs", "false"));
			// By default schema name will included in SQL statements
			// if schema name is available...
			// user include/exclude by setting this property
			schemaNameInSqlStatement = RuleServiceProviderManager.getInstance()
					.getDefaultProvider().getProperties().getProperty("be.dbconcepts.include.schemaname", "true").trim().equals("true");
			
			//Is added to have the quotes around table names and its columns
			//was required by db2 installed on z/os 
			quoteColumns = RuleServiceProviderManager.getInstance()
			.getDefaultProvider().getProperties().getProperty("be.dbconcepts.quote.column.names", "false").trim().equals("true");
			
			String strSQLTimeOut = RuleServiceProviderManager.getInstance().getDefaultProvider().getProperties().
	                    getProperty("be.dbconcepts.query.sqltimeout", "0");
	        sqltimeout = Integer.parseInt(strSQLTimeOut);
			
		} catch (Exception e) {
		}
	}
	public static PreparedStmtDbg getPrepStmt(DBConcept cept, Map pkSubset,
			String opType) throws Exception {
		com.tibco.cep.designtime.model.element.Concept dtConcept = 
			(com.tibco.cep.designtime.model.element.Concept) 
			RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getProject().getOntology().getEntity(cept.getExpandedName());
		return getPrepStmt (dtConcept, pkSubset, opType);
	}
	
    public static String getDelimitedFQDBObjectName(com.tibco.cep.designtime.model.element.Concept cept) {
		String tableName = getExtendedProp(cept, 
				com.tibco.cep.modules.db.model.designtime.DBConstants.OBJECT_NAME);
		if (tableName == null) {
			return null;
		}
		tableName = appendDelimitersForQuery(tableName);
		
		if (!schemaNameInSqlStatement) {
			return tableName;
		}
		
		String schemaName = getExtendedProp(cept, 
				com.tibco.cep.modules.db.model.designtime.DBConstants.SCHEMA_NAME);

		// If the schemaName as obtained by the "schema name" property in the designer is %%XYZ%% , it means that it refers to a global variable
		// So we need to handle this case separately
		if(schemaName.startsWith("%%") && schemaName.endsWith("%%")){
			String schemaNameGlobalVar = schemaName.substring(2,schemaName.length()-2);
	    	schemaName = RuleServiceProviderManager.getInstance().getDefaultProvider().getGlobalVariables().getVariableAsString(schemaNameGlobalVar, "");
		}	
		if (schemaName == null || schemaName.equals("")){
			logger.log(Level.ERROR, "Schema name not found for the table: %s", tableName);
			return tableName;
		}	
		schemaName = appendDelimitersForQuery(schemaName);
		// if this be.dbconcepts.include.schemaname property is true then
		// schema name will be part of SQL statement
		// else SQL statement will not have schema name
		if (schemaNameInSqlStatement) {
			tableName =  schemaName + "." +  tableName;
		} else {
			return tableName;
		}
		
		return tableName;
    }
    
    public static String getDelimitedSchemaName(com.tibco.cep.designtime.model.element.Concept cept) {
	
		String schemaName = getExtendedProp(cept, 
				com.tibco.cep.modules.db.model.designtime.DBConstants.SCHEMA_NAME);

		// If the schemaName as obtained by the "schema name" property in the designer is %%XYZ%% , it means that it refers to a global variable
		// So we need to handle this case separately
		if(schemaName.startsWith("%%") && schemaName.endsWith("%%")){
			String schemaNameGlobalVar = schemaName.substring(2,schemaName.length()-2);
	    	schemaName = RuleServiceProviderManager.getInstance().getDefaultProvider().getGlobalVariables().getVariableAsString(schemaNameGlobalVar, "");
		}	
		if (schemaName == null || schemaName.equals("")){
			return "";
		}	
		schemaName = appendDelimitersForQuery(schemaName);

		return schemaName;
    }    
    public static String getSimpleTableName(com.tibco.cep.designtime.model.element.Concept cept){
		String tableName = getExtendedProp(cept, 
				com.tibco.cep.modules.db.model.designtime.DBConstants.OBJECT_NAME);
		if (tableName == null) {
			return null;
		}
		return appendDelimitersForQuery(tableName);
    }

    
	public static PreparedStmtDbg getPrepStmt(com.tibco.cep.designtime.model.element.Concept cept, Map pkSubset,
			String opType) throws SQLException, Exception {
		String tableName = getDelimitedFQDBObjectName(cept);
		String connName = FunctionHelper.getExtendedProp(cept, com.tibco.cep.modules.db.model.designtime.DBConstants.JDBC_RESOURCE);
		Connection conn = JDBCHelper.getCurrentConnection(connName);
		StringBuffer buf = new StringBuffer();
		String sql = null;
		if (opType.equals("DELETE")) {
			sql = "delete from " + tableName + " where ";
		} else if (opType.equals("SELECT")) {
			sql = "select * from " + tableName + " where ";
		}

		Iterator i = pkSubset.entrySet().iterator();

		while (i.hasNext()) {
			Map.Entry e = (Entry) i.next();
			String propName = (String) e.getKey();
			String colName = getColNameFromCeptProp(cept, propName);
			if(colName != null) { 
				sql = sql + colName + "" + "=?";
				sql = sql + " and ";
			}
		}
		if(sql.endsWith(" and ")) {
			sql = sql.substring(0,sql.lastIndexOf(" and "));
		}
		buf.append(sql).append("(");
		PreparedStatement stmt = conn.prepareStatement(sql);
		i = pkSubset.entrySet().iterator();
		int j = 1;
		while (i.hasNext()) {

			Map.Entry e = (Entry) i.next();
			String propName = (String) e.getKey();
			String colName = getColNameFromCeptProp(cept, propName);
			if(colName != null) {
				PropertyAtom val = (PropertyAtom) e.getValue();
				addPreparedStmtParam(stmt, val, j, buf);
				buf.append(" ,");
			}
			j++;
		}
		if(buf.toString().endsWith(" ,")) {
			buf = new StringBuffer(buf.substring(0,buf.lastIndexOf(" ,")));
		}
		buf.append(")");
		PreparedStmtDbg prepStmt = new PreparedStmtDbg(stmt, buf.toString());
		return prepStmt;
	}

	static int addPreparedStmtParam(PreparedStatement stmt, Property p,
			int pos, StringBuffer buf) throws Exception {
		if (p == null) {
			stmt.setObject(pos, null);
			if (logger.isEnabledFor(Level.DEBUG)) {
				buf.append("null");
			}
		} else if (p instanceof PropertyAtomString) {
			stmt.setString(pos, ((PropertyAtomString) p).getString());
			if (logger.isEnabledFor(Level.DEBUG)) {
				buf.append(((PropertyAtomString) p).getString());
			}
		} else if (p instanceof PropertyAtomInt) {
			stmt.setInt(pos, ((PropertyAtomInt) p).getInt());
			if (logger.isEnabledFor(Level.DEBUG)) {
				buf.append(((PropertyAtomInt) p).getInt());
			}
		} else if (p instanceof PropertyAtomDouble) {
			stmt.setDouble(pos, ((PropertyAtomDouble) p).getDouble());
			if (logger.isEnabledFor(Level.DEBUG)) {
				buf.append(((PropertyAtomDouble) p).getDouble());
			}
		} else if (p instanceof PropertyAtomLong) {
			stmt.setLong(pos, ((PropertyAtomLong) p).getLong());
			if (logger.isEnabledFor(Level.DEBUG)) {
				buf.append(((PropertyAtomLong) p).getLong());
			}
		} else if (p instanceof PropertyAtomDateTime) {
			// TODO stmt.setDate(pos,
			java.sql.Timestamp sqlDt = null;
			if (((PropertyAtomDateTime) p).getDateTime() != null ) { 
			
				Date d = ((PropertyAtomDateTime) p).getDateTime().getTime();
				 sqlDt = new java.sql.Timestamp(d.getTime());
			}
			
			stmt.setTimestamp(pos, sqlDt);
			if (logger.isEnabledFor(Level.DEBUG)) {
				buf.append(sqlDt);
			}
		} else if (p instanceof PropertyAtomBoolean) {
			stmt.setBoolean(pos, ((PropertyAtomBoolean) p).getBoolean());
			if (logger.isEnabledFor(Level.DEBUG)) {
				buf.append(((PropertyAtomBoolean) p).getBoolean());
			}
		}
		if (logger.isEnabledFor(Level.DEBUG)) {
			buf.append(", ");
		}
		return pos++;
	}

	static String getExtendedProp(
			com.tibco.cep.designtime.model.element.Concept cept, String propName) {
		if (cept == null) {
			return null;
		}
		String propVal = (String) cept.getExtendedProperties().get(propName);
		if (propVal == null) {
			com.tibco.cep.designtime.model.element.Concept superCept = cept
					.getSuperConcept();
			return getExtendedProp(superCept, propName);
		} else {
			return propVal;
		}
	}

	public static PreparedStatement getPreparedStatement(PSInfo psInfo,
			Connection conn, int sqlType) throws Exception {
		try {
			PreparedStatement stmt = null;
			if (sqlType == TemplateEntry.SELECT_STMT) {
				stmt = conn.prepareStatement(psInfo.getPsStmt());
			} else if (sqlType == TemplateEntry.STORED_PROC) {
				stmt = conn.prepareCall("call " + psInfo.getPsStmt());
			} else {
				throw new Exception("SQL Type not recognized " + sqlType);
			}
			Iterator i = psInfo.getPatternValues().entrySet().iterator();

			while (i.hasNext()) {
				Map.Entry entry = (Entry) i.next();
				setIndexValue(stmt, ((Integer) entry.getKey()).intValue(),
						entry.getValue());
			}
			return stmt;
		} catch (Exception e) {
			logger.log(Level.ERROR, e, "Error creating prepared statement %s", e.getMessage());
			throw e;
		}
	}
	
	public static CallableStatement getCallableStatement(Connection conn, String procText) throws Exception {
		
		if(conn == null){
			throw new IllegalArgumentException("Connection is null");
		}
		
		CallableStatement stmt = conn.prepareCall(procText);
		return stmt;
	}

	static Concept getPropertyConcept(Concept c, String propertySequence) throws Exception {
		String[] tokens = propertySequence.split("\\.");
		if (tokens.length == 0) {
			c = getDummyPropCeptInstance(c, propertySequence);
			return c;
		} else {
			for (int i = 0; i < tokens.length; i++) {
				c = getDummyPropCeptInstance(c, tokens[i]);
			}
			return c;
		}
	}

	static String[][] getFullKeys(Concept cept, String propertySequence)  throws Exception {
		Concept parentCept = getParentPropertyConcept(cept, propertySequence);
		String propName = getLastPropertyName(propertySequence);
		Relation reln = ConstraintRegistry.getInstance().getDerivedRelation(
				parentCept, propName);
		if (reln == null) {
			return new String[][] {};
		}
		return reln.getKeyReferences();
	}

	static void setIndexValue(PreparedStatement stmt, int index, Object value)
			throws Exception {
		if (value != null) {
			if (value instanceof GregorianCalendar) {
				GregorianCalendar gc = (GregorianCalendar) value;
				java.sql.Timestamp sqlDateTimeStamp = new java.sql.Timestamp(gc
						.getTimeInMillis());
				value = sqlDateTimeStamp;
			}
			if (value instanceof java.sql.Timestamp) {
				GregorianCalendar gc = new GregorianCalendar();
				gc.setTimeInMillis(((java.sql.Timestamp) value).getTime());
				int millisecond = gc.get(GregorianCalendar.MILLISECOND);
				if (millisecond == 0) {
					java.util.Date utilDt = gc.getTime();
					java.sql.Date sqlDate = new java.sql.Date(utilDt.getTime());
					value = sqlDate;
				}
			}
		}
		stmt.setObject(index, value);
	}
	
	static Concept getDummyPropCeptInstance(Concept concept, String propName)
			throws Exception {
		if (concept != null) {
			Property childProp = concept.getProperty(propName);
			if (childProp != null && childProp instanceof PropertyConcept) {
				Class clz = ((PropertyConcept) childProp).getType();
				return (Concept) clz.newInstance();
			} else {
				throw new Exception("Property " + propName + " not found in concept: "
						+ concept.getExpandedName());
			}
		} else {
			throw new Exception("Specified concept is null ");
		}
	}

	public static ConceptImpl createConcept(String conceptNm) throws Exception {
			TypeManager tm = RuleServiceProviderManager.getInstance()
					.getDefaultProvider().getTypeManager();
			//conceptNm = conceptNm.substring(TypeManager.DEFAULT_BE_NAMESPACE_URI.length());
			ConceptImpl responseCpt = (ConceptImpl) tm.createEntity(conceptNm);
			return responseCpt;

	}
	
	private static Concept getParentPropertyConcept(Concept c,
			String propertySequence) throws Exception {
		String[] tokens = propertySequence.split("\\.");
		int i = 0;
		for (; i < tokens.length - 1; i++) {
			c = getDummyPropCeptInstance(c, tokens[i]);
		}
		return c;
	}

	private static String getLastPropertyName(String propertySequence) {
		String[] tokens = propertySequence.split("\\.");
		if (tokens.length == 0) {
			return propertySequence;
		} else {
			return tokens[tokens.length - 1];
		}
	}
	
	public static class PreparedStmtDbg {
		public PreparedStatement stmt;
		public String debugStr;

		public PreparedStmtDbg(PreparedStatement stmt, String debugStr) {
			this.stmt = stmt;
			this.debugStr = debugStr;
		}
	}

    public static String getColForProp(String[][] propToColMap, String propName) {
    	for (int i=0; i<propToColMap.length; i++) {
    		if (propToColMap[i][0].equals(propName)) {
    			return getColWithDelimter( propToColMap[i][1] );
    		}
    	}
    	return null;
    }
    
    /**
     * This function appends delimited identifiers to the dbObjectName
     * @param dbObjectName 
     * it needs to be added for code generation
     * @return -- the delimited db object name
     */
    public static String appendDelimitersForQuery(String dbObjectName) {
    	if(delimitersForQuery != null && !"".equals(delimitersForQuery.trim())) { 
	    	StringBuffer delimitedStr = new StringBuffer();
	    	delimitedStr.append(delimitersForQuery.charAt(0)).append(dbObjectName).append(delimitersForQuery.charAt(1));
	    	return delimitedStr.toString();
    	} else {
    		return dbObjectName;
    	}
	}
    
    public static String getPropForCol(String[][] propToColMap, String colName) {
       	for (int i=0; i<propToColMap.length; i++) {
    		if (propToColMap[i][1].equals(colName)) {
    			return propToColMap[i][0];
    		}
    	}
    	return colName;    	
    }

	public static LinkedHashMap getNotNullPKs (DBConcept dbConcept) {
		LinkedHashMap colSet = new LinkedHashMap(); 
		String[] primaryKeys = dbConcept.getPrimaryKeyNames();
		for (int i=0; i<primaryKeys.length; i++) {
			String propName = primaryKeys[i];
			Property prop = dbConcept.getPropertyNullOK(propName);
			if (prop == null || (prop!=null && !(prop instanceof PropertyAtom))) {
				continue;
			}
			colSet.put(propName, prop);
		}
		return colSet;
	}
    public static String getColNameFromCeptProp (com.tibco.cep.designtime.model.element.Concept cept, String propName) {
    	PropertyDefinition pd = cept.getPropertyDefinition(propName, false);
    	return getPropertyColName(pd);
    }
    public static String getPropertyColName(PropertyDefinition prop) {
    	if (prop != null) {
    		return getColWithDelimter( (String) prop.getExtendedProperties().get(com.tibco.cep.modules.db.model.designtime.DBConstants.COLUMN_NAME) );
    	}
    	return null;
    }

    static Object executeSQL(DBConcept cept, Statement stmt, String sql)
			throws RuntimeException {
		Object returnObj = null;
		boolean hasResults = false;
		try {
			stmt.setFetchSize(fetchSize);
	        stmt.setQueryTimeout(sqltimeout);
	        
            if (stmt instanceof PreparedStatement) {
            	hasResults = ((PreparedStatement) stmt).execute();
            } else {
            	hasResults = stmt.execute(sql);
            }
            if (hasResults) {
            	ResultSet rs = stmt.getResultSet();
            	List cepts = new ArrayList();
            	while (rs.next()) {
            		String ceptName = ((ConceptImpl) cept).getType();
            		ceptName = ceptName
            				.substring(TypeManager.DEFAULT_BE_NAMESPACE_URI.length());
            		DBConcept dbconcept = (DBConcept) FunctionHelper
            				.createConcept(ceptName);
            		dbconcept.setProperties(rs);
            		// if "tibco.be.dbconcept.setextid" property is set to true
            		// then generate and set extid
                	if (setExtId){
                		String extId = generateExtId(dbconcept);
                		dbconcept.setExtId(extId);
                		if (reuseRefs) {
                			Concept c = ObjectHelper.getByExtId(extId);
                			if (c != null) {
                				cepts.add(c);
                			} else {
            	    			cepts.add(dbconcept);
                			}
                		} else {
                		    cepts.add(dbconcept);
                		}
                	} else {
                		cepts.add(dbconcept);
                	}
            	}
            	returnObj = cepts;
            } else {
            	returnObj = new Integer(stmt.getUpdateCount());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
		return returnObj;
	}
    
    public static Object getPropertyValNullOK (PropertyAtom pa) {
    	Object o  = pa.getValue();
    	if (o == null) {
    		return null;
    	}
    	
    	if (PropertyNullValues.isPropertyValueNull(pa)) {
    		return null;
    	}
    	return o;
    	
    }

    public static void setPropValue (PropertyAtom p, Object val) {
    	if (val instanceof BigDecimal) {
    		BigDecimal bd = (BigDecimal)val;
    		if (p instanceof PropertyAtomInt) {
    			((PropertyAtomInt)p).setInt( bd.intValue());
    		} else if (p instanceof PropertyAtomDouble) {
    			((PropertyAtomDouble)p).setDouble(bd.doubleValue());
    		}
    	} else if (val instanceof BigInteger) {
    		BigInteger bd = (BigInteger)val;
    		if (p instanceof PropertyAtomInt) {
    			((PropertyAtomInt)p).setInt( bd.intValue());
    		} else if (p instanceof PropertyAtomDouble) {
    			((PropertyAtomDouble)p).setDouble(bd.doubleValue());
    		}
    	} else {
    		p.setValue(val);
    	}
    }

    public static Object getValueAtIndex(int index, ResultSet rs) throws Exception {
    	int colType = rs.getMetaData().getColumnType(index);
    	switch (colType) {
    	case Types.BIGINT:
    	case Types.CHAR:
    	case Types.BOOLEAN:
    	case Types.DECIMAL:
    	case Types.DOUBLE:
    	case Types.FLOAT:
    	case Types.INTEGER:
    	case Types.NULL:
    		return rs.getObject(index);
    	case Types.DATE:
    	case Types.TIME:
    	case Types.TIMESTAMP:
    		return rs.getTimestamp(index);
    	case Types.NUMERIC:
    	case Types.REAL:
    	case Types.SMALLINT:
    	default:
    		return rs.getObject(index);
    	}
    }

    public static Object getDefaultValue(com.tibco.cep.runtime.model.element.Concept cept, String propName) {
    	
    	RuleSession s = RuleSessionManager.getCurrentRuleSession();
    	Ontology o = s.getRuleServiceProvider().getProject().getOntology();
		com.tibco.cep.designtime.model.element.Concept dtConcept = 
			(com.tibco.cep.designtime.model.element.Concept) 
			o.getEntity(cept.getExpandedName());
		PropertyDefinition pd = dtConcept.getPropertyDefinition(propName, false);
		if (pd == null) return null;
		if (pd.getType() != PropertyDefinition.PROPERTY_TYPE_CONCEPT &&
			pd.getType() != PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE) {
			
			String defVal = pd.getDefaultValue();
	        if("".equals(defVal) || defVal == null) return null;
	        switch(pd.getType()) {
	            case PropertyDefinition.PROPERTY_TYPE_BOOLEAN:
	                return Boolean.valueOf(defVal);
	            case PropertyDefinition.PROPERTY_TYPE_INTEGER:
	                try {
	                    return Integer.parseInt(defVal);
	                } catch(NumberFormatException nfe) {
	                    //todo debug code (enforce this in the gui?)
	                    return null;
	                    //throw new IllegalArgumentException("NumberFormat exception while parsing integer default value for property definition: " + pd.getName());
	                }
	            case PropertyDefinition.PROPERTY_TYPE_REAL:
	                try {
	                    return Double.toString(Double.parseDouble(defVal));
	                } catch(NumberFormatException nfe) {
	                    //todo debug code
	                    throw new IllegalArgumentException("NumberFormat exception while parsing double default value for property definition: " + pd.getName());
	                }
	            case PropertyDefinition.PROPERTY_TYPE_STRING:
	                return defVal;
	            case PropertyDefinition.PROPERTY_TYPE_CONCEPT:
	            case PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE:
	                //todo implement default values for concept properties
	                return null;
	            case PropertyDefinition.PROPERTY_TYPE_DATETIME:
	            	if (defVal.equalsIgnoreCase("sysdate")) {
	            		return Calendar.getInstance();
	            	}
	            default:
	                return null;
	        }
		}
    	return null;
    }

    public static boolean getDefaultValueExists(com.tibco.cep.runtime.model.element.Concept cept, String propName) {
    	
    	RuleSession s = RuleSessionManager.getCurrentRuleSession();
    	Ontology o = s.getRuleServiceProvider().getProject().getOntology();
		com.tibco.cep.designtime.model.element.Concept dtConcept = 
			(com.tibco.cep.designtime.model.element.Concept) 
			o.getEntity(cept.getExpandedName());
		PropertyDefinition pd = dtConcept.getPropertyDefinition(propName, false);
		if (pd == null) {
			return false;
		}
		if (pd.getType() != PropertyDefinition.PROPERTY_TYPE_CONCEPT &&
			pd.getType() != PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE) {
			
			String defVal = pd.getDefaultValue();
			if (defVal != null && !defVal.equals("")) {
				return true;
			}
		}
		return false;
    }
    

//    public static String getColName(String[][] pToCol, String propName) {
//    	
//    	for (int i=0; i<pToCol.length; i++) {
//    		if (pToCol[i][0].equals(propName)) {
//    			return getColWithDelimter ( pToCol[i][1] );
//    		}
//    	}
//    	return "";
//    }

//    public static void addPKColsToSet (String[]pks, String[][]pToCol, Set set) {
//    	for (int i=0; i<pks.length; i++) {
//    		set.add(FunctionHelper.getColForProp(pToCol, pks[i]));
//    	}
//    }

    public static Object invokeMethod (Object obj, String methodNm) {
    	if (obj == null) {
    		return null;
    	}
    	try {
    		Class clz = obj.getClass();
    		Method m = clz.getMethod(methodNm);
    		Object retVal = m.invoke(obj);
    		return retVal;
    	} catch (Exception e) {
    		
    	}
    	return null;
    }
    
    public static boolean isSetExtId() {
    	return setExtId;
    }
    /*
     * 
     * Generate extid using extended property set for extid_props 
     */
    public static String generateExtId(DBConcept cept) throws Exception {
    	String extIdPrefix = cept.getExtIdPrefix();
    	String[] extIdProps = cept.getExtIdPropertyNames();
    	
    	String extId = "";
    	if(extIdProps.length > 0){
    		extId = extIdPrefix;
    	}
    	
    	for (int i = 0; i < extIdProps.length; i++) {
    		String propName = extIdProps[i];
    		String val = "";
    		Property prop = cept.getProperty(propName);
    		if(prop != null){
	    		if(prop instanceof PropertyAtom){
	    			// If the property is DateTime, then convert into long(time since epoch)
	    			if (prop instanceof PropertyAtomDateTime){
	    				val = String.valueOf(((PropertyAtomDateTime)prop).getDateTime().getTimeInMillis());
	    			} else {
	    				val = ((PropertyAtom) prop).getString();
	    			}
	    		} else if(prop instanceof PropertyArray){
	    			val = ((PropertyArray) prop).getString();
	    		}
    		}else if(propName.startsWith("%%") && propName.endsWith("%%")){
    			//its a global variable, resolve its value
    			val = RuleServiceProviderManager.getInstance().getDefaultProvider().getGlobalVariables().substituteVariables(propName).toString();
    		} else {
    			throw new Exception("Error while generating ExtId. " + propName +" is not a property or a global variable");
    		}
    		
    		if("".equals(extId)){
    			extId = val;
    		} else {
    			extId = extId + ":" + val;
    		}
		}
    	
    	return extId;
    }
    
    
    /*
     * Query database and return value for the sequence 
     */
    static long getSequenceValue(String seqName, boolean next) throws Exception {
        Connection conn = JDBCHelper.getCurrentConnection();
    	String valType = "NEXTVAL";
        if (!next) {
        	valType = "CURRVAL";
        }
        String seqCall = "select " + seqName + "." + valType +" from dual";
        Statement stmt = null;
        ResultSet rs   = null;
        long seqValue = -1;
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(seqCall);
            if (rs.next()) {
                seqValue = rs.getLong(1);
                logger.log(Level.DEBUG, "Sequence: %s - value: %s", seqCall, seqValue) ;
            }
        } finally {
            rs.close();
            stmt.close();
        }
        return seqValue;
    }
    
    public static com.tibco.cep.designtime.model.element.Concept getDTConceptFromRT (com.tibco.cep.runtime.model.element.Concept concept) {
		com.tibco.cep.designtime.model.element.Concept dtConcept = 
			(com.tibco.cep.designtime.model.element.Concept) 
			RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getProject().getOntology().getEntity(concept.getExpandedName());
		return dtConcept;
    }
    public static String getColWithDelimter(String column) {
    	if (quoteColumns) {
    		column = "\"" + column + "\"";
		}
		return column;
    }
}