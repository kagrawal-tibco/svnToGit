/**
 * 
 */
package com.tibco.cep.security;

import org.eclipse.ui.IStartup;

/**
 * @author aathalye
 *
 */
public class SecurityPluginStartup implements IStartup {

	static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IStartup#earlyStartup()
	 */
	@Override
	public void earlyStartup() {
		//Load any existing ACL managers
		/*LoadACLManagersJob loadACLManagersJob = new LoadACLManagersJob("Loading Access Config files");
		loadACLManagersJob.setPriority(Job.SHORT);
		loadACLManagersJob.setUser(true);
		loadACLManagersJob.schedule();*/
	}
}
