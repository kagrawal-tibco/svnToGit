package com.tibco.cep.query.api.impl.local;

import java.util.Map;
import java.util.concurrent.ExecutorService;

import com.tibco.cep.query.api.QueryPolicy;
import com.tibco.cep.query.exec.QueryExecutionPlan;
import com.tibco.cep.query.stream.cache.SharedObjectSourceRepository;
import com.tibco.cep.query.stream.core.Source;
import com.tibco.cep.query.stream.impl.rete.ReteEntitySource;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.impl.rete.query.ReteQueryImpl;
import com.tibco.cep.query.stream.impl.rete.query.ReteQueryImpl_V2;
import com.tibco.cep.query.stream.impl.rete.query.SnapshotAssistant;
import com.tibco.cep.query.stream.impl.rete.service.SnapshotFeederForCQ;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.query.snapshot.Bridge;
import com.tibco.cep.runtime.session.RuleSession;

/**
 * Moved from PlanGeneratorImpl.
 *
 * Author: Karthikeyan Subramanian / Date: Jun 29, 2010 / Time: 11:08:15 AM
 */
public class GeneratedArtifacts {

    protected QueryExecutionPlan qep;

    protected ReteQuery reteQuery;

    protected SnapshotFeederForCQ optionalFeederForCQ;

    protected QueryPolicy appliedPolicy;

    protected final int threadpoolSize;

    protected GeneratedArtifacts(
            String instanceName,
            int threadpoolSize,
            QueryExecutionPlan plan,
            QueryPolicy sentPolicy,
            RuleSession ruleSession,
            SharedObjectSourceRepository sourceRepository,
            ExecutorService executorService) {
        final Map<String, Source> sourceMap = plan.getSources();
        final ReteEntitySource[] sourceArray = new ReteEntitySource[sourceMap.size()];
        this.threadpoolSize = threadpoolSize;

        appliedPolicy = sentPolicy;

        if(sentPolicy instanceof QueryPolicy.Continuous) {
            int i = 0;
            for(Map.Entry<String, Source> entry : sourceMap.entrySet()) {
                sourceArray[i++] = (ReteEntitySource) entry.getValue();
            }
            if(threadpoolSize > 0) {
                reteQuery = new ReteQueryImpl_V2(plan.getRegionName(), new ResourceId(instanceName),
                    sourceArray, plan.getSink(), false, ruleSession);
            } else {
                reteQuery = new ReteQueryImpl(plan.getRegionName(), new ResourceId(instanceName),
                    sourceArray, plan.getSink(), false, ruleSession);
            }

        } else if(sentPolicy instanceof QueryPolicy.Snapshot) {
            int i = 0;
            for(Map.Entry<String, Source> entry : sourceMap.entrySet()) {
                sourceArray[i++] = (ReteEntitySource) entry.getValue();
            }

            if(threadpoolSize > 0){
                reteQuery = new ReteQueryImpl_V2(plan.getRegionName(), new ResourceId(instanceName),
                        sourceArray, plan.getSink(), true, ruleSession);
            } else {
                reteQuery = new ReteQueryImpl(plan.getRegionName(), new ResourceId(instanceName),
                        sourceArray, plan.getSink(), true, ruleSession);
            }
        } else if(sentPolicy instanceof QueryPolicy.SnapshotThenContinuous) {
            boolean[] snapshotRequired = new boolean[sourceArray.length];
            int i = 0;
            boolean usesOneOrMoreSnapshots = false;
            for(Map.Entry<String, Source> entry : sourceMap.entrySet()) {
                snapshotRequired[i] = ((QueryPolicy.SnapshotThenContinuous) appliedPolicy)
                        .getSnapshotRequired(entry.getKey());

                usesOneOrMoreSnapshots = usesOneOrMoreSnapshots || snapshotRequired[i];

                sourceArray[i++] = (ReteEntitySource) entry.getValue();
            }

            if(usesOneOrMoreSnapshots == false) {
                appliedPolicy = new QueryPolicy.Continuous();
            }

            if(appliedPolicy instanceof QueryPolicy.SnapshotThenContinuous) {
                optionalFeederForCQ = new SnapshotFeederForCQ();

                SnapshotAssistant assistant = optionalFeederForCQ.step1();
                if(threadpoolSize > 0) {
                    reteQuery = new ReteQueryImpl_V2(plan.getRegionName(), new ResourceId(instanceName),
                        sourceArray, plan.getSink(), assistant, snapshotRequired,
                        ruleSession);
                } else {
                    reteQuery = new ReteQueryImpl(plan.getRegionName(), new ResourceId(instanceName),
                        sourceArray, plan.getSink(), assistant, snapshotRequired,
                        ruleSession);
                }
                optionalFeederForCQ.step2(reteQuery, sourceRepository, executorService);
            } else {
                if(threadpoolSize > 0) {
                    reteQuery = new ReteQueryImpl_V2(plan.getRegionName(), new ResourceId(instanceName),
                        sourceArray, plan.getSink(), false, ruleSession);
                } else {
                    reteQuery = new ReteQueryImpl(plan.getRegionName(), new ResourceId(instanceName),
                        sourceArray, plan.getSink(), false, ruleSession);
                }
            }
        }

        qep = plan;
    }

    public QueryPolicy getAppliedPolicy() {
        return appliedPolicy;
    }

    public ReteQuery getReteQuery() {
        return reteQuery;
    }

    /**
     * @return Non-<code>null</code> bridge only if execution type is {@link com.tibco.cep.query.api.QueryPolicy.Snapshot}.
     */
    public Bridge getOptionalBridge() {
        return qep.getBridge();
    }

    /**
     * @return Non-<code>null</code> only if execution type is {@link com.tibco.cep.query.api.QueryPolicy.SnapshotThenContinuous}.
     *         {@link com.tibco.cep.query.stream.impl.rete.service.SnapshotFeederForCQ#stepGo()} must be invoked
     *         after the {@link com.tibco.cep.query.stream.impl.rete.query.ReteQueryImpl} has started.
     */
    public SnapshotFeederForCQ getOptionalFeederForCQ() {
        return optionalFeederForCQ;
    }
}
