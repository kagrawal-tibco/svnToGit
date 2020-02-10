/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 */

package com.tibco.cep.runtime.model.serializers.as;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.model.serializers.DataInputConceptDeserializer;
import com.tibco.cep.runtime.model.serializers.DataInputEventDeserializer;
import com.tibco.cep.runtime.model.serializers.DataOutputConceptSerializer;
import com.tibco.cep.runtime.model.serializers.DataOutputEventSerializer;
import com.tibco.cep.runtime.model.serializers.SerializableLite;
import com.tibco.cep.runtime.model.serializers.Serializer;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Jun 22, 2010
 * Time: 2:01:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class ASSerializer implements Serializer {
	static final Logger LOGGER;
	
	static {
    	Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
    	if (cluster != null) {
    		LOGGER = cluster.getRuleServiceProvider().getLogger(ASSerializer.class);
    	} else {
    		LOGGER = LogManagerFactory.getLogManager().getLogger(ASSerializer.class);
    	}
    }
	
	@Override
	public void deserialize(Type type, Object input, Object output, Map context) throws IOException {
        DataInput dio  = (DataInput) input;
        switch (type) {
            case CONCEPT: //Concept
            case STATEMACHINE: //StateMachine
                ((Concept)output).deserialize(new DataInputConceptDeserializer(dio));
                break;
            case SIMPLE_EVENT: //Events
                ((SimpleEvent)output).deserialize(new DataInputEventDeserializer(dio, null));
                break;
            case TIME_EVENT: //Events
                ((TimeEvent)output).deserialize(new DataInputEventDeserializer(dio, null));
                break;
        }
	}

	@Override
	public void serialize(Type type, Object input, Object output, Map context) throws IOException {
        DataOutput dio  = (DataOutput) output;
        switch (type) {
            case CONCEPT: //Concept
            case STATEMACHINE: //StateMachine
                ((Concept)input).serialize(new DataOutputConceptSerializer(dio, input.getClass()));
                break;
            case SIMPLE_EVENT: //Events
                ((SimpleEvent)input).serialize(new DataOutputEventSerializer(dio, input.getClass()));
                break;
            case TIME_EVENT: //Events
                ((TimeEvent)input).serialize(new DataOutputEventSerializer(dio, input.getClass()));
                break;
        }
	}
	
	public void writeSerializableLite(DataOutput dos, Object object) throws IOException {
		dos.writeBoolean(object == null);
        if (object != null) {
        	dos.writeUTF(object.getClass().getName());
        	((SerializableLite)object).writeExternal(dos);
        }
	}
	
	public Object readSerializableLite(DataInput din) throws IOException {
		return readSerializableLite(din, null);
	}

	@Override
	public Object readSerializableLite(DataInput din, ClassLoader classLoader) throws IOException {
		boolean isNull = din.readBoolean();
		if (!isNull) {
			try {
				SerializableLite obj = null;
				if (classLoader == null) {
					obj = (SerializableLite) Class.forName(din.readUTF()).newInstance();
				} else {
					obj = (SerializableLite) Class.forName(din.readUTF(), true, classLoader).newInstance();
				}
				obj.readExternal(din);
				return obj;
			} catch (IOException e) {
				throw e;
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, e, "Error reading serialized entry");
			}
		}
		return null;
	}
	
	public static Integer getConceptVersion(DataInput din) throws IOException {
		return DataInputConceptDeserializer.extractVersion(din);
	}
}
