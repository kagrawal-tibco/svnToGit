package com.tibco.cep.studio.wizard.as;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.tibco.cep.studio.wizard.as.internal.utils.Messages;

/**
 * The activator class controls the plug-in life cycle
 */
public class ASPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String _PLUGIN_ID                 = "com.tibco.cep.studio.wizard.as"; //$NON-NLS-1$

	// Some flags
	public static final int    _FLAG_PERFORM_SYNC_EXEC    = 1;
	public static final int    _FLAG_LOG_CORE_EXCEPTIONS  = 2;
	public static final int    _FLAG_LOG_OTHER_EXCEPTIONS = 4;

	// The shared instance
	private static ASPlugin    plugin;

	/**
	 * The constructor
	 */
	public ASPlugin() {
		plugin = this;
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static ASPlugin getDefault() {
		return plugin;
	}

	public Image getImage(String imageName) {
		Image image = getImageRegistry().get(imageName);
		if (image == null) {
			ImageDescriptor desc = getImageDescriptor(imageName);
			if (desc == null) {
				System.out.println("Could not find image " + imageName);
				return null;
			}
			image = desc.createImage();
			getImageRegistry().put(imageName, image);
		}
		return image;
	}
	
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(_PLUGIN_ID, path);
	}

	public static ImageDescriptor getImageDescriptor(String pluginId, String path) {
		pluginId = pluginId != null ? pluginId : _PLUGIN_ID;
		return imageDescriptorFromPlugin(pluginId, path);
	}

	public static void openDialog(Shell providedShell, final IOpenableInShell openable, int flags) {
		// If no shell was provided, try to get one from the active window
		if (providedShell == null) {
			IWorkbenchWindow window = plugin.getWorkbench().getActiveWorkbenchWindow();
			if (window != null) {
				providedShell = window.getShell();
				// sync-exec when we do this just in case
				flags = flags | _FLAG_PERFORM_SYNC_EXEC;
			}
		}

		// Create a runnable that will display the error status
		final Shell shell = providedShell;
		Runnable outerRunnable = new Runnable() {
			public void run() {
				Shell displayShell;
				if (shell == null) {
					Display display = Display.getCurrent();
					displayShell = new Shell(display);
				}
				else {
					displayShell = shell;
				}
				openable.open(displayShell);
				if (shell == null) {
					displayShell.dispose();
				}
			}
		};

		// Execute the above runnable as determined by the parameters
		if (shell == null || (flags & _FLAG_PERFORM_SYNC_EXEC) > 0) {
			Display display;
			if (shell == null) {
				display = Display.getCurrent();
				if (display == null) {
					display = Display.getDefault();
				}
			}
			else {
				display = shell.getDisplay();
			}
			display.syncExec(outerRunnable);
		}
		else {
			outerRunnable.run();
		}
	}

	public static void runWithProgress(Shell parent, final IRunnableWithProgress runnable) throws InvocationTargetException, InterruptedException {

		boolean createdShell = false;
		try {
			if (parent == null || parent.isDisposed()) {
				Display display = Display.getCurrent();
				if (display == null) {
					// not in UI thread
					runnable.run(new NullProgressMonitor());
					return;
				}
				parent = display.getActiveShell();
				if (parent == null) {
					parent = new Shell(display);
					createdShell = true;
				}
			}
			final Exception[] exception = new Exception[1];
			BusyIndicator.showWhile(parent.getDisplay(), new Runnable() {
				public void run() {
					try {
						runnable.run(new NullProgressMonitor());
					}
					catch (InvocationTargetException e) {
						exception[0] = e;
					}
					catch (InterruptedException e) {
						exception[0] = e;
					}
				}
			});
			if (exception[0] != null) {
				if (exception[0] instanceof InvocationTargetException) {
					throw (InvocationTargetException) exception[0];
				}
				else {
					throw (InterruptedException) exception[0];
				}
			}
		}
		finally {
			if (createdShell) parent.dispose();
		}
	}

	public static IStatus openError(Shell shell, String title, String message, Throwable exception) {
		return openError(shell, title, message, exception, _FLAG_LOG_OTHER_EXCEPTIONS);
	}

	public static IStatus openError(Shell providedShell, String title, String message, Throwable exception, int flags) {
		if (exception instanceof InvocationTargetException) {
			Throwable target = ((InvocationTargetException) exception).getTargetException();
			if (target instanceof RuntimeException) {
				throw (RuntimeException) target;
			}
			if (target instanceof Error) {
				throw (Error) target;
			}
			return openError(providedShell, title, message, target, flags);
		}

		IStatus status = null;
		boolean log = false;
		if (exception instanceof CoreException) {
			status = ((CoreException) exception).getStatus();
			log = ((flags & _FLAG_LOG_CORE_EXCEPTIONS) > 0);
		}
		else if (exception instanceof InterruptedException) {
			return new Status(IStatus.OK, _PLUGIN_ID, "OK"); //$NON-NLS-1$
		}
		else if (exception != null) {
			status = new Status(IStatus.ERROR, _PLUGIN_ID, "An internal error has occurred, consult the error log for details.", exception); //$NON-NLS-1$
			log = ((flags & _FLAG_LOG_OTHER_EXCEPTIONS) > 0);
			if (title == null) {
				title = "Internal Error"; //$NON-NLS-1$
			}
		}
		else {
			status = new Status(IStatus.ERROR, _PLUGIN_ID, Messages.getString("ASPlugin.status_error")); //$NON-NLS-1$
		}

		if (status.isMultiStatus() && status.getChildren().length == 1) {
			status = status.getChildren()[0];
		}

		if (status.isOK()) {
			return status;
		}

		if (log) {
			log(status);
		}

		final String displayTitle = title;
		final String displayMessage = message;
		final IStatus displayStatus = status;
		final IOpenableInShell openable = new IOpenableInShell() {
			public void open(Shell shell) {
				if (displayStatus.getSeverity() == IStatus.INFO && !displayStatus.isMultiStatus()) {
					MessageDialog.openInformation(shell, "ActiveSpaces Infomation", displayStatus.getMessage()); //$NON-NLS-1$
				}
				else {
					ErrorDialog.openError(shell, displayTitle, displayMessage, displayStatus);
				}
			}
		};
		openDialog(providedShell, openable, flags);

		return status;
	}

	public static void log(IStatus status) {
		getDefault().getLog().log(status);
	}

	public static void log(String msg) {
		getDefault().getLog().log(new Status(IStatus.INFO, _PLUGIN_ID, 0, msg, null));
	}

	public static void log(CoreException e) {
		log(e.getStatus().getSeverity(), "Internal Error", e); //$NON-NLS-1$
	}

	public static void log(int severity, String message, Throwable e) {
		log(new Status(severity, _PLUGIN_ID, 0, message, e));
	}

	public interface IOpenableInShell {
		public void open(Shell shell);
	}

}
