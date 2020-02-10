/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.txn;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.util.Collection;
import java.util.Iterator;

import com.tibco.be.util.XiSupport;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.NamedInstance;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.scheduler.impl.WorkerBasedController;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.cluster.events.notification.ClusterEntityListener;
import com.tibco.cep.runtime.service.cluster.filters.ClassFilter;
import com.tibco.cep.runtime.service.cluster.filters.NeverFilter;
import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiSerializer;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Feb 28, 2009
 * Time: 2:48:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class RtcTransactionSubscriber_V2 implements ClusterEntityListener {
    InferenceAgent cacheAgent;
    int[] topic;
    Logger logger;

    /**
     * @param cacheAgent
     * @param topic      Can be <code>null</code>, which means that the Rete will not receive any thing.
     */
    public RtcTransactionSubscriber_V2(InferenceAgent cacheAgent, int[] topic) {
        this.cacheAgent = cacheAgent;
        this.logger = cacheAgent.getCluster().getRuleServiceProvider().getLogger(RtcTransactionSubscriber_V2.class);
        this.topic = topic;
    }

    public boolean requireAsyncInvocation() {
        return true;
    }

    public String getListenerName() {
        return cacheAgent.getAgentName();
    }

//    public Filter getEntityFilter() {
//        if (topic != null)
//            return new ClassFilter(topic);
//        else
//            return new NeverFilter();
//    }


    protected Class getEntityClass(int typeId) throws Exception {
        return cacheAgent.getCluster().getMetadataCache().getClass(typeId);
    }

    protected boolean isActive() {
        return cacheAgent.getAgentState() == CacheAgent.AgentState.ACTIVATED;
    }

    protected boolean isInInit() {
        return cacheAgent.getAgentState() == CacheAgent.AgentState.UNINITIALIZED;
    }

    protected boolean isScorecard(Class entityClz) {
        return NamedInstance.class.isAssignableFrom(entityClz);
    }


    protected void printObject(String msg, Element element) {
        try {
            if (element instanceof Concept) {
                ExpandedName rootNm = ((Concept) element).getExpandedName();
                XiNode node = XiSupport.getXiFactory().createElement(rootNm);
                ((ConceptImpl) element).toXiNode(node, false);
                if (logger.isEnabledFor(Level.DEBUG)) {
                    logger.log(Level.DEBUG, msg + " = " + XiSerializer.serialize(node));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void onEntity(Object obj) {
        try {

            long start = System.currentTimeMillis();
            // Suresh TODO : increment metric Agent from InferenceAgent ++cacheAgent.numTransactionsSubscribed;
            Iterator ops = deserializeTxn((byte[]) obj);

            while (ops.hasNext()) {
                RtcTransaction.ReadFromCache entry = (RtcTransaction.ReadFromCache) ops.next();
                Class entityClz = getEntityClass(entry.getTypeId());
                long id = entry.id;
                switch (entry.type) {
                    case RtcTransaction.OP_NEW_CONCEPT:
                    case RtcTransaction.OP_MOD_CONCEPT:
                        if (cacheAgent.isRegisteredForChange(entry.getTypeId())) {
                            cacheAgent.getRuleSession().getTaskController().processTask(WorkerBasedController.DEFAULT_POOL_NAME,
                                    new ApplyElementChangesTask(cacheAgent.getRuleSession(), cacheAgent.getRegisteredRuleFunction(entry.typeId),
                                            entry, entityClz));
                        }
                        break;

                    case RtcTransaction.OP_DEL_CONCEPT:
                        if (cacheAgent.isRegisteredForDelete(entry.getTypeId())) {
                            cacheAgent.getRuleSession().getTaskController().processTask(WorkerBasedController.DEFAULT_POOL_NAME,
                                    new ApplyDeleteTask(cacheAgent.getRuleSession(), cacheAgent.getRegisteredRuleFunction(entry.typeId),
                                            entry, entityClz));
                        }
                        break;

                    case RtcTransaction.OP_NEW_EVENT:
                        if (cacheAgent.isRegisteredForChange(entry.getTypeId())) {
                           // logger.log(Level.ERROR, "Unexepected event OP_NEW_EVENT received for entry of type[%s] and id[%d]", entityClz.getName(), id);
                            //TODO - Need to implement this method
                            // System.err.println("Not Implemented Yet ..");
//                            cacheAgent.getRuleSession().getTaskController().processTask(WorkerBasedController.DEFAULT_POOL_NAME,
//                                    new CleanupEventHandleTask(cacheAgent.getRuleSession(), id));
                        }

                        break;

                    case RtcTransaction.OP_DEL_EVENT:
                        if (cacheAgent.isRegisteredForDelete(entry.getTypeId())) {
                            cacheAgent.getRuleSession().getTaskController().processTask(WorkerBasedController.DEFAULT_POOL_NAME,
                                    new ApplyDeleteTask(cacheAgent.getRuleSession(), cacheAgent.getRegisteredRuleFunction(entry.typeId),
                                            entry, entityClz));
                        }
                        break;

                    default:
                        throw new RuntimeException("RtcTransactionSubscriber_V2: UNKNOWN: Class=" + entityClz + ", id=" + entry.id);
                }
            }
            //Suresh TODO : Metric InferenceAgent cacheAgent.timeTransactionsSubscribed += (System.currentTimeMillis() - start);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected Iterator deserializeTxn(byte[] b) {
        ByteArrayInputStream bufStream = new ByteArrayInputStream(b);
        DataInput buf = new DataInputStream(bufStream);
        RtcTransaction txn = new RtcTransaction(null);
        return txn.readFromCacheOps(buf);
    }

    @Override
    public void entitiesAdded() {
    	topic = cacheAgent.topicsOfInterest; 
    }
    
    @Override
    public void entitiesChanged(Collection<Class<Entity>> changedClasses) {}

    class CleanupElementHandleTask implements Runnable {
        RuleSession session;
        long id;

        CleanupElementHandleTask(RuleSession session, long id) {
            this.session = session;
            this.id = id;
        }

        public void run() {
            try {
                ((RuleSessionImpl) session).cleanupElementHandle(id);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    class CleanupEventHandleTask implements Runnable {
        RuleSession session;
        long id;

        CleanupEventHandleTask(RuleSession session, long id) {
            this.session = session;
            this.id = id;
        }

        public void run() {
            try {
                ((RuleSessionImpl) session).cleanupEventHandle(id);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    class ApplyElementChangesTask implements Runnable {
        RuleSession session;
        RtcTransaction.ReadFromCache entry;
        RuleFunction rf;
        Class entityClz;

        ApplyElementChangesTask(RuleSession session, RuleFunction rf,
                                RtcTransaction.ReadFromCache entry,
                                Class entityClz) {
            this.session = session;
            this.entry = entry;
            this.entityClz = entityClz;
            this.rf = rf;
        }

        public void run() {
            try {
                ((RuleSessionImpl) session).applyElementChanges(rf, entry.getId(), entry.getExtId(), entry.getVersion(), entry.getTypeId(), entityClz, entry.getDirtyBits());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    class ApplyDeleteTask implements Runnable {
        RuleSession session;
        RtcTransaction.ReadFromCache entry;
        RuleFunction rf;
        Class entityClz;

        ApplyDeleteTask(RuleSession session, RuleFunction rf,
                        RtcTransaction.ReadFromCache entry,
                        Class entityClz) {
            this.session = session;
            this.entry = entry;
            this.entityClz = entityClz;
            this.rf = rf;
        }

        public void run() {
            try {
                ((RuleSessionImpl) session).applyDelete(rf, entry.getId(), entry.getExtId(), entry.getTypeId(), entityClz, entry.getVersion());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}
