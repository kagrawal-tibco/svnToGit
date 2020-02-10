package com.tibco.cep.studio.debug.core.launch;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.Launch;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamsProxy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;

public class VmInstall {
	
	private VmType fType;
	private String fId;
	private String fName;
	private File fInstallLocation;
	private LibraryLocation[] fSystemLibraryDescriptions;
	private URL fJavadocLocation;
	private String fVMArgs;
	/**
	 * Map VM specific attributes that are persisted restored with a VM install.
	 * @since 3.4
	 */
	@SuppressWarnings("rawtypes")
	private Map fAttributeMap = new HashMap();
	public static final String VAR_PORT = "${port}";
	private static final String PREF_VM_INSTALL_SYSTEM_PROPERTY = "PREF_VM_INSTALL_SYSTEM_PROPERTY"; //$NON-NLS-1$
	
	/**
	 * Constructs a new VM install.
	 * 
	 * @param	type	The type of this VM install.
	 * 					Must not be <code>null</code>
	 * @param	id		The unique identifier of this VM instance
	 * 					Must not be <code>null</code>.
	 * @throws	IllegalArgumentException	if any of the required
	 * 					parameters are <code>null</code>.
	 */
	public VmInstall(VmType type, String id) {
		if (type == null)
			throw new IllegalArgumentException("VM type cannot be null"); 
		if (id == null)
			throw new IllegalArgumentException("id cannot be null"); 
		fType= type;
		fId= id;
	}

	/* (non-Javadoc)
	 * Subclasses should not override this method.
	 * @see IVMInstall#getId()
	 */
	public String getId() {
		return fId;
	}

	/* (non-Javadoc)
	 * Subclasses should not override this method.
	 * @see IVMInstall#getName()
	 */
	public String getName() {
		return fName;
	}

	/* (non-Javadoc)
	 * Subclasses should not override this method.
	 * @see IVMInstall#setName(String)
	 */
	public void setName(String name) {
		if (!name.equals(fName)) {
			fName= name;
		}
	}

	/* (non-Javadoc)
	 * Subclasses should not override this method.
	 * @see IVMInstall#getInstallLocation()
	 */
	public File getInstallLocation() {
		return fInstallLocation;
	}

	/* (non-Javadoc)
	 * Subclasses should not override this method.
	 * @see IVMInstall#setInstallLocation(File)
	 */
	public void setInstallLocation(File installLocation) {
		if (!installLocation.equals(fInstallLocation)) {
			fInstallLocation= installLocation;
		}
	}

	/* (non-Javadoc)
	 * Subclasses should not override this method.
	 * @see IVMInstall#getVMInstallType()
	 */
	public VmType getVMInstallType() {
		return fType;
	}

	/* (non-Javadoc)
	 * @see IVMInstall#getVMRunner(String)
	 */
//	public IVMRunner getVMRunner(String mode) {
//		return null;
//	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.launching.IVMInstall#getLibraryLocations()
	 */
	public LibraryLocation[] getLibraryLocations() {
		return fSystemLibraryDescriptions;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.launching.IVMInstall#setLibraryLocations(org.eclipse.jdt.launching.LibraryLocation[])
	 */
	public void setLibraryLocations(LibraryLocation[] locations) {
		if (locations == fSystemLibraryDescriptions) {
			return;
		}
		LibraryLocation[] newLocations = locations;
		if (newLocations == null) {
			newLocations = getVMInstallType().getDefaultLibraryLocations(getInstallLocation()); 
		}
		LibraryLocation[] prevLocations = fSystemLibraryDescriptions;
		if (prevLocations == null) {
			prevLocations = getVMInstallType().getDefaultLibraryLocations(getInstallLocation()); 
		}
		
		if (newLocations.length == prevLocations.length) {
			int i = 0;
			boolean equal = true;
			while (i < newLocations.length && equal) {
				equal = newLocations[i].equals(prevLocations[i]);
				i++;
			}
			if (equal) {
				// no change
				return;
			}
		}

		fSystemLibraryDescriptions = locations;		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.launching.IVMInstall#getJavadocLocation()
	 */
	public URL getJavadocLocation() {
		return fJavadocLocation;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.launching.IVMInstall#setJavadocLocation(java.net.URL)
	 */
	public void setJavadocLocation(URL url) {
		if (url == fJavadocLocation) {
			return;
		}
		if (url != null && fJavadocLocation != null) {
			if (url.equals(fJavadocLocation)) {
				// no change
				return;
			}
		}
		
		fJavadocLocation = url;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
     * @since 2.1
	 */
	public boolean equals(Object object) {
		if (object instanceof VmInstall) {
			VmInstall vm = (VmInstall)object;
			return getVMInstallType().equals(vm.getVMInstallType()) &&
				getId().equals(vm.getId());
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 * @since 2.1
	 */
	public int hashCode() {
		return getVMInstallType().hashCode() + getId().hashCode();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.launching.IVMInstall#getDefaultVMArguments()
	 * @since 3.0
	 */
	public String[] getVMArguments() {
		String args = getVMArgs();
		if (args == null) {
		    return null;
		}
		ExecutionArguments ex = new ExecutionArguments(args, ""); //$NON-NLS-1$
		return ex.getVMArgumentsArray();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.launching.IVMInstall#setDefaultVMArguments(java.lang.String[])
	 * @since 3.0
	 */
	public void setVMArguments(String[] vmArgs) {
		if (vmArgs == null) {
			setVMArgs(null);
		} else {
		    StringBuffer buf = new StringBuffer();
		    for (int i = 0; i < vmArgs.length; i++) {
	            String string = vmArgs[i];
	            buf.append(string);
	            buf.append(" "); //$NON-NLS-1$
	        }
			setVMArgs(buf.toString().trim());
		}
	}
	
    /* (non-Javadoc)
     * @see org.eclipse.jdt.launching.IVMInstall2#getVMArgs()
     */
    public String getVMArgs() {
        return fVMArgs;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jdt.launching.IVMInstall2#setVMArgs(java.lang.String)
     */
    public void setVMArgs(String vmArgs) {
        if (fVMArgs == null) {
            if (vmArgs == null) {
                // No change
                return;
            }
        } else if (fVMArgs.equals(vmArgs)) {
    		// No change
    		return;
    	}
        fVMArgs = vmArgs;
    }	
    
   
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.launching.IVMInstall3#evaluateSystemProperties(java.lang.String[], org.eclipse.core.runtime.IProgressMonitor)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map evaluateSystemProperties(String[] properties, IProgressMonitor monitor) throws CoreException {
		//locate the launching support jar - it contains the main program to run
		if (monitor == null) {
			monitor = new NullProgressMonitor();
		}
		Map map = new HashMap();
	
		// launch VM to evaluate properties
		File file = StudioDebugCorePlugin.getFileInPlugin(new Path("lib/launchingsupport.jar")); //$NON-NLS-1$
		if (file.exists()) {
			String javaVersion = getJavaVersion();
			boolean hasXMLSupport = false;
			if (javaVersion != null) {
				hasXMLSupport = true;
				if (javaVersion.startsWith("1.1") ||
						javaVersion.startsWith("1.2") ||
						javaVersion.startsWith("1.3")) {
					hasXMLSupport = false;
				}
			}
			String mainType = null;
			if (hasXMLSupport) {
				mainType = "org.eclipse.jdt.internal.launching.support.SystemProperties"; //$NON-NLS-1$
			} else {
				mainType = "org.eclipse.jdt.internal.launching.support.LegacySystemProperties"; //$NON-NLS-1$
			}
			VmRunnerConfig config = new VmRunnerConfig(mainType, new String[]{file.getAbsolutePath()});
			VmRunner runner = getVMRunner(ILaunchManager.RUN_MODE);
			if (runner == null) {
				abort("Unable to retrieve system properties", null, -1);
			}
			config.setProgramArguments(properties);
			Launch launch = new Launch(null, ILaunchManager.RUN_MODE, null);
			if (monitor.isCanceled()) {
				return map;
			}
			monitor.beginTask("Evaluating system properties", 2);
			runner.run(config, launch, monitor);
			IProcess[] processes = launch.getProcesses();
			if (processes.length != 1) {
				abort("Unable to retrieve system properties", null, -1);
			}
			IProcess process = processes[0];
			try {
				int total = 0;
				while (!process.isTerminated()) {
					try {
						if (total > 3000) {
							break;
						}
						Thread.sleep(50);
						total+=50;
					} catch (InterruptedException e) {
					}
				}
			} finally {
				if (!launch.isTerminated()) {
					launch.terminate();
				}
			}
			monitor.worked(1);
			if (monitor.isCanceled()) {
				return map;
			}
			
			monitor.subTask("Reading system properties");
			IStreamsProxy streamsProxy = process.getStreamsProxy();
			String text = null;
			if (streamsProxy != null) {
				text = streamsProxy.getOutputStreamMonitor().getContents();
			}
			if (text != null && text.length() > 0) {
				try {
					DocumentBuilder parser = StudioDebugCorePlugin.getParser();
					Document document = parser.parse(new ByteArrayInputStream(text.getBytes()));
					Element envs = document.getDocumentElement();
					NodeList list = envs.getChildNodes();
					int length = list.getLength();
					for (int i = 0; i < length; ++i) {
						Node node = list.item(i);
						short type = node.getNodeType();
						if (type == Node.ELEMENT_NODE) {
							Element element = (Element) node;
							if (element.getNodeName().equals("property")) { //$NON-NLS-1$
								String name = element.getAttribute("name"); //$NON-NLS-1$
								String value = element.getAttribute("value"); //$NON-NLS-1$
								map.put(name, value);
							}
						}
					}			
				} catch (SAXException e) {
					abort("Exception retrieving system properties", e, -1);
				} catch (IOException e) {
					abort("Exception retrieving system properties", e, -1);
				}
			} else {
				abort("Unable to retrieve system properties", null, -1);
			}
			monitor.worked(1);
		} else {
			abort("Unable to retrieve system properties", null, -1);
		}
		
		monitor.done();
		return map;
	}

	/**
	 * Generates a key used to cache system property for this VM in this plug-ins
	 * preference store.
	 * 
	 * @param property system property name
	 * @return preference store key
	 */
	@SuppressWarnings("unused")
	private String getSystemPropertyKey(String property) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(PREF_VM_INSTALL_SYSTEM_PROPERTY);
		buffer.append("."); //$NON-NLS-1$
		buffer.append(getVMInstallType().getId());
		buffer.append("."); //$NON-NLS-1$
		buffer.append(getId());
		buffer.append("."); //$NON-NLS-1$
		buffer.append(property);
		return buffer.toString();
	}
	
	/**
	 * Throws a core exception with an error status object built from the given
	 * message, lower level exception, and error code.
	 * 
	 * @param message the status message
	 * @param exception lower level exception associated with the error, or
	 *            <code>null</code> if none
	 * @param code error code
	 * @throws CoreException the "abort" core exception
	 * @since 3.2
	 */
	protected void abort(String message, Throwable exception, int code) throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR, StudioDebugCorePlugin
				.getUniqueIdentifier(), code, message, exception));
	}	
	
	/**
	 * Sets a VM specific attribute. Attributes are persisted and restored with VM installs.
	 * Specifying a value of <code>null</code> as a value removes the attribute. Change
	 * notification is provided to {@link IVMInstallChangedListener} for VM attributes.
	 * 
	 * @param key attribute key, cannot be <code>null</code>
	 * @param value attribute value or <code>null</code> to remove the attribute
	 * @since 3.4
	 */
	@SuppressWarnings("unused")
	public void setAttribute(String key, String value) {
		String prevValue = (String) fAttributeMap.remove(key);
	}
    
	/**
	 * Returns a VM specific attribute associated with the given key or <code>null</code> 
	 * if none.
	 * 
	 * @param key attribute key, cannot be <code>null</code>
	 * @return attribute value, or <code>null</code> if none
	 * @since 3.4
	 */
	public String getAttribute(String key) {
		return (String) fAttributeMap.get(key);
	}
	
	/**
	 * Returns a map of VM specific attributes stored with this VM install. Keys
	 * and values are strings. Modifying the map does not modify the attributes
	 * associated with this VM install.
	 * 
	 * @return map of VM attributes
	 * @since 3.4
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getAttributes() {
		return new HashMap(fAttributeMap);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.launching.IVMInstall#getVMRunner(java.lang.String)
	 */
	public VmRunner getVMRunner(String mode) {
		if (ILaunchManager.RUN_MODE.equals(mode)) {
			return new VmRunner(this);
		} else if (ILaunchManager.DEBUG_MODE.equals(mode)) {
			return new VmDebugger(this);
		}
		return null;
	}

    /* (non-Javadoc)
     * @see org.eclipse.jdt.launching.IVMInstall#getJavaVersion()
     */
    public String getJavaVersion() {
        VmType installType = (VmType) getVMInstallType();
        File installLocation = getInstallLocation();
        if (installLocation != null) {
            File executable = getJavaExecutable();
            if (executable != null) {
                String vmVersion = installType.getVMVersion(installLocation, executable);
                // strip off extra info
                StringBuffer version = new StringBuffer();
                for (int i = 0; i < vmVersion.length(); i++) {
                    char ch = vmVersion.charAt(i);
                    if (Character.isDigit(ch) || ch == '.') {
                        version.append(ch);
                    } else {
                        break;
                    }
                }
                if (version.length() > 0) {
                    return version.toString();
                }
            }
        }
        return null;
    }
    
    /**
     * Returns the java executable for this VM or <code>null</code> if cannot be found
     * 
     * @return executable for this VM or <code>null</code> if none
     */
    File getJavaExecutable() {
    	File installLocation = getInstallLocation();
        if (installLocation != null) {
            return VmType.findJavaExecutable(installLocation);
        }
        return null;
    }    
    
    /**
     * Returns arguments used to start this VM in debug mode or 
     * <code>null</code> if default arguments should be used.
     * 
     * @return arguments used to start this VM in debug mode
     * or <code>null</code> if default arguments should be used
     */
    public String getDebugArgs() {
    	return null;
    }
	

}
