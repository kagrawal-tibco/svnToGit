package com.tibco.rta.model.serialize.impl;

import com.tibco.be.model.functions.impl.JavaAnnotationLookup;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.FunctionDescriptor;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public abstract class CatalogDeserializer<F extends FunctionDescriptor> {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_COMMON.getCategory());

    abstract public Map<String, F> deserialize() throws Exception;

    protected Map<String, F> getCatalogFromClasspath(String pattern) throws Exception {
        Map<String, F> descriptors = new LinkedHashMap<String, F>();
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        
        String classpath = System.getProperty("java.class.path");
        List<URL> urls = JavaAnnotationLookup.getClassPathURLs(classpath);
        for (final URL url : urls) {
        	try {
        		if (LOGGER.isEnabledFor(Level.DEBUG)) {
        			LOGGER.log(Level.DEBUG, "URL: %s", url);
        		}
        		readCatalogs(url.getFile(), pattern, descriptors);
        	} catch (Exception e) {
        		LOGGER.log(Level.WARN, "Could not read catalog from classpath element: %s", url);
        		LOGGER.log(Level.WARN, "Exception thrown... ", e);
        	}
        }
        return descriptors;
    }


    protected void readCatalogs(String element, String pattern, Map<String, F> descriptors) throws Exception {
        final File file = new File(element);
        if (!file.exists()) {
            return;
        }
        Pattern filePattern = Pattern.compile(pattern);
        if (file.isDirectory()) {
            getCatalogElementsFromDirectory(file, filePattern, descriptors);
        } else {
            getCatalogElementsFromJarFile(file, filePattern, descriptors);
        }
    }

    protected void getCatalogElementsFromJarFile(File file, Pattern filePattern, Map<String, F> descriptors) throws Exception {
        ZipFile zf;
        try {
            zf = new ZipFile(file);
        } catch (final ZipException e) {
            LOGGER.log(Level.WARN, "cannot read from zip/jar file %s", file.getAbsolutePath());
            throw new Exception(e);
        } catch (final IOException e) {
            LOGGER.log(Level.WARN, "cannot read from zip/jar file %s", file.getAbsolutePath());
            throw new Exception(e);
        }
        final Enumeration e = zf.entries();
        while (e.hasMoreElements()) {
            final ZipEntry ze = (ZipEntry) e.nextElement();
            final String fileName = ze.getName();
            final boolean accept = filePattern.matcher(fileName).matches();
            if (accept) {
                if (LOGGER.isEnabledFor(Level.INFO)) {
                    LOGGER.log(Level.INFO, "Catalog file: [%s] found in zip file: [%s]", fileName, zf.getName());
                }
                deserializeCatalogElements(zf.getInputStream(ze), descriptors);
            }
        }
        try {
            zf.close();
        } catch (final IOException e1) {
            throw new Exception(e1);
        }
    }


    private void getCatalogElementsFromDirectory(File directory, Pattern actionCatalogPattern, Map<String, F> descriptors) throws Exception {
        File[] fileList = directory.listFiles();
        if (fileList == null) {
            return;
        }
        for (final File file : fileList) {
            if (file.isDirectory()) {
                getCatalogElementsFromDirectory(file, actionCatalogPattern, descriptors);
            } else {
                try {
                    final String fileName = file.getCanonicalPath();
                    final boolean accept = actionCatalogPattern.matcher(fileName).matches();
                    if (accept) {
                        FileInputStream fins = new FileInputStream(file);
                        if (LOGGER.isEnabledFor(Level.INFO)) {
                            LOGGER.log(Level.INFO, "Catalog file: [%s] found in directory: [%s]", file.getName(), file.getAbsolutePath());
                        }
                        deserializeCatalogElements(fins, descriptors);
                    }
                } catch (final IOException e) {
                    throw new Exception(e);
                }
            }
        }
    }


    abstract protected void deserializeCatalogElements(InputStream in, Map<String, F> descriptors) throws Exception;

    abstract protected FunctionDescriptor deserializeCatalogElement(InputSource is) throws Exception;

}