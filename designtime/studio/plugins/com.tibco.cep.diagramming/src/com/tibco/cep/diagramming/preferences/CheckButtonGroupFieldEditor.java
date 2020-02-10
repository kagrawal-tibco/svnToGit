package com.tibco.cep.diagramming.preferences;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

/**
 * 
 * @author sasahoo
 *
 */
public class CheckButtonGroupFieldEditor extends FieldEditor implements SelectionListener {

	/**
	 * List of check button entries of the form [label, value].
	 */
	private String[][] labelsAndValues;

	/**
	 * Number of columns into which to arrange the radio buttons.
	 */
	private int numColumns;

	/**
	 * Indent used for the first column of the radion button matrix.
	 */
	private int indent = HORIZONTAL_GAP;

	/**
	 * Whether to use a Group control.
	 */
	private boolean useGroup;

	/**
	 * The box of check buttons, or <code>null</code> if none (before creation
	 * and after disposal).
	 */
	private Composite checkBox;

	/**
	 * The check buttons, or <code>null</code> if none (before creation and
	 * after disposal).
	 */
	private Button[] checkFieldButtons;

	/**
	 * Creates a new radio group field editor
	 */
	protected CheckButtonGroupFieldEditor() {
	}

	/**
	 * @param name
	 * @param labelText
	 * @param numColumns
	 * @param labels
	 * @param parent
	 */
	public CheckButtonGroupFieldEditor(String name, 
									   String labelText, 
									   int numColumns, 
									   String[][] labels,
									   Composite parent) {
		this(name, labelText, numColumns, labels, parent, false);
	}

	/**
	 * @param name
	 * @param labelText
	 * @param numColumns
	 * @param labels
	 * @param parent
	 * @param useGroup
	 */
	public CheckButtonGroupFieldEditor(String name, 
			                           String labelText, 
			                           int numColumns, 
			                           String[][] labels,
			                           Composite parent, 
			                           boolean useGroup) {
		init(name, labelText);
		this.labelsAndValues = labels;
		this.numColumns = numColumns;
		this.useGroup = useGroup;
		createControl(parent);
	}

	/**
	 * adjust the field edtior for n-columns
	 */
	protected void adjustForNumColumns(int numColumns) {
		Control control = getLabelControl();
		if (control != null) {
			((GridData) control.getLayoutData()).horizontalSpan = numColumns;
		}
		((GridData) checkBox.getLayoutData()).horizontalSpan = numColumns;
	}

	/**
	 * Fill the components into the grid
	 */
	protected void doFillIntoGrid(Composite parent, int numColumns) {
		if (useGroup) {
			Control control = getControl(parent);
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			control.setLayoutData(gd);
		} else {
			Control control = getLabelControl(parent);
			GridData gd = new GridData();
			gd.horizontalSpan = numColumns;
			control.setLayoutData(gd);
			control = getControl(parent);
			gd = new GridData();
			gd.horizontalSpan = numColumns;
			gd.horizontalIndent = indent;
			control.setLayoutData(gd);
		}
	}

	/*
	 * (non-Javadoc) Method declared on FieldEditor.
	 */
	protected void doLoad() {
		for (int i = 0; i < checkFieldButtons.length; i++) {
			updateValue(getPreferenceStore().getBoolean(labelsAndValues[i][1]), i);
		}
	}

	/*
	 * (non-Javadoc) Method declared on FieldEditor.
	 */
	protected void doLoadDefault() {
		for (int i = 0; i < checkFieldButtons.length; i++) {
			updateValue(getPreferenceStore().getDefaultBoolean(labelsAndValues[i][1]), i);
		}
	}

	/*
	 * (non-Javadoc) Method declared on FieldEditor.
	 */
	protected void doStore() {
		for (int i = 0; i < checkFieldButtons.length; i++) {
			boolean checkState = ((Boolean) checkFieldButtons[i].getData()).booleanValue();
			getPreferenceStore().setValue(labelsAndValues[i][1], checkState);
		}
	}


	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditor#getNumberOfControls()
	 */
	public int getNumberOfControls() {
		return 1;
	}

	/**
	 * @param parent
	 * @return
	 */
	public Composite getControl(Composite parent) {
		if (checkBox == null) {
			Font font = parent.getFont();

			if (useGroup) {
				Group group = new Group(parent, SWT.NONE);
				group.setFont(font);
				String text = getLabelText();
				if (text != null) {
					group.setText(text);
				}
				checkBox = group;
				GridLayout layout = new GridLayout();
				layout.horizontalSpacing = HORIZONTAL_GAP;
				layout.numColumns = numColumns;
				checkBox.setLayout(layout);
			} else {
				checkBox = new Composite(parent, SWT.NONE);
				GridLayout layout = new GridLayout();
				layout.marginWidth = 0;
				layout.marginHeight = 0;
				layout.horizontalSpacing = HORIZONTAL_GAP;
				layout.numColumns = numColumns;
				checkBox.setLayout(layout);
				checkBox.setFont(font);
			}

			checkFieldButtons = new Button[labelsAndValues.length];
			for (int i = 0; i < labelsAndValues.length; i++) {
				Button check = new Button(checkBox, SWT.CHECK | SWT.LEFT);
				String[] labelAndValue = labelsAndValues[i];
				checkFieldButtons[i] = check;
				check.setText(labelAndValue[0]);
				check.setData(new Boolean(false));
				check.setSelection(false);
				check.setFont(font);
				check.addSelectionListener(this);
			}
			checkBox.addDisposeListener(new DisposeListener() {
				public void widgetDisposed(DisposeEvent event) {
					checkBox = null;
					checkFieldButtons = null;
				}
			});
		} else {
			checkParent(checkBox, parent);
		}
		return checkBox;
	}

	/**
	 * @param indent
	 */
	public void setIndent(int indent) {
		if (indent < 0) {
			this.indent = 0;
		} else {
			this.indent = indent;
		}
	}

	/**
	 * @param selectedValue
	 * @param i
	 */
	private void updateValue(boolean selectedValue, int i) {
		setPresentsDefaultValue(false);
		if (checkFieldButtons == null) {
			return;
		}
		Button check = checkFieldButtons[i];
		check.setData(new Boolean(selectedValue));
		check.setSelection(selectedValue);
	}

	/*
	 * @see FieldEditor.setEnabled(boolean,Composite).
	 */
	public void setEnabled(boolean enabled, Composite parent) {
		if (!useGroup) {
			super.setEnabled(enabled, parent);
		}
		for (int i = 0; i < checkFieldButtons.length; i++) {
			checkFieldButtons[i].setEnabled(enabled);
		}
	}


	/**
	 * @return
	 */
	public String[] getPreferenceStoringLocations() {
		String[] storingLocations = new String[checkFieldButtons.length];
		for (int i = 0; i < checkFieldButtons.length; i++) {
			storingLocations[i] = getPreferenceName() + ";" + labelsAndValues[i];
		}
		return storingLocations;
	}

	@Override
	public void widgetSelected(SelectionEvent event) {
		Boolean checkState = (Boolean) event.widget.getData();
		if (checkState.booleanValue()) {
			fireValueChanged(VALUE, checkState, Boolean.FALSE);
			event.widget.setData(new Boolean(false));
		} else {
			fireValueChanged(VALUE, checkState, Boolean.TRUE);
			event.widget.setData(new Boolean(true));
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
