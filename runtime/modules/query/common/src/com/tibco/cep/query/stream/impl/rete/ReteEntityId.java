package com.tibco.cep.query.stream.impl.rete;

/*
* Author: Ashwin Jayaprakash Date: May 20, 2008 Time: 7:19:13 PM
*/
public class ReteEntityId extends Number {
    protected final long reteId;

    protected final short prefix;

    public ReteEntityId(short prefix, long reteId) {
        this.prefix = prefix;
        this.reteId = reteId;
    }

    public long getReteId() {
        return reteId;
    }

    public short getPrefix() {
        return prefix;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ReteEntityId)) {
            return false;
        }

        ReteEntityId that = (ReteEntityId) o;

        if (prefix != that.prefix) {
            return false;
        }

        if (reteId != that.reteId) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        long h = (prefix * 37) + (reteId ^ (reteId >>> 32));

        return (int) h;
    }

    //------------

    public int intValue() {
        return hashCode();
    }

    public long longValue() {
        return hashCode();
    }

    public float floatValue() {
        return hashCode();
    }

    public double doubleValue() {
        return hashCode();
    }
}
