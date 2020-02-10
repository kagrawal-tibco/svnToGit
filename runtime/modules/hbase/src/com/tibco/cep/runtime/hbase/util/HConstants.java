package com.tibco.cep.runtime.hbase.util;

import org.apache.hadoop.hbase.util.Bytes;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 7/2/13
 * Time: 5:45 PM
 * To change this template use File | Settings | File Templates.
 */
public interface HConstants {

    String STRING = "string";
    String LONG = "long";
    String FLOAT = "float";
    String DOUBLE = "double";
    String INTEGER = "integer";
    String INDEXTABLE = "indexTable";
    String ID = "id";
    String EXTID = "extId";
    String COLFAMILY = "info";
    byte[] EXTIDBYES = Bytes.toBytes(EXTID);
    byte[] IDBYTES= Bytes.toBytes(ID);
    byte[] COLFAMILYBYTES = Bytes.toBytes(COLFAMILY);
}
