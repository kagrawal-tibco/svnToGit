package com.tibco.cep.driver.as.internal;

import com.tibco.cep.repo.ArchiveResourceProvider;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.ChannelConfig;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.spi.ChannelDriver;


public class ASDriver implements ChannelDriver {

	  public Channel createChannel (ChannelManager manager, String uri, ChannelConfig config) throws Exception {
	        GlobalVariables gv = manager.getRuleServiceProvider().getGlobalVariables();
	        ArchiveResourceProvider provider = manager.getRuleServiceProvider().getProject().getSharedArchiveResourceProvider();
	        ASChannelConfig asConfig = new ASChannelConfig(config, gv, provider);
	        return new ASChannel(manager, uri, asConfig);    
	    }

}
