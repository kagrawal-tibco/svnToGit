package com.tibco.cep.studio.ui.navigator.actions;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.DeleteResourceAction;
import org.eclipse.ui.actions.SelectionListenerAction;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.security.authz.permissions.actions.ActionsFactory;
import com.tibco.cep.security.authz.permissions.actions.IAction;
import com.tibco.cep.security.authz.permissions.actions.Permit;
import com.tibco.cep.security.authz.utils.PermissionType;
import com.tibco.cep.security.authz.utils.ResourceType;
import com.tibco.cep.security.tokens.Role;
import com.tibco.cep.security.util.AuthTokenUtils;
import com.tibco.cep.security.util.SecurityUtil;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.Utils;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.navigator.model.ChannelDestinationNode;
import com.tibco.cep.studio.ui.navigator.model.DomainInstanceNode;

public class StudioDeleteAction extends SelectionListenerAction implements IShellProvider {

	private IShellProvider fShellProvider;
	private DeleteResourceAction fWrappedDeleteAction;
	
	public StudioDeleteAction(String text, IShellProvider provider) {
		super(text);
		this.fWrappedDeleteAction = new DeleteResourceAction(provider);
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_DELETE);
	}

	@Override
	public void run() {
		if (!checkDirtyEditors()) {
			return;
		}
    	if (Utils.isStandaloneDecisionManger()) {
    		if (!checkDeleteActionAccess()) {
				return;							
			}    	
    	}
		handleNavigatorNodeDelete();
		fWrappedDeleteAction.run();
	}
	
	/**
	 * Destination Deletion Handling
	 */
	@SuppressWarnings("rawtypes")
	private void handleNavigatorNodeDelete() {
		try {
			Set<IResource> eobjectResourceSet = new HashSet<IResource>();
			Set<EObject>  eobjectSet = new HashSet<EObject>();
			List selectedNonResources = getSelectedNonResources();
			Iterator iterator = selectedNonResources.iterator();
			while( iterator.hasNext()) {
				Object object = iterator.next();
				if (object instanceof ChannelDestinationNode) {
					ChannelDestinationNode destNode = (ChannelDestinationNode)object;
					Destination destination = (Destination)destNode.getEntity();
					Channel channel = destination.getDriverConfig().getChannel();
					eobjectSet.add(destination.getDriverConfig().getChannel());
					destination.getDriverConfig().getDestinations().remove(destination);
					eobjectResourceSet.add(IndexUtils.getFile(channel.getOwnerProjectName(), channel));
				}
				if (object instanceof DomainInstanceNode) {
					DomainInstanceNode domInstanceNode = (DomainInstanceNode)object;
					PropertyDefinition propDef = (PropertyDefinition)domInstanceNode.getInstance().getOwnerProperty();
					if(propDef.getOwner() instanceof Concept){
						Concept concept = (Concept)propDef.getOwner();
						propDef.getDomainInstances().remove(domInstanceNode.getInstance());
						eobjectSet.add(propDef);
						eobjectResourceSet.add(IndexUtils.getFile(concept.getOwnerProjectName(), concept));
					}
					if(propDef.getOwner() instanceof Event){
						Event event = (Event)propDef.getOwner();
						propDef.getDomainInstances().remove(domInstanceNode.getInstance());
						eobjectSet.add(propDef);
						eobjectResourceSet.add(IndexUtils.getFile(event.getOwnerProjectName(), event));
					}
					
				}
			}
			for (EObject object : eobjectSet) {
				ModelUtils.saveEObject(object);
			}
			for (IResource resource : eobjectResourceSet) {
				resource.refreshLocal(IFile.DEPTH_INFINITE,  null);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Shell getShell() {
		return fShellProvider.getShell();
	}

	private boolean checkDirtyEditors() {
		try {
			if (!requiresSave()) {
				return true;
			}
			IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			if (activePage != null && activePage.getDirtyEditors().length > 0) {
				if (MessageDialog.openConfirm(Display.getDefault().getActiveShell(), "Save all resources", "To delete resources of this type, you must save all modified resources before continuing.  Would you like to save now?")) {
					return activePage.saveAllEditors(false);
				}
				return false;
			}
		} catch (NullPointerException e) {
		}
		return true;
	}

	@SuppressWarnings({"rawtypes"})
	private boolean requiresSave() {
		try {
			List selectedResources = getSelectedResources();
			for (Object object : selectedResources) {
				if (requiresSave((IResource) object)) {
					return true;
				}
			}
		} catch (CoreException e) {
		}
		return false;
	}
	
	private boolean requiresSave(IResource resource) throws CoreException {
		// TODO : delegate this to each refactoring participant?  Do not like that this is hard coded for these two cases
		if (resource instanceof IFile) {
			if (CommonIndexUtils.DOMAIN_EXTENSION.equalsIgnoreCase(((IFile) resource).getFileExtension())) {
				return true;
			} else if (CommonIndexUtils.STATEMACHINE_EXTENSION.equalsIgnoreCase(((IFile) resource).getFileExtension())) {
				return true;
			}
		} else if (resource instanceof IContainer) {
			IResource[] members = ((IContainer) resource).members();
			for (IResource childResource : members) {
				if (requiresSave(childResource)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	protected boolean updateSelection(IStructuredSelection selection) {
		fWrappedDeleteAction.selectionChanged(selection);
		return super.updateSelection(selection);
	}

	@SuppressWarnings({"rawtypes"})
    private boolean checkDeleteActionAccess() {
		List selectedResources = getSelectedResources();
		Iterator iterator = selectedResources.iterator();
		boolean hasAccess = true;
		try {
			while (iterator.hasNext()) {
				Object object = iterator.next();
				if (object instanceof IFile) {
					IFile rfImplFile = (IFile) object;
					if (rfImplFile.getFileExtension().equals(CommonIndexUtils.RULE_FN_IMPL_EXTENSION)) {
						String projectName = rfImplFile.getProject().getName();
						String resourcePath = rfImplFile.getProjectRelativePath().removeFileExtension().toString();
						if (!resourcePath.startsWith(CommonIndexUtils.PATH_SEPARATOR)) {
							resourcePath = CommonIndexUtils.PATH_SEPARATOR + resourcePath;
						}
						DecisionTableElement decisiontableElement = (DecisionTableElement) IndexUtils.getElement(projectName, resourcePath, ELEMENT_TYPES.DECISION_TABLE);
						Table table = (Table)decisiontableElement.getImplementation();
						hasAccess = checkValidAccess(projectName, ResourceType.RULEFUNCTION, table.getImplements(), "del_impl" );
						if (!hasAccess) {
							MessageDialog.openError(Display.getDefault().getActiveShell(), "Access Denied", "Access Denied to Delete Decision table - " + table.getName());
							break;
						}					
					}					
				}
			}
		} catch (Exception ex) {
			hasAccess = false;
			MessageDialog.openError(Display.getDefault().getActiveShell(), "Access Denied", ex.getMessage());
			StudioCorePlugin.log(ex);
		}
    	return hasAccess;
    }
	
	private boolean checkValidAccess(String projectName, ResourceType resourceType, String resourcePath, String deleteAction) {
		List<Role> roles = AuthTokenUtils.getLoggedInUserRoles();
		Permit permit = Permit.DENY;
		if (!(roles.isEmpty()) && !(resourcePath.isEmpty())) {
			IAction action = ActionsFactory.getAction(resourceType, deleteAction);
			permit = SecurityUtil.ensurePermission(projectName, resourcePath, resourceType, roles, action, PermissionType.BERESOURCE);
		}
		if (Permit.DENY.equals(permit)) {
			return false;
		}
		return true;
	}	
}
