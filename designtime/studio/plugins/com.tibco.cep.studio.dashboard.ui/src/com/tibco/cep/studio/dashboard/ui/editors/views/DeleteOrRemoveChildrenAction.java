package com.tibco.cep.studio.dashboard.ui.editors.views;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;
import com.tibco.cep.studio.dashboard.ui.forms.ExceptionHandler;

public class DeleteOrRemoveChildrenAction extends ViewsTreeViewerAction {

	public static final String ID = DeleteOrRemoveChildrenAction.class.getSimpleName();

	public DeleteOrRemoveChildrenAction(ExceptionHandler exHandler, TreeViewer treeViewer) {
		super(treeViewer, ID, "Remove Children", exHandler);
		setImageDescriptor(DashboardUIPlugin.getInstance().getImageRegistry().getDescriptor("delete_all_16x16.png"));
		setToolTipText("Remove children");
	}

	@Override
	public void update() {
		setText("Remove Children");
		setToolTipText("Remove children");
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		if (selection.isEmpty() == false) {
			TreeContentNode selectedNode = (TreeContentNode) selection.getFirstElement();
			if (selection.size() == 1 && (selectedNode.getParent() == null || selectedNode.isTopLevelElement() == false) ) {
				LocalElement selectedLocalElement = (LocalElement) selectedNode.getData();
//				List<LocalParticle> particles = selectedLocalElement.getParticles(true);
//				String particleName = null;
//				for (LocalParticle localParticle : particles) {
//					if (localParticle.getMaxOccurs() != 1) {
//						if (particleName == null) {
//							particleName = localParticle.getName();
//						}
//						else if (particleName.equals(localParticle.getName()) == false) {
//							particleName = null;
//							break;
//						}
//					}
//				}
//				if (particleName != null) {
//					setText("Remove "+particleName+"(s)");
//					setToolTipText("Remove "+particleName.toLowerCase()+"s");
//				}
				Collection<String> particleNames = getRemovableParticleNames(selectedLocalElement);
				if (particleNames.size() == 1){
					String particleName = particleNames.iterator().next();
					setText("Remove "+particleName+"(s)");
					setToolTipText("Remove "+particleName.toLowerCase()+"s");
				}
				try {
					setEnabled(selectedLocalElement.getAllChildren(false).size() != 0 || selectedLocalElement.getAllReferences(false).size() != 0);
				} catch (Exception e) {
					exHandler.log(exHandler.createStatus(IStatus.ERROR, "could not determine child count in "+selectedLocalElement.getDisplayableName(), e));
				}
			}
		}
	}

	@Override
	public void run() {
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		if (selection.isEmpty() == false) {
			TreeContentNode selectedNode = (TreeContentNode) selection.getFirstElement();
			if (selection.size() == 1 && (selectedNode.getParent() == null || selectedNode.isTopLevelElement() == false)) {
				LocalElement selectedLocalElement = (LocalElement) selectedNode.getData();
				try {
					if (selectedLocalElement.getAllChildren(false).size() != 0 || selectedLocalElement.getAllReferences(false).size() != 0) {
						Collection<String> particleNames = getRemovableParticleNames(selectedLocalElement);
						String particleNamePrompt = particleNames.size() == 1 ? particleNames.iterator().next().toLowerCase()+"(s)" : "children";
						String msg = "Are you sure you want to remove all " + particleNamePrompt + "?";
						MessageDialog dialog = new MessageDialog(Display.getCurrent().getActiveShell(), "Remove...", null, msg, MessageDialog.QUESTION, new String[] { "Yes", "No" }, 0);
						int userResponse = dialog.open();
						if (userResponse == MessageDialog.OK) {
							for (String particleName : particleNames) {
								try {
									selectedLocalElement.removeChildrenByParticleName(particleName);
								} catch (Exception e) {
									exHandler.logAndAlert("Remove Children", exHandler.createStatus(IStatus.ERROR, "could not remove "+particleName.toLowerCase()+"(s) from "+selectedLocalElement.getDisplayableName(), e));
								}
							}
						}
						viewer.refresh();
						viewer.setSelection(selection, true);
					}
				} catch (Exception e) {
					exHandler.logAndAlert("Remove Children", exHandler.createStatus(IStatus.ERROR, "could not determine child count in "+selectedLocalElement.getDisplayableName(), e));
				}
			}
		}
	}

	private Collection<String> getRemovableParticleNames(LocalElement localElement) {
		Set<String> names = new LinkedHashSet<String>();
		List<LocalParticle> particles = localElement.getParticles(true);
		for (LocalParticle localParticle : particles) {
			if (localParticle.getMaxOccurs() != 1) {
					names.add(localParticle.getName());
			}
		}
		return names;
	}

}