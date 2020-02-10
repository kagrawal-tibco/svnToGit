package com.tibco.cep.query.exec.descriptors.impl;

import com.tibco.cep.query.exec.descriptors.StreamDescriptor;
import com.tibco.cep.query.exec.descriptors.TupleInfoDescriptor;
import com.tibco.cep.query.exec.descriptors.WindowBuilderDescriptor;
import com.tibco.cep.query.model.ModelContext;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Aug 20, 2008
* Time: 7:16:03 PM
*/
public class PartitionDescriptor
        extends StreamDescriptorImpl {


    protected final String groupAggregateInfoName;
    protected final WindowBuilderDescriptor windowBuilderDescriptor;


    /**
     * Constructor with single input stream descriptor and no variable name.
     *
     * @param streamName            String id of the Stream.
     * @param inputStreamDescriptor StreamDescriptor of the previous stream.
     * @param tupleInfoDescriptor   TupleInfoDescriptor describing the output Tuple of the Stream.
     * @param tupleInfoFieldName    String name of the variable holding the TupleInfoDescriptor.
     * @param context               ModelContextassociated to the Stream in the query, if any.
     * @param groupAggregateInfoName String
     * @param windowBuilderDescriptor WindowBuilderDescriptor
     */
    public PartitionDescriptor(
            String streamName,
            StreamDescriptor inputStreamDescriptor,
            TupleInfoDescriptor tupleInfoDescriptor,
            String tupleInfoFieldName,
            ModelContext context,
            String groupAggregateInfoName,
            WindowBuilderDescriptor windowBuilderDescriptor) {

        super(streamName, inputStreamDescriptor, tupleInfoDescriptor, tupleInfoFieldName, context);
        this.groupAggregateInfoName = groupAggregateInfoName;
        this.windowBuilderDescriptor = windowBuilderDescriptor;
    }


    public String getGroupAggregateInfoName() {
        return this.groupAggregateInfoName;
    }


    public WindowBuilderDescriptor getWindowBuilderDescriptor() {
        return this.windowBuilderDescriptor;
    }


}
