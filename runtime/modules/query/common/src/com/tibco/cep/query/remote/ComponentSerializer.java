package com.tibco.cep.query.remote;

import java.io.DataOutput;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Nov 16, 2007
 * Time: 6:57:32 PM
 * To change this template use File | Settings | File Templates.
 */
/**
     * Component serialization interface
     */
    public interface ComponentSerializer {
        /**
         * Returns the data output object
         * @return DataOutput
         */
        DataOutput getDataOutput();

        /**
         * Starts the component serialization
         */
        void startComponentSerialization();

        /**
         * Ends the component serialization
         */
        void endComponentSerialization();
    }
