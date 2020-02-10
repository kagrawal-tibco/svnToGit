package com.tibco.cep.query.exec.descriptors.impl;

import com.tibco.cep.query.exec.descriptors.StreamDescriptor;
import com.tibco.cep.query.exec.descriptors.TupleInfoDescriptor;
import com.tibco.cep.query.model.ModelContext;

/*
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Mar 4, 2008
 * Time: 7:49:01 PM
 */

/**
 * StreamDescriptor for a TruncatedStream.
 */
public class TruncatedStreamDescriptor extends StreamDescriptorImpl {


    protected String first;
    protected String offset;


    /**
     * @param streamName            String id of the Stream.
     * @param inputStreamDescriptor StreamDescriptor of the previous stream.
     * @param tupleInfoDescriptor   TupleInfoDescriptor describing the output Tuple of the Stream.
     * @param tupleInfoFieldName    String name of the variable holding the TupleInfoDescriptor.
     * @param context               ModelContextassociated to the Stream in the query, if any.
     * @param firstFieldName        String name for the number of values returned.
     * @param offsetFieldName       String name for the number of values ignored.
     */
    public TruncatedStreamDescriptor(
            String streamName,
            StreamDescriptor inputStreamDescriptor,
            TupleInfoDescriptor tupleInfoDescriptor,
            String tupleInfoFieldName,
            ModelContext context,
            String firstFieldName,
            String offsetFieldName) {

        super(streamName, inputStreamDescriptor,
                tupleInfoDescriptor, tupleInfoFieldName, context);

        this.first = firstFieldName;
        this.offset = offsetFieldName;
    }


    /**
     * @return String name for the number of values returned.
     */
    public String getFirstFieldName() {
        return this.first;
    }


    /**
     * @return String name for the number of values ignored.
     */
    public String getOffsetFieldName() {
        return this.offset;
    }

}
