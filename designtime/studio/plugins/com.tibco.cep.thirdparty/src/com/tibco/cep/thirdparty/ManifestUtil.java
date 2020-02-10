package com.tibco.cep.thirdparty;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.jar.JarFile;

import org.eclipse.core.runtime.IPath;

import com.tibco.cep.studio.util.StudioConfig;

public class ManifestUtil { 

//	// The jar files already exported by com.tibco.cep.bui.external
//	private static String[] buiExternalJars = 
//		new String[] { "commons-codec-1.3.jar", "commons-httpclient.jar", "commons-logging.jar",
//				"TIBCOrt.jar", "TIBCOxml.jar", "log4j.jar", "tibrvj.jar", "TIBCrypt.jar" };
//
//	// The jar files already exported by com.tibco.cep.be.external
//	private static String[] beCoreExternalJars = 
//		new String[] {"cep-common.jar", "cep-container.jar", "cep-drivers.jar",
//				"cep-kernel.jar", "be-palettes.jar", "be-functions.jar" };
//
//	// The jar files already exported by com.tibco.cep.be.core
//	private static String[] beExternalJars = 
//		new String[] { "coherence.jar", "je.jar", "jide-all.jar", "tangosol.jar",
//						"tomsawyer-viz-all.jar", "tools.jar", "javassist.jar" };
	
	/**
	 * Write the delegate MANIFEST.MF file to disk,
	 * based on the MANIFEST.MF.template file
	 * @return
	 */
	public static boolean writeDelegateManifest(String extendedCP) {

		DelegatePlugin.debug("Processing studio.extended.classpath");
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
		File[] files = entryFile.listFiles();
		for (File file : files) {
			
			Path path = file.toPath();
			if (Files.isSymbolicLink(path)) {
				Path linkPath = Files.readSymbolicLink(path);
				file = linkPath.toFile();
			}
			if (!file.exists()) {
				continue;
			}
			if (!file.canRead()) {
				continue;
			}
			
			if (file.getName().endsWith(".jar") && !externalJarNames.contains(file.getName())) {
				jarFiles.add(new JarFile(file));
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
		String sep = ";";
		if (!System.getProperty("os.name").startsWith("Window")) {
			sep = ":";
		}
		
		StringTokenizer st = new StringTokenizer(standardClasspath, sep);
		List<JarFile> jarFiles = new ArrayList<JarFile>();

		// We do not want to add the jars that already exported to the delegate classpath.
		// First, remove these jars from the list to be processed
		// TODO : Dynamically determine these jars by inspecting the appropriate Bundle
		List<String> externalJarNames = new ArrayList<String>();
		// Remove the jars exported by com.tibco.cep.bui.external
//		for (String jarName : buiExternalJars) {
//			externalJarNames.add(jarName);
//		}
//		// Remove the jars exported by com.tibco.cep.be.external
//		for (String jarName : beExternalJars) {
//			externalJarNames.add(jarName);
//		}
//		// Remove the jars exported by com.tibco.cep.be.core
//		for (String jarName : beCoreExternalJars) {
//			externalJarNames.add(jarName);
//		}
		try {
			while (st.hasMoreTokens()) {
				String entry = st.nextToken();
				File entryFile = new File(entry);
				if (!entryFile.exists()) {
					continue;
				}
				Path path = entryFile.toPath();
				if (Files.isSymbolicLink(path)) {
					Path linkPath = Files.readSymbolicLink(path);
					entryFile = linkPath.toFile();
				}
				if (!entryFile.exists()) {
					continue;
				}
				if (!entryFile.canRead()) {
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
		//DelegatePlugin.LOGGER.logDebug("Processing {0} jar files", new Object[] {jarFiles.size()});
		
		if (jarFiles.size() == 0) {
			//DelegatePlugin.LOGGER.logDebug("No jar files were found on the studio.extended.classpath.  MANIFEST.MF file not written");
			return false;
		}
		String jarNames = FileUtils.getJarNames(jarFiles);
		String packageNames = FileUtils.getPackageNames(jarFiles);
		
		URL templateEntry = DelegatePlugin.getDefault().getBundle().getEntry("MANIFEST.MF.template");
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
				stream.close();
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
					DelegatePlugin.debug(ManifestUtil.class.getName(), "Unable to create delegate bundle file");
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
			return bundleLocation.toURL();
		} catch (IOException e) {
			e.printStackTrace();
			//DelegatePlugin.LOGGER.logDebug("Unable to resolve delegate bundle file");
			//DelegatePlugin.LOGGER.logDebug(e);
		}
		return null;
	}
	
	/**
	 * Get the location of the delegate Bundle
	 * @return
	 */
	public static String getDelegateBundleLocation() {
		IPath path = DelegatePlugin.getDefault().getStateLocation();
		String fileLoc = path.toOSString()+File.separator+"delegate"+File.separator;
		return fileLoc;
	}


}
