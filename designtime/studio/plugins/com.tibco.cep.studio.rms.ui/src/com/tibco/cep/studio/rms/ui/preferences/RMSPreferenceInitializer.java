package com.tibco.cep.studio.rms.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.cep.studio.rms.ui.RMSUIPlugin;

/**
 * 
 * @author ggrigore
 *
 */
public class RMSPreferenceInitializer extends AbstractPreferenceInitializer {

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore msprefStore = RMSUIPlugin.getDefault().getPreferenceStore();
		msprefStore.setDefault(RMSPreferenceConstants.RMS_AUTH_URLS_SIZE, 1);
		msprefStore.setDefault(RMSPreferenceConstants.RMS_CHECKOUT_URLS_SIZE, 1);		
	}
}
