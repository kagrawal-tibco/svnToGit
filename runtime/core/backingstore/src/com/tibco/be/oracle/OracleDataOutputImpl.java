package com.tibco.be.oracle;

import java.io.DataOutput;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 4, 2006
 * Time: 3:46:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class OracleDataOutputImpl implements DataOutput {
    StructDescriptor oracleType;
    Object [] attributes;
    int curIndex=0;
    public final static Character TRUE  = new Character('T');
    public final static Character FALSE = new Character('F');

    /**
     *
     * @param oracleType
     */
    public OracleDataOutputImpl (StructDescriptor oracleType) throws java.sql.SQLException{
        this.oracleType=oracleType;
        attributes = new Object[oracleType.getLength()] ;
    }

    public void write(int b) throws IOException {
        attributes[curIndex++]= new Integer(b);
    }

    public void write(byte b[]) throws IOException {
        //attributes[]
    }

    public void write(byte b[], int off, int len) throws IOException {

    }

    public void writeBoolean(boolean v) throws IOException {
        attributes[curIndex++]= v ? TRUE: FALSE;
    }

    public void writeByte(int v) throws IOException {

    }

    public void writeShort(int v) throws IOException {
        attributes[curIndex++]= new java.lang.Short((short) v);
    }

    public void writeChar(int v) throws IOException {
        attributes[curIndex++]= new Character((char) v);
    }

    public void writeInt(int v) throws IOException {
        attributes[curIndex++]= new Integer(v);
    }

    public void writeLong(long v) throws IOException {
        attributes[curIndex++]= new Long(v);
    }

    public void writeFloat(float v) throws IOException {
        attributes[curIndex++]= new Float(v);
    }

    public void writeDouble(double v) throws IOException {
        attributes[curIndex++]= new Double(v);
    }

    public void writeBytes(String s) throws IOException {

    }

    public void writeChars(String s) throws IOException {
        attributes[curIndex++]= new String(s);
    }

    public void writeUTF(String str) throws IOException {
        attributes[curIndex++]= str;
    }

    public Datum toOracle(Connection sqlConnection) throws SQLException {
        return new STRUCT(oracleType, sqlConnection, attributes);
    }
}
