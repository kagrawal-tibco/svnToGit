package com.tibco.be.util;


public class NVPair {


    public NVPair() {
        this(null, null);
    }


    public NVPair(String name, Object value) {
        this.name = name;
        this.value = value;
    }


    public String name;
    public Object value;
}
