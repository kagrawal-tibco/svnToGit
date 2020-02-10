package com.tibco.cep.studio.application;

import java.text.MessageFormat;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 */
public class StudioApplicationPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.cep.studio.application";

	// The shared instance
	private static StudioApplicationPlugin plugin;
	
	public static boolean DEBUG = false;
	
	/**
	 * The constructor
	 */
	public StudioApplicationPlugin() {
		//this.logger = this.getLogger();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
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
	public static StudioApplicationPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
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
		getDefault().getLog().log(
				new Status(Status.INFO, PLUGIN_ID, Status.OK, msg, e));
	}

	/**
	 * Logs the specified status with this plug-in's log.
	 * 
	 * @param status
	 *            status to log
	 */
	public static void log(IStatus status) {
		getDefault().getLog().log(status);
	}

	/**
	 * Logs the specified throwable with this plug-in's log.
	 * 
	 * @param t
	 *            throwable to log
	 */
	public static void log(Throwable t) {
		log(newErrorStatus("Error logged from Studio Application Plugin: ", t)); //$NON-NLS-1$
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
				"Internal message logged from Studio Application Plugin: " + message, null)); //$NON-NLS-1$
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
