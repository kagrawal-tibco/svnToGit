package com.tibco.cep.studio.dbconcept.conceptgen.impl;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.cep.studio.dbconcept.conceptgen.BaseEntity;
import com.tibco.cep.studio.dbconcept.conceptgen.BaseRelationship;
import com.tibco.cep.studio.dbconcept.conceptgen.DBDataSource;
import com.tibco.cep.studio.dbconcept.conceptgen.DBEntity;
import com.tibco.cep.studio.dbconcept.conceptgen.DBEntityCatalog;

/**
 * 
 * @author schelwa
 * 
 * Implementation of DBEntityCatalog specific to ORACLE
 * Queries specific to ORACLE to read metadata and
 * generate the catalog
 *
 */
public class OracleCatalog extends DBEntityCatalog {

	private final String SQL_USER_TABLES = "select user OBJ_SCHEMA, table_name OBJ_NAME, 'T' OBJ_TYPE "
		+ "  from user_tables " + " where 1 = 1 ";
	
	private final String SQL_USER_TABLES_BY_OWNER = "select user OBJ_SCHEMA, table_name OBJ_NAME, 'T' OBJ_TYPE "
		+ "  from all_tables " + " where 1 = 1 and owner = ? ";

	private final String SQL_USER_VIEWS = "select user OBJ_SCHEMA, view_name OBJ_NAME, 'V' OBJ_TYPE "
		+ "  from user_views " + " where 1 = 1 ";
	
	private final String SQL_USER_VIEWS_BY_OWNER = "select user OBJ_SCHEMA, view_name OBJ_NAME, 'V' OBJ_TYPE "
		+ "  from all_views " + " where 1 = 1 and owner = ? ";

	private final String SQL_USER_TAB_COLUMNS = "select table_name OBJ_NAME," +
			" column_name COL_NAME, data_type DATATYPE, data_length LENGTH," +
			" data_precision PRECISION,data_scale SCALE,nullable NULLABLE,column_id COLUMN_ID,data_default DATA_DEFAULT "
		+ "  from all_tab_columns " + " where 1 = 1 ";

	private final String SQL_USER_CONSTRAINTS = "select owner, constraint_name, constraint_type, table_name, r_owner, r_constraint_name "
		+ "  from all_constraints " + " where 1 = 1 ";

	private final String SQL_USER_CONS_COLUMNS = "select owner, constraint_name, table_name, column_name, position "
		+ "  from all_cons_columns " + " where 1 = 1 ";

	private static final String OBJ_NAME = "OBJ_NAME";
	private static final String OBJ_SCHEMA = "OBJ_SCHEMA";
	private static final String OBJ_TYPE = "OBJ_TYPE";
	
	private static final String COL_NAME = "COL_NAME";
	private static final String DATATYPE = "DATATYPE";
	private static final String LENGTH = "LENGTH";
	private static final String PRECISION = "PRECISION";
	private static final String SCALE = "SCALE";
	private static final String NULLABLE = "NULLABLE";
	private static final String DATA_DEFAULT = "DATA_DEFAULT";
	
	private static final String CONSTRAINT_NAME = "CONSTRAINT_NAME";
	private static final String CONSTRAINT_TYPE = "CONSTRAINT_TYPE";
	private static final String R_CONSTRAINT_NAME = "R_CONSTRAINT_NAME";
	private static final String R_OWNER = "R_OWNER";
	private static final String COLUMN_NAME = "COLUMN_NAME";
	
	public OracleCatalog(DBDataSource ds) {
		super(ds);
	}

	// *****************************************************************
	// Return database specific query strings.
	// *****************************************************************         
	protected String getTableListQueryString() {
		if(ds.getSchemaOwner() == null) {
			return SQL_USER_TABLES + " union " + SQL_USER_VIEWS
			+ " order by OBJ_SCHEMA, OBJ_NAME, OBJ_TYPE";
		} else {
			return SQL_USER_TABLES_BY_OWNER + " union " + SQL_USER_VIEWS_BY_OWNER
			+ " order by OBJ_SCHEMA, OBJ_NAME, OBJ_TYPE";
		}
	}

	protected String getTableColumnQueryString() {
		if(ds.getSchemaOwner() == null) {
			return SQL_USER_TAB_COLUMNS
			+ " and table_name = ? order by COLUMN_ID";
			
		} else {
			return SQL_USER_TAB_COLUMNS
			+ " and owner = ? and table_name = ? order by COLUMN_ID";
		}
	}

	protected String getConstraintListQueryString() {
		if(ds.getSchemaOwner() == null) {
			return SQL_USER_CONSTRAINTS
			+ " and table_name = ? order by CONSTRAINT_NAME";			
		} else {
			return SQL_USER_CONSTRAINTS
			+ " and owner = ? and table_name = ? order by CONSTRAINT_NAME";			
		}

	}

	protected String getConstraintColumnQueryString() {
		if(ds.getSchemaOwner() == null) {
			return SQL_USER_CONS_COLUMNS
			+ " and constraint_name = ? order by POSITION";			
		} else {
			return SQL_USER_CONS_COLUMNS
			+ " and owner = ? and constraint_name = ? order by POSITION";			
		}
	}
	

	public void buildCatalog(boolean generateRel, String userQuery, IProgressMonitor monitor) throws SQLException {

		try {
			if (this.getConnection() == null) {
				throw new RuntimeException(new Exception(
				"Unable to retrieve database connection."));
			}

			Map<String, DBEntityImpl> dbSourceEntities = getDBSourceEntities(generateRel);
			this.entities.putAll(dbSourceEntities);
			
		}/* catch (Exception ex) {

			ExceptionOutput
			.outputStackTrace(ex, "OracleCatalog:buildCatalog()");
			throw new RuntimeException(ex);
		}*/ finally {
			this.putConnection();
			releaseConnManager();
		}
	}
	
	private Map<String, DBEntityImpl> getDBSourceEntities(boolean generateRel) throws SQLException {	
		// Maps entity full name to entity
		Map<String, DBEntityImpl> dbSourceEntities = new HashMap<String, DBEntityImpl>();

		// Maps pk constraint name to entity
		Map<String, DBEntityImpl> pkToEntityMap = new HashMap<String, DBEntityImpl>();

		String dsName = ds.getName();
		String schemaOwner = ds.getSchemaOwner(); 
		if(schemaOwner != null){
			schemaOwner = schemaOwner.toUpperCase();
		}
		String dbName = null;
		String dbVersion = null;
		DatabaseMetaData dsMetadata = conn.getMetaData();
		dbName = dsMetadata.getDatabaseProductName();
		dbVersion = dsMetadata.getDatabaseProductVersion();

		// prepare statements for later execution         
		PreparedStatement objStmt = conn
		.prepareStatement(getTableListQueryString());
		PreparedStatement colStmt = conn
		.prepareStatement(getTableColumnQueryString());
		PreparedStatement conStmt = conn
		.prepareStatement(getConstraintListQueryString());
		PreparedStatement cccStmt = conn
		.prepareStatement(getConstraintColumnQueryString());

		String objectName = null;
		String schemaName = null;

		if(schemaOwner != null) {
			objStmt.setString(1, schemaOwner);
			objStmt.setString(2, schemaOwner);
		}

		DBSchemaImpl dbs = null;
		
		ResultSet objRslt = objStmt.executeQuery();
		while (objRslt.next()) { // loop over list of tables/views
			
			objectName = objRslt.getString(OBJ_NAME);
			schemaName = objRslt.getString(OBJ_SCHEMA);
			
			if(dbs == null) {
				dbs = new DBSchemaImpl(schemaName, schemaName);
				dbSchemas.put(schemaName, dbs);
			}
			
			int objectType = objRslt.getString(OBJ_TYPE).startsWith("T") ? DBEntity.TABLE : DBEntity.VIEW;
		
			DBEntityImpl dbe = new DBEntityImpl(objectName, dsName , objectName, objectType, schemaOwner);
			dbe.setAliasName(objectName);
			dbe.setJDBCResourceURI(ds.getJdbcResourceURI());
			dbe.setType(BaseEntity.CONCEPT);
			dbe.setDBName(dbName);
			dbe.setDBVersion(dbVersion);

			dbs.addEntity(dbe);
			
			dbSourceEntities.put(dbe.getFullName(), dbe);

			// get list of columns for the table
			if(schemaOwner == null) {
				colStmt.setString(1, objectName);
			} else {
				colStmt.setString(1, schemaOwner);
				colStmt.setString(2, objectName);
			}
			ResultSet colRslt = colStmt.executeQuery();
			while (colRslt.next()) { // loop over object columns

				String colName = colRslt.getString(COL_NAME);
				String dataType = colRslt.getString(DATATYPE);
				int dataLength = colRslt.getInt(LENGTH);
				int dataPrecision = colRslt.getInt(PRECISION);
				int dataScale = colRslt.getInt(SCALE);
				Object defaultValue = colRslt.getObject(DATA_DEFAULT);
				if(defaultValue instanceof String && ((String)defaultValue).startsWith("\'")){
					defaultValue = ((String)defaultValue).substring(1, ((String)defaultValue).length()-1);
				}
				
				boolean isNullable = colRslt.getString(NULLABLE).equals("Y") ? true : false;

				DBPropertyImpl col = new OracleDBPropertyImpl(
						colName, dataType, dataLength,
						dataPrecision, dataScale, isNullable);
				col.setAlias(colName);
				col.setDefaultValue((String)defaultValue);
				dbe.addProperty(col);
			}
			colRslt.close();
			
			Map<String, DBConstraint> fkRelations = new HashMap<String, DBConstraint>();
			DBConstraint pkRelation = null;

			// get list of constraints for the table
			if(schemaOwner == null) {
				conStmt.setString(1, objectName);				
			} else {
				conStmt.setString(1, schemaOwner);
				conStmt.setString(2, objectName);					
			}

			ResultSet conRslt = conStmt.executeQuery();
			while (conRslt.next()) { // loop over constraints
				
				String constraintName = conRslt
				.getString(CONSTRAINT_NAME);
				String constraintType = conRslt
				.getString(CONSTRAINT_TYPE);
				String rOwner = conRslt.getString(R_OWNER);
				String rConstraintName = conRslt
				.getString(R_CONSTRAINT_NAME);

				DBConstraint con = new DBConstraint(
						constraintName, constraintType);
				con.setROwner(rOwner);
				con.setRConstraintName(rConstraintName);

				// get list of columns for the constraint
				if(schemaOwner == null){
					cccStmt.setString(1, constraintName);						
				} else {
					cccStmt.setString(1, schemaOwner);
					cccStmt.setString(2, constraintName);						
				}

				ResultSet cccRslt = cccStmt.executeQuery();
				while (cccRslt.next()) { // loop over constraint columns

					String consColName = cccRslt.getString(COLUMN_NAME);
					con.addColumn(consColName);

					if (generateRel && constraintType.equals("R")) {
						((DBPropertyImpl)(dbe.getProperty(consColName))).setFK(true);
					}
					if (constraintType.equals("P")) {
						((DBPropertyImpl)(dbe.getProperty(consColName))).setPK(true);
					}
				}
				cccRslt.close();

				if (generateRel && constraintType.equals("R")) {
					fkRelations.put(constraintName, con);
				}
				if (constraintType.equals("P")) {
					pkRelation = con; // should be only one primary key per table
					pkToEntityMap.put(constraintName, dbe); // back reference by primary key                  
				}
			}
			conRslt.close();

			dbe.setPkRelation(pkRelation);
			dbe.setFkRelations(fkRelations);
		}
		objRslt.close();

		cccStmt.close();
		conStmt.close();
		colStmt.close();
		objStmt.close();

		
		// determine if the relationship is containment or reference
		determineRelationshipType(dbSourceEntities, pkToEntityMap);
		
		Map<String, DBEntityImpl> retMap = new HashMap<String, DBEntityImpl>();
		retMap.putAll(dbSourceEntities);

		
		return retMap;

	}

	// *****************************************************************
	// Determine if the concept relationship is of type containment
	// or reference.  If the FK references the child table PK, and includes
	// all of the child PK columns, and if the FK columns are part of the
	// originating table's PK, then it is considered to be containment.
	//
	// In the EDS case:
	//    TRAVEL_DOCUMENT 
	//       contains TRAVEL_DOCUMENT_BOOKLETS
	//          contains COUPONS
	//             references TRAVEL_SEGMENT
	//
	// COUPONS(ITINERARY_ID, SEGMENT_ID) are non-primary keys in COUPONS,
	// but they are the foreign key columns that reference the
	// TRAVEL_SEGMENT primary key (ITINERARY_ID, SEGMENT_SEQ).
	//
	// Part of the TRAVEL_DOCUMENT_BOOKLETS primary key columns, however,
	// are used as part of the foreign key that references all columns
	// of the TRAVEL_DOCUMENT primary key.  Thus, this is containment.
	// *****************************************************************         
	protected void determineRelationshipType(Map<String, DBEntityImpl> dbSourceEntities, Map<String, DBEntityImpl> pkToEntityMap) {

		Iterator<DBEntityImpl> iter = dbSourceEntities.values().iterator();
		while (iter.hasNext()) {
			DBEntityImpl entity = (DBEntityImpl) iter.next();
			DBConstraint pkRel = entity.getPkRelation();
			Map<String, DBConstraint> fkRels = entity.getFkRelations();

			//iterate thru fk constrains to find out type of relation
			//and add relation to related entity
			Iterator<?> fkRelIterator = fkRels.values().iterator();
			while (fkRelIterator.hasNext()) {
				DBConstraint fkRel = (DBConstraint) fkRelIterator.next();
				String rConstraintName = fkRel.getRConstraintName();

				DBEntityImpl rEntity = (DBEntityImpl) pkToEntityMap.get(rConstraintName);
				if(rEntity == null){
					continue;
				}
				BaseRelationshipImpl relToAdd = new BaseRelationshipImpl();

				int type = BaseRelationship.REFERENCE;
				Iterator<String> pkColIter = rEntity.getPkRelation().getColumns().iterator();
				Iterator<String> fkColIter = fkRel.getColumns().iterator();

				boolean fkSubsetOfPk = false;
				if(pkRel != null){
					fkSubsetOfPk = pkRel.getColumns().containsAll(fkRel.getColumns());
				}
				// If fk of the entity is subset of pk of same entity then 
				// the relationship type is containment
				// By definition fk refers to only pk or unique key in database
				if (fkSubsetOfPk) { // column names
					type = BaseRelationship.CONTAINMENT;

					// If type is containment then the relationship is added to entity which 
					// contains entity. In database terms the object/entity whose pk/uk is refered,
					// holds the relationship, 
					// where as in case of reference, refering object/entity holds the relationsion.
					while (pkColIter.hasNext() && fkColIter.hasNext()) {
						String pkColName = (String) pkColIter.next();
						String fkColName = (String) fkColIter.next();
						
						RelationshipKeyImpl key = new RelationshipKeyImpl(pkColName, fkColName, true);
						relToAdd.addRelationshipKey(key);
					}
					relToAdd.setName(entity.getName());
					relToAdd.setRelationshipEnum(type);
					relToAdd.setChildEntityName(entity.getFullName());
					relToAdd.setChildEntity(entity);
					rEntity.addRelationship(relToAdd);

				} else {
					type = BaseRelationship.REFERENCE;

					while (pkColIter.hasNext() && fkColIter.hasNext()) {
						String pkColName = (String) pkColIter.next();
						String fkColName = (String) fkColIter.next();
						RelationshipKeyImpl key = new RelationshipKeyImpl(fkColName, pkColName, true);
						relToAdd.addRelationshipKey(key);
					}
					relToAdd.setName(rEntity.getName());
					relToAdd.setRelationshipEnum(type);
					relToAdd.setChildEntityName(rEntity.getFullName());
					relToAdd.setChildEntity(rEntity);
					entity.addRelationship(relToAdd);

				}
			}//fkRelIterator while loop ends
		}
	}
}
