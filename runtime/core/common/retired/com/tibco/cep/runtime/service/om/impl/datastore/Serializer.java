package com.tibco.cep.runtime.service.om.impl.datastore;

import java.io.DataInput;
import java.io.DataOutput;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Aug 3, 2006
 * Time: 9:12:24 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Serializer {

    public DataOutput serialize(DataOutput output, Object obj);

    public Object deserialize(DataInput input, Object obj);

}
