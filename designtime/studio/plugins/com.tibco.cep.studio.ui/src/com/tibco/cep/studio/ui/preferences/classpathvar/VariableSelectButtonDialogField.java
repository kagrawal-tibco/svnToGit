package com.tibco.cep.studio.ui.preferences.classpathvar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.cep.studio.ui.util.ClasspathVariableUiUtils;

public class VariableSelectButtonDialogField extends VariableDialogField {

	private Button fButton;
	private boolean fIsSelected;
	private VariableDialogField[] fAttachedDialogFields;
	private int fButtonStyle;

	/**
	 * Allowed button styles: SWT.RADIO, SWT.CHECK, SWT.TOGGLE, SWT.PUSH
	 */
	public VariableSelectButtonDialogField(int buttonStyle) {
		super();
		fIsSelected= false;
		fAttachedDialogFields= null;
		fButtonStyle= buttonStyle;
	}

	public void attachDialogField(VariableDialogField dialogField) {
		attachDialogFields(new VariableDialogField[] { dialogField });
	}

	public void attachDialogFields(VariableDialogField[] dialogFields) {
		fAttachedDialogFields= dialogFields;
		for (int i= 0; i < dialogFields.length; i++) {
			dialogFields[i].setEnabled(fIsSelected);
		}
	}

	/**
	 * Returns <code>true</code> is  teh gived field is attached to the selection button.
	 */
	public boolean isAttached(VariableDialogField editor) {
		if (fAttachedDialogFields != null) {
			for (int i=0; i < fAttachedDialogFields.length; i++) {
				if (fAttachedDialogFields[i] == editor) {
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * @see DialogField#doFillIntoGrid
	 */
	@Override
	public Control[] doFillIntoGrid(Composite parent, int nColumns) {
		Button button= getSelectionButton(parent);
		GridData gd= new GridData();
		gd.horizontalSpan= nColumns;
		gd.horizontalAlignment= GridData.FILL;
		if (fButtonStyle == SWT.PUSH) {
			gd.widthHint = ClasspathVariableUiUtils.getButtonWidthHint(button);
		}

		button.setLayoutData(gd);

		return new Control[] { button };
	}

	/*
	 * @see DialogField#getNumberOfControls
	 */
	@Override
	public int getNumberOfControls() {
		return 1;
	}

	public Button getSelectionButton(Composite group) {
		if (fButton == null) {
			fButton= new Button(group, fButtonStyle);
			fButton.setFont(group.getFont());
			fButton.setText(fLabelText);
			fButton.setEnabled(isEnabled());
			fButton.setSelection(fIsSelected);
			fButton.addSelectionListener(new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent e) {
					doWidgetSelected(e);
				}
				public void widgetSelected(SelectionEvent e) {
					doWidgetSelected(e);
				}
			});
		}
		return fButton;
	}

	private void doWidgetSelected(SelectionEvent e) {
		if (isOkToUse(fButton)) {
			changeValue(fButton.getSelection());
		}
	}

	private void changeValue(boolean newState) {
		if (fIsSelected != newState) {
			fIsSelected= newState;
			if (fAttachedDialogFields != null) {
				boolean focusSet= false;
				for (int i= 0; i < fAttachedDialogFields.length; i++) {
					fAttachedDialogFields[i].setEnabled(fIsSelected);
					if (fIsSelected && !focusSet) {
						focusSet= fAttachedDialogFields[i].setFocus();
					}
				}
			}
			dialogFieldChanged();
		} else if (fButtonStyle == SWT.PUSH) {
			dialogFieldChanged();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.internal.ui.wizards.dialogfields.DialogField#setLabelText(java.lang.String)
	 */
	@Override
	public void setLabelText(String labeltext) {
		fLabelText= labeltext;
		if (isOkToUse(fButton)) {
			fButton.setText(labeltext);
		}
	}


	// ------ model access

	/**
	 * Returns the selection state of the button.
	 */
	public boolean isSelected() {
		return fIsSelected;
	}

	/**
	 * Sets the selection state of the button.
	 */
	public void setSelection(boolean selected) {
		changeValue(selected);
		if (isOkToUse(fButton)) {
			fButton.setSelection(selected);
		}
	}

	/*
	 * @see DialogField#updateEnableState
	 */
	@Override
	protected void updateEnableState() {
		super.updateEnableState();
		if (isOkToUse(fButton)) {
			fButton.setEnabled(isEnabled());
		}
	}

	/*(non-Javadoc)
	 * @see org.eclipse.jdt.internal.ui.wizards.dialogfields.DialogField#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		if (isOkToUse(fButton)) {
			fButton.setSelection(fIsSelected);
		}
	}
}