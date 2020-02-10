package com.tibco.cep.runtime.model.serializers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tibco.cep.runtime.model.serializers.io.stream.SafeDataInputStream;
import com.tibco.cep.runtime.model.serializers.io.stream.SafeDataOutputStream;

/*
* Author: Ashwin Jayaprakash / Date: Nov 29, 2010 / Time: 6:00:37 PM
*/

public class SerializableLiteCodecHook extends ObjectCodecHook {
    /**
     * {@value}
     */
    public static final byte SERIALIZABLE_MAGIC_HEADER = 0x55;

    protected ClassLoader classLoader;

    protected Logger logger;

    public SerializableLiteCodecHook(ClassLoader classLoader, Logger logger) {
        this.classLoader = classLoader;
        this.logger = logger;
    }

    @Override
    public byte[] encode(Object object, String columnName) throws Exception {
        if (object instanceof SerializableLite) {
            SerializableLite serializableLite = (SerializableLite) object;

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            SafeDataOutputStream dos = new SafeDataOutputStream(bos);

            dos.write(SERIALIZABLE_MAGIC_HEADER);
            dos.writeUTF(object.getClass().getName());
            serializableLite.writeExternal(dos);

            try {
                dos.close();
            }
            catch (IOException e) {
                logger.log(Level.WARNING, "Error occured while closing the output stream", e);
            }

            return bos.toByteArray();
        }

        return super.encode(object, columnName);
    }

    @Override
    public Object decode(byte[] bytes, String columnName) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        SafeDataInputStream dis = new SafeDataInputStream(bis);

        try {
//Keep this in sync with getSerializableLiteDataInputStream()
            if (bytes[0] == SERIALIZABLE_MAGIC_HEADER) {
                //Swallow header.
                dis.read();

                String fqn = dis.readUTF();

                Class klass = Class.forName(fqn, true, classLoader);

                SerializableLite serializableLite = (SerializableLite) klass.newInstance();
                serializableLite.readExternal(dis);

                return serializableLite;
            }
        }
        finally {
            try {
                if (dis != null) {
                    dis.close();
                }
            }
            catch (IOException e) {
                logger.log(Level.WARNING, "Error occurred while closing the output stream", e);
            }
        }

        return super.decode(bytes, columnName);
    }

    
    public static boolean isSerializableLite(byte[] bytes) {
        return bytes != null && bytes.length > 0 && bytes[0] == SERIALIZABLE_MAGIC_HEADER;
    }
    
	//keep this in sync with the order of fields in decode()
	//TODO add try-with-resources for Java 7
	public static DataInputStream getSerializableLiteDataInputStream(byte[] bytes) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		SafeDataInputStream dis = new SafeDataInputStream(bis);
		try  {
			// Swallow header.
			dis.read();
			// class name
			dis.readUTF();
			return dis;
		} finally {
			if (dis != null) {
				dis.close();
			}
		}
	}
}
