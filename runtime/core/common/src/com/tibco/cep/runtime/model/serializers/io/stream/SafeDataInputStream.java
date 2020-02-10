
/**
 * Created by IntelliJ IDEA.
 * User: pgowrish
 * Date: 10/12/11
 * Time: 3:38 PM
 * To change this template use File | Settings | File Templates.
 */
package com.tibco.cep.runtime.model.serializers.io.stream;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SafeDataInputStream extends DataInputStream {

    public SafeDataInputStream(InputStream in) {
        super(in);
    }

    public String readSafeUTF() throws IOException {
        //Determine the type of UTF8 being read
        byte utfType = super.readByte();
        if (utfType == StreamConstants.UTF8_LARGE) {
            int readLength = super.readInt();
            byte[] byteBuffer = new byte[readLength];
            int resultLength = super.read(byteBuffer,0,readLength);
            return (new String(byteBuffer,StreamConstants.UTF8_TYPE));
        } else if (utfType == StreamConstants.UTF8_REGULAR) {
            return (super.readUTF());
        } else {
            return null;
        }
    }
}
