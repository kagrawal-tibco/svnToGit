package com.tibco.cep.studio.dashboard.ui.actiondelegate;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate;

import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;

public class GlobalConfigurationWizardsAction extends CommonWizardAction implements IWorkbenchWindowPulldownDelegate {

	private List<SelectionAwareWizardLauncherAction> actions;

	private Menu menu;

	private SelectionAwareWizardLauncherAction loginAction;

	private SelectionAwareWizardLauncherAction headerAction;

	@Override
	public void init(IWorkbenchWindow window) {
		setWorkbenchWindow(window);
		actions = new LinkedList<SelectionAwareWizardLauncherAction>();
		//login configuration
		loginAction = new SelectionAwareWizardLauncherAction(window,"Login", SelectionAwareWizardLauncherAction.AS_PUSH_BUTTON,"com.tibco.cep.studio.dashboard.ui.newwizards", "com.tibco.cep.studio.dashboard.ui.wizards.login");
		loginAction.setImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("login_new_16x16.png"));
		loginAction.setDisabledImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("login_new_disabled_16x16.png"));
		actions.add(loginAction);
		//header configuration
		headerAction = new SelectionAwareWizardLauncherAction(window,"Header", SelectionAwareWizardLauncherAction.AS_PUSH_BUTTON,"com.tibco.cep.studio.dashboard.ui.newwizards", "com.tibco.cep.studio.dashboard.ui.wizards.header");
		headerAction.setImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("header_new_16x16.png"));
		headerAction.setDisabledImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("header_new_disabled_16x16.png"));
		actions.add(headerAction);
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
//		boolean enabled = false;
//		IProject studioProject = getStudioProject(selection);
//		if (studioProject != null) {
//			loginAction.setEnabled(IndexUtils.getAllEntities(studioProject.getName(), ELEMENT_TYPES.LOGIN).isEmpty() == true);
//			headerAction.setEnabled(IndexUtils.getAllEntities(studioProject.getName(), ELEMENT_TYPES.HEADER).isEmpty() == true);
//			enabled = (loginAction.isEnabled() == true || headerAction.isEnabled() == true);
//		}
		action.setEnabled(getStudioProject(selection) != null);
		action.setToolTipText(actions.get(0).getToolTipText());
		action.setImageDescriptor(actions.get(0).getImageDescriptor());
		if (actions.get(0).getDisabledImageDescriptor() != null) {
			action.setDisabledImageDescriptor(actions.get(0).getDisabledImageDescriptor());
		}
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			for (SelectionAwareWizardLauncherAction globalConfigAction : actions) {
				globalConfigAction.selectionChanged(structuredSelection);
			}
		}
	}


	@Override
	public void run(IAction action) {
		actions.get(0).run();
	}


	@Override
	public Menu getMenu(Control parent) {
		if (menu != null) {
			menu.dispose();
		}
		menu = new Menu(parent);
		populateMenu(menu);
		return menu;
	}

	private void populateMenu(Menu menu) {
		if (menu != null) {
			// add all actions
			for (SelectionAwareWizardLauncherAction globalConfigAction : actions) {
				ActionContributionItem extenderItem = new ActionContributionItem(globalConfigAction);
				extenderItem.fill(menu, -1);
			}
		}
	}

	@Override
	public void dispose() {
		if (menu != null) {
			menu.dispose();
		}
	}


}
