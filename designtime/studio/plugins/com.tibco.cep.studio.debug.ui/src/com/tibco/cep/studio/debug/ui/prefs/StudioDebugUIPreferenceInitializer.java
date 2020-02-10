package com.tibco.cep.studio.debug.ui.prefs;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.PropertyChangeEvent;

import com.tibco.cep.studio.debug.ui.IStudioDebugUIConstants;
import com.tibco.cep.studio.debug.ui.StudioDebugUIPlugin;

public class StudioDebugUIPreferenceInitializer extends
		AbstractPreferenceInitializer {

	public StudioDebugUIPreferenceInitializer() {
	}

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = StudioDebugUIPlugin.getDefault().getPreferenceStore();
		store.setDefault(IStudioDebugPreferencesConstants.PREF_SUSPEND_ON_COMPILATION_ERRORS, true);
		store.setDefault(IStudioDebugPreferencesConstants.PREF_SUSPEND_ON_UNCAUGHT_EXCEPTIONS, true);
		store.setDefault(IStudioDebugPreferencesConstants.PREF_ALERT_HCR_FAILED, true);
		store.setDefault(IStudioDebugPreferencesConstants.PREF_ALERT_HCR_NOT_SUPPORTED, true);
		store.setDefault(IStudioDebugPreferencesConstants.PREF_ALERT_OBSOLETE_METHODS, true);
		store.setDefault(IStudioDebugPreferencesConstants.PREF_ALERT_UNABLE_TO_INSTALL_BREAKPOINT, true);

		store.setDefault(IStudioDebugPreferencesConstants.PREF_SHOW_QUALIFIED_NAMES, false);
		
		// JavaStepFilterPreferencePage
		store.setDefault(IStudioDebugPreferencesConstants.PREF_ACTIVE_FILTERS_LIST, "java.lang.ClassLoader"); //$NON-NLS-1$
		store.setDefault(IStudioDebugPreferencesConstants.PREF_INACTIVE_FILTERS_LIST, "com.ibm.*,com.sun.*,java.*,javax.*,jrockit.*,org.omg.*,sun.*,sunw.*"); //$NON-NLS-1$
		store.setDefault(IStudioDebugPreferencesConstants.PREF_STEP_THRU_FILTERS, true);
		
		store.setDefault(IDebugUIConstants.ID_VARIABLE_VIEW + "." + IStudioDebugPreferencesConstants.PREF_SHOW_CONSTANTS, false); //$NON-NLS-1$
		store.setDefault(IDebugUIConstants.ID_EXPRESSION_VIEW + "." + IStudioDebugPreferencesConstants.PREF_SHOW_CONSTANTS, false); //$NON-NLS-1$
		store.setDefault(IDebugUIConstants.ID_VARIABLE_VIEW + "." + IStudioDebugPreferencesConstants.PREF_SHOW_STATIC_VARIABLES, false); //$NON-NLS-1$
		store.setDefault(IDebugUIConstants.ID_EXPRESSION_VIEW + "." + IStudioDebugPreferencesConstants.PREF_SHOW_STATIC_VARIABLES, false); //$NON-NLS-1$

		store.setDefault(IStudioDebugPreferencesConstants.PREF_SHOW_CHAR, false);
		store.setDefault(IStudioDebugPreferencesConstants.PREF_SHOW_HEX, false);
		store.setDefault(IStudioDebugPreferencesConstants.PREF_SHOW_UNSIGNED, false);
		store.setDefault(IStudioDebugPreferencesConstants.PREF_SHOW_NULL_ARRAY_ENTRIES, true);
		store.setDefault(IStudioDebugPreferencesConstants.PREF_SHOW_DETAILS, IStudioDebugPreferencesConstants.DETAIL_PANE);
		
		store.setDefault(IStudioDebugUIConstants.PREF_SHOW_SYSTEM_THREADS, false);
		store.setDefault(IStudioDebugUIConstants.PREF_SHOW_MONITOR_THREAD_INFO, false);
		store.setDefault(IStudioDebugUIConstants.PREF_SHOW_THREAD_GROUPS, false);
		store.setDefault(IStudioDebugPreferencesConstants.PREF_OPEN_INSPECT_POPUP_ON_EXCEPTION, false);
		store.setDefault(IStudioDebugUIConstants.PREF_ALLINSTANCES_MAX_COUNT, 100);
		store.setDefault(IStudioDebugUIConstants.PREF_ALLREFERENCES_MAX_COUNT, 100);
	}
	
	/**
	 * Returns the boolean value from the given property change event.
	 * 
	 * @param event property change event
	 * @return new boolean value from the event
	 */
	public static boolean getBoolean(PropertyChangeEvent event) {
		Object newValue = event.getNewValue();
		if (newValue instanceof String) {
			return ((IPreferenceStore)event.getSource()).getBoolean(event.getProperty());
		} else {
			return ((Boolean)newValue).booleanValue();
		}		
	}

}
