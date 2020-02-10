package com.tibco.cep.studio.dashboard.ui.refactoring;

import static com.tibco.cep.studio.core.utils.ModelUtils.getPersistenceOptions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.text.edits.ReplaceEdit;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.search.SearchUtils;
import com.tibco.cep.studio.core.util.ResourceHelper;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.util.DashboardCoreResourceUtils;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.utils.DashboardResourceUtils;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;
import com.tibco.cep.studio.dashboard.utils.SystemElementsCreator;

public class SystemElementsRefactoringParticipant extends DashboardRefactoringParticipant {

	private static final String[] AFFECTED_ELEMENT_TYPES = new String[]{BEViewsElementNames.CHART_COLOR_SET, BEViewsElementNames.VIEW};

	private Collection<EObject> systemElements = null;
	private boolean changed = false;

	public SystemElementsRefactoringParticipant() {
		super("system", "");
	}

	//Modified to fix BE-8264
	@Override
	protected RefactoringStatus checkDeleteConditions(IProgressMonitor pm) {
		RefactoringStatus status = super.checkDeleteConditions(pm);
		if (isAffectedResourcesEmpty() == true) {
			IProject project = getResource().getProject();
			for (String elementType : AFFECTED_ELEMENT_TYPES) {
				try {
					List<LocalElement> children = LocalECoreFactory.getInstance(project).getChildren(elementType);
					if (children.isEmpty() == false){
						addAffectedResource(SearchUtils.getFile(children.get(0).getEObject()));
						break;
					}
				} catch (Exception e) {
					//do nothing
				}
			}
		}
		return status;
	}

	@Override
	protected BEViewsElement preProcessEntityChange(BEViewsElement renameParticipant) {
		//load all system skin elements
		IResource resource = getResource();
		EList<EObject> eObjects = DashboardCoreResourceUtils.loadMultipleElements(ResourceHelper.getLocationURI(resource).getPath());
		systemElements = EcoreUtil.copyAll(eObjects);
		changed = false;
		return super.preProcessEntityChange(renameParticipant);
	}

	@Override
	protected void preProcessMoveEntityChange(BEViewsElement renameParticipant) {
		//process a move change
		String newElementPath = getNewElementPath();
		for (EObject element : systemElements) {
			Entity entity = (Entity) element;
			entity.setFolder(newElementPath);
			entity.setNamespace(newElementPath);
		}
		changed = true;
	}

	/**
	 * Creates a <code>Change</code> object given the IFile and Entity. Simply
	 * serializes the Entity and creates a ReplaceEdit for the IFile. This is
	 * generally used after processing the entity, to create the actual
	 * refactoring change to take place
	 *
	 * @param file
	 * @param refactorParticipant
	 * @return
	 * @throws CoreException
	 */
	protected Change createTextFileChange(IFile file, Entity refactorParticipant) throws CoreException {
		if (changed == true){
			//create a custom text file change using the pre-loaded system skin elements
			return createTextFileChange(file, systemElements);
		}
		return null;
	}

	@Override
	protected void updateReferences(EObject object, String projectName, CompositeChange compositeChange) throws Exception {
		//do nothing
		return;
	}

	@Override
	protected String changeTitle() {
		return "System Skin Changes:";
	}

	@Override
	protected boolean isElementOfInterest(String elementType) {
		return false;
	}

	@Override
	public IStatus pasteElement(String newName, IResource resource, IContainer target, boolean overwrite, IProgressMonitor pm) throws CoreException, OperationCanceledException {
		if (resource.getProject().equals(target.getProject()) == true) {
			return new Status(IStatus.ERROR, DashboardUIPlugin.PLUGIN_ID, "Only one system element can exist in a project");
		}
    	if (resource instanceof IFile && IndexUtils.isEntityType((IFile)resource)) {
    		IFile file = target.getFile(new Path(newName));
    		if (file.exists() == true && overwrite == false){
    			return new Status(IStatus.WARNING,DashboardUIPlugin.PLUGIN_ID,"cannot copy "+newName+" over "+file.getFullPath());
    		}
    		try {
	    		String folder = target instanceof IProject ? "/" : "/"+target.getFullPath().removeFirstSegments(1).toString()+"/";
				List<Entity> systemElements = new SystemElementsCreator(target.getProject().getName(),folder,folder).create();
				DashboardCoreResourceUtils.persistEntities(systemElements, file.getFullPath().toString(), pm);
			} catch (IOException e) {
				throw new CoreException(new Status(IStatus.ERROR,DashboardUIPlugin.PLUGIN_ID,"could not save "+file.getFullPath(),e));
			} catch (Exception e) {
				throw new CoreException(new Status(IStatus.ERROR,DashboardUIPlugin.PLUGIN_ID,"could not save "+file.getFullPath(),e));
			}
    	}
		return null;
	}

	@Override
	protected void saveEntity(String newName, Entity entity, IContainer target, boolean overwrite) throws CoreException {
		//do nothing
	}

	@Override
	protected void updateProjectReference(EObject object, String projectName, CompositeChange compositeChange) throws Exception {
		String newElementName = getNewElementName();
		IProject project = getResource().getProject();
		IFile file = findFile(project);
		if (file != null) {
			EList<EObject> eObjects = DashboardCoreResourceUtils.loadMultipleElements(file.getFullPath().toOSString());
			Collection<EObject> eObjectCopies = EcoreUtil.copyAll(eObjects);
			for (EObject eObjectCopy : eObjectCopies) {
				Entity entity = (Entity) eObjectCopy;
				entity.setOwnerProjectName(newElementName);
			}
			Change change = createTextFileChange(file, eObjectCopies);
			compositeChange.add(change);
		}
	}

	private IFile findFile(IContainer container) throws CoreException {
		IResource[] members = container.members();
		for (IResource member : members) {
			if (member instanceof IFile) {
				if ("system".equals(((IFile) member).getFileExtension()) == true) {
					return (IFile) member;
				}
			} else if (member instanceof IContainer) {
				IFile file = findFile((IContainer) member);
				if (file != null) {
					return file;
				}
			}
		}
		return null;
	}

	protected Change createTextFileChange(IFile file, Collection<EObject> changedObjects) throws CoreException {
		//create a fake in-memory resource
		Resource resource = new XMIResourceImpl(URI.createURI(""));
		//add all changed contents to the resource
		resource.getContents().addAll(changedObjects);
		//serialize the resource
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		String contents = null;
		try {
			resource.save(os, getPersistenceOptions());
			byte[] byteContents = os.toByteArray();
			contents = new String(byteContents, file.getCharset());
		} catch (IOException ioex){
			throw new CoreException(new Status(IStatus.ERROR,DashboardUIPlugin.PLUGIN_ID,"could not generate changed contents of "+file.getFullPath(),ioex));
		} finally {
			try {
				os.close();
			} catch (IOException ignore) {
			}
		}
		InputStream fis = file.getContents();
		try {
			TextFileChange change = new TextFileChange("Content Change", file);
			int size = IndexUtils.getFileSize(file, fis);
			ReplaceEdit edit = new ReplaceEdit(0, size, contents);
			change.setEdit(edit);
			return change;
		} catch (IOException ioex) {
			throw new CoreException(new Status(IStatus.ERROR,DashboardUIPlugin.PLUGIN_ID,"could not read contents of "+file.getFullPath(),ioex));
		} finally {
			try {
				fis.close();
			} catch (IOException ignore) {
			}
		}
	}
}
