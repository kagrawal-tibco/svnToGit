package com.tibco.cep.runtime.model.serializers;

import java.io.DataInput;
import java.io.DataOutput;


public interface SerializableLite {

    void writeExternal (DataOutput out) throws java.io.IOException;

    void readExternal (DataInput in) throws java.io.IOException;
}
