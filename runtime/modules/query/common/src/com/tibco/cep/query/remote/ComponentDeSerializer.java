package com.tibco.cep.query.remote;

import java.io.DataInput;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Nov 16, 2007
 * Time: 6:58:13 PM
 * To change this template use File | Settings | File Templates.
 */
/**
     * Component deserialization interface
     */
    public interface ComponentDeSerializer {
        /**
         * Returns the data input object
         * @return DataInput
         */
        DataInput getDataInput();

        /**
         * Starts the component deserialization
         */
        void startComponentDeSerialization();

        /**
         * Ends the component deserialization
         */
        void endComponentDeSerialization();
    }
