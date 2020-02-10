package com.tibco.cep.studio.core.validation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import com.tibco.cep.studio.common.validation.utils.CommonValidationUtils;
import com.tibco.cep.studio.core.StudioCorePlugin;

public class MapperValidationUtils extends CommonValidationUtils {
	
	protected static final String FIX_PROVIDER 	= "quickFixProvider";
	protected static final String ATTR_PROVIDER = "provider";

	private static IQuickFixProvider[] fQuickFixProviders;

	public static synchronized IQuickFixProvider[] getFixProviders() {
		if (fQuickFixProviders == null) {
			List<IQuickFixProvider> providers = new ArrayList<IQuickFixProvider>();
			IConfigurationElement[] configurationElementsFor = Platform.getExtensionRegistry().getConfigurationElementsFor(StudioCorePlugin.PLUGIN_ID, FIX_PROVIDER);
			for (IConfigurationElement configurationElement : configurationElementsFor) {
				String attribute = configurationElement.getAttribute(ATTR_PROVIDER);
				IQuickFixProvider validator = null;
				if (attribute != null) {
					try {
						Object executableExtension = configurationElement.createExecutableExtension(ATTR_PROVIDER);
						if (executableExtension instanceof IQuickFixProvider) {
							validator = (IQuickFixProvider) executableExtension;
						}
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
				
				providers.add(validator);
			}
			fQuickFixProviders = new IQuickFixProvider[providers.size()];
			return providers.toArray(fQuickFixProviders);
		}
		return fQuickFixProviders;
	}
}
