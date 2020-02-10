package com.tibco.be.parser.codegen.stream;

import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaFileObject;

public class JavaFolderLocation extends AbstractResource {
	
	
	
	List<AbstractResource> filesAndFolders = new ArrayList<AbstractResource>();
	
	public JavaFolderLocation(String name,JavaFolderLocation parent, StreamFileManager streamFileManager) {
		super(name,parent,streamFileManager);
	}


	public JavaFileLocation addFile(JavaFileObject fileObj) {
		JavaFileLocation file = new JavaFileLocation(fileObj,this,getFileManager());
		filesAndFolders.add(file);
		return file;
	}
	
	public JavaFileLocation addFile(String generatedPackagePrefix, String javaFileName) {
		JavaFileLocation file =  new JavaFileLocation(javaFileName,generatedPackagePrefix,this,getFileManager());
		filesAndFolders.add(file);
		return file;
		
	}


	public JavaFolderLocation addFolder(String name) {
		JavaFolderLocation folder = new JavaFolderLocation(name,this,getFileManager());
		filesAndFolders.add(folder);
		return folder;
	}
	
	public AbstractResource [] files() {
		return filesAndFolders.toArray(new AbstractResource[filesAndFolders.size()]);
	}
	
	
	public boolean hasChildren() {
		return filesAndFolders.size() > 0;
	}
	
	@Override
	public boolean isFolder() {
		return true;
	}
	
}