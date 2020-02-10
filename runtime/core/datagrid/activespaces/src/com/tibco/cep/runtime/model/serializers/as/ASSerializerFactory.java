package com.tibco.cep.runtime.model.serializers.as;

import com.tibco.cep.runtime.model.serializers.Serializer;
import com.tibco.cep.runtime.model.serializers.SerializerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Jun 22, 2010
 * Time: 7:49:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class ASSerializerFactory implements SerializerFactory {

    @Override
    public Serializer newSerializer() {
        return new ASSerializer();  //To change body of implemented methods use File | Settings | File Templates.
    }
}
