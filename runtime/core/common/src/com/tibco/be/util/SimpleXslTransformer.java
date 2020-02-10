package com.tibco.be.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/*
 * User: nprade
 * Date: Dec 2, 2009
 * Time: 1:51:43 PM
 */

public class SimpleXslTransformer {


    private Transformer transformer;


    public SimpleXslTransformer(InputStream xslt) throws TransformerConfigurationException {
        this.transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(xslt));
        this.transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        this.transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        this.transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
    }


    public void transform(InputStream xml, OutputStream output) throws TransformerException {
        final Source xmlSource = new StreamSource(xml);
        final Result result = new StreamResult(output);
        this.transformer.transform(xmlSource, result);
    }
    
    public void transform(StreamSource source, StreamResult streamResult) throws TransformerException {
        this.transformer.transform(source, streamResult);
    }


    public static void transform(InputStream xml, InputStream xslt, OutputStream output)
            throws TransformerException {
        final Source xmlSource = new StreamSource(xml);
        final Source xsltSource = new StreamSource(xslt);
        final Result result = new StreamResult(output);
        final Transformer transformer = TransformerFactory.newInstance().newTransformer(xsltSource);

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        transformer.transform(xmlSource, result);
    }


    public static void main(final String[] args)
            throws IOException, TransformerException {
        int i = 0;
        final String xsltPath = args[i++];
        final FileInputStream xslt = new FileInputStream(xsltPath);
        System.out.println("xslt   : " + xsltPath);

        final File xmlFile = new File(args[i++]);
        System.out.println("start  : " + xmlFile.getPath());

        final FilenameFilter filter;
        if (xmlFile.isDirectory()) {
            final String inputExtension = "." + args[i++];
            System.out.println("source : <name>" + inputExtension);
            filter = new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.endsWith(inputExtension) || new File(dir, name).isDirectory();
                }
            };
        } else {
            filter = null;
        }

        final String outputExtension = "." + args[i];
        System.out.println("output : <name>" + outputExtension);
        System.out.println();

        processFile(new SimpleXslTransformer(xslt), xmlFile, filter, outputExtension, new ConcurrentHashMap<String, String>());
    }


    protected static void processFile(
            SimpleXslTransformer t, File f, FilenameFilter filter, String outputExtension,
            ConcurrentHashMap<String, String> processed)
            throws IOException, TransformerException {

        final String path = f.getCanonicalPath();
        if (null == processed.putIfAbsent(path, path)) {
            if (f.isFile()) {
                System.out.println(f.getPath());
                final String outputPath = path.substring(0, path.lastIndexOf(".")) + outputExtension;
                final OutputStream output = new FileOutputStream(outputPath);
                try {
                    t.transform(new FileInputStream(f), output);
                } catch (Exception e) {
                  e.printStackTrace();
                } finally {
                    output.close();
                }
            } else {
                final File[] filteredFiles = f.listFiles(filter);
                Arrays.sort(filteredFiles);
                for (File child : filteredFiles) {
                    processFile(t, child, filter, outputExtension, processed);
                }
            }
        }
    }


}
