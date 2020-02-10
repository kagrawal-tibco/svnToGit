package com.tibco.cep.runtime.scheduler;

import com.tibco.cep.runtime.session.RuleServiceProvider;


/**
 * This is a Marker interface as of V2.0
 * The interface will be described at future, but is provisioned here
 * The Destination uses the JobScheduler during the bind contract.
 *
 * The initial drawing board discussion
 * a.> A scheduler might be present per Channel
 * b.> A Scheduler at a RuleSession level
 * c.> A Scheduler at a global level
 *
 * The Scheduler's job is to schedule job task depending on varying load factors
 * The Job Task themselves could be
 * a.> Deserializing the incoming message into a SimpleEvent
 * b.> Filtering, and other policies
 * c.> Put them on various Queues and control the flow control
 *
 * As of now, this is a markup interface, with experimental implementations
 */

public interface JobScheduler {
    void init(RuleServiceProvider provider) throws Exception;
    void start() throws Exception;
}
