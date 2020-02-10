package com.tibco.cep.sharedresource.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.transform.TransformerException;

import com.tibco.cep.sharedresource.model.SharedResourceTransformerFactory;

/*
@author ssailapp
@date Oct 20, 2009 12:06:19 PM
 */

public class SchemaEditorUtil {

	private static final String BACKUP_FILE_EXTENSION = ".studiobak";
	
	public static String transformInput(String resourceName, String transformer) {
		try {
			if (resourceName == null || transformer == null)
				return null;
			String transformedResourceName = backupFile(resourceName);
			if (new File(resourceName).length() == 0)
				return transformedResourceName;
			SharedResourceTransformerFactory transFactory = new SharedResourceTransformerFactory();
			transFactory.transform(resourceName, transformer, new FileOutputStream(transformedResourceName));
			return transformedResourceName;
		} catch (TransformerException te) {
			te.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}
	
	public static boolean reverseTransformInput(String resourceName, String transformedResourceName, String transformer) {
		try {
			if (transformedResourceName==null || transformer==null)
				return true;
			SharedResourceTransformerFactory transFactory = new SharedResourceTransformerFactory();
			if (transformedResourceName != null) {
				transFactory.transform(transformedResourceName, transformer, new FileOutputStream(resourceName));
			}
		} catch (TransformerException te) {
			te.printStackTrace();
			return false;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
		return true;
	}	
	
	public static void deleteTempFile(String filename) {
		// Delete temp file
		File file = new File(filename);
		if (file.exists())
			file.delete();
	}
	
	public static String getTempFileName(String filename) {
		return (filename + BACKUP_FILE_EXTENSION);
	}
	
	public static String backupFile(String inFileName) throws IOException {
		File inFile = new File(inFileName);
		File outFile = new File(getTempFileName(inFileName));
		FileInputStream fis  = new FileInputStream(inFile);
		FileOutputStream fos = new FileOutputStream(outFile);
		try {
			byte[] buf = new byte[1024];
			int i = 0;
			while ((i = fis.read(buf)) != -1) {
				fos.write(buf, 0, i);
			}
		} 
		catch (IOException e) {
			throw e;
		}
		finally {
			if (fis != null) fis.close();
			if (fos != null) fos.close();
		}
		return (outFile.getAbsolutePath());
	}
	
	public static String getDisplayName(String editorName, String resourceType, String resourceExtension) {
		String displayName = resourceType + ": ";
		if (editorName != null)
			displayName += editorName.replace(resourceExtension, "");
		return (displayName);
	}
}