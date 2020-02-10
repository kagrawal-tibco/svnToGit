package com.tibco.cep.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/*
* Author: Ashwin Jayaprakash / Date: Apr 9, 2010 / Time: 3:42:38 PM
*/

public class DirectAccessByteArrayOS extends ByteArrayOutputStream {
    public DirectAccessByteArrayOS() {
    }

    public DirectAccessByteArrayOS(int size) {
        super(size);
    }

    /**
     * @return The actual internal byte[] instead of copying it to a new array.
     */
    @Override
    public byte[] toByteArray() {
        return buf;
    }

    /**
     * Sets the internal byte[] to null unlike the parent which does nothing..
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        buf = null;
    }
}
