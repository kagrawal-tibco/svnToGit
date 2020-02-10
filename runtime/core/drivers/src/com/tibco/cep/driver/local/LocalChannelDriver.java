package com.tibco.cep.driver.local;


import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.ChannelConfig;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.spi.ChannelDriver;

/**
 * Created by IntelliJ IDEA.
 * User: hzhang
 * Date: Apr 15, 2006
 * Time: 9:18:08 PM
 * To change this template use File | Settings | File Templates.
 */

public class LocalChannelDriver implements ChannelDriver {

    public Channel createChannel(ChannelManager manager, String uri, ChannelConfig config) throws Exception {
        return new LocalChannel(manager, uri, config);
    }

    public Class[] getSupportedSerializerClasses() {
        return null;
    }

}
