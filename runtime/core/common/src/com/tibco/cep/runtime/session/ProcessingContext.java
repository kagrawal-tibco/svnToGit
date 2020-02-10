package com.tibco.cep.runtime.session;

import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.runtime.channel.Channel;


/**
 * User: nprade
 * Date: 11/1/12
 * Time: 5:42 PM
 */
public interface ProcessingContext {


    RuleSession getRuleSession();


    Event getTriggerEvent();


    Channel.Destination getSourceDestination();


    long getStartTime();


}
