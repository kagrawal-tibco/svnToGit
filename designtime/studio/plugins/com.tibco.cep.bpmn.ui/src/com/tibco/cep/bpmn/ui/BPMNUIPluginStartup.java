package com.tibco.cep.bpmn.ui;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.activities.IWorkbenchActivitySupport;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;

/**
 * 
 * @author sasahoo
 *
 */
public class BPMNUIPluginStartup implements IStartup {
	
	static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
	
	
	private final static String PROCESS_ADDON_ACTIVITY_ID = "com.tibco.cep.bpmn.activities";

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IStartup#earlyStartup()
	 */
	@SuppressWarnings("unchecked")
	public void earlyStartup() {
		final IWorkbenchActivitySupport activitySupport = PlatformUI.getWorkbench().getActivitySupport();
		final IActivityManager activityManager = activitySupport.getActivityManager();
		Display.getDefault().asyncExec(new Runnable(){
			/* (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				BpmnCorePlugin.getDefault();
				Set<String> enabledActivities = new HashSet<String>(activityManager.getEnabledActivityIds());
				if (activityManager.getActivity(PROCESS_ADDON_ACTIVITY_ID).isDefined()) {
					enabledActivities.add(PROCESS_ADDON_ACTIVITY_ID);
				}
				activitySupport.setEnabledActivityIds(enabledActivities);
			}});
	}
}
