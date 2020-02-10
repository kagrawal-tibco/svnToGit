package com.tibco.cep.container.standalone.hawk.methods.channels;


import COM.TIBCO.hawk.ami.AmiConstants;
import COM.TIBCO.hawk.ami.AmiErrors;
import COM.TIBCO.hawk.ami.AmiException;
import COM.TIBCO.hawk.ami.AmiMethod;
import COM.TIBCO.hawk.ami.AmiParameter;
import COM.TIBCO.hawk.ami.AmiParameterList;

import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;
import com.tibco.cep.runtime.channel.ChannelManager;


/**
 * Hawk method enabling the user to reconnect all channels or a single channel.
 *
 * @version 2.0
 */
public class ReconnectChannelsMethod extends AmiMethod {


    protected HawkRuleAdministrator m_hma;


    public ReconnectChannelsMethod(HawkRuleAdministrator hma) {
        super("reconnectChannels", "Restarts all channels or a single channel.", AmiConstants.METHOD_TYPE_ACTION);
        m_hma = hma;
    }//constr


    public AmiParameterList getArguments() {
        final AmiParameterList args = new AmiParameterList();
        args.addElement(new AmiParameter("URI", "URI of the channel to restart (all channels are restarted if this is empty).", ""));
        return args;
    }


    public AmiParameterList getReturns() {
        return null;
    }


    public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
        try {
            final ChannelManager channelManager = m_hma.getServiceProvider().getChannelManager();
            final String uriRequested = inParams.getString(0);
            if ((null == uriRequested) || "".equals(uriRequested)) {
                channelManager.startChannels(channelManager.getMode());
            } else {
                channelManager.startChannel(uriRequested, channelManager.getMode());
            }
        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }
        return new AmiParameterList();
    }
}//class

