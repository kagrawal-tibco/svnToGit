package com.tibco.cep.studio.tester.ui;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.tibco.cep.studio.tester.ui.utils.TesterImages;

/**
 * The activator class controls the plug-in life cycle
 */
public class StudioTesterUIPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.cep.studio.tester.ui";

	// The shared instance
	private static StudioTesterUIPlugin plugin;
	
	private static final String ImagePath = "/icons/";
	
	private DelegateActivator delegateActivator;
	
	public static boolean DEBUG = false;
	
	/**
	 * The constructor
	 */
	public StudioTesterUIPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		delegateActivator = new DelegateActivator(context);
		//delegateActivator.startDelegate();
		getDefault().setDebugging(true);
		TesterImages.initialize();
		
		DebugPlugin.getDefault().addDebugEventListener(new TesterDebugEventsSetListener());
		DebugPlugin.getDefault().addDebugEventListener(new WMContentsDebugEventSetListener());
		DebugPlugin.getDefault().addDebugEventListener(new WorkingMemoryManipulatorDebugEventSetListener());
		DebugPlugin.getDefault().addDebugEventListener(new ClearWorkingMemoryDebugEventSetListener());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		delegateActivator.stopDelegate();
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static StudioTesterUIPlugin getDefault() {
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
			image = getImageDescriptor(imageName).createImage();
			getImageRegistry().put(imageName, image);
		}
		return image;
	}
	
    /**
     * 
     * @param file
     * @return
     */
    public ImageIcon  getImageIcon(String file)  {
		 ImageIcon imageIcon = null;
		 	try{
			 	InputStream resource = StudioTesterUIPlugin.class.getClassLoader().getResourceAsStream(ImagePath + file);
			 	if (resource == null) {
			 		System.err.println("Image file " + file + " is missing");
			 	}
			 	return new ImageIcon(ImageIO.read(resource));
		 		} catch (IOException e) {
					e.printStackTrace();
				}
		  return imageIcon;
	 }
    
    /**
     * 
     * @param icon
     * @return
     */
    public ImageIcon getIcon(String icon)  {
		 ImageIcon imageIcon = null;
		 	try{
			 	InputStream resource = StudioTesterUIPlugin.class.getClassLoader().getResourceAsStream(icon);
			 	if (resource == null) {
			 		System.err.println("Image file " + icon + " is missing");
			 	}
			 	return new ImageIcon(ImageIO.read(resource));
		 		} catch (IOException e) {
					e.printStackTrace();
				}
		  return imageIcon;
	 }
    
    public static IWorkbenchPage getActivePage() {
        IWorkbenchWindow window = getActiveWorkbenchWindow();
        if (window == null)
            return null;
        return getActiveWorkbenchWindow().getActivePage();
	}
	
	/**
	 * Returns the currently active workbench window or <code>null</code>
	 * if none.
	 * 
	 * @return the currently active workbench window or <code>null</code>
	 */
	public static IWorkbenchWindow getActiveWorkbenchWindow() {
		return getDefault().getWorkbench().getActiveWorkbenchWindow();
	}
	
	
	/**
	 * Returns the standard display to be used. The method first checks, if
	 * the thread calling this method has an associated display. If so, this
	 * display is returned. Otherwise the method returns the default display.
	 */
	public static Display getStandardDisplay() {
		Display display;
		display= Display.getCurrent();
		if (display == null)
			display= Display.getDefault();
		return display;		
	}
	
	/**
	 * Returns the currently active workbench window shell or <code>null</code>
	 * if none.
	 * 
	 * @return the currently active workbench window shell or <code>null</code>
	 */
	public static Shell getActiveWorkbenchShell() {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window == null) {
			IWorkbenchWindow[] windows = PlatformUI.getWorkbench().getWorkbenchWindows();
			if (windows.length > 0) {
				return windows[0].getShell();
			}
		}
		else {
			return window.getShell();
		}
		return null;
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
		if (getDefault().isDebugging()) { 
			System.out.println(MessageFormat.format("[{0}] {1}", PLUGIN_ID, message));
		}
	}
	
		
	public static void debug(String msg, Object... arguments) {
		String infoMessage = MessageFormat.format(msg, arguments);
		debug(MessageFormat.format("{1}", infoMessage));
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
		getDefault().getLog().log(status);
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
	 * Utility method with conventions
	 */
	public static void errorDialog(Shell shell, String title, String message, IStatus s) {
		// if the 'message' resource string and the IStatus' message are the same,
		// don't show both in the dialog
		if (s != null && message.equals(s.getMessage())) {
			message= null;
		}
		ErrorDialog.openError(shell, title, message, s);
	}
	
	/**
	 * Utility method with conventions
	 */
	public static void errorDialog(Shell shell, String title, String message, Throwable t) {
		IStatus status;
		if (t instanceof CoreException) {
			status= ((CoreException)t).getStatus();
			// if the 'message' resource string and the IStatus' message are the same,
			// don't show both in the dialog
			if (status != null && message.equals(status.getMessage())) {
				message= null;
			}
		} else {
			status= new Status(IStatus.ERROR, PLUGIN_ID,  "Error within Studio Debug UI Plugin: ", t);
			log(status);
		}
		ErrorDialog.openError(shell, title, message, status);
	}
	
	public static void statusDialog(IStatus status) {
		switch (status.getSeverity()) {
		case IStatus.ERROR:
			statusDialog("Error", status);
			break;
		case IStatus.WARNING:
			statusDialog("Warning", status);
			break;
		case IStatus.INFO:
			statusDialog("Info", status);
			break;
		}		
	}
	
	public static void statusDialog(String title, IStatus status) {
		Shell shell = getActiveWorkbenchShell();
		if (shell != null) {
			switch (status.getSeverity()) {
			case IStatus.ERROR:
				ErrorDialog.openError(shell, title, null, status);
				break;
			case IStatus.WARNING:
				MessageDialog.openWarning(shell, title, status.getMessage());
				break;
			case IStatus.INFO:
				MessageDialog.openInformation(shell, title, status.getMessage());
				break;
			}
		}		
	}


	public static String getUniqueIdentifier() {
		return PLUGIN_ID;
	}
	
	public DelegateActivator getDelegateActivator() {
		return delegateActivator;
	}
}