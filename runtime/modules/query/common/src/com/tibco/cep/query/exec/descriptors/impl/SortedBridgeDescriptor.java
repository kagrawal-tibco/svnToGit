package com.tibco.cep.query.exec.descriptors.impl;

import com.tibco.cep.query.exec.descriptors.StreamDescriptor;
import com.tibco.cep.query.exec.descriptors.TupleInfoDescriptor;
import com.tibco.cep.query.model.ModelContext;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Apr 1, 2008
* Time: 4:13:16 PM
*/

/**
 * StreamDescriptor for a SortedBridge.
 */
public class SortedBridgeDescriptor extends BridgeDescriptor {


    protected SortedStreamDescriptor sortedStreamDescriptor;


    public SortedBridgeDescriptor(String streamName,
                                  StreamDescriptor inputStreamDescriptor,
                                  TupleInfoDescriptor tupleInfoDescriptor,
                                  String tupleInfoFieldName,
                                  ModelContext context,
                                  StreamDescriptor setupStreamDescriptor,
                                  SortedStreamDescriptor sortedStreamDescriptor) {
        super(streamName, inputStreamDescriptor, tupleInfoDescriptor, tupleInfoFieldName, context, setupStreamDescriptor);
        this.sortedStreamDescriptor = sortedStreamDescriptor;
    }


    public SortedStreamDescriptor getSortedStreamDescriptor() {
        return this.sortedStreamDescriptor;
    }


}
