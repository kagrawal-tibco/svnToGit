package com.tibco.cep.query.exec.descriptors.impl;

import java.util.HashSet;
import java.util.Set;

import com.tibco.cep.query.exec.descriptors.StreamDescriptor;
import com.tibco.cep.query.exec.descriptors.TupleInfoDescriptor;
import com.tibco.cep.query.model.ModelContext;

/*
 * User: nprade
 * Date: Feb 7, 2008
 * Time: 5:16:28 PM
 */
public class StreamDescriptorImpl implements StreamDescriptor {

    protected TupleInfoDescriptor tupleInfoDescriptor;
    protected String tupleInfoFieldName;
    protected String streamName;
    protected String streamVariableName;
    protected Set<StreamDescriptor> inputStreamDescriptors;
    protected ModelContext context;
    protected Set<StreamDescriptor> outputStreamDescriptors;


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
    public StreamDescriptorImpl(
            String streamName,
            StreamDescriptor inputStreamDescriptor,
            String streamVariableName,
            TupleInfoDescriptor tupleInfoDescriptor,
            String tupleInfoFieldName,
            ModelContext context) {
        this(streamName,
                (null == inputStreamDescriptor) ? new StreamDescriptor[0]
                        : new StreamDescriptor[]{inputStreamDescriptor},
                streamVariableName,
                tupleInfoDescriptor, tupleInfoFieldName, context);
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
    public StreamDescriptorImpl(
            String streamName,
            StreamDescriptor inputStreamDescriptor,
            TupleInfoDescriptor tupleInfoDescriptor,
            String tupleInfoFieldName,
            ModelContext context) {
        this(streamName, inputStreamDescriptor, null, tupleInfoDescriptor, tupleInfoFieldName, context);
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
    public StreamDescriptorImpl(
            String streamName,
            StreamDescriptor[] inputStreamDescriptors,
            String streamVariableName,
            TupleInfoDescriptor tupleInfoDescriptor,
            String tupleInfoFieldName,
            ModelContext context) {
        this.context = context;
        this.inputStreamDescriptors = new HashSet<StreamDescriptor>();
        this.outputStreamDescriptors = new HashSet<StreamDescriptor>();
        this.streamName = streamName;
        this.streamVariableName = streamVariableName;
        this.tupleInfoDescriptor = tupleInfoDescriptor;
        this.tupleInfoFieldName = tupleInfoFieldName;

        if (null == inputStreamDescriptors) {
            this.inputStreamDescriptors = null;
        } else {
            for (StreamDescriptor inputDescriptor : inputStreamDescriptors) {
                this.inputStreamDescriptors.add(inputDescriptor);
                inputDescriptor.addOutputStreamDescriptor(this);
            }
        }
    }


    public void addOutputStreamDescriptor(StreamDescriptor descriptor) {
        this.outputStreamDescriptors.add(descriptor);
    }


    public ModelContext getContext() {
        return this.context;
    }


    public StreamDescriptor[] getInputStreamDescriptors() {
        return this.inputStreamDescriptors.toArray(new StreamDescriptor[this.inputStreamDescriptors.size()]);
    }


    public String getName() {
        return this.streamName;
    }


    public StreamDescriptor[] getOutputStreamDescriptors() {
        return this.outputStreamDescriptors.toArray(new StreamDescriptor[this.outputStreamDescriptors.size()]);
    }


    public String getVariableName() {
        return this.streamVariableName;
    }


    public TupleInfoDescriptor getTupleInfoDescriptor() {
        return this.tupleInfoDescriptor;
    }


    public String getTupleInfoFieldName() {
        return this.tupleInfoFieldName;
    }


    public void setContext(ModelContext context) {
        this.context = context;
    }


    public void setName(String streamName) {
        this.streamName = streamName;
    }


    public void setVariableName(String streamVariableName) {
        this.streamVariableName = streamVariableName;
    }


    public void setTupleInfoDescriptor(TupleInfoDescriptor tupleInfoDescriptor) {
        this.tupleInfoDescriptor = tupleInfoDescriptor;
    }


    public void setTupleInfoFieldName(String tupleInfoFieldName) {
        this.tupleInfoFieldName = tupleInfoFieldName;
    }


}
