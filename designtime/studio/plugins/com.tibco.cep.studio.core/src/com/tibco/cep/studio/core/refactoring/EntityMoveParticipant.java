package com.tibco.cep.studio.core.refactoring;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.MoveArguments;
import org.eclipse.ltk.core.refactoring.participants.MoveParticipant;
import org.eclipse.text.edits.ReplaceEdit;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.util.ResourceHelper;

public class EntityMoveParticipant extends MoveParticipant {

	private Object fElement;
	private static final String[] NON_SUPPORTED_EXTENSIONS = new String[] { "beprocess" };

	public EntityMoveParticipant() {
	}

	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws OperationCanceledException {
		RefactoringStatus status = new RefactoringStatus();
		if (!(fElement instanceof IFile)) {
			return null;
		}
		IFile file = (IFile) fElement;
		if ( file != null && isNonSupportedType( file ) ) {
			 return null ;
		}
		if (IndexUtils.getFileType(file) == null) {
			return status; // object is not an entity
		}
		status.addWarning("Moving entities is not recommended, as cross references might be broken.");
		return status;
	}
	private boolean isNonSupportedType( IFile file ) {
		
		String fileExt = file.getFileExtension();
		for (String ext : NON_SUPPORTED_EXTENSIONS) {
			if ( ext.equalsIgnoreCase( fileExt ) ) {
				return true;
			}
		}
		return false ;
	}
	@Override
	public Change createPreChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		EObject object = null;
		if (!(fElement instanceof IFile)) {
			return null;
		}
		IFile file = (IFile) fElement;
		if (!CommonUtil.isStudioProject(file.getProject())) {
			return null;
		}
		if (IndexUtils.getFileType(file) == null) {
			return null; // object is not an entity
		}
		if (IndexUtils.isRuleType(file)) {
			return null;
		}
		if(IndexUtils.isJavaType(file)){
			object = IndexUtils.loadJavaEntity(file);
			StudioJavaSourceRefactoringParticipant p = new StudioJavaSourceRefactoringParticipant();
			p.initialize(this.getProcessor(), file, getArguments());
			return p.createPreChange(pm);
		} else{
			object = IndexUtils.loadEObject(ResourceHelper.getLocationURI(file));
		}
		if (!(object instanceof Entity)) {
			return null;
		}
		Entity entity = (Entity) object;
		MoveArguments arguments = getArguments();
		Object destination = arguments.getDestination();
		String newPath = IndexUtils.getFullPath((IResource)destination);
		if (!newPath.endsWith("/")) {
			newPath += "/";
		}
		entity.setFolder(newPath);
		entity.setNamespace(newPath);
		byte[] objectContents;
		TextFileChange change = null;
		InputStream fis = null;
		try {
			//Modified by Anand on 04/29/2009 to fix 1-AP56AF & 1-AMFJDZ
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(entity.getOwnerProjectName());
			IPath projectLocation = project.getLocation();
			IPath path = projectLocation.append(entity.getFolder());
			objectContents = IndexUtils.getEObjectContents(path.toString(),entity);
			change = new TextFileChange("New name change", file);
			fis = file.getContents();
			int size = IndexUtils.getFileSize(file, fis);
			ReplaceEdit edit = new ReplaceEdit(0, size, new String(objectContents, file.getCharset()));
			change.setEdit(edit);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return change;
	}

	@Override
	public String getName() {
		return "Entity rename participant";
	}

	@Override
	protected boolean initialize(Object element) {
		this.fElement = element;
		return true;
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		return null;
	}

}
