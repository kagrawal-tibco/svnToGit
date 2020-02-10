package com.tibco.cep.runtime.perf.stats.impl;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.perf.stats.PerfStatsProperties;
import com.tibco.cep.runtime.perf.stats.StatsAggregator;
import com.tibco.cep.runtime.util.FQName;

public class EventsPerfStatsAggregator implements StatsAggregator {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(DestinationPerfStatsAggregator.class);

    private static int MATCHING_TERM_LOCATION = 4;

    private static String MATCHING_TERM_VALUE = "InferenceAgent";
    
    private static int AGENT_NAME_LOCATION = 2;
    private static int AGENT_ID_LOCATION = 3;
    private String agentName;
    private int agentId;

    private long totalEventCount;

    private long totalTimerEventCount;

    private TimedWindow timedWindow;

    private long eventsPerSecond;

    private long lastEventProcessedTime;

    private double averageEventProcessingTime;

    private long previousTotalEventCount;

    public EventsPerfStatsAggregator() {
        lastEventProcessedTime = -1;
    }
    
    public EventsPerfStatsAggregator(String agentName, int agentId) {
        lastEventProcessedTime = -1;
        this.agentName = agentName;
        this.agentId = agentId;
    }

    public long getTotalEventCount() {
        return totalEventCount;
    }

    public long getTotalTimerEventCount() {
        return totalTimerEventCount;
    }

    public long getEventsPerSecond() {
        return eventsPerSecond;
    }

    public long getLastEventProcessedTime() {
        return lastEventProcessedTime;
    }

    public double getAverageEventProcessingTime() {
        return averageEventProcessingTime;
    }

    @Override
    public boolean willAccept(FQName name) {
        String[] names = name.getComponentNames();
        //do we have enough names ?
        if (names.length > MATCHING_TERM_LOCATION) {
        	if(names[AGENT_NAME_LOCATION].equals(agentName) && names[AGENT_ID_LOCATION].equals(Integer.toString(agentId))){
	            //yes, we do !! is the matching term matching ?
	            if (names[MATCHING_TERM_LOCATION].equals(MATCHING_TERM_VALUE) == true) {
	                //yes it does, do call type and call identifier match
	                String callType = names.length > MATCHING_TERM_LOCATION ? names[MATCHING_TERM_LOCATION + 1] : null;
	                if ("General".equals(callType) == true || "TimerEvent".equals(callType) == true) {
	                    //yes we are dealing with a general stat or a timer event stat
	                    return true;
	                }
	            }
        	}
        }
        return false;
    }

    @Override
    public void aggregate(long time, FQName name, Data data) {
        String[] names = name.getComponentNames();
        String callType = names.length > MATCHING_TERM_LOCATION ? names[MATCHING_TERM_LOCATION + 1] : null;
        if ("General".equals(callType) == true) {
            //this covers all events (received as well as timer)
            totalEventCount = totalEventCount + (Long)data.getColumns()[2];
            //this is the actual event processing time
            averageEventProcessingTime = (averageEventProcessingTime + ((Double) data.getColumns()[3]))/2;
        }
        else if ("TimerEvent".equals(callType) == true) {
            //we capture the timer event information separate (so getting received event is total - timer)
            totalTimerEventCount = totalTimerEventCount + (Long)data.getColumns()[2];
        }
        //find the delta between the last window and now
        long eventsDelta = totalEventCount - previousTotalEventCount;
        if (eventsDelta > 0) {
            if (timedWindow == null) {
                //we do not have a timed window , create one with initial value as the delat
                timedWindow = TimedWindow.create(PerfStatsProperties.TIMED_WINDOW_DURATION, eventsDelta);
            } else if (timedWindow.hasExpired(time) == true) {
                //the existing timed Window as expired, snap shot the current total events in
                previousTotalEventCount = totalEventCount;
                timedWindow = TimedWindow.create(PerfStatsProperties.TIMED_WINDOW_DURATION, eventsDelta);
            } else {
                //we have a valid timed window, set the delta
                timedWindow.set(eventsDelta);
            }
            //set the events per second
            eventsPerSecond = timedWindow.getValue();
            lastEventProcessedTime = timedWindow.getLastModifiedTime();
            if (LOGGER.isEnabledFor(Level.DEBUG) == true) {
            	LOGGER.log(Level.DEBUG, "%s::%s", this.getClass().getSimpleName(), timedWindow.toString());
            }
        }
        if (LOGGER.isEnabledFor(Level.DEBUG) == true) {
			LOGGER.log(Level.DEBUG, "%s::Total Events %d (timer event count is %d) with average proc time of %f", this.getClass().getSimpleName(), totalEventCount, totalTimerEventCount, averageEventProcessingTime);
		}
    }

    @Override
    public void close() {
    }

}
