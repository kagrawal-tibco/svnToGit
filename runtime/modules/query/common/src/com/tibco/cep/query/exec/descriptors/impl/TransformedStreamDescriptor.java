package com.tibco.cep.query.exec.descriptors.impl;

import com.tibco.cep.query.exec.descriptors.StreamDescriptor;
import com.tibco.cep.query.exec.descriptors.TupleInfoDescriptor;
import com.tibco.cep.query.model.ModelContext;

/*
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Mar 4, 2008
 * Time: 7:35:55 PM
 */

/**
 * StreamDescriptor for a TransformedStream.
 */
public class TransformedStreamDescriptor extends StreamDescriptorImpl {


    private String transformInfoName;


    /**
     * @param streamName            String id of the Stream.
     * @param inputStreamDescriptor StreamDescriptor of the previous stream.
     * @param tupleInfoDescriptor   TupleInfoDescriptor describing the output Tuple of the Stream.
     * @param tupleInfoFieldName    String name of the variable holding the TupleInfoDescriptor.
     * @param context               ModelContextassociated to the Stream in the query, if any.
     * @param transformInfoName     String name of the TransformInfo.
     */
    public TransformedStreamDescriptor(
            String streamName,
            StreamDescriptor inputStreamDescriptor,
            TupleInfoDescriptor tupleInfoDescriptor,
            String tupleInfoFieldName,
            ModelContext context,
            String transformInfoName) {

        super(streamName, inputStreamDescriptor,
                tupleInfoDescriptor, tupleInfoFieldName, context);

        this.transformInfoName = transformInfoName;
    }


    /**
     * @return String name of the TransformInfo.
     */
    public String getTransformInfoName() {
        return this.transformInfoName;
    }

}
