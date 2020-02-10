package com.tibco.cep.query.exec.descriptors.impl;

import com.tibco.cep.query.exec.descriptors.TupleInfoDescriptor;
import com.tibco.cep.query.model.AcceptType;
import com.tibco.cep.query.model.ModelContext;
import com.tibco.cep.query.stream.impl.rete.expression.ReteEntityFilter;

/*
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Mar 7, 2008
 * Time: 2:24:42 PM
 */

/**
 * StreamDescriptor for a Source.
 */
public class SourceDescriptor extends EndPointDescriptor {

    protected final String alias;
    protected final AcceptType acceptType;
    protected ReteEntityFilter filter;

    /**
     * @param streamName          String id of the Stream.
     * @param tupleInfoDescriptor TupleInfoDescriptor describing the output Tuple of the Stream.
     * @param tupleInfoFieldName  String name of the variable holding the TupleInfoDescriptor.
     * @param context             ModelContextassociated to the Stream in the query, if any.
     * @param alias               String alias associated to the source.
     */
    public SourceDescriptor(
            String streamName,
            TupleInfoDescriptor tupleInfoDescriptor,
            String tupleInfoFieldName,
            ModelContext context,
            String alias,
            AcceptType acceptType) {

        super(streamName, null,
                tupleInfoDescriptor, tupleInfoFieldName, context);

        this.alias = alias;
        this.acceptType = acceptType;
        this.filter = null;
    }


    /**
     *
     * @return AcceptType.
     */
    public AcceptType getAcceptType() {
        return this.acceptType;
    }


    /**
     * Gets the alias associated to the source.
     *
     * @return String alias associated to the source.
     */
    public String getAlias() {
        return alias;
    }


    /**
     * Gets the class name of the entities in the tuple output by the source.
     *
     * @return String class name of the entities in the tuple output by the source.
     */
    public String getEntityClassName() {
        return this.tupleInfoDescriptor.getColumns().iterator().next().getClassName();
    }


    public ReteEntityFilter getFilter() {
        return this.filter;
    }


    public void setFilter(ReteEntityFilter filter) {
        this.filter = filter;
    }

}

