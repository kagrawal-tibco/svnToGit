package com.tibco.cep.studio.dashboard.ui.editors.views;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.forms.ExceptionHandler;
import com.tibco.cep.studio.dashboard.ui.images.KnownImageKeys;

public class MoveUpAction extends ViewsTreeViewerAction {

	public static final String ID = MoveUpAction.class.getSimpleName();

	public MoveUpAction(ExceptionHandler exHandler, TreeViewer treeViewer) {
		super(treeViewer, ID, "Move Up", exHandler);
		setImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor(KnownImageKeys.IMG_KEY_UP));
		setToolTipText("Move selection(s) up");
	}

	@Override
	public void update() {
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		TreeContentNode parent = null;
		for (Object selectedObject : selection.toArray()) {
			TreeContentNode selectedNode = (TreeContentNode) selectedObject;
			//get the parent
			if (parent == null) {
				parent = selectedNode.getParent();
			}
			else if (parent != selectedNode.getParent()) {
				//the selection do not belong to same parent
				setEnabled(false);
				return;
			}
			if (parent == null) {
				//the selection is the root, we do not allow root to be moved up or down
				setEnabled(false);
				return;
			}
			LocalParticle parentParticle = getParentParticle(selectedNode);
			if (parentParticle == null) {
				setEnabled(false);
				return;
			}
			List<LocalElement> elements = parentParticle.getElements(true);
			int idx = elements.indexOf(selectedNode.getData());
			if (idx == 0) {
				setEnabled(false);
				return;
			}
			setEnabled(true);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void run() {
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		if (selection.isEmpty() == false) {
			try {
				List selections = new LinkedList(selection.toList());
				for (Object selectedElement : selections) {
					TreeContentNode selectedNode = (TreeContentNode) selectedElement;

					LocalElement selectedLocalElement = (LocalElement) selectedNode.getData();

					LocalParticle selectedLocalElementParentParticle = getParentParticle(selectedNode);

					if (selectedLocalElementParentParticle != null) {
						List<LocalElement> children = selectedLocalElementParentParticle.getElements(true);
						int selectedLocalElementIdx = children.indexOf(selectedLocalElement);
						if (selectedLocalElementIdx != -1) {
							LocalElement elementAboveSelectedLocalElement = children.get(selectedLocalElementIdx - 1);
							selectedLocalElementParentParticle.swapElements(elementAboveSelectedLocalElement, selectedLocalElement);
						}
					}
				}
				viewer.refresh();
				// re-fire the selection to let the tool bar update it's state
				viewer.setSelection(selection, true);
			} catch (Exception e) {
				exHandler.logAndAlert("Move Down", exHandler.createStatus(IStatus.ERROR, "could not move selection(s) up in " + ((LocalElement) viewer.getInput()).getDisplayableName(), e));
			}
		}
	}

}