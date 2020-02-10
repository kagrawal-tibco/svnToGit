package com.tibco.cep.studio.cli.studiotools;

import java.io.File;
import java.util.Map;

import com.tibco.cep.studio.core.util.CoherenceFunctionMigrator;

/*
@author rhollom
 */
public class MigrateCoherenceCallsCLI extends CoreCLI {

	// Migrate Coherence Function Calls
	private final static String OPERATION_MIGRATE_FN_CALLS = "migrateCoherenceCalls";
	private final static String FLAG_IMPORT_STUDIO_PROJECT = "-p";	// Path of project in Studio
	
	public MigrateCoherenceCallsCLI() {
	}
	
	@Override
	public String[] getFlags() {
		return new String[] { FLAG_IMPORT_STUDIO_PROJECT };
	}
	
	@Override
	public String getHelp() {
		String helpMsg = "Usage: studio-tools " + getOperationCategory() + " " + getOperationFlag() + " " + getUsageFlags() + "\n" +
			"where \n" +
			"   -p is the absolute path to the directory of the Studio project to be migrated \n";
		return helpMsg;
	}
	
	@Override
	public String getOperationFlag() {
		return OPERATION_MIGRATE_FN_CALLS;
	}
	
	@Override
	public String getOperationName() {
		return ("Migrate Coherence Function Calls");
	}
	
	public String getUsageFlags() {
		return (FLAG_IMPORT_STUDIO_PROJECT + " <studioProjDir>");
	}
	
	@Override
	public boolean runOperation(Map<String, String> argsMap) throws Exception {
		if (checkIfExcludeOperation(argsMap))
			return true;

		String studioProj = argsMap.get(FLAG_IMPORT_STUDIO_PROJECT);
		if (studioProj == null) {
			throw new Exception(Messages.getString("Import.ProjectPath.Invalid"));
		}

		File studioProjDir = new File(studioProj);
		if (!studioProjDir.exists()) {
			throw new Exception(Messages.getString("Import.ProjectPath.Invalid"));
		}
		
		try {
			new CoherenceFunctionMigrator().migrateFunctionCalls(studioProjDir);
			System.out.println("\n" + Messages.getString("Migrate.Success"));
		} catch (Exception e) {
			throw new Exception(Messages.getString("Migrate.Error") + " - " + e.getMessage(), e);
		}
		return true;
	}

}
