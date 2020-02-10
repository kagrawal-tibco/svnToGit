/**
 * 
 */
package com.tibco.cep.bpmn.codegen;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.ToolProvider;

import com.tibco.be.parser.codegen.stream.AbstractStreamGenerator;
import com.tibco.be.parser.codegen.stream.JavaFileLocation;
import com.tibco.be.parser.codegen.stream.JavaFolderLocation;
import com.tibco.be.parser.codegen.stream.RootFolderLocation;
import com.tibco.be.parser.codegen.stream.StreamCompilerException;
import com.tibco.be.parser.codegen.stream.StreamFileManager;
import com.tibco.be.util.OversizeStringConstants;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author pdhar
 *
 */
public class ProcessCompiler extends AbstractStreamGenerator {

	private RootFolderLocation srcDir;
    static Logger logger = LogManagerFactory.getLogManager().getLogger(ProcessCompiler.class);

	/**
	 * @param classpath
	 * @param cl
	 * @param debug
	 * @param sourceJavaVersion
	 * @param targetJavaVersion
	 * @param encoding
	 * @throws Exception 
	 */
	public ProcessCompiler(String classpath, ClassLoader cl, boolean debug,
			String sourceJavaVersion, String targetJavaVersion, String encoding,boolean isAnnotationProcessingOnly,String[] annotationProcessors) throws Exception {
		super(classpath, cl, debug, sourceJavaVersion, targetJavaVersion,
				encoding,isAnnotationProcessingOnly,annotationProcessors);
		init();
		this.srcDir = getFileManager().getRootFolder("src");
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.tibco.be.parser.codegen.stream.AbstractStreamGenerator#debug(java.lang.String, java.lang.Object[])
	 */
	@Override
	public void debug(String msg, Object... args) {
		logger.log(Level.DEBUG, String.format(msg, args));

	}

	/* (non-Javadoc)
	 * @see com.tibco.be.parser.codegen.stream.AbstractStreamGenerator#log(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void log(String msg, Throwable e) {
        logger.log(Level.ERROR, e, msg);

	}

	public void addProcess(String folder, String packageName, String className, ByteArrayOutputStream codeStream) throws IOException {
		final JavaFolderLocation processDir = srcDir.addFolder(folder);
		JavaFileLocation outFile = processDir.addFile(packageName,className + ".java");
		writeStream(outFile.openOutputStream(),codeStream);
		
	}
	

    
    public void writeStream(OutputStream output,ByteArrayOutputStream codeStream) throws IOException {
    	BufferedOutputStream bos = new BufferedOutputStream(output);
    	ByteArrayInputStream bais = new ByteArrayInputStream(codeStream.toByteArray());
    	String s = new String(codeStream.toByteArray());
    	int nRead;
    	byte[] data = new byte[16384];

    	while ((nRead = bais.read(data, 0, data.length)) != -1) {
    	  bos.write(data, 0, nRead);
    	}
    	

    	bos.flush();
    	bos.close();
    	bais.close();

    }
    
    public InputStream compile(Map<String, byte[]> nameToByteCode) throws StreamCompilerException, IOException, URISyntaxException {
    	// load the be.jar entries
    	 // remove this so that analyzeByteCodes doesn't choke on it
    	nameToByteCode.remove(OversizeStringConstants.PROPERTY_FILE_NAME);
    	for(Map.Entry<String, byte[]> entry:nameToByteCode.entrySet()) {
    		// System.out.println(entry.getKey());
    		String qualifiedClassName = entry.getKey();
    		String packageName = "";
    		String className = qualifiedClassName;
    		if (qualifiedClassName.lastIndexOf(".") != -1) {
    			packageName = qualifiedClassName.substring(0,qualifiedClassName.lastIndexOf("."));
    			className = qualifiedClassName.substring(packageName.length() + 1);
    		}
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
    	compile(getCompilationClassLoader(), getClassPath(), getFileManager(), isDebug());
    	return getFileManager().getJarInputStream();
    }
    
    
    
	/**
	 * @param clazzloader
	 * @param classPath
	 * @param fileManager
	 * @param debug
	 *
	 * @return
	 * @throws StreamCompilerException
	 */
	public void compile(ClassLoader clazzloader, String classPath, StreamFileManager fileManager, boolean debug ) throws StreamCompilerException {
			    	
    	JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

    	DiagnosticCollector<JavaFileObject> diagnosticsCollector = 	new DiagnosticCollector<JavaFileObject>();
    	
    	List<JavaFileObject> fileObjects = fileManager.getFileObjects(new Kind[]{Kind.SOURCE});
    	
    	List<String> argStrings = new ArrayList<String>();
    	if(debug) {
    		argStrings.add("-g");
    	}
		argStrings.add("-source");
		argStrings.add(sourceJavaVersion);
		argStrings.add("-target");
		argStrings.add(targetJavaVersion);
		argStrings.add("-encoding");
		argStrings.add(encoding);
        argStrings.add("-classpath");
        argStrings.add(System.getProperty("java.class.path"));

		CompilationTask task = compiler.getTask(null, fileManager, diagnosticsCollector, argStrings, null, fileObjects); // Line 6
		
		if (!task.call()) {
            throw new StreamCompilerException("Compilation fails.",fileManager, diagnosticsCollector);
        }

			    	
	}


}
