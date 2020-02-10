package com.tibco.cep.runtime.model.serializers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/*
* Author: Ashwin Jayaprakash / Date: Dec 2, 2010 / Time: 12:00:02 PM
*/

/**
 * Default object codec that expects the object to be {@link Serializable} or something compatible.
 */
public class ObjectCodecHook {
    /**
     * @param object     Never null
     * @param columnName For reference only
     * @return
     * @throws Exception
     */
    public byte[] encode(Object object, String columnName) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        try {
            oos.writeObject(object);
        }
        finally {
            oos.close();
        }

        return bos.toByteArray();
    }

    /**
     * @param bytes      Never null
     * @param columnName For reference only
     * @return
     * @throws Exception
     */
    public Object decode(byte[] bytes, String columnName) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);

        Object retVal = null;

        try {
            retVal = ois.readObject();
        }
        finally {
            try {
                ois.close();
            }
            catch (IOException e) {
                //todo Warn
            }
        }

        return retVal;
    }
}
