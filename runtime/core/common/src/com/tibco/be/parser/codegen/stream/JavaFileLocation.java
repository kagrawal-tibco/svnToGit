package com.tibco.be.parser.codegen.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardLocation;


public class JavaFileLocation extends AbstractResource {
	private String packageName;
	private JavaFileObject fileObject;
	
	public JavaFileLocation(JavaFileObject file, JavaFolderLocation parent,
			StreamFileManager fileManager) {
		super(file.getName(),parent,fileManager);
		this.fileObject = file;
		if(file instanceof JavaFileObjectImpl) {
			this.packageName = ((JavaFileObjectImpl)file).getPackageName();
		}
	}
	

	protected JavaFileLocation(String name, JavaFolderLocation parent,StreamFileManager streamFileManager) {
		super(name,parent,streamFileManager);
	}

	
	public JavaFileLocation(String fileName,String packageName,JavaFolderLocation parent, StreamFileManager streamFileManager) {
		super(fileName,parent,streamFileManager);
		this.packageName = packageName;
		this.parent = parent;
		final String qualifiedName ;
		if(packageName != null && !packageName.isEmpty()) {
			qualifiedName = packageName+"."+fileName;
		} else {
			qualifiedName = fileName;
		}
		if(qualifiedName.endsWith(AbstractStreamGenerator.JAVA_EXTENSION)) {
			this.fileObject = new JavaFileObjectImpl(packageName,fileName, streamFileManager.getStreamGenerator());
		} else if (qualifiedName.endsWith(AbstractStreamGenerator.CLASS_EXTENSION)){
			JavaFileObjectImpl fObj = new JavaFileObjectImpl(packageName,fileName,Kind.CLASS);
			fObj.setLibClass(true);
			this.fileObject = fObj;
		} else {
			this.fileObject = new JavaFileObjectImpl(packageName,fileName,Kind.OTHER);
		}
		getFileManager().putFileForInput(StandardLocation.SOURCE_PATH, packageName, fileName, fileObject);
	}


	public String getPackageName() {
		return packageName;
	}
	
	@Override
	public boolean isFolder() {
		return false;
	}
	
	/**
	 * @return
	 * @throws IOException 
	 */
	public InputStream openInputStream() throws IOException {
		return fileObject.openInputStream();
	}
	
	/**
	 * @return
	 * @throws IOException 
	 */
	public OutputStream openOutputStream() throws IOException {
		return fileObject.openOutputStream();
	}
	
	/**
	 * @return
	 * @throws IOException
	 */
	public Reader openReader() throws IOException {
		return fileObject.openReader(false);
	}
	
	public Reader openReader(boolean ignoreEncodingErrors) throws IOException {
		return fileObject.openReader(ignoreEncodingErrors);
	}
	
	/**
	 * @return
	 * @throws IOException
	 */
	public Writer openWriter() throws IOException {
		return fileObject.openWriter();
	}
	
	public JavaFileObject getFileObject() {
		return fileObject;
	}

}