package com.tibco.cep.runtime.service.management.process;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Mar 8, 2010
 * Time: 6:32:42 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EngineProfilerMBean {
    public void StartFileBasedProfiler(String sessionName, String fileName, int level, long duration)  throws Exception;
    public void StopFileBasedProfiler(String session) throws Exception;
}
