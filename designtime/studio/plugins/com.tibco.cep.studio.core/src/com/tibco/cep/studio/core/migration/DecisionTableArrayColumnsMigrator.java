/**
 * 
 */
package com.tibco.cep.studio.core.migration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.Symbols;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.migration.helper.ArrayColumnMigrationHelper;

/**
 * Migrate Array type columns from 4.0 to 5.0 format.
 * @author aathalye
 *
 */
public class DecisionTableArrayColumnsMigrator extends DefaultStudioProjectMigrator {

	/**
	 * 
	 */
	public DecisionTableArrayColumnsMigrator() {
		// TODO Auto-generated constructor stub
	}
	
	private enum ARG_ATTR_TYPE {
		ARG_PATH,
		ARG_ALIAS,
		ARG_RES_TYPE;
	}

	@Override
	protected void migrateFile(File parentFile, File file, IProgressMonitor monitor) {
		String ext = getFileExtension(file);
		if ("rulefunctionimpl".equalsIgnoreCase(ext)) {
			monitor.subTask("Attempting array style migration on table at " + file.getName());
			try {
				processTable(parentFile, file);
			} catch (Exception e) {
				StudioCorePlugin.log(e);
			}
			return;
		}
	}
	
	/**
	 * Migrate each {@link Table} such that array columns are migrated.
	 * @param parentFile
	 * @param file
	 * @throws Exception
	 */
	private void processTable(File parentFile, File file) throws Exception {
		FileInputStream fileInputStream = new FileInputStream(file);
		Table tableEModel = (Table)CommonIndexUtils.deserializeEObject(fileInputStream);
		//Read all argument paths, argument aliases, resource types etc.
		Map<ARG_ATTR_TYPE, List<String>> allArgumentRetrievals = getAllArgumentRetrievals(tableEModel);
		String[] allArgumentPaths = allArgumentRetrievals.get(ARG_ATTR_TYPE.ARG_PATH).toArray(new String[allArgumentRetrievals.size()]);
		String[] allArgumentAliases = allArgumentRetrievals.get(ARG_ATTR_TYPE.ARG_ALIAS).toArray(new String[allArgumentRetrievals.size()]);
		String[] allArgumentResourceTypes = allArgumentRetrievals.get(ARG_ATTR_TYPE.ARG_RES_TYPE).toArray(new String[allArgumentRetrievals.size()]);
		//Process all columns
		Columns decisionTableColumns = tableEModel.getDecisionTable().getColumns();
		Columns exceptionTableColumns = tableEModel.getExceptionTable().getColumns();
		
		processColumns(projectLocation.getAbsolutePath(), 
				       decisionTableColumns,
				       allArgumentPaths,
				       allArgumentAliases,
				       allArgumentResourceTypes);
		
		processColumns(projectLocation.getAbsolutePath(), 
				       exceptionTableColumns,
			           allArgumentPaths,
			           allArgumentAliases,
			           allArgumentResourceTypes);
		
		//Save table
		saveModel(file, tableEModel);
	}
	
	/**
	 * Save changed model to the appropriate file.
	 * @param resourceFile -> The table file.
	 * @param tableEModel
	 * @throws IOException
	 */
	private void saveModel(File resourceFile, Table tableEModel) throws IOException {
		String resourceLocation = resourceFile.getAbsolutePath();
		XMIResource resource = 	new XMIResourceImpl(URI.createFileURI(resourceLocation));
		resource.getContents().add(tableEModel);
		InputStream is = null;
		OutputStream os = null;
		File tmpFile = null;
		try {
			is = new FileInputStream(resourceFile);
			if (!resourceFile.getParentFile().exists()) {
				resourceFile.getParentFile().mkdirs();
			}
			tmpFile = new File(resourceFile.getAbsolutePath() + "_tmp");
			os = new FileOutputStream(tmpFile);
			resource.save(os, null);
		} catch (FileNotFoundException e) {
			StudioCorePlugin.log(e);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.flush();
					os.close();
				}
			} catch (Exception e2) {
			}
		}
		if (tmpFile != null) {
			if (resourceFile.exists()) {
				resourceFile.delete();
			}
			tmpFile.renameTo(resourceFile);
			if (tmpFile.exists()) {
				tmpFile.delete();
			}
		}
	}
	
	/**
	 * Get all arg paths, aliases, resource types.
	 * @param tableEModel
	 * @return
	 */
	private Map<ARG_ATTR_TYPE, List<String>> getAllArgumentRetrievals(Table tableEModel) {
		List<Argument> allArguments = tableEModel.getArgument();
		List<String> argAliases = new ArrayList<String>(allArguments.size());
		List<String> argPaths = new ArrayList<String>(allArguments.size());
		List<String> argResourceTypes = new ArrayList<String>(allArguments.size());
		Map<ARG_ATTR_TYPE, List<String>> allArgumentRetrievals = new EnumMap<ARG_ATTR_TYPE, List<String>>(ARG_ATTR_TYPE.class);
		
		allArgumentRetrievals.put(ARG_ATTR_TYPE.ARG_ALIAS, argAliases);
		allArgumentRetrievals.put(ARG_ATTR_TYPE.ARG_PATH, argPaths);
		allArgumentRetrievals.put(ARG_ATTR_TYPE.ARG_RES_TYPE, argResourceTypes);

		String rulefunctionFile = projectLocation.getPath() + tableEModel.getImplements() + ".rulefunction";
		RuleFunction rulefunction = CommonIndexUtils.parseRuleFunctionFile(projectLocation.getName(), rulefunctionFile);
		
		for (Argument argument : allArguments) {
			ArgumentProperty argumentProperty = argument.getProperty();
			Symbol symbol = getRuleFunctionSymbol(rulefunction, argumentProperty);
			if (symbol != null) {
				if(argumentProperty.isArray()){
					argAliases.add(symbol.getIdName() + "[]");
				}else{
					argAliases.add(symbol.getIdName());
				}
				
				argPaths.add(symbol.getType());
				argResourceTypes.add(argumentProperty.getResourceType().toString());
			}	
		}
		return allArgumentRetrievals;
	}
	
	/**
	 * 
	 * @param projectRootDirPath
	 * @param columns
	 * @param allArgumentPaths
	 * @param allArgumentAliases
	 * @param allArgumentResourceTypes
	 */
	private void processColumns(String projectRootDirPath,
			                    Columns columns, 
			                    String[] allArgumentPaths,
			                    String[] allArgumentAliases,
			                    String[] allArgumentResourceTypes) {
		if (columns == null) {
			return;
		}
		for (Column column : columns.getColumn()) {
			String massagedColumnName = null;
			if(!column.isSubstitution()){
				massagedColumnName = ArrayColumnMigrationHelper.
											getMassagedColumnName(projectRootDirPath, 
																  allArgumentPaths,
											                      allArgumentAliases,
											                      allArgumentResourceTypes,
											                      column.getName());
			} else{
				massagedColumnName = column.getName();
			}
			//Set new name
			column.setName(massagedColumnName);
		}
	}
	
	@Override
	public int getPriority() {
		return 100; 
	}
	
	private Symbol getRuleFunctionSymbol(RuleFunction rulefunction, ArgumentProperty argumentProperty) {
		Symbol symbol = null;
		if (argumentProperty != null) {
			Symbols symbols = rulefunction.getSymbols();
			for (Symbol symbol2 : symbols.getSymbolList()) {
				if (symbol2.getIdName().equalsIgnoreCase(argumentProperty.getAlias())) {
					symbol = symbol2;
					break;
				}
			}
		}	
		return symbol;
	}
}
