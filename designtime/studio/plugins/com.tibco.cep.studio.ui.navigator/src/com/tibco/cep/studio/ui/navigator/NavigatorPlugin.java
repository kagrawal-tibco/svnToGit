package com.tibco.cep.studio.ui.navigator;

import java.text.MessageFormat;

import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;



/**
 * The activator class controls the plug-in life cycle
 */
public class NavigatorPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.cep.studio.ui.navigator";

	// The shared instance
	private static NavigatorPlugin plugin;
	
	public static boolean DEBUG = false;
	
	/**
	 * The constructor
	 */
	public NavigatorPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		// add Resource change Listener for Ontology Functions
		
		//ResourcesPlugin.getWorkspace().addResourceChangeListener(ModelFunctionsFactory.getINSTANCE());
		
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
	public static NavigatorPlugin getDefault() {
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
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	public Image getImage(String imageName) {
		Image image = getImageRegistry().get(imageName);
		if (image == null) {
			image = getImageDescriptor(imageName).createImage();
			getImageRegistry().put(imageName, image);
		}
		return image;
	}
}
