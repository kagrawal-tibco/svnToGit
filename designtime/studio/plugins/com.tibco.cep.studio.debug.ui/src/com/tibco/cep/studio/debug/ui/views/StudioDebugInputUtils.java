package com.tibco.cep.studio.debug.ui.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import com.tibco.cep.studio.debug.ui.StudioDebugUIPlugin;

public class StudioDebugInputUtils {
	
	private static final String DEBUG_INPUT_TAB_EXT_PT_ID = "debugInputTab";
	private static InputTab[] fInputTabs;

	/**
	 * @param view
	 * @return
	 */
	public synchronized static InputTab[] getDebugInputTabs(RuleDebugInputView inputView) {
		if (fInputTabs == null) {
			List<InputTab> inputTabs = new ArrayList<InputTab>();
			IConfigurationElement[] configurationElementsFor = Platform.getExtensionRegistry()
			.getConfigurationElementsFor(StudioDebugUIPlugin.PLUGIN_ID, DEBUG_INPUT_TAB_EXT_PT_ID);
			for (IConfigurationElement configurationElement : configurationElementsFor) {
				String attribute = configurationElement.getAttribute("Tab");
				if (attribute != null) {
					try {
						Object executableExtension = configurationElement.createExecutableExtension("Tab");
						if (executableExtension instanceof InputTab) {
							InputTab tab = (InputTab) executableExtension;
							tab.setInputView(inputView);
							inputTabs.add(tab);
						}
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
			inputTabs.add(new RuleInputTab(inputView));
			fInputTabs = new InputTab[inputTabs.size()];
			return inputTabs.toArray(fInputTabs);
		}
		return fInputTabs;
	}

}
