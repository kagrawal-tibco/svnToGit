/**
 * 
 */
package com.tibco.cep.bpmn.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.cep.bpmn.ui.BpmnUIPlugin;

/**
 * @author pdhar
 *
 */
public class BpmnPreferenceInitializer extends
		AbstractPreferenceInitializer {

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore preferenceStore = BpmnUIPlugin.getDefault().getPreferenceStore();
		
		preferenceStore.setDefault(BpmnPreferenceConstants.PREF_SHOW_TASK_ICONS, true);
		preferenceStore.setDefault(BpmnPreferenceConstants.PREF_FILL_TASK_NODES, false);
		preferenceStore.setDefault(BpmnPreferenceConstants.PREF_EXPAND_SUBPROCESS, true);
		preferenceStore.setDefault(BpmnPreferenceConstants.PREF_DISPLAY_FULL_NAMES, true);
		preferenceStore.setDefault(BpmnPreferenceConstants.PREF_ALIGN_TO_LEFT_TRIGGERING_NODES, false);
		
		preferenceStore.setDefault(BpmnPreferenceConstants.PREF_SHOW_BREAKPOINTS, false);
		preferenceStore.setDefault(BpmnPreferenceConstants.PREF_SHOW_BREAKPOINTS_ON_MOUSEOVER, false);
		
		preferenceStore.setDefault(BpmnPreferenceConstants.PREF_SHOW_SEQUENCE_LABEL, false);
		
		preferenceStore.setDefault(BpmnPreferenceConstants.PREF_SHOW_IMAGE_PIXELS, BpmnPreferenceConstants.PREF_SHOW_IMAGE_PIXELS_16);
		
		preferenceStore.setDefault(BpmnPreferenceConstants.PREF_CHANGE_PALLETE_IMAGE_PIXELS, false);
	}

}
