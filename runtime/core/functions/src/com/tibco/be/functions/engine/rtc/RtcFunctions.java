package com.tibco.be.functions.engine.rtc;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import java.util.Iterator;

import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.core.base.RtcOperationList;
import com.tibco.cep.kernel.core.rete.ReteWM;
import com.tibco.cep.kernel.core.rete.conflict.ConflictResolver;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.event.EventContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.service.cluster.agent.tasks.AgentActionManager;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.PreprocessContext;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.util.RuleTriggerManager;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "Engine.Rtc",
        synopsis = "Functions directly related to the RTC.")
public class RtcFunctions {

    @com.tibco.be.model.functions.BEFunction(
        name = "abortRTC",
        synopsis = "Cancels all rule actions scheduled to be executed in the current RTC.  Cancels all pending external changes in the current RTC: new/modified/deleted cache-based entities, events to send, event acknowledgments and scheduled events to schedule.  The rollback operation (if implemented) of the destination of the event (if any) that triggered the RTC will be invoked.",
        signature = "void abortRTC()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Cancels all rule actions scheduled to be executed in the current RTC.  Cancels all pending external changes in the current RTC: new/modified/deleted cache-based entities, events to send, event acknowledgments and scheduled events to schedule.  The rollback operation (if implemented) of the destination of the event (if any) that triggered the RTC will be invoked.",
        cautions = "This function only affects the present state of the RTC.  Any subsequent actions taken after the call, such as sending events or modifying concepts will proceed as normal.",
        fndomain = {ACTION},
        example = ""
    )
    public static void abortRTC() {
        RuleSessionImpl session = (RuleSessionImpl) RuleSessionManager.getCurrentRuleSession();
        if (session == null) return;

        session.getRuleServiceProvider().getLogger(RtcFunctions.class).log(Level.INFO, "Aborting RTC...");
        try {
        	Object trigger = RuleTriggerManager.getRuleTriggerFromProcessingContext();
        	final PreprocessContext ppContext = PreprocessContext.getContext();
            if (null != ppContext) {
                ppContext.setTrigger(null);
            } else {
	            ReteWM wm = (ReteWM) session.getWorkingMemory();
	            wm.getOpList().clear();
	            // Resets the agenda
	            wm.cleanResolver();
	            clearRtcBits(wm);
	            if (AgentActionManager.hasActionManager(session)) {
	            	AgentActionManager.clearActions(session);
	            }
            }
            
            if (trigger instanceof SimpleEvent) {
                final EventContext ctx = ((SimpleEvent)trigger).getContext();
                if (null != ctx && !((SimpleEvent)trigger).isAcknowledged()) {
                    ctx.rollBack();
	            }
            }
        }
        catch (Throwable t) {
            session.getRuleServiceProvider().getLogger(RtcFunctions.class).log(
                    Level.ERROR, t, "Exception while Aborting RTC");
        }
    }
    
    /**
     * Only concepts are cleared - since they are mutable
     * Events - Always non mutable, and their life span is governed by their TTL.
     * Events can also be CLUSTERED!!!
     * However they fall in two categories - One they are asserted and consumed in the same RTC or different - depends
     * on the rule.
     * @param wm ReteWM
     */
    private static void clearRtcBits(ReteWM wm) {
        RtcOperationList rtcList =  wm.getRtcOpList();
        Iterator itr = rtcList.nonClearingIterator();
        while (itr.hasNext()) {
            BaseHandle handle = (BaseHandle) itr.next();
            Object obj = handle.getObject();
            if (obj != null) {
            	if (obj instanceof Concept) {
	                ConceptImpl c = (ConceptImpl) obj;
	                c.setVersion(0); //forcefully make it a new concept - this way even modified concept is treated as new, and will be discarded of the changes
            	}
                wm.cleanupHandle(handle); //clean up the handle from Rete too.
                handle.rtcClearAll();
                handle.okMarkDirty();
            }
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "printAgenda",
        synopsis = "Returns the current agenda of the current RTC.",
        signature = "String printAgenda()",
        params = {
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "A textual description of the current agenda of the current RTC."),
        version = "3.0.2",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns the current agenda of the current RTC.",
        cautions = "Note that the agenda may change after each rule is executed as the working memory changes.",
        fndomain = {ACTION},
        example = ""
    )
    public static String printAgenda() {
        RuleSessionImpl session = (RuleSessionImpl) RuleSessionManager.getCurrentRuleSession();
        ReteWM wm = (ReteWM) session.getWorkingMemory();
        ConflictResolver resolver = wm.getResolver();
        StringBuffer buf = new StringBuffer();
        resolver.printAgenda(buf);
        return buf.toString();
    }
}
