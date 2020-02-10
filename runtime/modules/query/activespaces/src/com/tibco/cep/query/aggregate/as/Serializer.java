package com.tibco.cep.query.aggregate.as;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 11/15/12
 * Time: 4:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class Serializer<T> {

    public Serializer() {
    }

    public byte[] serialize(T obj) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(obj);
        return b.toByteArray();
    }

    public T deSerialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream o = new ObjectInputStream(b);
        T readObject = (T) o.readObject();
        return readObject;
    }
}
