package com.tibco.cep.studio.dashboard.ui.editors.views.rolepreference;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalRolePreference;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.editors.views.TreeContentNode;
import com.tibco.cep.studio.dashboard.ui.editors.views.ViewsTreeViewerAction;
import com.tibco.cep.studio.dashboard.ui.forms.ExceptionHandler;
import com.tibco.cep.studio.dashboard.ui.images.ContributableImageRegistry;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class AddChartsToGalleryFolderAction extends ViewsTreeViewerAction {

	public static final String ID = AddChartsToGalleryFolderAction.class.getSimpleName();

	public AddChartsToGalleryFolderAction(ExceptionHandler exHandler, TreeViewer treeViewer) {
		super(treeViewer, ID, "Add Charts", exHandler);
		setImageDescriptor(((ContributableImageRegistry) DashboardUIPlugin.getInstance().getImageRegistry()).getDescriptor("chart_new_16x16.png"));
		setToolTipText("Add charts");
	}

	@Override
	public void update() {
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		if (selection.size() == 1 && selection.getFirstElement() instanceof TreeContentNode) {
			TreeContentNode node = (TreeContentNode) selection.getFirstElement();
			if (node.getType().equals(BEViewsElementNames.COMPONENT_GALLERY_FOLDER) == true) {
				setEnabled(true);
			}
		}
	}

	@Override
	public void run() {
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		if (selection.size() == 1 && selection.getFirstElement() instanceof TreeContentNode) {
			TreeContentNode node = (TreeContentNode) selection.getFirstElement();
			if (node.isElement() == true) {
				LocalElement localElement = (LocalElement) node.getData();
				LocalRolePreference rolePreference = null;
				List<LocalElement> existingCharts = null;
				try {
					rolePreference = getRolePreference(localElement);
					existingCharts = localElement.getChildren(BEViewsElementNames.COMPONENT);
					ViewFilterableComponentSelectionDialog chartSelectionDialog = new ViewFilterableComponentSelectionDialog(exHandler, Display.getCurrent().getActiveShell(), rolePreference, existingCharts, true);
					// ChartSelectionDialog chartSelectionDialog = new ChartSelectionDialog(exHandler, Display.getCurrent().getActiveShell(), rolePreference, existingCharts, true, true);
					int returnCode = chartSelectionDialog.open();
					if (returnCode == ChartSelectionDialog.OK) {
						List<LocalElement> newCharts = chartSelectionDialog.getSelections();
						for (LocalElement newChart : newCharts) {
							localElement.addElement(BEViewsElementNames.COMPONENT, newChart);
						}
						viewer.refresh();
						((AbstractTreeViewer) viewer).expandToLevel(node, 1);
					}
				} catch (Exception ex) {
					if (rolePreference == null) {
						exHandler.logAndAlert("Add Charts", exHandler.createStatus(IStatus.ERROR, "could not find role preference", ex));
					} else if (existingCharts == null) {
						exHandler.logAndAlert("Add Charts", exHandler.createStatus(IStatus.ERROR, "could not find existing charts", ex));
					} else {
						exHandler.logAndAlert("Add Charts", exHandler.createStatus(IStatus.ERROR, "could not add charts to [" + localElement.getDisplayableName() + "]", ex));
					}
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