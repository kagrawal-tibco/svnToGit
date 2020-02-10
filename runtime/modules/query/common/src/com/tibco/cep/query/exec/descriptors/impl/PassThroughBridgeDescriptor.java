package com.tibco.cep.query.exec.descriptors.impl;

import com.tibco.cep.query.exec.descriptors.StreamDescriptor;
import com.tibco.cep.query.exec.descriptors.TupleInfoDescriptor;
import com.tibco.cep.query.model.ModelContext;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Sep 30, 2008
* Time: 5:39:16 PM
*/
public class PassThroughBridgeDescriptor
        extends BridgeDescriptor {


    public PassThroughBridgeDescriptor(
            String streamName,
            StreamDescriptor inputStreamDescriptor,
            TupleInfoDescriptor tupleInfoDescriptor,
            String tupleInfoFieldName,
            ModelContext context,
            StreamDescriptor setupDescriptor) {
        super(streamName, inputStreamDescriptor, tupleInfoDescriptor, tupleInfoFieldName, context, setupDescriptor);
    }

    
}
