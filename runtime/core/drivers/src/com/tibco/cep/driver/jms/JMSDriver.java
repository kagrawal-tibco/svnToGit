package com.tibco.cep.driver.jms;

import com.tibco.cep.repo.BEProject;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.ChannelConfig;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.spi.ChannelDriver;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 * Created by IntelliJ IDEA.
 * User: hzhang
 * Date: Apr 15, 2006
 * Time: 9:18:08 PM
 * To change this template use File | Settings | File Templates.
 */

public class JMSDriver implements ChannelDriver {
    protected static final ExpandedName NAME_TYPE = ExpandedName.makeName("type");

    @Override
    public Channel createChannel (ChannelManager manager, String uri, ChannelConfig config) throws Exception {
        final BEProject project = (BEProject) manager.getRuleServiceProvider().getProject();
        final JMSChannelConfig jmsConfig = new JMSChannelConfig(config, project);
        if (Boolean.parseBoolean(manager.getRuleServiceProvider().getProperties().getProperty(JMSChannel_Unified.PROP_UNIFIED, Boolean.FALSE.toString()))) {
            return new JMSChannel_Unified(manager, uri, jmsConfig);
        } else {
            return new JMSChannel(manager, uri, jmsConfig);
        }
    }
}
