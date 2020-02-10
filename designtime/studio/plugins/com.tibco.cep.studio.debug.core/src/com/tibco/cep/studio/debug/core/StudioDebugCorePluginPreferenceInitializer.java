package com.tibco.cep.studio.debug.core;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

import com.tibco.cep.studio.debug.core.model.RuleBreakpoint;
import com.tibco.cep.studio.debug.core.model.RuleDebugModel;

public class StudioDebugCorePluginPreferenceInitializer extends
		AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		Preferences prefs = StudioDebugCorePlugin.getDefault().getPluginPreferences();
		prefs.setDefault(RuleDebugModel.PREF_REQUEST_TIMEOUT, RuleDebugModel.DEF_REQUEST_TIMEOUT);
		prefs.setDefault(RuleDebugModel.PREF_HCR_WITH_COMPILATION_ERRORS, true);
		prefs.setDefault(RuleDebugModel.PREF_SUSPEND_FOR_BREAKPOINTS_DURING_EVALUATION, true);
		prefs.setDefault(StudioDebugCorePlugin.PREF_DEFAULT_BREAKPOINT_SUSPEND_POLICY, RuleBreakpoint.SUSPEND_THREAD);
		//0 is the first index, meaning both access and modification
		prefs.setDefault(StudioDebugCorePlugin.PREF_DEFAULT_WATCHPOINT_SUSPEND_POLICY, 0);
		prefs.setDefault(StudioDebugCorePlugin.PREF_SHOW_REFERENCES_IN_VAR_VIEW, false);
		prefs.setDefault(StudioDebugCorePlugin.PREF_ALL_REFERENCES_MAX_COUNT, 100);
		prefs.setDefault(StudioDebugCorePlugin.PREF_ALL_INSTANCES_MAX_COUNT, 100);
		prefs.addPropertyChangeListener(StudioDebugCorePlugin.getDefault());

	}

}
