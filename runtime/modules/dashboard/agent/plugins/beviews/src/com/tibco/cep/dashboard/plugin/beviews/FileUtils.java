package com.tibco.cep.dashboard.plugin.beviews;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

class FileUtils {

	static File createTempFolder(String folderName) throws IOException {
		File temp = new File(System.getProperty("java.io.tmpdir"));
		File tempDir = new File(temp,folderName);
		if (tempDir.exists() == false){
			if (tempDir.mkdir() == true) {
				return tempDir;
			}
			throw new IOException("could not create "+folderName+" under "+temp.getAbsolutePath());
		}
		return tempDir;
	}

	static void copy(File source, File dest) throws IOException {
		boolean copy = false;
		if (dest.exists() == false) {
			boolean created = dest.createNewFile();
			if (created == false) {
				throw new IOException("could not create "+dest.getAbsolutePath());
			}
			copy = true;
		}
		else {
			copy = source.lastModified() > dest.lastModified();
		}
		if (copy == true) {
			FileChannel sourceChannel = null;
			FileChannel destChannel = null;
			try {
				sourceChannel = new FileInputStream(source).getChannel();
				destChannel = new FileOutputStream(dest).getChannel();
				long sourceSize = sourceChannel.size();
				long destSize = destChannel.transferFrom(sourceChannel, 0, sourceSize);
				if (sourceSize != destSize){
					throw new IOException(dest.getAbsolutePath()+" could not be copied from "+source.getAbsolutePath());
				}

			} finally {
				if (destChannel != null) {
					destChannel.close();
				}
				if (sourceChannel != null) {
					sourceChannel.close();
				}
			}
		}
	}

}
