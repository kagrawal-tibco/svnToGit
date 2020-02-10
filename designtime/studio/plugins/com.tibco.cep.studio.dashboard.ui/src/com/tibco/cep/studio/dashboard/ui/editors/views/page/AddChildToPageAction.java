package com.tibco.cep.studio.dashboard.ui.editors.views.page;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.editors.views.TreeContentNode;
import com.tibco.cep.studio.dashboard.ui.editors.views.ViewsTreeViewerAction;
import com.tibco.cep.studio.dashboard.ui.editors.views.page.ComponentSelectionDialog.SELECTION_TYPE;
import com.tibco.cep.studio.dashboard.ui.forms.ExceptionHandler;
import com.tibco.cep.studio.dashboard.ui.images.ContributableImageRegistry;
import com.tibco.cep.studio.dashboard.ui.utils.NameValidator;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class AddChildToPageAction extends ViewsTreeViewerAction {

	public static final String ID = AddChildToPageAction.class.getSimpleName();

	public AddChildToPageAction(ExceptionHandler exHandler, TreeViewer treeViewer) {
		super(treeViewer, ID, "Add", exHandler);
		setImageDescriptor(((ContributableImageRegistry) DashboardUIPlugin.getInstance().getImageRegistry()).getDescriptor("add_16x16.gif"));
		setToolTipText("Adds children");
	}

	@Override
	public void update() {
		setText("Add");
		setToolTipText("Adds children");
		TreeContentNode node = getSelectedTreeContentNode();
		if (node != null) {
			if (BEViewsElementNames.DASHBOARD_PAGE.equals(node.getType()) == true) {
				setText("Add Partition");
				setToolTipText("Add partition to selected page");
				setEnabled(true);
			}
			else if (BEViewsElementNames.PARTITION.equals(node.getType()) == true) {
				setText("Add Panel");
				setToolTipText("Add panel to selected partition");
				setEnabled(true);
			}
			else if (BEViewsElementNames.PANEL.equals(node.getType()) == true) {
				setText("Add Component(s)");
				setToolTipText("Adds component(s) to selected panel");
				LocalElement panel = (LocalElement) node.getData();
				try {
					List<LocalElement> components = panel.getChildren(BEViewsElementNames.COMPONENT);
					if (components.isEmpty() == true) {
						setEnabled(!panel.getRoot().getChildren(BEViewsElementNames.COMPONENT).isEmpty());
					}
					else if (components.size() > 1) {
						List<LocalElement> extraCharts = panel.getRoot().getChildren(BEViewsElementNames.TEXT_OR_CHART_COMPONENT);
						extraCharts.removeAll(components);
						// we have more then one component, enable chart
						setEnabled(!extraCharts.isEmpty());
					}
					else {
						// we have only one component, check if the component is chart or not
						boolean isChartComponent = BEViewsElementNames.getChartOrTextComponentTypes().contains(components.get(0).getElementType());
						if (isChartComponent == true) {
							List<LocalElement> extraCharts = panel.getRoot().getChildren(BEViewsElementNames.TEXT_OR_CHART_COMPONENT);
							extraCharts.removeAll(components);
							setEnabled(!extraCharts.isEmpty());
						}
//						else {
//							//enable add other component to allow replace
//							List<LocalElement> extraComponents = panel.getRoot().getChildren(BEViewsElementNames.OTHER_COMPONENT);
//							extraComponents.removeAll(components);
//							setEnabled(!extraComponents.isEmpty());
//						}
					}
				} catch (Exception e) {
					exHandler.log(exHandler.createStatus(IStatus.ERROR, "could not retrieve components from " + panel + " in " + ((LocalElement)viewer.getInput()).getDisplayableName(), e));
				}
			}
		}
	}

	@Override
	public void run() {
		TreeContentNode node = getSelectedTreeContentNode();
		if (node != null) {
			if (BEViewsElementNames.DASHBOARD_PAGE.equals(node.getType()) == true) {
				addNewPartition(node);
			}
			else if (BEViewsElementNames.PARTITION.equals(node.getType()) == true) {
				addNewPanel(node);
			}
			else if (BEViewsElementNames.PANEL.equals(node.getType()) == true) {
				addComponents(node);
			}
		}
	}

	private void addNewPartition(TreeContentNode node) {
		LocalElement page = (LocalElement) node.getData();

		String newNameHint = page.getNewName(BEViewsElementNames.PARTITION, BEViewsElementNames.PARTITION);

		Image dialogIcon = DashboardUIPlugin.getInstance().getImageRegistry().get("partition_16x16.gif");
		InputDialog dialog = new IconableInputDialog(Display.getCurrent().getActiveShell(), dialogIcon,"New Partition", "Enter the name for a new partition", newNameHint, new NameValidator(page, BEViewsElementNames.PARTITION, newNameHint));

		if (dialog.open() == Window.OK) {
			try {
				// get the new name
				String newName = dialog.getValue();
				// create a partition
				LocalElement newPartition = page.createLocalElement(BEViewsElementNames.PARTITION);
				// set the new name on the newly create partition
				newPartition.setName(newName);
				// refresh the tree viewer
				viewer.refresh();
				//select the newly create partition
				viewer.setSelection(new StructuredSelection(new TreeContentNode(node,newPartition)),true);
			} catch (Exception e) {
				exHandler.logAndAlert("Add New Partition", exHandler.createStatus(IStatus.ERROR, "could not add a new partition to " + ((LocalElement) viewer.getInput()).getDisplayableName(), e));
			}
		}
	}

	private void addNewPanel(TreeContentNode node) {
		LocalElement partition = (LocalElement) node.getData();

		String newNameHint = partition.getNewName(BEViewsElementNames.PANEL, BEViewsElementNames.PANEL);

		Image dialogIcon = DashboardUIPlugin.getInstance().getImageRegistry().get("panel_16x16.gif");
		InputDialog dialog = new IconableInputDialog(Display.getCurrent().getActiveShell(), dialogIcon, "New Panel", "Enter the name for a new panel", newNameHint, new NameValidator(partition, BEViewsElementNames.PANEL, newNameHint));

		if (dialog.open() == Window.OK) {
			try {
				// get the new name
				String newName = dialog.getValue();
				// create a panel
				LocalElement newPanel = partition.createLocalElement(BEViewsElementNames.PANEL);
				// set the new name on the newly create partition
				newPanel.setName(newName);
				// refresh the tree viewer
				viewer.refresh();
				//select the newly create partition
				viewer.setSelection(new StructuredSelection(new TreeContentNode(node,newPanel)),true);
			} catch (Exception e) {
				exHandler.logAndAlert("Add New Panel", exHandler.createStatus(IStatus.ERROR, "could not add a new panel to " + partition.getDisplayableName() + " in " + ((LocalElement) viewer.getInput()).getDisplayableName(), e));
			}
		}

	}

	private void addComponents(TreeContentNode node) {
		LocalElement panel = (LocalElement) node.getData();
		ComponentSelectionDialog dialog = null;
		try {
			List<LocalElement> components = panel.getChildren(BEViewsElementNames.COMPONENT);
			LocalECoreFactory localECoreFactory = (LocalECoreFactory) panel.getRoot();
			if (components.isEmpty() == true) {
				//check if we have other components
				if (panel.getRoot().getChildren(BEViewsElementNames.OTHER_COMPONENT).isEmpty() == true) {
					dialog = new ComponentSelectionDialog(exHandler, Display.getCurrent().getActiveShell(), localECoreFactory, null, true, SELECTION_TYPE.CHART_OR_TEXT_COMPONENTS);
				}
				else {
					dialog = new ComponentSelectionDialog(exHandler, Display.getCurrent().getActiveShell(), localECoreFactory, null, true, SELECTION_TYPE.ALL_COMPONENTS);
				}
			}
			else if (components.size() > 1) {
				dialog = new ComponentSelectionDialog(exHandler, Display.getCurrent().getActiveShell(), localECoreFactory, components, true, SELECTION_TYPE.CHART_OR_TEXT_COMPONENTS);
			}
			else {
				// we have only one component, check if the component is chart or not
				boolean isChartComponent = BEViewsElementNames.getChartOrTextComponentTypes().contains(components.get(0).getElementType());
				if (isChartComponent == true) {
					dialog = new ComponentSelectionDialog(exHandler, Display.getCurrent().getActiveShell(), localECoreFactory, components, true, SELECTION_TYPE.CHART_OR_TEXT_COMPONENTS);
				}
				else {
					//enable add other component to allow replace
					dialog = new ComponentSelectionDialog(exHandler, Display.getCurrent().getActiveShell(), localECoreFactory, components, true, SELECTION_TYPE.NON_CHART_OR_TEXT_COMPONENTS);
				}
			}
			if (dialog != null) {
				int returnCode = dialog.open();
				if (returnCode == ComponentSelectionDialog.OK){
					List<LocalElement> selections = dialog.getSelections();
					for (LocalElement selection : selections) {
						panel.addElement(BEViewsElementNames.COMPONENT, selection);
					}
					viewer.refresh();
					//expand the panel to show the newly added components
					((AbstractTreeViewer) viewer).expandToLevel(node, 1);
				}
			}
			//re-fire the selection to fire action updating
			viewer.setSelection(new StructuredSelection(node),true);
		} catch (Exception e) {
			exHandler.logAndAlert("Add New Component", exHandler.createStatus(IStatus.ERROR, "could not retrieve components from " + panel + " in " + ((LocalElement)viewer.getInput()).getDisplayableName(), e));
		}
	}

	private TreeContentNode getSelectedTreeContentNode() {
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		if (selection.size() == 1 && selection.getFirstElement() instanceof TreeContentNode) {
			return (TreeContentNode) selection.getFirstElement();
		}
		return null;
	}


	class IconableInputDialog extends InputDialog {

		private Image dialogIcon;

		IconableInputDialog(Shell parentShell, Image dialogIcon, String dialogTitle, String dialogMessage, String initialValue, IInputValidator validator) {
			super(parentShell, dialogTitle, dialogMessage, initialValue, validator);
			this.dialogIcon = dialogIcon;
		}

		@Override
		protected void configureShell(Shell shell) {
			super.configureShell(shell);
			if (dialogIcon != null) {
				shell.setImage(dialogIcon);
			}
		}
	}
}
