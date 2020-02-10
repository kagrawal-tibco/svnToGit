package com.tibco.cep.studio.rms.ui.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.cep.studio.rms.ui.RMSUIPlugin;
import com.tibco.cep.studio.rms.ui.utils.Messages;


/**
 * 
 * @author ggrigore
 *
 */
public class RMSPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

	public RMSPreferencePage(){
		setPreferenceStore(RMSUIPlugin.getDefault().getPreferenceStore());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	@Override
	protected void createFieldEditors() {
		Composite parent = getFieldEditorParent();
		addField(new IntegerFieldEditor(
				RMSPreferenceConstants.RMS_AUTH_URLS_SIZE,
				Messages.getString("auth.url.size"), parent));
		addField(new IntegerFieldEditor(
				RMSPreferenceConstants.RMS_CHECKOUT_URLS_SIZE,
				Messages.getString("checkout.url.size"), parent));
	}
	
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
	}
}