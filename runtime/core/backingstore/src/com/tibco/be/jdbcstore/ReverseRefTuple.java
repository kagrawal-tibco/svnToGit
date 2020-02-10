package com.tibco.be.jdbcstore;

public class ReverseRefTuple {
    public String propertyName;
    public long id;

    public ReverseRefTuple(String name, long id) {
        this.propertyName = name;
        this.id = id;
    }
}
