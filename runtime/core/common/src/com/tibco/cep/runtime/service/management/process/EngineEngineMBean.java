package com.tibco.cep.runtime.service.management.process;

import javax.management.openmbean.TabularDataSupport;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Mar 8, 2010
 * Time: 12:34:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EngineEngineMBean {

    public void StopEngine();
    public TabularDataSupport GetHostInformation(String name) throws Exception;
    public TabularDataSupport GetNumberOfEvents(String sessionName) throws Exception;
    public TabularDataSupport GetNumberOfInstances(String sessionName) throws Exception;
    public TabularDataSupport GetMemoryUsage();
    public TabularDataSupport GetVersionInfo();
//    public boolean executeCommand(String command, String arguments);

    public TabularDataSupport GetLoggerNamesWithLevels();

    public void SetLogLevel(String loggerNameOrPattern, String level) throws Exception;
}
