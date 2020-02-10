package com.tibco.cep.diagramming.preferences;

import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.diagramming.utils.Messages;

/**
 * 
 * @author sasahoo
 *
 */
public class DoubleFieldEditor extends StringFieldEditor {
    private double minValidValue = 0.0;

    private double maxValidValue = Double.MAX_VALUE;

    private static final int DEFAULT_TEXT_LIMIT = 10;

    /**
     * Creates a new double field editor 
     */
    protected DoubleFieldEditor() {
    }

    /**
     * Creates an double field editor.
     * 
     * @param name the name of the preference this field editor works on
     * @param labelText the label text of the field editor
     * @param parent the parent of the field editor's control
     */
    public DoubleFieldEditor(String name, String labelText, Composite parent) {
        this(name, labelText, parent, DEFAULT_TEXT_LIMIT);
    }

    /**
     * Creates an double field editor.
     * 
     * @param name the name of the preference this field editor works on
     * @param labelText the label text of the field editor
     * @param parent the parent of the field editor's control
     * @param textLimit the maximum number of characters in the text.
     */
    public DoubleFieldEditor(String name, String labelText, Composite parent,
            int textLimit) {
        init(name, labelText);
        setTextLimit(textLimit);
        setEmptyStringAllowed(false);
        setErrorMessage(Messages.getString("DoubleFieldEditor.errorMessage"));
        createControl(parent);
    }

    /**
     * Sets the range of valid values for this field.
     * 
     * @param min the minimum allowed value (inclusive)
     * @param max the maximum allowed value (inclusive)
     */
    public void setValidRange(double min, double max) {
        minValidValue = min;
        maxValidValue = max;
        setErrorMessage(Messages.format(
        		"DoubleFieldEditor.errorMessageRange", new Object[] { new Double(min), new Double(max) }));
    }

    /* (non-Javadoc)
     * Method declared on StringFieldEditor.
     * Checks whether the entered String is a valid double or not.
     */
    protected boolean checkState() {

        Text text = getTextControl();

        if (text == null) {
			return false;
		}

        String numberString = text.getText();
        try {
            double number = Double.valueOf(numberString).doubleValue();
            if (number >= minValidValue && number <= maxValidValue) {
				clearErrorMessage();
				return true;
			}
            
			showErrorMessage();
			return false;
			
        } catch (NumberFormatException e1) {
            showErrorMessage();
        }

        return false;
    }

    /* (non-Javadoc)
     * Method declared on FieldEditor.
     */
    protected void doLoad() {
        Text text = getTextControl();
        if (text != null) {
            double value = getPreferenceStore().getDouble(getPreferenceName());
            text.setText("" + value);//$NON-NLS-1$
            oldValue = "" + value; //$NON-NLS-1$
        }

    }

    /* (non-Javadoc)
     * Method declared on FieldEditor.
     */
    protected void doLoadDefault() {
        Text text = getTextControl();
        if (text != null) {
            double value = getPreferenceStore().getDefaultDouble(getPreferenceName());
            text.setText("" + value);//$NON-NLS-1$
        }
        valueChanged();
    }

    /* (non-Javadoc)
     * Method declared on FieldEditor.
     */
    protected void doStore() {
        Text text = getTextControl();
        if (text != null) {
            Double i = new Double(text.getText());
            getPreferenceStore().setValue(getPreferenceName(), i.doubleValue());
        }
    }

    /**
     * Returns this field editor's current value as an double.
     *
     * @return the value
     * @exception NumberFormatException if the <code>String</code> does not
     *   contain a parsable double
     */
    public double getDoubleValue() throws NumberFormatException {
        return new Double(getStringValue()).doubleValue();
    }
}
