package com.tibco.cep.studio.dashboard.ui.editors.views;

import java.util.HashSet;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.forms.ExceptionHandler;
import com.tibco.cep.studio.dashboard.ui.images.KnownImageKeys;

public class DeleteOrRemoveAction extends ViewsTreeViewerAction {

	public static final String ID = DeleteOrRemoveAction.class.getSimpleName();

	public DeleteOrRemoveAction(ExceptionHandler exHandler, TreeViewer treeViewer) {
		super(treeViewer, ID, "Remove", exHandler);
		setImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor(KnownImageKeys.IMG_KEY_DELETE));
		setToolTipText("Remove selection(s)");
	}

	@Override
	public void update() {
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		if (selection.isEmpty() == false) {
			for (Object selectedObject : selection.toArray()) {
				TreeContentNode selectedNode = (TreeContentNode) selectedObject;
				if (selectedNode.getParent() == null) {
					//the selection is the root, we do not allow root to be deleted/removed
					setEnabled(false);
					return;
				}
				if (selectedNode.isElement() == true) {
					LocalParticle selectedLocalElementParticle = getParentParticle(selectedNode);
					if (selectedLocalElementParticle == null) {
						setEnabled(false);
						return;
					}
				} else if (selectedNode.isParticle() == true) {
					LocalParticle selectedParticle = (LocalParticle) selectedNode.getData();
					LocalElement selectedParticleParent = selectedParticle.getParent();
					if (selectedParticleParent == null) {
						setEnabled(false);
						return;
					}
				}
			}
			setEnabled(true);
		}
	}

	@Override
	public void run() {
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		if (selection.isEmpty() == false) {
			String msg = "Are you sure you want to remove the selected " + (selection.size() == 1 ? "element" : "elements") + "?";
			MessageDialog dialog = new MessageDialog(Display.getCurrent().getActiveShell(), "Remove...", null, msg, MessageDialog.QUESTION, new String[] { "Yes", "No" }, 0);
			int userResponse = dialog.open();
			if (userResponse == MessageDialog.OK) {
				HashSet<TreeContentNode> parents = new HashSet<TreeContentNode>();
				try {
					for (Object selectedObject : selection.toArray()) {
						TreeContentNode selectedNode = (TreeContentNode) selectedObject;
						if (selectedNode.isElement() == true) {
							LocalElement selectedLocalElement = (LocalElement) selectedNode.getData();
							LocalParticle selectedLocalElementParticle = getParentParticle(selectedNode);
							if (selectedLocalElementParticle != null) {
								selectedLocalElementParticle.getParent().removeElement(selectedLocalElementParticle.getName(), selectedLocalElement.getName(), LocalElement.FOLDER_NOT_APPLICABLE);
								parents.add(selectedNode.getParent());
							}
						} else if (selectedNode.isParticle() == true) {
							LocalParticle selectedParticle = (LocalParticle) selectedNode.getData();
							LocalElement selectedParticleParent = selectedParticle.getParent();
							selectedParticleParent.removeChildrenByParticleName(selectedParticle.getName());
							parents.add(selectedNode);
						}
					}
					viewer.refresh();
					Object newSelection = parents.toArray()[parents.size() - 1];
					if (newSelection != null) {
						viewer.setSelection(new StructuredSelection(newSelection), true);
					}
				} catch (Exception e) {
					exHandler.logAndAlert("Remove...", exHandler.createStatus(IStatus.ERROR, "could not remove selected elements from " + ((LocalElement) viewer.getInput()).getDisplayableName(), e));
				}
			}
		}
	}

}