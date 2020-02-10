package com.tibco.tea.agent.be.migration.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
 
/**
 * This utility to extract files and directories of a standard zip file.
 * @author ssinghal
 */

public class UnzipUtility {
   
    private static final int BUFFER_SIZE = 4096;
      
    public static void main(String[] args) {
        String zipFilePath = "somepath/fdcache.ear";
        String destDirectory = "somepath/unzip";
               
        UnzipUtility unzipper = new UnzipUtility();
        try {
            unzipper.unzip(zipFilePath, destDirectory, "somefilename.cdd");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
  
    public void unzip(String zipFilePath, String destDirectory, String newCddName) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        
        if(newCddName==null){
        	 while(entry!=null){
        		 extractFile(zipIn, destDirectory + File.separator + entry.getName());
        		 zipIn.closeEntry();
		         entry = zipIn.getNextEntry();
        	 }
        }else{
	        while(entry!=null){
	        	 if(entry.getName().endsWith(".sar")){
	        		 String filePath = destDirectory + File.separator + entry.getName();
	        		 extractFile(zipIn, filePath);
	        		 unzip(filePath, destDirectory, newCddName);
	        	 }else if(entry.getName().endsWith(".cdd")){
	        		 String filePath = destDirectory + File.separator + entry.getName();
	        		 extractFile(zipIn, filePath);
	        		 
	        		 String newCdd = destDirectory + File.separator + newCddName;
	        		 Path from = Paths.get(filePath);
	        		 Path to = Paths.get(newCdd);
	        		 //CopyOption[] options = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING}; 
	        		 Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
	        		 
	        		 break;
	        	 }
	        	 zipIn.closeEntry();
		         entry = zipIn.getNextEntry();
	        }
        }
        zipIn.close();
    }
   
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
    	OutputStream os = null;
    	BufferedOutputStream bos = null;
    	try{
	    	os = new FileOutputStream(filePath);
	        bos = new BufferedOutputStream(os);
	        byte[] bytesIn = new byte[BUFFER_SIZE];
	        int read = 0;
	        while ((read = zipIn.read(bytesIn)) != -1) {
	            bos.write(bytesIn, 0, read);
	        }
    	}catch(Exception e){
    		e.printStackTrace();
    		throw e;
    		
    	}
    	finally {
    		os.flush();
	        bos.flush();
	        bos.close();
	        os.close();
		}
    	
    }
}