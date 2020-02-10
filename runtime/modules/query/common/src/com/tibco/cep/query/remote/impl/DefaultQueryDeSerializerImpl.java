package com.tibco.cep.query.remote.impl;

import java.io.DataInput;

import com.tibco.cep.query.remote.RemoteQueryDeSerializer;
import com.tibco.cep.query.service.Query;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Nov 16, 2007
 * Time: 7:06:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultQueryDeSerializerImpl implements RemoteQueryDeSerializer {
        DataInput dataInput;

        public DefaultQueryDeSerializerImpl(Query query, DataInput dataInput) {
            this.dataInput = dataInput;
        }

        /**
         * deserialize the id generator to save the last id generated
         */
        public void deserializeIdGenerator() {

        }

        /**
         * deserialize the query oql string
         */
        public void deserializeOql() {

        }

        /**
         * serialize the execution plan classes loaded by the classloader
         */
        public void deserializeQueryClassLoader() {

        }

        /**
         * deserialize the parsed query model
         */
        public void deserializeQueryModel() {

        }

        /**
         * Ends the component deserialization
         */
        public void endComponentDeSerialization() {

        }

        /**
         * Returns the data input object
         *
         * @return DataInput
         */
        public DataInput getDataInput() {
            return this.dataInput;
        }

        /**
         * Starts the component deserialization
         */
        public void startComponentDeSerialization() {

        }
    } // end QueryDeSerializerImpl
