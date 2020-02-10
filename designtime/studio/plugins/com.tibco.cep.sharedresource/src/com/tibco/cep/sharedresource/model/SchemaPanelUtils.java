package com.tibco.cep.sharedresource.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.eclipse.core.resources.ResourcesPlugin;
import org.osgi.framework.Bundle;

import com.tibco.cep.sharedresource.SharedResourcePlugin;


/*
@author ssailapp
@date Nov 19, 2009 6:23:11 PM
 */

public class SchemaPanelUtils {
	
	public static String getResourcePath(String resourceName) {
		return (getResourceURL(resourceName).getPath());		
	}
	
	public static URL getResourceURL(String resourceName) {
		return (SchemaPanelUtils.class.getClassLoader().getResource(resourceName));
	}
	
	public static String getResourceFile(Bundle bundle, String jarFileName, String resourceName) throws IOException {
		String bundleLoc = getBundleLocation(bundle);
		if (bundleLoc == null)
			return null;
		JarFile jarFile = new JarFile(bundleLoc + jarFileName);
		JarEntry entry = jarFile.getJarEntry(resourceName);
		if (bundleLoc != null && entry != null) {
			InputStream input = jarFile.getInputStream(entry);
			String outFileName = cacheFile(resourceName, input);
			return outFileName;
		}
		return null;
	}
	
	public static String getResourceFile(Bundle bundle, String resourceName) throws IOException {
		URL resourceUrl = bundle.getEntry(resourceName);
		if (resourceUrl != null) {
			InputStream input = resourceUrl.openStream();
			String outFileName = cacheFile(resourceUrl.getFile(), input);
			return outFileName;
		}
		return null;
	}

	private static String cacheFile(String resourceName, InputStream input) throws IOException {
		InputStreamReader isr = new InputStreamReader(input);
		BufferedReader reader = new BufferedReader(isr);
		String outFileName = getCacheFileLocation(resourceName);
		new File(outFileName).getParentFile().mkdirs();
		BufferedWriter writer = new BufferedWriter(new FileWriter(outFileName));
		String line;
		while ((line = reader.readLine()) != null) {
			writer.write(line);
			writer.newLine();
		}
		reader.close();
		writer.close();
		return (outFileName);
	}
	
	public static String getParseSchemaFile(String schemaFile) {
		return (getCacheFileLocation(schemaFile));
	}
	
	private static String getCacheFileLocation(String resourceName) {
		String metadataDir = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString() + "/.metadata/.plugins/" + SharedResourcePlugin.PLUGIN_ID;
		String cacheFile = metadataDir + "/" + resourceName;
		return cacheFile;
	}
	
	private static String getBundleLocation(Bundle bundle) {
		String loc = bundle.getLocation();
		loc = loc.replaceFirst("reference:file:", "");
		if (loc.startsWith("/")) {
			loc = loc.replaceFirst("/", "");
		} else if (!new File(loc).isAbsolute()) {
			loc = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString() + "/" + loc;
		}
		if (new File(loc).exists())	
			return loc;
		return null;
	}
}