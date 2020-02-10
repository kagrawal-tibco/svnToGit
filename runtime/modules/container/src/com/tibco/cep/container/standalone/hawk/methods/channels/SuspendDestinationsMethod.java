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
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 16, 2004
 * Time: 11:49:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class SuspendDestinationsMethod extends AmiMethod {


    protected HawkRuleAdministrator m_hma;
    protected int m_processId;
    protected String m_hostName;


    public SuspendDestinationsMethod(HawkRuleAdministrator hma) {
        super("suspendDestinations", "Suspends Destinations.", AmiConstants.METHOD_TYPE_ACTION);
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
            throws AmiException{
        if (channel != null) {
            if ((null == destinationName) || "".equals(destinationName)) {
                final String channelURI = channel.getURI();
                for (Iterator it = channel.getDestinations().values().iterator(); it.hasNext(); row++) {
                    final Channel.Destination destination = (Channel.Destination) it.next();
                    destination.suspend();
                }//for
            } else {
                final Channel.Destination destination = (Channel.Destination) channel.getDestinations().get(
                        channel.getURI() + "/" + destinationName);
                if (null == destination) {
                    throw new AmiException(AmiErrors.AMI_REPLY_ERR, "Invalid Destination URI: " + destinationName);
                }//if
                destination.suspend();
            }//else
        }//if
    }


}//class

