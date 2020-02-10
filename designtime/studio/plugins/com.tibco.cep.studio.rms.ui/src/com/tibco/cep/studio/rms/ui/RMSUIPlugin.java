package com.tibco.cep.studio.rms.ui;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.cep.util.PlatformUtil;

/**
 * The activator class controls the plug-in life cycle
 */
public class RMSUIPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.cep.studio.rms.ui";

	// The shared instance
	private static RMSUIPlugin plugin;
	
	/**
	 * The constructor
	 */
	public RMSUIPlugin() {
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
		
		if (fgDisposeOnShutdownImages != null) {
			Iterator<Image> i = fgDisposeOnShutdownImages.iterator();
			while (i.hasNext()) {
				Image img = (Image) i.next();
				if (!img.isDisposed())
					img.dispose();
			}
		}
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static RMSUIPlugin getDefault() {
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
				System.out.println(MessageFormat.format("[{0}] {1}", PLUGIN_ID, message));
			}
		}
	}
	
		
	public static void log(String msg, Object... arguments) {
		String infoMessage = MessageFormat.format(msg, arguments);
		getDefault().getLog().log(new Status(Status.INFO, PLUGIN_ID, infoMessage));
	}
	
	/**
	 * Logs the specified status with this plug-in's log.
	 * 
	 * @param status status to log
	 */
	public static void log(IStatus status) {
		if (PlatformUtil.INSTANCE.isStudioPlatform()) {
			getDefault().getLog().log(status);
		}
	}
	
	/**
	 * Logs the specified throwable with this plug-in's log.
	 * 
	 * @param t throwable to log
	 */
	public static void log(Throwable t) {
		log(newErrorStatus(MessageFormat.format("Error logged from {0} Plugin: ", PLUGIN_ID), t)); //$NON-NLS-1$
	}
	
	/**
	 * Logs the specified throwable with this plug-in's log.
	 * 
	 * @param t throwable to log
	 */
	public static void log(String message, Throwable t) {
		log(newErrorStatus(MessageFormat.format(message, PLUGIN_ID), t)); //$NON-NLS-1$
	}
	
	/**
	 * Logs an internal error with the specified message.
	 * 
	 * @param message the error message to log
	 */
	public static void logErrorMessage(String message) {
		// this message is intentionally not internationalized, as an exception may
		// be due to the resource bundle itself
		log(newErrorStatus(MessageFormat.format("Internal message logged from {0} Plugin: {1}", PLUGIN_ID, message), null)); //$NON-NLS-1$
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
	
	/**
	 * @param imageName
	 * @return
	 */
	public Image getImage(String imageName) {
		Image image = getImageRegistry().get(imageName);
		if (image == null) {
			ImageDescriptor desc = getImageDescriptor(imageName);
			if (desc == null) {
				// System.out.println("Could not find image "+imageName);
				return null;
			}
			image = desc.createImage();
			getImageRegistry().put(imageName, image);
		}
		return image;
	}
	
	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}


	private static Map<ImageDescriptor, Image> fgImages2 = new Hashtable<ImageDescriptor, Image>(10);
	private static List<Image> fgDisposeOnShutdownImages= new ArrayList<Image>();
	
	/**
	 * Returns a shared image for the given adaptable.
	 * This convenience method queries the given adaptable
	 * for its <code>IWorkbenchAdapter.getImageDescriptor</code>, which it
	 * uses to create an image if it does not already have one.
	 * <p>
	 * Note: Images returned from this method will be automatically disposed
	 * of when this plug-in shuts down. Callers must not dispose of these
	 * images themselves.
	 * </p>
	 *
	 * @param adaptable the adaptable for which to find an image
	 * @return an image
	 */
	public static Image getImage(IAdaptable adaptable) {
		if (adaptable != null) {
			Object o= adaptable.getAdapter(IWorkbenchAdapter.class);
			if (o instanceof IWorkbenchAdapter) {
				ImageDescriptor id = ((IWorkbenchAdapter) o).getImageDescriptor(adaptable);
				if (id != null) {
					Image image= (Image)fgImages2.get(id);
					if (image == null) {
						image= id.createImage();
						try {
							fgImages2.put(id, image);
						} catch (NullPointerException ex) {
							// NeedWork
						}
						fgDisposeOnShutdownImages.add(image);

					}
					return image;
				}
			}
		}
		return null;
	}
}
