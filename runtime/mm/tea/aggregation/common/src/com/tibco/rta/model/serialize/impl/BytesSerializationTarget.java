package com.tibco.rta.model.serialize.impl;

import com.tibco.rta.model.serialize.SerializationTarget;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 21/12/12
 * Time: 3:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class BytesSerializationTarget implements SerializationTarget<byte[]> {



    @Override
    public void persist(File file, byte[] serializable) throws Exception {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(serializable);
        } catch (IOException ioe) {
            throw new Exception(ioe);
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    @Override
    public void persist(Writer writer, byte[] serializable) throws Exception {

    }

    public void persist(OutputStream outputStream, byte[] serializable) throws Exception {
        try {
            outputStream.write(serializable);
        } catch (IOException ioe) {
            throw new Exception(ioe);
        } finally {
            outputStream.close();
        }
    }
}
