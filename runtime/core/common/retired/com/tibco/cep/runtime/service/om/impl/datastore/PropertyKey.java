package com.tibco.cep.runtime.service.om.impl.datastore;


import com.tibco.cep.runtime.model.serializers.SerializableLite;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Aug 5, 2006
 * Time: 6:36:33 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Suresh : Date : 6/29/2010
 * Four years later - visiting the code
 * PropertyKey implements SerializableLite compared to SerializableLite
 * which is a tangosol interface.
 * Not sure why Nikunj thought of it.
 */
public class PropertyKey implements SerializableLite {

    long subjectId;
    String propClassName;

    public PropertyKey() {

    }

    public PropertyKey(long _subjectId, String _propClassName) {
        subjectId = _subjectId;
        propClassName = _propClassName;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final PropertyKey that = (PropertyKey) o;

        if (subjectId != that.subjectId) return false;
        if (!propClassName.equals(that.propClassName)) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (int) (subjectId ^ (subjectId >>> 32));
        result = 29 * result + propClassName.hashCode();
        return result;
    }

    public String toString() {
        return "PropertyKey{" +
                "propClassName='" + propClassName + '\'' +
                ", subjectId=" + subjectId +
                '}';
    }

    public void readExternal(DataInput dataInput) throws IOException {
        subjectId = dataInput.readLong();
        propClassName = dataInput.readUTF();
        throw new RuntimeException("This should not be called");
    }

    public void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(subjectId);
        dataOutput.writeUTF(propClassName);
        throw new RuntimeException("This should not be called");        
    }
}
