package com.tibco.cep.studio.debug.ui.launch;

import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.debug.ui.sourcelookup.SourceLookupTab;

/*
@author ssailapp
@date Jun 18, 2009 4:32:46 PM
 */

public class RuleTabGroup extends AbstractLaunchConfigurationTabGroup {
	public static final String RULE_DEBUG_MODE = "com.tibco.cep.studio.debug.ui.launchMode.rule";
	public RuleTabGroup() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] {new MainTab(mode, false),
																		new RulesTab(),
																		new SourceLookupTab(),
																		new CommonTab()
																		};
		setTabs(tabs);
	}
	
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
//		try {
//			DebugUITools.setLaunchPerspective(configuration.getType(),ILaunchManager.RUN_MODE,DebugPerspective.ID);			
//			DebugUITools.setLaunchPerspective(configuration.getType(),ILaunchManager.DEBUG_MODE,DebugPerspective.ID);			
//		} catch (CoreException e) {
//			StudioDebugUIPlugin.log(e);
//		}		
		super.performApply(configuration);
	}

}
