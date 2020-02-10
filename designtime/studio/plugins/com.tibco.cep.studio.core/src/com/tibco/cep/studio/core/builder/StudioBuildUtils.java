package com.tibco.cep.studio.core.builder;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import com.tibco.cep.studio.core.StudioCorePlugin;

public class StudioBuildUtils {

	private static final String BUILD_PARTICIPANT_EXT_PT_ID = "buildParticipant";
	private static IStudioBuildParticipant[] fBuildParticpants;

	public synchronized static IStudioBuildParticipant[] getBuildParticipants() {
		if (fBuildParticpants == null) {
			List<IStudioBuildParticipant> participants = new ArrayList<IStudioBuildParticipant>();
			IConfigurationElement[] configurationElementsFor = Platform.getExtensionRegistry().getConfigurationElementsFor(StudioCorePlugin.PLUGIN_ID, BUILD_PARTICIPANT_EXT_PT_ID);
			for (IConfigurationElement configurationElement : configurationElementsFor) {
				String attribute = configurationElement.getAttribute("Participant");
				if (attribute != null) {
					try {
						Object executableExtension = configurationElement.createExecutableExtension("Participant");
						if (executableExtension instanceof IStudioBuildParticipant) {
							participants.add((IStudioBuildParticipant) executableExtension);
						}
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
			fBuildParticpants = new IStudioBuildParticipant[participants.size()];
			return participants.toArray(fBuildParticpants);
		}
		return fBuildParticpants;
	}

}
