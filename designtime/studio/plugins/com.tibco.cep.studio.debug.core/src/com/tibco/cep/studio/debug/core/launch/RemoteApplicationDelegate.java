package com.tibco.cep.studio.debug.core.launch;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.TransportTimeoutException;
import com.tibco.cep.studio.debug.core.launch.impl.RemoteProcessBuilderImpl;
import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.debug.core.model.RuleDebugModel;

public class RemoteApplicationDelegate extends ApplicationDelegate implements ILaunchConfigurationDelegate {

	@Override
	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		monitor.beginTask(MessageFormat.format("{0}...", (Object[])new String[]{configuration.getName()}), 3);
		// check for cancellation
		if (monitor.isCanceled()) {
			return;
		}
		try {
			monitor.subTask("Verifying launch attributes..."); 
			
			RemoteProcessBuilder processBuilder = new RemoteProcessBuilderImpl(launch, configuration, true , monitor);
			launchRemote(processBuilder,configuration, launch, monitor);
			
			
		}
		finally {
			monitor.done();
		}
	}
	
	private void launchRemote(RemoteProcessBuilder remoteProcessBuilder,
			ILaunchConfiguration configuration, ILaunch launch,
			IProgressMonitor monitor) throws CoreException {
		
		// done the verification phase
		monitor.worked(1);
		
		monitor.subTask("Initializing launch configuration...");
		// check for cancellation
		if (monitor.isCanceled()) {
			return;
		}	
		remoteProcessBuilder.init();
		// check for cancellation
		if (monitor.isCanceled()) {
			return;
		}	
		monitor.worked(1);			
		monitor.subTask("Creating source locator...");
		// set the default source locator if required
		setDefaultSourceLocator(launch, configuration);
		
		monitor.worked(1);
		// Launch the configuration - 1 unit of work
		DebugPlugin.getDefault().addDebugEventListener(this);

				// check for cancellation
		if (monitor.isCanceled()) {
			return;
		}
		
		
		// connect to remote VM
		connect(remoteProcessBuilder, monitor, launch);
		
		// check for cancellation
		if (monitor.isCanceled()) {
			IDebugTarget[] debugTargets = launch.getDebugTargets();
            for (int i = 0; i < debugTargets.length; i++) {
                IDebugTarget target = debugTargets[i];
                if (target.canDisconnect()) {
                    target.disconnect();
                }
            }
            return;
		}
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void connect(RemoteProcessBuilder remoteProcessBuilder,IProgressMonitor monitor, ILaunch launch) throws CoreException {
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		
		IProgressMonitor subMonitor = new SubProgressMonitor(monitor, 1);
		subMonitor.beginTask("Connecting...", 2); 
		subMonitor.subTask("Configuring connection..."); 
		
		AttachingConnector connector= getAttachingConnector();
		if (connector == null) {
			abort("Couldn't find an appropriate debug connector", null, -1); 
		}
		
		if (connector == null) {
			abort("Connector not specified", null,-1); 
		}
		
		Map argMap = connector.defaultArguments();
        
		Connector.StringArgument hostnameArg = (Connector.StringArgument) argMap.get("hostname");
		hostName = remoteProcessBuilder.getHostName();
		if (hostName == null) {
			abort("Hostname unspecified for remote connection.", null,-1); 
		}
		hostnameArg.setValue(hostName);

		Connector.IntegerArgument portArg = (Connector.IntegerArgument) argMap.get("port");
		port = remoteProcessBuilder.getPort(); 
		if (port == null) {
			abort("Port unspecified for remote connection.", null,-1); 
		}
		portArg.setValue(port);
		
		Connector.IntegerArgument timeoutArg= (Connector.IntegerArgument) argMap.get("timeout");
		if (timeoutArg != null) {
			int timeout = ApplicationRuntime.getPreferences().getInt(ApplicationRuntime.PREF_CONNECT_TIMEOUT,RuleDebugModel.DEF_REQUEST_TIMEOUT);
			timeoutArg.setValue(timeout);
		}
        
		ILaunchConfiguration configuration = launch.getLaunchConfiguration();
		boolean allowTerminate = false;
		if (configuration != null) {
			allowTerminate = configuration.getAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_ALLOW_TERMINATE, false);
		}
		subMonitor.worked(1);
		subMonitor.subTask("Establishing connection..."); 
		try {
			VirtualMachine vm = connector.attach(argMap);
			String vmLabel = constructVMLabel(vm, hostName, port, configuration);
//			IDebugTarget debugTarget= JDIDebugModel.newDebugTarget(launch, vm, vmLabel, null, allowTerminate, true);
			IDebugTarget debugTarget= RuleDebugModel.newDebugTarget(launch,vmLabel,null,hostName,port,vm, true, allowTerminate, true);
			launch.addDebugTarget(debugTarget);
			((IRuleRunTarget)debugTarget).init();
			((IRuleRunTarget)debugTarget).start();
			subMonitor.worked(1);
			subMonitor.done();
        } catch (TransportTimeoutException e) {
            abort("Failed to connect to remote VM. Connection timed out.", e,-1);
		} catch (UnknownHostException e) {
			abort(MessageFormat.format("Failed to connect to remote VM because of unknown host \"{0}\"",(Object[]) new String[]{hostName}), e, -1); 
		} catch (ConnectException e) {
			abort("Failed to connect to remote VM. Connection refused.", e, -1); 
		} catch (IOException e) {
			abort("Failed to connect to remote VM", e,-1); 
		} catch (IllegalConnectorArgumentsException e) {
			abort("Failed to connect to remote VM", e,-1); 
		}
	}

}
