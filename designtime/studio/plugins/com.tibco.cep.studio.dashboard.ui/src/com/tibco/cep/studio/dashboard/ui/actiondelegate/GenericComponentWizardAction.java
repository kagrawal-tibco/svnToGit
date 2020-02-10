package com.tibco.cep.studio.dashboard.ui.actiondelegate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate;

import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;

public class GenericComponentWizardAction extends CommonWizardAction implements IWorkbenchWindowPulldownDelegate {

	private IStructuredSelection selection;

	private Menu menu;

	private List<IComponentContributor> extensionContributors;

	private SelectionAwareWizardLauncherAction pageSelectorAction;

	@Override
	public void init(IWorkbenchWindow window) {
		setWorkbenchWindow(window);
		extensionContributors = new ArrayList<IComponentContributor>();
		String EXTN_ID = "com.tibco.cep.studio.dashboard.ui.componentcreationtoolbar";
		IConfigurationElement[] members = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTN_ID);

		try {
			for (IConfigurationElement e : members) {
				System.out.println("Evaluating extension");
				final Object extenderObject = e.createExecutableExtension("contributor");
				if (extenderObject instanceof IComponentContributor) {
					extensionContributors.add((IComponentContributor) extenderObject);
				}
			}
			// sort the contributors list
			Collections.sort(extensionContributors, new ContributorSorter());
		} catch (CoreException ex) {
			System.out.println(ex.getMessage());
		}
		pageSelectorAction = new SelectionAwareWizardLauncherAction(window, "Page Selector Component", SelectionAwareWizardLauncherAction.AS_PUSH_BUTTON,"com.tibco.cep.studio.dashboard.ui.newwizards", "com.tibco.cep.studio.dashboard.ui.wizards.pagesetselector");
		pageSelectorAction.setImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("pageselectorcomponent_new_16x16.png"));
		pageSelectorAction.setDisabledImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("pageselectorcomponent_new_disabled_16x16.png"));
	}

	@Override
	public void run(IAction action) {
		extensionContributors.get(0).getAction().run();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		action.setEnabled(getStudioProject(selection) != null);
		action.setToolTipText(extensionContributors.get(0).getAction().getToolTipText());
		action.setImageDescriptor(extensionContributors.get(0).getAction().getImageDescriptor());
		if (extensionContributors.get(0).getAction().getDisabledImageDescriptor() != null) {
			action.setDisabledImageDescriptor(extensionContributors.get(0).getAction().getDisabledImageDescriptor());
		}
		if (selection instanceof IStructuredSelection) {
			this.selection = (IStructuredSelection) selection;
			for (IComponentContributor contributor : extensionContributors) {
				contributor.selectionChanged(this.selection);
			}
			pageSelectorAction.selectionChanged(this.selection);
		}
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
			// add all actions coming from extensions...
			for (IComponentContributor contributor : extensionContributors) {
				Action extenderWizardAction = contributor.getAction();
				ActionContributionItem extenderItem = new ActionContributionItem(extenderWizardAction);
				extenderItem.fill(menu, -1);
			}
			ActionContributionItem pageSelectorItem = new ActionContributionItem(pageSelectorAction);
			pageSelectorItem.fill(menu, -1);
		}

	}

	@Override
	public void dispose() {
		if (menu != null) {
			menu.dispose();
		}
	}

	class ContributorSorter implements Comparator<IComponentContributor> {
		@Override
		public int compare(IComponentContributor item1, IComponentContributor item2) {
			if(item1.getPriority() > item2.getPriority()) {
				return 1;
			}
			if(item1.getPriority() == item2.getPriority()) {
				return (item1.getAction().getText().compareTo(item2.getAction().getText()));
			}
			return -1;
		}
	}

}