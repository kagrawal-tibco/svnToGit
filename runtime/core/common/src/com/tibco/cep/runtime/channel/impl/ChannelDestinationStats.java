package com.tibco.cep.runtime.channel.impl;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Maintains some stats on a <code>Channel.Destination</code>.
 *
 */
public class ChannelDestinationStats {


    //TODO: status/state should be accessible from Channel.Destination, instead of here.
    public static final char STATUS_UNINITIALIZED = '?';
    public static final char STATUS_CONNECTED = 'c';
    public static final char STATUS_RUNNING = 'r';
    public static final char STATUS_SUSPENDED = 's';
    public static final char STATUS_CLOSED = 'd';


    private long m_eventsInCurrentCount = 0;
    private long m_eventsInLastCount = 0;
    private long m_eventsInTotalCount = 0;

    private long m_eventsOutCurrentCount = 0;
    private long m_eventsOutLastCount = 0;
    private long m_eventsOutTotalCount = 0;

    private long m_lastChecked = System.currentTimeMillis();
    private long m_timeStarted = m_lastChecked;
    private char m_status = STATUS_UNINITIALIZED;

    protected static long m_rate_interval = -1;
    protected static Set m_activeStats = new HashSet();
    protected static Timer m_statsUpdater = new Timer(true);
    protected static TimerTask m_statsUpdaterTask = new TimerTask() {
        public void run() {
            final long now = System.currentTimeMillis();
            synchronized (m_activeStats) {
                for (Iterator it = m_activeStats.iterator(); it.hasNext();) {
                    final ChannelDestinationStats stats = (ChannelDestinationStats) it.next();
                    stats.m_eventsInLastCount = stats.m_eventsInCurrentCount;
                    stats.m_eventsInCurrentCount = 0;
                    stats.m_eventsOutLastCount = stats.m_eventsOutCurrentCount;
                    stats.m_eventsOutCurrentCount = 0;
                    stats.m_lastChecked = now;
                }//for
            }//synchronized
        }//run
    };


    static {
        try {
//todo =- get the rule service provider            
//            m_rate_interval = Long.parseLong(BEProperties.getInstance().getProperty("be.engine.kpi.channel.rate.interval", "-1"));
        } catch (Exception e) {
        }
        if (m_rate_interval > 0) {
            m_rate_interval = m_rate_interval * 1000;
            if (m_rate_interval >= 5000) {
                m_statsUpdater.schedule(m_statsUpdaterTask, 0, m_rate_interval);
            }//if
            else {
                m_statsUpdater.schedule(m_statsUpdaterTask, 0, 5000);
            }
        }
    }


    /**
     * <code>Channel.Destination</code> implementors should call this once when an event is received.
     */
    public void addEventIn() {
        m_eventsInCurrentCount++;
        m_eventsInTotalCount++;
    }//addEventIn


    /**
     * <code>Channel.Destination</code> implementors should call this once when an event is sent.
     */
    public void addEventOut() {
        m_eventsOutCurrentCount++;
        m_eventsOutTotalCount++;
    }//addEventOut


    /**
     * Gets the number of events received on the <code>Channel.Destination</code> since the stats started.
     *
     * @return a long number of events received.
     */
    public long getNbEventsIn() {
        return m_eventsInTotalCount;
    }//getNbEventsIn


    /**
     * Gets the number of events sent on the <code>Channel.Destination</code> since the stats started.
     *
     * @return a long number of events sent.
     */
    public long getNbEventsOut() {
        return m_eventsOutTotalCount;
    }//getNbEventsOut


    /**
     * Gets the rate of events received in the last stats interval.
     *
     * @return a double indicating how many events were received per second, on average, within the last interval.
     */
    public double getLastRateIn() {
        double result;
        if (m_eventsInLastCount == 0) {
            result = (1000d * m_eventsInCurrentCount) / m_rate_interval;
        } else {
            result = (1000d * m_eventsInLastCount) / m_rate_interval;
        }
        return result;
    }//getRateIn


    /**
     * Gets the rate of events sent in the last stats interval.
     *
     * @return a double indicating how many events were sent per second, on average.
     */
    public double getLastRateOut() {
        double result;
        if (m_eventsOutLastCount == 0) {
            result = (1000d * m_eventsOutCurrentCount) / m_rate_interval;
        } else {
            result = (1000d * m_eventsOutLastCount) / m_rate_interval;
        }
        return result;
    }//getLastRateOut


    /**
     * Gets the rate of events received since the stats started.
     *
     * @return a double indicating how many events were received per second, on average, since the stats started.
     */
    public double getRateIn() {
        final long interval = getTimeSinceStatsStarted();
        return (1000d * m_eventsInTotalCount) / interval;
    }//getRateIn


    /**
     * Gets the rate of events received in the last stats interval.
     *
     * @return a double indicating how many events were sent per second, on average.
     */
    public double getRateOut() {
        final long interval = getTimeSinceStatsStarted();
        return (1000d * m_eventsOutTotalCount) / interval;
    }//getRateOut


    /**
     * Gets the maximum between 1 and the number of milliseconds spent since the stats started.
     *
     * @return long the time since the stats started.
     */
    public long getTimeSinceStatsStarted() {
        final long interval = System.currentTimeMillis() - m_timeStarted;
        return (interval < 1) ? 1 : interval;
    }//getTimeSinceStatsStarted


    //TODO: Define and use Channel.Destination.getState instead. m_status is never updated. getStatus is only used by Hawk methods.
    /**
     * @return a char that indicates the status of the Destination,
     *         i.e. STATUS_CONNECTED, STATUS_CLOSED, etc.
     */
    public char getStatus() {
        return m_status;
    }


    /**
     * Resets all the stats data.
     */
    public void reset() {
        synchronized (m_activeStats) {
            m_eventsInCurrentCount = 0;
            m_eventsInLastCount = 0;
            m_eventsInTotalCount = 0;
            m_eventsOutCurrentCount = 0;
            m_eventsOutLastCount = 0;
            m_eventsOutTotalCount = 0;
            m_lastChecked = System.currentTimeMillis();
        }//synchronized
    }//reset

    /* *
    * @param status a char that indicates the status of the Destination,
    *               i.e. STATUS_CONNECTED, STATUS_CLOSED, etc.
    *               <p/>
    *               Sets the status of the Destination to STATUS_CLOSED.
    */
    /*public void setStatus(char status) {
        switch (status) {
            case STATUS_CLOSED    : setStatusClosed();    break;
            case STATUS_CONNECTED : setStatusConnected(); break;
            case STATUS_RUNNING   : setStatusRunning();    break;
            case STATUS_SUSPENDED : setStatusSuspended(); break;
            default               : m_status = status;
        }//switch
    }//setStatus
      */


    /**
     * Releases all static resources used by this <em>class</em>, including the stats timer.
     */
    public static void destroy() {
        if (m_statsUpdater != null) {
            m_statsUpdater.cancel();
            m_statsUpdater = null;
        }
    }

}//class
