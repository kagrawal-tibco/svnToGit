package com.tibco.cep.studio.dashboard.ui.editors.views.page;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Spinner;

import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.ui.forms.AbstractSelectionListener;
import com.tibco.cep.studio.dashboard.ui.forms.PropertyControl;
import com.tibco.cep.studio.dashboard.ui.forms.SimplePropertyForm;

public class SpanSpinnerControl extends PropertyControl {
	
	private Spinner spinner;
	
	public SpanSpinnerControl(SimplePropertyForm parentForm, String displayName, SynProperty property) {
		super(parentForm, displayName, property);
	}

	@Override
	protected Control doCreateControl(SimplePropertyForm parentForm, Composite parent) {
		spinner = new Spinner(parent, SWT.BORDER);
		spinner.addSelectionListener(new AbstractSelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				setValue(spinner.getSelection()+"%");
			}
			
		});
		return spinner;
	}

	@Override
	protected void refreshEnumerations() {
		spinner.setMinimum(1);
		spinner.setMaximum(100);
		spinner.setPageIncrement(10);
	}

	@Override
	protected void refreshSelection() {
		String value = getValue();
		if (value.endsWith("%") == true) {
			value = value.substring(0, value.length()-1);
		}
		spinner.setSelection(Integer.parseInt(value));
	}
	
	
}