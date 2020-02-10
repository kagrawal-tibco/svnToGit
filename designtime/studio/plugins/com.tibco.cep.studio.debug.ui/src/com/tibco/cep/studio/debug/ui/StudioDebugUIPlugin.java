package com.tibco.cep.studio.debug.ui;

import java.text.MessageFormat;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.texteditor.IEditorStatusLine;
import org.osgi.framework.BundleContext;

import com.tibco.cep.studio.debug.core.model.IRuleDebugThread;
import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.debug.core.model.RuleDebugModel;

/**
 * The activator class controls the plug-in life cycle
 */
public class StudioDebugUIPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.cep.studio.debug.ui";
	

	// The shared instance
	private static StudioDebugUIPlugin plugin;
	
	public static boolean DEBUG = false;
	
	/**
	 * The constructor
	 */
	public StudioDebugUIPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		init();
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
	public static StudioDebugUIPlugin getDefault() {
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
	
	public static Image getImage(String path) {
		return (getImageDescriptor(path)).createImage();
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
			String throwableMessage = "Error within Studio Debug UI Plugin: ";
			if(t != null) {
				throwableMessage = t.getMessage();
			}
			status= new Status(IStatus.ERROR, PLUGIN_ID,throwableMessage, t);
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
	
	public void init() {
		DebugPlugin.getDefault().addDebugEventListener(new IDebugEventSetListener() {
		
			@Override
			public void handleDebugEvents(DebugEvent[] events) {
				for (int i = 0; i < events.length; i++) {
					DebugEvent event = events[i];
					if (event.getSource() instanceof IRuleRunTarget) {

						final IRuleRunTarget target = (IRuleRunTarget) event.getSource();
						if (target.getModelIdentifier().equals(
								RuleDebugModel.getModelIdentifier())) {
							if (event.getKind() == DebugEvent.MODEL_SPECIFIC) {
								if(event.getDetail() == IRuleRunTarget.END_OF_RTC){
									ShowStatusMessage("End of RTC...", false,true);
								}
							} else if(event.getKind() == DebugEvent.RESUME) {
								//ShowStatusMessage("", false,false);
							}
							
						}

					} else if(event.getSource() instanceof IRuleDebugThread) {
						final IRuleDebugThread target = (IRuleDebugThread) event.getSource();
						if (target.getModelIdentifier().equals(
								RuleDebugModel.getModelIdentifier())) {
							if (event.getKind() == DebugEvent.RESUME) {
								//ShowStatusMessage("", false,false);
							} 
							
						}
					}
				}
		
			}
		} );
	}
	
	public void ShowStatusMessage(final String message,final boolean beep,final boolean clear) {
		 Display d = getStandardDisplay();
		 d.asyncExec(new Runnable() {
		
			@Override
			public void run() {
				final IWorkbench wb = PlatformUI.getWorkbench();
		   	     final IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		   	     if(win != null) {
		   	    	 final IWorkbenchPage page = win.getActivePage();
		   	    	 if(page != null) {
		   	    		 IEditorPart editor = page.getActiveEditor();
		   	    		 if(editor != null) {
		   	    			 final IEditorStatusLine statusLine = (IEditorStatusLine) editor.getAdapter(IEditorStatusLine.class);
		   	    			 if(statusLine != null) {
		   	    				 StudioDebugUIPlugin.getStandardDisplay().asyncExec(new Runnable() {
		   	    					 public void run() {
		   	    						 if (statusLine != null) {
		   	    							 statusLine.setMessage(true, message, null);
		   	    						 }
		   	    						 if (beep && message != null && StudioDebugUIPlugin.getActiveWorkbenchShell() != null) {
		   	    							 Display.getCurrent().beep();
		   	    						 }
		   	    					 }
		   	    				 });
		   	    			 }
		   	    		 }
		   	    	 }
		   	     }
		
			}
		});
		if(clear) {
			Job clearStatusJob  = new Job("Clear status line"){
				
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					ShowStatusMessage("", false,false);
					return Status.OK_STATUS;
				}
			};
			clearStatusJob.schedule(5000);
		}
		 
		 
	}


	public static String getUniqueIdentifier() {
		return PLUGIN_ID;
	}
}
