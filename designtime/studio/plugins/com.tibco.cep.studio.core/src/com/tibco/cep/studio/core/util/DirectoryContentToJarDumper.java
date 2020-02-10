package com.tibco.cep.studio.core.util;


import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Jul 14, 2004
 * Time: 4:38:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class DirectoryContentToJarDumper {


    private static final int BUFFER_SIZE = 100 * 1024;
    private static final FileFilter DEFAULT_FILTER = new FileFilter() {
        public boolean accept(File pathname) {
            return true;
        }//accept
    };//DEFAULT_FILTER

    private JarOutputStream jarOutputStream;


    /**
     * @param jarOutputStream the JarOutputStream in which the File objects will be dumped.
     */
    public DirectoryContentToJarDumper(JarOutputStream jarOutputStream) {
        if (jarOutputStream == null) {
            throw new IllegalArgumentException("JarOutputStream must not be null.");
        }//if
        this.jarOutputStream = jarOutputStream;
    }//constr


    /**
     * Dumps a File into the JarOutputStream provided in the constructor.
     * If the File is a directory, all the directory content will be dumped.
     * Else the File is a simple file, and only this file will be dumped.
     * The File will appear in the jar under the directory path specified.
     * Note that the JarOutputStream is not flushed.
     *
     * @param directory          the File to dump.
     * @param directoryPathInJar a String representing the path under which the File will be dumped.
     * @throws java.io.IOException
     */
    public void dumpToJar(File directory, String directoryPathInJar, FileFilter filter) throws IOException {
        if (directory == null) {
            throw new IllegalArgumentException("Directory must not be null.");
        } else if (directoryPathInJar == null) {
            throw new IllegalArgumentException("Directory path must not be null.");
        } else if (filter == null) {
            throw new IllegalArgumentException("File filter must not be null.");
        }//else

        if ((directoryPathInJar.length() > 0)
                && !"/".equals(directoryPathInJar.substring(directoryPathInJar.length() - 1))) {
            directoryPathInJar += "/";
        }//if

        final File[] files = directory.listFiles(filter);

        if ((files.length == 0) && (directoryPathInJar.length() > 0)) { // Directory is empty.
            final JarEntry entry = new JarEntry(new ZipEntry(directoryPathInJar));
            this.jarOutputStream.putNextEntry(entry);
            this.jarOutputStream.closeEntry();

        } else { // Directory is not empty.

            for (int i = 0; i < files.length; i++) {
                final File file = files[i];

                if (file.isDirectory()) {
                    // File is a folder.
                    dumpToJar(file, directoryPathInJar + file.getName(), filter);
                } else {
                    // File is a regular file.
                    final String entryName = directoryPathInJar + file.getName();
                    final JarEntry entry = new JarEntry(new ZipEntry(entryName));
                    this.jarOutputStream.putNextEntry(entry);

                    final FileInputStream in = new FileInputStream(file);
                    final byte[] bytes = new byte[BUFFER_SIZE];
                    for (int bytesRead = in.read(bytes); bytesRead >= 0; bytesRead = in.read(bytes)) {
                        this.jarOutputStream.write(bytes, 0, bytesRead);
                    }//for
                    in.close();

                    this.jarOutputStream.closeEntry();
                }//else
            }//for

        }//else

    }//putDirectoryIntoJar


    public void dumpToJar(File directory, String directoryPathInJar) throws IOException {
        dumpToJar(directory, directoryPathInJar, DEFAULT_FILTER);
    }//dumpToJar


    /**
     * Equivalent to <code>dumpToJar(new File(fileSystemPath), "")</code>.
     *
     * @param fileSystemPath the path to the directory (or file) to dump.
     * @throws java.io.IOException
     */
    public void dumpToJar(String fileSystemPath) throws IOException {
        dumpToJar(fileSystemPath, DEFAULT_FILTER);
    }//write


    /**
     * Equivalent to <code>dumpToJar(directory, "")</code>.
     *
     * @param directory the File to dump.
     * @throws java.io.IOException
     */
    public void dumpToJar(File directory) throws IOException {
        dumpToJar(directory, DEFAULT_FILTER);
    }//write


    /**
     * Equivalent to <code>dumpToJar(new File(fileSystemPath), directoryPathInJar)</code>.
     *
     * @param fileSystemPath     the path to the directory (or file) to dump.
     * @param directoryPathInJar a String representing the path under which the File will be dumped.
     * @throws java.io.IOException
     */
    public void dumpToJar(String fileSystemPath, String directoryPathInJar) throws IOException {
        dumpToJar(new File(fileSystemPath), directoryPathInJar, DEFAULT_FILTER);
    }//write


    /**
     * Equivalent to <code>dumpToJar(new File(fileSystemPath), "")</code>.
     *
     * @param fileSystemPath the path to the directory (or file) to dump.
     * @throws java.io.IOException
     */
    public void dumpToJar(String fileSystemPath, FileFilter filter) throws IOException {
        dumpToJar(new File(fileSystemPath), "", filter);
    }//write


    /**
     * Equivalent to <code>dumpToJar(directory, "")</code>.
     *
     * @param directory the File to dump.
     * @throws java.io.IOException
     */
    public void dumpToJar(File directory, FileFilter filter) throws IOException {
        dumpToJar(directory, "", filter);
    }//write


    /**
     * Equivalent to <code>dumpToJar(new File(fileSystemPath), directoryPathInJar)</code>.
     *
     * @param fileSystemPath     the path to the directory (or file) to dump.
     * @param directoryPathInJar a String representing the path under which the File will be dumped.
     * @throws java.io.IOException
     */
    public void dumpToJar(String fileSystemPath, String directoryPathInJar, FileFilter filter) throws IOException {
        dumpToJar(new File(fileSystemPath), directoryPathInJar, filter);
    }//write


}//class
