package com.tibco.cep.studio.dbconcept.conceptgen;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IProgressMonitor;

import  com.tibco.be.jdbcstore.ssl.JdbcSSLConnectionInfo;
import com.tibco.cep.modules.db.service.JDBCConnectionPool;
import com.tibco.cep.studio.dbconcept.utils.DBConnectionManager;
import com.tibco.security.AXSecurityException;
import com.tibco.security.ObfuscationEngine;

/**
 * 
 * @author schelwa
 * 
 * An abstract class for database catalog, implements BaseEntityCatalog.\n
 * A database specific implementation has to be provided to build catalog
 * by reading database metadata
 *
 */
public abstract class DBEntityCatalog implements BaseEntityCatalog {
    
	public static boolean dbImportUseQuotes = System.getProperty("be.dbconcepts.dbimport.use.quotes", "false").equals("true");
	
    protected transient Connection conn;
    
    private static DBConnectionManager conManager; 

    // Maps entity full name to entity
    protected Map<String, DBEntity> entities = new LinkedHashMap<String, DBEntity>();
    
    // Maps schema name to DBSchema
    protected Map<String, DBSchema> dbSchemas = new LinkedHashMap<String, DBSchema>();
    
    protected DBDataSource ds;
    
    public DBEntityCatalog (DBDataSource ds) {
        
        this.ds = ds;

        String dsName   = ds.getName();
        String driver   = ds.getDriver();
        driver          = JDBCConnectionPool.getDriverClass(driver);
        String connUrl  = ds.getConnectionUrl();
        String username = ds.getUserId();
        String password = ds.getPassword();
        int retry       = ds.getRetryCount();

        createConnectionPool(dsName, driver, connUrl, username, password, retry, ds.getSSLConnectionInfo());
        
        conn = getConnManager().getConnection(ds.getName());
    }

    synchronized protected static DBConnectionManager getConnManager() {
        if (conManager == null) {
            conManager = DBConnectionManager.getInstance();
        }
        return conManager;  
    }
    
    synchronized protected static void releaseConnManager() {
        getConnManager().release();
        conManager = null;
    }
    
    synchronized protected Connection getConnection() {
        if(conn == null) {
            conn = getConnManager().getConnection(ds.getName());
        }
        return conn;
    }

    synchronized protected void putConnection() {
        if(conn != null) {
            getConnManager().freeConnection(ds.getName(), conn);
        }
    }
    
    public String getName() {
        return ds.getName();
    }

    public String getDatabaseType() {
        return ds.getDBType();
    }

    public Map<String, DBSchema> getDBSchemas() {
        return dbSchemas;
    }
    
    public DBSchema getDBSchema(String schemaName) {
        return (DBSchema) dbSchemas.get(schemaName);
    }
    
    public Map<String, DBEntity> getEntities() {
        return this.entities;
    }

    //public abstract void buildCatalog(boolean generateRel) throws SQLException;
    
    public abstract void buildCatalog(boolean generateRel,String userSQLQuery, IProgressMonitor monitor) throws SQLException;
    
    public BaseEntity getEntity(String entityNS) {
        return (BaseEntity) this.entities.get(entityNS);
    }
    
    private void createConnectionPool(String dsName, String driver,
            String connUrl, String user, String passwd, int retry, JdbcSSLConnectionInfo sslConnectionInfo) {

        DBConnectionManager dbConnMgr = getConnManager();

        try {
            if (ObfuscationEngine.hasEncryptionPrefix(passwd))
                passwd = new String(ObfuscationEngine.decrypt(passwd));
            dbConnMgr.loadDriver(driver);
            dbConnMgr.createPool(dsName, connUrl,
                    user, passwd,1, retry, sslConnectionInfo);
        } catch (AXSecurityException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw e;
        } catch (Throwable t) {
        	t.printStackTrace();
        	throw new RuntimeException(t);
        }
    }
    
    public String getCatalogAsString() {
        String BRK = "\n";
        StringBuffer b = new StringBuffer(1024);
        
        for (Iterator<Entry<String, DBEntity>> i = entities.entrySet().iterator(); i.hasNext();) {
            Entry<String, DBEntity> e = i.next();
            DBEntity dbe =  e.getValue();
            b.append(e.getKey()).append(BRK);
            b.append("    ");
            for (Iterator<?> j = dbe.getPK().iterator(); j.hasNext();) {
                b.append(j.next());
                if (j.hasNext()) {
                    b.append(", ");
                }
            }
            b.append(BRK);
            for (Iterator<?> j = dbe.getChildEntities().iterator(); j.hasNext();) {
                BaseRelationship rel = (BaseRelationship) j.next();
                b.append("    ").append(rel.getChildEntityName()).append(' ').
                    append (rel.getRelationshipEnum() == BaseRelationship.CONTAINMENT ? 'C' : 'R').append(BRK);
                for (Iterator<?> k = rel.getRelationshipKeySet().iterator(); k.hasNext();){
                    RelationshipKey key = (RelationshipKey) k.next();
                    b.append("        ").append(key.getParentKey()).append(" : ").append(key.getChildKey()).append(BRK);
                }
            }
            b.append(BRK);
        }
        b.append(BRK).append(BRK);
        
        return b.toString();
    }
    
    public String toString(){
        return getName();
    }
}
