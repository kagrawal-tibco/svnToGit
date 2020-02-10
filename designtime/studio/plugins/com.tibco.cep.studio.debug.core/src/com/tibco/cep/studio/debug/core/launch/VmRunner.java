package com.tibco.cep.studio.debug.core.launch;

import java.io.File;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IProcess;

import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;

public class VmRunner {
	
	private static final Object ID_JAVA_PROCESS_TYPE = "java";
	private static final Object ATTR_BOOTPATH_PREPEND = StudioDebugCorePlugin.getUniqueIdentifier() + ".-Xbootclasspath/p:";	 ;
	private static final Object ATTR_BOOTPATH = StudioDebugCorePlugin.getUniqueIdentifier() + ".-Xbootclasspath/p:";
	private static final Object ATTR_BOOTPATH_APPEND = StudioDebugCorePlugin.getUniqueIdentifier() + ".APPLET_APPLETVIEWER_CLASS";
	private static final Object ATTR_JAVA_COMMAND = StudioDebugCorePlugin.getUniqueIdentifier() + ".JAVA_COMMAND";;
	/**
	 * The VM install instance
	 */
	protected VmInstall fVMInstance;
	
	/**
	 * Constructor
	 * @param vmInstance
	 */
	public VmRunner(VmInstall vmInstance) {
		fVMInstance= vmInstance;
	}
	
	/**
	 * Returns the 'rendered' name for the current target
	 * @param classToRun
	 * @param host
	 * @return the name for the current target
	 */
	protected String renderDebugTarget(String classToRun, int host) {
		String format= "{0} at localhost:{1}"; 
		return MessageFormat.format(format, new Object[] { classToRun, String.valueOf(host) });
	}

	/**
	 * Returns the 'rendered' name for the specified command line
	 * @param commandLine
	 * @return the name for the process
	 */
	public static String renderProcessLabel(String[] commandLine) {
		String format= "{0} ({1})"; 
		String timestamp= DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM).format(new Date(System.currentTimeMillis()));
		return MessageFormat.format(format, new Object[] { commandLine[0], timestamp });
	}
	
	/**
	 * Prepares the command line from the specified array of strings
	 * @param commandLine
	 * @return
	 */
	protected static String renderCommandLine(String[] commandLine) {
		if (commandLine.length < 1)
			return ""; 
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
	 * Adds the values of args to the given list v
	 * @param args
	 * @param v
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void addArguments(String[] args, List v) {
		if (args == null) {
			return;
		}
		for (int i= 0; i < args.length; i++) {
			v.add(args[i]);
		}
	}
	
	/**
	 * Returns the working directory to use for the launched VM,
	 * or <code>null</code> if the working directory is to be inherited
	 * from the current process.
	 * 
	 * @return the working directory to use
	 * @exception CoreException if the working directory specified by
	 *  the configuration does not exist or is not a directory
	 */	
	protected File getWorkingDir(VmRunnerConfig config) throws CoreException {
		String path = config.getWorkingDirectory();
		if (path == null) {
			return null;
		}
		File dir = new File(path);
		if (!dir.isDirectory()) {
			abort(MessageFormat.format("Specified working directory does not exist or is not a directory: {0}", new Object[] {path}), null,-1); 
		}
		return dir;
	}
	

	/**
	 * Construct and return a String containing the full path of a java executable
	 * command such as 'java' or 'javaw.exe'.  If the configuration specifies an
	 * explicit executable, that is used.
	 * 
	 * @return full path to java executable
	 * @exception CoreException if unable to locate an executable
	 */
	@SuppressWarnings("rawtypes")
	protected String constructProgramString(VmRunnerConfig config) throws CoreException {

		// Look for the user-specified java executable command
		String command= null;
		Map map= config.getVMSpecificAttributesMap();
		if (map != null) {
			command = (String)map.get(ATTR_JAVA_COMMAND);
		}
		
		// If no java command was specified, use default executable
		if (command == null) {
			File exe = null;
			if (fVMInstance instanceof VmInstall) {
				exe = ((VmInstall)fVMInstance).getJavaExecutable();
			} else {
				exe = VmType.findJavaExecutable(fVMInstance.getInstallLocation());
			}
			if (exe == null) {
				abort(MessageFormat.format("Unable to locate executable for {0}", new Object[]{fVMInstance.getName()}), null, -1); 
			} else {
				return exe.getAbsolutePath();
			}
		}
				
		// Build the path to the java executable.  First try 'bin', and if that
		// doesn't exist, try 'jre/bin'
		String installLocation = fVMInstance.getInstallLocation().getAbsolutePath() + File.separatorChar;
		File exe = new File(installLocation + "bin" + File.separatorChar + command);  		
		if (fileExists(exe)){
			return exe.getAbsolutePath();
		}
		exe = new File(exe.getAbsolutePath() + ".exe"); 
		if (fileExists(exe)){
			return exe.getAbsolutePath();
		}
		exe = new File(installLocation + "jre" + File.separatorChar + "bin" + File.separatorChar + command);  //$NON-NLS-2$
		if (fileExists(exe)) {
			return exe.getAbsolutePath(); 
		}
		exe = new File(exe.getAbsolutePath() + ".exe"); 
		if (fileExists(exe)) {
			return exe.getAbsolutePath(); 
		}		

		
		// not found
		abort(MessageFormat.format("Specified executable {0} does not exist for {1}", new Object[]{command, fVMInstance.getName()}), null, -1); 
		// NOTE: an exception will be thrown - null cannot be returned
		return null;		
	}	
	
	/**
	 * Convenience method to determine if the specified file exists or not
	 * @param file
	 * @return true if the file indeed exists, false otherwise
	 */
	protected boolean fileExists(File file) {
		return file.exists() && file.isFile();
	}

	protected String convertClassPath(String[] cp) {
		int pathCount= 0;
		StringBuffer buf= new StringBuffer();
		if (cp.length == 0) {
			return "";    
		}
		for (int i= 0; i < cp.length; i++) {
			if (pathCount > 0) {
				buf.append(File.pathSeparator);
			}
			buf.append(cp[i]);
			pathCount++;
		}
		return buf.toString();
	}

	/**
	 * This method is used to ensure that the JVM file encoding matches that of the console preference for file encoding.
	 * If the user explicitly declares a file encoding in the launch configuration, then that file encoding is used.
	 * @param vmargs the original listing of JVM arguments
	 * @return the listing of JVM arguments including file encoding if one was not specified
	 * 
	 * @since 3.4
	 */
	protected String[] ensureEncoding(ILaunch launch, String[] vmargs) {
		boolean foundencoding = false;
		for(int i = 0; i < vmargs.length; i++) {
			if(vmargs[i].startsWith("-Dfile.encoding=")) { 
				foundencoding = true; 
			}
		}
		if(!foundencoding) {
			String encoding = launch.getAttribute(DebugPlugin.ATTR_CONSOLE_ENCODING);
			if(encoding == null) {
				return vmargs;
			}
			String[] newargs = new String[vmargs.length+1];
			System.arraycopy(vmargs, 0, newargs, 0, vmargs.length);
			newargs[newargs.length-1] = "-Dfile.encoding="+encoding; 
			return newargs;
		}
		return vmargs;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.launching.IVMRunner#run(org.eclipse.jdt.launching.VmRunnerConfig, org.eclipse.debug.core.ILaunch, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void run(VmRunnerConfig config, ILaunch launch, IProgressMonitor monitor) throws CoreException {

		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		
		IProgressMonitor subMonitor = new SubProgressMonitor(monitor, 1);
		subMonitor.beginTask("Launching VM...", 2); 
		subMonitor.subTask("Constructing command line..."); 
		
		String program= constructProgramString(config);
		
		List arguments= new ArrayList();
		arguments.add(program);
				
		// VM args are the first thing after the java program so that users can specify
		// options like '-client' & '-server' which are required to be the first option
		String[] allVMArgs = combineVmArgs(config, fVMInstance);
		addArguments(ensureEncoding(launch, allVMArgs), arguments);
		
		addBootClassPathArguments(arguments, config);
		
		String[] cp= config.getClassPath();
		if (cp.length > 0) {
			arguments.add("-classpath"); 
			arguments.add(convertClassPath(cp));
		}
		arguments.add(config.getClassToLaunch());
		
		String[] programArgs= config.getProgramArguments();
		addArguments(programArgs, arguments);
				
		String[] cmdLine= new String[arguments.size()];
		arguments.toArray(cmdLine);
		
		String[] envp = prependJREPath(config.getEnvironment());
		
		subMonitor.worked(1);

		// check for cancellation
		if (monitor.isCanceled()) {
			return;
		}
		
		subMonitor.subTask("Starting virtual machine..."); 
		Process p= null;
		File workingDir = getWorkingDir(config);
		p= exec(cmdLine, workingDir, envp);
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
		subMonitor.done();
	}
	
	/**
	 * Prepends the correct java version variable state to the environment path for Mac VMs
	 * 
	 * @param env the current array of environment variables to run with
	 * @param jdkpath the path of the current jdk
	 * @since 3.3
	 */
	@SuppressWarnings("rawtypes")
	protected String[] prependJREPath(String[] env) {
		if (Platform.OS_MACOSX.equals(Platform.getOS())) {
			if (fVMInstance instanceof VmInstall) {
				VmInstall vm = (VmInstall) fVMInstance;
				String javaVersion = vm.getJavaVersion();
				if (javaVersion != null) {
					if (env == null) {
						Map map = DebugPlugin.getDefault().getLaunchManager().getNativeEnvironmentCasePreserved();
						if (map.containsKey(VmDebugger.JAVA_JVM_VERSION)) {
							String[] env2 = new String[map.size()];
							Iterator iterator = map.entrySet().iterator();
							int i = 0;
							while (iterator.hasNext()) {
								Entry entry = (Entry) iterator.next();
								String key = (String) entry.getKey();
								if (VmDebugger.JAVA_JVM_VERSION.equals(key)) {
									env2[i] = key + "=" + javaVersion; 
								} else {
									env2[i] = key + "=" + (String)entry.getValue(); 
								}
								i++;
							}
							env = env2;
						}
					} else {
						for (int i = 0; i < env.length; i++) {
							String string = env[i];
							if (string.startsWith(VmDebugger.JAVA_JVM_VERSION)) {
								env[i]=VmDebugger.JAVA_JVM_VERSION+"="+javaVersion; 
								break;
							}
						}
					}
				}
			}
		}
		return env;
	}

	/**
	 * Adds arguments to the bootpath
	 * @param arguments
	 * @param config
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void addBootClassPathArguments(List arguments, VmRunnerConfig config) {
		String[] prependBootCP= null;
		String[] bootCP= null;
		String[] appendBootCP= null;
		Map map = config.getVMSpecificAttributesMap();
		if (map != null) {
			prependBootCP= (String[]) map.get(ATTR_BOOTPATH_PREPEND);
			bootCP= (String[]) map.get(ATTR_BOOTPATH);
			appendBootCP= (String[]) map.get(ATTR_BOOTPATH_APPEND);
		}
		if (prependBootCP == null && bootCP == null && appendBootCP == null) {
			// use old single attribute instead of new attributes if not specified
			bootCP = config.getBootClassPath();
		}
		if (prependBootCP != null) {
			arguments.add("-Xbootclasspath/p:" + convertClassPath(prependBootCP)); 
		}
		if (bootCP != null) {
			if (bootCP.length > 0) {
				arguments.add("-Xbootclasspath:" + convertClassPath(bootCP)); 
			}
		}
		if (appendBootCP != null) {
			arguments.add("-Xbootclasspath/a:" + convertClassPath(appendBootCP)); 
		}
	}
	
	

	/**
	 * Throws a core exception with an error status object built from
	 * the given message, lower level exception, and error code.
	 * 
	 * @param message the status message
	 * @param exception lower level exception associated with the
	 *  error, or <code>null</code> if none
	 * @param code error code
	 * @throws CoreException The exception encapsulating the reason for the abort
	 */
	protected void abort(String message, Throwable exception, int code) throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR, getPluginIdentifier(), code, message, exception));
	}
	
	/**
	 * Returns the identifier of the plug-in this VM runner 
	 * originated from.
	 * 
	 * @return plug-in identifier
	 */

	protected String getPluginIdentifier() {
		return StudioDebugCorePlugin.getUniqueIdentifier();
	}
	
	/**
	 * @see DebugPlugin#exec(String[], File)
	 */
	protected Process exec(String[] cmdLine, File workingDirectory) throws CoreException {
		return DebugPlugin.exec(cmdLine, workingDirectory);
	}
	
	/**
	 * @since 3.0
	 * @see DebugPlugin#exec(String[], File, String[])
	 */
	protected Process exec(String[] cmdLine, File workingDirectory, String[] envp) throws CoreException {
		return DebugPlugin.exec(cmdLine, workingDirectory, envp);
	}	
	
	/**
	 * Returns the given array of strings as a single space-delimited string.
	 * 
	 * @param cmdLine array of strings
	 * @return a single space-delimited string
	 */
	protected String getCmdLineAsString(String[] cmdLine) {
		StringBuffer buff= new StringBuffer();
		for (int i = 0, numStrings= cmdLine.length; i < numStrings; i++) {
			buff.append(cmdLine[i]);
			buff.append(' ');	
		} 
		return buff.toString().trim();
	}
	
	/**
	 * Returns the default process attribute map for Java processes.
	 * 
	 * @return default process attribute map for Java processes
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Map getDefaultProcessMap() {
		Map map = new HashMap();
		map.put(IProcess.ATTR_PROCESS_TYPE, ID_JAVA_PROCESS_TYPE);
		return map;
	}
	
	/**
	 * Returns a new process aborting if the process could not be created.
	 * @param launch the launch the process is contained in
	 * @param p the system process to wrap
	 * @param label the label assigned to the process
	 * @param attributes values for the attribute map
	 * @return the new process
	 * @throws CoreException problems occurred creating the process
	 * @since 3.0
	 */
	@SuppressWarnings("rawtypes")
	protected IProcess newProcess(ILaunch launch, Process p, String label, Map attributes) throws CoreException {
		IProcess process= DebugPlugin.newProcess(launch, p, label, attributes);
		if (process == null) {
			p.destroy();
			abort("An IProcess could not be created for the launch", null, -1); 
		}
		return process;
	}
	
	/**
	 * Combines and returns VM arguments specified by the runner configuration,
	 * with those specified by the VM install, if any.
	 * 
	 * @param configuration runner configuration
	 * @param vmInstall vm install
	 * @return combined VM arguments specified by the runner configuration
	 *  and VM install
	 * @since 3.0
	 */
	protected String[] combineVmArgs(VmRunnerConfig configuration, VmInstall vmInstall) {
		String[] launchVMArgs= configuration.getVMArguments();
		String[] vmVMArgs = vmInstall.getVMArguments();
		if (vmVMArgs == null || vmVMArgs.length == 0) {
			return launchVMArgs;
		}
		String[] allVMArgs = new String[launchVMArgs.length + vmVMArgs.length];
		System.arraycopy(launchVMArgs, 0, allVMArgs, 0, launchVMArgs.length);
		System.arraycopy(vmVMArgs, 0, allVMArgs, launchVMArgs.length, vmVMArgs.length);
		return allVMArgs;
	}

}
