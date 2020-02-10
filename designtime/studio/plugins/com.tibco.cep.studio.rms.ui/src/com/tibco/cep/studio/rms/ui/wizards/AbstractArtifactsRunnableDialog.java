package com.tibco.cep.studio.rms.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.ControlEnableState;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.operation.ModalContext;
import org.eclipse.jface.wizard.ProgressMonitorPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractArtifactsRunnableDialog extends Dialog  implements SelectionListener, ModifyListener,IRunnableContext{

	protected ProgressMonitorPart progressMonitorPart;
	protected Cursor waitCursor;
	protected Cursor arrowCursor;
	protected boolean needsProgressMonitor = false;
	
	// The number of long running operation executed from the dialog.
	protected long activeRunningOperations = 0;
	protected boolean lockedUI = false;
	protected static final String FOCUS_CONTROL = "focusControl"; 
	
	protected Button cancelButton;
	protected Button okButton;
	private SelectionAdapter cancelListener;

	
	protected AbstractArtifactsRunnableDialog(Shell parentShell) {
		super(parentShell);
//		this.title = dialogTitle == null ? JFaceResources.getString("Problem_Occurred") : dialogTitle;
		setShellStyle(getShellStyle() | SWT.RESIZE);
		
		cancelListener = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				cancelPressed();
			}
		};
	}

	protected ProgressMonitorPart createProgressMonitorPart(
			Composite composite, GridLayout layout) {
		return new ProgressMonitorPart(composite, layout, SWT.DEFAULT) {
			String currentTask = null;

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.wizard.ProgressMonitorPart#setBlocked(org.eclipse.core.runtime.IStatus)
			 */
			public void setBlocked(IStatus reason) {
				super.setBlocked(reason);
				if (!lockedUI) {
					getBlockedHandler().showBlocked(getShell(), this, reason,
							currentTask);
				}
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.wizard.ProgressMonitorPart#clearBlocked()
			 */
			public void clearBlocked() {
				super.clearBlocked();
				if (!lockedUI) {
					getBlockedHandler().clearBlocked();
				}
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.wizard.ProgressMonitorPart#beginTask(java.lang.String,
			 *      int)
			 */
			public void beginTask(String name, int totalWork) {
				super.beginTask(name, totalWork);
				currentTask = name;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.wizard.ProgressMonitorPart#setTaskName(java.lang.String)
			 */
			public void setTaskName(String name) {
				super.setTaskName(name);
				currentTask = name;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.wizard.ProgressMonitorPart#subTask(java.lang.String)
			 */
			public void subTask(String name) {
				super.subTask(name);
				// If we haven't got anything yet use this value for more
				// context
				if (currentTask == null) {
					currentTask = name;
				}
			}
		};
	}
	
	@SuppressWarnings("unchecked")
	private Object aboutToStart(boolean enableCancelButton) {
		Map<String, Control> savedState = null;
		if (getShell() != null) {
			// Save focus control
			Control focusControl = getShell().getDisplay().getFocusControl();
			if (focusControl != null && focusControl.getShell() != getShell()) {
				focusControl = null;
			}
			cancelButton.removeSelectionListener(cancelListener);
			// Set the busy cursor to all shells.
			Display d = getShell().getDisplay();
			waitCursor = new Cursor(d, SWT.CURSOR_WAIT);
			setDisplayCursor(waitCursor);
			// Set the arrow cursor to the cancel component.
			arrowCursor = new Cursor(d, SWT.CURSOR_ARROW);
			cancelButton.setCursor(arrowCursor);
			// Deactivate shell
			savedState = saveUIState(needsProgressMonitor && enableCancelButton);
			if (focusControl != null) {
				savedState.put(FOCUS_CONTROL, focusControl);
			}
			// Attach the progress monitor part to the cancel button
			if (needsProgressMonitor) {
				progressMonitorPart.attachToCancelComponent(cancelButton);
				progressMonitorPart.setVisible(true);
			}
		}
		return savedState;
	}
	
	/**
	 * @param keepCancelEnabled
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map saveUIState(boolean keepCancelEnabled) {
		Map savedState = new HashMap(10);
		saveEnableStateAndSet(okButton, savedState, "ok", true /*false*/);
		saveEnableStateAndSet(cancelButton, savedState,"cancel", keepCancelEnabled);
		if (getContents() != null) {
			savedState
					.put(
							"page", ControlEnableState.disable(getContents())); //$NON-NLS-1$
		}
		return savedState;
	}
	
	/**
	 * @param w
	 * @param h
	 * @param key
	 * @param enabled
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void saveEnableStateAndSet(Control w, Map h, String key,
			boolean enabled) {
		if (w != null) {
			h.put(key, w.getEnabled() ? Boolean.TRUE : Boolean.FALSE);
			w.setEnabled(enabled);
		}
	}
	
	/*
	 * (non-Javadoc) Method declared on Dialog.
	 */
	protected void cancelPressed() {
		if (activeRunningOperations <= 0) {
			setReturnCode(CANCEL);
			close();
		} else {
			cancelButton.setEnabled(false);
		}
	}
	
	private void setDisplayCursor(Cursor c) {
		Shell[] shells = getShell().getDisplay().getShells();
		for (int i = 0; i < shells.length; i++) {
			shells[i].setCursor(c);
		}
	}
	
	@Override
	public void run(boolean fork, boolean cancelable,
			IRunnableWithProgress runnable) throws InvocationTargetException,
			InterruptedException {
		// The operation can only be canceled if it is executed in a separate
		// thread.
		// Otherwise the UI is blocked anyway.
		Object state = null;
		if (activeRunningOperations == 0) {
			state = aboutToStart(fork && cancelable);
		}
		activeRunningOperations++;
		try {
			if (!fork) {
				lockedUI = true;
			}
			ModalContext.run(runnable, fork, getProgressMonitor(), getShell()
					.getDisplay());
			lockedUI = false;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			activeRunningOperations--;
			// Stop if this is the last one
			if (state != null) {
				stopped(state);
			}
		}
	}
	
	/**
	 * @return
	 */
	protected IProgressMonitor getProgressMonitor() {
		return progressMonitorPart;
	}
	
	/**
	 * @param savedState
	 */
	@SuppressWarnings("rawtypes")
	private void stopped(Object savedState) {
		if (getShell() != null && !getShell().isDisposed()) {
			if (needsProgressMonitor) {
				progressMonitorPart.setVisible(false);
				progressMonitorPart.removeFromCancelComponent(cancelButton);
			}
			Map state = (Map) savedState;
			restoreUIState(state);
			cancelButton.addSelectionListener(cancelListener);
			setDisplayCursor(null);
			cancelButton.setCursor(null);
			waitCursor.dispose();
			waitCursor = null;
			arrowCursor.dispose();
			arrowCursor = null;
			Control focusControl = (Control) state.get(FOCUS_CONTROL);
			if (focusControl != null && !focusControl.isDisposed()) {
				focusControl.setFocus();
			}
		}
	}
	
	/**
	 * @param state
	 */
	@SuppressWarnings("rawtypes")
	private void restoreUIState(Map state) {
		restoreEnableState(okButton, state, "ok"); 
		okButton.setVisible(false);
		restoreEnableState(cancelButton, state, "cancel");
		cancelButton.setText("Finish");
		Object pageValue = state.get("page"); 
		if (pageValue != null) {
			((ControlEnableState) pageValue).restore();
		}
	}
	
	/**
	 * @param w
	 * @param h
	 * @param key
	 */
	@SuppressWarnings("rawtypes")
	private void restoreEnableState(Control w, Map h, String key) {
		if (w != null) {
			Boolean b = (Boolean) h.get(key);
			if (b != null) {
				w.setEnabled(b);
			}
		}
	}

	/**
	 * @return the okButton
	 */
	public Button getOkButton() {
		return okButton;
	}
}