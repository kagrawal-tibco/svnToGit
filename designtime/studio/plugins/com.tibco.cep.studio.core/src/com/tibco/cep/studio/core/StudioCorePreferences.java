package com.tibco.cep.studio.core;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

import com.tibco.be.parser.codegen.CodeGenConstants;
import com.tibco.cep.studio.core.preferences.StudioCorePreferenceConstants;
import com.tibco.cep.studio.core.util.packaging.impl.DefaultRuntimeClassesPackager;

public class StudioCorePreferences extends AbstractPreferenceInitializer {

	public StudioCorePreferences() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initializeDefaultPreferences() {
//		Preferences prefs = StudioCorePlugin.getDefault().getPluginPreferences();
		IEclipsePreferences prefs = DefaultScope.INSTANCE.getNode(StudioCorePlugin.PLUGIN_ID);
		prefs.put(StudioCorePreferenceConstants.PREF_CODEGEN_COMPILATION_MODE, DefaultRuntimeClassesPackager.IN_MEMORY_COMPILER);
		prefs.put(StudioCorePreferenceConstants.PREF_CODEGEN_SOURCE_JAVA_VERSION, CodeGenConstants.JAVA_TARGET_VERSION);
		prefs.put(StudioCorePreferenceConstants.PREF_CODEGEN_TARGET_JAVA_VERSION, CodeGenConstants.JAVA_TARGET_VERSION);
	}

}
