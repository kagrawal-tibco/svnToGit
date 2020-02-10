package com.tibco.cep.runtime.model.serializers.coherence;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.tangosol.util.ExternalizableHelper;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.serializers.VersionedObjectDataOutputStream;

/*
* Author: Ashwin Jayaprakash Date: Jan 14, 2009 Time: 1:55:55 PM
*/
public class ConceptBytesHandler {
    protected static ClassLoader classLoader;

    public static void init(ClassLoader beClassLoader) {
        ConceptBytesHandler.classLoader = beClassLoader;
    }

    public static ClassLoader getClassLoader() {
        return classLoader;
    }

    public static byte[] cloak(Concept value) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream outputStream = new DataOutputStream(bos);
        ExternalizableHelper.writeObject(outputStream, value);
        outputStream.flush();
        outputStream.close();

        return bos.toByteArray();
    }

    public static VersionedCloakedData cloakVersioned(Concept value) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        VersionedObjectDataOutputStream outputStream = new VersionedObjectDataOutputStream(bos);
        ExternalizableHelper.writeObject(outputStream, value);
        outputStream.flush();
        outputStream.close();

        int v = outputStream.getVersionStartPosition();
        byte[] bytes = bos.toByteArray();

        return new VersionedCloakedData(v, bytes);
    }

    public static Concept uncloak(byte[] bytes) {
        return (Concept) ExternalizableHelper.fromByteArray(bytes, classLoader);
    }

    //-----------

    public static class VersionedCloakedData {
        protected int version;

        protected byte[] bytes;

        public VersionedCloakedData(int version, byte[] bytes) {
            this.version = version;
            this.bytes = bytes;
        }

        public int getVersion() {
            return version;
        }

        public byte[] getBytes() {
            return bytes;
        }
    }
}
