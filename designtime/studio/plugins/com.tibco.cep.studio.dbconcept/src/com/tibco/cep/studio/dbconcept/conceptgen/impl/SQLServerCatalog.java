package com.tibco.cep.studio.dbconcept.conceptgen.impl;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;

import com.tibco.cep.studio.dbconcept.conceptgen.BaseEntity;
import com.tibco.cep.studio.dbconcept.conceptgen.BaseRelationship;
import com.tibco.cep.studio.dbconcept.conceptgen.DBDataSource;
import com.tibco.cep.studio.dbconcept.conceptgen.DBEntity;
import com.tibco.cep.studio.dbconcept.conceptgen.DBEntityCatalog;
import com.tibco.cep.studio.dbconcept.conceptgen.RelationshipKey;
/**
 *
 * @author bgokhale
 *
 * Introspect system views (information_schema) in SQL Server and build a catalog
 * The catalog is used by DBCeptGenerator and ModelGenerator to build BE concepts/events
 *
 */
public class SQLServerCatalog extends DBEntityCatalog {

    static final String TABLE_NAME = "TABLE_NAME";
    static final String TABLE_TYPE = "TABLE_TYPE";

    static final String IS = ".information_schema.";
    static final String TC = IS + "table_constraints ";
    static final String RC = IS + "referential_constraints ";
    static final String KC = IS + "key_column_usage ";
    static final String TS = IS + "tables ";
    static final String SC = IS + "schemata ";
    static final String IC = IS + "columns ";
    private static String delimitersForQuery = System.getProperty("tibco.be.dbconcepts.sqlquery.delimiters", "");

    public SQLServerCatalog(DBDataSource ds) {
        super(ds);
    }

    @Override
    public void buildCatalog(boolean generateRel, String userQuery, IProgressMonitor monitor) throws SQLException {
        try {
            getConnection();

            buildCatalogForSchemaOwner(ds.getSchemaOwner(),generateRel);

        }/* catch (Exception e) {
            e.printStackTrace();
        }*/ finally {
            putConnection();
            releaseConnManager();
        }
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
    private void buildCatalogForSchemaOwner(String schemaOwner,boolean generateRel) throws SQLException {
        Statement stmt = null;
        try {
            String dbName = resolveDatabaseName(ds.getDriver(), ds.getConnectionUrl());
            String delimitedDbName = appendDelimitersForQuery(dbName);
            String tableQuery = "select schema_name from " + delimitedDbName + SC;
            String orderByClause = " order by schema_name";
            if(schemaOwner != null && !"".equals(schemaOwner))
                tableQuery += " where schema_owner = '" + schemaOwner + "' and catalog_name='"+dbName+"'";
            else
                tableQuery += " where catalog_name='"+dbName+"'";
            tableQuery += orderByClause;
            stmt = conn.createStatement();
            stmt.execute(tableQuery);
            ResultSet rs = stmt.getResultSet();

            if (rs != null) {
                while (rs.next()) {
                    String schemaName = rs.getString("schema_name");
                    DBSchemaImpl dbs = new DBSchemaImpl(schemaName, schemaName);
                    dbSchemas.put(schemaName, dbs);
                    createEntities(dbName, dbs);
                }
            }
            stmt.close();
            buildRelations(generateRel);
        } finally {
            if (stmt != null) stmt.close();
        }
    }

    private void createEntities(String dbName, DBSchemaImpl dbs) throws SQLException {

        String schemaName = dbs.getName();
        String delimitedDbeName = appendDelimitersForQuery(dbName);
        Statement stmt = null;
        try {
            String tableQuery = "select c.*, t.table_type from " + delimitedDbeName +  IC + " c, " + delimitedDbeName + TS +" t " +
            " where t.table_name = c.table_name and t.table_schema = c.table_schema " +
            " and c.table_schema='" + schemaName + "'" +
             " order by t.table_schema, t.table_name, c.ordinal_position";
            stmt = conn.createStatement();
            stmt.execute(tableQuery);
            ResultSet rs = stmt.getResultSet();
            if (rs == null) {
                return;
            }

            String prevNm = "";
            DBEntityImpl dbe = null;
            while (rs.next()) { // loop over list of tables/views
                String objectName = rs.getString(TABLE_NAME);
                if (!objectName.equals(prevNm)) {
                    int objectType = rs.getString(TABLE_TYPE).equals("BASE TABLE") ? DBEntity.TABLE : DBEntity.VIEW;

                    DatabaseMetaData dsMetadata = conn.getMetaData();
                    String dbVersion = dsMetadata.getDatabaseProductVersion();

                    dbe = new DBEntityImpl(objectName, dbName + "/"
                            + schemaName, objectName, objectType, schemaName);
                    dbe.setJDBCResourceURI(ds.getJdbcResourceURI());
                    dbe.setDBVersion(dbVersion);

                    dbe.setDBName(dbName);

                    dbe.setAliasName(objectName);
                    dbe.setType(BaseEntity.CONCEPT);
                    prevNm = objectName;
                }
                String columnName = rs.getString("COLUMN_NAME");
                String columnType = rs.getString("DATA_TYPE");
                int length = rs.getInt("CHARACTER_OCTET_LENGTH"); //applicable to char types
                int precision = rs.getInt("NUMERIC_PRECISION"); // applicable to numeric types
                int scale = rs.getInt("NUMERIC_SCALE");
                boolean nullable = rs.getString("IS_NULLABLE").equals("YES");
                String defValue = rs.getString("COLUMN_DEFAULT");

                DefaultDBPropertyImpl prop = new DefaultDBPropertyImpl(objectName, columnName, columnType, length, precision, scale, nullable);
                prop.setDefaultValue(defValue);

                dbe.addProperty(prop);

                dbs.addEntity(dbe);

                entities.put(dbe.getFullName(), dbe);
            }
        } finally {
            if (stmt !=null) stmt.close();
        }
    }

    private void buildRelations(boolean generateRel) throws SQLException {

        for (Iterator<Entry<String, DBEntity>> i = entities.entrySet().iterator(); i.hasNext();) {
             Entry<String, DBEntity> e = i.next();
            DBEntityImpl dbe = (DBEntityImpl) e.getValue();
            setPKs(dbe);
        }
        if(generateRel) {
            for (Iterator<Entry<String, DBEntity>> i = entities.entrySet().iterator(); i.hasNext();) {
                Entry<String, DBEntity> e = i.next();
                DBEntityImpl dbe = (DBEntityImpl) e.getValue();
                setFKs(dbe);
            }

            for (Iterator<Entry<String, DBEntity>> i = entities.entrySet().iterator(); i.hasNext();) {
                Entry<String, DBEntity> e = i.next();
                DBEntityImpl dbe = (DBEntityImpl) e.getValue();
                swapIfContainment(dbe);
            }
        }
    }

    private void setPKs(DBEntityImpl dbe) throws SQLException {
        Statement stmt = null;
        try {
            String delimitedDbeName = appendDelimitersForQuery(dbe.getDBName());
            String sql = "select k.column_name as kc from "
                + delimitedDbeName + KC +" k, "
                + delimitedDbeName + TC + " tc where k.constraint_name = tc.constraint_name and "
                    + "tc.constraint_type = 'PRIMARY KEY' and k.table_name='"
                    + dbe.getName() + "' and k.table_schema='"
                    + dbe.getSchema() + "'";

            stmt = conn.createStatement();
            stmt.execute(sql);
            ResultSet rs = stmt.getResultSet();
            if (rs != null) {
                DBConstraint dbc = new DBConstraint(null, null);
                while (rs.next()) {
                    String colName = rs.getString("kc");
                    dbc.addColumn(colName);
                    //get corresponding property and mark as PK
                    DBPropertyImpl p = (DBPropertyImpl)dbe.getProperty(colName);
                    if (p != null) {
                        p.isPK = true;
                    }
                }
                dbe.setPkRelation(dbc);

            }
            stmt.close();
        } finally {
            if (stmt != null) stmt.close();
        }
    }

    private void setFKs(DBEntityImpl dbe) throws SQLException {

        Statement stmt = null;
        try {
            /*
             * select k.column_name, k.constraint_name, k.table_name,
             * k2.table_name, k2.column_name from
             * adventureworks.information_schema.table_constraints tc,
             * adventureworks.information_schema.referential_constraints rc,
             * adventureworks.information_schema.key_column_usage k,
             * adventureworks.information_schema.key_column_usage k2 where
             * tc.table_name='SalesOrderDetail' and tc.table_schema='Sales' and
             * tc.constraint_type='FOREIGN KEY' and tc.constraint_name =
             * k.constraint_name and rc.constraint_name = tc.constraint_name and
             * rc.unique_constraint_name = k2.constraint_name and
             * k.ordinal_position = k2.ordinal_position
             */
            /*
             * select k.constraint_name as cs1, k.table_name as t1 ,
             * k.column_name as c1 , k2.table_name as t2, k2.column_name as c2
             * from adventureworks.information_schema.table_constraints tc,
             * adventureworks.information_schema.referential_constraints rc,
             * adventureworks.information_schema.key_column_usage k,
             * adventureworks.information_schema.key_column_usage k2 where
             * tc.constraint_type='FOREIGN KEY' and tc.constraint_name =
             * k.constraint_name and rc.constraint_name = tc.constraint_name and
             * rc.unique_constraint_name = k2.constraint_name and
             * k.ordinal_position = k2.ordinal_position
             *
             * order by t1,cs1
             */
            String delimitedDbeName = appendDelimitersForQuery(dbe.getDBName());
            String sql = "select k.constraint_name as cs1, k.table_name as t1," +
                "k.column_name as c1 , k2.table_name as t2, k2.column_name as c2, k2.table_catalog as tc2, k2.table_schema as s2 from " +
                delimitedDbeName + TC + " tc, " +
                delimitedDbeName + RC + " rc, " +
                delimitedDbeName + KC + " k, " +
                delimitedDbeName + KC + " k2 " +
                " where tc.constraint_type='FOREIGN KEY' and tc.constraint_name = k.constraint_name " +
                " and rc.constraint_name = tc.constraint_name and rc.unique_constraint_name = k2.constraint_name and " +
                " k.ordinal_position = k2.ordinal_position and tc.table_name=" + "'" + dbe.getName() + "' and tc.table_schema='" +
                dbe.getSchema() + "'";

            stmt = conn.createStatement();
            stmt.execute(sql);

            ResultSet rs = stmt.getResultSet();
            String prev = "";
            BaseRelationshipImpl br = null;
            while (rs.next()) {

                String curr = rs.getString("cs1");
                if (!curr.equals(prev)) {
                    br = new BaseRelationshipImpl();
                    br.setName(rs.getString("t2"));
                    br.setCardinality(0);
                    br.setRelationshipEnum(BaseRelationship.REFERENCE);
                    //String childEntityName = rs.getString(6) + "/" + rs.getString(7) + "/" + rs.getString(4);
                    String childEntityName = rs.getString("tc2") + "/" + rs.getString("s2") + "/" + rs.getString("t2");
                    BaseEntity childEntity = (BaseEntity) entities
                            .get(childEntityName);
                    if (childEntityName != null) {
                        br.setChildEntity(childEntity);
                    }
                    br.setChildEntityName(childEntityName);

                    dbe.addRelationship(br);
                    prev = curr;
                }
                //RelationshipKey key = new RelationshipKeyImpl(rs.getString(3), rs.getString(5), false);
                RelationshipKey key = new RelationshipKeyImpl(rs.getString("c1"), rs.getString("c2"), false);
                br.addRelationshipKey(key);
            }
            stmt.close();
        } finally {
            if (stmt != null) stmt.close();
        }
    }

    private void swapIfContainment(DBEntityImpl dbe) {
        Map<String, BaseRelationship> relMap = dbe.getRelations();
        for (Iterator<Entry<String, BaseRelationship>> i=relMap.entrySet().iterator(); i.hasNext();) {
            Entry<String, BaseRelationship> e = i.next();
            BaseRelationshipImpl relImpl = (BaseRelationshipImpl) e.getValue();
            //childSide Keys
            Set<String> childKeys = new HashSet<String>();

            Set<String> myKeys = new HashSet<String>();
            for (Iterator<RelationshipKey> j=relImpl.getRelationshipKeySet().iterator(); j.hasNext();) {
                RelationshipKey rkeys =  j.next();
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

            if (childPks.containsAll(childKeys) && childKeys.containsAll(childPks) && dbe.getPK().containsAll(myKeys)) {
                //swap the relationship

                BaseRelationshipImpl newRel = new BaseRelationshipImpl();
                newRel.cardinality = 0;
                newRel.childEntityName = dbe.getFullName();
                newRel.childEntity = dbe;
                newRel.relationshipName = dbe.getName();
                newRel.relEnum = BaseRelationship.CONTAINMENT;


                for (Iterator<RelationshipKey> j=relImpl.getRelationshipKeySet().iterator(); j.hasNext();) {
                    RelationshipKey rkeys =  j.next();
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


//    public static void main (String args[]) {
//        try {
//            DBDataSourceImpl ds = new DBDataSourceImpl();
//            //ds.connectionurl = "jdbc:tibcosoftwareinc:sqlserver://rbatra-dt:1433;databaseName=AdventureWorks";
//            ds.connectionurl = "jdbc:tibcosoftwareinc:sqlserver://localhost:1433;databaseName=BE-Vrishali";
//            ds.jdbcDriver = "tibcosoftwareinc.jdbc.sqlserver.SQLServerDriver";
//            ds.dbType = "SQLServer";
//            ds.dsName = "BE-Vrishali";
//            ds.username = "vrishali";
//            ds.password = "vrishali";
//            ds.schemaOwner = "dbo";
//            SQLServerCatalog cat = new SQLServerCatalog(ds);
//            cat.buildCatalog(true, null);
//            String s = cat.getCatalogAsString();
//
//            DBEntityCatalog dbc = (DBEntityCatalog) cat;
//            Map dbsMap = dbc.getDBSchemas();
//            Iterator iter = dbsMap.values().iterator();
//            while (iter.hasNext()) {
//                DBSchema dbs= (DBSchema) iter.next();
//                Map eMap = dbs.getEntities();
//                Iterator eIter = eMap.values().iterator();
//                while (eIter.hasNext()) {
//                    DBEntity dbe = (DBEntity) eIter.next();
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    //get databasename part from conn url
    public String resolveDatabaseName(String driver, String connURL){
        String dbNamePart = "";
        if("tibcosoftwareinc.jdbc.sqlserver.SQLServerDriver".equals(driver)
                || "com.microsoft.sqlserver.jdbc.SQLServerDriver".equals(driver)
                || "net.sourceforge.jtds.jdbc.Driver".equals(driver)){
            if(connURL.indexOf("databaseName=") > -1){
                dbNamePart = connURL.substring(connURL.indexOf("databaseName=")).substring("databaseName=".length());
            } else {
                throw new RuntimeException("Invalid database url " + connURL);
            }
        } else if("weblogic.jdbc.mssqlserver4.Driver".equals(driver)) {
            dbNamePart = connURL.substring("jdbc:weblogic:mssqlserver4:".length(), connURL.indexOf("@"));
        }
        return dbNamePart;
    }

}
