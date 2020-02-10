package com.tibco.cep.studio.tester.core.cli;

import static com.tibco.cep.studio.tester.core.utils.TesterCoreUtils.processTestResult;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.IStreamListener;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamMonitor;
import org.eclipse.debug.core.model.IStreamsProxy;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.studio.cli.studiotools.Messages;
import com.tibco.cep.studio.cli.studiotools.StudioCommandLineInterpreter;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexBuilder;
import com.tibco.cep.studio.debug.core.launch.IStudioDebugLaunchConfigurationConstants;
import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.debug.input.AbstractVmResponseTask;
import com.tibco.cep.studio.debug.input.DebugTestDataInputTask;
import com.tibco.cep.studio.debug.input.TesterInputTask;
import com.tibco.cep.studio.tester.core.model.EntityType;
import com.tibco.cep.studio.tester.core.model.ExecutionObjectType;
import com.tibco.cep.studio.tester.core.model.ObjectFactory;
import com.tibco.cep.studio.tester.core.model.OntologyObjectType;
import com.tibco.cep.studio.tester.core.model.OperationObjectType;
import com.tibco.cep.studio.tester.core.model.OperationType;
import com.tibco.cep.studio.tester.core.model.TesterResultsDatatype;
import com.tibco.cep.studio.tester.core.model.TesterResultsOperation;
import com.tibco.cep.studio.tester.core.model.TesterResultsType;
import com.tibco.cep.studio.tester.core.utils.TestDataAsserter;
import com.tibco.cep.studio.tester.core.utils.TesterCoreUtils;



/**
 * 
 * @author sasahoo
 *
 */
public class AssertTestDataCLI extends AbstractTesterCLI implements IDebugEventSetListener {

	private final static String OPERATION_ASSERT = "assert";
	private final static String BE_LAUNCH_CONFIGURATION_TYPE = "com.tibco.cep.studio.debug.core.ApplicationLaunch"; //$NON-NLS-1$
	private String BE_PROCESSING_UNIT_NAME = "default"; //$NON-NLS-1$
	private String BE_DEBUGGER_LAUNCH_INSTANCE_NAME = "Tester_New"; //$NON-NLS-1$
	private String BE_TESTER_SESSSION = "default"; //$NON-NLS-1$
	private String BE_TESTER_TEMP_DIR = "TesterCliTempTestData18072013";
	
	private Map<String, String> testDataFiles =  new HashMap<String, String>();
	private Map<String, String> entityURIMap = new HashMap<String, String>();
	
	private CountDownLatch startLatch;
	private CountDownLatch responseLatch;
	private CountDownLatch terminateLatch;
	
	private AbstractVmResponseTask task;
	private String projectName;
	
	private boolean html;
	private boolean overwrite;
	
	private String outputName;
	private String outputPath;
	private String outputFormat;
	
	protected final static String FLAG_HELP 			= "-h"; 	// Prints help
	protected final static String FLAG_PROJECTPATH		= "-p"; 	// Path of the Studio project
	protected final static String FLAG_CDD_PATH 		= "-c"; 	// Tester cdd file path
	protected final static String FLAG_EAR_PATH 		= "-r"; 	// Tester EAR file path
	protected final static String FLAG_WORKING_DIR 		= "-w";
	protected final static String FLAG_PROCESSING_UNIT 	= "-u"; 	// Tester Processing unit
	protected final static String FLAG_OUTPUTFORMAT		= "-f"; 	// Output format	
	protected final static String FLAG_OUTPUTPATH 		= "-o"; 	// Output Directory Path
	protected final static String FLAG_OUTPUTFILE 		= "-n"; 	// Output Directory Path
	protected final static String FLAG_OVERWRITE 		= "-x"; 	// Overwrite output files if is exists
	protected final static String FLAG_INPUTDIRECTORY 	= "-i"; 	// Input directory path
	
	
	public static void main(String[] args) throws Exception {
		if (args.length == 0 || 
				(args.length == 1 && args[0].trim().endsWith("help"))) {
			StudioCommandLineInterpreter.printPrologue(new String[]{});
			System.out.println(new AssertTestDataCLI().getHelp());
			//System.exit(-1);
			return;
		}
		String newArgs[] = new String[args.length+2];
		newArgs[0] = OPERATION_CATEGORY_TESTER;
		newArgs[1] = OPERATION_ASSERT; 
		System.arraycopy(args, 0, newArgs, 2, args.length);
		StudioCommandLineInterpreter.executeCommandLine(newArgs);
	}

	@Override
	public String[] getFlags() {
		return new String[] {   FLAG_HELP,
							FLAG_PROJECTPATH, 
							FLAG_CDD_PATH,
							FLAG_EAR_PATH,
							FLAG_WORKING_DIR,
							FLAG_PROCESSING_UNIT,
							FLAG_OUTPUTFORMAT,
							FLAG_OUTPUTPATH,
							FLAG_OUTPUTFILE,
							FLAG_OVERWRITE};
	}

	@Override
	public String getOperationFlag() {
		return OPERATION_ASSERT;
	}

	@Override
	public String getOperationName() {
		return ("Assert Test Data");
	}

	@Override
	public String getUsageFlags() {
		return ("[" + FLAG_HELP + "] " +
				 FLAG_PROJECTPATH + " <projectDir> "+
				 FLAG_CDD_PATH + " <cddFile> " +
				 FLAG_EAR_PATH + " <earFile> " +
				 FLAG_WORKING_DIR + " <workingDir> " +
				"[" + FLAG_PROCESSING_UNIT + " <processingUnit> "  + "] " +
				"[" + FLAG_OUTPUTFORMAT + "  < ops | type >" + "] " +
				"[" + FLAG_OUTPUTPATH + "  <outputDirectory>" + "] " +
				"[" + FLAG_OUTPUTFILE + "  <filename>" + "] " +
				"[" + FLAG_INPUTDIRECTORY + " <input path>" + "] " + 
				"[" + FLAG_OVERWRITE + "] "
				);	
	}

	@Override
	public String getHelp() {
		String helpMsg = "Usage: assertTestData " + getUsageFlags() + "\n" +
				"where, \n" +
				"	-h (optional) prints this usage \n" +
				"	-p specifies project path \n" +
				"	-c specifies cdd file path \n" +
				"	-r specifies ear file path \n" +
				"	-w specifies working directory path \n" +
				"	-u (optional) specifies processing unit name \n" +
				"	-f (optional) Specifies the output format. Acceptable values are 'ops' and 'type'. \n"+
				"	-i (optional) Input directory path." +
				"	-o (optional) specifies the output directory. If not specified, the output directory will be project directory/results.\n" +
				"	-n (optional) Name of the resultFile. If not specified then it would create a file with name Run-#.resultdata \n"+
				"	-x (optional) overwrites the specified output directory if it exists. \n" ;
        return helpMsg;
	}

	@Override
	public boolean runOperation(Map<String, String> argsMap) throws Exception {
		if (checkIfExcludeOperation(argsMap)) {
			return true;
		}
		
		if (argsMap.containsKey(FLAG_HELP)) {
			System.out.println(getHelp());
			return true;
		}
		
		if (argsMap.containsKey(FLAG_OVERWRITE)) {
			overwrite = true;
		}

		String projectPath = argsMap.get(FLAG_PROJECTPATH);
		if (projectPath == null) {
			throw new Exception(Messages.getString("BuildEar.ProjectPath.Invalid"));
		}
		File studioProjDir = new File(projectPath);
		if (!studioProjDir.exists()) {
			throw new Exception(Messages.getString("BuildEar.ProjectPath.NotFound"));
		}
		
		StudioProjectConfiguration spc = IndexBuilder.getProjectConfig(studioProjDir);
		projectName = spc.getName();

		String cddFilePath = argsMap.get(FLAG_CDD_PATH);
		if (cddFilePath == null) {
			throw new Exception(Messages.getString("asserttestdata.cdd.error"));
		}
		File cddFile = new File(cddFilePath);
		if (!cddFile.exists()) {
			throw new Exception(Messages.getString("asserttestdata.cdd.error"));
		}
		
		String earFilePath = argsMap.get(FLAG_EAR_PATH);
		if (earFilePath == null) {
			throw new Exception(Messages.getString("asserttestdata.ear.error"));
		}
		File earFile = new File(earFilePath);
		if (!earFile.exists()) {
			throw new Exception(Messages.getString("asserttestdata.ear.error"));
		}
		
		String workDirPath = argsMap.get(FLAG_WORKING_DIR);
		if (workDirPath == null) {
			throw new Exception(Messages.getString("asserttestdata.working.dir.invalid"));
		}
		File workDir = new File(workDirPath);
		if (!workDir.exists()) {
			throw new Exception(Messages.getString("asserttestdata.working.dir.invalid"));
		}
		
		String puName = argsMap.get(FLAG_PROCESSING_UNIT);
		if (puName != null) {
			BE_PROCESSING_UNIT_NAME = puName;
		}
		
		
		String inputDirPath = argsMap.get(FLAG_INPUTDIRECTORY);
		boolean useExternalInputDir = false;
		if(inputDirPath != null && new File(inputDirPath).exists()){
			useExternalInputDir = true;
		}
		
				
		outputPath = argsMap.get(FLAG_OUTPUTPATH);
		if(outputPath==null){
			outputPath = projectPath + "/results";
		}
		
		outputName = argsMap.get(FLAG_OUTPUTFILE);
		if(outputName==null){
			outputName = findOutputName(outputPath);
		}
		
		if(!overwrite){
			if(new File(outputPath+"/"+outputName+".resultdata").exists()){
				System.out.println("Output file already exists");
				return false;
			}
		}
		
		outputFormat = argsMap.get(FLAG_OUTPUTFORMAT);
		
		
		this.startLatch = new CountDownLatch(1);
		this.responseLatch = new CountDownLatch(1);
		this.terminateLatch = new CountDownLatch(1);

		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		DebugPlugin.getDefault().addDebugEventListener(this);
		ILaunchConfigurationType type = manager.getLaunchConfigurationType(BE_LAUNCH_CONFIGURATION_TYPE);
		ILaunchConfigurationWorkingCopy configuration = type.newInstance(null, BE_DEBUGGER_LAUNCH_INSTANCE_NAME);
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_PROJECT_NAME, projectName);
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_VM_ARGS, "");
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_CMD_STARTUP_OPTS, "");
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_CDD_FILE, cddFilePath);
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_UNIT_NAME, BE_PROCESSING_UNIT_NAME);
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_WORKING_DIR, workDirPath);
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_EAR_FILE, earFilePath);
		configuration.setAttribute(IStudioDebugLaunchConfigurationConstants.ATTR_CLASSPATH_CHANGE, false);

		
		IWorkspaceDescription wdesc= ResourcesPlugin.getWorkspace().getDescription(); 
		boolean isAutoBuilding= wdesc.isAutoBuilding(); 
		if (isAutoBuilding) { 
			wdesc.setAutoBuilding(false); 
			ResourcesPlugin.getWorkspace().setDescription(wdesc); 
		} 
		
		IProject eclipseProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		IProjectDescription desc = ResourcesPlugin.getWorkspace().newProjectDescription(eclipseProject.getName()); 
		desc.setLocation(new Path(projectPath));
	
		if (!eclipseProject.exists()) {
			eclipseProject.create(desc, null); 
		}
		if (!eclipseProject.isOpen()) {
			eclipseProject.open(null);
		}

		ILaunchConfiguration lc = configuration.doSave();
		ILaunch launch = lc.launch(ILaunchManager.RUN_MODE, new NullProgressMonitor());
		IRuleRunTarget debugTarget = (IRuleRunTarget) launch.getDebugTarget();
		
		if(useExternalInputDir){
			collectTestDataResourcesFromExternalPath(eclipseProject, projectPath, inputDirPath);
		}else{
			collectTestDataResources(eclipseProject, projectPath, "/TestData");
		}

		IProcess p = debugTarget.getProcess();
		IStreamsProxy sp = p.getStreamsProxy();
		sp.getOutputStreamMonitor().addListener(new IStreamListener() {

			@Override
			public void streamAppended(String text, IStreamMonitor monitor) {
				System.out.print(text);
				System.out.flush();

			}
		});
		startLatch.await();
		Map<String,String> rsMap = debugTarget.getRuleSessionMap();
		String ruleSession = rsMap.keySet().iterator().next();

		TestDataAsserter asserter = new TestDataAsserter(debugTarget, BE_TESTER_SESSSION, ruleSession, testDataFiles, debugTarget.getClusterName(), entityURIMap, "/TestData");
		asserter.deployTestDataFiles(null);
		asserter.doAssert(null, debugTarget);

		responseLatch.await();
		debugTarget.shutdown();
		terminateLatch.await();
		
		if(useExternalInputDir){
			eclipseProject.getFolder(BE_TESTER_TEMP_DIR).delete(true, null);
		}
		
		System.out.println("Test Data Assert Finished");

		return true;
	}
	
	@Override
	public void handleDebugEvents(DebugEvent[] events) {
		for (int i = 0; i < events.length; i++) {
			DebugEvent event = events[i];
			if (event.getSource() instanceof IRuleRunTarget) {
				if (event.getKind() == DebugEvent.CREATE) {
					if (event.getSource() instanceof IRuleRunTarget) {
//						final IRuleRunTarget target = (IRuleRunTarget) event.getSource();
						startLatch.countDown();
					}
				} else if(event.getKind() == DebugEvent.TERMINATE) {
					if(event.getSource() instanceof IRuleRunTarget){
//						final IRuleRunTarget target = (IRuleRunTarget) event.getSource();
						terminateLatch.countDown();
					}
				} else if(event.getKind() == DebugEvent.MODEL_SPECIFIC) {
					if(event.getSource() instanceof IRuleRunTarget){
						if(event.getDetail() == IRuleRunTarget.DEBUG_TASK_REPONSE ) {
							try {
								Object data = event.getData();
								if (data instanceof TesterInputTask || data instanceof DebugTestDataInputTask) {
									if (data instanceof TesterInputTask) {
										task = (TesterInputTask) data;
									}
									if (data instanceof DebugTestDataInputTask) {
										task = (DebugTestDataInputTask) data;
									}
									if (task.hasResponse()) {
										Object response = task.getResponse();
										TesterResultsType testerResultsType = processTestResult(response, AssertTestDataCLI.class.getName());
										testerResultsType.setProject(projectName);
										if(outputFormat==null){
											TesterCoreUtils.saveTestResultCli(outputPath, outputName, testerResultsType);
										}else if(outputFormat.equals("ops")){
											TesterResultsOperation tops = sortTestResultsByOperation(testerResultsType);
											TesterCoreUtils.saveTestResultCli(outputPath, outputName, tops);
										}else if(outputFormat.equals("type")){
											TesterResultsDatatype tdt = sortTesterResultsDatatype(testerResultsType);
											TesterCoreUtils.saveTestResultCli(outputPath, outputName, tdt);
										}else{
											TesterCoreUtils.saveTestResultCli(outputPath, outputName, testerResultsType);
										}
									}
								}
							}  catch (final Exception e) {
								e.printStackTrace();
							}
							responseLatch.countDown();
						}
					}
				}
			}
		}
	}
	
	
	private TesterResultsDatatype sortTesterResultsDatatype (TesterResultsType testerResultsType){
		ObjectFactory obf = new ObjectFactory();
		TesterResultsDatatype testerResultsDatatype = obf.createTesterResultsDatatype();
		testerResultsDatatype.setRunName(testerResultsType.getRunName());
		testerResultsDatatype.setProject(testerResultsType.getProject());
		Map<String,OntologyObjectType> objectMap = new HashMap<String,OntologyObjectType>();
		
		List<ExecutionObjectType> executionObjectList = testerResultsType.getExecutionObject();
		
		for(ExecutionObjectType executionObject : executionObjectList){
			
			EntityType type =  executionObject.getReteObject().getConcept();
			if(type==null){
				type = executionObject.getReteObject().getEvent();
			}
			String ns = null;
			if (type != null) {
				ns = type.getNamespace();
			} else if (executionObject.getReteObject().getRule() != null) {
				ns = executionObject.getReteObject().getRule().getNamespace();
			}
			if(ns!=null){
				String dataType = ns.replace(TesterCoreUtils.ENTITY_NS ,"");
				
				OntologyObjectType ot = objectMap.get(dataType);
				if(ot==null){
					ot = obf.createOntologyObjectType();
					ot.setDataType(dataType);
					objectMap.put(dataType, ot);
		
					OperationObjectType createdOperationObject = obf.createOperationObjectType();
					createdOperationObject.setOperation(OperationType.CREATED);
					ot.getOperationObject().add(0,createdOperationObject);
					
					OperationObjectType modifiedOperationObject = obf.createOperationObjectType();
					modifiedOperationObject.setOperation(OperationType.MODIFIED);
					ot.getOperationObject().add(1,modifiedOperationObject);
					
					OperationObjectType deletedOperationObject = obf.createOperationObjectType();
					deletedOperationObject.setOperation(OperationType.DELETED);
					ot.getOperationObject().add(2,deletedOperationObject);
					
					OperationObjectType execOperationObject = obf.createOperationObjectType();
					execOperationObject.setOperation(OperationType.RULEEXECUTED);
					ot.getOperationObject().add(3,execOperationObject);
					
					testerResultsDatatype.getOntologyObject().add(ot);
				}
				
				
				switch(executionObject.getReteObject().getChangeType()){
				case ASSERT:
					ot.getOperationObject().get(0).getExecutionObject().add(executionObject);
					break;
					
				case MODIFY:
					ot.getOperationObject().get(1).getExecutionObject().add(executionObject);
					break;
					
				case RETRACT:
					ot.getOperationObject().get(2).getExecutionObject().add(executionObject);
					break;
				default:
					
				}
				
			}
			
		
		}
		
		return testerResultsDatatype;
	}
	
	private TesterResultsOperation sortTestResultsByOperation(TesterResultsType testerResultsType){
		ObjectFactory obf = new ObjectFactory();
		TesterResultsOperation  testerResultsOperation =  obf.createTesterResultsOperation();
		testerResultsOperation.setRunName(testerResultsType.getRunName());
		testerResultsOperation.setProject(testerResultsType.getProject());
		
		OperationObjectType createdOperationObject = obf.createOperationObjectType();
		createdOperationObject.setOperation(OperationType.CREATED);
		
		OperationObjectType modifiedOperationObject = obf.createOperationObjectType();
		modifiedOperationObject.setOperation(OperationType.MODIFIED);
		
		OperationObjectType deletedOperationObject = obf.createOperationObjectType();
		deletedOperationObject.setOperation(OperationType.DELETED);
		
		OperationObjectType execOperationObject = obf.createOperationObjectType();
		execOperationObject.setOperation(OperationType.RULEEXECUTED);
		
		
		List<ExecutionObjectType> executionObjectList = testerResultsType.getExecutionObject();
				
		for(ExecutionObjectType executionObject : executionObjectList){
			
			switch(executionObject.getReteObject().getChangeType()){
				case ASSERT:
					createdOperationObject.getExecutionObject().add(executionObject);
					break;

				case MODIFY:
					modifiedOperationObject.getExecutionObject().add(executionObject);
					break;
				
				case RETRACT:
					deletedOperationObject.getExecutionObject().add(executionObject);
					break;

				case RULEEXECUTION:
				case RULEFIRED:
					execOperationObject.getExecutionObject().add(executionObject);
					break;
				default:
					
			}
		}
		
		testerResultsOperation.getOperationObject().add(createdOperationObject);
		testerResultsOperation.getOperationObject().add(modifiedOperationObject);
		testerResultsOperation.getOperationObject().add(deletedOperationObject);
		testerResultsOperation.getOperationObject().add(execOperationObject);
		return testerResultsOperation;
	}
	
	
	private void collectTestDataResources(IProject project, String projectPath, String relTestDataDir) throws Exception {
		TestDataResourceVisitor visitor = new TestDataResourceVisitor();
		project.accept(visitor);
		List<DesignerElement> list = CommonIndexUtils.getAllElements(project.getName(), new ELEMENT_TYPES[]{ELEMENT_TYPES.CONCEPT, ELEMENT_TYPES.SCORECARD, ELEMENT_TYPES.SIMPLE_EVENT});
		List<IFile> files = visitor.getTestDataFiles();
		for (IFile file :  files) {
			String destination = "";
			String p = "/" + project.getName() +  relTestDataDir;
			String fPath = file.getFullPath().toPortableString().replace(p, "");
			String path = fPath.replace(CommonIndexUtils.DOT + file.getFileExtension(), "");
			for (DesignerElement element : list) {
				Entity entity = ((EntityElement)element).getEntity();
				if (entity.getFullPath().equals(path)) {
					entityURIMap.put(file.getLocation().toString(), fPath);
					if (file.getFileExtension().equals("eventtestdata") & entity instanceof SimpleEvent) {
						SimpleEvent event = (SimpleEvent)entity;
						if (event.getDestination() != null) {
							StringBuilder sb = new StringBuilder();
							destination = sb.append(event.getChannelURI()).append("/").append(event.getDestinationName()).toString();
						}
					} 
				}
			}
			testDataFiles.put(file.getLocation().toString(), destination);
		}
		
	}
	
	private void collectTestDataResourcesFromExternalPath(IProject project, String projectPath, String externaldir) throws Exception {
		
		IPath extDirPath = new Path(externaldir);
		if(new File(externaldir).isDirectory()){
			IFolder folder = project.getFolder(BE_TESTER_TEMP_DIR);
			folder.createLink(extDirPath, IResource.REPLACE, null);
			collectTestDataResources(project, projectPath,"/TempTestData");
		}else{
			IFile file = project.getFile(extDirPath.lastSegment());
			file.createLink(extDirPath, IResource.NONE, null);
			//To-Do: Process external file inputs
		}
		
		
	}
	
	
	
	public static class TestDataResourceVisitor implements IResourceVisitor {
		
		private List<IFile> testDataResources;

		public TestDataResourceVisitor() {
			testDataResources = new ArrayList<IFile>();
		}

		public List<IFile> getTestDataFiles() {
			return new ArrayList<IFile>(testDataResources);
		}

		@Override
		public boolean visit(IResource resource) throws CoreException {
			
			if (resource instanceof IProject) {
				return true;
			}

			if (resource instanceof IFolder) {
				return true;
			}
			
			if (resource instanceof IFile) {
				IFile file = (IFile) resource;
				if (file.getFileExtension().equals("concepttestdata")
						|| file.getFileExtension().equals("scorecardttestdata")
						|| file.getFileExtension().equals("eventtestdata")) {
					testDataResources.add(file);
				}
				return true;
			}
			
			return false;
		}

	}

	private String findOutputName(String outputpath){
		String fileName = null;
		File dir = new File(outputpath);
		if (!dir.exists()) {
			fileName = "run-1";
		}else{
			int i=1;
			fileName = "run-" + i;
			while(new File(outputpath+"/"+fileName+".resultdata").exists()){
				i++;
				fileName = "run-" + i;
			}
			
		}
		return fileName;
	}
	
}
