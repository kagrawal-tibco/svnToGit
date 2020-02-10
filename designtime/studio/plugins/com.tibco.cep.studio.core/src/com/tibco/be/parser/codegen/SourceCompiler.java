package com.tibco.be.parser.codegen;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.jar.JarInputStream;

import com.tibco.be.parser.codegen.stream.AbstractStreamGenerator;
import com.tibco.be.parser.codegen.stream.JavaFileLocation;
import com.tibco.be.parser.codegen.stream.JavaFolderLocation;
import com.tibco.be.parser.codegen.stream.RootFolderLocation;
import com.tibco.be.util.OversizeStringConstants;
import com.tibco.cep.studio.core.StudioCorePlugin;

public class SourceCompiler extends AbstractStreamGenerator {

	
	private RootFolderLocation rootDir;
	private File targetDir;
	private JavaFolderLocation srcDir;

	/**
	 * @param classpath
	 * @param clazzLoader
	 * @param targetDir
	 * @param debug
	 * @param sourceJavaVersion
	 * @param targetJavaVersion
	 * @param encoding
	 * @param isAnnotationProcessingOnly
	 * @param annotationProcessors
	 */
	public SourceCompiler(String classpath, 
			ClassLoader clazzLoader,
			File targetDir, 
			boolean debug, 
			String sourceJavaVersion, 
			String targetJavaVersion, 
			String encoding,
			boolean isAnnotationProcessingOnly,
			String[] annotationProcessors) {
		super(classpath, 
				clazzLoader, 
				debug, 
				sourceJavaVersion, 
				targetJavaVersion, 
				encoding,
				isAnnotationProcessingOnly,
				annotationProcessors);
		this.targetDir = targetDir;
	}

	public RootFolderLocation getRootDir() {
		return rootDir;
	}
	
	public JavaFolderLocation getSrcDir(String name) {
		return rootDir.addFolder(name);
	}

	@Override
	public void init() throws Exception {
		super.init();
		this.rootDir = getFileManager().getRootFolder("Root");
		
	}

	public void compile(Map<String, byte[]> nameToByteCode) throws Exception {
		
		
		
		nameToByteCode.remove(OversizeStringConstants.PROPERTY_FILE_NAME);
    	for(Map.Entry<String, byte[]> entry:nameToByteCode.entrySet()) {
//    		System.out.println(entry.getKey());
    		String qualifiedClassName = entry.getKey();
    		String packageName = (qualifiedClassName.lastIndexOf(".") != -1) ? qualifiedClassName.substring(0,qualifiedClassName.lastIndexOf(".")) : qualifiedClassName;
    		String className = (qualifiedClassName.length() > packageName.length()) ? qualifiedClassName.substring(packageName.length()+1) : qualifiedClassName;
    		JavaFileLocation file =  new JavaFileLocation(className+".class",packageName,srcDir,getFileManager());
//    		JavaFileLocation file =  new JavaFileLocation(className,packageName,srcDir,getFileManager());
    		OutputStream os = file.openOutputStream();
    		ByteArrayInputStream bais = new ByteArrayInputStream((byte[])entry.getValue());
    		int nRead;
        	byte[] data = new byte[16384];

        	while ((nRead = bais.read(data, 0, data.length)) != -1) {
        	  os.write(data, 0, nRead);
        	}
        	os.flush();
        	os.close();
        	bais.close();
    		
    	}

		InputStream is =  compileInMemory(
				getCompilationClassLoader(),
				getClassPath(), 
				getFileManager(),
				isDebug());
		
		
		
		
		JarInputStream jis = new JarInputStream(is);
		JavacUtil.writeJarOutput(targetDir, jis);

	}
	
	

	@Override
	public void debug(String msg, Object... args) {
		StudioCorePlugin.debug(msg,args);

	}

	@Override
	public void log(String msg, Throwable e) {
		StudioCorePlugin.log(msg,e);

	}

	public void addFile(JavaFileWriter dtJavaFile) {
		String packages[] = dtJavaFile.getPackage().split("\\.");

		JavaFolderLocation parent = srcDir;
		//        for(String s: packages) {
		//        	parent = parent.addFolder(s);
		//        }
		dtJavaFile.setTargetFolder(parent);
	}


	
}
