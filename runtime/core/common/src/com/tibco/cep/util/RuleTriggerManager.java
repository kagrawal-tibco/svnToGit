package com.tibco.cep.util;

import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.runtime.session.ProcessingContext;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.PreprocessContext;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: May 18, 2008
 * Time: 9:51:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuleTriggerManager {
    public final static boolean retryOnException() {
    	Object trigger = getRuleTrigger();
        if(trigger instanceof Event) return ((Event)trigger).getRetryOnException();
        return true;
    }
    
    public static final Object getRuleTrigger() {
    	Object trigger = null;
    	PreprocessContext pc = PreprocessContext.getContext();
        if(pc != null) trigger = pc.getRtcTrigger();
        if(trigger == null) {
        	RuleSession sess = RuleSessionManager.getCurrentRuleSession();
        	if(sess instanceof RuleSessionImpl) trigger = ((RuleSessionImpl)sess).getRtcOperationList().getTrigger();        	
        	if(trigger == null) {
                if (null != sess) {
                    ProcessingContext procCtx = sess.getProcessingContextProvider().getProcessingContext();
                    if (null != procCtx) {
                        trigger = procCtx.getTriggerEvent();
                    }
                }
        	}
        }
        return trigger;
    }

    /**
     * For use by AbortRTC: AbortRTC should read from processingContext, since that is more likely to be an incoming event
     *                      instead of an event that has expired its ttl or something else that shouldn't be redelivered.
     * note: Without concurrentWM the rtcOpList isn't a thread local and so setting the trigger in it before taking the 
     *       rete lock causes wrong tuple to be returned.
     */
    public static final Object getRuleTriggerFromProcessingContext() {
        Object trigger = null;
        PreprocessContext pc = PreprocessContext.getContext();
        if(pc != null) trigger = pc.getRtcTrigger();
        if(trigger == null) {
            RuleSession sess = RuleSessionManager.getCurrentRuleSession();
            if (null != sess) {
                ProcessingContext procCtx = sess.getProcessingContextProvider().getProcessingContext();
                if (null != procCtx) {
                    trigger = procCtx.getTriggerEvent();
                }
            	if (trigger == null) {
	            	if(sess instanceof RuleSessionImpl) trigger = ((RuleSessionImpl)sess).getRtcOperationList().getTrigger();        	
                }
            }
        }
        return trigger;
    }
}
