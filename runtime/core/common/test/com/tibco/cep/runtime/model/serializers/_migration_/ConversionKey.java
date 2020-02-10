package com.tibco.cep.runtime.model.serializers._migration_;

/*
* Author: Ashwin Jayaprakash Date: Jan 19, 2009 Time: 1:43:42 PM
*/

/**
 * Only the {@link #getName()} is used to check for equality.
 */
public class ConversionKey<T> {
    protected String name;

    protected Class<T> type;

    public ConversionKey(String name, Class<T> type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Class<T> getType() {
        return type;
    }

    //------------

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ConversionKey)) {
            return false;
        }

        ConversionKey that = (ConversionKey) o;

        return name.equals(that.getName());
    }

    public int hashCode() {
        return name.hashCode();
    }
}
