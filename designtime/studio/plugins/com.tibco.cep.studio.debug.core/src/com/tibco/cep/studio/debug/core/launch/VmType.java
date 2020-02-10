package com.tibco.cep.studio.debug.core.launch;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.Launch;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamsProxy;
import org.eclipse.osgi.service.environment.Constants;

import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;

public class VmType {
	
	public static final String ID_STANDARD_VM_TYPE = "com.tibco.cep.studio.debug.core.launch.debugVmType"; //$NON-NLS-1$
	
	@SuppressWarnings("rawtypes")
	private List fVMs;
	private String fId;
	
	/**
	 * The root path for the attached source
	 */
	private String fDefaultRootPath = ""; //$NON-NLS-1$
	
	/**
	 * Map of the install path for which we were unable to generate
	 * the library info during this session.
	 */
	@SuppressWarnings("rawtypes")
	private static Map fgFailedInstallPath = new HashMap();
		
	/**
	 * Convenience handle to the system-specific file separator character
	 */															
	private static final char fgSeparator = File.separatorChar;

	/**
	 * The list of locations in which to look for the java executable in candidate
	 * VM install locations, relative to the VM install location.
	 */
	private static final String[] fgCandidateJavaFiles = {"javaw", "javaw.exe", "java", "java.exe", "j9w", "j9w.exe", "j9", "j9.exe"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
	private static final String[] fgCandidateJavaLocations = {"bin" + fgSeparator, "jre" + fgSeparator + "bin" + fgSeparator}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	
	/**
	 * Constructs a new VM install type.
	 */
	@SuppressWarnings("rawtypes")
	protected VmType() {
		fVMs= new ArrayList(10);
	}

	/* (non-Javadoc)
	 * Subclasses should not override this method.
	 * @see IVMType#getVMs()
	 */
	@SuppressWarnings("unchecked")
	public VmInstall[] getVMInstalls() {
		VmInstall[] vms= new VmInstall[fVMs.size()];
		return (VmInstall[])fVMs.toArray(vms);
	}

	/* (non-Javadoc)
	 * Subclasses should not override this method.
	 * @see IVMType#disposeVM(String)
	 */
	public void disposeVMInstall(String id) {
		for (int i= 0; i < fVMs.size(); i++) {
			VmInstall vm= (VmInstall)fVMs.get(i);
			if (vm.getId().equals(id)) {
				fVMs.remove(i);
				return;
			}
		}
	}

	/* (non-Javadoc)
	 * Subclasses should not override this method.
	 * @see IVMType#getVM(String)
	 */
	public VmInstall findVMInstall(String id) {
		for (int i= 0; i < fVMs.size(); i++) {
			VmInstall vm= (VmInstall)fVMs.get(i);
			if (vm.getId().equals(id)) {
				return vm;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * Subclasses should not override this method.
	 * @see IVMType#createVM(String)
	 */
	@SuppressWarnings("unchecked")
	public VmInstall createVMInstall(String id) throws IllegalArgumentException {
		if (findVMInstall(id) != null) {
			String format= "Duplicate VM: {0}"; 
			throw new IllegalArgumentException(MessageFormat.format(format, new Object[] { id }));
		}
		VmInstall vm= doCreateVMInstall(id);
		fVMs.add(vm);
		return vm;
	}
	/**
	 * Subclasses should return a new instance of the appropriate
	 * <code>IVMInstall</code> subclass from this method.
	 * @param	id	The vm's id. The <code>IVMInstall</code> instance that is created must
	 * 				return <code>id</code> from its <code>getId()</code> method.
	 * 				Must not be <code>null</code>.
	 * @return	the newly created IVMInstall instance. Must not return <code>null</code>.
	 */
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.launching.AbstractVMInstallType#doCreateVMInstall(java.lang.String)
	 */
	protected VmInstall doCreateVMInstall(String id) {
		return new VmInstall(this, id);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement, java.lang.String, java.lang.Object)
	 */
	/**
	 * Initializes the id parameter from the "id" attribute
	 * in the configuration markup.
	 * Subclasses should not override this method.
	 * @param config the configuration element used to trigger this execution. 
	 *		It can be queried by the executable extension for specific
	 *		configuration properties
	 * @param propertyName the name of an attribute of the configuration element
	 *		used on the <code>createExecutableExtension(String)</code> call. This
	 *		argument can be used in the cases where a single configuration element
	 *		is used to define multiple executable extensions.
	 * @param data adapter data in the form of a <code>String</code>, 
	 *		a <code>Hashtable</code>, or <code>null</code>.
	 * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement, java.lang.String, java.lang.Object)
	 */
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) {
		fId= config.getAttribute("id"); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * Subclasses should not override this method.
	 * @see IVMType#getId()
	 */
	public String getId() {
		return fId;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.launching.IVMInstallType#findVMInstallByName(java.lang.String)
	 */
	public VmInstall findVMInstallByName(String name) {
		for (int i= 0; i < fVMs.size(); i++) {
			VmInstall vm= (VmInstall)fVMs.get(i);
			if (vm.getName().equals(name)) {
				return vm;
			}
		}
		return null;
	}


	
	/**
	 * Returns a string of default VM arguments for a VM installed at the
	 * given home location, or <code>null</code> if none.
	 * The default implementation returns <code>null</code>, subclasses must override
	 * as appropriate.
	 * <p>
	 * Note, this method would ideally be added to <code>IVMInstallType</code>,
	 * but it would have been a breaking API change between 2.0 and 3.4. Thus,
	 * it has been added to the abstract base class that VM install types should
	 * subclass.
	 * </p>
	 * @param installLocation home location
	 * @return default VM arguments or <code>null</code> if none
	 * @since 3.4
	 */
	public String getDefaultVMArguments(File installLocation) {
		return null;
	}
	
	/**
	 * Starting in the specified VM install location, attempt to find the 'java' executable
	 * file.  If found, return the corresponding <code>File</code> object, otherwise return
	 * <code>null</code>.
	 */
	public static File findJavaExecutable(File vmInstallLocation) {
		// Try each candidate in order.  The first one found wins.  Thus, the order
		// of fgCandidateJavaLocations and fgCandidateJavaFiles is significant.
		for (int i = 0; i < fgCandidateJavaFiles.length; i++) {
			for (int j = 0; j < fgCandidateJavaLocations.length; j++) {
				File javaFile = new File(vmInstallLocation, fgCandidateJavaLocations[j] + fgCandidateJavaFiles[i]);
				if (javaFile.isFile()) {
					return javaFile;
				}				
			}
		}		
		return null;							
	}
	
	/**
	 * Return library information corresponding to the specified install
	 * location. If the information does not exist, create it using the given Java
	 * executable.
	 */
	@SuppressWarnings("unchecked")
	protected synchronized LibraryInfo getLibraryInfo(File javaHome, File javaExecutable) {
		
		// See if we already know the information for the requested VM.  If not, generate it.
		String installPath = javaHome.getAbsolutePath();
		LibraryInfo info = StudioDebugCorePlugin.getLibraryInfo(installPath);
		if (info == null) {
			info= (LibraryInfo)fgFailedInstallPath.get(installPath);
			if (info == null) {
				info = generateLibraryInfo(javaHome, javaExecutable);
				if (info == null) {
					info = getDefaultLibraryInfo(javaHome);
					fgFailedInstallPath.put(installPath, info);
				} else {
				    // only persist if we were able to generate information - see bug 70011
				    StudioDebugCorePlugin.setLibraryInfo(installPath, info);
				}
			}
		} 
		return info;
	}	
	
	/**
	 * Return <code>true</code> if the appropriate system libraries can be found for the
	 * specified java executable, <code>false</code> otherwise.
	 */
	protected boolean canDetectDefaultSystemLibraries(File javaHome, File javaExecutable) {
		LibraryLocation[] locations = getDefaultLibraryLocations(javaHome);
		String version = getVMVersion(javaHome, javaExecutable);
		return locations.length > 0 && !version.startsWith("1.1"); //$NON-NLS-1$
	}
	
	/**
	 * Returns the version of the VM at the given location, with the given
	 * executable.
	 * 
	 * @param javaHome
	 * @param javaExecutable
	 * @return String
	 */
	protected String getVMVersion(File javaHome, File javaExecutable) {
		LibraryInfo info = getLibraryInfo(javaHome, javaExecutable);
		return info.getVersion();
	}
		
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.launching.IVMInstallType#detectInstallLocation()
	 */
	public File detectInstallLocation() {
		// do not detect on the Mac OS
		if (Platform.getOS().equals(Constants.OS_MACOSX)) {
			return null;
		}		
		
		// Retrieve the 'java.home' system property.  If that directory doesn't exist, 
		// return null.
		File javaHome; 
		try {
			javaHome= new File (System.getProperty("java.home")).getCanonicalFile(); //$NON-NLS-1$
		} catch (IOException e) {
			StudioDebugCorePlugin.log(e);
			return null;
		}
		if (!javaHome.exists()) {
			return null;
		}

		// Find the 'java' executable file under the java home directory.  If it can't be
		// found, return null.
		File javaExecutable = findJavaExecutable(javaHome);
		if (javaExecutable == null) {
			return null;
		}
		
		// If the reported java home directory terminates with 'jre', first see if 
		// the parent directory contains the required libraries
		boolean foundLibraries = false;
		if (javaHome.getName().equalsIgnoreCase("jre")) { //$NON-NLS-1$
			File parent= new File(javaHome.getParent());			
			if (canDetectDefaultSystemLibraries(parent, javaExecutable)) {
				javaHome = parent;
				foundLibraries = true;
			}
		}	
		
		// If we haven't already found the libraries, look in the reported java home dir
		if (!foundLibraries) {
			if (!canDetectDefaultSystemLibraries(javaHome, javaExecutable)) {
				return null;
			}			
		}
		
		return javaHome;
	}

	/**
	 * Return an <code>IPath</code> corresponding to the single library file containing the
	 * standard Java classes for most VMs version 1.2 and above.
	 */
	protected IPath getDefaultSystemLibrary(File javaHome) {
		IPath jreLibPath= new Path(javaHome.getPath()).append("lib").append("rt.jar"); //$NON-NLS-2$ //$NON-NLS-1$
		if (jreLibPath.toFile().isFile()) {
			return jreLibPath;
		}
		return new Path(javaHome.getPath()).append("jre").append("lib").append("rt.jar"); //$NON-NLS-3$ //$NON-NLS-2$ //$NON-NLS-1$
	}
	
	/**
	 * Returns a path to the source attachment for the given libaray, or
	 * an empty path if none.
	 * 
	 * @param libLocation
	 * @return a path to the source attachment for the given library, or
	 *  an empty path if none
	 */
	protected IPath getDefaultSystemLibrarySource(File libLocation) {
		File parent= libLocation.getParentFile();
		while (parent != null) {
			File parentsrc= new File(parent, "src.jar"); //$NON-NLS-1$
			if (parentsrc.isFile()) {
				setDefaultRootPath("src");//$NON-NLS-1$
				return new Path(parentsrc.getPath());
			}
			parentsrc= new File(parent, "src.zip"); //$NON-NLS-1$
			if (parentsrc.isFile()) {
				setDefaultRootPath(""); //$NON-NLS-1$
				return new Path(parentsrc.getPath());
			}
			parent = parent.getParentFile();
		}
		setDefaultRootPath(""); //$NON-NLS-1$
		return Path.EMPTY; 
	}
	
	protected IPath getDefaultPackageRootPath() {
		return new Path(getDefaultRootPath());
	}

	/**
	 * NOTE: We do not add libraries from the "endorsed" directory explicitly, as
	 * the bootpath contains these entries already (if they exist).
	 * 
	 * @see org.eclipse.jdt.launching.IVMInstallType#getDefaultLibraryLocations(File)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LibraryLocation[] getDefaultLibraryLocations(File installLocation) {
		// Determine the java executable that corresponds to the specified install location
		// and use this to generate library information.  If no java executable was found, 
		// the 'standard' libraries will be returned.
		File javaExecutable = findJavaExecutable(installLocation);
		LibraryInfo libInfo;
		if (javaExecutable == null) {
			libInfo = getDefaultLibraryInfo(installLocation);
		} else {
			libInfo = getLibraryInfo(installLocation, javaExecutable);
		}
		
		// Add all endorsed libraries - they are first, as they replace
		List allLibs = new ArrayList(gatherAllLibraries(libInfo.getEndorsedDirs()));
		
		// next is the boot path libraries
		String[] bootpath = libInfo.getBootpath();
		List boot = new ArrayList(bootpath.length);
		URL url = getDefaultJavadocLocation(installLocation);
		for (int i = 0; i < bootpath.length; i++) {
			IPath path = new Path(bootpath[i]);
			File lib = path.toFile(); 
			if (lib.exists() && lib.isFile()) {
				LibraryLocation libraryLocation = new LibraryLocation(path,
								getDefaultSystemLibrarySource(lib),
								getDefaultPackageRootPath(),
								url);
				boot.add(libraryLocation);
			}
		}
		allLibs.addAll(boot);
				
		// Add all extension libraries
		allLibs.addAll(gatherAllLibraries(libInfo.getExtensionDirs()));
		
		//remove duplicates
		HashSet set = new HashSet();
		LibraryLocation lib = null;
		for(ListIterator liter = allLibs.listIterator(); liter.hasNext();) {
			lib = (LibraryLocation) liter.next();
			IPath systemLibraryPath = lib.getSystemLibraryPath();
			String device = systemLibraryPath.getDevice();
			if (device != null) {
				// @see Bug 197866 - Installed JRE Wizard creates duplicate system libraries when drive letter is lower case
				systemLibraryPath = systemLibraryPath.setDevice(device.toUpperCase());
			}
			if(!set.add(systemLibraryPath.toOSString())) {
				//did not add it, duplicate
				liter.remove();
			}
		}
		return (LibraryLocation[])allLibs.toArray(new LibraryLocation[allLibs.size()]);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.launching.AbstractVMInstallType#getDefaultJavadocLocation(java.io.File)
	 */
	public URL getDefaultJavadocLocation(File installLocation) {
		File javaExecutable = findJavaExecutable(installLocation);
		if (javaExecutable != null) {
			LibraryInfo libInfo = getLibraryInfo(installLocation, javaExecutable);
			if (libInfo != null) {
				String version = libInfo.getVersion();
				return getDefaultJavadocLocation(version);
			}
		}
		return null;
	}
	
	/**
	 * Returns a default Javadoc location for a language version, or <code>null</code>.
	 * 
	 * @param version language version such as "1.4"
	 * @return URL to default Javadoc location, or <code>null</code>
	 */
	protected static URL getDefaultJavadocLocation(String version) {
		try {
			if(version.startsWith("1.8")){ //$NON-NLS-1$
				return new URL("http://java.sun.com/javase/8/docs/api/");	 //$NON-NLS-1$
			} else if(version.startsWith("1.7")){ //$NON-NLS-1$
				return new URL("http://java.sun.com/javase/7/docs/api/");	 //$NON-NLS-1$
			} else if (version.startsWith("1.6")) { //$NON-NLS-1$
				return new URL("http://java.sun.com/javase/6/docs/api/"); //$NON-NLS-1$
			} else if (version.startsWith("1.5")) { //$NON-NLS-1$
				return new URL("http://java.sun.com/j2se/1.5.0/docs/api/"); //$NON-NLS-1$
			} else if (version.startsWith("1.4")) { //$NON-NLS-1$
				return new URL("http://java.sun.com/j2se/1.4.2/docs/api/"); //$NON-NLS-1$
			} else if (version.startsWith("1.3")) { //$NON-NLS-1$
				return new URL("http://java.sun.com/j2se/1.3/docs/api/"); //$NON-NLS-1$
			} else if (version.startsWith("1.2")) { //$NON-NLS-1$
				return new URL("http://java.sun.com/products/jdk/1.2/docs/api"); //$NON-NLS-1$
			}
		} catch (MalformedURLException e) {
		}
		return null;
	}
	
	/**
	 * Returns default library information for the given install location.
	 * 
	 * @param installLocation
	 * @return LibraryInfo
	 */
	protected LibraryInfo getDefaultLibraryInfo(File installLocation) {
		IPath rtjar = getDefaultSystemLibrary(installLocation);
		File extDir = getDefaultExtensionDirectory(installLocation);
		File endDir = getDefaultEndorsedDirectory(installLocation);
		String[] dirs = null;
		if (extDir == null) {
			dirs = new String[0];
		} else {
			dirs = new String[] {extDir.getAbsolutePath()};
		}
		String[] endDirs = null;
		if (endDir == null) {
			endDirs = new String[0]; 
		} else {
			endDirs = new String[] {endDir.getAbsolutePath()};
		}
		return new LibraryInfo("???", new String[] {rtjar.toOSString()}, dirs, endDirs);		 //$NON-NLS-1$
	}
	
	/**
	 * Returns a list of all zip's and jars contained in the given directories.
	 * 
	 * @param dirPaths a list of absolute paths of directories to search
	 * @return List of all zip's and jars
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static List gatherAllLibraries(String[] dirPaths) {
		List libraries = new ArrayList();
		for (int i = 0; i < dirPaths.length; i++) {
			File extDir = new File(dirPaths[i]);
			if (extDir.exists() && extDir.isDirectory()) {
				String[] names = extDir.list();
				for (int j = 0; j < names.length; j++) {
					String name = names[j];
					File jar = new File(extDir, name);
					if (jar.isFile()) {
						int length = name.length();
						if (length > 4) {
							String suffix = name.substring(length - 4);
							if (suffix.equalsIgnoreCase(".zip") || suffix.equalsIgnoreCase(".jar")) { //$NON-NLS-1$ //$NON-NLS-2$
								try {
									IPath libPath = new Path(jar.getCanonicalPath());
									LibraryLocation library = new LibraryLocation(libPath, Path.EMPTY, Path.EMPTY, null);
									libraries.add(library);
								} catch (IOException e) {
									StudioDebugCorePlugin.log(e);
								}
							}
						}
					}
				}
			}			
		}
		return libraries;
	}
		
	/**
	 * Returns the default location of the extension directory, based on the given
	 * install location. The resulting file may not exist, or be <code>null</code>
	 * if an extension directory is not supported.
	 * 
	 * @param installLocation 
	 * @return default extension directory or <code>null</code>
	 */
	protected File getDefaultExtensionDirectory(File installLocation) {
		File jre = null;
		if (installLocation.getName().equalsIgnoreCase("jre")) { //$NON-NLS-1$
			jre = installLocation;
		} else {
			jre = new File(installLocation, "jre"); //$NON-NLS-1$
		}
		File lib = new File(jre, "lib"); //$NON-NLS-1$
		File ext = new File(lib, "ext"); //$NON-NLS-1$
		return ext;
	}

	/**
	 * Returns the default location of the endorsed directory, based on the
	 * given install location. The resulting file may not exist, or be
	 * <code>null</code> if an endorsed directory is not supported.
	 * 
	 * @param installLocation 
	 * @return default endorsed directory or <code>null</code>
	 */
	protected File getDefaultEndorsedDirectory(File installLocation) {
		File lib = new File(installLocation, "lib"); //$NON-NLS-1$
		File ext = new File(lib, "endorsed"); //$NON-NLS-1$
		return ext;
	}

	protected String getDefaultRootPath() {
		return fDefaultRootPath;
	}

	protected void setDefaultRootPath(String defaultRootPath) {
		fDefaultRootPath = defaultRootPath;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.launching.IVMInstallType#validateInstallLocation(java.io.File)
	 */
	public IStatus validateInstallLocation(File javaHome) {
		IStatus status = null;
		if (Platform.getOS().equals(Constants.OS_MACOSX)) {
			status = new Status(IStatus.ERROR, StudioDebugCorePlugin.getUniqueIdentifier(), 0, "Standard VM not supported on MacOS.", null); 
		} else {
			File javaExecutable = findJavaExecutable(javaHome);
			if (javaExecutable == null) {
				status = new Status(IStatus.ERROR, StudioDebugCorePlugin.getUniqueIdentifier(), 0, "Target is not a JDK root. System library was not found.", null); //			
			} else {
				if (canDetectDefaultSystemLibraries(javaHome, javaExecutable)) {
					status = new Status(IStatus.OK, StudioDebugCorePlugin.getUniqueIdentifier(), 0, "ok", null); 
				} else {
					status = new Status(IStatus.ERROR, StudioDebugCorePlugin.getUniqueIdentifier(), 0, "Target is not a JDK root. System library was not found.", null); 
				}
			}
		}
		return status;		
	}

	/**
	 * Generates library information for the given java executable. A main
	 * program is run (<code>org.eclipse.jdt.internal.launching.support.
	 * LibraryDetector</code>), that dumps the system properties for bootpath
	 * and extension directories. This output is then parsed and cached for
	 * future reference.
	 * 
	 * @return library info or <code>null</code> if none
	 */	
	@SuppressWarnings("rawtypes")
	protected LibraryInfo generateLibraryInfo(File javaHome, File javaExecutable) {
		LibraryInfo info = null;
		
		// if this is 1.1.X, the properties will not exist		
		IPath classesZip = new Path(javaHome.getAbsolutePath()).append("lib").append("classes.zip"); //$NON-NLS-1$ //$NON-NLS-2$
		if (classesZip.toFile().exists()) {
			return new LibraryInfo("1.1.x", new String[] {classesZip.toOSString()}, new String[0], new String[0]); //$NON-NLS-1$
		}
		//locate the launching support jar - it contains the main program to run
		File file = StudioDebugCorePlugin.getFileInPlugin(new Path("lib/launchingsupport.jar")); //$NON-NLS-1$
		if (file.exists()) {	
			String javaExecutablePath = javaExecutable.getAbsolutePath();
			String[] cmdLine = new String[] {javaExecutablePath, "-classpath", file.getAbsolutePath(), "org.eclipse.jdt.internal.launching.support.LibraryDetector"};  //$NON-NLS-1$ //$NON-NLS-2$
			Process p = null;
			try {
				String envp[] = null;
				if (Platform.OS_MACOSX.equals(Platform.getOS())) {
					Map map = DebugPlugin.getDefault().getLaunchManager().getNativeEnvironmentCasePreserved();
					if (map.remove("JAVA_JVM_VERSION") != null) {
						envp = new String[map.size()];
						Iterator iterator = map.entrySet().iterator();
						int i = 0;
						while (iterator.hasNext()) {
							Entry entry = (Entry) iterator.next();
							envp[i] = (String)entry.getKey() + "=" + (String)entry.getValue(); //$NON-NLS-1$
							i++;
						}
					}
				}
				p = DebugPlugin.exec(cmdLine, null, envp);
				IProcess process = DebugPlugin.newProcess(new Launch(null, ILaunchManager.RUN_MODE, null), p, "Library Detection"); //$NON-NLS-1$
				for (int i= 0; i < 600; i++) {
					// Wait no more than 30 seconds (600 * 50 mils)
					if (process.isTerminated()) {
						break;
					}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
					}
				}
				info = parseLibraryInfo(process);
			} catch (CoreException ioe) {
				StudioDebugCorePlugin.log(ioe);
			} finally {
				if (p != null) {
					p.destroy();
				}
			}
		}
		if (info == null) {
		    // log error that we were unable to generate library information - see bug 70011
			StudioDebugCorePlugin.log(MessageFormat.format("Failed to retrieve default libraries for {0}", new Object[]{javaHome.getAbsolutePath()})); //$NON-NLS-1$
		}
		return info;
	}
	
	/**
	 * Parses the output from 'LibraryDetector'.
	 */
	protected LibraryInfo parseLibraryInfo(IProcess process) {
		IStreamsProxy streamsProxy = process.getStreamsProxy();
		String text = null;
		if (streamsProxy != null) {
			text = streamsProxy.getOutputStreamMonitor().getContents();
		}
		if (text != null && text.length() > 0) {
			int index = text.indexOf("|"); //$NON-NLS-1$
			if (index > 0) { 
				String version = text.substring(0, index);
				text = text.substring(index + 1);
				index = text.indexOf("|"); //$NON-NLS-1$	
				if (index > 0) {
					String bootPaths = text.substring(0, index);
					String[] bootPath = parsePaths(bootPaths);
					 
					text = text.substring(index + 1);
					index = text.indexOf("|"); //$NON-NLS-1$
					
					if (index > 0) {
						String extDirPaths = text.substring(0, index);
						String endorsedDirsPath = text.substring(index + 1);
						String[] extDirs = parsePaths(extDirPaths);
						String[] endDirs = parsePaths(endorsedDirsPath);
						return new LibraryInfo(version, bootPath, extDirs, endDirs);
					} 
				}
			}
		} 
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected String[] parsePaths(String paths) {
		List list = new ArrayList();
		int pos = 0;
		int index = paths.indexOf(File.pathSeparatorChar, pos);
		while (index > 0) {
			String path = paths.substring(pos, index);
			list.add(path);
			pos = index + 1;	
			index = paths.indexOf(File.pathSeparatorChar, pos);
		}
		String path = paths.substring(pos);
		if (!path.equals("null")) { //$NON-NLS-1$
			list.add(path);
		}
		return (String[])list.toArray(new String[list.size()]);
	}

}
