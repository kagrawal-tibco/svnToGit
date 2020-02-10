package com.tibco.cep.query.exec.descriptors.impl;

import com.tibco.cep.query.exec.descriptors.StreamDescriptor;
import com.tibco.cep.query.exec.descriptors.TupleInfoDescriptor;
import com.tibco.cep.query.model.ModelContext;

/*
 * User: nprade
 * Date: Feb 7, 2008
 * Time: 5:16:28 PM
 */
public class AggregationDescriptor
        extends StreamDescriptorImpl {


    private boolean emitFull;
    private String groupAggregateInfoName;
    private String windowBuilderName;


    /**
     * Constructor with single input stream descriptor.
     *
     * @param streamName            String id of the Stream.
     * @param inputStreamDescriptor StreamDescriptor of the previous stream.
     * @param streamVariableName    String name of the variable holding the Stream in the generated code.
     * @param tupleInfoDescriptor   TupleInfoDescriptor describing the output Tuple of the Stream.
     * @param tupleInfoFieldName    String name of the variable holding the TupleInfoDescriptor.
     * @param context               ModelContextassociated to the Stream in the query, if any.
     */
    public AggregationDescriptor(
            String streamName,
            StreamDescriptor inputStreamDescriptor,
            String streamVariableName,
            TupleInfoDescriptor tupleInfoDescriptor,
            String tupleInfoFieldName,
            ModelContext context,
            boolean emitFull,
            String groupAggregateInfoName,
            String windowBuilderName) {

        super(streamName, inputStreamDescriptor,
                streamVariableName, tupleInfoDescriptor,
                tupleInfoFieldName, context);
        this.emitFull = emitFull;
        this.groupAggregateInfoName = groupAggregateInfoName;
        this.windowBuilderName = windowBuilderName;
    }


    /**
     * Constructor with single input stream descriptor and no variable name.
     *
     * @param streamName            String id of the Stream.
     * @param inputStreamDescriptor StreamDescriptor of the previous stream.
     * @param tupleInfoDescriptor   TupleInfoDescriptor describing the output Tuple of the Stream.
     * @param tupleInfoFieldName    String name of the variable holding the TupleInfoDescriptor.
     * @param context               ModelContextassociated to the Stream in the query, if any.
     */
    public AggregationDescriptor(
            String streamName,
            StreamDescriptor inputStreamDescriptor,
            TupleInfoDescriptor tupleInfoDescriptor,
            String tupleInfoFieldName,
            ModelContext context,
            boolean emitFull,
            String groupAggregateInfoName,
            String windowBuilderName) {

        super(streamName, inputStreamDescriptor,
                tupleInfoDescriptor, tupleInfoFieldName,
                context);
        this.emitFull = emitFull;
        this.groupAggregateInfoName = groupAggregateInfoName;
        this.windowBuilderName = windowBuilderName;
    }


    /**
     * Constructor with multiple input stream descriptors.
     *
     * @param streamName             String id of the Stream.
     * @param inputStreamDescriptors StreamDescriptor's of the previous streams.
     * @param streamVariableName     String name of the variable holding the Stream in the generated code.
     * @param tupleInfoDescriptor    TupleInfoDescriptor describing the output Tuple of the Stream.
     * @param tupleInfoFieldName     String name of the variable holding the TupleInfoDescriptor.
     * @param context                ModelContextassociated to the Stream in the query, if any.
     */
    public AggregationDescriptor(
            String streamName,
            StreamDescriptor[] inputStreamDescriptors,
            String streamVariableName,
            TupleInfoDescriptor tupleInfoDescriptor,
            String tupleInfoFieldName,
            ModelContext context,
            boolean emitFull,
            String groupAggregateInfoName,
            String windowBuilderName) {

        super(streamName, inputStreamDescriptors,
                streamVariableName, tupleInfoDescriptor,
                tupleInfoFieldName, context);
        this.emitFull = emitFull;
        this.groupAggregateInfoName = groupAggregateInfoName;
        this.windowBuilderName = windowBuilderName;
    }


    public boolean isEmitFull() {
        return this.emitFull;
    }


    public String getGroupAggregateInfoName() {
        return this.groupAggregateInfoName;
    }


    public String getWindowBuilderName() {
        return this.windowBuilderName;
    }

}
