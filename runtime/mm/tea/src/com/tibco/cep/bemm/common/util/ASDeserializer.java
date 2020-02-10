package com.tibco.cep.bemm.common.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created with IntelliJ IDEA. User: mgharat Date: 5/14/14 Time: 4:08 PM To
 * change this template use File | Settings | File Templates.
 */
public class ASDeserializer implements Deserializer {

	// public static final byte SERIALIZABLE_MAGIC_HEADER = 0x55;

	@Override
	public Object decode(byte[] bytes) throws IOException, ClassNotFoundException, IllegalAccessException,
			InstantiationException {

		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = new ObjectInputStream(bis);

		Object retVal = null;

		try {
			retVal = ois.readObject();
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				// todo Warn
			}
		}

		return retVal;
		// ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		// SafeDataInputStream dis = new SafeDataInputStream(bis);
		//
		// SerializableLite serializableLite = null;
		//
		// try {
		// if (/*bytes[0] == SERIALIZABLE_MAGIC_HEADER*/true) {
		// //dis.read();
		//
		// String fqn = dis.readUTF();
		//
		// Class klass = Class.forName(fqn);
		//
		// serializableLite = (SerializableLite) klass.newInstance();
		// serializableLite.readExternal(dis);
		//
		// }
		// }
		// finally {
		// try {
		// if (dis != null) {
		// dis.close();
		// }
		// }
		// catch (IOException e) {
		// // logger.log(Level.WARNING,
		// "Error occurred while closing the output stream", e);
		//
		// e.printStackTrace();
		// }
		// }
		//
		// return serializableLite;

	}
}
