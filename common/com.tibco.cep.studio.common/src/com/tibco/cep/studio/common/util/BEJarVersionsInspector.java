package com.tibco.cep.studio.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 
 * @author rmishra
 *
 */
public class BEJarVersionsInspector {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    
    
    public static final String NS = "http://www.tibco.com/be/schemas/config";
    public static final String ENGINECONFIG = "engine-config";
    public static final String VERSIONS = "versions";
    public static final String VERSION = "version";
    public static final String NAME = "name";
    public static final String NUMBER = "number";
    public static final String COMPONENT = "component";
    public static final String LICENSE = "license";

    protected static final String FILE_SEPARATOR = System.getProperty("file.separator");
    protected static final String PATH_SEPARATOR = System.getProperty("path.separator");
    protected static final String[] BE_JAR_PREFIXES = new String[] {
            "be-", "cep-", "com.tibco.be.", "com.tibco.cep."
    };
    protected static final String[] MAINCLASS_PREFIXES = new String[] {
            "com.tibco.be.", "com.tibco.cep."
    };

   //protected static XiFactory xiFactory = XiFactoryFactory.newInstance();
    //protected static XiParser xiParser = XiParserFactory.newInstance();
  
  


    BEJarVersionsInspector() {
    	
    }


    /**
     * Retrieves the versions of all BE jars currently accessible from the classpath.
     *
     * @return <code>SortedMap</code> of jar name to <code>BEJarVersionsInspector.Version</code>.
     */
    public SortedMap getJarVersions() {
        final Map jarNameToClassName = this.getVersionClassNames();
        final SortedMap jarNameToVersion = new TreeMap();
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


    public static Node getJarVersionsAsXml()throws Exception {
        //final XiNode root = xiFactory.createElement(XNAME_VERSIONS);
    	final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    	dbFactory.setNamespaceAware(true);
    	final DocumentBuilder docbBuilder = dbFactory.newDocumentBuilder();
    	final Document document = docbBuilder.newDocument();
    	Element root = document.createElementNS(NS ,VERSIONS);
    	document.appendChild(root);
        final Map jarNameToClassName = getVersionClassNames();
        for (Iterator it = jarNameToClassName.entrySet().iterator(); it.hasNext();) {
            final Map.Entry e = (Map.Entry) it.next();
            //final XiNode node = root.appendElement(XNAME_VERSION);
            final Element node = document.createElement(VERSION);
            root.appendChild(node);
            final Class versionClass;
            try {
                versionClass = Class.forName((String) e.getValue());
                //node.setAttributeStringValue(XNAME_NAME, (String) e.getKey());
                node.setAttribute(NAME, (String) e.getKey());
                try {
                   // node.setAttributeStringValue(XNAME_NUMBER,
                     //       (String) versionClass.getDeclaredMethod("getVersion", new Class[0]).invoke(null, new Object[0]));
                    node.setAttribute(NUMBER, 
                    		(String) versionClass.getDeclaredMethod("getVersion", new Class[0]).invoke(null, new Object[0]));
                } catch (Exception ignore) {
                }
                try {
                    //node.setAttributeStringValue(XNAME_COMPONENT,
                      //      (String) versionClass.getDeclaredMethod("getComponent", new Class[0]).invoke(null, new Object[0]));
                    node.setAttribute(COMPONENT, 
                    		(String) versionClass.getDeclaredMethod("getComponent", new Class[0]).invoke(null, new Object[0])); 
                } catch (Exception ignore) {
                }
                try {
                    node.setAttribute(LICENSE,
                            (String) versionClass.getDeclaredMethod("getLicense", new Class[0]).invoke(null, new Object[0]));
                } catch (Exception ignore) {
                }
            } catch (ClassNotFoundException e1) {
            } catch (LinkageError e1) {            }
        }
        return document.getDocumentElement();
    }


    public static SortedMap getVersionClassNames() {
        final SortedMap map = new TreeMap();
        final String[] pathItems = System.getProperty("java.class.path").split(PATH_SEPARATOR);
        for (int i = 0; i < pathItems.length; i++) {
            final String pathItem = pathItems[i];
            if (acceptsPathItem(pathItem)) {
                try {
                    final JarFile jf = new JarFile(pathItem);
                    final Manifest mf = jf.getManifest();
                    if (null != mf) {
                        final Attributes attr = mf.getMainAttributes();
                        final String mainClassName = attr.getValue("Main-Class");
                        if (acceptsMainClassName(mainClassName)) {
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

    private static boolean acceptsPathItem(String pathItem) {
        if ((null != pathItem) && pathItem.endsWith(".jar")) {
            final int lastSlash = pathItem.lastIndexOf(FILE_SEPARATOR);
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


    private static boolean acceptsMainClassName(String mainClassName) {
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
    public static SortedMap parseJarVersions(Node versionsNode) {
        final SortedMap jarNameToVersion = new TreeMap();
        try {
        	NodeList childList = versionsNode.getChildNodes();
            for (int i =0 ; i< childList.getLength() ;++i) {
                final Node node = childList.item(i);
                if (VERSION.equals(node.getLocalName())){
                    try {
                        final String name = ((Element)node).getAttribute(NAME);
                        final Version version = new Version(name,
                        		((Element)node).getAttribute(NUMBER),
                        		((Element)node).getAttribute(COMPONENT),
                        		((Element)node).getAttribute(LICENSE));
                        if (null != name) {
                            jarNameToVersion.put(name, version);
                        }
                    } catch(Exception ignored) {
                    }
                }
          
            }
        } catch(Exception ignored) {
        }
        return jarNameToVersion;
    }


    public static void main(String[] args) {
        if (args.length < 1) {
            trace("Usage:\njava " + BEJarVersionsInspector.class.getName() + " </path/to/file.ear>");
            System.exit(1);
        }
        final File earFile = new File(args[0]);

        Node node = null;
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
        } catch (Exception e){
        	
        }

        try {
            node = ((Document)node).getDocumentElement();
            //node = XiChild.getChild(node, Constants.XNAME_ENGINE_CONFIG);
            //node = XiChild.getChild(node, BEJarVersionsInspector.XNAME_VERSIONS);
            node = node.getFirstChild();
            if (null == node) {
                throw new NullPointerException();
            }
        } catch(NullPointerException npe) {
            trace("Could not find versions info in configuration file.");
            System.exit(1);
        }

        trace("Jars used to build <" + earFile.getPath() + ">:");
//        showVersions(node);
    }

//    private static void showVersions(Node versionsNode) {
//        BEJarVersionsInspector inspector = new BEJarVersionsInspector();
//        SortedMap versions = inspector.parseJarVersions(versionsNode);
//        int maxLength = 0;
//        for (Iterator it = versions.keySet().iterator(); it.hasNext();) {
//            final String jarName = (String) it.next();
//            maxLength = Math.max(maxLength, jarName.length());
//        }
//        final char[] charSpaces = new char[maxLength];
//        Arrays.fill(charSpaces, ' ');
//        final String spaces = new String(charSpaces);
//        for (Iterator it = versions.entrySet().iterator(); it.hasNext();) {
//            final Map.Entry e = (Map.Entry) it.next();
//            final Version version = (Version) e.getValue();
//            final String versionString = version.getVersion();
//            final String component = version.getComponent();
//            final String license = version.getLicense();
//
//            trace((version.getName() + spaces).substring(0, maxLength)
//                + " : " + ((null == versionString) ? "" : versionString)
//                + " - " + ((null == component) ? "" : component)
//                + "   " + ((null == license) ? "" : license));
//        }
//    }

    private static Node getConfigXml(File earFile) throws Exception {
        Node node = null;
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
                                	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                                	DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
                                    node = docBuilder.parse(new InputSource(cfgIS));
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
}
