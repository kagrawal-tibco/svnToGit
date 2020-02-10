package com.tibco.cep.studio.ui.editors;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.update.IStudioModelChangedListener;
import com.tibco.cep.studio.ui.editors.rules.text.IRulesWorkingCopyManager;
import com.tibco.cep.studio.ui.editors.rules.text.RulesDocumentProvider;
import com.tibco.cep.studio.ui.update.StudioResourceChangeListener;
import com.tibco.cep.studio.ui.update.UIUpdateManger;

/**
 * The activator class controls the plug-in life cycle
 */
public class EditorsUIPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.cep.studio.ui.editors";
	
	// The shared instance
	private static EditorsUIPlugin plugin;
	
	private IResourceChangeListener studioResourceListener = new StudioResourceChangeListener(getActivePage());
	private IStudioModelChangedListener uiUpdateManager = new UIUpdateManger();
	public static boolean DEBUG = false;
	
	private RulesDocumentProvider fWorkingCopyManager;

	/**
	 * The constructor
	 */
	public EditorsUIPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		ResourcesPlugin.getWorkspace().addResourceChangeListener(studioResourceListener, IResourceChangeEvent.POST_CHANGE |IResourceChangeEvent.PRE_DELETE);
		StudioCorePlugin.getDefault().addDesignerModelChangedListener(uiUpdateManager);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(studioResourceListener);
		if (StudioCorePlugin.getDefault() != null) {
			StudioCorePlugin.getDefault().removeDesignerModelChangedListener(uiUpdateManager);
		}
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static EditorsUIPlugin getDefault() {
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
	
	public Image getImage(String imageName) {
		Image image = getImageRegistry().get(imageName);
		if (image == null) {
			ImageDescriptor desc = getImageDescriptor(imageName);
			if (desc == null) {
				System.out.println("Could not find image "+imageName);
				return null;
			}
			image = desc.createImage();
			getImageRegistry().put(imageName, image);
		}
		return image;
	}
	
	public ResourceBundle getResourceBundle() {
		return ResourceBundle.getBundle(PLUGIN_ID);
	}
	
	public IWorkbenchPage getActivePage() {
        IWorkbenchWindow window = getWorkbench().getActiveWorkbenchWindow();
        if (window == null)
            return null;
        return window.getActivePage();
	}

	public synchronized RulesDocumentProvider getDocumentProvider() {
		if (fWorkingCopyManager == null) {
			fWorkingCopyManager = new RulesDocumentProvider();
		}
		return fWorkingCopyManager;
	}
	
	public IRulesWorkingCopyManager getWorkingCopyManager() {
		return getDocumentProvider();
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