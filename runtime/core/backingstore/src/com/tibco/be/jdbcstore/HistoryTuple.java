package com.tibco.be.jdbcstore;

public class HistoryTuple {
    public java.sql.Timestamp ts;
    public Object value;

    public HistoryTuple(java.sql.Timestamp ts, Object value) {
        this.ts = ts;
        this.value = value;
    }
}
