/**
 * 
 */
package com.tibco.cep.studio.tester.core.provider;

/**
 * @author aathalye
 *
 */
public class TesterEngineManager {
	
	private static final ThreadLocal<String> currentRunningLaunchConfigs = new ThreadLocal<String>();
	
	public static String getCurrentLaunchConfig() {
		return currentRunningLaunchConfigs.get();
	}
	
	public static void setCurrentLaunchConfig(String launchConfigName) {
		currentRunningLaunchConfigs.set(launchConfigName);
	}
}
