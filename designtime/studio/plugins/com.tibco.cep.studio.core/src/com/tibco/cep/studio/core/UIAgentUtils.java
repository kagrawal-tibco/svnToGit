package com.tibco.cep.studio.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

public class UIAgentUtils {

	private static final String BUILD_PARTICIPANT_EXT_PT_ID = "agentExtension";
	private static UIAgentExtension[] fBuildParticpants;

	public synchronized static UIAgentExtension[] getAgentExtensions() {
		if (fBuildParticpants == null) {
			List<UIAgentExtension> participants = new ArrayList<UIAgentExtension>();
			IConfigurationElement[] configurationElementsFor = Platform.getExtensionRegistry().getConfigurationElementsFor(StudioCorePlugin.PLUGIN_ID, BUILD_PARTICIPANT_EXT_PT_ID);
			for (IConfigurationElement configurationElement : configurationElementsFor) {
				String attribute = configurationElement.getAttribute("agentExtension");
				if (attribute != null) {
					try {
						Object executableExtension = configurationElement.createExecutableExtension("agentExtension");
						if (executableExtension instanceof UIAgentExtension) {
							participants.add((UIAgentExtension) executableExtension);
						}
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
			fBuildParticpants = new UIAgentExtension[participants.size()];
			return participants.toArray(fBuildParticpants);
		}
		return fBuildParticpants;
	}

	
}
