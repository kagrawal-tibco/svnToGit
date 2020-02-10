package com.tibco.cep.util;

import java.io.File;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;

/*
* Author: Ashwin Jayaprakash Date: Mar 16, 2009 Time: 5:10:17 PM
*/

/**
 * Reverses the classloading sequence unlike other ClassLoaders. Here, the URLs are searched first,
 * then the parent ClassLoader.
 */
public class CustomClassLoader extends URLClassLoader {
    /**
     * @param urls   Use {@link  #expandAndFilterURLs(String[], java.io.PrintStream)}
     * @param parent
     */
    protected CustomClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public static CustomClassLoader create(String[] rawPaths, ClassLoader parent,
                                           PrintStream logStream)
            throws MalformedURLException {
        URL[] expandedURLs = expandAndFilterURLs(rawPaths, logStream);

        return new CustomClassLoader(expandedURLs, parent);
    }

    /**
     * Picks up only ".jar" files and the directories themselves.
     *
     * @param givenPaths
     * @param logStream
     * @return
     * @throws MalformedURLException
     */
    public static URL[] expandAndFilterURLs(String[] givenPaths, PrintStream logStream)
            throws MalformedURLException {
        LinkedList<URL> list = new LinkedList<URL>();

        for (String givenPath : givenPaths) {
            String s = givenPath.toString();
            File file = new File(s);
            collectClasspathRecursively(file, list);
        }

        String newline = System.getProperty("line.separator", "\n");
        StringBuilder builder = new StringBuilder(newline);
        builder.append("The following entries are being used in the custom classpath:");
        builder.append(newline);
        for (URL url : list) {
            String x = String.format("   [%-80s]%n", url.toString());
            builder.append(x);
        }

        logStream.println(builder);

        return list.toArray(new URL[list.size()]);
    }

    static void collectClasspathRecursively(File fileOrDir, LinkedList<URL> collection)
            throws MalformedURLException {
        if (fileOrDir.exists() == false) {
            return;
        }

        if (fileOrDir.isFile()) {
            String pathString = fileOrDir.getAbsolutePath();
            pathString = pathString.toLowerCase();

            //Only JAR files are added.
            if (pathString.endsWith(".jar")) {
                collection.add(fileOrDir.toURL());
            }
        }
        else if (fileOrDir.isDirectory()) {
            //Directory may contain DLLs and .classes. So, add it.
            collection.add(fileOrDir.toURL());

            //Search recursively.
            File[] files = fileOrDir.listFiles();
            for (File fileInDir : files) {
                collectClasspathRecursively(fileInDir, collection);
            }
        }
    }

    //-----------------

    @Override
    protected synchronized Class<?> loadClass(String name, boolean resolve)
            throws ClassNotFoundException {
        Class c = findLoadedClass(name);

        if (c == null) {
            try {
                c = findClass(name);
            }
            catch (ClassNotFoundException e) {
                ClassLoader p = getParent();

                if (p != null) {
                    try {
                        c = p.loadClass(name);
                    }
                    catch (ClassNotFoundException e1) {
                        c = findSystemClass(name);
                    }
                }
                else {
                    throw e;
                }
            }
        }

        if (resolve) {
            resolveClass(c);
        }

        return c;
    }
}
