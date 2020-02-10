package com.tibco.cep.modules.db.service;



import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleServiceProvider;


public class JDBCConnectionPoolManager   {

    HashMap jdbcCnxPools = new HashMap();
    DBConnectionFactory dbFactory;

    public JDBCConnectionPoolManager(DBConnectionFactory factory) {
        this.dbFactory = factory;
    }


    public void init() throws  Exception {

        RuleServiceProvider rsp = dbFactory.getModuleManager().getRuleServiceProvider();
        Collection jdbcpools = jdbcCnxPools.values();
        final Logger logger = rsp.getLogger(JDBCConnectionPoolManager.class);
        for(Iterator it = jdbcpools.iterator(); it.hasNext();) {
            com.tibco.cep.modules.db.service.JDBCConnectionPool pool = (com.tibco.cep.modules.db.service.JDBCConnectionPool) it.next();
            pool.init();
        }
    }

    public void start(int mode) throws Exception {
    }


    public void addConnectionPool(String poolUri, JDBCConnectionPool pool) {
        jdbcCnxPools.put(poolUri, pool);
    }

    public void stop()  {
        Collection jdbcpools = jdbcCnxPools.values();
        for(Iterator it = jdbcpools.iterator(); it.hasNext();) {
            com.tibco.cep.modules.db.service.JDBCConnectionPool pool = (com.tibco.cep.modules.db.service.JDBCConnectionPool) it.next();
            pool.close();
        }
    }



    public Connection getJDBCConnection(String jdbcResourceName) throws SQLException {
        JDBCConnectionPool cnxPool = (com.tibco.cep.modules.db.service.JDBCConnectionPool) jdbcCnxPools.get(jdbcResourceName);
        if (cnxPool == null) {
        	throw new RuntimeException ("jdbcResource " + jdbcResourceName + " not found");
        }
        return cnxPool.getConnection();
    }

    public void relaseConnection(String jdbcResourceName, Connection cnx) {
        JDBCConnectionPool cnxPool = (com.tibco.cep.modules.db.service.JDBCConnectionPool) jdbcCnxPools.get(jdbcResourceName);
        if (cnxPool != null) {
        	cnxPool.free(cnx);
        }
    }
    
    public int getConnectionStatus(String jdbcResourceName) {
        JDBCConnectionPool cnxPool = (com.tibco.cep.modules.db.service.JDBCConnectionPool) jdbcCnxPools.get(jdbcResourceName);
        if (cnxPool == null) {
        	throw new RuntimeException ("jdbcResource " + jdbcResourceName + " not found");
        }
        return cnxPool.getStatus();
    }

}
