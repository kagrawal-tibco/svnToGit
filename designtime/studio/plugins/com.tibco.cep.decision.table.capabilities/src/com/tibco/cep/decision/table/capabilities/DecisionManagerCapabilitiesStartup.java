package com.tibco.cep.decision.table.capabilities;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.activities.IWorkbenchActivitySupport;

/*
@author ssailapp
@date Sep 27, 2009 3:22:16 PM
 */

public class DecisionManagerCapabilitiesStartup implements IStartup {

	static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
	
	
	private final static String DECISION_MANAGER_ENABLE_FLAG = "ACTIVATE_BUSINESS_USER"; 
	private final static String DECISION_MANAGER_ACTIVITY_ID = "com.tibco.cep.decision.table.activities";
	
	@Override
	public void earlyStartup() {
		String isEnabled = System.getProperty(DECISION_MANAGER_ENABLE_FLAG);
		if (isEnabled==null || !isEnabled.equals("true")) {
			enableDecisionManagerActivity();
			return;
		}
		
		System.out.println("Activating Business user capabilities...");

		// Close all open perspectives
		IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (workbenchWindow != null) {
			ActionFactory.IWorkbenchAction closePerspectiveAction = ActionFactory.CLOSE_ALL_PERSPECTIVES.create(workbenchWindow);
			if (closePerspectiveAction != null) {
				closePerspectiveAction.run();
			}
		}
		DecisionManagerWorkbenchAdvisor.disableActionSets();
		DecisionManagerWorkbenchAdvisor.removeWizards();
	}
	
	private void enableDecisionManagerActivity() {
		final IWorkbenchActivitySupport activitySupport = PlatformUI.getWorkbench().getActivitySupport();
		final IActivityManager activityManager = activitySupport.getActivityManager();
		
		Display.getDefault().asyncExec(new Runnable() {
		
		@SuppressWarnings("unchecked")
		@Override
			public void run() {
				@SuppressWarnings({ "rawtypes"})
				Set enabledActivities = new HashSet(activityManager.getEnabledActivityIds());
				if (activityManager.getActivity(DECISION_MANAGER_ACTIVITY_ID).isDefined()) {
					enabledActivities.add(DECISION_MANAGER_ACTIVITY_ID);
				}
				activitySupport.setEnabledActivityIds(enabledActivities);
			}
		});
	}

}
