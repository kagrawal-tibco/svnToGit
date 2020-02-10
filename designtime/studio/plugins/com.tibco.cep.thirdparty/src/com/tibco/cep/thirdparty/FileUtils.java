package com.tibco.cep.thirdparty;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileUtils { 

	/**
	 * Returns the contents of the given file in String format
	 * @param fileToRead
	 * @return
	 */
	public static String readFile(File fileToRead) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(fileToRead);
			int avail = fis.available();
			byte[] arr = new byte[avail];
			fis.read(arr);
			return new String(arr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * Gets all the package names from the list of jarFiles.
	 * @param jarFiles
	 * @return A String of all package names, separated by a newline
	 */
	public static String getPackageNames(List<JarFile> jarFiles) {
		List<String> packageNames = new ArrayList<String>();
		for (JarFile jarFile : jarFiles) {
			Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				if (entry.isDirectory()) {
					continue;
				}
				String entryName = entry.getName();
				if (entryName.endsWith(".class")) {
					int index = entryName.lastIndexOf('/');
					if (index == -1) {
						// default package - do not add
						continue;
					}
					String entryPath = entryName.substring(0, index);
					String packageName = entryPath.replaceAll("/", ".");
					if (!packageNames.contains(packageName) && !packageName.startsWith("java.") && !packageName.startsWith("sun.")) {
						packageNames.add(packageName);
					}
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<packageNames.size(); i++) {
			sb.append(packageNames.get(i));
			if (i < packageNames.size()-1) {
				sb.append(",\n ");
			} else {
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	/**
	 * Given the list of jarFiles, returns a String appropriate
	 * for insertion in a MANIFEST.MF Bundle-Classpath entry
	 * @param jarFiles
	 * @return
	 */
	public static String getJarNames(List<JarFile> jarFiles) {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<jarFiles.size(); i++) {
			sb.append("external:");
			sb.append(jarFiles.get(i).getName().replace("\\", "/"));
			if (i < jarFiles.size()-1) {
				sb.append(",\n ");
			}
		}
		return sb.toString();
	}

}
