package com.tibco.cep.studio.ui.dialog;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;


/**
 * 
 * @author sasahoo
 *
 */
public class OverwriteMessageDialog extends MessageDialog {


	private IPreferenceStore store;
	private String pref;
	private String customMessage;

	/**
	 * @param store
	 * @param pref
	 * @param parentShell
	 * @param dialogTitle
	 * @param dialogTitleImage
	 * @param dialogMessage
	 * @param customMessage
	 * @param dialogImageType
	 * @param dialogButtonLabels
	 * @param defaultIndex
	 */
	public OverwriteMessageDialog(IPreferenceStore store, String pref, Shell parentShell, String dialogTitle,
			Image dialogTitleImage, String dialogMessage, String customMessage, int dialogImageType,
			String[] dialogButtonLabels, int defaultIndex) {
		super(parentShell, dialogTitle, dialogTitleImage, dialogMessage,
				dialogImageType, dialogButtonLabels, defaultIndex);
		this.store = store;
		this.pref = pref;
		this.customMessage = customMessage;
	}

	/**
	 * @param parent
	 * @param title
	 * @param message
	 * @return
	 */
	public static boolean openQuestion( Shell parent, String title,	String message, IPreferenceStore store, String pref, String customMessage) {
		return open(QUESTION, parent, title, message, SWT.NONE, store, pref, customMessage);
	}

	/**
	 * @param store
	 * @param pref
	 * @param customMessage
	 * @param kind
	 * @param parent
	 * @param title
	 * @param message
	 * @param style
	 * @return
	 */
	public static boolean open(int kind, Shell parent, String title, String message, int style, IPreferenceStore store, String pref, String customMessage) {
		OverwriteMessageDialog dialog = new OverwriteMessageDialog(store, pref, parent, title, null, message,customMessage, kind, getButtonLabels(kind), 0);
		style &= SWT.SHEET;
		dialog.setShellStyle(dialog.getShellStyle() | style);
		return dialog.open() == 0;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.MessageDialog#createCustomArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createCustomArea(Composite parent) {
		final Button button = new Button(parent, SWT.CHECK);
		button.setText(customMessage);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				store.setValue(pref, button.getSelection());
			}
		});
		return button;
	}

	static String[] getButtonLabels(int kind) {
		String[] dialogButtonLabels;
		switch (kind) {
		case ERROR:
		case INFORMATION:
		case WARNING: {
			dialogButtonLabels = new String[] { IDialogConstants.OK_LABEL };
			break;
		}
		case CONFIRM: {
			dialogButtonLabels = new String[] { IDialogConstants.OK_LABEL,
					IDialogConstants.CANCEL_LABEL };
			break;
		}
		case QUESTION: {
			dialogButtonLabels = new String[] { IDialogConstants.YES_LABEL,
					IDialogConstants.NO_LABEL };
			break;
		}
		case QUESTION_WITH_CANCEL: {
			dialogButtonLabels = new String[] { IDialogConstants.YES_LABEL,
					IDialogConstants.NO_LABEL,
					IDialogConstants.CANCEL_LABEL };
			break;
		}
		default: {
			throw new IllegalArgumentException(
					"Illegal value for kind in MessageDialog.open()"); //$NON-NLS-1$
		}
		}
		return dialogButtonLabels;
	}
}
