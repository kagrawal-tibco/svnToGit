package com.tibco.cep.studio.ui.preferences;

import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.preferences.classpathvar.VariableBlock;
import com.tibco.cep.studio.ui.util.Messages;

public class StudioClasspathVariablesPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	public static final String ID= "com.tibco.cep.studio.ui.page.classpathvars";

	public static final String DATA_SELECT_VARIABLE= "ClasspathVariablesPreferencePage.select_var"; //$NON-NLS-1$

	private VariableBlock fVariableBlock;
	private String fStoredSettings;

	/**
	 * Constructor for ClasspathVariablesPreferencePage
	 */
	public StudioClasspathVariablesPreferencePage() {
		setPreferenceStore(StudioUIPlugin.getDefault().getPreferenceStore());
		fVariableBlock= new VariableBlock(true, null);
		fStoredSettings= null;

		setTitle(Messages.getString("ClasspathVariablesPreferencePage_title"));
		setDescription(Messages.getString("ClasspathVariablesPreferencePage_description"));
		noDefaultAndApplyButton();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
	}

	/*
	 * @see PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {
		Control result= fVariableBlock.createContents(parent);
		Dialog.applyDialogFont(result);
		return result;
	}

	/*
	 * @see IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	/*
	 * @see PreferencePage#performDefaults()
	 */
	@Override
	protected void performDefaults() {
		super.performDefaults();
	}

	/*
	 * @see PreferencePage#performOk()
	 */
	@Override
	public boolean performOk() {
		return fVariableBlock.performOk();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean visible) {
		// check if the stored settings have changed
		if (visible) {
			if (fStoredSettings != null && !fStoredSettings.equals(getCurrentSettings())) {
				fVariableBlock.refresh(null);
			}
		} else {
			if (fVariableBlock.hasChanges()) {
				String title= Messages.getString("ClasspathVariablesPreferencePage_savechanges_title");
				String message= Messages.getString("ClasspathVariablesPreferencePage_savechanges_message");
				if (MessageDialog.openQuestion(getShell(), title, message)) {
					performOk();
				}
				fVariableBlock.setChanges(false); // forget
			}
			fStoredSettings= getCurrentSettings();
		}
		super.setVisible(visible);
	}

	private String getCurrentSettings() {
		StringBuffer buf= new StringBuffer();
		String[] names= StudioCore.getPathVariableNames();
		for (int i= 0; i < names.length; i++) {
			String curr= names[i];
			buf.append(curr).append('\0');
			IPath val= StudioCore.getClasspathVariable(curr);
			if (val != null) {
				buf.append(val.toString());
			}
			buf.append('\0');
		}
		return buf.toString();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#applyData(java.lang.Object)
	 */
	@Override
	public void applyData(Object data) {
		if (data instanceof Map && fVariableBlock != null) {
			Object id= ((Map<?, ?>) data).get(DATA_SELECT_VARIABLE);
			if (id instanceof String) {
				fVariableBlock.setSelection((String) id);
			}
		}
		super.applyData(data);
	}

	
}
