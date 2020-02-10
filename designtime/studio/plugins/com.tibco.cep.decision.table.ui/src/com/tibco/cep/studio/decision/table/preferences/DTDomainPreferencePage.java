package com.tibco.cep.studio.decision.table.preferences;

import static com.tibco.cep.studio.decision.table.preferences.PreferenceConstants.DT_DOMAIN_IS_CELL_EDITABLE;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.studio.decision.table.ui.utils.Messages;

/**
 * This class is used to create the DTDomain page in Preferences.
 * 
 * @author yrajput
 * 
 */
public class DTDomainPreferencePage extends FieldEditorPreferencePage implements
IWorkbenchPreferencePage {

	public static final String Id = "com.tibco.cep.decision.table.dtdomain";

	private Button allowCustomvalueForDTDomain;
	
	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));	
		createDTDomainEntriesLimitedButton(composite);
		return composite;
	}

	/**
	 * Create the widget for the Rule viewing.
	 * 
	 * @param composite
	 */
	protected void createDTDomainEntriesLimitedButton(Composite composite) {
		allowCustomvalueForDTDomain = new Button(composite, SWT.CHECK);
		allowCustomvalueForDTDomain.setText(Messages.getString("DtDomain_isCellEditable"));
		allowCustomvalueForDTDomain.setToolTipText(Messages.getString("DtDomain_isCellEditable"));
		allowCustomvalueForDTDomain.setSelection(getPreferenceStore().getBoolean(
				PreferenceConstants.DT_DOMAIN_IS_CELL_EDITABLE));
	}

	
	public void init(IWorkbench workbench) {
		setPreferenceStore(DecisionTableUIPlugin.getDefault()
				.getPreferenceStore());
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		allowCustomvalueForDTDomain.setSelection(getPreferenceStore().getDefaultBoolean(
				DT_DOMAIN_IS_CELL_EDITABLE));

	}
	@Override
	public boolean performOk() {
		super.performOk();
		getPreferenceStore().setValue(DT_DOMAIN_IS_CELL_EDITABLE,
				allowCustomvalueForDTDomain.getSelection());

		return true;
	}

	@Override
	protected void createFieldEditors() {
		


	}

}