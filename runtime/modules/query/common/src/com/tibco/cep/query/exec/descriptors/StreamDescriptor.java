package com.tibco.cep.query.exec.descriptors;

import com.tibco.cep.query.model.ModelContext;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Feb 8, 2008
 * Time: 8:02:16 PM
 * To change this template use File | Settings | File Templates.
 */
public interface StreamDescriptor {

    void addOutputStreamDescriptor(StreamDescriptor descriptor);

    ModelContext getContext();

    StreamDescriptor[] getInputStreamDescriptors();

    String getName();

    StreamDescriptor[] getOutputStreamDescriptors();

    TupleInfoDescriptor getTupleInfoDescriptor();

    String getTupleInfoFieldName();

    String getVariableName();

    void setVariableName(String streamVariableName);
}
