package com.tibco.cep.runtime.service.management.agent;

import javax.management.openmbean.TabularDataSupport;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Mar 8, 2010
 * Time: 5:52:29 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AgentChannelsMBean {
    public TabularDataSupport GetSessionInputDestinations() throws Exception;
}
