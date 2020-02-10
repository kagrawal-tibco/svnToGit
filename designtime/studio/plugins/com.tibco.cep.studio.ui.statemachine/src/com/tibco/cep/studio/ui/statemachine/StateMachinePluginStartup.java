package com.tibco.cep.studio.ui.statemachine;

import org.eclipse.ui.IStartup;

/**
 * 
 * @author sasahoo
 *
 */
public class StateMachinePluginStartup implements IStartup {

	static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IStartup#earlyStartup()
	 */
	@Override
	public void earlyStartup() {
		// TODO Auto-generated method stub
	}
}
