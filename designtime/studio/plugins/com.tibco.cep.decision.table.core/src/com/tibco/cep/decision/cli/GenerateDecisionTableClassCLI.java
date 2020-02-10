package com.tibco.cep.decision.cli;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarInputStream;

import com.tibco.be.parser.RuleError;
import com.tibco.be.parser.codegen.CodeGenConstants;
import com.tibco.be.parser.codegen.CodeGenHelper;
import com.tibco.be.parser.codegen.CompilationFailedException;
import com.tibco.be.parser.codegen.JavaFileWriter;
import com.tibco.be.parser.codegen.JavacUtil;
import com.tibco.be.parser.codegen.SourceCompiler;
import com.tibco.be.parser.codegen.stream.JavaFolderLocation;
import com.tibco.be.parser.codegen.stream.StreamClassLoaderImpl;
import com.tibco.cep.decision.table.codegen.DTCodegenGlobalContext;
import com.tibco.cep.decision.table.codegen.DTCodegenTableContext;
import com.tibco.cep.decision.table.codegen.DTCodegenUtil;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.utils.DecisionTableCoreUtil;
import com.tibco.cep.designtime.model.registry.AddOnType;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.studio.cli.studiotools.ConsoleInput;
import com.tibco.cep.studio.cli.studiotools.GenerateClassCLI;
import com.tibco.cep.studio.cli.studiotools.StudioCommandLineInterpreter;
import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.repo.emf.EMFProject;
import com.tibco.cep.studio.core.repo.emf.providers.CoreIndexResourceProvider;
import com.tibco.cep.studio.core.util.packaging.impl.PackagingHelper;

/**
 * CLI class to build and compile specified DT file
 * 
 * @author smarathe
 * @author Vikram Patil
 */
public class GenerateDecisionTableClassCLI extends DecisionTableCLI {
	static {
		com.tibco.cep.Bootstrap.ensureBootstrapped();
	}

	private final static String OPERATION_GENERATE_DT_CLASS = "generateDTClass";
	private final static String FLAG_GENERATE_DT_CLASS_HELP = "-h"; // Prints
																	// help
	private final static String FLAG_GENERATE_DT_CLASS_OVERWRITE = "-x"; // Overwrite
																			// classes
	private final static String FLAG_GENERATE_DT_CLASS_OUTPUTDIR = "-o"; // Output
																			// root
																			// directory
																			// to
																			// generate
																			// classes
																			// in
	private final static String FLAG_GENERATE_DT_CLASS_DTPATH = "-d"; // Path of
																		// the
																		// DT
																		// within
																		// project
	private final static String FLAG_GENERATE_DT_CLASS_PROJECTPATH = "-p"; // Path
																			// of
																			// the
																			// Studio
																			// project
	private final static String FLAG_BUILDEAR_PROJECT_LIB_PATH= "-pl"; 	// Project Libs <plibpath><path sep><plibpath>
	private final static String FLAG_GENERATE_DT_CLASS_EAR_PATH = "-e";
	private final static String FLAG_GENERATE_DT_CLASS_EXTENDED_CP = "-cp";
	private final static String FLAG_GENERATE_USE_LEGACY_COMPILER = "-lc";

	public static void main(String[] args) throws Exception {
		if (args.length == 0 || (args.length == 1 && args[0].trim().endsWith("help"))) {
			StudioCommandLineInterpreter.printPrologue(new String[] {});
			System.out.println(new GenerateClassCLI().getHelp());
			// System.exit(-1);
			return;
		}
		String newArgs[] = new String[args.length + 2];
		newArgs[0] = DecisionTableCLI.OPERATION_CATEGORY_DT;
		newArgs[1] = OPERATION_GENERATE_DT_CLASS;
		System.arraycopy(args, 0, newArgs, 2, args.length);
		StudioCommandLineInterpreter.executeCommandLine(newArgs);
	}

	public String[] getFlags() {
		return new String[] { FLAG_GENERATE_DT_CLASS_HELP, FLAG_GENERATE_DT_CLASS_OVERWRITE, FLAG_GENERATE_DT_CLASS_OUTPUTDIR, FLAG_GENERATE_DT_CLASS_PROJECTPATH,
				FLAG_GENERATE_DT_CLASS_DTPATH, FLAG_GENERATE_DT_CLASS_EAR_PATH, FLAG_BUILDEAR_PROJECT_LIB_PATH, FLAG_GENERATE_DT_CLASS_EXTENDED_CP };
	}

	public String getHelp() {
		String helpMsg = "Usage: " + getOperationCategory() + " " + getOperationFlag() + " " + getUsageFlags() + "\n" + "where, \n" + "	-h (optional) prints this usage \n"
				+ "	-x (optional) overwrites the specified output file if it exists \n"
				+ "	-o (optional) specifies the output folder for the generated classes \n"
				+ "   -p specifies path to the project to which the DT belongs \n" + "	-d specifies path to the Decision Table \n"
				+ "   -e specifies path to the ear of the project to which the Decision Table belongs. \n"
				+ "   -pl (optional) specifies the list of project library file path separated by a path separator \n"
				+ "   -cp (optional) specifies extended classpath \n"
				+ "   -lc use legacy compiler\n";

		return helpMsg;
	}

	public String getOperationFlag() {
		return OPERATION_GENERATE_DT_CLASS;
	}

	public String getOperationName() {
		return ("Generate Decision Table Class");
	}

	public String getUsageFlags() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		stringBuilder.append(FLAG_GENERATE_DT_CLASS_HELP);
		stringBuilder.append("]");
		stringBuilder.append(FLAG_GENERATE_DT_CLASS_DTPATH);
		stringBuilder.append(" ");
		stringBuilder.append("Decision Table Path");
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_GENERATE_DT_CLASS_PROJECTPATH);
		stringBuilder.append(" ");
		stringBuilder.append("Project Path");
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_GENERATE_DT_CLASS_OUTPUTDIR);
		stringBuilder.append(" ");
		stringBuilder.append("<Output Directory for generated classes>");
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_GENERATE_DT_CLASS_OVERWRITE);
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_GENERATE_DT_CLASS_EAR_PATH);
		stringBuilder.append(" ");
		stringBuilder.append("EAR Path");
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_BUILDEAR_PROJECT_LIB_PATH);
		stringBuilder.append(" ");
		stringBuilder.append("<project lib path><path separator><project lib path>");
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_GENERATE_DT_CLASS_EXTENDED_CP);
		stringBuilder.append(" ");
		stringBuilder.append("<Optional extended classpath>");
		stringBuilder.append(" ");
		return stringBuilder.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.studio.cli.studiotools.ICommandLineInterpreter#runOperation
	 * (java.util.Map)
	 */
	@Override
	public boolean runOperation(Map<String, String> argsMap) throws Exception {
		if (checkIfExcludeOperation(argsMap))
			return true;

		if (argsMap.containsKey(FLAG_GENERATE_DT_CLASS_HELP)) {
			System.out.println(getHelp());
			return true;
		}

		String DTPath = argsMap.get(FLAG_GENERATE_DT_CLASS_DTPATH);
		String EARPath = argsMap.get(FLAG_GENERATE_DT_CLASS_EAR_PATH);
		String projectPath = argsMap.get(FLAG_GENERATE_DT_CLASS_PROJECTPATH);
		String extendedClassPath = argsMap.get(FLAG_GENERATE_DT_CLASS_EXTENDED_CP);
		boolean useLegacyCompiler = argsMap.containsKey(FLAG_GENERATE_USE_LEGACY_COMPILER); 

		if (!new File(projectPath,".beproject").exists()) {
			throw new Exception(String.format("Project does not exist at location specified by %s", projectPath));
		}

		if (!DTPath.endsWith(".rulefunctionimpl")) {
			throw new Exception(String.format("Decision Table specified by %s is incorrect", DTPath));
		}

		File earFile = new File(EARPath);
		if (!earFile.exists()) {
			throw new Exception(String.format("EAR File does not exist at location specified by %s", EARPath));
		}
		
		File dtPath = new File(projectPath,DTPath);

		System.out.println("Generating Classes for Decision Table " + dtPath + " ...");

		if (!dtPath.exists()) {
			throw new Exception(String.format("Decision Table does not exist at location specified by %s", dtPath));
		}

		String outputDirectory = argsMap.get(FLAG_GENERATE_DT_CLASS_OUTPUTDIR);
		if (outputDirectory == null) {
			outputDirectory = System.getProperty("user.dir");
		}
		boolean overwriteDTClasses = argsMap.containsKey(FLAG_GENERATE_DT_CLASS_OVERWRITE);
		
		File outputDirFile = new File(outputDirectory);
		if (!outputDirFile.exists()) {
			if (!outputDirFile.mkdirs()) {
				throw new Exception(String.format("Error creating Decision Table classes output directory %s", outputDirectory));
			}
		} else {
			if(!overwriteDTClasses) {
				overwriteDTClasses = ConsoleInput.readYesNo("Decision Table output folder exist already.Overwrite(Y/N)",false);
				if(!overwriteDTClasses) {
					return true;
				}
			}
		}
		
		String projLibPath = argsMap.get(FLAG_BUILDEAR_PROJECT_LIB_PATH);
		String [] projlibpaths = null;
		if(projLibPath!=null){
			projlibpaths = projLibPath.split(File.pathSeparator);
			for(String plibpath:projlibpaths){
				if (!new File(plibpath).exists()) {
					throw new Exception("Project Library path not found");
				}
			}
		}
		File beJar = null;

		
		try {
			EMFProject project = new EMFProject(projectPath);
			if(projlibpaths!=null && projlibpaths.length>0){
				project.setProjectLibraries(projlibpaths);
			}
			project.setStudionProjectConfigManager(StudioProjectConfigurationManager.getInstance());
			project.load();
			project.removeStudionProjectConfigManager();
			
			CoreIndexResourceProvider coreIndexProvider = (CoreIndexResourceProvider) project.getIndexResourceProviderMap().get(AddOnType.CORE);
            StudioProjectCache.getInstance().putIndex(project.getName(), coreIndexProvider.getIndex());
			
			List<String> corePaths = new ArrayList<String>();
			JavacUtil.loadCoreInternalLibraries(corePaths);
			
			StringBuilder classpathBuilder = new StringBuilder();
			for(String path:corePaths){
				classpathBuilder.append(path).append(File.pathSeparator);
			}
			if (extendedClassPath != null && !extendedClassPath.isEmpty()) {
				classpathBuilder.append(extendedClassPath).append(File.pathSeparator);
			}
			
			classpathBuilder.append(PackagingHelper.getProjectClassPath(project));
			
			String classpath = classpathBuilder.toString();
			
			PackagingHelper.addExtendedClasspathEntires(extendedClassPath,project.getName());
			PackagingHelper.loadExtendedClasspathOntologyFunctions(extendedClassPath,project);
						
			if(useLegacyCompiler) {
				beJar = JavacUtil.writeBeJar(earFile, outputDirFile);
				JavaFileWriter dtJavaFile = generateDTJavaClass(project, dtPath.getPath(), earFile, outputDirFile, new HashMap<String, Map<String, int[]>>(), true);

				classpath = constructClassPath(beJar, classpath);
				compileDTClass(outputDirFile, dtJavaFile.getPackage(), dtJavaFile.getShortName(), classpath);
			} else {
				byte[] beJarArry = JavacUtil.readBeJar(earFile);
				StreamClassLoaderImpl scldr = new StreamClassLoaderImpl(StudioCorePlugin.class.getClassLoader());

				Map<String, byte[]> nameToByteCode = new HashMap<String, byte[]>();

				nameToByteCode.putAll(JavacUtil.readJarInputStream(new JarInputStream(new ByteArrayInputStream(beJarArry))));
				SourceCompiler sc = new SourceCompiler(classpath.toString(), scldr, outputDirFile, true, StudioCore.getSourceJavaVersion(), StudioCore.getTargetJavaVersion(),
						CodeGenConstants.charset,false,new String[0]);
				sc.init();

				generateDTJavaClass(project, dtPath.getPath(), earFile, sc.getSrcDir("src"), new HashMap<String, Map<String, int[]>>(), false);
				sc.compile(nameToByteCode);
			}
		} catch (Exception e) {
			throw new Exception("Errors occurred when compiling Decision Table. - " + e.getMessage(),e);
		} finally {
			//finally remove be.jar
			if (beJar != null) {
				beJar.delete();
			}
		}
		System.out.println("Generation completed successfully ...");
		return true;
	}

	/**
	 * Constructs the class path for compilation
	 * 
	 * @param extendedClassPath
	 * @param earFile
	 * @return
	 * @throws Exception
	 */
	private String constructClassPath(File beJar, String extendedClassPath) throws Exception {
		String classPath = null;

		RuleServiceProvider rsp = RuleServiceProviderManager.getInstance().getDefaultProvider();
		if (rsp != null) {
			classPath = RuleServiceProviderManager.getInstance().getDefaultProvider().getProperties().getProperty(DTCodegenUtil.BUI_PREPEND_CLASSPATH, "");
		} else {
			classPath = System.getProperty("java.class.path", "");
		}

		// add reference to be.jar
		if (beJar.exists()) {
			classPath += File.pathSeparator + beJar.getAbsolutePath();
		}

		// add any extended classpath
		if (extendedClassPath != null && !extendedClassPath.isEmpty()) {
			classPath += File.pathSeparator + extendedClassPath;
		}

		return classPath;
	}

	/**
	 * Generates the Java source for the specified DT
	 * 
	 * @param managedProject
	 * @param artifactPath
	 * @param earPath
	 * @param outputDir
	 * @return
	 * @throws Exception
	 */
	private static JavaFileWriter generateDTJavaClass(EMFProject managedProject, String artifactPath, File earFile, Object outputFolder,
			Map<String, Map<String, int[]>> propInfoCache, boolean useFS) throws Exception {
		List<RuleError> errors = new ArrayList<RuleError>();
		JavaFileWriter javaFileWriter = null;

		DTCodegenGlobalContext globalContext = null;
		// Build Global context
		if(useFS) {
			globalContext = new DTCodegenGlobalContext(managedProject.getOntology(), (File) outputFolder, errors, new Properties(), new HashMap<Object, Object>(),
					false, propInfoCache);
		} else {
			globalContext = new DTCodegenGlobalContext(managedProject.getOntology(), (JavaFolderLocation) outputFolder, errors, new Properties(), new HashMap<Object, Object>(),
					false, propInfoCache);
		}

		Table table = DecisionTableCoreUtil.loadModel(artifactPath);
		if (table != null) {
			// Build the table context
			DTCodegenTableContext tableContext = new DTCodegenTableContext(table, managedProject.getName(), null, errors);
			// Generate Java file
			javaFileWriter = DTCodegenUtil.makeOptimizedDTImpl(managedProject.getOntology(), tableContext, propInfoCache);
			if(useFS) {
				javaFileWriter.setTargetDir(globalContext.targetDir);
				javaFileWriter.writeFile();
			} else {
				javaFileWriter.setTargetFolder(globalContext.targetLoc);
				javaFileWriter.writeStream();
			}
		} else {
			throw new Exception("This utility is not available for non-decision table artifacts.");
		}

		if (errors.size() > 0) {
			final StringBuffer buffer = new StringBuffer(
					"Failed to parse decision tables:\n");
			for (Iterator<RuleError> i = errors.iterator(); i.hasNext();) {
				final RuleError error = (RuleError) i.next();
				if (error.getName() != null) {
					buffer.append(error.getName()).append(":\n");
				}
				buffer.append(error.toString()).append("\n");
			}
			throw new RuntimeException(buffer.toString());
		}
		
		return javaFileWriter;
	}
	
	/**
     * Compile generated java DT file.
     * 
     * @param targetDir
     * @param javaPackageName
     * @param className
     * @param classpath
     * @throws CompilationFailedException
     */
    private static void compileDTClass(File targetDir,
                                     String javaPackageName,
                                     String className,
                                     String classpath) throws CompilationFailedException {
        //Massage package name
        String packageDirPath = javaPackageName.replaceAll("\\.", "/");
        String javaSourceFile = new StringBuilder(targetDir.toString()).append(File.separator).append(packageDirPath).append(File.separator).append(className).append(CodeGenConstants.JAVA_FILE_EXTENSION).toString();

        List<File> sourceFiles = new ArrayList<File>(0);
        sourceFiles.add(new File(javaSourceFile));
        CodeGenHelper.compileSourceFiles(classpath, sourceFiles, targetDir, true,false,new String[0]);
    }
}
