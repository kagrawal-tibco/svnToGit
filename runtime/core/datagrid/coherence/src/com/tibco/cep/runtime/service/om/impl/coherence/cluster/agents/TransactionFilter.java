/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 13/8/2010
 */

package com.tibco.cep.runtime.service.om.impl.coherence.cluster.agents;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

import com.tangosol.io.ExternalizableLite;
import com.tangosol.util.Filter;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.service.cluster.Cluster;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Sep 19, 2008
 * Time: 9:16:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class TransactionFilter implements Filter, ExternalizableLite {

    public final byte INSERT = 0x01;
    public final byte MODIFY = 0x02;
    public final byte DELETE = 0x04;

    int[] interests;

    public TransactionFilter(Cluster cluster, String resourceURI, String ruleFunctionURI,
                             boolean includeInherited, boolean includeContained, boolean insert, boolean modify, boolean delete) throws Exception {


        Class[] registeredClasses = cluster.getMetadataCache().getRegisteredTypes();
        interests = new int[registeredClasses.length];

        for (int i = 0; i < interests.length; i++) {
            interests[i] = 0;
        }

        if (resourceURI == null) {
            for (int i = 0; i < interests.length; i++) {
                if (insert)
                    interests[i] = interests[i] | INSERT;
                if (modify)
                    interests[i] = interests[i] | MODIFY;
                if (delete)
                    interests[i] = interests[i] | DELETE;
            }
        } else {
        }
    }

    protected int _encode(int interest, boolean insert, boolean modify, boolean delete) {
        if (insert)
            interest = interest | INSERT;
        if (modify)
            interest = interest | MODIFY;
        if (delete)
            interest = interest | DELETE;

        return interest;
    }

    protected void encode(Cluster cluster, int[] interests, String resourceURI,
                          boolean insert, boolean modify, boolean delete, boolean includeContained, boolean includeInherited) throws Exception {
        TypeManager.TypeDescriptor td = cluster.getRuleServiceProvider().getTypeManager().getTypeDescriptor(resourceURI);
        if (td != null) {
            Class entityClz = td.getImplClass();
            int type_id = cluster.getMetadataCache().getTypeId(entityClz);
            interests[type_id] = _encode(interests[type_id], insert, modify, delete);

            if (includeInherited) {

            }

            if (includeContained) {
                List<Integer> containedTypes = cluster.getMetadataCache().getContainedTypes(resourceURI);
                for (int i = 0; i < containedTypes.size(); i++) {
                    int typeId = containedTypes.get(i);
                    interests[typeId] = _encode(interests[typeId], insert, modify, delete);
                }
            }

            if (includeInherited) {
                List<Integer> inheritedTypes = cluster.getMetadataCache().getInheritedTypes(type_id);
                for (int i = 0; i < inheritedTypes.size(); i++) {
                    int typeId = inheritedTypes.get(i);
                    interests[typeId] = _encode(interests[typeId], insert, modify, delete);
                }
            }

        }
    }


    public boolean evaluate(Object o) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void readExternal(DataInput dataInput) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void writeExternal(DataOutput dataOutput) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
