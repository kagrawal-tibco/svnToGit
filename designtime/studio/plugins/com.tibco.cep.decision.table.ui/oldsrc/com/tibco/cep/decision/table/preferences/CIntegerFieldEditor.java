package com.tibco.cep.decision.table.preferences;

import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;



/**
 * 
 * @author sasahoo
 *
 */
public class CIntegerFieldEditor extends IntegerFieldEditor {
	private static final int DEFAULT_TEXT_LIMIT = 10;
	public CIntegerFieldEditor(String name, String labelText, Composite parent) {
		super(name, labelText, parent, DEFAULT_TEXT_LIMIT);
	}

	/* (non-Javadoc)
	 * Method declared on FieldEditor.
	 */
	@Override
	protected void doLoad() {
		setPreferenceStore(DecisionTableUIPlugin.getDefault().getPreferenceStore());
		Text text = getTextControl();
		if (text != null) {
			int value = getPreferenceStore().getInt(getPreferenceName());
			text.setText("" + value);//$NON-NLS-1$
		}
	}

}
