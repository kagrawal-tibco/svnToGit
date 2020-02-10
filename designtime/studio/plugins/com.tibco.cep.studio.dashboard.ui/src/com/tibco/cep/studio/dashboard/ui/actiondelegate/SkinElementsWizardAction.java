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

public class SkinElementsWizardAction extends CommonWizardAction implements IWorkbenchWindowPulldownDelegate {

	private IStructuredSelection selection;

	private List<SelectionAwareWizardLauncherAction> actions;

	private Menu menu;

	@Override
	public void init(IWorkbenchWindow window) {
		setWorkbenchWindow(window);
		actions = new LinkedList<SelectionAwareWizardLauncherAction>();
		//series color
		SelectionAwareWizardLauncherAction action = new SelectionAwareWizardLauncherAction(window,"Series Color", SelectionAwareWizardLauncherAction.AS_PUSH_BUTTON,"com.tibco.cep.studio.dashboard.ui.newwizards", "com.tibco.cep.studio.dashboard.ui.wizards.seriescolor");
		action.setImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("seriescolor_new_16x16.png"));
		action.setDisabledImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("seriescolor_new_disabled_16x16.png"));
		actions.add(action);
		//chart component color set
		action = new SelectionAwareWizardLauncherAction(window,"Chart Component Color Set", SelectionAwareWizardLauncherAction.AS_PUSH_BUTTON,"com.tibco.cep.studio.dashboard.ui.newwizards", "com.tibco.cep.studio.dashboard.ui.wizards.chartcolorset");
		action.setImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("chartcomponentcolorset_new_16x16.png"));
		action.setDisabledImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("chartcomponentcolorset_new_disabled_16x16.png"));
		actions.add(action);
		//text component color set
		action = new SelectionAwareWizardLauncherAction(window,"Text Component Color Set", SelectionAwareWizardLauncherAction.AS_PUSH_BUTTON,"com.tibco.cep.studio.dashboard.ui.newwizards", "com.tibco.cep.studio.dashboard.ui.wizards.textcolorset");
		action.setImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("textcomponentcolorset_new_16x16.png"));
		action.setDisabledImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("textcomponentcolorset_new_disabled_16x16.png"));
		actions.add(action);
		//skin
		action = new SelectionAwareWizardLauncherAction(window,"Skin", SelectionAwareWizardLauncherAction.AS_PUSH_BUTTON,"com.tibco.cep.studio.dashboard.ui.newwizards", "com.tibco.cep.studio.dashboard.ui.wizards.skin");
		action.setImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("skin_new_16x16.png"));
		action.setDisabledImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("skin_new_disabled_16x16.png"));
		actions.add(action);
		//system elements
		action = new SelectionAwareWizardLauncherAction(window,"System Skin", SelectionAwareWizardLauncherAction.AS_PUSH_BUTTON,"com.tibco.cep.studio.dashboard.ui.newwizards", "com.tibco.cep.studio.dashboard.ui.wizards.systemelements");
		action.setImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("system_new_16x16.png"));
		action.setDisabledImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("system_new_disabled_16x16.png"));
		actions.add(action);
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		action.setEnabled(getStudioProject(selection) != null);
		if (selection instanceof IStructuredSelection) {
			this.selection = (IStructuredSelection) selection;
			for (SelectionAwareWizardLauncherAction skinAction : actions) {
				skinAction.selectionChanged(this.selection);
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
		for (SelectionAwareWizardLauncherAction skinAction : actions) {
			ActionContributionItem item = new ActionContributionItem(skinAction);
			item.fill(menu, -1);
		}
	}

	@Override
	public void dispose() {
		if (menu != null) {
			menu.dispose();
		}
	}

}