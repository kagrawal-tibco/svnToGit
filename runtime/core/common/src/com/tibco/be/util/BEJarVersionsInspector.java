package com.tibco.be.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.be.util.packaging.Constants;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Aug 23, 2007
 * Time: 4:27:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class BEJarVersionsInspector {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    

    public static final String NS = "http://www.tibco.com/be/schemas/config";
    public static final ExpandedName XNAME_ENGINECONFIG = ExpandedName.makeName(NS, "engine-config");
    public static final ExpandedName XNAME_VERSIONS = ExpandedName.makeName(NS, "versions");
    public static final ExpandedName XNAME_VERSION = ExpandedName.makeName(NS, "version");
    public static final ExpandedName XNAME_NAME = ExpandedName.makeName("name");
    public static final ExpandedName XNAME_NUMBER = ExpandedName.makeName("number");
    public static final ExpandedName XNAME_COMPONENT = ExpandedName.makeName("component");
    public static final ExpandedName XNAME_LICENSE = ExpandedName.makeName("license");

    protected static final String FILE_SEPARATOR = System.getProperty("file.separator");
    protected static final String PATH_SEPARATOR = System.getProperty("path.separator");
    protected static final String[] BE_JAR_PREFIXES = new String[] {
            "be-", "cep-", "com.tibco.be.", "com.tibco.cep."
    };
    protected static final String[] MAINCLASS_PREFIXES = new String[] {
            "com.tibco.be.", "com.tibco.cep."
    };
//    protected static final Pattern VERSION_PATTERN = Pattern.compile(
//            "(?:Version\\s)?(\\d+(?:\\.\\d+)*),\\s(\\d{4}-\\d{2}-\\d{2})(?:\\s(.*))?");

    protected static XiFactory xiFactory = XiFactoryFactory.newInstance();
    protected static XiParser xiParser = XiParserFactory.newInstance();


    public BEJarVersionsInspector() {
    }


    /**
     * Retrieves the versions of all BE jars currently accessible from the classpath.
     * @param classpath TODO
     *
     * @return <code>SortedMap</code> of jar name to <code>BEJarVersionsInspector.Version</code>.
     */
    public SortedMap<String,BEJarVersionsInspector.Version> getJarVersions(String classpath) {
        final Map jarNameToClassName = this.getVersionClassNames(classpath);
        final SortedMap<String,BEJarVersionsInspector.Version> jarNameToVersion = new TreeMap<String,BEJarVersionsInspector.Version>();
        for (Iterator it = jarNameToClassName.entrySet().iterator(); it.hasNext();) {
            final Map.Entry entry = (Map.Entry) it.next();
            final String jarName = (String) entry.getKey();
            final String className = (String) entry.getValue();
            final Class versionClass;
            try {
                versionClass = Class.forName(className);
                String number;
                String component;
                String license;
                try {
                    number = (String) versionClass.getDeclaredMethod("getVersion", new Class[0])
                            .invoke(null, new Object[0]);
                } catch (Exception e) {
                    number = null;
                }
                try {
                    component = (String) versionClass.getDeclaredMethod("getComponent", new Class[0])
                            .invoke(null, new Object[0]);
                } catch (Exception e) {
                    component = null;
                }

                try {
                    license = (String) versionClass.getDeclaredMethod("getLicense", new Class[0])
                            .invoke(null, new Object[0]);
                } catch (Exception e) {
                    license = null;
                }

                jarNameToVersion.put(jarName, new BEJarVersionsInspector.Version(jarName, number, component,license));
            } catch (ClassNotFoundException ignore) {
            } catch (LinkageError ignore) {
            }
        }
        return jarNameToVersion;
    }


    public XiNode getJarVersionsAsXml(String classpath) {
        final XiNode root = xiFactory.createElement(XNAME_VERSIONS);

        final Map jarNameToClassName = this.getVersionClassNames(classpath);
        for (Iterator it = jarNameToClassName.entrySet().iterator(); it.hasNext();) {
            final Map.Entry e = (Map.Entry) it.next();
            final XiNode node = root.appendElement(XNAME_VERSION);
            final Class versionClass;
            try {
                versionClass = Class.forName((String) e.getValue());
                node.setAttributeStringValue(XNAME_NAME, (String) e.getKey());
                try {
                    node.setAttributeStringValue(XNAME_NUMBER,
                            (String) versionClass.getDeclaredMethod("getVersion", new Class[0]).invoke(null, new Object[0]));
                } catch (Exception ignore) {
                }
                try {
                    node.setAttributeStringValue(XNAME_COMPONENT,
                            (String) versionClass.getDeclaredMethod("getComponent", new Class[0]).invoke(null, new Object[0]));
                } catch (Exception ignore) {
                }
                try {
                    node.setAttributeStringValue(XNAME_LICENSE,
                            (String) versionClass.getDeclaredMethod("getLicense", new Class[0]).invoke(null, new Object[0]));
                } catch (Exception ignore) {
                }
            } catch (ClassNotFoundException e1) {
            } catch (LinkageError e1) {            }
        }
        return root;
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
        	pathItem = pathItem.replace("\\", "/");//BE-16669
        	final int lastSlash = pathItem.lastIndexOf("/");
            if (lastSlash >= 0) {
              pathItem = pathItem.substring(lastSlash+1);
            }
            for (int i=BE_JAR_PREFIXES.length-1; i>=0; i--) {
                if (pathItem.startsWith(BE_JAR_PREFIXES[i])) {
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


    /**
     * @param versionsNode <code>XiNode</code> that represents jar versions.
     * @return <code>SortedMap</code> of jar name to <code>BEJarVersionsInspector.Version</code>.
     */
    public SortedMap<String,Version> parseJarVersions(XiNode versionsNode) {
        final SortedMap jarNameToVersion = new TreeMap();
        try {
            for (Iterator it = XiChild.getIterator(versionsNode, XNAME_VERSION); it.hasNext();) {
                final XiNode node = (XiNode) it.next();
                try {
                    final String name = node.getAttributeStringValue(XNAME_NAME);
                    final Version version = new Version(name,
                            node.getAttributeStringValue(XNAME_NUMBER),
                            node.getAttributeStringValue(XNAME_COMPONENT),
                            node.getAttributeStringValue(XNAME_LICENSE));
                    if (null != name) {
                        jarNameToVersion.put(name, version);
                    }
                } catch(Exception ignored) {
                }
            }
        } catch(Exception ignored) {
        }
        return jarNameToVersion;
    }


    /**
     * @param nvPairsNode <code>XiNode</code> that represents jar versions.
     * @return <code>SortedMap</code> of jar name to <code>BEJarVersionsInspector.Version</code>.
     */
    public SortedMap<String, Version> parseFromNameValuePairs(XiNode nvPairsNode) {
        final SortedMap<String, Version> jarNameToVersion = new TreeMap<String, Version>();
        try {
            for (Iterator it = XiChild.getIterator(nvPairsNode, Constants.DD.XNames.NAME_VALUE_PAIR); it.hasNext();) {
                final XiNode node = (XiNode) it.next();
                try {
                    final String name = XiChild.getString(node, Constants.DD.XNames.NAME);
                    if (null != name) {
                        final String value = XiChild.getString(node, Constants.DD.XNames.VALUE);
                        if (null != value) {
//                            final Matcher m = VERSION_PATTERN.matcher(value);
//                            final Version version;
//                            if (m.matches()) {
//                                String g3 = m.group(3);
//                                version = new Version(name, m.group(1), ((null == g3) ? "" : g3), m.group(2));
//                            } else {
//                                version = new Version(name, value, "", "");
//                            }
//                            jarNameToVersion.put(name, version);
                             jarNameToVersion.put(name, new Version(name, value, "", ""));                            
                        }
                    }
                } catch (Exception ignored) {
                }
            }
        } catch (Exception ignored) {
        }
        return jarNameToVersion;
    }


    public static void main(String[] args) {
        if (args.length < 1) {
            trace("Usage:\njava " + BEJarVersionsInspector.class.getName() + " </path/to/file.ear>");
            System.exit(1);
        }
        final File earFile = new File(args[0]);

        XiNode node = null;
        try {
            node = getConfigXml(earFile);
        } catch (FileNotFoundException e) {
            trace("Could not find EAR file: " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            trace("Could not find BAR in EAR file: " + e.getMessage());
            System.exit(1);
        } catch (SAXException e) {
            trace("Could not parse configuration file: " + e.getMessage());
            System.exit(1);
        }

        try {
            node = node.getRootNode();
            node = XiChild.getChild(node, Constants.Config.XNames.ENGINE_CONFIG);
            node = XiChild.getChild(node, BEJarVersionsInspector.XNAME_VERSIONS);
            if (null == node) {
                throw new NullPointerException();
            }
        } catch(NullPointerException npe) {
            trace("Could not find versions info in configuration file.");
            System.exit(1);
        }

        trace("Jars used to build <" + earFile.getPath() + ">:");
        showVersions(node);
    }

    private static void showVersions(XiNode versionsNode) {
        final BEJarVersionsInspector inspector = new BEJarVersionsInspector();
        final SortedMap versions = inspector.parseJarVersions(versionsNode);
        int maxLength = 0;
        for (Iterator it = versions.keySet().iterator(); it.hasNext();) {
            final String jarName = (String) it.next();
            maxLength = Math.max(maxLength, jarName.length());
        }
        final char[] charSpaces = new char[maxLength];
        Arrays.fill(charSpaces, ' ');
        final String spaces = new String(charSpaces);
        for (Iterator it = versions.entrySet().iterator(); it.hasNext();) {
            final Map.Entry e = (Map.Entry) it.next();
            final Version version = (Version) e.getValue();
            final String versionString = version.getVersion();
            final String component = version.getComponent();
            final String license = version.getLicense();

            trace((version.getName() + spaces).substring(0, maxLength)
                + " : " + ((null == versionString) ? "" : versionString)
                + " - " + ((null == component) ? "" : component)
                + "   " + ((null == license) ? "" : license));
        }
    }

    private static XiNode getConfigXml(File earFile) throws IOException, SAXException {
        XiNode node = null;
        final ZipFile earZip = new ZipFile(earFile);
        try {
            for (Enumeration e = earZip.entries(); e.hasMoreElements();) {
                ZipEntry entry = (ZipEntry) e.nextElement();
                if (entry.getName().endsWith(".bar")) {
                    final InputStream is = earZip.getInputStream(entry);
                    try {
                        final File barFile = File.createTempFile("bar", null);
                        try {
                            final FileOutputStream fos = new FileOutputStream(barFile);
                            try {
                                int read;
                                byte[] buffer = new byte[4096];
                                while ((read = is.read(buffer, 0, 4096)) >= 0) {
                                    fos.write(buffer, 0, read);
                                }
                            } finally {
                                fos.close();
                            }
                            final ZipFile barZip = new ZipFile(barFile);
                            final ZipEntry cfgEntry = barZip.getEntry("engine-config.xml");
                            if (null != cfgEntry) {
                                final InputStream cfgIS = barZip.getInputStream(cfgEntry);
                                try {
                                    node = xiParser.parse(new InputSource(cfgIS));
                                } finally {
                                    cfgIS.close();
                                }
                            }
                        } finally {
                            barFile.delete();
                        }
                    } finally {
                        is.close();
                    }
                    break;
                }
            }
        } finally {
            earZip.close();
        }
        return node;
    }


    private static void trace(CharSequence text) {
        System.out.println(text);
    }


    public static class Version {
        private String name;
        private String version;
        private String component;
        private String license;

        public Version(String name, String version, String component, String license ) {
            this.name = name;
            this.version = version;
            this.component = component;
            this.license = license;
        }

        public String getComponent() {
            return component;
        }

        public String getName() {
            return name;
        }

        public String getVersion() {
            return version;
        }

        public String getLicense() {
            return license;
        }

        public String toString() {
            return this.name + ": " + this.version + " - " + this.component + "  "+ this.license;
        }

    }

    String fixExtDirPath(String extDirs) {
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

    String getClassPathFromExtDirs(String extDirs) {
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
}
