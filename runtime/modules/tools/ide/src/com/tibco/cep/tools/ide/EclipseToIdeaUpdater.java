package com.tibco.cep.tools.ide;

import com.tibco.cep.tools.ide.intellij.Ipr;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.Manifest;

/**
 * Created by IntelliJ IDEA.
 * User: nicolasprade
 * Date: 11/29/11
 * Time: 4:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class EclipseToIdeaUpdater {


    private static final FilenameFilter FILTER = new FilenameFilter() {
        public boolean accept(File dir, String name) {
            return name.equals(".classpath")
                    || (new File(dir, name).isDirectory()
                    && !(".svn".equals(name) || "src".equals(name) || "lib".equals(name) || "docs".equals(name)
                    || "examples".equals(name) || name.endsWith("test") || name.endsWith(".linux")
                    || ("common".equals(name) && dir.getPath().endsWith("runtime/modules/query"))));
        }
    };

    private final static String IML_EXTENSION = ".iml";

    private static final XslTransformer XSLT;

    static {
        final InputStream is = EclipseToIdeaUpdater.class.getResourceAsStream("/eclipseClasspathToIml.xsl");
        try {
            XSLT = new XslTransformer(is);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static void main(final String[] args)
            throws Exception {

        final File iprFile = new File(args[0]);
        if (!(iprFile.isFile() && iprFile.canRead() && iprFile.getName().endsWith(".ipr"))) {
            System.err.println("Specify the path to a .ipr file.");
            System.exit(1);
        }
        System.out.println("ipr: " + iprFile.getPath());
        System.out.println();

        final EclipseToIdeaUpdater updater = new EclipseToIdeaUpdater();
        updater.update(iprFile);
    }


    public void update(
            File f)
            throws IOException, TransformerException, SAXException {

        final UpdateContext context = new UpdateContext(f);
        this.updateImlFiles(context);
        this.updateIprFile(context);

    }

    private void updateImlFiles(UpdateContext context)
            throws TransformerException, IOException {
        this.updateImlFiles(context, context.getIprFile().getParentFile());
    }

    private void updateImlFiles(
            UpdateContext context,
            File f)
            throws IOException, TransformerException {


        final String path = f.getCanonicalPath();
        if (context.addProcessed(path)) {
            if (!f.isFile()) {
                final File[] filteredFiles = f.listFiles(FILTER);
                Arrays.sort(filteredFiles);
                for (File child : filteredFiles) {
                    if (".classpath".equals(child.getName())) {
                        final List<String> modules = new LinkedList<String>();
                        final List<String> libraries = new LinkedList<String>();
                        final File parentFile = child.getParentFile();
                        if ("com.tibco.cep.rt".equals(parentFile.getName())
                                || "com.tibco.cep.tpcl".equals(parentFile.getName())) {
                            modules.add(context.getIprFile().getParentFile().getName());
                        }
                        final File manifestFile = new File(parentFile, "META-INF/MANIFEST.MF");
                        if (manifestFile.exists()) {
                            final Manifest manifest;
                            final FileInputStream fis = new FileInputStream(manifestFile);
                            try {
                                manifest = new Manifest(fis);
                            } finally {
                                fis.close();
                            }
                            final String requiredBundleStr = manifest.getMainAttributes().getValue("Require-Bundle");
                            if (null != requiredBundleStr) {
                                for (final String required : requiredBundleStr.split(",")) {
                                    if (required.startsWith("com.tibco.")) {
                                        final String name = required.split(";")[0];
                                        if (!modules.contains(name)) {
                                            modules.add(name);
                                        }
                                    } else if ((required.startsWith("org.eclipse.core.")
                                            || required.startsWith("org.eclipse.ui"))
                                            && !libraries.contains("TSI-EXTERNAL-ECLIPSE-3.7")) {
                                        libraries.add("TSI-EXTERNAL-ECLIPSE-3.7");
                                    } else if (required.startsWith("org.eclipse.emf.")
                                            && !libraries.contains("TSI-EXTERNAL-ECLIPSE-EMF-2.7.0")) {
                                        libraries.add("TSI-EXTERNAL-ECLIPSE-EMF-2.7.0");
                                    } else if (required.startsWith("org.eclipse.uml2.")
                                            && !libraries.contains("TSI-EXTERNAL-ECLIPSE-MDT-3.2.0")) {
                                        libraries.add("TSI-EXTERNAL-ECLIPSE-MDT-3.2.0");
                                    } else if (required.startsWith("org.eclipse.epf.")
                                            && !libraries.contains("TSI-EXTERNAL-ECLIPSE-EPF-RICHTEXT-1.5.1.2")) {
                                        libraries.add("TSI-EXTERNAL-ECLIPSE-EPF-RICHTEXT-1.5.1.2");
                                    } else if ((required.startsWith("org.eclipse.gef.")
                                            || required.startsWith("org.eclipse.draw2d"))
                                            && !libraries.contains("TSI-EXTERNAL-ECLIPSE-GEF-3.7.0")) {
                                        libraries.add("TSI-EXTERNAL-ECLIPSE-GEF-3.7.0");
                                    }
                                }
                            }
                        }
                        this.createIml(context, child, modules, libraries);

                        if (context.getIprFile().getParentFile().equals(f)) {
                            break;
                        } else {
                            return;
                        }
                    }
                }//for
                for (File child : filteredFiles) {
                    updateImlFiles(context, child);
                }
            }
        }
    }

    private void createIml(
            UpdateContext context,
            File f,
            List<String> modules,
            List<String> libraries
    ) throws IOException, TransformerException {
        System.out.println(f.getPath());
        final File parent = f.getParentFile();
        final String moduleName = parent.getName();
        final String imlPath = parent.getPath() + File.separator + moduleName + IML_EXTENSION;
        context.addModule(moduleName, imlPath);
        final OutputStream output = new FileOutputStream(imlPath);

        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("modules", makeParamString(modules));
        params.put("libraries", makeParamString(libraries));

        try {
            final InputStream is = EclipseToIdeaUpdater.class.getResourceAsStream("/eclipseClasspathToIml.xsl");
            try {
                XslTransformer.transform(new FileInputStream(f), is, output, params);
            } finally {
                is.close();
            }
        } finally {
            output.close();
        }
    }

    private String makeParamString(List<String> modules) {
        final StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String s : modules) {
            if (first) {
                first = false;
            } else {
                sb.append(",");
            }
            sb.append(s);
        }
        return sb.toString();
    }


    private void updateIprFile(UpdateContext context)
            throws IOException, SAXException {
        final Ipr ipr = new Ipr(context.getIprFile());
        for (final String modulePath : context.getModules().values()) {
            if (!modulePath.endsWith("test")) {
                ipr.addModule(modulePath);
            }
        }
        ipr.update();
    }


    protected static class UpdateContext {

        private ConcurrentHashMap<String, String> processed = new ConcurrentHashMap<String, String>();
        private ConcurrentHashMap<String, String> moduleNameToImlFile = new ConcurrentHashMap<String, String>();
        private File iprFile;

        public UpdateContext(File iprFile) {
            this.iprFile = iprFile;
        }


        public void addModule(String moduleName, String imlPath) {
            this.moduleNameToImlFile.put(moduleName, imlPath);
        }

        public boolean addProcessed(String path) {
            return (null == this.processed.putIfAbsent(path, path));
        }

        public File getIprFile() {
            return this.iprFile;
        }

        public ConcurrentHashMap<String, String> getModules() {
            return this.moduleNameToImlFile;
        }

    }


}





