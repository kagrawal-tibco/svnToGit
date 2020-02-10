package com.tibco.cep.studio.core.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Enumeration;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.osgi.framework.Bundle;

import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.preferences.StudioCorePreferenceConstants;

/**
 * 
 * @author sasahoo
 *
 */
public class DocUtils {

	public static String getCSSString() {
		return Platform.getPreferencesService().getString(StudioCorePlugin.PLUGIN_ID, StudioCorePreferenceConstants.DOC_GEN_CSS_STRING, getDefaultCSSString(), null);
	}
	
	public static String getDefaultCSSString() {
		return DefaultScope.INSTANCE.getNode(StudioCorePlugin.PLUGIN_ID).get(StudioCorePreferenceConstants.DOC_GEN_CSS_STRING, null);
	}
	
	public static String readFile(String path) throws IOException { 
		FileInputStream stream = new FileInputStream(new File(path)); 
		try { 
			FileChannel fileChannel = stream.getChannel(); 
			MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size()); 
			return Charset.defaultCharset().decode(mappedByteBuffer).toString();//Instead of using default, pass in a decoder. 
		} 
		finally { 
			stream.close(); 
		} 
	} 
	
	public static void copyTemplates(String templatePluginPath, String destLocation, String filePattern) {
		try {
			Bundle bundle = Platform.getBundle(StudioCorePlugin.PLUGIN_ID);
			Enumeration<URL> files = bundle.findEntries(templatePluginPath, filePattern, false);
			while (files.hasMoreElements()) {
				URL file = files.nextElement();
				String filePath = file.getFile();
				filePath = filePath.replaceFirst(templatePluginPath, "");
				File destFile = new File(destLocation + "/" + filePath);
				copyFile(file.openStream(), destFile);
			}

			//copyDirectory(new File(srcDir), new File(dstDir));
		} catch (Exception e) {
			e.printStackTrace();
			StudioCorePlugin.log(e);
		}
	}

	public static void copyFile(InputStream stream, File dst) throws IOException {
		OutputStream out = new FileOutputStream(dst);
		byte[] buf = new byte[1024];
		int len;
		while ((len = stream.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		stream.close();
		out.close();
	}
	
	public static void copyString(String stream, File dst) throws IOException {
		try {
		    BufferedWriter out = new BufferedWriter(new FileWriter(dst));
		    out.write(stream);
		    out.close();
		} catch (IOException e) {
		}

	}
	
	public static InputStream replaceContents(String templateFilePath, String key, String value) {
		InputStream contents = null;
		try {
			contents = new FileInputStream(templateFilePath);
			String contentsStr = processTemplate(contents, key, value);
			contents.close();
			contents = new ByteArrayInputStream(contentsStr.getBytes("UTF-8"));
			copyFile(contents, new File(templateFilePath));
		} catch (IOException e) {
		}
		
		return contents;
	}
	public static InputStream MreplaceContent(String templateFilePath, String[] key, String[] value) {
		InputStream contents = null;
		try {
			contents = new FileInputStream(templateFilePath);
			String contentsStr = MprocessTemplate(contents, key, value);
			contents.close();
			contents = new ByteArrayInputStream(contentsStr.getBytes("UTF-8"));
			copyFile(contents, new File(templateFilePath));
		} catch (IOException e) {
		}
		
		return contents;
	}
	private static String MprocessTemplate(InputStream inputStream, String[] key, String[] value) {
		String contentsStr = "";

		try {
			int i = 0;
			contentsStr = getString(inputStream);
			while(i <key.length) {
			if(key[i]!=null && value[i]!=null){
				contentsStr = contentsStr.replaceAll(key[i], value[i]);
			}
			i++;
			}
		} catch (IOException ioe) {
		}
		return contentsStr;
	}


	private static String processTemplate(InputStream inputStream, String key, String value) {
		String contentsStr = "";
		try {
			contentsStr = getString(inputStream);
			contentsStr = contentsStr.replaceAll(key, value);
		} catch (IOException ioe) {
		}
		return contentsStr;
	}

	private static String getString(InputStream inputStream) throws IOException {
		StringBuilder sb = new StringBuilder();
		String line;
		if (inputStream != null) {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
				while ((line = reader.readLine()) != null) {
					sb.append(line).append("\n");
				}
			} finally {
				inputStream.close();
			}
			return sb.toString();
		} else {       
			return "";
		}
	}
	
	/**
	 * Computes relative path to a file 'targetFile' from a given location - 'currentDir'.<br/>
	 * <i>Both of these has to be on the same drive.</i><br/>
	 * Example:<br/>
	 * currentDir is "C:/exports/docs/proc1/e1";<br/>
	 * targetFile is "C:/exports/docs/index.html"<br/>
	 * then return value will be "../../index.html"
	 * 
	 * @param currentDir
	 * @param targetFile
	 * @return
	 */
	public static String computeRelativePath(File currentDir, File targetFile) {
		String currentLocation = currentDir.getAbsolutePath().replace('\\', '/');
		if (!currentLocation.endsWith("/")) {
			currentLocation += "/";
		}
		String targetFilePath = targetFile.getAbsolutePath().replace('\\', '/');
		String d = StringUtils.difference(currentLocation, targetFilePath);
		String commonPath = targetFilePath.substring(0, targetFilePath.lastIndexOf(d));
		if (commonPath.charAt(commonPath.length()-1) !='/') {
			//Below will fix the cases D:/docs/myDocs, D:/docs/myOutFolder => 'D:/docs/my' (incorrect)
			commonPath = commonPath.substring(0, commonPath.lastIndexOf('/') +1);
		}
		String relativePath = "";
		File t = new File(currentLocation);
		while (!new File(commonPath).equals(t)) {
			t = t.getParentFile();
			relativePath += "../";
		}
		relativePath += targetFilePath.substring(commonPath.length());
		relativePath = relativePath.replace("//", "/");
		if (relativePath.startsWith("/")) {
			relativePath = relativePath.substring(1, relativePath.length());
		}
		return relativePath;
	}
	
}
