/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.model.serializers.coherence;

import java.io.DataInput;
import java.io.IOException;

import com.tangosol.io.ReadBuffer.BufferInput;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.event.EventSerializer;
import com.tibco.cep.runtime.model.serializers.DataInputEventDeserializer;


public class CoherenceDataInputEventDeserializer extends DataInputEventDeserializer {
	
    public CoherenceDataInputEventDeserializer(DataInput buf, TypeManager typeManager) {
    	super(buf, typeManager);
    }
    
    public CoherenceDataInputEventDeserializer(DataInput buf, TypeManager typeManager, EventSerializer.EventMigrator migrator) throws IOException{
        super(buf, typeManager, migrator);
    }

     /**
     *
     * @param s
     */
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

