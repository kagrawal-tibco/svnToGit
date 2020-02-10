package com.tibco.cep.runtime.util.scheduler;

/**
 *
 * Author: Karthikeyan Subramanian / Date: Jan 27, 2010 / Time: 12:20:25 PM
 */
public interface Job {    
    
    void setId(Id id);

    Id getId();

    void execute(JobContext context);

}
