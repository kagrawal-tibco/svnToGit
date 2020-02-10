/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster;

import com.tibco.cep.kernel.core.rete.BeTransaction;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.PayloadValidationHelper;
import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.model.event.EventContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.SimpleEventImpl;
import com.tibco.cep.runtime.scheduler.impl.WorkerBasedControllerV2;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionMetrics;
import com.tibco.cep.runtime.session.impl.PreprocessContext;
import com.tibco.cep.runtime.session.impl.ProcessingContextImpl;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: May 18, 2008
 * Time: 12:45:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClusterTaskController extends WorkerBasedControllerV2 {

    /**
     * @param session
     */
    public ClusterTaskController(RuleSession session) {
        super(session);
    }

    protected void executeTask(SimpleEvent event, EventContext context, final Channel.Destination dest, final SerializationContext sci) {
        try {
            final long start = System.currentTimeMillis();
            if (event == null) { // try deserializing the event
                if (dest.getEventSerializer() != null) {
                    event = dest.getEventSerializer().deserialize(context.getMessage(), sci);
                    if (event != null) {
                        event.setContext(context);
                        ((SimpleEventImpl) event).setDestinationURI(dest.getURI());
                        if (PayloadValidationHelper.ENABLED) {
                            PayloadValidationHelper.validate(event, sci);
                        }
                    }
                }
            }

            if (event != null) {
                final SimpleEvent eventFinal = event;
                new BeTransaction("RTC-" + generateTxnName(dest, eventFinal)) {
                    @Override
                    protected void doTxnWork() {
                        try {
                            long t1 = System.currentTimeMillis();
                            long t2 = 0L;
                            long preRTCTime = 0L;
                            
                            ruleSession.getProcessingContextProvider()
                                    .setProcessingContext(new ProcessingContextImpl(ruleSession, dest,
                                                                                    start, eventFinal));

                            cleanup(ruleSession);

                            RuleFunction preprocessor = sci.getDeployedDestinationConfig().getPreprocessor();
                            if (preprocessor != null) {
                                PreprocessContext pc = ((RuleSessionImpl) ruleSession).preprocessPassthru(preprocessor, eventFinal); // ELFIZ
                                if (pc != null) {
                                    preRTCTime = pc.getPreRTCTime();
                                }
                            } else {
                                ruleSession.assertObject(eventFinal, true);
                            }
                            
                            t2 = System.currentTimeMillis();
                            long inRTCTime = ((t2 - t1) - preRTCTime);

                            Cluster cluster = ruleSession.getRuleServiceProvider().getCluster();
                            int typeId = cluster.getMetadataCache().getTypeId(eventFinal.getClass());
                            RuleSessionMetrics metrics = ruleSession.getRuleSessionMetrics();
                            if (metrics != null) {
                                metrics.setMetric(RuleSessionMetrics.MetricType.PRERTC, typeId, preRTCTime);
                                metrics.setMetric(RuleSessionMetrics.MetricType.INRTC, typeId, inRTCTime);
                            }
                        }
                        catch (DuplicateExtIdException e) {
                            throw new RuntimeException(e);
                        } finally {
                            ruleSession.getProcessingContextProvider().setProcessingContext(null);
                        }
                    }
                }.execute();
            }
        }
        catch (Exception ex) {
            this.logger.log(Level.ERROR, ex, ex.getMessage());
        }
        finally {
            // decrActiveWorkerTask();
        }
    }

    /**
     * @param dest
     * @param event
     * @param ctx
     * @throws Exception
     */
    public void processEvent(Channel.Destination dest, SimpleEvent event, EventContext ctx) throws Exception {
        super.processEvent(dest, event, ctx);
    }
}
