package com.tibco.cep.driver.http;

import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.ChannelConfig;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.spi.ChannelDriver;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Mar 27, 2008
 * Time: 12:16:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpDriver implements ChannelDriver {
    
    @Override
    public Channel createChannel(ChannelManager manager, String uri, ChannelConfig config) throws Exception {
    	final RuleServiceProvider rsp = manager.getRuleServiceProvider();
        final HttpChannelConfig httpConfig = new HttpChannelConfig(config, rsp);
        return new HttpChannel(manager, uri, httpConfig);        
    }
}
