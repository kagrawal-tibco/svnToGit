package com.tibco.cep.studio.core.refactoring;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.text.edits.ReplaceEdit;

import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.util.EntityNameHelper;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class StudioProjectRefactoringParticipant extends
		StudioRefactoringParticipant implements IStudioPasteParticipant {

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		return null;
	}

	@Override
	public Change createPreChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		if (isDeleteRefactor()) {
			return null;
		}
		if (isProjectRefactor()) {
			IResource resource = getResource();
			CompositeChange change = new CompositeChange("Project configuration changes");
			updateProjectConfigurationFile(resource, change);
			updateArtifactSummaryFile(resource, change);
			return change;
		}
		return super.createPreChange(pm);
	}

	private void updateArtifactSummaryFile(IResource resource,
			CompositeChange change) {
		// TODO Update .ars file with new name
		
	}

	private void updateProjectConfigurationFile(IResource resource,
			CompositeChange change) throws CoreException {
		StudioProjectConfiguration projectConfiguration = StudioProjectConfigurationManager.getInstance().getProjectConfiguration(resource.getProject().getName());
		if (projectConfiguration == null) {
			return;
		}
		projectConfiguration.setName(getNewElementName());
		IFile file = resource.getProject().getFile(StudioProjectConfigurationManager.STUDIO_PROJECT_CONFIG_FILENAME);
		TextFileChange textChange = new TextFileChange("Project configuration change", file);
		int len = -1;
		String newContents = null;
		InputStream contents = null;
		try {
			newContents = new String(IndexUtils.getEObjectContents(null, projectConfiguration), file.getCharset());
			contents = file.getContents();
			len = IndexUtils.getFileSize(file, contents);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				contents.close();
			} catch (IOException e) {
				// ignore
			}
		}
		if (len > 0 && newContents != null) {
			ReplaceEdit edit = new ReplaceEdit(0, len, newContents);
			textChange.setEdit(edit);
		}
		change.add(textChange);
	}

	@Override
	public String getName() {
		return "Project refactoring";
	}

	@Override
	public boolean isSupportedPaste(IResource resource, IContainer target) {
		return resource instanceof IProject;
	}

	@Override
	public IStatus pasteElement(String newName, IResource elementToPaste,
			IContainer target, boolean overwrite, IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IStatus validateName(IResource resource, IContainer target,
			String newName) {
		if (!EntityNameHelper.isValidBEProjectIdentifier(newName)) {
			return new Status(IStatus.ERROR, StudioCorePlugin.PLUGIN_ID, newName + " is not a valid BE identifier");
		}
		return Status.OK_STATUS;
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
