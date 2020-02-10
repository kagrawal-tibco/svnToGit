package com.tibco.cep.bpmn.ui.refactoring;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor;
import org.eclipse.text.edits.ReplaceEdit;

import com.tibco.cep.bpmn.common.palette.model.BpmnCommonPaletteGroupEmfItemType;
import com.tibco.cep.bpmn.common.palette.model.BpmnCommonPaletteGroupItemType;
import com.tibco.cep.bpmn.core.BpmnCoreConstants;
import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.editor.BpmnMessages;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroup;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteModel;
import com.tibco.cep.bpmn.ui.search.BpmnPaletteSearchParticipant;
import com.tibco.cep.bpmn.ui.utils.BpmnPaletteResourceUtil;
import com.tibco.cep.studio.common.palette.DocumentRoot;
import com.tibco.cep.studio.common.palette.PaletteModel;
import com.tibco.cep.studio.common.util.EntityNameHelper;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.TypeElement;
import com.tibco.cep.studio.core.index.model.impl.FolderImpl;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.refactoring.IStudioPasteParticipant;
import com.tibco.cep.studio.core.refactoring.StudioRefactoringParticipant;
import com.tibco.cep.studio.core.search.ISearchParticipant;

/**
 * 
 * @author majha
 * 
 */
public class BpmnPaletteRefactoringParticipant extends
		StudioRefactoringParticipant implements IStudioPasteParticipant {
	private static final String[] SUPPORTED_EXTENSIONS = new String[] { BpmnCoreConstants.PALETTE_FILE_EXTN };
	private static String newElementName= new String();
	private static boolean nameRefactored = false;

	public BpmnPaletteRefactoringParticipant() {
		// TODO Auto-generated constructor stub
	}

	protected ISearchParticipant getSearchParticipant() {
		return new BpmnPaletteSearchParticipant();
	}

	@Override
	public boolean isSupportedPaste(IResource resource, IContainer target) {
		return BpmnCoreConstants.PALETTE_FILE_EXTN.equals(resource
				.getFileExtension());
	}

	@Override
	public IStatus pasteElement(String newName, IResource elementToPaste,
			IContainer target, boolean overwrite, IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		if (elementToPaste instanceof IFile
				&& isSupportedPaste(elementToPaste, target)) {
			IFile file = target.getFile(new Path(newName));
		//	BpmnPaletteModel model =new BpmnPaletteModel((BpmnPaletteResourceUtil.loadBpmnPalette(elementToPaste, null)).getModel());
			URI uri = URI.createPlatformResourceURI(file.getFullPath().toPortableString(), false);
			Resource resource = BpmnPaletteResourceUtil.getResource(uri);
			Map<Object, Object> options = new HashMap<Object, Object>();
			options.put(XMIResource.OPTION_ENCODING, "UTF-8");
			try {
				resource.load(elementToPaste.getLocationURI().toURL().openStream(),options);
			} catch (IOException e) {
				BpmnUIPlugin.log(e);
			}
			DocumentRoot dr = (DocumentRoot) resource.getContents().get(0);
			PaletteModel model = dr.getModel();
			if(newName.endsWith(".beprocesspalette")){
				model.setName(newName.substring(0, newName.indexOf(".")));
			} else {
				model.setName(newName);
			}
			BpmnPaletteModel bpmnPaletteModel = new BpmnPaletteModel(model);
			
			savePalette(newName, bpmnPaletteModel, target, overwrite);
		}

		return null;
	}
	
	protected void savePalette(String newName, BpmnPaletteModel model, IContainer target, boolean overwrite) throws CoreException {
		try {
			IFile file = target.getFile(new Path(newName));
			
			if (!file.exists() || overwrite){ 
				BpmnPaletteResourceUtil.saveBpmnPalette(model, file);
			}
		} catch (Exception e) {
			BpmnUIPlugin.log("Error while saving",e);
		} 
	}

	@Override
	public IStatus validateName(IResource resource, IContainer target,
			String newName) {
		if (!EntityNameHelper.isValidBEProjectIdentifier(newName)) {
			return new Status(IStatus.ERROR, BpmnUIPlugin.PLUGIN_ID, newName
					+ BpmnMessages.getString("bpmnPaletteRefactorParticipant_error_validIdentifier_message"));
		}
		return Status.OK_STATUS;
	}

	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws OperationCanceledException {
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
			boolean isDuplicateBEResource = BpmnPaletteResourceUtil
					.isDuplicateProcessResource(parentResource, newName,
							duplicateFileName);
			if (isDuplicateBEResource) {
				status.addFatalError(com.tibco.cep.studio.core.util.Messages
						.getString("BE_Resource_FilenameExists",
								duplicateFileName, newName));
			}
		}
	}

	protected boolean isSupportedResource(IFile file) {
		String[] extensions = getSupportedExtensions();
		if (extensions == null
				&& (file.getFileExtension() == null || file.getFileExtension()
						.length() == 0)) {
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

	
	public static String getModelName(BpmnPaletteModel model) {
		if(nameRefactored)
		return newElementName;
		else{
			return model.getName();
		}
	}
	
	public Change createPreChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		CompositeChange compositeChange = new CompositeChange(
				"Bpmn Palette changes:");
		if (!isValidProject()) {
			return null;
		}
		IResource resource = getResource();
		this.fProjectName = resource.getProject().getName();
		if (resource instanceof IProject) {
			return null;
		}

		if (resource instanceof IFolder) {
			return null;
		}

		if (!(resource instanceof IFile)) {
			return null;
		}
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
			BpmnPaletteModel model = BpmnPaletteResourceUtil.loadBpmnPalette(file, null);
			if (isRenameRefactor()) {
			    newElementName = getNewElementName();
			    nameRefactored =true;
				model.setName(newElementName);
				Change change = createTextFileChange(file, model);
				compositeChange.add(change);
			} 
			
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}

		Change change = null;
		if (compositeChange.getChildren().length > 0)
			change = compositeChange;

		return change;

	}
	
	
	private void handleForFolderRefactor(String projectName,
			CompositeChange compositeChange, FolderImpl refactoringElement) throws CoreException{
		EList<DesignerElement> entries = refactoringElement.getEntries();
		for (DesignerElement designerElement : entries) {
			handleArtifactsRefactorForFolder(projectName, compositeChange, designerElement);
		}
		IResource resource = getResource();
		if(resource instanceof IFolder){
			handleForFolderRefactorContainingProcess(projectName, compositeChange, (IFolder)resource);
		}
	}
	
	private void handleForFolderRefactorContainingProcess(String projectName,
			CompositeChange compositeChange, IFolder folder) throws CoreException{
		final List<IFile> processFiles = new ArrayList<IFile>();
		IResourceVisitor visitor = new IResourceVisitor() {
			
			@Override
			public boolean visit(IResource resource) throws CoreException {
				if (resource instanceof IContainer) {
					return true;
				}
				if (resource instanceof IFile) {
					if (BpmnIndexUtils.isBpmnType((IFile) resource)) {
						processFiles.add((IFile) resource);
					}
				}
				return false;
			}
		};
		
		folder.accept(visitor);
		for (IFile iFile : processFiles) {
			handleArtifactsRefactorForFolder(projectName, compositeChange, iFile);
		}
	}
	
	

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		if (!isValidProject() || isProjectRefactor() ) {
			return null;
		}

		IResource resource = getResource();
		if ( resource instanceof IProject) {
			return null; // these changes are done in the pre-change
		}
		Object elementToRefactor = getElementToRefactor();
		CompositeChange compositeChange = new CompositeChange(
				"Bpmn Palette changes:");

		if (!shouldUpdateReferences()) {
			return null;
		}
		
		if (elementToRefactor instanceof FolderImpl) {
			handleForFolderRefactor(fProjectName, compositeChange,
					(FolderImpl) elementToRefactor);
		} else if(elementToRefactor instanceof IFolder)
			handleForFolderRefactorContainingProcess(fProjectName, compositeChange, (IFolder)elementToRefactor);
		else if (elementToRefactor instanceof TypeElement)
			handleArtifactsRefactor(fProjectName, compositeChange,
					elementToRefactor);
		else if (elementToRefactor instanceof IFile && ((IFile)elementToRefactor).getFileExtension() != null &&  ((IFile)elementToRefactor).getFileExtension().equals("beprocess"))
			handleArtifactsRefactor(fProjectName, compositeChange,
					elementToRefactor);

		Change change = null;
		if (compositeChange.getChildren().length > 0)
			change = compositeChange;

		return change;
	}

	private void handleArtifactsRefactor(String projectName,
			CompositeChange compositeChange, Object refactoringElement)
			throws CoreException {
		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName);
		Map<IFile, BpmnPaletteModel> allPalettes = BpmnPaletteResourceUtil.getAllPalettes(project);
		Set<IFile> keySet = allPalettes.keySet();
		for (IFile f : keySet) {
			BpmnPaletteModel model = allPalettes.get(f);
			boolean changed = false;
			List<BpmnPaletteGroup> bpmnPaletteGroups = model
					.getBpmnPaletteGroups();
			for (BpmnPaletteGroup bpmnPaletteGroup : bpmnPaletteGroups) {
				List<BpmnPaletteGroupItem> paletteItems = bpmnPaletteGroup
						.getPaletteItems();
				for (BpmnPaletteGroupItem bpmnPaletteGroupItem : paletteItems) {
					String attachedResource = bpmnPaletteGroupItem
							.getAttachedResource();
					if(attachedResource.trim().isEmpty())
						continue;
					if (refactoringElement instanceof TypeElement) {
						boolean found = false;
						BpmnCommonPaletteGroupItemType itemType = bpmnPaletteGroupItem
								.getItemType();
						if (itemType instanceof BpmnCommonPaletteGroupEmfItemType) {
							BpmnCommonPaletteGroupEmfItemType emfType = (BpmnCommonPaletteGroupEmfItemType) itemType;
							EClass modelType = BpmnMetaModel.getInstance()
									.getEClass(emfType.getEmfType());
							if (modelType
									.equals(BpmnModelClass.INFERENCE_TASK)) {
								if (attachedResource
										.contains(getOldElementFullPath())) {
									String fullPath = null;
									found = true;
									if (isMoveRefactor()) {
										fullPath = getNewElementPath()
												+ getOldElementName();
									} else if (isRenameRefactor()) {
										fullPath = getOldElementPath()
												+ getNewElementName();
									}else if(isDeleteRefactor()){
										fullPath = "";
									}
									if (fullPath != null){
										changed = true;
										attachedResource = attachedResource.replace(
												getOldElementFullPath(),
												fullPath);
										if(fullPath.isEmpty())
											attachedResource = attachedResource.replace(",,", ",");
										
										bpmnPaletteGroupItem.setAttachedResource(attachedResource);
									}
								}
							}
						}
						if (!found
								&& attachedResource
										.equals(getOldElementFullPath())) {
							String fullPath = null;
							if (isMoveRefactor()) {
								fullPath = getNewElementPath()
										+ getOldElementName();
							} else if (isRenameRefactor()) {
								fullPath = getOldElementPath()
										+ getNewElementName();
							}else if(isDeleteRefactor()){
								fullPath = "";
							}
							if (fullPath != null){
								changed = true;
								bpmnPaletteGroupItem
										.setAttachedResource(fullPath);
							}
						}

					} else if (refactoringElement instanceof IFile
							&& ((IFile) refactoringElement).getFileExtension()
									.equals("beprocess")) {
						IFile processFile = (IFile) refactoringElement;
						String name = processFile.getName().replace(
								".beprocess", "");
						String fPath = IndexUtils.getFullPath(processFile);
						if (processFile != null && processFile.exists()) {
							if (attachedResource.equals(fPath)) {
								String fullPath = null;
								if (isMoveRefactor()) {
									fullPath = getNewElementPath() + name;
								} else if (isRenameRefactor()) {
									fullPath = fPath.replace(name,
											getNewElementName());
								}else if(isDeleteRefactor()){
									fullPath = "";
								}
								if (fullPath != null) {
									changed = true;
									bpmnPaletteGroupItem
											.setAttachedResource(fullPath);
								}
							}
						}
					}

				}
			}
			if (changed) {
				Change change = createTextFileChange(f, model);
				compositeChange.add(change);
			}

		}
	}
	
	private void handleArtifactsRefactorForFolder(String projectName,
			CompositeChange compositeChange, Object refactoringElement)
			throws CoreException {
		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName);
		Map<IFile, BpmnPaletteModel> allPalettes = BpmnPaletteResourceUtil.getAllPalettes(project);
		Set<IFile> keySet = allPalettes.keySet();
		for (IFile f : keySet) {
			BpmnPaletteModel model = allPalettes.get(f);
			boolean changed = false;
			List<BpmnPaletteGroup> bpmnPaletteGroups = model
					.getBpmnPaletteGroups();
			for (BpmnPaletteGroup bpmnPaletteGroup : bpmnPaletteGroups) {
				List<BpmnPaletteGroupItem> paletteItems = bpmnPaletteGroup
						.getPaletteItems();
				for (BpmnPaletteGroupItem bpmnPaletteGroupItem : paletteItems) {
					String attachedResource = bpmnPaletteGroupItem
							.getAttachedResource();
					if(attachedResource.trim().isEmpty())
						continue;
					if (refactoringElement instanceof TypeElement) {
						TypeElement ele = (TypeElement)refactoringElement;
						boolean found = false;
						BpmnCommonPaletteGroupItemType itemType = bpmnPaletteGroupItem
								.getItemType();
						if (itemType instanceof BpmnCommonPaletteGroupEmfItemType) {
							BpmnCommonPaletteGroupEmfItemType emfType = (BpmnCommonPaletteGroupEmfItemType) itemType;
							EClass modelType = BpmnMetaModel.getInstance()
									.getEClass(emfType.getEmfType());
							if (modelType
									.equals(BpmnModelClass.INFERENCE_TASK)) {
								if (attachedResource
										.contains(getOldElementPath()+"/"+ele.getName())) {
									String fullPath = null;
									found = true;
									if (isMoveRefactor()) {
										fullPath = getNewElementPath()+  getOldElementName()
												+ "/"+ele.getName();
									} else if (isRenameRefactor()) {
										fullPath = getOldElementPath().replace(getOldElementName(), getNewElementName()) 
												+ "/"+ele.getName();
									}else if(isDeleteRefactor()){
										fullPath = "";
									}
									if (fullPath != null){
										changed = true;
										attachedResource = attachedResource.replace(
												getOldElementPath()+"/"+ele.getName(),
												fullPath);
										if(fullPath.isEmpty())
											attachedResource = attachedResource.replace(",,", ",");
										
										bpmnPaletteGroupItem.setAttachedResource(attachedResource);
									}
								}
							}
						}
						if (!found
								&& attachedResource
										.equals(getOldElementPath()+"/"+ele.getName())) {
							String fullPath = null;
							if (isMoveRefactor()) {
								fullPath = getNewElementPath()+ getOldElementName()
										+ "/"+ele.getName();
							} else if (isRenameRefactor()) {
								fullPath = getOldElementPath().replace(getOldElementName(), getNewElementName()) 
										+ "/"+ele.getName();
							}else if(isDeleteRefactor()){
								fullPath = "";
							}
							if (fullPath != null){
								changed = true;
								bpmnPaletteGroupItem
										.setAttachedResource(fullPath);
							}
						}

					} else if (refactoringElement instanceof IFile
							&& ((IFile) refactoringElement).getFileExtension()
									.equals("beprocess")) {
						IFile processFile = (IFile) refactoringElement;
						String name = processFile.getName().replace(
								".beprocess", "");
						String fPath = IndexUtils.getFullPath(processFile);
						if (processFile != null && processFile.exists()) {
							if (attachedResource.equals(fPath)) {
								String fullPath = null;
								if (isMoveRefactor()) {
									fullPath = getNewElementPath()
											+ getOldElementName() + "/" + name;
								} else if (isRenameRefactor()) {
									fullPath = getOldElementPath().replace(
											getOldElementName(),
											getNewElementName())
											+ "/" + name;
								}else if(isDeleteRefactor()){
									fullPath = "";
								}
								if (fullPath != null) {
									changed = true;
									bpmnPaletteGroupItem
											.setAttachedResource(fullPath);
								}
							}
						}
					}

				}
			}
			if (changed) {
				Change change = createTextFileChange(f, model);
				compositeChange.add(change);
			}

		}
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
	protected Change createTextFileChange(IFile file, BpmnPaletteModel paletteModel)
			throws CoreException {
		
		//To restrict references to be refactored from Project Library 
		if(!file.exists()){
			return null;
		}
		if (isDeleteRefactor() && isDeleted(file)) {
			// if this file is already getting deleted, then don't create a text file change for it
			return null;
		}
		byte[] objectContents = BpmnPaletteResourceUtil.getBpmnPaletteContents(paletteModel, file);
		TextFileChange change = null;
		InputStream fis = null;
		try {
			
			String contents = new String(objectContents, file.getCharset());
			
			change = new TextFileChange("Bpmn palette change", file);
			fis = file.getContents();
			int size = IndexUtils.getFileSize(file, fis);
			ReplaceEdit edit = new ReplaceEdit(0, size, contents);
			change.setEdit(edit);
			
		} catch (IOException e) {
			BpmnUIPlugin.log(e);
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				BpmnUIPlugin.log(e);
			}
		}
		return change;
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
	public String getName() {
		// TODO Auto-generated method stub
		return null;
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
