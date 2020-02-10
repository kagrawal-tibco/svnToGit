/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 11/8/2010
 */

package com.tibco.cep.runtime.service.om.impl.coherence.tangosol;

import java.io.Serializable;

import com.tangosol.util.SafeHashMap;
import com.tibco.cep.runtime.service.ft.FTNode;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Sep 29, 2006
 * Time: 6:16:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class FTNodeMap extends SafeHashMap implements Serializable {

    public static final String NAME = "TANGOSOL_FT_NODE_MAP";
    public static final int NODE_ADDED = 1;
    public static final int NODE_UPDATED = 2;
    public static final int NODE_DELETED = 3;
    public static final int NODE_REFRESHED = 4;
    public String lastChangedNodeGUID;
    public String lastModifierGUID;
    public int nodeActionPerformed;
    public String activeNodeGUID;
    public String lockNodeGUID;

    public String toString() {
        String strMap = "NodeMap [ ";
        Object[] keys = (Object[]) this.keySet().toArray();
        for (int i = 0; i < keys.length; i++) {
            FTNode node = (FTNode) get(keys[i]);
            strMap = strMap + ((i == 0) ? "" : ",") + node;
        }
        strMap = strMap + "]";
        return (strMap);
    }
}
