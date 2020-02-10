package com.tibco.cep.bpmn.core.refactoring;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMLResource.URIHandler;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.FileStatusContext;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.RefactoringStatusContext;
import org.eclipse.ltk.core.refactoring.RefactoringStatusEntry;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor;
import org.eclipse.text.edits.ReplaceEdit;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.core.index.BpmnNameGenerator;
import com.tibco.cep.bpmn.core.search.BpmnProcessSearchParticipant;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.core.utils.ECoreHelper;
import com.tibco.cep.bpmn.core.utils.ECoreHelper.BpmnIndexURIHandler;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.Identifier;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.impl.PropertyDefinitionImpl;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.studio.common.util.EntityNameHelper;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.refactoring.IStudioPasteParticipant;
import com.tibco.cep.studio.core.refactoring.StudioRefactoringParticipant;
import com.tibco.cep.studio.core.search.ISearchParticipant;
import com.tibco.cep.studio.core.search.SearchResult;
import com.tibco.cep.studio.core.search.SearchUtils;
import com.tibco.xml.data.primitive.ExpandedName;

public class BpmnProcessRefactoringParticipant extends
		StudioRefactoringParticipant implements IStudioPasteParticipant {
	private static final String[] SUPPORTED_EXTENSIONS = new String[] { BpmnCommonIndexUtils.BPMN_PROCESS_EXTENSION };

	Object fElt ;
	public BpmnProcessRefactoringParticipant() {
		// TODO Auto-generated constructor stub
	}
	
	protected ISearchParticipant getSearchParticipant() {
		return new BpmnProcessSearchParticipant();
	}
	
	@Override
	public boolean isSupportedPaste(IResource resource, IContainer target) {
		return BpmnCommonIndexUtils.BPMN_PROCESS_EXTENSION.equals(resource.getFileExtension());
	}

	@Override
	public IStatus pasteElement(String newName, IResource elementToPaste,
			IContainer target, boolean overwrite, IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		if (elementToPaste instanceof IFile && isSupportedPaste(elementToPaste, target)) {
			try {
				BpmnProcessFileChangeHelper bpmnProcessFileChangeHelper = new BpmnProcessFileChangeHelper();
				bpmnProcessFileChangeHelper.handleBpmnProcessPaste(newName, elementToPaste, target, overwrite);
			} catch (Exception e) {
				BpmnCorePlugin.log(e);
			}
    	}
		return null;
	}
	

	@Override
	public IStatus validateName(IResource resource, IContainer target,
			String newName) {
		if (!EntityNameHelper.isValidBEProjectIdentifier(newName)) {
			return new Status(IStatus.ERROR, BpmnCorePlugin.PLUGIN_ID, newName + " is not a valid BE identifier");
		}
		return Status.OK_STATUS;
	}
	
	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws OperationCanceledException {
		if (isDeleteRefactor()) {
			// checks for references to the deleted element(s)
			return checkDeleteConditions(pm);
		}
		IResource resource = getResource();
		if (!(resource instanceof IFile)) {
			return null;
		}
		IFile file = (IFile) resource;
		if (isSupportedResource(file)) {
			return super.checkConditions(pm, context);
		}
		return new RefactoringStatus();
	}
	
	protected void checkForDuplicateElement(RefactoringStatus status,
			String newName, IResource parentResource) {

		if (isRenameRefactor()) {
			StringBuilder duplicateFileName = new StringBuilder("");
			boolean isDuplicateBEResource = BpmnModelUtils.isDuplicateProcessResource(
					parentResource, newName, duplicateFileName);
			if (isDuplicateBEResource) {
				status.addFatalError(com.tibco.cep.studio.core.util.Messages
						.getString("BE_Resource_FilenameExists",
								duplicateFileName, newName));
			}
		}
	}
	
	protected RefactoringStatus checkDeleteConditions(IProgressMonitor pm) {
		RefactoringStatus status = new RefactoringStatus();
		ISearchParticipant part = getSearchParticipant();
		if (part != null) {
			Object elementToRefactor = getElementToRefactor();
			IResource resource = getResource();
			
			if(resource instanceof IFile && isSupportedResource((IFile)resource)){
				try {
					elementToRefactor = loadBpmnProcess((IFile)resource, null);
				} catch (Exception e) {
					BpmnCorePlugin.log(e);
				}
			}
			
			SearchResult search = part.search(elementToRefactor, fProjectName, getNewElementName(), new SubProgressMonitor(pm, 1));
			List<EObject> exactMatches = search.getExactMatches();
			// warn the user that references exist
			for (EObject object : exactMatches) {
				RefactoringStatusContext statusContext = createRefactoringStatusContext(object);
				IFile file = BpmnIndexUtils.getFile(fProjectName, object);
				String warningLabel = null;
				if (file != null && file.exists()) {
					if (file.equals(getResource()) || isContainedDelete(file)) {
						// skip the file being deleted, or any file contained within a folder being deleted
						continue;
					}
					warningLabel = "Warning - references exist in "+file.getFullPath().toString();
				} else {
					warningLabel = "Warning - references exist: "+SearchUtils.getElementMatchLabel(object);
				}
				status.addEntry(new RefactoringStatusEntry(RefactoringStatus.WARNING,
						warningLabel, statusContext));
				
				// add this as a resource to be validated, post delete
				addAffectedResource(file);
			}
		}
		return status;
	}
	
	protected RefactoringStatusContext createRefactoringStatusContext(
			EObject data) {
		IFile file = BpmnIndexUtils.getFile(fProjectName, data);
		if (file != null) {
			return new FileStatusContext(file, null);
		}
		return null;
	}
	
	protected boolean isSupportedResource(IFile file) {
		String[] extensions = getSupportedExtensions();
		if (extensions == null && (file.getFileExtension() == null || file.getFileExtension().length() == 0)) {
			return true;
		}
		for (String ext : extensions) {
			if (ext.equalsIgnoreCase(file.getFileExtension())) {
				return true;
			}
		}
		return false;
	}
	
	protected String[] getSupportedExtensions() {
		return SUPPORTED_EXTENSIONS;
	}
	
	@Override
	public Change createPreChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		CompositeChange compositeChange = new CompositeChange("Process changes:");
		if (!isValidProject()) {
			return null;
		}
		BpmnProcessFileChangeHelper helper = new BpmnProcessFileChangeHelper();
		IResource resource = getResource();
		this.fProjectName = resource.getProject().getName();
		if (resource instanceof IProject) {
			helper.handleProjectRefactor(compositeChange, fProjectName, pm);
			Change change = null;
			if(compositeChange.getChildren().length > 0)
				change = compositeChange;
			return change;
		}

		if (resource instanceof IFolder) {
			helper.handleFolderRefactor(compositeChange, fProjectName, pm);
			Change change = null;
			if(compositeChange.getChildren().length > 0)
				change = compositeChange;
			return change;
		}

		if (!(resource instanceof IFile)) {
			return null;
		}
		Change change = null;
		IFile file = (IFile) resource;
		
		if (!isSupportedResource(file)) {
			return null;
		}

		if (!shouldUpdateReferences()) {
			return null;
		}
		
		if (isContainedDelete(file)) {
			return null;
		}

		try {
			helper.handleProcessRefactor(compositeChange, file);
		} catch (Exception e) {
			BpmnCorePlugin.log(e);
		}

		
		if(compositeChange.getChildren().length > 0)
			change = compositeChange;
		
		return change;
		
	}
	

	/**
	 * Some changes need to be done prior to the file delete.  In most cases,
	 * it is suitable to simply process the delete in the processEntity call,
	 * where the changes are made post-delete
	 * @param entity
	 * @return the altered entity, or <code>null</code> if no changes are made
	 */
	protected EObjectWrapper<EClass, EObject> preProcessEntityDelete(EObjectWrapper<EClass, EObject> refactorParticipant) {
//		need to preProcess entity in specific refactoring participants
		return null;
	}
	


	/**
	 * Creates a <code>Change</code> object given the IFile and Entity.
	 * Simply serializes the Entity and creates a ReplaceEdit for the
	 * IFile.  This is generally used after processing the entity, to
	 * create the actual refactoring change to take place
	 * @param file
	 * @param refactorParticipant
	 * @return
	 * @throws CoreException
	 */
	
	public Change createTextFileChange(IFile file, EObjectWrapper<EClass, EObject> refactorParticipant, URIHandler uriHandler){
		if(file== null || !file.exists()){
			return null;
		}
		if (isDeleteRefactor() && isDeleted(file)) {
			// if this file is already getting deleted, then don't create a text file change for it
			return null;
		}
		
		try {
			return createTextFileModification(file,refactorParticipant,uriHandler,isProjectRefactor(),getOldElementName());
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return null ;
		
	}
	
	public static Change createTextFileModification(IFile file, EObjectWrapper<EClass, EObject> refactorParticipant, URIHandler uriHandler,boolean isProjectRefactor,String oldEltName)
			throws CoreException {
		
//		//To restrict references to be refactored from Project Library 
//		if(file== null || !file.exists()){
//			return null;
//		}
//		if (isDeleteRefactor() && isDeleted(file)) {
//			// if this file is already getting deleted, then don't create a text file change for it
//			return null;
//		}
		refactorParticipant = EObjectWrapper.wrap(BpmnIndexUtils.getRootContainer(refactorParticipant.getEInstance() ));
		byte[] objectContents;
		TextFileChange change = null;
		InputStream fis = null;
		try {
			
			String contents = null;
			if (refactorParticipant.isInstanceOf(BpmnModelClass.PROCESS)) {
				//Modified by Anand on 04/29/2009 to fix 1-AP56AF & 1-AMFJDZ
				String pathAsString = null;
				String folder = refactorParticipant.getAttribute(BpmnMetaModelConstants.E_ATTR_FOLDER);
				String ownerProject = refactorParticipant.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT);
				IProject project = null;
				if (isProjectRefactor) {
					project = ResourcesPlugin.getWorkspace().getRoot().getProject(oldEltName);
					IPath projectLocation = project.getLocation();
					IPath path = projectLocation == null ? null : projectLocation.append(folder);
					pathAsString = path == null ? null : path.toString();
				} else {
					project = ResourcesPlugin.getWorkspace().getRoot().getProject(ownerProject);
					IPath projectLocation = project.getLocation();
					IPath path = projectLocation == null ? null : projectLocation.append(folder);
					pathAsString = path == null ? null : path.toString();
				}
				URIHandler handler = uriHandler;
				if(handler == null)
					handler = ECoreHelper.getURIHandler(project);
				
				objectContents = BpmnIndexUtils.getEObjectContents(
							pathAsString,
							refactorParticipant.getEInstance(), 
							handler);
				contents = new String(objectContents, file.getCharset());
			}
			
			change = new TextFileChange("New name change", file);
			fis = file.getContents();
			int size = IndexUtils.getFileSize(file, fis);
			ReplaceEdit edit = new ReplaceEdit(0, size, contents);
			change.setEdit(edit);
			
		} catch (IOException e) {
			BpmnCorePlugin.log(e);
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				BpmnCorePlugin.log(e);
			}
		}
		return change;
	}
	
	
	protected String getNewPath(String elementPath, IFolder folder) {
		int offset = 0;
		String initChar = "";
		if (elementPath.startsWith("/")) {
			initChar = "/";
			offset = 1;
		}
		String oldPath = folder.getProjectRelativePath().toString();
		if (isMoveRefactor()) {
			return getNewElementPath() + folder.getName() + elementPath.substring(oldPath.length()+offset);
		} else if (isRenameRefactor()) {
			if (folder.getParent() instanceof IFolder) {
				String parentPath = folder.getParent().getProjectRelativePath().toString();
				return initChar + parentPath + "/" + getNewElementName() + elementPath.substring(oldPath.length()+offset);
			}
			return initChar + getNewElementName() + elementPath.substring(oldPath.length()+offset);
		}
		return elementPath;
	}
	
	
	public static List<EObject> getAllProcess(URIHandler uriHandler,String projName){
		List<EObject> allProcesses = BpmnIndexUtils.getAllProcesses(projName);
		List<EObject> results = new ArrayList<EObject>();
		for (EObject process : allProcesses) {
			EObject loadProcess = loadProcess(process, uriHandler,projName);
			if (loadProcess != null)
				results.add(loadProcess);
		}
		return results;
	}
	

	
	public static EObject loadProcess(EObject process, URIHandler uriHandler,String projName){
		EObject object = null;
		IFile file = BpmnIndexUtils.getFile(projName, process);
		if (file != null && file.exists()) {
			try {
				object = loadBpmnProcess(file, uriHandler);
			} catch (Exception e) {
				BpmnCorePlugin.log(e);
			}
		}
		return object;
	}

		
	private boolean isDeleted(IFile file) {
		RefactoringProcessor processor = getProcessor();
		if (isDeleteRefactor()) {
			Object[] elements = processor.getElements();
			for (int i=0; i<elements.length; i++) {
				Object o = elements[i];
				if (o instanceof IFile) {
					if (o.equals(file)) {
						return true;
					}
				}
			}
		}
		return false;
	}


	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		if (!isValidProject() || isProjectRefactor() || isFolderRefactor()) {
			return null;
		}
		
		IResource resource = getResource();
		if (resource instanceof IFolder || resource instanceof IProject) {
			return null; // these changes are done in the pre-change
		}
		Object elementToRefactor = getElementToRefactor();
		CompositeChange compositeChange = new CompositeChange("Process changes:");
		
		if (!shouldUpdateReferences()) {
			return null;
		}
		
		BpmnProcessFileChangeHelper helper = new BpmnProcessFileChangeHelper();
		if (elementToRefactor instanceof EntityElement){
			helper.handleArtifactsRefactor(fProjectName, compositeChange,
					elementToRefactor);
			Entity entity = ((EntityElement) elementToRefactor).getEntity();
			if(entity instanceof Channel){
				Channel ch = (Channel)entity;
				helper.handleChannelRefactor(fProjectName, compositeChange,ch);
			}
		}else if(elementToRefactor instanceof RuleElement){
			helper.handleArtifactsRefactor(fProjectName, compositeChange,
					elementToRefactor);
		}else if(elementToRefactor instanceof DecisionTableElement){
			helper.handleArtifactsRefactor(fProjectName, compositeChange,
					elementToRefactor);
		}
		else if(elementToRefactor instanceof IFile){
			helper.handleFileRefactor(fProjectName, compositeChange,
					(IFile)elementToRefactor);
		}else if(elementToRefactor instanceof Destination){
			helper.handleDestinationRefactor(fProjectName, compositeChange,
					(Destination)elementToRefactor);
		}else if( elementToRefactor instanceof PropertyDefinitionImpl ) {
			PropertyDefinitionImpl propDef = ( PropertyDefinitionImpl ) elementToRefactor ;
			IFile file = ( IFile ) resource ;
			Entity ent = IndexUtils.getEntity(fProjectName, file.getProjectRelativePath().removeFileExtension().toString());
			if (ent instanceof Event || ent instanceof Concept ) {
				try {
					helper.handleProcessXsltRefactor(compositeChange , file , propDef) ;
				}catch ( Exception e ) {
					
				}
			}
		}
		
		Change change = null;
		if(compositeChange.getChildren().length > 0)
			change = compositeChange;
		
		return change;
	}


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static EObject loadBpmnProcess(IResource processResource,URIHandler handler) throws Exception{
		URIHandler uriHandler = handler;
		if(uriHandler == null){
			uriHandler = ECoreHelper.getURIHandler(processResource.getProject());
		}
		EList<EObject> resources = ECoreHelper.deserializeModelXMI(processResource, true,uriHandler);
		return resources.get(0);
	}
	
	/**
	 * Helper class to do changes in BPMN process due to refactoring of project/folder/artifacts
	 * @author majha
	 *
	 */
	public class BpmnProcessFileChangeHelper{
		
		public void handleBpmnProcessPaste(String newName,IResource source, IContainer target, boolean overwrite) throws Exception{
			if (newName == null) {
				return;
			}
			// creating new location map , to convert old project name key to new project name key
			Map<String, URI> uriMap = null;
			URI fileURI = URI.createFileURI(BpmnIndexUtils
					.getIndexLocation(target.getProject().getName()));
			uriMap = new HashMap<String, URI>();
			uriMap.put(source.getProject().getName(), fileURI);
			
			URIHandler uriHandler = new BpmnIndexURIHandler(source.getProject(),uriMap);
			
			EObject process = loadBpmnProcess(source, uriHandler);
			String oldFullPath=EObjectWrapper.wrap(process).getAttribute(BpmnMetaModelConstants.E_ATTR_FOLDER);
			if (oldFullPath.length() > 1 ) {
				oldFullPath =oldFullPath+ "/" + EObjectWrapper.wrap(process).getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			}
			else
				oldFullPath= "/" + EObjectWrapper.wrap(process).getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			
			String name = BpmnCommonIndexUtils.removeExtension(newName);
			EObjectWrapper<EClass, EObject> processWrap = EObjectWrapper.wrap(process);

			String oldProcessName = processWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			processWrap.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME,name);

			if (!source.getParent().equals(target)) {
				// different folder, update entity folder/namespace.  
				// If the target is an IProject (i.e. root level), then use single slash for folder/ns
				String newPath = target instanceof IProject ? "/" : "/"+target.getFullPath().removeFirstSegments(1).toString()+"/";
				String tempName = changeToBpmnCompatibleFolderName(newPath);
				processWrap.setAttribute(BpmnMetaModelConstants.E_ATTR_FOLDER, tempName);
			}
			
			String newFullPath=EObjectWrapper.wrap(process).getAttribute(BpmnMetaModelConstants.E_ATTR_FOLDER);
			if (newFullPath.length() > 1) {
				newFullPath = newFullPath+"/" + EObjectWrapper.wrap(process).getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			}
			else
				newFullPath= "/" + EObjectWrapper.wrap(process).getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			
			if (!source.getProject().getName().equals(target.getProject().getName())) {
				// reset owner project name
				processWrap.setAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT, target.getProject().getName());
				
				List<EObject> allFlowNodes = BpmnModelUtils.getAllFlowNodes(processWrap.getEInstance());
				allFlowNodes.add(0, process);
				for (EObject flowNode : allFlowNodes) {
					Object resource = BpmnModelUtils.getAttachedResource(flowNode);
					if (resource == null)
						continue;
					if(resource instanceof EObject ){//case of call activity
						try {
							EObject val = (EObject) resource;
							IFile newFile = getFile(target.getProject().getName(), val);
							if(newFile != null){
								URI uri = URI.createPlatformResourceURI(newFile.getFullPath().toPortableString(), false); 
								ResourceSet rset = ECoreHelper.getModelResourceSet(uri);
								Resource createResource = rset.createResource(uri);
								createResource.getContents().add(val);
							}
						} catch (Exception e) {
							BpmnCorePlugin.log(e);
						}
					}
					
				}
				BpmnProcessRefactiongForXsltHelper.removeProcessXslt(processWrap.getEInstance());
			}
			else{
				target.getFullPath();
				List<EObject> allFlowNodes=BpmnModelUtils.getAllFlowNodes(process);
				Map<String, String> nameSpacemapper = new HashMap<String, String>();
				nameSpacemapper.put(RDFTnsFlavor.BE_NAMESPACE+oldFullPath, RDFTnsFlavor.BE_NAMESPACE+newFullPath);
				
				for(EObject flownode:allFlowNodes){
						BpmnProcessRefactiongForXsltHelper.processXSLTFunctionForProcessPaste(flownode,newFullPath, oldFullPath, nameSpacemapper);
				}
			}

			if (!name.equals(oldProcessName))
				processChildren(name, EObjectWrapper.wrap(process),
						EObjectWrapper.wrap(process), target.getProject()
								.getName(), oldProcessName);
			//BpmnProcessRefactiongForXsltHelper.removeProcessXslt(processWrap.getEInstance());
			
			
			saveProcess(newName, process, target, overwrite);
		}
		
		public void handleProcessXsltRefactor(CompositeChange compositeChange,
				IFile file , PropertyDefinitionImpl propDef) {
			String filePath = file.getProjectRelativePath().removeFileExtension().toString() ; 
			if (!filePath.startsWith("/") ) 
				filePath = "/" + filePath ;
			List<EObject> allProcesses = getAllProcess(null,fProjectName);
			for (EObject process : allProcesses) {
				IFile filePr = BpmnIndexUtils.getFile(fProjectName, process);
				List<EObject> allFlowNodes = BpmnModelUtils.getAllFlowNodes(process);
				allFlowNodes.add(0, process);
				for (EObject flowNode : allFlowNodes) {
					String resource = ( String )BpmnModelUtils.getAttachedResource(flowNode);
					if ( filePath.equals(resource) ) {
						String[] arr = filePath.split("/");
						System.out.println(arr[2] + propDef.getName() );
						String oldXsltFormula =  arr[2] + "/" +propDef.getName() ;
						String newXsltFormula =  arr[2] + "/" + getNewElementName() ;
						Map<String, String> nameSpaceMapper = new HashMap<String, String>();
						boolean changeBool = BpmnProcessRefactiongForXsltHelper.handleXsltChangeForEventPropRefactor(flowNode, oldXsltFormula, newXsltFormula, nameSpaceMapper, isRenameRefactor());
						try {
							if ( changeBool  ) {
								EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper.wrap(process);
								Change change = createTextFileChange(filePr, processWrapper, null);
								compositeChange.add(change);
							}
						} catch ( Exception e ){
							
						}
					}
				}
			}
			
			
		}

		public void handleProjectRefactor(CompositeChange compositeChange,
				String projectName, IProgressMonitor pm) throws CoreException {
			// creating new location map , to convert old project name key to
			// new project name key
			Map<String, URI> uriMap = null;
			URI fileURI = URI.createFileURI(BpmnIndexUtils
					.getIndexLocation(getNewElementName()));
			uriMap = new HashMap<String, URI>();
			uriMap.put(getOldElementName(), fileURI);
			IProject oldProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			IProject newProject = ResourcesPlugin.getWorkspace().getRoot().getProject(getNewElementName());
			URIHandler uriHandler = new BpmnIndexURIHandler(oldProject,uriMap);
			List<EObject> allProcesses = getAllProcess(uriHandler,fProjectName);
			for (EObject process : allProcesses) {
				IFile file = BpmnIndexUtils.getFile(projectName, process);

				EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper
						.wrap(process);

				String newElementName = getNewElementName();
				processWrapper.setAttribute(
						BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT,
						newElementName);

				uriMap = new HashMap<String, URI>();
				uriMap.put(getNewElementName(), fileURI);

				uriHandler = new BpmnIndexURIHandler(newProject,uriMap);

				Change change = createTextFileChange(file, processWrapper,
						uriHandler);
				compositeChange.add(change);
			}
		}
		
		public void handleFolderRefactor(CompositeChange compositeChange, String projectName, IProgressMonitor pm) throws CoreException{
			if(isDeleteRefactor())
				return;
			List<EObject> allProcesses = getAllProcess(null,fProjectName);
			for (EObject process : allProcesses) {
				IFile file = BpmnIndexUtils.getFile(projectName, process);

				EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper.wrap(process);

				IFolder folder = (IFolder) getResource();
				String fPath = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_FOLDER);
				if (BpmnIndexUtils.startsWithPath(fPath, folder, true)) {
					String newPath = getNewPath(fPath, folder);
					String tempName = changeToBpmnCompatibleFolderName(newPath);
					processWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_FOLDER,tempName);
					Identifier id = BpmnModelUtils
							.nextIdentifier(
									processWrapper,
									processWrapper,
									(String) processWrapper
											.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME));
					processWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, id.getId());
					Change change = createTextFileChange(file, processWrapper, null);
					compositeChange.add(change);
				}
			}
		}
		
		public void handleProcessRefactor(CompositeChange compositeChange, IFile file) throws Exception{
			EObject loadBpmnProcess = loadBpmnProcess(file, null);
			EObjectWrapper<EClass, EObject> processToBeChanged = EObjectWrapper.wrap(loadBpmnProcess); 
			String oldId = processToBeChanged.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
			String oldProcessName  = "";
			if(processToBeChanged.containsAttribute(BpmnMetaModelConstants.E_ATTR_NAME))
				oldProcessName = processToBeChanged.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			
			if (isRenameRefactor()) {
				String newElementName = getNewElementName();
				
				processToBeChanged.setAttribute(
						BpmnMetaModelConstants.E_ATTR_NAME, newElementName);
				processChildren(newElementName, processToBeChanged, processToBeChanged,
						fProjectName, oldProcessName);
				
				String oldPath = getOldElementPath();
				String[] split = oldPath.split("/");
				split[split.length -1] = getNewElementName();
				String newPath= "";
				for (String string : split) {
					if(!string.trim().isEmpty())
						newPath = newPath+"/"+string;
				}
				Map<String, String> nameSpacemapper = new HashMap<String, String>();
				nameSpacemapper.put(RDFTnsFlavor.BE_NAMESPACE+oldPath, RDFTnsFlavor.BE_NAMESPACE+newPath);
				BpmnProcessRefactiongForXsltHelper.handleProcessRefactor(processToBeChanged.getEInstance(), oldPath, newPath,nameSpacemapper);
				
			} else if (isMoveRefactor()) {
				String newElementPath = getNewElementPath();
				String tempName = changeToBpmnCompatibleFolderName(newElementPath);
				processToBeChanged.setAttribute(
						BpmnMetaModelConstants.E_ATTR_FOLDER,
						tempName);
				Identifier id = BpmnModelUtils
						.nextIdentifier(
								processToBeChanged,
								processToBeChanged,
								(String)processToBeChanged
										.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME));
				processToBeChanged.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, id.getId());
				String oldPath = getOldElementPath();
				String newPath = getNewElementPath()+oldProcessName;
				newPath=newPath.replace(".beprocess", "");
				Map<String, String> nameSpacemapper = new HashMap<String, String>();
				nameSpacemapper.put(RDFTnsFlavor.BE_NAMESPACE+oldPath, RDFTnsFlavor.BE_NAMESPACE+newPath);
				BpmnProcessRefactiongForXsltHelper.handleProcessRefactor(processToBeChanged.getEInstance(), oldPath, newPath, nameSpacemapper);
			}
			
//			if(isMoveRefactor()){
//				String oldPath = getOldElementPath();
//				String newPath = getNewElementPath()+getOldElementName();
//				newPath=newPath.replace(".beprocess", "");
//				Map<String, String> nameSpacemapper = new HashMap<String, String>();
//				nameSpacemapper.put(RDFTnsFlavor.BE_NAMESPACE+oldPath, RDFTnsFlavor.BE_NAMESPACE+newPath);
//				BpmnProcessRefactiongForXsltHelper.handleProcessRefactor(processToBeChanged.getEInstance(), oldPath, newPath, nameSpacemapper);
//			}else if(isRenameRefactor()){
//				String oldPath = getOldElementPath();
//				String[] split = oldPath.split("/");
//				split[split.length -1] = getNewElementName();
//				String newPath= "";
//				for (String string : split) {
//					if(!string.trim().isEmpty())
//						newPath = newPath+"/"+string;
//				}
//				Map<String, String> nameSpacemapper = new HashMap<String, String>();
//				nameSpacemapper.put(RDFTnsFlavor.BE_NAMESPACE+oldPath, RDFTnsFlavor.BE_NAMESPACE+newPath);
//				BpmnProcessRefactiongForXsltHelper.handleProcessRefactor(processToBeChanged.getEInstance(), oldPath, newPath,nameSpacemapper);
//			}
			
			refactorProcessReferences(fProjectName, compositeChange, processToBeChanged, oldId, file);
			Change change = createTextFileChange(file, processToBeChanged, null);
			compositeChange.add(change);
		}
		
	
		public void handleArtifactsRefactor(String projectName, CompositeChange compositeChange, Object refactoringElement) throws CoreException{
			List<EObject> allProcesses = getAllProcess(null,fProjectName);
			for (EObject process : allProcesses) {
				IFile file = BpmnIndexUtils.getFile(projectName, process);
				List<EObject> allFlowNodes = BpmnModelUtils.getAllFlowNodes(process);
				allFlowNodes.add(0, process);
				String oldElementFullPath = getOldElementFullPath();
				boolean changed = false;
				changed = changeProcessNodeForArtifactRefactor(process, oldElementFullPath);
				for (EObject flowNode : allFlowNodes) {
					
					// refactoring  join and fork rfs 
					if(refactoringElement instanceof RuleElement){
						IFile filerf=IndexUtils.getFile(projectName,(RuleElement) refactoringElement);
						ELEMENT_TYPES et=IndexUtils.getFileType(filerf);
						
					if(et.equals(ELEMENT_TYPES.RULE_FUNCTION) && (flowNode.eClass().equals(BpmnModelClass.PARALLEL_GATEWAY)|| flowNode.eClass().equals(BpmnModelClass.INCLUSIVE_GATEWAY))){
						EObjectWrapper<EClass, EObject> userObjWrapper = EObjectWrapper.wrap(flowNode);
							if (userObjWrapper != null ) {
								EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
										.getAddDataExtensionValueWrapper(userObjWrapper);
								String newRefactorPath=null;
								boolean flag=false;
								if(isMoveRefactor()){
									 newRefactorPath = getNewElementPath()+getOldElementName();
								}else if(isRenameRefactor()){
									newRefactorPath = getOldElementPath()+ getNewElementName();
								}
								if (ExtensionHelper.isValidDataExtensionAttribute(userObjWrapper,  BpmnMetaModelExtensionConstants.E_ATTR_JOIN_RULEFUNCTION)) {
									if( (valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JOIN_RULEFUNCTION)).equals(oldElementFullPath)){
										valueWrapper.setAttribute( BpmnMetaModelExtensionConstants.E_ATTR_JOIN_RULEFUNCTION, newRefactorPath);
										flag=true;
									}
								}
								if (ExtensionHelper.isValidDataExtensionAttribute(userObjWrapper, BpmnMetaModelExtensionConstants.E_ATTR_FORK_RULEFUNCTION)) {
									if( (valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_FORK_RULEFUNCTION)).equals(oldElementFullPath)){
										valueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_FORK_RULEFUNCTION, newRefactorPath);
										flag=true;
									}
								}
										
								if(flag && (ExtensionHelper.isValidDataExtensionAttribute(userObjWrapper, BpmnMetaModelExtensionConstants.E_ATTR_FORK_RULEFUNCTION) ||
													ExtensionHelper.isValidDataExtensionAttribute(userObjWrapper,  BpmnMetaModelExtensionConstants.E_ATTR_JOIN_RULEFUNCTION))){
										EObjectWrapper<EClass, EObject> processToBeChanged = EObjectWrapper.wrap(process);
										Change change = createTextFileChange(file, processToBeChanged, null);
										compositeChange.add(change);
									}
							}
					}
					}
					Object resource = BpmnModelUtils.getAttachedResource(flowNode);
					if(resource == null)
						continue;
					if(resource instanceof String && (refactoringElement instanceof RuleElement || refactoringElement instanceof DecisionTableElement || refactoringElement instanceof EntityElement)){
						String val = (String) resource;
						if(val.equals(oldElementFullPath)){
							String fullPath = null;
							if(isMoveRefactor()){
								fullPath = getNewElementPath()+getOldElementName();
							}else if(isRenameRefactor()){
								fullPath = getOldElementPath()+ getNewElementName();
							}
							
							BpmnModelUtils.setResourceAttr(flowNode, fullPath);
							changed = true;
							if(isDeleteRefactor()){
								if(flowNode.eClass().equals(BpmnModelClass.BUSINESS_RULE_TASK)){
									EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(flowNode);
									EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(flowNodeWrapper);
									if(valueWrapper != null){
										valueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_IMPLEMENTATIONS, new ArrayList<EObject>());
									}
								}
							}
							
							changeForEventRefactor(flowNode, process, oldElementFullPath);
							changeForRulefunctionRefactor(flowNode, process, oldElementFullPath);
							changeForJavaSourceRefactor(flowNode, process, oldElementFullPath);
							EClass eClass = flowNode.eClass();
							if(BpmnModelClass.EVENT.isSuperTypeOf(eClass) ||
									BpmnModelClass.RECEIVE_TASK.isSuperTypeOf(eClass)||
										BpmnModelClass.SEND_TASK.isSuperTypeOf(eClass)){
								if(fullPath != null){
									Map<String, String> nameSpaceMapper = new HashMap<String, String>();
									nameSpaceMapper.put(RDFTnsFlavor.BE_NAMESPACE+oldElementFullPath, RDFTnsFlavor.BE_NAMESPACE+fullPath);
									BpmnProcessRefactiongForXsltHelper.handleXsltChangeForEventRefactor(flowNode, oldElementFullPath, fullPath, nameSpaceMapper, isRenameRefactor());
								}
								
							}
							
							if(BpmnModelClass.BUSINESS_RULE_TASK.isSuperTypeOf(eClass)||
										BpmnModelClass.RULE_FUNCTION_TASK.isSuperTypeOf(eClass)){
								if(fullPath != null){
									Map<String, String> nameSpaceMapper = new HashMap<String, String>();
									nameSpaceMapper.put(RDFTnsFlavor.BE_NAMESPACE+oldElementFullPath, RDFTnsFlavor.BE_NAMESPACE+fullPath);
									BpmnProcessRefactiongForXsltHelper.handleXsltChangeForRuleFunctionRefactor(flowNode, oldElementFullPath, fullPath, nameSpaceMapper, isRenameRefactor());
								}
								
							}
						}
						if(!val.isEmpty() && flowNode.eClass().equals(BpmnModelClass.BUSINESS_RULE_TASK)){
							boolean ch =changeForDecisionTableRefactor(flowNode, oldElementFullPath);
							if(ch)
								changed = ch;
						}
					} else if(resource instanceof Collection
							&& !((Collection<?>) resource).isEmpty() && refactoringElement instanceof RuleElement) {
						@SuppressWarnings("unchecked")
						Collection<String> rules = (Collection<String>) resource;
						List<String> toBeRemoved = new ArrayList<String>();
						List<String> toBeAdded = new ArrayList<String>();
						for (String ruleName : rules) {
							if(ruleName.equals(oldElementFullPath)){
								String fullPath = null;
								if(isMoveRefactor()){
									fullPath = getNewElementPath()+getOldElementName();
								}else if(isRenameRefactor()){
									fullPath = getOldElementPath()+ getNewElementName();
								}
								if(fullPath != null)
									toBeAdded.add(fullPath);
								toBeRemoved.add(ruleName);
							}
						}
						if (!toBeRemoved.isEmpty()){
							rules.removeAll(toBeRemoved);
							rules.addAll(toBeAdded);
							changed = true;
						}
					}
				}
				if (changed) {
					EObjectWrapper<EClass, EObject> processToBeChanged = EObjectWrapper.wrap(process);
					Change change = createTextFileChange(file, processToBeChanged, null);
					compositeChange.add(change);
				}
			}
		}
		
		
		public void handleFileRefactor(String projectName,
				CompositeChange compositeChange, IFile refactoredFile)
				throws CoreException {
			if(refactoredFile == null || refactoredFile.getFileExtension() == null)
				return;
			if (!refactoredFile.getFileExtension().equals("wsdl"))
				return;
			List<EObject> allProcesses = getAllProcess(null,fProjectName);
			for (EObject process : allProcesses) {
				IFile file = BpmnIndexUtils.getFile(projectName, process);
				List<EObject> allFlowNodes = BpmnModelUtils
						.getAllFlowNodes(process);
				allFlowNodes.add(0, process);
				String oldElementFullPath = getOldElementPath();
				boolean changed = false;
				for (EObject flowNode : allFlowNodes) {
					if (!flowNode.eClass().equals(BpmnModelClass.SERVICE_TASK))
						continue;

					Object resource = BpmnModelUtils
							.getAttachedResource(flowNode);
					if (resource == null)
						continue;
					if (resource instanceof String) {
						String val = (String) resource;
						if (val.equals(oldElementFullPath)) {
							String fullPath = null;
							if (isMoveRefactor()) {
								String name = refactoredFile.getName().replace(".wsdl", "");
								String path = getNewElementPath()+ name;
								fullPath = path;
							} else if (isRenameRefactor()) {
								String path = refactoredFile.getParent()
										.getProjectRelativePath()
										.toPortableString();
								if (!path.startsWith("/")) {
									path = "/" + path;
								}
								if (!path.endsWith("/")) {
									path = path + "/";
								}
								fullPath = path + getNewElementName();
							}
							
							BpmnModelUtils.setResourceAttr(flowNode,
									fullPath);
							changed = true;
							if(fullPath == null && flowNode.eClass().equals(BpmnModelClass.SERVICE_TASK)){
								webServiceResourceDeleted(flowNode);
							}
						}
					}
				}
				if (changed) {
					EObjectWrapper<EClass, EObject> processToBeChanged = EObjectWrapper
							.wrap(process);
					Change change = createTextFileChange(file,
							processToBeChanged, null);
					compositeChange.add(change);
				}
			}
		}
		
		public void handleJavaFileRefactor(String projectName, CompositeChange compositeChange, IFile refactoredFile)	throws CoreException {
			if(refactoredFile == null || refactoredFile.getFileExtension() == null)
				return;
			if (!refactoredFile.getFileExtension().equals("java"))
				return;
			List<EObject> allProcesses = getAllProcess(null,fProjectName);
			for (EObject process : allProcesses) {
				IFile file = BpmnIndexUtils.getFile(projectName, process);
				List<EObject> allFlowNodes = BpmnModelUtils	.getAllFlowNodes(process);
				allFlowNodes.add(0, process);
				String oldElementFullPath = getOldElementPath();
				boolean changed = false;
				for (EObject flowNode : allFlowNodes) {
					if (!flowNode.eClass().equals(BpmnModelClass.JAVA_TASK))
						continue;

					Object resource = BpmnModelUtils.getAttachedResource(flowNode);
					if (resource == null)
						continue;
					if (resource instanceof String) {
						String val = (String) resource;
						if (val.equals(oldElementFullPath)) {
							String fullPath = null;
							if (isMoveRefactor()) {
								String name = refactoredFile.getName().replace(".java", "");
								String path = getNewElementPath()+ name;
								fullPath = path;
							} else if (isRenameRefactor()) {
								String path = refactoredFile.getParent().getProjectRelativePath().toPortableString();
								if (!path.startsWith("/")) {
									path = "/" + path;
								}
								if (!path.endsWith("/")) {
									path = path + "/";
								}
								fullPath = path + getNewElementName();
							}
							
							BpmnModelUtils.setResourceAttr(flowNode, fullPath);
							changed = true;
							if(fullPath == null && flowNode.eClass().equals(BpmnModelClass.JAVA_TASK)){
								javaTaskResourceDeleted(flowNode);
							}
						}
					}
				}
				if (changed) {
					EObjectWrapper<EClass, EObject> processToBeChanged = EObjectWrapper
							.wrap(process);
					Change change = createTextFileChange(file,
							processToBeChanged, null);
					compositeChange.add(change);
				}
			}
		}
		
		public void handleDestinationRefactor(String projectName,
				CompositeChange compositeChange, Destination dest)
				throws CoreException {
			List<EObject> allProcesses = getAllProcess(null,fProjectName);
			for (EObject process : allProcesses) {
				IFile file = BpmnIndexUtils.getFile(projectName, process);
				List<EObject> allFlowNodes = BpmnModelUtils
						.getAllFlowNodes(process);
				allFlowNodes.add(0, process);
				boolean changed = false;
				for (EObject flowNode : allFlowNodes) {
					EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(flowNode);
					if(!ExtensionHelper.isValidDataExtensionAttribute(wrap,
							BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION))
						continue;
					
					EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
							.getAddDataExtensionValueWrapper(wrap);
					if (valueWrapper != null) {
						String destPath = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION);
						if(destPath != null && !destPath.trim().isEmpty()){
							if(dest.getFullPath().equals(destPath)){
								String fullPath = null;
								if(isMoveRefactor()){
									fullPath = getNewElementPath()+getOldElementName();
								}else if(isRenameRefactor()){
									fullPath = getOldElementPath()+ getNewElementName();
								}
								
								valueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION, fullPath);
								changed = true;
							}
						}
					}
				}
				if (changed) {
					EObjectWrapper<EClass, EObject> processToBeChanged = EObjectWrapper
							.wrap(process);
					Change change = createTextFileChange(file,
							processToBeChanged, null);
					compositeChange.add(change);
				}
			}
		}
		
		
		public void handleChannelRefactor(String projectName,
				CompositeChange compositeChange, Channel channel)
				throws CoreException {
			List<EObject> allProcesses = getAllProcess(null,fProjectName);
			for (EObject process : allProcesses) {
				IFile file = BpmnIndexUtils.getFile(projectName, process);
				List<EObject> allFlowNodes = BpmnModelUtils
						.getAllFlowNodes(process);
				allFlowNodes.add(0, process);
				boolean changed = false;
				for (EObject flowNode : allFlowNodes) {
					EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(flowNode);
					if(!ExtensionHelper.isValidDataExtensionAttribute(wrap,
							BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION))
						continue;
					
					EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
							.getAddDataExtensionValueWrapper(wrap);
					if (valueWrapper != null) {
						String destPath = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION);
						if(destPath != null && !destPath.trim().isEmpty()){
							int lastIndexOf = destPath.lastIndexOf("/");
							String channelPath = destPath.substring(0, lastIndexOf);
							String destName = destPath.substring(lastIndexOf);
							if(channelPath.equals(channel.getFullPath())){
								String fullPath = null;
								if(isMoveRefactor()){
									fullPath = getNewElementPath()+getOldElementName()+destName;
								}else if(isRenameRefactor()){
									fullPath = getOldElementPath()+ getNewElementName()+destName;
								}
								
								valueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_DESTINATION, fullPath);
								changed = true;
							}
						}
					}
				}
				if (changed) {
					EObjectWrapper<EClass, EObject> processToBeChanged = EObjectWrapper
							.wrap(process);
					Change change = createTextFileChange(file,
							processToBeChanged, null);
					compositeChange.add(change);
				}
			}
		}
		
		private boolean changeProcessNodeForArtifactRefactor(EObject flowNode,
				String oldPath) {
			boolean changed = false;
			if (flowNode.eClass().equals(BpmnModelClass.PROCESS)) {
				EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
						.wrap(flowNode);
				List<EObject> propDefs = wrap
						.getListAttribute(BpmnMetaModelConstants.E_ATTR_PROPERTIES);
				List<EObject> toBeRemoved = new ArrayList<EObject>();
				for (EObject eObject : propDefs) {
					EObjectWrapper<EClass, EObject> prop = EObjectWrapper
							.wrap(eObject);
					EObject itemdef = prop
							.getAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF);
					if (itemdef != null) {
						EObjectWrapper<EClass, EObject> defWrap = EObjectWrapper
								.wrap(itemdef);
						String id = defWrap
								.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
						if (id != null) {
							EObject imp = defWrap
									.getAttribute(BpmnMetaModelConstants.E_ATTR_IMPORT);
							if (imp != null) {
								EObjectWrapper<EClass, EObject> impWrap = EObjectWrapper
										.wrap(imp);
								String loc = impWrap
										.getAttribute(BpmnMetaModelConstants.E_ATTR_LOCATION);
								if (loc != null && loc.equals(oldPath)) {
									EObject refactorItemDefinition = refactorItemDefinition(
											defWrap, wrap, oldPath);
									if (refactorItemDefinition != null) {
										prop.setAttribute(
												BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF,
												refactorItemDefinition);
										changed = true;
									}else{
										toBeRemoved.add(eObject);
										changed = true;
									}
								}
							}
						}
					}
				}
				propDefs.removeAll(toBeRemoved);
			}
			return changed;
		}
		
		private boolean changeForEventRefactor(EObject flowNode, EObject process, String oldResourcePath){
			boolean change = false;
			if(flowNode.eClass().equals(BpmnModelClass.START_EVENT) ||
					flowNode.eClass().equals(BpmnModelClass.END_EVENT)){
				EObjectWrapper<EClass, EObject> elementWrapper = EObjectWrapper.wrap(flowNode);
				EList<EObject> listAttribute = elementWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_EVENT_DEFINITIONS);
				if(listAttribute.size() > 0){
					EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(listAttribute.get(0));
					if(wrap.getEClassType().equals(BpmnModelClass.MESSAGE_EVENT_DEFINITION)){
						EObject msgRef = (EObject)wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_MESSAGE_REF);
						if(msgRef != null){
							EObjectWrapper<EClass, EObject> msgRefWrap = EObjectWrapper.wrap(msgRef);
							EObject itemdef = msgRefWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_STRUCTURE_REF);  
							String name = msgRefWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
							if(name != null){
								msgRefWrap.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, getNewElementName());
							}
							 if(itemdef != null){
								 EObjectWrapper<EClass, EObject> defWrap = EObjectWrapper.wrap(itemdef);
								 String id = defWrap
											.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
									if (id != null) {
										EObject imp = defWrap
												.getAttribute(BpmnMetaModelConstants.E_ATTR_IMPORT);
										if (imp != null) {
											EObjectWrapper<EClass, EObject> impWrap = EObjectWrapper
													.wrap(imp);
											String loc = impWrap
													.getAttribute(BpmnMetaModelConstants.E_ATTR_LOCATION);
											if (loc != null && loc.equals(oldResourcePath)) {
												EObject refactorItemDefinition = refactorItemDefinition(
														defWrap, EObjectWrapper.wrap(process), oldResourcePath);
												if (refactorItemDefinition != null) {
													 msgRefWrap.setAttribute(BpmnMetaModelConstants.E_ATTR_STRUCTURE_REF, refactorItemDefinition);
													 change = true;
												}else{
													wrap.setAttribute(BpmnMetaModelConstants.E_ATTR_MESSAGE_REF, null);
													change = true;
												}
											}
										}
									}
							 }
						}
					}
				}
				if (BpmnModelClass.CATCH_EVENT.isSuperTypeOf(elementWrapper
						.getEClassType())) {
					EObject dataOutput = elementWrapper
							.getAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT);
					if (dataOutput != null) {
						EObjectWrapper<EClass, EObject> dataOuputWrap = EObjectWrapper
								.wrap(dataOutput);
						String name = dataOuputWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
						if(name != null){
							dataOuputWrap.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, getNewElementName());
						}
						EObject itemdef = dataOuputWrap
								.getAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF);
						if (itemdef != null) {
							EObjectWrapper<EClass, EObject> defWrap = EObjectWrapper
									.wrap(itemdef);
							 String id = defWrap
										.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
								if (id != null) {
									EObject imp = defWrap
											.getAttribute(BpmnMetaModelConstants.E_ATTR_IMPORT);
									if (imp != null) {
										EObjectWrapper<EClass, EObject> impWrap = EObjectWrapper
												.wrap(imp);
										String loc = impWrap
												.getAttribute(BpmnMetaModelConstants.E_ATTR_LOCATION);
										if (loc != null && loc.equals(oldResourcePath)) {
											EObject refactorItemDefinition = refactorItemDefinition(
													defWrap, EObjectWrapper.wrap(process), oldResourcePath);
											if (refactorItemDefinition != null) { 
												dataOuputWrap.setAttribute(
														BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF,
														refactorItemDefinition);
											}else{
												elementWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT, null);
											}
											if(isDeleteRefactor())
												elementWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATION,new ArrayList<EObject>());
											change = true;
										}
									}
								}
						}
					}
				}
				if(BpmnModelClass.THROW_EVENT.isSuperTypeOf(elementWrapper
						.getEClassType())){
					EObject dataInput = elementWrapper
							.getAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUT);
					if (dataInput != null) {
						EObjectWrapper<EClass, EObject> dataInputWrap = EObjectWrapper
								.wrap(dataInput);
						String name = dataInputWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
						if(name != null){
							dataInputWrap.setAttribute(BpmnMetaModelConstants.E_ATTR_NAME, getNewElementName());
						}
						EObject itemdef = dataInputWrap
								.getAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF);
						if (itemdef != null) {
							EObjectWrapper<EClass, EObject> defWrap = EObjectWrapper
									.wrap(itemdef);
							String id = defWrap
									.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
							if (id != null) {
								EObject imp = defWrap
										.getAttribute(BpmnMetaModelConstants.E_ATTR_IMPORT);
								if (imp != null) {
									EObjectWrapper<EClass, EObject> impWrap = EObjectWrapper
											.wrap(imp);
									String loc = impWrap
											.getAttribute(BpmnMetaModelConstants.E_ATTR_LOCATION);
									if (loc != null && loc.equals(oldResourcePath)) {
										EObject refactorItemDefinition = refactorItemDefinition(
												defWrap, EObjectWrapper.wrap(process), oldResourcePath);
										if (refactorItemDefinition != null) { 
											dataInputWrap.setAttribute(
													BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF,
													refactorItemDefinition);
										}else{
											elementWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUT, null);
											
										}
										if(isDeleteRefactor())
											elementWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATION,new ArrayList<EObject>());
										change = true;
									}
								}
							}
						}
					}
				}
			}
			
			return change;
		}
		
		private boolean changeForRulefunctionRefactor(EObject flowNode, EObject process, String oldResourcePath){
			boolean change = false;
			if(flowNode.eClass().equals(BpmnModelClass.RULE_FUNCTION_TASK) ||
					flowNode.eClass().equals(BpmnModelClass.BUSINESS_RULE_TASK)){
				EObjectWrapper<EClass, EObject> elementWrapper = EObjectWrapper.wrap(flowNode);
				EObject ioSpec = elementWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_IO_SPECIFICATION);
				if(ioSpec != null){
					EObjectWrapper<EClass, EObject> ioSpecWrap = EObjectWrapper.wrap(ioSpec);
					EList<EObject> listAttribute = ioSpecWrap.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUTS);
					if(listAttribute != null && listAttribute.size() >0){
						EObject eObject = listAttribute.get(0);
						EObjectWrapper<EClass, EObject> dataOutputWrap = EObjectWrapper.wrap(eObject);
						EObject itemdef = dataOutputWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF);
						if (itemdef != null) {
							EObjectWrapper<EClass, EObject> defWrap = EObjectWrapper
									.wrap(itemdef);
							String id = defWrap
									.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
							if (id != null) {
								EObject imp = defWrap
										.getAttribute(BpmnMetaModelConstants.E_ATTR_IMPORT);
								if (imp != null) {
									EObjectWrapper<EClass, EObject> impWrap = EObjectWrapper
											.wrap(imp);
									String loc = impWrap
											.getAttribute(BpmnMetaModelConstants.E_ATTR_LOCATION);
									if (loc != null && loc.equals(oldResourcePath)) {
										EObject refactorItemDefinition = refactorItemDefinitionForRuleElement(
												defWrap, EObjectWrapper.wrap(process), oldResourcePath, true);
										if (refactorItemDefinition != null) { 
											dataOutputWrap
												.setAttribute(
													BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF,
													refactorItemDefinition);
										}else{
											ioSpecWrap.setAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUTS, new ArrayList<EObject>());
											
										}
										if(isDeleteRefactor())
											elementWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATIONS,new ArrayList<EObject>());
										change = true;
									}
								}
							}
						}
					}
					
					listAttribute = ioSpecWrap.getListAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUTS);
					if(listAttribute != null && listAttribute.size() >0){
						EObject eObject = listAttribute.get(0);
						EObjectWrapper<EClass, EObject> dataOutputWrap = EObjectWrapper.wrap(eObject);
						EObject itemdef = dataOutputWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF);
						if (itemdef != null) {
							EObjectWrapper<EClass, EObject> defWrap = EObjectWrapper
									.wrap(itemdef);
							String id = defWrap
									.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
							if (id != null) {
								EObject imp = defWrap
										.getAttribute(BpmnMetaModelConstants.E_ATTR_IMPORT);
								if (imp != null) {
									EObjectWrapper<EClass, EObject> impWrap = EObjectWrapper
											.wrap(imp);
									String loc = impWrap
											.getAttribute(BpmnMetaModelConstants.E_ATTR_LOCATION);
									if (loc != null && loc.equals(oldResourcePath)) {
										EObject refactorItemDefinition = refactorItemDefinitionForRuleElement(
												defWrap, EObjectWrapper.wrap(process), oldResourcePath, false);
										if (refactorItemDefinition != null) { 
											dataOutputWrap
												.setAttribute(
													BpmnMetaModelConstants.E_ATTR_ITEM_SUBJECT_REF,
													refactorItemDefinition);
										}else{
											ioSpecWrap.setAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUTS, new ArrayList<EObject>());
											
										}
										if(isDeleteRefactor())
											elementWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATIONS,new ArrayList<EObject>());
										change = true;
									}
								}
							}
						}
					}
				}
			}
			return change;
		}
		
	
		private boolean changeForDecisionTableRefactor(EObject flowNode, String oldResourcePath){
			String newPath = "";
			if(isMoveRefactor()){
				newPath = getNewElementPath()+getOldElementName();
			}else if(isRenameRefactor()){
				newPath = getOldElementPath()+ getNewElementName();
			}
			if(flowNode.eClass().equals(BpmnModelClass.BUSINESS_RULE_TASK)){
				EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper.wrap(flowNode);
				EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(flowNodeWrapper);
				if(valueWrapper != null){
					EList<EObject> listAttribute = valueWrapper.getListAttribute(BpmnMetaModelExtensionConstants.E_ATTR_IMPLEMENTATIONS);
					EObject toBeRemoved = null;
					for (EObject eObject : listAttribute) {
						EObjectWrapper<EClass, EObject> implementation = EObjectWrapper.wrap(eObject);
						String uri = implementation.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_URI);
						if(uri.equals(oldResourcePath)){
							if (!newPath.trim().isEmpty()) {
								implementation
										.setAttribute(
												BpmnMetaModelExtensionConstants.E_ATTR_URI,
												newPath);
								return true;
							}else{
								toBeRemoved = eObject;
								break;
							}
						}
					}	
					if(toBeRemoved != null){
						valueWrapper.removeFromListAttribute(BpmnMetaModelExtensionConstants.E_ATTR_IMPLEMENTATIONS, toBeRemoved);
						return true;
					}
				}
			}
			
			return false;
		}
		
		
		private EObject refactorItemDefinition(EObjectWrapper<EClass, EObject> itemDefWrap,EObjectWrapper<EClass, EObject> processWrap, String oldPath){
			String newPath = "";
			if(isMoveRefactor()){
				newPath = getNewElementPath()+getOldElementName();
			}else if(isRenameRefactor()){
				newPath = getOldElementPath()+ getNewElementName();
			}
			if(!newPath.trim().isEmpty()){
				String id = itemDefWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				 if(id!= null){
					 EObject imp = itemDefWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_IMPORT);
					 if(imp != null){
						 EObjectWrapper<EClass, EObject> impWrap = EObjectWrapper.wrap(imp);
						 String loc = impWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_LOCATION);
						 if(loc != null && loc.equals(oldPath)){
							 boolean isArray = id.contains("[]");
							 EObject dummyItemDef = getDummyItemDef(newPath, isArray);
							 EObjectWrapper<EClass, EObject> dummyItemDefWrap = EObjectWrapper.wrap(dummyItemDef);
							 String dummyItemDefId = dummyItemDefWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
							 
							 String projName = processWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT);
							 EObject index = BpmnIndexUtils.getIndex(projName);
							 if(index != null){
								 EObjectWrapper<EClass, EObject> indexWrap = EObjectWrapper.wrap(index);
								 List<EObject> rootElements = BpmnCommonIndexUtils.getRootElements(indexWrap);
								 for (EObject eObject : rootElements) {
									EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(eObject);
									String itemdefId = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
									if (itemdefId != null && itemdefId.equals(dummyItemDefId)) {
										dummyItemDef = eObject;
										break;
									}
								}
								 BpmnCommonIndexUtils.addRootElement(indexWrap, dummyItemDef);
								 
//								 indexWrap.removeFromListAttribute(BpmnMetaModelConstants.E_ATTR_ROOT_ELEMENTS, dummyItemDef);
								 return dummyItemDef;
							 }
						 }
					 }
				 }
			}
			 return null;
		}
		
		private EObject refactorItemDefinitionForRuleElement(EObjectWrapper<EClass, EObject> itemDefWrap,EObjectWrapper<EClass, EObject> processWrap, String oldPath, boolean isReturn){
			String newPath = "";
			if(isMoveRefactor()){
				newPath = getNewElementPath()+getOldElementName();
			}else if(isRenameRefactor()){
				newPath = getOldElementPath()+ getNewElementName();
			}
			if(!newPath.trim().isEmpty()){
				String id = itemDefWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				 if(id!= null){
					 EObject imp = itemDefWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_IMPORT);
					 if(imp != null){
						 EObjectWrapper<EClass, EObject> impWrap = EObjectWrapper.wrap(imp);
						 String loc = impWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_LOCATION);
						 if(loc != null && loc.equals(oldPath)){
							 boolean isArray = id.contains("[]");
							 EObject dummyItemDef = getDummyItemDefForRuleElement(newPath, isReturn, isArray);
							 EObjectWrapper<EClass, EObject> dummyItemDefWrap = EObjectWrapper.wrap(dummyItemDef);
							 String dummyItemDefId = dummyItemDefWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
							 
							 String projName = processWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT);
							 EObject index = BpmnIndexUtils.getIndex(projName);
							 if(index != null){
								 EObjectWrapper<EClass, EObject> indexWrap = EObjectWrapper.wrap(index);
								 List<EObject> rootElements = BpmnCommonIndexUtils.getRootElements(indexWrap);
								 for (EObject eObject : rootElements) {
									EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(eObject);
									String itemdefId = wrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
									if (itemdefId != null && itemdefId.equals(dummyItemDefId)) {
										dummyItemDef = eObject;
										break;
									}
								}
								 BpmnCommonIndexUtils.addRootElement(indexWrap, dummyItemDef);
								 
//								 indexWrap.removeFromListAttribute(BpmnMetaModelConstants.E_ATTR_ROOT_ELEMENTS, dummyItemDef);
								 return dummyItemDef;
							 }
						 }
					 }
				 }
			}
			 return null;
		}
		
		
		private EObject getDummyItemDef(String path, boolean isArray){
			String name = path.substring(path.lastIndexOf('/') + 1);
			String nsURI = RDFTnsFlavor.BE_NAMESPACE + path ;
			
			ExpandedName type = ExpandedName.makeName(nsURI, name);
			String id = type.toString();
			if(isArray)
				id = id + "[]";
			
			EObjectWrapper<EClass, EObject> itemDefWrapper = EObjectWrapper
					.createInstance(BpmnModelClass.ITEM_DEFINITION);
			itemDefWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID,id);
			itemDefWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_KIND,
					BpmnModelClass.ENUM_ITEM_KIND_INFORMATION);
			
			return itemDefWrapper.getEInstance();
		}
		
		private EObject getDummyItemDefForRuleElement(String path, boolean isReturn, boolean isArray){
			String name = "";
			if(isReturn)
				name = "return";
			else
				name= "arguments";
			
			String nsURI = RDFTnsFlavor.BE_NAMESPACE + path ;
			
			ExpandedName type = ExpandedName.makeName(nsURI, name);
			String id = type.toString();
			if(isArray)
				id = id + "[]";
			
			EObjectWrapper<EClass, EObject> itemDefWrapper = EObjectWrapper
					.createInstance(BpmnModelClass.ITEM_DEFINITION);
			itemDefWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ID,id);
			itemDefWrapper.setAttribute(BpmnMetaModelConstants.E_ATTR_ITEM_KIND,
					BpmnModelClass.ENUM_ITEM_KIND_INFORMATION);
			
			return itemDefWrapper.getEInstance();
		}

		protected void refactorProcessReferences(String projectName, CompositeChange compositeChange, EObjectWrapper<EClass, EObject> refactoredProcess, String oldProcessId, IFile refactoredProcessFile) throws CoreException{
			List<EObject> allProcesses = getAllProcess(null,fProjectName);
			
			for (EObject process : allProcesses) {
				boolean changed = false; 
				String processId = EObjectWrapper.wrap(process)
						.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				if (processId.equals(oldProcessId))
					continue;// as this already being refactored
				
				IFile file = BpmnIndexUtils.getFile(projectName, process);
				List<EObject> allFlowNodes = BpmnModelUtils.getAllFlowNodes(process);
				allFlowNodes.add(0, process);
				for (EObject flowNode : allFlowNodes) {
					Object resource = BpmnModelUtils.getAttachedResource(flowNode);
					if (resource == null)
						continue;
					if(resource instanceof EObject ){//case of call activity
						EObject val = (EObject) resource;
						boolean valid = false;
						if(val.eIsProxy()){
							URI proxyURI = ((org.eclipse.emf.ecore.InternalEObject)val).eProxyURI();
							URI fileURI  = URI.createPlatformResourceURI(refactoredProcessFile.getFullPath().toPortableString(),false);
							if(proxyURI.path().equals(fileURI.path())){
								valid = true;
							}
						}else{
							EObjectWrapper<EClass, EObject> valWrapper = EObjectWrapper.wrap(val);
							String id = (String) valWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
							valid = id.equals(oldProcessId);
						}

						if (valid) {
							if (isDeleteRefactor()) {
								BpmnModelUtils.setResourceAttr(flowNode, null);
								BpmnProcessRefactiongForXsltHelper.removeFlownodeXslt(flowNode);
								changed = true;
							} else if (isRenameRefactor()) {
								
								IFile newFile = getFile(projectName, refactoredProcess.getEInstance());
								URI uri = URI.createPlatformResourceURI(newFile.getFullPath().toPortableString(), false); 
								ResourceSet rset = ECoreHelper.getModelResourceSet(uri);
								Resource createResource = rset.createResource(uri);
								createResource.getContents().add(refactoredProcess.getEInstance());
								BpmnModelUtils.setResourceAttr(flowNode, refactoredProcess.getEInstance());
								//for mapper
								String oldPath = getOldElementPath();
								String[] split = oldPath.split("/");
								split[split.length -1] = getNewElementName();
								String newPath= "";
								for (String string : split) {
									if(!string.trim().isEmpty())
										newPath = newPath+"/"+string;
								}
								Map<String, String> nameSpacemapper = new HashMap<String, String>();
								nameSpacemapper.put(RDFTnsFlavor.BE_NAMESPACE+oldPath, RDFTnsFlavor.BE_NAMESPACE+newPath);
								BpmnProcessRefactiongForXsltHelper.handleXsltChangeForProcessRefactor(flowNode, oldPath, newPath,nameSpacemapper);
								
								changed = true;
							} else if (isMoveRefactor()) {
								IFile movedFile = getFile(projectName, refactoredProcess.getEInstance());
								if (movedFile != null ) {
									URI uri = URI.createPlatformResourceURI(movedFile.getFullPath().toPortableString(), false); 
									ResourceSet rset = ECoreHelper.getModelResourceSet(uri);
									Resource createResource = rset.createResource(uri);
									createResource.getContents().add(refactoredProcess.getEInstance());
									BpmnModelUtils.setResourceAttr(flowNode,refactoredProcess.getEInstance());
									// for mapper
									String oldPath = getOldElementPath();
									String newPath = getNewElementPath()+getOldElementName();
									newPath=newPath.replace(".beprocess", "");
									Map<String, String> nameSpacemapper = new HashMap<String, String>();
									nameSpacemapper.put(RDFTnsFlavor.BE_NAMESPACE+oldPath, RDFTnsFlavor.BE_NAMESPACE+newPath);
									BpmnProcessRefactiongForXsltHelper.handleXsltChangeForProcessRefactor(flowNode, oldPath, newPath, nameSpacemapper);
									
									changed = true;
								}
							}
						}
					}
				}
				
				if (changed) {
					EObjectWrapper<EClass, EObject> processToBeChanged = EObjectWrapper.wrap(process);
					Change change = createTextFileChange(file, processToBeChanged, null);
					compositeChange.add(change);
				}
			}
		}
		
		public IFile getFile(String projectName, EObject element) {
			if (element == null) {
				return null;
			}
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			IPath filePath = new Path(BpmnIndexUtils.getElementPath(element));
			IFile file = project.getFile(filePath);

			return file;
		}
		
		
		
		protected void processChildren(String newName,
				EObjectWrapper<EClass, EObject> flowElementContainer,
				EObjectWrapper<EClass, EObject> eobjWrap, String newProjName, String oldProcessName) {

			//For project copy/paste, check for new project's index 
			BpmnNameGenerator nameGenerator = BpmnCorePlugin.getDefault().getBpmnModelManager().getNameGenerator(newProjName);
			if (nameGenerator.getIndex() == null) {
				return;
			}

			if (eobjWrap
					.containsAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT)) {
				eobjWrap.setAttribute(BpmnMetaModelConstants.E_ATTR_OWNER_PROJECT,
						newProjName);
			}
			if(flowElementContainer.getEClassType().equals(BpmnModelClass.PROCESS))
				flowElementContainer.setAttribute(BpmnMetaModelConstants.E_ATTR_ID,newName);
			if (eobjWrap.containsAttribute(BpmnMetaModelConstants.E_ATTR_ID) && !eobjWrap.getEClassType().equals(BpmnModelClass.PROCESS)) {
//				if(eobjWrap.containsAttribute(BpmnMetaModelConstants.E_ATTR_NAME)){
//					String wrapName = eobjWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
//					String wrapId = eobjWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
//					if(wrapId != null && wrapId.contains(oldProcessName)){
//						Identifier id = BpmnModelUtils
//								.nextIdentifier(
//										flowElementContainer,
//										eobjWrap,wrapName);
//						eobjWrap.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, id.getId());
//					}
//				}else{
					Identifier id = BpmnModelUtils
							.nextIdentifier(
									flowElementContainer,
									eobjWrap,null);
					String oldId = eobjWrap.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
					//Fix for BE-21911
					if(oldId != null && !("arguments").equals(oldId))
						eobjWrap.setAttribute(BpmnMetaModelConstants.E_ATTR_ID, id.getId());
//				}
			}

			EList<EStructuralFeature> allStructuralFeatures = eobjWrap
					.getEInstance().eClass().getEAllStructuralFeatures();

			for (EStructuralFeature structuralFeature : allStructuralFeatures) {
				if (structuralFeature instanceof EReference) {
					if (((EReference) structuralFeature).isContainment()) {
						Object get = eobjWrap.getEInstance()
								.eGet(structuralFeature);
						if (get instanceof EObject) {
							EObject flowelement = BpmnModelUtils.getFlowElementContainer((EObject) get);
							flowElementContainer =EObjectWrapper
									.wrap(flowelement);
							processChildren(newName, flowElementContainer,
									EObjectWrapper.wrap((EObject) get),
									newProjName, oldProcessName);
						} else if (get instanceof List) {
							processList((List<?>) get, newName, newProjName, oldProcessName);
						}
					}
				}
			}
		}

		private void processList(List<?> list, String newName, String oldProjName, String oldProcessName) {
			for (Object obj : list) {
				if (obj instanceof EObject) {
					EObject flowElement = BpmnModelUtils.getFlowElementContainer((EObject)obj);
					EObjectWrapper<EClass, EObject> flowElementContainer = EObjectWrapper.wrap(flowElement);
					processChildren(newName, flowElementContainer, EObjectWrapper.wrap((EObject) obj), oldProjName, oldProcessName);
				} else if (obj instanceof List) {
					processList((List<?>) obj, newName, oldProjName, oldProcessName);
				}
			}
		}
	
		protected void saveProcess(String newName, EObject process, IContainer target, boolean overwrite) throws CoreException {
			try {
				IFile file = target.getFile(new Path(newName));
				
				if (!file.exists() || overwrite){ 
						ECoreHelper.serializeModelXMI(file,process);
				}
			} catch (Exception e) {
				BpmnCorePlugin.log("Error while saving",e);
			} 
		}
		
		 String changeToBpmnCompatibleFolderName(String folder) {
			String newName = folder;
			if (newName.trim().length() != 1 && newName.endsWith("/")) {
				newName = newName.substring(0, newName.length() - 1);
			}
			return newName;
		}
		
		
		private void webServiceResourceDeleted(EObject flowNode) {
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
					.wrap(flowNode);
			EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper
					.getAddDataExtensionValueWrapper(wrap);
			if (addDataExtensionValueWrapper != null) {
				addDataExtensionValueWrapper.setAttribute(
						BpmnMetaModelExtensionConstants.E_ATTR_SERVICE, "");
				addDataExtensionValueWrapper.setAttribute(
						BpmnMetaModelExtensionConstants.E_ATTR_PORT, "");
				addDataExtensionValueWrapper.setAttribute(
						BpmnMetaModelExtensionConstants.E_ATTR_END_POINT_URL,
						"");
				addDataExtensionValueWrapper.setAttribute(
						BpmnMetaModelExtensionConstants.E_ATTR_OPERATION, "");
				addDataExtensionValueWrapper.setAttribute(
						BpmnMetaModelExtensionConstants.E_ATTR_SOAP_ACTION, "");
				addDataExtensionValueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_BINDING_TYPE,BpmnModelClass.ENUM_WS_BINDING_HTTP);
				addDataExtensionValueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_TIMEOUT,0);
				addDataExtensionValueWrapper.setAttribute(BpmnMetaModelExtensionConstants.E_ATTR_JMS_CONFIG, null);
			}
			List<EObject> associations = new ArrayList<EObject>();
			wrap.setAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATIONS,
					associations);
			
			associations = new ArrayList<EObject>();
			wrap.setAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATIONS,
					associations);
		}
		
		private void javaTaskResourceDeleted(EObject flowNode) {
			EObjectWrapper<EClass, EObject> wrap = EObjectWrapper
					.wrap(flowNode);
			EObjectWrapper<EClass, EObject> addDataExtensionValueWrapper = ExtensionHelper
					.getAddDataExtensionValueWrapper(wrap);
			if (addDataExtensionValueWrapper != null) {
				//TODO
			}
			List<EObject> associations = new ArrayList<EObject>();
			wrap.setAttribute(BpmnMetaModelConstants.E_ATTR_DATA_INPUT_ASSOCIATIONS,
					associations);

			associations = new ArrayList<EObject>();
			wrap.setAttribute(BpmnMetaModelConstants.E_ATTR_DATA_OUTPUT_ASSOCIATIONS,
					associations);
		}
		
	}
	private boolean changeForJavaSourceRefactor(EObject flowNode, EObject process, String oldResourcePath){
		boolean change = false;
		String newPath = null;
		if (isMoveRefactor()) {
			newPath = getNewElementPath() + getOldElementName();
		} else if (isRenameRefactor()) {
			newPath = getOldElementPath() + getNewElementName();
		}
		if (flowNode.eClass().equals(BpmnModelClass.JAVA_TASK)) {
			EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper
					.wrap(flowNode);
			EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
					.getAddDataExtensionValueWrapper(flowNodeWrapper);
			valueWrapper.setAttribute(
					BpmnMetaModelExtensionConstants.E_ATTR_JAVA_FILE_PATH,
					newPath);

			if (isMoveRefactor()) {
				String packageName = IndexUtils.getJavaPackageName(newPath, fProjectName);
				valueWrapper.setAttribute(
						BpmnMetaModelExtensionConstants.E_ATTR_JAVA_PACKAGE,
						packageName);
			}
			change = true;
		}

		return change;
	}

	
	@Override
	public void setProjectPaste(boolean projectPaste) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isProjectPaste() {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
