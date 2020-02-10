package com.tibco.cep.query.exec.descriptors.impl;

import com.tibco.cep.query.exec.descriptors.StreamDescriptor;
import com.tibco.cep.query.exec.descriptors.TupleInfoDescriptor;
import com.tibco.cep.query.model.ModelContext;

/*
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Mar 5, 2008
 * Time: 4:36:07 PM
 */

/**
 * StreamDescriptor for a Bridge.
 */
public abstract class BridgeDescriptor extends StreamDescriptorImpl {


    private StreamDescriptor setupDescriptor;


    /**
     * @param streamName            String id of the Stream.
     * @param inputStreamDescriptor StreamDescriptor of the previous stream.
     * @param tupleInfoDescriptor   TupleInfoDescriptor describing the output Tuple of the Stream.
     * @param tupleInfoFieldName    String name of the variable holding the TupleInfoDescriptor.
     * @param context               ModelContextassociated to the Stream in the query, if any.
     * @param setupDescriptor       StreamDescriptor of the stream that the bridge is set up with.
     */
    public BridgeDescriptor(
            String streamName,
            StreamDescriptor inputStreamDescriptor,
            TupleInfoDescriptor tupleInfoDescriptor,
            String tupleInfoFieldName,
            ModelContext context,
            StreamDescriptor setupDescriptor) {

        super(streamName, inputStreamDescriptor,
                tupleInfoDescriptor, tupleInfoFieldName, context);

        this.setupDescriptor = setupDescriptor;
    }


    public StreamDescriptor getSetupDescriptor() {
        return this.setupDescriptor;
    }


}
