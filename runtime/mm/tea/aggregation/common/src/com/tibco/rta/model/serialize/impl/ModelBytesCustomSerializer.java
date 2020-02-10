package com.tibco.rta.model.serialize.impl;

import com.tibco.rta.Fact;
import com.tibco.rta.Key;
import com.tibco.rta.impl.FactKeyImpl;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 25/6/13
 * Time: 11:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class ModelBytesCustomSerializer {

    private ByteArrayOutputStream bytesOutputStream = new CustomByteArrayOutputStream();

    private DataOutputStream dataOutputStream = new DataOutputStream(bytesOutputStream);

    public byte[] serialize(List<Fact> facts) {
        try {
            for (Fact fact : facts) {
                writeFactDelimiter();
                Key key = fact.getKey();
                FactKeyImpl factKey = (FactKeyImpl) key;
                //Write Key
                writeKey(factKey);
                //Write number of attrs
                dataOutputStream.writeByte(fact.getAttributeNames().size());
                //Write attributes
                for (String attributeName : fact.getAttributeNames()) {
                    Object value = fact.getAttribute(attributeName);
                    writeAttributeDetails(attributeName, value);
                }
            }
            return bytesOutputStream.toByteArray();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                dataOutputStream.close();
                bytesOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void writeKey(FactKeyImpl factKey) throws IOException {
        String schemaName = factKey.getSchemaName();
        dataOutputStream.writeUTF(schemaName);
        //Write UID
        dataOutputStream.writeUTF(factKey.getUid());
    }

    private void writeAttributeDetails(String attributeName, Object value) throws IOException {
        dataOutputStream.writeUTF(attributeName);

        if (value instanceof Short) {
            dataOutputStream.writeShort((Short) value);
        } else if (value instanceof Byte) {
            dataOutputStream.writeByte((Byte) value);
        } else if (value instanceof Integer) {
            dataOutputStream.writeInt((Integer) value);
        } else if (value instanceof Long) {
            dataOutputStream.writeLong((Long) value);
        } else if (value instanceof Float) {
            dataOutputStream.writeFloat((Float) value);
        } else if (value instanceof Double) {
            dataOutputStream.writeDouble((Double) value);
        } else if (value instanceof String) {
            dataOutputStream.writeUTF(value.toString());
        }
    }

    private void writeFactDelimiter() throws IOException {
        dataOutputStream.writeByte('|');
    }

    private class CustomByteArrayOutputStream extends ByteArrayOutputStream {

        /**
         * No thread safe implementation is used. Also just returns same buffer in place of copy.
         * @return
         */
        @Override
        public byte[] toByteArray() {
            return buf;
        }
    }
}
