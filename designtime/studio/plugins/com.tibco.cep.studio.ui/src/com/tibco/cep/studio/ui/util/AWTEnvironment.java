package com.tibco.cep.studio.ui.util;

import java.awt.EventQueue;
import java.awt.Frame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.diagramming.utils.SyncXErrorHandler;


/**
 * 
 * @author sasahoo
 *
 */
public final class AWTEnvironment {

	private static final String GTK_LOOK_AND_FEEL_NAME = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel"; //$NON-NLS-1$

	private static AWTEnvironment instance = null;
	private static boolean isLookAndFeelInitialized = false;

	private final Display display;
	private final SwingDialogListener dialogListener;

	public static AWTEnvironment getInstance(Display display) {
		if (display == null) {
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		}
		if ((instance != null) && !display.equals(instance.display)) {
			throw new UnsupportedOperationException(
			"Multiple displays not supported");
		}
		synchronized (AWTEnvironment.class) {
			if (instance == null) {
				instance = new AWTEnvironment(display);
			}
		}
		return instance;
	}

	// Private constructor - clients use getInstance() to obtain instances
	private AWTEnvironment(Display display) {
		assert display != null;
//		System.setProperty("sun.awt.noerasebackground", "true"); //$NON-NLS-1$//$NON-NLS-2$
//		try {
//			EventQueue.invokeAndWait(new Runnable() {
//				public void run() {
//					setSystemLookAndFeel();
//				}
//			});
//		} catch (InterruptedException e) {
//			SWT.error(SWT.ERROR_FAILED_EXEC, e);
//		} catch (InvocationTargetException e) {
//			SWT.error(SWT.ERROR_FAILED_EXEC, e);
//		}

		this.display = display;
		dialogListener = new SwingDialogListener(display);
	}

	public void invokeAndBlockSwt(final Runnable runnable) {
		assert display != null;
		if (runnable == null) {
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		}
		if (display != Display.getCurrent()) {
			SWT.error(SWT.ERROR_THREAD_INVALID_ACCESS);
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// do swing work...
					runnable.run();
				} finally {
					display.asyncExec(new Runnable() {
						public void run() {
							// Unblock SWT
							DisplayBlocker.unblock();
						}
					});
				}
			}
		});
		DisplayBlocker.block();
	}

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

//	private void setSystemLookAndFeel() {
//		assert EventQueue.isDispatchThread(); // On AWT event thread
//
//		if (!isLookAndFeelInitialized) {
//			isLookAndFeelInitialized = true;
//			try {
//				String systemLaf = UIManager.getSystemLookAndFeelClassName();
//				String xplatLaf = UIManager
//				.getCrossPlatformLookAndFeelClassName();
//				if (xplatLaf.equals(systemLaf) && SWT.getPlatform().equals("gtk")) {
//					systemLaf = GTK_LOOK_AND_FEEL_NAME;
//				}
//				UIManager.setLookAndFeel(systemLaf);
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (InstantiationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (UnsupportedLookAndFeelException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}

	// This method is called by unit tests
	static void reset() {
		instance = null;
	}

}

