package com.tibco.cep.studio.dashboard.ui.editors.views;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.forms.ExceptionHandler;
import com.tibco.cep.studio.dashboard.ui.utils.NameValidator;

public class RenameAction extends ViewsTreeViewerAction {

	public static final String ID = RenameAction.class.getSimpleName();

	public RenameAction(ExceptionHandler exHandler, TreeViewer treeViewer) {
		super(treeViewer, ID, "Rename", exHandler);
		setImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("rename_16x16.gif"));
		setToolTipText("Rename selection");
	}

	@Override
	public void update() {
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		if (selection.size() == 1 && selection.getFirstElement() instanceof TreeContentNode) {
			TreeContentNode node = (TreeContentNode) selection.getFirstElement();
			if (node.isElement() && node.isTopLevelElement() == false) {
				setEnabled(true);
			}
		}
	}

	@Override
	public void run() {
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		if (selection.size() == 1 && selection.getFirstElement() instanceof TreeContentNode) {
			TreeContentNode node = (TreeContentNode) selection.getFirstElement();
			if (node.isElement() && node.isTopLevelElement() == false) {
				LocalElement selectedElement = (LocalElement) node.getData();
				try {
					LocalElement parent = selectedElement.getParent();
					String childType = selectedElement.getElementType();
					String currentName = selectedElement.getName();
					InputDialog dialog = new FixedWidthInputDialog(Display.getCurrent().getActiveShell(), "Rename", "Enter a new name", selectedElement.getName(), new NameValidator(parent, childType, currentName));
					if (dialog.open() == Window.OK) {
						// get the new name
						String newName = dialog.getValue();
						if (selectedElement.getName().equals(newName) == false) {
							// rename the element
							selectedElement.setName(newName);
							// refresh the tree viewer
							viewer.refresh();
							// re-fire the selection to trigger selection listeners
							viewer.setSelection(selection);
						}
					}
				} catch (Exception e) {
					exHandler.logAndAlert("Rename", exHandler.createStatus(IStatus.ERROR, "could not rename " + selectedElement.getDisplayableName(), e));
				}
			}
		}
	}

	class FixedWidthInputDialog extends InputDialog {

		public FixedWidthInputDialog(Shell parentShell, String dialogTitle, String dialogMessage, String initialValue, IInputValidator validator) {
			super(parentShell, dialogTitle, dialogMessage, initialValue, validator);
		}

		@Override
		protected Point getInitialSize() {
			return getShell().computeSize(500, SWT.DEFAULT, true);
		}

	}

}