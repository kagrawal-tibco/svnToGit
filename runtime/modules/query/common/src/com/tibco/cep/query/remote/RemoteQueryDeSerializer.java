package com.tibco.cep.query.remote;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Nov 16, 2007
 * Time: 7:01:05 PM
 * To change this template use File | Settings | File Templates.
 */
 public interface RemoteQueryDeSerializer extends ComponentDeSerializer {
        /**
         * deserialize the query oql string
         */
        public void deserializeOql();

        /**
         * serialize the execution plan classes loaded by the classloader
         */
        public void deserializeQueryClassLoader();

        /**
         * deserialize the id generator to save the last id generated
         */
        public void deserializeIdGenerator();

        /**
         * deserialize the parsed query model
         */
        public void deserializeQueryModel();

    }
