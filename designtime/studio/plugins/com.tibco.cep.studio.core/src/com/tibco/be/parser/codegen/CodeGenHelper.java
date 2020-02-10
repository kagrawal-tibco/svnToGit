package com.tibco.be.parser.codegen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.tibco.be.parser.CompileErrors;
import com.tibco.be.parser.codegen.stream.JavaFileLocation;
import com.tibco.be.parser.codegen.stream.JavaFolderLocation;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.modules.CEPModule;
import com.tibco.cep.modules.ModuleRegistry;
import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.core.StudioCorePlugin;

public class CodeGenHelper {
	
	
	public static void flattenRuleFnUsages(Map usages) {
		HashSet alreadyFlattened = new HashSet();
		for (Iterator it = usages.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			flattenUsageSet((String) entry.getKey(), (Set) entry.getValue(),
					usages, alreadyFlattened);
		}
	}

	private static void flattenUsageSet(String fnClass, Set fnUsages,
			Map usageMap, Set alreadyFlattened) {
		// ignore already flattened functions
		if (alreadyFlattened.contains(fnClass))
			return;
		// flatten each usage set of each function that is used by function
		// being considered
		for (Iterator it = new ArrayList(fnUsages).iterator(); it.hasNext();) {
			String usedFnClass = (String) it.next();
			Set usedFnUsages = (Set) usageMap.get(usedFnClass);
			// prevent an infinite loop if two functions use each other
			// or a function uses itself
			if (!usedFnClass.equals(fnClass) && !usedFnUsages.contains(fnClass)) {
				flattenUsageSet(usedFnClass, usedFnUsages, usageMap,
						alreadyFlattened);
			}
			// add the usage set of each used function to this function's set
			// (usedFnUsages will be modified by the above step)
			fnUsages.addAll(usedFnUsages);
		}
		alreadyFlattened.add(fnClass);
	}


	public static Writer makeFileWriter(File file)
			throws FileNotFoundException, UnsupportedEncodingException {
		FileOutputStream fos = new FileOutputStream(file, false);
		OutputStreamWriter osw = new OutputStreamWriter(fos, CodeGenConstants.charset);
		return new BufferedWriter(osw);
		// return new BufferedWriter(new OutputStreamWriter(new
		// FileOutputStream(file, false), charset));
	}

	private static Reader makeFileReader(File file)
			throws FileNotFoundException, UnsupportedEncodingException {
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis, CodeGenConstants.charset);
		return new BufferedReader(isr);
		// return new BufferedReader(new InputStreamReader(new
		// FileInputStream(file), charset));
	}

	public static SmapFileWriter setupSmapFileWriter(String javaFileName,
			String javaPackageName, File targetDir, char separator)
			throws FileNotFoundException, IOException {
		SmapFileWriter writer;
		writer = new SmapFileWriter(javaFileName, javaPackageName, separator,
				targetDir);
		return writer;
	}
	
	public static SmapFileWriter setupSmapStreamWriter(String javaFileName,
			String javaPackageName, JavaFolderLocation targetDirLocation,
			char separator) {
		SmapFileWriter writer;
		writer = new SmapFileWriter(javaFileName, javaPackageName, separator,
				targetDirLocation);
		return writer;
	}
	
	public static JavaFileWriter setupJavaStreamWriter(String javaFileName,
			String javaPackageName, JavaFolderLocation targetDirLocation,
			char separator) {
		JavaFileWriter writer;
        // Setup the JavaFileWriter
//        String outFilePath = javaPackageName.replace(separator, File.separatorChar);
//        JavaFileLocation javaFile = targetDir.addFile(javaPackageName, javaFileName+ CodeGenConstants.JAVA_FILE_EXTENSION);
        writer = new JavaFileWriter(javaFileName,javaPackageName, targetDirLocation, separator);
        return writer;
	}
	
	public static JavaFileWriter setupJavaFileWriter(String javaFileName, 
			String javaPackageName, File targetDir, char separator)
					throws FileNotFoundException, IOException {
		JavaFileWriter writer;
		// Setup the JavaFileWriter
		String outFilePath = javaPackageName.replace(separator, File.separatorChar);
		File outFileDir = new File(targetDir, outFilePath);
//        File outFile = new File(outFileDir, javaFileName + CodeGenConstants.JAVA_FILE_EXTENSION);
		if(!outFileDir.exists()) {
			outFileDir.mkdirs();
		}
//        Writer fwriter = makeFileWriter(outFile);
		writer = new JavaFileWriter(javaFileName,javaPackageName, targetDir, separator);
		return writer;
	}
	
	public static JavaSourceWriter setupJavaSourceWriter(String javaFileName,
			String javaPackageName, JavaFolderLocation targetDirLocation,String entityPath,
			char separator) {
        JavaSourceWriter writer = new JavaSourceWriter(javaFileName,javaPackageName, targetDirLocation,entityPath, separator);
        return writer;
	}
	
    public static JavaSourceWriter setupJavaSourceWriter(String javaFileName, 
    		String javaPackageName, File targetDir,String entityPath, char separator)
            throws FileNotFoundException, IOException {
        String outFilePath = javaPackageName.replace(separator, File.separatorChar);
        File outFileDir = new File(targetDir, outFilePath);
        if(!outFileDir.exists()) {
            outFileDir.mkdirs();
        }
        JavaSourceWriter writer = new JavaSourceWriter(javaFileName,javaPackageName, targetDir,entityPath, separator);
        return writer;
    }
    
    public static JavaResourceWriter setupJavaResourceWriter(String javaFileName,
			String javaPackageName, JavaFolderLocation targetDirLocation,String entityPath,
			char separator) {
    	JavaResourceWriter writer = new JavaResourceWriter(javaFileName,javaPackageName, targetDirLocation,entityPath, separator);
        return writer;
	}
    
    public static JavaResourceWriter setupJavaResourceWriter(String javaFileName, 
    		String javaPackageName, File targetDir,String entityPath, char separator)
            throws FileNotFoundException, IOException {
        String outFilePath = javaPackageName.replace(separator, File.separatorChar);
        File outFileDir = new File(targetDir, outFilePath);
        if(!outFileDir.exists()) {
            outFileDir.mkdirs();
        }
        JavaResourceWriter writer = new JavaResourceWriter(javaFileName,javaPackageName, targetDir,entityPath, separator);
        return writer;
    }
    
//    public static void writeFile(JavaFile jFile, File targetDir, char separator) throws IOException {
//        String outFilePath = jFile.getPackage().replace(separator, File.separatorChar);
//        File outFileDir = new File(targetDir, outFilePath);
//        File outFile = new File(outFileDir, jFile.getShortName() + CodeGenConstants.JAVA_FILE_EXTENSION);
//        if(!outFileDir.exists()) {
//            outFileDir.mkdirs();
//        }
//        Writer writer = CodeGenHelper.makeFileWriter(outFile);
//        writer.write(jFile.toString());
//        writer.flush();
//        writer.close();
//
//    }
//    
//    public static void writeStream(JavaFile jFile,JavaFolderLocation targetDir, char separator) throws Exception {
//    	JavaFileLocation outFile = targetDir.addFile(jFile.getPackage(), jFile
//				.getShortName()
//				+ CodeGenConstants.JAVA_FILE_EXTENSION);
//    	Writer writer = outFile.openWriter();
//    	writer.write(jFile.toString());    	
//        writer.flush();
//        writer.close();
//    }

    
    public static String generateStringCode(String str) {
        if (null == str) {
            return "null";
        } else {
            final StringBuffer buffer = new StringBuffer("new String(new char[]{");
            final int length = str.length();
            if (length > 0) {
                buffer.append((int) str.charAt(0));
            }
            if (length > 1) {
                for (int i=1; i<length; i++ ) {
                    buffer.append(",").append((int) str.charAt(i));
                }
            }
            return buffer.append("})").toString();
        }
    }
    
    public static boolean isDBConcept(Concept cept) {
        Map hp = cept.getExtendedProperties();
        if(hp.containsKey("SCHEMA_NAME") &&
                hp.containsKey("OBJECT_NAME")&&
                hp.containsKey("OBJECT_TYPE"))
            return true;
        return false;  //To change body of created methods use File | Settings | File Templates.
    }
    
    
    public static void createJAR(String jarName, File dir, String baseDir) throws Exception {
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");

        File jarFile = new File(jarName);

        OutputStream fileOut = new FileOutputStream(jarFile);
        JarOutputStream jarOut = new JarOutputStream(fileOut, manifest);

        createJar(jarOut, dir, baseDir);
        jarOut.flush();
        jarOut.finish();

    }
    
    public static void createJar(JarOutputStream jarOut, File directory, String baseDir) throws Exception {

        File[] fileArray = directory.listFiles();
        byte buffer[] = new byte[1024];
        int bytesRead;
        for (int i=0; i<fileArray.length; i++) {
            File currFile = fileArray[i];
            if (currFile.isDirectory()) {
                createJar(jarOut, currFile, baseDir);
            }
            else {
                String currFilename = currFile.getName();
                // split the filename into directory and class name - in order
                // to prevent putting the complete filenames into JAR

                String pathInJar = directory.getCanonicalPath();
                pathInJar = pathInJar.substring(baseDir.length()+1);

                JarEntry entry = new JarEntry(pathInJar + "/" + currFilename);
                jarOut.putNextEntry(entry);
                entry.setMethod(JarEntry.DEFLATED);

                File file = new File(directory, currFilename);
                FileInputStream fis = new FileInputStream(file);
                while ((bytesRead = fis.read(buffer)) != -1) {
                    jarOut.write(buffer, 0, bytesRead);
                }
                fis.close();
                jarOut.flush();
                jarOut.closeEntry();
            }
        }
    }
    
    public static void deleteFileOrDirectory(File f) {
    	if(f == null) 
    		return;
        if (f.isDirectory()) {
            final File[] files = f.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFileOrDirectory(files[i]);
            }//for
        }//if
        f.delete();
    }//deleteFileOrDirectory
    
    public static boolean isModuleEntity(Entity entity) {
        String typeName = (String) entity.getTransientProperty("typeName");
        CEPModule module = ModuleRegistry.getInstance().getModule(typeName);
        return (module != null) ;
    }
    
    /**
     * @deprecated Use {@link CodeGenHelper.compileInMemory }
     */    
    public static void compileSourceFiles(String classPath, List<File> srcFiles,
			File targetDir, boolean debug,boolean isAnnotationProcessingOnly,String[] annotationProcessors) throws CompilationFailedException {
		if (srcFiles.size() <= 0) {
			throw new CompilationFailedException(CompileErrors
					.noJavaFilesToCompile());
		}

		ArrayList<String> argStrings = new ArrayList<String>();
		if (debug)
			argStrings.add("-g");
		argStrings.add("-source");
		argStrings.add(StudioCore.getSourceJavaVersion());
		argStrings.add("-target");
		argStrings.add(StudioCore.getTargetJavaVersion());
		argStrings.add("-encoding");
		argStrings.add(CodeGenConstants.charset);
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
		argStrings.add("-classpath");
		argStrings.add(classPath);
		if (targetDir != null) {
			if (!targetDir.exists()) {
				targetDir.mkdirs();
			}
			if (targetDir.exists()) {
				argStrings.add("-d");
				argStrings.add(targetDir.getAbsolutePath());
			}
		}

		// add source files to the end of the args
		for (int ii = 0; ii < srcFiles.size(); ii++) {
			argStrings.add((srcFiles.get(ii)).getAbsolutePath());
		}

		String[] argsArray = (String[]) argStrings
				.toArray(new String[argStrings.size()]);
		argStrings = null;

		StringWriter output = new StringWriter();
		PrintWriter pw = new PrintWriter(new BufferedWriter(output));
		int result = 0;
		try {
			Class main = Class.forName("com.sun.tools.javac.Main");
			Method compile = main.getDeclaredMethod("compile", String[].class, PrintWriter.class);
			result = (Integer)compile.invoke(null, argsArray, pw);
		} catch (Exception roe) {
			throw new CompilationFailedException("Could not load com.sun.tools.javac.Main.  tools.jar may be missing from classpath.", roe);
		}
		pw.flush();
		pw.close();
		output.flush();
		if (result != 0) {
			throw new CompilationFailedException(output.toString());
		}
	}
    
    /**
     * @param classPath
     * @param srcFiles
     * @param targetDir
     * @param debug
     */
    public static void compileInMemory(String classPath,List<File> srcFiles,
			File targetDir, boolean debug) {
    	
    	JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    	
    	
    	CodegenDiagnosticListener diagnosticListener = new CodegenDiagnosticListener();
    	
    	DiagnosticCollector<JavaFileObject> diagnosticsCollector =
    		new DiagnosticCollector<JavaFileObject>();
    	
    	StandardJavaFileManager fileManager = compiler.getStandardFileManager(
				diagnosticListener, Locale.getDefault(), Charset.forName(CodeGenConstants.charset));
    	
    	Iterable<? extends JavaFileObject> fileObjects = fileManager
				.getJavaFileObjectsFromFiles(srcFiles);
    	
    	List<String> argStrings = new ArrayList<String>();
    	if(debug) {
    		argStrings.add("-g");
    	}
		argStrings.add("-source");
		argStrings.add(StudioCore.getSourceJavaVersion());
		argStrings.add("-target");
		argStrings.add(StudioCore.getTargetJavaVersion());
		argStrings.add("-encoding");
		argStrings.add(CodeGenConstants.charset);
		if(classPath != null && !classPath.isEmpty()){
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
		
		List<Diagnostic<? extends JavaFileObject>> diagnostics = diagnosticsCollector
				.getDiagnostics();
		for (Diagnostic<? extends JavaFileObject> d : diagnostics) {
			// Print all the information here.
		}
		if (result == true) {
			StudioCorePlugin.log("Compilation has succeeded");
		} else {
			CompilationFailedException e = new CompilationFailedException(diagnosticListener.getCompileOutput());
			StudioCorePlugin.log("Compilation fails.",e);
		}
    	
    }
    
    
    
    public static class CodegenDiagnosticListener implements DiagnosticListener<JavaFileObject> {
    	private String compileOutput;
		public CodegenDiagnosticListener() {
			this.compileOutput = "";
		}
    	public void report(javax.tools.Diagnostic<? extends JavaFileObject> diagnostic) {
    		StringBuilder sb = new StringBuilder();
    		sb.append("Code->" +  diagnostic.getCode())
    		.append("Column Number->" + diagnostic.getColumnNumber())
    		.append("End Position->" + diagnostic.getEndPosition())
    		.append("Kind->" + diagnostic.getKind())
    		.append("Line Number->" + diagnostic.getLineNumber())
    		.append("Message->"+ diagnostic.getMessage(Locale.getDefault()))
    		.append("Position->" + diagnostic.getPosition())
    		.append("Source" + diagnostic.getSource())
    		.append("Start Position->" + diagnostic.getStartPosition())
    		.append("\n");
    		compileOutput = sb.toString();
    	}
    	
    	public String getCompileOutput() {
			return compileOutput;
		}
    }



	



	

}
