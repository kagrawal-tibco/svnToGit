package com.tibco.cep.sharedresource.refactor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.NullChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;

import com.tibco.cep.sharedresource.mqtt.MqttModelMgr;
import com.tibco.cep.studio.core.util.CommonUtil;

/**
 * @author ssinghal
 *
 */
public class MqttRefactoringParticipant extends AbstractSharedResourceRefactoring {
	
	public Change createPreChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		if (!isValidProject()) {
			return null;
		}
		if (isDeleteRefactor()) {
			return null;
		}

		IResource resource = getResource();

		if (((resource instanceof IFile)) && (!CommonUtil.isSharedResource(resource))) {
			return null;
		}

		if ((resource instanceof IFolder)) {
			Object elementToRefactor = getElementToRefactor();

			return processMqttConfig(elementToRefactor, this.fProjectName, pm);
		}

		if (!(resource instanceof IFile)) {
			return null;
		}
		IFile file = (IFile) getResource();

		if (!getExtension().equalsIgnoreCase(file.getFileExtension())) {
			return null;
		}
		Object elementToRefactor = getElementToRefactor();

		String name = resource.getName().replace("." + getExtension(), "");
		MqttModelMgr modelmgr = new MqttModelMgr(resource);

		modelmgr.parseModel();

		CompositeChange change = new CompositeChange("Changes to " + name);
		processMqttConfigElement(this.fProjectName, change, file, modelmgr, elementToRefactor);
		return change;
	}

	public RefactoringStatus checkConditions(IProgressMonitor pm, CheckConditionsContext context)
			throws OperationCanceledException {
		if (!isValidProject()) {
			return null;
		}
		if (isDeleteRefactor()) {
			return super.checkConditions(pm, context);
		}
		IResource resource = getResource();
		if (((resource instanceof IFile)) && (!CommonUtil.isSharedResource(resource))) {
			return null;
		}

		RefactoringStatus status = super.checkConditions(pm, context);
		if (!(resource instanceof IFile)) {
			return status;
		}
		IFile file = (IFile) resource;
		if ((isDeleteRefactor()) || (getExtension().equalsIgnoreCase(file.getFileExtension()))) {
			return status;
		}

		return status;
	}

	public Change createChange(IProgressMonitor pm) throws CoreException, OperationCanceledException {
		if (!isValidProject()) {
			return null;
		}
		IResource resource = getResource();
		if ((resource instanceof IFolder)) {
			return null;
		}

		if (((resource instanceof IFile)) && (!CommonUtil.isSharedResource(resource))) {
			return null;
		}

		Object elementToRefactor = getElementToRefactor();
		return processMqttConfig(elementToRefactor, this.fProjectName, pm);
	}

	public Change processMqttConfig(Object elementToRefactor, String projectName, IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		if (isDeleteRefactor()) {
			return new NullChange();
		}
		if (!shouldUpdateReferences()) {
			System.out.println("not updating references");
			return null;
		}
		CompositeChange compositeChange = new CompositeChange("Mqtt Configuration changes:");
		List<IFile> fileList = new ArrayList<IFile>();
		CommonUtil.getResourcesByExtension(getResource().getProject(), getExtension(), fileList);
		for (IFile element : fileList) {
			if (isFileProcessed(elementToRefactor, element)) {
				continue;
			}
			MqttModelMgr modelmgr = new MqttModelMgr(element);
			modelmgr.parseModel();
			processMqttConfigElement(this.fProjectName, compositeChange, element, modelmgr, elementToRefactor);
		}
		if ((compositeChange.getChildren() != null) && (compositeChange.getChildren().length > 0)) {
			return compositeChange;
		}
		return null;
	}

	public String getName() {
		return "Mqtt Configuration Refactoring participant";
	}

	private void processMqttConfigElement(String projectName, CompositeChange compositeChange, IFile sharedFile,
			MqttModelMgr modelmgr, Object elementToRefactor) throws CoreException {
		boolean changed = false;
		sharedFile.getName().replace("." + getExtension(), "");

		if (changed) {
			String changedText = modelmgr.saveModel();
			Change change = createTextFileChange(sharedFile, changedText);
			compositeChange.add(change);
		}
	}

	public String getExtension() {
		return "sharedmqttcon";
	}

}
