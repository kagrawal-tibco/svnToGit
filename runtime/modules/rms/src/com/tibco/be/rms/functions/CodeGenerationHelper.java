package com.tibco.be.rms.functions;

import static com.tibco.be.model.functions.FunctionDomain.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tibco.be.util.CommandStreamReader;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.util.PlatformUtil;

/**
 * Catalog function to be used by RMS for various compilation options
 * @author vpatil
 */
@com.tibco.be.model.functions.BEPackage(
		catalog = "BRMS",
        category = "RMS.CodeGeneration",
        synopsis = "Functions for building EAR, classes and generating class files from decision tables.",
        enabled = @com.tibco.be.model.functions.Enabled(property="TIBCO.BE.function.catalog.RMS.CodeGeneration", value=true))

public class CodeGenerationHelper {
	private static Logger LOGGER = null;
	private static String STUDIO_TOOLS_PATH = "/studio/bin/studio-tools";
	private static final String COMMAND_EXEC_FAILURE_MSG = "java.lang.Exception:";

    static {
    	if (!PlatformUtil.INSTANCE.isStudioPlatform()) {
    		LOGGER = LogManagerFactory.getLogManager().getLogger(CodeGenerationHelper.class);
    	}
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "generateClass",
        signature = "String generateClass(String projectPath, String earPath, String outputDirectory, String artifactPath, String extendedClasspath, String studioToolsExecPath, boolean useLegacyCompiler)",
        params = {
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "projectPath", type = "String", desc = "Project Path."),
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "earPath", type = "String", desc = "Path to enterprise archive."),
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "outputDirectory", type = "String", desc = "Output directory for generated classes"),
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "artifactPath", type = "String", desc = "Artifact to compile"),
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "extendedClasspath", type = "String", desc = "Extended class path if any"),
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "useLegacyCompiler", type = "boolean", desc = "Whether to use legacy compilation"),
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "libPath", type = "String", desc = "Optional lib path")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Success/Failure"),
        version = "5.1.0",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Generates class file for the given Decision Table.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static String generateClass(String projectPath,
    		String earPath,
    		String outputDirectory,
    		String artifactPath,
    		String extendedClasspath,
    		boolean useLegacyCompiler,
    		String ... libPath) {

    	String beHome = System.getProperty("tibco.env.BE_HOME");
    	if(beHome != null) {
    		String studioToolsExecPath = beHome + STUDIO_TOOLS_PATH;

	    	List<String> generateClassCommand = new ArrayList<String>(); 
	    	generateClassCommand.add(studioToolsExecPath);
	    	generateClassCommand.add("--propFile");
	    	generateClassCommand.add(studioToolsExecPath + ".tra");
	    	generateClassCommand.add("-dt");
	    	generateClassCommand.add("generateDTClass");
	    	generateClassCommand.add("-x");
	    	generateClassCommand.add("-o");
	    	generateClassCommand.add(outputDirectory);
	    	generateClassCommand.add("-e");
	    	generateClassCommand.add(earPath);
	    	generateClassCommand.add("-d");
	    	generateClassCommand.add(artifactPath);
	    	generateClassCommand.add("-p");
	    	generateClassCommand.add(projectPath);
	
	    	if (extendedClasspath != null && extendedClasspath.length() > 0) {
	    		extendedClasspath = extendedClasspath.replaceAll("\\\\", "/");
	
	    		generateClassCommand.add("-cp");
	    		generateClassCommand.add(extendedClasspath);
	    	}
	    	
	    	if (libPath.length > 0 && libPath[0] != null && !libPath[0].isEmpty()) {
	    		generateClassCommand.add("-pl");
	    		generateClassCommand.add(libPath[0]);
    		}
	    	
	    	 if (useLegacyCompiler) generateClassCommand.add("-lc");
	    	 
	    	 String cmdOutput;
	    	 try {
	    		 cmdOutput = executeCommand(generateClassCommand, projectPath);
	    		 LOGGER.log(Level.DEBUG, "Command O/P - " + cmdOutput);
	    	 } catch(Exception exception){
	    		 if (LOGGER != null) {
	    			 LOGGER.log(Level.ERROR, exception, "Exception while generating DT class.");
	    		 } else {
	    			 exception.printStackTrace();
	    		 }
	    		 return exception.getMessage();
	    	 }
	    	 return checkCommandForFailures(cmdOutput);
    	} else {
    		if (LOGGER != null) {
    			LOGGER.log(Level.ERROR, "BE_HOME not set.");
    		} else {
    			System.out.println("BE_HOME not set.");
    		}
    		return "BE_HOME not set.";
    	}
    }
    
    /**
     * Check if the command execution succeeded/failed
     * @param cmdOutput
     * @return
     */
    private static String checkCommandForFailures(String cmdOutput) {
    	if (cmdOutput != null && cmdOutput.contains(COMMAND_EXEC_FAILURE_MSG)) {
			LOGGER.log(Level.ERROR, cmdOutput);
			int errMsgStartIndex = cmdOutput.indexOf(COMMAND_EXEC_FAILURE_MSG) + COMMAND_EXEC_FAILURE_MSG.length() + 1;
    		return cmdOutput.substring(errMsgStartIndex, cmdOutput.indexOf("\n", errMsgStartIndex));
    	}
    	return null;
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
    	/**
    	 * Ported BE-20321 - In case of RMS running as a Windows service the below tra properties are getting passed on to
    	 * the subprocess (studio-tools) and are causing problem with generate deployable/classes.
    	 * Thus these should be removed from the subprocess env. 
    	 */
    	env.remove("APP_ARGS");
    	env.remove("CUSTOM_EXT_APPEND_CP");
    	    	
    	env.put("be.codegen.rootDirectory", System.getProperty("be.codegen.rootDirectory", "Codegen"));

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
    
    public static void main(String[] args){
//    	System.out.println("Response - " + CodeGenerationHelper.generateClass("d:/Archives/examples/CreditCardApplication", "d:/Archives/examples/CreditCardApplication/bin/CreditCardApplication.ear", "d:/Archives/examples/CreditCardApplication/deployment", "/Virtual_RF/Applicant_Simple.rulefunctionimpl", "decisiondata", "C:/tibco/BE5.1/V60/be/5.1/lib/ext/tibco/TIBCOxml.jar;C:/tibco/BE5.1/V60/be/5.1/lib/cep-common.jar;C:/tibco/BE5.1/V60/be/5.1/lib/cep-kernel.jar;C:/tibco/BE5.1/V60/be/5.1/lib/be-functions.jar;C:/tibco/BE5.1/V60/be/5.1/studio/eclipse/plugins/com.tibco.cep.studio.rms.core_5.1.0.jar;", "C:/tibco/BE5.1/V60/be/5.1/studio/bin/studio-tools"));
//    	System.out.println("Response - " + CodeGenerationHelper.generateClass("C:/tibco/BE5.1/V511_V21/be/5.1/examples/standard/WebStudio/NQ3Conversation", "C:/tibco/BE5.1/V511_V21/be/5.1/examples/standard/WebStudio/NQ3Conversation.ear", "C:/tibco/BE5.1/V511_V21/be/5.1/examples/standard/WebStudio", "/RuleFunctions/VRF/Auto/CoverageCombinations/Policy/InvalidChkDT.rulefunctionimpl", "C:/tibco/BE5.1/V511_V21/be/5.1/examples/standard/WebStudio/NQ3Conversation/lib/BECommonUtilities.jar;C:/tibco/BE5.1/V511_V21/be/5.1/examples/standard/WebStudio/NQ3Conversation/lib/fw-core-3.7.0.jar", "C:/tibco/BE5.1/V511_V21/be/5.1/studio/bin/studio-tools", false));
    	System.out.println("Response - " + CodeGenerationHelper.generateClass("/Users/vpatil/Installations/tibco/BE5.5.0/HF06_Test__02_21_19/be/5.5/examples/standard/WebStudio/CreditCardApplication", "/Users/vpatil/Installations/tibco/BE5.5.0/HF06_Test__02_21_19/be/5.5/examples/standard/WebStudio/CreditCardApplication.ear", "/Users/vpatil/Installations/tibco/BE5.5.0/HF06_Test__02_21_19/be/5.5/examples/standard/WebStudio/classes", "/Virtual_RF/Applicant_Simple.rulefunctionimpl", null, false));
    }
}
