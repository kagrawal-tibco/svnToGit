package com.tibco.cep.impl.common.resource;

import com.tibco.cep.common.resource.Id;
import com.tibco.cep.util.annotation.Optional;

/*
* Author: Ashwin Jayaprakash Date: Jul 9, 2009 Time: 4:17:23 PM
*/

public class DefaultId implements Id {
    public static final String NULL_STRING = "[null]";

    @Optional
    protected Object parent;

    protected Object actual;

    public DefaultId(Object actual) {
        this(null, actual);
    }

    /**
     * @param parent
     * @param actual If this is null, then parent <b>should</b> also be null.
     */
    public DefaultId(@Optional Object parent, Object actual) {
        this.parent = parent;
        this.actual = actual;
    }

    @Optional
    public Object getParent() {
        return parent;
    }

    public Object getActual() {
        return actual;
    }

    //------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultId)) {
            return false;
        }

        DefaultId defaultId = (DefaultId) o;

        if (actual != null ? !actual.equals(defaultId.actual) : defaultId.actual != null) {
            return false;
        }
        if (parent != null ? !parent.equals(defaultId.parent) : defaultId.parent != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = parent != null ? parent.hashCode() : 0;
        result = 31 * result + (actual != null ? actual.hashCode() : 0);

        return result;
    }

    //------------------

    /**
     * @return "parent.actual" or simply "actual".
     */
    @Override
    public String getAsString() {
        if (parent == null) {
            return (actual == null) ? NULL_STRING : actual.toString();
        }

        return parent.toString() + "." + actual.toString();
    }

    @Override
    public String toString() {
        return getAsString();
    }
}
