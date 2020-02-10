package com.tibco.be.jdbcstore.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;

public class StatementCache {

    private final static Logger logger = LogManagerFactory.getLogManager().getLogger(StatementCache.class);

    private static HashMap      mConnectionMap = new HashMap();
    static private boolean      mCachingEnabled = true;
    static private int          mCacheSize = 280;
    private String              mSqlStatement;
    private boolean             mForcedDisable = false;

    /**
     *
     * @param   sql   sql statement string
     */
    public StatementCache(String sql) {
        if (null == sql) {
            throw new IllegalArgumentException();
        }
        mSqlStatement = sql;
    }

    public StatementCache() {
    }

    public String getSqlStatment() {
        return this.mSqlStatement;
    }

    public PreparedStatement getPreparedStatement(Connection conn) throws Exception {
        return getPreparedStatement(conn, mSqlStatement);
    }

    public PreparedStatement getNewPreparedStatement(Connection conn) throws Exception {
            return conn.prepareStatement(mSqlStatement);
    }

    public synchronized static  Map getConnMap(Connection conn) {
        String connKey = String.valueOf(conn.hashCode());
        Object connEntry = mConnectionMap.get(connKey);
        if (connEntry == null) {
            connEntry = com.tibco.be.jdbcstore.impl.StatementCacheManager.newInstance(mCacheSize);
            mConnectionMap.put(connKey, connEntry);
            logger.log(Level.DEBUG, "Cache-miss connection (%s): %s %s",
                    mConnectionMap.size(), conn.hashCode(), conn.getClass().getName());
        }
        return (Map)connEntry;
    }

    public synchronized static  StatementCacheEntry getStatementCacheEntry(Map cacheManager, String sql, boolean doInc) {
        Object c = cacheManager.get(sql);
        if (c != null) {
            StatementCacheEntry sce = (StatementCacheEntry) c;
            if (doInc) {
                sce.incUseCount();
            }
            return sce;
        }
        logger.log(Level.DEBUG, "Cache-miss connection-statement (%s): %s", cacheManager.size(), sql);
        return null;
    }

    /*
     * put the statement in the statement cache.  No attempt is made to
     * avoid putting two entries in for the same statement since that
     * is benign and rare/impossible.  Only one thread should be manipulating
     * the cache for this connection at a time.
     */
    public synchronized static  void putStatementCacheEntry(Map cMap, PreparedStatement stmt , String sql) {
        StatementCacheEntry sce = new StatementCacheEntry(sql, stmt);
        sce.incUseCount();
        cMap.put(sql, sce);
    }

    public synchronized static  void doneStatementCacheEntry(StatementCacheEntry sce) {
        sce.decUseCount();
    }

    public PreparedStatement getPreparedStatement(Connection conn, String sql) throws Exception {
        PreparedStatement stmt = null;
        if (false == mCachingEnabled || true == mForcedDisable) {
            stmt = conn.prepareStatement(sql);
            return stmt;
        }
        Map cMap = getConnMap(conn);     // cache for this connection
        StatementCacheEntry sce = getStatementCacheEntry(cMap, sql, true);

        if (sce == null) {
            try {
                // System.out.println(sql);
                stmt = conn.prepareStatement(sql);
                putStatementCacheEntry(cMap, stmt , sql);
            }
            catch (Exception e) {
                logger.log(Level.WARN, e, "Failed preparing statement.");
                try {
                    if (stmt != null) {
                        stmt.close();
                        cMap.clear();
                        throw e;
                    }
                }
                catch (Exception ee) {
                    cMap.clear();
                }
                throw e;
            }

        } else {
            stmt = sce.getPreparedStatement();
        }
        return stmt;
    }

    public void releaseStatement(Connection conn, String sql, PreparedStatement stmt) throws Exception {
        if (false == mCachingEnabled || true == mForcedDisable) {
            stmt.close();
        } else {
            Map cMap = getConnMap(conn);
            StatementCacheEntry sce = getStatementCacheEntry(cMap, sql, false);
            if (sce != null) {
                sce.decUseCount();
            } else {
                logger.log(Level.DEBUG, "Can't find statement-cache-entry to decrement use count.");
            }
        }
    }

    public void releaseStatement(PreparedStatement stmt, boolean force) throws Exception {
    }

    static public void enableCaching(boolean enable, int size) {
        mCachingEnabled = enable;
        mCacheSize = size;
    }

    public void disableCaching(boolean disable) {
        mForcedDisable = disable;
    }
}
