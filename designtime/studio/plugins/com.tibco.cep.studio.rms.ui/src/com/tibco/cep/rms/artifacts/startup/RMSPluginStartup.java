/**
 * 
 */
package com.tibco.cep.rms.artifacts.startup;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.IStartup;

import com.tibco.cep.studio.rms.artifacts.manager.impl.RMSArtifactsSummaryManager;
import com.tibco.cep.studio.rms.core.LoadACLManagersJob;
import com.tibco.cep.studio.rms.preferences.util.RMSPreferenceUtils;

/**
 * @author aathalye
 *
 */
public class RMSPluginStartup implements IStartup {
	
	static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IStartup#earlyStartup()
	 */
	@Override
	public void earlyStartup() {
		//Load/create
		RMSArtifactsSummaryManager.getInstance();
		//Initialize properties
//		StudioConfig.init();
		//Read any preferences file if present
		RMSPreferenceUtils.loadRMSPreferences();
		
		//Handling RMS resource actions
		RMSResourceUpdateManager.getInstance();
		
		//Load any existing ACLManagers
		LoadACLManagersJob loadACLManagersJob = new LoadACLManagersJob("Loading Access Config files");
		loadACLManagersJob.setPriority(Job.SHORT);
		loadACLManagersJob.setUser(true);
		loadACLManagersJob.schedule();
	}
}
