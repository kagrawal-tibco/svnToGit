package com.tibco.cep.util;

import java.io.IOException;

/*
* Author: Ashwin Jayaprakash / Date: Apr 9, 2010 / Time: 3:42:38 PM
*/

public class ReusableDirectAccessByteArrayOS extends DirectAccessByteArrayOS {
    public ReusableDirectAccessByteArrayOS() {
    }

    public ReusableDirectAccessByteArrayOS(int size) {
        super(size);
    }

    /**
     * Does nothing. Preserves the array.
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        //No op.
    }
}