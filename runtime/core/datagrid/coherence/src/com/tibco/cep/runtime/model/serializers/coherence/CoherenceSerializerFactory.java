package com.tibco.cep.runtime.model.serializers.coherence;

import com.tibco.cep.runtime.model.serializers.Serializer;
import com.tibco.cep.runtime.model.serializers.SerializerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Jun 22, 2010
 * Time: 2:14:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class CoherenceSerializerFactory implements SerializerFactory {

    
    public Serializer newSerializer() {
        return new CoherenceSerializer();
    }

}
