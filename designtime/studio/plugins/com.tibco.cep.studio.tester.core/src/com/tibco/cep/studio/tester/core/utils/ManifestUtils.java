package com.tibco.cep.studio.tester.core.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.jar.JarFile;

import org.eclipse.core.runtime.IPath;

import com.tibco.cep.studio.tester.core.StudioTesterCorePlugin;
import com.tibco.cep.studio.util.StudioConfig;

public class ManifestUtils {

	/**
	 * Write the delegate MANIFEST.MF file to disk,
	 * based on the MANIFEST.MF.template file
	 * @return
	 */
	public static boolean writeDelegateManifest(String extendedCP) {

		if (extendedCP == null || extendedCP.length() == 0) {
			return false;
		}

		String extendedClasspath = preprocessClasspath(extendedCP);
		
		return writeManifestFile(extendedClasspath);
	}
	
	/**
	 * Collect all jar files in the given directory
	 * @param jarFiles
	 * @param entryFile
	 * @param externalJarNames
	 * @throws IOException
	 */
	private static void processDirectory(List<JarFile> jarFiles, File entryFile, List<String> externalJarNames) throws IOException {
		// entryFile is a directory
		String entryFileName = entryFile.getAbsolutePath();
		if (entryFileName.indexOf(File.separator + "jre" + File.separator) != -1) {
			return;
		}
		String[] fileNames = entryFile.list();
		for (String fileName : fileNames) {
			if (fileName.endsWith(".jar") && !externalJarNames.contains(fileName)) {
				jarFiles.add(new JarFile(new File(entryFile, fileName)));
			}
		}
	}

	/**
	 * Process the classpath and replace all environment variables with their
	 * actual value
	 * @param standardCP
	 * @return
	 */
	private static String preprocessClasspath(String standardCP) {
		while (standardCP.indexOf('%') != -1) {
			int i1 = standardCP.indexOf('%');
			int i2 = standardCP.indexOf('%', i1+1);
			if (i2 == -1) {
				break;
			}
			String var = standardCP.substring(i1+1, i2);
			String varSubst = System.getProperty(var);
			if (varSubst == null) {
				varSubst = StudioConfig.getInstance().getProperty("tibco.env."+var);
			}
			if (varSubst == null) {
				varSubst = "";
			}
			standardCP = standardCP.replaceAll("%"+var+"%", varSubst);
		}
		return standardCP;
	}

	private static boolean writeManifestFile(String standardClasspath) {
		StringTokenizer st = new StringTokenizer(standardClasspath, ";");
		List<JarFile> jarFiles = new ArrayList<JarFile>();

		// We do not want to add the jars that already exported to the delegate classpath.
		// First, remove these jars from the list to be processed
		// TODO : Dynamically determine these jars by inspecting the appropriate Bundle
		List<String> externalJarNames = new ArrayList<String>();
		
		try {
			while (st.hasMoreTokens()) {
				String entry = st.nextToken();
				File entryFile = new File(entry);
				if (!entryFile.exists()) {
					continue;
				}
				if (entryFile.isDirectory()) {
					processDirectory(jarFiles, entryFile, externalJarNames);
				} else {
					JarFile jarFile;
					jarFile = new JarFile(entryFile);
					if (!jarFiles.contains(jarFile) && !externalJarNames.contains(entryFile.getName())) {
						jarFiles.add(jarFile);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
//		got all the jars, need to extract package information and write manifest

		if (jarFiles.size() == 0) {
			return false;
		}
		String jarNames = TesterCoreUtils.getJarNames(jarFiles);
		String packageNames = TesterCoreUtils.getPackageNames(jarFiles);
		
		URL templateEntry = StudioTesterCorePlugin.getDefault().getBundle().getEntry("MANIFEST.MF.template");
		InputStream stream = null;
		String manifestFileContents = null;
		try {
			stream = templateEntry.openStream();
			byte[] arr = new byte[stream.available()];
			stream.read(arr);
			manifestFileContents = new String(arr);
			
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (stream != null) {
					stream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String newManifestContents = manifestFileContents.replace("%BUNDLE_CLASSPATH%", jarNames);
		newManifestContents = newManifestContents.replace("%EXPORTED_PACKAGES%", packageNames);
		
		String fileLoc = getDelegateBundleLocation();
		fileLoc += "META-INF/MANIFEST.MF";
		File manifestFile = new File(fileLoc);

		FileOutputStream fos = null;
		try {
			if (!manifestFile.exists()) {
				if (!manifestFile.getParentFile().exists()) {
					manifestFile.getParentFile().mkdirs();
				}
				if (!manifestFile.createNewFile()) {
					return false;
				}
			}
			fos = new FileOutputStream(manifestFile);
			fos.write(newManifestContents.getBytes());
			fos.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
		
	}

	/**
	 * Get the URL of the delegate Bundle location
	 * @return
	 */
	public static URL getDelegateBundleLocationURL() {
		try {
			String delegateBundleLoc = getDelegateBundleLocation();
			File bundleLocation = new File(delegateBundleLoc);
			
			return bundleLocation.toURI().toURL();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get the location of the delegate Bundle
	 * @return
	 */
	public static String getDelegateBundleLocation() {
		IPath path = StudioTesterCorePlugin.getDefault().getStateLocation();
		String fileLoc = path.toOSString() + File.separator + "delegate" + File.separator;
		return fileLoc;
	}
}
