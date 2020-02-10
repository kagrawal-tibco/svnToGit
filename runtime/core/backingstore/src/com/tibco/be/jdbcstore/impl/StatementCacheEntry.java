package com.tibco.be.jdbcstore.impl;

import java.sql.PreparedStatement;

/**
 * This class contains cached prepared statements and the string
 * that was used to create them.  The intent is that this class
 * is used where the connection is know, as prepared statements
 * must be used with the connection instance that made them.
 *
 */
public class StatementCacheEntry {

    private String              mStatementString;
    private PreparedStatement   mPreparedStatement;
    private int                 mUseCount = 0;

    /**
     * Creates a cache entry item.
     *
     */
    public StatementCacheEntry(String statementString, PreparedStatement preparedStmt) {
        this.mStatementString = statementString;
        this.mPreparedStatement = preparedStmt;
    }

    /**
     * Return statement string.
     *
     */
    public String getStatementString() {
        return mStatementString;
    }

    /**
     * Return prepared statment.
     *
     */
    public PreparedStatement getPreparedStatement() {
        return mPreparedStatement;
    }

    /**
     * get in use.
     *
     */
    public int getUseCount() {
        return mUseCount;
    }

    /**
     * get in use.
     *
     */
    public synchronized void incUseCount() {
        mUseCount++;
    }

    /**
     * set in use.
     *
     */
    public synchronized void decUseCount() {
        mUseCount--;
    }
}
