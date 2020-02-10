package com.tibco.cep.studio.dashboard.ui.editors.views.rolepreference;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalRolePreference;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.editors.views.TreeContentNode;
import com.tibco.cep.studio.dashboard.ui.editors.views.ViewsTreeViewerAction;
import com.tibco.cep.studio.dashboard.ui.forms.ExceptionHandler;
import com.tibco.cep.studio.dashboard.ui.utils.NameValidator;

public class AddNewGalleryRootFolderAction extends ViewsTreeViewerAction {

	public static final String ID = AddNewGalleryRootFolderAction.class.getSimpleName();

	public AddNewGalleryRootFolderAction(ExceptionHandler exHandler, TreeViewer treeViewer) {
		super(treeViewer, ID, "New Root Folder", exHandler);
		setImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("folder_new_16x16.gif"));
		setToolTipText("Adds new root folder");
	}

	@Override
	public void update() {
		if (viewer.getInput() instanceof LocalRolePreference) {
			setEnabled(true);
		}
	}

	@Override
	public void run() {
		if (viewer.getInput() instanceof LocalRolePreference) {
			LocalRolePreference rolePreference = (LocalRolePreference) viewer.getInput();
			String newNameHint = rolePreference.getNewName("Gallery", "Folder");
			NameValidator validator = new NameValidator(rolePreference, "Gallery", newNameHint);
			InputDialog dialog = new InputDialog(Display.getCurrent().getActiveShell(), getText(), "Enter the name for a "+getText().toLowerCase(), newNameHint, validator);
			if (dialog.open() == Window.OK) {
				try {
					// get the new name
					String newName = dialog.getValue();
					// create a partition
					LocalElement newFolder = rolePreference.createLocalElement("Gallery");
					// set the new name on the newly create partition
					newFolder.setName(newName);
					//refresh the tree viewer
					viewer.refresh();
					// expand the newly created folder
					((AbstractTreeViewer) viewer).expandToLevel(new TreeContentNode(null, newFolder), TreeViewer.ALL_LEVELS);
				} catch (Exception e) {
					exHandler.logAndAlert("Add "+getText(), exHandler.createStatus(IStatus.ERROR, "could not add a "+getText().toLowerCase(), e));
				}
			}
		}
	}


}