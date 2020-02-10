package com.tibco.be.oracle.impl;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import com.tibco.be.jdbcstore.ssl.JdbcSSLConnectionInfo;

import oracle.jdbc.pool.OracleDataSource;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Apr 27, 2008
 * Time: 9:04:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class OracleConnectionManager {
    static Map m_dataSources = Collections.synchronizedMap(new LinkedHashMap());
    static Map m_activePools = Collections.synchronizedMap(new LinkedHashMap());

    public final static OracleConnectionPool registerDataSource(String key, Properties datasrcProps, Properties poolProps, boolean isActive, JdbcSSLConnectionInfo sslConnectionInfo) throws Exception {
        // cache specific pool sizes override global pool size definition
        Class.forName("oracle.jdbc.OracleDriver");
        OracleDataSource ds = new oracle.jdbc.pool.OracleDataSource();
        ds.setURL(datasrcProps.getProperty("location"));
        ds.setUser(datasrcProps.getProperty("user"));
        ds.setPassword(datasrcProps.getProperty("password"));
        // This requires ons.jar in the classpath!
        //ds.setConnectionCachingEnabled(true);
        //ds.setFastConnectionFailoverEnabled(true);
        ds.setDataSourceName(key);
        Properties props = new Properties();
        if (sslConnectionInfo != null) {
        	props.putAll(sslConnectionInfo.getProperties());
        	sslConnectionInfo.loadSecurityProvider();
        }
        props.put("oracle.jdbc.ReadTimeout", poolProps.getProperty("ReadTimeout"));
        ds.setConnectionProperties(props);
        return registerDataSource(key, ds, poolProps, isActive);
    }

    /**
     * @param key
     * @param ds
     * @param properties
     * @return
     */
    public final static OracleConnectionPool registerDataSource(String key, OracleDataSource ds, Properties properties, boolean isActive) {
        synchronized (m_dataSources) {
            if (!m_dataSources.containsKey(key)) {
                try {
                    OracleConnectionPool connectionPool = new OracleConnectionPool(key, ds, properties);
                    m_dataSources.put(key, connectionPool);

                    if (isActive) {
                        OracleActiveConnectionPool activePool = new OracleActiveConnectionPool(key, connectionPool, null,
                                properties);
                        m_activePools.put(key, activePool);
                    }
                    //System.err.println("Registered Datasource " + key + " Properties=" + ds.getConnectionProperties());
                    return connectionPool;
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                return (OracleConnectionPool) m_dataSources.get(key);
            }
        }
    }

    public final static void deregisterDataSource(String key) {
        synchronized (m_dataSources) {
            m_dataSources.remove(key);
        }
    }

    public final static OracleConnectionPool getDataSource(String key) {
        synchronized (m_dataSources) {
            return (OracleConnectionPool) m_dataSources.get(key);
        }
    }

    public final static OracleActiveConnectionPool getActiveConnectionPool(String key) {
        synchronized (m_activePools) {
            return (OracleActiveConnectionPool) m_activePools.get(key);
        }
    }
}
