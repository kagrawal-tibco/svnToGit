package com.tibco.cep.studio.debug.ui.launch;

import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.EnvironmentTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.debug.ui.sourcelookup.SourceLookupTab;

import com.tibco.cep.studio.debug.ui.launch.classpath.ClasspathTab;

/*
@author ssailapp
@date Jun 18, 2009 11:50:15 AM
*/

public class ApplicationTabGroup extends AbstractLaunchConfigurationTabGroup {
	

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		if (ILaunchManager.DEBUG_MODE.equalsIgnoreCase(mode)) {
			ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] {
					new MainTab(mode, false),new SourceLookupTab(),
					new ClasspathTab(), new EnvironmentTab(), new CommonTab()/*, new TesterTab() */};
			setTabs(tabs);
		} else if (ILaunchManager.RUN_MODE.equals(mode)) {
			ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] {
					new MainTab(mode, false), new SourceLookupTab(),
					new ClasspathTab(), new EnvironmentTab(), new CommonTab(),/* new TesterTab() */};
			setTabs(tabs);
		}
	}
	

}
