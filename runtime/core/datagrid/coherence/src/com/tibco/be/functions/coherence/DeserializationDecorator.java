/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.be.functions.coherence;

import com.tibco.cep.runtime.model.serializers.coherence.ConceptBytesHandler;

/*
* Author: Ashwin Jayaprakash Date: Jan 14, 2009 Time: 4:18:54 PM
*/
public class DeserializationDecorator {
    public static Object deserialize(Object object) {
        if (object instanceof byte[]) {
            return ConceptBytesHandler.uncloak((byte[]) object);
        }

        return object;
    }
}
