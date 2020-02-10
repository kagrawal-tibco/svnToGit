package com.tibco.cep.runtime.model.serializers.coherence;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;

import com.tangosol.io.ExternalizableLite;
import com.tangosol.util.ExternalizableHelper;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.model.serializers.SerializableLite;
import com.tibco.cep.runtime.model.serializers.Serializer;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Jun 22, 2010
 * Time: 2:16:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class CoherenceSerializer implements Serializer {


    /**
     * Serialize as per the context provided.
     * Context has 3 named elements : type, output, and input
     */
    public void serialize(Type type, Object input, Object output, Map context) throws IOException {

        DataOutput dio  = (DataOutput) output;

        switch (type) {
            case CONCEPT: //Concept
            case STATEMACHINE: //StateMachine
                ((Concept)input).serialize(new CoherenceDataOutputConceptSerializer(dio, input.getClass()));
                break;
            case SIMPLE_EVENT: //Events
                ((SimpleEvent)input).serialize(new CoherenceDataOutputEventSerializer(dio, input.getClass()));
                break;
            case TIME_EVENT: //Events
                ((TimeEvent)input).serialize(new CoherenceDataOutputEventSerializer(dio, input.getClass()));
                break;
//            case SENDEVENTTASK:
//            {
//            	ExternalizableHelper.writeExternalizableLite(dio, (ExternalizableLite)input);
//            	break;
//            }
        }
    }

    
    public void deserialize(Type type, Object input, Object output, Map context) throws IOException {

        DataInput dio  = (DataInput) input;

        switch (type) {
            case CONCEPT: //Concept
            case STATEMACHINE: //StateMachine
                ((Concept)output).deserialize(new CoherenceDataInputConceptDeserializer(dio));
                break;
            case SIMPLE_EVENT: //Events
                ((SimpleEvent)output).deserialize(new CoherenceDataInputEventDeserializer(dio, null));
                break;
            case TIME_EVENT: //Events
                ((TimeEvent)output).deserialize(new CoherenceDataInputEventDeserializer(dio, null));
                break;
        }
    }
    
	public void writeSerializableLite(DataOutput dos, Object object) throws IOException {
		ExternalizableHelper.writeExternalizableLite(dos, (ExternalizableLite)object);
	}
	
	public SerializableLite readSerializableLite(DataInput din) throws IOException {
		return (SerializableLite) ExternalizableHelper.readExternalizableLite(din);
	}


	@Override
	public Object readSerializableLite(DataInput din, ClassLoader classLoader) throws IOException {
		 return ExternalizableHelper.readExternalizableLite(din,classLoader);
	}    
}
