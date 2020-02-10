package com.tibco.cep.runtime.service.management.agent;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Sep 22, 2009
 * Time: 12:30:31 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AgentProfilerMBean {
    //Profiler
    public void StartFileBasedProfiler(String fileName, int level, long duration) throws Exception;
    public void StopFileBasedProfiler() throws Exception;
}
