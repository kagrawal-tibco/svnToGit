package com.tibco.cep.studio.debug.core.launch;

import java.util.Properties;

public interface RuntimeEnvironment {
	
	public static String JAVA_EXTENDED_PROPERTIES = "java.extended.properties";
	
    String getJavaExecutablePath();

    Properties getEnvironment();

    ProcessBuilder newProcessBuilder();
}