package com.tibco.be.oracle;

import java.io.DataInput;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 4, 2006
 * Time: 3:46:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class OracleDataInputImpl implements DataInput {

    public void readFully(byte b[]) throws IOException {

    }

    public void readFully(byte b[], int off, int len) throws IOException {

    }

    public int skipBytes(int n) throws IOException {
        return 0;
    }

    public boolean readBoolean() throws IOException {
        return false;
    }

    public byte readByte() throws IOException {
        return 0;
    }

    public int readUnsignedByte() throws IOException {
        return 0;
    }

    public short readShort() throws IOException {
        return 0;
    }

    public int readUnsignedShort() throws IOException {
        return 0;
    }

    public char readChar() throws IOException {
        return 0;
    }

    public int readInt() throws IOException {
        return 0;
    }

    public long readLong() throws IOException {
        return 0;
    }

    public float readFloat() throws IOException {
        return 0;
    }

    public double readDouble() throws IOException {
        return 0;
    }

    public String readLine() throws IOException {
        return null;
    }

    public String readUTF() throws IOException {
        return null;
    }
}
