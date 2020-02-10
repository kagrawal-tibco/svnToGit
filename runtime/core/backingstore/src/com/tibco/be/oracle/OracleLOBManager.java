package com.tibco.be.oracle;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import oracle.sql.BLOB;
import oracle.sql.CLOB;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Apr 6, 2009
 * Time: 12:08:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class OracleLOBManager {
    static ConcurrentMap<Thread, HashSet> m_lobs= new ConcurrentHashMap<Thread, HashSet>(256);

    private static void _registerCLOB(CLOB clob) {
        Thread t=Thread.currentThread();
        HashSet lobs=m_lobs.get(t);
        if (lobs == null) {
            lobs = new HashSet();
            m_lobs.put(t, lobs);
        }
        lobs.add(clob);
    }

    private static void _registerBLOB(BLOB blob) {
        Thread t=Thread.currentThread();
        HashSet lobs=m_lobs.get(t);
        if (lobs == null) {
            lobs = new HashSet();
            m_lobs.put(t, lobs);
        }
        lobs.add(blob);
    }

    public static void free() throws SQLException {
        HashSet lobs=m_lobs.get(Thread.currentThread());
        if (lobs != null) {
            for (Object obj : lobs) {
                if (obj instanceof CLOB) {
                    CLOB clob = (CLOB) obj;
                    if (clob.isOpen() && (!clob.isEmptyLob())) {
                        clob.close();
                    }
                    if (clob.isTemporary())
                        clob.freeTemporary();
                } else if (obj instanceof BLOB) {
                    BLOB blob = (BLOB) obj;
                    if (blob.isOpen() && (!blob.isEmptyLob()))
                        blob.close();
                    if (blob.isTemporary())
                        blob.freeTemporary();
                }
            }
            lobs.clear();
        }
    }

    public static void freeList(List list) throws SQLException {
        if (list == null)
            return;
        HashSet lobs = m_lobs.get(Thread.currentThread());
        for (Object obj : list) {
            if (obj instanceof CLOB) {
                CLOB clob = (CLOB) obj;
                if (clob.isOpen() && (!clob.isEmptyLob())) {
                    clob.close();
                }
                if (clob.isTemporary())
                    clob.freeTemporary();
            } else if (obj instanceof BLOB) {
                BLOB blob = (BLOB) obj;
                if (blob.isOpen() && (!blob.isEmptyLob()))
                    blob.close();
                if (blob.isTemporary())
                    blob.freeTemporary();
            }

            //TODO SS : I believe from Puneet/Amitabh's code this call is made from insert/update.
            //If other threads have this clob/blob and am removing it from the current thread.
            //I am not sure why it is a thread specific datastructure.
            if (lobs != null)
                lobs.remove(obj);
        }
    }

    public static Clob createCLOB(Connection oracleConn, String s) throws SQLException, IOException {
        CLOB clob=null;
        try {
            clob=CLOB.createTemporary(oracleConn, true, CLOB.DURATION_SESSION);
            clob.open(CLOB.MODE_READWRITE);
            Writer writer= clob.setCharacterStream(0);
            writer.write(s);
            //os.write(bytes);
            writer.flush();
            writer.close();
            return clob;
        } finally {
            if ((clob != null) && clob.isTemporary()) {
                _registerCLOB(clob);
            }
        }
    }

    public static Blob createBLOB(Connection oracleConn, byte[] b) throws SQLException, IOException {
        BLOB blob=null;
        try {
            blob=BLOB.createTemporary(oracleConn, true, BLOB.DURATION_SESSION);
            blob.open(CLOB.MODE_READWRITE);
            OutputStream os= blob.setBinaryStream(0);
            os.write(b);
            os.close();
            return blob;
        } finally {
            if ((blob != null) && blob.isTemporary()) {
                _registerBLOB(blob);
            }
        }
    }
}
