package com.tibco.cep.studio.dashboard.ui.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;

public class TextPropertyControl extends PropertyControl {

	private boolean multiLine;

	private int style;

	private Text text;

	protected TextPropertyControl(SimplePropertyForm parentForm, String displayName, SynProperty property, boolean multiLine) {
		super(parentForm, displayName, property);
		this.multiLine = multiLine;
		if (this.multiLine == true) {
			style = SWT.MULTI | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER;
		} else {
			style = SWT.SINGLE | SWT.BORDER;
		}
	}

	@Override
	protected Control doCreateControl(SimplePropertyForm parentForm, Composite parent) {
		if (isReadOnly() == true) {
			style = style | SWT.READ_ONLY;
		}
		text = new Text(parent, style);
		text.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				setValue(text.getText());
			}

		});
		return text;
	}

	@Override
	protected void refreshEnumerations() {
		// do nothing
	}

	@Override
	protected void refreshSelection() {
		if (text != null) {
			text.setText(getValue());
		}
	}
	
	public boolean isMultiLine() {
		return multiLine;
	}

}