package com.tibco.cep.container.standalone.hawk.methods.channels;


import java.util.Iterator;

import COM.TIBCO.hawk.ami.AmiConstants;
import COM.TIBCO.hawk.ami.AmiErrors;
import COM.TIBCO.hawk.ami.AmiException;
import COM.TIBCO.hawk.ami.AmiMethod;
import COM.TIBCO.hawk.ami.AmiParameter;
import COM.TIBCO.hawk.ami.AmiParameterList;

import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.ChannelManager;


/**
 * Hawk method that gets info on one or all Channels.
 *
 * @version 2.0
 * @since 2.0
 */
public class GetChannelsMethod extends AmiMethod {


    protected HawkRuleAdministrator m_hma;


    public GetChannelsMethod(HawkRuleAdministrator hma) {
        super("getChannels", "Retrieves Channel Info.", AmiConstants.METHOD_TYPE_INFO, "Line");
        m_hma = hma;
    }//constr


    public AmiParameterList getArguments() {
        final AmiParameterList args = new AmiParameterList();
        args.addElement(new AmiParameter("URI", "URI of the Channel (optional)", ""));
        return args;
    }//getArguments


    public AmiParameterList getReturns() {
        final AmiParameterList values = new AmiParameterList();
        values.addElement(new AmiParameter("Line", "Line Number", 0));
        values.addElement(new AmiParameter("URI", "URI of the Channel.", ""));
        values.addElement(new AmiParameter("State", "Current state of the Channel", ""));
        return values;
    }//getReturns


    protected void fillInOneReturnsEntry(AmiParameterList values, int row, String uri, Channel channel) throws Exception {
        if (null != channel) {
            values.addElement(new AmiParameter("Line", "Line Number", row));
            values.addElement(new AmiParameter("URI", "URI of the Channel.", uri));
            values.addElement(new AmiParameter("State", "Current state of the Channel", channel.getState().toString()));
        }
    }//fillInOneReturnsEntry


    public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
        try {
            final AmiParameterList values = new AmiParameterList();
            final ChannelManager channelManager = m_hma.getServiceProvider().getChannelManager();
            final String uriRequested = inParams.getString(0);
            if ((null == uriRequested) || "".equals(uriRequested)) {
                int row = 0;
                for (Iterator it = channelManager.getChannels().iterator(); it.hasNext(); row++) {
                    final Channel channel = (Channel) it.next();
                    this.fillInOneReturnsEntry(values, row, channel.getURI(), channel);
                }
            } else {
                final Channel channel = channelManager.getChannel(uriRequested);
                if (null == channel) {
                    throw new AmiException(AmiErrors.AMI_REPLY_ERR, "Invalid Channel URI: " + uriRequested);
                }
                this.fillInOneReturnsEntry(values, 0, uriRequested, channel);
            }
            return values;
        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch
    }//onInvoke


}//class

