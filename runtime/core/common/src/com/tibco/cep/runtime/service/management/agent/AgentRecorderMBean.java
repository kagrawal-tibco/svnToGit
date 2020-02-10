package com.tibco.cep.runtime.service.management.agent;

import javax.management.openmbean.TabularDataSupport;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Jan 25, 2010
 * Time: 4:50:05 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AgentRecorderMBean {
    public TabularDataSupport StartFileBasedRecorder(String directory, String mode) throws Exception;
    public TabularDataSupport StopFileBasedRecorder() throws Exception;
}
