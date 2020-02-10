package com.tibco.cep.query.stream.partition.purge;

import java.util.LinkedHashMap;

import com.tibco.cep.query.stream.aggregate.AggregateInfo;
import com.tibco.cep.query.stream.aggregate.AggregateItemInfo;
import com.tibco.cep.query.stream.aggregate.AggregatedStream;
import com.tibco.cep.query.stream.context.Context;
import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.LocalContext;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.core.SubStream;
import com.tibco.cep.query.stream.core.SubStreamOwner;
import com.tibco.cep.query.stream.monitor.Logger;
import com.tibco.cep.query.stream.monitor.Logger.LogLevel;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.partition.Window;
import com.tibco.cep.query.stream.partition.WindowOwner;

/*
 * Author: Ashwin Jayaprakash Date: Nov 20, 2007 Time: 12:50:17 PM
 */

public class WindowPurgeManager {
    protected final Window window;

    protected final WindowOwner windowOwner;

    protected final WindowSpy windowSpy;

    protected final WindowStats windowStats;

    protected final WindowSpyChannelEnd spyChannelEnd;

    /**
     * Can be <code>null</code>
     */
    protected final AggregatedStream aggregatedStream;

    protected final WindowPurgeAdvisor windowPurgeAdvisor;

    protected ScheduledWindowPurgeAdvice scheduledPurgeAdvice;

    protected ImmediateWindowPurgeAdvice immAdviceFromPrevCycleEnd;

    /**
     * No explicit Aggregates.
     *
     * @param window
     * @param windowOwner
     * @param windowPurgeAdvisor
     */
    public WindowPurgeManager(Window window, WindowOwner windowOwner,
                              WindowPurgeAdvisor windowPurgeAdvisor) {
        this(window, windowOwner, windowPurgeAdvisor, null);
    }

    /**
     * @param window
     * @param windowOwner
     * @param windowPurgeAdvisor
     * @param purgeAggregateInfo Can be <code>null</code>.
     */
    @SuppressWarnings("unchecked")
    public WindowPurgeManager(Window window, WindowOwner windowOwner,
                              WindowPurgeAdvisor windowPurgeAdvisor,
                              AggregateInfo purgeAggregateInfo) {
        this.window = window;
        this.windowOwner = windowOwner;

        ResourceId spyName = new ResourceId(window.getResourceId(), "WindowSpy");
        this.windowSpy = new WindowSpy(spyName, window);

        String[] otherAggregateAliases = null;
        if (purgeAggregateInfo != null) {
            LinkedHashMap<String, AggregateItemInfo> aggrItemInfos =
                    purgeAggregateInfo.getAggregateItems();
            otherAggregateAliases =
                    aggrItemInfos.keySet().toArray(new String[aggrItemInfos.size()]);
        }
        this.windowStats =
                new WindowStats(this.window.getGroupKey().getGroupColumns(), otherAggregateAliases);

        if (purgeAggregateInfo == null) {
            this.aggregatedStream = null;

            ResourceId spyChannelEndId =
                    new ResourceId(this.windowSpy.getResourceId(), "WindowSpyChannelEnd");
            this.spyChannelEnd =
                    new WindowSpyChannelEnd(spyChannelEndId, this.windowSpy, window,
                            this.windowStats);
        }
        else {
            ResourceId purgeAdvisorAggrId =
                    new ResourceId(window.getResourceId(), "PurgeAdvisorAggregator");
            this.aggregatedStream =
                    new AggregatedStream(this.windowSpy, purgeAdvisorAggrId, purgeAggregateInfo,
                            false);

            ResourceId spyChannelEndId = new ResourceId(purgeAdvisorAggrId, "WindowSpyChannelEnd");
            this.spyChannelEnd = new WindowSpyChannelEnd(spyChannelEndId, this.aggregatedStream,
                    window, this.windowStats, otherAggregateAliases);

            boolean b = this.windowSpy.producesDStream();
            this.aggregatedStream.setOwner(new AggregatedStreamOwner(b));
        }

        this.windowPurgeAdvisor = windowPurgeAdvisor;
    }

    public Window getWindow() {
        return window;
    }

    public WindowOwner getWindowOwner() {
        return windowOwner;
    }

    public WindowSpy getWindowSpy() {
        return windowSpy;
    }

    public AggregatedStream getAggregatedStream() {
        return aggregatedStream;
    }

    public WindowPurgeAdvisor getWindowPurgeAdvisor() {
        return windowPurgeAdvisor;
    }

    // ---------

    /**
     * @param context
     * @return <b>Has</b> to be <code>null</code> if there is no advice.
     */
    public ImmediateWindowPurgeAdvice cycleStart(Context context) {
        return adviceGetter(context, true);
    }

    protected ImmediateWindowPurgeAdvice adviceGetter(Context context, boolean cycleStart) {
        /*
         * If there is a pending scheduledPurgeAdvice, nothing else will be done
         * until it completes.
         */

        ImmediateWindowPurgeAdvice retVal = immAdviceFromPrevCycleEnd;

        // Set to null. retVal has the value now.
        immAdviceFromPrevCycleEnd = null;

        if (retVal != null) {
            return retVal;
        }

        if (scheduledPurgeAdvice != null) {
            // Check if Time to apply advice.
            if (context.getQueryContext().getCurrentCycleTimestamp() >= scheduledPurgeAdvice
                    .getScheduleAt()) {
                retVal = timeToSeekScheduledAdvice(context, cycleStart);
            }
        }
        else {
            GlobalContext gc = context.getGlobalContext();
            DefaultQueryContext qc = context.getQueryContext();

            WindowPurgeAdvice windowPurgeAdvice = windowPurgeAdvisor.advise(gc, qc, windowStats);

            if (windowPurgeAdvice != null) {
                if (windowPurgeAdvice instanceof ScheduledWindowPurgeAdvice) {
                    /*
                     * Store it for later, when the time is right to apply
                     * advice.
                     */
                    scheduledPurgeAdvice = (ScheduledWindowPurgeAdvice) windowPurgeAdvice;

                    long sch = scheduledPurgeAdvice.getScheduleAt();
                    if (qc.getCurrentCycleTimestamp() >= sch) {
                        retVal = timeToSeekScheduledAdvice(context, cycleStart);
                    }
                    else {
                        windowOwner.scheduleWindowAt(context, window, sch);
                    }
                }
                else if (windowPurgeAdvice instanceof ImmediateWindowPurgeAdvice) {
                    ImmediateWindowPurgeAdvice temp =
                            (ImmediateWindowPurgeAdvice) windowPurgeAdvice;

                    retVal = handleImmediatePurgeAdvice(context, cycleStart, temp);
                }
            }
        }

        return retVal;
    }

    /**
     * @param context
     * @param cycleStart
     * @return <code>null</code> if there is no immediate advise.
     */
    private ImmediateWindowPurgeAdvice timeToSeekScheduledAdvice(Context context,
                                                                 boolean cycleStart) {
        ImmediateWindowPurgeAdvice retVal = scheduledPurgeAdvice.adviseNow(windowStats);
        // Clear the schedule.
        scheduledPurgeAdvice = null;

        //Seek confirmation.
        retVal = handleImmediatePurgeAdvice(context, cycleStart, retVal);

        return retVal;
    }

    /**
     * @param context
     * @param cycleStart
     * @param advice
     * @return <code>null</code> if there is no immediate advise.
     */
    private ImmediateWindowPurgeAdvice handleImmediatePurgeAdvice(Context context,
                                                                  boolean cycleStart,
                                                                  ImmediateWindowPurgeAdvice advice) {
        ImmediateWindowPurgeAdvice retVal = null;

        if (cycleStart) {
            // Use it immediately.
            retVal = advice;
        }
        else {
            // Save for the beginning of the next cycle.
            immAdviceFromPrevCycleEnd = advice;

            windowOwner.scheduleSubStreamForNextCycle(context, window);
        }

        return retVal;
    }

    /**
     * @param context
     */
    public void cycleEnd(Context context) {
        adviceGetter(context, false);
    }

    public void discard(Context context) {
        if (aggregatedStream != null) {
            LocalContext originalContext = context.getLocalContext();

            LocalContext nullLocalContext = new LocalContext(true);
            try {
                context.setLocalContext(nullLocalContext);

                /*
                 * The output gets ignored. This is just to purge the last [0,
                 * 0, ..] aggregate-column Tuples from the AggregatedStream.
                 */
                try {
                    aggregatedStream.process(context);
                }
                catch (Exception e) {
                    Logger logger = Registry.getInstance().getComponent(Logger.class);

                    String s = "Error occurred during WindowPurge-Aggregate Sub-Stream cleanup: "
                            + aggregatedStream.getResourceId();
                    logger.log(LogLevel.WARNING, s, e);
                }
            }
            finally {
                // Re-instate the original.
                context.setLocalContext(originalContext);
            }
        }

        windowStats.clearStats();
        try {
            windowSpy.stop();
        }
        catch (Exception e) {
            Logger logger = Registry.getInstance().getComponent(Logger.class);

            String s =
                    "Error occurred during WindowSpy cleanup: " + windowSpy.getResourceId();
            logger.log(LogLevel.WARNING, s, e);
        }
    }

    // ---------

    public class AggregatedStreamOwner implements SubStreamOwner {
        protected final boolean canSendDeletesToSubStream;

        public AggregatedStreamOwner(boolean canSendDeletesToSubStream) {
            this.canSendDeletesToSubStream = canSendDeletesToSubStream;
        }

        public void scheduleSubStreamForNextCycle(Context context, SubStream subStream) {
            /*
             * Do nothing. This will get invoked by the AggregateStream, just
             * before the Window gets discarded.
             */
        }

        public boolean canSendDeletesToSubStream() {
            return canSendDeletesToSubStream;
        }
    }
}
