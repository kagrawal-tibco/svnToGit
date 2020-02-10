package com.tibco.cep.studio.core.index.resolution;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import com.tibco.cep.studio.core.StudioCorePlugin;

public class ResolutionUtils {

	private static final String RESOLUTION_PROVIDER_EXT_PT_ID = "elementResolutionProvider";
	private static IElementResolutionProvider[] fElementResolutionProviders;

	public synchronized static IElementResolutionProvider[] getElementResolutionProviders() {
		if (fElementResolutionProviders == null) {
			List<IElementResolutionProvider> participants = new ArrayList<IElementResolutionProvider>();
			IConfigurationElement[] configurationElementsFor = Platform.getExtensionRegistry().getConfigurationElementsFor(StudioCorePlugin.PLUGIN_ID, RESOLUTION_PROVIDER_EXT_PT_ID);
			for (IConfigurationElement configurationElement : configurationElementsFor) {
				String attribute = configurationElement.getAttribute("elementResolutionProvider");
				if (attribute != null) {
					try {
						Object executableExtension = configurationElement.createExecutableExtension("elementResolutionProvider");
						if (executableExtension instanceof IElementResolutionProvider) {
							participants.add((IElementResolutionProvider) executableExtension);
						}
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
			fElementResolutionProviders = new IElementResolutionProvider[participants.size()];
			return participants.toArray(fElementResolutionProviders);
		}
		return fElementResolutionProviders;
	}

}
