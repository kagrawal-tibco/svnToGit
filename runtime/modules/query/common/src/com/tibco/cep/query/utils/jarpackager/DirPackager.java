package com.tibco.cep.query.utils.jarpackager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Nov 30, 2007
 * Time: 8:50:58 AM
 */
public class DirPackager {

    private String sourceDir;
    private String outputPath;
    private String manifestVerion;
    private String mainClass;
    private String classPath;
    private boolean moveFiles;

    private static FileOutputStream fos = null;
    private static JarOutputStream jos = null;
    private static int iBaseFolderLength = 0;

    /**
     * Creates a new instance of createJar
     */
    public DirPackager(String sourceDir, String outputPath, boolean moveFiles) {
        setSourceDir(sourceDir);
        setOutputPath(outputPath);
        setMoveFiles(this.moveFiles);
    }

    public boolean isMoveFiles() {
        return moveFiles;
    }

    public void setMoveFiles(boolean moveFiles) {
        this.moveFiles = moveFiles;
    }

    /**
     * @return String
     */
    public String getSourceDir() {
        return sourceDir;
    }

    /**
     * @param sourceDir
     */
    public void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
    }

    /**
     * @return String
     */
    public String getOutputPath() {
        return outputPath;
    }

    /**
     * @param outputPath
     */
    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    /**
     * @return String
     */
    public String getManifestVerion() {
        if (null == manifestVerion) {
            setManifestVerion("1.0");
        }
        return manifestVerion;
    }

    /**
     * @param manifestVerion
     */
    public void setManifestVerion(String manifestVerion) {
        this.manifestVerion = manifestVerion;
    }

    /**
     * @return String
     */
    public String getMainClass() {
        if (null == mainClass) {
            setMainClass("");
        }
        return mainClass;
    }

    /**
     * @param mainClass
     */
    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    /**
     * @return String
     */
    public String getClassPath() {
        if (null == classPath) {
            setClassPath("");
        }
        return classPath;
    }

    /**
     * @param classPath
     */
    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    /**
     * @param directoryName
     * @throws IllegalArgumentException
     */
    public static void checkDirectory(String directoryName) throws Exception {
        File dirobject = new File(directoryName);
        if (dirobject.exists() == true) {
            if (dirobject.isDirectory() == true) {
                File [] fileList = dirobject.listFiles();
                // Loop through the files
                for (int i = 0; i < fileList.length; i++) {
                    if (fileList[i].isDirectory()) {
                        checkDirectory(fileList[i].getPath());
                    } else if (fileList[i].isFile()) {
                        // Call the zipFunc function
                        jarFile(fileList[i].getPath());
                    }
                }
            } else {
                throw new IllegalArgumentException(directoryName + " is not a directory.");
            }
        } else {
            throw new IllegalArgumentException("Directory " + directoryName + " does not exist.");
        }
    }

    /**
     * @param filePath
     */
    private static void jarFile(String filePath) throws Exception {
        final FileInputStream fis = new FileInputStream(filePath);
        final BufferedInputStream bis = new BufferedInputStream(fis);
        try {
            JarEntry fileEntry = new JarEntry(filePath.substring(iBaseFolderLength));
            jos.putNextEntry(fileEntry);
            byte[] data = new byte[1024];
            int byteCount;
            while ((byteCount = bis.read(data, 0, 1024)) > -1) {
                jos.write(data, 0, byteCount);
            }
        } finally {
            bis.close();
            fis.close();
        }
        //System.out.println(filePath.substring(iBaseFolderLength));
    }

    /**
     * This function will recursivly delete directories and files.
     *
     * @param path File or Directory to be deleted
     * @return true indicates success.
     */
    private boolean deleteDir(File path) throws Exception {
        if (path.exists()) {
            if (path.isDirectory()) {
                File[] files = path.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
                        deleteDir(files[i]);
                    } else {
                        final boolean success = files[i].delete();
                        if (!success) {
                            throw new Exception("Cannot delete file :" + path.getCanonicalPath());
                        }
                    }
                }
            }
        }
        boolean success = path.delete();
        if (!success) {
            throw new Exception("Cannot delete file :" + path.getCanonicalPath());
        }
        return (success);
    }

    /**
     * @throws Exception
     */
    public void createJar() throws Exception {
        createJar(getSourceDir(), getOutputPath(), getManifestVerion(), getMainClass(), getClassPath());
        deleteDir(new File(getSourceDir()));
    }

    /**
     * @param sourceDir
     * @param outputPath
     * @param manifestVerion
     * @param mainClass
     * @param classPath
     * @throws Exception
     */
    private void createJar(String sourceDir,
                           String outputPath,
                           String manifestVerion,
                           String mainClass,
                           String classPath) throws Exception {
        try {
            String strBaseFolder = sourceDir + File.separator;
            iBaseFolderLength = strBaseFolder.length();
            fos = new FileOutputStream(outputPath);
            Manifest manifest = new Manifest();
            Attributes manifestAttr = manifest.getMainAttributes();
            //note:Must set Manifest-Version,or the manifest file will be empty!
            if (manifestVerion != null) {
                manifestAttr.putValue("Manifest-Version", manifestVerion);
                if (mainClass != null) {
                    manifestAttr.putValue("Main-Class", mainClass);
                }
                if (classPath != null) {
                    manifestAttr.putValue("Class-Path", classPath);
                }
            }
            java.util.Set entries = manifestAttr.entrySet();
//            for (java.util.Iterator i = entries.iterator(); i.hasNext();) {
//                System.out.println("Manifest attribute:>> "+ i.next().toString());
//            }

            jos = new JarOutputStream(fos, manifest);


            //System.out.println(strBaseFolder);

            checkDirectory(sourceDir);
        } finally {
            // Close the file output streams
            jos.flush();
            jos.close();
            fos.close();
        }

    }
}
