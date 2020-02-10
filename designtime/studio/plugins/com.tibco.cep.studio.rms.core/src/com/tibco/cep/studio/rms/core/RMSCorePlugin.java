package com.tibco.cep.studio.rms.core;

import java.text.MessageFormat;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

import com.tibco.cep.util.PlatformUtil;

public class RMSCorePlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.cep.studio.rms.core";

	public static boolean DEBUG = false;

	// The shared instance
	private static RMSCorePlugin plugin;

	/**
	 * The constructor
	 */
	public RMSCorePlugin() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);

		
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static RMSCorePlugin getDefault() {
		return plugin;
	}

	
	

	/**
	 * @param className
	 * @param message
	 */
	public static void debug(String className, String message, Object... args) {
		String debugMessage = MessageFormat.format(message, args);
		debug(MessageFormat.format("[{0}] {1}", className, debugMessage));

	}

	/**
	 * If the debug flag is set the specified message is printed to the console
	 * 
	 * @param message
	 */
	public static void debug(String message) {
		if (PlatformUtil.INSTANCE.isStudioPlatform()) {
			if (getDefault().isDebugging()) {
				System.out.println(MessageFormat.format("[{0}] {1}", PLUGIN_ID,
						message));
			}
		}
	}

	public static void log(String msg, Object... arguments) {
		String infoMessage = MessageFormat.format(msg, arguments);
		getDefault().getLog().log(
				new Status(Status.INFO, PLUGIN_ID, infoMessage));
	}

	/**
	 * Logs the specified status with this plug-in's log.
	 * 
	 * @param status
	 *            status to log
	 */
	public static void log(IStatus status) {
		if (PlatformUtil.INSTANCE.isStudioPlatform()) {
			getDefault().getLog().log(status);
		}
	}

	/**
	 * Logs the specified throwable with this plug-in's log.
	 * 
	 * @param t
	 *            throwable to log
	 */
	public static void log(Throwable t) {
		log(newErrorStatus(MessageFormat.format(
				"Error logged from {0} Plugin: ", PLUGIN_ID), t)); //$NON-NLS-1$
	}

	/**
	 * Logs the specified throwable with this plug-in's log.
	 * 
	 * @param t
	 *            throwable to log
	 */
	public static void log(String message, Throwable t) {
		log(newErrorStatus(MessageFormat.format(message, PLUGIN_ID), t)); //$NON-NLS-1$
	}

	/**
	 * Logs an internal error with the specified message.
	 * 
	 * @param message
	 *            the error message to log
	 */
	public static void logErrorMessage(String message) {
		// this message is intentionally not internationalized, as an exception
		// may
		// be due to the resource bundle itself
		log(newErrorStatus(
				MessageFormat
						.format(
								"Internal message logged from {0} Plugin: {1}", PLUGIN_ID, message), null)); //$NON-NLS-1$
	}

	/**
	 * Returns a new error status for this plug-in with the given message
	 * 
	 * @param message
	 *            the message to be included in the status
	 * @param exception
	 *            the exception to be included in the status or
	 *            <code>null</code> if none
	 * @return a new error status
	 */
	public static IStatus newErrorStatus(String message, Throwable exception) {
		return new Status(IStatus.ERROR, PLUGIN_ID, message, exception);
	}

}
