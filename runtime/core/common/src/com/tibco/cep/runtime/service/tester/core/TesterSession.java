package com.tibco.cep.runtime.service.tester.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import com.tibco.cep.runtime.service.tester.model.InvocationObject;
import com.tibco.cep.runtime.service.tester.model.ReteObject;
import com.tibco.cep.runtime.service.tester.model.TesterObject;
import com.tibco.cep.runtime.session.RuleSession;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: May 1, 2010
 * Time: 6:07:13 PM
 * <!--
 * Add Description of the class here
 * -->
 */
//TODO add session expiry
public class TesterSession {

    private String sessionId;

    private static final int MAX_RUNS = 10;

    /**
     * Unbounded queue to hold execution objects.
     */
    protected BlockingQueue<TesterRun> cachedRuns = new ArrayBlockingQueue<TesterRun>(MAX_RUNS);

    /**
     * Any objects remaining at the time session was stopped before all RTCs completed
     */
    protected List<ReteObject> residualObjectsQueue = new ArrayList<ReteObject>();

    private final ReentrantLock lock = new ReentrantLock();

    /**
     * Oldest runs
     */
    private List<TesterRun> deprecatedRuns = new ArrayList<TesterRun>();

    private TesterRuleServiceProvider RSP;

    private String sessionName;

    /**
     * The Rulesession this Tester session is working with
     */
    private RuleSession ruleSession;

    /**
     * The current status of the Session
     */
    private volatile int status;

    private static final int RUNNING = 0x1;

    private static final int STOPPED = 0x2;

    /**
     * Counter of concurrent runs
     */
    private AtomicInteger runCounter = new AtomicInteger(1);

    /**
     *
     * @param RSP
     * @param ruleSession
     * @param testerSessionName
     */
    public TesterSession(TesterRuleServiceProvider RSP,
                         RuleSession ruleSession,
                         String testerSessionName) {
        this.RSP = RSP;
        this.sessionName = testerSessionName;
        this.ruleSession = ruleSession;
        sessionId = "TS-" + UUID.randomUUID().toString();
    }

    /**
     * Starts a new {@link TesterRun} for this {@link TesterSession}.
     * @return {@link TesterRun}
     */
    public TesterRun start() {
         //If running session, no need to start
        if ((status & RUNNING) == RUNNING) {
//				TesterPlugin.debug("Tester Session Named {0} already running ", sessionName);
        } else {
            status = RUNNING;
        }
        //Create a new Run
        TesterRun testerRun =
                new TesterRun(ruleSession.getName() + "/" + sessionName + "/Run-" + runCounter.getAndIncrement(), this);

        try {
            lock.lock();

            if (!cachedRuns.offer(testerRun)) {
                //Remove the oldest entry. ArrayBlockingQueue gives that
                TesterRun oldestRun = cachedRuns.poll();

                deprecatedRuns.add(oldestRun);

                cachedRuns.offer(testerRun);
            }
        } finally {
            lock.unlock();
        }
        return testerRun;
    }

    /**
     * Stop this {@link TesterSession}
     */
    public void stop() {
        if (RSP != null) {

            status = STOPPED;

            RSP.deregisterSession(sessionId);
        }
    }

    /**
     * @return the sessionId
     */
    public final String getSessionId() {
        return sessionId;
    }


    /**
     * Return the name of the Session.
     * @return
     */
    public String getSessionName() {
        return sessionName;
    }

    /**
     * Get instance of {@link com.tibco.cep.runtime.session.RuleServiceProvider} associated with this {@link TesterSession}
     * @return
     */
    public TesterRuleServiceProvider getRSP() {
        return RSP;
    }

    /**
     * Returns a {@link List} of currently runs
     * @return
     */
    public Iterator<TesterRun> getCurrentRuns() {
        try {
            lock.lock();

            return cachedRuns.iterator();

        } finally {
            lock.unlock();
        }
    }

    public RuleSession getRuleSession() {
        return ruleSession;
    }

    /**
     * Dispatch the causal objects for execution to this session
     * which in turn dispatches to all its {@link TesterRun}s and finds the run
     * which has all causal objects in it.
     * @param invocationObject
     * @return the {@link TesterRun} which contains all the causal objects.
     */
    public TesterRun dispatch(InvocationObject invocationObject) {
        //Get causal objects
        TesterObject[] causalObjects = invocationObject.getCausalObjects();
        try {
            lock.lock();

            TesterRun[] runs = new TesterRun[cachedRuns.size()];
            cachedRuns.toArray(runs);
            for (int i = runs.length-1; i >= 0; i--) {
                //Search each run for causal objects, starting with the most recent
				TesterRun run = runs[i];
                boolean hasCausalObjects = run.hasCausalObjects(causalObjects);

                if (hasCausalObjects) {
                    return run;
                }
			}
        } finally {
            lock.unlock();
        }
        return null;
    }
}
