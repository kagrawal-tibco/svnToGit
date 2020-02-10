package com.tibco.cep.studio.cluster.topology.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.cep.studio.cluster.topology.ClusterTopologyPlugin;

/**
 * 
 * @author ggrigore
 *
 */
public class ClusterTopologyPreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		// TODO Auto-generated method stub
		IPreferenceStore preferenceStore = ClusterTopologyPlugin.getDefault().getPreferenceStore();
		preferenceStore.setDefault(PreferenceConstants.GRID, PreferenceConstants.LINE);
	}

}
