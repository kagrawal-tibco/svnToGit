package com.tibco.cep.studio.ui.preferences.classpathvar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;


public class VariableStringDialogField extends VariableDialogField {

	private String fText;
	private Text fTextControl;
	private ModifyListener fModifyListener;

	public VariableStringDialogField() {
		super();
		fText= ""; //$NON-NLS-1$
	}

	/*
	 * @see DialogField#doFillIntoGrid
	 */
	@Override
	public Control[] doFillIntoGrid(Composite parent, int nColumns) {
		Label label= getLabelControl(parent);
		label.setLayoutData(gridDataForLabel(1));
		Text text= getTextControl(parent);
		text.setLayoutData(gridDataForText(nColumns - 1));

		return new Control[] { label, text };
	}

	/*
	 * @see DialogField#getNumberOfControls
	 */
	@Override
	public int getNumberOfControls() {
		return 2;
	}

	protected static GridData gridDataForText(int span) {
		GridData gd= new GridData();
		gd.horizontalAlignment= GridData.FILL;
		gd.grabExcessHorizontalSpace= false;
		gd.horizontalSpan= span;
		return gd;
	}

	// ------- focus methods

	/*
	 * @see DialogField#setFocus
	 */
	@Override
	public boolean setFocus() {
		if (isOkToUse(fTextControl)) {
			fTextControl.setFocus();
			fTextControl.setSelection(0, fTextControl.getText().length());
		}
		return true;
	}

	// ------- ui creation

	/**
	 * Creates or returns the created text control.
	 * @param parent The parent composite or <code>null</code> when the widget has
	 * already been created.
	 * @return the text control
	 */
	public Text getTextControl(Composite parent) {
		if (fTextControl == null) {
			fModifyListener= new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					doModifyText();
				}
			};

			fTextControl= createTextControl(parent);
			// moved up due to 1GEUNW2
			fTextControl.setText(fText);
			fTextControl.setFont(parent.getFont());
			fTextControl.addModifyListener(fModifyListener);

			fTextControl.setEnabled(isEnabled());
		}
		return fTextControl;
	}

	protected Text createTextControl(Composite parent) {
		return new Text(parent, SWT.SINGLE | SWT.BORDER);
	}

	private void doModifyText() {
		if (isOkToUse(fTextControl)) {
			fText= fTextControl.getText();
		}
		dialogFieldChanged();
	}

	/*
	 * @see DialogField#updateEnableState
	 */
	@Override
	protected void updateEnableState() {
		super.updateEnableState();
		if (isOkToUse(fTextControl)) {
			fTextControl.setEnabled(isEnabled());
		}
	}

	public String getText() {
		return fText;
	}

	public void setText(String text) {
		fText= text;
		if (isOkToUse(fTextControl)) {
			fTextControl.setText(text);
		} else {
			dialogFieldChanged();
		}
	}

	public void setTextWithoutUpdate(String text) {
		fText= text;
		if (isOkToUse(fTextControl)) {
			fTextControl.removeModifyListener(fModifyListener);
			fTextControl.setText(text);
			fTextControl.addModifyListener(fModifyListener);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.internal.ui.wizards.dialogfields.DialogField#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		if (isOkToUse(fTextControl)) {
			setTextWithoutUpdate(fText);
		}
	}

}