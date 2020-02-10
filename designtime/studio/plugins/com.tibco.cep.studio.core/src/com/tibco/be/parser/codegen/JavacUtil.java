package com.tibco.be.parser.codegen;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.osgi.framework.BundleException;

import com.tibco.be.util.OversizeStringConstants;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.tpcl.IRuntimeBundleClasspathProvider;

public class JavacUtil {
	
	public static final String RUNTIME_CLASSPATH_PROVIDER = "com.tibco.cep.tpcl.runtimeBundleClasspathProvider";
	public static final String RUNTIME_CLASSPATH_PROVIDER_ATTR = "classpathProvider";
	public static final String EXTERNAL_LIB_PREFIX = "external:";//$NON-NLS-1$
	public static final String REFERENCE_LIB_PREFIX = "reference:file:"; //$NON-NLS-1$
	public static final String PLGUINS_OUTPUT_DIR = "bin"; 
	private static final int BUFFER_SIZE = 2048;
	
	/**
	 * @param extendedClassPath
	 * @param urls
	 * @throws Exception
	 */
	public static void constructClassPathUrls(String extendedClassPath, Collection<URL> urls) throws Exception {
		String paths[] = extendedClassPath.split(File.pathSeparator);
		for (String path : paths) {
			File f = new File(path);
			constructClassPathUrls(urls, f);
		}
	}


	/**
	 * @param urls
	 * @param f
	 * @throws IOException
	 */
	public static void constructClassPaths(Collection<String> urls, File f) throws IOException {
		if (f.exists()) {
			if (f.isDirectory()) {
				for (File children : f.listFiles()) {
					constructClassPaths(urls, children);
				}
			} else if (f.isFile() && f.getName().endsWith(CodeGenConstants.JAR_FILE_EXTENSION)) {
				urls.add(f.getCanonicalPath());
			}
		}
		
	}
	/**
	 * @param urls
	 * @param f
	 * @throws MalformedURLException
	 * @throws URISyntaxException
	 */
	public static void constructClassPathUrls(Collection<URL> urls, File f) throws MalformedURLException, URISyntaxException {
		if (f.exists()) {
			if (f.isDirectory()) {
				for (File children : f.listFiles()) {
					constructClassPathUrls(urls, children);
				}
			} else if (f.isFile() && f.getName().endsWith(CodeGenConstants.JAR_FILE_EXTENSION)) {
				URL url = f.toURI().toURL();
				urls.add(url);
			}
		}

	}

	/**
	 * Loads the core runtime libraries from the installed base
	 * 
	 * @param urls
	 * @throws Exception
	 */
	@SuppressWarnings("restriction")
	public static void loadCoreInternalLibraries(Collection<String> urls) throws Exception {
		try {

			IExtensionRegistry reg = Platform.getExtensionRegistry();
			if (reg != null) {
				IConfigurationElement[] extensions = reg.getConfigurationElementsFor(RUNTIME_CLASSPATH_PROVIDER);
				for (int i = 0; i < extensions.length; i++) {
					IConfigurationElement element = extensions[i];
					final Object o = element.createExecutableExtension(RUNTIME_CLASSPATH_PROVIDER_ATTR);
					if (o != null && o instanceof IRuntimeBundleClasspathProvider) {
						File[] classpath = ((IRuntimeBundleClasspathProvider) o).getClasspath();
						for (File file : classpath) {
							constructClassPaths(urls, file);
						}
//						for (String s : classpath) {
//							if (s.startsWith(".")) {
//								File runtimeJar = ((org.eclipse.osgi.baseadaptor.BaseData) b.getBundleData()).getBundleFile().getFile(s, true);
//								if (runtimeJar != null) {
//									constructClassPaths(urls, new Path(runtimeJar.getPath()).toFile());
//								}
//								continue;
//							}
//							if (s.startsWith(REFERENCE_LIB_PREFIX)) {
//								Path p = new Path(s.substring(REFERENCE_LIB_PREFIX.length()));
//								String jpath = p.toOSString();
//								if (jpath != null && !jpath.isEmpty()) {
//									constructClassPaths(urls, new Path(jpath).toFile());
//									continue;
//								}
//							}
//							if (s.startsWith(EXTERNAL_LIB_PREFIX)) { // RUNTIME
//								String path = StudioProjectConfigurationManager.substituteVars(s.substring(EXTERNAL_LIB_PREFIX.length()));
//								constructClassPaths(urls, new Path(path).toFile());
//								continue;
//							}
//							File file = new File(s);
//							if (file.exists() == true) {
//								String path = file.getPath();
//								if(file.isDirectory() && file.toPath().toString().endsWith(File.separator+PLGUINS_OUTPUT_DIR)){
//									//should happen in dev environment only
//									urls.add(file.toPath().toString());
//								}
//								constructClassPaths(urls, new Path(path).toFile());
//							} else {
//								// DEV environment
//								File runtimeJarFile = ((org.eclipse.osgi.baseadaptor.BaseData) b.getBundleData()).getBundleFile().getFile(s, true);
//								if (runtimeJarFile == null) {
//									System.err.println("Could not find runtime jar " + s + " in bundle " + b.getSymbolicName());
//									continue;
//								}
//								String path = runtimeJarFile.getPath();
//								constructClassPaths(urls, new Path(path).toFile());
//							}
//						}
					}
				}
			}
		} catch (BundleException e) {
			StudioCorePlugin.log(e);
		} catch (CoreException e) {
			StudioCorePlugin.log(e);
		}
	}
	
	/**
	 * return a class name to byte code map that contains in the JarInputStream
	 * 
	 * @param jis
	 * @return
	 * @throws Exception
	 */

	static public Map<String, byte[]> readJarInputStream(JarInputStream jis) throws Exception {
		Map<String, byte[]> nameToByteCode = new HashMap<String, byte[]>();
		final byte[][] bufHandle = new byte[][] { new byte[8192] };
		for (JarEntry je = jis.getNextJarEntry(); je != null; je = jis.getNextJarEntry()) {
			if (je.isDirectory())
				continue;
			String pathname = je.getName();
			if (pathname.endsWith(".class")) {
				pathname = getClassName(pathname);
				byte[] b = getBytes(bufHandle, jis);
				nameToByteCode.put(pathname, b);
			} else if (pathname.equals(OversizeStringConstants.PROPERTY_FILE_NAME)) {
				byte[] b = getBytes(bufHandle, jis);
				nameToByteCode.put(pathname, b);
			}
		}
		return nameToByteCode;
	}
	
	/**
	 * @param bufHandle
	 * @param jis
	 * @return
	 * @throws IOException
	 */
	static private byte[] getBytes(byte[][] bufHandle, JarInputStream jis) throws IOException {
		byte[] buf = bufHandle[0];
		int totalBytes = 0;
		while (true) {
			int read = 0;
			read = jis.read(buf, totalBytes, buf.length - totalBytes);
			if (read == -1) {
				byte[] ret = new byte[totalBytes];
				System.arraycopy(buf, 0, ret, 0, totalBytes);
				return ret;
			} else {
				totalBytes += read;
				if (totalBytes == buf.length) {
					byte[] tmp = new byte[buf.length * 2];
					System.arraycopy(buf, 0, tmp, 0, buf.length);
					buf = tmp;
					bufHandle[0] = tmp;
				}
			}
		}
	}
	

	/**
	 * @param fullpath
	 * @return
	 */
	static private String getClassName(String fullpath) {
		StringBuffer classBuf = new StringBuffer();
		String[] token = fullpath.split("/|\\.|\\\\");

		if (token == null || token.length == 0)
			return fullpath;
		int count = 0;
		if (!("class".equalsIgnoreCase(token[token.length - 1]))) {
			count = token.length;
		} else {
			count = token.length - 1;
		}
		classBuf.append(token[0]);
		for (int i = 1; i < count; i++) {
			classBuf.append(".");
			classBuf.append(token[i]);
		}
		return classBuf.toString();
	}
	
	
	public static void writeJarOutput(File targetDir,JarInputStream jis) throws FileNotFoundException, IOException {
		JarEntry entry = null;
		BufferedOutputStream dest = null;
		try{

			while ((entry = jis.getNextJarEntry()) != null) {
				if (entry.isDirectory()) {
					File dir = new File(targetDir,entry.getName());
					dir.mkdir();
					if (entry.getTime() != -1) dir.setLastModified(entry.getTime());
					continue;
				}
				int count;
				byte data[] = new byte[BUFFER_SIZE];
				File destFile = new File(targetDir, entry.getName());
				FileOutputStream fos = new FileOutputStream(destFile);
				dest = new BufferedOutputStream(fos, BUFFER_SIZE);
				while ((count = jis.read(data, 0, BUFFER_SIZE)) != -1) {
					dest.write(data, 0, count);
				}
				dest.flush();
				dest.close();
				if (entry.getTime() != -1) destFile.setLastModified(entry.getTime());
			}

		} finally {
			jis.close();
		}
	}
	public static byte[] readBeJar(File earFile) throws FileNotFoundException, IOException {
        ZipInputStream earZis = new ZipInputStream(new FileInputStream(earFile));
        ZipEntry barZe = earZis.getNextEntry();
        ZipInputStream barZis = null;
        ZipEntry beJarZe = null;
        boolean foundBar = false;
        try {
            outer: for (; barZe != null; barZe = earZis.getNextEntry()) {
                String name = barZe.getName();
                if (barZe != null && name.toLowerCase().endsWith(".bar")) {
                    foundBar = true;
                    barZis = new ZipInputStream(earZis);
                    beJarZe = barZis.getNextEntry();
                    for (; beJarZe != null; beJarZe = barZis.getNextEntry()) {
                        String beJarName = beJarZe.getName();
                        if (beJarZe != null && beJarName.toLowerCase().endsWith("be.jar")) {
                            break outer;
                        }
                    }
                }
            }
        } catch (IOException ioe){
            if(barZis != null) barZis.close();
            if(earZis != null) earZis.close();
            throw ioe;
        }

        if (!foundBar) throw new IOException("couldn't find BAR in " + earFile.getAbsolutePath());
        if (beJarZe == null || barZis == null) throw new IOException("couldn't find be.jar in " + barZe.getName());

        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        long totalRead = 0;
        long beJarSize = barZe.getSize();
        try {
            while (true) {
                int read = barZis.read(buf);
                if (read == -1) break;
                totalRead += read;
                fos.write(buf, 0, read);
            }
        } finally {
            fos.flush();
            fos.close();
            if(barZis != null) barZis.close();
            if(earZis != null) earZis.close();
        }

        if (beJarSize > totalRead) throw new IOException("didn't read entire be.jar from " + barZe.getName());
        return fos.toByteArray();
    }
	
    public static File writeBeJar(File earFile, File tempDir) throws FileNotFoundException, IOException {
        ZipInputStream earZis = new ZipInputStream(new FileInputStream(earFile));
        ZipEntry barZe = earZis.getNextEntry();
        ZipInputStream barZis = null;
        ZipEntry beJarZe = null;
        boolean foundBar = false;
        try {
            outer: for (; barZe != null; barZe = earZis.getNextEntry()) {
                String name = barZe.getName();
                if (barZe != null && name.toLowerCase().endsWith(".bar")) {
                    foundBar = true;
                    barZis = new ZipInputStream(earZis);
                    beJarZe = barZis.getNextEntry();
                    for (; beJarZe != null; beJarZe = barZis.getNextEntry()) {
                        String beJarName = beJarZe.getName();
                        if (beJarZe != null && beJarName.toLowerCase().endsWith("be.jar")) {
                            break outer;
                        }
                    }
                }
            }
        } catch (IOException ioe){
            if(barZis != null) barZis.close();
            if(earZis != null) earZis.close();
            throw ioe;
        }

        if (!foundBar) throw new IOException("couldn't find BAR in " + earFile.getAbsolutePath());
        if (beJarZe == null || barZis == null) throw new IOException("couldn't find be.jar in " + barZe.getName());

        File beJarFile = new File(tempDir, "be.jar");
        if(beJarFile.exists()) beJarFile.delete();
        beJarFile.getParentFile().mkdirs();
        beJarFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(beJarFile);
        byte[] buf = new byte[1024];
        long totalRead = 0;
        long beJarSize = barZe.getSize();
        try {
            while (true) {
                int read = barZis.read(buf);
                if (read == -1) break;
                totalRead += read;
                fos.write(buf, 0, read);
            }
        } finally {
            fos.flush();
            fos.close();
            if(barZis != null) barZis.close();
            if(earZis != null) earZis.close();
        }

        if (beJarSize > totalRead) throw new IOException("didn't read entire be.jar from " + barZe.getName());
        return beJarFile;
    }
   

    public static void clearTempDir(File tempDir) {
        if(!tempDir.isDirectory()) return;
        deleteFileOrDirectory(tempDir, true);
    }
    public static void deleteRecursive(File tempDir) {
        deleteFileOrDirectory(tempDir, false);
    }
    //copied from DefaultRuntimeClassesPackager
    private static void deleteFileOrDirectory(File f, boolean keep) {
        if (f.isDirectory()) {
            final File[] files = f.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFileOrDirectory(files[i], false);
            }//for
        }//if
        if(!keep) f.delete();
    }//deleteFileOrDirectory
    
    public static boolean isBuildParticpatingJavaSource(IPath path){
    	try {
    		if(path!=null && path.getFileExtension()!=null && path.getFileExtension().equals("java")){
    			IFile javaFile = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(path);
    			if(javaFile!=null){
	     	   		IJavaProject javaProject  = JavaCore.create(javaFile.getProject());
	     	   	   	if(javaProject.isOnClasspath(javaFile) ){
		    			return true;
		    		}
    			}
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return false;
    }
    
    public static boolean isBuildParticpatingJavaSource(IFile file){
    	try {
    		if(file!=null && file.getFileExtension()!=null && file.getFileExtension().equals("java")){
    			IJavaProject javaProject  = JavaCore.create(file.getProject());
     	   	   	if(javaProject.isOnClasspath(file) ){
	    			return true;
	    		}
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return false;
    }
}