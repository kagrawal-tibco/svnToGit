package com.tibco.cep.studio.ui.actions;

import org.eclipse.emf.common.util.EList;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;

import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.core.refactoring.EntityElementRefactoring;
import com.tibco.cep.studio.ui.refactoring.EntityRenameElementRefactoringWizard;
import com.tibco.cep.studio.ui.refactoring.RenameElementRefactoringWizard;

/**
 * 
 * @author sasahoo
 *
 */
public class EntityRenameElementAction extends RenameElementAction {
	
	private EList<PropertyDefinition> list;
	
	public EntityRenameElementAction(EList<PropertyDefinition> list) {
		super();
		this.list = list;
	}

	public EList<PropertyDefinition> getList() {
		return list;
	}

	public void setList(EList<PropertyDefinition> list) {
		this.list = list;
	}

	protected RenameElementRefactoringWizard invokeRefactoringWizard(EntityElementRefactoring refactoring) {
		return new EntityRenameElementRefactoringWizard(refactoring, RefactoringWizard.DIALOG_BASED_USER_INTERFACE, this);
	}
	
}
