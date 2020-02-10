package com.tibco.cep.studio.tester.core.cli;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.cli.studiotools.Messages;
import com.tibco.cep.studio.cli.studiotools.StudioCommandLineInterpreter;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexBuilder;
import com.tibco.cep.studio.tester.core.utils.TestDataCsvExportHandler;
import com.tibco.cep.studio.tester.core.utils.TestDataExcelExportHandler;

/**
 * 
 * @author sasahoo
 *
 */
public class GenerateTestDataTemplatesCLI extends AbstractTesterCLI {

	private final static String OPERATION_GENERATE_TEMPLATE = "generate";
	
	protected final static String FLAG_HELP 			= "-h"; 	// Prints help
	protected final static String FLAG_OUTPUTPATH 		= "-o";		// The output path where test data templates need to be generated
	protected final static String FLAG_PROJECTPATH 		= "-p"; 	// Path of the Studio project
	protected final static String FLAG_OUTPUTTYPE 		= "-t";     // The the type of template file that needs to be generated csv or xls
	protected final static String FLAG_COLUMNSEPERATOR 	= "-s";		// Column Separator for output files
	protected final static String FLAG_OVERWRITE 		= "-x"; 	// Overwrite output files if is exists
	
	protected final static List<String> outputTypes 		= Arrays.asList("csv", "xls"); 	// List of supported output types
	protected final static List<String> columnSeperators  	= Arrays.asList(","); 	// List of supported column separators for csv files
	
	
	public static void main(String[] args) throws Exception {
		if (args.length == 0 || 
				(args.length == 1 && args[0].trim().endsWith("help"))) {
			StudioCommandLineInterpreter.printPrologue(new String[]{});
			System.out.println(new GenerateTestDataTemplatesCLI().getHelp());
			//System.exit(-1);
			return;
		}
		String newArgs[] = new String[args.length+2];
		newArgs[0] = OPERATION_CATEGORY_TESTER;
		newArgs[1] = OPERATION_GENERATE_TEMPLATE; 
		System.arraycopy(args, 0, newArgs, 2, args.length);
		StudioCommandLineInterpreter.executeCommandLine(newArgs);
	}
	
	@Override
	public String[] getFlags() {
		return new String[] {   FLAG_HELP,
								FLAG_OUTPUTPATH,
								FLAG_PROJECTPATH,
								FLAG_OUTPUTTYPE,
								FLAG_COLUMNSEPERATOR,
								FLAG_OVERWRITE};
	}

	@Override
	public String getOperationFlag() {
		return OPERATION_GENERATE_TEMPLATE;
	}

	@Override
	public String getOperationName() {
		return ("Generate Tester Input Data Templates");
	}

	@Override
	public String getUsageFlags() {
		return ("[" + FLAG_HELP + "] " +
				"[" +FLAG_OUTPUTPATH + " <outputDirectory>" + "] " +
				FLAG_PROJECTPATH + " <projectDir> "+
				"[" +FLAG_OUTPUTTYPE + " < csv | xls >" + "] " +
				"[" +FLAG_OVERWRITE+ "] " 
				);	
	}

	@Override
	public String getHelp() {
		String helpMsg = "Usage: genTestDataTemplate " + getUsageFlags() + "\n" +
				"where, \n" +
				"	-h (optional) Prints this usage \n" +
				"	-o (optional) Specifies the output directory. Default value: ProjectDirectory/templates.\n"+
				"	-p Specifies project path \n" +
				"	-t (optional) Specifies the output type Supported values are < csv | xls >. Default value: csv. \n"+
				"	-x (optional) Overwrites the specified output directory if it exists. \n";
        return helpMsg;
	}

	@Override
	public boolean runOperation(Map<String, String> argsMap) throws Exception {
		if (checkIfExcludeOperation(argsMap))
			return true;
		
		if (argsMap.containsKey(FLAG_HELP)) {
			System.out.println(getHelp());
			return true;
		}
		
		boolean overwrite = false;
		if (argsMap.containsKey(FLAG_OVERWRITE)) {
			overwrite = true;
		}
		
		String projectPath = argsMap.get(FLAG_PROJECTPATH);
		if (projectPath == null) {
			throw new Exception(Messages.getString("GenerateTestDataTemplates.ProjectPath.Invalid"));
		}
		File studioProjDir = new File(projectPath);
		if (!studioProjDir.exists()) {
			throw new Exception(Messages.getString("GenerateTestDataTemplates.ProjectPath.NotFound"));
		}

		String outputStringPath = argsMap.get(FLAG_OUTPUTPATH);
		if (outputStringPath == null) {
			outputStringPath = projectPath + "/" + "TestDataTemplates";
		}
		File outputPath = new File(outputStringPath);
		if (!outputPath.exists()) {
			outputPath.mkdir();
		}
		else if(!overwrite){
			System.out.println(Messages.getString("GenerateTestDataTemplates.OutputPath.Exists"));
			return false;
		}
		
		String outputType = argsMap.get(FLAG_OUTPUTTYPE);
		if(outputType==null){
			outputType = "csv";
		}
		if(!outputTypes.contains(outputType)){
			System.out.println(Messages.getString("GenerateTestDataTemplates.InvalidOutputType"));
			return false;
		}
		
		String columnSeperator = null;
		if(outputType.equals("csv")){
			columnSeperator = argsMap.get(FLAG_COLUMNSEPERATOR);
			if(columnSeperator==null){
				columnSeperator  = "," ;
			}else if (!columnSeperators.contains(columnSeperator)){
				System.out.println(Messages.getString("GenerateTestDataTemplates.InvalidColumnSeperator"));
				return false;
			}
		}		
		
		try {
			IndexBuilder builder = new IndexBuilder(studioProjDir);
			DesignerProject index = builder.loadProject();
			
			if (index == null) {
				System.err.println(Messages.formatMessage("GenerateTestDataTemplates.Index.Error", projectPath));
				return true;
			}
			
			StudioProjectConfiguration spc = IndexBuilder.getProjectConfig(studioProjDir);
			String projectName = spc.getName();
			
			List<DesignerElement> list = CommonIndexUtils.getAllElements(index, new ELEMENT_TYPES[]{ELEMENT_TYPES.CONCEPT, 
					 ELEMENT_TYPES.SIMPLE_EVENT, ELEMENT_TYPES.SCORECARD});
			List<Entity> enlist = new ArrayList<Entity>(); 
			for (DesignerElement element: list) {
			   if (element instanceof EntityElement) {
				   Entity entity = ((EntityElement)element).getEntity();
				   if (entity instanceof Concept) {
					   enlist.add(entity);
				   } else if (entity instanceof Event) {
					   enlist.add(entity);
				   } 
			   }
			}
			if(outputType.equals("xls")){
				TestDataExcelExportHandler handler = new TestDataExcelExportHandler(projectName,projectPath,outputStringPath, enlist);
				handler.execute();
			}
			else if(outputType.equals("csv")){
				TestDataCsvExportHandler handler = new TestDataCsvExportHandler(projectName,projectPath,outputStringPath,columnSeperator,enlist);
				handler.execute();
			}else{
				System.out.println(Messages.getString("GenerateTestDataTemplates.InvalidOutputType"));
				return false;
			}
			
		} catch (Exception e) {
			throw new Exception(Messages.getString("GenerateTestDataTemplates.Error") + " - " + e.getMessage(),e);
		}
		finally{
		}
		System.out.println(Messages.getString("GenerateTestDataTemplates.Success"));
		return true;
	}
}
	