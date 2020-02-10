package com.tibco.cep.studio.ui.navigator.providers;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

public class StudioNavigatorRefactorActionProvider extends CommonActionProvider {

	private ActionGroup fRefactoringActionGroup;

	public StudioNavigatorRefactorActionProvider() {
	}

	@Override
	public ActionContext getContext() {
		return fRefactoringActionGroup.getContext();
	}

	@Override
	public void setContext(ActionContext context) {
		if(fRefactoringActionGroup!=null && context !=null){
			fRefactoringActionGroup.setContext(context);
		}
	}

	@Override
	public void fillActionBars(IActionBars actionBars) {
		fRefactoringActionGroup.fillActionBars(actionBars);
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {
		fRefactoringActionGroup.fillContextMenu(menu);
	}

	@Override
	public void updateActionBars() {
		fRefactoringActionGroup.updateActionBars();
	}

	@Override
	public void init(ICommonActionExtensionSite site) {
		super.init(site);
		fRefactoringActionGroup = new StudioNavigatorRefactorActionGroup(site);
	}

}
