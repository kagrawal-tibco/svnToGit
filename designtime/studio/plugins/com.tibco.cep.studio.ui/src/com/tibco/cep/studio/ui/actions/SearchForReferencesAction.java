package com.tibco.cep.studio.ui.actions;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.ResourceHelper;
import com.tibco.cep.studio.ui.search.StudioElementSearchQuery;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

public class SearchForReferencesAction implements IWorkbenchWindowActionDelegate {

	protected ISelection fSelection;

	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		this.fSelection = selection;
		//Enable/disable for IAdaptable
		StructuredSelection sel = (StructuredSelection) fSelection;
		if (!selection.isEmpty()) {
			Object selectedElement = sel.getFirstElement();
			if (selectedElement instanceof IResource) {
				action.setEnabled(true);
			}else if (selectedElement instanceof IAdaptable) {
				Object adapter = ((IAdaptable) selectedElement).getAdapter(Entity.class);
				if (adapter instanceof StateEntity) {
					action.setEnabled(false);
				}
			}
		}	
	}

	public void run(IAction action) {
		if (!(fSelection instanceof StructuredSelection)) {
			return;
		}
		
		StructuredSelection selection = (StructuredSelection) fSelection;
		if (selection.isEmpty()) {
			return;
		}
		
		Object selectedElement = selection.getFirstElement();
		if (selectedElement instanceof IResource) {
			IResource resource = (IResource) selectedElement;
			DesignerElement element = IndexUtils.getElement(resource);
			if (element != null) {
				StudioElementSearchQuery query = new StudioElementSearchQuery(resource.getProject().getName(), element);
				NewSearchUI.runQueryInBackground(query);
			} else {
				// attempt to load the file, and see if it gets an EObject
				EObject object = IndexUtils.loadEObject(ResourceHelper.getLocationURI(resource));
				if (object != null) {
					StudioElementSearchQuery query = new StudioElementSearchQuery(resource.getProject().getName(), object);
					NewSearchUI.runQueryInBackground(query);
				} else {
					System.out.println("Selection does not resolve to a Studio element");
				}
			}
		} else if (selectedElement instanceof IAdaptable) {
			Object adapter = ((IAdaptable) selectedElement).getAdapter(Entity.class);
			if (adapter instanceof Entity) {
				Entity entity = (Entity) adapter;
				StudioElementSearchQuery query = new StudioElementSearchQuery(entity.getOwnerProjectName(), entity);
				NewSearchUI.runQueryInBackground(query);
			}
		} else if (selectedElement instanceof SharedElement) {
			SharedElement el = (SharedElement) selectedElement;
			EObject root = StudioUIUtils.getRoot(el);
			String projectName = "";
			if (root instanceof DesignerProject) {
				projectName = ((DesignerProject)root).getName();
			}		
			StudioElementSearchQuery query = new StudioElementSearchQuery(projectName, el);
			NewSearchUI.runQueryInBackground(query);
		}
	}

}
