package com.tibco.cep.studio.dashboard.ui.editors.views;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.viewers.ContentViewer;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.ui.actions.UpdateableAction;
import com.tibco.cep.studio.dashboard.ui.forms.ExceptionHandler;

public abstract class ViewsTreeViewerAction extends UpdateableAction {

	public ViewsTreeViewerAction(ContentViewer viewer, String id, String text, ExceptionHandler exHandler, int style) {
		super(viewer, id, text, exHandler, style);
	}

	public ViewsTreeViewerAction(ContentViewer viewer, String id, String text, ExceptionHandler exHandler) {
		super(viewer, id, text, exHandler);
	}

	protected LocalParticle getParentParticle(TreeContentNode node) {
		LocalElement localElement = (LocalElement) node.getData();
		//get the parent in the tree viewer
		TreeContentNode parentNode = node.getParent();
		//check if the parent is a particle
		if (parentNode != null && parentNode.isParticle() == true) {
			//yes, it is , return that as parent particle
			return (LocalParticle) parentNode.getData();
		}
		//parent is not a particle, so we need to hunt for a parent particle
		//by finding the physical parent of local element and then searching
		//in the parent for local element
		LocalElement parentLocalElement = null;
		if (parentNode != null) {
			parentLocalElement = (LocalElement) parentNode.getData();
		}
		else {
			try {
				parentLocalElement = localElement.getParent();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (parentLocalElement == null) {
			return null;
		}
//		if (parentNode == null) {
//			if (node.isTopLevelElement() == false) {
//				try {
//					return localElement.getParentParticle();
//				} catch (Exception e) {
//					exHandler.log(exHandler.createStatus(IStatus.ERROR, "could not determine parent particle for " + localElement.getDisplayableName(), e));
//				}
//			}
//			return null;
//		}
//		if (parentNode.isParticle() == true) {
//			return (LocalParticle) parentNode.getData();
//		}

//		LocalElement parentLocalElement = (LocalElement) parentNode.getData();
		for (LocalParticle particle : parentLocalElement.getParticles(true)) {
			try {
				if (particle.getElementByID(localElement.getID()) != null) {
					return particle;
				}
			} catch (Exception e) {
				exHandler.log(exHandler.createStatus(IStatus.ERROR, "could not determine parent particle for " + localElement.getDisplayableName(), e));
			}
		}
		return null;
	}

}
