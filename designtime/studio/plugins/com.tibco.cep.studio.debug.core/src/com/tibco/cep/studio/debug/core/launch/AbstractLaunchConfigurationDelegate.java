package com.tibco.cep.studio.debug.core.launch;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamsProxy;
import org.eclipse.debug.core.sourcelookup.ISourceLookupDirector;
import org.eclipse.jdi.Bootstrap;
/*
import com.sun.jdi.Bootstrap;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.ListeningConnector;
*/
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.connect.ListeningConnector;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;

public class AbstractLaunchConfigurationDelegate implements
		ILaunchConfigurationDelegate {

	@Override
	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Convenience method to get the launch manager.
	 * 
	 * @return the launch manager
	 */
	protected ILaunchManager getLaunchManager() {
		return DebugPlugin.getDefault().getLaunchManager();
	}
	/**
	 * Throws a core exception with an error status object built from the given
	 * message, lower level exception, and error code.
	 * 
	 * @param message
	 *            the status message
	 * @param exception
	 *            lower level exception associated with the error, or
	 *            <code>null</code> if none
	 * @param code
	 *            error code
	 * @throws CoreException
	 *             the "abort" core exception
	 */
	protected void abort(String message, Throwable exception, int code)
			throws CoreException {
		throw new CoreException((IStatus) new Status(IStatus.ERROR, StudioDebugCorePlugin
				.PLUGIN_ID, code, message, exception));
	}
	
	/**
	 * @see DebugPlugin#exec(String[], File)
	 */
	protected Process exec(String[] cmdLine, File workingDirectory) throws CoreException {
		return DebugPlugin.exec(cmdLine, workingDirectory);
	}
	
	/**
	 * @see DebugPlugin#exec(String[], File, String[])
	 */
	protected Process exec(String[] cmdLine, File workingDirectory, String[] envp) throws CoreException {
		return DebugPlugin.exec(cmdLine, workingDirectory, envp);
	}
	
	/**
	 * 
	 * @param launch
	 * @param p
	 * @param label
	 * @param attributes
	 * @return
	 * @throws CoreException
	 */
	@SuppressWarnings("rawtypes")
	protected IProcess newProcess(ILaunch launch, Process p, String label, Map attributes) throws CoreException {
		IProcess process= DebugPlugin.newProcess(launch, p, label, attributes);
		if (process == null) {
			p.destroy();
			abort("An IProcess could not be created for the launch", null,-1); 
		}
		return process;
	}
	
	/**
	 * Assigns a default source locator to the given launch if a source locator
	 * has not yet been assigned to it, and the associated launch configuration
	 * does not specify a source locator.
	 * 
	 * @param launch
	 *            launch object
	 * @param configuration
	 *            configuration being launched
	 * @exception CoreException
	 *                if unable to set the source locator
	 */
	protected void setDefaultSourceLocator(ILaunch launch,
			ILaunchConfiguration configuration) throws CoreException {
		//  set default source locator if none specified
		if (launch.getSourceLocator() == null) {
			ISourceLookupDirector sourceLocator = new ApplicationSourceLookupDirector();
			sourceLocator
					.setSourcePathComputer(getLaunchManager()
							.getSourcePathComputer(
									"com.tibco.cep.studio.debug.core.applicationSourceLocator"));
			sourceLocator.initializeDefaults(configuration);
			launch.setSourceLocator(sourceLocator);
		}
	}
	
	/**
	 * Prepares the command line from the specified array of strings
	 * @param commandLine
	 * @return
	 */
	protected static String renderCommandLine(String[] commandLine) {
		if (commandLine.length < 1)
			return ""; //$NON-NLS-1$
		StringBuffer buf= new StringBuffer();
		for (int i= 0; i < commandLine.length; i++) {
			buf.append(' ');
			char[] characters= commandLine[i].toCharArray();
			StringBuffer command= new StringBuffer();
			boolean containsSpace= false;
			for (int j = 0; j < characters.length; j++) {
				char character= characters[j];
				if (character == '\"') {
					command.append('\\');
				} else if (character == ' ') {
					containsSpace = true;
				}
				command.append(character);
			}
			if (containsSpace) {
				buf.append('\"');
				buf.append(command.toString());
				buf.append('\"');
			} else {
				buf.append(command.toString());
			}
		}	
		return buf.toString();
	}
	
	/**
	 * Returns the 'rendered' name for the specified command line
	 * @param commandLine
	 * @param port 
	 * @param hostName 
	 * @return the name for the process
	 */
	public static String renderProcessLabel(String entryPoint, String hostName, String port) {
		String format= "{0} ({1}) at {2}:{3}"; 
		String timestamp= DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM).format(new Date(System.currentTimeMillis()));
		return MessageFormat.format(format,(Object[]) new String[] { entryPoint, timestamp,hostName,port });
	}
	
	/**
	 * Returns the 'rendered' name for the current target
	 * @param classToRun
	 * @param host
	 * @return the name for the current target
	 */
	protected String renderDebugTarget(String classToRun, String port) {
		String format="{0} at localhost:{1}"; 
		return MessageFormat.format(format, (Object[])new String[] { classToRun, port });
	}
	
	
	/**
	 * Returns the default 'com.sun.jdi.SocketListen' connector
	 * @return
	 */
	protected ListeningConnector getListeningConnector() {
		Iterator iter= Bootstrap.virtualMachineManager().listeningConnectors().iterator();
		while(iter.hasNext()) {
			ListeningConnector lc= (ListeningConnector) iter.next();
			if ("com.sun.jdi.SocketListen".equals(lc.name())) //$NON-NLS-1$
				return lc;
		}
		return null;
	}
	
	/**
	 * Returns the default 'com.sun.jdi.SocketConnection' connector
	 * @return
	 */
	protected AttachingConnector getAttachingConnector() {
		Iterator iter= Bootstrap.virtualMachineManager().attachingConnectors().iterator();
		while(iter.hasNext()) {
			AttachingConnector lc= (AttachingConnector) iter.next();
			if ("com.sun.jdi.SocketAttach".equals(lc.name()) && lc.transport().name().equals("dt_socket")) {
				return lc;
			}			
		}
		return null;
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
	 * Returns the default process attribute map for Java processes.
	 * @param debugProfile 
	 * 
	 * @return default process attribute map for Java processes
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Map getDefaultProcessMap(RunProfile runProfile) {
		Map map = new HashMap();
		map.put(IProcess.ATTR_PROCESS_TYPE,runProfile.getType().getName());
		return map;
	}
	
	/**
	 * Helper method that constructs a human-readable label for a remote VM.
	 */
	protected String constructVMLabel(VirtualMachine vm, String host, String port,
			ILaunchConfiguration configuration) {
				String name = null;
				try {
					name = vm.name();
				} catch (VMDisconnectedException e) {
					// do nothing
				}
				if (name == null) {
					if (configuration == null) {
						name = ""; //$NON-NLS-1$
					} else {
						name = configuration.getName();
					}
				}
				StringBuffer buffer = new StringBuffer(name);
				buffer.append('['); 
				buffer.append(host);
				buffer.append(':'); 
				buffer.append(port);
				buffer.append(']'); 
				return buffer.toString();
			}

	/**
	 * Used to attach to a VM in a separate thread, to allow for cancellation
	 * and detect that the associated System process died before the connect
	 * occurred.
	 */
	class ConnectRunnable implements Runnable {
		
		private VirtualMachine fVM = null;
		private ListeningConnector fConnector = null;
		@SuppressWarnings("rawtypes")
		private Map fConnectionMap = null;
		private Exception fException = null;
		
		/**
		 * Constructs a runnable to connect to a VM via the given connector
		 * with the given connection arguments.
		 * 
		 * @param connector
		 * @param map
		 */
		@SuppressWarnings("rawtypes")
		public ConnectRunnable(ListeningConnector connector, Map map) {
			fConnector = connector;
			fConnectionMap = map;
		}
		
		@SuppressWarnings("unchecked")
		public void run() {
			try {
				fVM = fConnector.accept(fConnectionMap);
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
			return fVM;
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

}
