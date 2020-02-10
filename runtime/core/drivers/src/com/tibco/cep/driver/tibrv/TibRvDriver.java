package com.tibco.cep.driver.tibrv;

import com.tibco.cep.repo.ArchiveResourceProvider;
import com.tibco.cep.repo.GlobalVariables;
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

public class TibRvDriver implements ChannelDriver {
    
    @Override
    public Channel createChannel(ChannelManager manager, String uri, ChannelConfig config) throws Exception {
        GlobalVariables gv = manager.getRuleServiceProvider().getGlobalVariables();
        ArchiveResourceProvider provider = manager.getRuleServiceProvider().getProject().getSharedArchiveResourceProvider();
        TibRvChannelConfig rvConfig = new TibRvChannelConfig(config, gv, provider, manager.getRuleServiceProvider());
        String showExpertSettings = rvConfig.getShowExpertSettings();
        if (showExpertSettings != null) {
            if ("certified".equalsIgnoreCase(showExpertSettings)) {
                return new TibRvcmChannel(manager, uri, rvConfig, false);
            }
            if ("certifiedQ".equalsIgnoreCase(showExpertSettings)) {
                return new TibRvcmChannel(manager, uri, rvConfig, true);
            }
        }
        return new TibRvChannel(manager, uri, rvConfig);
    }

    public Class[] getSupportedSerializerClasses() {
        return new Class[0];  //To change body of implemented methods use File | Settings | File Templates.
    }
}
