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
import com.tibco.cep.runtime.channel.impl.AbstractDestination;
import com.tibco.cep.runtime.channel.impl.ChannelDestinationStats;
import com.tibco.cep.runtime.session.RuleServiceProvider;


/**
 * Hawk method that gets info on the destinations of a Channel.
 *
 * @version 2.0
 * @since 2.0
 */
public class GetDestinationsMethod extends AmiMethod {


    protected HawkRuleAdministrator m_hma;


    public GetDestinationsMethod(HawkRuleAdministrator hma) {
        super("getDestinations", "Retrieves Destination Info.", AmiConstants.METHOD_TYPE_INFO, "Line");

        m_hma = hma;

    }//constr


    public AmiParameterList getArguments() {
        final AmiParameterList args = new AmiParameterList();
        args.addElement(new AmiParameter("Channel URI", "URI of the Channel (optional).", ""));
        args.addElement(new AmiParameter("Destination Name", "Name of the Destination (optional).", ""));
        return args;
    }//getArguments


    public AmiParameterList getReturns() {
        final AmiParameterList values = new AmiParameterList();
        values.addElement(new AmiParameter("Line", "Line Number.", 0));
        values.addElement(new AmiParameter("Channel URI", "URI of the Channel.", ""));
        values.addElement(new AmiParameter("Destination URI", "URI of the Destination.", ""));
        //values.addElement(new AmiParameter("Status", "Status of the Destination.", ""));
        values.addElement(new AmiParameter("Nb in", "Number of Events in.", 0L));
        values.addElement(new AmiParameter("Rate in", "Rate of Events in.", 0.0D));
        final RuleServiceProvider rsp = this.m_hma.getServiceProvider();
        long rate_interval = -1;
        String inter = "";
        if (null != rsp) {
            rate_interval = Long.parseLong(rsp.getProperties().getProperty("be.engine.kpi.channel.rate.interval", "-1"));
            inter = "(" + rate_interval + "sec Interval)";
            if (rate_interval > 0) {
                values.addElement(new AmiParameter("Rate in " + inter, "Rate of Events in " + inter + ".", ""));
            }
        }
        values.addElement(new AmiParameter("Nb out", "Number of Events out.", 0L));
        values.addElement(new AmiParameter("Rate out", "Rate of Events out.", 0.0D));
        if (rate_interval > 0) {
            values.addElement(new AmiParameter("Rate out " + inter, "Rate of Events out " + inter + ".", 0.0D));
        }
        return values;
    }//getReturns


    void fillInOneReturnsEntry(AmiParameterList values, int row, String channelURI, Channel.Destination destination) {
        values.addElement(new AmiParameter("Line", "Line Number.", row));
        values.addElement(new AmiParameter("Channel URI", "URI of the Channel.", channelURI));
        values.addElement(new AmiParameter("Destination URI", "URI of the Destination.", destination.getURI()));
        //values.addElement(new AmiParameter("Status", "Status of the Destination.", destination.getState())); TODO there is no getState

        final ChannelDestinationStats listenerStats = ((AbstractDestination) destination).getStats();
        final long rate_interval = Long.parseLong(this.m_hma.getServiceProvider().getProperties()
                .getProperty("be.engine.kpi.channel.rate.interval", "-1"));

        values.addElement(new AmiParameter("Nb in", "Number of Events in.", listenerStats.getNbEventsIn()));
        values.addElement(new AmiParameter("Rate in", "Rate of Events in.", (((int) (listenerStats.getRateIn() * 100)) / 100D)));
        if (rate_interval > 0) {
            values.addElement( new AmiParameter("Last rate Events in", "Recent rate of Events in.",
                    ((int) (listenerStats.getLastRateIn() * 100)) / 100D
                    + " in the last " + rate_interval + " seconds.") );
        }

        values.addElement(new AmiParameter("Nb out", "Number of Events out.", listenerStats.getNbEventsOut()));
        values.addElement(new AmiParameter("Rate out", "Rate of Events out.", (((int) (listenerStats.getRateOut() * 100)) / 100D)));
        if (rate_interval > 0) {
            values.addElement( new AmiParameter("Last rate Events out", "Recent rate of Events out.",
                    ((int) (listenerStats.getLastRateOut() * 100)) / 100D
                    + " in the last " + rate_interval + " seconds.") );
        }
    }//fillInOneReturnsEntry


    public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
        try {
            final AmiParameterList values = new AmiParameterList();
            final String channelUriRequested = inParams.getString(0);
            final String destinationNameRequested = inParams.getString(1);
            final ChannelManager channelManager = m_hma.getServiceProvider().getChannelManager();
            if ((null == channelUriRequested) || "".equals(channelUriRequested)) {
                int row = 0;
                for (Iterator channelsIt = channelManager.getChannels().iterator(); channelsIt.hasNext();) {
                    row = this.processChannel(values, row, (Channel) channelsIt.next(), destinationNameRequested);
                }
            } else {
                this.processChannel(values, 0, channelManager.getChannel(channelUriRequested), destinationNameRequested);
            }
            return values;
        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch
    }//onInvoke


    protected int processChannel(AmiParameterList outParams, int row, Channel channel, String destinationName) {
        if (channel != null) {
            if ((null == destinationName) || "".equals(destinationName)) {
                final String channelURI = channel.getURI();
                for (Iterator it = channel.getDestinations().values().iterator(); it.hasNext(); row++) {
                    final Channel.Destination destination = (Channel.Destination) it.next();
                    this.fillInOneReturnsEntry(outParams, row, channelURI, destination);
                }//for
            } else {
                final Channel.Destination destination = (Channel.Destination) channel.getDestinations().get(
                        channel.getURI() + "/" + destinationName);
                if (null != destination) {
                    this.fillInOneReturnsEntry(outParams, 0, channel.getURI(), destination);
                }//if
            }//else
        }//if
        return row;
    }


}//class

