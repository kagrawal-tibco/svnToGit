/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 *  All Rights Reserved.
 *
 *  This software is confidential and proprietary information of
 *  TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.agent;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.tibco.cep.impl.common.resource.UID;
import com.tibco.cep.runtime.model.serializers.SerializableLite;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.CacheAgent.AgentState;
import com.tibco.cep.runtime.service.cluster.CacheAgent.Type;

/*
* Author: Suresh Subramani / Date: Nov 8, 2010 / Time: 4:49:47 PM
*/

public class DefaultAgentTuple implements AgentManager.AgentTuple, SerializableLite {
    protected int agentId;

    protected String agentName;

    protected int priority;

    protected UID memberId;

    protected CacheAgent.Type type;

    protected CacheAgent.AgentState state;

    protected String txnCacheName;

    public DefaultAgentTuple() {

    }

    public DefaultAgentTuple(int agentId, String agentName, int priority, UID memberId, Type type, AgentState state,
                             String txnCacheName) {
        this.agentId = agentId;
        this.agentName = agentName;
        this.priority = priority;
        this.memberId = memberId;
        this.type = type;
        this.state = state;
        this.txnCacheName = txnCacheName;
    }

    public DefaultAgentTuple(CacheAgent agent) {
        agentId = agent.getAgentId();
        agentName = agent.getAgentName();
        priority = agent.getPriority();
        memberId = agent.getNodeId();
        type = agent.getAgentType();
        state = agent.getAgentState();
        txnCacheName = agent.getTransactionCacheName();
    }

    @Override
    public int getAgentId() {
        return agentId;
    }

    @Override
    public String getAgentName() {
        return agentName;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public UID getMemberId() {
        return memberId;
    }

    @Override
    public CacheAgent.Type getType() {
        return type;
    }

    @Override
    public CacheAgent.AgentState getState() {
        return state;
    }

    @Override
    public String getTransactionCacheName() {
        return txnCacheName;
    }

    @Override
    public void writeExternal(DataOutput out) throws IOException {
        out.writeInt(agentId);
        out.writeUTF(agentName);
        out.writeInt(priority);
        out.writeUTF(memberId.getAsString());
        out.writeUTF(type.name());
        out.writeUTF(state.name());
        boolean hasValue = txnCacheName != null;
        if (hasValue) {
            out.writeBoolean(true);
            out.writeUTF(txnCacheName);
        }
        else {
            out.writeBoolean(false);
        }
    }

    @Override
    public void readExternal(DataInput in) throws IOException {
        agentId = in.readInt();
        agentName = in.readUTF();
        priority = in.readInt();
        memberId = new UID(in.readUTF());
        type = Enum.valueOf(CacheAgent.Type.class, in.readUTF());
        state = Enum.valueOf(CacheAgent.AgentState.class, in.readUTF());
        boolean hasValue = in.readBoolean();
        if (hasValue) {
            txnCacheName = in.readUTF();
        }

    }
}
