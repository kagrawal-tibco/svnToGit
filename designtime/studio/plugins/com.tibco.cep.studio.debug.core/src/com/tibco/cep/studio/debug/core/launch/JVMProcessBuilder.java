package com.tibco.cep.studio.debug.core.launch;

import java.util.Properties;


/*
@author ssailapp
@date Jul 21, 2009
*/

public interface JVMProcessBuilder {
	
	public static final String BRK = System.getProperty("line.separator", "\n");
//	public static final String MINUS_X_DEBUGOPTS = "-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=localhost:";
	public static final String MINUS_X_DEBUGOPTS = "-agentlib:jdwp=transport=dt_socket,suspend=y,address=localhost:";
	public static final String JAVA_EXTENDED_PROPERTIES = "java.extended.properties";
	

	ProcessBuilder getProcessBuilder();

//    ProcessOutputCollector getCollector();
    
    void init() throws Exception;

//    void start();

//    void stop();

//    void waitForAttach() throws InterruptedException;

    String getHostName();

    String getPort();

	public Properties getRuntimeTRA();

	public String[] getCommand();

	public String getCommandLineString();
}
