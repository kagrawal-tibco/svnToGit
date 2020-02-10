package com.tibco.cep.studio.ui.refactoring;

import org.eclipse.emf.common.util.EList;
import org.eclipse.ltk.core.refactoring.Refactoring;

import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.ui.actions.EntityRenameElementAction;

/**
 * 
 * @author sasahoo
 *
 */
public class EntityRenameElementRefactoringWizard extends RenameElementRefactoringWizard {

	private EList<PropertyDefinition> list;
	private EntityRenameElementAction action;
	/**
	 * @param refactoring
	 * @param flags
	 * @param list
	 */
	public EntityRenameElementRefactoringWizard(Refactoring refactoring, int flags, EntityRenameElementAction action) {
		super(refactoring, flags);
		this.action = action;
	}
	
	@Override
	protected void addUserInputPages() {
		fRenameEntityPage = new EntityRenameElementRefactoringPage("Rename element", action);
		addPage(fRenameEntityPage);
	}

}
