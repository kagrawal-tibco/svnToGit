package com.tibco.cep.studio.ui.navigator.providers;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.getSubConceptProperties;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.getSubEventProperties;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.actions.SelectionListenerAction;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.Utils;
import com.tibco.cep.studio.ui.StudioNavigatorNode;
import com.tibco.cep.studio.ui.actions.EntityRenameElementAction;
import com.tibco.cep.studio.ui.actions.MigrateCoherenceFunctionsAction;
import com.tibco.cep.studio.ui.actions.MigrateMapperFunctionsAction;
import com.tibco.cep.studio.ui.actions.MoveResourceAction;
import com.tibco.cep.studio.ui.actions.RenameElementAction;
import com.tibco.cep.studio.ui.navigator.NavigatorPlugin;
import com.tibco.cep.studio.ui.navigator.actions.WrappedStudioAction;
import com.tibco.cep.studio.ui.navigator.model.StateEntityNode;
import com.tibco.cep.studio.ui.navigator.model.VirtualRuleFunctionImplementationNavigatorNode;

public class StudioNavigatorRefactorActionGroup extends ActionGroup {

	private SelectionListenerAction fRenameAction;

	//Added for validation to duplicate entries due to inheritance 
	private SelectionListenerAction fRenameCustomAction;
	
	private SelectionListenerAction fMoveAction;
	private SelectionListenerAction fMigrateCoherenceAction;
	private SelectionListenerAction fMigrateMapperAction;
	
	public StudioNavigatorRefactorActionGroup(ICommonActionExtensionSite site) {
		makeActions(site.getViewSite().getShell());
	}

	@Override
	public void fillActionBars(IActionBars actionBars) {
		actionBars.setGlobalActionHandler(ActionFactory.RENAME.getId(), fRenameAction);
		actionBars.setGlobalActionHandler(ActionFactory.MOVE.getId(), fMoveAction);
		actionBars.setGlobalActionHandler("fMigrateCoherenceAction", fMigrateCoherenceAction);
		actionBars.setGlobalActionHandler("fMigrateMapperAction", fMigrateMapperAction);
		updateActionBars();
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {
		IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
		
		Object object = selection.getFirstElement();
		if (object instanceof IResource) {
			IResource resource = (IResource)object;
			if (IndexUtils.isProjectLibType(resource)) {
				return;
			}
		}
		
		boolean isPropertyDef = false;
		
		MenuManager refactorMenu = new MenuManager("Refactor");
		menu.appendToGroup("group.refactor", refactorMenu);
		if (object instanceof StudioNavigatorNode) {
			StudioNavigatorNode node = (StudioNavigatorNode)object;
			if (node.getEntity() instanceof PropertyDefinition) {
				PropertyDefinition defn = (PropertyDefinition)node.getEntity();
				
				EList<PropertyDefinition> allList = null;
				if(defn.getOwner() instanceof Concept) {
					Concept concept = (Concept)defn.getOwner();
					allList = concept.getAllProperties();
					List<PropertyDefinition> subList = getSubConceptProperties(concept.getFullPath(), concept.getOwnerProjectName());
					allList.addAll(subList);
				} else if (defn.getOwner() instanceof Event){
					Event event = (Event)defn.getOwner();
					allList = event.getAllUserProperties();
					List<PropertyDefinition> subList = getSubEventProperties(event.getFullPath(), event.getOwnerProjectName());
					allList.addAll(subList);
				}
				EntityRenameElementAction action = (EntityRenameElementAction)((WrappedStudioAction)fRenameCustomAction).getWrappedAction();
				action.setList(allList);
				isPropertyDef = true;
				fRenameCustomAction.selectionChanged(selection);
				refactorMenu.add(fRenameCustomAction);
				
			} 
		}
		if (!isPropertyDef) {
			fRenameAction.selectionChanged(selection);
			refactorMenu.add(fRenameAction);
		}
		fMoveAction.selectionChanged(selection);
		refactorMenu.add(fMoveAction);
		fMigrateCoherenceAction.selectionChanged(selection);
		refactorMenu.add(fMigrateCoherenceAction);
		fMigrateMapperAction.selectionChanged(selection);
		refactorMenu.add(fMigrateMapperAction);
		
		
		//Standalone DM check
		if (Utils.isStandaloneDecisionManger()) {
			if (object instanceof IFile && (((IFile)object).getFileExtension().equals("domain") || 
					 ((IFile)object).getFileExtension().equals("rulefunctionimpl"))) {
				fRenameAction.setEnabled(true);
				fMoveAction.setEnabled(true);
			} else {
				fRenameAction.setEnabled(false);
				fMoveAction.setEnabled(false);
			}
			return;
		}
		
		if(object instanceof IFile && (("display").equalsIgnoreCase(((IFile)object).getFileExtension()))) {
			fRenameAction.setEnabled(false);
			fMoveAction.setEnabled(false);
		}
		
		if (selection.toArray().length > 1) {
			fRenameAction.setEnabled(false);
			fMoveAction.setEnabled(false);
			fMigrateCoherenceAction.setEnabled(false);
			fMigrateMapperAction.setEnabled(false);
		} else if (object instanceof IProject) {
			IProject project = (IProject)object;
			fMoveAction.setEnabled(false);
			if (project.isOpen()) {
				fRenameAction.setEnabled(true);
				fMigrateCoherenceAction.setEnabled(true);
				fMigrateMapperAction.setEnabled(true);
			} else {
				fRenameAction.setEnabled(false);
				fMigrateCoherenceAction.setEnabled(false);
				fMigrateMapperAction.setEnabled(false);
			}
		} else if (!(object instanceof IResource)) {
			//disabling refactor for Decision Table Implementation links under VRF 
			if(object instanceof VirtualRuleFunctionImplementationNavigatorNode ||
					object instanceof StateEntityNode){
				fRenameAction.setEnabled(false);
				fMoveAction.setEnabled(false);
				fMigrateCoherenceAction.setEnabled(false);
				fMigrateMapperAction.setEnabled(false);
			} else if (object instanceof StudioNavigatorNode && ((StudioNavigatorNode) object).isSharedElement()) {
				if (!isPropertyDef) {
					fRenameAction.setEnabled(false);
				} else {
					fRenameCustomAction.setEnabled(false);
				}
				fMoveAction.setEnabled(false);
				fMigrateCoherenceAction.setEnabled(false);
				fMigrateMapperAction.setEnabled(false);
			} else {
				fMoveAction.setEnabled(false);
				fMigrateCoherenceAction.setEnabled(false);
				fMigrateMapperAction.setEnabled(false);
			}
		}
	}

	@Override
	public void updateActionBars() {
		IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
		fRenameAction.selectionChanged(selection);
		fMoveAction.selectionChanged(selection); 
		fMigrateCoherenceAction.selectionChanged(selection);
		fMigrateMapperAction.selectionChanged(selection);
	}

	private void makeActions(final Shell shell) {
		fRenameAction = new WrappedStudioAction("Rename...", new RenameElementAction());
		fRenameAction.setImageDescriptor(NavigatorPlugin.getImageDescriptor("icons/rename.gif"));
		
		fRenameCustomAction = new WrappedStudioAction("Rename...", new EntityRenameElementAction(null));
		fRenameCustomAction.setImageDescriptor(NavigatorPlugin.getImageDescriptor("icons/rename.gif"));
		
		fMoveAction = new WrappedStudioAction("Move...", new MoveResourceAction());
		fMoveAction.setImageDescriptor(NavigatorPlugin.getImageDescriptor("icons/move.gif"));
		fMigrateCoherenceAction = new WrappedStudioAction("Migrate Coherence Function Calls...", new MigrateCoherenceFunctionsAction());
		fMigrateCoherenceAction.setImageDescriptor(NavigatorPlugin.getImageDescriptor("icons/move.gif"));
		fMigrateMapperAction = new WrappedStudioAction("Migrate Mapper Function Calls...", new MigrateMapperFunctionsAction());
		fMigrateMapperAction.setImageDescriptor(NavigatorPlugin.getImageDescriptor("icons/move.gif"));
	}

}
