package com.tibco.cep.modules.db.functions;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.modules.db.functions.FunctionHelper.PreparedStmtDbg;
import com.tibco.cep.modules.db.model.runtime.AbstractDBConceptImpl;
import com.tibco.cep.modules.db.model.runtime.DBConcept;
import com.tibco.cep.modules.db.service.ConstraintRegistry;
import com.tibco.cep.runtime.model.PropertyNullValues;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.ContainedConcept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.Property.PropertyConcept;
import com.tibco.cep.runtime.model.element.PropertyArrayConceptReference;
import com.tibco.cep.runtime.model.element.PropertyArrayContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomConceptReference;
import com.tibco.cep.runtime.model.element.PropertyAtomContainedConcept;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;

/**
 * Helper functions for JDBC Query functions
 * @author bgokhale
 *
 */

public class QueryHelper {
	private static final int INT_PRECISION_LIMIT = 10;
	
	static Logger logger;
	static {
		try {
			logger = RuleServiceProviderManager.getInstance().getDefaultProvider().getLogger(QueryHelper.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	


	static Concept[] queryChildCepts(Concept retTypeCept, List concepts, boolean queryChildren) throws Exception {
		Map queriedCepts = new HashMap();
		getExistingCepts(queriedCepts, concepts);
		Concept[] cepts = (Concept[]) concepts.toArray((Concept[])Array.newInstance(retTypeCept.getClass(), concepts.size()));
		if (queryChildren) {
			for (int i = 0; i < cepts.length; i++) {
				queryChildren((DBConcept) cepts[i], queryChildren, queriedCepts);
			}
		}
		return cepts;
	}

	static List executeQuery(DBConcept cept, Statement stmt, String sql)
			throws Exception {
		Object obj = FunctionHelper.executeSQL(cept, stmt, sql);
		if (obj instanceof List) {
			return (List)obj;
		} else {
			throw new Exception("SQL did not return resultset");
		}
	}

	static PreparedStmtDbg getPrepStmt(DBConcept dbConcept,
			SimpleEvent pKeyEvent) throws SQLException {
		int conditions = 0;
		
		com.tibco.cep.designtime.model.element.Concept dtCept = FunctionHelper.getDTConceptFromRT(dbConcept);

		String tableName = FunctionHelper.getDelimitedFQDBObjectName(dtCept);

		StringBuffer sqlBuf = new StringBuffer("select * from ").append(tableName);;
		
		Set nameSet = new HashSet(Arrays.asList(pKeyEvent.getPropertyNames()));
		Map keySet = new LinkedHashMap();

		for (Iterator it = nameSet.iterator(); it.hasNext();) {
			String keyName = (String) it.next();
			try {
				PropertyAtom pa = dbConcept.getPropertyAtom(keyName);
				if (pa == null) {
					continue; //No such column in the table, skip
				}
				if (!isPK(dbConcept, keyName)) {
					continue;
				}
				String colName = FunctionHelper.getColForProp(((AbstractDBConceptImpl)dbConcept).getPropertyToColMap(), keyName);
				if(colName != null) {
					conditions++;
					if (conditions == 1) {
						sqlBuf.append(" where ");
					}
					if (conditions > 1) {
						sqlBuf.append(" and ");
					}
					sqlBuf.append(colName).append("=?");
					keySet.put(colName, pKeyEvent.getProperty(keyName));
				}

			} catch (NoSuchFieldException e) {
				continue;
			}
		}
		
		Connection conn = JDBCHelper.getCurrentConnection(((AbstractDBConceptImpl)dbConcept).getJDBCResourceName());
		PreparedStatement stmt = conn.prepareStatement(sqlBuf.toString());
		
		StringBuffer debugStr = new StringBuffer(64).append(sqlBuf);
		
		int pos = 1;
		for (Iterator i=keySet.entrySet().iterator(); i.hasNext();) {
			Map.Entry e = (Entry) i.next();
			Object val = e.getValue();
			if (val == null) {
				debugStr.append("null, ");
			} else {
				debugStr.append(val + ", ");
			}
			if (val instanceof GregorianCalendar) {
				Date d = ((GregorianCalendar)val).getTime();
				val = new java.sql.Timestamp(d.getTime());
			}
			stmt.setObject(pos, val);
			pos++;
		}
		
		PreparedStmtDbg prepStmt = new PreparedStmtDbg(stmt, debugStr.toString());
	
		return prepStmt;
	}

	static List executeInitialQuery(DBConcept cept, LinkedHashMap colSet) throws Exception {
		FunctionHelper.PreparedStmtDbg prepStmtDbg = null;
		try {
			prepStmtDbg = FunctionHelper.getPrepStmt(cept, colSet, "SELECT");
			List concepts = executeQuery(cept, prepStmtDbg.stmt, null);
			return concepts; 
		} catch (Exception e) {
			throw e;
		} finally {
			if (prepStmtDbg != null && prepStmtDbg.stmt != null) {
				try {
					prepStmtDbg.stmt.close();
				} catch (Exception e){}
			}
		}
	}

	static void queryChildren(DBConcept dbconcept, boolean queryChildren, Map queriedCepts)
			throws Exception {

		Property[] props = dbconcept.getProperties();
		for (int i=0; i<props.length; i++) {
			Property property = props[i];
			String propName = property.getName();
			
			//loop over non-concept properties
			if (!(property instanceof PropertyConcept)) continue;
			
			ConstraintRegistry.Relation reln = 
				ConstraintRegistry.getInstance().getDerivedRelation(dbconcept, propName);

			if (reln == null || reln != null && reln.getKeyReferences().length < 1) {
				continue;
			}
			
			String[][] keys = reln.getKeyReferences();
			LinkedHashMap pkeys = new LinkedHashMap();
			for (int j = 0; j < keys.length; j++) {
				String parentKey = keys[j][0];
				//if (childPks.contains(keys[j][1])) { //BALA: Can have FK on non-primary (but unique) keys..!!!
					pkeys.put(keys[j][1], dbconcept.getProperty(parentKey));
				//}
			}
			
			Class conceptType = ((PropertyConcept)property).getType();
			DBConcept child = (DBConcept)conceptType.newInstance();
			FunctionHelper.PreparedStmtDbg stmtDbg = FunctionHelper.getPrepStmt(child, pkeys, "SELECT");
			if (stmtDbg == null || stmtDbg.stmt == null) {
				continue;
			}
			
            logger.log(Level.DEBUG, stmtDbg.debugStr);

			List cepts = new ArrayList();
			try {
				cepts = executeQuery(child, stmtDbg.stmt, null);
			} catch (Exception e) {
				throw e;
			} finally {
				if (stmtDbg != null && stmtDbg.stmt != null) {
					try {
						stmtDbg.stmt.close();
					} catch (Exception e) {}
				}
			}
			
			Map newVsOldMap = getExistingCepts(queriedCepts, cepts);
			
			if (queryChildren) {
				for (int k = 0; k < cepts.size(); k++) {
					if (!newVsOldMap.containsKey(cepts.get(k))) {
						queryChildren(((DBConcept) cepts.get(k)), queryChildren, queriedCepts);
					}
				}
			}
			
			if (cepts.size() > 0) {
				Concept c = getExistingOrNewCept(newVsOldMap, (Concept)cepts.get(0));
				if (property instanceof PropertyAtomContainedConcept) {
					((PropertyAtomContainedConcept) property).
						setContainedConcept((ContainedConcept) c);
				} else if (property instanceof PropertyAtomConceptReference) {
					((PropertyAtomConceptReference) property).setConcept(c);
				} else if (property instanceof PropertyArrayContainedConcept) {
					((PropertyArrayContainedConcept) property).clear();
					for (int k = 0; k < cepts.size(); k++) {
						c = getExistingOrNewCept(newVsOldMap, (Concept)cepts.get(k));
						((PropertyArrayContainedConcept) property).add(c);
					}
				} else if (property instanceof PropertyArrayConceptReference) {
					((PropertyArrayConceptReference) property).clear();
					for (int k = 0; k < cepts.size(); k++) {
						c = getExistingOrNewCept(newVsOldMap, (Concept)cepts.get(k));
						((PropertyArrayConceptReference) property).add(c);
					}
				}
			}
		}
	}

	
	static LinkedHashMap getNotNullCols (DBConcept dbConcept) {
		LinkedHashMap colSet = new LinkedHashMap(); 
		
		String[] props = ((AbstractDBConceptImpl)dbConcept).getPropertyNames();

		for (int i=0; i<props.length; i++) {
			String propName = props[i];
			Property prop = dbConcept.getPropertyNullOK(propName);
			if (prop == null || (prop!=null && !(prop instanceof PropertyAtom))) {
				continue;
			}
			colSet.put(propName, prop);
		}
		return colSet;
	}
	
	public static Object [] getNotNullParameterValues (Concept concept) {
		ArrayList paramValuesList = new ArrayList();
		Property [] props = concept.getProperties();

		for (int i=0; i<props.length; i++) {
			if (props[i] == null || (props[i]!=null && !(props[i] instanceof PropertyAtom))) {
				continue;
			}
			PropertyAtom prop = (PropertyAtom)props[i];
			if(prop.isSet())
				paramValuesList.add(prop.getValue());
		}
		return paramValuesList.toArray();
	}

	public static void setPropertiesToConcept(Concept cept, ResultSet rs) throws Exception {
		Property [] props = cept.getProperties();
		for (int i=0; i<props.length; i++) {
			if (props[i] == null || (props[i]!=null && !(props[i] instanceof PropertyAtom))) {
				continue;
			}
			PropertyAtom prop = (PropertyAtom)props[i];
			Object propertyValue = convertToDBPropertyType(rs, rs.findColumn(prop.getName()));
			if(propertyValue == null)
				propertyValue = PropertyNullValues.getNullValue(prop);
			else
				prop.setValue(propertyValue);
		}
	}

	public static Object convertToDBPropertyType(ResultSet rs,int columnIndex) throws Exception {
		ResultSetMetaData rsmd = rs.getMetaData();
		Object columnData = null;
		int sqlType = rsmd.getColumnType(columnIndex);
	    switch(sqlType) {
	    	case Types.BIGINT:	
	    						columnData = rs.getLong(columnIndex);
	    						if(rs.wasNull())
	    							columnData = null;
	    					   	break;
	    	case Types.VARCHAR:
	    	case Types.NVARCHAR:
	    	case Types.NCHAR:
	    	case Types.LONGVARCHAR:
	    	case Types.CHAR:
	    	case Types.BLOB:
	    	case Types.CLOB:
	    	case Types.LONGVARBINARY:
	    	case Types.VARBINARY:	    		
		    						columnData = rs.getString(columnIndex);
			   						break;
	    	case Types.BIT:		
	    	case Types.BOOLEAN:	
	    	case Types.INTEGER:	
	    	case Types.SMALLINT:	
	    	case Types.TINYINT:	
	    	case Types.NUMERIC:	
					    		if (rsmd.getScale(columnIndex) > 0) {
					    			columnData = rs.getDouble(columnIndex);
					    		} else {
					    			int precisionValue = rsmd.getPrecision(columnIndex);
					    			if (precisionValue < INT_PRECISION_LIMIT) {
					    				columnData = (precisionValue == 0) ? rs.getLong(columnIndex) : rs.getInt(columnIndex);
						    		} else {
					    				columnData = rs.getLong(columnIndex);
						    		}
					    		}
								if(rs.wasNull())
									columnData = null;
		   						break;
	    	case Types.DECIMAL:
	    	case Types.DOUBLE:
	    	case Types.FLOAT:
	    	case Types.REAL:
								columnData = rs.getDouble(columnIndex);
								if(rs.wasNull())
									columnData = null;
		   						break;
	    		
	    	case Types.DATE:
	    						if(rs.getDate(columnIndex) != null) {
								columnData = Calendar.getInstance();
								((Calendar)columnData).setTime((java.sql.Date)rs.getDate(columnIndex));
	    						}
								break;
		   						
	    	case Types.TIME:
	    	case Types.TIMESTAMP:
	    						if(rs.getTimestamp(columnIndex) != null) {
									columnData = Calendar.getInstance();
										((Calendar)columnData).setTime((java.sql.Timestamp)rs.getTimestamp(columnIndex));
	    						}
								break;
		   						

	    }
	    return columnData;
	}

	
	static Map getExistingCepts(Map queriedCepts, List cepts) {
		Map newVsOldMap = new HashMap();
		for (int k = 0; k < cepts.size(); k++) {
			DBConcept c = (DBConcept) cepts.get(k);
			String type = ((ConceptImpl)c).getType();
			Map ceptMap = (Map) queriedCepts.get(type);

			StringBuffer keyBuf = new StringBuffer(32);
			for (int i=0; i<c.getPrimaryKeyNames().length; i++) {
				PropertyAtom pa = (PropertyAtom) c.getProperty(c.getPrimaryKeyNames()[i]);
				//query may not include primary key column
				if (pa != null) {
					keyBuf.append(pa.getValue().toString());
					if ((i+1) < c.getPrimaryKeyNames().length) keyBuf.append(":");
				}
			}
			String key = keyBuf.toString();			
			
			if (ceptMap == null) {
				ceptMap = new HashMap();
				queriedCepts.put(type, ceptMap);
				ceptMap.put(key, c);
				continue;
			}
			

			Concept oldCept = (Concept) ceptMap.get(key);
			if (oldCept != null) {
				newVsOldMap.put(c, oldCept);
			} else {
				ceptMap.put(key, c);
			}
			
		}
		return newVsOldMap;
	}
	
	static boolean isPK (DBConcept cept, String propName) {
		for(int i=0; i<cept.getPrimaryKeyNames().length; i++) {
			if (cept.getPrimaryKeyNames()[i].equals(propName)) {
				return true;
			}
		}
		return false;
	}
	static Concept getExistingOrNewCept (Map m, Concept cept) {
		Concept c = (Concept) m.get(cept);
		if (c == null) {
			c = cept;
		}
		return c;
	}
}
