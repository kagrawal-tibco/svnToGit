package com.tibco.rta.model.stats;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 12/9/14
 * Time: 2:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class DBTransactionStats {

    private long insertCount;

    private long updateCount;

    private long deleteCount;

    private long transactionCount;

    public DBTransactionStats(long insertCount,
                              long updateCount,
                              long deleteCount,
                              long transactionCount) {
        this.insertCount = insertCount;
        this.updateCount = updateCount;
        this.deleteCount = deleteCount;
        this.transactionCount = transactionCount;
    }

    public long getInsertCount() {
        return insertCount;
    }

    public long getUpdateCount() {
        return updateCount;
    }

    public long getDeleteCount() {
        return deleteCount;
    }

    public long getTransactionCount() {
        return transactionCount;
    }
}
