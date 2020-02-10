package com.tibco.be.functions.build;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.be.util.CommandStreamReader;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.util.PlatformUtil;

/**
 * Catalog function to be used by building ear/class file for BE project
 * @author vpatil
 */
@com.tibco.be.model.functions.BEPackage (
		catalog = "Studio",
        category = "StudioUtil",
        synopsis = "Functions for building EAR and generating class files for the project.")
public class BuildFunctions {
 
    private static Logger LOGGER = null;
    private static String STUDIO_TOOLS_PATH = "/studio/bin/studio-tools";
    private static final String COMMAND_EXEC_FAILURE_MSG = "java.lang.Exception: ";
   
    static  {
    	if(!PlatformUtil.INSTANCE.isStudioPlatform()) {
    		LOGGER = LogManagerFactory.getLogManager().getLogger(BuildFunctions.class);
    	}
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "buildEar",
        synopsis = "Builds the project EAR file for the given project path.",
        signature = "boolean buildEar(String projectName, String projectPath, String earPath, String extendedClasspath, boolean useLegacyCompiler)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "The name of the project to be built."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectPath", type = "String", desc = "The path to the project to be built."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "earPath", type = "String", desc = "The full path to the output ear file, i.e. C:/temp/proj.ear."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "extendedClasspath", type = "String", desc = "or <code>null</code> if no additional classPath entries are needed"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "useLegacyCompiler", type = "boolean", desc = "Whether to use legacy compilation"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "libPath", type = "String", desc = "Optional lib path")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "A boolean indicating success or failure."),
        version = "4.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Builds the project EAR file for the given project path.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
 
    public static boolean buildEar(String projectName, String projectPath, String earPath, String extendedClasspath, boolean useLegacyCompiler, String ... libPath) {
    	String beHome = System.getProperty("tibco.env.BE_HOME");
    	if(beHome != null) {
    		String studioToolsExecPath = beHome + STUDIO_TOOLS_PATH;
    		
    		List<String> buildEarCommand = new ArrayList<String>();
    		buildEarCommand.add(studioToolsExecPath);
    		buildEarCommand.add("--propFile");
    		buildEarCommand.add(studioToolsExecPath + ".tra");
    		buildEarCommand.add("-core");
    		buildEarCommand.add("buildEar");
    		buildEarCommand.add("-x");
    		buildEarCommand.add("-o");
    		buildEarCommand.add(earPath);
    		buildEarCommand.add("-p");
    		buildEarCommand.add(projectPath);
    		buildEarCommand.add("-n");
    		buildEarCommand.add(projectName);
    		
    		if (extendedClasspath != null && extendedClasspath.length() > 0) {
    			extendedClasspath = extendedClasspath.replaceAll("\\\\", "/");

    			buildEarCommand.add("-cp");
    			buildEarCommand.add(extendedClasspath);
    		}
    		
    		if (libPath.length > 0 && libPath[0] != null && !libPath[0].isEmpty()) {
    			buildEarCommand.add("-pl");
    			buildEarCommand.add(libPath[0]);
    		}
    		
    		if (useLegacyCompiler) buildEarCommand.add("-lc");

	    	String cmdOutput;
	    	try {
	    		cmdOutput = executeCommand(buildEarCommand, projectPath);
	    		LOGGER.log(Level.DEBUG, "\nCommand O/P - " + cmdOutput);
	    	} catch(Exception exception) {
	    		if (LOGGER != null) {
	    			LOGGER.log(Level.ERROR, exception, "Exception while building EAR.");
	    		} else {
	    			exception.printStackTrace();
	    		}
	    		return false;
	    	}
	    	return checkCommandForFailures(cmdOutput);
    	} else {
    		if(LOGGER != null) {
    			LOGGER.log(Level.ERROR, "BE_HOME not set.");
    		} else {
    			System.out.println("BE_HOME not set.");
    		}
    		return false;
    	}
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "buildClasses",
        synopsis = "Builds classes for all resources inside this project.",
        signature = "boolean buildClasses(String projectName, String projectPath, String outputDirectory, String extendedClasspath, boolean useLegacyCompiler)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectName", type = "String", desc = "The name of the project to be built."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectPath", type = "String", desc = "The path to the project to be built."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "outputDirectory", type = "String", desc = "The full path of the output directory, e.g : C:/temp."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "extendedClasspath", type = "String", desc = "or <code>null</code> if no additional classPath entries are needed"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "useLegacyCompiler", type = "boolean", desc = "Whether to use legacy compilation"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "libPath", type = "String", desc = "Optional lib path")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "A boolean indicating success or failure."),
        version = "4.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Builds classes for all resources inside this project.",
        cautions = "none",
        fndomain = {ACTION},
        example = ""
    )
	public static boolean buildClasses(String projectName, String projectPath,
			String outputDirectory, String extendedClasspath, boolean useLegacyCompiler, String ... libPath) {
		String beHome = System.getProperty("tibco.env.BE_HOME");
		if (beHome != null) {
			String studioToolsExecPath = beHome + STUDIO_TOOLS_PATH;
			
			List<String> buildClassesCommand = new ArrayList<String>();
	    	buildClassesCommand.add(studioToolsExecPath); 
	    	buildClassesCommand.add("--propFile");
	    	buildClassesCommand.add(studioToolsExecPath + ".tra");
	    	buildClassesCommand.add("-core");
	    	buildClassesCommand.add("generateClass");
	    	buildClassesCommand.add("-x");
	    	buildClassesCommand.add("-o");
	    	buildClassesCommand.add(outputDirectory);
	    	buildClassesCommand.add("-p");
	    	buildClassesCommand.add(projectPath);
	    	buildClassesCommand.add("-n");
	    	buildClassesCommand.add(projectName);
	    	
	    	if (extendedClasspath != null && extendedClasspath.length() > 0) {
	    		extendedClasspath = extendedClasspath.replaceAll("\\\\", "/");
	    		
	    		buildClassesCommand.add("-cp");
	        	buildClassesCommand.add(extendedClasspath);
	    	}
	    	
	    	if (useLegacyCompiler) buildClassesCommand.add("-lc");
	    	
	    	if (libPath.length > 0 && libPath[0] != null && !libPath[0].isEmpty()) {
    			buildClassesCommand.add("-pl");
    			buildClassesCommand.add(libPath[0]);
    		}
	    	
	    	String cmdOutput;
	    	try {
	    		cmdOutput = executeCommand(buildClassesCommand, projectPath);
	    		LOGGER.log(Level.DEBUG, "Command O/P - " + cmdOutput);
	    	} catch(Exception exception) {
	    		if (LOGGER != null) {
	    			LOGGER.log(Level.ERROR, exception, "Exception while building classes.");
	    		} else {
	    			exception.printStackTrace();
	    		}
	    		return false;
	    	}
	    	  	
	    	return checkCommandForFailures(cmdOutput);
		} else {
			if (LOGGER != null) {
				LOGGER.log(Level.ERROR, "BE_HOME not set.");
			} else {
				System.out.println("BE_HOME not set.");
			}
			return false;
		}
	}

    /**
     * Executes the studio tools command
     * 
     * @param studioToolsExecutable
     * @param projectPath
     * @return
     */
    private static String executeCommand(List<String> studioToolsExecutable, String projectPath) throws Exception {
    	ProcessBuilder stProcessBuilder = new ProcessBuilder(studioToolsExecutable);

    	Map<String, String> env = stProcessBuilder.environment();
    	env.put("be.codegen.rootDirectory", System.getProperty("be.codegen.rootDirectory", "Codegen"));

    	/**
    	 * In case of project using StudioUtils buildEar/buildClasses and running as a Windows service and 
    	 * the below tra properties are getting passed on to the subprocess (studio-tools) and are causing problem.
    	 * Thus these should be removed from the subprocess env. 
    	 */
    	env.remove("APP_ARGS");
    	env.remove("CUSTOM_EXT_APPEND_CP");

    	Process studioToolsProcess = stProcessBuilder.start();

    	CommandStreamReader errorStream = new CommandStreamReader(studioToolsProcess.getErrorStream());
    	errorStream.start();
    	CommandStreamReader outputStream = new CommandStreamReader(studioToolsProcess.getInputStream());
    	outputStream.start();
    	
    	int exitValue = studioToolsProcess.waitFor();

    	String cmdOutput = (errorStream.getCommandOutput() != null) ? errorStream.getCommandOutput() : "";
    	cmdOutput += (outputStream.getCommandOutput() != null) ? outputStream.getCommandOutput() : "";
    	
    	return cmdOutput;
    }
    
    /**
     * Check if the command execution succeeded/failed
     * @param cmdOutput
     * @return
     */
    private static boolean checkCommandForFailures(String cmdOutput) {
    	if (cmdOutput != null && cmdOutput.contains(COMMAND_EXEC_FAILURE_MSG)) {
			LOGGER.log(Level.ERROR, cmdOutput);
    		return false;
    	}
    	return true;
    }
    
    public static void main(String[] args) {
//    	System.out.println("Response - " + BuildFunctions.buildEar("CreditCardApplication", "C:/tibco/BE5.1/511_HF1_V38/be/5.1/examples/standard/WebStudio/CreditCardApplication", "C:/tibco/BE5.1/511_HF1_V38/be/5.1/examples/standard/WebStudio/CCA.ear", "", false));
//    	System.out.println("Response - " + BuildFunctions.buildEar("fil_calc_engine", "D:/Softwares/Tibco/Customers/HUCH/fil_calc_engine", "D:/Softwares/Tibco/Customers/HUCH/Archives/fil_calc_engine.ear", "D:/Softwares/Tibco/Customers/HUCH/Archives/dtmaintainance.jar;D:/Softwares/Tibco/Customers/HUCH/Archives/dom4j-1.6.1.jar;D:/Softwares/Tibco/Customers/HUCH/Archives/jaxen-1.1-beta-6.jar", false));
//    	System.out.println("Response - " + BuildFunctions.buildClasses("CreditCardApplication", "C:/tibco/BE5.1/511_HF1_V38/be/5.1/examples/standard/WebStudio/CreditCardApplication", "C:/tibco/BE5.1/511_HF1_V38/be/5.1/examples/standard/WebStudio/CreditCardApplication/deployment", "", true));
    }
}
