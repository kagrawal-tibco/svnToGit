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

import java.net.InetAddress;

import com.tibco.cep.runtime.service.ft.FTNode;
import com.tibco.cep.runtime.service.ft.spi.NodeInfo;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Oct 4, 2006
 * Time: 5:19:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class NodeInfoImpl implements NodeInfo {

    private FTNode member;
    private int state;

    public NodeInfoImpl(FTNode _member, int _state) {
        member = _member;
        state = _state;
    }

    public int getState() {
        return state;
    }

    public String getNodeGUID() {
        return member.getNodeGUID();
    }

    public int getNodeCacheId() {
        return member.getNodeCacheId();
    }

    public String getNodeCacheUid() {
        return member.getNodeCacheUid();
    }

    public int getMachineId() {
        return member.getMachineId();
    }

    public InetAddress getAddress() {
        return member.getAddress();
    }

    public int getPort() {
        return member.getPort();
    }

    public long getJoinedTimestamp() {
        return member.getTimeCreated();
    }

    public int getPriority() {
        return member.getPriority();
    }
}
