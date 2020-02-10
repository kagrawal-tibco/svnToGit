package com.tibco.be.parser.codegen.stream;

import java.net.URI;
import java.net.URISyntaxException;


public abstract class AbstractResource {
	
	public static final String PATH_SEPARATOR = "/";//$NON-NLS-1$
	public static final String PACKAGE_SEPARATOR = ".";//$NON-NLS-1$
	
	protected String name;
	protected JavaFolderLocation parent;
	protected StreamFileManager fileManager;
	
	public AbstractResource(String name, JavaFolderLocation parent,
			StreamFileManager fileManager) {
		super();
		this.name = name;
		this.parent = parent;
		this.fileManager = fileManager;
	}

	/**
	 * @return the fileManager
	 */
	public StreamFileManager getFileManager() {
		return fileManager;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the parent
	 */
	public JavaFolderLocation getParent() {
		return parent;
	}

	public String getPath() {
		if(parent != null) { 
			return parent.getPath()+PATH_SEPARATOR+getName();
		} else {
			return getName();
		}
	}

	public abstract boolean isFolder();
	
	
	@Override
	public String toString() {
		return getPath();
	}
	
	
	URI toURI() throws URISyntaxException {
		URI uri = null;
		if(getParent() != null) {
			uri = new URI(getParent().toURI().toString()+"/"+getName());
		} else {
			uri = new URI("");
		}
		return uri;
	}
	
	
}