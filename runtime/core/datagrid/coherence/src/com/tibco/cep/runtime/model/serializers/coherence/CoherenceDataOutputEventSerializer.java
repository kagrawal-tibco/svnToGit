/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.model.serializers.coherence;


import java.io.DataOutput;
import java.io.IOException;

import com.tangosol.io.WriteBuffer.BufferOutput;
import com.tibco.cep.runtime.model.serializers.DataOutputEventSerializer;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 19, 2006
 * Time: 8:09:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class CoherenceDataOutputEventSerializer extends DataOutputEventSerializer {

    public CoherenceDataOutputEventSerializer(DataOutput buf, int typeId) {
       super(buf, typeId);
    }

    public CoherenceDataOutputEventSerializer(DataOutput buf, Class entityClz) {
    	super(buf, entityClz);
    }

    /**
     *
     * @param s
     */
    public void writeStringProperty(String s) {
        try {
            if (!error) {
                if (s != null) {
                	buf.writeBoolean(true);
                    if (buf instanceof BufferOutput) {
                    	((BufferOutput)buf).writeSafeUTF(s);
                    } else {
                    	buf.writeUTF(s);
                    }
                } else {
                    buf.writeBoolean(false);
                }
            }
        } catch (IOException ex) {
            error=true;
            msg= ex.getMessage();
        }
    }

}

