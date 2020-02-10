package com.tibco.cep.studio.dashboard.ui.editors.views.rolepreference;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalRolePreference;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalView;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.editors.views.TreeContentNode;
import com.tibco.cep.studio.dashboard.ui.editors.views.ViewsTreeViewerAction;
import com.tibco.cep.studio.dashboard.ui.forms.ExceptionHandler;
import com.tibco.cep.studio.dashboard.ui.images.ContributableImageRegistry;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class AddChartsFromViewToGalleryFolderAction extends ViewsTreeViewerAction {

	public static final String ID = AddChartsFromViewToGalleryFolderAction.class.getSimpleName();

	public AddChartsFromViewToGalleryFolderAction(ExceptionHandler exHandler, TreeViewer treeViewer) {
		super(treeViewer, ID, "Add Charts From View", exHandler);
		setImageDescriptor(((ContributableImageRegistry) DashboardUIPlugin.getInstance().getImageRegistry()).getDescriptor("chart_new_16x16.png"));
		setToolTipText("Adds all charts from the selected view");
	}

	@Override
	public void update() {
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		if (selection.size() == 1 && selection.getFirstElement() instanceof TreeContentNode) {
			TreeContentNode node = (TreeContentNode) selection.getFirstElement();
			if (node.getType().equals(BEViewsElementNames.COMPONENT_GALLERY_FOLDER) == true) {
				LocalElement localElement = (LocalElement) node.getData();
				LocalRolePreference rolePreference = null;
				LocalView view = null;
				List<LocalElement> charts = null;
				try {
					rolePreference = getRolePreference(localElement);
					if (rolePreference != null) {
						view = (LocalView) rolePreference.getElement(BEViewsElementNames.VIEW);
						if (view != null) {
							charts = view.getComponents(BEViewsElementNames.getChartOrTextComponentTypes());
							if (charts.isEmpty() == false) {
								List<LocalElement> chartsInFolder = new LinkedList<LocalElement>(localElement.getChildren(BEViewsElementNames.COMPONENT));
								charts.removeAll(chartsInFolder);
								if (charts.isEmpty() == false) {
									setEnabled(true);
								}
							}
						}
					}
				} catch (Exception ex) {
					if (rolePreference == null) {
						exHandler.log(exHandler.createStatus(IStatus.ERROR, "could not find role preference", ex));
					}
					else if (view == null) {
						exHandler.log(exHandler.createStatus(IStatus.ERROR, "could not find view", ex));
					}
					else if (charts == null) {
						exHandler.log(exHandler.createStatus(IStatus.ERROR, "could not find charts", ex));
					}
					else {
						exHandler.log(exHandler.createStatus(IStatus.ERROR, "could not add charts to "+localElement.getDisplayableName(), ex));
					}
				}
			}
		}
	}

	@Override
	public void run() {
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		if (selection.size() == 1 && selection.getFirstElement() instanceof TreeContentNode) {
			TreeContentNode node = (TreeContentNode) selection.getFirstElement();
			LocalElement localElement = (LocalElement) node.getData();
			LocalRolePreference rolePreference = null;
			LocalView view = null;
			List<LocalElement> charts = null;
			try {
				rolePreference = getRolePreference(localElement);
				view = (LocalView) rolePreference.getElement(BEViewsElementNames.VIEW);
				charts = view.getComponents(BEViewsElementNames.getChartOrTextComponentTypes());
				for (LocalElement chart : charts) {
					if (localElement.getElementByID(BEViewsElementNames.COMPONENT, chart.getID()) == null) {
						localElement.addElement(BEViewsElementNames.COMPONENT, chart);
					}
				}
				viewer.refresh();
				((AbstractTreeViewer) viewer).expandToLevel(node, 1);
			} catch (Exception ex) {
				if (rolePreference == null) {
					exHandler.logAndAlert("Add Charts From View", exHandler.createStatus(IStatus.ERROR, "could not find role preference", ex));
				}
				else if (view == null) {
					exHandler.logAndAlert("Add Charts From View", exHandler.createStatus(IStatus.ERROR, "could not find view", ex));
				}
				else if (charts == null) {
					exHandler.logAndAlert("Add Charts From View", exHandler.createStatus(IStatus.ERROR, "could not find charts", ex));
				}
				else {
					exHandler.logAndAlert("Add Charts From View", exHandler.createStatus(IStatus.ERROR, "could not add charts to "+localElement.getDisplayableName(), ex));
				}
			}
		}
	}

	private LocalRolePreference getRolePreference(LocalElement localElement) throws Exception {
		LocalElement parent = localElement.getParent();
		while (parent != null && parent.getElementType().equals(BEViewsElementNames.ROLE_PREFERENCE) == false) {
			parent = parent.getParent();
		}
		return (LocalRolePreference) parent;
	}

}