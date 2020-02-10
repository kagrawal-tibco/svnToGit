/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.agent.tasks;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.model.event.impl.CommandEventImpl;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jan 23, 2009
 * Time: 9:42:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class AgentScheduleCommand extends CommandEventImpl implements AgentCommand {
    int type_id;
    String closure;
    String extId;

    public final static String URI= "Command.Agent.Schedule";

    public AgentScheduleCommand() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public AgentScheduleCommand(int typeId, String extId, String closure) {
        this.type_id = typeId;
        this.extId = extId;
        this.closure = closure;
    }

    public String getURI() {
        return URI;
    }

    public void deserialize(byte [] buf) {
        ByteArrayInputStream bo = new ByteArrayInputStream(buf);
        DataInputStream ds= new DataInputStream(bo);
        try {
            type_id=ds.readInt();
            if (ds.readBoolean()) {
                closure=ds.readUTF();
            }
            if (ds.readBoolean()) {
                extId=ds.readUTF();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public byte[] serialize() {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        DataOutputStream ds= new DataOutputStream(bo);
        try {
            ds.writeInt(type_id);
            if (closure != null) {
                ds.writeBoolean(true);
                ds.writeUTF(closure);
            } else {
                ds.writeBoolean(false);
            }

            if (extId != null) {
                ds.writeBoolean(true);
                ds.writeUTF(extId);
            } else {
                ds.writeBoolean(false);
            }
            return bo.toByteArray();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void execute(InferenceAgent cacheAgent) {
        try {
        	Class clz = cacheAgent.getCluster().getMetadataCache().getClass(type_id);
            long id= cacheAgent.getCluster().getRuleServiceProvider().getIdGenerator().nextEntityId(clz);
            Entity en=cacheAgent.getCluster().getMetadataCache().createEntity(type_id, id, extId);
            cacheAgent.assertObject(en, getContext());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void schedule(int type_id, String extId, String closure) {
        this.type_id=type_id;
        this.extId=extId;
        this.closure=closure;
    }
}
