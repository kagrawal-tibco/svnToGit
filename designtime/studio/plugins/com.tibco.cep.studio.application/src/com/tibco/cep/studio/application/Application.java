package com.tibco.cep.studio.application;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.application.utils.Messages;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {

	protected static final String AWT_THREAD_NAME = "java.awt.EventDispatchThread";

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
	 */
	public Object start(IApplicationContext context) {
		Display display = PlatformUI.createDisplay();
		try {
			
			if (Messages.BE_LICENSE != null && Messages.BE_LICENSE.length()!=0 )
//			StudioApplicationPlugin.LOGGER.logInfo(Messages.BE_LICENSE);
//			StudioApplicationPlugin.LOGGER.logInfo(Messages.Application_Info1);
			
			initUncaughtExceptionHandler();

			int returnCode = PlatformUI.createAndRunWorkbench(display,
				new ApplicationWorkbenchAdvisor());

			if (returnCode == PlatformUI.RETURN_RESTART) {
				return IApplication.EXIT_RESTART;
			}
			return IApplication.EXIT_OK;
		}
		finally {
			display.dispose();
		}
	}

	/**
	 * We provide our own UncaughtExceptionHandler to avoid logging UI exceptions
	 * from the EventDispatchThread
	 */
	private void initUncaughtExceptionHandler() {
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			
			public void uncaughtException(Thread t, Throwable e) {
				
				if (t != null && AWT_THREAD_NAME.equals(t.getClass().getName())) {
					// swallow exception, do not print uncaught UI exceptions
				} else if (!(e instanceof ThreadDeath)) {
					System.err.print("Exception in thread \""
							+ t.getName() + "\" ");
					e.printStackTrace(System.err);
				}
			}
		
		});

	}

	

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	public void stop() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench == null)
			return;
		final Display display = workbench.getDisplay();
		display.syncExec(new Runnable() {
			public void run() {
				if (!display.isDisposed())
					workbench.close();
			}
		});
	}
}
