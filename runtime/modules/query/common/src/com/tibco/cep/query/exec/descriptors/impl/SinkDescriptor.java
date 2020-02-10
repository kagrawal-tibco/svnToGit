package com.tibco.cep.query.exec.descriptors.impl;

import com.tibco.cep.query.exec.descriptors.StreamDescriptor;
import com.tibco.cep.query.exec.descriptors.TupleInfoDescriptor;
import com.tibco.cep.query.model.ModelContext;

/*
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Mar 6, 2008
 * Time: 3:57:11 PM
 */

/**
 * StreamDescriptor for a Sink.
 */
public class SinkDescriptor extends EndPointDescriptor {

    private boolean isStreamed;


    /**
     * @param streamName            String id of the Stream.
     * @param inputStreamDescriptor StreamDescriptor of the previous stream.
     * @param tupleInfoDescriptor   TupleInfoDescriptor describing the output Tuple of the Stream.
     * @param tupleInfoFieldName    String name of the variable holding the TupleInfoDescriptor.
     * @param isStreamed            boolean true to request a streamed sink.
     * @param context               ModelContextassociated to the Stream in the query, if any.
     */
    public SinkDescriptor(
            String streamName,
            StreamDescriptor inputStreamDescriptor,
            TupleInfoDescriptor tupleInfoDescriptor,
            String tupleInfoFieldName,
            boolean isStreamed,
            ModelContext context) {

        super(streamName, inputStreamDescriptor,
                tupleInfoDescriptor, tupleInfoFieldName, context);

        this.isStreamed = isStreamed;
    }


    public boolean isStreamed() {
        return isStreamed;
    }

    
}
