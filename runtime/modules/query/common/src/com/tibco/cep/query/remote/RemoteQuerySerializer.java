package com.tibco.cep.query.remote;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Nov 16, 2007
 * Time: 7:00:23 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RemoteQuerySerializer extends ComponentSerializer {
        /**
         * serialize OQL query string
         */
        public void serializeOql();

        /**
         * serialize the execution plan classes loaded by the classloader
         */
        public void serializeQueryClassLoader();

        /**
         * serialize the id generator to save the last id generated
         */
        public void serializeIdGenerator();

        /**
         * serialize the parsed query model
         */
        public void serializeQueryModel();

    }
