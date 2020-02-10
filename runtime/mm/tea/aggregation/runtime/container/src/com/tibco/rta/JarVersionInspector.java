/**
 * 
 */
package com.tibco.rta;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * @author admin
 *
 */
public class JarVersionInspector {

    protected static final String FILE_SEPARATOR = System.getProperty("file.separator");
    protected static final String PATH_SEPARATOR = System.getProperty("path.separator");
    protected static final String[] JAR_PREFIXES = new String[] {
            "rt"
    };
    protected static final String[] MAINCLASS_PREFIXES = new String[] {
            "com.tibco.rta.", "com.tibco.spm."
    };

    public JarVersionInspector() {
    }

    /**
     * Retrieves the versions of all jars currently accessible from the classpath.
     * @param classpath TODO
     *
     * @return <code>SortedMap</code> of jar name to <code>JarVersionInspector.Version</code>.
     */
    public SortedSet<String> getJarVersions(String classpath) {
        final Map<String, String> jarNameToClassName = this.getVersionClassNames(classpath);
        final SortedSet<String> jarNameToVersion = new TreeSet<String>();
        for (Iterator<Entry<String, String>> it = jarNameToClassName.entrySet().iterator(); it.hasNext();) {
            final Map.Entry entry = (Map.Entry) it.next();
            final String jarName = (String) entry.getKey();
            final String className = (String) entry.getValue();
            final Class<?> versionClass;
            try {
                versionClass = Class.forName(className);
                String versionInfo;
                try {
                	versionInfo = (String) versionClass.getDeclaredMethod("printVersionInfo", new Class[0]).invoke(null);
                } catch (Exception e) {
                	versionInfo = null;
                }
//                System.out.println(jarName + " ==> " + versionInfo);
                if (jarName.equalsIgnoreCase("rtapi.jar")) {
					jarNameToVersion.add(jarName + " =>   " + versionInfo);
				} else {
					jarNameToVersion.add(jarName + " =>   " + versionInfo);
				}
            } catch (ClassNotFoundException ignore) {
            } catch (LinkageError ignore) {
            }
        }
        return jarNameToVersion;
    }


    public SortedMap<String,String> getVersionClassNames(String classpath) {
        final SortedMap<String,String> map = new TreeMap<String,String>();
        ArrayList<String> pathItems = new ArrayList<String>();
        if(classpath != null) {
        	final String[] classPathItems = classpath.split(File.pathSeparator);
        	for(String s: classPathItems) {
                pathItems.add(s);
            }
        }
        
        final String[] classPathItems = System.getProperty("java.class.path").split(File.pathSeparator);
        for(String s: classPathItems) {
            pathItems.add(s);
        }
//        System.out.println("class path:"+System.getProperty("java.class.path"));
        String extDirPath = System.getProperty("java.ext.dirs");
        if (extDirPath != null) {
            extDirPath = fixExtDirPath(extDirPath);
            extDirPath = getClassPathFromExtDirs(extDirPath);
            String[] extPathItems = extDirPath.split(PATH_SEPARATOR);
            for (String s : extPathItems) {
                pathItems.add(s);
            }
        }
        for (int i = 0; i < pathItems.size(); i++) {
            final String pathItem = pathItems.get(i);
            if (this.acceptsPathItem(pathItem)) {
                try {
                    final JarFile jf = new JarFile(pathItem);
                    final Manifest mf = jf.getManifest();
                    if (null != mf) {
                        final Attributes attr = mf.getMainAttributes();
                        final String mainClassName = attr.getValue("Main-Class");
                        if (this.acceptsMainClassName(mainClassName)) {
                            String jarName = pathItem;
                            final int lastSep = jarName.lastIndexOf(FILE_SEPARATOR);
                            if (lastSep >= 0) {
                                jarName = jarName.substring(lastSep + 1);
                            }
                            if(!map.containsKey(jarName)) {
                                map.put(jarName, mainClassName);
                            }
                        }
                    }
                } catch (Exception ignored) {
                }
            }
        }
        return map;
    }

    private boolean acceptsPathItem(String pathItem) {
        if ((null != pathItem) && pathItem.endsWith(".jar")) {
            final int lastSlash = pathItem.lastIndexOf(FILE_SEPARATOR);
            if (lastSlash >= 0) {
              pathItem = pathItem.substring(lastSlash+1);
            }
            for (int i=JAR_PREFIXES.length-1; i>=0; i--) {
                if (pathItem.startsWith(JAR_PREFIXES[i])) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean acceptsMainClassName(String mainClassName) {
        if (null != mainClassName) {
            for (int i=MAINCLASS_PREFIXES.length-1; i>=0; i--) {
                if (mainClassName.startsWith(MAINCLASS_PREFIXES[i])) {
                    return true;
                }
            }
        }
        return false;
    }

    private String fixExtDirPath(String extDirs) {
        ArrayList<File> cpjars = new ArrayList<File>();
        String[] urls = extDirs.split(File.pathSeparator);
        for(String url:urls) {
            File f = new File(url);
            if(f.exists()) {
                if(f.isDirectory()) {
                    cpjars.add(f);
                } else if( f.isFile()) {
                    if( f.getName().endsWith(".jar")) {
                        cpjars.add(f.getParentFile());
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
    }

    private String getClassPathFromExtDirs(String extDirs) {
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
    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JarVersionInspector jvInspector = new JarVersionInspector();
        final SortedSet<String> jarNameToClassName = jvInspector.getJarVersions(null);
        for (Iterator<String> it = jarNameToClassName.iterator(); it.hasNext();) {
        	final String versionInfo = (String) it.next();
            System.out.println(versionInfo);
        }
	}
}
