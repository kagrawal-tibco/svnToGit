package com.tibco.cep.impl.common.resource;

import static com.tibco.cep.util.Helper.$processId;
import static com.tibco.cep.util.Helper.$uniqueMachineId;

import java.util.Random;

import com.tibco.cep.common.resource.Id;

/*
* Author: Ashwin Jayaprakash / Date: Sep 1, 2010 / Time: 4:03:34 PM
*/

public class UID implements Id {
    protected transient static Random random = new Random();

    protected String id;

    public UID() {
    }

    /**
     * Simple constructor. Expects the String parameter to be unique.
     *
     * @param id
     */
    public UID(String id) {
        this.id = id;
    }

    public UID(String machineId, String processId, long additionalQualifier) {
        this.id = machineId + "." + processId + "." + additionalQualifier;
    }

    @Override
    public String getAsString() {
        return id;
    }

    /**
     * @return A unique Id that is unique across machines/processes and also very likely inside the same process.
     * @see #UID(String, String, long)
     */
    public static UID makeUID() {
        String m = $uniqueMachineId();
        String p = $processId();

        long l = System.currentTimeMillis();
        l = l ^ System.nanoTime();
        l = l ^ random.nextInt();

        return new UID(m, p, l);
    }

    //-------------

    @Override
    public String toString() {
        return getAsString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UID)) {
            return false;
        }

        UID uid = (UID) o;

        if (!id.equals(uid.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
