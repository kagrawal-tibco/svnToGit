package com.tibco.cep.studio.ui.dialog;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.studio.ui.StudioUIPlugin;

public class DoNotAskAgainMessageDialog extends MessageDialog {

	private String preferenceName;
	private String showPreferenceName;
	private IPreferenceStore store;
	private boolean makeDefault = false;

	public DoNotAskAgainMessageDialog(IPreferenceStore store, String preferenceName, String hidePreferenceName, Shell parentShell, String dialogTitle,
			String dialogMessage, int dialogImageType) {
		super(parentShell, dialogTitle, null, dialogMessage,
				dialogImageType, new String[] { IDialogConstants.YES_LABEL,
                        IDialogConstants.NO_LABEL }, 0);
		this.preferenceName = preferenceName;
		this.showPreferenceName = hidePreferenceName;
		this.store = store;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		final Button l = new Button(composite, SWT.CHECK);
		l.setText("Do this by default and do not ask again");
		l.addSelectionListener(new SelectionListener() {
		
			@Override
			public void widgetSelected(SelectionEvent e) {
				makeDefault = l.getSelection();
			}
		
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		return composite;
	}

	@Override
	public int getReturnCode() {
		return super.getReturnCode();
	}

	@Override
	public int open() {
		// check whether this "do not ask again" preference has been set.  If so, do not present the
		// message and just return 'OK'
		if (!StudioUIPlugin.getDefault().getPreferenceStore().getBoolean(showPreferenceName)) {
			setReturnCode(OK);
			return OK;
		} else {
			int ret = super.open();
			if (makeDefault) {
				store.setValue(preferenceName, ret == OK ? true : false);
				store.setValue(showPreferenceName, false);
			}
			return ret;
		}
	}

}
