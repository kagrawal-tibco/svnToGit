package com.tibco.cep.bpmn.ui.refactoring;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RefactoringParticipant;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;

import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.core.refactoring.BpmnProcessRefactoringParticipant;
import com.tibco.cep.bpmn.core.utils.BpmnModelUtils;
import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.BpmnCommonModelUtils;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.designtime.core.model.java.JavaResource;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.refactoring.StudioRenameParticipantWrapper;
import com.tibco.cep.studio.core.util.RefactoringUtils;

public class JavaRenameParticipant extends StudioRenameParticipantWrapper {

	
	private RefactoringParticipant[] fRefactoringParticipants;

	protected Object fElement;
	protected String fProjectName;
	private IResource fresource;

	public JavaRenameParticipant() {
	}

	@Override
	public Change createPreChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		CompositeChange renameChange = new CompositeChange(
				"Java Method refactor:");
		getResource();
		RefactoringParticipant[] refactoringParticipants = getRefactoringParticipants();
		for (RefactoringParticipant refactoringParticipant : refactoringParticipants) {
			if (refactoringParticipant instanceof BpmnProcessRefactoringParticipant
					&& refactoringParticipant != null
					&& fElement instanceof IMethod) {
				JavaResource js = IndexUtils
						.loadJavaResource((IFile) getResource());
				refactorBpmnArtifact(js, renameChange);
				Change change = refactoringParticipant.createPreChange(pm);
				if (change != null && change.getAffectedObjects() != null
						&& change.getAffectedObjects().length > 0) {
					renameChange.add(change);
				}
			}

		}
		if (renameChange.getChildren() != null
				&& renameChange.getChildren().length > 0) {
			return renameChange;
		}
		return null;
	}
	
	private void refactorBpmnArtifact(JavaResource js,CompositeChange renameChange) {
		boolean change = false;
		List<EObject> allProcesses = BpmnProcessRefactoringParticipant
				.getAllProcess(null, fProjectName);
		for (EObject process : allProcesses) {
			IFile file = BpmnIndexUtils.getFile(fProjectName, process);
			List<EObject> allFlowNodes = BpmnModelUtils
					.getAllFlowNodes(process);
			allFlowNodes.add(0, process);
			for (EObject flowNode : allFlowNodes) {
				Object resource = BpmnModelUtils.getAttachedResource(flowNode);
				if (resource == null)
					continue;
				if (resource instanceof String && fElement instanceof IMethod) {
					String oldResourcePath = js.getFullPath();
					if (flowNode.eClass().equals(BpmnModelClass.JAVA_TASK)) {
						String javaFilePath = (String) BpmnCommonModelUtils
								.getAttachedResource(flowNode);
						String oldElementFullPathwoExt = oldResourcePath;
						if (oldResourcePath.contains(".")) {
							oldElementFullPathwoExt = oldResourcePath
									.split("\\.")[0];
						}
						if (oldElementFullPathwoExt.equals(javaFilePath)) {
							EObjectWrapper<EClass, EObject> flowNodeWrapper = EObjectWrapper
									.wrap(flowNode);
							EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper
									.getAddDataExtensionValueWrapper(flowNodeWrapper);
							String methodName = valueWrapper
									.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_METHOD_NAME);
							IMethod method = (IMethod) fElement;
							if (methodName.equals(method.getElementName())) {
								valueWrapper
										.setAttribute(
												BpmnMetaModelExtensionConstants.E_ATTR_METHOD_NAME,
												getArguments().getNewName());
							}
							change = true;

						}
					}
				}
			}
			if (change) {
				EObjectWrapper<EClass, EObject> processToBeChanged = EObjectWrapper.wrap(process);
				Change renamechange = null;
				try {
					renamechange = BpmnProcessRefactoringParticipant.createTextFileModification(file, processToBeChanged, null,false,null);
					renameChange.add(renamechange);
				} catch (CoreException e) {
					e.printStackTrace();
				}
				
			}
		}
	}

	
	protected IResource getResource() {
		IResource resource = null;
		if (fElement instanceof IMethod) {
			resource = (IResource) ((IMethod) fElement).getResource();
		} else if (fElement instanceof IResource) {
			resource = (IResource) fElement;
		}
		return resource;
	}
	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws OperationCanceledException {
		RefactoringStatus status = new RefactoringStatus();
		RefactoringParticipant[] refactoringParticipants = getRefactoringParticipants();
		for (RefactoringParticipant refactoringParticipant : refactoringParticipants) {
			RefactoringStatus st = refactoringParticipant.checkConditions(pm, context);
			if (st != null) {
				status.merge(st);
			}
		}
		return status;
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		CompositeChange renameChange = new CompositeChange("Element changes:");
		RefactoringParticipant[] refactoringParticipants = getRefactoringParticipants();
		for (RefactoringParticipant refactoringParticipant : refactoringParticipants) {
			if (refactoringParticipant instanceof BpmnProcessRefactoringParticipant
						&& refactoringParticipant != null  && fElement instanceof IMethod) {
			Change change = refactoringParticipant.createChange(pm);
			if (change != null && change.getAffectedObjects() != null && change.getAffectedObjects().length > 0) {
				renameChange.add(change);
			}
			if (renameChange.getChildren() != null && renameChange.getChildren().length > 0) {
				return renameChange;
			}
		}
		
		}
		return null;
	}
	
	

	protected void reset() {
		fElement = null;
		fProjectName = null;
	}
	@Override
	public String getName() {
		return "JavaSource Rename Participant";
	}

	public String getNewElementName() {
		RenameArguments arguments = (RenameArguments) getArguments();
		String newName = arguments.getNewName();
		int idx = newName.lastIndexOf('.');
		if (idx > 0) {
			// strip file extension
			newName = newName.substring(0, idx);
		}
		return newName;
	}
	@Override
	protected boolean initialize(Object element) {
//		RefactoringParticipant[] refactoringParticipants = getRefactoringParticipants();s
//		for (RefactoringParticipant refactoringParticipant : refactoringParticipants) {
//			refactoringParticipant.initialize(getProcessor(), element, getArguments());
//		}
		reset();
		this.fElement = element;
		fresource=getResource();
		if(fresource != null) {
			fProjectName = fresource.getProject().getName();
		}
		return true;
	}

	private RefactoringParticipant[] getRefactoringParticipants() {
		if (fRefactoringParticipants == null) {
			fRefactoringParticipants = RefactoringUtils.getRefactoringParticipants();
		}
		return fRefactoringParticipants;
	}
}
