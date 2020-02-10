package com.tibco.cep.thirdparty;

import java.text.MessageFormat;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class DelegatePlugin extends Plugin { 

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.cep.thirdparty";

	// The shared instance
	private static DelegatePlugin plugin;
	public static boolean DEBUG = false;

	private DelegateActivator fActivator;
	
	/**
	 * The constructor
	 */
	public DelegatePlugin() {
	}


	public static void activatePlugin() {
		// Initializing, so that the classpath is configured correctly for the Studio instance.
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		fActivator = new DelegateActivator(context);
		fActivator.startDelegate();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		fActivator.stopDelegate();
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static DelegatePlugin getDefault() {
		return plugin;
	}
	
	
	public Bundle getDelegateBundle() {
		return fActivator.getDelegateBundle();
	}
	
	/**
	 * @param className
	 * @param message
	 */
	public static void debug(String className, String message) {
		debug(MessageFormat.format("[{0}] {1}", className,message));
		
	}
	
	/**
	 * If the debug flag is set the specified message is printed to the console
	 * 
	 * @param message
	 */
	public static void debug(String message) {
		if(getDefault().isDebugging()) { // run eclipse with -debug option
			System.out.println(MessageFormat.format("[{0}] {1}",PLUGIN_ID,message));
		}
	}
	
	public static void log(String msg) {
		log(msg, null);
	}

	public static void log(String msg, Exception e) {
		getDefault().getLog().log(new Status(Status.INFO, PLUGIN_ID, Status.OK, msg, e));
	}
	
	/**
	 * Logs the specified status with this plug-in's log.
	 * 
	 * @param status status to log
	 */
	public static void log(IStatus status) {
		getDefault().getLog().log(status);
	}
	
	/**
	 * Logs the specified throwable with this plug-in's log.
	 * 
	 * @param t throwable to log
	 */
	public static void log(Throwable t) {
		log(newErrorStatus(MessageFormat.format("Error logged from {0} Plugin: ",PLUGIN_ID), t)); //$NON-NLS-1$
	}
	
	/**
	 * Logs an internal error with the specified message.
	 * 
	 * @param message the error message to log
	 */
	public static void logErrorMessage(String message) {
		// this message is intentionally not internationalized, as an exception may
		// be due to the resource bundle itself
		log(newErrorStatus(MessageFormat.format("Internal message logged from {0} Plugin: {1}",PLUGIN_ID,message), null)); //$NON-NLS-1$
	}
	
	/**
	 * Returns a new error status for this plug-in with the given message
	 * @param message the message to be included in the status
	 * @param exception the exception to be included in the status or <code>null</code> if none
	 * @return a new error status
	 */
	public static IStatus newErrorStatus(String message, Throwable exception) {
		return new Status(IStatus.ERROR, PLUGIN_ID, message, exception);
	}

}
