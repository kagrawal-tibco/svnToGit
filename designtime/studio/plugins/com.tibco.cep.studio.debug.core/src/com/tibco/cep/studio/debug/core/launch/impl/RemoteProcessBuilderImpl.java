package com.tibco.cep.studio.debug.core.launch.impl;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;

import com.tibco.cep.studio.debug.core.launch.RemoteProcessBuilder;
import com.tibco.cep.studio.debug.core.launch.RemoteProfile;

public class RemoteProcessBuilderImpl implements RemoteProcessBuilder {

	RemoteProfile profile;
	protected String hostName;
	protected String port;
	protected boolean isDebugSession = false;
	protected ILaunch launch;
	protected IProgressMonitor monitor;

	public RemoteProcessBuilderImpl(ILaunch launch,
			ILaunchConfiguration config, boolean isDebugSession,
			IProgressMonitor monitor) {
		this.launch = launch;
		if (monitor == null) {
			this.monitor = new NullProgressMonitor();
		} else {
			this.monitor = monitor;
		}
		this.profile = new RemoteProfileImpl(config, monitor);
	}

	public void init() throws CoreException {
		IProgressMonitor subMonitor = new SubProgressMonitor(getMonitor(), 1);
		subMonitor.beginTask("Initializing Process Builder...", 1);
		subMonitor.subTask("Initializing Remote Profile...");
		profile.init();
		setPort(profile.getPortNo());
		setHostName(profile.getHostName());
		subMonitor.worked(1);
	}
	
	

	/**
	 * @return the hostName
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * @param hostName
	 *            the hostName to set
	 */
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @return the profile
	 */
	public RemoteProfile getProfile() {
		return profile;
	}

	/**
	 * @param profile
	 *            the profile to set
	 */
	public void setProfile(RemoteProfile profile) {
		this.profile = profile;
	}

	/**
	 * @return the isDebugSession
	 */
	public boolean isDebugSession() {
		return isDebugSession;
	}

	/**
	 * @param isDebugSession
	 *            the isDebugSession to set
	 */
	public void setDebugSession(boolean isDebugSession) {
		this.isDebugSession = isDebugSession;
	}

	/**
	 * @return the launch
	 */
	public ILaunch getLaunch() {
		return launch;
	}

	/**
	 * @param launch
	 *            the launch to set
	 */
	public void setLaunch(ILaunch launch) {
		this.launch = launch;
	}

	/**
	 * @return the monitor
	 */
	public IProgressMonitor getMonitor() {
		return monitor;
	}

	/**
	 * @param monitor
	 *            the monitor to set
	 */
	public void setMonitor(IProgressMonitor monitor) {
		this.monitor = monitor;
	}

}
