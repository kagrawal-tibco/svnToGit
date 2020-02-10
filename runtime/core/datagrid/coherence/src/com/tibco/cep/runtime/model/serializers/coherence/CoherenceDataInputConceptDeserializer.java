package com.tibco.cep.runtime.model.serializers.coherence;

import com.tangosol.io.ReadBuffer.BufferInput;
import java.io.DataInput;
import java.io.IOException;

import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.serializers.DataInputConceptDeserializer;

public class CoherenceDataInputConceptDeserializer extends DataInputConceptDeserializer {

    public CoherenceDataInputConceptDeserializer(DataInput buf) throws IOException{
    	super(buf);
    }

    public CoherenceDataInputConceptDeserializer(DataInput buf, ConceptSerializer.ConceptMigrator migrator) throws IOException{
    	super(buf, migrator);
    }

    public String getStringProperty() {
        try {
            if (buf.readBoolean()) {
            	if (buf instanceof BufferInput) {
            		return ((BufferInput)buf).readSafeUTF();
            	} else {
            		return buf.readUTF();
            	}
            } else {
                return null;
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }
}
