package com.tibco.cep.studio.dashboard.ui.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;

public class CheckBoxPropertyControl extends PropertyControl {

	protected Button btn_checkBox;

	protected boolean showDisplayName;

	protected CheckBoxPropertyControl(SimplePropertyForm parentForm, String displayName, SynProperty property) {
		super(parentForm, displayName, property);
		showDisplayName = true;
	}

	public void setShowDisplayName(boolean showDisplayName) {
		this.showDisplayName = showDisplayName;
	}

	public boolean isShowDisplayName() {
		return showDisplayName;
	}

	@Override
	protected Control doCreateControl(SimplePropertyForm parentForm, Composite parent) {
		if (showDisplayName == true) {
			btn_checkBox = parentForm.createButton(parent, displayName, SWT.CHECK);
		}
		else {
			btn_checkBox = parentForm.createButton(parent, "", SWT.CHECK);
		}
		btn_checkBox.addSelectionListener(new AbstractSelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				setValue(Boolean.toString(btn_checkBox.getSelection()));
			}

		});
		return btn_checkBox;
	}

	@Override
	protected void refreshEnumerations() {
		//do nothing
	}

	@Override
	protected void refreshSelection() {
		if (btn_checkBox != null) {
			btn_checkBox.setSelection(Boolean.parseBoolean(getValue()));
		}
	}

}