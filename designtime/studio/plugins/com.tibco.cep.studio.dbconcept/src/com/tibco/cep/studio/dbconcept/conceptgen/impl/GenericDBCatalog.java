package com.tibco.cep.studio.dbconcept.conceptgen.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.tibco.cep.studio.dbconcept.conceptgen.BaseEntity;
import com.tibco.cep.studio.dbconcept.conceptgen.BaseRelationship;
import com.tibco.cep.studio.dbconcept.conceptgen.DBDataSource;
import com.tibco.cep.studio.dbconcept.conceptgen.DBEntity;
import com.tibco.cep.studio.dbconcept.conceptgen.DBEntityCatalog;
import com.tibco.cep.studio.dbconcept.conceptgen.RelationshipKey;

/**
 * 
 * Implementation of DBEntityCatalog using JDBC Metadata API's
 * The purpose of this class is to query the database catalog and populate it into DB Concept model
 * The implementation details:
 * This class uses a catalogHelper for db specific logic.
 * Execution Flow:
 * 1.Gets the list of schema names based on the schema owner, uses DB specific method CatalogHelper.getSchemas()
 * 2.Gets all the tables and columns for each schema and populates DBEnities with or without relationship based on the generateRel flag
 */
public class GenericDBCatalog extends DBEntityCatalog {
	
	
	
	protected CatalogHelper catalogHelper;
	protected DatabaseMetaData dsMetadata;
	
	private boolean checkPrivs = true;
	/**
	 * Constructs a Generic DB Catalog with DB Specific CatalogHelper 
	 * @param ds 
	 * @param catalogHelper
	 */
	public GenericDBCatalog(DBDataSource ds,CatalogHelper catalogHelper) { 
		super(ds);
		this.catalogHelper = catalogHelper;
		checkPrivs = System.getProperty("be.dbconcepts.dbimport.check.privileges", "true").equals("true");
	}

	/**
	 * Builds the catalog with generate Relationship flag 
	 */
	public void buildCatalog(boolean generateRel, String userSQLQuery, IProgressMonitor monitor) throws SQLException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		try {
			if (this.getConnection() == null) {
				throw new RuntimeException(new Exception(
				"Unable to retrieve database connection."));
			}
			dsMetadata = conn.getMetaData();
			if (userSQLQuery == null) {
				getDBSourceEntities(generateRel,monitor);
			} else {
				getDBSourceEntitiesWithUserSQLQuery(generateRel,userSQLQuery);
			}

		}
		finally {
			this.putConnection();
			releaseConnManager();
		}
		monitor.done(); // finish the sub progress monitor
	}
	
	
	/**
	 * Add DB column properties to the DB Entity 
	 * @param dbe
	 * @param colRslt
	 * @throws SQLException
	 */
	private void addPropertiesToDBEnity(DBEntityImpl dbe,ResultSet colRslt) throws SQLException {
		String tableSchema = colRslt.getString("TABLE_SCHEM");
		String tableName = colRslt.getString("TABLE_NAME");
		if( tableSchema != null && !tableSchema.equals(dbe.getSchema()))
			return;
		if(tableName != null && !tableName.equals(dbe.getObjName()))
			return;
		String colName = colRslt.getString("COLUMN_NAME");
		String dataType = colRslt.getString("TYPE_NAME");
		int dataLength = colRslt.getInt("COLUMN_SIZE");
		int dataPrecision = colRslt.getInt("COLUMN_SIZE");// Precision is maximum number of digits (Including digits to the right and left of decimal).
		int dataScale = colRslt.getInt("DECIMAL_DIGITS"); // Scale is the number of digits to the right of decimal.
		String string = (String)colRslt.getString("COLUMN_DEF");
		boolean isNullable = colRslt.getString("IS_NULLABLE").equals("NO") ? false : true;
		DBPropertyImpl col = catalogHelper.createDBProperty(tableName,colName, dataType, dataLength,
				dataPrecision, dataScale, isNullable);
		col.setAlias(colName);
		col.setDefaultValue(string);
		dbe.addProperty(col);
		
	}
	
	/**
	 * Read the catalog information and return the map of all DB Entities
	 * @param generateRel -- flag which indicates whether referential integrities should be considered, true indicates all referential relationships are read 
	 * @return The map of all DB Entities
	 * @throws SQLException
	 */
	
	List<?> executeUserSQLQuery(Connection conn, String userQuery)
			throws SQLException {

		List<Object> tableList = new ArrayList<Object>();
		Statement stmt = null;
		ResultSet rs = null;
		stmt = conn.createStatement();
		rs = stmt.executeQuery(userQuery);
		while (rs.next()) {
			tableList.add(new Object[] { rs.getObject(1), rs.getObject(2), DBEntity.TABLE });
		}
		rs.close();
		stmt.close();
		return tableList;
	}
	
	private Map<String, DBEntity> getEntitiesForSchemas(String schemaName, String tableName, int objectType) throws SQLException {

		DBSchemaImpl dbs = null;
		String dbName = dsMetadata.getDatabaseProductName();
		String dsName = ds.getName();
		String dbVersion = dsMetadata.getDatabaseProductVersion();

		DBEntityImpl dbe = new DBEntityImpl(tableName, catalogHelper
				.getNamespacePrefix(dsName, schemaName), tableName, objectType,
				schemaName);
		dbe.setAliasName(tableName);
		dbe.setJDBCResourceURI(ds.getJdbcResourceURI());
		dbe.setType(BaseEntity.CONCEPT);
		dbe.setDBName(dbName);
		dbe.setDBVersion(dbVersion);
		dbs = (DBSchemaImpl) dbSchemas.get(schemaName);
		if (dbs == null) {
			dbs = new DBSchemaImpl(schemaName, schemaName);
			dbSchemas.put(schemaName, dbs);
		}
		dbs.addEntity(dbe);

		// Maps entity full name to entity
		Map<String, DBEntity> dbSourceEntities = new HashMap<String, DBEntity>();

		ResultSet colRslt = dsMetadata.getColumns(null, schemaName, quoteName(tableName), null);
		while (colRslt.next()) { // loop over object columns
			addPropertiesToDBEnity(dbe, colRslt);
		}

		colRslt.close();
		buildPKRelations(dbe);

		dbs = (DBSchemaImpl) dbSchemas.get(schemaName);
		if (dbs == null) {
			dbs = new DBSchemaImpl(schemaName, schemaName);
			dbSchemas.put(schemaName, dbs);
		}

		dbSourceEntities.put(dbe.getFullName(), dbe);
		return dbSourceEntities;
	}

	private String quoteName(String tableName) {
		String quotedTable = tableName;
		if (dbImportUseQuotes) {
			quotedTable = "\"" + tableName + "\"";
		}
		return quotedTable;
	}
	
	private Map<?, ?> getDBSourceEntitiesWithUserSQLQuery(boolean generateRel,String userSQLQuery) throws SQLException {
		List<?> tableList = executeUserSQLQuery(conn, userSQLQuery);
		return buildRelns(generateRel, tableList, new NullProgressMonitor());
	}

	private class RunningAverage {
		long startTime;
		int totalSteps;
		
		public void start() {
			startTime = System.currentTimeMillis();
		}
		
		public String getTimeRemaining(int currentStep) {
			long timeTaken = System.currentTimeMillis() - startTime;
			double ave = timeTaken/currentStep;
			int seconds = (int) ((ave * totalSteps - timeTaken) / 1000);
			if (seconds > 120) {
				return "[approximate time remaining for this step: "+ seconds/60 +" minutes";
			}
			
			return "[approximate time remaining for this step: "+ seconds +" seconds";
		}
		
	}
	
	private Map<String, DBEntity> buildRelns(boolean generateRel, List<?> tableList, IProgressMonitor monitor) throws SQLException {
		Map<String, DBEntity> m = new HashMap<String, DBEntity>();
		monitor.setTaskName("Processing tables ");
		RunningAverage av = new RunningAverage();
		av.totalSteps = tableList.size();
		av.start();
		monitor.beginTask("", tableList.size() * 2); // just estimate that the total work will be the tableList size * 2
		int tableListSize = tableList.size();
		for (int i = 0; i < tableListSize; i++) {
			if (monitor.isCanceled()) {
				throw new RuntimeException(new InterruptedException("DB Import canceled"));
			}
			String schemaNm = (String) ((Object[]) tableList.get(i))[0];
			String tableName = (String) ((Object[]) tableList.get(i))[1];
			int objectType = (Integer) ((Object[]) tableList.get(i))[2];
			StringBuilder b = new StringBuilder("table ").append(i+1).append(" of ").append(tableListSize).append(" ").append(av.getTimeRemaining(i+1));
			b.append(", table name: ").append(tableName).append(']');
			monitor.subTask(b.toString());
			m.putAll(getEntitiesForSchemas(schemaNm, tableName, objectType));
			monitor.worked(1);
		}
		entities.putAll(m);
		if (generateRel) {
			int i = 0;
			int totalSize = m.size();
			monitor.setTaskName("Building table relationships");
			av.totalSteps = totalSize;
			av.start();
			Iterator<DBEntity> iter = m.values().iterator();
			while (iter.hasNext()) {
				if (monitor.isCanceled()) {
					throw new RuntimeException(new InterruptedException("DB Import canceled"));
				}
				DBEntityImpl dbe = (DBEntityImpl) iter.next();
				StringBuilder b = new StringBuilder("table ").append(i+1).append(" of ").append(tableListSize).append(" ").append(av.getTimeRemaining(i+1));
				b.append(", table name: ").append(dbe.getName()).append(']');
				monitor.subTask(b.toString());
				buildFKRelations(dbe);
				i++;
				monitor.worked(1);
			}
		}
		return m;
	} 
	
	private Map<?, ?> getDBSourceEntities(boolean generateRel, IProgressMonitor monitor) throws SQLException {

		String schemaOwner = null;
		if (ds.getSchemaOwner() != null
				&& !"".equals(ds.getSchemaOwner().trim())) {
			schemaOwner = ds.getSchemaOwner().trim();
		}
		String[] schemaNames = null;

		monitor.setTaskName("Getting schema names");
		schemaNames = catalogHelper.getSchemas(conn, schemaOwner, ds.getName());
		
		List<Object> tableNames = new ArrayList<Object>(); 
		monitor.setTaskName("Getting tables for schemas");
		for (int i = 0; i < schemaNames.length; i++) {
			List<?> l1 = getTablesForSchemas(schemaNames[i], monitor);
			tableNames.addAll(l1);
		}
		
		return buildRelns(generateRel, tableNames, monitor);
	}
	
	//stores the names Tables from RECYCLEBIN 
	//for Oracle Database 10g 
	HashSet<String> recycleObjects = new HashSet<String>(10);
	
	/**
	 * this method will identify the RECYCLEBIN table and returns true or false
	 * valid only for Oracle Database 10g , 
	 * should not be called for other database...
	 * 
	 * @param con
	 * @param objectName
	 * @return
	 */
  
	private boolean isRecycleBinTable(Connection con, String objectName) {
		if (recycleObjects.contains(objectName)) {
			return false;
		}

		return true;
	}
	
	private void getRecycleBinTable(Connection con) {
		final String query = "SELECT object_name FROM recyclebin";
		try {
			Statement sta = con.createStatement();
			ResultSet res = sta.executeQuery(query);
			while (res.next()) {
				String systemGenratedName = res.getString("object_name");
				recycleObjects.add(systemGenratedName);
			}
			res.close();
			sta.close();
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		}
	}
	
	private List<?> getTablesForSchemas(String schemaName, IProgressMonitor monitor) throws SQLException {
		// Maps entity full name to entity
		List<Object> tableList = new ArrayList<Object>();

		ResultSet objRslt = dsMetadata.getTables(null,schemaName,null,new String[]{"TABLE", "VIEW"});
		String dbName = dsMetadata.getDatabaseProductName();
		if ( dbName.toUpperCase().compareTo("ORACLE") == 0 ) {
			getRecycleBinTable(this.getConnection()); // only execute this once
		}

		while (objRslt.next()) { // loop over list of tables/views
			
			String tableType = objRslt.getString("TABLE_TYPE");
			int objectType;
			if(tableType.equals("TABLE") )
				objectType = DBEntity.TABLE;
			else if(tableType.equals("VIEW"))
				objectType = DBEntity.VIEW;
			else
				continue; // query only tables or views
			String objectName = objRslt.getString("TABLE_NAME");
			monitor.subTask(objectName);
			/*
			 * Need to be performed only for oracle database.
			 */
			if ( dbName.toUpperCase().compareTo("ORACLE") == 0 ) {
				if ( isRecycleBinTable(this.getConnection(), objectName) == false ) {
					continue;
				}
			}
			
			String tableschemaName = objRslt.getString("TABLE_SCHEM");
			if(schemaName != null && tableschemaName != null && !tableschemaName.trim().toUpperCase().equals(schemaName.toUpperCase()))
				continue;
			if(catalogHelper.isSystemObject(objectName))
				continue;
			
			Object [] threeTuple = {schemaName, objectName, objectType};
			
			tableList.add(threeTuple);
		}
		objRslt.close();
		return tableList;
	}
	

	private void buildFKRelations(DBEntityImpl dbe) throws SQLException {

		if(!checkPrivileges(dbe)) {
			return;
		}
		
		ResultSet conRslt = dsMetadata.getImportedKeys(null, dbe.getSchema(), quoteName(dbe.getObjName()));
		// get list of constraints for the table
		BaseRelationshipImpl br = null;
		Set<String> contraintNameList = new HashSet<String>();
		HashMap<?, ?> aliasToTableMapping = catalogHelper.getAliasToTableMapping(conn);
		while (conRslt.next()) { // loop over constraints
			String currConstraintName = conRslt
			.getString("FK_NAME");
			if(!contraintNameList.contains(currConstraintName)) {
				String pkTableName = conRslt.getString("PKTABLE_NAME");
				String pkTableSchema = conRslt.getString("PKTABLE_SCHEM");
				if(pkTableSchema == null || pkTableSchema.trim().length() == 0)
					pkTableSchema = ds.getName(); //For Db's where schema_name is database_name the table schema returned is null, so need to set it here
				br = new BaseRelationshipImpl();
				if(aliasToTableMapping != null) {
					String base_tablename = (String)aliasToTableMapping.get(pkTableSchema+"."+pkTableName);
					if(base_tablename != null) {
						pkTableSchema = base_tablename.substring(0, base_tablename.indexOf('.'));
						pkTableName = base_tablename.substring(base_tablename.indexOf('.')+1,base_tablename.length());
					}
				}
				br.setName(pkTableName);
				br.setCardinality(0);
				br.setRelationshipEnum(BaseRelationship.REFERENCE);
				String childEntityName = dbe.getNamespace() + pkTableName;
				BaseEntity childEntity = (BaseEntity) entities
						.get(childEntityName);
				if (childEntityName != null) {
					br.setChildEntity(childEntity);
				}
				br.setChildEntityName(childEntityName); 
				dbe.addRelationship(br);
				contraintNameList.add(currConstraintName); 
			}
			RelationshipKey key = new RelationshipKeyImpl(conRslt.getString("FKCOLUMN_NAME"), conRslt.getString("PKCOLUMN_NAME"), false);
			br.addRelationshipKey(key);
			
		}
		conRslt.close();
		swapIfContainment(dbe);
	}
	
	
	/**
	 * Builds the primary key relationships for the entity
	 * @param dbe
	 * @throws SQLException
	 */
	private void buildPKRelations(DBEntityImpl dbe) throws SQLException{
		DBConstraint pkRelation = null;
		
		if(checkPrivileges(dbe)==false) {
			return;
		}
		
		ResultSet conRslt = dsMetadata.getPrimaryKeys(null, dbe.getSchema(),quoteName(dbe.getObjName()));
		// get list of constraints for the table
		DBConstraint con = new DBConstraint(
				null, "P");
		while (conRslt.next()) { // loop over constraints
			String constraintName = conRslt
			.getString("PK_NAME");
			con.setConstraintName(constraintName);
			// get list of columns for the constraint
			con.addColumn(conRslt.getString("COLUMN_NAME"));
			((DBPropertyImpl)(dbe.getProperty(conRslt.getString("COLUMN_NAME")))).setPK(true);
					pkRelation = con; // should be only one primary key per table
		}
		conRslt.close();
		if(pkRelation != null)
			dbe.setPkRelation(pkRelation);
	}
	
	/**
	 * 
	 * @param dbe
	 * @return
	 */
	private boolean checkPrivileges(DBEntityImpl dbe) throws SQLException {
		
		if (!checkPrivs) {
			return true;
		}
		boolean bPrivilege = true;
		
		ResultSet privilagesRslt = dsMetadata.getTablePrivileges(null, dbe.getSchema(), quoteName(dbe.getObjName()));
		
		while (privilagesRslt.next()) {
			if (privilagesRslt.getString("PRIVILEGE").trim().isEmpty()) {
				bPrivilege = false;
				break;
			}
		}
		privilagesRslt.close();
		return bPrivilege;
	}
	
	/**
	 * Swap the foreign key relationship
	 * If the foreign key is a subset of primary key of the child table, then it is swapped and made a 'C' relation in the primary key table
	 * @param dbe
	 */
	private void swapIfContainment(DBEntityImpl dbe) {
		Map<String, BaseRelationship> relMap = dbe.getRelations();
		for (Iterator<Entry<String, BaseRelationship>> i=relMap.entrySet().iterator(); i.hasNext();) {
			 Entry<String, BaseRelationship> e = i.next();
			BaseRelationshipImpl relImpl = (BaseRelationshipImpl) e.getValue();
			if ( relImpl.relEnum == BaseRelationship.CONTAINMENT ) {
				//already relation is established between entities
				continue;
			}
			//childSide Keys
			Set<String> childKeys = new HashSet<String>();
			
			Set<String> myKeys = new HashSet<String>();
			for (Iterator<RelationshipKey> j=relImpl.getRelationshipKeySet().iterator(); j.hasNext();) {
				RelationshipKey rkeys = (RelationshipKey) j.next();
				childKeys.add(rkeys.getChildKey());
				myKeys.add(rkeys.getParentKey());
			}
			BaseEntityImpl be = (BaseEntityImpl)relImpl.getChildEntity();
			if (be == dbe) {
				continue;
			}
			Set<?> childPks = new HashSet<Object>();
			if (be instanceof DBEntity) {
				DBEntity ce = (DBEntity)be;
				childPks = ce.getPK();
			}
			if (dbe.getPkRelation() != null && childPks.containsAll(childKeys) && childKeys.containsAll(childPks)  && dbe.getPK().containsAll(myKeys)) {
				//swap the relationship
				
				BaseRelationshipImpl newRel = new BaseRelationshipImpl();
				newRel.cardinality = 0;
				newRel.childEntityName = dbe.getFullName();
				newRel.childEntity = dbe;
				newRel.relationshipName = dbe.getName();
				newRel.relEnum = BaseRelationship.CONTAINMENT;
				
				
				for (Iterator<RelationshipKey> j=relImpl.getRelationshipKeySet().iterator(); j.hasNext();) {
					RelationshipKey rkeys = (RelationshipKey) j.next();
					String childKey = rkeys.getChildKey();
					String parentKey = rkeys.getParentKey();
					
					RelationshipKey newKey = new RelationshipKeyImpl(childKey, parentKey, false);
					newRel.addRelationshipKey(newKey);
				}
				i.remove();
				be.addRelationship(newRel);
			}
		}
	}
}
