package com.tibco.cep.query.exec.descriptors.impl;

import com.tibco.cep.query.exec.descriptors.StreamDescriptor;
import com.tibco.cep.query.exec.descriptors.TupleInfoDescriptor;
import com.tibco.cep.query.model.ModelContext;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Aug 20, 2008
* Time: 6:42:25 PM
*/
public class JoinDescriptor
        extends StreamDescriptorImpl {


    private String expressionFieldName;


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
    public JoinDescriptor(
            String streamName,
            StreamDescriptor inputStreamDescriptor,
            String streamVariableName,
            TupleInfoDescriptor tupleInfoDescriptor,
            String tupleInfoFieldName,
            ModelContext context,
            String expressionFieldName) {

        super(streamName, inputStreamDescriptor, streamVariableName, tupleInfoDescriptor, tupleInfoFieldName, context);
        this.expressionFieldName = expressionFieldName;
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
    public JoinDescriptor(
            String streamName,
            StreamDescriptor inputStreamDescriptor,
            TupleInfoDescriptor tupleInfoDescriptor,
            String tupleInfoFieldName,
            ModelContext context,
            String expressionFieldName) {
        super(streamName, inputStreamDescriptor, tupleInfoDescriptor, tupleInfoFieldName, context);
        this.expressionFieldName = expressionFieldName;
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
    public JoinDescriptor(
            String streamName,
            StreamDescriptor[] inputStreamDescriptors,
            String streamVariableName,
            TupleInfoDescriptor tupleInfoDescriptor,
            String tupleInfoFieldName,
            ModelContext context,
            String expressionFieldName) {
        super(streamName, inputStreamDescriptors, streamVariableName, tupleInfoDescriptor, tupleInfoFieldName, context);
        this.expressionFieldName = expressionFieldName;
    }


    public String getExpressionFieldName() {
        return this.expressionFieldName;
    }

}
