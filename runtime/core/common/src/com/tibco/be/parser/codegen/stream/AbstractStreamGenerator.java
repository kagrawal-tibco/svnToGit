package com.tibco.be.parser.codegen.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;



public abstract class AbstractStreamGenerator {
	
	public static final String JAVA_EXTENSION = ".java";

	public static final String CLASS_EXTENSION = ".class";
	
	/**
	 * COnverts a String to a URI.
	 * 
	 * @param name
	 *            a file name
	 * @return a URI
	 */
	protected static URI toURI(String name) {
		try {
			return new URI(name);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected StreamFileManager fileManager;

	protected ClassLoader compilationClassLoader;

	protected String classPath;

	protected boolean debug;

	protected String encoding;

	protected String targetJavaVersion;
	

	protected String sourceJavaVersion;
	
	protected String[] annotationProcessors;

	private boolean isAnnotationProcessingOnly;
	
	public AbstractStreamGenerator(String classpath,
						ClassLoader cl,
						boolean debug,
						String sourceJavaVersion,
						String targetJavaVersion,
						String encoding,
						boolean isAnnotationProcessingOnly,
						String[] annotationProcessors
						) {
		this.classPath = classpath;
		this.compilationClassLoader = cl;
		this.debug = debug;
		this.sourceJavaVersion = sourceJavaVersion;
		this.targetJavaVersion = targetJavaVersion;
		this.encoding = encoding;
		this.isAnnotationProcessingOnly=isAnnotationProcessingOnly;
		this.annotationProcessors=annotationProcessors;
	}
	
	/**
	 * @param clazzloader
	 * @param classPath
	 * @param fileManager
	 * @param debug
	 * @param sourceJavaVersion
	 * @param targetJavaVersion
	 * @param encoding
	 * @return
	 * @throws CompilationFailedException
	 */
	public InputStream compileInMemory(ClassLoader clazzloader, 
			String classPath,
			StreamFileManager fileManager, 
			boolean debug ) throws StreamCompilerException {
			    	
			    	JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			    	
			    	
			    	
			    	DiagnosticCollector<JavaFileObject> diagnosticsCollector =
			    		new DiagnosticCollector<JavaFileObject>();
			    	
			//    	StandardJavaFileManager fileManager = compiler.getStandardFileManager(
			//				diagnosticCollector, Locale.getDefault(), Charset.forName(CodeGenConstants.charset));
			    	
			    	List<JavaFileObject> fileObjects = fileManager
							.getFileObjects(new Kind[]{Kind.SOURCE});
			    	
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
					if(isAnnotationProcessingOnly){
						argStrings.add("-proc:only");
					} else {
						argStrings.add("-proc:none");
					}
					if(annotationProcessors != null && annotationProcessors.length >0) {
						argStrings.add("-processor");
						StringBuilder sb = new StringBuilder();
						for(int i=0; i< annotationProcessors.length;i++){
							if(i>0){
								sb.append(",");
							}
							sb.append(annotationProcessors[i]);
							
						}
						argStrings.add(sb.toString());
					}
					if(classPath != null && !classPath.isEmpty()) {
						argStrings.add("-classpath");
						argStrings.add(classPath);
					}
			    	/**
			    	 * Parameters:
			    	 * out a Writer for additional output from the compiler; use System.err if null
			    	 * fileManager a file manager; if null use the compiler's standard filemanager
			    	 * diagnosticListener a diagnostic listener; if null use the compiler's default method for reporting diagnostics
			    	 * options compiler options, null means no options
			    	 * classes class names (for annotation processing), null means no class names
			    	 * compilationUnits the compilation units to compile, null means no compilation units
			    	 * Returns:
			    	 * an object representing the compilation
			    	 * Throws:
			    	 * RuntimeException - if an unrecoverable error occurred in a user supplied component. The cause will be the error in user code.
			    	 * IllegalArgumentException - if any of the given compilation units are of other kind than source
			    	 */
					CompilationTask task = compiler.getTask(null, fileManager,
							diagnosticsCollector, argStrings, null, fileObjects); // Line 6
					
					Boolean result = task.call(); // Line 7
					
					if (result == true) {
						log("Compilation has succeeded",null);
					} else {
						throw new StreamCompilerException("Compilation fails.",fileManager, diagnosticsCollector);
					}
					try {
				         // For each class name in the inpput map, get its compiled
				         // class and put it in the output map
				         Map<String, Class<?>> compiled = new HashMap<String, Class<?>>();
				         List<Map.Entry<URI, JavaFileObject>> fileMapItems = fileManager.getFileEntries(new Kind[]{Kind.CLASS});
				         for (Entry<URI, JavaFileObject> entry : fileMapItems) {
				        	 final String[] parts = entry.getKey().toString().split("/");
				        	 final String kind = parts[0];
				        	 final String packageName = parts[1];
				        	 final String className = parts[2];
				        	 String qualifiedClassName = (packageName == null || packageName.isEmpty())? className :packageName+"."+className;
				        	
				 			if(qualifiedClassName.endsWith(".class")) {
				 				qualifiedClassName = qualifiedClassName.substring(0, qualifiedClassName.length() - ".class".length());
				 			}
				            final Class<?> newClass = fileManager.loadClass(qualifiedClassName);
				            compiled.put(qualifiedClassName, newClass);
			//	            URL is = fileManager.getClassLoader().getResource("be/gen/OversizeStringConstants.java");
			//	            if(is != null) {
			//	            	try {
			//						InputStream ss = is.openStream();
			//						if(ss != null) {}
			//					} catch (IOException e) {
			//						// TODO Auto-generated catch block
			//						e.printStackTrace();
			//					}
			//	            }
				         }
				      } catch (ClassNotFoundException e) {
				         throw new StreamCompilerException(fileManager, e, diagnosticsCollector);
				      } catch (IllegalArgumentException e) {
				    	  throw new StreamCompilerException(fileManager, e, diagnosticsCollector);
				      } catch (SecurityException e) {
				    	  throw new StreamCompilerException(fileManager, e, diagnosticsCollector);
				      }
				    try {
						return fileManager.getJarInputStream();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						throw new StreamCompilerException(fileManager, e, diagnosticsCollector);
					}
			    	
			    }


	public abstract void debug(String msg,Object...args);

	

	/**
	 * @return the classPath
	 */
	public String getClassPath() {
		return classPath;
	}
	
	/**
	 * @return the compilationClassLoader
	 */
	public ClassLoader getCompilationClassLoader() {
		return compilationClassLoader;
	}
	
	public String getEncoding() {
		return encoding;
	}
	
	/**
	 * @return the fileManager
	 */
	public StreamFileManager getFileManager() {
		return fileManager;
	}

	/**
	 * @param ignoreEncodingErrors
	 * @return
	 * @throws IOException
	 */
	public Map<URI, Reader> getFileURIReaderMap(boolean ignoreEncodingErrors) throws IOException {
		List<Map.Entry<URI, JavaFileObject>> fMap = fileManager.getFileEntries(Kind.values());
		Map<URI, Reader> outputMap = new HashMap<URI, Reader>();
		for(Entry<URI, JavaFileObject> entry:fMap){
			outputMap.put(entry.getKey(), entry.getValue().openReader(ignoreEncodingErrors));
		}
		return outputMap;
	}

	public Map<URI, InputStream> getFileURIStreamMap() throws IOException {
		List<Map.Entry<URI, JavaFileObject>> fMap = fileManager.getFileEntries(Kind.values());
		Map<URI, InputStream> outputMap = new HashMap<URI, InputStream>();
		for(Entry<URI, JavaFileObject> entry:fMap){
			outputMap.put(entry.getKey(), entry.getValue().openInputStream());
		}
		return outputMap;
	}

	public String getSourceJavaVersion() {
		return sourceJavaVersion;
	}

	public String getTargetJavaVersion() {
		return targetJavaVersion;
	}

	public void init() throws Exception {
//		StreamDiagnosticListener streamdiagnosticlistener = new StreamDiagnosticListener();
		
//		StandardJavaFileManager stdFileManager = ToolProvider
//		.getSystemJavaCompiler().getStandardFileManager(
//				streamdiagnosticlistener, Locale.getDefault(),
//				Charset.forName(CodeGenConstants.charset));
		StandardJavaFileManager stdFileManager = ToolProvider
				.getSystemJavaCompiler().getStandardFileManager(
						null, null,
						null);
		
		setFileManager(new StreamFileManager(stdFileManager,getCompilationClassLoader(), this));

	}
	
	
	public InputStream compile() throws Exception {
		return compileInMemory(
				getCompilationClassLoader(),
				getClassPath(), 
				getFileManager(),
				isDebug());
	}

	/**
	 * @return
	 */
	public boolean isDebug() {
		return debug;
	}

	public abstract void log(String msg,Throwable e);
	
	/**
	 * @param compilationClassLoader the compilationClassLoader to set
	 */
	public void setCompilationClassLoader(ClassLoader compilationClassLoader) {
		this.compilationClassLoader = compilationClassLoader;
	}
	
	/**
	 * @param fileManager the fileManager to set
	 */
	public void setFileManager(StreamFileManager fileManager) {
		this.fileManager = fileManager;
	}

}
