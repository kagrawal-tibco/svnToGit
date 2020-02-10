/**
 * 
 */
package com.tibco.cep.studio.core.repo;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


/**
 * @author aathalye
 *
 */
public class ProjectUnpackager {
	
	public static void unzip(InputStream zippedContents,
			                 String unzipFolderName, 
			                 String unzipLocation) throws Exception {
		
		InputStream inStream = zippedContents;
		
		try {
			BufferedOutputStream destination = null;
			//Wrap the zip contents into ZipInputStream
			ZipInputStream zis = new ZipInputStream(inStream);
			ZipEntry entry;
			int SIZE = 1024;
			
			StringBuilder sBuilder = new StringBuilder(unzipLocation);
			sBuilder.append(File.separatorChar);
			sBuilder.append(unzipFolderName);
			
			String destinationLocation = sBuilder.toString();
			
			File unzipDir = new File(destinationLocation);
			if (!unzipDir.exists()) {
				unzipDir.mkdirs();
			}
			while ((entry = zis.getNextEntry()) != null) {
				int count;
				byte data[] = new byte[SIZE];
				// write the files to the disk
				File fileEntry = new File(destinationLocation + File.separatorChar
						+ entry.getName());
				if (!fileEntry.exists()) {
					// Get its parent
					File parent = fileEntry.getParentFile();
					if (!parent.exists()) {
						parent.mkdirs();
					}
				}
				FileOutputStream fos = new FileOutputStream(fileEntry);
				destination = new BufferedOutputStream(fos, SIZE);
				while ((count = zis.read(data, 0, SIZE)) != -1) {
					destination.write(data, 0, count);
				}
				destination.flush();
				destination.close();
			}
			zis.close();
			/*
			 * File remove = new File(zipLocation); if (remove.exists()) {
			 * //Delete it remove.delete(); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
