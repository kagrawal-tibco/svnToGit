package com.tibco.cep.modules.db.functions;

import java.io.Writer;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicLong;

import oracle.sql.CLOB;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.modules.db.model.runtime.AbstractDBConceptImpl;
import com.tibco.cep.modules.db.model.runtime.DBConcept;
import com.tibco.cep.modules.db.service.ConstraintRegistry;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArrayConceptReference;
import com.tibco.cep.runtime.model.element.PropertyArrayContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomConceptReference;
import com.tibco.cep.runtime.model.element.PropertyAtomContainedConcept;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;

/*
 * 
 * Update a top level DBConcept
 * 
 */
public class UpdateHelper {
	static com.tibco.cep.kernel.service.logging.Logger logger;
	static boolean versionCheck; 
	static {
		try {
			logger = RuleServiceProviderManager.getInstance().getDefaultProvider().getLogger(UpdateHelper.class);
			
			String strVersionCheck = RuleServiceProviderManager.getInstance().getDefaultProvider().getProperties().
			getProperty("tibco.be.dbconcepts.update.check.version", "false");
			versionCheck = "true".equalsIgnoreCase(strVersionCheck);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public static int update(Concept concept) {
		try {
			List dbConceptUpdateList = new ArrayList();
			getDbConceptList(concept, dbConceptUpdateList);
			Iterator iter  = dbConceptUpdateList.iterator();
			int totUpdts = 0;
			while(iter.hasNext()){
				DBConcept dbc = (DBConcept) iter.next();
				int updtCnt = updateDBCept(dbc);
				totUpdts += updtCnt;
				
			}
			return totUpdts;
		} catch (Exception e) {
			//JDBCHelper.rollback(); --> onus on caller
			throw new RuntimeException(e);
		}
	}

    /**
     * @param concept
     * @return
     */
    private static void getDbConceptList(Concept concept, List updateList) throws Exception {
       
        if (concept == null) {
        	return;
        }
        
        if (concept instanceof DBConcept) {
            updateList.add(concept);
        }

	    Property[] properties = ((ConceptImpl) concept).getPropertiesNullOK();
		for (int i = 0; i < properties.length; i++) {
			Property prop = properties[i];

			if (prop == null || !(prop instanceof Property.PropertyConcept)) {
				continue;
			}

			if (prop instanceof PropertyAtomContainedConcept) {
				getDbConceptList(((PropertyAtomContainedConcept) prop)
						.getContainedConcept(), updateList);
			} else if (prop instanceof PropertyAtomConceptReference) {
				getDbConceptList(((PropertyAtomConceptReference) prop).getConcept(), updateList);
			} else if (prop instanceof PropertyArrayContainedConcept) {
				PropertyArrayContainedConcept pacc = ((PropertyArrayContainedConcept) prop);
				int len = pacc.length();
				for (int j = 0; j < len; j++) {
					PropertyAtomContainedConcept pc = (PropertyAtomContainedConcept) pacc.get(j);
					getDbConceptList(pc.getContainedConcept(), updateList);
				}
			} else if (prop instanceof PropertyArrayConceptReference) { 
				PropertyArrayConceptReference pacr = ((PropertyArrayConceptReference) prop);
				int len = pacr.length();
				for (int j = 0; j < len; j++) {
					PropertyAtomConceptReference pc = (PropertyAtomConceptReference) pacr.get(j);
					getDbConceptList(pc.getConcept(), updateList);
				}
			}
		}
    }

    /**
     * 
     * @param dbConcept
     * @return
     * @throws Exception
     */
	public static int updateDBCept(DBConcept dbConcept) throws Exception {
		int updtCnt = 0;
		Map updtSetColMap = new LinkedHashMap<String, Object>();
		Map updtWhereColMap = new LinkedHashMap<String, Object>();
		getUpdtColMap(dbConcept, updtSetColMap);
		StringBuffer debugBuffer = new StringBuffer(1024);
		if ((updtSetColMap.size()) > 0) {

			StringBuffer updtBuf = setUpdtProps(dbConcept, updtSetColMap,
					debugBuffer);

			if (dbConcept.getPrimaryKeyMap().size() > 0) {
				// append where clause
				updtBuf.append(" where ");
				debugBuffer.append(" where ");

				// This modifies the update list (adds PKs at end of the list)
				setPrimKeys(dbConcept, updtWhereColMap, debugBuffer, updtBuf);
			} else {
				throw new Exception(
						"Primary keys unavailable for concept "
								+ dbConcept.getExpandedName().getLocalName()
								+ ". Update operation failed.");
			}
			
			String jdbcResName = ((AbstractDBConceptImpl)dbConcept).getJDBCResourceName();

			// sets the params on prepared statement and executes the update
			updtCnt = issueUpdate(jdbcResName, updtSetColMap, updtWhereColMap, debugBuffer, updtBuf);

		}
		return updtCnt;
	}
	
	public static int execUpdate(DBConcept model, DBConcept dbConcept) throws RuntimeException {
		int updtCnt = 0;
		Map updtSetColMap = new LinkedHashMap<String, Object>();
		Map updtWhereColMap = new LinkedHashMap<String, Object>();
		getUpdtColMap(dbConcept, updtSetColMap);
		
		if (versionCheck){
			//Add version column and value to update
			setVersionColumn(model, dbConcept, updtSetColMap);
		}

		StringBuffer debugBuffer = new StringBuffer(1024);
		if ((updtSetColMap.size()) > 0) {

			StringBuffer updtBuf;
            try {
                updtBuf = setUpdtProps(dbConcept, updtSetColMap, debugBuffer);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

			if (model.getPrimaryKeyMap().size() > 0) {
				// append where clause
				updtBuf.append(" where ");
				debugBuffer.append(" where ");

				// This modifies the update list (adds PKs at end of the list)
				setPrimKeys(model, updtWhereColMap, debugBuffer, updtBuf);
				if(versionCheck){
					addVersionCondition(model, updtWhereColMap, debugBuffer, updtBuf);
				}
			} else {
				throw new RuntimeException(
						"Primary keys unavailable for concept "
								+ model.getExpandedName().getLocalName()
								+ ". Update operation failed.");
			}

			String jdbcResName = ((AbstractDBConceptImpl)model).getJDBCResourceName();
			
			try {
	            // sets the params on prepared statement and executes the update
                updtCnt = issueUpdate(jdbcResName, updtSetColMap, updtWhereColMap, debugBuffer, updtBuf);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
		}
		return updtCnt;
	}
	
	/*
	 * Add version column name to set clause
	 */
	private static void setVersionColumn(DBConcept model, DBConcept dbConcept, Map updtSetColMap) throws RuntimeException {
		if (!versionCheck){
			return;
		}
		try {
    		String verPropName = model.getVersionPropertyName();
    		if (verPropName != null && !"".equals(verPropName)){
    			String verPolicy   = model.getVersionPolicy();
    			String verColName = FunctionHelper.getColForProp(((AbstractDBConceptImpl)model).getPropertyToColMap(), verPropName);
    			if (verColName != null) {
    				Long nextValue = null;
    				if (verPolicy == null || "".equalsIgnoreCase(verPolicy) || "AUTO".equalsIgnoreCase(verPolicy)) {
    					long currentValue = Long.valueOf(getPropertyValue(model, verPropName).toString());
    					AtomicLong temp = new AtomicLong(currentValue);
    					nextValue = temp.incrementAndGet();
    				} else {
    					ConstraintRegistry.UniqueIdentifier uid = ConstraintRegistry.getInstance().getUniqueIdentifier(model, verPropName);
    					if(uid == null){
    						throw new NullPointerException("Sequence information missing for concept " 
    								+ model.getExpandedName().getLocalName() 
    								+ " and property " + verPropName);
    					}
    					String seqName = uid.getUniqueIdentifierName();
    					nextValue = FunctionHelper.getSequenceValue(seqName, true);
    				}
    				dbConcept.getPropertyAtom(verPropName).setValue(nextValue);
    				updtSetColMap.put(verPropName, nextValue);
    			}
    		}
		} catch (Exception e) {
		    throw new RuntimeException(e);
		}
	}
	
	/*
	 * add version column name to where clause
	 */
	private static void addVersionCondition(DBConcept dbConcept, Map updtWhereColMap, StringBuffer debugBuffer, StringBuffer updtBuf){
		if(!versionCheck){
			return;
		}
		String verPropName = dbConcept.getVersionPropertyName();
		String verColName = FunctionHelper.getColForProp(((AbstractDBConceptImpl)dbConcept).getPropertyToColMap(), verPropName);
		Object verValue     = getPropertyValue(dbConcept, verPropName);
		
		updtBuf.append(" and ");
		updtBuf.append(verColName).append("=?");
		
		debugBuffer.append(" and ");
		debugBuffer.append(verColName).append("=");
		debugBuffer.append(verValue==null? "null" : verValue.toString());
	
		updtWhereColMap.put(verColName, verValue);
	}
	
	/*
	 * This function add the primary key columns to where clause 
	 * and modifies the update list meaning adds PKs at end of the update column list
	 */
	private static void setPrimKeys(DBConcept dbConcept, Map updtWhereColMap, StringBuffer debugBuffer, StringBuffer updtBuf) {
		int i = 0;
		for (Iterator j = dbConcept.getPrimaryKeyMap().entrySet()
				.iterator(); j.hasNext(); i++) {
			Map.Entry entry = (Entry) j.next();
			String pkName = ((String) entry.getKey()).trim();
			String colName = FunctionHelper.getColForProp(((AbstractDBConceptImpl)dbConcept).getPropertyToColMap(), pkName);
			updtBuf.append(colName).append("=?");
			
			Object pkVal = getPropertyValue(dbConcept, pkName);	
			
			debugBuffer.append(colName).append("=");
			debugBuffer.append(pkVal==null? "null" : pkVal.toString());

			if (i < dbConcept.getPrimaryKeyMap().size() - 1) {
				updtBuf.append(" and ");
				debugBuffer.append(" and ");
			}
		
			//TODO: IMPORTANT !! Add the PK to the updtList so that prepared params can be set in one shot
			updtWhereColMap.put(pkName, pkVal);
		}
	}
	
	/*
	 * This function returns a prepared statement with the given list of columns
	 */
	private static StringBuffer setUpdtProps(DBConcept dbConcept, Map updtColMap, StringBuffer debugBuffer) throws Exception {
		StringBuffer updtBuf = new StringBuffer(512);

		com.tibco.cep.designtime.model.element.Concept dtCept = FunctionHelper.getDTConceptFromRT(dbConcept);
		String tableName = FunctionHelper.getDelimitedFQDBObjectName(dtCept);

		updtBuf.append("update ").append(tableName).append(" set ");
		debugBuffer.append(updtBuf);
		
		Iterator iter = updtColMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iter.next();
			String propName = entry.getKey();
			Object propValue= entry.getValue();
			String colName = FunctionHelper.getColForProp(((AbstractDBConceptImpl)dbConcept).getPropertyToColMap(), propName);
			String dataType = dbConcept.getDBColumnDataType(propName);
			if(colName != null) {
				if (dataType!= null && dataType.equalsIgnoreCase("xmltype")){
					updtBuf.append(colName).append("=(XMLType(?))");
					if (propValue instanceof Clob) {
						Clob clob = (Clob) propValue;
						// these 3 only for debug purposes.
						debugBuffer.append(colName).append("=");
						debugBuffer.append(clob);
					}
				}
				else{
					updtBuf.append(colName).append("=?");
					//these 3 only for debug purposes.
					debugBuffer.append(colName).append("=");
					String value = propValue == null? "null" : propValue.toString();
					debugBuffer.append(value);
				}

				//if (iter.hasNext()) {
					updtBuf.append(",");
					debugBuffer.append(",");
				//}
			}
		}
		if(updtBuf.toString().endsWith(",")) {
			updtBuf = new StringBuffer(updtBuf.substring(0,updtBuf.lastIndexOf(",")));
		}
		if(debugBuffer.toString().endsWith(",")) {
			debugBuffer = new StringBuffer(debugBuffer.substring(0,debugBuffer.lastIndexOf(","))); 
		}
		return updtBuf;
	}
	
	
    protected static Clob getCLOB(String value, Connection conn)
			throws SQLException {
		if (value == null || value.trim().length() == 0)
			return null;
		CLOB tempClob = null;
		Writer tempClobWriter = null;
		try {
			// If the temporary CLOB has not yet been created, create new
			tempClob = CLOB.createTemporary(conn, true, CLOB.DURATION_SESSION);

			// Open the temporary CLOB in readwrite mode to enable writing
			tempClob.open(CLOB.MODE_READWRITE);
			// Get the output stream to write
			tempClobWriter = tempClob.getCharacterOutputStream();
			// Write the data into the temporary CLOB
			tempClobWriter.write(value);

			// Flush and close the stream
			tempClobWriter.flush();
			tempClobWriter.close();
		} catch (SQLException sqlexp) {
			if (tempClob != null)
				tempClob.freeTemporary();
			if (logger != null)
				logger.log(Level.ERROR, sqlexp,"Error while writing XMLType to DB");
		} catch (Exception exp) {
			if (tempClob != null)
				tempClob.freeTemporary();
			if (logger != null)
				logger.log(Level.ERROR, exp,"Error while writing XMLType to DB");
		} finally {
			if (tempClob != null)
				tempClob.close();
		}
		return tempClob;
	}

	/*
	 * Get list of column names to be updated. This list includes non pk and not null columns only.
	 */
	private static void getUpdtColMap(DBConcept dbConcept, Map updtColMap) throws RuntimeException {
		String props[] = ((AbstractDBConceptImpl)dbConcept).getPropertyNames();
		for (int i=0; i<props.length; i++) {
			String propName = props[i];
			String colName = FunctionHelper.getColForProp(((AbstractDBConceptImpl)dbConcept).getPropertyToColMap(), propName);
			if(colName != null) {
				Property prop = dbConcept.getPropertyNullOK(propName);
				
				if (prop == null || (prop != null && !(prop instanceof PropertyAtom))) {
					continue;
				}
	
				if (dbConcept.getPrimaryKeyMap().containsKey(propName)) {
					continue;
				}
				String dataType = dbConcept.getDBColumnDataType(propName);
				Object value = getPropertyValue(dbConcept, propName);
				if (dataType!= null && dataType.equalsIgnoreCase("xmltype")){
					String jdbcResName = ((AbstractDBConceptImpl)dbConcept).getJDBCResourceName();
					Connection currentConnection = JDBCHelper.getCurrentConnection(jdbcResName);
					Clob clob;
					try {
						clob = getCLOB((String)value, currentConnection);
					} catch (Exception e) {
						clob = null;
					}
					if (clob != null) {
						updtColMap.put(propName, clob);
					}
				} else {
					updtColMap.put(propName, value);
				}
			}
		}
	}

	/*
	 * sets the params on prepared statement and executes the update
	 */
	private static int issueUpdate(String jdbcResName, Map updtSetColMap, Map updtWhereColMap, StringBuffer debugBuffer, StringBuffer updtBuf) throws Exception {
		PreparedStatement updtStmt = null;
		try {
			updtStmt = JDBCHelper
					.getCurrentConnection(jdbcResName).prepareStatement(updtBuf.toString());
			
			setPreparedStmtParams(updtStmt, updtSetColMap, updtWhereColMap);
			
			logger.log(Level.DEBUG, "SQL: %s", debugBuffer);

			updtStmt.execute();
			int updateCnt = updtStmt.getUpdateCount();
			return updateCnt;
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		} finally {
			//fix for BE-11639 - for oracle-db 11g, isClosed is Abstract, but for sybase check is necessary
			try {
				//BE-9882 : statement's close() executed twice , causing problem with Sybase Database.
				if (updtStmt != null && !updtStmt.isClosed()) {
					updtStmt.close();
				}
			}catch (final AbstractMethodError e) {
				//ignore, rest of the exceptions should be thrown
			}
		}
	}
	
	/*
	 * for a column name in update list, get the value for property in the dbConcept
	 * and set this value in the prepared statement 
	 */
	private static void setPreparedStmtParams(PreparedStatement updtStmt, Map updtColMap, Map updtWhereColMap) throws Exception {
		int k=1;
		Iterator iter1 = updtColMap.values().iterator();
		while (iter1.hasNext()) {
			Object val = (Object) iter1.next();
			updtStmt.setObject(k++, val);
		}
		
		Iterator iter2 = updtWhereColMap.values().iterator();
		while (iter2.hasNext()) {
			Object val = (Object) iter2.next();
			updtStmt.setObject(k++, val);
		}
	}
	
	/*
	 * Given a column name return the PropertyAtom value attached to it
	 */
	private static Object getPropertyValue(DBConcept dbConcept, String propName) {
		PropertyAtom pa = dbConcept.getPropertyAtom(propName);
		Object val = FunctionHelper.getPropertyValNullOK(pa);
		if (val != null && val instanceof Calendar) {
			val = new java.sql.Timestamp(((Calendar)val).getTimeInMillis());
		}
		return val;
	}
}
