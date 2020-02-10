package com.tibco.cep.runtime.session.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.kernel.core.rete.FilterNode;
import com.tibco.cep.kernel.core.rete.JoinNode;
import com.tibco.cep.kernel.core.rete.ReteListener;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.rule.Rule;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.management.impl.metrics.Constants;
import com.tibco.cep.runtime.metrics.CountingStopWatch;
import com.tibco.cep.runtime.metrics.StopWatchManager;
import com.tibco.cep.runtime.metrics.StopWatchOwner;
import com.tibco.cep.runtime.metrics.StopWatchOwnerOption;
import com.tibco.cep.runtime.metrics.util.StopWatchDispenser;
import com.tibco.cep.runtime.service.ServiceRegistry;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Feb 21, 2009 Time: 6:28:01 PM
*/
public class RuleSessionStopWatchKeeper {
    static final Logger logger = LogManagerFactory.getLogManager().getLogger(RuleSessionStopWatchKeeper.class);

    protected ConcurrentHashMap<Object, StopWatchDispenser<CountingStopWatch>> allDispensers;

    protected ThreadLocal<PerThreadContext> allPerThreadContexts;

    protected StopWatchManager stopWatchManager;

    protected FQName metricDefName;

    protected ConcurrentHashMap<String, FQName> inReteNameToFQNames;

    protected String[] inReteNamePrefix;

    protected ReteListenerImpl reteListener;

    private boolean createThreadLocalWatches;

    public RuleSessionStopWatchKeeper(String clusterName, String processId,
                                      String agentName, int agentId, boolean createThreadLocalWatches) {
        this.metricDefName =
        new FQName(Constants.KEY_TEMPLATE_CLUSTER_NAME, Constants.KEY_TEMPLATE_PROCESS_ID,
        Constants.KEY_TEMPLATE_AGENT_NAME, Constants.KEY_TEMPLATE_AGENT_ID,
        InferenceAgent.class.getSimpleName(), "%inReteCallType%", "%inReteCallIndentifier");

        this.inReteNameToFQNames = new ConcurrentHashMap<String, FQName>();

        this.inReteNamePrefix = new String[]{clusterName, processId, agentName, agentId + "",
        InferenceAgent.class.getSimpleName()};

        this.allDispensers =
        new ConcurrentHashMap<Object, StopWatchDispenser<CountingStopWatch>>(32);
        this.allPerThreadContexts = new ThreadLocal<PerThreadContext>();
        this.createThreadLocalWatches = createThreadLocalWatches;
    }

    public ReteListener init() {
        ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
        this.stopWatchManager = registry.getService(StopWatchManager.class);

        this.reteListener = new ReteListenerImpl();

        return this.reteListener;
    }

    public void discard() {
        allDispensers.clear();
    }

    //------------

    private CountingStopWatch fetchWatch(String reteCallType, String reteCallIndentifier) {
        String key = reteCallType+":"+reteCallIndentifier;
        StopWatchDispenser<CountingStopWatch> dispenser = allDispensers.get(key);

        if (dispenser != null) {
            return dispenser.createOrGetStopWatchForThread();
        }

        //----------

        String inReteName = key.toString();
        FQName metricName = inReteNameToFQNames.get(inReteName);
        if (metricName == null) {
            String[] fqnArray = new String[inReteNamePrefix.length + 2];
            int i = 0;
            for (; i < inReteNamePrefix.length; i++) {
                fqnArray[i] = inReteNamePrefix[i];
            }
            fqnArray[i] = reteCallType;
            fqnArray[++i] = reteCallIndentifier;

            metricName = new FQName(fqnArray);

            inReteNameToFQNames.put(inReteName, metricName);
        }

        //We use the same basic parent-metric-def for all metrics.
        StopWatchOwner<CountingStopWatch> watchOwner =
                stopWatchManager.fetchOwner(metricDefName, metricName,
                        CountingStopWatch.class, StopWatchOwnerOption.OPTION_MEASURE_LATENCY);

        dispenser = new StopWatchDispenser<CountingStopWatch>(watchOwner, createThreadLocalWatches);

        StopWatchDispenser<CountingStopWatch> existingDispenser =
                allDispensers.putIfAbsent(key, dispenser);

        if (existingDispenser != null) {
            dispenser = existingDispenser;
        }

        return dispenser.createOrGetStopWatchForThread();
    }

    private PerThreadContext fetchPerThreadContext() {
        PerThreadContext perThreadContext = allPerThreadContexts.get();

        if (perThreadContext != null) {
            return perThreadContext;
        }

        //----------

        perThreadContext = new PerThreadContext();
        allPerThreadContexts.set(perThreadContext);

        return perThreadContext;
    }

    protected class ReteListenerImpl extends AbstractReteListener {
        @Override
        public void rtcStart(int rtcType, Object context) {
            try {
                PerThreadContext perThreadContext = RuleSessionStopWatchKeeper.this.fetchPerThreadContext();
                String callType = "General";
                String callIdentifier = null;

                switch (rtcType) {
                    case RTC_OBJECT_ASSERTED:
                    case RTC_OBJECT_MODIFIED:
                    case RTC_OBJECT_DELETED:
                    {
                        callType = "Object";
                        callIdentifier = context.getClass().getName();
                        break;
                    }

                    case RTC_EVENT_EXPIRED:
                    {
                        callType = "EventExpiry";
                        callIdentifier = context.getClass().getName();
                        break;
                    }

                    case RTC_INVOKE_ACTION: {
                        callType = "Action";
                        callIdentifier = context.getClass().getName();
                        break;
                    }

                    case RTC_REPEAT_TIMEEVENT: {
                        callType = "TimerEvent";
                        callIdentifier = context.getClass().getName();
                        break;
                    }

                    case RTC_EXECUTE_RULE: {
                        callType = "API";
                        callIdentifier = "Rule Execution";
                        break;
                    }

                    case RTC_INVOKE_FUNCTION: {
                        callType = "Rule";
                        callIdentifier = ((RuleFunction) context).getSignature();
                        break;
                    }

                    case RTC_POST_PROCESS: {
                        callIdentifier = context.toString();
                        break;
                    }

                    default: {
                        perThreadContext.push();
                        break;
                    }
                }
                if (callIdentifier == null) {
                    callIdentifier = context.getClass().getName();
                }
                if (logger.isEnabledFor(Level.TRACE) == true) {
                    logger.log(Level.TRACE, "rtcStart(%s, %s:%s):Local Stack Size Is %d", rtcTypes[rtcType], callType, callIdentifier, perThreadContext.perThreadCallStack.size());
                }
                //push a general purpose RTC stop watch
                perThreadContext.push(fetchWatch("General", "Rete"));
                //push the specific purpose stop watch
                CountingStopWatch watch = fetchWatch(callType, callIdentifier);
                perThreadContext.push(watch);
            }
            catch (Exception e) {
                logger.log(Level.INFO, e, "Error occurred while capturing RTC metrics");
            }
        }

        @Override
        public void rtcResolved() {
            if (logger.isEnabledFor(Level.TRACE) == true) {
                logger.log(Level.TRACE, "rtcResolved()");
            }
        }

        @Override
        public void rtcEnd() {
            if (logger.isEnabledFor(Level.TRACE) == true) {
                logger.log(Level.TRACE, "rtcEnd()");
            }
            try {
                //pop the specific purpose stop watch
                PerThreadContext perThreadContext = RuleSessionStopWatchKeeper.this.fetchPerThreadContext();
                perThreadContext.pop();
                //pop the general purpose RTC stop watch
                perThreadContext.pop();
            } catch (Exception e) {
                logger.log(Level.INFO, e, "Error occurred while capturing RTC metrics");
            }
        }

        @Override
        public void actionStart(int actionType, Object context) {
            PerThreadContext perThreadContext = RuleSessionStopWatchKeeper.this.fetchPerThreadContext();
            String callType = "General";
            String callIdentifier = null;
            switch(actionType) {
                case ACTION_RULE_ACTION:
                    callType = "Rule";
                    callIdentifier = ((Rule) context).getUri();
                    break;
                case ACTION_EVENT_EXPIRY:
                    callType = "Rule";
                    callIdentifier = ((Event) context).getType();
                    break;
                case ACTION_INVOKE_ACTION:
                    callIdentifier = context.getClass().getName();
                    break;
                case ACTION_INVOKE_FUNCTION:
                    callType = "Rule";
                    callIdentifier = ((RuleFunction) context).getSignature();
                    break;
            }
            if (callIdentifier == null) {
                callIdentifier = context.getClass().getName();
            }
            if (logger.isEnabledFor(Level.TRACE) == true) {
                logger.log(Level.TRACE, "actionStart(%s,%s:%s)", actionTypes[actionType], callType, callIdentifier);
            }
            CountingStopWatch watch = fetchWatch(callType, callIdentifier);
            perThreadContext.push(watch);
        }

        @Override
        public void actionExecuted() {
            if (logger.isEnabledFor(Level.TRACE) == true) {
                logger.log(Level.TRACE, "actionExecuted()");
            }
        }

        @Override
        public void actionEnd(int agendaSize) {
            if (logger.isEnabledFor(Level.TRACE) == true) {
                logger.log(Level.TRACE, "actionEnd(%d)", agendaSize);
            }
            try {
                PerThreadContext perThreadContext = RuleSessionStopWatchKeeper.this.fetchPerThreadContext();
                perThreadContext.pop();
            } catch (Exception e) {
                logger.log(Level.INFO, e, "Error occurred while capturing RTC metrics");
            }
        }

        @Override
        public void filterConditionStart(FilterNode filerNode) {
            PerThreadContext perThreadContext = RuleSessionStopWatchKeeper.this.fetchPerThreadContext();
            String callIdentifier = filerNode.getRule().getUri();
            if (logger.isEnabledFor(Level.TRACE) == true) {
                logger.log(Level.TRACE, "filterConditionStart(%s)", callIdentifier);
            }
            CountingStopWatch watch = fetchWatch("RuleFilterCondition", callIdentifier);
            perThreadContext.push(watch);
        }

        @Override
        public void filterConditionEnd(boolean success) {
            if (logger.isEnabledFor(Level.TRACE) == true) {
                logger.log(Level.TRACE, "filterConditionEnd(%s)", Boolean.valueOf(success));
            }
            PerThreadContext perThreadContext = RuleSessionStopWatchKeeper.this.fetchPerThreadContext();
            perThreadContext.pop();
        }

        @Override
        public void joinConditionStart(JoinNode joinNode, boolean leftSearch) {
            PerThreadContext perThreadContext = RuleSessionStopWatchKeeper.this.fetchPerThreadContext();
            String callIdentifier = joinNode.getRule().getUri();
            if (logger.isEnabledFor(Level.TRACE) == true) {
                logger.log(Level.TRACE, "joinConditionStart(%s,%s)", callIdentifier, Boolean.valueOf(leftSearch));
            }
            CountingStopWatch watch = fetchWatch("RuleJoinCondition", callIdentifier);
            perThreadContext.push(watch);
        }

        @Override
        public void joinConditionEnd(int numSuccess, int numFailed) {
            if (logger.isEnabledFor(Level.TRACE) == true) {
                logger.log(Level.TRACE, "joinConditionEnd(%d,%d)", numSuccess, numFailed);
            }
            PerThreadContext perThreadContext = RuleSessionStopWatchKeeper.this.fetchPerThreadContext();
            perThreadContext.pop();
        }

    }

    /*
    todo How are exceptions handled - *end is always called? Will it leave an
    uncleared PerThreadContext?
    */

    /**
     * This is a work around for the bad listener API whose *end(..) methods do not carry context.
     * The stack has to be maintained by us.
     * <p/>
     * All this to avoid calling {@link com.tibco.cep.runtime.metrics.CountingStopWatch#start()}
     * more than once before calling a {@link com.tibco.cep.runtime.metrics.CountingStopWatch#stop()}.
     */
    protected static class PerThreadContext {
        /**
         * The {@link com.tibco.cep.kernel.core.rete.ReteListener} does not have a symmetric start
         * and end method signature. Since we do not profile all start methods, it's hard to tell if
         * an end method was preceeded by a start method - with a stopwatch. So, we push and pop
         * this junk object when the stopwatch is not to be pushed.
         */
        protected static final Object NOT_A_STOPWATCH = new Object();

        protected LinkedList<Object> perThreadCallStack;

        protected HashMap<CountingStopWatch, Integer> watchesAndRefCounts;

        public PerThreadContext() {
            this.perThreadCallStack = new LinkedList<Object>();
            this.watchesAndRefCounts = new HashMap<CountingStopWatch, Integer>();
        }

        public void push(CountingStopWatch stopWatch) {
            Integer currentCount = watchesAndRefCounts.get(stopWatch);

            if (currentCount == null) {
                watchesAndRefCounts.put(stopWatch, 1);
                stopWatch.start();
            }
            else {
                watchesAndRefCounts.put(stopWatch, (currentCount + 1));
            }

            stopWatch.count();

            perThreadCallStack.addLast(stopWatch);
        }

        public void push() {
            perThreadCallStack.addLast(NOT_A_STOPWATCH);
        }

        public void pop() {
            if (perThreadCallStack.isEmpty()) {
                return;
            }

            Object fromStack = perThreadCallStack.removeLast();
            if (fromStack == NOT_A_STOPWATCH) {
                return;
            }

            //----------

            CountingStopWatch stopWatch = (CountingStopWatch) fromStack;

            /*
            Removes are better because the number of recursive calls are going to be few. Count
            will be max 1 most of the time.
            */
            Integer count = watchesAndRefCounts.remove(stopWatch);

            if (count == 1) {
                stopWatch.stop();
            }
            //Hmm... so it was a recursive call. Put one less back.
            else {
                watchesAndRefCounts.put(stopWatch, (count - 1));
            }
        }
    }
}
