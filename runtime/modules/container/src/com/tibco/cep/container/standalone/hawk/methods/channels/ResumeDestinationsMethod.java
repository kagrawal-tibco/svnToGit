package com.tibco.cep.container.standalone.hawk.methods.channels;


import COM.TIBCO.hawk.ami.*;
import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.impl.AbstractDestination;

import java.util.Iterator;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 16, 2004
 * Time: 11:49:24 AM
 * To change this template use File | Settings | File Templates.
 * @.category test
 */
public class ResumeDestinationsMethod extends AmiMethod {


    protected HawkRuleAdministrator m_hma;


    public ResumeDestinationsMethod(HawkRuleAdministrator hma) {
        super("resumeDestinations", "Resumes Destinations.", AmiConstants.METHOD_TYPE_ACTION);
        m_hma = hma;
    }//constr


    public AmiParameterList getArguments() {
        final AmiParameterList args = new AmiParameterList();
        args.addElement(new AmiParameter("Channel URI", "URI of the Channel that contains the Destination.", ""));
        args.addElement(new AmiParameter("Destination Name", "Name of the Destination (optional).", ""));
//        args.addElement(new AmiParameter("Direction", "IN or OUT or BOTH", ""));
        return args;
    }//getArguments


    public AmiParameterList getReturns() {
        return new AmiParameterList();
    }//getReturns


    public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
        try {
            final AmiParameterList values = new AmiParameterList();
            final String channelUriRequested = inParams.getString(0);
            final String destinationNameRequested = inParams.getString(1);
            final ChannelManager channelManager = m_hma.getServiceProvider().getChannelManager();
            if ((null == channelUriRequested) || "".equals(channelUriRequested)) {
                int row = 0;
                for (Iterator channelsIt = channelManager.getChannels().iterator(); channelsIt.hasNext(); row++) {
                    this.processChannel(values, row, (Channel) channelsIt.next(), destinationNameRequested);
                }
            } else {
                this.processChannel(values, 0, channelManager.getChannel(channelUriRequested), destinationNameRequested);
            }
            return values;
        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch
    }//onInvoke


    protected void processChannel(AmiParameterList outParams, int row, Channel channel, String destinationName)
            throws AmiException {
        if (channel != null) {
            if ((null == destinationName) || "".equals(destinationName)) {
                final String channelURI = channel.getURI();
                for (Iterator it = channel.getDestinations().values().iterator(); it.hasNext(); row++) {
                    final Channel.Destination destination = (Channel.Destination) it.next();
                    ((AbstractDestination)destination).resumeEx();
                }//for
            } else {
                final Channel.Destination destination = (Channel.Destination) channel.getDestinations().get(
                        channel.getURI() + "/" + destinationName);
                if (null == destination) {
                    throw new AmiException(AmiErrors.AMI_REPLY_ERR, "Invalid Destination URI: " + destinationName);
                }//if
                destination.resume();
            }//else
        }//if
    }

}//class

