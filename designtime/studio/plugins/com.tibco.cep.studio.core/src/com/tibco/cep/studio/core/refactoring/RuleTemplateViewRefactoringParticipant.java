package com.tibco.cep.studio.core.refactoring;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.rule.RuleTemplate;
import com.tibco.cep.designtime.core.model.rule.RuleTemplateView;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.search.EntitySearchParticipant;
import com.tibco.cep.studio.core.search.RuleTemplateViewSearchParticipant;

public class RuleTemplateViewRefactoringParticipant extends EntityRefactoringParticipant<RuleTemplateView> {

	private static final String[] SUPPORTED_EXTENSIONS = new String[] { CommonIndexUtils.RULE_TEMPLATE_VIEW_EXTENSION };

	public RuleTemplateViewRefactoringParticipant() {
		super();
	}

	@Override
	protected EntitySearchParticipant getSearchParticipant() {
		return new RuleTemplateViewSearchParticipant();
	}

	@Override
	protected String[] getSupportedExtensions() {
		return SUPPORTED_EXTENSIONS;
	}

	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws OperationCanceledException {
		if (isDeleteRefactor()) {
			// checks for references to the deleted element(s)
			return super.checkConditions(pm, context);
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

	@Override
	protected RuleTemplateView preProcessEntityChange(RuleTemplateView refactorParticipant) {
		if (!(refactorParticipant instanceof RuleTemplateView)) {
			return super.preProcessEntityChange(refactorParticipant);
		}
		
		CompositeChange compositeChange = new CompositeChange("");
		try {
			return processRTView(refactorParticipant.getOwnerProjectName(), compositeChange, refactorParticipant, getElementToRefactor(), true);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return refactorParticipant;
	}

	@Override
	public Change processEntity(Object elementToRefactor, String projectName, IProgressMonitor pm, boolean preChange) throws CoreException,
			OperationCanceledException {
		if (elementToRefactor instanceof EntityElement) {
			elementToRefactor = ((EntityElement) elementToRefactor).getEntity();
		}
		if (!shouldUpdateReferences()) {
			System.out.println("not updating references");
			return null;
		}
		CompositeChange compositeChange = new CompositeChange("Rule Template View changes:");
		List<Entity> allRuleTemplateView = IndexUtils.getAllEntities(projectName, new ELEMENT_TYPES[] { ELEMENT_TYPES.RULE_TEMPLATE_VIEW });
		for (Entity entity : allRuleTemplateView) {
			if (entity == elementToRefactor) {
				// already processed in the pre-change
				continue;
			}
			processRTView(projectName, compositeChange, (RuleTemplateView) entity, elementToRefactor, preChange);
		}
		if (compositeChange.getChildren() != null && compositeChange.getChildren().length > 0) {
			return compositeChange;
		}
		return null;
	}

	private RuleTemplateView processRTView(String projectName,
			CompositeChange compositeChange, RuleTemplateView ruleTemplateView, Object elementToRefactor, boolean isPreProcess) throws CoreException {
		// must be sure to copy the element before making changes, otherwise canceling the wizard will keep the changes made and corrupt the elements
		IFile file = IndexUtils.getFile(projectName, ruleTemplateView);
		ruleTemplateView = (RuleTemplateView) EcoreUtil.copy(ruleTemplateView);
		boolean changed = false;
		if (isContainedDelete(file)) {
			return ruleTemplateView;
		}
		if (isProjectRefactor()) {
			processChildren(projectName, ruleTemplateView, getNewElementName());
			changed = true;
		}
		if (elementToRefactor instanceof EntityElement) {
			elementToRefactor = ((EntityElement) elementToRefactor).getEntity();
		}
		if (elementToRefactor instanceof RuleElement) {
			elementToRefactor = ((RuleElement) elementToRefactor).getRule();
		}
		if (processFolder(ruleTemplateView)) {
			changed = true;
		}
		if (elementToRefactor instanceof RuleTemplate || isFolderRefactor()) {
			boolean needsChange = false;
			String rtPath = ruleTemplateView.getRuleTemplatePath();
			if (elementToRefactor instanceof RuleTemplate) {
				RuleTemplate refactoredElement = (RuleTemplate) elementToRefactor;
				if (refactoredElement.getFullPath().equals(rtPath)) {
					if (isRenameRefactor()) {
						rtPath = refactoredElement.getFolder()+getNewElementName();
						needsChange = true;
					} else if (isMoveRefactor()) {
						rtPath = getNewElementPath()+refactoredElement.getName();
						needsChange = true;
					}else if(isDeleteRefactor()){
						rtPath = "";
						needsChange = true;
					}
				}
			} else if (isFolderRefactor()) {
				IFolder folder = (IFolder) getResource();
				if (IndexUtils.startsWithPath(rtPath, folder)) {
					rtPath = getNewPath(rtPath, folder);
					needsChange = true;
				}
			}
			if (needsChange) {
				ruleTemplateView.setRuleTemplatePath(rtPath);
				changed = true;
			}
		}

		if (changed) {
			Change change = createTextFileChange(file, ruleTemplateView);
			compositeChange.add(change);
		}
		return ruleTemplateView;
	}

}
