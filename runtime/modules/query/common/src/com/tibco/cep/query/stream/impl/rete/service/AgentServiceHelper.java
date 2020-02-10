package com.tibco.cep.query.stream.impl.rete.service;

import java.util.Collection;

import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.impl.monitor.QueryMonitor;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.monitor.Logger;
import com.tibco.cep.query.stream.monitor.ResourceId;

/*
* Author: Ashwin Jayaprakash Date: Apr 14, 2009 Time: 1:35:26 PM
*/
public class AgentServiceHelper {
    public static void resumeQueriesInRegion(String agentName) {
        Registry registry = Registry.getInstance();
        QueryMonitor queryMonitor = registry.getComponent(QueryMonitor.class);

        //----------

        Collection<ReteQuery> queries = queryMonitor.listContinuousQueries(agentName);

        for (ReteQuery query : queries) {
            if (query.hasStopped() == false) {
                query.resume();

                //Just in case it's stuck somewhere when.
                query.ping();
            }
        }

        //----------

        queries = queryMonitor.listSnapshotQueries(agentName);

        for (ReteQuery query : queries) {
            if (query.hasStopped() == false) {
                query.resume();

                //Just in case it's stuck somewhere when.
                query.ping();
            }
        }
    }

    public static void pauseQueriesInRegion(ResourceId resourceId, String agentName) {
        Registry registry = Registry.getInstance();
        QueryMonitor queryMonitor = registry.getComponent(QueryMonitor.class);
        Logger logger = registry.getComponent(Logger.class);

        //----------

        Collection<ReteQuery> queries = queryMonitor.listContinuousQueries(agentName);

        waitForQueriesToPause(resourceId, queries, logger);

        //----------

        queries = queryMonitor.listSnapshotQueries(agentName);

        waitForQueriesToPause(resourceId, queries, logger);
    }

    public static void waitForQueriesToPause(ResourceId resourceId, Collection<ReteQuery> queries,
                                             Logger logger) {
        //Make pause request first.
        for (ReteQuery query : queries) {
            query.pause();
        }

        //------------

        int waitCycles = 0;
        for (; ;) {
            boolean someStillRunning = false;

            for (ReteQuery query : queries) {
                boolean notDone = !(query.isPaused() || query.hasStopped());

                someStillRunning = someStillRunning || notDone;

                if (notDone) {
                    query.ping();

                    if (waitCycles > 3 && logger.isAllowed(Logger.LogLevel.WARNING)) {
                        logger.log(Logger.LogLevel.WARNING, resourceId, query.getName() +
                                " still running after " + waitCycles + " attempts to pause it.");
                    }
                }
            }

            if (someStillRunning == false) {
                break;
            }
            else if (waitCycles == 10 && logger.isAllowed(Logger.LogLevel.ERROR)) {
                logger.log(Logger.LogLevel.ERROR, resourceId,
                        "Some queries are still running." +
                                " Giving up further attempts to pause this set." +
                                " Resuming other work.");

                break;
            }

            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e) {
                //Ignore.
            }

            waitCycles++;
        }
    }
}
