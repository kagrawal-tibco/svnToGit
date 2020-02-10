package com.tibco.cep.studio.ui.preferences;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.osgi.service.prefs.BackingStoreException;

import com.tibco.be.parser.codegen.CodeGenConstants;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.preferences.StudioCorePreferenceConstants;
import com.tibco.cep.studio.core.util.packaging.impl.DefaultRuntimeClassesPackager;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.Messages;

/*
@author ssailapp
@date Jun 9, 2011
 */

public class CodegenPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private ComboFieldEditor compilationModeFieldEditor;
	private ComboFieldEditor sourceJavaVersionFieldEditor;
	private ComboFieldEditor targetJavaVersionFieldEditor;
	
	public CodegenPreferencePage() {
		super(GRID);
		setPreferenceStore(StudioUIPlugin.getDefault().getPreferenceStore());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
		if(compilationModeFieldEditor != null){
			compilationModeFieldEditor.load();
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#dispose()
	 */
	public void dispose() {
		super.dispose();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	@Override
	protected void createFieldEditors() {
		Composite parent = getFieldEditorParent();
		parent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));

		String[][] compilationModes = new String[][]{ {"File System", DefaultRuntimeClassesPackager.DEFAULT_COMPILER}, {"In Memory", DefaultRuntimeClassesPackager.IN_MEMORY_COMPILER}};
		compilationModeFieldEditor = new ComboFieldEditor(StudioCorePreferenceConstants.PREF_CODEGEN_COMPILATION_MODE, Messages.getString("pref.codegen.compilation.mode"), compilationModes, parent);
		addField(compilationModeFieldEditor);

		String[][] javaVersions = new String[][]{ {"1.5", "1.5"}, {"1.6", "1.6"},{"1.7","1.7"},{"1.8","1.8"}};
		sourceJavaVersionFieldEditor = new ComboFieldEditor(StudioCorePreferenceConstants.PREF_CODEGEN_SOURCE_JAVA_VERSION, Messages.getString("pref.codegen.source.java.version"), javaVersions, parent) {
		};
		addField(sourceJavaVersionFieldEditor);
		
		targetJavaVersionFieldEditor = new ComboFieldEditor(StudioCorePreferenceConstants.PREF_CODEGEN_TARGET_JAVA_VERSION, Messages.getString("pref.codegen.target.java.version"), javaVersions, parent) {
		};
		
		addField(targetJavaVersionFieldEditor);
	}
	

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#propertyChange(org.eclipse.jface.util.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		String prefName = null;
		if (event.getSource() == compilationModeFieldEditor) {
			prefName = StudioCorePreferenceConstants.PREF_CODEGEN_COMPILATION_MODE;
		} else if (event.getSource() == sourceJavaVersionFieldEditor) {
			prefName = StudioCorePreferenceConstants.PREF_CODEGEN_SOURCE_JAVA_VERSION;
		} else if (event.getSource() == targetJavaVersionFieldEditor) {
			prefName = StudioCorePreferenceConstants.PREF_CODEGEN_TARGET_JAVA_VERSION;
		}
		
		if (prefName != null) {
			String value = (String) event.getNewValue();
			IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(StudioCorePlugin.PLUGIN_ID);
			prefs.put(prefName, value);
			checkState();
			if (!isValid()) {
				String oldValue = (String) event.getOldValue();
				prefs.put(prefName, oldValue);
			}
			try {
				prefs.flush();
			} catch (BackingStoreException e) {
				StudioUIPlugin.log(e);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#checkState()
	 */
	@Override
	protected void checkState() {
		super.checkState();
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(StudioCorePlugin.PLUGIN_ID);
		String sourceJavaVersionString = prefs.get(StudioCorePreferenceConstants.PREF_CODEGEN_SOURCE_JAVA_VERSION,CodeGenConstants.JAVA_TARGET_VERSION);
		String targetJavaVersionString = prefs.get(StudioCorePreferenceConstants.PREF_CODEGEN_TARGET_JAVA_VERSION,CodeGenConstants.JAVA_TARGET_VERSION);
		try { 
			float sourceJavaVersion = Float.valueOf(sourceJavaVersionString);
			float targetJavaVersion = Float.valueOf(targetJavaVersionString);
			if (sourceJavaVersion > targetJavaVersion) {
				setErrorMessage("Source Java Version cannot be higher than the Target Java Version.");
				setValid(false);
			} else {
				setErrorMessage(null);
				setValid(true);
			}
		} catch (NumberFormatException nfe) {
			// We should never get here.
			setValid(true);
		}
	}
	
	@Override
	protected void performDefaults() {
		// TODO Auto-generated method stub
		super.performDefaults();
		String compilationMode =  getPreferenceStore().getDefaultString(StudioCorePreferenceConstants.PREF_CODEGEN_COMPILATION_MODE);
		String sourceJavaVersionString = getPreferenceStore().getDefaultString(StudioCorePreferenceConstants.PREF_CODEGEN_SOURCE_JAVA_VERSION);
		String targetJavaVersionString = getPreferenceStore().getDefaultString(StudioCorePreferenceConstants.PREF_CODEGEN_TARGET_JAVA_VERSION);
		IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(StudioCorePlugin.PLUGIN_ID);
		prefs.put(StudioCorePreferenceConstants.PREF_CODEGEN_COMPILATION_MODE, compilationMode);
		prefs.put(StudioCorePreferenceConstants.PREF_CODEGEN_SOURCE_JAVA_VERSION, sourceJavaVersionString);
		prefs.put(StudioCorePreferenceConstants.PREF_CODEGEN_TARGET_JAVA_VERSION, targetJavaVersionString);
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			StudioUIPlugin.log(e);
		}
	}
	
	
}
