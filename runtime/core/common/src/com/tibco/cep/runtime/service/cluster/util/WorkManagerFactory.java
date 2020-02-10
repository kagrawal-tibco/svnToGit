/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 19/8/2010
 */

package com.tibco.cep.runtime.service.cluster.util;

import com.tibco.cep.runtime.management.impl.metrics.Constants;
import com.tibco.cep.runtime.service.basic.AsyncWorkerServiceWatcher;
import com.tibco.cep.runtime.session.JobGroupManager;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.util.FQName;
import com.tibco.cep.runtime.util.ProcessInfo;

import java.util.concurrent.atomic.AtomicReference;

/*
* Author: Ashwin Jayaprakash Date: Mar 13, 2009 Time: 1:56:46 PM
*/

public class WorkManagerFactory {
    public static final String COMMON_SCHEDULED_WORK_MANAGER_NAME = "CommonScheduledWorkManager";

    static final AtomicReference<ScheduledWorkManager> commonScheduledWorkManager =
            new AtomicReference<ScheduledWorkManager>();

    private static WorkManager createWMAtomically(String clusterName, String wmName,
                                                  AtomicReference<? extends WorkManager> wmRef,
                                                  boolean scheduled,
                                                  RuleServiceProvider rsp) {
        WorkManager workManager = null;

        boolean success = false;
        while (success == false) {
            workManager = wmRef.get();
            if (workManager != null && workManager.isActive()) {
                return workManager;
            }

            String procInfo = ProcessInfo.getProcessIdentifier();
            FQName fqn = new FQName(clusterName,
                    procInfo,
                    Constants.KEY_TEMPLATE_AGENT_NAME,
                    Constants.KEY_TEMPLATE_AGENT_ID,
                    AsyncWorkerServiceWatcher.AsyncWorkerService.class.getSimpleName(),
                    wmName);

            int cpus = Runtime.getRuntime().availableProcessors();

            WorkManager tempWM = null;
            if (scheduled) {
                tempWM = new ScheduledWorkManagerImpl(fqn, wmName, cpus, rsp);
            } else {
                tempWM = new WorkManagerImpl(fqn, wmName, cpus, rsp);
            }

            // Generics limitation.
            AtomicReference genericWMRef = wmRef;
            success = genericWMRef.compareAndSet(workManager, tempWM);

            if (success == false) {
                tempWM.shutdown();
            } else {
                tempWM.start();
                workManager = tempWM;
            }
        }

        return workManager;
    }

    public static ScheduledWorkManager fetchCommonScheduledWorkManager(String clusterName,
                                                                       RuleServiceProvider rsp) {
        return (ScheduledWorkManager) createWMAtomically(clusterName,
                COMMON_SCHEDULED_WORK_MANAGER_NAME, commonScheduledWorkManager, true, rsp);
    }

    public static WorkManager create(String clusterName,
                                     String optionalAgentName,
                                     Integer optionalAgentId,
                                     String name,
                                     int coreNumberOfThreads,
                                     int maxNumberOfThreads,
                                     int maxJobQueueSize,
                                     RuleServiceProvider rsp) {
        FQName fqName = makeFQName(clusterName, optionalAgentName, optionalAgentId, name);

        return new WorkManagerImpl(fqName, name, coreNumberOfThreads, maxNumberOfThreads, maxJobQueueSize, rsp, null);
    }

    public static WorkManager create(String clusterName,
            String optionalAgentName,
            Integer optionalAgentId,
            String name,
            int numberOfThreads,
            int maxJobQueueSize,
            RuleServiceProvider rsp) {
        FQName fqName = makeFQName(clusterName, optionalAgentName, optionalAgentId, name);

        return new WorkManagerImpl(fqName, name, numberOfThreads, maxJobQueueSize, rsp);
    }

    public static WorkManager createUnpausable(String clusterName,
                                               String optionalAgentName,
                                               Integer optionalAgentId,
                                               String name,
                                               int numberOfThreads,
                                               RuleServiceProvider rsp) {
        FQName fqName = makeFQName(clusterName, optionalAgentName, optionalAgentId, name);

        return new WorkManagerImpl(fqName, name, numberOfThreads, numberOfThreads * 2, rsp, false);
    }

    private static FQName makeFQName(String clusterName, String optionalAgentName,
                                     Integer optionalAgentId, String name) {
        String agentName = Constants.KEY_TEMPLATE_AGENT_NAME;
        agentName = (optionalAgentName == null) ? agentName : optionalAgentName;

        String agentId = Constants.KEY_TEMPLATE_AGENT_ID;
        agentId = (optionalAgentId == null) ? agentId : optionalAgentId.toString();

        return new FQName(clusterName, ProcessInfo.getProcessIdentifier(), agentName,
                agentId, AsyncWorkerServiceWatcher.AsyncWorkerService.class.getSimpleName(), name);
    }

    public static WorkManager create(String clusterName,
                                     String optionalAgentName,
                                     Integer optionalAgentId,
                                     String name,
                                     int numberOfThreads,
                                     RuleServiceProvider rsp) {
        return create(clusterName, optionalAgentName, optionalAgentId, name, numberOfThreads,
                2 * numberOfThreads, rsp);
    }

    public static WorkManager create(String clusterName,
                                     String optionalAgentName,
                                     Integer optionalAgentId,
                                     String name,
                                     int numberOfThreads,
                                     int maxJobQueueSize,
                                     RuleServiceProvider rsp,
                                     JobGroupManager groupManager) {
        FQName fqName = makeFQName(clusterName, optionalAgentName, optionalAgentId, name);

        return new WorkManagerImpl(fqName, name, numberOfThreads, maxJobQueueSize, rsp,
                groupManager);
    }
}
