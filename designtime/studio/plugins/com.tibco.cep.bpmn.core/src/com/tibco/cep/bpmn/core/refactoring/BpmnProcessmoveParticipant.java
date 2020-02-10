package com.tibco.cep.bpmn.core.refactoring;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.MoveArguments;
import org.eclipse.ltk.core.refactoring.participants.MoveParticipant;

import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonIndexUtils;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.model.designtime.utils.Identifier;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.util.StudioJavaUtil;

public class BpmnProcessmoveParticipant extends MoveParticipant {

	private Object fElement;
	private static final String[] SUPPORTED_EXTENSIONS = new String[] { BpmnCommonIndexUtils.BPMN_PROCESS_EXTENSION };
	protected String fBpmnProjectName;
	public MoveArguments BpmnfArguments;
	
	public BpmnProcessmoveParticipant() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean initialize(Object element) {
		this.fElement = element;
		this.BpmnfArguments =  this.getArguments() ;
		return true;
	}
	protected String[] getSupportedExtensions() {
		return SUPPORTED_EXTENSIONS;
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

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws OperationCanceledException {
		RefactoringStatus status = new RefactoringStatus();
		if (!(fElement instanceof IFile)) {
			return null;
		}
		IFile file = (IFile) fElement;
		if (!isValidProject()) {
			return null;
		}
		fBpmnProjectName = file.getProject().getName() ;
		if (IndexUtils.getFileType(file) == null) {
			return status; // object is not an entity
		}
		
		if(IndexUtils.isJavaType(file)){
			if( !StudioJavaUtil.isTargetJavaSourceFolder(getResource(),refacObj.getNewElementPath())){
				status.addFatalError("The Destination Folder is not a valid JavaSource Folder.Please choose a different one");
				return status;
			}
		}
		
		status.addWarning("Moving entities is not recommended, as cross references might be broken.");
		return status;
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		// TODO Auto-generated method stub
		return null;
	}

	protected IResource getResource() {
		IResource resource = null;
		if (fElement instanceof IAdaptable) {
			resource = (IResource) ((IAdaptable) fElement).getAdapter(IResource.class);
		} else if (fElement instanceof IResource) {
			resource = (IResource) fElement;
		}
		return resource;
	}

	protected boolean isValidProject() {
		IResource resource = getResource();
		if (resource != null) {
			return CommonUtil.isStudioProject(getResource().getProject());
		}
		return false;
	}
	BpmnProcessRefactoringParticipant refacObj = new BpmnProcessRefactoringParticipant(){
		@Override
		public String getNewElementPath() {
			MoveArguments arguments = (MoveArguments) BpmnfArguments;
			Folder fld = (Folder) arguments.getDestination() ;
			String path = (String) fld.getFullPath().toPortableString();
			int idx = path.lastIndexOf('.');
			if (idx > 0) {
				// strip file extension
				path = path.substring(0, idx);
			}
			if (!path.startsWith("/")) {
				path = "/" + path;
			}
			if (!path.endsWith("/")) {
				path = path + "/";
			}
			if ( path.contains( fBpmnProjectName ) ) {
				path = path.substring(fBpmnProjectName.length()+1, path.length() ) ;
			}
			return path;
		}
	} ;
	@Override
	public Change createPreChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		CompositeChange compositeChange = new CompositeChange("Process changes:");
		BpmnProcessRefactoringParticipant.BpmnProcessFileChangeHelper helper = refacObj.new BpmnProcessFileChangeHelper( ){
			@Override
			public void handleProcessRefactor(CompositeChange compositeChange, IFile file) throws Exception{
				EObject loadBpmnProcess = refacObj.loadBpmnProcess(file, null);
				EObjectWrapper<EClass, EObject> processToBeChanged = EObjectWrapper.wrap(loadBpmnProcess); 
				String oldId = processToBeChanged.getAttribute(BpmnMetaModelConstants.E_ATTR_ID);
				
				 	String oldPath = ( String ) processToBeChanged.getAttribute( BpmnMetaModelConstants.E_ATTR_FOLDER ) ;
				 	String oldEltName = (String ) processToBeChanged.getAttribute( BpmnMetaModelConstants.E_ATTR_NAME ) ;
					String newElementPath = refacObj.getNewElementPath();
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
				
				
			
//					String oldPath = refacObj.getOldElementPath();
					String newPath = refacObj.getNewElementPath( ) + oldEltName ;
					oldPath = oldPath + "/"+oldEltName ;
					newPath=newPath.replace(".beprocess", "");
					Map<String, String> nameSpacemapper = new HashMap<String, String>();
					nameSpacemapper.put(RDFTnsFlavor.BE_NAMESPACE+oldPath, RDFTnsFlavor.BE_NAMESPACE+newPath);
					BpmnProcessRefactiongForXsltHelper.handleProcessRefactor(processToBeChanged.getEInstance(), oldPath, newPath, nameSpacemapper);
				
				
				refactorProcessReferences(fBpmnProjectName, compositeChange, processToBeChanged, oldId, file);
				Change change = refacObj.createTextFileChange(file, processToBeChanged, null);
				compositeChange.add(change);
			}
			
		};			
			
				
		IResource resource = getResource();
		this.fBpmnProjectName = resource.getProject().getName();
		if (!isValidProject()) {
			return null;
		}
		if (resource instanceof IProject) {
			helper.handleProjectRefactor(compositeChange, fBpmnProjectName, pm);
			Change change = null;
			if(compositeChange.getChildren().length > 0)
				change = compositeChange;
			return change;
		}

		if (resource instanceof IFolder) {
			helper.handleFolderRefactor(compositeChange, fBpmnProjectName, pm);
			Change change = null;
			if(compositeChange.getChildren().length > 0)
				change = compositeChange;
			return change;
		}
		
		if (!(resource instanceof IFile)) {
			return null;
		}
		IFile file = (IFile) resource;
		if (!isSupportedResource(file)) {
			return null;
		}
		
//		if (!shouldUpdateReferences()) {
//			return null;
//		}
//		
//		if (isContainedDelete(file)) {
//			return null;
//		}
		

		try {
			helper.handleProcessRefactor(compositeChange, file);
		} catch (Exception e) {
			BpmnCorePlugin.log(e);
		}
		
		Change change = null;
		if(compositeChange.getChildren().length > 0)
			change = compositeChange;
		
		return change;
	}
	

}
