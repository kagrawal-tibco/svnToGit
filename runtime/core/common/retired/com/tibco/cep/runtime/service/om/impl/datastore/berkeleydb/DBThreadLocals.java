package com.tibco.cep.runtime.service.om.impl.datastore.berkeleydb;

import com.sleepycat.je.DatabaseEntry;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Oct 26, 2004
 * Time: 5:11:26 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * This class provides support for the variables that we do not need to create again and again in loops.
 * But they need to be threadlocals which can be reused safely.
 */
public class DBThreadLocals extends Object {

    protected DatabaseEntry keyEntry;
    protected ByteArrayOutputStream keybytes;
    protected DataOutputStream keyos;

    protected DatabaseEntry dataEntry;
    //protected EntityDatabaseEntry dataEntry2nd;
    protected ByteArrayOutputStream databytes;
    protected DataOutputStream dataos;

    protected DBThreadLocals() {
        keyEntry = new DatabaseEntry();
        keybytes = new ByteArrayOutputStream();
        keyos = new DataOutputStream(keybytes);

        dataEntry = new DatabaseEntry();
        //dataEntry2nd = new EntityDatabaseEntry();
        databytes = new ByteArrayOutputStream();
        dataos = new DataOutputStream(databytes);
    }

    /**
     * Clear all variables. This method should be called before reusing the objects.
     */
    public void clearAll() {
        keyEntry.setData(null);
        keybytes.reset();
        dataEntry.setData(null);
        //dataEntry2nd.setData(null);
        databytes.reset();
    }
}
