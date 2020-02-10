package com.tibco.cep.query.exec.descriptors.impl;

import com.tibco.cep.query.exec.descriptors.StreamDescriptor;
import com.tibco.cep.query.exec.descriptors.TupleInfoDescriptor;
import com.tibco.cep.query.model.ModelContext;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Dec 22, 2008
* Time: 4:19:07 PM
*/
public class SimpleBridgeDescriptor
        extends BridgeDescriptor {


    /**
     * @param streamName            String id of the Stream.
     * @param inputStreamDescriptor StreamDescriptor of the previous stream.
     * @param tupleInfoDescriptor   TupleInfoDescriptor describing the output Tuple of the Stream.
     * @param tupleInfoFieldName    String name of the variable holding the TupleInfoDescriptor.
     * @param context               ModelContextassociated to the Stream in the query, if any.
     * @param setupDescriptor       StreamDescriptor of the stream that the bridge is set up with.
     */
    public SimpleBridgeDescriptor(
            String streamName,
            StreamDescriptor inputStreamDescriptor,
            TupleInfoDescriptor tupleInfoDescriptor,
            String tupleInfoFieldName,
            ModelContext context,
            StreamDescriptor setupDescriptor) {
        super(streamName, inputStreamDescriptor, tupleInfoDescriptor, tupleInfoFieldName, context, setupDescriptor);
    }
    
}
