package com.tibco.cep.studio.debug.core.launch;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.text.MessageFormat;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.IStatusHandler;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.debug.core.model.IProcess;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.Connector.Argument;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.ListeningConnector;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.launch.impl.DebugProcessBuilder;
import com.tibco.cep.studio.debug.core.launch.impl.DebugProcessBuilderImpl;
import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.debug.core.model.RuleDebugModel;

/*
@author ssailapp
@date Jun 18, 2009 11:47:39 AM
*/

public class ApplicationDelegate extends AbstractLaunchConfigurationDelegate implements ILaunchConfigurationDelegate,IDebugEventSetListener {
	
	//private String projectName, vmArgs, traFile, workingDir, earFile;
	protected String hostName;
	protected String port;
	//private boolean isAttach;

	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.debug.core.launch.AbstractLaunchConfigurationDelegate#launch(org.eclipse.debug.core.ILaunchConfiguration, java.lang.String, org.eclipse.debug.core.ILaunch, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {
		
		DefaultScope.INSTANCE.getNode(StudioDebugCorePlugin.PLUGIN_ID).putInt(ApplicationRuntime.PREF_CONNECT_TIMEOUT, ApplicationRuntime.DEF_CONNECT_TIMEOUT);
		
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
			DebugProcessBuilder debugProcessBuilder = getJVMProcessBuilder(launch,mode,configuration,monitor);
			launchLocal(debugProcessBuilder,configuration, launch, monitor);
		}
		finally {
			monitor.done();
		}
	}
	
	private void launchLocal(DebugProcessBuilder debugProcessBuilder, ILaunchConfiguration configuration,
			ILaunch launch, IProgressMonitor monitor) throws CoreException,
			DebugException {
		// done the verification phase
		monitor.worked(1);
		
		monitor.subTask("Initializing JVM process...");
		// check for cancellation
		if (monitor.isCanceled()) {
			return;
		}	
		debugProcessBuilder.init();
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
		//final String cmdLine = debugProcessBuilder.getCommandLineString();
		
		monitor.subTask("Constructing command line...");
		String[] cmdArray = debugProcessBuilder.getCommand();
		monitor.worked(1);
		final String port = debugProcessBuilder.getPort();
		final String hostName = debugProcessBuilder.getHostName();
		final String entryPoint = debugProcessBuilder.getProfile().getEntryPoint();
		if(Integer.valueOf(port) == -1) {
			abort("Could not find a free socket for the debugger", null,-1);
		}
		
		// check for cancellation
		if (monitor.isCanceled()) {
			return;
		}
		
		monitor.subTask("Starting JVM Process...");
		
		ListeningConnector connector = getListeningConnector();
		if (connector == null) {
			abort("Couldn't find an appropriate debug connector", null, -1); 
		}
		Map<String, Argument> map = connector.defaultArguments();
		specifyArguments(map, hostName,Integer.valueOf(port));
		String[] envp = DebugPlugin.getDefault().getLaunchManager().getEnvironment(configuration);
//		ArrayList<String> envs = new ArrayList<String>();
//		for(Entry<String, String> env:System.getenv().entrySet()) {
//			envs.add(env.getKey()+"="+env.getValue());
//		}
//		String[] envp = envs.toArray(new String[envs.size()]);
		
		Process p= null;
		try {
			try {
				// check for cancellation
				if (monitor.isCanceled()) {
					return;
				}				
				
				connector.startListening(map);
				
				File workingDir = new File(debugProcessBuilder.getProfile().getWorkingDir());
				p = exec(cmdArray, workingDir,envp);
				if (p == null) {
					return;
				}
				
				// check for cancellation
				if (monitor.isCanceled()) {
					p.destroy();
					return;
				}				
				
				IProcess lProcess= newProcess(launch, p, renderProcessLabel(entryPoint,hostName,port), getDefaultProcessMap(debugProcessBuilder.getProfile()));
				String renderString = renderCommandLine(cmdArray);
				lProcess.setAttribute(IProcess.ATTR_CMDLINE, renderString);
				StudioDebugCorePlugin.debug(renderString);
				monitor.worked(1);
				monitor.subTask("Establishing debug connection..."); 
				boolean retry= false;
				do  {
					try {
						
						ConnectRunnable runnable = new ConnectRunnable(connector, map);
						Thread connectThread = new Thread(runnable, "Listening Connector"); //$NON-NLS-1$
		                connectThread.setDaemon(true);
						connectThread.start();
						while (connectThread.isAlive()) {
							if (monitor.isCanceled()) {
		                        try {
		                            connector.stopListening(map);
		                        } catch (IOException ioe) {
		                            //expected
		                        }
								p.destroy();
								return;
							}
							try {
								p.exitValue();
								// process has terminated - stop waiting for a connection
								try {
									connector.stopListening(map); 
								} catch (IOException e) {
									// expected
								}
								checkErrorMessage(lProcess);
							} catch (IllegalThreadStateException e) {
								// expected while process is alive
							}
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
							}
						}

						Exception ex = runnable.getException();
						if (ex instanceof IllegalConnectorArgumentsException) {
							throw (IllegalConnectorArgumentsException)ex;
						}
						if (ex instanceof InterruptedIOException) {
							throw (InterruptedIOException)ex;
						}
						if (ex instanceof IOException) {
							throw (IOException)ex;
						}
						
						VirtualMachine vm= runnable.getVirtualMachine();
						if (vm != null) {
							IDebugTarget target = RuleDebugModel
									.newDebugTarget(launch, 
											renderDebugTarget(debugProcessBuilder.getProfile().getType().getEntryPoint(), port),
											lProcess, hostName, port, vm, false, true, true);
							((IRuleRunTarget)target).init();
							((IRuleRunTarget)target).start();
//								createDebugTarget(launch, port, lProcess, vm);
							monitor.worked(1);
							monitor.done();
						}
						return;
					} catch (InterruptedIOException e) {
						checkErrorMessage(lProcess);
						
						// timeout, consult status handler if there is one
						IStatus status = new Status(IStatus.ERROR, StudioDebugCorePlugin.PLUGIN_ID,-1, "Debug connection timed out", e);
						IStatusHandler handler = DebugPlugin.getDefault().getStatusHandler(status);
						
						retry= false;
						if (handler == null) {
							// if there is no handler, throw the exception
							throw new CoreException(status);
						} 
						Object result = handler.handleStatus(status, this);
						if (result instanceof Boolean) {
							retry = ((Boolean)result).booleanValue();
						}
					}
				} while (retry);
			} finally {
				connector.stopListening(map);
			}
		} catch (IOException e) {
			abort("Cannot connect to VM", e,-1);  
		} catch (IllegalConnectorArgumentsException e) {
			abort("Cannot connect to VM", e, -1);  
		} 
		if (p != null) {
			p.destroy();
		}			
		

	}


	/**
	 * @param launch
	 * @param mode
	 * @param configuration
	 * @param monitor
	 * @return
	 * @throws CoreException 
	 */
	protected DebugProcessBuilder getJVMProcessBuilder(ILaunch launch, String mode, ILaunchConfiguration configuration, IProgressMonitor monitor) throws CoreException {
		DebugProcessBuilder rte = null;
		if (mode.equals(ILaunchManager.DEBUG_MODE)) {
			rte = new DebugProcessBuilderImpl(launch, configuration, true,
					monitor);
		} else if (mode.equals(ILaunchManager.RUN_MODE)) {
			rte = new DebugProcessBuilderImpl(launch, configuration, false,
					monitor);
		}
		return rte;
	}
	
	/**
	 * Allows arguments to be specified
	 * @param map
	 * @param portNumber
	 */
	protected void specifyArguments(Map<String, Argument> map, String hostName,int portNumber) {
		// XXX: Revisit - allows us to put a quote (") around the classpath
		Connector.IntegerArgument port= (Connector.IntegerArgument) map.get("port");
		port.setValue(portNumber);
//		Connector.StringArgument host = (Connector.StringArgument)map.get("localAddress");
//		host.setValue(hostName);
		
		Connector.IntegerArgument timeoutArg= (Connector.IntegerArgument) map.get("timeout");
		if (timeoutArg != null) {
			int timeout = ApplicationRuntime.getPreferences().getInt(ApplicationRuntime.PREF_CONNECT_TIMEOUT,RuleDebugModel.DEF_REQUEST_TIMEOUT);//milliseconds
			timeoutArg.setValue(timeout);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.debug.core.IDebugEventSetListener#handleDebugEvents(org.eclipse.debug.core.DebugEvent[])
	 */
	public void handleDebugEvents(DebugEvent[] events) {
		for (int i = 0; i < events.length; i++) {
			DebugEvent event = events[i];
			Object eventSource = event.getSource();
			switch(event.getKind()) {
				
				// Delete the HTML file used for the launch
				case DebugEvent.TERMINATE :
					if (eventSource != null) {
						ILaunch launch = null;
						if (eventSource instanceof IProcess) {
							IProcess process = (IProcess) eventSource;
							launch = process.getLaunch();
						} else if (eventSource instanceof IDebugTarget) {
							IDebugTarget debugTarget = (IDebugTarget) eventSource;
							launch = debugTarget.getLaunch();
						}
						if (launch != null) {
							cleanup(launch);
						}
					}
					break;
			}
		}
	}

	private void cleanup(ILaunch launch) {
		DebugPlugin.getDefault().removeDebugEventListener(this);
	}
	
	
}
