package com.tibco.cep.studio.debug.core.launch.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.emf.common.util.EList;

import com.tibco.be.util.BEStringUtilities;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.cep.studio.common.configuration.CustomFunctionLibEntry;
import com.tibco.cep.studio.common.configuration.NativeLibraryPath;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.ThirdPartyLibEntry;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.debug.core.StudioDebugCorePlugin;
import com.tibco.cep.studio.debug.core.launch.IStudioDebugLaunchConfigurationConstants;
import com.tibco.cep.studio.debug.core.launch.JVMProcessBuilder;
import com.tibco.cep.studio.debug.core.launch.RunProfile;
import com.tibco.cep.studio.debug.core.launch.RuntimeEnvironment;
import static com.tibco.cep.studio.debug.util.ProjectBuilder.updatePathforWhiteSpace;

public abstract class AbstractProcessBuilder implements JVMProcessBuilder {

    public static final String BRK = System.getProperty("line.separator", "\n");

    protected ProcessBuilder processBuilder;
    protected final Object processStartLock = new Object();
    protected String hostName;
    protected String port;
    RunProfile profile;
    protected boolean isDebugSession = false;
    public Properties runtimeTRA = null;
    protected ILaunch launch;
	protected IProgressMonitor monitor;
	//private VmInstall fVMInstance;
	private ILaunchConfiguration config;

    public AbstractProcessBuilder(ILaunch launch,ILaunchConfiguration config, IProgressMonitor monitor) {
    	this.launch = launch;
    	this.config = config;
    	if(monitor == null) {
    		this.monitor = new NullProgressMonitor();
    	} else {
    		this.monitor = monitor;
    	}
        this.profile = new DebugProfileImpl(config,monitor);
    }
    
    @Override
    public void init() throws CoreException {
    	IProgressMonitor subMonitor = new SubProgressMonitor(getMonitor(), 1);
		subMonitor.beginTask("Initializing Process Builder...",1); 
		subMonitor.subTask("Finding free socket..."); 
    	this.profile.init();
    	
    	int portNum = findOpenPort(0);
    	setPort(String.valueOf(portNum));
    	setHostName(RunProfile.DEFAULT_DEBUG_HOST);
    	
    	subMonitor.worked(1);
    	try {
			this.processBuilder = createProcessBuilder();
		} catch (FileNotFoundException e) {
			throw new CoreException(new Status(IStatus.ERROR,StudioDebugCorePlugin.PLUGIN_ID,"Unable to open "+getProfile().getEngineTra()+" file",e));
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR,StudioDebugCorePlugin.PLUGIN_ID,"Unable to load TRA properties",e));
		}
    }
    
    protected IProgressMonitor getMonitor() {
		return monitor;
	}

    public ProcessBuilder getProcessBuilder() {
        return processBuilder;
    }

    public RunProfile getProfile() {
        return profile;
    }

    public boolean isDebugSession() {
        return isDebugSession;
    }

    public void setDebugSession(boolean debugSession) {
        isDebugSession = debugSession;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	protected  Properties copyTra() throws FileNotFoundException, IOException{
    	IProgressMonitor subMonitor = new SubProgressMonitor(getMonitor(), 1);
		subMonitor.beginTask("Process Runtime properties...",1); 
		subMonitor.subTask("Load Default TRA...");
		
        String engineTra = getProfile().getEngineTra();
        
        
        

        Properties prop = new Properties();
        prop.load(new FileInputStream(engineTra));
        
        //Fix: BE-13703 : JMS Reconnection : When specified property 
        //"be.jms.reconnect.timeout" without prefix "java.property" in be-engine.tra file, it is not taking effect in Tester. 
        //After adding prefix it works.
        
        String jmsprop = "be.jms.reconnect.timeout"; 
        if (prop.get(jmsprop) != null) {
        	Object value = prop.getProperty(jmsprop);
        	prop.put("java.property." + jmsprop, value);
        	prop.remove(jmsprop);
        }
        
        subMonitor.worked(1);
        subMonitor.subTask("Processing extended properties...");
        StringBuffer minusXOpts = parseMinusXOptions(prop.getProperty(JAVA_EXTENDED_PROPERTIES));
        Map<String,String> minusDOpts = parseOptions("-D");
        Map<String,String> minusVOpts = parseOptions("-V");
        if(isDebugSession == true) {
            minusDOpts.put(SystemProperty.DEBUGGER_SERVICE_ENABLED.getPropertyName(),"true");
            minusXOpts.append(MINUS_X_DEBUGOPTS);
            minusXOpts.append(getPort());
        } else {
            minusDOpts.put(SystemProperty.DEBUGGER_SERVICE_ENABLED.getPropertyName(),"true");
            if(minusXOpts.indexOf("jdwp") == -1) {
            	minusXOpts.append(MINUS_X_DEBUGOPTS);
            	minusXOpts.append(getPort());
            }
        }
        prop.setProperty(JAVA_EXTENDED_PROPERTIES, minusXOpts.toString().trim());
        //minusXOpts.append(" -Xint ");
        subMonitor.worked(1);
        subMonitor.subTask("Processing System properties...");
        boolean foundEncoding = false;
        for (Entry<String, String> entry : minusDOpts.entrySet()) {
            if(entry.getKey().startsWith("file.encoding")) {
            	foundEncoding = true;
            }
        }
        if(!foundEncoding) {
        	String encoding = launch.getAttribute(DebugPlugin.ATTR_CONSOLE_ENCODING);
			if(encoding != null) {
				prop.setProperty("java.property." + "file.encoding", encoding);
			}			
        }
        for (Entry<String, String> e : minusDOpts.entrySet()) {
            prop.setProperty("java.property." + e.getKey(), e.getValue());
        }
        subMonitor.worked(1);
        subMonitor.subTask("Processing Client Variables...");
        for (Entry<String, String> e : minusVOpts.entrySet()) {
            prop.setProperty("tibco.clientVar." + (String)e.getKey(), (String)e.getValue());
        }
        subMonitor.worked(1);
        
        Properties profileProps = getProfileProperties(isDebugSession);
        prop.putAll(profileProps);
        /*StudioProjectConfiguration pconfig = StudioProjectConfigurationManager
        .getInstance().getProjectConfiguration(getProfile().getName());
        EList<ThirdPartyLibEntry> libs = pconfig.getThirdpartyLibEntries();
        String extPath =(String) prop.get("tibco.class.path.extended");
        for(ThirdPartyLibEntry lib: libs) {
        	extPath += "%PSP%"+lib.getPath();
        }
        EList<CustomFunctionLibEntry> entries = pconfig.getCustomFunctionLibEntries();
        for (CustomFunctionLibEntry entry : entries) {
        	extPath += "%PSP%"+entry.getPath();
		}*/
        // java lib paths
        List<String> llist = new ArrayList<String>();
        llist.addAll(getThirdPartyLibPaths(getProfile().getName(),false));
        llist.addAll(getCustomFunctionLibPaths(getProfile().getName(),false));
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< llist.size();i++) {
        	if(i > 0 ){
        		sb.append("%PSP%");
        	}
        	sb.append(llist.get(i));
        }
        prop.put("be.project.libs",sb.toString());
        // native lib paths moved from runtimeenvironmentimpl to here as it is related to the libs themselves
        List<String> nlist = new ArrayList<String>();
        nlist.addAll(getThirdPartyLibPaths(getProfile().getName(),true));
        nlist.addAll(getCustomFunctionLibPaths(getProfile().getName(),true));
        StringBuilder nsb = new StringBuilder();
        for(int i=0; i< nlist.size();i++) {
        	if(i > 0 ){
        		nsb.append("%PSP%");
        	}
        	nsb.append(nlist.get(i));
        }
        prop.put("be.project.libs.native",nsb.toString());
        
        String extPath =(String) prop.get("tibco.class.path.extended");
        prop.put("tibco.class.path.extended", extPath+"%PSP%"+(String)prop.get("be.project.libs"));
        prop.put("java.property.java.class.path", extPath+"%PSP%"+(String)prop.get("be.project.libs"));

        SortedMap map = new TreeMap(new TraComparator());
        map.putAll(prop);

        subMonitor.worked(1);
        subMonitor.subTask("Generating Debug TRA in memory...");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        writeMap(map, baos, "###### Generated TRA from '" + profile.getEngineTra() + "'",true);
        Properties props = new Properties();
        props.load(new InputStreamReader(new ByteArrayInputStream(baos.toByteArray()),"UTF-8"));
        subMonitor.worked(1);
        return props;

    }
    
    
   
    
    private String normalize(String libpath) {
    	IResource initSel = null;
		IPath path = new Path(libpath);
		if(path.isAbsolute()) {
			return path.toOSString();
		} else {
			IWorkspaceRoot root= ResourcesPlugin.getWorkspace().getRoot();
			if (libpath.length() > 0) {
				initSel= root.findMember(new Path(libpath));
			}
			if (initSel == null) {
				return null;
			} else {
				return initSel.getLocation().toOSString();
			}
		}
	}
    
    @SuppressWarnings("unchecked")
	private List<String> getThirdPartyLibPaths(String projectName,boolean findNative) {
    	StringBuilder libPathBuilder = new StringBuilder();
    	List<String> llist = new ArrayList<String>();
    	try {
			List<String> tplList = this.config.getAttribute(
					IStudioDebugLaunchConfigurationConstants.ATTR_THIRD_PARTY_LIBRARIES,
					Collections.EMPTY_LIST);
			for(String libPath: tplList){
				libPathBuilder.append("%PSP%");
				if(libPath.equals(IStudioDebugLaunchConfigurationConstants.DEFAULT_THIRD_PARTY_LIB_NAME)) {
					StudioProjectConfiguration pconfig = 
						StudioProjectConfigurationManager.getInstance()
						.getProjectConfiguration(projectName);
					EList<ThirdPartyLibEntry> libs = pconfig.getThirdpartyLibEntries();
					for(ThirdPartyLibEntry lib: libs) {
						if(findNative) {
							NativeLibraryPath nativeLibLocation = lib.getNativeLibraryLocation();
							if(nativeLibLocation != null && nativeLibLocation.getPath()!= null && !nativeLibLocation.getPath().isEmpty()){
								String nativeLibpath = normalize(nativeLibLocation.getPath());
								llist.add(nativeLibpath);
							}
						} else {
							String path = lib.getPath(lib.isVar());
							//path = updatePathforWhiteSpace(path);
							libPathBuilder.append(path);
							libPathBuilder.append("%PSP%");
							llist.add(path);
						}
						
					}
				} else {
					libPathBuilder.append(libPath);
					llist.add(libPath);
				}
			}
		} catch (CoreException e) {
			StudioDebugCorePlugin.log(e);
		}
        //return libPathBuilder.toString();
    	return llist;
        
    }

    @SuppressWarnings("unchecked")
	private List<String> getCustomFunctionLibPaths(String projectName,boolean findNative) {
    	StringBuilder libPathBuilder = new StringBuilder();
    	List<String> llist = new ArrayList<String>();
    	try {
			List<String> customFnList = this.config.getAttribute(
					IStudioDebugLaunchConfigurationConstants.ATTR_CUSTOM_FN_LIBRARIES,
					Collections.EMPTY_LIST);
			for(String libPath: customFnList){
				libPathBuilder.append("%PSP%");
				if(libPath.equals(IStudioDebugLaunchConfigurationConstants.DEFAULT_CUSTOM_FN_LIB_NAME)) {
					StudioProjectConfiguration pconfig = 
						StudioProjectConfigurationManager.getInstance()
						.getProjectConfiguration(projectName);
					EList<CustomFunctionLibEntry> libs = pconfig.getCustomFunctionLibEntries();
					for(CustomFunctionLibEntry lib: libs) {
						if(findNative) {
							NativeLibraryPath nativeLibLocation = lib.getNativeLibraryLocation();
							if(nativeLibLocation != null && nativeLibLocation.getPath()!= null && !nativeLibLocation.getPath().isEmpty()){
								String nativeLibpath = normalize(nativeLibLocation.getPath());
								llist.add(nativeLibpath);
							}
						} else {
							String path = lib.getPath(lib.isVar());
							//path = updatePathforWhiteSpace(path);
							libPathBuilder.append(path);
							libPathBuilder.append("%PSP%");
							llist.add(path);
						}
					}
				} else {
					libPathBuilder.append(libPath);
					llist.add(libPath);
				}
			}
		} catch (CoreException e) {
			StudioDebugCorePlugin.log(e);
		}
//    	return libPathBuilder.toString();
        return llist;
    }
    
    //-agentlib:jdwp=transport=dt_socket,suspend=y,address=localhost:1059
    //"-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=127.0.0.1:";
    /*private String getDebugOptions() {
    	StringBuffer arguments = new StringBuffer();
    	// VM arguments are the first thing after the java program so that users can specify
		// options like '-client' & '-server' which are required to be the first options
		double version = getJavaVersion();
		if (version < 1.5) {
			arguments.append("-Xdebug"); //$NON-NLS-1$
			arguments.append("-Xnoagent"); //$NON-NLS-1$
		}
		
		//check if java 1.4 or greater
		if (version < 1.4) {
			arguments.append("-Djava.compiler=NONE"); //$NON-NLS-1$
		}
		if (version < 1.5) { 
			arguments.append("-Xrunjdwp:transport=dt_socket,suspend=y,address=localhost:" + port); //$NON-NLS-1$
		} else {
			arguments.append("-agentlib:jdwp=transport=dt_socket,suspend=y,address=localhost:" + port); //$NON-NLS-1$
		}
		return arguments.toString();
    }*/



	/**
	 * Returns the version of the current VM in use
	 * @return the VM version
	 */
	/*private double getJavaVersion() {
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

	}*/
	
	abstract protected Properties getProfileProperties(boolean isDebugSession);

    abstract protected RuntimeEnvironment getRuntimeEnvironment(Properties traProperties);

    protected ProcessBuilder createProcessBuilder() throws FileNotFoundException, IOException  {
        File currentDirectory = new File(profile.getWorkingDir());
        runtimeTRA = copyTra();
        RuntimeEnvironment runtimeEnv = getRuntimeEnvironment(runtimeTRA);
        ProcessBuilder pb = runtimeEnv.newProcessBuilder();
        pb.directory(currentDirectory);
        pb.redirectErrorStream(true);

        return pb;

    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Properties getRuntimeTRA() {
        return runtimeTRA;
    }

    protected static int findOpenPort(int port) {
        port = getServerPort(port);
        if(port == -1) {
        	port = getServerPort(0);
        }
        return port;
    }

    private static int getServerPort(int port) {
    	ServerSocket socket= null;
		try {
			socket= new ServerSocket(port);
			socket.setReuseAddress(false);
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

    protected StringBuffer parseMinusXOptions(String extProps) {
        boolean foundXmsSize = false;
        boolean foundXmxSize = false;
        ArrayList<String> agents = new ArrayList<String>();
        String msSize="256m";
        String mxSize="256m";
        String [] extPropSplt;
        ArrayList<String> loadedProps = new ArrayList<String>();
        ArrayList<String> verboseProps = new ArrayList<String>();
        StringBuffer buf = new StringBuffer();

        if(extProps != null && extProps.length() > 0) {
            extPropSplt = extProps.split(" ");
            for (String s : extPropSplt) {
                if (s.startsWith("-D") || s.startsWith("-V")) {
                    continue;
                }
                if (s.startsWith("-Xms")) {
                    foundXmsSize = true;
                    msSize = s.substring("-Xms".length());
                } else if (s.startsWith("-Xmx")) {
                    foundXmxSize = true;
                    mxSize = s.substring("-Xmx".length());
                } else if (s.startsWith("-javaagent:")) {
                    agents.add(s);
                } else if( s.startsWith("-verbose:")){
                	verboseProps.add(s);
                } else {
                    loadedProps.add(s);
                }
            }
        }
        String[] splts = profile.getVmArgs().split(" ");
        for (String s:splts) {
            if ((s.startsWith("-D") || s.startsWith("-V") || s.length() < 1) ||
            		s.trim().equals("")) {
                continue;
            }
            if(s.startsWith("-Xms")) {
                foundXmsSize = true;
                msSize = s.substring("-Xms".length());
            } else if(s.startsWith("-Xmx")) {
                foundXmxSize = true;
                mxSize = s.substring("-Xmx".length());
            } else if(s.startsWith("-javaagent:")) {
                if(!agents.contains(s)) {
                    agents.add(s);
                }
            }else if(s.startsWith("-verbose:")) {
            	if(!verboseProps.contains(s)){
            		verboseProps.add(s);
            	}
            } else {
                buf.append(s);
                buf.append(" ");
            }
        }
        if(foundXmsSize && !foundXmxSize) {
            if(System.getProperty("file.separator").equals("/")) {
                buf.append("-Xms"+msSize).append(" ");
                buf.append("-Xmx"+msSize).append(" ");
            }
        } else if(!foundXmsSize && foundXmxSize) {
            buf.append("-Xms"+mxSize).append(" ");
            buf.append("-Xmx"+mxSize).append(" ");
        } else if(foundXmsSize && foundXmxSize ) {
            buf.append("-Xms"+msSize).append(" ");
            buf.append("-Xmx"+mxSize).append(" ");
        }
        for(String s:loadedProps) {
            if(buf.indexOf(s) == -1) {
                buf.append(s).append(" ");
            }
        }
     // add the javaagents
        for(String s: verboseProps) {
            buf.append(s).append(" ");
        }
        // add the javaagents
        for(String s: agents) {
            buf.append(s).append(" ");
        }
        
        return buf;

    }

    protected Map<String,String> parseOptions(String option) {

        String[] splts = profile.getVmArgs().split(" ");
        Map<String,String> map = new HashMap<String,String>();

        for (String s:splts) {
            if (s.startsWith(option)) {
                s = s.substring(option.length());
                String[] equalSplit = s.split("=");
                map.put(equalSplit[0], equalSplit[1]);

            }
        }
        return map;
    }

    @SuppressWarnings("rawtypes")
	protected void writeMap(Map map, OutputStream out, String comment,boolean escape) throws IOException  {

        Set entrySet = map.entrySet();

        StringBuffer buf = new StringBuffer();
        buf.append(comment).append(BRK);
        for (Object o : entrySet) {
            Map.Entry e = (Map.Entry) o;
            if(escape) {
            	buf.append(e.getKey()).append("=").append(BEStringUtilities.escape((String) e.getValue())).append(BRK);
            } else {
            	buf.append(e.getKey()).append("=").append((String) e.getValue()).append(BRK);
            }
        }
        buf.append("######## end #######"+ BRK);
        out.write(buf.toString().getBytes("UTF-8"));
        out.flush();


    }

    public String[] getCommand() {
    	List<String> cmd = processBuilder.command();
		return cmd.toArray(new String[cmd.size()]);
	}

	public String getCommandLineString() {
		StringBuilder sb = new StringBuilder();
		String[] cmds = getCommand();
		for( String s: cmds) {
			sb.append(s).append(" ");
		}
		return sb.toString();
	}

	class TraComparator implements Comparator<Object> {
        public int compare(Object o1, Object o2) {
            String key1 = o1.toString();
            String key2 = o2.toString();

            if (key1.equals("tibco.env.PSP")) return -1;

            if (key1.startsWith("tibco.env")) {
                if (key2.startsWith("tibco.env")) {
                    return key1.compareTo(key2);
                }
                return -1; //always smaller
            }

            if (key2.startsWith("tibco.env")) {
                return 1;
            }
            return key1.compareTo(key2);
        }
    }
	
	

}
