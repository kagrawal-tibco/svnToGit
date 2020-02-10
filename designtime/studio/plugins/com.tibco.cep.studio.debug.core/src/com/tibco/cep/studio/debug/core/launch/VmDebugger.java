package com.tibco.cep.studio.debug.core.launch;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.IStatusHandler;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamsProxy;

import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.ListeningConnector;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.model.RuleDebugModel;
@SuppressWarnings({"rawtypes","unchecked"})
public class VmDebugger extends VmRunner {
	
	/**
	 * @since 3.3 OSX environment variable specifying JRE to use
	 */
	protected static final String JAVA_JVM_VERSION = "JAVA_JVM_VERSION"; //$NON-NLS-1$
	
	/**
	 * Jre path segment descriptor
	 * 
	 * String equals the word: <code>jre</code>
	 * 
	 * @since 3.3.1
	 */
	protected static final String JRE = "jre"; //$NON-NLS-1$

	/**
	 * Bin path segment descriptor
	 * 
	 * String equals the word: <code>bin</code>
	 * 
	 * @since 3.3.1
	 */
	protected static final String BIN = "bin"; //$NON-NLS-1$
	
	/**
	 * Used to attach to a VM in a separate thread, to allow for cancellation
	 * and detect that the associated System process died before the connect
	 * occurred.
	 */
	class ConnectRunnable implements Runnable {
		
		private VirtualMachine fVirtualMachine = null;
		private ListeningConnector fConnector = null;
		private Map fConnectionMap = null;
		private Exception fException = null;
		
		/**
		 * Constructs a runnable to connect to a VM via the given connector
		 * with the given connection arguments.
		 * 
		 * @param connector
		 * @param map
		 */
		public ConnectRunnable(ListeningConnector connector, Map map) {
			fConnector = connector;
			fConnectionMap = map;
		}
		
		public void run() {
			try {
				fVirtualMachine = fConnector.accept(fConnectionMap);
			} catch (IOException e) {
				fException = e;
			} catch (IllegalConnectorArgumentsException e) {
				fException = e;
			}
		}
		
		/**
		 * Returns the VM that was attached to, or <code>null</code> if none.
		 * 
		 * @return the VM that was attached to, or <code>null</code> if none
		 */
		public VirtualMachine getVirtualMachine() {
			return fVirtualMachine;
		}
		
		/**
		 * Returns any exception that occurred while attaching, or <code>null</code>.
		 * 
		 * @return IOException or IllegalConnectorArgumentsException
		 */
		public Exception getException() {
			return fException;
		}
	}

	/**
	 * Creates a new launcher
	 */
	public VmDebugger(VmInstall vmInstance) {
		super(vmInstance);
	}
	
	/**
	 * Returns a free port number on localhost, or -1 if unable to find a free port.
	 * 
	 * @return a free port number on localhost, or -1 if unable to find a free port
	 * @since 3.0
	 */
	public static int findFreePort() {
		ServerSocket socket= null;
		try {
			socket= new ServerSocket(0);
			return socket.getLocalPort();
		} catch (IOException e) { 
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
				}
			}
		}
		return -1;		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.launching.IVMRunner#run(org.eclipse.jdt.launching.VmRunnerConfig, org.eclipse.debug.core.ILaunch, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void run(VmRunnerConfig config, ILaunch launch, IProgressMonitor monitor) throws CoreException {

		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		
		IProgressMonitor subMonitor = new SubProgressMonitor(monitor, 1);
		subMonitor.beginTask("Launching VM...", 4); 
		subMonitor.subTask("Finding free socket..."); 

		int port= findFreePort();
		if (port == -1) {
			abort("Cannot find a free socket for the debugger", null,-1); 
		}
		
		subMonitor.worked(1);
		
		// check for cancellation
		if (monitor.isCanceled()) {
			return;
		}		
		
		subMonitor.subTask("Constructing command line..."); 
				
		String program= constructProgramString(config);
		
		List arguments= new ArrayList(12);

		arguments.add(program);
		
		if (fVMInstance instanceof VmInstall && ((VmInstall)fVMInstance).getDebugArgs() != null){
			String debugArgString = ((VmInstall)fVMInstance).getDebugArgs().replaceAll("\\Q" + VmInstall.VAR_PORT + "\\E", Integer.toString(port));  //$NON-NLS-1$ //$NON-NLS-2$
			String[] debugArgs = DebugPlugin.parseArguments(debugArgString);
			for (int i = 0; i < debugArgs.length; i++) {
				arguments.add(debugArgs[i]);
			}
		} else {
			// VM arguments are the first thing after the java program so that users can specify
			// options like '-client' & '-server' which are required to be the first options
			double version = getJavaVersion();
			if (version < 1.5) {
				arguments.add("-Xdebug"); //$NON-NLS-1$
				arguments.add("-Xnoagent"); //$NON-NLS-1$
			}
			
			//check if java 1.4 or greater
			if (version < 1.4) {
				arguments.add("-Djava.compiler=NONE"); //$NON-NLS-1$
			}
			if (version < 1.5) { 
				arguments.add("-Xrunjdwp:transport=dt_socket,suspend=y,address=localhost:" + port); //$NON-NLS-1$
			} else {
				arguments.add("-agentlib:jdwp=transport=dt_socket,suspend=y,address=localhost:" + port); //$NON-NLS-1$
			}
		
		}
		
		String[] allVMArgs = combineVmArgs(config, fVMInstance);
		addArguments(ensureEncoding(launch, allVMArgs), arguments);
		addBootClassPathArguments(arguments, config);
		
		String[] cp= config.getClassPath();
		if (cp.length > 0) {
			arguments.add("-classpath"); //$NON-NLS-1$
			arguments.add(convertClassPath(cp));
		}
		
		
		
		arguments.add(config.getClassToLaunch());
		addArguments(config.getProgramArguments(), arguments);
		String[] cmdLine= new String[arguments.size()];
		arguments.toArray(cmdLine);
		
		//With the newer VMs and no backwards compatibility we have to always prepend the current env path (only the runtime one)
		//with a 'corrected' path that points to the location to load the debug dlls from, this location is of the standard JDK installation 
		//format: <jdk path>/jre/bin
		String[] envp = prependJREPath(config.getEnvironment(), new Path(program));
		
		// check for cancellation
		if (monitor.isCanceled()) {
			return;
		}		
		
		subMonitor.worked(1);
		subMonitor.subTask("Starting virtual machine..."); 

		ListeningConnector connector= getConnector();
		if (connector == null) {
			abort("Cannot find an appropriate debug connector", null, -1); 
		}
		Map map= connector.defaultArguments();
		
		specifyArguments(map, port);
		Process p= null;
		try {
			try {
				// check for cancellation
				if (monitor.isCanceled()) {
					return;
				}				
				
				connector.startListening(map);
				
				File workingDir = getWorkingDir(config);
				p = exec(cmdLine, workingDir, envp);				
				if (p == null) {
					return;
				}
				
				// check for cancellation
				if (monitor.isCanceled()) {
					p.destroy();
					return;
				}				
				
				IProcess process= newProcess(launch, p, renderProcessLabel(cmdLine), getDefaultProcessMap());
				process.setAttribute(IProcess.ATTR_CMDLINE, renderCommandLine(cmdLine));
				subMonitor.worked(1);
				subMonitor.subTask("Establishing debug connection..."); 
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
								checkErrorMessage(process);
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
							createDebugTarget(config, launch, port, process, vm);
							subMonitor.worked(1);
							subMonitor.done();
						}
						return;
					} catch (InterruptedIOException e) {
						checkErrorMessage(process);
						
						// timeout, consult status handler if there is one
						IStatus status = new Status(IStatus.ERROR, StudioDebugCorePlugin.getUniqueIdentifier(), -1, "", e); //$NON-NLS-1$
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
			abort("Cannot connect to VM", e, -1);  
		} catch (IllegalConnectorArgumentsException e) {
			abort("Cannot connect to VM", e, -1);  
		}
		if (p != null) {
			p.destroy();
		}
	}

	/**
	 * This method performs platform specific operations to modify the runtime path for JREs prior to launching.
	 * Nothing is written back to the original system path.
	 * 
	 * <p>
	 * For Windows:
	 * Prepends the location of the JRE bin directory for the given JDK path to the PATH variable in Windows.
	 * This method assumes that the JRE is located within the JDK install directory
	 * in: <code><JDK install dir>/jre/bin/</code> where the JRE itself would be located 
	 * in: <code><JDK install dir>/bin/</code>  where the JDK itself is located
	 * </p>
	 * <p>
	 * For Mac OS:
	 * Searches for and sets the correct state of the JAVA_VM_VERSION environment variable to ensure it matches
	 * the currently chosen VM of the launch config
	 * </p>
	 * 
	 * @param env the current array of environment variables to run with
	 * @param jdkpath the path to the executable (javaw).
	 * @since 3.3
	 */
	protected String[] prependJREPath(String[] env, IPath jdkpath) {
		if(Platform.OS_WIN32.equals(Platform.getOS())) {
			IPath jrepath = jdkpath.removeLastSegments(1);
			if(jrepath.lastSegment().equals(BIN)) {
				if(!jrepath.segment(jrepath.segmentCount()-2).equals(JRE)) {
					jrepath = jrepath.removeLastSegments(1).append(JRE).append(BIN);
				}
			}
			else {
				jrepath = jrepath.append(JRE).append(BIN);
			}
			if(jrepath.toFile().exists()) {
				String jrestr = jrepath.toOSString();
				if(env == null){
					Map map = DebugPlugin.getDefault().getLaunchManager().getNativeEnvironment();
					env = new String[map.size()];
					String var = null;
					int index = 0;
					for(Iterator iter = map.keySet().iterator(); iter.hasNext();) {
						var = (String) iter.next();
						String value = (String) map.get(var);
						if (value == null) {
							value = ""; //$NON-NLS-1$
						}
						if (var.equalsIgnoreCase("path")) { //$NON-NLS-1$
							if(value.indexOf(jrestr) == -1) {
								value = jrestr+';'+value;
							}
						}
						env[index] = var+"="+value; //$NON-NLS-1$
						index++;
					}
				} else {
					String var = null;
					int esign = -1;
					for(int i = 0; i < env.length; i++) {
						esign = env[i].indexOf('=');
						if(esign > -1) {
							var = env[i].substring(0, esign);
							if(var != null && var.equalsIgnoreCase("path")) { //$NON-NLS-1$
								if(env[i].indexOf(jrestr) == -1) {
									env[i] = var + "="+jrestr+';'+(esign == env.length ? "" : env[i].substring(esign+1)); //$NON-NLS-1$ //$NON-NLS-2$
									break;
								}
							}
						}
					}
				}
			}
		} 
		return super.prependJREPath(env);
	}
	
	/**
	 * Creates a new debug target for the given virtual machine and system process
	 * that is connected on the specified port for the given launch.
	 * 
	 * @param config run configuration used to launch the VM
	 * @param launch launch to add the target to
	 * @param port port the VM is connected to
	 * @param process associated system process
	 * @param vm JDI virtual machine
	 */
	protected IDebugTarget createDebugTarget(VmRunnerConfig config, ILaunch launch, int port, IProcess process, VirtualMachine vm) {
//		return RuleDebugModel.newDebugTarget(launch, vm, renderDebugTarget(config.getClassToLaunch(), port), process, true, false, config.isResumeOnStartup());
		return RuleDebugModel.newDebugTarget(launch,renderDebugTarget(config.getClassToLaunch(), port),process,null,null,vm, false, true, true);
	}
	
	/**
	 * Returns the version of the current VM in use
	 * @return the VM version
	 */
	private double getJavaVersion() {
		String version = null;
		if (fVMInstance instanceof VmInstall) {
			version = ((VmInstall)fVMInstance).getJavaVersion();
		} else {
			LibraryInfo libInfo = StudioDebugCorePlugin.getLibraryInfo(fVMInstance.getInstallLocation().getAbsolutePath());
			if (libInfo == null) {
			    return 0D;
			}
			version = libInfo.getVersion();
		}
		if (version == null) {
			// unknown version
			return 0D;
		}
		int index = version.indexOf("."); //$NON-NLS-1$
		int nextIndex = version.indexOf(".", index+1); //$NON-NLS-1$
		try {
			if (index > 0 && nextIndex>index) {
				return Double.parseDouble(version.substring(0,nextIndex));
			} 
			return Double.parseDouble(version);
		} catch (NumberFormatException e) {
			return 0D;
		}

	}

	/**
	 * Checks and forwards an error from the specified process
	 * @param process
	 * @throws CoreException
	 */
	protected void checkErrorMessage(IProcess process) throws CoreException {
		IStreamsProxy streamsProxy = process.getStreamsProxy();
		if (streamsProxy != null) {
			String errorMessage= streamsProxy.getErrorStreamMonitor().getContents();
			if (errorMessage.length() == 0) {
				errorMessage= streamsProxy.getOutputStreamMonitor().getContents();
			}
			if (errorMessage.length() != 0) {
				abort(errorMessage, null, -1);
			}
		}										
	}
		
	/**
	 * Allows arguments to be specified
	 * @param map
	 * @param portNumber
	 */
	protected void specifyArguments(Map map, int portNumber) {
		// XXX: Revisit - allows us to put a quote (") around the classpath
		Connector.IntegerArgument port= (Connector.IntegerArgument) map.get("port"); //$NON-NLS-1$
		port.setValue(portNumber);
		
		Connector.IntegerArgument timeoutArg= (Connector.IntegerArgument) map.get("timeout"); //$NON-NLS-1$
//		if (timeoutArg != null) {
//			int timeout = JavaRuntime.getPreferences().getInt(JavaRuntime.PREF_CONNECT_TIMEOUT);
//		}
		timeoutArg.setValue(3000);
	}

	/**
	 * Returns the default 'com.sun.jdi.SocketListen' connector
	 * @return
	 */
	protected ListeningConnector getConnector() {
		List connectors= com.sun.jdi.Bootstrap.virtualMachineManager().listeningConnectors();
		for (int i= 0; i < connectors.size(); i++) {
			ListeningConnector c= (ListeningConnector) connectors.get(i);
			if ("com.sun.jdi.SocketListen".equals(c.name())) //$NON-NLS-1$
				return c;
		}
		return null;
	}

}
