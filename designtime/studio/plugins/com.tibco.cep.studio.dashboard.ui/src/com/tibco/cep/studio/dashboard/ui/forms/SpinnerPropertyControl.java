package com.tibco.cep.studio.dashboard.ui.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Spinner;

import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynNumericType;

public class SpinnerPropertyControl extends PropertyControl {

	private Spinner spinner;

	private boolean minAlreadySet;

	private boolean maxAlreadySet;

	private int precision;

	protected SpinnerPropertyControl(SimplePropertyForm parentForm, String displayName, SynProperty property) {
		this(parentForm, displayName, property, 0);
	}

	protected SpinnerPropertyControl(SimplePropertyForm parentForm, String displayName, SynProperty property, int precision) {
		super(parentForm, displayName, property);
		this.minAlreadySet = false;
		this.maxAlreadySet = false;
		this.precision = precision;
	}

	@Override
	protected Control doCreateControl(SimplePropertyForm parentForm, Composite parent) {
		spinner = new Spinner(parent, SWT.BORDER);
		spinner.setDigits(precision);
		spinner.addSelectionListener(new AbstractSelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				int selection = spinner.getSelection();
				if (precision == 0) {
					setValue(Integer.toString(selection));
				} else {
					double value = selection / (precision * 10.00);
					setValue(Double.toString(value));
				}
			}

		});
		return spinner;
	}

	@Override
	protected void refreshEnumerations() {
		SynNumericType typeDefinition = (SynNumericType) getProperty().getTypeDefinition();
		if (minAlreadySet == false) {
			int minimum = 0;
			if (precision == 0) {
				minimum = Integer.parseInt(typeDefinition.getMinInclusive());
			}
			else {
				minimum = (int) (precision * 10 * Double.parseDouble(typeDefinition.getMinInclusive()));
			}
			spinner.setMinimum(minimum);
		}
		if (maxAlreadySet == false) {
			int maximum = 0;
			if (precision == 0) {
				maximum = Integer.parseInt(typeDefinition.getMaxInclusive());
			}
			else {
				maximum = (int) (precision * 10 * Double.parseDouble(typeDefinition.getMaxInclusive()));
			}
			spinner.setMaximum(maximum);
		}
	}

	public void setMaximum(double maximum) {
		if (precision != 0) {
			maximum = (int) (precision * 10 * maximum);
		}
		spinner.setMaximum((int)maximum);
		maxAlreadySet = true;
	}

	public void setMinimum(double minimum) {
		if (precision != 0) {
			minimum = (int) (precision * 10 * minimum);
		}
		spinner.setMinimum((int) minimum);
		minAlreadySet = true;
	}

	@Override
	protected void refreshSelection() {
		try {
			int selection = 0;
			if (precision == 0) {
				selection = Integer.parseInt(getValue());
			}
			else {
				selection = (int) (precision * 10 * Double.parseDouble(getValue()));
			}
			spinner.setSelection(selection);
		} catch (NumberFormatException e) {
			// do nothing
		}
	}

}