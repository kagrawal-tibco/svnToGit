package com.tibco.cep.query.exec.descriptors.impl;

import com.tibco.cep.query.exec.descriptors.StreamDescriptor;
import com.tibco.cep.query.exec.descriptors.TupleInfoDescriptor;
import com.tibco.cep.query.model.ModelContext;

/*
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Mar 4, 2008
 * Time: 7:15:00 PM
 */

/**
 * StreamDescriptor for a SortedStream.
 */
public class SortedStreamDescriptor extends StreamDescriptorImpl {


    protected String comparatorsName;
    protected String extractorsName;
    protected String sortInfoName;


    /**
     * @param streamName            String id of the Stream.
     * @param inputStreamDescriptor StreamDescriptor of the previous stream.
     * @param tupleInfoDescriptor   TupleInfoDescriptor describing the output Tuple of the Stream.
     * @param tupleInfoFieldName    String name of the variable holding the TupleInfoDescriptor.
     * @param context               ModelContextassociated to the Stream in the query, if any.
     * @param sortInfoName          String name of the SortInfo.
     * @param extractorsName        String name of the array of TupleValueExtractor's.
     * @param comparatorsName       String name of the array of Comparator's.
     */
    public SortedStreamDescriptor(
            String streamName,
            StreamDescriptor inputStreamDescriptor,
            TupleInfoDescriptor tupleInfoDescriptor,
            String tupleInfoFieldName,
            ModelContext context,
            String sortInfoName,
            String extractorsName,
            String comparatorsName) {

        super(streamName, inputStreamDescriptor,
                tupleInfoDescriptor, tupleInfoFieldName, context);

        this.comparatorsName = comparatorsName;
        this.extractorsName = extractorsName;
        this.sortInfoName = sortInfoName;
    }


    /**
     * @return String name of the array of Comparator's
     */
    public String getComparatorsName() {
        return this.comparatorsName;
    }


    /**
     * @return String name of the array of TupleValueExtractor's.
     */
    public String getExtractorsName() {
        return this.extractorsName;
    }


    /**
     * @return String name of the SortInfo.
     */
    public String getSortInfoName() {
        return this.sortInfoName;
    }


}
