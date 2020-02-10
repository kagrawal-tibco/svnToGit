package com.tibco.cep.query.exec.descriptors.impl;

import com.tibco.cep.query.exec.descriptors.StreamDescriptor;
import com.tibco.cep.query.exec.descriptors.TupleInfoDescriptor;
import com.tibco.cep.query.model.ModelContext;

/*
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Mar 7, 2008
 * Time: 2:23:37 PM
 */

/**
 * Common parent of for source and sink descriptors.
 */
public abstract class EndPointDescriptor extends StreamDescriptorImpl {
    StreamDescriptor internalStreamDescriptor;

    public EndPointDescriptor(
            String streamName,
            StreamDescriptor inputStreamDescriptor,
            TupleInfoDescriptor tupleInfoDescriptor,
            String tupleInfoFieldName,
            ModelContext context) {
        super(streamName, inputStreamDescriptor, tupleInfoDescriptor, tupleInfoFieldName, context);
        internalStreamDescriptor = new InternalStreamDescriptor();
    }

    public StreamDescriptor getInternalStreamDescriptor() {
        return this.internalStreamDescriptor;
    }

    protected class InternalStreamDescriptor implements StreamDescriptor {

        public void addOutputStreamDescriptor(StreamDescriptor descriptor) {
            EndPointDescriptor.this.addOutputStreamDescriptor(descriptor);
        }

        public ModelContext getContext() {
            return EndPointDescriptor.this.getContext();
        }

        public StreamDescriptor[] getInputStreamDescriptors() {
            return EndPointDescriptor.this.getInputStreamDescriptors();
        }

        public String getName() {
            return EndPointDescriptor.this.getName();
        }

        public StreamDescriptor[] getOutputStreamDescriptors() {
            return EndPointDescriptor.this.getOutputStreamDescriptors();
        }

        public TupleInfoDescriptor getTupleInfoDescriptor() {
            return EndPointDescriptor.this.getTupleInfoDescriptor();
        }

        public String getTupleInfoFieldName() {
            return EndPointDescriptor.this.getTupleInfoFieldName();
        }

        public String getVariableName() {
            return EndPointDescriptor.this.getVariableName() + ".getInternalStream()";
        }

        public void setVariableName(String streamVariableName) {
            throw new UnsupportedOperationException();
        }
    }
}
