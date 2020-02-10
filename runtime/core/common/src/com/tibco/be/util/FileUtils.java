package com.tibco.be.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.util.PlatformUtil;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Mar 6, 2009
 * Time: 3:13:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileUtils {

    private static Logger LOGGER = null;
    static  {
    	if(!PlatformUtil.INSTANCE.isStudioPlatform()){
    		LOGGER = LogManagerFactory.getLogManager().getLogger(FileUtils.class);
    	}
    }

    public static void addBytesFromJar(File jarFile, Map<String, byte[]> bytes, String[] extensions) {
        if (!jarFile.exists()) return;
        try {
            ZipInputStream zis = new ZipInputStream(new FileInputStream(jarFile));

            ByteArray ba = new ByteArray(8192);
            byte[] buf = new byte[8192];
            for (ZipEntry ze = zis.getNextEntry(); ze != null; ze = zis.getNextEntry()) {
                if (ze.isDirectory()) continue;
                String pathname = ze.getName();
                if (checkExt(pathname, extensions)) {
                    String className = ModelUtils.convertPathToPackage(pathname, '/');
                    if (className != null && className.length() > 0) {
                        byte[] entry = getBytes(buf, ba, ze, zis);
                        if (entry != null) bytes.put(className, entry);
                    }
                }
            }
        }
        catch (FileNotFoundException fnfe) {
        	if(LOGGER != null) {
        		LOGGER.log(Level.ERROR, fnfe, "");
        	} else {
        		fnfe.printStackTrace();
        	}
        }
        catch (IOException ioe) {
        	if(LOGGER != null) {
        		LOGGER.log(Level.ERROR, ioe, "");
        	}else {
        		ioe.printStackTrace();
        	}
        }
    }

    /**
     * Loads a class file matching the classname from the specified jar file
     *
     * @param jarFile
     * @param className
     * @param extensions
     * @param logger
     * @return byte[] for the class bytes
     */
    public static byte[] getClassBytesFromJar(File jarFile,
                                              String className,
                                              String[] extensions) {
        if (!jarFile.exists()) {
            return null;
        }
        if (className == null || className.length() == 0) {
            throw new IllegalArgumentException("Class name cannot be null");
        }
        try {
            ZipInputStream zis = new ZipInputStream(new FileInputStream(jarFile));
            ByteArray ba = new ByteArray(8192);
            byte[] buf = new byte[8192];
            for (ZipEntry ze = zis.getNextEntry(); ze != null; ze = zis.getNextEntry()) {
                if (ze.isDirectory()) continue;
                String pathname = ze.getName();
                if (checkExt(pathname, extensions)) {
                    //Get the entire path of zip entry and check if it is a class
                    String clazz = ModelUtils.convertPathToPackage(pathname, '/');
                    //If this class is same as the passed class....
                    if (clazz.intern() == className.intern()) {
                        return getBytes(buf, ba, ze, zis);
                    }
                }
            }
        } catch (FileNotFoundException fnfe) {
        	if(LOGGER != null) {
        		LOGGER.log(Level.ERROR, fnfe, "");
        	} else {
        		fnfe.printStackTrace();
        	}
        }
        catch (IOException ioe) {
        	if(LOGGER != null) {
        		LOGGER.log(Level.ERROR, ioe, "");
        	} else {
        		ioe.printStackTrace();
        	}
        }
        return null;
    }

    /**
     * Loads a class file matching the classname from the specified root directory.
     *
     * @param rootDir
     * @param className
     * @return byte[] for the class bytes
     */
    public static byte[] getClassBytesFromPath(File rootDir,
                                               String className) {
        if (!rootDir.isDirectory()) {
            throw new IllegalArgumentException("Root path '" + rootDir + "' is not directory");
        }
        if (!rootDir.canRead()) {
            throw new IllegalArgumentException("Root directory '" + rootDir + "' cannot be read");
        }
        String dirPath = rootDir.getAbsolutePath();
        // Message class name
        String clazz = className.replace('.', File.separatorChar);
        // Append to root
        StringBuilder sBuilder = new StringBuilder(dirPath);
        sBuilder.append(File.separatorChar);
        sBuilder.append(clazz);
        sBuilder.append(".class");
        // Get full path
        String fullPath = sBuilder.toString();
        File classFile = new File(fullPath);

        if (!classFile.exists()) {
            String message = "Class " + className + " not found in " + dirPath;
            if (LOGGER != null) {
            	LOGGER.log(Level.ERROR, message);
            } else {
            	System.err.println("Error: " + message);
            }
            throw new RuntimeException(message);
        }
        try {
            return getBytes(classFile);
        } catch (IOException e) {
        	if (LOGGER != null) {
        		LOGGER.log(Level.ERROR, "Failed during getClassBytesFromPath call", e);
        	} else {
        		e.printStackTrace();
        	}
            return null;
        }
    }

    public static boolean checkExt(String path, String[] exts) {
        for (String ext : exts) {
            if (path.endsWith(ext)) return true;
        }
        return false;
    }

    /**
     * 
     * @param rootDir -> The directory specified by {@link com.tibco.cep.runtime.util.SystemProperty#CLUSTER_EXTERNAL_CLASSES_PATH}
     * @param bytes
     * @param extensions
     * @param packageExclusions -> String[] of possible packages excluded while loading
     * @param logger
     */
    public static void addBytesFromPath(File rootDir,
                                        Map<String, byte[]> bytes,
                                        String[] extensions,
                                        String[] packageExclusions) {
        FileFilter filter = null;
        if (extensions != null) {
            filter = new ExtensionFilter(extensions);
            if (packageExclusions != null) {
                filter = new PackageExclusionFilter(filter, rootDir.getAbsolutePath(), packageExclusions);
            }
        }
        addBytesFromPath(rootDir, bytes, filter);
    }

    public static void addBytesFromPath(File rootDir,
                                        Map<String, byte[]> bytes,
                                        FileFilter filter) {
        ArrayList<File> files = new ArrayList<File>();
        appendFilesRecursively(rootDir, files, filter);
        if (files.size() <= 0) return;

        String baseCanonicalPath = null;
        String baseAbsolutePath = rootDir.getAbsolutePath();
        try {
            baseCanonicalPath = rootDir.getCanonicalPath();
        } catch (IOException ioe) {
        }

        for (File file : files) {
            String classNameAsPath = null;
            if (baseCanonicalPath != null) {
                String canonical = null;
                try {
                    canonical = file.getCanonicalPath();
                } catch (IOException ioe) {
                }
                if (canonical != null) {
                    classNameAsPath = canonical.substring(baseCanonicalPath.length(), canonical.length());
                }
            }
            if (classNameAsPath == null && baseAbsolutePath != null) {
                String absolute = file.getAbsolutePath();
                classNameAsPath = absolute.substring(baseAbsolutePath.length(), absolute.length());
            }

            String className = ModelUtils.convertPathToPackage(classNameAsPath, File.separatorChar);
            if (className != null && className.length() > 0) {
                byte[] bytearr = null;
                try {
                    bytearr = getBytes(file);
                } catch (IOException ioe) {
                	if (LOGGER != null) {
                		LOGGER.log(Level.ERROR, "", ioe);
                	} else {
                		ioe.printStackTrace();
                	}
                }
                if (bytearr != null) bytes.put(className, bytearr);
            }
        }
    }

    public static void appendFilesRecursively(File srcDir, ArrayList<File> fileList) {
        appendFilesRecursively(srcDir, fileList, (FileFilter) null);
    }

    public static void appendFilesRecursively(File srcDir, ArrayList<File> fileList, String[] exts) {
        FileFilter filter = null;
        if (exts != null) {
            filter = new ExtensionFilter(exts);
        }
        appendFilesRecursively(srcDir, fileList, filter);
    }

    public static void appendFilesRecursively(File srcDir, ArrayList<File> fileList, FileFilter filter) {
        File[] files = srcDir.listFiles(filter);

        if (files == null) return;
        fileList.addAll(Arrays.asList(files));

        File[] subDirs = srcDir.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });

        if (subDirs == null) return;

        for (int ii = 0; ii < subDirs.length; ii++) {
            appendFilesRecursively(subDirs[ii], fileList, filter);
        }
    }

    public static byte[] getBytes(File classFile) throws IOException {
        FileInputStream fis = new FileInputStream(classFile);
        long size = classFile.length();
        if (size > Integer.MAX_VALUE) return null;
        byte[] bytes = new byte[(int) size];
        int bytesRead = 0;
        try {
            bytesRead = fis.read(bytes, 0, bytes.length);
        } finally {
            fis.close();
        }
        if (bytesRead < bytes.length) {
            throw new IOException("Read fewer bytes than file size for " + classFile.getCanonicalPath() + 
                    ". Read " + bytesRead + " of " + bytes.length);
        }
        return bytes;
    }

    public static byte[] getBytes(byte[] buf, ByteArray bytes, ZipEntry ze, ZipInputStream zis) throws IOException {
        if (ze.getSize() > Integer.MAX_VALUE) return null;
        bytes.reset();

        for (int result = zis.read(buf); result != -1; result = zis.read(buf)) {
            bytes.add(buf, result);
        }
        return bytes.getValue();

    }

    public static boolean createWritableDirectory(String dataStorePath) {
        // Check if the PersistenceDirectory exists. If not, create it
        File dataStoreHandle = new File(dataStorePath);
        if (dataStoreHandle.exists()) {
            if (dataStoreHandle.isDirectory()) {
                if (!dataStoreHandle.canWrite()) {
                    throw new RuntimeException("Directory '" + dataStorePath + "' does not have write permissions.");
                }
                LOGGER.log(Level.INFO, "Directory '" + dataStorePath + "' already exists");
            } else {
                // File exists with the same name? Flag as error
                String msg = "Path '" + dataStorePath + "' is not a directory.";
                LOGGER.log(Level.ERROR, msg);
                throw new RuntimeException(msg);
            }
        } else {
            LOGGER.log(Level.INFO, "Creating directory hierarchy: '" + dataStorePath + "'");
            // Create the full hierarchy if needed
            if (!dataStoreHandle.mkdirs()) {
                String msg = "Executing mkdirs() failed!. Unable to create path: '" + dataStorePath + "'";
                throw new RuntimeException(msg);
            }
        }
        return true;
    }

    private static class ExtensionFilter implements FileFilter {
        private final String[] exts;

        private ExtensionFilter(final String[] exts) {
            this.exts = exts;
        }

        public boolean accept(File file) {
            return file.exists() && checkExt(file.getName(), exts);
        }
    }

    private static class PackageExclusionFilter implements FileFilter {

        /**
         * Wrap around existing filter
         */
        private FileFilter extensionFilter;

        private final String[] packageExclusions;

        private String rootDir;

        private PackageExclusionFilter(FileFilter extensionFilter,
                                       String rootDir,
                                       String[] packageExclusions) {
            this.packageExclusions = packageExclusions;
            this.rootDir = rootDir;
            this.extensionFilter = extensionFilter;
        }

        public boolean accept(File file) {
            return extensionFilter.accept(file)
                    /** Check whether file does not belong to excluded packages **/
                    && !isExcluded(file);
        }

        private boolean isExcluded(File file) {
            //Get directory path
            String absolutePackagePath = file.getParentFile().getAbsolutePath();
            String relativeFilePathName = absolutePackagePath.substring(rootDir.length() + 1, absolutePackagePath.length());
            String targetPackageName = ModelUtils.convertPathToPackage(relativeFilePathName, File.separatorChar);
            boolean excluded = false;
            for (String packageExclusion : packageExclusions) {
                if (packageExclusion.endsWith("*")) {
                    // This has wildcard
                    if (targetPackageName.length() > packageExclusion.length()) {
                        int offsetLength = packageExclusion.length() - 1;
                        if (targetPackageName.regionMatches(0, packageExclusion, 0, offsetLength)) {
                            // This class needs to be excluded as per this exclusion
                            excluded = true;
                            if (LOGGER != null) {
                            	LOGGER.log(Level.WARN, "Classes in package %s excluded from loading sequence", targetPackageName);
                            } else {
                            	System.err.println(String.format("Classes in package %s excluded from loading sequence", targetPackageName));
                            }
                        }
                    }
                } else {
                    // Now check simple equality
                    if (targetPackageName.intern() == packageExclusion.intern()) {
                        excluded = true;
                        if (LOGGER != null) {
                        	LOGGER.log(Level.WARN, "Classes in package %s excluded from loading sequence", targetPackageName);
                        } else {
                        	System.err.println(String.format("Classes in package %s excluded from loading sequence", targetPackageName));
                        }
                    }
                }
            }
            return excluded;
        }
    }
}
