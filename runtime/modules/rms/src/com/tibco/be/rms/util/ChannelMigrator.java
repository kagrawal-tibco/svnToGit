package com.tibco.be.rms.util;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;


public class ChannelMigrator {

	private static Transformer TRANSFORMER;

	static {
		//load xslt
		ClassLoader classLoader = ChannelMigrator.class.getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("com/tibco/be/rms/util/ChannelMigration.xsl");
		if (inputStream != null) {
			StreamSource streamSource = new StreamSource(inputStream);
			try {
				Templates templates = TransformerFactory.newInstance().newTemplates(streamSource);
				TRANSFORMER = templates.newTransformer();
			} catch (TransformerConfigurationException e) {
				e.printStackTrace();
			} catch (TransformerFactoryConfigurationError e) {
				e.printStackTrace();
			}
		}
	}

    /**
     * Migrate each channel in the project to new format
     * @param locationFile
     * @param extension
     * @param deleteOrig
     * @throws Exception
     */
    public static void migrateChannels(File locationFile,
                                       String extension,
                                       boolean deleteOrig) throws Exception {
        if (locationFile.isDirectory()) {
            //List
            String[] children = locationFile.list(new FilenameFilter() {
                public boolean accept(File dir, String file) {
                    return !file.startsWith(".");
                }
            });

            for (String child : children) {
                //Recurse
                migrateChannels(new File(locationFile, child), extension, deleteOrig);
            }
        } else {
            //Process the file
            //rootDir can be a file also
            if (locationFile.getAbsolutePath().endsWith(extension)) {
                String name = locationFile.getName();   //Get Name
                File parentDir = locationFile.getParentFile();   //Get parent dir
                String newName = name.substring(0, name.lastIndexOf('.')) + "_modified" + "." + extension;

                File newFile = new File(parentDir, newName);
                FileOutputStream fos = new FileOutputStream(newFile);
                transform(locationFile, fos);

                boolean isTransformed = checkIfFileTransformed(locationFile, newFile);
                if (!isTransformed) {
                	newFile.delete();
                	return;
                }

                //Rename original to .backup
                String backupName = name.substring(0, name.lastIndexOf('.')) + "." + extension + ".orig";
                File backupFile = new File(parentDir, backupName);
                locationFile.renameTo(backupFile);
                locationFile = backupFile;

                //Rename modified to original
                File origFile = new File(parentDir, name);
                newFile.renameTo(origFile);
                if (deleteOrig) {
                    locationFile.delete();
                }
            }
        }
    }

    private static boolean checkIfFileTransformed(File locationFile, File newFile) {
    	// Better check will be to compare the file contents, or to compare CRC32/MD5 hash
    	if (locationFile.length() == newFile.length()) {
    		return false;
        }
		return true;
	}

	/**
     * Carry out the transformation
     * @param channelFile
     * @param newFileOutputStream
     * @throws Exception
     */
    private static void transform(File channelFile,
                                  FileOutputStream newFileOutputStream) throws Exception {

        if (TRANSFORMER != null) {
	        //Create source file source
	        StreamSource xmlSource = new StreamSource(channelFile);
	        StreamResult outputResult = new StreamResult(newFileOutputStream);
	        TRANSFORMER.transform(xmlSource, outputResult);
        	//Close outputstream
	        newFileOutputStream.close();
        }
    }
}


