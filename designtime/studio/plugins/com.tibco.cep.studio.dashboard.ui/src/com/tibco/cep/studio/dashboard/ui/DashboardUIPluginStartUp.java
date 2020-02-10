package com.tibco.cep.studio.dashboard.ui;

import org.eclipse.ui.IStartup;

public class DashboardUIPluginStartUp implements IStartup {

	@Override
	public void earlyStartup() {
		//do nothing right now
		//we need this to force the invocation of selectionChanged(..) on all action delegates
	}

}
