package com.tibco.cep.studio.debug.core.model.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.studio.debug.core.launch.RunProfile;
import com.tibco.cep.studio.debug.core.launch.RuntimeEnvironment;
import com.tibco.cep.studio.debug.util.ProjectBuilder;

public class RuntimeEnvironmentImpl implements RuntimeEnvironment {
    private BEProperties env;
    static final String MAIN_ENTRY_POINT = "com.tibco.cep.container.standalone.BEMain";
    static final String SPACE = " ";
    public static final String BRK = System.getProperty("line.separator", "\n");
	private RunProfile profile;


    public RuntimeEnvironmentImpl(Properties traProps,RunProfile profile) {
    	this.profile = profile;
        env = new BEProperties();
        env.putAll(traProps);
        env.putAll(System.getProperties());
        env.substituteTibcoEnvironmentVariables();
        env.substituteTibcoEnvironmentVariables(); //Yes - Call once more - It is for resolving some other variables.
    }
    
    public RunProfile getProfile() {
		return profile;
	}


    public String getJavaExecutablePath() {
        File jvmExec = null;
        if(env.getProperty("tibco.env.TIB_JAVA_HOME") != null ) {
            File javaHome = new File(env.getProperty("tibco.env.TIB_JAVA_HOME"));
            if(javaHome.exists()) {
                String execSuffix = "";
                if(File.separator.equals("\\")) {
                    execSuffix = ".exe";
                }
                jvmExec = new File(javaHome,"bin" + File.separator + "java"+ execSuffix);
                if(jvmExec.exists()) {
                    try {
                        return jvmExec.getCanonicalPath();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    throw new RuntimeException("Java executable not found at directory indicated by property tibco.env.TIB_JAVA_HOME");
                }
            } else {
                throw new RuntimeException("Directory indicated by property tibco.env.TIB_JAVA_HOME does not exist :"+javaHome.getPath());
            }
        } else {
            throw new RuntimeException("Property tibco.env.TIB_JAVA_HOME not set in be-engine.tra TRA file");
        }
    }

    public Properties getEnvironment() {
        return env;
    }

    @SuppressWarnings("rawtypes")
	public ProcessBuilder newProcessBuilder() {

        ArrayList<String> processArgs = new ArrayList<String>();

        processArgs.add(checkCommand(getJavaExecutablePath()));

        processExtendedProperties(processArgs);
        
        
        

        Properties minusDProp = env.getBranch("java.property");
        for (Object o : minusDProp.entrySet()) {
            StringBuffer buffer = new StringBuffer();
            Map.Entry e = (Map.Entry) o;
            if(e.getKey().equals("BE_HOME")) {
            	buffer.append("-Dtibco.env.").append(e.getKey()).append("=").append((String) e.getValue());
            	processArgs.add(checkCommand(buffer.toString()));
            	buffer=new StringBuffer();
            	buffer.append("-D").append(e.getKey()).append("=").append((String) e.getValue());
            	processArgs.add(checkCommand(buffer.toString()));
            } else {
            	buffer.append("-D").append(e.getKey()).append("=").append((String) e.getValue());
            	processArgs.add(checkCommand(buffer.toString()));
            }
        }

        Properties minusVProp = env.getBranch("tibco.clientVar");
        for (Object o : minusVProp.entrySet()) {
            StringBuffer buffer = new StringBuffer();
            Map.Entry e = (Map.Entry) o;
            buffer.append("-Dtibco.clientVar.").append(e.getKey()).append("=").append((String) e.getValue());
            processArgs.add(checkCommand(buffer.toString()));
        }

//        String extDirPath = fixExtDirPath(env.getString("tibco.class.path.extended"));
        String extDirPath = fixExtDirPath(cleanPath(env.getString("tibco.class.path.extended")));
        
        
        // processArgs.add("-Djava.ext.dirs=" + "\"" + extDirPath + File.pathSeparator + "\"" );
        
        String[] pathArr = extDirPath.split(File.pathSeparator);
        for(int i=0 ;i<pathArr.length; i++) {
			if(!pathArr[i].endsWith(".jar")) {
				pathArr[i] = pathArr[i] + "/*";
			}
		}
		String updatedExtDirPath = String.join(File.pathSeparator, pathArr);
        
        
        processArgs.add("-cp" );
        processArgs.add("\"" + updatedExtDirPath + File.pathSeparator + "\"" );
        
        processJavaLibraryPath(processArgs);
        
        //processProjectLibraries(processArgs);

        processArgs.add(profile.getEntryPoint());
        processArgs.add("-c");
        processArgs.add(profile.getCddFilePath());
        processArgs.add("-u");
        processArgs.add(profile.getUnitName());
        processArgs.add(env.getString("tibco.repourl"));
        
        processStartupOptions(processArgs);
	
        return new ProcessBuilder(processArgs);

    }

	protected void processStartupOptions(ArrayList<String> processArgs) {
		String[] options = {"--propFile", "--propVar", "-p", "-n"};
		String[] splits = profile.getCmdStartUpOpts().split(" ");
		List<String> splitList = Arrays.asList(splits);
		if (splitList.contains("-h")) {
			processArgs.add("-h");
		}
		for (String opt : options) {
			if (splitList.contains(opt)) {
				int index = splitList.indexOf(opt);
				String val = splitList.get(index + 1);
				processArgs.add(opt);
				processArgs.add(val);
			}
		}
    }
    
    private String cleanPath(String string) {
    	String path = string;
		String[] pArray = path.split(File.pathSeparator);
		StringBuilder sb = new StringBuilder();
		List<String> plist = new ArrayList<String>();
		for(int i=0;i<pArray.length;i++) {
			if(pArray[i] != null && !pArray[i].isEmpty()&& !plist.contains(pArray[i])){
				if(i>0){
					sb.append(File.pathSeparator);
				}
				sb.append(pArray[i].trim());
				plist.add(pArray[i]);
				
			}
		}
		return sb.toString();
	}

	@SuppressWarnings("unused")
	private void processProjectLibraries(ArrayList<String> processArgs) {
    	String libs = env.getString("be.project.libs");
    	if(libs != null && !libs.isEmpty()){
    		StringBuilder sb = new StringBuilder().append("-classpath").append(" \"").append(libs).append(" \"");
    		processArgs.add(sb.toString());
    	}
    }
    private void processJavaLibraryPath(ArrayList<String> processArgs) {
    	String libs = env.getString("be.project.libs.native");
    	if(libs != null && !libs.isEmpty()){
	    	StringBuilder sb = new StringBuilder().append("-Djava.library.path=").append(libs);
			processArgs.add(sb.toString());
    	}
	}
    

//	private void processLibraryPathOld(ArrayList<String> processArgs) {
//    	String [] javaLibraryPath = getLibraryPaths();
//    	if (javaLibraryPath != null && javaLibraryPath.length > 0) {
//			StringBuffer path = new StringBuffer();
//			path.append("-Djava.library.path="); //$NON-NLS-1$
//			path.append("\""); //$NON-NLS-1$
//			for (int i = 0; i < javaLibraryPath.length; i++) {
//				if (i > 0) {
//					path.append(File.pathSeparatorChar);
//				}
//				path.append(javaLibraryPath[i]);
//			}
//			path.append("\""); //$NON-NLS-1$
//			processArgs.add(path.toString());
//		}
//		
//	}
    
//    String [] getLibraryPaths() {
//    	List<String> libpaths = new ArrayList<String>();
//    	StudioProjectConfiguration pconfig = StudioProjectConfigurationManager
//        .getInstance().getProjectConfiguration(getProfile().getName());
//        EList<ThirdPartyLibEntry> libs = pconfig.getThirdpartyLibEntries();
//        for(ThirdPartyLibEntry lib: libs) {
//        	NativeLibraryPath nativeLibLocation = lib.getNativeLibraryLocation();
//        	if(nativeLibLocation != null) {
//        		if(nativeLibLocation.getPath() != null && !nativeLibLocation.getPath().isEmpty()) {
//        			String nativeLibpath = normalize(nativeLibLocation.getPath());
//        			if(nativeLibpath != null) {
//        				libpaths.add(nativeLibpath);
//        			}
//        		}
//        	}
//        }
//        EList<CustomFunctionLibEntry> entries = pconfig.getCustomFunctionLibEntries();
//        for (CustomFunctionLibEntry entry : entries) {
//        	NativeLibraryPath nativeLibLocation = entry.getNativeLibraryLocation();
//        	if(nativeLibLocation != null) {
//        		if(nativeLibLocation.getPath() != null && !nativeLibLocation.getPath().isEmpty()) {
//        			String nativeLibpath = normalize(nativeLibLocation.getPath());
//        			if(nativeLibpath != null) {
//        				libpaths.add(nativeLibpath);
//        			}
//        		}
//        	}
//		}
//        return libpaths.toArray(new String [libpaths.size()]);    	
//    }
    
//    private String normalize(String libpath) {
//    	IResource initSel = null;
//		IPath path = new Path(libpath);
//		if(path.isAbsolute()) {
//			return path.toOSString();
//		} else {
//			IWorkspaceRoot root= ResourcesPlugin.getWorkspace().getRoot();
//			if (libpath.length() > 0) {
//				initSel= root.findMember(new Path(libpath));
//			}
//			if (initSel == null) {
//				return null;
//			} else {
//				return initSel.getLocation().toOSString();
//			}
//		}
//	}


	private String fixExtDirPath(String extDirs) {

		ArrayList<File> cpjars = new ArrayList<File>();
		String[] urls = extDirs.split(File.pathSeparator);
		int count = 0;
		StringBuilder sb = new StringBuilder();
		for (String url : urls) {
			if(url.isEmpty()) continue;
			IPath path = new Path(url);

			if (path.getFileExtension() != null) {
				path = path.removeFileExtension();
				path = path.removeLastSegments(1);
			} 
			if (count > 0)
				sb.append(File.pathSeparator);
			sb.append(ProjectBuilder.updatePathforWhiteSpace(path.toPortableString()));
			count++;
			// File f = new File(url);
			// if(f.exists()) {
			// if(f.isDirectory()) {
			// cpjars.add(f);
			// } else if( f.isFile()) {
			// if( f.getName().endsWith(".jar")) {
			// cpjars.add(f.getParentFile());
			// }
			// }
			// }
		}
		// StringBuilder sb = new StringBuilder();
		// int i=0;
		// for(File f: cpjars) {
		// if(i > 0 ) {
		// sb.append(File.pathSeparator);
		// }
		// sb.append(f.getPath());
		// i++;
		// }
		return sb.toString();
	}

    /*String getClassPathFromExtDirs(String extDirs) {
        ArrayList<File> cpjars = new ArrayList<File>();
        String[] urls = extDirs.split(File.pathSeparator);
        for(String url:urls) {
            File f = new File(url);
            if(f.exists()) {
                if(f.isDirectory()) {
                    cpjars.add(f);
                    File [] jarFiles = f.listFiles(new FilenameFilter() {
                        public boolean accept(File file, String name) {
                            return name.endsWith(".jar");
                        }
                    });
                    if(jarFiles.length > 0) {
                       for(File jarFile: jarFiles) {
                          cpjars.add(jarFile); 
                       }
                    }

                } else if( f.isFile()) {
                    if( f.getName().endsWith(".jar")) {
                        cpjars.add(f);
                    }                     
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        int i=0;
        for(File f: cpjars) {
            if(i > 0 ) {
                sb.append(File.pathSeparator);
            }
            sb.append(f.getPath());
            i++;
        }
        return sb.toString();
        
    }*/

    private void processExtendedProperties(ArrayList<String> processArgs) {
        String extProps = env.getString(JAVA_EXTENDED_PROPERTIES);
        String [] props = extProps.split(" ");
        ArrayList<String> agents = new ArrayList<String>();
        for(String s: props) {
            if(s.startsWith("-javaagent:")) {
                if(!agents.contains(s)) {
                    agents.add(s);
                }
            } else {
                processArgs.add(s);
            }
        }
        for(String s: agents) {
            processArgs.add(s);
        }
    }

    private String checkCommand(String cmd) {
        StringBuilder cmdbuf = new StringBuilder(80);

        String s = cmd;
        if (s.indexOf(' ') >= 0 || s.indexOf('\t') >= 0) {
            if (s.charAt(0) != '"') {
                cmdbuf.append('"');
                cmdbuf.append(s);
                if (s.endsWith("\\")) {
                    cmdbuf.append("\\");
                }
                cmdbuf.append('"');
            } else if (s.endsWith("\"")) {
                /* The argument has already been quoted. */
                cmdbuf.append(s);
            } else {
                /* Unmatched quote for the argument. */
                throw new IllegalArgumentException();
            }
        } else {
            cmdbuf.append(s);
        }
        return cmdbuf.toString();
    }

    /*private String checkForSpaces(String value) {
        if(value.trim().indexOf(" ") != -1) { // space present in the string
            return "\""+value.trim()+"\"";
        } else {
            return value.trim();
        }
    }*/
}
