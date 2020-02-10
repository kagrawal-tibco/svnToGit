package com.tibco.cep.studio.application;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.activities.IWorkbenchActivitySupport;

public class StudioApplicationPluginStartup implements IStartup {
	
	static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
	
	
	private final static String STUDIO_STANDARD_ACTIVITY_ID = "com.tibco.cep.standard.development";
	private final static String STUDIO_WST_ACTIVITY_ID = "com.tibco.cep.studio.application.wst.activity";

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
			@SuppressWarnings("rawtypes")
			@Override
			public void run() {
				Set enabledActivities = new HashSet(activityManager.getEnabledActivityIds());
				if (activityManager.getActivity(STUDIO_STANDARD_ACTIVITY_ID).isDefined()) {
					enabledActivities.add(STUDIO_STANDARD_ACTIVITY_ID);
				}
				if (activityManager.getActivity(STUDIO_WST_ACTIVITY_ID).isDefined()) {
					enabledActivities.add(STUDIO_WST_ACTIVITY_ID);
				}
				activitySupport.setEnabledActivityIds(enabledActivities);
			}});
	}
}
