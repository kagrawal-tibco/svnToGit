package com.tibco.cep.studio.cli.studiotools;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;

import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.XPATH_VERSION;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.utils.IndexBuilder;
import com.tibco.cep.studio.core.migration.IStudioProjectMigrationContext;
import com.tibco.cep.studio.core.migration.StudioProjectMigrationContext;
import com.tibco.cep.studio.core.nature.StudioProjectNature;
import com.tibco.cep.studio.core.util.MapperFunctionMigrator;
import com.tibco.xml.mappermodel.emfapi.EMapperUtilities;

/*
@author rhollom
 */
public class MigrateMapperFunctionsCLI extends CoreCLI {

	// Migrate Coherence Function Calls
	private final static String OPERATION_MIGRATE_FN_CALLS = "migrateMapperFunctions";
	private final static String FLAG_IMPORT_STUDIO_PROJECT = "-p";	// Path of project in Studio
	private final static String FLAG_UPDATE_VERSIONS = "-v";	// Update the version strings in XSLT
	
	public MigrateMapperFunctionsCLI() {
	}
	
	@Override
	public String[] getFlags() {
		return new String[] { FLAG_IMPORT_STUDIO_PROJECT, FLAG_UPDATE_VERSIONS };
	}
	
	@Override
	public String getHelp() {
		String helpMsg = "Usage: studio-tools " + getOperationCategory() + " " + getOperationFlag() + " " + getUsageFlags() + "\n" +
			"where \n" +
			"   -p is the absolute path to the directory of the Studio project to be migrated \n"+
			"   -v (optional) will update the version from 1.0 to 2.0 in all XSLT strings\n";
		return helpMsg;
	}
	
	@Override
	public String getOperationFlag() {
		return OPERATION_MIGRATE_FN_CALLS;
	}
	
	@Override
	public String getOperationName() {
		return ("Migrate Mapper Function Calls");
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
		
		StudioProjectConfiguration projectConfig = IndexBuilder.getProjectConfig(studioProjDir);
		if (projectConfig == null) {
			throw new IllegalArgumentException(Messages.getString("BuildLibrary.ProjectPath.Invalid"));
		}
		if (projectConfig.getXpathVersion() == XPATH_VERSION.XPATH_10) {
			System.out.println(Messages.formatMessage("Migrate.Mapper.XPath10", studioProj));
			return true; // no need to migrate
		}
		IndexBuilder builder = new IndexBuilder(studioProjDir);
		DesignerProject index = builder.loadProject();
		if (index == null) {
			System.err.println(Messages.formatMessage("Migrate.Mapper.Index.Error", studioProj));
		}
		StudioProjectConfigurationManager.getInstance().getConfigurationsCache().put(studioProjDir.getName(), projectConfig);
		StudioProjectCache.getInstance().putIndex(index.getName(), index);
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectConfig.getName());
		if (!project.exists()) {
			IProjectDescription description = ResourcesPlugin.getWorkspace().newProjectDescription(projectConfig.getName());
			Path path = new Path(studioProjDir.getAbsolutePath());
			description.setLocation(path);
			String[] newNatures = new String[1];
			newNatures[0] = StudioProjectNature.STUDIO_NATURE_ID;
			
			description.setNatureIds(newNatures);
			project.create(description, new NullProgressMonitor()); 
			project.open(new NullProgressMonitor());
		}

		if (!project.isOpen()) {
			project.open(new NullProgressMonitor());
		}
		try {
			Class<?> custFuncClass = Class.forName("com.tibco.xml.cxf.functionlist.CustomXPathFunctionsList");
			Method method = custFuncClass.getMethod("getCustomFuncs");
			List<Map<String, Object>> customFuncs = (List<Map<String, Object>>) method.invoke(custFuncClass);
			EMapperUtilities.setCustomFunctions(customFuncs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			boolean updateVersions = argsMap.containsKey(FLAG_UPDATE_VERSIONS);

			IStudioProjectMigrationContext context = new StudioProjectMigrationContext(studioProjDir, XPATH_VERSION.XPATH_20);
			MapperFunctionMigrator migrator = new MapperFunctionMigrator();
			migrator.setUpdateVersions(updateVersions);
			migrator.migrateProject(context, new NullProgressMonitor());
//			new MapperFunctionMigrator().migrateFunctionCalls(projectConfig.getName(), studioProjDir);
			System.out.println("\n" + Messages.getString("Migrate.Mapper.Success"));
		} catch (Exception e) {
			throw new Exception(Messages.getString("Migrate.Mapper.Error") + " - " + e.getMessage(), e);
		}
		return true;
	}

}
