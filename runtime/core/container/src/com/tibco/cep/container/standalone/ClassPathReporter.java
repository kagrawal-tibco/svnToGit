package com.tibco.cep.container.standalone;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.StandardMBean;

/*
* Author: Ashwin Jayaprakash / Date: 3/8/11 / Time: 3:30 PM
*/

/**
 * Reports the locations from which BE specific JAR files/classes have been loaded. Specifically JARs that that have
 * "main-class" defined in the JAR files like: cep-common.jar and such.
 * <p/>
 * <pre>
 * Analyzing [/G:/plugins/org.eclipse.equinox.registry.source_3.4.0.v20080516-0950.jar]
 * .. .. ..
 * .. ..
 * Analyzing [/R:/tibco/tpcl/5.6.2/L/win/x86/rel/lib/log4j.jar]
 * Analyzing [/G:/plugins/org.eclipse.core.contenttype_3.3.0.v20080604-1400.jar]
 * Using [cep-pattern.jar] located at [file:\C:\Users\ajayapra\Desktop\junk\cep-pattern.jar]
 * Using [xdata.jar] located at [file:\R:\tibco\bw\5.6.3\L\win\x86\rel\lib\palettes\xdata.jar]
 * Using [Deployment.jar] located at [file:\R:\tibco\tra\5.6.2\L\win\x86\rel\lib\Deployment.jar]
 * Using class [com.tibco.cep.as.cep_datagrid_tibcoVersion] loaded from [Q:\be\branches\5.0\classes\com\tibco\cep\as\cep_datagrid_tibcoVersion.class]
 * Using class [com.tibco.be.functions.be_functionsVersion] loaded from [Q:\be\branches\5.0\classes\com\tibco\be\functions\be_functionsVersion.class]
 * Using class [com.tibco.cep.cep_datagrid_oracleVersion] loaded from [Q:\be\branches\5.0\classes\com\tibco\cep\cep_datagrid_oracleVersion.class]
 * Using [mapper.jar] located at [file:\R:\tibco\bw\5.6.3\L\win\x86\rel\lib\palettes\mapper.jar]
 * Using class [com.tibco.cep.query.cep_queryVersion] loaded from [Q:\be\branches\5.0\classes\com\tibco\cep\query\cep_queryVersion.class]
 * Done.
 * </pre>
 */
public class ClassPathReporter {
    protected HashSet<URL> urls;

    protected HashSet<String> classNames;

    protected HashMap<String, String> successfulResults;

    public ClassPathReporter() {
        this.urls = new HashSet<URL>();
        this.classNames = new HashSet<String>();
        this.successfulResults = new HashMap<String, String>();
    }

    /**
     * See {@link #getSuccessfulResults()}.
     *
     * @param classLoader
     * @param writer
     */
    public void work(ClassLoader classLoader, PrintWriter writer) {
        collect(classLoader);

        analyze(writer);

        report(classLoader, writer);
    }

    public Map<String, String> getSuccessfulResults() {
        return successfulResults;
    }

    protected void collect(ClassLoader classLoader) {
        if (!(classLoader instanceof URLClassLoader)) {
            return;
        }

        URLClassLoader urlClassLoader = (URLClassLoader) classLoader;

        URL[] array = urlClassLoader.getURLs();
        for (URL url : array) {
            urls.add(url);
        }

        ClassLoader parentCL = urlClassLoader.getParent();
        if (parentCL != null) {
            collect(parentCL);
        }
    }

    protected void analyze(PrintWriter writer) {
        if (urls.isEmpty()) {
            writer.append("Class path collection was not successful.");
            writer.println();

            return;
        }

        for (URL url : urls) {
            try {
                analyze(url, writer);
            }
            catch (IOException e) {
                //Ignore.
            }
        }
    }

    protected void analyze(URL url, PrintWriter writer) throws IOException {
        String path = url.getFile();

        writer.append("Analyzing [").append(path).append("]");
        writer.println();

        File file = new File(path);
        if (!file.canRead() || !file.isFile()) {
            return;
        }

        analyzeIfJar(path, writer);
    }

    /**
     * Collects the Main-Class from the JAR if available.
     *
     * @param path
     * @param writer
     * @throws IOException
     */
    protected void analyzeIfJar(String path, PrintWriter writer) throws IOException {
        JarFile jar = new JarFile(path);

        try {
            Manifest mf = jar.getManifest();
            if (mf == null) {
                return;
            }

            Attributes mainAttributes = mf.getMainAttributes();
            if (mainAttributes == null) {
                return;
            }

            String className = mainAttributes.getValue("Main-Class");
            if (className != null) {
                classNames.add(className);
            }
        }
        finally {
            jar.close();
        }
    }

    protected void report(ClassLoader classLoader, PrintWriter writer) {
        final String msg = "Reporting was not successful.";

        if (classNames.isEmpty()) {
            writer.append(msg);
            writer.println();

            return;
        }

        int k = 0;
        for (String className : classNames) {
            try {
                boolean b = report(classLoader, writer, className);
                if (b) {
                    k++;
                }
            }
            catch (ClassNotFoundException e) {
                //Ignore.
            }
        }

        if (k == 0) {
            writer.append(msg);
            writer.println();
        }
        else {
            writer.append("Done.");
            writer.println();
        }
    }

    /**
     * @param classLoader
     * @param writer
     * @param className
     * @return true if the className
     * @throws ClassNotFoundException
     */
    protected boolean report(ClassLoader classLoader, PrintWriter writer, String className)
            throws ClassNotFoundException {
        if (!className.endsWith("Version")) {
            return false;
        }

        if (!className.toLowerCase().contains("tibco")) {
            return false;
        }

        //---------------------

        //See who loads this class.
        Class klass = classLoader.loadClass(className);

        String classResourcePath = "/" + klass.getName().replaceAll("\\.", "/") + ".class";
        URL url = klass.getResource(classResourcePath);
        if (url == null) {
            return false;
        }

        //---------------------

        File file = new File(url.getPath());
        String filePath = file.getPath();

        if (file.exists()) {
            handleNotFromJar(writer, className, filePath);

            return true;
        }

        //---------------------

        String jarFileName = klass.getSimpleName();
        jarFileName = jarFileName.replaceAll("_", "-");
        int k = jarFileName.indexOf("Version");
        if (k > 0) {
            jarFileName = jarFileName.substring(0, k);
        }
        jarFileName = jarFileName + ".jar";

        //Example: com.tibco.cep.query.cep_queryVersion was loaded from the JAR [C:\tibco\be\5.0\mm\lib\cep-query.jar!\com\tibco\cep\query\cep_queryVersion.class]
        String x = jarFileName + "!";
        int idx = filePath.indexOf(x);
        if (idx >= 0) {
            String jarPath = filePath.substring(0, idx + x.length() - 1 /* Skip the trailing "!" */);

            writer.append("Using [").append(jarFileName).append("] located at [").append(jarPath).append("]");
            writer.println();

            successfulResults.put(jarFileName, jarPath);
        }
        else {
            int bangIdx = filePath.indexOf('!');

            //Example: com.tibco.cep.query.cep_queryVersion was loaded from [C:\tibco\be\5.0\mm\lib\be-q.jar!\com\tibco\cep\query\cep_queryVersion.class]
            if (bangIdx > 0) {
                String jarPath = filePath.substring(0, bangIdx);

                writer.append("Using [").append(className).append("] located at [").append(jarPath).append("]");
                writer.println();

                successfulResults.put(jarFileName, jarPath);
            }
            else {
                handleNotFromJar(writer, className, filePath);
            }
        }

        return true;
    }

    private void handleNotFromJar(PrintWriter writer, String className, String filePath) {
        writer.append("Using class [").append(className).append("] loaded from [").append(filePath).append("]");
        writer.println();

        successfulResults.put(className, filePath);
    }

    public void discard() {
        urls.clear();
        urls = null;

        classNames.clear();
        classNames = null;

        successfulResults = null;
    }

    public static Map<String, String> fetchClassPathReport() {
        Map<String, String> results = null;

        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        PrintWriter printWriter = new PrintWriter(baos);
        try {
            results = printClassPathReport(printWriter);
        }
        finally {
            printWriter.close();
            baos = null;
        }

        return results;
    }

    /**
     * @param printWriter
     * @return null if there were errors.
     */
    public static Map<String, String> printClassPathReport(PrintWriter printWriter) {
        Map<String, String> results = null;

        printWriter.append("Starting ").append(ClassPathReporter.class.getName());
        printWriter.println();

        try {
            ClassPathReporter reporter = new ClassPathReporter();

            try {
                reporter.work(reporter.getClass().getClassLoader(), printWriter);

                results = reporter.getSuccessfulResults();
            }
            finally {
                reporter.discard();
            }
        }
        finally {
            printWriter.flush();
        }

        return results;
    }

    public static void main(String[] args) {
        try {
            PrintWriter printWriter = new PrintWriter(System.out);
            try {
                printClassPathReport(printWriter);
            }
            finally {
                printWriter.close();
            }
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }

    //-----------------

    public static interface ClassPathReportHandlerMBean {
        void printClassPathReport();
    }

    public static class ClassPathReportHandler implements ClassPathReportHandlerMBean {
        public ClassPathReportHandler() {
        }

        public void registerSelf() throws Exception {
            MBeanServer platform = ManagementFactory.getPlatformMBeanServer();

            ObjectName on = new ObjectName("com.tibco.be:type=Container,service="
                    + ClassPathReporter.class.getSimpleName());

            if (platform.isRegistered(on)) {
                return;
            }

            StandardMBean standardMBean = new StandardMBean(this, ClassPathReportHandlerMBean.class);

            platform.registerMBean(standardMBean, on);
        }

        @Override
        public void printClassPathReport() {
            PrintWriter printWriter = new PrintWriter(System.err);
            try {
                ClassPathReporter.printClassPathReport(printWriter);
            }
            finally {
                printWriter.close();
            }
        }
    }
}
