/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.agent.tasks;

import com.tibco.cep.runtime.model.event.impl.CommandEventImpl;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jan 23, 2009
 * Time: 7:44:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class AgentPingCommand extends CommandEventImpl implements AgentCommand {

    public final static String URI= "Command.Agent.Ping";

    public AgentPingCommand() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public byte[] serialize() {
        return null;
    }

    public String getURI() {
        return URI;
    }

    public final static AgentPingCommand createAgentPingCommand(byte [] buf) {
        return new AgentPingCommand();
    }

    public void execute(InferenceAgent cacheAgent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deserialize(byte[] buf) {
    }
}
