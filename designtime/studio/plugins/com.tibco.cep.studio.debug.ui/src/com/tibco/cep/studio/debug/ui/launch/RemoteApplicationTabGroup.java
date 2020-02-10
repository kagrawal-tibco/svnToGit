package com.tibco.cep.studio.debug.ui.launch;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.debug.ui.sourcelookup.SourceLookupTab;

public class RemoteApplicationTabGroup extends
		AbstractLaunchConfigurationTabGroup {

	public RemoteApplicationTabGroup() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] {
				new MainTab(mode, true), new RemoteTab(),
				new SourceLookupTab(), new CommonTab() };
		setTabs(tabs);

	}

}
