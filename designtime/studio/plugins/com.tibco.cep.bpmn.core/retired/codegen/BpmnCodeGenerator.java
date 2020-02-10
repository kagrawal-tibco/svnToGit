package com.tibco.cep.bpmn.core.codegen;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.cep.bpmn.core.BpmnCoreConstants;
import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.Messages;
import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.model.designtime.ontology.BpmnIndex;
import com.tibco.cep.bpmn.model.designtime.ontology.impl.DefaultBpmnIndex;
import com.tibco.cep.bpmn.model.designtime.ontology.symbols.RootSymbolMap;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.studio.common.configuration.BpmnProcessSettings;
import com.tibco.cep.studio.core.repo.emf.StudioEMFProject;
import com.tibco.cep.studio.core.util.packaging.impl.DefaultRuntimeClassesPackager;
import com.tibco.cep.studio.parser.codegen.CodeGenContext;

public class BpmnCodeGenerator implements BaseGenerator{
	protected CodeGenContext context;
	protected IProgressMonitor monitor = new NullProgressMonitor();
	protected IPath outputFolderPath;
	protected DefaultRuntimeClassesPackager packager;
	protected File tmpDir;
	protected File srcDir;
	protected File jarFile;
	protected IFolder[] sourceDirs;
	protected String classpath;
	private IProject project;
	private List<IPath> processes;
	private boolean overwrite;
	private BpmnProcessSettings bpmnConfig;
	private StudioEMFProject studioProject;
	
	
	public BpmnCodeGenerator(IProgressMonitor monitor, IProject project,
			List<IPath> processes, boolean overwrite) {
		this.project = project;
		this.studioProject = new StudioEMFProject(project.getName());
		if(monitor != null) {
			this.monitor = monitor;
		}
		this.processes = processes;
		this.overwrite = overwrite;
		
	}
	
	
	public void init() throws Exception {
		// TODO Auto-generated method stub
		monitor.setTaskName(Messages.getString("bpmn.build.studio.project.load")); //$NON-NLS-1$
		studioProject.load();
		this.bpmnConfig = BpmnCorePlugin.getBpmnProjectConfiguration(project.getName());
		monitor.worked(20);
		
		monitor.subTask(Messages.getString("bpmn.build.create.codegen.folder"));
//		final BEProperties props = BEProperties.loadDefault();
		setOutputFolderPath(new Path(bpmnConfig.getBuildFolder()));
		if(project.isOpen()) {
			
			IFolder directory = project.getFolder(getOutputFolderPath());
			if(overwrite &&  directory.exists()) {
				deleteFileOrDirectory(directory,monitor);
				directory.create(true,true,monitor);
			}
			
			
			final IFolder conceptDir = directory.getFolder(new Path(BpmnCoreConstants.BPMN_CODEGEN_CONCEPT_FOLDER));
			final IFolder scorecardDir = directory.getFolder(new Path(BpmnCoreConstants.BPMN_CODEGEN_SCORECARD_FOLDER));
			final IFolder eventDir = directory.getFolder(new Path(BpmnCoreConstants.BPMN_CODEGEN_EVENT_FOLDER));
			final IFolder timeEventDir = directory.getFolder(new Path(BpmnCoreConstants.BPMN_CODEGEN_TIME_EVENT_FOLDER));
			final IFolder ruleFunctionDir = directory.getFolder(new Path(BpmnCoreConstants.BPMN_CODEGEN_RULEFUNCTION_FOLDER));
			final IFolder ruleDir = directory.getFolder(new Path(BpmnCoreConstants.BPMN_CODEGEN_RULE_FOLDER));
			final IFolder constantsDir = directory.getFolder(new Path(BpmnCoreConstants.BPMN_CODEGEN_CONSTANTS_FOLDER));
			if(!eventDir.exists()) {
				eventDir.create(true, true, monitor);
			}
			if(!timeEventDir.exists()) {
				timeEventDir.create(true, true, monitor);
			}
			if(!conceptDir.exists()) {
				conceptDir.create(true, true, monitor);
			}
			if(!scorecardDir.exists()){
				scorecardDir.create(true, true, monitor);
			}
			if(!ruleFunctionDir.exists()){
				ruleFunctionDir.create(true, true, monitor);
			}
			if(!ruleDir.exists()){
				ruleDir.create(true, true, monitor);
			}
			if(!constantsDir.exists()){
				constantsDir.create(true, true, monitor);
			}
			setSourceDirs(new IFolder[] { eventDir, conceptDir, ruleDir,
					ruleFunctionDir, timeEventDir, scorecardDir });
			
			RootSymbolMap rootSymbolMap = BpmnCorePlugin.getDefault().getBpmnModelManager().getRootSymbolMap(project.getName());
			rootSymbolMap.refresh();
			CodeGenContext cgContext = new CodeGenContext();
			cgContext.put(BpmnCoreConstants.BPMN_CODEGEN_PROJECT,project);
			cgContext.put(BpmnCoreConstants.BPMN_CONFIG, bpmnConfig);
			cgContext.put(BpmnCoreConstants.BE_CODEGEN_ONTOLOGY,getOntology());
			cgContext.put(BpmnCoreConstants.BPMN_CODEGEN_ONTOLOGY,getBpmnOntology());
			cgContext.put(BpmnCoreConstants.BPMN_CODEGEN_FOLDER, directory);
			cgContext.put(BpmnCoreConstants.BPMN_CODEGEN_EVENT_FOLDER, eventDir);
			cgContext.put(BpmnCoreConstants.BPMN_CODEGEN_TIME_EVENT_FOLDER, timeEventDir);
			cgContext.put(BpmnCoreConstants.BPMN_CODEGEN_CONCEPT_FOLDER, conceptDir);
			cgContext.put(BpmnCoreConstants.BPMN_CODEGEN_SCORECARD_FOLDER, scorecardDir);
			cgContext.put(BpmnCoreConstants.BPMN_CODEGEN_RULE_FOLDER, ruleDir);
			cgContext.put(BpmnCoreConstants.BPMN_CODEGEN_RULEFUNCTION_FOLDER,ruleFunctionDir);
			cgContext.put(BpmnCoreConstants.BPMN_CODEGEN_CONSTANTS_FOLDER,constantsDir);
			cgContext.put(BpmnCoreConstants.BPMN_CODEGEN_SELECTED_PROCESSES, processes);
			cgContext.put(BpmnCoreConstants.BPMN_CODEGEN_ERROR_LIST, new LinkedList<CodegenError>());
			cgContext.put(BpmnCoreConstants.BPMN_CODEGEN_PROJECT_SYMBOL_MAP,rootSymbolMap);
			cgContext.put(BpmnCoreConstants.BPMN_CODEGEN_GEN_URI_MAP,new HashMap<String,String>());
			setContext(cgContext);
			
		}
		
		monitor.worked(1);
		

		
		
		
	}
	
	public void generate() throws Exception {
		BpmnCorePlugin.debug("Start codegen...");
		CodeGenContext ctx = getContext();
		List<IPath> spaths = (List<IPath>) ctx.get(BpmnCoreConstants.BPMN_CODEGEN_SELECTED_PROCESSES);
		Collection<EObject> procs = getBpmnOntology().getAllProcesses();
		for(EObject proc:procs) {
			EcoreUtil.resolveAll(proc);
			EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper.wrap(proc);
			IFile processFile = BpmnIndexUtils.getFile(this.project.getName(),proc);
			IPath processPath = processFile.getProjectRelativePath().makeAbsolute();
			if(spaths.contains(processPath)) {
				
				
				
				BpmnMsgArtifactsGenerator bpmnArtifactsGen = new BpmnMsgArtifactsGenerator(proc,null,ctx,monitor,overwrite);
				bpmnArtifactsGen.generate();
				Symbol msgContextSymbol = bpmnArtifactsGen.getBpmnContextSymbol();
				Symbol msgIdSymbol = bpmnArtifactsGen.getMsgIdSymbol();
				
				BpmnCorePlugin.debug("generating process:"+processPath);
				ProcessGenerator pg = GeneratorFactory.createGenerator(
												processWrapper.getEInstance(),
												bpmnArtifactsGen,
												ctx,
												monitor,
												overwrite);
				pg.generate();
				
				// generate the msg proc
				BpmnMsgProcGenerator eventProcGen 
					= new BpmnMsgProcGenerator(proc,
												bpmnArtifactsGen,
												ctx,
												monitor,
												overwrite,
												Arrays.asList(msgIdSymbol,msgContextSymbol));
				eventProcGen.generate();
//				ProcessLauncherGenerator<EClass,EObject> processLauncherGenerator = new ProcessLauncherGenerator<EClass,EObject>(ctx,monitor,overwrite);
//				processWrapper.accept(processLauncherGenerator);
				
			}
			
		}
		
		
		List<CodegenError> errors = (List<CodegenError>) ctx.get(BpmnCoreConstants.BPMN_CODEGEN_ERROR_LIST);
		for(CodegenError err:errors) {
			BpmnCorePlugin.log(err.toString());
		}
	}

	




	private BpmnIndex getBpmnOntology() {
		EObject index = BpmnIndexUtils.getIndex(this.project);
		return new DefaultBpmnIndex(index);
	}


	private Ontology getOntology() {
		return studioProject.getOntology();
	}


	@Override
	public BaseGenerator getParent() {
		return null;
	}
	
	public void setContext(CodeGenContext context) {
		this.context = context;
	}
	
	public CodeGenContext getContext() {
		return context;
	}
	
	/**
	 * @return
	 */
	public IPath getOutputFolderPath() {
		return outputFolderPath;
	}
	
	/**
	 * @param outputFolderPath
	 */
	public void setOutputFolderPath(IPath outputFolderPath) {
		this.outputFolderPath = outputFolderPath;
	}
	
	public void setSourceDirs(IFolder[] sourceDirs) {
		this.sourceDirs = sourceDirs;
	}
	
	public IFolder[] getSourceDirs() {
		return sourceDirs;
	}
	
	 public static void deleteFileOrDirectory(IResource f,IProgressMonitor monitor) throws CoreException {
	    	if(f == null) 
	    		return;
	        if (f instanceof IFolder) {
	            final IResource[] files = ((IFolder)f).members();
	            for (int i = 0; i < files.length; i++) {
	                deleteFileOrDirectory(files[i],monitor);
	            }//for
	        }//if
	        f.delete(true,monitor);
	 }//deleteFileOrDirectory
	
	
	

}
