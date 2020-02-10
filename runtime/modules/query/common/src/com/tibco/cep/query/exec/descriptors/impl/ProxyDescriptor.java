package com.tibco.cep.query.exec.descriptors.impl;

import com.tibco.cep.query.exec.descriptors.StreamDescriptor;
import com.tibco.cep.query.exec.descriptors.TupleInfoDescriptor;
import com.tibco.cep.query.model.ModelContext;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Aug 20, 2008
* Time: 3:08:44 PM
*/
public class ProxyDescriptor extends StreamDescriptorImpl {

    private String inputName;


    /**
     * Constructor with single input stream descriptor and no variable name.
     *
     * @param streamName            String id of the Stream.
     * @param inputStreamDescriptor StreamDescriptor of the previous stream.
     * @param tupleInfoDescriptor   TupleInfoDescriptor describing the output Tuple of the Stream.
     * @param tupleInfoFieldName    String name of the variable holding the TupleInfoDescriptor.
     * @param context               ModelContextassociated to the Stream in the query, if any.
     */
    public ProxyDescriptor(
            String streamName,
            StreamDescriptor inputStreamDescriptor,
            TupleInfoDescriptor tupleInfoDescriptor,
            String tupleInfoFieldName,
            ModelContext context,
            String inputName) {
        super(streamName, inputStreamDescriptor, tupleInfoDescriptor, tupleInfoFieldName, context);
        this.inputName = inputName;
    }


    public String getInputName() {
        return inputName;
    }
        
}
