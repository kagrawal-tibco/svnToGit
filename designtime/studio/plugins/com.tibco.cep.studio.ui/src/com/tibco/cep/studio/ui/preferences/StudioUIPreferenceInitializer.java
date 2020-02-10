package com.tibco.cep.studio.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.PropertyChangeEvent;

import com.tibco.cep.studio.core.preferences.StudioCorePreferenceConstants;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.util.packaging.impl.DefaultRuntimeClassesPackager;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.compare.preferences.ComparePreferenceConstants;

/**
 * 
 * @author sasahoo
 *
 */
public class StudioUIPreferenceInitializer extends
		AbstractPreferenceInitializer {

	public StudioUIPreferenceInitializer() {
	}

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore prefStore = StudioUIPlugin.getDefault().getPreferenceStore();
		
		//Tester preferences
//		prefStore.setDefault(StudioUIPreferenceConstants.AUTO_DEPLOY_BEFORE_TEST, true);
		prefStore.setDefault(StudioUIPreferenceConstants.TEST_DATA_INPUT_PATH, "/TestData");
		prefStore.setDefault(StudioUIPreferenceConstants.TEST_DATA_OUTPUT_PATH, "/TestData");
		prefStore.setDefault(StudioUIPreferenceConstants.WM_OBJCECTS_SHOW_MAX_NO, 50);
		prefStore.setDefault(StudioUIPreferenceConstants.AUTO_SCROLL_RESULT_TABLE, true);
		
		// General
		prefStore.setDefault(StudioUIPreferenceConstants.CATALOG_FUNCTION_SHOW_TOOLTIPS, true);
		prefStore.setDefault(StudioUIPreferenceConstants.OPEN_CONFIRM_PERSPECTIVE_CHANGE, false);
		prefStore.setDefault(StudioUIPreferenceConstants.MIGRATE_OLD_PROJECTS_HIDE, false);
		prefStore.setDefault(StudioUIPreferenceConstants.SWITCH_PERSPECTIVE_ON_EDITOR_ACTIVATION, true);
//		prefStore.setDefault(StudioUIPreferenceConstants.SWITCH_PERSPECTIVE_ON_EDITOR_ACTIVATION_HIDE, true);
		
		prefStore.setDefault(StudioUIPreferenceConstants.STUDIO_SHOW_WARNINGS, true);
		
		String value = CommonUtil.getUpdatedValues(CommonUtil.getAllExtensions());
		prefStore.setDefault(StudioUIPreferenceConstants.STUDIO_SHOW_WARNINGS_IGNORE_PATTERNS, value);

		prefStore.setDefault(ComparePreferenceConstants.COMPARE_MERGE_AUTOMERGE_COLUMNS,true);
		prefStore.setDefault(ComparePreferenceConstants.COMPARE_ADDED_COLOR,"228,228,241");
		prefStore.setDefault(ComparePreferenceConstants.COMPARE_REMOVED_COLOR,"254,218,235");
		prefStore.setDefault(ComparePreferenceConstants.COMPARE_CHANGED_COLOR,"255,255,255");
		prefStore.setDefault(ComparePreferenceConstants.COMPARE_SINGLE_LINE_PROP,false);
		prefStore.setDefault(ComparePreferenceConstants.COMPARE_OPEN_STRUCTURE_PROP,true);
		prefStore.setDefault(ComparePreferenceConstants.COMPARE_OPEN_TEXT_PROP,true);
		prefStore.setDefault(ComparePreferenceConstants.COMPARE_SYNC_SCROLL_PROP,true);
		prefStore.setDefault(ComparePreferenceConstants.COMPARE_HIGHLIGHT_CHANGES_PROP,true);
		prefStore.setDefault(ComparePreferenceConstants.COMPARE_IGNORE_WHITESPACE_PROP,true);
//		prefStore.setDefault(PreferenceConstants.COMPARE_SAVE_EDITORS_PROP,true);
		prefStore.setDefault(ComparePreferenceConstants.PREF_NAVIGATION_END_ACTION,ComparePreferenceConstants.PREF_VALUE_PROMPT);
		prefStore.setDefault(ComparePreferenceConstants.COMPARE_SHOW_INFO_PROP,true);
		
		//Codegen
		
		prefStore.setDefault(StudioCorePreferenceConstants.PREF_CODEGEN_COMPILATION_MODE,DefaultRuntimeClassesPackager.IN_MEMORY_COMPILER);
		prefStore.setDefault(StudioCorePreferenceConstants.PREF_CODEGEN_SOURCE_JAVA_VERSION,1.8);
		prefStore.setDefault(StudioCorePreferenceConstants.PREF_CODEGEN_TARGET_JAVA_VERSION,1.8);
		
		//ear overwrite
		prefStore.setDefault(StudioUIPreferenceConstants.STUDIO_OVERWRITE_EAR, false);

		prefStore.setDefault(StudioUIPreferenceConstants.STUDIO_PERSIST_ENTITIES_AS_SOURCE, false);
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