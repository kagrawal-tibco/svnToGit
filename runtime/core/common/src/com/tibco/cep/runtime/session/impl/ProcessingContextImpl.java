package com.tibco.cep.runtime.session.impl;

import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.session.ProcessingContext;
import com.tibco.cep.runtime.session.RuleSession;


/**
 * User: nprade
 * Date: 11/1/12
 * Time: 5:45 PM
 */
public class ProcessingContextImpl
        implements ProcessingContext {


    private final RuleSession ruleSession;
    private final long startTime;
    private final Event triggerEvent;
    private Channel.Destination sourceDestination;


    public ProcessingContextImpl(
            RuleSession ruleSession,
            Channel.Destination sourceDestination,
            long startTime,
            Event triggerEvent) {

        this.ruleSession = ruleSession;
        this.sourceDestination = sourceDestination;
        this.startTime = startTime;
        this.triggerEvent = triggerEvent;
    }


    @Override
    public RuleSession getRuleSession() {
        return this.ruleSession;
    }


    @Override
    public long getStartTime() {
        return this.startTime;
    }


    @Override
    public Channel.Destination getSourceDestination() {
        return this.sourceDestination;

    }


    @Override
    public Event getTriggerEvent() {
        return this.triggerEvent;
    }


}
