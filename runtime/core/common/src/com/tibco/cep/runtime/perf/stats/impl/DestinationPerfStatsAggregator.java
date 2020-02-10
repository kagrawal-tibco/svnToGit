package com.tibco.cep.runtime.perf.stats.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.Channel.Destination;
import com.tibco.cep.runtime.channel.impl.AbstractDestination;
import com.tibco.cep.runtime.channel.impl.ChannelDestinationStats;
import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.perf.stats.DestinationStats;
import com.tibco.cep.runtime.perf.stats.PerfStatsProperties;
import com.tibco.cep.runtime.perf.stats.StatsAggregator;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.util.FQName;

public class DestinationPerfStatsAggregator implements StatsAggregator {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(DestinationPerfStatsAggregator.class);

    private static int MATCHING_TERM_LOCATION = 4;

    private static String MATCHING_TERM_VALUE = "InferenceAgent";

    private List<DestinationStatsProvider> destinationStatsProviders;

    private List<DestinationStats> destinationStats;

    @SuppressWarnings("rawtypes")
    public DestinationPerfStatsAggregator(RuleSession ruleSession) {
        destinationStatsProviders = new LinkedList<DestinationStatsProvider>();
        destinationStats = new LinkedList<DestinationStats>();
        //create destination stat providers and destination stat objects
        Collection channels = ruleSession.getRuleServiceProvider().getChannelManager().getChannels();
        Iterator channelsIterator = channels.iterator();
        while (channelsIterator.hasNext()) {
            Channel channel = (Channel) channelsIterator.next();
            Set<Entry<String,Destination>> entrySet = channel.getDestinations().entrySet();
            for (Entry<String, Destination> entry : entrySet) {
                String uri = entry.getKey();
                AbstractDestination destination = (AbstractDestination) entry.getValue();
                DestinationStatsProvider statsProvider = new DestinationStatsProvider(uri,destination.getStats());
                destinationStatsProviders.add(statsProvider);
                destinationStats.add(statsProvider.statsValueObj);
            }
        }
    }

    public Collection<DestinationStats> getDestinationStats(){
        return destinationStats;
    }

    @Override
    public boolean willAccept(FQName name) {
        String[] names = name.getComponentNames();
        //do we have enough names ?
        if (names.length > MATCHING_TERM_LOCATION) {
            //yes, we do !! is the matching term matching ?
            if (names[MATCHING_TERM_LOCATION].equals(MATCHING_TERM_VALUE) == true) {
                //yes it does, do call type and call identifier match
                String callType = names.length > MATCHING_TERM_LOCATION ? names[MATCHING_TERM_LOCATION + 1] : null;
                String callIdentifier = names.length > MATCHING_TERM_LOCATION + 2 ? names[MATCHING_TERM_LOCATION + 2] : null;
                if (("General".equals(callType) == true && "Rete".equals(callIdentifier) == true)) {
                    //yes we are dealing with a rete general stat, accept it
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void aggregate(long time, FQName name, Data data) {
        for (DestinationStatsProvider provider : destinationStatsProviders) {
            provider.update(time, name, data);
        }
    }

    public void close(){
        destinationStatsProviders.clear();
        destinationStats.clear();
    }

    private class DestinationStatsProvider {

        private DestinationStats statsValueObj;

        private ChannelDestinationStats destinationStatsProvider;

        private long lastTotalEventsIn;

        private TimedWindow timedWindow;

        DestinationStatsProvider(String uri, ChannelDestinationStats channelDestinationStatsProvider){
            this.destinationStatsProvider = channelDestinationStatsProvider;
            this.statsValueObj = new DestinationStats();
            this.statsValueObj.setName(uri);
        }

        void update(long time, FQName name, Data data) {
            //set total events sent out
            this.statsValueObj.setTotalEventsSent(destinationStatsProvider.getNbEventsOut());
            //get the total events in (this is from start of collection
            long totalEventsIn = destinationStatsProvider.getNbEventsIn();
            //set total events received
            this.statsValueObj.setTotalEventsReceived(totalEventsIn);
            //compute the delta wrt to last window's total events in
            long eventsInDelta = totalEventsIn - lastTotalEventsIn;
            if (eventsInDelta > 0) {
                //we have new events
                if (timedWindow == null) {
                    //we do not have a timed window , create one with initial value as the delta
                    timedWindow = TimedWindow.create(PerfStatsProperties.TIMED_WINDOW_DURATION, eventsInDelta);
                }
                else if (timedWindow.hasExpired(time) == true) {
                    //the existing timed Window as expired, snap shot the current total events in
                    lastTotalEventsIn = totalEventsIn;
                    timedWindow = TimedWindow.create(PerfStatsProperties.TIMED_WINDOW_DURATION, eventsInDelta);
                }
                else {
                    //we have a valid timed window, set the delta
                    timedWindow.set(eventsInDelta);
                }
                //set the events per second
                this.statsValueObj.setEventsReceivedPerSecond(timedWindow.getValue());
                this.statsValueObj.setLastEPSModification(timedWindow.getLastModifiedTime());
                if (LOGGER.isEnabledFor(Level.DEBUG) == true) {
                	LOGGER.log(Level.DEBUG, "%s::%s", this.getClass().getSimpleName(), timedWindow.toString());
                    LOGGER.log(Level.DEBUG, statsValueObj.toString());
                }
            }
        }
    }

}