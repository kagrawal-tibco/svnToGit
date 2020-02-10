package com.tibco.cep.runtime.service.management.process.impl;

import java.util.Iterator;

import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.TabularDataSupport;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.channel.impl.AbstractDestination;
import com.tibco.cep.runtime.channel.impl.ChannelDestinationStats;
import com.tibco.cep.runtime.channel.impl.ChannelManagerImpl;
import com.tibco.cep.runtime.service.management.ChannelMethodsImpl;
import com.tibco.cep.runtime.service.management.MBeanTabularDataHandler;
import com.tibco.cep.runtime.service.management.exception.BEMMUserActivityException;
import com.tibco.cep.runtime.service.management.process.EngineChannelsMBean;
import com.tibco.cep.runtime.service.management.process.EngineMBeansSetter;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Sep 21, 2009
 * Time: 10:31:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class EngineChannelsMBeanImpl extends ChannelMethodsImpl implements EngineChannelsMBean, EngineMBeansSetter {

    //entity methods that are common to agents and engines are implemented in the superclass

    public void setRuleServiceProvider(RuleServiceProvider ruleServiceProvider) {
        super.ruleServiceProvider = ruleServiceProvider;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setLogger(Logger logger) {
        super.logger = logger;
    }

    public void ReconnectChannels(String channelURI) throws Exception {
        try {
            final ChannelManagerImpl channelManager = (ChannelManagerImpl) ruleServiceProvider.getChannelManager();
                //no channel URI provided, so reconnect every channel of this RSP/ChannelManager
            if ((null == channelURI) || "".equals(channelURI)) {
                logger.log(Level.DEBUG,"Reconnecting every channel of RSP " + ruleServiceProvider.getName());
                channelManager.startChannels(channelManager.getMode());
                logger.log(Level.DEBUG,"Channels of RSP " + ruleServiceProvider.getName() + " reconnected");
            } else {  //Reconnect just the channel with the provided URI
                if (channelManager.getChannelForReconnect(channelURI) == null)
                    throw new BEMMUserActivityException("No channel with URI \"" + channelURI + "\" found. Please enter a valid channel URI.");
                logger.log(Level.DEBUG,"Reconnecting channel " + channelURI);
                channelManager.startChannelDuringReconnect(channelURI, channelManager.getMode());
                logger.log(Level.DEBUG,"Channel " + channelURI + " reconnected");
            }
        } catch (BEMMUserActivityException uae) {
            logger.log(Level.WARN, uae.getMessage());
            throw uae;
        }
        catch (Exception e) {
            logger.log(Level.ERROR,e,"Exception occurred while trying to reconnect channels.");
            throw e;
        }
    } //reconnectChannels

    //behavior overridden by suspendChannels. To expose it uncomment this code, and the method definition in the Interface.
    /*public void resumeChannels(String channelURI) {
        try {
            final ChannelManager channelManager = ruleServiceProvider.getChannelManager();
                //Resume just the channel with the provided URI
            if ((null != channelURI) && !"".equals(channelURI)) {
                final Channel channel = channelManager.getChannel(channelURI);
                if (null == channel) {
                    throw new IllegalArgumentException("Invalid Channel URI: " + channelURI);
                }
                logger.log(Level.DEBUG,"Resuming channel " + channelURI);
                channel.resume();
                logger.log(Level.DEBUG,"Channel " + channelURI + " resumed");
            } else {   //Resume every channel of this RSP/ChannelManager
                logger.log(Level.DEBUG,"Resuming every channel of RSP " + ruleServiceProvider.getName());
                for (Iterator it = channelManager.getChannels().iterator(); it.hasNext();) {
                    final Channel channel = (Channel) it.next();
                    channel.resume();
                }//for
                logger.log(Level.DEBUG,"Channels of RSP " + ruleServiceProvider.getName() + " resumed");
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }//catch
        catch (Exception e1) {
            logger.log(Level.ERROR,"Unexpected Exception occurred");
            e1.printStackTrace();
        }
    } //resumeChannels*/

    //behavior overridden by suspendDestinations. To expose it uncomment this code, and the method definition in the Interface.
    /*public void suspendChannels(String channelURI) {
         try {
             final ChannelManager channelManager = ruleServiceProvider.getChannelManager();
                 //Suspend just the channel with the provided URI
             if ((null != channelURI) && !"".equals(channelURI)) {
                 final Channel channel = channelManager.getChannel(channelURI);
                 if (null == channel) {
                     throw new IllegalArgumentException("Invalid Channel URI: " + channelURI);
                 }
                 logger.log(Level.DEBUG,"Suspending channel " + channelURI);
                 channel.suspend();
                 logger.log(Level.DEBUG,"Channel " + channelURI + " suspended");
             } else { //Suspend every channel of this RSP/ChannelManager
                 logger.log(Level.DEBUG,"Suspending every channel of RSP " + ruleServiceProvider.getName());
                 for (Iterator it = channelManager.getChannels().iterator(); it.hasNext();) {
                     ((Channel) it.next()).suspend();
                 }//for
                 logger.log(Level.DEBUG,"Channels of RSP " + ruleServiceProvider.getName() + " suspended");
             }
         } catch (IllegalArgumentException e) {
             e.printStackTrace();
         }//catch
         catch (Exception e1) {
             logger.log(Level.ERROR,"Unexpected Exception occurred");
             e1.printStackTrace();
         }
     }//suspendChannels*/

    public void ResumeDestinations(String channelURI, String destName) throws Exception {
        logger.log(Level.INFO, "Resuming destinations...");
        processDestinations(channelURI, destName, true);
        logger.log(Level.INFO, "All destinations resume called successfully");
    }

    public void SuspendDestinations(String channelURI, String destName) throws Exception {
        logger.log(Level.INFO, "Suspending destinations");
        processDestinations(channelURI, destName, false);
        logger.log(Level.INFO, "All destinations suspend called successfully");
    }

    //Auxiliary method called by the exposed methods resumeDestinations(.,.) and suspendDestinations(.,.)
    //The isResume parameter is set to true when the action is to "Resume" the destinations,
    //and set to false when the action is to "Suspend" the destinations
    private void processDestinations(String channelURI, String destName, Boolean isResume) throws Exception {
        try {
            final ChannelManager channelManager = ruleServiceProvider.getChannelManager();
                //No URI - process all channels
            if ((null == channelURI) || "".equals(channelURI)) {
                int row = 0;
                logger.log(Level.DEBUG,"Iterating over all channels of RSP " + ruleServiceProvider.getName());
                for (Iterator channelsIt = channelManager.getChannels().iterator(); channelsIt.hasNext(); row++) {
                    Channel channel = (Channel) channelsIt.next();
                    logger.log(Level.DEBUG,"Processing channel " + channel.getURI());
                    this.processChannel(row, channel, destName, isResume);
                }
            } else { //specific channel URI provided
                logger.log(Level.DEBUG,"Processing channel " + channelURI);
                this.processChannel(0, channelManager.getChannel(channelURI), destName, isResume);
            }
        } catch (BEMMUserActivityException uae) {
            logger.log(Level.WARN, uae.getMessage());
            throw uae;
        }
        catch (Exception e) {
            final String msg = isResume ? "resume" : "suspend";
            logger.log(Level.ERROR,e,"Exception occurred while trying to %s channels.",msg);
            throw e;
        }
    } //processDestinations

    //This method is called by the private method processDestinations(String channelURI, String destName) above
    private void processChannel(int row, Channel channel, String destinationName, Boolean isResume) throws BEMMUserActivityException {
        if (channel != null) {
            final String channURI = channel.getURI();
            //Destination URI was NOT provided by the user so iterate over all the destinations of this channel
            if ((null == destinationName) || "".equals(destinationName)) {
                logger.log(Level.DEBUG,"Iterating over all the destinations of channel " + channURI);
                for (Iterator it = channel.getDestinations().values().iterator(); it.hasNext(); row++) {
                    final Channel.Destination destination = (Channel.Destination) it.next();
                    if (isResume) {
                        logger.log(Level.DEBUG,"Resuming destination " + destination.getURI());
                        ((AbstractDestination)destination).resumeEx();
                    } else {  //suspend
                        logger.log(Level.DEBUG,"Suspending destination " + destination.getURI());
                        destination.suspend();
                    }
                }//for
            } else {  //Destination URI was provided by the user
                final Channel.Destination destination = (Channel.Destination) channel.getDestinations().get(
                        channURI + "/" + destinationName);
                if (null == destination) {
                    throw new BEMMUserActivityException("Invalid Destination: " + destinationName);
                }//if
                 if (isResume) {
                    logger.log(Level.DEBUG,"Resuming destination " + destination.getURI());
                    destination.resume();
                 } else { //suspend
                     logger.log(Level.DEBUG,"Suspending destination " + destination.getURI());
                     destination.suspend();
                 }
            }//else URI
        }//if (channel != null)
        else {
            throw new BEMMUserActivityException("Invalid Channel URI");
        }
    } //processChannel

    // ===================================================================================================

    //params: it is the destination name, not the destination URI.
    public TabularDataSupport GetDestinations(String channelUri, String destinationName) throws Exception {
        final String INVOKED_METHOD = "getDestinations";
        try {
            final ChannelManager channelManager = ruleServiceProvider.getChannelManager();

            MBeanTabularDataHandler tabularDataHandler = new MBeanTabularDataHandler(logger);
            tabularDataHandler.setTabularData(INVOKED_METHOD);
            logger.log(Level.DEBUG,"Getting destinations info " + destinationName);

            //if no channel URI provided, iterate over all the channels
            if ( (null == channelUri) || "".equals(channelUri) )  {
                int row = 0;
                for (Iterator channelsIt = channelManager.getChannels().iterator(); channelsIt.hasNext();) {
                    row = processChannel(tabularDataHandler, row, (Channel) channelsIt.next(), destinationName, channelUri);
                }
            } else { // process specified channel
                this.processChannel(tabularDataHandler, 0, channelManager.getChannel(channelUri), destinationName, channelUri);
            }
            return tabularDataHandler.getTabularData(INVOKED_METHOD);
        }
        catch (BEMMUserActivityException uae) {
            logger.log(Level.WARN, uae.getMessage());
            throw uae;
        }
        catch (Exception e) {
            logger.log(Level.ERROR,e,"Exception occurred while invoking method %s", INVOKED_METHOD );
            throw e;
        }
    } //getDestinations

    private int processChannel(MBeanTabularDataHandler tabularDataHandler, int row, Channel channel,
                               String destinationName, String channURI) throws OpenDataException, BEMMUserActivityException {
        if (channel != null) {
            //if no destination URI provided, iterate over all destinations
            if ( (null == destinationName) || "".equals(destinationName) ) {
                final String channelURI = channel.getURI();
                for (Iterator it = channel.getDestinations().values().iterator(); it.hasNext(); row++) {
                    final Channel.Destination destination = (Channel.Destination) it.next();
                    putDestsInTableRow(tabularDataHandler, row, channelURI, destination);
                }//for
            } else {
                final Channel.Destination destination = (Channel.Destination) channel.getDestinations().get(
                        channel.getURI() + "/" + destinationName);
                if (null != destination) {
                    putDestsInTableRow(tabularDataHandler, 0, channel.getURI(), destination);
                }//if
                else
                    throw new BEMMUserActivityException("Invalid Destination name: " + destinationName);
            }//else
        }//if
        else
            throw new BEMMUserActivityException("Invalid Channel URI: " + channURI);
        return row;
    } //processChannel

        private void putDestsInTableRow(MBeanTabularDataHandler tabularDataHandler, int row, String channelURI,
                                Channel.Destination destination) throws OpenDataException {

        //They must have the same length
        Object[] itemValues = new Object[tabularDataHandler.getNumItems()];
        itemValues[0]= row;
        itemValues[1]= channelURI;
        itemValues[2]= destination.getURI();

        final ChannelDestinationStats listenerStats = ((AbstractDestination) destination).getStats();
        final long rate_interval = Long.parseLong(ruleServiceProvider.getProperties()
                .getProperty("be.engine.kpi.channel.rate.interval", "-1"));

        itemValues[3]= listenerStats.getNbEventsIn();
        itemValues[4]= (((int) (listenerStats.getRateIn() * 100)) / 100D);

        if (rate_interval > 0) { //todo: Commented out because this seems not be working well in Hawk
//                tabularData.addElement( new AmiParameter("Last rate Events in", "Recent rate of Events in.",
//                        ((int) (listenerStats.getLastRateIn() * 100)) / 100D
//                        + " in the last " + rate_interval + " seconds.") );
        }

        itemValues[5]= listenerStats.getNbEventsOut();
        itemValues[6]= (((int) (listenerStats.getRateOut() * 100)) / 100D);

        if (rate_interval > 0) {  //todo: Commented out because this seems not be working well in Hawk
//            tabularData.addElement( new AmiParameter("Last rate Events out", "Recent rate of Events out.",
//                    ((int) (listenerStats.getLastRateOut() * 100)) / 100D
//                    + " in the last " + rate_interval + " seconds.") );
        }

        itemValues[7]=destination.isSuspended();
        //ads current row to the table
        tabularDataHandler.put(itemValues);
    }//putDestsInTableRow

    public TabularDataSupport GetChannels(String channelURI) throws Exception {
        final String INVOKED_METHOD = "getChannels";
        try {
            final ChannelManager channelManager = ruleServiceProvider.getChannelManager();

            MBeanTabularDataHandler tabularDataHandler = new MBeanTabularDataHandler(logger);
            tabularDataHandler.setTabularData(INVOKED_METHOD);

            logger.log(Level.INFO, "Getting channels info...");
            //if no channel URI provided, iterate over all the channels
            if ((null == channelURI) || "".equals(channelURI)) {
                int row = 0;
                for (Iterator it = channelManager.getChannels().iterator(); it.hasNext(); row++) {
                    final Channel channel = (Channel) it.next();
                    putChannelsInTableRow(tabularDataHandler, row, channel.getURI(), channel);
                }
            } else {
                final Channel channel = channelManager.getChannel(channelURI);
                if (null == channel) {
                    throw new BEMMUserActivityException("Invalid Channel URI: " + channelURI);
                }
                putChannelsInTableRow(tabularDataHandler, 0, channelURI, channel);
            }
            return tabularDataHandler.getTabularData(INVOKED_METHOD);
        }
        catch (BEMMUserActivityException uae) {
            logger.log(Level.INFO, uae.getMessage());
            throw uae;
        }
        catch (Exception e) {
            logger.log(Level.ERROR,e,"Exception occurred while invoking method %s", INVOKED_METHOD );
            throw e;
        }
    } //getChannels

    private void putChannelsInTableRow(MBeanTabularDataHandler tabularDataHandler, int row, String channelURI,
                               Channel channel) throws OpenDataException {
        Object[] itemValues = new Object[tabularDataHandler.getNumItems()];
        itemValues[0] = row;
        itemValues[1] = channelURI;
        itemValues[2] = channel.getState().toString();
        //ads current row to the table
        tabularDataHandler.put(itemValues);
    } //putChannelsInTableRow

}  //class
