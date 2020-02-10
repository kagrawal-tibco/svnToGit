
/**
 * Created by IntelliJ IDEA.
 * User: pgowrish
 * Date: 10/12/11
 * Time: 3:13 PM
 * To change this template use File | Settings | File Templates.
 */
package com.tibco.cep.runtime.model.serializers.io.stream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SafeDataOutputStream extends DataOutputStream {

    public SafeDataOutputStream(OutputStream out) {
        super(out);
    }

    public void writeSafeUTF(String str) throws IOException {
		// Size computed while accounting for char->byte encoding difference [org:str.length]
    	long size = str.getBytes(StreamConstants.UTF8_TYPE).length;
        if (size < StreamConstants.MAX_UTF_LEN) {
                //Write a single preamble byte and set its value to UTF8_REGULAR
                //This is to indicate that this is a UTF string < 64k
                super.writeByte(StreamConstants.UTF8_REGULAR);
                super.writeUTF(str);
        } else {
                byte[] byteArray = str.getBytes(StreamConstants.UTF8_TYPE);
                //Write a single preamble byte and set its value to UTF8_LARGE
                //This is to indicate that this is a UTF string > 64k
                super.writeByte(StreamConstants.UTF8_LARGE);
                //Write the length as an integer
                super.writeInt(byteArray.length);
                //Write the actual UTF string as a byte array
                super.write(byteArray);
        }
    }
}
