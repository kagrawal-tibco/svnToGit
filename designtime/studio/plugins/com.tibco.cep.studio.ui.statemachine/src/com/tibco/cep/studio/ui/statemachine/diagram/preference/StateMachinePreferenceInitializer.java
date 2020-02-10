package com.tibco.cep.studio.ui.statemachine.diagram.preference;

import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.cep.studio.ui.preferences.StudioPreferenceConstants;
import com.tibco.cep.studio.ui.preferences.StudioPreferenceInitializer;
import com.tibco.cep.studio.ui.statemachine.StateMachinePlugin;

/**
 * 
 * @author sasahoo
 *
 */
public class StateMachinePreferenceInitializer extends StudioPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		
	IPreferenceStore prefStore = StateMachinePlugin.getDefault().getPreferenceStore();
	prefStore.setDefault(StudioPreferenceConstants.STATEMACHINE_GRID, StudioPreferenceConstants.STATEMACHINE_LINES);
	//prefStore.setDefault(StudioPreferenceConstants.STATEMACHINE_LINK_TYPE, StudioPreferenceConstants.STATEMACHINE_LINK_TYPE_CURVED);
	prefStore.setDefault(StudioPreferenceConstants.STATEMACHINE_FIX_LABELS, true);
	
	}
}
