/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.be.functions.coherence.filters;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import com.tangosol.util.ValueExtractor;
import com.tibco.be.functions.coherence.DeserializationDecorator;

/*
* Author: Ashwin Jayaprakash Date: Jan 14, 2009 Time: 1:59:48 PM
*/
public class DeserializationDecoratorFilter implements ValueExtractor, Externalizable {
    public Object extract(Object object) {
        return DeserializationDecorator.deserialize(object);
    }

    public void writeExternal(ObjectOutput out) throws IOException {
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    }
}
