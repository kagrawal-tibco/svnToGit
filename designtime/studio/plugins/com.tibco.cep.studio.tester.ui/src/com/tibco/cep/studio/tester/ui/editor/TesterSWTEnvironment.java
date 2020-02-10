package com.tibco.cep.studio.tester.ui.editor;

import java.awt.EventQueue;
import java.awt.Frame;
import java.lang.reflect.InvocationTargetException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.diagramming.utils.SyncXErrorHandler;

public final class TesterSWTEnvironment {

	private static final String GTK_LOOK_AND_FEEL_NAME = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel"; //$NON-NLS-1$

	private static TesterSWTEnvironment instance = null;
	private static boolean isLookAndFeelInitialized = false;

	private final Display display;
	private final TesterDialogListener dialogListener;

	/**
	 * Returns the single instance of AwtEnvironment for the given display. On
	 * the first call to this method, the necessary initialization to allow
	 * AWT/Swing code to run properly within an Eclipse application is done.
	 * This initialization includes setting the approprite look and feel and
	 * registering the necessary listeners to ensure proper behavior of modal
	 * dialogs.
	 * <p>
	 * The first call to this method must occur before any AWT/Swing APIs are
	 * called.
	 * <p>
	 * The current implementation limits the number of instances of
	 * AwtEnvironment to one. If this method is called with a display different
	 * to one used on a previous call, {@link UnsupportedOperationException} is
	 * thrown.
	 * 
	 * @param display
	 *            the non-null SWT display
	 * @return the AWT environment
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the display is null</li>
	 *                </ul>
	 * @exception UnsupportedOperationException -
	 *                on attempt to use multiple displays.
	 */
	public static TesterSWTEnvironment getInstance(Display display) {
		// For now assume a single display. If necessary, this implementation
		// can be changed to create multiple environments for multiple display
		// applications.
		// TODO: add multiple display support
		if (display == null) {
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		}
		if ((instance != null) && !display.equals(instance.display)) {
			throw new UnsupportedOperationException(
					"Multiple displays not supported");
		}
		synchronized (TesterSWTEnvironment.class) {
			if (instance == null) {
				instance = new TesterSWTEnvironment(display);
			}
		}
		return instance;
	}

	// Private constructor - clients use getInstance() to obtain instances
	private TesterSWTEnvironment(Display display) {
		assert display != null;

		/*
		 * This property removes a large amount of flicker from embedded swing
		 * components. Ideally it would not be set until EmbeddedSwingComposite
		 * is used, but since its value is read once and cached by AWT, it needs
		 * to be set before any AWT/Swing APIs are called.
		 */
		// TODO: this is effective only on Windows.
		System.setProperty("sun.awt.noerasebackground", "true"); //$NON-NLS-1$//$NON-NLS-2$

		/*
		 * RCP apps always want the standard platform look and feel It's
		 * important to wait for the L&F to be set so that any subsequent calls
		 * to createFrame() will be return a frame with the proper L&F (note
		 * that createFrame() happens on the SWT thread).
		 * 
		 * The call to invokeAndWait is safe because the first call
		 * AwtEnvironment.getInstance should happen before any (potential
		 * deadlocking) activity occurs on the AWT thread.
		 */
		try {
			EventQueue.invokeAndWait(new Runnable() {
				public void run() {
					setSystemLookAndFeel();
				}
			});
		} catch (InterruptedException e) {
			SWT.error(SWT.ERROR_FAILED_EXEC, e);
		} catch (InvocationTargetException e) {
			SWT.error(SWT.ERROR_FAILED_EXEC, e);
		}

		this.display = display;

		// Listen for AWT modal dialogs to make them modal application-wide
		dialogListener = new TesterDialogListener(display);
	}

	/**
	 * Invokes the given runnable in the AWT event thread while blocking user
	 * input on the SWT event thread. The SWT event thread will remain blocked
	 * until the runnable task completes, at which point this method will
	 * return.
	 * <p>
	 * This method is useful for displayng modal AWT/Swing dialogs from the SWT
	 * event thread. The modal AWT/Swing dialog will always block input across
	 * the whole application, but not until it appears. By calling this method,
	 * it is guaranteed that SWT input is blocked immediately, even before the
	 * AWT/Swing dialog appears.
	 * <p>
	 * To avoid unnecessary flicker, AWT/Swing dialogs should have their parent
	 * set to a frame returned by {@link #createDialogParentFrame()}.
	 * <p>
	 * This method must be called from the SWT event thread.
	 * 
	 * @param runnable
	 *            the code to schedule on the AWT event thread
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the runnable is null</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                SWT event thread
	 *                </ul>
	 */
	public void invokeAndBlockSwt(final Runnable runnable) {
		assert display != null;

		/*
		 * This code snippet is based on the following thread on
		 * news.eclipse.platform.swt:
		 * http://dev.eclipse.org/newslists/news.eclipse.platform.swt/msg24234.html
		 */
		if (runnable == null) {
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		}
		if (display != Display.getCurrent()) {
			SWT.error(SWT.ERROR_THREAD_INVALID_ACCESS);
		}

		// Switch to the AWT thread...
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// do swing work...
					runnable.run();
				} finally {
					display.asyncExec(new Runnable() {
						public void run() {
							// Unblock SWT
							TesterInputBlocker.unblock();
						}
					});
				}
			}
		});

		// Prevent user input on SWT components
		TesterInputBlocker.block();
	}

	/**
	 * Creates an AWT frame suitable as a parent for AWT/Swing dialogs.
	 * <p>
	 * This method must be called from the SWT event thread. There must be an
	 * active shell associated with the environment's display.
	 * <p>
	 * The created frame is a non-visible child of the active shell and will be
	 * disposed when that shell is disposed.
	 * <p>
	 * See {@link #createDialogParentFrame(Shell)} for more details.
	 * 
	 * @return a {@link java.awt.Frame} to be used for parenting dialogs
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                SWT event thread
	 *                </ul>
	 * @exception IllegalStateException
	 *                if the current display has no shells
	 */
	public Frame createDialogParentFrame() {
		if (display != Display.getCurrent()) {
			SWT.error(SWT.ERROR_THREAD_INVALID_ACCESS);
		}
		Shell parent = display.getActiveShell();
		if (parent == null) {
			throw new IllegalStateException("No Active Shell");
		}
		return createDialogParentFrame(parent);
	}

	/**
	 * Creates an AWT frame suitable as a parent for AWT/Swing dialogs.
	 * <p>
	 * This method must be called from the SWT event thread. There must be an
	 * active shell associated with the environment's display.
	 * <p>
	 * The created frame is a non-visible child of the given shell and will be
	 * disposed when that shell is disposed.
	 * <p>
	 * This method is useful for creating a frame to parent any AWT/Swing
	 * dialogs created for use inside a SWT application. A modal AWT/Swing
	 * dialogs will flicker less if its parent is set to the returned frame
	 * rather than to null or to an independently created {@link java.awt.Frame}.
	 * 
	 * @return a {@link java.awt.Frame} to be used for parenting dialogs
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                SWT event thread
	 *                </ul>
	 * @exception IllegalStateException
	 *                if the current display has no shells
	 */
	public Frame createDialogParentFrame(Shell parent) {
		if (parent == null) {
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		}
		if (display != Display.getCurrent()) {
			SWT.error(SWT.ERROR_THREAD_INVALID_ACCESS);
		}
		Shell shell = new Shell(parent);
		shell.setVisible(false);
		Composite composite = new Composite(shell, SWT.EMBEDDED);
		 Frame frame = SWT_AWT.new_Frame(composite);
		 new SyncXErrorHandler().installHandler();
		 return frame;
	}

	// Find a shell to use, giving preference to the active shell.
	Shell getShell() {
		Shell shell = display.getActiveShell();
		if (shell == null) {
			Shell[] allShells = display.getShells();
			if (allShells.length > 0) {
				shell = allShells[0];
			}
		}
		return shell;
	}

	void requestAwtDialogFocus() {
		assert dialogListener != null;

		dialogListener.requestFocus();
	}

	private void setSystemLookAndFeel() {
		assert EventQueue.isDispatchThread(); // On AWT event thread

		if (!isLookAndFeelInitialized) {
			isLookAndFeelInitialized = true;
			try {
				String systemLaf = UIManager.getSystemLookAndFeelClassName();
				String xplatLaf = UIManager
						.getCrossPlatformLookAndFeelClassName();

				// Java makes metal the system look and feel if running under a
				// non-gnome Linux desktop. Fix that here, if the RCP itself is
				// running
				// with the GTK windowing system set.
				if (xplatLaf.equals(systemLaf) && SWT.getPlatform().equals("gtk")) {
					systemLaf = GTK_LOOK_AND_FEEL_NAME;
				}
				UIManager.setLookAndFeel(systemLaf);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// This method is called by unit tests
	static void reset() {
		instance = null;
	}

}
