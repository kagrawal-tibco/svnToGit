/**
 * 
 */
package com.tibco.cep.studio.common.legacy;

import java.io.File;
import java.io.FilenameFilter;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * @author aathalye
 *
 */
public class OldTableMigrator {
	
		
	/**
	 * Migrate each table in the project to new format
	 * @param rootDir
	 * @param styleSheet
	 * @param deleteOrig
	 * @throws Exception
	 */
	public static void migrateTables(File rootDir, 
			                         File styleSheet,
			                         String extension,
			                         boolean deleteOrig) throws Exception {
		if (rootDir.isDirectory()) {		
			//List 
			String[] children = rootDir.list(new FilenameFilter() {
				public boolean accept(File dir, String file) {
					return !file.startsWith(".");
				}
			});
			
			for (String child : children) {
				//Recurse
				migrateTables(new File(rootDir, child), styleSheet, extension, deleteOrig);
			}
		} else {
			//Process the file
			//rootDir can be a file also
			if (rootDir.getAbsolutePath().endsWith(extension)) {
				//Get Name
				String name = rootDir.getName();
				//Get parent dir
				File parentFile = rootDir.getParentFile();
				
				String newName = 
					name.substring(0, name.lastIndexOf('.')) + "_modified" + "." + extension;
				
				File newFile = new File(parentFile, newName);
				transform(rootDir, styleSheet, newFile);
				
				//Rename original to .backup
				
				String backupName = 
					name.substring(0, name.lastIndexOf('.')) + "." + extension + ".orig";
				
				File backupFile = new File(parentFile, backupName);
				
				rootDir.renameTo(backupFile);
				rootDir = backupFile;
				
				//Rename modified to original
				File origFile = new File(parentFile, name);
				newFile.renameTo(origFile);
								
				if (deleteOrig) {
					System.out.println("Deleting " + rootDir.getAbsolutePath());
					rootDir.delete();
				}
			}
		}
	}
	
	/**
	 * Carry out the transformation
	 * @param ruleFunctionImplFile
	 * @param styleSheet
	 * @param newFileName
	 * @throws Exception
	 */
	public static void transform(File ruleFunctionImplFile, 
			                     File styleSheet, 
			                     File newFileName) throws Exception {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		
		StreamSource styleSource = new StreamSource(styleSheet);
		Templates templates = transformerFactory.newTemplates(styleSource);
		
		Transformer transformer = templates.newTransformer();
		
				
		//Create source file source
		StreamSource xmlSource = new StreamSource(ruleFunctionImplFile);
		StreamResult outputResult = new StreamResult(newFileName);
		System.out.println("Transforming " + ruleFunctionImplFile.getAbsolutePath() + " to " + newFileName.getAbsolutePath());
		transformer.transform(xmlSource, outputResult);
	}
}
