package com.tibco.be.jdbcstore;

public class DateTimeValueTuple {
    public java.sql.Timestamp ts;
    public String tz;

    public DateTimeValueTuple(java.sql.Timestamp ts, String tz) {
        this.ts = ts;
        this.tz = tz;
    }
}
