package com.tibco.cep.runtime.hbase.cacheaside.Interfaces;

import org.apache.hadoop.hbase.util.Bytes;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 7/2/13
 * Time: 5:45 PM
 * To change this template use File | Settings | File Templates.
 */
public interface HConstants_old {

    final String STRING = "string";
    final String LONG = "long";
    final String FLOAT = "float";
    final String DOUBLE = "double";
    final String INTEGER = "integer";
    final String INDEXTABLE = "indexTable";
    final String ID = "id";
    final String EXTID = "extId";
    final byte[] EXTIDBYES = Bytes.toBytes(EXTID);
    final byte[] IDBYTES= Bytes.toBytes(ID);
    final byte[] INFOBYTES = Bytes.toBytes("info");
}
