package com.tibco.cep.studio.debug.core.launch.impl;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.debug.core.ILaunchConfiguration;

import com.tibco.cep.studio.debug.core.launch.IStudioDebugLaunchConfigurationConstants;
import com.tibco.cep.studio.debug.core.launch.RemoteProfile;
import com.tibco.cep.studio.debug.core.launch.RunProfile;

public class RemoteProfileImpl implements RemoteProfile {
	
	protected String portNo;
	protected String hostName;
	protected ILaunchConfiguration launchConfiguration;
	protected IProgressMonitor monitor;
	

	public RemoteProfileImpl(ILaunchConfiguration config,
			IProgressMonitor monitor) {
		setLaunchConfiguration(config);
		if(monitor == null) {
			this.monitor = new NullProgressMonitor();
		} else {
			this.monitor = monitor;
		}
	}
	
	


	/**
	 * @return the portNo
	 */
	public String getPortNo() {
		return portNo;
	}



	/**
	 * @param portNo the portNo to set
	 */
	public void setPortNo(String portNo) {
		this.portNo = portNo;
	}



	/**
	 * @return the hostName
	 */
	public String getHostName() {
		return hostName;
	}



	/**
	 * @param hostName the hostName to set
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}



	/**
	 * @return the launchConfiguration
	 */
	public ILaunchConfiguration getLaunchConfiguration() {
		return launchConfiguration;
	}



	/**
	 * @param launchConfiguration the launchConfiguration to set
	 */
	public void setLaunchConfiguration(ILaunchConfiguration launchConfiguration) {
		this.launchConfiguration = launchConfiguration;
	}



	public void init() throws CoreException {
    	IProgressMonitor subMonitor = new SubProgressMonitor(monitor, 1);
		subMonitor.beginTask("Initializing LaunchConfiguration...",1); 
		// check for cancellation
		if (monitor.isCanceled()) {
			return;
		}	
    	setHostName(getLaunchConfiguration().getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_HOST,RunProfile.DEFAULT_DEBUG_HOST));
    	setPortNo(getLaunchConfiguration().getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_PORT,RunProfile.DEFAULT_DEBUG_PORT));
    	subMonitor.worked(1);		
    }

}
