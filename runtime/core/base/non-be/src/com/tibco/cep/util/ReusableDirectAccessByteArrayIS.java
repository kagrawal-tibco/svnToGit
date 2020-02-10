package com.tibco.cep.util;

import java.io.ByteArrayInputStream;

/*
* Author: Ashwin Jayaprakash / Date: Jul 27, 2010 / Time: 11:44:02 AM
*/

public class ReusableDirectAccessByteArrayIS extends ByteArrayInputStream {
    public ReusableDirectAccessByteArrayIS(byte[] buf) {
        super(buf);
    }

    public ReusableDirectAccessByteArrayIS(byte[] buf, int offset, int length) {
        super(buf, offset, length);
    }

    /**
     * Replaces the internal byte array an related fields to match the array provided. Similar to calling {@link
     * #ReusableDirectAccessByteArrayIS(byte[])}.
     *
     * @param buf
     */
    public synchronized void reuse(byte[] buf) {
        this.buf = buf;
        this.pos = 0;
        this.count = buf.length;
    }

    /**
     * Replaces the internal byte array an related fields to match the array provided. Similar to calling {@link
     * #ReusableDirectAccessByteArrayIS(byte[], int, int)}.
     *
     * @param buf
     * @param offset
     * @param length
     */
    public synchronized void reuse(byte[] buf, int offset, int length) {
        this.buf = buf;
        this.pos = offset;
        this.count = Math.min(offset + length, buf.length);
        this.mark = offset;
    }
}
