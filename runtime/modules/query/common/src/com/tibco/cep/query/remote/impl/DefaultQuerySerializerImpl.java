package com.tibco.cep.query.remote.impl;

import java.io.DataOutput;

import com.tibco.cep.query.remote.RemoteQuerySerializer;
import com.tibco.cep.query.service.Query;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Nov 16, 2007
 * Time: 7:04:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultQuerySerializerImpl implements RemoteQuerySerializer {
    private DataOutput dataOutput;
    private Query query;

    public DefaultQuerySerializerImpl(Query query, DataOutput dataOutput) {
        this.query = query;
        this.dataOutput = dataOutput;
    }

    /**
     * serialize the id generator to save the last id generated
     */
    public void serializeIdGenerator() {

    }

    /**
     * serialize OQL query string
     */
    public void serializeOql() {

    }

    /**
     * serialize the execution plan classes loaded by the classloader
     */
    public void serializeQueryClassLoader() {

    }

    /**
     * serialize the parsed query model
     */
    public void serializeQueryModel() {

    }

    /**
     * Ends the component serialization
     */
    public void endComponentSerialization() {

    }

    /**
     * Returns the data output object
     *
     * @return DataOutput
     */
    public DataOutput getDataOutput() {
        return this.dataOutput;
    }

    /**
     * Starts the component serialization
     */
    public void startComponentSerialization() {

    }
} // end QuerySerializerImpl
