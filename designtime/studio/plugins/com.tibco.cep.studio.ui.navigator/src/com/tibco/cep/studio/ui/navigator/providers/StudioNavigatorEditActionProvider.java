package com.tibco.cep.studio.ui.navigator.providers;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

public class StudioNavigatorEditActionProvider extends CommonActionProvider {

	private ActionGroup editActionGroup;

	public StudioNavigatorEditActionProvider() {
	}

	@Override
	public void fillActionBars(IActionBars actionBars) {
		editActionGroup.fillActionBars(actionBars);
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {
		editActionGroup.fillContextMenu(menu);
	}

	@Override
	public ActionContext getContext() {
		return editActionGroup.getContext();
	}

	@Override
	public void setContext(ActionContext context) {
		editActionGroup.setContext(context);
	}

	@Override
	public void updateActionBars() {
		editActionGroup.updateActionBars();
	}

	@Override
	public void init(ICommonActionExtensionSite site) {
		super.init(site);
		editActionGroup = new StudioNavigatorEditActionGroup(site);
	}

}
