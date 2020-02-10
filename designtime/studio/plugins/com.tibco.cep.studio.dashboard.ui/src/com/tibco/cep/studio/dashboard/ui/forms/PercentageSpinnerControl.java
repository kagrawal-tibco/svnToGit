package com.tibco.cep.studio.dashboard.ui.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Spinner;

import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynNumericType;

public class PercentageSpinnerControl extends PropertyControl {
	
	private Spinner spinner;
	
	public PercentageSpinnerControl(SimplePropertyForm parentForm, String displayName, SynProperty property) {
		super(parentForm, displayName, property);
	}

	@Override
	protected Control doCreateControl(SimplePropertyForm parentForm, Composite parent) {
		spinner = new Spinner(parent, SWT.BORDER);
		spinner.addSelectionListener(new AbstractSelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				setValue(Double.toString(spinner.getSelection()/100.00));
			}
			
		});
		return spinner;
	}

	@Override
	protected void refreshEnumerations() {
		SynNumericType typeDefinition = (SynNumericType) getProperty().getTypeDefinition();
		int minInclusive = (int)(100*Double.parseDouble(typeDefinition.getMinInclusive()));
		int maxInclusive = (int)(100*Double.parseDouble(typeDefinition.getMaxInclusive()));
		spinner.setMinimum(minInclusive);
		spinner.setMaximum(maxInclusive);
	}

	@Override
	protected void refreshSelection() {
		spinner.setSelection((int)(100*Double.parseDouble(getValue())));
	}
	
	
}