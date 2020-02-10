/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.agent.tasks;

import com.tibco.cep.runtime.model.event.impl.CommandFactory;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jan 23, 2009
 * Time: 7:47:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class AgentCommandFactory {
    public final static int COMMAND_PING = 1;

    public static void registerCommands(RuleServiceProvider RSP) throws Exception{
        CommandFactory factory= RSP.getChannelManager().getCommandFactory();
        factory.registerCommandType(AgentPingCommand.URI, AgentPingCommand.class);
    }


}
