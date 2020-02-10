package com.tibco.cep.studio.cli.studiotools;

import java.util.Map;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

public class StudioToolsApplication implements IApplication {

	@Override
	public Object start(IApplicationContext context) throws Exception {
		String[] args = (String[]) context.getArguments().get("application.args");

		ArgumentParser argParser = new ArgumentParser();
		boolean success = argParser.parseArguments(args);
		if (!success) {
			StudioCommandLineInterpreter.printPrologue(args);
			StudioCommandLineInterpreter.printUsage();
			return StudioCommandLineInterpreter.CLI_ERROR;
		}
		Map<String, String> argsMap = argParser.getArgsMap();
//		try {
//			if (argsMap.get(ConvertToStudioProjectCLI.OPERATION_CATEGORY_CORE)
//					.equalsIgnoreCase(ConvertToStudioProjectCLI.OPERATION_CONVERT_TO_STUDIO_PROJECT)) {
//				if (!argsMap.keySet().contains(ConvertToStudioProjectCLI.OPERATION_WORKSPACE_LOCATION)) {
//					StudioCommandLineInterpreter.printPrologue(args);
//					StudioCommandLineInterpreter.printUsage();
//					return StudioCommandLineInterpreter.CLI_ERROR;
//				}
//				String w = argsMap.get(ConvertToStudioProjectCLI.OPERATION_WORKSPACE_LOCATION).toString();
//				Location instanceLoc = Platform.getInstanceLocation();
//				instanceLoc.set(new URL("file", null, w), false);
//			}
//		}
//		catch (Exception err) {
//			System.out.println(err.getMessage());
//		}
		return StudioCommandLineInterpreter.main(args, argsMap);
	}

	@Override
	public void stop() {
		System.out.println("Stopping Studio Tools Application");
	}

}
