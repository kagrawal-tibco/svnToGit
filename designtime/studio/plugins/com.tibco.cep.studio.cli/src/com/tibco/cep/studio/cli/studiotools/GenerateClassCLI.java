/**
 * 
 */
package com.tibco.cep.studio.cli.studiotools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarInputStream;

import org.eclipse.core.runtime.NullProgressMonitor;

import com.tibco.be.parser.codegen.CodeGenHelper;
import com.tibco.be.parser.codegen.JavacUtil;
import com.tibco.be.parser.codegen.stream.StreamClassLoaderImpl;
import com.tibco.cep.designtime.model.registry.AddOnType;
import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.repo.emf.EMFProject;
import com.tibco.cep.studio.core.repo.emf.providers.CoreIndexResourceProvider;
import com.tibco.cep.studio.core.util.packaging.impl.DefaultRuntimeClassesPackager;
import com.tibco.cep.studio.core.util.packaging.impl.PackagingHelper;

/**
 * CLI class to generate class files for all be resources inside 
 * a project.
 *
 */
public class GenerateClassCLI extends CoreCLI {

	private final static String OPERATION_GENERATE_CLASS = "generateClass";

    private final static String FLAG_GENERATE_CLASS_HELP = "-h"; 	// Prints help

    private final static String FLAG_GENERATE_CLASS_OVERWRITE = "-x"; 	// Overwrite classes

    private final static String FLAG_GENERATE_CLASS_OUTPUTDIR = "-o"; 	// Output root directory to generate classes in

    private final static String FLAG_GENERATE_CLASS_PROJECTPATH = "-p"; 	// Path of the Studio project
    
    private final static String FLAG_BUILDEAR_PROJECT_LIB_PATH= "-pl"; 	// Project Libs <plibpath><path sep><plibpath>

    private final static String FLAG_GENERATE_CLASS_PROJECTNAME = "-n"; 	// Name of the Studio project

    private final static String FLAG_GENERATE_CLASS_EXTENDED_CP = "-cp";   // extended classpath
    
    private final static String FLAG_GENERATE_USE_LEGACY_COMPILER = "-lc"; // use legacy compiler

    public static void main(String[] args) throws Exception {
        if (args.length == 0 ||
                (args.length == 1 && args[0].trim().endsWith("help"))) {
            StudioCommandLineInterpreter.printPrologue(new String[]{});
            System.out.println(new GenerateClassCLI().getHelp());
            //System.exit(-1);
            return;
        }
        String newArgs[] = new String[args.length + 2];
        newArgs[0] = CoreCLI.OPERATION_CATEGORY_CORE;
        newArgs[1] = OPERATION_GENERATE_CLASS;
        System.arraycopy(args, 0, newArgs, 2, args.length);
        StudioCommandLineInterpreter.executeCommandLine(newArgs);
    }


    public String[] getFlags() {
        return new String[] {FLAG_GENERATE_CLASS_HELP,
                FLAG_GENERATE_CLASS_OVERWRITE,
                FLAG_GENERATE_USE_LEGACY_COMPILER,
                FLAG_GENERATE_CLASS_OUTPUTDIR,
                FLAG_GENERATE_CLASS_PROJECTPATH,
                FLAG_GENERATE_CLASS_PROJECTNAME,
                FLAG_BUILDEAR_PROJECT_LIB_PATH,
                FLAG_GENERATE_CLASS_EXTENDED_CP};
    }

    public String getHelp() {
        String helpMsg = "Usage: " + getOperationFlag() + " " + getUsageFlags() + "\n" +
                "where, \n" +
                "	-h (optional) prints this usage \n" +
                "	-x (optional) overwrites the specified output file if it exists. \n" +
                "	-lc (optional) use legacy compiler. \n" +
                //"	-v (optional) validates project before creating archive. Aborts on errors. Warnings are ignored. \n" +
                //"	-s (optional) saves the project after the archive is created (project must be writable). \n" +
                "	-o (optional) specifies the output folder for generated classes" +
                "	-p specifies project \n"+
                "   -pl (optional) specifies the list of project library file path separated by a path separator \n"+
                "   -cp (optional) extended classpath";
        return helpMsg;
    }


    public String getOperationFlag() {
        return OPERATION_GENERATE_CLASS;
    }

    public String getOperationName() {
    	return ("Generate Class");
    }
    
    public String getUsageFlags() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		stringBuilder.append(FLAG_GENERATE_CLASS_HELP);
		stringBuilder.append("]");
		stringBuilder.append(FLAG_GENERATE_CLASS_PROJECTPATH);
		stringBuilder.append(" ");
		stringBuilder.append("<Project Path>");
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_GENERATE_CLASS_PROJECTNAME);
		stringBuilder.append(" ");
		stringBuilder.append("<Optional Project Name>");
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_GENERATE_CLASS_OUTPUTDIR);
		stringBuilder.append(" ");
		stringBuilder.append("<Output Directory for generated classes>");
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_GENERATE_CLASS_OVERWRITE);
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_GENERATE_USE_LEGACY_COMPILER).append(" ");
		stringBuilder.append(FLAG_BUILDEAR_PROJECT_LIB_PATH);
		stringBuilder.append(" ");
		stringBuilder.append("<project lib path><path separator><project lib path>");
		stringBuilder.append(" ");
		stringBuilder.append(FLAG_GENERATE_CLASS_EXTENDED_CP);
		stringBuilder.append(" ");
		stringBuilder.append("<Optional extended classpath>");
		stringBuilder.append(" ");
		return stringBuilder.toString();
    }

    public boolean runOperation(Map<String, String> argsMap) throws Exception {
    	if (checkIfExcludeOperation(argsMap))
    		return true;
    	
        if (argsMap.containsKey(FLAG_GENERATE_CLASS_HELP)) {
            System.out.println(getHelp());
            return true;
        }
        
        return generateClasses(argsMap);
        
    }

    public static boolean generateClasses(Map<String, String> argsMap) throws Exception {
        String projectName = argsMap.get(FLAG_GENERATE_CLASS_PROJECTNAME);
        String outputDirectory = argsMap.get(FLAG_GENERATE_CLASS_OUTPUTDIR);
        String projectPath = argsMap.get(FLAG_GENERATE_CLASS_PROJECTPATH);
        boolean overwriteEarFile = argsMap.containsKey(FLAG_GENERATE_CLASS_OVERWRITE);
        
        boolean useLegacyCompiler = argsMap.containsKey(FLAG_GENERATE_USE_LEGACY_COMPILER);
        
		
		
        if (projectPath == null) {
            throw new Exception("Project path cannot be null");
        }
        if (!new File(projectPath).exists()) {
            throw new Exception("Project path does not exist");
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

        try {
            if (projectName == null) {
                projectName = new File(projectPath).getName();
            }
            
            
            //Load project
            EMFProject emfProject = new EMFProject(projectPath);
            /**
			 * There is no need to set name for the index as it will be derived from the repo 
			 * path passed to the EMFProject in the preload section
			 */
//            emfProject.getRuntimeIndex(AddOnType.CORE).setName(projectName);
			if(projlibpaths!=null && projlibpaths.length>0){
				emfProject.setProjectLibraries(projlibpaths);
			}
            emfProject.setStudionProjectConfigManager(StudioProjectConfigurationManager.getInstance());
            emfProject.load();
            emfProject.removeStudionProjectConfigManager();
            
			
            emfProject.getProjectConfiguration().getEnterpriseArchiveConfiguration().setOverwrite(overwriteEarFile);
            CoreIndexResourceProvider coreIndexProvider = (CoreIndexResourceProvider) emfProject.getIndexResourceProviderMap().get(AddOnType.CORE);
            // The project name is very important otherwise resources are not found from the studio project cache.
            StudioProjectCache.getInstance().putIndex(projectName, coreIndexProvider.getIndex());
			
			String extendedCP = argsMap.get(FLAG_GENERATE_CLASS_EXTENDED_CP);
			
			List<String> corePaths = new ArrayList<String>();
			JavacUtil.loadCoreInternalLibraries(corePaths);
			StringBuilder classpath = new StringBuilder();
			for(String path:corePaths){
				classpath.append(path).append(File.pathSeparator);
			}
			if (extendedCP != null && !extendedCP.isEmpty()) {
				classpath.append(extendedCP).append(File.pathSeparator);
			}
			
			classpath.append(PackagingHelper.getProjectClassPath(emfProject));
			
			if (new File(outputDirectory).exists()) {
				if(!overwriteEarFile) {
					overwriteEarFile = ConsoleInput.readYesNo(Messages.getString("GenerateClasses.OutputFolder.Exists"),false);
					if(!overwriteEarFile) {
						return true;
					}
					
				}
			}
			
			PackagingHelper.addExtendedClasspathEntires(extendedCP,emfProject.getName());
			PackagingHelper.loadExtendedClasspathOntologyFunctions(extendedCP,emfProject);
			
			//We do not need any archive
			//Hence the first parameter is set to null
            DefaultRuntimeClassesPackager classesPackager =
                    new DefaultRuntimeClassesPackager(null,	emfProject.getOntology(),
                                                      outputDirectory,
                                                      true,
                                                      classpath.toString(),
                                                      false,
                                                      false, new NullProgressMonitor(), useLegacyCompiler);
            
            if(useLegacyCompiler){
            	classesPackager.setDeleteTempFiles(false);
            	classesPackager.close();
            } else {
            	
            	classesPackager.setDeleteTempFiles(true);
            	classesPackager.close();
            	
            	JarInputStream jis = new JarInputStream(classesPackager.getCompileInputStream());
            	//Creates BE dir with timestamp to avoid creation problems on unix.
            	//Take temp dir as a property in case any customization is desired 
            	
            	String dir = System.getenv("be.codegen.rootDirectory");
            	if(dir == null || dir.length() <= 0) {
            		dir = "BE_" + System.currentTimeMillis();
            	}
            	File tmpDir = new File(outputDirectory, dir);
            	
            	CodeGenHelper.deleteFileOrDirectory(tmpDir);
            	if (!tmpDir.mkdirs()) {
            		throw new IOException("Failed to create BE directory: "
            				+ tmpDir.getCanonicalPath());
            	}// if
            	JavacUtil.writeJarOutput(tmpDir, jis);
            }
            System.out.println("\n" + Messages.getString("GenerateClass.Success"));
           
        } catch (Exception e) {
        	throw new Exception(Messages.getString("GenerateClass.Failure") + " - " + e.getMessage(), e);           
        }
        return true;
    }
}
