package com.tibco.be.maven.plugin.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class BEMavenUtil {
	
	public static List<String> lookInDirectory(String name, File dir,List<String> jarsList)throws ClassNotFoundException, FileNotFoundException{
        File[] files = dir.listFiles();
        File file;
        String fileName;
        String extension = ".jar";
        final int size = files.length;
        for (int i = 0; i < size; i++) {
            file = files[i];
            fileName = file.getName();
            if (file.isFile() && fileName.toLowerCase().endsWith(extension)) {  
            	if(!fileName.equals("jackson.jar"))
            		jarsList.add(name.replace('.', File.separatorChar) + fileName);
            }
            if (file.isDirectory()) {
            	jarsList = lookInDirectory(name + fileName + ".", file,jarsList);
            }
        }
		return jarsList;
    }
	public static final boolean readYesNo(String prompt) {
		String input = readLine(prompt).toLowerCase().trim();
		if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) {
			return true;
		} 
		if (input.equalsIgnoreCase("no") || input.equalsIgnoreCase("n")) {
			return false;
		} 
		if(!input.toLowerCase().startsWith("y")&& !input.toLowerCase().startsWith("n")){
			System.out.println("\n");
			return readYesNo(prompt);
		}
		return false;
	}
	private static String readLine(String prompt) {
		System.out.print(prompt);
		StringBuilder sb = new StringBuilder();
		while (true) {
			try {
				char c = (char) System.in.read();
				sb.append(c);
				if (c == '\n') {
					return sb.toString().trim();
				} else if (c == '\r') {
				} 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static boolean isArgumentNull(String arg){
	   if(arg=="" || arg==null)
		   return true;
	   else
	       return false;
	}

}
