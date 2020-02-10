/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 */

package com.tibco.cep.runtime.model.serializers;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Jun 22, 2010
 * Time: 10:07:27 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Serializer {
    /**
     * Serialize according to the Transport specific way. The context is interpreted accordingingly.
     * @param type : an identifier that tells what type of entity it is.
     *               0 : Concept
     *               1 : StateMachine
     *               2 : SimpleEvent
     *               3 : TimeEvent
     * @param input : the entity that needs to be serialized [Concept|StateMachine|Event|]
     * @param output : the output channel on which entity needs to be serialized.
     *                 It could be provider specific and the serializer implementation understands it.
     *                  Examples are DataOutput, Tuple (for AS), ObjectOutput, RvMessage, JMSMessage etc.
     * @param context : Any provider specific context.
     * @throws Exception
     */
    void serialize(Type type, Object input, Object output, Map context) throws IOException;

    /**
     * DeSerialize according to the Transport specific way. The context is interpreted accordingly.
     * @param type : an identifier that tells what type of entity it is.
     * @param input : the input channel/stream that contains the data
     * @param output : the output object whose attributes have to be filled.
     *                 It could be provider specific and the serializer implementation understands it.
     *
     * @param context : Any provider specific context.
     * @throws Exception
     */
    void deserialize(Type type, Object input, Object output, Map context) throws IOException;


    enum Type {
        CONCEPT,
        STATEMACHINE,
        SIMPLE_EVENT,
        TIME_EVENT
    }
    
	public void writeSerializableLite(DataOutput dos, Object object) throws IOException;
	public Object readSerializableLite(DataInput din) throws IOException;
	public Object readSerializableLite(DataInput din, ClassLoader classLoader) throws IOException;

}
