package com.tibco.cep.runtime.model.serializers;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


public class SerializableHelper {

    public static SerializableLite readSerializableLite(DataInput dataInput) throws IOException {
    	SerializerFactory fac = SerializerFactoryFactory.getInstance();
    	Serializer s = fac.newSerializer();
    	return (SerializableLite) s.readSerializableLite(dataInput);
    }


    public static SerializableLite readSerializableLite(DataInput dataInput, ClassLoader classLoader) throws IOException {
    	SerializerFactory fac = SerializerFactoryFactory.getInstance();
    	Serializer s = fac.newSerializer();
    	return (SerializableLite) s.readSerializableLite(dataInput, classLoader);
    }

    public static void writeSerializableLite(DataOutput dataOutput, SerializableLite lite) throws IOException {
    	SerializerFactory fac = SerializerFactoryFactory.getInstance();
    	Serializer s = fac.newSerializer();
    	s.writeSerializableLite(dataOutput, lite);
    }
    


}
