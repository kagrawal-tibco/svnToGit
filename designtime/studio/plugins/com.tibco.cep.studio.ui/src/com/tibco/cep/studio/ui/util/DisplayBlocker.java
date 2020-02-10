package com.tibco.cep.studio.ui.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


/**
 * 
 * @author sasahoo
 *
 */
class DisplayBlocker extends Dialog {
	static private DisplayBlocker instance = null;
	static private int blockCount = 0;
	private Shell shell;

	private DisplayBlocker(Shell parent) {
		super(parent, SWT.NONE);
	}

	private Object open() {
		assert Display.getCurrent() != null; // On SWT event thread

		final Shell parent = getParent();
		shell = new Shell(parent, SWT.APPLICATION_MODAL);
		shell.setSize(0, 0);
		shell.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				if (SWT.getPlatform().equals("gtk")) {
					shell.moveBelow(null);
				}
				AWTEnvironment.getInstance(shell.getDisplay())
						.requestAwtDialogFocus();
			}
		});
		shell.open();

		Display display = parent.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return null;
	}

	private void close() {
		assert shell != null;

		shell.dispose();
	}

	static void unblock() {
		assert blockCount >= 0;
		assert Display.getCurrent() != null; // On SWT event thread
		if (blockCount == 0) {
			return;
		}
		if ((blockCount == 1) && (instance != null)) {
			instance.close();
			instance = null;
		}
		blockCount--;
	}

	static void block() {
		assert blockCount >= 0;

		final Display display = Display.getCurrent();
		assert display != null; // On SWT event thread

		blockCount++;
		if (blockCount == 1) {
			assert instance == null; // should be no existing blocker

			// get a shell to parent the blocking dialog
			Shell shell = AWTEnvironment.getInstance(display).getShell();
			if (shell != null) {
				instance = new DisplayBlocker(shell);
				instance.open();
			}
		}
	}

}

